/*******************************************************************************
 * @file   NewFirmwareWizard.java
 *
 * @author Sree Hari Vignesh, Kalycito Infotech Private Limited.
 *
 * @copyright (c) 2017, Kalycito Infotech Private Limited
 *                    All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   * Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *   * Neither the name of the copyright holders nor the
 *     names of its contributors may be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *******************************************************************************/

package org.epsg.openconfigurator.wizards;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.epsg.openconfigurator.model.FirmwareManager;
import org.epsg.openconfigurator.model.Module;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.model.PowerlinkRootNode;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectUtils;
import org.epsg.openconfigurator.util.PluginErrorDialogUtils;
import org.epsg.openconfigurator.xmlbinding.projectfile.FirmwareList;
import org.epsg.openconfigurator.xmlbinding.projectfile.FirmwareList.Firmware;
import org.epsg.openconfigurator.xmlbinding.projectfile.InterfaceList;
import org.epsg.openconfigurator.xmlbinding.projectfile.TCN;

/**
 * Wizard page to add new firmware.
 *
 * @author Sree Hari
 *
 */
public class NewFirmwareWizard extends Wizard {

    private static final String WINDOW_TITLE = "POWERLINK firmware wizard";

    private static Module getModule(Object obj) {
        if (obj instanceof Module) {
            Module module = (Module) obj;
            return module;
        }
        return null;
    }

    private static Node getNode(Object obj) {
        if (obj instanceof Node) {
            Node node = (Node) obj;
            return node;
        }
        if (obj instanceof Module) {
            Module module = (Module) obj;
            return module.getNode();
        }
        return null;
    }

    private static Object getObjModel(Object obj) {
        if (obj instanceof Node) {
            Node node = (Node) obj;
            return node.getNodeModel();
        }
        if (obj instanceof Module) {
            Module module = (Module) obj;
            return module.getModelOfModule();
        }
        return null;
    }

    /**
     * Add validateFirmwareWizardPage
     */
    private final ValidateFirmwareWizardPage validateFirmwarePage;

    /**
     * Selected node object. The new node will be added below this node.
     */
    private Node selectedNodeObj;

    private Module selectedModuleObj;

    private Firmware firmwareObj;

    private Object nodeOrModuleObj;

    public NewFirmwareWizard(
            @SuppressWarnings("unused") PowerlinkRootNode nodeList,
            Object selectedObj) {
        if (selectedObj == null) {
            System.err.println("Invalid node selection");
        }
        nodeOrModuleObj = selectedObj;
        Object nodeModel = null;
        Object moduleModel = null;
        if (selectedObj instanceof Node) {
            selectedNodeObj = (Node) selectedObj;
            if (selectedNodeObj != null) {
                nodeModel = selectedNodeObj.getNodeModel();
            }
            if (nodeModel == null) {
                System.err.println("The NodeModel is empty!");
            }
        } else if (selectedObj instanceof Module) {
            selectedModuleObj = (Module) selectedObj;
            if (selectedModuleObj != null) {
                moduleModel = selectedModuleObj.getModelOfModule();
            }
            if (moduleModel == null) {
                System.err.println("The Module Model is empty!");
            }
        }

        setWindowTitle(WINDOW_TITLE);
        validateFirmwarePage = new ValidateFirmwareWizardPage(selectedObj);
    }

    /**
     * Add wizard page
     */
    @Override
    public void addPages() {
        super.addPages();
        addPage(validateFirmwarePage);
    }

    @Override
    public boolean canFinish() {

        if (!validateFirmwarePage.isPageComplete()) {
            return false;
        }

        return true;
    }

    @Override
    public boolean performFinish() {
        Path firmwareFilePath = validateFirmwarePage
                .getFirmwareConfigurationPath();

        Object objModel = getObjModel(nodeOrModuleObj);
        if (firmwareFilePath != null) {
            Path firmwareFileName = firmwareFilePath.getFileName();
            if (validateFmwareFileName(firmwareFilePath)) {
                MessageDialog dialog = new MessageDialog(null,
                        "Firmware file exists", null,
                        "The firmware file with name '"
                                + firmwareFileName.toString()
                                + "' already exists in the project. \nPlease rename the file and try again.",
                        MessageDialog.ERROR, new String[] { "OK" }, 0);
                dialog.open();
                return false;
            }

            if (updateFirmwareFile(firmwareFilePath, objModel,
                    nodeOrModuleObj)) {
                String nodeId = getNode(nodeOrModuleObj).getNodeIdString();
                int modulePos = 0;
                if (getModule(nodeOrModuleObj) != null) {
                    modulePos = getModule(nodeOrModuleObj).getPosition();
                }
                PowerlinkRootNode rootNode = getNode(nodeOrModuleObj)
                        .getPowerlinkRootNode();
                List<Node> cnNodeList = rootNode.getCnNodeList();
                for (Node node : cnNodeList) {
                    List<String> nodeFirmwareFileNameList = new ArrayList<>();
                    if (!node.getNodeFirmwareCollection().isEmpty()) {
                        nodeFirmwareFileNameList = node
                                .getNodeFirmwareFileNameList();
                    }
                    Object nodeObj = node.getNodeModel();
                    String newNodeFirmwareFileName = StringUtils.EMPTY;
                    if (firmwareFilePath.getFileName() != null) {
                        newNodeFirmwareFileName = firmwareFilePath.getFileName()
                                .toString();
                    }

                    if (!nodeId.equalsIgnoreCase(node.getNodeIdString())) {
                        if (!validateFirmwarePage
                                .checkWithXddAttributes(nodeObj)) {
                            if (!nodeFirmwareFileNameList
                                    .contains(newNodeFirmwareFileName)) {
                                updateFirmwareFile(firmwareFilePath, nodeObj,
                                        node);
                            }
                        }
                    }
                    if (node.getInterface() != null) {
                        if (!node.getInterface().getModuleCollection()
                                .isEmpty()) {
                            for (Module module : node.getInterface()
                                    .getModuleCollection().values()) {
                                List<String> firmwareFileNameList = new ArrayList<>();
                                if (!module.getModuleFirmwareCollection()
                                        .isEmpty()) {
                                    firmwareFileNameList = module
                                            .getModuleFirmwareFileNameList();
                                }
                                int position = module.getPosition();
                                Object moduleObj = module.getModelOfModule();
                                if (position != modulePos) {
                                    if (!validateFirmwarePage
                                            .checkWithXddAttributes(
                                                    moduleObj)) {
                                        String newFirmwareFileName = StringUtils.EMPTY;

                                        if (firmwareFilePath
                                                .getFileName() != null) {
                                            newFirmwareFileName = firmwareFilePath
                                                    .getFileName().toString();
                                        }

                                        if (module
                                                .canFirmwareAdded(moduleObj)) {

                                            if (!firmwareFileNameList.contains(
                                                    newFirmwareFileName)) {
                                                updateFirmwareFile(
                                                        firmwareFilePath,
                                                        moduleObj, module);
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }

        return true;
    }

    public boolean updateFirmwareFile(Path firmwareFilePath, Object objModel,
            Object nodeOrModule) {
        Charset charset = Charset.forName("UTF-8");
        Firmware firmware = new Firmware();
        System.err.println("Object model of fiermware.." + nodeOrModule);
        if (objModel instanceof TCN) {
            TCN cn = (TCN) objModel;
            FirmwareList firmwareList = cn.getFirmwareList();

            if (firmwareList != null) {
                firmwareList.getFirmware().add(firmware);
            } else {
                FirmwareList fwList = new FirmwareList();
                cn.setFirmwareList(fwList);
                fwList.getFirmware().add(firmware);
            }
        } else if (objModel instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module mod = (InterfaceList.Interface.Module) objModel;
            FirmwareList firmwareList = mod.getFirmwareList();

            if (firmwareList != null) {
                firmwareList.getFirmware().add(firmware);
            } else {
                FirmwareList fwList = new FirmwareList();
                mod.setFirmwareList(fwList);
                fwList.getFirmware().add(firmware);
            }
        }

        firmware.setURI(firmwareFilePath.toString());

        firmwareObj = firmware;
        FirmwareManager firmwareMngr = new FirmwareManager(nodeOrModule,
                validateFirmwarePage.getFirmwareModel(), firmwareObj);

        byte[] venId = String.valueOf(firmwareMngr.getVendorId())
                .getBytes(charset);
        firmware.setVendorId(venId);

        if (nodeOrModule instanceof Node) {
            Node cnNode = (Node) nodeOrModule;
            cnNode.getNodeFirmwareCollection().put(firmwareMngr,
                    firmwareMngr.getFirmwarefileVersion());
            for (int version : cnNode.getNodeFirmwareCollection().values()) {
                if (firmwareMngr.getFirmwarefileVersion() < version) {
                    MessageDialog dialog = new MessageDialog(null,
                            "Add lowest version firmware file", null,
                            "The firmware file version '"
                                    + firmwareMngr.getFirmwarefileVersion()
                                    + "' is lower than the available firmware files. "
                                    + "Do you wish to continue? ",
                            MessageDialog.WARNING, new String[] { "Yes", "No" },
                            1);
                    int result = dialog.open();
                    if (result != 0) {
                        return false;
                    }

                }
            }
        } else if (nodeOrModule instanceof Module) {
            Module cnModule = (Module) nodeOrModuleObj;
            cnModule.getModuleFirmwareCollection().put(firmwareMngr,
                    firmwareMngr.getFirmwarefileVersion());
            for (int version : cnModule.getModuleFirmwareCollection()
                    .values()) {
                if (firmwareMngr.getFirmwarefileVersion() < version) {
                    MessageDialog dialog = new MessageDialog(null,
                            "Add lowest version firmware file", null,
                            "The firmware file version '"
                                    + firmwareMngr.getFirmwarefileVersion()
                                    + "' is lower than the available firmware files. "
                                    + "Do you wish to continue? ",
                            MessageDialog.WARNING, new String[] { "Yes", "No" },
                            1);
                    int result = dialog.open();
                    if (result != 0) {
                        return false;
                    }

                }
            }
        }

        try {
            OpenConfiguratorProjectUtils.importFirmwareFile(firmwareFilePath,
                    firmwareMngr);
        } catch (IOException e) {
            System.err.println("The firmware file import is not successfull.");
            PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                    "File is out of sync with the file system.",
                    getNode(nodeOrModule).getProject().getName());
            e.printStackTrace();
            return false;
        }

        if (firmwareMngr.updateFirmwareInProjectFile(firmwareMngr, nodeOrModule,
                firmwareObj)) {
            System.out.println(
                    "Firmware file successfully updated in project file.");
        } else {
            System.err
                    .println("Firmware file failed to update in project file.");
        }

        try {
            firmwareMngr.getProject().refreshLocal(IResource.DEPTH_INFINITE,
                    new NullProgressMonitor());
        } catch (CoreException e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * Validates the name of imported firmware file with available file in
     * device firmware directory.
     *
     * @param firmwareFilePath The imported firmware file path.
     * @return <code>true</code> if file name available, <code>false</code>
     *         otherwise.
     */
    private boolean validateFmwareFileName(Path firmwareFilePath) {
        Path nameOfFirmwareFile = firmwareFilePath.getFileName();
        String fileName = StringUtils.EMPTY;
        if (nameOfFirmwareFile != null) {
            fileName = nameOfFirmwareFile.toString();
        }
        if (nodeOrModuleObj != null) {

            List<String> firmwareFileList = new ArrayList<>();

            if (nodeOrModuleObj instanceof Node) {
                Node node = (Node) nodeOrModuleObj;
                if (!node.getNodeFirmwareCollection().isEmpty()) {
                    for (FirmwareManager fwMngr : node
                            .getNodeFirmwareCollection().keySet()) {
                        if (fwMngr.getFirmwareUri() != null) {
                            File firmwareDirectory = new File(
                                    fwMngr.getFirmwareUri());
                            firmwareFileList.add(firmwareDirectory.getName());
                        }
                    }
                }
            } else if (nodeOrModuleObj instanceof Module) {
                Module module = (Module) nodeOrModuleObj;
                if (!module.getModuleFirmwareCollection().isEmpty()) {
                    for (FirmwareManager fwMngr : module
                            .getModuleFirmwareCollection().keySet()) {
                        if (fwMngr.getFirmwareUri() != null) {
                            File firmwareDirectory = new File(
                                    fwMngr.getFirmwareUri());
                            firmwareFileList.add(firmwareDirectory.getName());
                        }
                    }
                }
            }

            for (String fwFile : firmwareFileList) {
                if (!fileName.isEmpty()) {
                    if (fwFile.equalsIgnoreCase(fileName)) {
                        return true;
                    }
                }
            }

        }
        return false;
    }
}

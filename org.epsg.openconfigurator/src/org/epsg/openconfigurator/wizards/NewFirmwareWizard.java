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

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.epsg.openconfigurator.console.OpenConfiguratorMessageConsole;
import org.epsg.openconfigurator.lib.wrapper.NodeAssignment;
import org.epsg.openconfigurator.lib.wrapper.OpenConfiguratorCore;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.model.FirmwareManager;
import org.epsg.openconfigurator.model.IControlledNodeProperties;
import org.epsg.openconfigurator.model.Module;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.model.PowerlinkRootNode;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectUtils;
import org.epsg.openconfigurator.util.PluginErrorDialogUtils;
import org.epsg.openconfigurator.xmlbinding.projectfile.FirmwareList;
import org.epsg.openconfigurator.xmlbinding.projectfile.FirmwareList.Firmware;
import org.epsg.openconfigurator.xmlbinding.projectfile.InterfaceList;
import org.epsg.openconfigurator.xmlbinding.projectfile.TCN;
import org.jdom2.JDOMException;

/**
 * Wizard page to add new firmware.
 *
 * @author Sree Hari
 *
 */
public class NewFirmwareWizard extends Wizard {

    private static final String WINDOW_TITLE = "POWERLINK firmware wizard";

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
        @SuppressWarnings("unused")
        boolean isPageComplete = validateFirmwarePage.isPageComplete();

        if (!validateFirmwarePage.isPageComplete()) {
            return false;
        }

        return true;
    }

    @Override
    public boolean performFinish() {
        Path firmwareFilePath = validateFirmwarePage
                .getFirmwareConfigurationPath();
        Result res = new Result();
        Charset charset = Charset.forName("UTF-8");
        Object objModel = getObjModel(nodeOrModuleObj);
        Firmware firmware = new Firmware();
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
        FirmwareManager firmwareMngr = new FirmwareManager(nodeOrModuleObj,
                validateFirmwarePage.getFirmwareModel(), firmwareObj);

        byte[] venId = String.valueOf(firmwareMngr.getVendorId())
                .getBytes(charset);
        firmware.setVendorId(venId);

        if (nodeOrModuleObj instanceof Node) {
            Node cnNode = (Node) nodeOrModuleObj;
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
        } else if (nodeOrModuleObj instanceof Module) {
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
                    getNode(nodeOrModuleObj).getProject().getName());
            e.printStackTrace();
            return false;
        }

        try {
            OpenConfiguratorProjectUtils.addFirmwareList(firmwareMngr,
                    nodeOrModuleObj, firmwareObj);
        } catch (JDOMException | IOException e) {
            System.err.println(
                    "The project file update of firmware element fails.");
            e.printStackTrace();
        }
        Node node = getNode(nodeOrModuleObj);
        res = OpenConfiguratorCore.GetInstance().AddNodeAssignment(
                node.getNetworkId(), node.getCnNodeId(),
                NodeAssignment.NMT_NODEASSIGN_SWUPDATE);

        if (!res.IsSuccessful()) {
            OpenConfiguratorMessageConsole.getInstance()
                    .printLibraryErrorMessage(res);
        }

        res = OpenConfiguratorCore.GetInstance().AddNodeAssignment(
                node.getNetworkId(), node.getCnNodeId(),
                NodeAssignment.NMT_NODEASSIGN_SWVERSIONCHECK);
        if (!res.IsSuccessful()) {
            OpenConfiguratorMessageConsole.getInstance()
                    .printLibraryErrorMessage(res);
        }

        Object nodeModelObj = node.getNodeModel();
        if (nodeModelObj != null) {
            if (nodeModelObj instanceof TCN) {
                TCN cn = (TCN) nodeModelObj;
                cn.setAutoAppSwUpdateAllowed(true);
                cn.setVerifyAppSwVersion(true);
            }
        }

        try {
            OpenConfiguratorProjectUtils.updateNodeAttributeValue(node,
                    IControlledNodeProperties.CN_VERIFY_APP_SW_VERSION_OBJECT,
                    "true");
            OpenConfiguratorProjectUtils.updateNodeAttributeValue(node,
                    IControlledNodeProperties.CN_AUTO_APP_SW_UPDATE_ALLOWED_OBJECT,
                    "true");
        } catch (JDOMException | IOException e1) {
            System.err.println(
                    "The node assignment value is not updated in the project file.");
            e1.printStackTrace();
        }

        try {
            firmwareMngr.getProject().refreshLocal(IResource.DEPTH_INFINITE,
                    new NullProgressMonitor());
        } catch (CoreException e) {
            e.printStackTrace();
        }

        return true;
    }
}

/*******************************************************************************
 * @file   NewModuleWizard.java
 *
 * @author Sree Hari Vignesh, Kalycito Infotech Private Limited.
 *
 * @copyright (c) 2016, Kalycito Infotech Private Limited
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.file.Path;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.epsg.openconfigurator.lib.wrapper.ErrorCode;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.model.HeadNodeInterface;
import org.epsg.openconfigurator.model.Module;
import org.epsg.openconfigurator.model.PowerlinkRootNode;
import org.epsg.openconfigurator.util.OpenConfiguratorLibraryUtils;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectUtils;
import org.epsg.openconfigurator.util.PluginErrorDialogUtils;
import org.epsg.openconfigurator.util.XddMarshaller;
import org.epsg.openconfigurator.xmlbinding.projectfile.InterfaceList;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745ProfileContainer;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

/**
 * Wizard page to add new module.
 *
 * @author SreeHari
 *
 */
public class NewModuleWizard extends Wizard {

    private static final String WINDOW_TITLE = "POWERLINK module wizard";

    /**
     * Add new module wizard page.
     */
    private AddModuleWizardPage addModulePage;

    /**
     * Add validateXddWizardPage
     */
    private ValidateXddModuleWizardPage validateXddModulePage;

    private AddChildModuleWizardPage addChildmodulePage;

    private HeadNodeInterface selectedNodeObj;

    private PowerlinkRootNode rootNode;

    /**
     * Constructor to update the wizard page.
     *
     * @param nodeList Instance of POWERLINK root node.
     * @param selectedNodeObj instance of head node interface in which modules
     *            are to be added.
     */
    public NewModuleWizard(PowerlinkRootNode nodeList,
            HeadNodeInterface selectedNodeObj) {
        if (selectedNodeObj == null) {
            System.err.println("Invalid Interface selection");
        } else {
            this.selectedNodeObj = selectedNodeObj;
            setWindowTitle(WINDOW_TITLE);
            addModulePage = new AddModuleWizardPage(selectedNodeObj);
            validateXddModulePage = new ValidateXddModuleWizardPage(
                    selectedNodeObj);
            addChildmodulePage = new AddChildModuleWizardPage(selectedNodeObj);
        }
        rootNode = nodeList;
    }

    @Override
    public void addPages() {
        super.addPages();
        addPage(addModulePage);
        addPage(validateXddModulePage);
        addPage(addChildmodulePage);
        validateXddModulePage.setPreviousPage(addModulePage);
        addChildmodulePage.setPreviousPage(validateXddModulePage);

    }

    @Override
    public boolean canFinish() {
        if (getContainer().getCurrentPage() == addModulePage) {
            return false;
        } else if (getContainer().getCurrentPage() == validateXddModulePage) {
            return false;
        } else if (getContainer().getCurrentPage() == addChildmodulePage) {
            boolean canFnsh = validateXddModulePage.isPageComplete()
                    && addModulePage.isPageComplete()
                    && addChildmodulePage.isPageComplete();
            System.err.println("Can Finish ... " + canFnsh);
            return validateXddModulePage.isPageComplete()
                    && addModulePage.isPageComplete()
                    && addChildmodulePage.isPageComplete();

        }
        return true;
    }

    @Override
    public boolean performFinish() {
        Object moduleObject = addModulePage.getModulemodelinWizard();
        Path xdcPath = validateXddModulePage.getNodeConfigurationPath();
        int position = addChildmodulePage.getPosition();
        int address = addChildmodulePage.getAddress();
        boolean enabled = addChildmodulePage.isEnabled();

        ISO15745ProfileContainer xddModel = null;
        try {
            xddModel = XddMarshaller.unmarshallXDDFile(xdcPath.toFile());
        } catch (FileNotFoundException | UnsupportedEncodingException
                | JAXBException | SAXException
                | ParserConfigurationException e2) {
            if ((e2.getMessage() != null) && !e2.getMessage().isEmpty()) {
                validateXddModulePage.getErrorStyledText(e2.getMessage());
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        e2.getMessage(), "");
            } else if ((e2.getCause() != null)
                    && (e2.getCause().getMessage() != null)
                    && !e2.getCause().getMessage().isEmpty()) {
                validateXddModulePage
                        .getErrorStyledText(e2.getCause().getMessage());
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        e2.getCause().getMessage(),
                        selectedNodeObj.getNode().getNetworkId());
            }
            e2.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (moduleObject instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module module = (InterfaceList.Interface.Module) moduleObject;
            module.setPathToXDC(xdcPath.toString());
            module.setPosition(BigInteger.valueOf(position));
            module.setEnabled(enabled);
            module.setAddress(BigInteger.valueOf(address));
        } else {
            System.err.println("Invalid Module model");
        }

        Module newModule = new Module(rootNode,
                selectedNodeObj.getNode().getProjectXml(), moduleObject,
                selectedNodeObj.getNode(), xddModel, selectedNodeObj);

        try {
            OpenConfiguratorProjectUtils
                    .importModuleConfigurationFile(newModule);
        } catch (IOException e1) {

            e1.printStackTrace();
        }

        Result res = OpenConfiguratorLibraryUtils.addModule(newModule);
        if (res.IsSuccessful()) {
            selectedNodeObj.getModuleCollection()
                    .put(Integer.valueOf(newModule.getPosition()), newModule);

            selectedNodeObj.getAddressCollection()
                    .put(Integer.valueOf(newModule.getAddress()), newModule);

            selectedNodeObj.getModuleNameCollection()
                    .put(newModule.getModuleName(), newModule);

            System.err.println("Module collection values ... "
                    + selectedNodeObj.getModuleCollection().values());

            try {
                OpenConfiguratorProjectUtils.addModuleNode(
                        selectedNodeObj.getNode(), selectedNodeObj, newModule);
            } catch (JDOMException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else {
            System.err.println("ERROR occured while adding the module. "
                    + OpenConfiguratorLibraryUtils.getErrorMessage(res));
            PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR, res);

            // Try removing the node.
            // FIXME: do we need this?
            res = OpenConfiguratorLibraryUtils.removeModule(newModule);
            if (!res.IsSuccessful()) {
                if (res.GetErrorType() != ErrorCode.NODE_DOES_NOT_EXIST) {
                    // Show or print error message.
                    System.err
                            .println("ERROR occured while removin the module. "
                                    + OpenConfiguratorLibraryUtils
                                            .getErrorMessage(res));
                }
            }
        }

        try {
            selectedNodeObj.getNode().getProject().refreshLocal(
                    IResource.DEPTH_INFINITE, new NullProgressMonitor());
        } catch (CoreException e) {
            System.err.println("unable to refresh the resource due to "
                    + e.getCause().getMessage());
        }

        return true;
    }

}

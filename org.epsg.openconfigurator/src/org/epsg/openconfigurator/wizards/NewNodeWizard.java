/*******************************************************************************
 * @file   NewNodeWizard.java
 *
 * @author Ramakrishnan Periyakaruppan, Kalycito Infotech Private Limited.
 *
 * @copyright (c) 2015, Kalycito Infotech Private Limited
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
import java.nio.file.Path;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.epsg.openconfigurator.lib.wrapper.ErrorCode;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.model.PowerlinkRootNode;
import org.epsg.openconfigurator.util.OpenConfiguratorLibraryUtils;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectUtils;
import org.epsg.openconfigurator.util.PluginErrorDialogUtils;
import org.epsg.openconfigurator.util.XddMarshaller;
import org.epsg.openconfigurator.xmlbinding.projectfile.TCN;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNetworkConfiguration;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNodeCollection;
import org.epsg.openconfigurator.xmlbinding.projectfile.TRMN;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745ProfileContainer;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

/**
 * Wizard dialog to add a new POWERLINK node.
 *
 * @author Ramakrishnan P
 *
 */
public class NewNodeWizard extends Wizard {

    private static final String WINDOW_TITLE = "POWERLINK node wizard";
    private static final String ERROR_WHILE_COPYING_XDD = "Error occurred while copying the configuration file.";
    public static final String ERROR_NODE_MODEL = "Invalid node model.";

    /**
     * Add new node wizard page.
     */
    private final AddControlledNodeWizardPage addNodePage;

    /**
     * Add validateXddWizardPage
     */
    private final ValidateXddWizardPage validateXddPage;

    /**
     * Selected node object. The new node will be added below this node.
     */
    private Node selectedNodeObj;
    private PowerlinkRootNode nodeList;
    private TNodeCollection nodeCollectionModel;

    public NewNodeWizard(PowerlinkRootNode nodeList, Node selectedNodeObj) {
        if (selectedNodeObj == null) {
            System.err.println("Invalid node selection");
        }

        this.selectedNodeObj = selectedNodeObj;
        this.nodeList = nodeList;
        Object nodeModel = this.selectedNodeObj.getNodeModel();
        if (nodeModel instanceof TNetworkConfiguration) {
            TNetworkConfiguration net = (TNetworkConfiguration) nodeModel;
            nodeCollectionModel = net.getNodeCollection();
        }

        setWindowTitle(WINDOW_TITLE);
        addNodePage = new AddControlledNodeWizardPage(nodeCollectionModel);
        validateXddPage = new ValidateXddWizardPage();
    }

    /**
     * Add wizard page
     */
    @Override
    public void addPages() {
        super.addPages();
        addPage(addNodePage);
        addPage(validateXddPage);
        validateXddPage.setPreviousPage(addNodePage);
    }

    /**
     * Move to next page or finish the wizard.
     */
    @Override
    public boolean canFinish() {
        if (getContainer().getCurrentPage() == addNodePage) {
            return false;
        } else {
            return validateXddPage.isPageComplete() && true;
        }

    }

    /**
     * Checks for errors in the wizard pages.
     *
     * @return <code>True</code> if errors present, <code>False</code>
     *         otherwise.
     */
    public boolean hasErrors() {
        return addNodePage.hasErrors();
    }

    /**
     * Complete the Wizard page.
     */
    @Override
    public boolean performFinish() {
        validateXddPage.getErrorStyledText("");
        Object nodeObject = addNodePage.getNode();
        Path xdcPath = validateXddPage.getNodeConfigurationPath();
        if (nodeObject instanceof TCN) {
            TCN cnModel = (TCN) nodeObject;
            cnModel.setPathToXDC(xdcPath.toString());
        } else if (nodeObject instanceof TRMN) {
            TRMN rmnModel = (TRMN) nodeObject;
            rmnModel.setPathToXDC(xdcPath.toString());
        } else {
            validateXddPage.getErrorStyledText(ERROR_NODE_MODEL);
            System.err.println(ERROR_NODE_MODEL);
        }

        ISO15745ProfileContainer xddModel = null;
        try {
            xddModel = XddMarshaller.unmarshallXDDFile(xdcPath.toFile());
        } catch (FileNotFoundException | UnsupportedEncodingException
                | JAXBException | SAXException
                | ParserConfigurationException e2) {
            if ((e2.getMessage() != null) && !e2.getMessage().isEmpty()) {
                validateXddPage.getErrorStyledText(e2.getMessage());
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        e2.getMessage(), "");
            } else if ((e2.getCause() != null)
                    && (e2.getCause().getMessage() != null)
                    && !e2.getCause().getMessage().isEmpty()) {
                validateXddPage.getErrorStyledText(e2.getCause().getMessage());
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        e2.getCause().getMessage(),
                        selectedNodeObj.getNetworkId());
            }
            e2.printStackTrace();
            return false;
        }

        Node newNode = new Node(nodeList, selectedNodeObj.getProjectXml(),
                nodeObject, xddModel);

        try {
            OpenConfiguratorProjectUtils.importNodeConfigurationFile(newNode);
        } catch (IOException e1) {
            validateXddPage.getErrorStyledText(e1.getCause().getMessage());
            PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                    e1.getCause().getMessage(), newNode.getProject().getName());
            e1.printStackTrace();
        }

        Result res = OpenConfiguratorLibraryUtils.addNode(newNode);
        if (res.IsSuccessful()) {

            try {
                nodeList.addNode(nodeCollectionModel, newNode);
            } catch (IOException | JDOMException e) {
                if ((e.getMessage() != null) && !e.getMessage().isEmpty()) {
                    validateXddPage.getErrorStyledText(e.getMessage());
                    PluginErrorDialogUtils.showMessageWindow(
                            MessageDialog.ERROR, e.getMessage(), "");
                } else if ((e.getCause() != null)
                        && (e.getCause().getMessage() != null)
                        && !e.getCause().getMessage().isEmpty()) {
                    validateXddPage
                            .getErrorStyledText(e.getCause().getMessage());
                    PluginErrorDialogUtils.showMessageWindow(
                            MessageDialog.ERROR, ERROR_WHILE_COPYING_XDD,
                            newNode.getProject().getName());
                }
            }
        } else {
            PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR, res);

            // Try removing the node.
            // FIXME: do we need this?
            res = OpenConfiguratorLibraryUtils.removeNode(newNode);
            if (!res.IsSuccessful()) {
                if (res.GetErrorType() != ErrorCode.NODE_DOES_NOT_EXIST) {
                    // Show or print error message.
                    System.err.println("ERROR occured while removin the node. "
                            + OpenConfiguratorLibraryUtils
                                    .getErrorMessage(res));
                }
            }
        }
        return true;
    }
}

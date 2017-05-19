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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.epsg.openconfigurator.lib.wrapper.ErrorCode;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.model.FirmwareManager;
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
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745Profile;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745ProfileContainer;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDataType;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDevicePowerlinkModularHead;
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

    private static final String CNPRES_CHAINING_ERROR_MESSAGE = "The node {0} does not support PRes Chaining operation.";
    private static final String CHAINED_STATION_ERROR_MESSAGE = "POWERLINK network with RMN does not support PRes Chaining operation.";
    private static final String MULTIPLEXING_OPERATION_NOT_SUPPORTED_ERROR = "Currently Multiplexing operation not supported.";

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
        Object nodeModel = null;
        if (this.selectedNodeObj != null) {
            nodeModel = this.selectedNodeObj.getNodeModel();
        }
        if (nodeModel == null) {
            System.err.println("The NodeModel is empty!");
        } else {
            if (nodeModel instanceof TNetworkConfiguration) {
                TNetworkConfiguration net = (TNetworkConfiguration) nodeModel;
                nodeCollectionModel = net.getNodeCollection();
            }
        }
        setWindowTitle(WINDOW_TITLE);
        addNodePage = new AddControlledNodeWizardPage(nodeCollectionModel);
        validateXddPage = new ValidateXddWizardPage();
    }

    private boolean addNodeFirmwareFile(Node newNode) {
        List<FirmwareManager> validFwList = new ArrayList<>();
        if (nodeList != null) {
            if (!nodeList.getCnNodeList().isEmpty()) {
                for (Node cnNode : nodeList.getCnNodeList()) {
                    if (cnNode.getVendorIdValue()
                            .equalsIgnoreCase(newNode.getVendorIdValue())) {
                        if (cnNode.getProductCodeValue().equalsIgnoreCase(
                                newNode.getProductCodeValue())) {
                            if (!cnNode.getValidFirmwareList().isEmpty()) {

                                System.err.println(
                                        "The firmware collection values.."
                                                + cnNode.getValidFirmwareList());
                                for (FirmwareManager fwMngr : cnNode
                                        .getValidFirmwareList()) {
                                    validFwList.add(fwMngr);

                                }
                            }
                        }
                    }

                }

                if (!validFwList.isEmpty()) {
                    MessageDialog dialog = new MessageDialog(null,
                            "Add firmware file", null,
                            "The project contains firmware file for Node '"
                                    + newNode.getNodeIDWithName() + "'."
                                    + " \nDo you wish to add the firmware file? ",
                            MessageDialog.WARNING, new String[] { "Yes", "No" },
                            1);
                    int result = dialog.open();
                    if (result != 0) {
                        return true;
                    }
                    Map<String, FirmwareManager> firmwarelist = new HashMap<>();
                    for (FirmwareManager fwMngr : validFwList) {
                        firmwarelist.put(fwMngr.getFirmwareUri(), fwMngr);

                    }

                    for (FirmwareManager fw : firmwarelist.values()) {

                        FirmwareManager firmwareMngr = new FirmwareManager(
                                newNode, fw.getFirmwareXddModel(),
                                fw.getFirmwareObjModel());
                        newNode.getNodeFirmwareCollection().put(firmwareMngr,
                                firmwareMngr.getFirmwarefileVersion());
                        fw.updateFirmwareInProjectFile(firmwareMngr, newNode,
                                firmwareMngr.getFirmwareObjModel());
                    }

                }
                return true;
            }
        }
        return false;
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
        }
        return validateXddPage.isPageComplete() && true;

    }

    public ProfileBodyDataType getProfileBody(
            ISO15745ProfileContainer xddModel) {
        if (xddModel != null) {
            List<ISO15745Profile> profiles = xddModel.getISO15745Profile();
            for (ISO15745Profile profile : profiles) {
                ProfileBodyDataType profileBodyDatatype = profile
                        .getProfileBody();
                if (profileBodyDatatype instanceof ProfileBodyDevicePowerlinkModularHead) {
                    return profileBodyDatatype;
                }
            }
        }
        return null;
    }

    private boolean handleStationTypeChanged(int selectionIndex, Node cnNode) {

        int val = ((Integer) selectionIndex).intValue();
        if (val == 1) {
            // Checks the value of PresChaining from the XDD
            // model of MN and CN.

            boolean cnPresChaining = cnNode.getNetworkManagement()
                    .getCnFeaturesOfNode().isDLLCNPResChaining();
            if (!cnPresChaining) {
                // do not allow
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        MessageFormat.format(CNPRES_CHAINING_ERROR_MESSAGE,
                                cnNode.getNodeIDWithName()),
                        cnNode.getProject().getName());
                getContainer().showPage(addNodePage);
                return false;
            }

            List<Node> rmnNodes = cnNode.getPowerlinkRootNode()
                    .getRmnNodeList();
            if (rmnNodes.size() > 0) {
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        CHAINED_STATION_ERROR_MESSAGE,
                        cnNode.getProject().getName());
                getContainer().showPage(addNodePage);
                return false;
            }
        } else if (val == 2) {
            PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                    MULTIPLEXING_OPERATION_NOT_SUPPORTED_ERROR,
                    cnNode.getProject().getName());
            getContainer().showPage(addNodePage);
            return false;
        }
        return true;

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

        ISO15745ProfileContainer xddModel = null;
        try {
            xddModel = XddMarshaller.unmarshallXDDFile(xdcPath.toFile());
        } catch (FileNotFoundException | UnsupportedEncodingException
                | JAXBException | SAXException | ParserConfigurationException
                | NullPointerException e2) {

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
            } else {
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        "The XDD/XDC file is out of sync with the file system.",
                        "");
                validateXddPage.getErrorStyledText(
                        "The XDD/XDC file for '" + addNodePage.getNodeName()
                                + "' is out of sync with the file system.");
            }

            e2.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

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

        Node newNode = new Node(nodeList, selectedNodeObj.getProjectXml(),
                nodeObject, xddModel);

        if (!(handleStationTypeChanged(addNodePage.getStationTypeChanged(),
                newNode))) {
            getContainer().showPage(addNodePage);
            // addNodePage.setErrorMessage(CHAINED_STATION_ERROR_MESSAGE);
            return false;
        }
        addNodePage.setErrorMessage("");

        try {
            OpenConfiguratorProjectUtils.importNodeConfigurationFile(newNode);
        } catch (IOException e1) {
            validateXddPage.getErrorStyledText(e1.getCause().getMessage());
            PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                    e1.getCause().getMessage(), newNode.getProject().getName());
            e1.printStackTrace();
        }

        if (getProfileBody(
                xddModel) instanceof ProfileBodyDevicePowerlinkModularHead) {
            Result res = OpenConfiguratorLibraryUtils
                    .addModularHeadNode(newNode);
            System.err.println("Modular CN...");
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
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        res);

                // Try removing the node.
                // FIXME: do we need this?
                res = OpenConfiguratorLibraryUtils.removeNode(newNode);
                if (!res.IsSuccessful()) {
                    if (res.GetErrorType() != ErrorCode.NODE_DOES_NOT_EXIST) {
                        // Show or print error message.
                        System.err.println(
                                "ERROR occured while removin the node. "
                                        + OpenConfiguratorLibraryUtils
                                                .getErrorMessage(res));
                    }
                }
            }

        } else {
            System.err.println("Normal CN...");
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
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        res);

                // Try removing the node.
                // FIXME: do we need this?
                res = OpenConfiguratorLibraryUtils.removeNode(newNode);
                if (!res.IsSuccessful()) {
                    if (res.GetErrorType() != ErrorCode.NODE_DOES_NOT_EXIST) {
                        // Show or print error message.
                        System.err.println(
                                "ERROR occured while removin the node. "
                                        + OpenConfiguratorLibraryUtils
                                                .getErrorMessage(res));
                    }
                }
            }
        }

        try {

            newNode.addStationTypeofNode(addNodePage.getStationTypeChanged());

        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }

        if (addNodeFirmwareFile(newNode)) {
            return true;
        }

        return true;
    }
}

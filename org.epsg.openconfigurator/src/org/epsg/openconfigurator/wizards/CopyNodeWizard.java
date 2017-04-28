/*******************************************************************************
 * @file   CopyNodeWizard.java
 *
 * @author Sree hari Vignesh, Kalycito Infotech Private Limited.
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.epsg.openconfigurator.model.FirmwareManager;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.model.PowerlinkRootNode;
import org.epsg.openconfigurator.xmlbinding.projectfile.TCN;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNetworkConfiguration;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNodeCollection;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745Profile;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745ProfileContainer;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDataType;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDevicePowerlinkModularHead;

/**
 * Wizard dialog to copy the POWERLINK controlled node
 *
 * @author Sree hari
 *
 */
public class CopyNodeWizard extends Wizard {
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

    private String stationType;

    /**
     * Selected node object. The new node will be added below this node.
     */
    private Node selectedNodeObj;
    private PowerlinkRootNode nodeList;
    private TNodeCollection nodeCollectionModel;

    /**
     * Constructor to initialize the node model with respect to the selected
     * node object
     *
     * @param nodeList Instance of POWERLINK root node
     * @param selectedNodeObj The node object to be copied
     */
    public CopyNodeWizard(PowerlinkRootNode nodeList, Node selectedNodeObj) {
        if (selectedNodeObj == null) {
            System.err.println("Invalid node selection");
        }

        this.selectedNodeObj = selectedNodeObj;
        this.nodeList = nodeList;
        Object nodeModel = null;
        if (this.selectedNodeObj != null) {
            nodeModel = nodeList.getMN().getNodeModel();
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
        // addNodePage.resetWizard();
    }

    // Adds firmware to the node based on the valid firmware files available in
    // the project
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
                        firmwarelist.put(fwMngr.getUri(), fwMngr);

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

    }

    /**
     * Move to next page or finish the wizard.
     */
    @Override
    public boolean canFinish() {
        addNodePage.resetWizard();
        return addNodePage.isPageComplete() && true;

    }

    /**
     * @return The value of node ID entered in the wizard page
     */
    public String getNodeId() {
        String nodeId = StringUtils.EMPTY;
        Object nodeModel = addNodePage.getNode();
        if (nodeModel instanceof TCN) {
            TCN cn = (TCN) nodeModel;
            nodeId = cn.getNodeID();
        }
        return nodeId;
    }

    /**
     * @return The name of the node entered in the wizard page
     */
    public String getNodeName() {
        String nodeName = StringUtils.EMPTY;
        Object nodeModel = addNodePage.getNode();
        if (nodeModel instanceof TCN) {
            TCN cn = (TCN) nodeModel;
            nodeName = cn.getName();
        }
        return nodeName;
    }

    /**
     * Gets the XDD profile based on the ISO15745ProfileContainer
     *
     * @param xddModel XDD instance
     * @return The profile type of XDD model
     */
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

    /**
     * @return The value of POWERLINK operational mode entered in the wizard
     *         page
     */
    public String getStationType() {
        return stationType;
    }

    /**
     * Receives the index of station type selected in the combo box widget of
     * wizard page
     *
     * @param stationType The text available in the station type combo box
     *            widget
     * @return The selection index of combo box
     */
    public int getStationTypeIndex(String stationType) {
        int stationTypeChanged = 0;
        if (stationType.equalsIgnoreCase("chained")) {
            stationTypeChanged = 1;
        }
        return stationTypeChanged;
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

        Object nodeObject = addNodePage.getNode();
        stationType = addNodePage.getStationType();

        return true;
    }
}

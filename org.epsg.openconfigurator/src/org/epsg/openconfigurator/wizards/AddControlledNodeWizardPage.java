/*******************************************************************************
* @file   AddControlledNodeWizardPage.java
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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.model.PowerlinkRootNode;
import org.epsg.openconfigurator.util.IPowerlinkConstants;
import org.epsg.openconfigurator.validation.NodeNameVerifyListener;
import org.epsg.openconfigurator.views.IndustrialNetworkView;
import org.epsg.openconfigurator.xmlbinding.projectfile.TCN;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNodeCollection;
import org.epsg.openconfigurator.xmlbinding.projectfile.TRMN;

/**
 * Add new node (Controlled node, Redundant managing node) wizard page.
 *
 * @author Ramakrishnan P
 *
 */
public class AddControlledNodeWizardPage extends WizardPage {

    public static final String CONTROLLED_NODE_LABEL = "Controlled Node";
    public static final String REDUNDANT_MANAGING_NODE_LABEL = "Redundant Managing Node";
    private static final String NODE_TYPES[] = { CONTROLLED_NODE_LABEL,
            REDUNDANT_MANAGING_NODE_LABEL };

    private static final String STATION_TYPES[] = { "Normal", "Chained" };

    private static final String DIALOG_PAGE_NAME = "AddCnwizardPage"; //$NON-NLS-1$
    public static final String DIALOG_TILE = "POWERLINK node";
    public static final String DIALOG_DESCRIPTION = "Add a POWERLINK node to the network.";

    private static final String NODE_TYPE_LABEL = "Node Type:";
    private static final String NODE_ID_LABEL = "Node ID:";
    private static final String RANGE_LABEL = "Range:";
    private static final String NAME_LABEL = "Name:";

    private static final String ERROR_MAXIMUM_NODE_ID_LIMIT_REACHED = "Maximum Node ID limit({0}) reached.";
    private static final String ERROR_MODEL_NOT_AVAILABLE = "Editor is not available to fetch the model.";
    private static final String ERROR_NODE_ALREADY_EXISTS = "Node already exists.";
    private static final String ERROR_INVALID_NODE_ID = "Invalid node ID.";
    private static final String ERROR_INVALID_NODE_NAME = "Enter a valid node name.";
    private static final String ERROR_RMN_NOT_SUPPORTED = "{0} does not support RMN.";
    private static final String ERROR_RMN_WITH_CHAINED_STATION = "POWERLINK network with chained station cannot have RMN.";
    private static final String MNPRES_CHAINING_ERROR_MESSAGE = "The MN {0} does not support PRes Chaining operation.";
    private static final String CHAINED_STATION_ERROR_MESSAGE = "POWERLINK network with RMN does not support PRes Chaining operation.";

    /**
     * @return The list of nodes from POWERLINK root node.
     */
    private static PowerlinkRootNode getNodelist() {
        IViewPart viewPart = PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getActivePage()
                .findView(IndustrialNetworkView.ID);
        if (viewPart instanceof IndustrialNetworkView) {
            IndustrialNetworkView industrialView = (IndustrialNetworkView) viewPart;
            PowerlinkRootNode nodeList = industrialView.getNodeList();
            return nodeList;
        }
        return null;
    }

    /**
     * Checks for node available in the given node ID list or not.
     *
     * @param nodeIdList The list of node IDs.
     * @param nodeIdTobeChecked the node ID to be checked.
     * @return <code>True</code> if node is already present <code>False</code>
     *         otherwise.
     */
    private static boolean isNodeIdAvailable(List<Short> nodeIdList,
            short nodeIdTobeChecked) {

        boolean nodeIdAvailable = false;
        for (Short nodeId : nodeIdList) {
            if (nodeId.shortValue() == nodeIdTobeChecked) {
                nodeIdAvailable = true;
            }
        }
        return nodeIdAvailable;
    }

    /**
     * Checks for the valid name for the master node.
     *
     * @param nodeName Name
     * @return true if valid, false otherwise.
     */
    private static boolean isNodeNameValid(final String nodeName) {
        boolean retval = false;

        if (nodeName == null) {
            return retval;
        }

        if (nodeName.length() == 0) {
            return retval;
        }

        // Space as first character is not allowed. ppc:tNonEmptyString
        if (nodeName.charAt(0) == ' ') {
            return retval;
        }

        if (nodeName.length() > 0) {
            retval = true;
        }

        return retval;
    }

    private static boolean isValidCnNodeId(short cnNodeId) {
        if ((cnNodeId >= IPowerlinkConstants.CN_MIN_NODE_ID)
                && (cnNodeId <= IPowerlinkConstants.CN_MAX_NODE_ID)) {
            return true;
        }
        return false;
    }

    private static boolean isValidRmnNodeId(short rmnNodeId) {
        if ((rmnNodeId >= IPowerlinkConstants.RMN_MIN_NODE_ID)
                && (rmnNodeId <= IPowerlinkConstants.RMN_MAX_NODE_ID)) {
            return true;
        }
        return false;
    }

    /**
     * Control to display the Node name.
     */
    private Text nodeName;

    /**
     * Control to list the type of supported node.
     */
    private Combo nodeTypeCombo;

    /**
     * Control to list the station type to node.
     */
    private Combo stationTypeCombo;

    /**
     * Control to display the node ID.
     */
    private Spinner nodeIdSpinner;

    /**
     * Control to display the node ID value range.
     */
    private Label lblNodeIdRangeValue;

    /**
     * Flag to display the error status of this page.
     */
    private boolean errorField = false;

    /**
     * Name verify listener
     */
    private NodeNameVerifyListener nameVerifyListener = new NodeNameVerifyListener();

    /**
     * The node collection instance.
     */
    private TNodeCollection nodeCollection = null;

    /**
     * The base object to store the node model.
     */
    private Object nodeModel = null; // TCN/TRMN

    private int selectionIndex;

    /**
     * Create the wizard.
     */
    public AddControlledNodeWizardPage(TNodeCollection nodeCollection) {
        super(DIALOG_PAGE_NAME);
        setTitle(DIALOG_TILE);
        setDescription(DIALOG_DESCRIPTION);

        setErrorMessage(null);
        this.nodeCollection = nodeCollection;

    }

    /**
     * Create contents of the wizard.
     *
     * @param parent Parent composite
     */
    @Override
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NULL);

        setControl(container);

        Label lblNodeType = new Label(container, SWT.NONE);
        lblNodeType.setBounds(21, 13, 73, 23);
        lblNodeType.setText(NODE_TYPE_LABEL);

        nodeTypeCombo = new Combo(container, SWT.READ_ONLY);
        nodeTypeCombo.setBounds(121, 13, 219, 23);
        nodeTypeCombo.setItems(NODE_TYPES);
        nodeTypeCombo.select(0);
        nodeTypeCombo.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                handleNodeTypeChanged(nodeTypeCombo.getSelectionIndex());
            }
        });

        Label lblNodeId = new Label(container, SWT.NONE);
        lblNodeId.setBounds(21, 58, 55, 19);
        lblNodeId.setText(NODE_ID_LABEL);

        nodeIdSpinner = new Spinner(container, SWT.BORDER);
        nodeIdSpinner.setMaximum(IPowerlinkConstants.CN_MAX_NODE_ID);
        nodeIdSpinner.setMinimum(IPowerlinkConstants.CN_MIN_NODE_ID);
        nodeIdSpinner.setSelection(getNewCnNodeId());
        nodeIdSpinner.setBounds(121, 58, 117, 22);
        nodeIdSpinner.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                getWizard().getContainer().updateButtons();
            }
        });

        Label lblName = new Label(container, SWT.NONE);
        lblName.setBounds(21, 103, 55, 21);
        lblName.setText(NAME_LABEL);

        nodeName = new Text(container, SWT.BORDER);
        nodeName.setBounds(121, 103, 291, 21);
        nodeName.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                setErrorMessage(null);
                setPageComplete(true);
                if (!isNodeNameValid(nodeName.getText())) {
                    setErrorMessage(ERROR_INVALID_NODE_NAME);
                    setPageComplete(false);
                }
                getWizard().getContainer().updateButtons();
            }
        });
        nodeName.addVerifyListener(nameVerifyListener);
        nodeName.setFocus();

        lblNodeIdRangeValue = new Label(container, SWT.LEFT);
        lblNodeIdRangeValue.setBounds(302, 58, 133, 15);
        lblNodeIdRangeValue.setText(
                nodeIdSpinner.getMinimum() + "-" + nodeIdSpinner.getMaximum());

        Label lblRange = new Label(container, SWT.NONE);
        lblRange.setBounds(244, 58, 51, 19);
        lblRange.setText(RANGE_LABEL);

        Label lblNewLabel = new Label(container, SWT.NONE);
        lblNewLabel.setBounds(21, 148, 73, 23);
        lblNewLabel.setText("Station Type:");

        stationTypeCombo = new Combo(container, SWT.READ_ONLY);
        stationTypeCombo.setBounds(121, 148, 117, 23);
        stationTypeCombo.setItems(STATION_TYPES);
        stationTypeCombo.select(0);
        stationTypeCombo.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                handleStationTypeChanged();
            }

        });
    }

    public String getCnNodeId() {
        return nodeIdSpinner.getText();
    }

    /**
     * @return Returns the next available CN node ID.
     */
    private short getNewCnNodeId() {
        setErrorMessage(null);

        if (nodeCollection == null) {
            errorField = true;
            setErrorMessage(ERROR_MODEL_NOT_AVAILABLE);
            setPageComplete(false);
            return 1;
        }
        List<TCN> cnList = nodeCollection.getCN();
        return getNextValidCnNodeId(cnList);

    }

    /**
     * @return Returns the next available RMN node ID.
     */
    private short getNewRmnNodeId() {
        setErrorMessage(null);

        if (nodeCollection == null) {
            errorField = true;
            setErrorMessage(ERROR_MODEL_NOT_AVAILABLE);
            setPageComplete(false);
            return IPowerlinkConstants.RMN_MIN_NODE_ID;
        }
        // Return min nodeid if there is no RMN available in the model.
        if (nodeCollection.getRMN().isEmpty()) {
            return IPowerlinkConstants.RMN_MIN_NODE_ID;
        }

        List<TRMN> rmnList = nodeCollection.getRMN();
        return getNextValidRmnNodeId(rmnList);

    }

    /**
     * Returns the next available CN node ID.
     *
     * @param cnNodeList The list of CNs available.
     * @return the new CN node ID.
     */
    private short getNextValidCnNodeId(List<TCN> cnNodeList) {
        setErrorMessage(null);

        List<Short> nodeIdList = new ArrayList<>();

        for (TCN cn : cnNodeList) {
            Short cnNodeId = Short.valueOf(cn.getNodeID());
            nodeIdList.add(cnNodeId);
        }

        for (short i = IPowerlinkConstants.CN_MIN_NODE_ID; i <= IPowerlinkConstants.CN_MAX_NODE_ID; i++) {
            if (!isNodeIdAvailable(nodeIdList, i)) {
                if (isValidCnNodeId(i)) {
                    return i;
                }
            }
        }

        // Error occurred.
        errorField = true;
        String errorMessage = MessageFormat.format(
                ERROR_MAXIMUM_NODE_ID_LIMIT_REACHED,
                IPowerlinkConstants.CN_MAX_NODE_ID);
        setErrorMessage(errorMessage);
        setPageComplete(false);
        return IPowerlinkConstants.INVALID_NODE_ID;
    }

    /**
     * Returns the next available RMN node ID.
     *
     * @param rmnNodeList The list of RMNs available.
     * @return the new RMN node ID.
     */
    private short getNextValidRmnNodeId(List<TRMN> rmnNodeList) {
        List<Short> rmnNodeIdList = new ArrayList<>();
        for (TRMN rmn : rmnNodeList) {
            rmnNodeIdList.add(Short.parseShort(rmn.getNodeID()));
        }

        for (short i = IPowerlinkConstants.RMN_MIN_NODE_ID; i <= IPowerlinkConstants.RMN_MAX_NODE_ID; i++) {
            if (!isNodeIdAvailable(rmnNodeIdList, i)) {
                if (isValidRmnNodeId(i)) {
                    return i;
                }
            }
        }

        // Error occurred.
        errorField = true;
        String errorMessage = MessageFormat.format(
                ERROR_MAXIMUM_NODE_ID_LIMIT_REACHED,
                IPowerlinkConstants.RMN_MAX_NODE_ID);
        setErrorMessage(errorMessage);
        setPageComplete(false);
        return IPowerlinkConstants.INVALID_NODE_ID;
    }

    /**
     * @return Returns the node model.
     */
    Object getNode() {
        return nodeModel;
    }

    /**
     * @return The node ID.
     */
    int getNodeId() {
        return nodeIdSpinner.getSelection();
    }

    /**
     * @return the list of node IDs.
     */
    private List<Short> getNodeIDs() {
        List<Short> nodeIdList = new ArrayList<>();

        nodeIdList.add(nodeCollection.getMN().getNodeID());

        for (TCN cn : nodeCollection.getCN()) {
            Short cnNodeId = Short.valueOf(cn.getNodeID());
            nodeIdList.add(cnNodeId);
        }

        for (TRMN rmn : nodeCollection.getRMN()) {
            Short rmnNodeId = Short.valueOf(rmn.getNodeID());
            nodeIdList.add(rmnNodeId);
        }
        return nodeIdList;
    }

    /**
     * @return The name of the node selected.
     */
    public String getNodeName() {
        return nodeName.getText();
    }

    /**
     * @return The type of the node selected.
     */
    String getNodeType() {
        return nodeTypeCombo.getText();
    }

    public String getStationType() {
        return stationTypeCombo.getText();
    }

    public int getStationTypeChanged() {
        selectionIndex = stationTypeCombo.getSelectionIndex();
        return selectionIndex;
    }

    /**
     * Handles the node type change events.
     *
     * @param selection The node type selection
     */
    private void handleNodeTypeChanged(int selection) {
        nodeName.removeVerifyListener(nameVerifyListener);

        switch (nodeTypeCombo.getText()) {
            case CONTROLLED_NODE_LABEL:
                nodeIdSpinner.setMinimum(IPowerlinkConstants.CN_MIN_NODE_ID);
                nodeIdSpinner.setMaximum(IPowerlinkConstants.CN_MAX_NODE_ID);
                lblNodeIdRangeValue.setText(nodeIdSpinner.getMinimum() + "-"
                        + nodeIdSpinner.getMaximum());

                short newCnNodeId = getNewCnNodeId();
                nodeIdSpinner.setSelection(newCnNodeId);
                stationTypeCombo.setEnabled(true);
                break;

            case REDUNDANT_MANAGING_NODE_LABEL:
                nodeIdSpinner.setMaximum(IPowerlinkConstants.RMN_MAX_NODE_ID);
                nodeIdSpinner.setMinimum(IPowerlinkConstants.RMN_MIN_NODE_ID);
                lblNodeIdRangeValue.setText(nodeIdSpinner.getMinimum() + "-"
                        + nodeIdSpinner.getMaximum());
                short newRmnNodeId = getNewRmnNodeId();
                nodeIdSpinner.setSelection(newRmnNodeId);
                stationTypeCombo.setEnabled(false);
                stationTypeCombo.select(0);
                break;

            default:
                nodeIdSpinner.setMaximum(IPowerlinkConstants.INVALID_NODE_ID);
                nodeIdSpinner.setMinimum(IPowerlinkConstants.INVALID_NODE_ID);
                lblNodeIdRangeValue.setText(nodeIdSpinner.getMinimum() + "-"
                        + nodeIdSpinner.getMaximum());
                nodeIdSpinner.setSelection(IPowerlinkConstants.INVALID_NODE_ID);
                System.err.println("New node type"
                        + NODE_TYPES[nodeTypeCombo.getSelectionIndex()]
                        + " not handled.");
                break;
        }

        nodeName.addVerifyListener(nameVerifyListener);
    }

    private void handleStationTypeChanged() {

        if (stationTypeCombo.getText().equalsIgnoreCase("Chained")) {

            List<TRMN> rmnNodes = nodeCollection.getRMN();
            if (rmnNodes.size() > 0) {

                setPageComplete(false);
                setErrorMessage(CHAINED_STATION_ERROR_MESSAGE);
            }

            Node mnNode = getNodelist().getMN();
            if (!mnNode.getNetworkManagement().getMnFeaturesOfNode()
                    .isDLLMNPResChaining()) {

                setPageComplete(false);
                setErrorMessage(
                        MessageFormat.format(MNPRES_CHAINING_ERROR_MESSAGE,
                                mnNode.getNodeIDWithName()));
            }

        } else {
            setErrorMessage(null);
            setPageComplete(true);
        }

    }

    /**
     * @return The error status of this page.
     */
    public boolean hasErrors() {
        return errorField;
    }

    private boolean isNodeIdAvailableInModel(String nodeId) {
        List<Short> nodeIdList = getNodeIDs();
        boolean retVal = false;
        try {
            short parsedNodeId = Short.parseShort(nodeId);
            retVal = isNodeIdAvailable(nodeIdList, parsedNodeId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.err.println("Number format exception");
        }

        return retVal;
    }

    /**
     * Checks for errors in the page and updates the error message if any.
     */
    @Override
    public boolean isPageComplete() {

        setErrorMessage(null);
        boolean nodeIdValid = isValidNodeId(nodeIdSpinner.getText());
        if (!nodeIdValid) {
            setErrorMessage(ERROR_INVALID_NODE_ID);
            return false;
        }

        boolean nodeIdAlreadyAvailable = isNodeIdAvailableInModel(
                nodeIdSpinner.getText());
        if (nodeIdAlreadyAvailable) {
            setErrorMessage(ERROR_NODE_ALREADY_EXISTS);
            return false;
        }

        boolean nameValid = isNodeNameValid(nodeName.getText());
        if (!nameValid) {
            setErrorMessage(ERROR_INVALID_NODE_NAME);
            return false;
        }

        // The value of nameValid,nodeIdAlreadyAvailable,nodeIdValid is not true
        // for all cases.
        boolean pageComplete = (super.isPageComplete());

        if (pageComplete) {
            updateCnModel();

            if ((nodeModel == null)) {
                pageComplete = false;
            }
        }
        // Refresh the wizard page based on the node type.
        IWizardPage nextPage = getNextPage();
        if (nextPage instanceof ValidateXddWizardPage) {
            ValidateXddWizardPage validatePage = (ValidateXddWizardPage) nextPage;
            if (nodeTypeCombo.getText()
                    .equalsIgnoreCase(REDUNDANT_MANAGING_NODE_LABEL)) {
                Node mnNode = getNodelist().getMN();
                if (!mnNode.getNetworkManagement().getMnFeaturesOfNode()
                        .isNMTMNRedundancy()) {
                    setErrorMessage(
                            MessageFormat.format(ERROR_RMN_NOT_SUPPORTED,
                                    mnNode.getNodeIDWithName()));
                    pageComplete = false;
                    return pageComplete;
                }
                List<Node> cnNodes = getNodelist().getCnNodeList();
                for (Node cnNode : cnNodes) {
                    if (cnNode
                            .getPlkOperationMode() == org.epsg.openconfigurator.model.PlkOperationMode.CHAINED) {
                        setErrorMessage(ERROR_RMN_WITH_CHAINED_STATION);
                        pageComplete = false;
                        return pageComplete;
                    }
                }

                validatePage.resetRmnWizard();

            } else if (nodeTypeCombo.getText()
                    .equalsIgnoreCase(CONTROLLED_NODE_LABEL)) {
                validatePage.resetCnWizard();
                if (stationTypeCombo.getText()
                        .equalsIgnoreCase(STATION_TYPES[1])) {
                    List<Node> rmnNodes = getNodelist().getRmnNodeList();

                    if (!rmnNodes.isEmpty()) {
                        setErrorMessage(ERROR_RMN_WITH_CHAINED_STATION);
                        pageComplete = false;
                        return pageComplete;
                    }

                }

            } else {
                validatePage.resetRmnWizard();
            }
        } else {
            System.err.println("Invalid wizard page.");
        }

        return pageComplete;
    }

    private boolean isValidNodeId(short nodeId) {
        switch (nodeTypeCombo.getText()) {
            case CONTROLLED_NODE_LABEL:
                if ((nodeId > IPowerlinkConstants.INVALID_NODE_ID)
                        && (nodeId <= IPowerlinkConstants.CN_MAX_NODE_ID)) {
                    return true;
                }
                break;
            case REDUNDANT_MANAGING_NODE_LABEL:
                if ((nodeId >= IPowerlinkConstants.RMN_MIN_NODE_ID)
                        && (nodeId <= IPowerlinkConstants.RMN_MAX_NODE_ID)) {
                    return true;
                }
                break;
            default:
                break;
        }

        return false;
    }

    private boolean isValidNodeId(String nodeId) {
        boolean retVal = false;
        try {
            short parsedNodeId = Short.parseShort(nodeId);
            retVal = isValidNodeId(parsedNodeId);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.err.println("Number format exception");
        }
        return retVal;
    }

    public void resetWizard() {
        nodeTypeCombo.setEnabled(false);
    }

    private void updateCnModel() {
        switch (getNodeType()) {
            case CONTROLLED_NODE_LABEL:
                TCN cnModel = new TCN();
                cnModel.setName(nodeName.getText());
                cnModel.setNodeID(
                        Integer.toString(nodeIdSpinner.getSelection()));
                nodeModel = cnModel;
                break;
            case REDUNDANT_MANAGING_NODE_LABEL:
                TRMN rmnModel = new TRMN();
                rmnModel.setName(nodeName.getText());
                rmnModel.setNodeID(
                        Integer.toString(nodeIdSpinner.getSelection()));
                nodeModel = rmnModel;
                break;
            default:
                nodeModel = null;
                break;
        }

        if (nodeModel == null) {
            System.err.println("Invalid node type. " + getNodeType());
            return;
        }

    }
}

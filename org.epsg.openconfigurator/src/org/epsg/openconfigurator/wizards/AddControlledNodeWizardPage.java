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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.epsg.openconfigurator.model.IPowerlinkProjectSupport;
import org.epsg.openconfigurator.resources.IOpenConfiguratorResource;
import org.epsg.openconfigurator.util.IPowerlinkConstants;
import org.epsg.openconfigurator.util.PluginErrorDialogUtils;
import org.epsg.openconfigurator.util.XddMarshaller;
import org.epsg.openconfigurator.validation.NodeNameVerifyListener;
import org.epsg.openconfigurator.xmlbinding.projectfile.TCN;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNodeCollection;
import org.epsg.openconfigurator.xmlbinding.projectfile.TRMN;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745ProfileContainer;
import org.xml.sax.SAXException;

/**
 * Add new node (Controlled node, Redundant managing node) wizard page.
 *
 * @author Ramakrishnan P
 *
 */
public class AddControlledNodeWizardPage extends WizardPage {

    public static final String CONTROLLED_NODE_LABEL = "Controlled Node";
    public static final String REDUNDANT_MANAGING_NODE_LABEL = "Redundant Managing Node";
    public static final String NODE_TYPES[] = { CONTROLLED_NODE_LABEL,
            REDUNDANT_MANAGING_NODE_LABEL };

    private static final String DIALOG_PAGE_NAME = "AddCnwizardPage"; //$NON-NLS-1$
    private static final String DIALOG_TILE = "Add a POWERLINK node";
    private static final String DIALOG_DESCRIPTION = "Add a POWERLINK node to the network";

    private static final String NODE_TYPE_LABEL = "Node Type:";
    private static final String NODE_ID_LABEL = "Node ID:";
    private static final String RANGE_LABEL = "Range:";
    private static final String NAME_LABEL = "Name:";
    private static final String NODE_CONFIGURATION_LABEL = "XDD/XDC:";
    private static final String DEFAULT_CONFIGURATION_LABEL = "Default";
    private static final String CUSTOM_CONFIGURATION_LABEL = "Custom";
    private static final String BROWSE_CONFIGURATION_LABEL = "Browse...";
    private static final String IMPORT_CN_CONFIGURATION_FILE_DIALOG_LABEL = "Import node's XDD/XDC";

    private static final String ERROR_DEFAULT_CN_XDD_NOT_FOUND = "Default CN XDD not found.";
    private static final String ERROR_DEFAULT_RMN_XDD_NOT_FOUND = "Default Redundant managing XDD not found.";
    private static final String ERROR_MAXIMUM_NODE_ID_LIMIT_REACHED = "Maximum Node ID limit({0}) reached.";
    private static final String ERROR_MODEL_NOT_AVAILABLE = "Editor is not available to fetch the model";
    private static final String ERROR_NODE_ALREADY_EXISTS = "Node already exists";
    private static final String ERROR_INVALID_NODE_ID = "Invalid node ID";
    private static final String ERROR_INVALID_NODE_NAME = "Enter a valid node name";
    private static final String ERROR_CHOOSE_VALID_FILE_MESSAGE = "Choose a valid XDD/XDC file";

    /**
     * Control to display the Node name.
     */
    private Text nodeName;

    /**
     * Control to display the node configuration path.
     */
    private Text nodeConfigurationPath;

    /**
     * Control to list the type of supported node.
     */
    private Combo nodeTypeCombo;

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
     * Browse XDD/XDC button.
     */
    private Button btnBrowse;

    /**
     * Default radio button.
     */
    private Button btnDefault;

    /**
     * Default controlled node XDD path.
     */
    private final String defaultCnXDD;

    /**
     * Default managing node XDD path.
     */
    private final String defaultMnXDD;

    /**
     * Custom radio button.
     */
    private Button btnCustom;

    /**
     * This is to restore the custom configuration.
     */
    private String customConfiguration;

    /**
     * The base object to store the node model.
     */
    private Object nodeModel = null; // TCN/TRMN

    /**
     * The XDD/XDC model.
     */
    private ISO15745ProfileContainer xddModel = null;

    /**
     * Create the wizard.
     */
    public AddControlledNodeWizardPage(TNodeCollection nodeCollection) {
        super(DIALOG_PAGE_NAME);
        setTitle(DIALOG_TILE);
        setDescription(DIALOG_DESCRIPTION);

        setErrorMessage(null);
        this.nodeCollection = nodeCollection;

        String cnXddPath = IOpenConfiguratorResource.DEFAULT_CN_XDD;

        try {
            cnXddPath = org.epsg.openconfigurator.Activator
                    .getAbsolutePath(IOpenConfiguratorResource.DEFAULT_CN_XDD);
        } catch (IOException e) {
            e.printStackTrace();
            PluginErrorDialogUtils
                    .displayErrorMessageDialog(
                            org.epsg.openconfigurator.Activator.getDefault()
                                    .getWorkbench().getActiveWorkbenchWindow()
                                    .getShell(),
                            ERROR_DEFAULT_CN_XDD_NOT_FOUND, e);
        }

        defaultCnXDD = cnXddPath;

        String mnXddPath = IOpenConfiguratorResource.DEFAULT_MN_XDD;
        try {
            mnXddPath = org.epsg.openconfigurator.Activator
                    .getAbsolutePath(IOpenConfiguratorResource.DEFAULT_MN_XDD);
        } catch (IOException e) {
            e.printStackTrace();
            PluginErrorDialogUtils
                    .displayErrorMessageDialog(
                            org.epsg.openconfigurator.Activator.getDefault()
                                    .getWorkbench().getActiveWorkbenchWindow()
                                    .getShell(),
                            ERROR_DEFAULT_RMN_XDD_NOT_FOUND, e);
        }

        defaultMnXDD = mnXddPath;
        customConfiguration = "";
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
        lblNodeType.setBounds(21, 10, 73, 23);
        lblNodeType.setText(NODE_TYPE_LABEL);

        nodeTypeCombo = new Combo(container, SWT.READ_ONLY);
        nodeTypeCombo.setBounds(121, 10, 191, 23);
        nodeTypeCombo.setItems(NODE_TYPES);
        nodeTypeCombo.select(0);
        nodeTypeCombo.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                handleNodeTypeChanged(nodeTypeCombo.getSelectionIndex());
            }
        });

        Label lblNodeId = new Label(container, SWT.NONE);
        lblNodeId.setBounds(21, 48, 55, 19);
        lblNodeId.setText(NODE_ID_LABEL);

        nodeIdSpinner = new Spinner(container, SWT.BORDER);
        nodeIdSpinner.setMaximum(IPowerlinkConstants.CN_MAX_NODE_ID);
        nodeIdSpinner.setMinimum(IPowerlinkConstants.CN_MIN_NODE_ID);
        nodeIdSpinner.setSelection(getNewCnNodeId());
        nodeIdSpinner.setBounds(121, 45, 63, 22);
        nodeIdSpinner.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                handleNodeIdSpinnerModification(e);
            }
        });

        Label lblName = new Label(container, SWT.NONE);
        lblName.setBounds(21, 89, 55, 21);
        lblName.setText(NAME_LABEL);

        nodeName = new Text(container, SWT.BORDER);
        nodeName.setBounds(121, 89, 291, 21);
        nodeName.setText(CONTROLLED_NODE_LABEL + " " + nodeIdSpinner.getText());
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

        lblNodeIdRangeValue = new Label(container, SWT.LEFT);
        lblNodeIdRangeValue.setBounds(268, 48, 133, 15);
        lblNodeIdRangeValue.setText(
                nodeIdSpinner.getMinimum() + "-" + nodeIdSpinner.getMaximum());

        Label lblRange = new Label(container, SWT.NONE);
        lblRange.setBounds(211, 48, 51, 19);
        lblRange.setText(RANGE_LABEL);

        Group grpConfigurationFile = new Group(container, SWT.NONE);
        grpConfigurationFile.setText("Configuration file");
        grpConfigurationFile.setBounds(21, 127, 543, 116);

        Label lblXddxdc = new Label(grpConfigurationFile, SWT.NONE);
        lblXddxdc.setBounds(26, 60, 73, 16);
        lblXddxdc.setText("XDD/XDC file:");

        btnDefault = new Button(grpConfigurationFile, SWT.RADIO);
        btnDefault.setBounds(26, 30, 82, 16);
        btnDefault.setText(DEFAULT_CONFIGURATION_LABEL);

        btnCustom = new Button(grpConfigurationFile, SWT.RADIO);
        btnCustom.setBounds(114, 30, 90, 16);
        btnCustom.setText(CUSTOM_CONFIGURATION_LABEL);
        btnCustom.setSelection(true);

        nodeConfigurationPath = new Text(grpConfigurationFile, SWT.BORDER);
        nodeConfigurationPath.setBounds(110, 57, 292, 25);

        nodeConfigurationPath.setToolTipText(nodeConfigurationPath.getText());
        if (btnDefault.getSelection()) {
            nodeConfigurationPath.setEnabled(false);
            nodeConfigurationPath.setText(defaultCnXDD);
        }
        if (btnDefault.getSelection()) {
            btnBrowse.setEnabled(false);
        }

        btnBrowse = new Button(grpConfigurationFile, SWT.NONE);
        btnBrowse.setBounds(421, 57, 90, 25);
        btnBrowse.setText(BROWSE_CONFIGURATION_LABEL);
        btnBrowse.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {

                FileDialog fileDialog = new FileDialog(
                        getContainer().getShell(), SWT.OPEN);

                fileDialog.setText(IMPORT_CN_CONFIGURATION_FILE_DIALOG_LABEL);
                // Set filter on .XDD and .XDC files
                fileDialog.setFilterExtensions(
                        IPowerlinkProjectSupport.CONFIGURATION_FILTER_EXTENSIONS);
                // Put in a readable name for the filter
                fileDialog.setFilterNames(
                        IPowerlinkProjectSupport.CONFIGURATION_FILTER_NAMES_EXTENSIONS);
                // Open Dialog and save result of selection
                String selectedFile = fileDialog.open();

                if (selectedFile != null) {
                    nodeConfigurationPath.setText(selectedFile.trim());
                }
            }
        });
        nodeConfigurationPath.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                setErrorMessage(null);
                setPageComplete(true);
                if (!isNodeConfigurationValid(
                        nodeConfigurationPath.getText())) {
                    setErrorMessage(ERROR_CHOOSE_VALID_FILE_MESSAGE);
                    setPageComplete(false);
                }

                if (btnCustom.getSelection()) {
                    customConfiguration = nodeConfigurationPath.getText();
                }
                nodeConfigurationPath
                        .setToolTipText(nodeConfigurationPath.getText());
                getWizard().getContainer().updateButtons();
            }
        });
        btnCustom.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {

                if (btnCustom.getSelection()) {
                    nodeConfigurationPath.setEnabled(true);
                    nodeConfigurationPath.setText(customConfiguration);
                    btnBrowse.setEnabled(true);
                }
            }
        });
        btnDefault.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                handleDefaultRadioButtonSelectionChanged(e);
            }
        });
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
        } else {
            List<TCN> cnList = nodeCollection.getCN();
            return getNextValidCnNodeId(cnList);
        }
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
        } else {
            // Return min nodeid if there is no RMN available in the model.
            if (nodeCollection.getRMN().isEmpty()) {
                return IPowerlinkConstants.RMN_MIN_NODE_ID;
            }

            List<TRMN> rmnList = nodeCollection.getRMN();
            return getNextValidRmnNodeId(rmnList);
        }
    }

    /**
     * Returns the next available CN node ID.
     *
     * @param cnNodeList The list of CNs available.
     * @return the new CN node ID.
     */
    private short getNextValidCnNodeId(List<TCN> cnNodeList) {
        setErrorMessage(null);

        List<Short> nodeIdList = getNodeIDs(cnNodeList);
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
        List<Short> rmnNodeIdList = new ArrayList<Short>();
        for (TRMN rmn : rmnNodeList) {
            rmnNodeIdList.add(rmn.getNodeID());
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
     * Returns the list of node IDs.
     *
     * @param cnNodeList The list of nodes.
     * @return the list of node IDs.
     */
    private List<Short> getNodeIDs(List<TCN> cnNodeList) {
        List<Short> nodeIdList = new ArrayList<Short>();
        for (TCN cn : cnNodeList) {
            Short cnNodeId = new Short(cn.getNodeID());
            nodeIdList.add(cnNodeId);
        }
        return nodeIdList;
    }

    /**
     * @return The name of the node selected.
     */
    String getNodeName() {
        return nodeName.getText();
    }

    /**
     * @return The type of the node selected.
     */
    String getNodeType() {
        return nodeTypeCombo.getText();
    }

    /**
     * @return The path for the XDD/XDC.
     */
    String getXdcPath() {
        return nodeConfigurationPath.getText();
    }

    /**
     * @return The XDD/XDC model.
     */
    ISO15745ProfileContainer getXddModel() {
        return xddModel;
    }

    /**
     * Handles the selection change events of the XDD/XDC configuration group.
     *
     * @param e The selection change events.
     */
    private void handleDefaultRadioButtonSelectionChanged(SelectionEvent e) {
        if (btnDefault.getSelection()) {
            nodeConfigurationPath.setEnabled(false);
            btnBrowse.setEnabled(false);
            switch (nodeTypeCombo.getText()) {
                case CONTROLLED_NODE_LABEL:
                    nodeConfigurationPath.setText(defaultCnXDD);
                    break;
                case REDUNDANT_MANAGING_NODE_LABEL:
                    nodeConfigurationPath.setText(defaultMnXDD);
                    break;
                default:
                    System.err.println("invalid selection");
            }

        }
    }

    /**
     * Handles the node ID change events.
     *
     * @param e Modification event.
     */
    private void handleNodeIdSpinnerModification(ModifyEvent e) {
        setErrorMessage(null);
        setPageComplete(true);

        switch (nodeTypeCombo.getText()) {
            case CONTROLLED_NODE_LABEL:
                if (btnDefault.getSelection()) {
                    nodeConfigurationPath.setText(defaultCnXDD);
                }
                break;
            case REDUNDANT_MANAGING_NODE_LABEL:
                if (btnDefault.getSelection()) {
                    nodeConfigurationPath.setText(defaultMnXDD);
                }
                break;
            default:
                System.err.println("invalid selection");
                setPageComplete(false);
        }
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

                nodeName.setText(CONTROLLED_NODE_LABEL + " " + newCnNodeId);
                btnCustom.setEnabled(true);
                break;

            case REDUNDANT_MANAGING_NODE_LABEL:
                nodeIdSpinner.setMaximum(IPowerlinkConstants.RMN_MAX_NODE_ID);
                nodeIdSpinner.setMinimum(IPowerlinkConstants.RMN_MIN_NODE_ID);
                lblNodeIdRangeValue.setText(nodeIdSpinner.getMinimum() + "-"
                        + nodeIdSpinner.getMaximum());
                short newRmnNodeId = getNewRmnNodeId();
                nodeIdSpinner.setSelection(newRmnNodeId);

                nodeName.setText(
                        REDUNDANT_MANAGING_NODE_LABEL + " " + newRmnNodeId);

                if (btnCustom.getSelection()) {
                    btnCustom.setSelection(false);
                    btnDefault.setSelection(true);
                    btnDefault.notifyListeners(SWT.Selection, new Event());
                }
                btnCustom.setEnabled(false);

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

    /**
     * @return The error status of this page.
     */
    public boolean hasErrors() {
        return errorField;
    }

    /**
     * Checks for the valid XDD path.
     *
     * @param xddPath The path to the XDD/XDC.
     * @return true if valid, false otherwise.
     */
    private boolean isNodeConfigurationValid(final String xddPath) {
        boolean retVal = false;
        if ((xddPath == null) || (xddPath.isEmpty())) {
            return retVal;
        }

        File file = new File(xddPath);
        retVal = file.isFile();

        return retVal;
    }

    /**
     * Checks for node available in the given node ID list or not.
     *
     * @param nodeIdList The list of node IDs.
     * @param nodeIdTobeChecked the node ID to be checked.
     * @return <code>True</code> if node is already present <code>False</code>
     *         otherwise.
     */
    private boolean isNodeIdAvailable(List<Short> nodeIdList,
            short nodeIdTobeChecked) {

        boolean nodeIdAvailable = false;
        for (Short nodeId : nodeIdList) {
            if (nodeId.shortValue() == nodeIdTobeChecked) {
                nodeIdAvailable = true;
            }
        }
        return nodeIdAvailable;
    }

    private boolean isNodeIdAvailableInModel(String nodeId) {
        List<Short> nodeIdList = getNodeIDs(nodeCollection.getCN());
        boolean retVal = false;
        try {
            short parsedNodeId = Short.parseShort(nodeId);
            retVal = isNodeIdAvailable(nodeIdList, parsedNodeId);
        } catch (NumberFormatException e) {
            System.err.println("Number format exception");
        }

        return retVal;
    }

    /**
     * Checks for the valid name for the master node.
     *
     * @param nodeName Name
     * @return true if valid, false otherwise.
     */
    private boolean isNodeNameValid(final String nodeName) {
        boolean retval = false;
        if (nodeName == null) {
            return retval;
        }

        if (nodeName.length() > 3) {
            retval = true;
        }
        return retval;
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
        }

        boolean nodeIdAlreadyAvailable = isNodeIdAvailableInModel(
                nodeIdSpinner.getText());
        if (nodeIdAlreadyAvailable) {
            setErrorMessage(ERROR_NODE_ALREADY_EXISTS);
        }

        boolean nameValid = isNodeNameValid(nodeName.getText());
        if (!nameValid) {
            setErrorMessage(ERROR_INVALID_NODE_NAME);
        }

        boolean nodeConfigurationValid = isNodeConfigurationValid(
                nodeConfigurationPath.getText());
        if (!nodeConfigurationValid) {
            setErrorMessage(ERROR_CHOOSE_VALID_FILE_MESSAGE);
        }

        boolean pageComplete = (super.isPageComplete() && nodeIdValid
                && !nodeIdAlreadyAvailable && nameValid
                && nodeConfigurationValid);

        if (pageComplete) {
            updateCnModel();

            if ((nodeModel == null) || (xddModel == null)) {
                pageComplete = false;
            }
        }

        return pageComplete;
    }

    private boolean isValidCnNodeId(short cnNodeId) {
        if ((cnNodeId >= IPowerlinkConstants.CN_MIN_NODE_ID)
                && (cnNodeId <= IPowerlinkConstants.CN_MAX_NODE_ID)) {
            return true;
        }
        return false;
    }

    private boolean isValidNodeId(short nodeId) {
        if ((nodeId > IPowerlinkConstants.INVALID_NODE_ID)
                && (nodeId <= IPowerlinkConstants.BROADCAST_NODE_ID)) {
            return true;
        }
        return false;
    }

    private boolean isValidNodeId(String nodeId) {
        boolean retVal = false;
        try {
            short parsedNodeId = Short.parseShort(nodeId);
            retVal = isValidNodeId(parsedNodeId);
        } catch (NumberFormatException e) {
            System.err.println("Number format exception");
        }
        return retVal;
    }

    private boolean isValidRmnNodeId(short rmnNodeId) {
        if ((rmnNodeId >= IPowerlinkConstants.RMN_MIN_NODE_ID)
                && (rmnNodeId <= IPowerlinkConstants.RMN_MAX_NODE_ID)) {
            return true;
        }
        return false;
    }

    private void updateCnModel() {
        switch (getNodeType()) {
            case AddControlledNodeWizardPage.CONTROLLED_NODE_LABEL:
                TCN cnModel = new TCN();
                cnModel.setName(nodeName.getText());
                cnModel.setNodeID(
                        Integer.toString(nodeIdSpinner.getSelection()));
                cnModel.setPathToXDC(nodeConfigurationPath.getText());
                nodeModel = cnModel;
                break;
            case AddControlledNodeWizardPage.REDUNDANT_MANAGING_NODE_LABEL:
                TRMN rmnModel = new TRMN();
                rmnModel.setName(nodeName.getText());
                rmnModel.setNodeID(Short.parseShort(
                        Integer.toString(nodeIdSpinner.getSelection())));
                rmnModel.setPathToXDC(nodeConfigurationPath.getText());
                nodeModel = rmnModel;
                break;
            default:
                nodeModel = null;
                break;
        }

        try {
            xddModel = XddMarshaller.unmarshallXDDFile(
                    new File(nodeConfigurationPath.getText()));
        } catch (FileNotFoundException | JAXBException | SAXException
                | ParserConfigurationException
                | UnsupportedEncodingException e) {
            xddModel = null;
            setErrorMessage("Invalid XDD/XDC file. " + e.getLocalizedMessage());
            e.printStackTrace();
        }
    }
}

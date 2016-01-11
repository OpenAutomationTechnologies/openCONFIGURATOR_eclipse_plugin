/*******************************************************************************
 * @file   ValidateXddWizardPage.java
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
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.epsg.openconfigurator.model.IPowerlinkProjectSupport;
import org.epsg.openconfigurator.resources.IOpenConfiguratorResource;
import org.epsg.openconfigurator.util.PluginErrorDialogUtils;
import org.epsg.openconfigurator.util.XddMarshaller;
import org.epsg.openconfigurator.xmlbinding.projectfile.TCN;
import org.epsg.openconfigurator.xmlbinding.projectfile.TMN;
import org.epsg.openconfigurator.xmlbinding.projectfile.TRMN;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745ProfileContainer;
import org.xml.sax.SAXException;

public class ValidateXddWizardPage extends WizardPage {

    public static final String CONTROLLED_NODE_LABEL = "Controlled Node";
    public static final String REDUNDANT_MANAGING_NODE_LABEL = "Redundant Managing Node";
    public static final String DIALOG_DESCRIPTION = "Add XDD file to the node.";
    private static final String CONFIGURATION_FILE_LABEL = "Configuration File";
    private static final String NODE_CONFIGURATION_LABEL = "XDD/XDC file:";
    private static final String DEFAULT_CONFIGURATION_LABEL = "Default";
    private static final String CUSTOM_CONFIGURATION_LABEL = "Custom";

    private static final String DIALOG_PAGE_NAME = "ValidateXddwizardPage";
    private static final String BROWSE_CONFIGURATION_LABEL = "Browse...";
    private static final String IMPORT_CN_CONFIGURATION_FILE_DIALOG_LABEL = "Import node's XDD/XDC";
    private static final String ERROR_DEFAULT_CN_XDD_NOT_FOUND = "Default CN XDD not found.";
    private static final String ERROR_DEFAULT_RMN_XDD_NOT_FOUND = "Default Redundant managing XDD not found.";
    private static final String ERROR_CHOOSE_VALID_FILE_MESSAGE = "Choose a valid XDD/XDC file.";
    private static final String ERROR_CHOOSE_VALID_PATH_MESSAGE = "XDD/XDC file does not exist in the path: ";
    private static final String ERROR_INVALID_FILE = "Invalid XDD/XDC file.";
    private static final String VALID_FILE_MESSAGE = "XDD/XDC schema validation successful for ";

    /**
     * Control to display the node configuration path.
     */
    private Text nodeConfigurationPath;

    /**
     * Control to display the xdd error message.
     */
    private StyledText errorinfo;

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
     * Checks for the valid XDD path.
     *
     * @param xddPath The path to the XDD/XDC.
     * @return true if valid, false otherwise.
     */
    boolean retVal = false;

    /**
     * Create the wizard and initializes the path of xdd.
     */
    public ValidateXddWizardPage() {
        super(DIALOG_PAGE_NAME);
        setTitle(AddControlledNodeWizardPage.DIALOG_TILE);
        setDescription(DIALOG_DESCRIPTION);
        String cnXddPath = IOpenConfiguratorResource.DEFAULT_CN_XDD;

        try {
            cnXddPath = org.epsg.openconfigurator.Activator
                    .getAbsolutePath(IOpenConfiguratorResource.DEFAULT_CN_XDD);
        } catch (IOException e) {
            e.printStackTrace();
            PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                    ERROR_DEFAULT_CN_XDD_NOT_FOUND);
        }

        defaultCnXDD = cnXddPath;

        String mnXddPath = IOpenConfiguratorResource.DEFAULT_MN_XDD;
        try {
            mnXddPath = org.epsg.openconfigurator.Activator
                    .getAbsolutePath(IOpenConfiguratorResource.DEFAULT_MN_XDD);
        } catch (IOException e) {
            e.printStackTrace();
            PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                    ERROR_DEFAULT_RMN_XDD_NOT_FOUND);
        }

        defaultMnXDD = mnXddPath;
        customConfiguration = "";
    }

    /**
     * validates the path of xddFile.
     */
    public boolean checkXddModel() {
        try {
            getInfoStyledText("");
            File xddFile = new File(nodeConfigurationPath.getText());

            boolean fileExists = xddFile.exists();
            // if file exists
            if (fileExists) {

                ISO15745ProfileContainer xddModel = XddMarshaller
                        .unmarshallXDDFile(xddFile);
                getInfoStyledText(VALID_FILE_MESSAGE + getNodeNamewithId());
            } else {
                setErrorMessage(ERROR_CHOOSE_VALID_PATH_MESSAGE
                        + nodeConfigurationPath.getText());
                getErrorStyledText(ERROR_CHOOSE_VALID_PATH_MESSAGE
                        + nodeConfigurationPath.getText());
            }

        } catch (FileNotFoundException | JAXBException | SAXException
                | ParserConfigurationException
                | UnsupportedEncodingException e) {
            if ((e.getMessage() != null) && !e.getMessage().isEmpty()) {
                getErrorStyledText(e.getMessage());
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        e.getMessage(), "");
            } else if ((e.getCause() != null)
                    && (e.getCause().getMessage() != null)
                    && !e.getCause().getMessage().isEmpty()) {
                getErrorStyledText(getNodeNamewithId() + " - "
                        + e.getCause().getMessage());
            }
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Create contents of the wizard.
     *
     * @param parent Parent composite
     */
    @Override
    public void createControl(Composite parent) {

        FormToolkit formToolkit = new FormToolkit(Display.getDefault());
        Composite container = new Composite(parent, SWT.NONE);
        container.setLayout(new FillLayout(SWT.VERTICAL));
        setControl(container);
        Composite composite = new Composite(container, SWT.NONE);
        composite.setLayout(new GridLayout(1, false));

        Composite composite_2 = new Composite(composite, SWT.NONE);
        composite_2.setLayoutData(
                new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        composite_2.setLayout(new FillLayout(SWT.HORIZONTAL));

        Composite headerFrame = new Composite(composite_2, SWT.BORDER);
        headerFrame.setLayout(new GridLayout(1, false));

        Group grpConfigurationFile = new Group(headerFrame, SWT.NONE);
        GridData gd_grpConfigurationFile = new GridData(SWT.LEFT, SWT.CENTER,
                false, false, 1, 1);
        gd_grpConfigurationFile.widthHint = 558;
        grpConfigurationFile.setLayoutData(gd_grpConfigurationFile);
        grpConfigurationFile.setText(CONFIGURATION_FILE_LABEL);
        grpConfigurationFile.setLayout(new GridLayout(4, false));

        btnDefault = new Button(grpConfigurationFile, SWT.RADIO);
        btnDefault.setText(DEFAULT_CONFIGURATION_LABEL);

        btnCustom = new Button(grpConfigurationFile, SWT.RADIO);
        btnCustom.setLayoutData(
                new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
        btnCustom.setText(CUSTOM_CONFIGURATION_LABEL);
        btnCustom.setSelection(true);
        new Label(grpConfigurationFile, SWT.NONE);

        if (btnDefault.getSelection()) {
            nodeConfigurationPath.setEnabled(false);
            nodeConfigurationPath.setText(defaultCnXDD);
            btnBrowse.setEnabled(false);
        }

        Label lblXddxdc = new Label(grpConfigurationFile, SWT.NONE);
        lblXddxdc.setText(NODE_CONFIGURATION_LABEL);

        nodeConfigurationPath = new Text(grpConfigurationFile, SWT.BORDER);
        nodeConfigurationPath.setLayoutData(
                new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));

        nodeConfigurationPath.setToolTipText(nodeConfigurationPath.getText());
        nodeConfigurationPath.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                setErrorMessage(null);
                getInfoStyledText("");
                setPageComplete(true);
                if (!isNodeConfigurationValid(
                        nodeConfigurationPath.getText())) {
                    setErrorMessage(ERROR_CHOOSE_VALID_FILE_MESSAGE);
                    getErrorStyledText(ERROR_CHOOSE_VALID_FILE_MESSAGE);
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

        btnBrowse = new Button(grpConfigurationFile, SWT.NONE);
        btnBrowse.setLayoutData(
                new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
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

        TextViewer textViewer = new TextViewer(headerFrame,
                SWT.BORDER | SWT.WRAP | SWT.READ_ONLY);
        errorinfo = textViewer.getTextWidget();
        errorinfo.setLayoutData(
                new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
        formToolkit.paintBordersFor(errorinfo);

    }

    /**
     *
     * @return instance of text Viewer widget.
     */
    public StyledText getErrorStyledText(String message) {
        errorinfo.setForeground(new Color(Display.getDefault(), 255, 0, 0));
        errorinfo.setText(message);
        return errorinfo;
    }

    /**
     *
     * @return instance of text Viewer widget.
     */
    public StyledText getInfoStyledText(String message) {
        errorinfo.setForeground(new Color(Display.getDefault(), 46, 92, 39));
        errorinfo.setText(message);
        return errorinfo;
    }

    /**
     * Receive nodeConfiguration path.
     *
     * @return Path of nodeConfiguration.
     */
    public Path getNodeConfigurationPath() {
        return Paths.get(nodeConfigurationPath.getText());
    }

    /**
     * Receive node name with ID.
     *
     * @return Name and ID of node.
     */
    public String getNodeNamewithId() {
        Object nodeobj = setnode();
        if (nodeobj instanceof TCN) {
            TCN cnmodel = (TCN) nodeobj;
            return cnmodel.getName() + "(" + cnmodel.getNodeID() + ")";

        } else if (nodeobj instanceof TRMN) {
            TRMN rmnmodel = (TRMN) nodeobj;
            return rmnmodel.getName() + "(" + rmnmodel.getNodeID() + ")";
        } else if (nodeobj instanceof TMN) {
            TMN mnmodel = (TMN) nodeobj;
            return mnmodel.getName() + "(" + mnmodel.getNodeID() + ")";
        } else {
            System.err.println("Invalid node model.");
        }
        return null;
    }

    private void handleDefaultRadioButtonSelectionChanged(SelectionEvent e) {
        if (btnDefault.getSelection()) {
            nodeConfigurationPath.setEnabled(false);
            btnBrowse.setEnabled(false);

            Object nodeobj = setnode();
            if (nodeobj instanceof TCN) {
                TCN cnmodel = (TCN) nodeobj;
                cnmodel.setPathToXDC(defaultCnXDD);
                nodeConfigurationPath.setText(defaultCnXDD);
            } else if (nodeobj instanceof TRMN) {
                TRMN rmnmodel = (TRMN) nodeobj;
                rmnmodel.setPathToXDC(defaultMnXDD);
                nodeConfigurationPath.setText(defaultMnXDD);
            } else if (nodeobj instanceof TMN) {
                TMN mnmodel = (TMN) nodeobj;
                mnmodel.setPathToXDC(defaultMnXDD);
                nodeConfigurationPath.setText(defaultMnXDD);
            } else {
                System.err.println("Invalid node model.");
            }
        }
    }

    private boolean isNodeConfigurationValid(final String xddPath) {
        if ((xddPath == null) || (xddPath.isEmpty())) {
            return retVal;
        }

        File file = new File(xddPath);
        retVal = file.isFile();

        return retVal;
    }

    /**
     * Checks for error in the page
     *
     * @return page complete status.
     */
    @Override
    public boolean isPageComplete() {
        boolean nodeConfigurationValid = isNodeConfigurationValid(
                nodeConfigurationPath.getText());
        if (nodeConfigurationPath.getText().isEmpty()) {
            setErrorMessage(ERROR_CHOOSE_VALID_FILE_MESSAGE);
            errorinfo.setBackground(
                    new Color(Display.getDefault(), 240, 241, 241));
            errorinfo.setEnabled(false);

            return false;
        } else {
            errorinfo.setBackground(
                    new Color(Display.getDefault(), 255, 255, 255));
            errorinfo.setEnabled(true);
        }
        if (!nodeConfigurationValid) {
            setErrorMessage(ERROR_CHOOSE_VALID_FILE_MESSAGE);
            return false;
        }
        boolean pageComplete = (super.isPageComplete())
                && nodeConfigurationValid;

        if (checkXddModel()) {
            pageComplete = true;
        } else {
            pageComplete = false;
        }

        return pageComplete;
    }

    /**
     * Node model values of previous wizard page.
     *
     * @return node model of previous page.
     */
    public Object setnode() {
        IWizardPage previousPage = getPreviousPage();
        if (previousPage instanceof AddControlledNodeWizardPage) {
            AddControlledNodeWizardPage adCnPage = (AddControlledNodeWizardPage) previousPage;
            return adCnPage.getNode();
        } else if (previousPage instanceof AddDefaultMasterNodeWizardPage) {
            AddDefaultMasterNodeWizardPage adMnPage = (AddDefaultMasterNodeWizardPage) previousPage;
            return adMnPage.getNode();
        }
        return null;
    }
}

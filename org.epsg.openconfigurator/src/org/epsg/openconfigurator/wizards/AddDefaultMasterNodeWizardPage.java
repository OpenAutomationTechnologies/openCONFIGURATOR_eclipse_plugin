/*******************************************************************************
 * @file   AddDefaultMasterNodeWizardPage.java
 *
 * @brief  Wizard page to to add default managing node for the project.
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
import java.io.IOException;
import java.nio.file.Paths;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.epsg.openconfigurator.resources.IOpenConfiguratorResource;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectMarshaller;
import org.epsg.openconfigurator.util.PluginErrorDialogUtils;
import org.epsg.openconfigurator.xmlbinding.projectfile.OpenCONFIGURATORProject;
import org.epsg.openconfigurator.xmlbinding.projectfile.TAutoGenerationSettings;
import org.epsg.openconfigurator.xmlbinding.projectfile.TGenerator;
import org.epsg.openconfigurator.xmlbinding.projectfile.TMN;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNetworkConfiguration;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNodeCollection;
import org.epsg.openconfigurator.xmlbinding.projectfile.TPath;
import org.epsg.openconfigurator.xmlbinding.projectfile.TProjectConfiguration;

/**
 * A wizardpage class to add a default master node to the project.
 *
 * @author Ramakrishnan P
 *
 */
final class AddDefaultMasterNodeWizardPage extends WizardPage {

    /**
     * Dialog messages and labels.
     */
    public static final String DEFAULT_MN_WIZARD_PAGE_NAME = "AddDefaultMasterNode";
    public static final String DEFAULT_MN_WIZARD_PAGE_TITLE = "Project Managing Node";
    public static final String DEFAULT_MN_WIZARD_PAGE_DESCRIPTION = "Add a managing node configuration";

    public static final String PROJECT_DIRECTORY_DEVICEIMPORT = "deviceImport";
    public static final String PROJECT_DIRECTORY_DEVICECONFIGURATION = "deviceConfiguration";
    public static final String XDC_EXTENSION = ".xdc"; ////$NON-NLS-1$
    public static final String XDD_EXTENSION = ".xdd"; ////$NON-NLS-1$

    public static final String DEFAULT_MN_WIZARDPAGE_CONFIGURATION_FILE_LABEL = "Configuration file";
    public static final String DEFAULT_MN_WIZARDPAGE_IMPORT_FILE_LABEL = "Import Master Node's XDD/XDC";
    public static final String DEFAULT_MN_WIZARDPAGE_XDD_FILTERS = "*.xdd;*.XDD"; ////$NON-NLS-1$
    public static final String DEFAULT_MN_WIZARDPAGE_XDC_FILTERS = "*.xdc;*.XDC"; ////$NON-NLS-1$
    public static final String DEFAULT_MN_WIZARDPAGE_XDD_FILTER_NAME = "XML Device Description(*.xdd)";
    public static final String DEFAULT_MN_WIZARDPAGE_XDC_FILTER_NAME = "XML Device Configuration(*.xdc)";
    public static final String DEFAULT_MN_WIZARDPAGE_BROWSE_LABEL = "Browse...";
    public static final String DEFAULT_MN_WIZARDPAGE_DEFAULT_LABEL = "Default";
    public static final String DEFAULT_MN_WIZARDPAGE_CUSTOM_LABEL = "Custom";

    public static final String DEFAULT_MN_WIZARDPAGE_CHOOSE_VALID_FILE_MESSAGE = "Choose a valid XDD/XDC file";
    public static final String DEFAULT_MN_WIZARDPAGE_MN_PATH_TIP = "Path for the managing node device configuration file.\n"
            + "If you are new to POWERLINK use 'Default' MN XDD.";
    public static final String DEFAULT_MN_WIZARDPAGE_NODEID_TIP = "ID of the managing node.";
    public static final String DEFAULT_MN_WIZARDPAGE_NODEID_LABEL = "Node ID:";
    public static final String DEFAULT_MN_WIZARDPAGE_XDD_XDC_FILE_LABEL = "XDD/XDC file:";
    public static final String DEFAULT_MN_WIZARDPAGE_NAME_LABEL = "Name:";
    public static final String DEFAULT_MN_WIZARDPAGE_NAME_TIP = "Name of the managing node";
    public static final String DEFAULT_MN_WIZARDPAGE_NAME_MESSAGE = "Enter a valid node name";

    private static final String DEFAULT_MN_XDD_NOT_FOUND = "Default MN XDD not found.";
    private static final String ERROR_WHILE_COPYING_XDD = "Error occurred while copying the XDD files.";

    /**
     * MN XDD/XDC configuration path.
     */
    private Text mnConfiguration;

    /**
     * Radio button for default configuration selection.
     */
    private Button btnDefaultConfiguation;

    /**
     * Defualt managing node name.
     */
    private Text txtNodeName;

    /**
     * Browse XDD/XDC button.
     */
    private Button btnBrowse;

    /**
     * Default managing node name.
     */
    private final String defaultMasterName = "openPOWERLINK_MN";

    /**
     * Default managing node XDD path.
     */
    private final String defaultMnXDD;

    /**
     * Default managing node ID.
     */
    private final short defaultMnNodeId = 240;

    /**
     * MN object model.
     */
    private TMN mn = new TMN();

    /**
     * Falg to monitor the page completion.
     */
    private boolean pageComplete = false;

    /**
     * Create the Add default managing node wizard.
     */
    public AddDefaultMasterNodeWizardPage() {
        super(AddDefaultMasterNodeWizardPage.DEFAULT_MN_WIZARD_PAGE_NAME,
                AddDefaultMasterNodeWizardPage.DEFAULT_MN_WIZARD_PAGE_TITLE,
                null);
        setDescription(AddDefaultMasterNodeWizardPage.DEFAULT_MN_WIZARD_PAGE_DESCRIPTION);

        String mnXddPath;
        try {
            mnXddPath = org.epsg.openconfigurator.Activator
                    .getAbsolutePath(IOpenConfiguratorResource.DEFAULT_MN_XDD);
        } catch (IOException e) {
            e.printStackTrace();
            mnXddPath = "openPOWERLINK_MN.XDD";
            PluginErrorDialogUtils.displayErrorMessageDialog(
                    org.epsg.openconfigurator.Activator.getDefault()
                            .getWorkbench().getActiveWorkbenchWindow()
                            .getShell(),
                    AddDefaultMasterNodeWizardPage.DEFAULT_MN_XDD_NOT_FOUND, e);
        }

        defaultMnXDD = mnXddPath;

        /**
         * sets the default values for the MN instance.
         */
        mn.setNodeID(defaultMnNodeId);
        mn.setPathToXDC(defaultMnXDD);
        mn.setName(defaultMasterName);
    }

    /**
     * Import the device description and device configuration files into the
     * respective directories into the project.
     *
     * @param projectPath Path of the project
     */
    void copyXddToDeviceImportDir(final String projectPath) {
        try {

            java.nio.file.Path mnXdd = getMnXddFile().toPath();

            System.out.println("MN XDD path" + mnXdd.toString());

            String targetImportPath = new String(
                    projectPath
                            + File.separator
                            + AddDefaultMasterNodeWizardPage.PROJECT_DIRECTORY_DEVICEIMPORT
                            + File.separator + mnXdd.getFileName().toString());

            // Copy the MN XDD to deviceImport dir
            java.nio.file.Files.copy(
                    new java.io.File(mnXdd.toString()).toPath(),
                    new java.io.File(targetImportPath).toPath(),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING,
                    java.nio.file.StandardCopyOption.COPY_ATTRIBUTES,
                    java.nio.file.LinkOption.NOFOLLOW_LINKS);

            // Rename the XDD to XDC and copy the deviceImport MN XDD to
            // deviceConfiguration dir
            String extensionXdd = mnXdd.getFileName().toString();
            int pos = extensionXdd.lastIndexOf(".");
            if (pos > 0) {
                extensionXdd = extensionXdd.substring(0, pos);
            } else {
                // TODO: ERR. need to assert.
            }

            String targetConfigurationPath = new String(
                    projectPath
                            + File.separator
                            + AddDefaultMasterNodeWizardPage.PROJECT_DIRECTORY_DEVICECONFIGURATION
                            + File.separator + extensionXdd
                            + AddDefaultMasterNodeWizardPage.XDC_EXTENSION);

            java.nio.file.Files.copy(
                    new java.io.File(targetImportPath).toPath(),
                    new java.io.File(targetConfigurationPath).toPath(),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING,
                    java.nio.file.StandardCopyOption.COPY_ATTRIBUTES,
                    java.nio.file.LinkOption.NOFOLLOW_LINKS);

            java.nio.file.Path pathBase = Paths.get(projectPath);

            // Set the relative path to the MN object
            java.nio.file.Path pathRelative = pathBase.relativize(Paths
                    .get(targetConfigurationPath));
            mn.setPathToXDC(pathRelative.toString());

        } catch (UnsupportedOperationException | SecurityException
                | IOException e) {
            e.printStackTrace();

            PluginErrorDialogUtils.displayErrorMessageDialog(
                    org.epsg.openconfigurator.Activator.getDefault()
                            .getWorkbench().getActiveWorkbenchWindow()
                            .getShell(),
                    AddDefaultMasterNodeWizardPage.ERROR_WHILE_COPYING_XDD, e);
        }
    }

    /**
     * Create contents of the add default master node wizard page.
     *
     * @param parent Parent control.
     */
    @Override
    public void createControl(final Composite parent) {
        Composite container = new Composite(parent, SWT.NULL);

        setControl(container);
        container.setLayout(new FillLayout(SWT.HORIZONTAL));

        Composite composite = new Composite(container, SWT.NONE);
        composite.setLayout(new FormLayout());

        Group grpConfiguration = new Group(composite, SWT.NONE);
        FormData fd_grpConfiguration = new FormData();
        fd_grpConfiguration.right = new FormAttachment(100, -58);
        fd_grpConfiguration.left = new FormAttachment(0, 10);
        grpConfiguration.setLayoutData(fd_grpConfiguration);
        grpConfiguration
                .setText(AddDefaultMasterNodeWizardPage.DEFAULT_MN_WIZARDPAGE_CONFIGURATION_FILE_LABEL);
        grpConfiguration.setLayout(new FormLayout());

        btnBrowse = new Button(grpConfiguration, SWT.PUSH);
        btnBrowse.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                FileDialog fileDialog = new FileDialog(parent.getShell(),
                        SWT.OPEN);
                fileDialog
                        .setText(AddDefaultMasterNodeWizardPage.DEFAULT_MN_WIZARDPAGE_IMPORT_FILE_LABEL);
                // Set filter on .XDD and .XDC files
                fileDialog
                        .setFilterExtensions(new String[] {
                                AddDefaultMasterNodeWizardPage.DEFAULT_MN_WIZARDPAGE_XDD_FILTERS,
                                AddDefaultMasterNodeWizardPage.DEFAULT_MN_WIZARDPAGE_XDC_FILTERS });
                // Put in a readable name for the filter
                fileDialog
                        .setFilterNames(new String[] {
                                AddDefaultMasterNodeWizardPage.DEFAULT_MN_WIZARDPAGE_XDD_FILTER_NAME,
                                AddDefaultMasterNodeWizardPage.DEFAULT_MN_WIZARDPAGE_XDC_FILTER_NAME });
                // Open Dialog and save result of selection
                String selectedFile = fileDialog.open();

                if (selectedFile != null) {
                    mnConfiguration.setText(selectedFile.trim());
                    mn.setPathToXDC(AddDefaultMasterNodeWizardPage.this
                            .getMnConfiguration());
                }
            }
        });
        btnBrowse.setEnabled(false);
        FormData fd_btnBrowse = new FormData();
        fd_btnBrowse.right = new FormAttachment(0, 485);
        fd_btnBrowse.top = new FormAttachment(0, 54);
        fd_btnBrowse.left = new FormAttachment(0, 410);
        btnBrowse.setLayoutData(fd_btnBrowse);
        btnBrowse
                .setText(AddDefaultMasterNodeWizardPage.DEFAULT_MN_WIZARDPAGE_BROWSE_LABEL);

        Composite composite_1 = new Composite(grpConfiguration, SWT.NONE);
        FormData fd_composite_1 = new FormData();
        fd_composite_1.bottom = new FormAttachment(0, 33);
        fd_composite_1.right = new FormAttachment(0, 230);
        fd_composite_1.top = new FormAttachment(0, 8);
        fd_composite_1.left = new FormAttachment(0, 24);
        composite_1.setLayoutData(fd_composite_1);
        composite_1.setLayout(new RowLayout(SWT.HORIZONTAL));

        btnDefaultConfiguation = new Button(composite_1, SWT.RADIO);
        btnDefaultConfiguation.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                mnConfiguration.setEnabled(false);
                btnBrowse.setEnabled(false);
                mnConfiguration.setText(defaultMnXDD);
                mn.setPathToXDC(defaultMnXDD);
                // pageComplete = true;
                // getWizard().getContainer().updateButtons();
            }
        });
        btnDefaultConfiguation.setSelection(true);
        btnDefaultConfiguation
                .setText(AddDefaultMasterNodeWizardPage.DEFAULT_MN_WIZARDPAGE_DEFAULT_LABEL);

        Button btnCustomConfiguration = new Button(composite_1, SWT.RADIO);
        btnCustomConfiguration.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                mnConfiguration.setEnabled(true);
                btnBrowse.setEnabled(true);
                mnConfiguration.setText("");
                mn.setPathToXDC("");
                // pageComplete = false;
                // getWizard().getContainer().updateButtons();
            }
        });
        btnCustomConfiguration
                .setText(AddDefaultMasterNodeWizardPage.DEFAULT_MN_WIZARDPAGE_CUSTOM_LABEL);

        mnConfiguration = new Text(grpConfiguration, SWT.BORDER);
        mnConfiguration.setEnabled(false);
        mnConfiguration.setText(defaultMnXDD);
        mnConfiguration
                .setToolTipText(AddDefaultMasterNodeWizardPage.DEFAULT_MN_WIZARDPAGE_MN_PATH_TIP);
        mnConfiguration.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {

                if (isMnConfigurationValid(getMnConfiguration())) {
                    setErrorMessage(null);
                    mn.setPathToXDC(getMnConfiguration());
                    pageComplete = true;
                } else {
                    setErrorMessage(AddDefaultMasterNodeWizardPage.DEFAULT_MN_WIZARDPAGE_CHOOSE_VALID_FILE_MESSAGE);
                    pageComplete = false;
                }
                getWizard().getContainer().updateButtons();
            }
        });
        FormData fd_text = new FormData();
        fd_text.right = new FormAttachment(0, 404);
        fd_text.top = new FormAttachment(0, 56);
        fd_text.left = new FormAttachment(0, 104);
        mnConfiguration.setLayoutData(fd_text);

        Spinner spinnerNodeId = new Spinner(composite, SWT.BORDER);
        fd_grpConfiguration.bottom = new FormAttachment(spinnerNodeId, 133,
                SWT.BOTTOM);
        fd_grpConfiguration.top = new FormAttachment(spinnerNodeId, 20);
        FormData fd_spinner = new FormData();
        fd_spinner.top = new FormAttachment(0, 56);
        fd_spinner.left = new FormAttachment(0, 120);
        spinnerNodeId.setLayoutData(fd_spinner);
        spinnerNodeId.setMaximum(255);
        spinnerNodeId.setSelection(defaultMnNodeId);
        spinnerNodeId.setEnabled(false);
        spinnerNodeId
                .setToolTipText(AddDefaultMasterNodeWizardPage.DEFAULT_MN_WIZARDPAGE_NODEID_TIP);

        Label lblNodeId = new Label(composite, SWT.NONE);
        FormData fd_lblNodeId = new FormData();
        fd_lblNodeId.right = new FormAttachment(0, 75);
        fd_lblNodeId.top = new FormAttachment(0, 59);
        fd_lblNodeId.left = new FormAttachment(0, 10);
        lblNodeId.setLayoutData(fd_lblNodeId);
        lblNodeId
                .setText(AddDefaultMasterNodeWizardPage.DEFAULT_MN_WIZARDPAGE_NODEID_LABEL);

        Label lblXddxdcFile = new Label(grpConfiguration, SWT.NONE);
        FormData fd_lblXddxdcFile = new FormData();
        fd_lblXddxdcFile.bottom = new FormAttachment(btnBrowse, 0, SWT.BOTTOM);
        fd_lblXddxdcFile.top = new FormAttachment(mnConfiguration, 0, SWT.TOP);
        fd_lblXddxdcFile.right = new FormAttachment(mnConfiguration, 0);
        lblXddxdcFile.setLayoutData(fd_lblXddxdcFile);
        lblXddxdcFile
                .setText(AddDefaultMasterNodeWizardPage.DEFAULT_MN_WIZARDPAGE_XDD_XDC_FILE_LABEL);

        Label lblName = new Label(composite, SWT.NONE);
        FormData fd_lblName = new FormData();
        fd_lblName.right = new FormAttachment(0, 65);
        fd_lblName.top = new FormAttachment(0, 18);
        fd_lblName.left = new FormAttachment(0, 10);
        lblName.setLayoutData(fd_lblName);
        lblName.setText(AddDefaultMasterNodeWizardPage.DEFAULT_MN_WIZARDPAGE_NAME_LABEL);

        txtNodeName = new Text(composite, SWT.BORDER);
        txtNodeName.setText(defaultMasterName);
        txtNodeName
                .setToolTipText(AddDefaultMasterNodeWizardPage.DEFAULT_MN_WIZARDPAGE_NAME_TIP);
        txtNodeName.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                if (isMnNameValid(getTxtNodeName())) {
                    setErrorMessage(null);
                    mn.setName(getTxtNodeName());
                    pageComplete = true;
                } else {
                    setErrorMessage(AddDefaultMasterNodeWizardPage.DEFAULT_MN_WIZARDPAGE_NAME_MESSAGE);
                    pageComplete = false;
                }
                getWizard().getContainer().updateButtons();
            }
        });

        txtNodeName.addVerifyListener(new VerifyListener() {
            @Override
            public void verifyText(VerifyEvent event) {
                // Assume we don't allow it
                event.doit = false;

                // Get the character typed
                char inputChar = event.character;

                // Allow 0-9, A-Z, a-z
                if (Character.isLetterOrDigit(inputChar)) {
                    event.doit = true;
                }

                // Allow space
                if (Character.isSpaceChar(inputChar)) {
                    event.doit = true;
                }

                // Allow arrow keys and backspace and delete keys
                if ((inputChar == SWT.BS) || (inputChar == SWT.ARROW_LEFT)
                        || (inputChar == SWT.ARROW_RIGHT)
                        || (inputChar == SWT.DEL)) {
                    event.doit = true;
                }
            }
        });
        FormData fd_text_1 = new FormData();
        fd_text_1.right = new FormAttachment(0, 294);
        fd_text_1.top = new FormAttachment(0, 15);
        fd_text_1.left = new FormAttachment(0, 120);
        txtNodeName.setLayoutData(fd_text_1);
        txtNodeName.setFocus();
        composite.setTabList(new Control[] { txtNodeName, spinnerNodeId,
                grpConfiguration });
        grpConfiguration.setTabList(new Control[] { composite_1,
                mnConfiguration, btnBrowse });
        composite_1.setTabList(new Control[] { btnDefaultConfiguation,
                btnCustomConfiguration });

        // Add default values are correct. So set the page as completed.
        pageComplete = true;
    }

    /**
     * Create the new openCONFIGURATOR project file with a MN in the given path.
     *
     * @param projectFilePath Path for the new project file.
     */
    void createProject(final String projectFilePath) {
        File openConfiguratorProjectFile = new File(projectFilePath);
        OpenCONFIGURATORProject openConfiguratorProject = new OpenCONFIGURATORProject();
        TGenerator tGenerator = new TGenerator();
        tGenerator
        .setVendor("Kalycito Infotech Private Limited &amp; Bernecker + Rainer Industrie Elektronik Ges.m.b.H.");
        // TODO: Identify the user from the system.
        tGenerator.setCreatedBy("openCONFIGURATOR eclipse plugin");
        tGenerator.setToolName("openCONFIGURATOR");
        tGenerator.setToolVersion("2.0.0");
        tGenerator.setCreatedOn(getXMLGregorianCalendarNow());
        tGenerator.setModifiedOn(getXMLGregorianCalendarNow());
        // TODO: Identify the user from the system.
        tGenerator.setModifiedBy("openCONFIGURATOR eclipse plugin");
        openConfiguratorProject.setGenerator(tGenerator);

        // Add default project configurations
        TProjectConfiguration tProjectConfiguration = new TProjectConfiguration();
        tProjectConfiguration.setActiveAutoGenerationSetting("all");

        // Add default output path
        TProjectConfiguration.PathSettings pathSettings = new TProjectConfiguration.PathSettings();
        java.util.List<TPath> pathList = pathSettings.getPath();
        TPath path = new TPath();
        path.setId("defaultOutputPath");
        path.setPath("output");
        pathList.add(path);
        tProjectConfiguration.setPathSettings(pathSettings);

        // Auto generation settings
        java.util.List<TAutoGenerationSettings> autoGenerationSettingsList = tProjectConfiguration
                .getAutoGenerationSettings();
        TAutoGenerationSettings tAutoGenerationSettings = new TAutoGenerationSettings();
        tAutoGenerationSettings.setId("all");
        autoGenerationSettingsList.add(tAutoGenerationSettings);

        tAutoGenerationSettings = new TAutoGenerationSettings();
        tAutoGenerationSettings.setId("none");
        autoGenerationSettingsList.add(tAutoGenerationSettings);

        // Add Network configurations
        TNodeCollection nc = new TNodeCollection();
        nc.setMN(getMnNode());

        TNetworkConfiguration tNetworkConfiguration = new TNetworkConfiguration();
        tNetworkConfiguration.setNodeCollection(nc);

        openConfiguratorProject.setProjectConfiguration(tProjectConfiguration);
        openConfiguratorProject.setNetworkConfiguration(tNetworkConfiguration);
        try {
            OpenConfiguratorProjectMarshaller.marshallOpenConfiguratorProject(
                    openConfiguratorProject, openConfiguratorProjectFile);
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @return The MN configuration location.
     */
    private String getMnConfiguration() {
        return mnConfiguration.getText().trim();
    }

    /**
     * @return The MN instance
     */
    private TMN getMnNode() {
        return mn;
    }

    /**
     * Returns the File instance of the input MN XDD file path. Null if not a
     * valid path
     *
     * @return File
     */
    private File getMnXddFile() {

        if (btnDefaultConfiguation.getSelection()) {
            return new File(defaultMnXDD);
        } else {
            return new java.io.File(getMnConfiguration());
        }
    }

    /**
     * Returns the name of the MN node from the wizard page.
     *
     * @return String
     */
    private String getTxtNodeName() {
        if (txtNodeName != null) {
            return txtNodeName.getText().trim();
        }

        return "";
    }

    /**
     * Returns the current Gregorian Calendar time. If any exception returns
     * null;
     *
     * @return XMLGregorianCalendar
     */
    public XMLGregorianCalendar getXMLGregorianCalendarNow() {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        DatatypeFactory datatypeFactory;
        try {
            datatypeFactory = DatatypeFactory.newInstance();
            XMLGregorianCalendar now = datatypeFactory
                    .newXMLGregorianCalendar(gregorianCalendar);
            return now;
        } catch (DatatypeConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.err.println("Error getting the system time");
        return null;
    }

    /**
     * Checks for the valid MN XDD path.
     *
     * @param mnXddPath The path to the MN XDD/XDC.
     * @return true if valid, false otherwise.
     */
    private boolean isMnConfigurationValid(final String mnXddPath) {
        boolean retVal = false;
        if (getMnConfiguration().isEmpty()) {
            return retVal;
        }

        if (btnDefaultConfiguation.getSelection()) {
            retVal = true;
        } else {
            retVal = validateURI(mnXddPath);
        }
        return retVal;
    }

    /**
     * Checks for the valid name for the master node.
     *
     * @param mnName Name
     * @return true if valid, false otherwise.
     */
    private boolean isMnNameValid(final String mnName) {
        boolean retval = false;
        if (mnName.length() > 5) {
            retval = true;
        }
        return retval;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPageComplete() {
        boolean mnConfigurationValid = isMnConfigurationValid(getMnConfiguration());
        boolean mnNameValid = isMnNameValid(getTxtNodeName());
        return (pageComplete && mnConfigurationValid && mnNameValid);
    }

    /**
     * Validates the string for the proper URI syntax. True if correct syntax
     * and false otherwise.
     *
     * @param uriValue String that has to be validated for the URI syntax.
     * @return boolean true if the URI is valid, false otherwise.
     */
    private boolean validateURI(String uriValue) {
        File file = new File(uriValue);
        return file.isFile();
    }
}

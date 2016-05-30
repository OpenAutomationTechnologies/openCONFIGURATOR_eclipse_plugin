package org.epsg.openconfigurator.wizards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.epsg.openconfigurator.model.HeadNodeInterface;
import org.epsg.openconfigurator.model.IPowerlinkProjectSupport;
import org.epsg.openconfigurator.model.Module;
import org.epsg.openconfigurator.model.PowerlinkRootNode;
import org.epsg.openconfigurator.resources.IOpenConfiguratorResource;
import org.epsg.openconfigurator.util.PluginErrorDialogUtils;
import org.epsg.openconfigurator.util.XddMarshaller;
import org.epsg.openconfigurator.views.IndustrialNetworkView;
import org.epsg.openconfigurator.xmlbinding.projectfile.InterfaceList;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745Profile;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745ProfileContainer;
import org.epsg.openconfigurator.xmlbinding.xdd.ModuleType;
import org.epsg.openconfigurator.xmlbinding.xdd.ModuleTypeList;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyCommunicationNetworkPowerlink;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyCommunicationNetworkPowerlinkModularHead;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDataType;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDevicePowerlink;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDevicePowerlinkModularChild;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDevicePowerlinkModularHead;
import org.epsg.openconfigurator.xmlbinding.xdd.TModuleInterface;
import org.xml.sax.SAXException;

public class ValidateXddModuleWizardPage extends WizardPage {

    public static final String CONTROLLED_NODE_LABEL = "Controlled Node";
    public static final String REDUNDANT_MANAGING_NODE_LABEL = "Redundant Managing Node";
    public static final String DIALOG_DESCRIPTION = "Add XDD file to the module.";
    private static final String CONFIGURATION_FILE_LABEL = "Configuration File";
    private static final String NODE_CONFIGURATION_LABEL = "XDD/XDC file:";

    private static final String DIALOG_PAGE_NAME = "ValidateXddwizardPage";
    private static final String BROWSE_CONFIGURATION_LABEL = "Browse...";
    private static final String IMPORT_CN_CONFIGURATION_FILE_DIALOG_LABEL = "Import node's XDD/XDC";
    private static final String ERROR_CHOOSE_VALID_FILE_MESSAGE = "Choose a valid XDD/XDC file.";
    private static final String ERROR_CHOOSE_VALID_PATH_MESSAGE = "XDD/XDC file does not exist in the path: ";
    private static final String ERROR_INVALID_MODULAR_CHILD_CN_FILE_MESSAGE = "Cannot import CN or modular head node XDD / XDC as module. \nPlease choose a valid modular child XDD / XDC";
    private static final String VALID_FILE_MESSAGE = "XDD/XDC schema validation successful for ";
    public static final String DIALOG_TILE = "POWERLINK module";

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

    private Group grpConfigurationFile;

    /**
     * This is to restore the custom configuration.
     */
    private String customConfiguration;

    private HeadNodeInterface headNodeInetrfaceObject;

    private ISO15745ProfileContainer xddModel;

    private Text moduleTypeText;

    private Label moduleTypeLabel;

    /**
     * Checks for the valid XDD path.
     *
     * @param xddPath The path to the XDD/XDC.
     * @return true if valid, false otherwise.
     */
    boolean retVal = false;

    // Node configuration path listener
    private ModifyListener nodeConfigurationPathModifyListener = new ModifyListener() {
        @Override
        public void modifyText(ModifyEvent e) {
            setErrorMessage(null);
            getInfoStyledText("");
            setPageComplete(true);
            if (!isNodeConfigurationValid(nodeConfigurationPath.getText())) {
                setErrorMessage(ERROR_CHOOSE_VALID_FILE_MESSAGE);
                getErrorStyledText(ERROR_CHOOSE_VALID_FILE_MESSAGE);
                setPageComplete(false);
            }

            customConfiguration = nodeConfigurationPath.getText();

            nodeConfigurationPath
                    .setToolTipText(nodeConfigurationPath.getText());
            getWizard().getContainer().updateButtons();

        }
    };

    /**
     * Create the wizard and initializes the path of xdd.
     */
    public ValidateXddModuleWizardPage(HeadNodeInterface interfaceObj) {
        super(DIALOG_PAGE_NAME);
        headNodeInetrfaceObject = interfaceObj;
        setTitle(DIALOG_TILE);
        setDescription(DIALOG_DESCRIPTION);
        String cnXddPath = IOpenConfiguratorResource.DEFAULT_CN_XDD;

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

                xddModel = XddMarshaller.unmarshallXDDFile(xddFile);

                // Checks for the valid XDD/XDC file of respective node.

                for (ISO15745Profile profile : xddModel.getISO15745Profile()) {
                    ProfileBodyDataType profileBodyDatatype = profile
                            .getProfileBody();
                    if ((profileBodyDatatype instanceof ProfileBodyDevicePowerlinkModularHead)
                            || (profileBodyDatatype instanceof ProfileBodyCommunicationNetworkPowerlinkModularHead)
                            || (profileBodyDatatype instanceof ProfileBodyDevicePowerlink)
                            || (profileBodyDatatype instanceof ProfileBodyCommunicationNetworkPowerlink)) {

                        getErrorStyledText(
                                ERROR_INVALID_MODULAR_CHILD_CN_FILE_MESSAGE);
                        return false;
                    }

                }

                String moduleInterfaceType = getModuleInterface().getType();
                List<ModuleType> moduleTypeList = getModuleTypeList()
                        .getModuleType();
                boolean validModuleType = true;
                for (ModuleType moduleType : moduleTypeList) {
                    if ((moduleTypeText.getText()
                            .equalsIgnoreCase(moduleType.getType()))
                            || moduleTypeText.getText()
                                    .contains(moduleInterfaceType)) {
                        System.out.println("Valid Module type");
                        validModuleType = true;
                    } else {
                        validModuleType = false;
                    }
                    if (headNodeInetrfaceObject.getModuleCollection()
                            .size() != 0) {
                        if (headNodeInetrfaceObject.isMultipleModules()) {
                            System.out.println("Multiple Modules = "
                                    + headNodeInetrfaceObject
                                            .isMultipleModules());
                        } else {
                            Collection<Module> moduleCollection = headNodeInetrfaceObject
                                    .getModuleCollection().values();
                            for (Module mod : moduleCollection) {
                                String moduleTypes = mod.getModuleInterface()
                                        .getType();
                                System.err.println(
                                        "Module type = " + moduleTypes);
                                if (moduleTypes.equals(moduleType.getType())) {
                                    setErrorMessage(
                                            "Module Type already available");
                                    validModuleType = false;
                                }
                            }
                        }
                    }
                }
                if (String
                        .valueOf(headNodeInetrfaceObject.getModuleAddressing())
                        .equals("POSITION")) {
                    if (!validModuleType) {
                        getErrorStyledText(
                                "The XDD/XDC file does not support the module type.");
                        return false;
                    }

                    // if (String
                    // .valueOf(headNodeInetrfaceObject.getModuleAddressing())
                    // .equals("POSITION")) {
                    if (moduleTypeText.getText()
                            .equalsIgnoreCase(moduleInterfaceType)) {

                    } else {
                        getErrorStyledText(
                                "The XDD/XDC file does not support the module type.");
                        return false;
                    }
                }
                // }

                getInfoStyledText(VALID_FILE_MESSAGE + getModuleName() + ".");
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
                getErrorStyledText(
                        getModuleName() + " - " + e.getCause().getMessage());
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

        grpConfigurationFile = new Group(headerFrame, SWT.NONE);
        GridData gd_grpConfigurationFile = new GridData(SWT.FILL, SWT.FILL,
                true, false, 1, 1);
        gd_grpConfigurationFile.widthHint = 558;
        grpConfigurationFile.setLayoutData(gd_grpConfigurationFile);
        grpConfigurationFile.setText(CONFIGURATION_FILE_LABEL);
        grpConfigurationFile.setLayout(new GridLayout(5, false));

        moduleTypeLabel = new Label(grpConfigurationFile, SWT.NONE);
        moduleTypeLabel.setBounds(71, 148, 69, 15);
        moduleTypeLabel.setText("Module Type");

        moduleTypeText = new Text(grpConfigurationFile,
                SWT.BORDER | SWT.READ_ONLY);
        moduleTypeText.setLayoutData(
                new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
        new Label(grpConfigurationFile, SWT.NONE);
        new Label(grpConfigurationFile, SWT.NONE);

        Label lblXddxdc = new Label(grpConfigurationFile, SWT.NONE);
        lblXddxdc.setText(NODE_CONFIGURATION_LABEL);

        nodeConfigurationPath = new Text(grpConfigurationFile, SWT.BORDER);
        nodeConfigurationPath.setLayoutData(
                new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1));

        nodeConfigurationPath.setToolTipText(nodeConfigurationPath.getText());
        nodeConfigurationPath
                .addModifyListener(nodeConfigurationPathModifyListener);

        nodeConfigurationPath
                .addModifyListener(nodeConfigurationPathModifyListener);

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

    public HeadNodeInterface getInterface() {
        IWizardPage previousPage = getPreviousPage();
        if (previousPage instanceof AddModuleWizardPage) {
            AddModuleWizardPage adModulePage = (AddModuleWizardPage) previousPage;
            return adModulePage.getheadNodeInterface();
        }
        return null;
    }

    /**
     * @return The path of MN XDC file.
     */
    public String getMnXdcPath() {
        PowerlinkRootNode rootNode = getNodeList();
        String mnXdcPath = rootNode.getMN().getAbsolutePathToXdc();
        return mnXdcPath;
    }

    public TModuleInterface getModuleInterface() {
        if (xddModel != null) {
            List<ISO15745Profile> profiles = xddModel.getISO15745Profile();
            for (ISO15745Profile profile : profiles) {
                ProfileBodyDataType profileBodyDatatype = profile
                        .getProfileBody();
                if (profileBodyDatatype instanceof ProfileBodyDevicePowerlinkModularChild) {
                    ProfileBodyDevicePowerlinkModularChild modChild = (ProfileBodyDevicePowerlinkModularChild) profileBodyDatatype;
                    TModuleInterface modInterface = modChild.getDeviceManager()
                            .getModuleManagement().getModuleInterface();

                    return modInterface;
                }
            }
        }
        return null;
    }

    /**
     * Node model values of previous wizard page.
     *
     * @return node model of previous page or null otherwise.
     */
    public Object getModuleModel() {
        IWizardPage previousPage = getPreviousPage();
        if (previousPage instanceof AddModuleWizardPage) {
            AddModuleWizardPage adModulePage = (AddModuleWizardPage) previousPage;
            return adModulePage.getModulemodel();
        }

        return null;
    }

    /**
     * Receive node name with ID.
     *
     * @return Name and ID of node.
     */
    public String getModuleName() {
        Object nodeobj = getModuleModel();
        if (nodeobj instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module moduleModel = (InterfaceList.Interface.Module) nodeobj;
            return moduleModel.getName();
        } else {
            System.err.println("Invalid node model.");
        }
        return null;
    }

    public ModuleTypeList getModuleTypeList() {
        if (getModuleInterface() != null) {
            ModuleTypeList moduletypeList = getModuleInterface()
                    .getModuleTypeList();
            return moduletypeList;
        } else {
            System.err.println("Module Type list is null");
            return null;
        }
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
     * Get the node list available from POWERLINK root node.
     *
     * @return The list of nodes
     */
    public PowerlinkRootNode getNodeList() {
        IViewPart viewPart = PlatformUI.getWorkbench()
                .getActiveWorkbenchWindow().getActivePage()
                .findView(IndustrialNetworkView.ID);
        if (viewPart instanceof IndustrialNetworkView) {
            IndustrialNetworkView industrialView = (IndustrialNetworkView) viewPart;
            return industrialView.getNodeList();
        }
        return null;
    }

    public ISO15745ProfileContainer getXddModel() {
        return xddModel;
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

        if (headNodeInetrfaceObject.getModuleCollection().size() == 0) {
            moduleTypeText.setText(getInterface().getInterfaceType());

        } else {
            Set<Integer> positionSet = headNodeInetrfaceObject
                    .getModuleCollection().keySet();
            for (Integer position1 : positionSet) {
                Module mod = headNodeInetrfaceObject.getModuleCollection()
                        .get(position1);
                System.err.println(
                        "Value Set . . " + mod.getModuleInterface().getType());
                moduleTypeText.setText(mod.getModuleType());

            }

        }

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

    public void resetChildModuleWizard() {

    }

    public void resetWizardPage() {
        moduleTypeLabel.setVisible(false);
        moduleTypeText.setVisible(false);
    }

}

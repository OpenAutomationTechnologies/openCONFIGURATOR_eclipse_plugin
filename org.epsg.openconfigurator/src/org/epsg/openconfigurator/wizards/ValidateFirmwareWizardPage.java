package org.epsg.openconfigurator.wizards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.TextViewer;
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
import org.epsg.openconfigurator.model.Module;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.util.PluginErrorDialogUtils;
import org.epsg.openconfigurator.util.XddMarshaller;
import org.epsg.openconfigurator.xmlbinding.firmware.Firmware;
import org.xml.sax.SAXException;

public class ValidateFirmwareWizardPage extends WizardPage {

    public static final String CONTROLLED_NODE_LABEL = "Controlled Node";
    public static final String REDUNDANT_MANAGING_NODE_LABEL = "Redundant Managing Node";
    public static final String DIALOG_DESCRIPTION = "Add firmware file to the node/module.";
    public static final String DIALOG_PAGE_LABEL = "POWERLINK firmware";
    private static final String FIRMWARE_FILE_LABEL = "Firmware File";
    private static final String DEFAULT_CONFIGURATION_LABEL = "Choose a firmware file";

    private static final String DIALOG_PAGE_NAME = "ValidateFirmwarewizardPage";
    private static final String BROWSE_CONFIGURATION_LABEL = "Browse...";
    private static final String IMPORT_FIRMWARE_FILE_DIALOG_LABEL = "Import firmware file for node/module.";
    private static final String ERROR_CHOOSE_VALID_FILE_MESSAGE = "Choose a valid firmware file.";
    private static final String ERROR_CHOOSE_VALID_PATH_MESSAGE = "Firmware file does not exist in the path: ";
    private static final String VALID_FILE_MESSAGE = "Firmware schema validation successful.";

    private static final String[] FIRMWARE_FILTER_EXTENSIONS = { "*.fw", "*" };

    private static final String[] CONFIGURATION_FILTER_NAMES_EXTENSIONS = {
            "Firmware files", "All files" };

    private static final String ERROR_XDD_PARAM_VALIDATION = "Firmware parameter validation failed compare to XDD values for file: ";
    private static final String ERROR_PARAM_VALIDATION_FAILED = "Firmware parameter validation failed";

    private static final int XDD_OBJECT_INDEX_TOCHECK = 0x1018;
    private static final short XDD_SUBOBJECT_INDEX_VENDORID = 1;
    private static final short XDD_SUBOBJECT_INDEX_PRODUCTCODE = 2;
    private static final short XDD_SUBOBJECT_INDEX_REVISIONNO = 3;

    /**
     * Control to display the node configuration path.
     */
    private Text firmwareConfigurationPath;

    /**
     * Control to display the xdd error message.
     */
    private StyledText errorinfo;

    /**
     * Browse XDD/XDC button.
     */
    private Button btnBrowse;

    private Group grpFirmwareFile;

    private Firmware firmwareModel;

    private Module module;

    private Object nodeOrModuleObj;

    private Node node;

    boolean retVal = false;

    // Node configuration path listener
    private ModifyListener nodeConfigurationPathModifyListener = new ModifyListener() {
        @Override
        public void modifyText(ModifyEvent e) {
            setErrorMessage(null);
            getInfoStyledText("");
            setPageComplete(true);
            if (!isNodeConfigurationValid(
                    firmwareConfigurationPath.getText())) {
                setErrorMessage(ERROR_CHOOSE_VALID_FILE_MESSAGE);
                setPageComplete(false);
            }

            firmwareConfigurationPath
                    .setToolTipText(firmwareConfigurationPath.getText());
            getWizard().getContainer().updateButtons();

        }
    };

    /**
     * Create the wizard and initializes the path of firmware.
     */
    public ValidateFirmwareWizardPage(Object obj) {
        super(DIALOG_PAGE_NAME);
        nodeOrModuleObj = obj;
        if (obj instanceof Node) {
            Node node = (Node) obj;
            this.node = node;
        } else if (obj instanceof Module) {
            Module module = (Module) obj;
            this.module = module;
        }

        setTitle(DIALOG_PAGE_LABEL);
        setDescription(DIALOG_DESCRIPTION);
    }

    /**
     * Checks the firmware file header attributes against XDD values.
     */
    private boolean CheckWithXddAttributes() {
        try {
            // Get the attributes from firmware file header
            boolean isFirmwareVendorIdEmpty = firmwareModel.getVen().isEmpty();
            long firmwareVen = 0;
            if (!isFirmwareVendorIdEmpty) {
                firmwareVen = Long.decode(firmwareModel.getVen());
            }
            long firmwareDev = firmwareModel.getDev();
            long firmwareVar = firmwareModel.getVar();

            if (nodeOrModuleObj instanceof Node) {
                // Get the XDD values for controlled node
                String xddVendorId = node.getObjectDictionary()
                        .getSubObject(XDD_OBJECT_INDEX_TOCHECK,
                                XDD_SUBOBJECT_INDEX_VENDORID)
                        .getActualDefaultValue();
                String xddProductCode = node.getObjectDictionary()
                        .getSubObject(XDD_OBJECT_INDEX_TOCHECK,
                                XDD_SUBOBJECT_INDEX_PRODUCTCODE)
                        .getActualDefaultValue();
                String xddRevisionNo = node.getObjectDictionary()
                        .getSubObject(XDD_OBJECT_INDEX_TOCHECK,
                                XDD_SUBOBJECT_INDEX_REVISIONNO)
                        .getActualDefaultValue();

                if ((!xddVendorId.isEmpty()) && (!xddProductCode.isEmpty())
                        && (!xddRevisionNo.isEmpty())) {
                    if (isFirmwareVendorIdEmpty) {
                        // vendor ID shall not be compared if firmware doesn't
                        // contain the variable 'Var'
                        if ((Long.decode(xddProductCode) == firmwareDev)
                                && (Long.decode(
                                        xddRevisionNo) == firmwareVar)) {
                            return true;
                        }
                    } else {
                        if ((Long.decode(xddVendorId) == firmwareVen)
                                && (Long.decode(xddProductCode) == firmwareDev)
                                && (Long.decode(
                                        xddRevisionNo) == firmwareVar)) {
                            return true;
                        }
                    }
                }
            } else if (nodeOrModuleObj instanceof Module) {
                // Get the XDD values for controlled node
                String xddVendorId = module.getVendorId();
                String xddProductCode = module.getProductId();

                if ((!xddVendorId.isEmpty()) && (!xddProductCode.isEmpty())) {
                    if (isFirmwareVendorIdEmpty) {
                        // vendor ID shall not be compared if firmware doesn't
                        // contain the variable 'Var'
                        if (Long.decode(xddProductCode) == firmwareDev) {
                            return true;
                        }
                    } else {
                        if ((Long.decode(xddVendorId) == firmwareVen) && (Long
                                .decode(xddProductCode) == firmwareDev)) {
                            return true;
                        }
                    }
                }
            } else {
                // Unknown node type
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * validates the path of xddFile.
     */
    public boolean checkXddModel() {
        try {
            getInfoStyledText("");
            File firmwareFile = new File(firmwareConfigurationPath.getText());

            boolean fileExists = firmwareFile.exists();
            // if file exists
            if (fileExists) {
                firmwareModel = XddMarshaller
                        .unmarshallFirmwareFile(firmwareFile);
                getInfoStyledText(VALID_FILE_MESSAGE);

                // Check node attribute with XDD
                if (!CheckWithXddAttributes()) {
                    setErrorMessage(ERROR_PARAM_VALIDATION_FAILED);
                    getErrorStyledText(ERROR_XDD_PARAM_VALIDATION
                            + firmwareConfigurationPath.getText());
                    return false;
                }

            } else {
                setErrorMessage(ERROR_CHOOSE_VALID_PATH_MESSAGE
                        + firmwareConfigurationPath.getText());
                // getErrorStyledText(ERROR_CHOOSE_VALID_PATH_MESSAGE
                // + firmwareConfigurationPath.getText());
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
                getErrorStyledText(" - " + e.getCause().getMessage());
            }
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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

        grpFirmwareFile = new Group(headerFrame, SWT.NONE);
        GridData gd_grpConfigurationFile = new GridData(SWT.FILL, SWT.FILL,
                true, false, 1, 1);
        gd_grpConfigurationFile.widthHint = 558;
        grpFirmwareFile.setLayoutData(gd_grpConfigurationFile);
        grpFirmwareFile.setText(FIRMWARE_FILE_LABEL);
        grpFirmwareFile.setLayout(new GridLayout(4, false));

        Label lblFile = new Label(grpFirmwareFile, SWT.NONE);

        formToolkit.adapt(lblFile, true, true);
        lblFile.setText("File: ");

        firmwareConfigurationPath = new Text(grpFirmwareFile, SWT.BORDER);
        firmwareConfigurationPath.setLayoutData(
                new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
        firmwareConfigurationPath
                .setToolTipText(firmwareConfigurationPath.getText());
        firmwareConfigurationPath
                .addModifyListener(nodeConfigurationPathModifyListener);
        firmwareConfigurationPath
                .addModifyListener(nodeConfigurationPathModifyListener);

        btnBrowse = new Button(grpFirmwareFile, SWT.NONE);
        btnBrowse.setLayoutData(
                new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
        btnBrowse.setText(BROWSE_CONFIGURATION_LABEL);

        btnBrowse.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {

                FileDialog fileDialog = new FileDialog(
                        getContainer().getShell(), SWT.OPEN);

                fileDialog.setText(IMPORT_FIRMWARE_FILE_DIALOG_LABEL);
                // Set filter on .XDD and .XDC files
                fileDialog.setFilterExtensions(FIRMWARE_FILTER_EXTENSIONS);
                // Put in a readable name for the filter
                fileDialog
                        .setFilterNames(CONFIGURATION_FILTER_NAMES_EXTENSIONS);
                // Open Dialog and save result of selection
                String selectedFile = fileDialog.open();

                if (selectedFile != null) {
                    firmwareConfigurationPath.setText(selectedFile.trim());
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

        // The value of nodeConfigurationValid is not true in all cases.
        boolean pageComplete = (super.isPageComplete());

        if (checkXddModel()) {
            pageComplete = true;
        } else {
            pageComplete = false;
        }

        return pageComplete;
    }

}

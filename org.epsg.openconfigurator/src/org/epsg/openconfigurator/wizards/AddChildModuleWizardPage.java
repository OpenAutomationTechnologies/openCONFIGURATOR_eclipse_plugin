package org.epsg.openconfigurator.wizards;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.epsg.openconfigurator.model.HeadNodeInterface;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.util.IPowerlinkConstants;
import org.epsg.openconfigurator.xmlbinding.projectfile.InterfaceList;
import org.epsg.openconfigurator.xmlbinding.projectfile.InterfaceList.Interface;
import org.epsg.openconfigurator.xmlbinding.projectfile.TCN;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745Profile;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745ProfileContainer;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDataType;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDevicePowerlinkModularChild;
import org.epsg.openconfigurator.xmlbinding.xdd.TModuleInterface;

public class AddChildModuleWizardPage extends WizardPage {

    private static final String DIALOG_PAGE_NAME = "AddCNModulewizardPage"; //$NON-NLS-1$
    public static final String DIALOG_TILE = "POWERLINK node module";
    public static final String DIALOG_DESCRIPTION = "Add a POWERLINK node module to the network.";

    private HeadNodeInterface interfaceObj;
    private Text moduleTypeText;
    private Spinner position;
    private Label positionlabel;
    private Label moduleTypeLabel;
    private Button enabled;
    private Label positionRange;
    private Label positionRangevalue;
    private Node node;

    protected AddChildModuleWizardPage(HeadNodeInterface selectedNodeObj) {

        super(DIALOG_PAGE_NAME);
        interfaceObj = selectedNodeObj;
        System.out.println("Constructor called........");
        node = selectedNodeObj.getNode();

        setTitle(DIALOG_TILE);
        setDescription(DIALOG_DESCRIPTION);
    }

    @Override
    public void createControl(Composite parent) {
        System.out.println("Create Part control called........");
        Composite container = new Composite(parent, SWT.NULL);

        setControl(container);
        if (interfaceObj.isUnUsedSlots()) {
            position = new Spinner(container, SWT.BORDER);
            position.setBounds(164, 63, 47, 22);
            position.setMaximum(interfaceObj.getMaxModules().intValue());
            // position.setSelection(getNewPosition());
            position.addModifyListener(new ModifyListener() {

                @Override
                public void modifyText(ModifyEvent e) {
                    getWizard().getContainer().updateButtons();
                    setErrorMessage(null);
                    setPageComplete(true);
                    if ((position.getSelection() > position.getMaximum())
                            || (position.getSelection() < position
                                    .getMinimum())) {
                        setErrorMessage("Enter a valid position");
                        setPageComplete(false);
                    }
                }

            });
        } else {
            position = new Spinner(container, SWT.BORDER | SWT.READ_ONLY);
            position.setBounds(164, 63, 47, 22);
            position.setMaximum(interfaceObj.getMaxModules().intValue());
            position.addModifyListener(new ModifyListener() {

                @Override
                public void modifyText(ModifyEvent e) {
                    getWizard().getContainer().updateButtons();
                    setErrorMessage(null);
                    setPageComplete(true);
                    if ((position.getSelection() > position.getMaximum())
                            || (position.getSelection() < position
                                    .getMinimum())) {
                        setErrorMessage("Enter a valid position");
                        setPageComplete(false);
                    }
                }

            });
        }

        positionlabel = new Label(container, SWT.NONE);
        positionlabel.setBounds(71, 66, 55, 15);
        positionlabel.setText("Position");

        moduleTypeLabel = new Label(container, SWT.NONE);
        moduleTypeLabel.setBounds(71, 148, 69, 15);
        moduleTypeLabel.setText("Module Type");

        moduleTypeText = new Text(container, SWT.BORDER | SWT.READ_ONLY);
        moduleTypeText.setBounds(164, 148, 108, 21);

        enabled = new Button(container, SWT.CHECK);
        enabled.setBounds(371, 150, 93, 16);
        enabled.setText("Enabled");
        enabled.setSelection(true);

        positionRange = new Label(container, SWT.NONE);
        positionRange.setBounds(364, 66, 86, 15);
        positionRange.setText("Max Modules :");

        positionRangevalue = new Label(container, SWT.NONE);
        positionRangevalue.setBounds(464, 66, 30, 15);
        positionRangevalue.setText(String.valueOf(position.getMaximum()));
    }

    public TModuleInterface getModuleInterface() {
        if (getXDDModel() != null) {
            List<ISO15745Profile> profiles = getXDDModel().getISO15745Profile();
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

    private List<Integer> getModulePositionList() {
        Object nodeModel = node.getNodeModel();
        List<InterfaceList.Interface> interfaceList = new ArrayList<InterfaceList.Interface>();
        List<Integer> positionList = new ArrayList<Integer>();
        if (nodeModel instanceof TCN) {

            TCN cn = (TCN) nodeModel;
            interfaceList.addAll(cn.getInterfaceList().getInterface());

            for (Interface interfaces : interfaceList) {
                List<InterfaceList.Interface.Module> moduleList = interfaces
                        .getModule();
                for (InterfaceList.Interface.Module module : moduleList) {
                    positionList.add(module.getPosition().intValue());
                }
            }
        }
        return positionList;
    }

    private int getNewPosition() {
        setErrorMessage(null);
        List<InterfaceList.Interface> interfaceList = new ArrayList<InterfaceList.Interface>();
        if (interfaceObj.getModuleCollection().size() == 0) {
            // errorField = true;
            System.err.println("Module Collection size .. "
                    + interfaceObj.getModuleCollection().size());
            setErrorMessage("ERROR_MODEL_NOT_AVAILABLE");
            // setPageComplete(false);
            return getModuleInterface().getMinPosition().intValue();
        } else {
            Object nodeModel = node.getNodeModel();
            if (nodeModel instanceof TCN) {
                System.err.println("Module Collection size .. "
                        + interfaceObj.getModuleCollection().size());
                TCN cn = (TCN) nodeModel;
                interfaceList.addAll(cn.getInterfaceList().getInterface());
            }

            return getNextValidModulePosition(interfaceList);
        }
    }

    private int getNextValidModulePosition(
            List<InterfaceList.Interface> interfacelist) {
        setErrorMessage(null);

        List<Integer> positionList = new ArrayList<Integer>();

        for (Interface interfaces : interfacelist) {
            List<InterfaceList.Interface.Module> moduleList = interfaces
                    .getModule();
            for (InterfaceList.Interface.Module module : moduleList) {
                positionList.add(module.getPosition().intValue());
            }
        }

        if (!isPositionAvailable(positionList, position.getSelection())) {
            return position.getSelection();
        }

        // errorField = true;
        String errorMessage = MessageFormat.format(
                "ERROR_MAXIMUM_NODE_ID_LIMIT_REACHED",
                IPowerlinkConstants.CN_MAX_NODE_ID);
        setErrorMessage(errorMessage);
        setPageComplete(false);
        return IPowerlinkConstants.INVALID_NODE_ID;
    }

    public int getPosition() {
        return Integer.valueOf(position.getText());
    }

    public ISO15745ProfileContainer getXDDModel() {
        IWizardPage previousPage = getPreviousPage();
        if (previousPage instanceof ValidateXddWizardPage) {
            ValidateXddWizardPage xddPage = (ValidateXddWizardPage) previousPage;
            return xddPage.getXddModel();
        }
        return null;
    }

    public boolean isEnabled() {
        return enabled.getSelection();
    }

    @Override
    public boolean isPageComplete() {
        boolean isMaxPositionValid = true;
        boolean isMinPositionValid = true;
        boolean valid = true;
        if (getModuleInterface().getMaxPosition() != null) {
            isMaxPositionValid = Integer
                    .valueOf(position.getText()) <= getModuleInterface()
                            .getMaxPosition().intValue();
        } else {
            isMaxPositionValid = Integer
                    .valueOf(position.getText()) <= interfaceObj.getMaxModules()
                            .intValue();
        }

        if (getModuleInterface().getMaxPosition() != null) {
            isMinPositionValid = Integer
                    .valueOf(position.getText()) >= getModuleInterface()
                            .getMinPosition().intValue();
        }

        moduleTypeText.setText(getModuleInterface().getType());

        position.setMinimum(getModuleInterface().getMinPosition().intValue());

        if (!isMaxPositionValid) {
            setErrorMessage(
                    "Module cannot be placed in the specified position");
            return false;
        } else {
            setErrorMessage(null);
        }

        if (!isMinPositionValid) {
            setErrorMessage(
                    "Module cannot be placed in the specified position");
            return false;
        } else {
            setErrorMessage(null);
        }

        if (!interfaceObj.isUnUsedSlots()) {
            setErrorMessage("Unused slots not available");
            return false;
        }

        List<Integer> positionList = getModulePositionList();

        for (int positions : positionList) {
            if (positions == position.getSelection()) {
                valid = false;
            }
        }

        if (!valid) {
            setErrorMessage("Selected position already available");
            return false;
        }

        boolean pageComplete = (super.isPageComplete());

        return pageComplete;

    }

    private boolean isPositionAvailable(List<Integer> positionList,
            int positionTobeChecked) {
        boolean positionAvailable = false;
        for (int position : positionList) {
            if (position == positionTobeChecked) {
                positionAvailable = true;
            }
        }
        return positionAvailable;
    }

    private boolean isValidPosition(int position) {
        System.err.println("valid position..." + position);
        if ((position >= getModuleInterface().getMinPosition().intValue())
                && (position <= getModuleInterface().getMaxPosition()
                        .intValue())) {
            System.err.println("valid position... inside loop...");
            return true;
        }
        return false;
    }

    public void resetChildModulePage() {
        // TODO:

    }
}

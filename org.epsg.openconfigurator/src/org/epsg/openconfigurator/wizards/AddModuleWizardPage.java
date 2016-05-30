package org.epsg.openconfigurator.wizards;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.epsg.openconfigurator.model.HeadNodeInterface;
import org.epsg.openconfigurator.model.Module;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.validation.NodeNameVerifyListener;
import org.epsg.openconfigurator.xmlbinding.projectfile.InterfaceList;
import org.epsg.openconfigurator.xmlbinding.projectfile.InterfaceList.Interface;
import org.epsg.openconfigurator.xmlbinding.projectfile.TCN;

public class AddModuleWizardPage extends WizardPage {

    private static final String DIALOG_PAGE_NAME = "AddCNModulewizardPage"; //$NON-NLS-1$
    public static final String DIALOG_TILE = "POWERLINK module";
    public static final String DIALOG_DESCRIPTION = "Add a POWERLINK module to {0}/{1}.";
    private static final String ERROR_INVALID_MODULE_NAME = "Enter a valid module name.";

    private Text text;
    private Text nodeIdText;
    private Text interfaceIdText;
    private Text moduleName;
    private Spinner position;
    private Label positionRange;

    private Object nodeModel = null;

    private Object moduleModel = null;

    private NodeNameVerifyListener moduleNameVerifyListener = new NodeNameVerifyListener();

    private HeadNodeInterface interfaceObj;

    private String nodeId;

    private Node node;

    protected AddModuleWizardPage(HeadNodeInterface selectedNodeObj) {

        super(DIALOG_PAGE_NAME);
        interfaceObj = selectedNodeObj;
        node = interfaceObj.getNode();
        setTitle(DIALOG_TILE);
        setDescription(MessageFormat.format(DIALOG_DESCRIPTION, node.getName(),
                interfaceObj.getInterfaceUId()));
    }

    @Override
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NULL);

        setControl(container);
        Label lblNodeType = new Label(container, SWT.NONE);
        lblNodeType.setBounds(48, 10, 73, 23);
        lblNodeType.setText("Node ID:");

        nodeIdText = new Text(container, SWT.BORDER | SWT.READ_ONLY);
        nodeIdText.setBounds(126, 7, 76, 21);
        nodeIdText.setText(getNodeId());

        Label lblInterfaceId = new Label(container, SWT.NONE);
        lblInterfaceId.setBounds(48, 49, 73, 15);
        lblInterfaceId.setText("Interface ID:");

        interfaceIdText = new Text(container, SWT.BORDER | SWT.READ_ONLY);
        interfaceIdText.setBounds(126, 46, 76, 21);
        interfaceIdText.setText(interfaceObj.getUniqueId());

        Label lblName = new Label(container, SWT.NONE);
        lblName.setBounds(48, 86, 55, 15);
        lblName.setText("Name:");

        moduleName = new Text(container, SWT.BORDER);
        moduleName.setBounds(126, 83, 160, 21);
        moduleName.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                setErrorMessage(null);
                setPageComplete(true);
                if (!isNodeNameValid(moduleName.getText())) {
                    setErrorMessage(ERROR_INVALID_MODULE_NAME);
                    setPageComplete(false);
                }
                getWizard().getContainer().updateButtons();
            }
        });
        moduleName.addVerifyListener(moduleNameVerifyListener);
        moduleName.setFocus();

    }

    public HeadNodeInterface getheadNodeInterface() {
        return interfaceObj;
    }

    public Object getModulemodel() {
        return moduleModel;
    }

    private List<String> getModuleNamelist() {
        List<String> nameList = new ArrayList<String>();
        if (interfaceObj.getModuleCollection().size() != 0) {
            Collection<Module> moduleList = interfaceObj.getModuleCollection()
                    .values();
            for (Module module : moduleList) {
                String moduleName = module.getModuleName();
                nameList.add(moduleName);
            }
        } else {
            nameList = null;
        }
        return nameList;
    }

    @Deprecated
    private List<String> getModuleNameList() {
        Object nodeModel = node.getNodeModel();
        List<InterfaceList.Interface> interfaceList = new ArrayList<InterfaceList.Interface>();
        List<String> nameList = new ArrayList<String>();
        if (nodeModel instanceof TCN) {

            TCN cn = (TCN) nodeModel;
            if (cn.getInterfaceList() != null) {
                interfaceList.addAll(cn.getInterfaceList().getInterface());
            } else {
                return null;
            }
        }
        for (Interface interfaces : interfaceList) {
            List<InterfaceList.Interface.Module> moduleList = interfaces
                    .getModule();
            for (InterfaceList.Interface.Module module : moduleList) {
                nameList.add(module.getName());
            }
        }
        return nameList;

    }

    public Node getNode() {
        return interfaceObj.getNode();
    }

    public String getNodeId() {
        return interfaceObj.getNode().getNodeIdString();
    }

    private boolean isNodeNameValid(final String moduleName) {
        boolean retval = false;

        if (moduleName == null) {
            return retval;
        }

        if (moduleName.length() == 0) {
            return retval;
        }

        // Space as first character is not allowed. ppc:tNonEmptyString
        if (moduleName.charAt(0) == ' ') {
            return retval;
        }

        if (moduleName.length() > 0) {
            retval = true;
        }

        return retval;
    }

    @Override
    public boolean isPageComplete() {
        boolean nameValid = isNodeNameValid(moduleName.getText());
        boolean valid = true;
        if (!nameValid) {
            setErrorMessage(ERROR_INVALID_MODULE_NAME);
            return false;
        }
        List<String> nameList = getModuleNamelist();
        if (nameList == null) {
            valid = true;
        } else {
            for (String name : nameList) {
                if (name.equals(moduleName.getText())) {
                    valid = false;
                }
            }
        }
        if (!valid) {
            setErrorMessage("Selected Name already available");
            return false;
        }

        boolean pageComplete = (super.isPageComplete());

        if (pageComplete) {
            updateCnModel();

            if ((moduleModel == null)) {
                pageComplete = false;
            }
        }
        // IWizardPage nextPage = getNextPage();
        // if (nextPage instanceof ValidateXddModuleWizardPage) {
        // ValidateXddModuleWizardPage validatePage =
        // (ValidateXddModuleWizardPage) nextPage;
        //
        // if (String.valueOf(interfaceObj.getModuleAddressing())
        // .equals("MANUAL")) {
        // System.err.println("Manual........");
        // validatePage.resetWizardPage();
        // }
        // }

        return pageComplete;

    }

    private void updateCnModel() {
        nodeModel = getNode().getNodeModel();
        if (nodeModel instanceof TCN) {
            TCN cnModel = (TCN) nodeModel;
            InterfaceList.Interface.Module cnModule = new InterfaceList.Interface.Module();
            cnModule.setName(moduleName.getText());
            // cnModule.setPosition(BigInteger.valueOf(position.getSelection()));
            moduleModel = cnModule;
        } else {
            System.err.println("Invalid node model : " + nodeModel);
        }

        if (moduleModel == null) {
            System.err.println("Invalid module model :" + moduleModel);
        }

    }
}

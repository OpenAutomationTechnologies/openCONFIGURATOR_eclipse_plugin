/*******************************************************************************
 * @file   AddModuleWizardPage.java
 *
 * @author Sree Hari Vignesh, Kalycito Infotech Private Limited.
 *
 * @copyright (c) 2016, Kalycito Infotech Private Limited
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

/**
 * Wizard page to add child module to the head node.
 *
 * @author SreeHari
 *
 */
public class AddModuleWizardPage extends WizardPage {

    // labels to describe the wizard page.
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
                if (!isModuleNameValid(moduleName.getText())) {
                    setErrorMessage(ERROR_INVALID_MODULE_NAME);
                    setPageComplete(false);
                }
                getWizard().getContainer().updateButtons();
            }
        });
        moduleName.addVerifyListener(moduleNameVerifyListener);
        moduleName.setFocus();

    }

    /**
     * @return Instance of HeadNodeInterface
     */
    public HeadNodeInterface getheadNodeInterface() {
        return interfaceObj;
    }

    /**
     * @return Instance of Module object model
     */
    public Object getModulemodel() {
        return moduleModel;
    }

    /**
     * @return Lists of available module name in the head node.
     */
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

    /**
     * @return Instance of Node
     */
    public Node getNode() {
        return interfaceObj.getNode();
    }

    /**
     * @return NodeId in which the module is connected.
     */
    public String getNodeId() {
        return interfaceObj.getNode().getNodeIdString();
    }

    /**
     * Verifies the module name to be added.
     *
     * @param moduleName The name of module to be verified.
     * @return <code>true</code> if module is valid, <code>false</code> if not
     *         valid.
     */
    private boolean isModuleNameValid(final String moduleName) {
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
        boolean nameValid = isModuleNameValid(moduleName.getText());
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
            setErrorMessage("Module Name already exixts.");
            return false;
        }

        boolean pageComplete = (super.isPageComplete());

        if (pageComplete) {
            updateCnModel();

            if ((moduleModel == null)) {
                pageComplete = false;
            }
        }

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

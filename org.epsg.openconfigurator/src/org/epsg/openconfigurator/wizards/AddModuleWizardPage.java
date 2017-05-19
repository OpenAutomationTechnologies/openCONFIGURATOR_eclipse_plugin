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
import java.util.Set;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.epsg.openconfigurator.model.HeadNodeInterface;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.validation.NodeNameVerifyListener;
import org.epsg.openconfigurator.xmlbinding.projectfile.InterfaceList;

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

    /**
     * Verifies the module name to be added.
     *
     * @param moduleName The name of module to be verified.
     * @return <code>true</code> if module is valid, <code>false</code> if not
     *         valid.
     */
    private static boolean isModuleNameValid(final String moduleName) {
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

    private Text nodeIdText;
    private Text interfaceIdText;

    private Text moduleName;

    private Object moduleModel = null;

    private NodeNameVerifyListener moduleNameVerifyListener = new NodeNameVerifyListener();

    private HeadNodeInterface interfaceObj;

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
        nodeIdText.setBounds(126, 10, 76, 21);
        nodeIdText.setText(getHeadNodeId());

        Label lblInterfaceId = new Label(container, SWT.NONE);
        lblInterfaceId.setBounds(48, 49, 73, 15);
        lblInterfaceId.setText("Interface ID:");

        interfaceIdText = new Text(container, SWT.BORDER | SWT.READ_ONLY);
        interfaceIdText.setBounds(126, 49, 76, 21);
        interfaceIdText.setText(interfaceObj.getInterfaceUniqueId());

        Label lblName = new Label(container, SWT.NONE);
        lblName.setBounds(48, 88, 55, 15);
        lblName.setText("Name:");

        moduleName = new Text(container, SWT.BORDER);
        moduleName.setBounds(126, 88, 160, 21);
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
     * @return NodeId in which the module is connected.
     */
    public String getHeadNodeId() {
        return interfaceObj.getNode().getNodeIdString();
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
    public Object getModulemodelinWizard() {
        return moduleModel;
    }

    /**
     * @return Instance of Node
     */
    public Node getNode() {
        return interfaceObj.getNode();
    }

    @Override
    public boolean isPageComplete() {
        boolean nameValid = isModuleNameValid(moduleName.getText());
        boolean valid = true;
        if (!nameValid) {
            setErrorMessage(ERROR_INVALID_MODULE_NAME);
            return false;
        }

        Set<String> moduleNameList = interfaceObj.getModuleNameCollection()
                .keySet();

        if (moduleNameList == null) {
            valid = true;
        } else {
            for (String name : moduleNameList) {
                if (name.equals(moduleName.getText())) {
                    valid = false;
                }
            }
        }

        if (!valid) {
            setErrorMessage("Module Name already exists.");
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
        Object nodeModel = getNode().getNodeModel();
        InterfaceList.Interface.Module cnModule = new InterfaceList.Interface.Module();
        cnModule.setName(moduleName.getText());
        moduleModel = cnModule;

    }
}

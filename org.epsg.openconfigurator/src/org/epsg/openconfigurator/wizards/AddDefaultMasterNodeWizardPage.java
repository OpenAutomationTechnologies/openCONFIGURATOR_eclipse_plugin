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

import javax.xml.bind.JAXBException;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Text;
import org.epsg.openconfigurator.util.IPowerlinkConstants;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectMarshaller;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectUtils;
import org.epsg.openconfigurator.xmlbinding.projectfile.OpenCONFIGURATORProject;
import org.epsg.openconfigurator.xmlbinding.projectfile.TMN;

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
    public static final String DEFAULT_MN_WIZARD_PAGE_DESCRIPTION = "Add a managing node configuration.";

    public static final String PROJECT_DIRECTORY_DEVICEIMPORT = "deviceImport";
    public static final String PROJECT_DIRECTORY_DEVICECONFIGURATION = "deviceConfiguration";

    public static final String DEFAULT_MN_WIZARDPAGE_CONFIGURATION_FILE_LABEL = "Configuration File";
    public static final String DEFAULT_MN_WIZARDPAGE_IMPORT_FILE_LABEL = "Import Master Node's XDD/XDC";
    public static final String DEFAULT_MN_WIZARDPAGE_BROWSE_LABEL = "Browse...";
    public static final String DEFAULT_MN_WIZARDPAGE_DEFAULT_LABEL = "Default";
    public static final String DEFAULT_MN_WIZARDPAGE_CUSTOM_LABEL = "Custom";

    public static final String DEFAULT_MN_WIZARDPAGE_CHOOSE_VALID_FILE_MESSAGE = "Choose a valid XDD/XDC file.";
    public static final String DEFAULT_MN_WIZARDPAGE_MN_PATH_TIP = "Path for the managing node device configuration file.\n"
            + "If you are new to POWERLINK use 'Default' MN XDD.";
    public static final String DEFAULT_MN_WIZARDPAGE_NODEID_TIP = "ID of the managing node.";
    public static final String DEFAULT_MN_WIZARDPAGE_NODEID_LABEL = "Node ID:";
    public static final String DEFAULT_MN_WIZARDPAGE_XDD_XDC_FILE_LABEL = "XDD/XDC file:";
    public static final String DEFAULT_MN_WIZARDPAGE_NAME_LABEL = "Name:";
    public static final String DEFAULT_MN_WIZARDPAGE_NAME_TIP = "Name of the managing node";
    public static final String DEFAULT_MN_WIZARDPAGE_NAME_MESSAGE = "Enter a valid node name.";

    /**
     * Default managing node name.
     */
    private final static String defaultMasterName = "openPOWERLINK_MN";

    /**
     * Default managing node ID.
     */
    private final static short defaultMnNodeId = IPowerlinkConstants.MN_DEFAULT_NODE_ID;

    /**
     * Checks for the valid name for the master node.
     *
     * @param mnName Name
     * @return true if valid, false otherwise.
     */
    private static boolean isMnNameValid(final String mnName) {
        boolean retval = false;
        if (mnName.length() == 0) {
            return retval;
        }

        // Space as first character is not allowed. ppc:tNonEmptyString
        if (mnName.charAt(0) == ' ') {
            return retval;
        }

        if (mnName.length() > 0) {
            retval = true;
        }

        return retval;
    }

    /**
     * Default managing node name.
     */
    private Text txtNodeName;

    /**
     * MN object model.
     */
    private TMN mn = new TMN();

    /**
     * Flag to monitor the page completion.
     */
    private boolean pageComplete = false;

    private Object nodeModel = null;

    /**
     * Create the Add default managing node wizard.
     */
    public AddDefaultMasterNodeWizardPage() {
        super(AddDefaultMasterNodeWizardPage.DEFAULT_MN_WIZARD_PAGE_NAME,
                AddDefaultMasterNodeWizardPage.DEFAULT_MN_WIZARD_PAGE_TITLE,
                null);
        setDescription(
                AddDefaultMasterNodeWizardPage.DEFAULT_MN_WIZARD_PAGE_DESCRIPTION);

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
        container.setLayout(new FormLayout());

        Composite composite = new Composite(container, SWT.NONE);
        FormData fd_composite = new FormData();
        fd_composite.top = new FormAttachment(0, 10);
        fd_composite.left = new FormAttachment(0, 22);
        fd_composite.right = new FormAttachment(0, 348);
        fd_composite.bottom = new FormAttachment(0, 167);
        composite.setLayoutData(fd_composite);

        Label lblName = new Label(composite, SWT.NONE);
        lblName.setBounds(10, 34, 55, 15);
        lblName.setText(
                AddDefaultMasterNodeWizardPage.DEFAULT_MN_WIZARDPAGE_NAME_LABEL);

        txtNodeName = new Text(composite, SWT.BORDER);
        txtNodeName.setBounds(92, 34, 198, 21);
        txtNodeName.setText(defaultMasterName);
        txtNodeName.setToolTipText(
                AddDefaultMasterNodeWizardPage.DEFAULT_MN_WIZARDPAGE_NAME_TIP);
        pageComplete = true;
        txtNodeName.addModifyListener(new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                if (isMnNameValid(getTxtNodeName())) {
                    setErrorMessage(null);
                    mn.setName(getTxtNodeName());
                    pageComplete = true;
                } else {
                    setErrorMessage(
                            AddDefaultMasterNodeWizardPage.DEFAULT_MN_WIZARDPAGE_NAME_MESSAGE);
                    pageComplete = false;
                }
                getWizard().getContainer().updateButtons();
            }
        });

        Label lblNewLabel = new Label(composite, SWT.NONE);
        lblNewLabel.setBounds(10, 103, 55, 15);
        lblNewLabel.setText("Node ID:");

        Spinner spinnerNodeId = new Spinner(composite, SWT.BORDER);
        spinnerNodeId.setBounds(92, 103, 159, 22);
        spinnerNodeId.setMaximum(255);
        spinnerNodeId.setSelection(defaultMnNodeId);
        spinnerNodeId.setEnabled(false);
        spinnerNodeId.setToolTipText(
                AddDefaultMasterNodeWizardPage.DEFAULT_MN_WIZARDPAGE_NODEID_TIP);

    }

    /**
     * Create the new openCONFIGURATOR project file with a MN in the given path.
     *
     * @param projectFilePath Path for the new project file.
     */
    void createProject(final String projectFilePath) {
        File openConfiguratorProjectFile = new File(projectFilePath);
        OpenCONFIGURATORProject project = OpenConfiguratorProjectUtils
                .newDefaultOpenCONFIGURATORProject(getMnNode());
        try {
            OpenConfiguratorProjectMarshaller.marshallOpenConfiguratorProject(
                    project, openConfiguratorProjectFile);
        } catch (JAXBException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * @return The MN instance
     */
    private TMN getMnNode() {
        return mn;
    }

    /**
     *
     * @return node model of TMN.
     */
    Object getNode() {
        mn.setNodeID(defaultMnNodeId);
        mn.setName(getTxtNodeName());
        nodeModel = mn;
        return nodeModel;
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
     * {@inheritDoc}
     */
    @Override
    public boolean isPageComplete() {
        boolean canPageComplete = false;
        setErrorMessage(null);

        boolean mnNameValid = isMnNameValid(getTxtNodeName());
        if (!mnNameValid) {
            setErrorMessage(
                    AddDefaultMasterNodeWizardPage.DEFAULT_MN_WIZARDPAGE_NAME_MESSAGE);
            return canPageComplete;
        }

        // The value of mnNameValid is not true for all cases.
        canPageComplete = (pageComplete && mnNameValid);

        return canPageComplete;
    }

}

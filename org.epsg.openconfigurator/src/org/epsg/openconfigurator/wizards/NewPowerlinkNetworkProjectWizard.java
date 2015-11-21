/*******************************************************************************
 * @file   NewPowerlinkNetworkProjectWizard.java
 *
 * @brief  The main wizard interface for creating a new POWERLINK network project.
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
import java.net.URI;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;
import org.epsg.openconfigurator.util.PluginErrorDialogUtils;

/**
 * Creates the new POWERLINK network project wizard.
 *
 * @see WizardNewProjectCreationPage
 * @see AddDefaultMasterNodeWizardPage
 *
 * @author Ramakrishnan P
 *
 */
public class NewPowerlinkNetworkProjectWizard extends Wizard
        implements INewWizard, IExecutableExtension {

    /**
     * Wizard labels and messages.
     */
    public static final String NEW_PROJECT_WIZARD_CREATION_PAGE_NAME = "POWERLINK network project wizard";
    public static final String NEW_PROJECT_WIZARD_CREATION_PAGE_TITLE = "POWERLINK network project";
    public static final String NEW_PROJECT_WIZARD_CREATION_PAGE_DESCRIPTION = "Create a new POWERLINK network project.";
    public static final String NEW_PROJECT_WIZARD_TITLE = "New POWERLINK network project";

    /**
     * New project creation wizard page.
     */
    private WizardNewProjectCreationPage newProjectCreationPage;

    /**
     * Wizard page to add default managing node.
     */
    private AddDefaultMasterNodeWizardPage addDefaultMasterPage;

    /**
     * Configuration element.
     */
    private IConfigurationElement _configurationElement;

    /**
     * Constructor.
     */
    public NewPowerlinkNetworkProjectWizard() {
        // Do nothing.
    }

    @Override
    public void addPages() {
        super.addPages();

        newProjectCreationPage = new WizardNewProjectCreationPage(
                NewPowerlinkNetworkProjectWizard.NEW_PROJECT_WIZARD_CREATION_PAGE_NAME);
        newProjectCreationPage.setTitle(
                NewPowerlinkNetworkProjectWizard.NEW_PROJECT_WIZARD_CREATION_PAGE_TITLE);
        newProjectCreationPage.setDescription(
                NewPowerlinkNetworkProjectWizard.NEW_PROJECT_WIZARD_CREATION_PAGE_DESCRIPTION);
        addPage(newProjectCreationPage);

        addDefaultMasterPage = new AddDefaultMasterNodeWizardPage();
        addDefaultMasterPage.setPreviousPage(newProjectCreationPage);
        addPage(addDefaultMasterPage);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.eclipse.jface.wizard.Wizard#canFinish()
     */
    @Override
    public boolean canFinish() {
        if (getContainer().getCurrentPage() == newProjectCreationPage) {
            return false;
        }
        return addDefaultMasterPage.isPageComplete();
    }

    /**
     * Creates the new POWERLINK network project file inside the path.
     */
    private void createNewProjectFile() {
        if (newProjectCreationPage.getLocationURI().getPath().isEmpty()) {
            return;
        }

        String projectPath = newProjectCreationPage.getLocationPath().toString()
                + File.separator + newProjectCreationPage.getProjectName();
        addDefaultMasterPage.copyXddToDeviceImportDir(projectPath);

        String projectFileName = newProjectCreationPage.getLocationURI()
                .getPath() + File.separator
                + newProjectCreationPage.getProjectName() + ".xml";
        addDefaultMasterPage.createProject(projectFileName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init(IWorkbench workbench, IStructuredSelection selection) {
        setWindowTitle(
                NewPowerlinkNetworkProjectWizard.NEW_PROJECT_WIZARD_TITLE);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.eclipse.jface.wizard.Wizard#performCancel()
     */
    @Override
    public boolean performCancel() {
        return super.performCancel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean performFinish() {

        // get a project descriptor
        URI location = null;
        if (!newProjectCreationPage.useDefaults()) {
            location = newProjectCreationPage.getLocationURI();
        }

        String name = newProjectCreationPage.getProjectName();

        // get a project handle
        try {
            IProject newProjectHandle = PowerlinkNetworkProjectSupport
                    .createProject(name, location);

            createNewProjectFile();

            if (newProjectHandle != null) {
                newProjectHandle.refreshLocal(IResource.DEPTH_INFINITE,
                        new NullProgressMonitor());
            }

            BasicNewProjectResourceWizard
                    .updatePerspective(_configurationElement);
            BasicNewResourceWizard.selectAndReveal(newProjectHandle,
                    PlatformUI.getWorkbench().getActiveWorkbenchWindow());
        } catch (CoreException ex) {
            ex.printStackTrace();
            PluginErrorDialogUtils.displayErrorMessageDialog(getShell(),
                    "Failed to create a new project.", ex);
            getContainer().showPage(newProjectCreationPage);
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInitializationData(IConfigurationElement config,
            String propertyName, Object data) throws CoreException {
        _configurationElement = config;
    }

}

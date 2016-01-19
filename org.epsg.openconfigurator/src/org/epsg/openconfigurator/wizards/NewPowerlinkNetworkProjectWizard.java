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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;
import org.epsg.openconfigurator.model.IPowerlinkProjectSupport;
import org.epsg.openconfigurator.util.PluginErrorDialogUtils;
import org.epsg.openconfigurator.util.XddMarshaller;
import org.epsg.openconfigurator.xmlbinding.projectfile.TMN;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745ProfileContainer;
import org.xml.sax.SAXException;

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
    private static final String ERROR_WHILE_CREATING_PROJECT = "Failed to create a new project.";
    private static final String ERROR_WHILE_COPYING_XDD = "Error occurred while copying the XDD files.";

    /**
     * New project creation wizard page.
     */
    private WizardNewProjectCreationPage newProjectCreationPage;

    /**
     * Wizard page to add default managing node.
     */
    private AddDefaultMasterNodeWizardPage addDefaultMasterPage;

    /**
     * Add validateXddWizardPage
     */
    private final ValidateXddWizardPage validateXddPage;

    /**
     * Configuration element.
     */
    private IConfigurationElement _configurationElement;

    /**
     * Constructor.
     */
    public NewPowerlinkNetworkProjectWizard() {
        validateXddPage = new ValidateXddWizardPage();
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
        validateXddPage.setPreviousPage(addDefaultMasterPage);
        addPage(validateXddPage);
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
        if (getContainer().getCurrentPage() == addDefaultMasterPage) {
            return false;
        }
        return validateXddPage.isPageComplete();
    }

    /**
     * Import the device description and device configuration files into the
     * respective directories into the project.
     *
     * @param projectPath Path of the project
     */
    void copyXddToDeviceImportDir(final String projectPath) {
        try {

            java.nio.file.Path mnXdd = validateXddPage
                    .getNodeConfigurationPath();

            Path sourcePath = Paths.get(mnXdd.toString());

            System.out.println("MN XDD path" + sourcePath);

            String targetImportPath = projectPath + IPath.SEPARATOR
                    + AddDefaultMasterNodeWizardPage.PROJECT_DIRECTORY_DEVICEIMPORT
                    + IPath.SEPARATOR + mnXdd.getFileName().toString();

            Path targetPath = Paths.get(targetImportPath);

            System.err.println("Target path == " + targetPath);

            // Copy the MN XDD to deviceImport dir

            Files.copy(sourcePath, targetPath,
                    StandardCopyOption.REPLACE_EXISTING);

            // Rename the XDD to XDC and copy the deviceImport MN XDD to
            // deviceConfiguration dir
            String extensionXdd = mnXdd.getFileName().toString();
            int pos = extensionXdd.lastIndexOf(".");
            if (pos > 0) {
                extensionXdd = extensionXdd.substring(0, pos);
            }

            String targetConfigurationPath = new String(
                    projectPath + IPath.SEPARATOR
                            + AddDefaultMasterNodeWizardPage.PROJECT_DIRECTORY_DEVICECONFIGURATION
                            + IPath.SEPARATOR + extensionXdd
                            + IPowerlinkProjectSupport.XDC_EXTENSION);

            java.nio.file.Files.copy(
                    new java.io.File(targetImportPath).toPath(),
                    new java.io.File(targetConfigurationPath).toPath(),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING,
                    java.nio.file.StandardCopyOption.COPY_ATTRIBUTES,
                    java.nio.file.LinkOption.NOFOLLOW_LINKS);

            java.nio.file.Path pathBase = Paths.get(projectPath);

            // Set the relative path to the MN object
            java.nio.file.Path pathRelative = pathBase
                    .relativize(Paths.get(targetConfigurationPath));

            String relativePath = pathRelative.toString();
            relativePath = relativePath.replace('\\', '/');

            Object nodemodel = addDefaultMasterPage.getNode();
            ((TMN) nodemodel).setPathToXDC(relativePath);

        } catch (UnsupportedOperationException | SecurityException
                | IOException e) {
            e.printStackTrace();
            PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                    ERROR_WHILE_COPYING_XDD);
        }
    }

    /**
     * Creates the new POWERLINK network project file inside the path.
     */
    private void createNewProjectFile() {
        if (newProjectCreationPage.getLocationURI().getPath().isEmpty()) {
            return;
        }
        // Check if the location of project is default or custom path
        if (newProjectCreationPage.useDefaults()) {

            String projectPath = newProjectCreationPage.getLocationPath()
                    + File.separator + newProjectCreationPage.getProjectName();

            copyXddToDeviceImportDir(projectPath);

            String projectFileName = newProjectCreationPage.getLocationURI()
                    .getPath() + File.separator
                    + newProjectCreationPage.getProjectName() + ".xml";
            addDefaultMasterPage.createProject(projectFileName);

        } else {

            String projectURI = newProjectCreationPage.getLocationURI()
                    .getPath() + File.separator
                    + newProjectCreationPage.getProjectName();
            String projectPath = projectURI.substring(1);
            System.err.println("Project path " + projectPath);

            copyXddToDeviceImportDir(projectPath);

            String projectFileName = newProjectCreationPage.getLocationURI()
                    .getPath() + File.separator
                    + newProjectCreationPage.getProjectName() + File.separator
                    + newProjectCreationPage.getProjectName() + ".xml";
            addDefaultMasterPage.createProject(projectFileName);

        }
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
            String projectURI = newProjectCreationPage.getLocationURI()
                    .getPath() + File.separator
                    + newProjectCreationPage.getProjectName();
            String projectPath = projectURI.substring(1);
            Path project = Paths.get(projectPath);
            location = project.toUri();
        }

        String name = newProjectCreationPage.getProjectName();

        Object nodeObject = addDefaultMasterPage.getNode();
        Path xdcPath = validateXddPage.getNodeConfigurationPath();
        if (nodeObject instanceof TMN) {
            TMN mnModel = (TMN) nodeObject;
            mnModel.setPathToXDC(xdcPath.toString());
        } else {
            validateXddPage.getErrorStyledText("Invalid node model");
            System.err.println("Invalid node model");
        }

        ISO15745ProfileContainer xddModel = null;
        try {
            xddModel = XddMarshaller.unmarshallXDDFile(xdcPath.toFile());
            validateXddPage.getErrorStyledText("");
        } catch (FileNotFoundException | UnsupportedEncodingException
                | JAXBException | SAXException
                | ParserConfigurationException e2) {
            validateXddPage.getErrorStyledText(e2.getCause().getMessage());
            PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                    e2.getCause().getMessage());
            e2.printStackTrace();
            return false;
        }
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
            PluginErrorDialogUtils.displayErrorMessageDialog(
                    ERROR_WHILE_CREATING_PROJECT, ex);
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

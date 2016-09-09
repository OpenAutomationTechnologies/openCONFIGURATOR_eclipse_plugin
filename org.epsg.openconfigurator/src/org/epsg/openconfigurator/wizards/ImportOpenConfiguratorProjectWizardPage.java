/*******************************************************************************
 * @file   ImportOpenConfiguratorProjectWizardPage.java
 *
 * @brief  Wizard page to import POWERLINK network projects into the workspace.
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
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FilenameUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubProgressMonitor;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.PixelConverter;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.IOverwriteQuery;
import org.eclipse.ui.wizards.datatransfer.FileSystemStructureProvider;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;
import org.epsg.openconfigurator.Activator;
import org.epsg.openconfigurator.builder.PowerlinkNetworkProjectNature;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectMarshaller;
import org.epsg.openconfigurator.xmlbinding.projectfile.OpenCONFIGURATORProject;
import org.xml.sax.SAXException;

/**
 * An wizard page to Import openCONFIGURATOR Projects into the workspace.
 *
 * @author Ramakrishnan P
 *
 */
public final class ImportOpenConfiguratorProjectWizardPage extends WizardPage {

    /**
     * A view filter to select/de-select the items in the list.
     *
     * @author Ramakrishnan P
     *
     */
    // It is not required to be a static inner class.
    private final class ConflictingProjectFilter extends ViewerFilter {

        @Override
        public boolean select(Viewer viewer, Object parentElement,
                Object element) {
            if (element instanceof ProjectRecord) {
                ProjectRecord project = (ProjectRecord) element;
                // Select only valid openCONFIGURATOR projects and project
                // without conflicts.
                return (project.isValidOpenConfiguratorProject()
                        && !project.hasConflicts());
            }
            return false;
        }
    }

    /**
     * A label provider to text and image for the items in the list.
     *
     * @author Ramakrishnan P
     *
     */
    private final class ProjectLabelProvider extends LabelProvider
            implements IColorProvider {

        @Override
        public Color getBackground(Object element) {
            return null;
        }

        @Override
        public Color getForeground(Object element) {
            if (element instanceof ProjectRecord) {
                ProjectRecord projectRecord = (ProjectRecord) element;

                if (projectRecord.hasConflicts()
                        || !projectRecord.isValidOpenConfiguratorProject()) {
                    return getShell().getDisplay()
                            .getSystemColor(SWT.COLOR_GRAY);
                }
            }

            return null;
        }

        @Override
        public Image getImage(Object element) {
            if (element instanceof ProjectRecord) {
                ProjectRecord projectRecord = (ProjectRecord) element;
                if (projectRecord.hasConflicts()) {
                    return PlatformUI.getWorkbench().getSharedImages()
                            .getImage(ISharedImages.IMG_DEC_FIELD_WARNING);
                }
                if (!projectRecord.isValidOpenConfiguratorProject()) {
                    return PlatformUI.getWorkbench().getSharedImages()
                            .getImage(ISharedImages.IMG_DEC_FIELD_ERROR);
                }
            }
            return null;
        }

        @Override
        public String getText(Object element) {

            return ((ProjectRecord) element).getProjectLabel();
        }
    }

    /**
     * A class to hold the additional informations about the Projects to be
     * imported.
     *
     * @author Ramakrishnan P
     *
     */
    private final class ProjectRecord {

        /**
         * The project file.
         */
        // It is not required to be a static inner class.
        private File projectSystemFile;

        /**
         * Name of the project.
         */
        private String projectName = "";

        /**
         * Project version from the openCONFIGURATOR project.
         */
        private String projectVersion = "";

        /**
         * Flag to denote that the project has conflicts or not.
         */
        private boolean hasConflicts = false;

        /**
         * The project description.
         */
        private IProjectDescription description;

        /**
         * Flag to denote that the project is schema validated.
         */
        private boolean validProject = false;

        public ProjectRecord(File file) {
            projectSystemFile = file;
            updateProjectName();
            updateProjectFlags();
        }

        /**
         * @return Returns the string in the format ProjectName(Version).
         */
        public String getProjectLabel() {
            // TODO : May be add project folder path for more easy
            // identification.
            return (projectName + " (" + projectVersion + ")");
        }

        /**
         * @return Returns the project name.
         */
        public String getProjectName() {
            return projectName;
        }

        /**
         * @return Returns true if the project record has conflicts with other
         *         project records, false otherwise.
         */
        public boolean hasConflicts() {
            return hasConflicts;
        }

        private boolean isProjectFileValid() {
            boolean retVal = false;
            try {
                OpenCONFIGURATORProject oc = OpenConfiguratorProjectMarshaller
                        .unmarshallOpenConfiguratorProject(projectSystemFile);
                projectVersion = oc.getGenerator().getToolVersion();

                retVal = true;
            } catch (FileNotFoundException | MalformedURLException
                    | JAXBException | SAXException
                    | ParserConfigurationException e) {
                e.printStackTrace();
                // Do nothing. We only need to detect it is valid or not.
            }

            return retVal;
        }

        /**
         * @return Returns true if the file is a valid openCONFIGURATOR project,
         *         false otherwise.
         */
        public boolean isValidOpenConfiguratorProject() {
            return validProject;
        }

        private void updateProjectFlags() {
            validProject = isProjectFileValid();
            // TODO: May be check all the XDD and XDC available in the project.
        }

        private void updateProjectName() {
            projectName = FilenameUtils
                    .removeExtension(projectSystemFile.getName());
        }
    }

    private static String previouslyBrowsedDirectory = ""; //$NON-NLS-1$

    private static final String IMPORT_PROJECT_WIZARD_PAGE_NAME = "Import openCONFIGURATOR project";

    private static final String IMPORT_PROJECT_WIZARD_PAGE_TITLE = "Import openCONFIGURATOR project";
    private static final String IMPORT_PROJECT_WIZARD_PAGE_DESCRIPTION = "Select a directory to search for existing projects.\n(Version 1.4.0 or above required)";

    private static final String OPTIONS_GROUP_LABEL = "Options";
    private static final String NESTED_PROJECTS_LABEL = "Search for nested projects";
    private static final String COPY_PROJECTS_LABEL = "Copy projects into workspace";
    private static final String HIDE_PROJECTS_LABEL = "Hide projects that already exists in the workspace";

    private static final String SELECT_ROOT_DIRECTORY_LABEL = "Select root directory:";
    private static final String BROWSE_LABEL = "Browse...";
    private static final String PROJECT_LABEL = "Projects:";
    private static final String SELECT_ALL_LABEL = "Select all";
    private static final String DE_SELECT_ALL_LABEL = "Deselect all";
    private static final String REFRESH_LABEL = "Refresh";
    private static final String IMPORT_OPENCONFIGURATOR_LABEL = "Import existing openCONFIGURATOR projects";
    private static final String PROJECT_CREATION_ERRORS_LABEL = "Error occurred while importing the projects";

    private static final String XML_EXTENSION_LABEL = ".xml"; //$NON-NLS-1$
    private static final String XAP_XML_LABEL = "xap.xml"; //$NON-NLS-1$

    private static final String CREATE_PROJECTS_TASK_LABEL = "Create projects";
    private static final String SEARCH_PROJECTS_TASK_LABEL = "Searching for projects";
    private static final String SEARCH_PROJECTS_IN_TASK_LABEL = "Searching for projects in ";
    private static final String PROCESSING_RESULTS_TASK_LABEL = "Processing results";
    private static final String PROJECTS_EXISTS_WORKSPACE_MESSAGE = "Some projects cannot be imported because they already exist in the workspace.";
    private static final String NO_PROJECTS_FOUND_MESSAGE = "No projects are found to import.";

    /**
     * Searches for the project file with the extension .xml and returns
     *
     * @param directory Location to search for the project file.
     *
     * @return The project file, null otherwise.
     */
    private static File getProjectFile(final File directory) {

        File[] files = directory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return (name.toLowerCase().endsWith(
                        ImportOpenConfiguratorProjectWizardPage.XML_EXTENSION_LABEL)
                        && !name.equalsIgnoreCase(
                                ImportOpenConfiguratorProjectWizardPage.XAP_XML_LABEL));
            }
        });

        if ((files != null) && (files.length > 0)) {
            return files[0];
        }

        return null;
    }

    /**
     * Check if the project folder is already in workspace or not.
     *
     * @param projectName Project name
     * @return True if present, false otherwise.
     */
    private static boolean isProjectFolderInWorkspacePath(
            final String projectName) {
        final IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IPath wsPath = workspace.getRoot().getLocation();
        IPath localProjectPath = wsPath.append(projectName);
        return localProjectPath.toFile().exists();
    }

    /**
     * Check if the project is already in workspace or not.
     *
     * @param projectName Project name
     * @return True if present, false otherwise.
     */
    private static boolean isProjectInWorkspace(final String projectName) {
        if (projectName == null) {
            return false;
        }
        IProject[] workspaceProjects = ResourcesPlugin.getWorkspace().getRoot()
                .getProjects();
        for (IProject workspaceProject : workspaceProjects) {
            if (projectName.equals(workspaceProject.getName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Overwrite query to get the input from the user whether to overwrite or
     * not.
     */
    private IOverwriteQuery overwriteQuery = new IOverwriteQuery() {

        @Override
        public String queryOverwrite(String pathString) {
            Path path = new Path(pathString);

            String messageString;
            // Break the message up if there is a file name and a directory
            // and there are at least 2 segments.
            if ((path.getFileExtension() == null)
                    || (path.segmentCount() < 2)) {
                messageString = "'" + pathString
                        + "' already exists. Would you like to overwrite it?";
            } else {
                messageString = "Overwrite " + path.lastSegment() + " in folder"
                        + path.removeLastSegments(1).toOSString() + "?";
            }

            final MessageDialog dialog = new MessageDialog(
                    getContainer().getShell(), "Question", null, messageString,
                    MessageDialog.QUESTION,
                    new String[] { IDialogConstants.YES_LABEL,
                            IDialogConstants.YES_TO_ALL_LABEL,
                            IDialogConstants.NO_LABEL,
                            IDialogConstants.NO_TO_ALL_LABEL,
                            IDialogConstants.CANCEL_LABEL },
                    0) {
                @Override
                protected int getShellStyle() {
                    return super.getShellStyle() | SWT.SHEET;
                }
            };

            String[] response = new String[] { IOverwriteQuery.YES,
                    IOverwriteQuery.ALL, IOverwriteQuery.NO,
                    IOverwriteQuery.NO_ALL, IOverwriteQuery.CANCEL };
            // run in syncExec because callback is from an operation,
            // which is probably not running in the UI thread.
            getControl().getDisplay().syncExec(new Runnable() {
                @Override
                public void run() {
                    dialog.open();
                }
            });
            return dialog.getReturnCode() < 0 ? IOverwriteQuery.CANCEL
                    : response[dialog.getReturnCode()];
        }

    };

    /**
     * Check state listener to handle the check events of the project list
     * viewer.
     */
    private ICheckStateListener projectListCheckStateListener = new ICheckStateListener() {

        @Override
        public void checkStateChanged(CheckStateChangedEvent event) {
            Object element = event.getElement();
            if (element instanceof ProjectRecord) {
                ProjectRecord projectFile = (ProjectRecord) event.getElement();
                if (projectFile.hasConflicts()
                        || !projectFile.isValidOpenConfiguratorProject()) {
                    projectsListCheckBoxTreeViewer.setChecked(projectFile,
                            false);
                }
            }

            ImportOpenConfiguratorProjectWizardPage.this
                    .setPageComplete(projectsListCheckBoxTreeViewer
                            .getCheckedElements().length > 0);
        }
    };

    /**
     * Flag to allow/dis-allow copy the files into workspace.
     */
    private boolean copyFiles = true;

    private boolean lastCopyFiles = false;

    /**
     * The last selected path to minimize searches
     */
    private String lastPath;

    /**
     * The last time that the file or folder at the selected path was modified
     * to minimize searches
     */
    private long lastModified;

    /**
     * List of project records.
     */
    private List<ProjectRecord> selectedProjects = new ArrayList<>();

    /**
     * List projects from directory radio button.
     */
    private Button projectFromDirectoryRadio;
    /**
     * Directory text box.
     */
    private Text directoryPathField;

    /**
     * Checkbox based tree viewer to list all the available projects.
     */
    private CheckboxTreeViewer projectsListCheckBoxTreeViewer;

    /**
     * Search nested projects.
     */
    private boolean nestedProjects = false;

    private boolean lastNestedProjects = false;

    /**
     * A conflicting project filter to disallow project naming conflicts.
     */
    private ConflictingProjectFilter conflictingProjectsFilter = new ConflictingProjectFilter();

    /**
     * Create the import openCONFIGURATOR project wizard.
     */
    public ImportOpenConfiguratorProjectWizardPage() {
        super(ImportOpenConfiguratorProjectWizardPage.IMPORT_PROJECT_WIZARD_PAGE_NAME);
        setTitle(
                ImportOpenConfiguratorProjectWizardPage.IMPORT_PROJECT_WIZARD_PAGE_TITLE);
        setDescription(
                ImportOpenConfiguratorProjectWizardPage.IMPORT_PROJECT_WIZARD_PAGE_DESCRIPTION);
        setPageComplete(false);
    }

    /**
     * Collect the project files from the directory.
     *
     * @param[out] files List of project files found
     * @param directory Location to search
     *
     * @return true if it has project files inside the collection, false if the
     *         either the operation has cancelled or the directory has no
     *         project files.
     */
    private boolean collectProjectFilesFromDirectory(Collection<File> files,
            final File directory) {

        File[] contents = directory.listFiles();
        if (contents == null) {
            return false;
        }

        File file = getProjectFile(directory);
        if ((file != null) && file.isFile()) {
            files.add(file);
        }

        if (nestedProjects) {
            String[] subDirNameList = directory.list(new FilenameFilter() {
                @Override
                public boolean accept(File current, String name) {
                    return new File(current, name).isDirectory();
                }
            });
            if (subDirNameList != null) {
                for (String subDir : subDirNameList) {
                    File subDirProjetfile = getProjectFile(new File(
                            directory.getPath() + File.separator + subDir));
                    if (subDirProjetfile != null) {
                        files.add(subDirProjetfile);
                    }
                }
            }
        }

        return !files.isEmpty();
    }

    /**
     * Create contents of the wizard.
     *
     * @param parent Parent control.
     */
    @Override
    public void createControl(Composite parent) {

        initializeDialogUnits(parent);

        Composite container = new Composite(parent, SWT.NONE);

        setControl(container);
        container.setLayout(new GridLayout());
        container.setLayoutData(new GridData(GridData.FILL_BOTH
                | GridData.GRAB_HORIZONTAL | GridData.GRAB_VERTICAL));

        createProjectRoot(container);
        createProjectsList(container);
        createOptionsGroup(container);
    }

    /**
     * Create options group controls
     *
     * @param parent Parent
     */
    private void createOptionsGroup(final Composite parent) {
        Group optionsGroup = new Group(parent, SWT.NONE);

        GridLayout layout = new GridLayout();

        optionsGroup.setLayout(layout);
        optionsGroup.setLayoutData(new GridData(
                GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL));

        optionsGroup.setText(
                ImportOpenConfiguratorProjectWizardPage.OPTIONS_GROUP_LABEL);

        optionsGroup.setFont(parent.getFont());
        createOptionsGroupButtons(optionsGroup);
    }

    /**
     * Create nested, copy, hide conflicting projects check box buttons in the
     * options group.
     *
     * @param optionsGroup Options group.
     */
    private void createOptionsGroupButtons(final Group optionsGroup) {
        final Button nestedProjectsCheckbox = new Button(optionsGroup,
                SWT.CHECK);
        nestedProjectsCheckbox.setText(
                ImportOpenConfiguratorProjectWizardPage.NESTED_PROJECTS_LABEL);

        nestedProjectsCheckbox
                .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        nestedProjectsCheckbox.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {

                nestedProjects = nestedProjectsCheckbox.getSelection();

                if (projectFromDirectoryRadio.getSelection()) {

                    ImportOpenConfiguratorProjectWizardPage.this
                            .updateProjectsList(
                                    directoryPathField.getText().trim());
                }
            }
        });

        final Button copyCheckbox = new Button(optionsGroup, SWT.CHECK);
        copyCheckbox.setText(
                ImportOpenConfiguratorProjectWizardPage.COPY_PROJECTS_LABEL);
        copyCheckbox.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        copyCheckbox.setSelection(copyFiles);
        copyCheckbox.setEnabled(false);
        copyCheckbox.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {

                copyFiles = copyCheckbox.getSelection();
                // need to refresh the project list as projects already
                // in the workspace directory are treated as conflicts
                // and should be hidden too

                projectsListCheckBoxTreeViewer.refresh(true);

            }
        });

        final Button hideConflictingProjects = new Button(optionsGroup,
                SWT.CHECK);
        hideConflictingProjects.setText(
                ImportOpenConfiguratorProjectWizardPage.HIDE_PROJECTS_LABEL);
        hideConflictingProjects
                .setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        hideConflictingProjects.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                projectsListCheckBoxTreeViewer
                        .removeFilter(conflictingProjectsFilter);
                if (hideConflictingProjects.getSelection()) {
                    projectsListCheckBoxTreeViewer
                            .addFilter(conflictingProjectsFilter);
                }

            }
        });
        Dialog.applyDialogFont(hideConflictingProjects);

    }

    /**
     * Create the eclipse based project description for the openCONFIGURATOR
     * projects into the workspace.
     *
     * @param selectedProjectRecord The project record.
     * @param monitor The progress monitor instance
     *
     * @throws InvocationTargetException
     * @throws InterruptedException
     * @throws IOException
     */
    private void createProjectDescription(ProjectRecord selectedProjectRecord,
            IProgressMonitor monitor) throws InvocationTargetException,
            InterruptedException, IOException {

        String projectName = FilenameUtils
                .removeExtension(selectedProjectRecord.getProjectName());
        final IWorkspace workspace = ResourcesPlugin.getWorkspace();

        final IProject project = workspace.getRoot().getProject(projectName);

        if (selectedProjectRecord.description == null) {
            selectedProjectRecord.description = workspace
                    .newProjectDescription(projectName);
            IPath locationPath = new Path(
                    selectedProjectRecord.projectSystemFile.getAbsolutePath());

            // If it is under the root use the default location
            if (Platform.getLocation().isPrefixOf(locationPath)) {
                selectedProjectRecord.description.setLocation(null);
            } else {
                selectedProjectRecord.description.setLocation(locationPath);
            }
        } else {
            selectedProjectRecord.description.setName(projectName);
        }

        // import from file system
        File importSource = null;

        if (copyFiles) {
            // import project from location copying files - use default project
            // location for this
            // workspace
            URI locationURI = selectedProjectRecord.description
                    .getLocationURI();

            // if location is null, project already exists in this location or
            // some error occurred.
            if (locationURI != null) {
                // validate the location of the project being copied
                IStatus result = ResourcesPlugin.getWorkspace()
                        .validateProjectLocationURI(project, locationURI);

                if (!result.isOK()) {
                    throw new InvocationTargetException(
                            new CoreException(result));
                }

                importSource = new File(locationURI);

                IProjectDescription description = workspace
                        .newProjectDescription(projectName);
                PowerlinkNetworkProjectSupport.addNatureId(description);
                PowerlinkNetworkProjectNature.addBuildDescription(description);
                selectedProjectRecord.description = description;
            }
        }

        try {
            monitor.beginTask(
                    ImportOpenConfiguratorProjectWizardPage.CREATE_PROJECTS_TASK_LABEL,
                    100);
            project.create(selectedProjectRecord.description,
                    new SubProgressMonitor(monitor, 30));
            project.open(IResource.BACKGROUND_REFRESH,
                    new SubProgressMonitor(monitor, 70));

        } catch (CoreException e) {
            throw new InvocationTargetException(e);
        } finally {
            monitor.done();
        }

        // import operation to import project files if copy checkbox is selected
        if (copyFiles && (importSource != null)) {

            List filesToImport = FileSystemStructureProvider.INSTANCE
                    .getChildren(importSource.getParentFile());
            ImportOperation operation = new ImportOperation(
                    project.getFullPath(), importSource.getParentFile(),
                    FileSystemStructureProvider.INSTANCE, overwriteQuery,
                    filesToImport);
            operation.setContext(getShell());

            operation.setOverwriteResources(true); // need to overwrite
            operation.setCreateContainerStructure(false);
            operation.run(monitor);
        }
    }

    /**
     * Create project path controls.
     *
     * @param workArea Parent
     */
    private void createProjectRoot(final Composite workArea) {
        Composite projectGroup = new Composite(workArea, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 3;
        layout.makeColumnsEqualWidth = false;
        layout.marginWidth = 0;
        projectGroup.setLayout(layout);
        projectGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        // new project from directory radio button
        projectFromDirectoryRadio = new Button(projectGroup, SWT.RADIO);
        projectFromDirectoryRadio.setText(
                ImportOpenConfiguratorProjectWizardPage.SELECT_ROOT_DIRECTORY_LABEL);

        // project location entry
        directoryPathField = new Text(projectGroup, SWT.BORDER);

        GridData directoryPathData = new GridData(
                GridData.HORIZONTAL_ALIGN_FILL | GridData.GRAB_HORIZONTAL);
        directoryPathData.widthHint = new PixelConverter(directoryPathField)
                .convertWidthInCharsToPixels(25);
        directoryPathField.setLayoutData(directoryPathData);

        // browse button
        Button browseDirectoriesButton = new Button(projectGroup, SWT.PUSH);
        browseDirectoriesButton
                .setText(ImportOpenConfiguratorProjectWizardPage.BROWSE_LABEL);
        setButtonLayoutData(browseDirectoriesButton);

        projectFromDirectoryRadio.setSelection(true);
        browseDirectoriesButton.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                ImportOpenConfiguratorProjectWizardPage.this
                        .promptSearchProjectLocation();
            }

        });

        directoryPathField.addTraverseListener(new TraverseListener() {

            @Override
            public void keyTraversed(TraverseEvent e) {
                if (e.detail == SWT.TRAVERSE_RETURN) {
                    e.doit = false;

                    ImportOpenConfiguratorProjectWizardPage.this
                            .updateProjectsList(
                                    directoryPathField.getText().trim());
                }
            }
        });

        directoryPathField.addFocusListener(new FocusAdapter() {

            @Override
            public void focusLost(org.eclipse.swt.events.FocusEvent e) {
                ImportOpenConfiguratorProjectWizardPage.this.updateProjectsList(
                        directoryPathField.getText().trim());
            }
        });

    }

    /**
     * Create the projects in the workspace.
     *
     * @return true if the operation completed successfully, false otherwise.
     */
    boolean createProjects() {

        final Object[] selectedProjectsList = projectsListCheckBoxTreeViewer
                .getCheckedElements();

        WorkspaceModifyOperation op = new WorkspaceModifyOperation() {
            @Override
            protected void execute(IProgressMonitor monitor)
                    throws InvocationTargetException, InterruptedException {
                try {
                    monitor.beginTask("", selectedProjectsList.length); //$NON-NLS-1$
                    if (monitor.isCanceled()) {
                        throw new OperationCanceledException();
                    }
                    for (Object selectProject : selectedProjectsList) {
                        try {
                            ImportOpenConfiguratorProjectWizardPage.this
                                    .createProjectDescription(
                                            (ProjectRecord) selectProject,
                                            new SubProgressMonitor(monitor, 1));
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } finally {
                    monitor.done();
                }
            }
        };
        // run the new project creation operation
        try {
            getContainer().run(true, true, op);
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        } catch (InvocationTargetException e) {
            // one of the steps resulted in a core exception
            Throwable t = e.getTargetException();
            IStatus status;
            if (t instanceof CoreException) {
                status = ((CoreException) t).getStatus();
            } else {
                status = new Status(IStatus.ERROR, Activator.PLUGIN_ID, 1,
                        ImportOpenConfiguratorProjectWizardPage.PROJECT_CREATION_ERRORS_LABEL,
                        t);
            }
            ErrorDialog.openError(getShell(),
                    ImportOpenConfiguratorProjectWizardPage.PROJECT_CREATION_ERRORS_LABEL,
                    null, status);
            return false;
        }

        return true;
    }

    /**
     * Create projects tree viewer controls.
     *
     * @param workArea Parent
     */
    private void createProjectsList(final Composite workArea) {
        Label title = new Label(workArea, SWT.NONE);
        title.setText(ImportOpenConfiguratorProjectWizardPage.PROJECT_LABEL);
        Composite listComposite = new Composite(workArea, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        layout.marginWidth = 0;
        layout.makeColumnsEqualWidth = false;
        listComposite.setLayout(layout);
        listComposite.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL
                | GridData.GRAB_VERTICAL | GridData.FILL_BOTH));

        projectsListCheckBoxTreeViewer = new CheckboxTreeViewer(listComposite,
                SWT.BORDER);
        GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
        gridData.widthHint = new PixelConverter(
                projectsListCheckBoxTreeViewer.getControl())
                        .convertWidthInCharsToPixels(25);
        gridData.heightHint = new PixelConverter(
                projectsListCheckBoxTreeViewer.getControl())
                        .convertHeightInCharsToPixels(10);
        projectsListCheckBoxTreeViewer.getControl().setLayoutData(gridData);
        projectsListCheckBoxTreeViewer
                .setContentProvider(new ITreeContentProvider() {

                    @Override
                    public void dispose() {
                    }

                    @Override
                    public Object[] getChildren(Object parentElement) {
                        return new Object[0];
                    }

                    @Override
                    public Object[] getElements(Object inputElement) {
                        return ImportOpenConfiguratorProjectWizardPage.this
                                .getProjectRecords().toArray();
                    }

                    @Override
                    public Object getParent(Object element) {
                        return null;
                    }

                    @Override
                    public boolean hasChildren(Object element) {
                        // openCONFIGURATOR project has no subprojects.
                        return false;
                    }

                    @Override
                    public void inputChanged(Viewer viewer, Object oldInput,
                            Object newInput) {
                    }
                });
        projectsListCheckBoxTreeViewer
                .setLabelProvider(new ProjectLabelProvider());
        projectsListCheckBoxTreeViewer
                .addCheckStateListener(projectListCheckStateListener);
        projectsListCheckBoxTreeViewer.setInput(this);
        projectsListCheckBoxTreeViewer.setComparator(new ViewerComparator());
        createSelectionButtons(listComposite);
    }

    /**
     * Create project list selection buttons
     *
     * @param parent Parent
     */
    private void createSelectionButtons(Composite parent) {
        Composite buttonsComposite = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        buttonsComposite.setLayout(layout);

        buttonsComposite
                .setLayoutData(new GridData(GridData.VERTICAL_ALIGN_BEGINNING));

        Button selectAll = new Button(buttonsComposite, SWT.PUSH);
        selectAll.setText(
                ImportOpenConfiguratorProjectWizardPage.SELECT_ALL_LABEL);
        selectAll.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                for (ProjectRecord selectedProject : selectedProjects) {
                    boolean hasConflicts = selectedProject.hasConflicts();
                    boolean isValid = selectedProject
                            .isValidOpenConfiguratorProject();

                    projectsListCheckBoxTreeViewer.setChecked(selectedProject,
                            !(hasConflicts || !isValid));
                }

                ImportOpenConfiguratorProjectWizardPage.this
                        .setPageComplete(projectsListCheckBoxTreeViewer
                                .getCheckedElements().length > 0);
            }
        });
        Dialog.applyDialogFont(selectAll);
        setButtonLayoutData(selectAll);

        Button deSelectAll = new Button(buttonsComposite, SWT.PUSH);
        deSelectAll.setText(
                ImportOpenConfiguratorProjectWizardPage.DE_SELECT_ALL_LABEL);
        deSelectAll.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                for (ProjectRecord selectedProject : selectedProjects) {
                    projectsListCheckBoxTreeViewer.setChecked(selectedProject,
                            false);
                }
                ImportOpenConfiguratorProjectWizardPage.this
                        .setPageComplete(projectsListCheckBoxTreeViewer
                                .getCheckedElements().length > 0);
            }
        });
        Dialog.applyDialogFont(deSelectAll);
        setButtonLayoutData(deSelectAll);

        Button refresh = new Button(buttonsComposite, SWT.PUSH);
        refresh.setText(ImportOpenConfiguratorProjectWizardPage.REFRESH_LABEL);
        refresh.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (projectFromDirectoryRadio.getSelection()) {
                    ImportOpenConfiguratorProjectWizardPage.this
                            .updateProjectsList(
                                    directoryPathField.getText().trim());
                }
            }
        });
        Dialog.applyDialogFont(refresh);
        setButtonLayoutData(refresh);
    }

    /**
     * @return The list of selected projects.
     */
    private ArrayList<ProjectRecord> getProjectRecords() {
        ArrayList<ProjectRecord> projectRecords = new ArrayList<>();
        for (ProjectRecord selectedProject : selectedProjects) {
            String projectName = selectedProject.getProjectName();
            selectedProject.hasConflicts = (isProjectFolderInWorkspacePath(
                    projectName) && copyFiles)
                    || isProjectInWorkspace(projectName);
            projectRecords.add(selectedProject);
        }

        return projectRecords;
    }

    /**
     * Prompts the path from the user to search for the projects.
     */
    private void promptSearchProjectLocation() {
        DirectoryDialog dialog = new DirectoryDialog(
                ImportOpenConfiguratorProjectWizardPage.this.directoryPathField
                        .getShell(),
                SWT.SHEET);
        dialog.setMessage(
                ImportOpenConfiguratorProjectWizardPage.IMPORT_OPENCONFIGURATOR_LABEL);

        String dirName = ImportOpenConfiguratorProjectWizardPage.this.directoryPathField
                .getText().trim();
        if (dirName.length() == 0) {
            dirName = ImportOpenConfiguratorProjectWizardPage.previouslyBrowsedDirectory;
        }

        if (dirName.length() == 0) {
            dialog.setFilterPath(ResourcesPlugin.getWorkspace().getRoot()
                    .getLocation().toOSString());
        } else {
            File path = new File(dirName);
            if (path.exists()) {
                dialog.setFilterPath(new Path(dirName).toOSString());
            }
        }

        String selectedDirectory = dialog.open();
        if (selectedDirectory != null) {
            ImportOpenConfiguratorProjectWizardPage.previouslyBrowsedDirectory = selectedDirectory;
            ImportOpenConfiguratorProjectWizardPage.this.directoryPathField
                    .setText(
                            ImportOpenConfiguratorProjectWizardPage.previouslyBrowsedDirectory);
            ImportOpenConfiguratorProjectWizardPage.this
                    .updateProjectsList(selectedDirectory);
        }
    }

    /**
     * Sets the focus to the Directory path text box.
     *
     * @param visible
     */
    @Override
    public void setVisible(boolean visible) {

        super.setVisible(visible);

        if (visible && projectFromDirectoryRadio.getSelection()) {
            directoryPathField.setFocus();
        }
    }

    /**
     * Searches for the openCONFIGURATOR project files in the given path and
     * updates the check box tree viewer with the list.
     *
     * @param path Directory to search for projects.
     */
    private void updateProjectsList(final String path) {

        if ((path == null) || (path.length() == 0)) {
            selectedProjects = new ArrayList<>();
            projectsListCheckBoxTreeViewer.refresh(true);
            projectsListCheckBoxTreeViewer
                    .setCheckedElements(selectedProjects.toArray());
            setPageComplete(projectsListCheckBoxTreeViewer
                    .getCheckedElements().length > 0);
            lastPath = path;
            return;
        }

        final File directory = new File(path);
        long modified = directory.lastModified();
        if (path.equals(lastPath) && (lastModified == modified)
                && (lastNestedProjects == nestedProjects)
                && (lastCopyFiles == copyFiles)) {
            // since the file/folder was not modified and the path did not
            // change, no refreshing is required
            return;
        }

        lastPath = path;
        lastModified = modified;
        lastNestedProjects = nestedProjects;
        lastCopyFiles = copyFiles;

        final boolean dirSelected = projectFromDirectoryRadio.getSelection();
        try {
            getContainer().run(true, true, new IRunnableWithProgress() {

                @Override
                public void run(IProgressMonitor monitor) {

                    monitor.beginTask(
                            ImportOpenConfiguratorProjectWizardPage.SEARCH_PROJECTS_TASK_LABEL,
                            100);
                    selectedProjects = new ArrayList<>();
                    Collection<File> files = new ArrayList<>();
                    monitor.worked(10);

                    if (dirSelected && directory.isDirectory()) {

                        monitor.subTask(
                                ImportOpenConfiguratorProjectWizardPage.SEARCH_PROJECTS_IN_TASK_LABEL
                                        + directory.getPath());

                        if (!collectProjectFilesFromDirectory(files,
                                directory)) {
                            return;
                        }

                        monitor.worked(50);

                        monitor.subTask(
                                ImportOpenConfiguratorProjectWizardPage.PROCESSING_RESULTS_TASK_LABEL);

                        for (File projectFile : files) {
                            selectedProjects
                                    .add(new ProjectRecord(projectFile));
                        }

                    } else {
                        monitor.worked(90);
                    }
                    monitor.done();
                }

            });
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            // Nothing to do if the user interrupts.
        }

        projectsListCheckBoxTreeViewer.refresh(true);
        boolean displayWarning = false;
        ArrayList<ProjectRecord> projectsLists = getProjectRecords();
        for (ProjectRecord project : projectsLists) {
            if (project.hasConflicts()
                    || !project.isValidOpenConfiguratorProject()) {
                displayWarning = true;
                projectsListCheckBoxTreeViewer.setGrayed(project, true);
            } else {
                projectsListCheckBoxTreeViewer.setChecked(project, true);
            }
        }

        if (displayWarning) {
            this.setMessage(
                    ImportOpenConfiguratorProjectWizardPage.PROJECTS_EXISTS_WORKSPACE_MESSAGE,
                    IMessageProvider.WARNING);
        } else {
            this.setMessage(null);
        }

        setPageComplete(
                projectsListCheckBoxTreeViewer.getCheckedElements().length > 0);
        if (selectedProjects.isEmpty()) {
            this.setMessage(
                    ImportOpenConfiguratorProjectWizardPage.NO_PROJECTS_FOUND_MESSAGE,
                    IMessageProvider.WARNING);
        }

    }
}

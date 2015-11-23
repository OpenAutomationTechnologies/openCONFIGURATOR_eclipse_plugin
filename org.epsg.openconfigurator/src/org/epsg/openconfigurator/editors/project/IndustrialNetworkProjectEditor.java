/*******************************************************************************
 * @file   IndustrialNetworkEditor.java
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

package org.epsg.openconfigurator.editors.project;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.IFormPage;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.epsg.openconfigurator.Activator;
import org.epsg.openconfigurator.lib.wrapper.OpenConfiguratorCore;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.model.IPowerlinkProjectSupport;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.model.Path;
import org.epsg.openconfigurator.util.OpenConfiguratorLibraryUtils;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectMarshaller;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectUtils;
import org.epsg.openconfigurator.util.PluginErrorDialogUtils;
import org.epsg.openconfigurator.views.IndustrialNetworkView;
import org.epsg.openconfigurator.xmlbinding.projectfile.OpenCONFIGURATORProject;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNetworkConfiguration;
import org.epsg.openconfigurator.xmlbinding.projectfile.TPath;
import org.epsg.openconfigurator.xmlbinding.projectfile.TProjectConfiguration.PathSettings;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

/**
 * IndustrialNetworkProjectEditor class provides the multi-tab based form editor
 * to configure the openCONFIGURATOR project
 *
 * This editor is the main UI opened once the project.xml is double clicked from
 * the project explorer.
 *
 * Additionally this opens the {@link IndustrialNetworkView}.
 *
 * @author Ramakrishnan P
 *
 */
public final class IndustrialNetworkProjectEditor extends FormEditor
        implements IResourceChangeListener, IPropertyChangeListener {

    /**
     * Identifier for this page.
     */
    public static final String ID = "org.epsg.openconfigurator.editors.project.IndustrialNetworkProjectEditor";

    /**
     * Editor strings and messages.
     */
    private static final String PROJECT_EDITOR_PAGE_NAME = "POWERLINK Project";
    private static final String PROJECT_EDITOR_CREATION_ERROR_MESSAGE = "Error creating project editor overview page";
    private static final String PROJECT_SOURCE_PAGE_NAME = "Source";
    private static final String PROJECT_SOURCE_PAGE_CREATION_ERROR_MESSAGE = "Error creating nested XML editor";
    private static final String MARSHALL_ERROR = "Error marshalling the openCONFIGURATOR project";
    private static final String UNMARSHALL_ERROR = "Error unmarshalling the openCONFIGURATOR project";
    private static final String INVALID_INPUT_ERROR = "Invalid input: Must be a valid openCONFIGURATOR project file.";

    /**
     * Network identifier for the editor.
     */
    private String networkId;

    /**
     * Eclipse project instance linked with this editor.
     */
    private IProject activeProject;

    /**
     * openCONFIGURATOR project XML instance linked with this editor.
     */
    private IFile projectFile;

    /**
     * openCONFIGURATOR project model
     */
    private OpenCONFIGURATORProject currentProject;

    /**
     * List of nodes available in the project.
     */
    private Map<Short, Node> nodeCollection = new HashMap<Short, Node>();

    /**
     * Flag to check for the library initialization
     */
    private boolean initSuccessful = false;

    /**
     * Source editor page.
     */
    private IndustrialNetworkProjectSourcePage sourcePage;

    /**
     * Advanced project editor page.
     */
    private IndustrialNetworkProjectEditorPage editorPage;

    private Job importNodeXdcJob;

    /**
     * Constructor
     */
    public IndustrialNetworkProjectEditor() {
        super();
        ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
    }

    /**
     * Adds an editor page and source page for the project editor.
     *
     * @see IndustrialNetworkProjectSourcePage
     * @see IndustrialNetworkProjectEditorPage
     */
    @Override
    protected void addPages() {

        try {

            createPowerlinkProjectEditor();
            createProjectSourceEditor();

            this.setActivePage(editorPage.getId());

            editorPage.setOpenCONFIGURATORProject(currentProject);

        } catch (NullPointerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Closes the all the pages added this editor.
     */
    private void closeEditor() {
        // Run in a separate thread.
        Display.getDefault().asyncExec(new Runnable() {
            @Override
            public void run() {

                IWorkbenchPage[] pages = IndustrialNetworkProjectEditor.this
                        .getSite().getWorkbenchWindow().getPages();
                for (IWorkbenchPage editorPage : pages) {
                    IEditorPart editorPart = editorPage
                            .findEditor(getEditorInput());
                    editorPage.closeEditor(editorPart, true);
                }
            }
        });
    }

    /**
     * Creates the POWERLNK project editor page
     *
     * @see IndustrialNetworkProjectEditorPage
     */
    private void createPowerlinkProjectEditor() {

        try {
            editorPage = new IndustrialNetworkProjectEditorPage(this,
                    getTitle());
            int index = this.addPage(editorPage, getEditorInput());
            setPageText(index,
                    IndustrialNetworkProjectEditor.PROJECT_EDITOR_PAGE_NAME);
            editorPage.setIndex(index);

        } catch (PartInitException e) {
            ErrorDialog.openError(getSite().getShell(), null,
                    IndustrialNetworkProjectEditor.PROJECT_EDITOR_CREATION_ERROR_MESSAGE,
                    e.getStatus());
            e.printStackTrace();
        }
    }

    /**
     * Creates the POWERLINK project editor source page
     *
     * @see IndustrialNetworkProjectSourcePage
     */
    private void createProjectSourceEditor() {
        try {
            sourcePage = new IndustrialNetworkProjectSourcePage();
            int index = this.addPage(sourcePage, getEditorInput());
            setPageText(index,
                    IndustrialNetworkProjectEditor.PROJECT_SOURCE_PAGE_NAME);
            sourcePage.setIndex(index);
            sourcePage.initialize(this);
        } catch (PartInitException e) {
            ErrorDialog.openError(getSite().getShell(), null,
                    IndustrialNetworkProjectEditor.PROJECT_SOURCE_PAGE_CREATION_ERROR_MESSAGE,
                    e.getStatus());
            e.printStackTrace();
        }
    }

    /**
     * Disposes the project editor UI.
     */
    @Override
    public void dispose() {
        if (initSuccessful) {

            Display.getDefault().syncExec(new Runnable() {
                @Override
                public void run() {
                    if (importNodeXdcJob != null) {
                        importNodeXdcJob.cancel();
                        try {
                            importNodeXdcJob.join();
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
            });

            nodeCollection.clear();

            Display.getDefault().syncExec(new Runnable() {
                @Override
                public void run() {
                    IViewPart viewPart = PlatformUI.getWorkbench()
                            .getActiveWorkbenchWindow().getActivePage()
                            .findView(IndustrialNetworkView.ID);
                    if (viewPart instanceof IndustrialNetworkView) {
                        IndustrialNetworkView industrialView = (IndustrialNetworkView) viewPart;
                        industrialView.editorActivated(
                                IndustrialNetworkProjectEditor.this);
                    }
                }
            });

            Result libApiRes = OpenConfiguratorCore.GetInstance()
                    .RemoveNetwork(networkId);
            if (!libApiRes.IsSuccessful()) {
                // Report error to the user using the dialog.
                String errorMessage = OpenConfiguratorLibraryUtils
                        .getErrorMessage(libApiRes);
                System.err.println(errorMessage);
                PluginErrorDialogUtils.displayErrorMessageDialog(
                        getSite().getShell(), errorMessage, null);
            }
        }
        ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
        super.dispose();
    }

    /**
     * Saves the multi-page editor's changes.
     */
    @Override
    public void doSave(IProgressMonitor monitor) {
        editorPage.doSave(monitor);
        sourcePage.doSave(monitor);
    }

    @Override
    public void doSaveAs() {
        // TODO Auto-generated method stub

    }

    /**
     * @return the document instance
     */
    private IDocument getDocument() {
        final IDocumentProvider provider = sourcePage.getDocumentProvider();

        return provider.getDocument(getEditorInput());
    }

    /**
     * @return the openCONFIGURATOR project model contents as string
     */
    private String getModelData() {
        String retVal = new String("");
        try {
            if (currentProject != null) {
                OpenConfiguratorProjectUtils
                        .updateGeneratorInformation(currentProject);
                retVal = OpenConfiguratorProjectMarshaller
                        .marshallOpenConfiguratorProject(currentProject);
            }
        } catch (JAXBException e) {
            IStatus errorStatus = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                    1, e.getMessage(), e);
            ErrorDialog.openError(getSite().getShell(), null,
                    IndustrialNetworkProjectEditor.MARSHALL_ERROR, errorStatus);
            e.printStackTrace();
        }
        return retVal;
    }

    /**
     * @return The network configuration instance from the project model.
     */
    public TNetworkConfiguration getNetworkConfiguration() {
        return currentProject.getNetworkConfiguration();
    }

    /**
     * @return The network ID associated with the editor.
     */
    public String getNetworkId() {
        return networkId;
    }

    /**
     * @return Returns the XDD files.
     */
    public Map<Short, Node> getNodeCollection() {
        return nodeCollection;
    }

    /**
     * @return The IFile instance of the openCONFIGURATOR project XML file.
     */
    public IFile getProjectFile() {
        return projectFile;
    }

    /**
     * @return Returns the output path settings from the project XML.
     */
    public Path getProjectOutputPath() {
        PathSettings pathSett = currentProject.getProjectConfiguration()
                .getPathSettings();
        String activeOutputPathID = pathSett.getActivePath();
        if (activeOutputPathID == null) {
            if (!pathSett.getPath().isEmpty()) {
                TPath defaultPath = OpenConfiguratorProjectUtils
                        .getTPath(pathSett, "defaultOutputPath");
                if (defaultPath != null) {
                    return new Path(activeProject, defaultPath.getPath(), true);
                } else {
                    System.err.println(
                            "Unhandled error occurred. defaultPath not found");
                }
            }
        } else {
            TPath defaultPath = OpenConfiguratorProjectUtils.getTPath(pathSett,
                    activeOutputPathID);
            if (defaultPath != null) {
                if (!defaultPath.getId()
                        .equalsIgnoreCase("defaultOutputPath")) {
                    return new Path(activeProject, defaultPath.getPath(),
                            false);
                }
            } else {
                System.err.println(
                        "Unhandled error occurred. activeOutputPath not found");
            }
        }
        return new Path(activeProject,
                IPowerlinkProjectSupport.DEFAULT_OUTPUT_DIR, true);
    }

    /**
     * Marks the content in the project editor source page based on the marker.
     *
     * @param marker Marker details
     */
    public void gotoMarker(IMarker marker) {
        this.setActivePage(0);
        IDE.gotoMarker(getEditor(0), marker);
    }

    /**
     * Handles the property change signals.
     */
    @Override
    protected void handlePropertyChange(int propertyId) {
        super.handlePropertyChange(propertyId);
    }

    /**
     * Initializes the project editor
     *
     * The <code>IndustrialNetworkProjectEditor</code> implementation of this
     * method checks that the input is an instance of
     * <code>IFileEditorInput</code>.
     */
    @Override
    public void init(IEditorSite site, IEditorInput editorInput)
            throws PartInitException {
        if (!(editorInput instanceof IFileEditorInput)) {
            throw new PartInitException(
                    IndustrialNetworkProjectEditor.INVALID_INPUT_ERROR);
        }
        super.init(site, editorInput);

        IFileEditorInput input = (IFileEditorInput) editorInput;
        projectFile = input.getFile();
        setPartName(projectFile.getName());

        activeProject = projectFile.getProject();
        networkId = activeProject.getName();

        try {
            activeProject.refreshLocal(IResource.DEPTH_INFINITE,
                    new NullProgressMonitor());
        } catch (CoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Validate the input with openCONFIGURATOR project file schema.
        try {
            currentProject = OpenConfiguratorProjectMarshaller
                    .unmarshallOpenConfiguratorProject(
                            projectFile.getContents());
        } catch (FileNotFoundException | MalformedURLException | JAXBException
                | SAXException | ParserConfigurationException
                | CoreException e) {
            e.printStackTrace();
            throw new PartInitException(
                    IndustrialNetworkProjectEditor.INVALID_INPUT_ERROR);
        }

        OpenConfiguratorProjectUtils
                .upgradeOpenConfiguratorProject(currentProject);

        Result libApiRes = OpenConfiguratorCore.GetInstance()
                .CreateNetwork(networkId);
        if (!libApiRes.IsSuccessful()) {
            // Report error to the user using the dialog.
            String errorMessage = OpenConfiguratorLibraryUtils
                    .getErrorMessage(libApiRes);
            System.err.println(errorMessage);
            PluginErrorDialogUtils.displayErrorMessageDialog(
                    getSite().getShell(), errorMessage, null);
            return;
        }

        libApiRes = OpenConfiguratorLibraryUtils
                .addOpenCONFIGURATORProject(currentProject, networkId);
        if (!libApiRes.IsSuccessful()) {
            // Report error to the user using the dialog.
            String errorMessage = OpenConfiguratorLibraryUtils
                    .getErrorMessage(libApiRes);
            System.err.println(errorMessage);
            PluginErrorDialogUtils.displayErrorMessageDialog(
                    getSite().getShell(), errorMessage, null);
            return;
        }

        System.out.println("activeProject- path" + activeProject.getLocation());

        importNodeXdcJob = new Job("Import node XDD/XDC") {

            @Override
            protected IStatus run(IProgressMonitor monitor) {

                TNetworkConfiguration networkCfg = currentProject
                        .getNetworkConfiguration();
                int totalWork = 1
                        + networkCfg.getNodeCollection().getCN().size()
                        + networkCfg.getNodeCollection().getRMN().size();
                monitor.beginTask("Import MN XDD/XDC", totalWork);
                IStatus result = OpenConfiguratorProjectUtils.importNodes(
                        projectFile, networkCfg, nodeCollection, monitor);
                Display.getDefault().syncExec(new Runnable() {
                    @Override
                    public void run() {
                        IViewPart viewPart = PlatformUI.getWorkbench()
                                .getActiveWorkbenchWindow().getActivePage()
                                .findView(IndustrialNetworkView.ID);
                        if (viewPart instanceof IndustrialNetworkView) {
                            IndustrialNetworkView industrialView = (IndustrialNetworkView) viewPart;
                            industrialView.editorActivated(
                                    IndustrialNetworkProjectEditor.this);
                        }
                    }
                });

                return result;
            }
        };

        importNodeXdcJob.schedule();

        PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage()
                .showView(IndustrialNetworkView.ID);
        initSuccessful = true;
    }

    /**
     * Returns false as saveAs is not supported for this editor.
     */
    @Override
    public boolean isSaveAsAllowed() {
        return false;
    }

    /**
     * Reloads the contents of pages when the it is activated.
     */
    @Override
    protected void pageChange(int newPageIndex) {

        if ((getActivePage() == sourcePage.getIndex()) && (isDirty())) {
            updateModelToSource();
        }

        // switch page
        super.pageChange(newPageIndex);

        // update page if needed
        final IFormPage page = getActivePageInstance();
        if (page != null) {
            editorPage.setOpenCONFIGURATORProject(currentProject);
            page.setFocus();
        }
    }

    /**
     * Persist the data from the library in the XDC and the project files.
     *
     * @param monitor The progress monitor instance.
     * @throws InterruptedException
     * @throws InvocationTargetException
     */
    public void persistLibraryData(IProgressMonitor monitor)
            throws InterruptedException, InvocationTargetException {
        monitor.beginTask("Save XDC configurations", nodeCollection.size());

        WorkspaceModifyOperation wmo = new WorkspaceModifyOperation() {

            @Override
            protected void execute(IProgressMonitor monitor)
                    throws CoreException, InvocationTargetException,
                    InterruptedException {
                // Write the XDC configuration Changes from the library to the
                // XDC file.

                try {
                    Result res = OpenConfiguratorProjectUtils
                            .persistNodes(nodeCollection, monitor);
                    if (!res.IsSuccessful()) {
                        System.err.println(OpenConfiguratorLibraryUtils
                                .getErrorMessage(res));
                        IStatus errorStatus = new Status(IStatus.ERROR,
                                Activator.PLUGIN_ID, IStatus.OK,
                                OpenConfiguratorLibraryUtils
                                        .getErrorMessage(res),
                                null);
                        throw new CoreException(errorStatus);
                    }
                } catch (JDOMException | IOException exception) {
                    exception.printStackTrace();
                    IStatus errorStatus = new Status(IStatus.ERROR,
                            Activator.PLUGIN_ID, IStatus.OK,
                            "Error while saving the XDC", exception);

                    throw new CoreException(errorStatus);
                }
            }
        };

        wmo.run(monitor);

        monitor.done();
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        // TODO Auto-generated method stub
    }

    /**
     * Reloads the project editor source page contents from the model.
     */
    public void reloadEditorContentsFromModel() {
        setContent(getModelData());
    }

    /**
     * Updates the openCONFIGURATOR project model with the given input.
     *
     * @param input Input content of openCONFIGURATOR type
     */
    public void reloadFromSourceText(final String input) {

        try {
            InputStream is = new ByteArrayInputStream(input.getBytes());
            currentProject = OpenConfiguratorProjectMarshaller
                    .unmarshallOpenConfiguratorProject(is);
        } catch (FileNotFoundException | MalformedURLException | JAXBException
                | SAXException | ParserConfigurationException e) {
            IStatus errorStatus = new Status(IStatus.ERROR, Activator.PLUGIN_ID,
                    1, e.getMessage(), e);
            ErrorDialog.openError(getSite().getShell(), null,
                    IndustrialNetworkProjectEditor.UNMARSHALL_ERROR,
                    errorStatus);
            e.printStackTrace();
        }
    }

    /**
     * Handles the project change events.
     */
    @Override
    public void resourceChanged(final IResourceChangeEvent event) {

        switch (event.getType()) {
            case IResourceChangeEvent.POST_CHANGE:
                break;
            case IResourceChangeEvent.PRE_CLOSE:
            case IResourceChangeEvent.PRE_DELETE: // Fallthrough
                if (((FileEditorInput) sourcePage.getEditorInput()).getFile()
                        .getProject().equals(event.getResource())) {
                    closeEditor();
                }
                break;
            case IResourceChangeEvent.PRE_BUILD:
                break;
            case IResourceChangeEvent.POST_BUILD:
                break;
            case IResourceChangeEvent.PRE_REFRESH:
                break;
            default:
                break;
        }

        // Handle project file delete and rename events
        IResourceDelta delta = event.getDelta();
        if (delta == null) {
            return;
        }

        IFileEditorInput input = (IFileEditorInput) getEditorInput();
        if (input == null) {
            return;
        }

        // Project file is not present, so close all the editor pages.
        IFile currentProjectFile = input.getFile();
        if (currentProjectFile == null) {
            closeEditor();
            return;
        }

        IResourceDelta oldDelta = delta
                .findMember(currentProjectFile.getFullPath());
        if (oldDelta == null) {
            return;
        }

        switch (oldDelta.getKind()) {
            case IResourceDelta.REMOVED:
                if ((oldDelta.getFlags() & IResourceDelta.MOVED_TO) != 0) {
                    IPath newPath = oldDelta.getMovedToPath();
                    final IFile newfile = ResourcesPlugin.getWorkspace()
                            .getRoot().getFile(newPath);
                    if (newfile != null) {
                        setInput(new FileEditorInput(newfile));

                        if (newfile.getName() != null) {
                            Display.getDefault().asyncExec(new Runnable() {
                                @Override
                                public void run() {
                                    setPartName(newfile.getName());
                                }
                            });
                        }
                    }
                } else if (oldDelta.getFlags() == 0) {
                    closeEditor();
                }
                break;
            case IResourceDelta.CHANGED:
                if (oldDelta.getFlags() == IResourceDelta.CONTENT) {
                    setInput(new FileEditorInput(currentProjectFile));
                }

                break;
            default:
                break;
        }
    }

    /**
     * Sets the input contents to the project source editor.
     *
     * @param source
     */
    private void setContent(String source) {
        getDocument().set(source);
    }

    /**
     * Updates the openCONIGURATOR project model into the XML source file.
     */
    void updateModelToSource() {
        String dataFromModel = getModelData();
        setContent(dataFromModel);
    }
}

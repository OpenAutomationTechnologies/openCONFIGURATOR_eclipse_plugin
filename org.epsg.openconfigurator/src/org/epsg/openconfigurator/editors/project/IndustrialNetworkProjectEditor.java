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
import java.io.InputStream;
import java.net.MalformedURLException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.epsg.openconfigurator.Activator;
import org.epsg.openconfigurator.lib.wrapper.OpenConfiguratorCore;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.util.OpenConfiguratorLibraryUtils;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectMarshaller;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectUtils;
import org.epsg.openconfigurator.util.PluginErrorDialogUtils;
import org.epsg.openconfigurator.views.IndustrialNetworkView;
import org.epsg.openconfigurator.xmlbinding.projectfile.OpenCONFIGURATORProject;
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
public final class IndustrialNetworkProjectEditor extends FormEditor implements
IResourceChangeListener {

    /**
     * Identifier for this page.
     */
    public static final String ID = "org.epsg.openconfigurator.editors.project.IndustrialNetworkProjectEditor";

    /**
     * Editor strings and messages.
     */
    private static final String PROJECT_EDITOR_PAGE_NAME = "POWERLINK project";
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
     * openCONFIGURATOR project model
     */
    private OpenCONFIGURATORProject currentProject;

    /**
     * Source editor page.
     */
    private IndustrialNetworkProjectSourcePage sourcePage;

    /**
     * Advanced project editor page.
     */
    private IndustrialNetworkProjectEditorPage editorPage;

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

            Result libApiRes = OpenConfiguratorCore.GetInstance()
                    .CreateNetwork(networkId);
            if (!libApiRes.IsSuccessful()) {
                // Report error to the user using the dialog.
                String errorMessage = OpenConfiguratorLibraryUtils
                        .getErrorMessage(libApiRes);
                System.err.println(errorMessage);
                PluginErrorDialogUtils.displayErrorMessageDialog(getSite()
                        .getShell(), errorMessage, null);
                return;
            }

            createPowerlinkProjectEditor();
            createProjectSourceEditor();

            this.setActivePage(editorPage.getId());

            OpenConfiguratorProjectUtils
            .upgradeOpenConfiguratorProject(currentProject);
            editorPage.setOpenCONFIGURATORProject(currentProject);

            libApiRes = OpenConfiguratorLibraryUtils
                    .addOpenCONFIGURATORProjectIntoLibrary(currentProject,
                            networkId);
            if (!libApiRes.IsSuccessful()) {
                // Report error to the user using the dialog.
                String errorMessage = OpenConfiguratorLibraryUtils
                        .getErrorMessage(libApiRes);
                System.err.println(errorMessage);
                PluginErrorDialogUtils.displayErrorMessageDialog(getSite()
                        .getShell(), errorMessage, null);
                return;
            }

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
            ErrorDialog
            .openError(
                    getSite().getShell(),
                    null,
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

        } catch (PartInitException e) {
            ErrorDialog
            .openError(
                    getSite().getShell(),
                    null,
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
        Result libApiRes = OpenConfiguratorCore.GetInstance().RemoveNetwork(
                networkId);
        if (!libApiRes.IsSuccessful()) {
            // Report error to the user using the dialog.
            String errorMessage = OpenConfiguratorLibraryUtils
                    .getErrorMessage(libApiRes);
            System.err.println(errorMessage);
            PluginErrorDialogUtils.displayErrorMessageDialog(getSite()
                    .getShell(), errorMessage, null);
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

    /**
     * Implement the actions that happens in the saveAs action.
     */
    @Override
    public void doSaveAs() {
        // TODO Auto-generated method stub
    }

    /**
     * Returns the contents of the project editor source page.
     *
     * @return the contents.
     */
    private String getContent() {
        return getDocument().get();
    }

    /**
     * @return the document instance
     */
    private IDocument getDocument() {
        final IDocumentProvider provider = sourcePage.getDocumentProvider();

        return provider.getDocument(getEditorInput());
    }

    /**
     * @return The network ID associated with the editor.
     */
    public String getNetworkId() {
        return networkId;
    }

    /**
     * @return the openCONFIGURATOR project model contents as string
     */
    private String getSource() {
        String retVal = new String("");
        try {
            if (currentProject != null) {
                OpenConfiguratorProjectUtils
                .updateGeneratorInformation(currentProject);
                retVal = OpenConfiguratorProjectMarshaller
                        .marshallOpenConfiguratorProject(currentProject);
            }
        } catch (JAXBException e) {
            IStatus errorStatus = new Status(IStatus.ERROR,
                    Activator.PLUGIN_ID, 1, e.getMessage(), e);
            ErrorDialog.openError(getSite().getShell(), null,
                    IndustrialNetworkProjectEditor.MARSHALL_ERROR, errorStatus);
            e.printStackTrace();
        }
        return retVal;
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
        IFile file = input.getFile();

        // Validate the input with openCONFIGURATOR project file schema.
        try {
            currentProject = OpenConfiguratorProjectMarshaller
                    .unmarshallOpenConfiguratorProject(file.getContents());
        } catch (FileNotFoundException | MalformedURLException | JAXBException
                | SAXException | ParserConfigurationException | CoreException e) {
            e.printStackTrace();
            throw new PartInitException(
                    IndustrialNetworkProjectEditor.INVALID_INPUT_ERROR);
        }

        IProject activeProject = file.getProject();
        networkId = activeProject.getName();

        setInput(editorInput);
        setPartName(file.getName());
    }

    /**
     * Checks whether the editor is dirty by checking all the pages that
     * implemented.
     */
    @Override
    public boolean isDirty() {
        return (editorPage.isDirty() || sourcePage.isDirty() || super.isDirty());
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

        super.pageChange(newPageIndex);

        if (sourcePage.getIndex() == newPageIndex) {
            String openconfiguratorProjectSource = new String("");
            try {
                if (currentProject != null) {
                    openconfiguratorProjectSource = OpenConfiguratorProjectMarshaller
                            .marshallOpenConfiguratorProject(currentProject);
                }
            } catch (JAXBException e) {
                IStatus errorStatus = new Status(IStatus.ERROR,
                        Activator.PLUGIN_ID, 1, e.getMessage(), e);
                ErrorDialog.openError(getSite().getShell(), null,
                        IndustrialNetworkProjectEditor.MARSHALL_ERROR,
                        errorStatus);
                e.printStackTrace();
            }
            sourcePage.setContent(openconfiguratorProjectSource);

            if (!editorPage.isDirty()) {
                sourcePage.doSave(null);
            }
        }

        if (editorPage != null) {
            if (editorPage.getIndex() == newPageIndex) {
                String editorText = getContent();
                reloadFromSourceText(editorText);
                editorPage.setOpenCONFIGURATORProject(currentProject);
            }
        }
    }

    /**
     * Reloads the project editor source page contents from the model.
     */
    public void reloadEditorContentsFromModel() {
        setContent(getSource());
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
            IStatus errorStatus = new Status(IStatus.ERROR,
                    Activator.PLUGIN_ID, 1, e.getMessage(), e);
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

        IResourceDelta oldDelta = delta.findMember(currentProjectFile
                .getFullPath());
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
}

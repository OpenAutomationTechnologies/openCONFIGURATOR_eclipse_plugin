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
import org.eclipse.core.resources.ResourcesPlugin;
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
import org.epsg.openconfigurator.util.OpenCONFIGURATORLibraryUtils;
import org.epsg.openconfigurator.util.OpenCONFIGURATORProjectMarshaller;
import org.epsg.openconfigurator.util.OpenCONFIGURATORProjectUtils;
import org.epsg.openconfigurator.util.PluginErrorDialogUtils;
import org.epsg.openconfigurator.views.IndustrialNetworkView;
import org.epsg.openconfigurator.xmlbinding.projectfile.OpenCONFIGURATORProject;
import org.xml.sax.SAXException;

/**
 * IndustrialNetworkProjectEditor class provides the multi-tab based form editor to configure the
 * openCONFIGURATOR project
 *
 * This editor is the main UI opened once the project.xml is double clicked from the project
 * explorer.
 *
 * Additionally this opens the {@link IndustrialNetworkView}.
 *
 * @author Ramakrishnan P
 *
 */
public final class IndustrialNetworkProjectEditor extends FormEditor implements
    IResourceChangeListener {

  public static final String ID = "org.epsg.openconfigurator.editors.project.IndustrialNetworkProjectEditor";

  private static final String PROJECT_EDITOR_PAGE_NAME = "POWERLINK project";
  private static final String PROJECT_EDITOR_CREATION_ERROR_MESSAGE = "Error creating project editor overview page";
  private static final String PROJECT_SOURCE_PAGE_NAME = "Source";
  private static final String PROJECT_SOURCE_PAGE_CREATION_ERROR_MESSAGE = "Error creating nested XML editor";

  private static final String MARSHALL_ERROR = "Error marshalling the openCONFIGURATOR project";
  private static final String UNMARSHALL_ERROR = "Error unmarshalling the openCONFIGURATOR project";

  private String networkId;

  /**
   * openCONFIGURATOR project model
   */
  private OpenCONFIGURATORProject currentProject;

  private IndustrialNetworkProjectSourcePage sourcePage;
  private IndustrialNetworkProjectEditorPage editorPage;

  public IndustrialNetworkProjectEditor() {
    super();
    ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
  }

  /**
   * @brief Initializes the project editor
   *
   *        The <code>IndustrialNetworkProjectEditor</code> implementation of this method checks
   *        that the input is an instance of <code>IFileEditorInput</code>.
   */
  @Override
  public void init(IEditorSite site, IEditorInput editorInput) throws PartInitException {
    if (!(editorInput instanceof IFileEditorInput))
      throw new PartInitException("Invalid Input: Must be IFileEditorInput");
    super.init(site, editorInput);

    IFileEditorInput input = (IFileEditorInput) editorInput;
    IFile file = input.getFile();
    IProject activeProject = file.getProject();
    networkId = activeProject.getName();
  }

  /**
   * @return The network ID associated with the editor.
   */
  public String getNetworkId() {
    return networkId;
  }

  /**
   * @brief Checks whether the editor is dirty by checking all the pages that implemented.
   */
  @Override
  public boolean isDirty() {
    return (editorPage.isDirty() || sourcePage.isDirty() || super.isDirty());
  }

  /**
   * @brief Returns false as saveAs is not supported for this editor.
   */
  @Override
  public boolean isSaveAsAllowed() {
    return false;
  }

  /**
   * @brief Implement the actions that happens in the saveAs action.
   */
  @Override
  public void doSaveAs() {
    // TODO Auto-generated method stub
  }

  /**
   * @brief Reloads the project editor source page contents from the model.
   */
  public void reloadEditorContentsFromModel() {
    setContent(getSource());
  }

  /**
   * @brief Updates the openCONFIGURATOR project model with the given input.
   *
   * @param input Input content of openCONFIGURATOR type
   */
  public void reloadFromText(String input) {

    try {
      InputStream is = new ByteArrayInputStream(input.getBytes());
      currentProject = OpenCONFIGURATORProjectMarshaller.unmarshallopenCONFIGURATORProject(is);
    } catch (FileNotFoundException | MalformedURLException | JAXBException | SAXException
        | ParserConfigurationException e) {
      IStatus errorStatus = new Status(IStatus.ERROR, Activator.PLUGIN_ID, 1, e.getMessage(), e);
      ErrorDialog.openError(getSite().getShell(), null, UNMARSHALL_ERROR, errorStatus);
      e.printStackTrace();
    }
  }

  /**
   * @brief Handles the project change events.
   */
  @Override
  public void resourceChanged(final IResourceChangeEvent event) {

    switch (event.getType()) {
      case IResourceChangeEvent.POST_CHANGE:
        break;
      case IResourceChangeEvent.PRE_CLOSE:
      case IResourceChangeEvent.PRE_DELETE:
        Display.getDefault().asyncExec(new Runnable() {
          @Override
          public void run() {
            IWorkbenchPage[] pages = IndustrialNetworkProjectEditor.this.getSite()
                .getWorkbenchWindow().getPages();
            for (int i = 0; i < pages.length; i++) {
              if (((FileEditorInput) sourcePage.getEditorInput()).getFile().getProject()
                  .equals(event.getResource())) {
                IEditorPart editorPart = pages[i].findEditor(sourcePage.getEditorInput());
                pages[i].closeEditor(editorPart, true);
              }
            }
          }
        });
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
  }

  /**
   * @brief Disposes the project editor UI.
   */
  @Override
  public void dispose() {
    Result libApiRes = OpenConfiguratorCore.GetInstance().RemoveNetwork(networkId);
    if (!libApiRes.IsSuccessful()) {
      // Report error to the user using the dialog.
      String errorMessage = "Code:" + libApiRes.GetErrorType().ordinal() + "\t"
          + libApiRes.GetErrorMessage();
      PluginErrorDialogUtils.displayErrorMessageDialog(getSite().getShell(), errorMessage, null);
      System.err.println("RemoveNetwork failed. " + errorMessage);
    }

    ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
    super.dispose();
  }

  /**
   * @brief Saves the multi-page editor's changes.
   */
  @Override
  public void doSave(IProgressMonitor monitor) {
    editorPage.doSave(monitor);
    sourcePage.doSave(monitor);
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
   * @brief Adds an editor page and source page for the project editor.
   *
   * @see IndustrialNetworkProjectSourcePage
   * @see IndustrialNetworkProjectEditorPage
   */
  @Override
  protected void addPages() {

    try {
      createProjectSourceEditor();

      Result libApiRes = OpenConfiguratorCore.GetInstance().CreateNetwork(networkId);
      if (!libApiRes.IsSuccessful()) {
        // Report error to the user using the dialog.
        String errorMessage = "Code:" + libApiRes.GetErrorType().ordinal() + "\t"
            + libApiRes.GetErrorMessage();
        PluginErrorDialogUtils.displayErrorMessageDialog(getSite().getShell(), errorMessage, null);
        System.err.println("CreateNetwork failed " + errorMessage);
        return;
      }

      String editorText = getContent();
      reloadFromText(editorText);

      createPowerlinkProjectEditor();

      this.setActivePage(editorPage.getId());

      OpenCONFIGURATORProjectUtils.fixOpenCONFIGURATORProject(currentProject);
      editorPage.setOpenCONFIGURATORProject(currentProject);

      libApiRes = OpenCONFIGURATORLibraryUtils.addOpenCONFIGURATORProjectIntoLibrary(
          currentProject, networkId);
      if (!libApiRes.IsSuccessful()) {
        // Report error to the user using the dialog.
        String errorMessage = "Code:" + libApiRes.GetErrorType().ordinal() + "\t"
            + libApiRes.GetErrorMessage();
        PluginErrorDialogUtils.displayErrorMessageDialog(getSite().getShell(), errorMessage, null);
        System.err.println("AddOpenConfiguaratorProjectIntoLib failed. " + errorMessage);
        return;
      }

    } catch (NullPointerException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * @brief Handles the property change signals.
   */
  @Override
  protected void handlePropertyChange(int propertyId) {
    super.handlePropertyChange(propertyId);
    // TODO: implement
  }

  /**
   * @brief Reloads the contents of pages when the it is activated.
   */
  @Override
  protected void pageChange(int newPageIndex) {

    super.pageChange(newPageIndex);

    // currentProject = editorPage.getOpenCONFIGURATORProject();

    if (sourcePage.getIndex() == newPageIndex) {
      String openconfiguratorProjectSource = new String("");
      try {
        if (currentProject != null) {
          openconfiguratorProjectSource = OpenCONFIGURATORProjectMarshaller
              .marshallopenCONFIGURATORProject(currentProject);
        }
      } catch (JAXBException e) {
        IStatus errorStatus = new Status(IStatus.ERROR, Activator.PLUGIN_ID, 1, e.getMessage(), e);
        ErrorDialog.openError(getSite().getShell(), null, MARSHALL_ERROR, errorStatus);
        e.printStackTrace();
      }
      sourcePage.setContent(openconfiguratorProjectSource);
    }

    if (editorPage != null) {
      if (editorPage.getIndex() == newPageIndex) {
        String editorText = getContent();
        reloadFromText(editorText);
        editorPage.setOpenCONFIGURATORProject(currentProject);
      }
    }
  }

  /**
   * @brief Creates the POWERLNK project editor page
   * @see IndustrialNetworkProjectEditorPage
   */
  private void createPowerlinkProjectEditor() {

    try {
      editorPage = new IndustrialNetworkProjectEditorPage(this, getTitle());
      int index = this.addPage(editorPage, getEditorInput());
      setPageText(index, PROJECT_EDITOR_PAGE_NAME);
      editorPage.setIndex(index);

    } catch (PartInitException e) {
      ErrorDialog.openError(getSite().getShell(), null, PROJECT_EDITOR_CREATION_ERROR_MESSAGE,
          e.getStatus());
      e.printStackTrace();
    }
  }

  /**
   * @brief Creates the POWERLINK project editor source page
   * @see IndustrialNetworkProjectSourcePage
   */
  private void createProjectSourceEditor() {
    try {
      sourcePage = new IndustrialNetworkProjectSourcePage();
      int index = this.addPage(sourcePage, getEditorInput());
      setPageText(index, PROJECT_SOURCE_PAGE_NAME);
      sourcePage.setIndex(index);

    } catch (PartInitException e) {
      ErrorDialog.openError(getSite().getShell(), null, PROJECT_SOURCE_PAGE_CREATION_ERROR_MESSAGE,
          e.getStatus());
      e.printStackTrace();
    }
  }

  /**
   * @brief Returns the contents of the project editor source page.
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
   * @return the openCONFIGURATOR project model contents as string
   */
  private String getSource() {
    String retVal = new String("");
    try {
      if (currentProject != null) {
        OpenCONFIGURATORProjectUtils.updateGeneratorInformation(currentProject);
        retVal = OpenCONFIGURATORProjectMarshaller.marshallopenCONFIGURATORProject(currentProject);
      }
    } catch (JAXBException e) {
      IStatus errorStatus = new Status(IStatus.ERROR, Activator.PLUGIN_ID, 1, e.getMessage(), e);
      ErrorDialog.openError(getSite().getShell(), null, MARSHALL_ERROR, errorStatus);
      e.printStackTrace();
    }
    return retVal;
  }

  /**
   * @brief Sets the input contents to the project source editor.
   *
   * @param source
   */
  private void setContent(String source) {
    getDocument().set(source);
  }
}

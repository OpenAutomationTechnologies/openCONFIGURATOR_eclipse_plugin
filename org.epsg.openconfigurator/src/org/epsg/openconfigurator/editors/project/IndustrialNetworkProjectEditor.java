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
import org.epsg.openconfigurator.util.OpenCONFIGURATORProjectMarshaller;
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

    InputStream is = new ByteArrayInputStream(input.getBytes());

    try {
      currentProject = OpenCONFIGURATORProjectMarshaller.unmarshallopenCONFIGURATORProject(is);
    } catch (FileNotFoundException | MalformedURLException | JAXBException | SAXException
        | ParserConfigurationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * @brief Closes all project files on project close.
   */
  @Override
  public void resourceChanged(final IResourceChangeEvent event) {
    // MessageDialog.openWarning(this.getSite().getShell(), "Info",
    // "ResourceChanged" + event.getType());
    if (event.getType() == IResourceChangeEvent.PRE_CLOSE) {
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
    }
  }

  /**
   * @brief Disposes the project editor UI.
   */
  @Override
  public void dispose() {
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

      String editorText = getContent();
      reloadFromText(editorText);

      createPowerlinkProjectEditor();

      this.setActivePage(editorPage.getId());

      editorPage.setOpenCONFIGURATORProject(currentProject);

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
   * @brief Calculates the contents of page 2 when the it is activated.
   */
  @Override
  protected void pageChange(int newPageIndex) {
    System.out.println("PageChange event" + newPageIndex);
    super.pageChange(newPageIndex);

    currentProject = editorPage.getOpenCONFIGURATORProject();

    if (sourcePage.getIndex() == newPageIndex) {
      String openconfiguratorProjectSource = new String("");
      try {
        openconfiguratorProjectSource = OpenCONFIGURATORProjectMarshaller
            .marshallopenCONFIGURATORProject(currentProject);
      } catch (JAXBException e) {
        e.printStackTrace();
      }
      sourcePage.setContent(openconfiguratorProjectSource);
    }

    if (editorPage.getIndex() == newPageIndex) {
      String editorText = getContent();
      reloadFromText(editorText);
      editorPage.setOpenCONFIGURATORProject(currentProject);
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
      ErrorDialog.openError(getSite().getShell(), PROJECT_EDITOR_CREATION_ERROR_MESSAGE, null,
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
      ErrorDialog.openError(getSite().getShell(), PROJECT_SOURCE_PAGE_CREATION_ERROR_MESSAGE, null,
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
   * @return Returns the project name
   */
  private String getProjectName() {
    IFileEditorInput input = (IFileEditorInput) getEditorInput();
    IFile file = input.getFile();
    IProject activeProject = file.getProject();
    return activeProject.getName();
  }

  /**
   * @return the openCONFIGURATOR project model contents as string
   */
  private String getSource() {
    String retVal = "";
    try {
      retVal = OpenCONFIGURATORProjectMarshaller.marshallopenCONFIGURATORProject(currentProject);
    } catch (JAXBException e) {
      // TODO Auto-generated catch block
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

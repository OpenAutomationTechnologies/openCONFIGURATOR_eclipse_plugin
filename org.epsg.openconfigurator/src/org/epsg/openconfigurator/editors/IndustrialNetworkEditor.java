package org.epsg.openconfigurator.editors;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FontDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.epsg.openconfigurator.util.OpenCONFIGURATORProjectMarshaller;
import org.epsg.openconfigurator.util.XddMarshaller;
import org.epsg.openconfigurator.xmlbinding.projectfile.OpenCONFIGURATORProject;
import org.epsg.openconfigurator.xmlbinding.projectfile.TCN;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745ProfileContainer;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyCommunicationNetworkPowerlink;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDevicePowerlink;
import org.xml.sax.SAXException;

/**
 * An example showing how to create a multi-page editor. This example has 3 pages:
 * <ul>
 * <li>page 0 contains a nested text editor.
 * <li>page 1 allows you to change the font used in page 2
 * <li>page 2 shows the words in page 0 in sorted order
 * </ul>
 */
public class IndustrialNetworkEditor extends MultiPageEditorPart implements IResourceChangeListener {

  public static final String ID = "org.epsg.openconfigurator.views.IndustrialNetworkEditor";

  private OpenCONFIGURATORProject currentProject;
  private List<ISO15745ProfileContainer> xddFiles;

  /** The text editor used in page 0. */
  private TextEditor editor;

  /** The font chosen in page 1. */
  private Font font;

  /** The text widget used in page 2. */
  private StyledText text;
  /**
   * Creates a multi-page editor example.
   */

  private IndustrialNetworkOutlineView myOutlinePage;

  public IndustrialNetworkEditor() {
    super();
    ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
    xddFiles = new ArrayList<ISO15745ProfileContainer>();
  }

  /**
   * Creates page 0 of the multi-page editor, which contains a text editor.
   */
  void createPage0() {
    try {
      editor = new TextEditor();
      int index = addPage(editor, getEditorInput());
      setPageText(index, "Source File");
    } catch (PartInitException e) {
      ErrorDialog.openError(getSite().getShell(), "Error creating nested text editor", null,
          e.getStatus());
    }
  }

  /**
   * Creates page 1 of the multi-page editor, which allows you to change the font used in page 2.
   */
  void createPage1() {

    int index = addPage(new IndustrialNetworkEditorNetworkTab(getContainer(), SWT.NONE));
    setPageText(index, "Network");
  }

  /**
   * Creates page 2 of the multi-page editor, which shows the sorted text.
   */
  void createPage2() {
    Composite composite = new Composite(getContainer(), SWT.NONE);
    FillLayout layout = new FillLayout();
    composite.setLayout(layout);
    text = new StyledText(composite, SWT.H_SCROLL | SWT.V_SCROLL);
    text.setEditable(false);

    int index = addPage(composite);
    setPageText(index, "Mapping");
  }

  /**
   * Creates the pages of the multi-page editor.
   */
  @Override
  protected void createPages() {
    try {
      createPage1();
      createPage2();
      createPage0();
      String editorText = editor.getDocumentProvider().getDocument(editor.getEditorInput()).get();
      InputStream is = new ByteArrayInputStream(editorText.getBytes());

      currentProject = OpenCONFIGURATORProjectMarshaller.unmarshallopenCONFIGURATORProject(is);
      for (int i = 0; i < currentProject.getNetworkConfiguration().getNodeCollection().getCN()
          .size(); i++) {
        TCN cn = currentProject.getNetworkConfiguration().getNodeCollection().getCN().get(i);
        // get object which represents the workspace
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        File workspaceDirectory = workspace.getRoot().getLocation().toFile();
        File file = new File(workspaceDirectory.getAbsolutePath()
            + System.getProperty("file.separator") + "LISIM" + System.getProperty("file.separator")
            + cn.getPathToXDC());
        System.err.println(workspaceDirectory.getAbsolutePath()
            + System.getProperty("file.separator") + "LISIM" + System.getProperty("file.separator")
            + cn.getPathToXDC());
        if (file.exists()) {
          ISO15745ProfileContainer xdd = XddMarshaller.unmarshallXDDFile(file);
          xddFiles.add(xdd);
        }
      }

    } catch (FileNotFoundException | MalformedURLException | JAXBException | SAXException
        | ParserConfigurationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /**
   * The <code>MultiPageEditorPart</code> implementation of this <code>IWorkbenchPart</code> method
   * disposes all nested editors. Subclasses may extend.
   */
  @Override
  public void dispose() {
    ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
    super.dispose();
  }

  /**
   * Saves the multi-page editor's document.
   */
  @Override
  public void doSave(IProgressMonitor monitor) {
    getEditor(2).doSave(monitor);
  }

  /**
   * Saves the multi-page editor's document as another file. Also updates the text for page 0's tab,
   * and updates this multi-page editor's input to correspond to the nested editor's.
   */
  @Override
  public void doSaveAs() {
    IEditorPart editor = getEditor(0);
    editor.doSaveAs();
    setPageText(2, editor.getTitle());
    setInput(editor.getEditorInput());
  }

  /*
   * (non-Javadoc) Method declared on IEditorPart
   */
  public void gotoMarker(IMarker marker) {
    setActivePage(0);
    IDE.gotoMarker(getEditor(0), marker);
  }

  /**
   * The <code>MultiPageEditorExample</code> implementation of this method checks that the input is
   * an instance of <code>IFileEditorInput</code>.
   */
  @Override
  public void init(IEditorSite site, IEditorInput editorInput) throws PartInitException {
    if (!(editorInput instanceof IFileEditorInput))
      throw new PartInitException("Invalid Input: Must be IFileEditorInput");
    super.init(site, editorInput);
  }

  /*
   * (non-Javadoc) Method declared on IEditorPart.
   */
  @Override
  public boolean isSaveAsAllowed() {
    return true;
  }

  /**
   * Calculates the contents of page 2 when the it is activated.
   */
  @Override
  protected void pageChange(int newPageIndex) {
    super.pageChange(newPageIndex);
    if (newPageIndex == 2) {
      sortWords();
    }
  }

  /**
   * Closes all project files on project close.
   */
  @Override
  public void resourceChanged(final IResourceChangeEvent event) {
    if (event.getType() == IResourceChangeEvent.PRE_CLOSE) {
      Display.getDefault().asyncExec(new Runnable() {
        @Override
        public void run() {
          IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
          for (int i = 0; i < pages.length; i++) {
            if (((FileEditorInput) editor.getEditorInput()).getFile().getProject()
                .equals(event.getResource())) {
              IEditorPart editorPart = pages[i].findEditor(editor.getEditorInput());
              pages[i].closeEditor(editorPart, true);
            }
          }
        }
      });
    }
  }

  /**
   * Sets the font related data to be applied to the text in page 2.
   */
  void setFont() {
    FontDialog fontDialog = new FontDialog(getSite().getShell());
    fontDialog.setFontList(text.getFont().getFontData());
    FontData fontData = fontDialog.open();
    if (fontData != null) {
      if (font != null)
        font.dispose();
      font = new Font(text.getDisplay(), fontData);
      text.setFont(font);
    }
  }

  /**
   * Sorts the words in page 0, and shows them in page 2.
   */
  void sortWords() {
    StringWriter displayText = new StringWriter();
    for (int i = 0; i < currentProject.getNetworkConfiguration().getNodeCollection().getCN().size(); i++) {
      IWorkspace workspace = ResourcesPlugin.getWorkspace();
      File workspaceDirectory = workspace.getRoot().getLocation().toFile();
      TCN cn = currentProject.getNetworkConfiguration().getNodeCollection().getCN().get(i);
      displayText.write((cn.getName()));
      displayText.write(System.getProperty("line.separator"));
      displayText.write((cn.getNodeID()));
      displayText.write(System.getProperty("line.separator"));
      displayText.write((workspaceDirectory.getAbsolutePath()
          + System.getProperty("file.separator") + cn.getPathToXDC()));
      displayText.write(System.getProperty("line.separator"));
      displayText.write(System.getProperty("line.separator"));
    }

    if (xddFiles.get(0).getISO15745Profile().get(0).getProfileBody() instanceof ProfileBodyDevicePowerlink) {
      ProfileBodyDevicePowerlink device = (ProfileBodyDevicePowerlink) xddFiles.get(0)
          .getISO15745Profile().get(0).getProfileBody();
      displayText.write(device.getDeviceIdentity().getVendorName().getValue().toString());
    }
    if (xddFiles.get(0).getISO15745Profile().get(1).getProfileBody() instanceof ProfileBodyCommunicationNetworkPowerlink) {
      ProfileBodyCommunicationNetworkPowerlink device = (ProfileBodyCommunicationNetworkPowerlink) xddFiles
          .get(0).getISO15745Profile().get(1).getProfileBody();
      displayText.write(Integer.toString(device.getApplicationLayers().getObjectList().getObject()
          .size()));
    }

    text.setText(displayText.toString());

  }

  @Override
  public Object getAdapter(Class required) {
    if (IContentOutlinePage.class.equals(required)) {
      if (myOutlinePage == null) {
        myOutlinePage = new IndustrialNetworkOutlineView(getDocumentProvider(), this);
        myOutlinePage.setInput(getEditorInput());
      }
      return myOutlinePage;
    }
    return super.getAdapter(required);
  }

  private Object getDocumentProvider() {

    return null;
  }

}

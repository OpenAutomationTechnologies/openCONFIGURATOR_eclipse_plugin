/*******************************************************************************
 * @file   IndustrialNetworkProjectSourceEditor.java
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

/**
 *
 */
package org.epsg.openconfigurator.editors.project;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.IFormPage;
import org.epsg.openconfigurator.editors.project.xml.ColorManager;
import org.epsg.openconfigurator.editors.project.xml.XMLConfiguration;
import org.epsg.openconfigurator.editors.project.xml.XMLDocumentProvider;

/**
 * The source page to manipulate the contents of the openCONFIGURATOR project.
 *
 * @author Ramakrishnan P
 *
 */
public class IndustrialNetworkProjectSourcePage extends TextEditor implements IFormPage {

  private static String ID = "org.epsg.openconfigurator.editors.IndustrialNetworkProjectSourceEditor";

  private ColorManager colorManager;

  private IndustrialNetworkProjectEditor editor;
  private int index;
  private Control partControl;
  private boolean dirty;

  public IndustrialNetworkProjectSourcePage() {
    super();
    colorManager = new ColorManager();
    setSourceViewerConfiguration(new XMLConfiguration(colorManager));
    this.setDocumentProvider(new XMLDocumentProvider());
  }

  @Override
  public boolean canLeaveThePage() {

    if (dirty) {
      // text content changed since last switch -> update data model
      editor.reloadFromText(getDocument().get());

    }
    return true;
  }

  @Override
  public void createPartControl(Composite parent) {
    super.createPartControl(parent);
    Control[] children = parent.getChildren();
    partControl = children[children.length - 1];
  }

  @Override
  public void dispose() {
    colorManager.dispose();
    super.dispose();
  }

  @Override
  public void doSave(IProgressMonitor progressMonitor) {
    super.doSave(progressMonitor);
  }

  @Override
  public FormEditor getEditor() {
    return editor;
  }

  @Override
  public String getId() {
    return ID;
  }

  @Override
  public int getIndex() {
    return index;
  }

  @Override
  public IManagedForm getManagedForm() {
    // not a form page
    return null;
  }

  @Override
  public Control getPartControl() {
    return partControl;
  }

  @Override
  public void initialize(FormEditor editor) {
    this.editor = (IndustrialNetworkProjectEditor) editor;
  }

  @Override
  public boolean isActive() {
    return equals(editor.getActivePageInstance());
  }

  @Override
  public boolean isEditor() {
    return true;
  }

  @Override
  public boolean selectReveal(Object object) {
    return false;
  }

  @Override
  public void setActive(boolean active) {
    if (active) {
      // clear dirty flag if text page is active now
      dirty = false;
    }
  }

  public void setContent(String source) {
    getDocumentProvider().getDocument(getEditorInput()).set(source);
  }

  @Override
  public void setIndex(int index) {
    this.index = index;
  }

}
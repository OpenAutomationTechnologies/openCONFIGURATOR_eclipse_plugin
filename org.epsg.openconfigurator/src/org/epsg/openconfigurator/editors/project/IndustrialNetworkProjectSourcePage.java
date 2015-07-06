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
import org.epsg.openconfigurator.editors.project.xml.XmlConfiguration;
import org.epsg.openconfigurator.editors.project.xml.XmlDocumentProvider;

/**
 * The source page to manipulate the contents of the openCONFIGURATOR project.
 *
 * @author Ramakrishnan P
 *
 */
public class IndustrialNetworkProjectSourcePage extends TextEditor implements
        IFormPage {

    /**
     * Identifier
     */
    private static String ID = "org.epsg.openconfigurator.editors.IndustrialNetworkProjectSourceEditor";

    /**
     * Color manager to decorate the source pages.
     */
    private ColorManager colorManager;

    /**
     * The editor instance.
     */
    private IndustrialNetworkProjectEditor editor;

    /**
     * Page index.
     */
    private int index;

    /**
     * The editors part control.
     */
    private Control partControl;

    /**
     * Constructor.
     */
    public IndustrialNetworkProjectSourcePage() {
        super();
        colorManager = new ColorManager();
        setSourceViewerConfiguration(new XmlConfiguration(colorManager));
        this.setDocumentProvider(new XmlDocumentProvider());
    }

    /**
     * Always true and can flip to another page. The error handling is done in
     * page change in the main editor.
     */
    @Override
    public boolean canLeaveThePage() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createPartControl(Composite parent) {
        super.createPartControl(parent);
        Control[] children = parent.getChildren();
        partControl = children[children.length - 1];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void dispose() {
        colorManager.dispose();
        super.dispose();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doSave(IProgressMonitor progressMonitor) {
        // super.doSave(progressMonitor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FormEditor getEditor() {
        return editor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {
        return IndustrialNetworkProjectSourcePage.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getIndex() {
        return index;
    }

    /**
     * All ways null. Since the source page is not a form page.
     */
    @Override
    public IManagedForm getManagedForm() {
        // not a form page
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Control getPartControl() {
        return partControl;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize(FormEditor editor) {
        this.editor = (IndustrialNetworkProjectEditor) editor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isActive() {
        return equals(editor.getActivePageInstance());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isEditor() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean selectReveal(Object object) {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setActive(boolean active) {
        // Ignore. Handled in main editor pageChange.
    }

    /**
     * Sets the content string to the source editor from the project model.
     *
     * @param source The string source.
     */
    public void setContent(final String source) {
        getDocumentProvider().getDocument(getEditorInput()).set(source);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIndex(int index) {
        this.index = index;
    }

}

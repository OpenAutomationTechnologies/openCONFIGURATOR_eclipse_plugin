/*******************************************************************************
 * @file   IndustrialNetworkContribute.java
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

package org.epsg.openconfigurator.editors;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.ide.IDEActionFactory;
import org.eclipse.ui.part.MultiPageEditorActionBarContributor;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;

/**
 * Manages the installation/deinstallation of global actions for multi-page
 * editors. Responsible for the redirection of global actions to the active
 * editor. Multi-page contributor replaces the contributors for the individual
 * editors in the multi-page editor.
 */
public class IndustrialNetworkContribute extends
        MultiPageEditorActionBarContributor {
    private IEditorPart activeEditorPart;
    private Action sampleAction;

    /**
     * Creates a multi-page contributor.
     */
    public IndustrialNetworkContribute() {
        super();
        createActions();
    }

    @Override
    public void contributeToMenu(IMenuManager manager) {
        IMenuManager menu = new MenuManager("open&CONFIGURATOR");
        manager.prependToGroup(IWorkbenchActionConstants.MB_ADDITIONS, menu);
        menu.add(sampleAction);
    }

    @Override
    public void contributeToToolBar(IToolBarManager manager) {
        manager.add(new Separator());
        manager.add(sampleAction);
    }

    private void createActions() {
        sampleAction = new Action() {
            @Override
            public void run() {
                MessageDialog.openInformation(null, "openCONFIGURATOR",
                        "Sample Action Executed");
            }
        };
        sampleAction.setText("Add Controlled Node");
        sampleAction.setToolTipText("Sample Action tool tip");
        sampleAction.setImageDescriptor(PlatformUI.getWorkbench()
                .getSharedImages()
                .getImageDescriptor(IDE.SharedImages.IMG_OBJS_TASK_TSK));
    }

    /**
     * Returns the action registed with the given text editor.
     *
     * @return IAction or null if editor is null.
     */
    protected IAction getAction(ITextEditor editor, String actionID) {
        return (editor == null ? null : editor.getAction(actionID));
    }

    /*
     * (non-JavaDoc) Method declared in
     * AbstractMultiPageEditorActionBarContributor.
     */
    @Override
    public void setActivePage(IEditorPart part) {
        if (activeEditorPart == part) {
            return;
        }

        activeEditorPart = part;

        IActionBars actionBars = getActionBars();
        if (actionBars != null) {

            ITextEditor editor = (part instanceof ITextEditor) ? (ITextEditor) part
                    : null;

            actionBars.setGlobalActionHandler(ActionFactory.DELETE.getId(),
                    getAction(editor, ITextEditorActionConstants.DELETE));
            actionBars.setGlobalActionHandler(ActionFactory.UNDO.getId(),
                    getAction(editor, ITextEditorActionConstants.UNDO));
            actionBars.setGlobalActionHandler(ActionFactory.REDO.getId(),
                    getAction(editor, ITextEditorActionConstants.REDO));
            actionBars.setGlobalActionHandler(ActionFactory.CUT.getId(),
                    getAction(editor, ITextEditorActionConstants.CUT));
            actionBars.setGlobalActionHandler(ActionFactory.COPY.getId(),
                    getAction(editor, ITextEditorActionConstants.COPY));
            actionBars.setGlobalActionHandler(ActionFactory.PASTE.getId(),
                    getAction(editor, ITextEditorActionConstants.PASTE));
            actionBars.setGlobalActionHandler(ActionFactory.SELECT_ALL.getId(),
                    getAction(editor, ITextEditorActionConstants.SELECT_ALL));
            actionBars.setGlobalActionHandler(ActionFactory.FIND.getId(),
                    getAction(editor, ITextEditorActionConstants.FIND));
            actionBars.setGlobalActionHandler(
                    IDEActionFactory.BOOKMARK.getId(),
                    getAction(editor, IDEActionFactory.BOOKMARK.getId()));
            actionBars.updateActionBars();
        }
    }
}

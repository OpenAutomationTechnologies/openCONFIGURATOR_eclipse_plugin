/*******************************************************************************
 * @file   LinkWithEditorPartListener.java
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

package org.epsg.openconfigurator.views;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.IWorkbenchPartReference;

/**
 * Links the editor with the view by participating in the editor lifecycle
 * events.
 *
 * @author Ramakrishnan P
 *
 */
public class LinkWithEditorPartListener implements IPartListener2 {
    private final ILinkedWithEditorView view;

    public LinkWithEditorPartListener(ILinkedWithEditorView view) {
        this.view = view;
    }

    @Override
    public void partActivated(IWorkbenchPartReference ref) {

        if (ref.getPart(true) instanceof IEditorPart) {

            view.editorActivated((IEditorPart) ref.getPart(true));
        }
    }

    @Override
    public void partBroughtToTop(IWorkbenchPartReference ref) {

        if (ref.getPart(true) instanceof IEditorPart) {
            IEditorPart editor = view.getViewSite().getPage().getActiveEditor();
            if (editor != null) {
                view.editorActivated(editor);
            }
        }

        // if (ref.getPart(true) == view) {
        // view.editorActivated(view.getViewSite().getPage().getActiveEditor());
        // }
    }

    @Override
    public void partClosed(IWorkbenchPartReference ref) {
        // Working
        if (ref.getPart(true) instanceof IEditorPart) {
            view.editorClosed((IEditorPart) ref.getPart(true));
        }

        // if (ref.getPart(true) == view) {
        // IEditorPart editor = view.getViewSite().getPage().getActiveEditor();
        // if (editor != null) {
        // view.editorClosed(editor);
        // }
        // }
    }

    @Override
    public void partDeactivated(IWorkbenchPartReference ref) {
        // nothing to do when the editor is minimized or left focus.
    }

    @Override
    public void partHidden(IWorkbenchPartReference ref) {
    }

    @Override
    public void partInputChanged(IWorkbenchPartReference ref) {

        if (ref.getPart(true) instanceof IEditorPart) {
            IEditorPart editor = view.getViewSite().getPage().getActiveEditor();
            if (editor != null) {
                view.editorActivated(editor);
            }
        }

        // if (ref.getPart(true) == view) {
        // IEditorPart editor = view.getViewSite().getPage().getActiveEditor();
        // if (editor != null) {
        // view.editorActivated(editor);
        // }
        // }
    }

    @Override
    public void partOpened(IWorkbenchPartReference ref) {

        if (ref.getPart(true) instanceof IEditorPart) {
            System.out.println("Part opened: " + ref);
            IEditorPart editor = view.getViewSite().getPage().getActiveEditor();
            if (editor != null) {
                view.editorActivated(editor);
            }
        }

        // if (ref.getPart(true) == view) {
        // IEditorPart editor = view.getViewSite().getPage().getActiveEditor();
        // if (editor != null) {
        // view.editorActivated(editor);
        // }
        // }
    }

    @Override
    public void partVisible(IWorkbenchPartReference ref) {

        if (ref.getPart(true) instanceof IEditorPart) {
            System.out.println("Part Visible: " + ref);
            IEditorPart editor = view.getViewSite().getPage().getActiveEditor();
            if (editor != null) {
                view.editorActivated(editor);
            }
        }

        // if (ref.getPart(true) == view) {
        // IEditorPart editor = view.getViewSite().getPage().getActiveEditor();
        // if (editor != null) {
        // view.editorActivated(editor);
        // }
        // }
    }

}

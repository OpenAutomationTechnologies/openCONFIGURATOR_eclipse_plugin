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

    /**
     * Notifies POWERLINK network view with state of editor as input.
     *
     * @param ref IWorkbenchPartReference
     */
    public void notifyView(IWorkbenchPartReference ref) {
        if (ref.getPart(true) instanceof IEditorPart) {
            System.out.println("partBroughtToTop: " + ref);
            IEditorPart editor = view.getViewSite().getPage().getActiveEditor();
            if (editor != null) {
                view.editorActivated(editor);
            }
        }
    }

    /**
     * Notifies the activated state of editor to view.
     */
    @Override
    public void partActivated(IWorkbenchPartReference ref) {
        notifyView(ref);
    }

    /**
     * Notifies view if the editor is brought to top.
     */
    @Override
    public void partBroughtToTop(IWorkbenchPartReference ref) {
        notifyView(ref);
    }

    /**
     * Notifies the closed state of editor to view.
     */
    @Override
    public void partClosed(IWorkbenchPartReference ref) {
        notifyView(ref);
    }

    /**
     * Notifies the deactivated state of editor to view.
     */
    @Override
    public void partDeactivated(IWorkbenchPartReference ref) {
        notifyView(ref);
    }

    /**
     * Notifies the hidden state of editor to view.
     */
    @Override
    public void partHidden(IWorkbenchPartReference ref) {
        notifyView(ref);
    }

    /**
     * Notifies view if the input change occurs in the editor.
     */
    @Override
    public void partInputChanged(IWorkbenchPartReference ref) {
        notifyView(ref);
    }

    /**
     * Notifies the opened state of editor to view.
     */
    @Override
    public void partOpened(IWorkbenchPartReference ref) {
        notifyView(ref);
    }

    /**
     * Notifies the visible state of editor to view.
     */
    @Override
    public void partVisible(IWorkbenchPartReference ref) {
        notifyView(ref);
    }

}

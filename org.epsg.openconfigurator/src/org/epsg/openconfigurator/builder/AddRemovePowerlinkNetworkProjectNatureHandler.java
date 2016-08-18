/*******************************************************************************
 * @file   AddRemovePowerlinkNetworkProjectNatureHandler.java
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

package org.epsg.openconfigurator.builder;

import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

/**
 * Handles to Add or Remove the PowerlinkNetworkProject
 *
 * @author Ramakrishnan P
 *
 */
public class AddRemovePowerlinkNetworkProjectNatureHandler
        extends AbstractHandler {

    /**
     * Toggles sample nature on a project
     *
     * @param project to have sample nature added or removed
     */
    private static void toggleNature(IProject project) throws CoreException {
        IProjectDescription description = project.getDescription();
        String[] natures = description.getNatureIds();

        for (int i = 0; i < natures.length; ++i) {
            if (PowerlinkNetworkProjectNature.NATURE_ID.equals(natures[i])) {
                // Remove the nature
                String[] newNatures = new String[natures.length - 1];
                System.arraycopy(natures, 0, newNatures, 0, i);
                System.arraycopy(natures, i + 1, newNatures, i,
                        natures.length - i - 1);
                description.setNatureIds(newNatures);
                project.setDescription(description, null);
                return;
            }
        }

        // Add the nature
        String[] newNatures = new String[natures.length + 1];
        System.arraycopy(natures, 0, newNatures, 0, natures.length);
        newNatures[natures.length] = PowerlinkNetworkProjectNature.NATURE_ID;
        description.setNatureIds(newNatures);
        project.setDescription(description, null);
    }

    @Override
    public Object execute(ExecutionEvent event) throws ExecutionException {
        ISelection selection = HandlerUtil.getCurrentSelection(event);
        //
        if (selection instanceof IStructuredSelection) {
            for (Iterator<?> it = ((IStructuredSelection) selection)
                    .iterator(); it.hasNext();) {
                Object element = it.next();
                IProject project = null;
                if (element instanceof IProject) {
                    project = (IProject) element;
                } else if (element instanceof IAdaptable) {
                    project = ((IAdaptable) element).getAdapter(IProject.class);
                }
                if (project != null) {
                    try {
                        toggleNature(project);
                    } catch (CoreException e) {
                        // TODO log something
                        throw new ExecutionException("Failed to toggle nature",
                                e);
                    }
                }
            }
        }

        return null;
    }

}

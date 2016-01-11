/*******************************************************************************
 * @file   PdoNodeIdComboSelectionChangedListener.java
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

package org.epsg.openconfigurator.views.mapping;

import java.io.IOException;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.epsg.openconfigurator.console.OpenConfiguratorMessageConsole;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.model.PdoChannel;
import org.epsg.openconfigurator.model.PowerlinkObject;
import org.epsg.openconfigurator.model.PowerlinkSubobject;
import org.epsg.openconfigurator.util.OpenConfiguratorLibraryUtils;
import org.jdom2.JDOMException;

/**
 * Listener to handle the Node Id combobox selection changes events.
 *
 * @author Ramakrishnan P
 *
 */
/* Default */ class PdoNodeIdComboSelectionChangedListener
        implements ISelectionChangedListener {

    private ComboViewer channelComboViewer;

    public PdoNodeIdComboSelectionChangedListener(
            ComboViewer channelComboViewer) {
        this.channelComboViewer = channelComboViewer;
    }

    @Override
    public void selectionChanged(SelectionChangedEvent event) {
        ISelection channelSelection = channelComboViewer.getSelection();
        if (channelSelection instanceof IStructuredSelection) {
            IStructuredSelection stSelection = (IStructuredSelection) channelSelection;
            Object selectedObject = stSelection.getFirstElement();

            if (selectedObject instanceof PdoChannel) {
                PdoChannel pdoChannel = (PdoChannel) selectedObject;

                ISelection selection = event.getSelection();

                if (selection instanceof IStructuredSelection) {

                    IStructuredSelection ss = (IStructuredSelection) selection;
                    if (ss.size() > 1) {
                        System.err.println("Multiple selection not handled.");
                        return;
                    }

                    Object selectedObj = ss.getFirstElement();

                    if (selectedObj == null) {
                        MappingView.showMessageWindow(MessageDialog.ERROR,
                                "Select any node.");
                        return;
                    }

                    if (selectedObj instanceof Node) {
                        Node node = (Node) selectedObj;

                        PowerlinkObject commParam = pdoChannel
                                .getCommunicationParam();

                        PowerlinkSubobject subObj = commParam
                                .getSubObject((short) 01);
                        if (subObj == null) {
                            MappingView.showMessageWindow(MessageDialog.ERROR,
                                    "Object " + commParam.getObjectIndex()
                                            + "/0x1 does not exists!");
                        }

                        Result res = OpenConfiguratorLibraryUtils
                                .setSubObjectActualValue(subObj,
                                        node.getNodeIdString());
                        if (!res.IsSuccessful()) {
                            MappingView.showMessage(res);
                            return;
                        }

                        try {
                            subObj.setActualValue(node.getNodeIdString(), true);
                        } catch (JDOMException | IOException e) {
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printErrorMessage(e.getMessage());
                            e.printStackTrace();
                        }
                        try {
                            pdoChannel.getNode().getProject().refreshLocal(
                                    IResource.DEPTH_INFINITE,
                                    new NullProgressMonitor());
                        } catch (CoreException e) {
                            System.err.println(
                                    "unable to refresh the resource due to "
                                            + e.getCause().getMessage());
                        }
                    }
                }
            }
        }
    }
}

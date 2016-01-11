/*******************************************************************************
 * @file   PdoActionsDownButtonSelectionListener.java
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

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.epsg.openconfigurator.console.OpenConfiguratorMessageConsole;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.model.PdoChannel;
import org.epsg.openconfigurator.model.PowerlinkSubobject;
import org.epsg.openconfigurator.util.OpenConfiguratorLibraryUtils;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectUtils;
import org.jdom2.JDOMException;

/**
 * Selection listener to handle the down button selection change events.
 *
 * @author Ramakrishnan P
 *
 */
/* Default */ class PdoActionsDownButtonSelectionListener
        extends SelectionAdapter {
    private ComboViewer channelComboViewer;
    private TableViewer tableViewer;

    public PdoActionsDownButtonSelectionListener(ComboViewer channelComboViewer,
            TableViewer tableViewer) {
        this.tableViewer = tableViewer;
        this.channelComboViewer = channelComboViewer;
    }

    @Override
    public void widgetSelected(SelectionEvent e) {
        // Get the selected channel.
        ISelection selection = channelComboViewer.getSelection();
        if (selection instanceof IStructuredSelection) {
            IStructuredSelection stSelection = (IStructuredSelection) selection;
            Object selectedObject = stSelection.getFirstElement();
            if (selectedObject instanceof PdoChannel) {
                PdoChannel pdoChannel = (PdoChannel) selectedObject;

                // Get the selected mapping object.
                ISelection rowSelection = tableViewer.getSelection();
                if (rowSelection instanceof IStructuredSelection) {
                    IStructuredSelection rowStSelection = (IStructuredSelection) rowSelection;
                    Object selectedRowObject = rowStSelection.getFirstElement();
                    if (selectedRowObject instanceof PowerlinkSubobject) {
                        PowerlinkSubobject currentRowData = (PowerlinkSubobject) selectedRowObject;
                        PowerlinkSubobject nextRowData = pdoChannel
                                .getNextSubObject(currentRowData);
                        if (nextRowData != null) {

                            Result res = OpenConfiguratorLibraryUtils
                                    .moveMappingObject(pdoChannel,
                                            currentRowData.getSubobjecId(),
                                            nextRowData.getSubobjecId());
                            if (!res.IsSuccessful()) {
                                System.err.println(
                                        "moveMappingObject to the next position Error:"
                                                + res.GetErrorType()
                                                + " Message:"
                                                + res.GetErrorMessage());
                                MappingView.showMessage(res);
                            }

                            try {
                                OpenConfiguratorProjectUtils
                                        .updatePdoChannelActualValue(
                                                pdoChannel);
                            } catch (JDOMException | IOException e1) {
                                OpenConfiguratorMessageConsole.getInstance()
                                        .printErrorMessage(e1.getMessage());
                                e1.printStackTrace();
                            }

                            ISelection nextRowDataSelection = new StructuredSelection(
                                    nextRowData);
                            tableViewer.setSelection(nextRowDataSelection);

                            tableViewer.refresh();
                        }
                    }
                }
            }
        }
    }
}

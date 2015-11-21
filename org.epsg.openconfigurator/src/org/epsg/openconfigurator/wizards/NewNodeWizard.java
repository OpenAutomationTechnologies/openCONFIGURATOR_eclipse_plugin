/*******************************************************************************
 * @file   NewNodeWizard.java
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

package org.epsg.openconfigurator.wizards;

import java.io.IOException;
import java.util.Map;

import org.eclipse.jface.wizard.Wizard;
import org.epsg.openconfigurator.lib.wrapper.ErrorCode;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.util.OpenConfiguratorLibraryUtils;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectUtils;
import org.epsg.openconfigurator.util.PluginErrorDialogUtils;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNetworkConfiguration;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNodeCollection;

/**
 * Wizard dialog to add a new POWERLINK node.
 *
 * @author Ramakrishnan P
 *
 */
public class NewNodeWizard extends Wizard {

    private static final String WINDOW_TITLE = "POWERLINK node wizard";
    private static final String ERROR_WHILE_COPYING_XDD = "Error occurred while copying the configuration file.";

    /**
     * Add new node wizard page.
     */
    private final AddControlledNodeWizardPage addNodePage;

    /**
     * Selected node object. The new node will be added below this node.
     */
    private Node selectedNodeObj;
    private Map<Short, Node> nodeList;
    private TNodeCollection nodeCollection;

    public NewNodeWizard(Map<Short, Node> nodeList, Node selectedNodeObj) {
        if (selectedNodeObj == null) {
            System.err.println("Invalid node selection");
        }

        this.selectedNodeObj = selectedNodeObj;
        this.nodeList = nodeList;
        Object nodeModel = this.selectedNodeObj.getNodeModel();
        if (nodeModel instanceof TNetworkConfiguration) {
            TNetworkConfiguration net = (TNetworkConfiguration) nodeModel;
            nodeCollection = net.getNodeCollection();
        }

        setWindowTitle(WINDOW_TITLE);
        addNodePage = new AddControlledNodeWizardPage(nodeCollection);
    }

    @Override
    public void addPages() {
        super.addPages();
        addPage(addNodePage);
    }

    @Override
    public boolean canFinish() {
        return super.canFinish() && addNodePage.isPageComplete();
    }

    /**
     * Checks for errors in the wizard pages.
     *
     * @return <code>True</code> if errors present, <code>False</code>
     *         otherwise.
     */
    public boolean hasErrors() {
        return addNodePage.hasErrors();
    }

    @Override
    public boolean performFinish() {

        Node newNode = new Node(nodeList, selectedNodeObj.getProjectXml(),
                addNodePage.getNode(), addNodePage.getXddModel());

        Result res = OpenConfiguratorLibraryUtils
                .addNode(newNode.getNetworkId(), newNode.getNodeId(), newNode);
        if (res.IsSuccessful()) {

            try {
                OpenConfiguratorProjectUtils.addNode(nodeList, nodeCollection,
                        newNode);
            } catch (IOException e) {
                PluginErrorDialogUtils
                        .displayErrorMessageDialog(
                                org.epsg.openconfigurator.Activator.getDefault()
                                        .getWorkbench()
                                        .getActiveWorkbenchWindow().getShell(),
                                ERROR_WHILE_COPYING_XDD, null);
            }
        } else {
            PluginErrorDialogUtils.displayErrorMessageDialog(
                    org.epsg.openconfigurator.Activator.getDefault()
                            .getWorkbench().getActiveWorkbenchWindow()
                            .getShell(),
                    OpenConfiguratorLibraryUtils.getErrorMessage(res), null);

            // Try removing the node.
            // FIXME: do we need this?
            res = OpenConfiguratorLibraryUtils.removeNode(newNode);
            if (!res.IsSuccessful()) {
                if (res.GetErrorType() != ErrorCode.NODE_DOES_NOT_EXIST) {
                    // Show or print error message.
                    System.err.println("ERROR occured while removin the node. "
                            + OpenConfiguratorLibraryUtils
                                    .getErrorMessage(res));
                }
            }
        }
        return true;
    }
}

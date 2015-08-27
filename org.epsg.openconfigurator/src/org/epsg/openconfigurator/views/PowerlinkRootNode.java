/*******************************************************************************
 * @file   PowerlinkRootNode.java
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

import java.util.ArrayList;
import java.util.Map;

import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.util.IPowerlinkConstants;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectUtils;
import org.epsg.openconfigurator.xmlbinding.projectfile.TCN;
import org.epsg.openconfigurator.xmlbinding.projectfile.TRMN;

/**
 * Wrapper class to act as a POWERLINK root node.
 *
 * @author Ramakrishnan P
 *
 */
public class PowerlinkRootNode {
    private Map<Short, Node> nodeCollection;

    PowerlinkRootNode() {
    }

    /**
     * @return The list of CN nodes available in the project.
     */
    public ArrayList<Node> getCnNodeList() {
        ArrayList<Node> returnNodeList = new ArrayList<Node>();
        ArrayList<Node> nodeList = new ArrayList<Node>(nodeCollection.values());

        for (Node node : nodeList) {
            Object nodeModel = node.getNodeModel();
            if (nodeModel instanceof TCN) {
                returnNodeList.add(node);
            }
        }
        return returnNodeList;
    }

    /**
     * @return The MN node if available in the project, null otherwise.
     */
    public Node getMN() {
        Node mnNode = nodeCollection
                .get(new Short(IPowerlinkConstants.MN_DEFAULT_NODE_ID));
        return mnNode;
    }

    public Map<Short, Node> getNodeCollection() {
        return nodeCollection;
    }

    /**
     * Returns the list of MN and CN nodes available in the project.
     *
     * @param inputElement The parent instance.
     * @return The nodes list.
     */
    public Object[] getNodeList(Object inputElement) {
        ArrayList<Node> returnNodeList = new ArrayList<Node>();
        if (inputElement instanceof PowerlinkRootNode) {

            ArrayList<Node> nodeList = new ArrayList<Node>(
                    nodeCollection.values());

            Node mnNode = nodeCollection
                    .get(new Short(IPowerlinkConstants.MN_DEFAULT_NODE_ID));
            if (mnNode != null) {
                returnNodeList.add(mnNode);
            }

            for (Node node : nodeList) {
                Object nodeModel = node.getNodeModel();
                if (nodeModel instanceof TCN) {
                    returnNodeList.add(node);
                }

                // MN/NetworkConfiguration instance is already added.
            }
        }
        return returnNodeList.toArray();
    }

    /**
     * @return Returns the list of RMNs available in the project.
     */
    public ArrayList<Node> getRmnNodeList() {
        ArrayList<Node> returnNodeList = new ArrayList<Node>();
        ArrayList<Node> nodeList = new ArrayList<Node>(nodeCollection.values());

        for (Node node : nodeList) {
            Object nodeModel = node.getNodeModel();
            if (nodeModel instanceof TRMN) {
                returnNodeList.add(node);
            }
        }
        return returnNodeList;
    }

    /**
     * Removes the node from the project.
     *
     * @param node The node to be removed.
     * @return <code>True</code> if successful and <code>False</code> otherwise.
     */
    public boolean removeNode(Node node) {
        return OpenConfiguratorProjectUtils.deleteNode(nodeCollection, node,
                null);
    }

    /**
     * Updates the new node collection.
     *
     * @param nodeCollection The new node collection
     */
    public void setNodeCollection(Map<Short, Node> nodeCollection) {
        this.nodeCollection = nodeCollection;
    }

    /**
     * Enables or disables the node.
     *
     * @param node The node to enabled or disabled.
     *
     * @return <code>True</code> if successful and <code>False</code> otherwise.
     */
    public boolean toggleEnableDisable(Node node) {
        boolean retVal = false;
        if (node == null) {
            return retVal;
        }

        Object nodeObjectModel = node.getNodeModel();
        if (nodeObjectModel instanceof TCN) {
            TCN cnModel = (TCN) nodeObjectModel;
            cnModel.setEnabled(!cnModel.isEnabled());
            OpenConfiguratorProjectUtils.updateNodeAttributeValue(node,
                    "enabled", String.valueOf(cnModel.isEnabled()));
        } else {
            System.err.println("Enable disable not supported for nodeType"
                    + nodeObjectModel);
        }

        return retVal;
    }
}

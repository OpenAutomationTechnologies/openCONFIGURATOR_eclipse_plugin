/*******************************************************************************
 * @file   PowerlinkRootNode.java
 *
 * @author Ramakrishnan Periyakaruppan, Kalycito Infotech Private Limited.
 *
 * @copyright (c) 2016, Kalycito Infotech Private Limited
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

package org.epsg.openconfigurator.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.epsg.openconfigurator.console.OpenConfiguratorMessageConsole;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.util.IPowerlinkConstants;
import org.epsg.openconfigurator.util.OpenConfiguratorLibraryUtils;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectUtils;
import org.epsg.openconfigurator.util.XddMarshaller;
import org.epsg.openconfigurator.xmlbinding.projectfile.TCN;
import org.epsg.openconfigurator.xmlbinding.projectfile.TMN;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNetworkConfiguration;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNodeCollection;
import org.epsg.openconfigurator.xmlbinding.projectfile.TRMN;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745ProfileContainer;
import org.epsg.openconfigurator.xmloperation.JDomUtil;
import org.epsg.openconfigurator.xmloperation.ProjectJDomOperation;
import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

/**
 * Wrapper class to act as a POWERLINK root node.
 *
 * @author Ramakrishnan P
 *
 */
public class PowerlinkRootNode {

    private static final String INVALID_XDC_CONTENTS_ERROR = "Invalid XDD/XDC exists in the project. Node configuration specified for the Node: {0} is invalid. XDC Path: {1}";
    private static final String XDC_FILE_NOT_FOUND_ERROR = "XDD/XDC file for the node: {0} does not exists in the project. XDC Path: {1}";

    private Map<Short, Node> nodeCollection = new HashMap<Short, Node>();

    public PowerlinkRootNode() {
    }

    /**
     * Add node into the node collection and the node list.
     *
     * @param nodeCollectionModel The TNodeCollection instance.
     * @param node New node.
     * @return <code>True</code> if successful and <code>False</code> otherwise.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public synchronized boolean addNode(TNodeCollection nodeCollectionModel,
            Node node) throws IOException, JDOMException {

        Object nodeModel = node.getNodeModel();
        if (nodeModel instanceof TCN) {
            TCN cn = (TCN) nodeModel;
            nodeCollectionModel.getCN().add(cn);
        } else if (nodeModel instanceof TRMN) {
            TRMN rmn = (TRMN) nodeModel;
            nodeCollectionModel.getRMN().add(rmn);
        } else {
            // Invalid node
            return false;
        }

        nodeCollection.put(new Short(node.getNodeId()), node);

        String projectXmlLocation = node.getProjectXml().getLocation()
                .toString();
        File xmlFile = new File(projectXmlLocation);
        org.jdom2.Document document = JDomUtil.getXmlDocument(xmlFile);

        ProjectJDomOperation.addNode(document, node);

        JDomUtil.writeToXmlFile(document, xmlFile);

        // Updates modified time in project file.
        OpenConfiguratorProjectUtils.updatemodifiedTime(node,
                OpenConfiguratorProjectUtils.MODIFIED_ON_ATTRIBUTE,
                OpenConfiguratorProjectUtils.getCurrentTimeandDate());

        try {
            node.getProject().refreshLocal(IResource.DEPTH_INFINITE,
                    new NullProgressMonitor());
        } catch (CoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;
    }

    /**
     * Clear collection of nodes and set to empty.
     */
    public synchronized void clearNodeCollection() {
        nodeCollection.clear();
    }

    /**
     * @return The list of CN nodes available in the project.
     */
    public synchronized ArrayList<Node> getCnNodeList() {
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
    public synchronized Node getMN() {
        Node mnNode = nodeCollection
                .get(new Short(IPowerlinkConstants.MN_DEFAULT_NODE_ID));
        return mnNode;
    }

    /**
     * @return NodeCollection instance.
     */
    public synchronized Map<Short, Node> getNodeCollection() {
        return nodeCollection;
    }

    /**
     * @return The number of nodes available.
     */
    public synchronized int getNodeCount() {
        return nodeCollection.size();
    }

    /**
     * Returns the list of MN and CN nodes available in the project.
     *
     * @param inputElement The parent instance.
     * @return The nodes list.
     */
    public synchronized Object[] getNodeList(Object inputElement) {
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
    public synchronized ArrayList<Node> getRmnNodeList() {
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
     * Import the nodes available in the project XML file. Add the created node
     * into the nodeCollection.
     *
     * @param projectFile The project file instance.
     * @param networkCfg The network configuration instance from the project XML
     *            file.
     * @param monitor The monitor instance to display the current status.
     * @return Status of the import nodes.
     */
    public synchronized Status importNodes(IFile projectFile,
            TNetworkConfiguration networkCfg, IProgressMonitor monitor) {
        Node processingNode = new Node();

        try {
            // MN section
            {
                TMN mnNode = networkCfg.getNodeCollection().getMN();

                monitor.subTask("Import MN node XDC:" + mnNode.getName() + "("
                        + mnNode.getNodeID() + ")");

                File mnXddFile = new File(projectFile.getProject().getLocation()
                        + File.separator + mnNode.getPathToXDC());
                System.out.println(
                        "MN XDD file path:" + mnXddFile.getAbsolutePath());
                processingNode = new Node(this, projectFile, networkCfg, null);

                ISO15745ProfileContainer xdd = XddMarshaller
                        .unmarshallXDDFile(mnXddFile);
                // add TNetworkManagement Not TMN
                Node newNode = new Node(this, projectFile, networkCfg, xdd);
                processingNode = newNode;

                Result res = OpenConfiguratorLibraryUtils.addNode(newNode);
                if (res.IsSuccessful()) {
                    nodeCollection.put(newNode.getNodeId(), newNode);
                } else {
                    return new Status(IStatus.ERROR,
                            org.epsg.openconfigurator.Activator.PLUGIN_ID,
                            OpenConfiguratorLibraryUtils.getErrorMessage(res),
                            null);
                }
            }

            // Import the CN nodes
            for (TCN cnNode : networkCfg.getNodeCollection().getCN()) {

                if (monitor.isCanceled()) {
                    return new Status(IStatus.OK,
                            org.epsg.openconfigurator.Activator.PLUGIN_ID,
                            "Cancelled", null);
                }

                monitor.subTask("Import CN node XDC:" + cnNode.getName() + "("
                        + cnNode.getNodeID() + ")");

                File cnXddFile = new File(projectFile.getProject().getLocation()
                        + File.separator + cnNode.getPathToXDC());
                System.out.println(
                        "CN XDD file path:" + cnXddFile.getAbsolutePath());

                processingNode = new Node(this, projectFile, cnNode, null);
                try {

                    ISO15745ProfileContainer xdd = XddMarshaller
                            .unmarshallXDDFile(cnXddFile);

                    Node newNode = new Node(this, projectFile, cnNode, xdd);

                    processingNode = newNode;

                    Result res = OpenConfiguratorLibraryUtils.addNode(newNode);
                    if (res.IsSuccessful()) {
                        nodeCollection.put(new Short(newNode.getNodeId()),
                                newNode);
                    } else {
                        return new Status(IStatus.ERROR,
                                org.epsg.openconfigurator.Activator.PLUGIN_ID,
                                OpenConfiguratorLibraryUtils
                                        .getErrorMessage(res),
                                null);
                    }

                } catch (JAXBException | SAXException
                        | ParserConfigurationException | FileNotFoundException
                        | UnsupportedEncodingException e) {
                    OpenConfiguratorMessageConsole.getInstance()
                            .printErrorMessage(
                                    "XDD/XDC file is invalid for the node "
                                            + "'" + cnNode.getName() + "("
                                            + cnNode.getNodeID() + ")" + "'");
                }
                nodeCollection.put(new Short(processingNode.getNodeId()),
                        processingNode);
                monitor.worked(1);
            }

            // Import the RMN nodes
            for (TRMN rmnNode : networkCfg.getNodeCollection().getRMN()) {

                if (monitor.isCanceled()) {
                    return new Status(IStatus.OK,
                            org.epsg.openconfigurator.Activator.PLUGIN_ID,
                            "Cancelled", null);
                }

                monitor.subTask("Import RMN node XDC:" + rmnNode.getName() + "("
                        + rmnNode.getNodeID() + ")");

                File rmnXddFile = new File(
                        projectFile.getProject().getLocation() + File.separator
                                + rmnNode.getPathToXDC());
                System.out.println(
                        "RMN XDD file path:" + rmnXddFile.getAbsolutePath());
                processingNode = new Node(this, projectFile, rmnNode, null);
                try {
                    ISO15745ProfileContainer xdd = XddMarshaller
                            .unmarshallXDDFile(rmnXddFile);

                    Node newNode = new Node(this, projectFile, rmnNode, xdd);
                    processingNode = newNode;

                    Result res = OpenConfiguratorLibraryUtils.addNode(newNode);
                    if (res.IsSuccessful()) {
                        nodeCollection.put(new Short(newNode.getNodeId()),
                                newNode);
                    } else {
                        return new Status(IStatus.ERROR,
                                org.epsg.openconfigurator.Activator.PLUGIN_ID,
                                OpenConfiguratorLibraryUtils
                                        .getErrorMessage(res),
                                null);
                    }
                } catch (JAXBException | SAXException
                        | ParserConfigurationException | FileNotFoundException
                        | UnsupportedEncodingException e) {
                    OpenConfiguratorMessageConsole.getInstance()
                            .printErrorMessage(e.getCause().getMessage()
                                    + " for the node " + "'" + rmnNode.getName()
                                    + "(" + rmnNode.getNodeID() + ")" + "'");
                }
                nodeCollection.put(new Short(processingNode.getNodeId()),
                        processingNode);
                monitor.worked(1);
            }

        } catch (JAXBException | SAXException | ParserConfigurationException
                | UnsupportedEncodingException e) {
            e.printStackTrace();

            String xdcPath = processingNode.getPathToXDC();

            String errorMessage = MessageFormat.format(
                    INVALID_XDC_CONTENTS_ERROR,
                    processingNode.getNodeIDWithName(), xdcPath);
            return new Status(IStatus.ERROR,
                    org.epsg.openconfigurator.Activator.PLUGIN_ID, errorMessage,
                    e);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            String xdcPath = processingNode.getPathToXDC();

            String errorMessage = MessageFormat.format(XDC_FILE_NOT_FOUND_ERROR,
                    processingNode.getNodeIDWithName(), xdcPath);

            return new Status(IStatus.ERROR,
                    org.epsg.openconfigurator.Activator.PLUGIN_ID, errorMessage,
                    e1);
        }

        monitor.done();
        return new Status(IStatus.OK,
                org.epsg.openconfigurator.Activator.PLUGIN_ID, "OK", null);
    }

    /**
     * Checks if the node id already available in the project.
     *
     * @param nodeIdTobeChecked The node Id to be checked.
     *
     * @return <code> True</code> if already available. <code>False</code>
     *         otherwise.
     */
    public synchronized boolean isNodeIdAlreadyAvailable(
            short nodeIdTobeChecked) {
        Set<Short> nodeSet = nodeCollection.keySet();
        boolean nodeIdAvailable = false;
        for (Short tempNodeId : nodeSet) {
            if (tempNodeId.shortValue() == nodeIdTobeChecked) {
                nodeIdAvailable = true;
            }
        }
        return nodeIdAvailable;
    }

    /**
     * Check availability of XDD/XDC file for the node and persist the node
     * data.
     *
     * @param monitor The monitor instance to display the current status.
     * @return Result from the persist node data.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public synchronized Result persistNodes(IProgressMonitor monitor)
            throws JDOMException, IOException {
        Result res = new Result();

        for (Map.Entry<Short, Node> entry : nodeCollection.entrySet()) {

            Node node = entry.getValue();
            if (node == null) {
                System.err.println("Node" + entry.getKey() + " is null");
                continue;
            }

            if (node.hasXdd()) {
                OpenConfiguratorMessageConsole.getInstance().printErrorMessage(
                        "The XDD/XDC file cannot be found for the node " + "'"
                                + node.getNodeIDWithName() + "'",
                        node.getProject().getName());
                continue;
            }

            if (monitor.isCanceled()) {
                throw new OperationCanceledException(
                        "Operation cancelled by user. Not all data is saved to the XDC.");
            }

            System.out.println(
                    entry.getKey() + "----" + node.getAbsolutePathToXdc());

            monitor.subTask("Updating node:" + node.getNodeIDWithName() + " ->"
                    + node.getPathToXDC());

            res = OpenConfiguratorProjectUtils.persistNodeData(node);
            if (!res.IsSuccessful()) {
                // Continue operation for other nodes
                continue;
            }

            OpenConfiguratorProjectUtils.updateNodeAssignmentValues(node);
            monitor.worked(1);
        }
        return res;
    }

    /**
     * Removes the node from the project.
     *
     * @param node The node to be removed.
     * @return <code>True</code> if successful and <code>False</code> otherwise.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public synchronized boolean removeNode(Node node)
            throws JDOMException, IOException {
        return OpenConfiguratorProjectUtils.deleteNode(nodeCollection, node,
                null);
    }

    /**
     * Updates the new node collection.
     *
     * @param nodeCollection The new node collection
     */
    public synchronized void setNodeCollection(
            Map<Short, Node> nodeCollection) {
        this.nodeCollection = nodeCollection;
    }

    /**
     * Set the modified node Id
     *
     * @param oldNodeId The ID of the node before modification.
     * @param newNodeId The ID of the node after modification.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     * @throws InterruptedException Errors with interrupt in the thread.
     */
    public synchronized void setNodeId(final short oldNodeId,
            final short newNodeId)
                    throws IOException, JDOMException, InterruptedException {

        Node oldNode = nodeCollection.get(oldNodeId);

        oldNode.setNodeId(newNodeId);
        // Remove the old node ID from node collection.
        nodeCollection.remove(new Short(oldNodeId));
        // Add the modified node ID into node collection.
        nodeCollection.put(new Short(newNodeId), oldNode);
        try {
            oldNode.getProject().refreshLocal(IResource.DEPTH_INFINITE,
                    new NullProgressMonitor());
        } catch (CoreException e) {
            System.err.println("unable to refresh the resource due to "
                    + e.getCause().getMessage());
        }
    }

    /**
     * Enables or disables the node.
     *
     * @param node The node to enabled or disabled.
     *
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public synchronized void toggleEnableDisable(Node node)
            throws JDOMException, IOException {
        node.setEnabled(!node.isEnabled());
    }
}

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
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.epsg.openconfigurator.Activator;
import org.epsg.openconfigurator.console.OpenConfiguratorMessageConsole;
import org.epsg.openconfigurator.lib.wrapper.OpenConfiguratorCore;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.model.Node.NodeType;
import org.epsg.openconfigurator.util.IPowerlinkConstants;
import org.epsg.openconfigurator.util.OpenConfiguratorLibraryUtils;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectUtils;
import org.epsg.openconfigurator.util.XddMarshaller;
import org.epsg.openconfigurator.xmlbinding.projectfile.OpenCONFIGURATORProject;
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
    private OpenCONFIGURATORProject currentProject;

    public PowerlinkRootNode() {
    }

    public PowerlinkRootNode(OpenCONFIGURATORProject currentProject) {
        this.currentProject = currentProject;
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

        JDomUtil.writeToProjectXmlDocument(document, xmlFile);

        // Updates generator attributes in project file.
        OpenConfiguratorProjectUtils.updateGeneratorInfo(node);

        return true;
    }

    /**
     * Clear collection of nodes and set to empty.
     */
    public void clearNodeCollection() {
        synchronized (nodeCollection) {
            nodeCollection.clear();
        }
    }

    /**
     * @return The list of CN nodes available in the project.
     */
    public ArrayList<Node> getCnNodeList() {
        ArrayList<Node> returnNodeList = new ArrayList<Node>();
        Collection<Node> nodeList = nodeCollection.values();

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

    /**
     * @return The number of nodes available.
     */
    public int getNodeCount() {
        synchronized (nodeCollection) {
            return nodeCollection.size();
        }
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

            Collection<Node> nodeList = nodeCollection.values();

            Node mnNode = getMN();
            if (mnNode != null) {
                returnNodeList.add(mnNode);
            }

            for (Node node : nodeList) {
                if (node.getNodeType() == NodeType.CONTROLLED_NODE) {
                    returnNodeList.add(node);
                }

                // MN/NetworkConfiguration instance is already added.
            }
        }
        return returnNodeList.toArray();
    }

    /**
     * @return OpenCONFIGURATORProject instance.
     */
    public OpenCONFIGURATORProject getOpenConfiguratorProject() {
        return currentProject;
    }

    /**
     * @return Returns the list of RMNs available in the project.
     */
    public ArrayList<Node> getRmnNodeList() {
        ArrayList<Node> returnNodeList = new ArrayList<Node>();

        Collection<Node> nodeList = nodeCollection.values();

        for (Node node : nodeList) {
            if (node.getNodeType() == NodeType.REDUNDANT_MANAGING_NODE) {
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
    public Status importNodes(IFile projectFile,
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
                // Workaround to update lossOfSoctolerance value in the network
                // property
                PowerlinkObject obj = newNode.getObjectDictionary().getObject(
                        INetworkProperties.LOSS_SOC_TOLERANCE_OBJECT_ID);
                if (obj != null) {
                    String lossOfSocToleranceString = obj.getActualValue();
                    if (!lossOfSocToleranceString.isEmpty()) {
                        Long lossOfSocToleranceNs = Long
                                .decode(lossOfSocToleranceString);
                        networkCfg.setLossOfSocTolerance(
                                BigInteger.valueOf(lossOfSocToleranceNs));
                    }
                }
            }

            Iterator<TCN> cnNodeIterator = networkCfg.getNodeCollection()
                    .getCN().iterator();
            while (cnNodeIterator.hasNext()) {

                if (monitor.isCanceled()) {
                    return new Status(IStatus.OK,
                            org.epsg.openconfigurator.Activator.PLUGIN_ID,
                            "Cancelled", null);
                }

                TCN cnNode = cnNodeIterator.next();

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
                    if (e instanceof FileNotFoundException) {
                        String errorMessage = MessageFormat.format(
                                XDC_FILE_NOT_FOUND_ERROR,
                                processingNode.getNodeIDWithName(),
                                processingNode.getAbsolutePathToXdc());
                        OpenConfiguratorMessageConsole.getInstance()
                                .printErrorMessage(errorMessage,
                                        processingNode.getProject().getName());
                    } else {
                        OpenConfiguratorMessageConsole.getInstance()
                                .printErrorMessage(
                                        e.getCause().getMessage()
                                                + " for the XDD/XDC file of node "
                                                + processingNode
                                                        .getNodeIDWithName(),
                                        processingNode.getProject().getName());
                    }
                }
                nodeCollection.put(new Short(processingNode.getNodeId()),
                        processingNode);
                monitor.worked(1);
            }

            // Import the RMN nodes
            Iterator<TRMN> rmnIterator = networkCfg.getNodeCollection().getRMN()
                    .iterator();
            while (rmnIterator.hasNext()) {

                if (monitor.isCanceled()) {
                    return new Status(IStatus.OK,
                            org.epsg.openconfigurator.Activator.PLUGIN_ID,
                            "Cancelled", null);
                }
                TRMN rmnNode = rmnIterator.next();
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
                            .printErrorMessage(
                                    e.getCause().getMessage()
                                            + " for the XDD/XDC file of the node "
                                            + "'" + rmnNode.getName() + "("
                                            + rmnNode.getNodeID() + ")" + "'",
                                    processingNode.getProject().getName());
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
            OpenConfiguratorMessageConsole.getInstance().printErrorMessage(
                    errorMessage, processingNode.getProject().getName());
            return new Status(IStatus.ERROR,
                    org.epsg.openconfigurator.Activator.PLUGIN_ID, errorMessage,
                    e);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            String xdcPath = processingNode.getPathToXDC();

            String errorMessage = MessageFormat.format(XDC_FILE_NOT_FOUND_ERROR,
                    processingNode.getNodeIDWithName(), xdcPath);
            OpenConfiguratorMessageConsole.getInstance().printErrorMessage(
                    errorMessage, processingNode.getProject().getName());
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
    public boolean isNodeIdAlreadyAvailable(short nodeIdTobeChecked) {
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
    public Result persistNodes(IProgressMonitor monitor)
            throws JDOMException, IOException {

        final Iterator<Entry<Short, Node>> entries = nodeCollection.entrySet()
                .iterator();
        while (entries.hasNext()) {

            Map.Entry<Short, Node> entry = entries.next();
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
            try {

                Result res = OpenConfiguratorProjectUtils.persistNodeData(node);
                if (!res.IsSuccessful()) {
                    // Continue operation for other nodes
                    continue;
                }
                OpenConfiguratorProjectUtils.updateNodeAssignmentValues(node);
            } catch (Exception e) {
                e.printStackTrace();
                if (e instanceof FileNotFoundException) {
                    String errorMessage = MessageFormat.format(
                            XDC_FILE_NOT_FOUND_ERROR, node.getNodeIDWithName(),
                            node.getAbsolutePathToXdc());
                    OpenConfiguratorMessageConsole.getInstance()
                            .printErrorMessage(errorMessage,
                                    node.getProject().getName());
                } else {
                    OpenConfiguratorMessageConsole.getInstance()
                            .printErrorMessage(
                                    e.getCause().getMessage()
                                            + " for the  XDD/XDC file of node "
                                            + node.getNodeIDWithName() + ".",
                                    node.getProject().getName());
                }
            }

            monitor.worked(1);
        }

        Node mnNode = getMN();
        // Updates LossOfSoc tolerance value during build from library.
        long[] lossOfSocToleranceValue = new long[1];
        Result res = OpenConfiguratorCore.GetInstance().GetLossOfSocTolerance(
                mnNode.getNetworkId(), IPowerlinkConstants.MN_DEFAULT_NODE_ID,
                lossOfSocToleranceValue);
        if (res.IsSuccessful()) {
            mnNode.setLossOfSocTolerance(lossOfSocToleranceValue[0]);
        } else {
            OpenConfiguratorMessageConsole.getInstance()
                    .printLibraryErrorMessage(res);
        }

        // Updates generator attributes in project file.
        OpenConfiguratorProjectUtils.updateGeneratorInfo(getMN());

        return new Result();
    }

    /**
     * Removes the node from the project.
     *
     * @param node The node to be removed.
     * @return <code>True</code> if successful and <code>False</code> otherwise.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public boolean removeNode(final Node node)
            throws JDOMException, IOException {
        boolean retVal = false;
        if (node == null) {
            return retVal;
        }

        IProject currentProject = node.getProject();
        if (currentProject == null) {
            System.err.println("Current project null. returned null");
            return retVal;
        }

        System.err.println("Remove node....");
        final WorkspaceModifyOperation wmo = new WorkspaceModifyOperation() {

            @Override
            protected void execute(IProgressMonitor monitor)
                    throws CoreException, InvocationTargetException,
                    InterruptedException {

                System.err.println("Remove node.... entered critical section");

                Node mnNode = getMN();

                if (!node.hasXdd()) {
                    Result libResult = OpenConfiguratorLibraryUtils
                            .removeNode(node);
                    if (!libResult.IsSuccessful()) {
                        System.err.println(OpenConfiguratorLibraryUtils
                                .getErrorMessage(libResult));
                        return;
                    }
                }

                // Remove from the viewer node collection.
                Object nodeObjectModel = node.getNodeModel();
                if (nodeObjectModel instanceof TRMN) {
                    TRMN rMN = (TRMN) nodeObjectModel;
                    short nodeId = Short.parseShort(rMN.getNodeID());
                    nodeCollection.remove(nodeId);
                    // retVal = true;
                } else if (nodeObjectModel instanceof TCN) {
                    TCN cnNode = (TCN) nodeObjectModel;
                    short nodeId = Short.parseShort(cnNode.getNodeID());
                    nodeCollection.remove(nodeId);
                    // retVal = true;
                } else {
                    System.err.println("Un-supported node" + nodeObjectModel);
                    // return false;
                    // FIXME: Throw an exception
                }

                // Remove from the openconfigurator model.
                if (mnNode.getNodeModel() instanceof TNetworkConfiguration) {
                    TNetworkConfiguration net = (TNetworkConfiguration) mnNode
                            .getNodeModel();
                    if (nodeObjectModel instanceof TRMN) {
                        List<TRMN> rmn = net.getNodeCollection().getRMN();
                        rmn.remove(nodeObjectModel);
                    } else if (nodeObjectModel instanceof TCN) {
                        List<TCN> cn = net.getNodeCollection().getCN();
                        cn.remove(nodeObjectModel);
                    } else {
                        System.err.println(
                                "Remove from openCONF model failed. Node ID:"
                                        + node.getNodeId() + " modelType:"
                                        + nodeObjectModel);
                        // return false;
                        // FIXME: Throw an exception
                    }
                } else {
                    System.err.println("Node model has been changed");
                }

                // Remove from the openconfigurator project xml.
                String projectXmlLocation = node.getProjectXml().getLocation()
                        .toString();
                File xmlFile = new File(projectXmlLocation);

                try {
                    org.jdom2.Document document = JDomUtil
                            .getXmlDocument(xmlFile);
                    ProjectJDomOperation.deleteNode(document, node);

                    JDomUtil.writeToProjectXmlDocument(document, xmlFile);

                    // Updates generator attributes in project file.
                    OpenConfiguratorProjectUtils.updateGeneratorInfo(node);

                    // Delete the XDC file from the deviceConfiguration directory.
                    Files.delete(Paths.get(node.getAbsolutePathToXdc()));

                } catch (JDOMException | IOException ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                    IStatus errorStatus = new Status(IStatus.ERROR,
                            Activator.PLUGIN_ID, IStatus.OK,
                            "Error ocurred while delete a node", ex);
                    throw new CoreException(errorStatus);
                }

                System.err.println("Remove node.... leaving critical section");
            }
        };

        WorkspaceJob job = new WorkspaceJob("Delete a Node") {

            @Override
            public IStatus runInWorkspace(IProgressMonitor monitor)
                    throws CoreException {
                try {
                    wmo.run(monitor);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                    Status errStatus = new Status(IStatus.ERROR,
                            Activator.PLUGIN_ID, e.getMessage(),
                            e.getTargetException());
                    throw new CoreException(errStatus);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }

                return Status.OK_STATUS;
            }
        };

        job.setUser(true);
        job.schedule();

        return true;
    }

    /**
     * Set the modified node Id
     *
     * @param oldNodeId The ID of the node before modification.
     * @param newNodeId The ID of the node after modification.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     * @throws InterruptedException Errors with interrupt in the thread.
     * @throws InvocationTargetException
     */
    public void setNodeId(final short oldNodeId, final short newNodeId)
            throws IOException, JDOMException, InterruptedException,
            InvocationTargetException {

        final WorkspaceModifyOperation wmo = new WorkspaceModifyOperation() {

            @Override
            protected void execute(IProgressMonitor monitor)
                    throws CoreException, InvocationTargetException,
                    InterruptedException {

                if (nodeCollection.containsKey(oldNodeId)) {
                    Node oldNode = nodeCollection.get(oldNodeId);

                    try {
                        oldNode.setNodeId(newNodeId);

                        Result res = OpenConfiguratorCore.GetInstance()
                                .SetNodeId(oldNode.getNetworkId(), oldNodeId,
                                        newNodeId);
                        if (!res.IsSuccessful()) {
                            System.err.println("RES set Node ID:"
                                    + OpenConfiguratorLibraryUtils
                                            .getErrorMessage(res));
                        }

                        // Remove the old node ID from node collection.
                        nodeCollection.remove(new Short(oldNodeId));
                        // Add the modified node ID into node collection.
                        nodeCollection.put(new Short(newNodeId), oldNode);
                    } catch (IOException | JDOMException ex) {
                        ex.printStackTrace();
                        IStatus errorStatus = new Status(IStatus.ERROR,
                                Activator.PLUGIN_ID, IStatus.OK,
                                "Error ocurred while setting the new node id",
                                ex);
                        throw new CoreException(errorStatus);
                    } finally {
                        monitor.done();
                    }
                } else {
                    System.err.println(
                            "Node id not found in the network. ID:" + oldNodeId);
                }
            }
        };
        wmo.run(new NullProgressMonitor());

        try {
            getMN().getProject().refreshLocal(IResource.DEPTH_INFINITE,
                    new NullProgressMonitor());
        } catch (CoreException e) {
            System.err.println("unable to refresh the resource due to "
                    + e.getCause().getMessage());
        }

    }
}

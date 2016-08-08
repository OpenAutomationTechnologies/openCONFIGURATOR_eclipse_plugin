/*******************************************************************************
 * @file   Node.java
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

package org.epsg.openconfigurator.model;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.epsg.openconfigurator.event.NodePropertyChangeEvent;
import org.epsg.openconfigurator.lib.wrapper.NodeAssignment;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectUtils;
import org.epsg.openconfigurator.xmlbinding.projectfile.InterfaceList;
import org.epsg.openconfigurator.xmlbinding.projectfile.OpenCONFIGURATORProject;
import org.epsg.openconfigurator.xmlbinding.projectfile.TAbstractNode;
import org.epsg.openconfigurator.xmlbinding.projectfile.TAbstractNode.ForcedObjects;
import org.epsg.openconfigurator.xmlbinding.projectfile.TCN;
import org.epsg.openconfigurator.xmlbinding.projectfile.TMN;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNetworkConfiguration;
import org.epsg.openconfigurator.xmlbinding.projectfile.TRMN;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745ProfileContainer;
import org.epsg.openconfigurator.xmlbinding.xdd.Interface;
import org.epsg.openconfigurator.xmloperation.XddJdomOperation;
import org.jdom2.JDOMException;

/**
 * Wrapper class for a POWERLINK node.
 *
 * @author Ramakrishnan P
 *
 */
public class Node {

    /**
     * Node type ENUM constants.
     *
     * @author Ramakrishnan P
     *
     */
    public enum NodeType {
        UNDEFINED, CONTROLLED_NODE, MANAGING_NODE, REDUNDANT_MANAGING_NODE, MODULAR_CHILD_NODE
    }

    /**
     * Returns the attribute name linked with the node assignment value.
     *
     * @param nmtNodeAssign The node assignment value.
     * @return The attribute name.
     */
    public static String getNodeAssignment(NodeAssignment nmtNodeAssign) {
        String attributeName = "";
        switch (nmtNodeAssign) {

            case NMT_NODEASSIGN_NODE_EXISTS:
                // No.op
                break;
            case NMT_NODEASSIGN_NODE_IS_CN:
                // No.op
                break;
            case NMT_NODEASSIGN_START_CN:
                attributeName = IControlledNodeProperties.CN_AUTO_START_NODE_OBJECT;
                break;
            case NMT_NODEASSIGN_MANDATORY_CN:
                attributeName = IControlledNodeProperties.CN_IS_MANDATORY_OBJECT;
                break;
            case NMT_NODEASSIGN_KEEPALIVE:
                attributeName = IControlledNodeProperties.CN_RESET_IN_OPERATIONAL_OBJECT;
                break;
            case NMT_NODEASSIGN_SWVERSIONCHECK:
                attributeName = IControlledNodeProperties.CN_VERIFY_APP_SW_VERSION_OBJECT;
                break;
            case NMT_NODEASSIGN_SWUPDATE:
                attributeName = IControlledNodeProperties.CN_AUTO_APP_SW_UPDATE_ALLOWED_OBJECT;
                break;
            case NMT_NODEASSIGN_ASYNCONLY_NODE:
                attributeName = IAbstractNodeProperties.NODE_IS_ASYNC_ONLY_OBJECT;
                break;
            case NMT_NODEASSIGN_MULTIPLEXED_CN:
                attributeName = IControlledNodeProperties.CN_IS_MULTIPLEXED;
                break;
            case NMT_NODEASSIGN_RT1:
                attributeName = IAbstractNodeProperties.NODE_IS_TYPE1_ROUTER_OBJECT;
                break;
            case NMT_NODEASSIGN_RT2:
                attributeName = IAbstractNodeProperties.NODE_IS_TYPE2_ROUTER_OBJECT;
                break;
            case NMT_NODEASSIGN_MN_PRES:
                attributeName = IManagingNodeProperties.MN_TRANSMIT_PRES_OBJECT;
                break;
            case NMT_NODEASSIGN_PRES_CHAINING:
                attributeName = IControlledNodeProperties.CN_IS_CHAINED;
                break;
            case MNT_NODEASSIGN_VALID:
                // No.op
                break;
            default:
                break;
        }

        return attributeName;
    }

    /**
     * Node instance from the openCONFIGURATOR project. Example: The Object is
     * one of TNetworkConfiguration(only for MN), TCN, TRMN.
     */
    private final Object nodeModel;

    /**
     * The memory of the XDC linked to this Node.
     */
    private final ISO15745ProfileContainer xddModel;

    /**
     * Associated Eclipse project.
     */
    private final IProject project;

    /**
     * openCONFIGURATOR project file.
     */
    private final IFile projectXml;

    /**
     * Interface list of modular controlled node.
     */
    private List<Interface> interfaceList = new ArrayList<>();

    /**
     * Interface of modular node.
     */
    private List<HeadNodeInterface> interfaceOfNodes = new ArrayList<>();

    /**
     * Interface of node from the XDD instance.
     */
    private List<InterfaceList> interfaceListOfNodes = new ArrayList<>();

    /**
     * Root node instance.
     */
    private final PowerlinkRootNode rootNode;

    /**
     * Network ID used for the openCONFIGURATOR library.
     */
    private final String networkId;

    /**
     * Object dictionary instance.
     */
    private final ObjectDictionary objectDictionary;

    /**
     * Node ID in short datatype.
     */
    private short nodeId;

    /**
     * Node type instance.
     */
    private final NodeType nodeType;

    /**
     * Configuration error value.
     */
    private String configurationError;

    /**
     * Instance of NetworkManagement.
     */
    private final NetworkManagement networkmanagement;

    /**
     * Instance of Module management.
     */
    private final ModuleManagement moduleManagement;

    /**
     * Instance of DeviceModularinterface.
     */
    private final DeviceModularInterface moduleInterface;

    /**
     * Instance of head node interface.
     */
    private HeadNodeInterface headNodeInterface;

    /**
     * Constructor to initialize the node variables.
     */
    public Node() {
        nodeModel = null;
        xddModel = null;
        project = null;
        projectXml = null;
        rootNode = null;
        networkId = null;
        nodeId = 0;
        nodeType = NodeType.UNDEFINED;
        objectDictionary = null;
        networkmanagement = null;
        moduleManagement = null;
        moduleInterface = null;
        configurationError = "";
    }

    /**
     * Constructs the Node instance with the following inputs.
     *
     * @param rootNode Instance of PowerlinkRootNode to get the list of nodes.
     * @param projectXml openCONFIGURATOR project file.
     * @param nodeModel Node instance from the openCONFIGURATOR project.
     *            Example: The Object is one of TNetworkConfiguration(only for
     *            MN), TCN, TRMN.
     * @param xddModel The memory of the XDC linked to this Node.
     */
    public Node(PowerlinkRootNode rootNode, IFile projectXml, Object nodeModel,
            ISO15745ProfileContainer xddModel) {
        this.rootNode = rootNode;
        this.projectXml = projectXml;

        if (projectXml != null) {
            project = projectXml.getProject();
            networkId = projectXml.getProject().getName();
        } else {
            project = null;
            networkId = null;
        }

        this.nodeModel = nodeModel;

        // Calculate the node ID
        if (nodeModel instanceof TNetworkConfiguration) {
            TNetworkConfiguration net = (TNetworkConfiguration) nodeModel;
            nodeId = net.getNodeCollection().getMN().getNodeID();
            nodeType = NodeType.MANAGING_NODE;
        } else if (nodeModel instanceof TCN) {
            TCN cn = (TCN) nodeModel;

            nodeId = Short.decode(cn.getNodeID());
            nodeType = NodeType.CONTROLLED_NODE;
        } else if (nodeModel instanceof TRMN) {
            TRMN rmn = (TRMN) nodeModel;

            nodeId = Short.parseShort(rmn.getNodeID());
            nodeType = NodeType.REDUNDANT_MANAGING_NODE;
        } else if (nodeModel instanceof TMN) {
            TMN tempMn = (TMN) nodeModel;
            nodeId = tempMn.getNodeID();
            nodeType = NodeType.MANAGING_NODE;
        } else if (nodeModel instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module module = (InterfaceList.Interface.Module) nodeModel;
            nodeType = NodeType.MODULAR_CHILD_NODE;
        } else {
            nodeId = 0;

            nodeType = NodeType.UNDEFINED;
            System.err.println("Unhandled node model type:" + nodeModel);
        }

        if (xddModel == null) {
            configurationError = "XDD parse not successful";
        }

        this.xddModel = xddModel;
        objectDictionary = new ObjectDictionary(this, xddModel);
        networkmanagement = new NetworkManagement(this, xddModel);
        moduleManagement = new ModuleManagement(this, xddModel);
        List<Interface> interfaceList = moduleManagement.getInterfacelist();
        for (Interface interfaces : interfaceList) {
            headNodeInterface = new HeadNodeInterface(this, interfaces);
            interfaceOfNodes.add(headNodeInterface);

        }

        moduleInterface = new DeviceModularInterface(this,
                moduleManagement.getModuleInterface());

        if (nodeModel instanceof TCN) {
            TCN cnModel = (TCN) nodeModel;
            if (isModularheadNode()) {
                InterfaceList it = cnModel.getInterfaceList();
                if (it != null) {
                    List<InterfaceList.Interface> intfc = it.getInterface();
                    System.out.println("The interface list.." + intfc);
                }
            }
        }
    }

    /**
     * Add/remove force object model in the project model.
     *
     * @param forceObj The forced object model.
     * @param force true to add and false to remove.
     */
    public void forceObjectActualValue(
            org.epsg.openconfigurator.xmlbinding.projectfile.Object forceObj,
            boolean force) {
        ForcedObjects forcedObjTag = null;
        if (nodeModel instanceof TNetworkConfiguration) {
            TNetworkConfiguration net = (TNetworkConfiguration) nodeModel;
            TMN mn = net.getNodeCollection().getMN();
            forcedObjTag = mn.getForcedObjects();
            if (force) {
                if (forcedObjTag == null) {
                    mn.setForcedObjects(new ForcedObjects());
                    forcedObjTag = mn.getForcedObjects();
                }
            } else {
                if (forcedObjTag != null) {
                    removeForcedObject(forcedObjTag, forceObj);
                }
                if (forcedObjTag != null) {
                    if (forcedObjTag.getObject().isEmpty()) {
                        mn.setForcedObjects(null);
                    }
                }
            }
        } else if (nodeModel instanceof TCN) {
            TCN cn = (TCN) nodeModel;
            forcedObjTag = cn.getForcedObjects();
            if (force) {
                if (forcedObjTag == null) {
                    cn.setForcedObjects(new ForcedObjects());
                    forcedObjTag = cn.getForcedObjects();
                }
            } else {
                if (forcedObjTag != null) {
                    removeForcedObject(forcedObjTag, forceObj);
                }
                if (forcedObjTag != null) {
                    if (forcedObjTag.getObject().isEmpty()) {
                        cn.setForcedObjects(null);
                    }
                }
            }
        } else if (nodeModel instanceof TRMN) {
            TRMN rmn = (TRMN) nodeModel;
            forcedObjTag = rmn.getForcedObjects();
            if (force) {
                if (forcedObjTag == null) {
                    rmn.setForcedObjects(new ForcedObjects());
                    forcedObjTag = rmn.getForcedObjects();
                }
            } else {
                if (forcedObjTag != null) {
                    removeForcedObject(forcedObjTag, forceObj);
                }
                if (forcedObjTag != null) {
                    if (forcedObjTag.getObject().isEmpty()) {
                        rmn.setForcedObjects(null);
                    }
                }
            }
        } else {
            System.err.println("Invalid node model" + nodeModel);
        }

        if (force) {
            boolean alreadyForced = false;
            for (org.epsg.openconfigurator.xmlbinding.projectfile.Object tempForceObj : forcedObjTag
                    .getObject()) {

                if (java.util.Arrays.equals(tempForceObj.getIndex(),
                        forceObj.getIndex())) {
                    if (forceObj.getSubindex() == null) {
                        alreadyForced = true;
                        break;
                    } else {
                        if (java.util.Arrays.equals(tempForceObj.getSubindex(),
                                forceObj.getSubindex())) {
                            alreadyForced = true;
                            break;
                        }
                    }
                }
            }

            if (!alreadyForced) {
                forcedObjTag.getObject().add(forceObj);
            }
        }
    }

    /**
     * @return The absolute path of the XDC associated with the Node.
     */
    public String getAbsolutePathToXdc() {

        String pathToXdc = project.getLocation().toString();
        String xdcPath = getPathToXDC();
        pathToXdc = pathToXdc + IPath.SEPARATOR + xdcPath;
        return pathToXdc;
    }

    public String getAbsolutePathToXdc(String nodeName) {
        String pathToXdc = project.getLocation().toString();
        String modulePath = nodeName;
        pathToXdc = pathToXdc + IPath.SEPARATOR
                + IPowerlinkProjectSupport.DEVICE_CONFIGURATION_DIR
                + IPath.SEPARATOR + modulePath;
        return pathToXdc;
    }

    /**
     * @return The value of AsndMaxNumber from object dictionary
     */
    public String getAsndMaxNumber() {
        String asndMaxNumberValue = getObjectDictionary().getValue(
                IManagingNodeProperties.ASND_MAX_NR_OBJECT_ID,
                IManagingNodeProperties.ASND_MAX_NR_SUBOBJECT_ID);
        return asndMaxNumberValue;
    }

    /**
     * @return The value of AsyncMtu from object dictionary.
     */
    public String getAsyncMtu() {
        String asyncMtuValue = getObjectDictionary().getValue(
                INetworkProperties.ASYNC_MTU_OBJECT_ID,
                INetworkProperties.ASYNC_MTU_SUBOBJECT_ID);
        return asyncMtuValue;
    }

    /**
     * @return The value of AsyncSlotTimeout from object dictionary.
     */
    public String getAsyncSlotTimeout() {
        String asyncSlotTimeoutValue = getObjectDictionary().getValue(
                IManagingNodeProperties.ASYNC_SLOT_TIMEOUT_OBJECT_ID,
                IManagingNodeProperties.ASYNC_SLOT_TIMEOUT_SUBOBJECT_ID);
        return asyncSlotTimeoutValue;
    }

    /**
     * @return Instance of openCONFIGURATOR project from POWERLINK root node.
     */
    public OpenCONFIGURATORProject getCurrentProject() {
        return rootNode.getOpenConfiguratorProject();
    }

    /**
     * @return The cycle time value from object dictionary.
     */
    public String getCycleTime() {
        String cycleTimeValue = getObjectDictionary()
                .getActualValue(INetworkProperties.CYCLE_TIME_OBJECT_ID);
        return cycleTimeValue;
    }

    /**
     * @return Error in configuration file.
     */
    public String getError() {
        return configurationError;
    }

    /**
     * @return The list of forced objects in a string format.
     */
    public String getForcedObjectsString() {

        String objectText = StringUtils.EMPTY;

        TAbstractNode abstractNodeInstance;
        if (nodeModel instanceof TNetworkConfiguration) {
            TNetworkConfiguration net = (TNetworkConfiguration) nodeModel;
            abstractNodeInstance = net.getNodeCollection().getMN();
        } else if (nodeModel instanceof TCN) {
            abstractNodeInstance = (TCN) nodeModel;
        } else if (nodeModel instanceof TRMN) {
            abstractNodeInstance = (TRMN) nodeModel;
        } else {
            return objectText;
        }

        if (abstractNodeInstance.getForcedObjects() != null) {
            List<org.epsg.openconfigurator.xmlbinding.projectfile.Object> forcedObjList = abstractNodeInstance
                    .getForcedObjects().getObject();
            for (org.epsg.openconfigurator.xmlbinding.projectfile.Object obj : forcedObjList) {
                objectText = objectText.concat("0x");

                objectText = objectText.concat(
                        DatatypeConverter.printHexBinary(obj.getIndex()));
                if (obj.getSubindex() != null) {
                    objectText = objectText.concat("/0x");
                    objectText = objectText.concat(DatatypeConverter
                            .printHexBinary(obj.getSubindex()));
                }
                objectText = objectText.concat(";");
            }
        }
        return objectText;
    }

    /**
     * @return The list of interface in the modular controlled node.
     */
    public List<HeadNodeInterface> getHeadNodeInterface() {
        return interfaceOfNodes;
    }

    /**
     * @return Interface instance of node.
     */
    public HeadNodeInterface getInterface() {
        return headNodeInterface;
    }

    /**
     * @return List of interface from the XDD model.
     */
    public List<Interface> getInterfaceList() {
        if (getModuleManagement().getModularHeadInterface() != null) {
            interfaceList.addAll(getModuleManagement().getModularHeadInterface()
                    .getInterface());
        }
        return interfaceList;
    }

    /**
     * @return Interface list of node.
     */
    public List<InterfaceList> getInterfaceListOfNodes() {
        return interfaceListOfNodes;
    }

    /**
     * @return The XDC model.
     */
    public ISO15745ProfileContainer getISO15745ProfileContainer() {
        return xddModel;
    }

    /**
     * @return Loss of SoC tolerance value from network configuration.
     */
    public String getLossOfSocTolerance() {
        if (nodeModel instanceof TNetworkConfiguration) {
            TNetworkConfiguration net = (TNetworkConfiguration) nodeModel;
            BigInteger lossSocToleranceValue = net.getLossOfSocTolerance();
            return String.valueOf(lossSocToleranceValue);
        } else {
            System.err.println("Invalid node model");
        }
        return null;
    }

    /**
     * @return Instance of DeviceModularInterface.
     */
    public DeviceModularInterface getModuleInterface() {
        return moduleInterface;
    }

    /**
     * @return Instance of ModuleManagement.
     */
    public ModuleManagement getModuleManagement() {
        return moduleManagement;
    }

    /**
     * @return the value of MultiplexedCycleCnt from object dictionary.
     */
    public String getMultiplexedCycleCnt() {
        // M for all
        String multiplexedValue = getObjectDictionary().getValue(
                INetworkProperties.MUTLIPLEX_CYCLE_CNT_OBJECT_ID,
                INetworkProperties.MUTLIPLEX_CYCLE_CNT_SUBOBJECT_ID);
        return multiplexedValue;
    }

    /**
     * @return The name of the node.
     */
    public String getName() {
        String nodeName = StringUtils.EMPTY;
        if (nodeModel == null) {
            return nodeName;
        }

        if (nodeModel instanceof TNetworkConfiguration) {
            TNetworkConfiguration net = (TNetworkConfiguration) nodeModel;

            nodeName = net.getNodeCollection().getMN().getName();
        } else if (nodeModel instanceof TCN) {
            TCN cn = (TCN) nodeModel;

            nodeName = cn.getName();
        } else if (nodeModel instanceof TRMN) {
            TRMN rmn = (TRMN) nodeModel;

            nodeName = rmn.getName();
        } else if (nodeModel instanceof TMN) {
            TMN mn = (TMN) nodeModel;
            nodeName = mn.getName();
        } else {
            System.err
                    .println("getName Unhandled node model type:" + nodeModel);
        }

        return nodeName;
    }

    /**
     * @return The network ID used for the openCONFIGURATOR library.
     */
    public String getNetworkId() {
        return networkId;
    }

    /**
     * @return Instance of NetworkManagement to access TNetworkManagement.
     */
    public NetworkManagement getNetworkManagement() {
        return networkmanagement;
    }

    /**
     * @return The node ID.
     */
    public short getNodeId() {
        return nodeId;
    }

    /**
     * @return The node ID in string format.
     */
    public String getNodeIdString() {
        return String.valueOf(nodeId);
    }

    /**
     * @return Name with node ID in the format <Name(NodeID)>
     */
    public String getNodeIDWithName() {
        return getName() + " (" + getNodeIdString() + ")";
    }

    /**
     * The Object is one of TNetworkConfiguration(only for MN), TCN, TRMN.
     *
     * @return Node instance from the openCONFIGURATOR project.
     */
    public Object getNodeModel() {
        return nodeModel;
    }

    /**
     * @return The node type.
     */
    public NodeType getNodeType() {
        return nodeType;
    }

    /**
     *
     * @return Instance of object dictionary.
     */
    public ObjectDictionary getObjectDictionary() {
        return objectDictionary;
    }

    /**
     * @return XDC path of output controlled node.
     */
    public String getOutputPathToXdc() {
        String xdcPath = StringUtils.EMPTY;

        java.nio.file.Path nodeImportFile = new File(getAbsolutePathToXdc())
                .toPath();
        String pathToXdc = project.getLocation().toString();
        if (nodeImportFile != null) {
            if (nodeImportFile.getFileName() != null) {
                xdcPath = pathToXdc + IPath.SEPARATOR
                        + IPowerlinkProjectSupport.DEFAULT_OUTPUT_DIR
                        + IPath.SEPARATOR
                        + String.valueOf(nodeImportFile.getFileName());
            }
        }

        return xdcPath;
    }

    /**
     * @return The relative path to the XDC available in the openCONFIGURATOR
     *         project model. Empty string if node not supported.
     */
    public String getPathToXDC() {
        String pathToXdc = StringUtils.EMPTY;

        if (nodeModel == null) {
            return pathToXdc;
        }

        if (nodeModel instanceof TNetworkConfiguration) {
            TNetworkConfiguration net = (TNetworkConfiguration) nodeModel;

            pathToXdc = net.getNodeCollection().getMN().getPathToXDC();
        } else if (nodeModel instanceof TCN) {
            TCN cn = (TCN) nodeModel;

            pathToXdc = cn.getPathToXDC();
        } else if (nodeModel instanceof TRMN) {
            TRMN rmn = (TRMN) nodeModel;

            pathToXdc = rmn.getPathToXDC();
        } else {
            System.err.println(
                    "GetPathToXdc Unhandled node model type:" + nodeModel);
        }

        return pathToXdc;
    }

    /**
     * @return Normal or Multiplexed or chained mode of node.
     */
    public PlkOperationMode getPlkOperationMode() {
        if (nodeModel instanceof TCN) {
            TCN cn = (TCN) nodeModel;

            if (cn.isIsChained()) {
                return PlkOperationMode.CHAINED;
            } else if (cn.isIsMultiplexed()) {
                return PlkOperationMode.MULTIPLEXED;

            }
            // Else normal.
        }

        return PlkOperationMode.NORMAL;
    }

    /**
     * @return Instance of POWERLINK root node.
     */
    public PowerlinkRootNode getPowerlinkRootNode() {
        return rootNode;
    }

    /**
     * @return Prescaler value available for this node.
     */
    public String getPrescaler() {
        // MN: M; CN: O
        String preScalerValue = getObjectDictionary().getValue(
                INetworkProperties.PRESCALER_OBJECT_ID,
                INetworkProperties.PRESCALER_SUBOBJECT_ID);
        return preScalerValue;
    }

    /**
     * @return PresTimeoutvalue of CN node in ns
     */
    public long getPresTimeoutvalue() {

        if (nodeModel instanceof TCN) {
            Node mnNode = rootNode.getMN();
            PowerlinkSubobject pollresponseSubObj = mnNode.getObjectDictionary()
                    .getSubObject(
                            INetworkProperties.POLL_RESPONSE_TIMEOUT_OBJECT_ID,
                            nodeId);
            if (pollresponseSubObj != null) {

                String presTimeOutValueInNs = pollresponseSubObj
                        .getActualDefaultValue();
                if (presTimeOutValueInNs != null) {

                    if (!presTimeOutValueInNs.isEmpty()) {
                        return Long.decode(presTimeOutValueInNs);
                    }

                } else {
                    System.err.println("PresTimeout sub-object "
                            + INetworkProperties.POLL_RESPONSE_TIMEOUT_OBJECT_ID
                            + "/" + pollresponseSubObj.getIdRaw()
                            + " has no value.");
                }

            } else {
                System.err.println("PresTimeout sub-object "
                        + INetworkProperties.POLL_RESPONSE_TIMEOUT_OBJECT_ID
                        + "/" + Integer.toHexString(nodeId) + " not found");
            }
        }

        return 0;
    }

    /**
     * @return Eclipse project associated with the node.
     */
    public IProject getProject() {
        return project;
    }

    /**
     * @return openCONFIGURATOR project XML file.
     */
    public IFile getProjectXml() {
        return projectXml;
    }

    /**
     * @return Priority value of RMN node from object dictionary.
     */
    public String getRmnPriority() {
        if (nodeType != NodeType.REDUNDANT_MANAGING_NODE) {
            // throw new UnsupportedOperationException();
            // FIXME: throw exception as un supported operation.
        }

        String priorityValue = getObjectDictionary().getValue(
                IRedundantManagingNodeProperties.RMN_PRIORITY_OBJECT_ID,
                IRedundantManagingNodeProperties.RMN_PRIORITY_SUBOBJECT_ID);
        return priorityValue;
    }

    /**
     * @return WaitNotActive value of RMN node from object dictionary.
     */
    public String getWaitNotActive() {
        // M for all
        String waitNotActiveValue = getObjectDictionary().getValue(
                IRedundantManagingNodeProperties.RMN_WAIT_NOT_ACTIVE_OBJECT_ID,
                IRedundantManagingNodeProperties.RMN_WAIT_NOT_ACTIVE_SUBOBJECT_ID);
        return waitNotActiveValue;
    }

    /**
     * @return Xpath of node.
     */
    public String getXpath() {
        String xpath = "//oc:";

        if (nodeModel instanceof TNetworkConfiguration) {
            xpath += IManagingNodeProperties.MN_TAG;
        } else if (nodeModel instanceof TCN) {
            xpath += IControlledNodeProperties.CN_TAG;
        } else if (nodeModel instanceof TRMN) {
            xpath += IRedundantManagingNodeProperties.RMN_TAG;
        }
        xpath += "[@" + IAbstractNodeProperties.NODE_ID_OBJECT + "='"
                + getNodeIdString() + "']";

        return xpath;
    }

    /**
     * Checks for valid XDD model.
     *
     * @return <code>True</code> if node has no XDD/XDC file. <code>False</code>
     *         if node has XDD/XDC file
     */
    public boolean hasError() {
        if (configurationError == null) {
            return false;
        }
        return !configurationError.isEmpty();
    }

    /**
     * @return <true> if the node has interface , <false> otherwise.
     */
    public boolean hasInterface() {
        List<HeadNodeInterface> interfaces = getHeadNodeInterface();
        if (interfaces.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * Checks for the enable disable flag available in the openCONFIGURATOR
     * project model. Currently supported on for a CN node. Other nodes are
     * enabled by default.
     *
     * @return <code>True</code> if node is enabled. <code>False</code> if node
     *         is disabled.
     */
    public boolean isEnabled() {
        if (nodeModel == null) {
            return true;
        }

        if (nodeModel instanceof TCN) {
            TCN cnModel = (TCN) nodeModel;
            return cnModel.isEnabled();
        }

        return true;
    }

    /**
     * @return <true> if the node is modular head node, <false> otherwise.
     */
    public boolean isModularheadNode() {
        if (getModuleManagement().getModularHeadInterface() != null) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the object is forced.
     *
     * @param objectId The object index in arrays of bytes.
     * @param subObjectId The sub object index in arrays of bytes.
     * @return <code> True</code> if Object forced. <code>False</code>
     *         otherwise.
     */
    public boolean isObjectIdForced(byte[] objectId, byte[] subObjectId) {

        ForcedObjects forcedObjTag = null;
        if (nodeModel instanceof TNetworkConfiguration) {
            TNetworkConfiguration net = (TNetworkConfiguration) nodeModel;
            TMN mn = net.getNodeCollection().getMN();
            forcedObjTag = mn.getForcedObjects();
        } else if (nodeModel instanceof TCN) {
            TCN cn = (TCN) nodeModel;
            forcedObjTag = cn.getForcedObjects();
        } else if (nodeModel instanceof TRMN) {
            TRMN rmn = (TRMN) nodeModel;
            forcedObjTag = rmn.getForcedObjects();
        } else {
            System.err.println("Invalid node model" + nodeModel);
        }

        if (forcedObjTag == null) {
            return false;
        }

        boolean alreadyForced = false;
        for (org.epsg.openconfigurator.xmlbinding.projectfile.Object tempForceObj : forcedObjTag
                .getObject()) {
            if (java.util.Arrays.equals(tempForceObj.getIndex(), objectId)) {
                if (subObjectId == null) {
                    alreadyForced = true;
                } else if (java.util.Arrays.equals(tempForceObj.getSubindex(),
                        subObjectId)) {
                    alreadyForced = true;
                }
            }
        }

        return alreadyForced;
    }

    /**
     * Checks for the boolean value of self PDO receipt in XDD of the given
     * node.
     *
     * @return <code>True</code>if the PDOSelfReceipt is assigned to true in XDD
     *         model, <code>False</code>if the PDOSelfReceipt is assigned to
     *         false in XDD model
     */
    public boolean isPDOSelfReceipt() {
        return networkmanagement.getGeneralFeatures().isPDOSelfReceipt();
    }

    private void removeForcedObject(ForcedObjects forcedObjTag,
            org.epsg.openconfigurator.xmlbinding.projectfile.Object forcedObjToBeRemoved) {
        org.epsg.openconfigurator.xmlbinding.projectfile.Object tempForcedObjToBeRemoved = null;
        for (org.epsg.openconfigurator.xmlbinding.projectfile.Object tempForceObj : forcedObjTag
                .getObject()) {

            if (java.util.Arrays.equals(tempForceObj.getIndex(),
                    forcedObjToBeRemoved.getIndex())) {
                if (forcedObjToBeRemoved.getSubindex() == null) {
                    tempForcedObjToBeRemoved = tempForceObj;
                    break;
                } else {
                    if (java.util.Arrays.equals(tempForceObj.getSubindex(),
                            forcedObjToBeRemoved.getSubindex())) {
                        tempForcedObjToBeRemoved = tempForceObj;
                        break;
                    }
                }
            }
        }

        if (tempForcedObjToBeRemoved != null) {
            forcedObjTag.getObject().remove(tempForcedObjToBeRemoved);
        }
    }

    /**
     * Set ASnd max number
     *
     * @param value AsndMaxNumber value in ns.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public void setAsndMaxNumber(Short value)
            throws JDOMException, IOException {
        if (value == null) {
            // FIXME: throw invalid argument.
            return;
        }

        // TBD about the category.
        getObjectDictionary().setActualValue(
                IManagingNodeProperties.ASND_MAX_NR_OBJECT_ID,
                IManagingNodeProperties.ASND_MAX_NR_SUBOBJECT_ID,
                value.toString());
    }

    /**
     * Set the asyncMtuValue to the node.
     *
     * @param value AsyncMtu value in bytes.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public void setAsyncMtu(Integer value) throws JDOMException, IOException {
        if (value == null) {
            // FIXME: throw invalid argument.
            return;
        }

        // M for all.
        getObjectDictionary().setActualValue(
                INetworkProperties.ASYNC_MTU_OBJECT_ID,
                INetworkProperties.ASYNC_MTU_SUBOBJECT_ID, value.toString());
    }

    /**
     * Set AsyncTimeout value for MN
     *
     * @param value AsyncTimeout value in ns.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public void setAsyncSlotTimeout(Long value)
            throws JDOMException, IOException {
        if (value == null) {
            // FIXME: throw invalid argument.
            return;
        }

        // O for TBD.
        getObjectDictionary().setActualValue(
                IManagingNodeProperties.ASYNC_SLOT_TIMEOUT_OBJECT_ID,
                IManagingNodeProperties.ASYNC_SLOT_TIMEOUT_SUBOBJECT_ID,
                value.toString());
    }

    /**
     * Set the PRes timeout value.
     *
     * Supported for a CN only.
     *
     * @param value PRes timeout value in ns.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public void setCnPresTimeout(String value)
            throws JDOMException, IOException {
        if (value == null) {
            // FIXME: throw invalid argument.
            return;
        }

        if (nodeModel instanceof TCN) {
            Node mnNode = rootNode.getMN();
            if (mnNode != null) {
                PowerlinkSubobject prestimeoutsubobj = mnNode
                        .getObjectDictionary().getSubObject(
                                INetworkProperties.POLL_RESPONSE_TIMEOUT_OBJECT_ID,
                                nodeId);
                if (prestimeoutsubobj != null) {
                    prestimeoutsubobj.setActualValue(value, true);
                } else {
                    System.err.println("PresTimeout SubObject not found");
                }
            }
        }
    }

    /**
     * Set the cycleTimeValue to the node.
     *
     * @param value cycleTimeValue in micro seconds.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public void setCycleTime(Long value) throws JDOMException, IOException {
        if (value == null) {
            // FIXME: throw invalid argument.
            return;
        }

        // M for all
        getObjectDictionary().setActualValue(
                INetworkProperties.CYCLE_TIME_OBJECT_ID, value.toString());
    }

    /**
     * Enables or disables this node, depending on the value of the parameter
     * enabled.
     *
     * @param enabled True to enable the node, false to disable the node.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public void setEnabled(boolean enabled) throws JDOMException, IOException {
        if (nodeModel instanceof TCN) {
            TCN cnModel = (TCN) nodeModel;
            OpenConfiguratorProjectUtils.updateNodeAttributeValue(this,
                    "enabled", String.valueOf(enabled));
            cnModel.setEnabled(enabled);
        } else {
            System.err.println(
                    "Enable disable not supported for nodeType" + nodeModel);
        }

    }

    /**
     * Sets the error flag to the node.
     *
     * @param errorDescription The error message corresponding to the flag.
     */
    public void setError(String errorDescription) {
        configurationError = errorDescription;
    }

    /**
     * Update the LossOfSoc tolerance value into the node model and write into
     * the project file.
     *
     * @param lossOfSocToleranceValue The value of LossOfSocTolerance to be
     *            updated in the project file.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public void setLossOfSocTolerance(long lossOfSocToleranceValue)
            throws JDOMException, IOException {
        if (nodeModel instanceof TNetworkConfiguration) {
            TNetworkConfiguration net = (TNetworkConfiguration) nodeModel;
            net.setLossOfSocTolerance(
                    BigInteger.valueOf(lossOfSocToleranceValue));
        } else {
            System.err.println(
                    "Invalid model type. Only available in the managing node!");
        }

        // Update LossOfSocTolerance value in the project file.
        OpenConfiguratorProjectUtils.updateNetworkAttributeValue(this,
                INetworkProperties.NET_LOSS_OF_SOC_TOLERANCE_ATTRIBUTE_NAME,
                String.valueOf(lossOfSocToleranceValue));
    }

    /**
     * Set the multiplexed cycle length value.
     *
     * @param value Value to be set in cycles.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public void setMultiplexedCycleLength(Integer value)
            throws JDOMException, IOException {
        if (value == null) {
            // FIXME: throw invalid argument.
            return;
        }

        // M for all
        getObjectDictionary().setActualValue(
                INetworkProperties.MUTLIPLEX_CYCLE_CNT_OBJECT_ID,
                INetworkProperties.MUTLIPLEX_CYCLE_CNT_SUBOBJECT_ID,
                value.toString());
    }

    /**
     * Set new name to this node.
     *
     * @param newName New name.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public void setName(final String newName)
            throws JDOMException, IOException {
        if (nodeModel instanceof TNetworkConfiguration) {
            TNetworkConfiguration net = (TNetworkConfiguration) nodeModel;
            net.getNodeCollection().getMN().setName(newName);
        } else if (nodeModel instanceof TCN) {
            TCN cn = (TCN) nodeModel;
            cn.setName(newName);
        } else if (nodeModel instanceof TRMN) {
            TRMN rmn = (TRMN) nodeModel;
            rmn.setName(newName);
        } else {
            System.err.println(
                    "setName(newName); Unhandled node model type:" + nodeModel);
        }

        OpenConfiguratorProjectUtils.updateNodeAttributeValue(this,
                IAbstractNodeProperties.NODE_NAME_OBJECT, newName);

        rootNode.fireNodePropertyChanged(new NodePropertyChangeEvent(this));
    }

    /**
     * Add or remove the new node assignment value
     *
     * @param nmtNodeassign The node assignment value.
     * @param enabled True to add or false to disable.
     */
    public boolean setNodeAssignment(NodeAssignment nmtNodeassign,
            boolean enabled) {
        boolean updatedInTheModel = false;

        if (nodeModel instanceof TNetworkConfiguration) {
            TNetworkConfiguration net = (TNetworkConfiguration) nodeModel;
            TMN mn = net.getNodeCollection().getMN();

            switch (nmtNodeassign) {

                case NMT_NODEASSIGN_NODE_EXISTS:
                    // No.op
                    break;
                case NMT_NODEASSIGN_NODE_IS_CN:
                    // No.op
                    break;
                case NMT_NODEASSIGN_START_CN:
                    // N.A in MN
                    break;
                case NMT_NODEASSIGN_MANDATORY_CN:
                    // N.A in MN
                    break;
                case NMT_NODEASSIGN_KEEPALIVE:
                    // N.A in MN
                    break;
                case NMT_NODEASSIGN_SWVERSIONCHECK:
                    // N.A in MN
                    break;
                case NMT_NODEASSIGN_SWUPDATE:
                    // N.A in MN
                    break;
                case NMT_NODEASSIGN_ASYNCONLY_NODE:
                    mn.setIsAsyncOnly(enabled);
                    updatedInTheModel = true;
                    break;
                case NMT_NODEASSIGN_MULTIPLEXED_CN:
                    // N.A in MN
                    break;
                case NMT_NODEASSIGN_RT1:
                    mn.setIsType1Router(enabled);
                    updatedInTheModel = true;
                    break;
                case NMT_NODEASSIGN_RT2:
                    mn.setIsType2Router(enabled);
                    updatedInTheModel = true;
                    break;
                case NMT_NODEASSIGN_MN_PRES:
                    mn.setTransmitsPRes(enabled);
                    updatedInTheModel = true;
                    break;
                case NMT_NODEASSIGN_PRES_CHAINING:
                    // N.A in MN
                    break;
                case MNT_NODEASSIGN_VALID:
                    // No.op
                    break;
                default:
                    break;
            }
        } else if (nodeModel instanceof TCN) {
            TCN cn = (TCN) nodeModel;
            switch (nmtNodeassign) {

                case NMT_NODEASSIGN_NODE_EXISTS:
                    // No.op
                    break;
                case NMT_NODEASSIGN_NODE_IS_CN:
                    // No.op
                    break;
                case NMT_NODEASSIGN_START_CN:
                    cn.setAutostartNode(enabled);
                    updatedInTheModel = true;
                    break;
                case NMT_NODEASSIGN_MANDATORY_CN:
                    cn.setIsMandatory(enabled);
                    updatedInTheModel = true;
                    break;
                case NMT_NODEASSIGN_KEEPALIVE:
                    cn.setResetInOperational(enabled);
                    updatedInTheModel = true;
                    break;
                case NMT_NODEASSIGN_SWVERSIONCHECK:
                    cn.setVerifyAppSwVersion(enabled);
                    updatedInTheModel = true;
                    break;
                case NMT_NODEASSIGN_SWUPDATE:
                    cn.setAutoAppSwUpdateAllowed(enabled);
                    updatedInTheModel = true;
                    break;
                case NMT_NODEASSIGN_ASYNCONLY_NODE:
                    cn.setIsAsyncOnly(enabled);
                    updatedInTheModel = true;
                    break;
                case NMT_NODEASSIGN_MULTIPLEXED_CN:
                    cn.setIsMultiplexed(enabled);
                    updatedInTheModel = true;
                    break;
                case NMT_NODEASSIGN_RT1:
                    cn.setIsType1Router(enabled);
                    updatedInTheModel = true;
                    break;
                case NMT_NODEASSIGN_RT2:
                    cn.setIsType2Router(enabled);
                    updatedInTheModel = true;
                    break;
                case NMT_NODEASSIGN_MN_PRES:
                    // Not applicable for CN.
                    break;
                case NMT_NODEASSIGN_PRES_CHAINING:
                    cn.setIsChained(enabled);
                    updatedInTheModel = true;
                    break;
                case MNT_NODEASSIGN_VALID:
                    // No.op
                    break;
                default:
                    break;
            }
        } else if (nodeModel instanceof TRMN) {
            TRMN rmn = (TRMN) nodeModel;
            switch (nmtNodeassign) {

                case NMT_NODEASSIGN_NODE_EXISTS:
                    // No.op
                    break;
                case NMT_NODEASSIGN_NODE_IS_CN:
                    // No.op
                    break;
                case NMT_NODEASSIGN_START_CN:
                    // N.A in MN
                    break;
                case NMT_NODEASSIGN_MANDATORY_CN:
                    // N.A in MN
                    break;
                case NMT_NODEASSIGN_KEEPALIVE:
                    // N.A in MN
                    break;
                case NMT_NODEASSIGN_SWVERSIONCHECK:
                    // N.A in MN
                    break;
                case NMT_NODEASSIGN_SWUPDATE:
                    // N.A in MN
                    break;
                case NMT_NODEASSIGN_ASYNCONLY_NODE:
                    rmn.setIsAsyncOnly(enabled);
                    updatedInTheModel = true;
                    break;
                case NMT_NODEASSIGN_MULTIPLEXED_CN:
                    // N.A in MN
                    break;
                case NMT_NODEASSIGN_RT1:
                    rmn.setIsType1Router(enabled);
                    updatedInTheModel = true;
                    break;
                case NMT_NODEASSIGN_RT2:
                    rmn.setIsType2Router(enabled);
                    updatedInTheModel = true;
                    break;
                case NMT_NODEASSIGN_MN_PRES:
                    // TBD.
                    break;
                case NMT_NODEASSIGN_PRES_CHAINING:
                    // N.A in MN
                    break;
                case MNT_NODEASSIGN_VALID:
                    // No.op
                    break;
                default:
                    break;
            }
        } else {
            System.err.println(
                    "setNodeAssignment Unhandled node model type:" + nodeModel);
        }

        return updatedInTheModel;
    }

    /**
     * Set modified node Id to node model.
     *
     * @param newNodeId The value of node ID for modification
     * @throws IOException Error with XDC file modifications.
     * @throws JDOMException Error with time modifications.
     * @throws InterruptedException Error with interrupt in thread.
     */
    public void setNodeId(short newNodeId)
            throws IOException, JDOMException, InterruptedException {
        // Update the new node id in the XDC file.
        OpenConfiguratorProjectUtils.updateNodeConfigurationPath(this,
                String.valueOf(newNodeId));

        // Update the node configuration path in the project file.
        OpenConfiguratorProjectUtils.updateNodeAttributeValue(this,
                IAbstractNodeProperties.NODE_CONIFG_OBJECT, getPathToXDC());

        // Set the new node id into the project file.
        OpenConfiguratorProjectUtils.updateNodeAttributeValue(this,
                IAbstractNodeProperties.NODE_ID_OBJECT,
                String.valueOf(newNodeId));

        // Update the new node id.
        if (nodeModel instanceof TCN) {
            TCN cn = (TCN) nodeModel;
            cn.setNodeID(String.valueOf(newNodeId));
        } else if (nodeModel instanceof TRMN) {
            TRMN rmn = (TRMN) nodeModel;
            rmn.setNodeID(String.valueOf(newNodeId));
        }

        nodeId = newNodeId;
    }

    /**
     * Set the path to the XDC. Example:
     * deviceConfiguration/000000_000Oplk_nodeId.xdc
     *
     * @param pathToXdc The relative path to the XDC.
     */
    public void setPathToXDC(final String pathToXdc) {
        if (nodeModel instanceof TNetworkConfiguration) {
            TNetworkConfiguration net = (TNetworkConfiguration) nodeModel;

            net.getNodeCollection().getMN().setPathToXDC(pathToXdc);
        } else if (nodeModel instanceof TCN) {
            TCN cn = (TCN) nodeModel;

            cn.setPathToXDC(pathToXdc);
        } else if (nodeModel instanceof TRMN) {
            TRMN rmn = (TRMN) nodeModel;

            rmn.setPathToXDC(pathToXdc);
        }
    }

    /**
     * Set the operational mode of POWERLINK.
     *
     * @param value The POWERLINK operational mode selected in the property
     *            source.
     */
    public void setPlkOperationMode(PlkOperationMode value) {
        if (nodeModel instanceof TCN) {
            TCN cn = (TCN) nodeModel;
            switch (value) {
                case NORMAL:
                    cn.setIsChained(false);
                    cn.setIsMultiplexed(false);
                    break;
                case CHAINED:
                    cn.setIsChained(true);
                    cn.setIsMultiplexed(false);
                    break;
                case MULTIPLEXED:
                    cn.setIsChained(false);
                    cn.setIsMultiplexed(true);
                    break;
                default:
                    System.err.println("Invalid value type");
                    break;
            }
        } else {
            System.err.println("setPlkoperationmode; Unhandled node model type:"
                    + nodeModel);
        }

        rootNode.fireNodePropertyChanged(new NodePropertyChangeEvent(this));

    }

    /**
     * Set the prescalerValue to the node.
     *
     * @param value Value to be set in cycles.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public void setPrescaler(Integer value) throws JDOMException, IOException {
        if (value == null) {
            // FIXME: throw invalid argument.
            return;
        }

        // MN: M; CN:O
        getObjectDictionary().setActualValue(
                INetworkProperties.PRESCALER_OBJECT_ID,
                INetworkProperties.PRESCALER_SUBOBJECT_ID, value.toString());
    }

    /**
     * Set the priority to an RMN node.
     *
     * @param priority Priority value (no units).
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     * @See IRedundantManagingNodeProperties.RMN_PRIORITY_DESCRIPTION
     */
    public void setRmnPriority(Long priority)
            throws JDOMException, IOException {
        if (priority == null) {
            // FIXME: throw invalid argument.
            return;
        }

        // M for RMN
        if (nodeType != NodeType.REDUNDANT_MANAGING_NODE) {
            // FIXME: throw exception for un supported node type.
            System.err.println(
                    "setRmnPriority Un-supported node type:" + nodeModel);
            return;
        }

        getObjectDictionary().setActualValue(
                IRedundantManagingNodeProperties.RMN_PRIORITY_OBJECT_ID,
                IRedundantManagingNodeProperties.RMN_PRIORITY_SUBOBJECT_ID,
                priority.toString());
    }

    /**
     * Set the wait not active value to an RMN.
     *
     * @param waitNotActive Wait not active value (no units).
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     *
     * @See IRedundantManagingNodeProperties.RMN_WAIT_NOT_ACTIVE_DESCRIPTION
     */
    public void setRmnWaitNotActive(Long waitNotActive)
            throws JDOMException, IOException {
        if (waitNotActive == null) {
            // FIXME: throw invalid argument.
            return;
        }

        // M for RMN
        if (nodeType != NodeType.REDUNDANT_MANAGING_NODE) {
            // FIXME: throw exception for un supported node type.
            System.err.println(
                    "setRmnWaitNotActive Un-supported node type:" + nodeModel);
            return;
        }

        getObjectDictionary().setActualValue(
                IRedundantManagingNodeProperties.RMN_WAIT_NOT_ACTIVE_OBJECT_ID,
                IRedundantManagingNodeProperties.RMN_WAIT_NOT_ACTIVE_SUBOBJECT_ID,
                waitNotActive.toString());
    }

    /**
     * Updates the interace values into the java model and in the project file.
     *
     * @param interfaceObj Project file instrance of interface.
     */
    public void updateInterfaceValue(
            org.epsg.openconfigurator.xmlbinding.projectfile.InterfaceList.Interface interfaceObj) {
        if (nodeModel instanceof TCN) {
            TCN cn = (TCN) nodeModel;
            cn.setInterfaceList(new InterfaceList());
            List<org.epsg.openconfigurator.xmlbinding.projectfile.InterfaceList.Interface> interfaceList = cn
                    .getInterfaceList().getInterface();
            for (org.epsg.openconfigurator.xmlbinding.projectfile.InterfaceList.Interface intfc : interfaceList) {
                if (interfaceObj != null) {
                    interfaceObj = intfc;
                }
            }

        }

    }

    /**
     * Updates the actual value of objects in XDD file.
     *
     * @param objectJCollection The list of objects with its actual value.
     * @param document The instance of XDD/XDC document.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public void writeObjectActualValues(
            java.util.LinkedHashMap<java.util.Map.Entry<Long, Integer>, String> objectJCollection,
            org.jdom2.Document document) throws JDOMException, IOException {
        for (Map.Entry<Map.Entry<Long, Integer>, String> objectJcollectionEntry : objectJCollection
                .entrySet()) {
            String actualValue = objectJcollectionEntry.getValue();
            long objectIdLong = objectJcollectionEntry.getKey().getKey();

            boolean isSubObject = false;
            int subObjectIdShort = objectJcollectionEntry.getKey().getValue();
            if (subObjectIdShort != -1) {
                isSubObject = true;
            }

            PowerlinkObject object = getObjectDictionary()
                    .getObject(objectIdLong);
            if (object != null) {

                if (!isSubObject) {
                    object.setActualValue(actualValue, false);
                    XddJdomOperation.updateActualValue(document, object,
                            actualValue);
                } else {
                    PowerlinkSubobject subObj = object
                            .getSubObject((short) subObjectIdShort);
                    if (subObj != null) {
                        subObj.setActualValue(actualValue, false);
                        XddJdomOperation.updateActualValue(document, subObj,
                                actualValue);
                    } else {
                        System.err.println("SubObject 0x"
                                + String.format("%04X", objectIdLong) + "/0x"
                                + String.format("%02X", subObjectIdShort)
                                + "does not exists in the XDC");
                    }
                }
            } else {
                System.err.println(
                        "Object 0x" + String.format("%04X", objectIdLong)
                                + "does not exists in the XDC");
            }
        }
    }
}

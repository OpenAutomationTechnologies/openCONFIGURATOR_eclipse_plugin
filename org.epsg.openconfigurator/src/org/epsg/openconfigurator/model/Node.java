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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.epsg.openconfigurator.lib.wrapper.NodeAssignment;
import org.epsg.openconfigurator.util.IPowerlinkConstants;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectUtils;
import org.epsg.openconfigurator.xmlbinding.projectfile.TAbstractNode;
import org.epsg.openconfigurator.xmlbinding.projectfile.TAbstractNode.ForcedObjects;
import org.epsg.openconfigurator.xmlbinding.projectfile.TCN;
import org.epsg.openconfigurator.xmlbinding.projectfile.TMN;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNetworkConfiguration;
import org.epsg.openconfigurator.xmlbinding.projectfile.TRMN;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745Profile;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745ProfileContainer;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyCommunicationNetworkPowerlink;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDataType;
import org.epsg.openconfigurator.xmlbinding.xdd.TApplicationLayers;
import org.epsg.openconfigurator.xmlbinding.xdd.TObject;
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
     * List of nodes from a collection.
     */
    private final Map<Short, Node> nodeCollection;
    /**
     * List of Objects available in the node.
     */
    private final List<PowerlinkObject> objectsList = new ArrayList<PowerlinkObject>();;

    /**
     * TPDO mappable objects list.
     */
    private final List<PowerlinkObject> tpdoMappableObjectList = new ArrayList<PowerlinkObject>();

    /**
     * RPDO mappable objects list
     */
    private final List<PowerlinkObject> rpdoMappableObjectList = new ArrayList<PowerlinkObject>();

    /**
     * TPDO channels list.
     */
    private final List<TpdoChannel> tpdoChannelsList = new ArrayList<TpdoChannel>();

    /**
     * RPDO channels list.
     */
    private final List<RpdoChannel> rpdoChannelsList = new ArrayList<RpdoChannel>();

    /**
     * Network ID used for the openCONFIGURATOR library.
     */
    private final String networkId;

    /**
     * Node ID in short datatype.
     */
    private short nodeId;

    public Node() {
        nodeModel = null;
        xddModel = null;
        project = null;
        projectXml = null;
        nodeCollection = null;
        networkId = null;
        nodeId = 0;
    }

    /**
     * Constructs the Node instance with the following inputs.
     *
     * @param nodeCollection The node list.
     * @param projectXml openCONFIGURATOR project file.
     * @param nodeModel Node instance from the openCONFIGURATOR project.
     *            Example: The Object is one of TNetworkConfiguration(only for
     *            MN), TCN, TRMN.
     * @param xddModel The memory of the XDC linked to this Node.
     */
    public Node(Map<Short, Node> nodeCollection, IFile projectXml,
            Object nodeModel, ISO15745ProfileContainer xddModel) {
        this.nodeCollection = nodeCollection;
        this.projectXml = projectXml;

        if (projectXml != null) {
            project = projectXml.getProject();
            networkId = projectXml.getProject().getName();
        } else {
            project = null;
            networkId = null;
        }

        this.nodeModel = nodeModel;

        this.xddModel = xddModel;

        // Calculate the node ID
        if (nodeModel instanceof TNetworkConfiguration) {
            TNetworkConfiguration net = (TNetworkConfiguration) nodeModel;

            nodeId = net.getNodeCollection().getMN().getNodeID();
        } else if (nodeModel instanceof TCN) {
            TCN cn = (TCN) nodeModel;

            nodeId = Short.decode(cn.getNodeID());
        } else if (nodeModel instanceof TRMN) {
            TRMN rmn = (TRMN) nodeModel;

            nodeId = rmn.getNodeID();
        } else if (nodeModel instanceof TMN) {
            TMN tempMn = (TMN) nodeModel;
            nodeId = tempMn.getNodeID();
        } else {
            nodeId = 0;
            System.err.println("Unhandled node model type:" + nodeModel);
        }

        // Calculate the objects during the object construction to improve
        // performance. Since there are no addition and deletion of objects
        // at runtime this is perfectly valid.
        List<PowerlinkObject> commParamObjList = new ArrayList<PowerlinkObject>();
        List<PowerlinkObject> mapParamObjList = new ArrayList<PowerlinkObject>();

        if (xddModel != null) {

            List<ISO15745Profile> profiles = xddModel.getISO15745Profile();
            for (ISO15745Profile profile : profiles) {
                ProfileBodyDataType profileBodyDatatype = profile
                        .getProfileBody();
                if (profileBodyDatatype instanceof ProfileBodyCommunicationNetworkPowerlink) {
                    ProfileBodyCommunicationNetworkPowerlink commProfile = (ProfileBodyCommunicationNetworkPowerlink) profileBodyDatatype;
                    TApplicationLayers.ObjectList tempObjectLists = commProfile
                            .getApplicationLayers().getObjectList();
                    List<TObject> objList = tempObjectLists.getObject();
                    for (TObject obj : objList) {
                        PowerlinkObject plkObj = new PowerlinkObject(this, obj);
                        objectsList.add(plkObj);

                        if (plkObj.getObjectIndex().startsWith("0x14")
                                || plkObj.getObjectIndex().startsWith("0x18")) {
                            commParamObjList.add(plkObj);
                        } else if (plkObj.getObjectIndex().startsWith("0x16")
                                || plkObj.getObjectIndex().startsWith("0x1A")) {
                            mapParamObjList.add(plkObj);
                        }

                        if (plkObj.hasRpdoMappableSubObjects()
                                || plkObj.isRpdoMappable()) {
                            rpdoMappableObjectList.add(plkObj);
                        }

                        if (plkObj.hasTpdoMappableSubObjects()
                                || plkObj.isTpdoMappable()) {
                            tpdoMappableObjectList.add(plkObj);
                        }
                    }
                }
            }

            for (int i = 0; i < commParamObjList.size(); i++) {
                try {
                    PowerlinkObject commParam = commParamObjList.get(i);
                    PowerlinkObject mapParam = mapParamObjList.get(i);

                    char mapParamId = mapParam.getObjectIndex().charAt(3);

                    if (mapParamId == '6') {
                        rpdoChannelsList.add(
                                new RpdoChannel(this, commParam, mapParam));
                    } else if ((mapParamId == 'A') || (mapParamId == 'a')) {
                        tpdoChannelsList.add(
                                new TpdoChannel(this, commParam, mapParam));
                    } else {
                        System.err.println("Invalid PDO detected!");
                    }
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
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
                removeForcedObject(forcedObjTag, forceObj);

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
                removeForcedObject(forcedObjTag, forceObj);

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
                removeForcedObject(forcedObjTag, forceObj);
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

    public String getAsndMaxNumber() {
        PowerlinkSubobject asndMaxNumberObj = getSubObject(
                IManagingNodeProperties.ASND_MAX_NR_OBJECT_ID,
                IManagingNodeProperties.ASND_MAX_NR_SUBOBJECT_ID);
        if (asndMaxNumberObj != null) {
            if (asndMaxNumberObj.getActualValue() != null) {
                return asndMaxNumberObj.getActualValue();
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * @return AsyncMtu value available for this node.
     */
    public String getAsyncMtu() {

        PowerlinkSubobject asyncMtuObj = getSubObject(
                INetworkProperties.ASYNC_MTU_OBJECT_ID,
                INetworkProperties.ASYNC_MTU_SUBOBJECT_ID);
        if (asyncMtuObj != null) {
            if (asyncMtuObj.getActualValue() != null) {
                return asyncMtuObj.getActualValue();
            }
        }

        return StringUtils.EMPTY;
    }

    public String getAsyncSlotTimeout() {
        PowerlinkSubobject asyncSlotTimeoutObj = getSubObject(
                IManagingNodeProperties.ASYNC_SLOT_TIMEOUT_OBJECT_ID,
                IManagingNodeProperties.ASYNC_SLOT_TIMEOUT_SUBOBJECT_ID);
        if (asyncSlotTimeoutObj != null) {
            if (asyncSlotTimeoutObj.getActualValue() != null) {
                return asyncSlotTimeoutObj.getActualValue();
            }
        }

        return StringUtils.EMPTY;
    }

    /**
     * @return Cycle time value available for this node.
     */
    public String getCycleTime() {

        PowerlinkObject cycleTimeObj = getObjects(
                INetworkProperties.CYCLE_TIME_OBJECT_ID);
        if (cycleTimeObj != null) {
            if (cycleTimeObj.getActualValue() != null) {
                return cycleTimeObj.getActualValue();
            }
        }

        return StringUtils.EMPTY;
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
     * @return The XDC model.
     */
    public ISO15745ProfileContainer getISO15745ProfileContainer() {
        return xddModel;
    }

    /**
     * @return Loss of SoC tolerance value available for this node.
     */
    public String getLossOfSocTolerance() {
        PowerlinkObject lossSocToleranceObj = getObjects(
                IAbstractNodeProperties.LOSS_SOC_TOLERANCE_OBJECT_ID);
        if (lossSocToleranceObj != null) {
            if (lossSocToleranceObj.getActualValue() != null) {
                return lossSocToleranceObj.getActualValue();
            }
        }

        return StringUtils.EMPTY;
    }

    /**
     * @return Multiplexed cycle length value available for this node.
     */
    public String getMultiplexedCycleLength() {

        PowerlinkSubobject multiplexedCylCntObj = getSubObject(
                INetworkProperties.MUTLIPLEX_CYCLE_CNT_OBJECT_ID,
                INetworkProperties.MUTLIPLEX_CYCLE_CNT_SUBOBJECT_ID);
        if (multiplexedCylCntObj != null) {
            if (multiplexedCylCntObj.getActualValue() != null) {
                return multiplexedCylCntObj.getActualValue();
            }
        }

        return StringUtils.EMPTY;
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

    public PowerlinkObject getObject(final byte[] objectId) {
        if (objectId == null) {
            return null;
        }

        String objectIdRaw = DatatypeConverter.printHexBinary(objectId);
        long objectIdL = 0;
        try {
            objectIdL = Long.parseLong(objectIdRaw, 16);
        } catch (NumberFormatException ex) {
            return null;
        }
        return getObjects(objectIdL);
    }

    /**
     * @param objectId object ID
     * @return Object with ID equals to objectId null otherwise.
     */
    public PowerlinkObject getObjects(final long objectId) {
        for (PowerlinkObject obj : objectsList) {
            if (obj.getObjectId() == objectId) {
                return obj;
            }
        }
        return null;
    }

    /**
     * @return The list of objects available in the Node.
     */
    public List<PowerlinkObject> getObjectsList() {
        return objectsList;
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
     * @return Prescaler value available for this node.
     */
    public String getPrescaler() {

        PowerlinkSubobject prescalerObj = getSubObject(
                INetworkProperties.PRESCALER_OBJECT_ID,
                INetworkProperties.PRESCALER_SUBOBJECT_ID);
        if (prescalerObj != null) {
            if (prescalerObj.getActualValue() != null) {
                return prescalerObj.getActualValue();
            }
        }

        return StringUtils.EMPTY;
    }

    /**
     * @return The PRes MaxLatency value of a CN node
     */
    public String getPresMaxLatencyValue() {
        if (nodeModel instanceof TCN) {
            PowerlinkSubobject presMaxLatencyObj = getSubObject(
                    IControlledNodeProperties.CN_POLL_RESPONSE_MAX_LATENCY_OBJECT_ID,
                    IControlledNodeProperties.CN_POLL_RESPONSE_MAX_LATENCY_SUBOBJECT_ID);
            if (presMaxLatencyObj != null) {
                if (presMaxLatencyObj.getActualValue() != null) {
                    return presMaxLatencyObj.getActualValue();
                }
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * @return PresTimeoutvalue of CN node in ns
     */
    public long getPresTimeoutvalue() {

        if (nodeModel instanceof TCN) {
            Node mnNode = nodeCollection
                    .get(new Short(IPowerlinkConstants.MN_DEFAULT_NODE_ID));
            PowerlinkSubobject pollresponseSubObj = mnNode.getSubObject(
                    INetworkProperties.POLL_RESPONSE_TIMEOUT_OBJECT_ID, nodeId);
            if (pollresponseSubObj != null) {

                String presTimeOutValueInNs = pollresponseSubObj
                        .getActualValue();
                if (presTimeOutValueInNs != null) {

                    if (!presTimeOutValueInNs.isEmpty()) {
                        return Long.decode(presTimeOutValueInNs);
                    }

                } else {

                    presTimeOutValueInNs = pollresponseSubObj.getDefaultValue();
                    if (presTimeOutValueInNs != null) {

                        if (!presTimeOutValueInNs.isEmpty()) {
                            return Long.decode(presTimeOutValueInNs);
                        }
                    } else {
                        System.err.println("PresTimeout sub-object "
                                + INetworkProperties.POLL_RESPONSE_TIMEOUT_OBJECT_ID
                                + "/" + pollresponseSubObj.getSubobjectIdRaw()
                                + " has no value.");
                    }
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
     * @return The priority value of an RMN.
     */
    public String getRmnPriority() {
        if (nodeModel instanceof TRMN) {
            PowerlinkSubobject priorityObj = getSubObject(
                    IRedundantManagingNodeProperties.RMN_PRIORITY_OBJECT_ID,
                    IRedundantManagingNodeProperties.RMN_PRIORITY_SUBOBJECT_ID);
            if (priorityObj != null) {
                if (priorityObj.getActualValue() != null) {
                    return priorityObj.getActualValue();
                }
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * @return The wait not active value of an RMN.
     */
    public String getRmnWaitNotActive() {
        if (nodeModel instanceof TRMN) {
            PowerlinkSubobject waitNotActiveObj = getSubObject(
                    IRedundantManagingNodeProperties.RMN_WAIT_NOT_ACTIVE_OBJECT_ID,
                    IRedundantManagingNodeProperties.RMN_WAIT_NOT_ACTIVE_SUBOBJECT_ID);
            if (waitNotActiveObj != null) {
                if (waitNotActiveObj.getActualValue() != null) {
                    return waitNotActiveObj.getActualValue();
                }
            }
        }
        return StringUtils.EMPTY;
    }

    public List<RpdoChannel> getRpdoChannelsList() {
        return rpdoChannelsList;
    }

    public List<PowerlinkObject> getRpdoMappableObjectList() {
        return rpdoMappableObjectList;
    }

    public PowerlinkSubobject getSubObject(final byte[] objectId,
            final byte[] subObjectId) {
        PowerlinkObject object = getObject(objectId);
        if ((object == null) || (subObjectId == null)) {
            return null;
        }

        return object.getSubObject(subObjectId);
    }

    /**
     * Retrieves the subobject instance for the given objectId and subObjectId
     * available in this node.
     *
     * @param objectId Object ID.
     * @param subObjectId Subobject ID.
     *
     * @return Subobject instance.
     */
    public PowerlinkSubobject getSubObject(final long objectId,
            final short subObjectId) {
        PowerlinkObject obj = getObjects(objectId);
        if (obj == null) {
            return null;
        }
        return obj.getSubObject(subObjectId);
    }

    public List<TpdoChannel> getTpdoChannelsList() {
        return tpdoChannelsList;
    }

    public List<PowerlinkObject> getTpdoMappableObjectList() {
        return tpdoMappableObjectList;
    }

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
     * @param value value to be set.
     * @throws IOException
     * @throws JDOMException
     */
    public void setAsndMaxNumber(Short value)
            throws JDOMException, IOException {
        if (nodeModel instanceof TNetworkConfiguration) {
            PowerlinkSubobject asndMaxNumberObj = getSubObject(
                    IManagingNodeProperties.ASND_MAX_NR_OBJECT_ID,
                    IManagingNodeProperties.ASND_MAX_NR_SUBOBJECT_ID);

            if (asndMaxNumberObj != null) {
                asndMaxNumberObj.setActualValue(value.toString(), true);
            } else {
                System.err.println("AsndMaxNumber object not found.");
            }
        }
    }

    /**
     * Set the asyncMtuValue to the node.
     *
     * @param value Value to be set.
     * @throws IOException
     * @throws JDOMException
     */
    public void setAsyncMtu(Integer value) throws JDOMException, IOException {
        if (nodeModel instanceof TNetworkConfiguration) {
            PowerlinkSubobject asyncMtuObj = getSubObject(
                    INetworkProperties.ASYNC_MTU_OBJECT_ID,
                    INetworkProperties.ASYNC_MTU_SUBOBJECT_ID);
            if (asyncMtuObj != null) {
                asyncMtuObj.setActualValue(value.toString(), true);
            } else {
                System.err.println("AsyncMtu object not found.");
            }
        }
    }

    /**
     * Set AsyncTimeout value for MN
     *
     * @param value Value to be set.
     * @throws IOException
     * @throws JDOMException
     */
    public void setAsyncSlotTimeout(Long value)
            throws JDOMException, IOException {
        if (nodeModel instanceof TNetworkConfiguration) {
            PowerlinkSubobject asyncSlotTimeoutObj = getSubObject(
                    IManagingNodeProperties.ASYNC_SLOT_TIMEOUT_OBJECT_ID,
                    IManagingNodeProperties.ASYNC_SLOT_TIMEOUT_SUBOBJECT_ID);

            if (asyncSlotTimeoutObj != null) {
                asyncSlotTimeoutObj.setActualValue(value.toString(), true);
            } else {
                System.err.println("AsyncTimeout object not found.");
            }
        }
    }

    /**
     * Set the PRes MaxLatency value for a CN node.
     *
     * @param value PRes MaxLatency value to set.
     * @throws IOException
     * @throws JDOMException
     */
    public void setCnPresMaxLatency(Long value)
            throws JDOMException, IOException {
        if (nodeModel instanceof TCN) {
            PowerlinkSubobject presMaxLatencyObj = getSubObject(
                    IControlledNodeProperties.CN_POLL_RESPONSE_MAX_LATENCY_OBJECT_ID,
                    IControlledNodeProperties.CN_POLL_RESPONSE_MAX_LATENCY_SUBOBJECT_ID);
            if (presMaxLatencyObj != null) {
                presMaxLatencyObj.setActualValue(value.toString(), true);
            } else {
                System.err.println("PresMaxLatency object not found.");
            }
        }
    }

    /**
     * Set the PRes timeout value.
     *
     * Supported for a CN only.
     *
     * @param value PRes timeout value in ns.
     * @throws IOException
     * @throws JDOMException
     */
    public void setCnPresTimeout(String value)
            throws JDOMException, IOException {
        if (nodeModel instanceof TCN) {
            Node mnNode = nodeCollection
                    .get(new Short(IPowerlinkConstants.MN_DEFAULT_NODE_ID));
            if (mnNode != null) {
                PowerlinkSubobject prestimeoutsubobj = mnNode.getSubObject(
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
     * @param value Value to be set.
     * @throws IOException
     * @throws JDOMException
     */
    public void setCycleTime(Long value) throws JDOMException, IOException {
        if (nodeModel instanceof TNetworkConfiguration) {
            PowerlinkObject cycleTimeObj = getObjects(
                    INetworkProperties.CYCLE_TIME_OBJECT_ID);
            if (cycleTimeObj != null) {
                cycleTimeObj.setActualValue(value.toString(), true);
            } else {
                System.err.println("CycleTime object not found.");
            }
        }
    }

    /**
     * Enables or disables this node, depending on the value of the parameter
     * enabled.
     *
     * @param enabled True to enable the node, false to disable the node.
     * @throws IOException
     * @throws JDOMException
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
     * Set the loss of SoC tolerance value.
     *
     * @param value Value to be set.
     * @throws IOException
     * @throws JDOMException
     */
    public void setLossOfSocTolerance(Long value)
            throws JDOMException, IOException {
        if (nodeModel instanceof TNetworkConfiguration) {
            PowerlinkObject lossOfSocToleranceObj = getObjects(
                    IAbstractNodeProperties.LOSS_SOC_TOLERANCE_OBJECT_ID);
            if (lossOfSocToleranceObj != null) {
                lossOfSocToleranceObj.setActualValue(value.toString(), true);
            } else {
                System.err.println("LossOfSocTolerance object not found.");
            }
        }
    }

    /**
     * Set the multiplexed cycle length value.
     *
     * @param value Value to be set.
     * @throws IOException
     * @throws JDOMException
     */
    public void setMultiplexedCycleLength(Integer value)
            throws JDOMException, IOException {
        if (nodeModel instanceof TNetworkConfiguration) {
            PowerlinkSubobject multiplexedCylCntObj = getSubObject(
                    INetworkProperties.MUTLIPLEX_CYCLE_CNT_OBJECT_ID,
                    INetworkProperties.MUTLIPLEX_CYCLE_CNT_SUBOBJECT_ID);
            if (multiplexedCylCntObj != null) {
                multiplexedCylCntObj.setActualValue(value.toString(), true);
            } else {
                System.err.println("MultiplexedCycleLength object not found.");
            }
        }
    }

    /**
     * Set new name to this node.
     *
     * @param newName New name.
     * @throws IOException
     * @throws JDOMException
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
     * Updates the id with the given node id.
     *
     * @param newNodeId The node id to be updated.
     *
     * @throws IOException Errors with project file or XDC file modifications.
     * @throws JDOMException
     */
    public void setNodeId(final short newNodeId)
            throws IOException, JDOMException {
        if (!((nodeModel instanceof TCN) || (nodeModel instanceof TRMN))) {
            System.err.println("setNodeID(newID) ; Unhandled node model type:"
                    + nodeModel);
            return;
        }

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

        synchronized (this) {
            nodeCollection.remove(new Short(nodeId));

            // Update the new node id.
            if (nodeModel instanceof TCN) {
                TCN cn = (TCN) nodeModel;
                cn.setNodeID(String.valueOf(newNodeId));
            } else if (nodeModel instanceof TRMN) {
                TRMN rmn = (TRMN) nodeModel;
                rmn.setNodeID(newNodeId);
            }

            nodeId = newNodeId;

            nodeCollection.put(new Short(nodeId), this);
        }
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
     * Set the prescalerValue to the node.
     *
     * @param value Value to be set.
     * @throws IOException
     * @throws JDOMException
     */
    public void setPrescaler(Integer value) throws JDOMException, IOException {
        if (nodeModel instanceof TNetworkConfiguration) {
            PowerlinkSubobject prescalerObj = getSubObject(
                    INetworkProperties.PRESCALER_OBJECT_ID,
                    INetworkProperties.PRESCALER_SUBOBJECT_ID);
            if (prescalerObj != null) {
                prescalerObj.setActualValue(value.toString(), true);
            } else {
                System.err.println("Prescaler object not found.");
            }
        }
    }

    /**
     * Set the priority to an RMN node.
     *
     * @param priority Priority value.
     * @throws IOException
     * @throws JDOMException
     * @See IRedundantManagingNodeProperties.RMN_PRIORITY_DESCRIPTION
     */
    public void setRmnPriority(Long priority)
            throws JDOMException, IOException {
        if (nodeModel instanceof TRMN) {
            PowerlinkSubobject priorityObj = getSubObject(
                    IRedundantManagingNodeProperties.RMN_PRIORITY_OBJECT_ID,
                    IRedundantManagingNodeProperties.RMN_PRIORITY_SUBOBJECT_ID);
            if (priorityObj != null) {
                priorityObj.setActualValue(priority.toString(), true);
            } else {
                System.err.println("Priority object not found in RMN.");
            }
        }
    }

    /**
     * Set the wait not active value to an RMN.
     *
     * @param waitNotActive Wait not active value.
     * @throws IOException
     * @throws JDOMException
     *
     * @See IRedundantManagingNodeProperties.RMN_WAIT_NOT_ACTIVE_DESCRIPTION
     */
    public void setRmnWaitNotActive(Long waitNotActive)
            throws JDOMException, IOException {
        if (nodeModel instanceof TRMN) {
            PowerlinkSubobject waitNotActiveObj = getSubObject(
                    IRedundantManagingNodeProperties.RMN_WAIT_NOT_ACTIVE_OBJECT_ID,
                    IRedundantManagingNodeProperties.RMN_WAIT_NOT_ACTIVE_SUBOBJECT_ID);
            if (waitNotActiveObj != null) {
                waitNotActiveObj.setActualValue(waitNotActive.toString(), true);
            } else {
                System.err.println("WaitNotActive object not found.");
            }
        }
    }

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

            PowerlinkObject object = getObjects(objectIdLong);
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

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.epsg.openconfigurator.console.OpenConfiguratorMessageConsole;
import org.epsg.openconfigurator.event.NodePropertyChangeEvent;
import org.epsg.openconfigurator.lib.wrapper.ErrorCode;
import org.epsg.openconfigurator.lib.wrapper.NodeAssignment;
import org.epsg.openconfigurator.lib.wrapper.OpenConfiguratorCore;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.util.OpenConfiguratorLibraryUtils;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectUtils;
import org.epsg.openconfigurator.util.PluginErrorDialogUtils;
import org.epsg.openconfigurator.xmlbinding.projectfile.InterfaceList;
import org.epsg.openconfigurator.xmlbinding.projectfile.OpenCONFIGURATORProject;
import org.epsg.openconfigurator.xmlbinding.projectfile.TAbstractNode;
import org.epsg.openconfigurator.xmlbinding.projectfile.TAbstractNode.ForcedObjects;
import org.epsg.openconfigurator.xmlbinding.projectfile.TCN;
import org.epsg.openconfigurator.xmlbinding.projectfile.TMN;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNetworkConfiguration;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNodeCollection;
import org.epsg.openconfigurator.xmlbinding.projectfile.TRMN;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745Profile;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745ProfileContainer;
import org.epsg.openconfigurator.xmlbinding.xdd.Interface;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyCommunicationNetworkPowerlink;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyCommunicationNetworkPowerlinkModularHead;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDataType;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDevicePowerlink;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDevicePowerlinkModularHead;
import org.epsg.openconfigurator.xmlbinding.xdd.TCNFeatures;
import org.epsg.openconfigurator.xmlbinding.xdd.TGeneralFeatures;
import org.epsg.openconfigurator.xmlbinding.xdd.TVersion;
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

    private static final String EMPTY_OBJ_ACTUAL_VALUE = "0x0000000000000000";

    private static final int XDD_VENDOR_ID_OBJECT_INDEX_TOCHECK = 0x1018;

    private static final short XDD_VENDOR_ID_SUBOBJECT_INDEX = 1;
    private static final short XDD_SUBOBJECT_INDEX_PRODUCTCODE = 2;

    public static final String XAP_XML = "xap.xml"; //$NON-NLS-1$

    private static final String ERROR_WHILE_COPYING_XDD = "Error occurred while copying the configuration file.";

    private static final String XDC_FILE_NOT_FOUND_ERROR = "XDD/XDC file for the node: {0} does not exists in the project.\n XDC Path: {1} ";

    private static final String FIRMWARE_FILE_MODULE_NOT_FOUND_ERROR = "Firmware file {0} for the module {1} does not exists in the project.\n Firmware file Path: {2} ";
    private static final String INVALID_MODULE_XDC_ERROR = " The XDD/XDC file of module {0} is not available for the node {1}.";

    private static final String INVALID_MODULE_FIRMWARE_FILE_ERROR = " The firmware file {0} is not available for the module {1}.";

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

    private static void removeForcedObject(ForcedObjects forcedObjTag,
            org.epsg.openconfigurator.xmlbinding.projectfile.Object forcedObjToBeRemoved) {
        org.epsg.openconfigurator.xmlbinding.projectfile.Object tempForcedObjToBeRemoved = null;
        for (org.epsg.openconfigurator.xmlbinding.projectfile.Object tempForceObj : forcedObjTag
                .getObject()) {

            if (java.util.Arrays.equals(tempForceObj.getIndex(),
                    forcedObjToBeRemoved.getIndex())) {
                if (forcedObjToBeRemoved.getSubindex() == null) {
                    tempForcedObjToBeRemoved = tempForceObj;
                    break;
                }
                if (java.util.Arrays.equals(tempForceObj.getSubindex(),
                        forcedObjToBeRemoved.getSubindex())) {
                    tempForcedObjToBeRemoved = tempForceObj;
                    break;
                }
            }
        }

        if (tempForcedObjToBeRemoved != null) {
            forcedObjTag.getObject().remove(tempForcedObjToBeRemoved);
        }
    }

    private ProcessImage processImage;

    /**
     * Node instance from the openCONFIGURATOR project. Example: The Object is
     * one of TNetworkConfiguration(only for MN), TCN, TRMN.
     */
    private final Object nodeModel;

    /**
     * Instance of Xdd model.
     */
    private TCN tcn;

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
     * XDD instance of firmware.
     */
    private FirmwareFile xddFirmwareFile;

    private OpenCONFIGURATORProject currentProject;

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

    private Map<FirmwareManager, Integer> nodeFirmwareCollection = new HashMap<>();

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
        xddFirmwareFile = null;
        currentProject = null;
        processImage = null;
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
        if ((nodeType == NodeType.MODULAR_CHILD_NODE)
                || (nodeType == NodeType.CONTROLLED_NODE)) {
            xddFirmwareFile = new FirmwareFile(xddModel);
        }
        List<Interface> interfaceList = moduleManagement
                .getInterfacelistOfNode();
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

    private boolean addAvailableFirmware(Module newModule) {
        List<FirmwareManager> validFwList = new ArrayList<>();
        if (rootNode != null) {
            if (!rootNode.getModuleList().isEmpty()) {
                for (Module module : rootNode.getModuleList()) {
                    if (module.getVenIdValue()
                            .equalsIgnoreCase(newModule.getVenIdValue())) {
                        if (module.getProductId()
                                .equalsIgnoreCase(newModule.getProductId())) {
                            if (!module.getModuleFirmwareFileList().isEmpty()) {
                                for (FirmwareManager fwMngr : module
                                        .getModuleFirmwareFileList()) {
                                    validFwList.add(fwMngr);
                                }
                            }
                        }
                    }

                }
                if (!validFwList.isEmpty()) {
                    MessageDialog dialog = new MessageDialog(null,
                            "Add firmware file", null,
                            "The project contains firmware file for Module '"
                                    + newModule.getModuleName() + "'."
                                    + " \nDo you wish to add the firmware file? ",
                            MessageDialog.WARNING, new String[] { "Yes", "No" },
                            1);
                    int result = dialog.open();
                    if (result != 0) {
                        return true;
                    }
                    Map<String, FirmwareManager> firmwarelist = new HashMap<>();
                    for (FirmwareManager fwMngr : validFwList) {
                        firmwarelist.put(fwMngr.getFirmwareUri(), fwMngr);

                    }

                    for (FirmwareManager fw : firmwarelist.values()) {
                        newModule.getModuleFirmwareCollection().put(fw,
                                fw.getFirmwarefileVersion());
                        fw.updateFirmwareInProjectFile(fw, newModule,
                                fw.getFirmwareObjModel());
                    }

                }

                return true;
            }
        }
        return false;
    }

    private boolean addNodeFirmwareFile(Node newNode) {
        List<FirmwareManager> validFwList = new ArrayList<>();
        if (rootNode != null) {
            if (!rootNode.getCnNodeList().isEmpty()) {
                for (Node cnNode : rootNode.getCnNodeList()) {
                    if (cnNode.getVendorIdValue()
                            .equalsIgnoreCase(newNode.getVendorIdValue())) {
                        if (cnNode.getProductCodeValue().equalsIgnoreCase(
                                newNode.getProductCodeValue())) {
                            if (!cnNode.getValidFirmwareList().isEmpty()) {

                                System.err.println(
                                        "The firmware collection values.."
                                                + cnNode.getValidFirmwareList());
                                for (FirmwareManager fwMngr : cnNode
                                        .getValidFirmwareList()) {
                                    validFwList.add(fwMngr);

                                }
                            }
                        }
                    }

                }

                if (!validFwList.isEmpty()) {
                    MessageDialog dialog = new MessageDialog(null,
                            "Add firmware file", null,
                            "The project contains firmware file for Node '"
                                    + newNode.getNodeIDWithName() + "'."
                                    + " \nDo you wish to add the firmware file? ",
                            MessageDialog.WARNING, new String[] { "Yes", "No" },
                            1);
                    int result = dialog.open();
                    if (result != 0) {
                        return true;
                    }
                    Map<String, FirmwareManager> firmwarelist = new HashMap<>();
                    for (FirmwareManager fwMngr : validFwList) {
                        firmwarelist.put(fwMngr.getFirmwareUri(), fwMngr);

                    }

                    for (FirmwareManager fw : firmwarelist.values()) {

                        FirmwareManager firmwareMngr = new FirmwareManager(
                                newNode, fw.getFirmwareXddModel(),
                                fw.getFirmwareObjModel());
                        newNode.getNodeFirmwareCollection().put(firmwareMngr,
                                firmwareMngr.getFirmwarefileVersion());
                        fw.updateFirmwareInProjectFile(firmwareMngr, newNode,
                                firmwareMngr.getFirmwareObjModel());
                    }

                }
                return true;
            }
        }
        return false;
    }

    public void addStationTypeofNode(int stationTypeChanged)
            throws JDOMException, IOException {
        Result res = new Result();
        Object nodeObject = getNodeModel();
        if (nodeObject instanceof TCN) {
            TCN tcn = (TCN) nodeObject;
            switch (stationTypeChanged) {
                case 0:
                    break;
                case 1:
                    tcn.setIsChained(true);
                    break;
                case 2:
                    tcn.setIsMultiplexed(true);
                    break;
                default:
                    System.err.println("Invalid Selection.");
                    break;
            }

            PlkOperationMode plkMode = null;
            int val = ((Integer) stationTypeChanged).intValue();
            if (val == 0) { // Normal Station.
                res = OpenConfiguratorCore.GetInstance()
                        .ResetOperationMode(getNetworkId(), getCnNodeIdValue());
                plkMode = PlkOperationMode.NORMAL;
            } else if (val == 1) {
                res = OpenConfiguratorCore.GetInstance()
                        .SetOperationModeChained(getNetworkId(),
                                getCnNodeIdValue());
                plkMode = PlkOperationMode.CHAINED;
            } else if (val == 2) {
                res = OpenConfiguratorCore.GetInstance()
                        .SetOperationModeMultiplexed(getNetworkId(),
                                getCnNodeIdValue(),
                                (short) tcn.getForcedMultiplexedCycle());
                plkMode = PlkOperationMode.MULTIPLEXED;
            }
            if (plkMode != null) {
                if (res.IsSuccessful()) {
                    setPlkOperationMode(plkMode);
                } else {
                    OpenConfiguratorMessageConsole.getInstance()
                            .printLibraryErrorMessage(res);
                }
            } else {
                System.err.println("Invalid POWERLINK operation mode");
            }

            // Node Assignment values will be modified by the
            // library. So refresh the project file data.
            OpenConfiguratorProjectUtils.updateNodeAssignmentValues(this);

            // RPDO nodeID will be changed by the library. So
            // refresh the node XDD data
            OpenConfiguratorProjectUtils.persistNodeData(this);

            // Updates the generator attributes in project file.
            OpenConfiguratorProjectUtils.updateGeneratorInfo(this);
        }
    }

    public void copyNode(int nodeId, int stationType, String name)
            throws JDOMException, InterruptedException, IOException {
        copyXdcFile(this, nodeId, stationType, name);
    }

    private void copyXdcFile(Node selectednode, int nodeId, int stationType,
            String name)
            throws JDOMException, InterruptedException, IOException {
        System.err.println("The node XDC path before.."
                + selectednode.getAbsolutePathToXdc());

        TCN cnModel = new TCN();
        cnModel.setName(name);
        cnModel.setNodeID(String.valueOf(nodeId));

        Node newNode = new Node(rootNode, projectXml, cnModel, xddModel);
        try {
            OpenConfiguratorProjectUtils.copyConfigurationFile(selectednode,
                    nodeId, newNode);

        } catch (IOException e1) {

            PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                    e1.getCause().getMessage(), getProject().getName());
            e1.printStackTrace();
        }

        TNodeCollection nodeCollection = null;
        Object nodeCollectionModel = rootNode.getMN().getNodeModel();
        if (nodeCollectionModel instanceof TNetworkConfiguration) {
            TNetworkConfiguration netConfig = (TNetworkConfiguration) nodeCollectionModel;
            nodeCollection = netConfig.getNodeCollection();
        }

        if (getProfileBody(
                xddModel) instanceof ProfileBodyDevicePowerlinkModularHead) {
            Result res = OpenConfiguratorLibraryUtils
                    .addModularHeadNode(newNode);
            System.err.println("Modular CN...");
            if (res.IsSuccessful()) {

                try {
                    rootNode.addNode(nodeCollection, newNode);

                } catch (IOException | JDOMException e) {
                    if ((e.getMessage() != null) && !e.getMessage().isEmpty()) {
                        PluginErrorDialogUtils.showMessageWindow(
                                MessageDialog.ERROR, e.getMessage(), "");
                    } else if ((e.getCause() != null)
                            && (e.getCause().getMessage() != null)
                            && !e.getCause().getMessage().isEmpty()) {
                        PluginErrorDialogUtils.showMessageWindow(
                                MessageDialog.ERROR, ERROR_WHILE_COPYING_XDD,
                                newNode.getProject().getName());
                    }
                }
                if (!selectednode.getInterface().getModuleCollection()
                        .isEmpty()) {
                    for (Module module : selectednode.getInterface()
                            .getModuleCollection().values()) {
                        updateModuleNode(module, newNode);
                    }

                }

            } else {
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        res);

                // Remove the node.
                res = OpenConfiguratorLibraryUtils.removeNode(newNode);
                if (!res.IsSuccessful()) {
                    if (res.GetErrorType() != ErrorCode.NODE_DOES_NOT_EXIST) {
                        // Show or print error message.
                        System.err.println(
                                "ERROR occured while removin the node. "
                                        + OpenConfiguratorLibraryUtils
                                                .getErrorMessage(res));
                    }
                }
            }

        } else {
            System.err.println("Normal CN...");
            Result res = OpenConfiguratorLibraryUtils.addNode(newNode);
            if (res.IsSuccessful()) {

                try {
                    rootNode.addNode(nodeCollection, newNode);

                } catch (IOException | JDOMException e) {
                    if ((e.getMessage() != null) && !e.getMessage().isEmpty()) {
                        PluginErrorDialogUtils.showMessageWindow(
                                MessageDialog.ERROR, e.getMessage(), "");
                    } else if ((e.getCause() != null)
                            && (e.getCause().getMessage() != null)
                            && !e.getCause().getMessage().isEmpty()) {
                        PluginErrorDialogUtils.showMessageWindow(
                                MessageDialog.ERROR, ERROR_WHILE_COPYING_XDD,
                                newNode.getProject().getName());
                    }
                }
            } else {
                PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                        res);

                // Remove the node.
                res = OpenConfiguratorLibraryUtils.removeNode(newNode);
                if (!res.IsSuccessful()) {
                    if (res.GetErrorType() != ErrorCode.NODE_DOES_NOT_EXIST) {
                        // Show or print error message.
                        System.err.println(
                                "ERROR occured while removin the node. "
                                        + OpenConfiguratorLibraryUtils
                                                .getErrorMessage(res));
                    }
                }
            }
        }

        try {

            newNode.addStationTypeofNode(stationType);

        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }

        if (addNodeFirmwareFile(newNode)) {
            System.out.println("Firmware File added.");
        }

        try {
            getProject().refreshLocal(IResource.DEPTH_INFINITE,
                    new NullProgressMonitor());
        } catch (CoreException e) {
            System.err.println("unable to refresh the resource due to "
                    + e.getCause().getMessage());
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
            if (forcedObjTag != null) {
                for (org.epsg.openconfigurator.xmlbinding.projectfile.Object tempForceObj : forcedObjTag
                        .getObject()) {

                    if (java.util.Arrays.equals(tempForceObj.getIndex(),
                            forceObj.getIndex())) {
                        if (forceObj.getSubindex() == null) {
                            alreadyForced = true;
                            break;
                        }
                        if (java.util.Arrays.equals(tempForceObj.getSubindex(),
                                forceObj.getSubindex())) {
                            alreadyForced = true;
                            break;
                        }
                    }
                }

                if (!alreadyForced) {
                    forcedObjTag.getObject().add(forceObj);
                }
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
     * @return The CN features.
     */

    public TCNFeatures getCnNodeFeatures() {
        if (xddModel != null) {
            List<ISO15745Profile> profiles = xddModel.getISO15745Profile();
            for (ISO15745Profile profile : profiles) {
                ProfileBodyDataType profileBody = profile.getProfileBody();

                if (profileBody instanceof ProfileBodyCommunicationNetworkPowerlink) {
                    ProfileBodyCommunicationNetworkPowerlink devProfile = (ProfileBodyCommunicationNetworkPowerlink) profileBody;
                    return devProfile.getNetworkManagement().getCNFeatures();
                }
                if (profileBody instanceof ProfileBodyCommunicationNetworkPowerlinkModularHead) {
                    ProfileBodyCommunicationNetworkPowerlinkModularHead devProfile = (ProfileBodyCommunicationNetworkPowerlinkModularHead) profileBody;
                    return devProfile.getNetworkManagement().getCNFeatures();

                }
            }
        }

        return null;
    }

    /**
     * @return The node ID.
     */
    public short getCnNodeIdValue() {
        return nodeId;
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
     * @return The general features.
     */

    public TGeneralFeatures getGeneralFeatures() {
        if (xddModel != null) {
            List<ISO15745Profile> profiles = xddModel.getISO15745Profile();
            for (ISO15745Profile profile : profiles) {
                ProfileBodyDataType profileBody = profile.getProfileBody();

                if (profileBody instanceof ProfileBodyCommunicationNetworkPowerlink) {
                    ProfileBodyCommunicationNetworkPowerlink devProfile = (ProfileBodyCommunicationNetworkPowerlink) profileBody;
                    return devProfile.getNetworkManagement()
                            .getGeneralFeatures();
                }
                if (profileBody instanceof ProfileBodyCommunicationNetworkPowerlinkModularHead) {
                    ProfileBodyCommunicationNetworkPowerlinkModularHead devProfile = (ProfileBodyCommunicationNetworkPowerlinkModularHead) profileBody;
                    return devProfile.getNetworkManagement()
                            .getGeneralFeatures();
                }
            }
        }

        return null;
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
     * @return List of firmware files added to Node
     */
    public Map<FirmwareManager, Integer> getNodeFirmwareCollection() {
        return nodeFirmwareCollection;
    }

    public List<String> getNodeFirmwareFileNameList() {
        List<String> fwList = new ArrayList<>();

        fwList.clear();

        for (FirmwareManager fwManager : getNodeFirmwareCollection().keySet()) {
            String filename = FilenameUtils
                    .getName(fwManager.getFirmwareConfigPath());
            fwList.add(filename);

        }
        return fwList;
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
     * Gets the name of node based on the ID
     *
     * @param nodeId The ID of node
     * @return Name of Node
     */
    public String getNodeName(String nodeId) {
        List<Node> nodeList = rootNode.getNodeLists(rootNode);
        for (Node node : nodeList) {
            if (node.getNodeIdString().equalsIgnoreCase(nodeId)) {
                return node.getNodeIDWithName();
            }
        }
        return StringUtils.EMPTY;
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
     * @return Instance of ProcessImage
     */
    public ProcessImage getProcessImage() {
        return processImage;
    }

    /**
     * @return The product code value of node from node XDD/XDC.
     */
    public String getProductCodeValue() {
        String value = StringUtils.EMPTY;
        if (getObjectDictionary() != null) {
            if (getObjectDictionary()
                    .getObject(XDD_VENDOR_ID_OBJECT_INDEX_TOCHECK)
                    .getSubObject(XDD_SUBOBJECT_INDEX_PRODUCTCODE) != null) {
                value = getObjectDictionary()
                        .getObject(XDD_VENDOR_ID_OBJECT_INDEX_TOCHECK)
                        .getSubObject(XDD_SUBOBJECT_INDEX_PRODUCTCODE)
                        .getActualDefaultValue();
            }
        } else {
            System.err.println("Object Dictionary not available.");
        }
        return value;
    }

    /**
     * @return The product Name of node.
     */
    public String getProductName() {
        if (xddModel != null) {
            List<ISO15745Profile> profiles = xddModel.getISO15745Profile();
            for (ISO15745Profile profile : profiles) {
                ProfileBodyDataType profileBody = profile.getProfileBody();

                if (profileBody instanceof ProfileBodyDevicePowerlink) {
                    ProfileBodyDevicePowerlink devProfile = (ProfileBodyDevicePowerlink) profileBody;
                    if (devProfile.getDeviceIdentity() != null) {
                        return devProfile.getDeviceIdentity().getProductName()
                                .getValue();
                    }
                }

                if (profileBody instanceof ProfileBodyDevicePowerlinkModularHead) {
                    ProfileBodyDevicePowerlinkModularHead devProfile = (ProfileBodyDevicePowerlinkModularHead) profileBody;
                    if (devProfile.getDeviceIdentity() != null) {
                        return devProfile.getDeviceIdentity().getProductName()
                                .getValue();
                    }
                }
            }
        }

        return StringUtils.EMPTY;
    }

    public ProfileBodyDataType getProfileBody(
            ISO15745ProfileContainer xddModel) {
        if (xddModel != null) {
            List<ISO15745Profile> profiles = xddModel.getISO15745Profile();
            for (ISO15745Profile profile : profiles) {
                ProfileBodyDataType profileBodyDatatype = profile
                        .getProfileBody();
                if (profileBodyDatatype instanceof ProfileBodyDevicePowerlinkModularHead) {
                    return profileBodyDatatype;
                }
            }
        }
        return null;
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
     * @return The valid firmware file for node from the project file.
     */
    public List<FirmwareManager> getValidFirmwareList() {
        List<FirmwareManager> fwList = new ArrayList<>();
        fwList.clear();

        Map<String, FirmwareManager> nodeDevRevisionList = new HashMap<>();
        if (getNodeFirmwareCollection() != null) {
            for (FirmwareManager fwManager : getNodeFirmwareCollection()
                    .keySet()) {
                nodeDevRevisionList.put(fwManager.getdevRevNumber(), fwManager);
                if (!nodeDevRevisionList.isEmpty()) {
                    for (FirmwareManager fwMan : nodeDevRevisionList.values()) {
                        if (fwMan.getNodeIdofFirmware().equalsIgnoreCase(
                                String.valueOf(getCnNodeIdValue()))) {
                            if (fwMan.getFirmwarefileVersion() < fwManager
                                    .getFirmwarefileVersion()) {
                                nodeDevRevisionList.put(
                                        fwManager.getdevRevNumber(), fwManager);
                            }
                        }
                    }
                }

            }
        } else {
            System.err.println("Firmware list not available for node!");
        }
        fwList.addAll(nodeDevRevisionList.values());
        return fwList;
    }

    /**
     * @return The vendor ID value of node from node XDD/XDC.
     */
    public String getVendorIdValue() {
        String value = StringUtils.EMPTY;
        if (getObjectDictionary() != null) {
            if (getObjectDictionary()
                    .getObject(XDD_VENDOR_ID_OBJECT_INDEX_TOCHECK)
                    .getSubObject(XDD_VENDOR_ID_SUBOBJECT_INDEX) != null) {
                value = getObjectDictionary()
                        .getObject(XDD_VENDOR_ID_OBJECT_INDEX_TOCHECK)
                        .getSubObject(XDD_VENDOR_ID_SUBOBJECT_INDEX)
                        .getActualDefaultValue();
            }
        } else {
            System.err.println("Object Dictionary not available.");
        }
        return value;
    }

    /**
     * @return The vendor Name of node.
     */
    public String getVendorName() {
        if (xddModel != null) {
            List<ISO15745Profile> profiles = xddModel.getISO15745Profile();
            for (ISO15745Profile profile : profiles) {
                ProfileBodyDataType profileBody = profile.getProfileBody();

                if (profileBody instanceof ProfileBodyDevicePowerlink) {
                    ProfileBodyDevicePowerlink devProfile = (ProfileBodyDevicePowerlink) profileBody;
                    if (devProfile.getDeviceIdentity() != null) {
                        return devProfile.getDeviceIdentity().getVendorName()
                                .getValue();
                    }
                }
                if (profileBody instanceof ProfileBodyDevicePowerlinkModularHead) {
                    ProfileBodyDevicePowerlinkModularHead devProfile = (ProfileBodyDevicePowerlinkModularHead) profileBody;
                    if (devProfile.getDeviceIdentity() != null) {
                        return devProfile.getDeviceIdentity().getVendorName()
                                .getValue();
                    }
                }
            }
        }

        return StringUtils.EMPTY;
    }

    /**
     * @return The Hardware, Software or Firmware version values.
     */
    public String getVersionValue(String versionType) {
        if (xddModel != null) {
            // Get version details if the XDD model is valid
            List<ISO15745Profile> profiles = xddModel.getISO15745Profile();
            for (ISO15745Profile profile : profiles) {
                ProfileBodyDataType profileBody = profile.getProfileBody();

                if (profileBody instanceof ProfileBodyDevicePowerlink) {
                    ProfileBodyDevicePowerlink devProfile = (ProfileBodyDevicePowerlink) profileBody;
                    for (TVersion ver : devProfile.getDeviceIdentity()
                            .getVersion()) {
                        if (ver.getVersionType()
                                .equalsIgnoreCase(versionType)) {
                            return ver.getValue();
                        }
                    }
                }

                if (profileBody instanceof ProfileBodyDevicePowerlinkModularHead) {
                    ProfileBodyDevicePowerlinkModularHead devProfile = (ProfileBodyDevicePowerlinkModularHead) profileBody;
                    for (TVersion ver : devProfile.getDeviceIdentity()
                            .getVersion()) {
                        if (ver.getVersionType()
                                .equalsIgnoreCase(versionType)) {
                            return ver.getValue();
                        }
                    }
                }
            }
        }

        return StringUtils.EMPTY;
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
     * @return Instance of FirmwareFile.
     */
    public FirmwareFile getXddFirmwareFile() {
        return xddFirmwareFile;
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
        if (xddModel != null) {
            return networkmanagement.getGeneralFeatures().isPDOSelfReceipt();
        }
        return false;
    }

    /**
     * Set ASnd max number
     *
     * @param value AsndMaxNumber value in ns.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public void setAsndMaxNumberOfNode(Short value)
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
    public void setAsyncMtuOfNode(Integer value)
            throws JDOMException, IOException {
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
     * Set modified node Id to node model.
     *
     * @param newNodeId The value of node ID for modification
     * @throws IOException Error with XDC file modifications.
     * @throws JDOMException Error with time modifications.
     * @throws InterruptedException Error with interrupt in thread.
     */
    public void setCnNodeId(short newNodeId)
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

    public void setNodeData() {

        if (getNodeModel() instanceof TCN) {
            tcn = (TCN) getNodeModel();
        } else {
            tcn = null;
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
     * Provides the instance of processImage class
     *
     * @param processImage Instance of ProcessImage
     */
    public void setProcessImage(ProcessImage processImage) {

        if (processImage != null) {
            this.processImage = processImage;
        }
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

            try {
                if (cn.getInterfaceList() != null) {
                    List<org.epsg.openconfigurator.xmlbinding.projectfile.InterfaceList.Interface> interfaceList = cn
                            .getInterfaceList().getInterface();
                    for (org.epsg.openconfigurator.xmlbinding.projectfile.InterfaceList.Interface intfc : interfaceList) {
                        if (interfaceObj != null) {
                            interfaceObj = intfc;
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void updateModuleNode(Module selectedModule, Node newNode)
            throws IOException {
        Object moduleObject = selectedModule.getModelOfModule();
        if (moduleObject instanceof InterfaceList.Interface.Module) {

            InterfaceList.Interface.Module module = (InterfaceList.Interface.Module) moduleObject;
            if (newNode.getNodeModel() instanceof TCN) {
                TCN cnModel = (TCN) newNode.getNodeModel();
                InterfaceList itfc = cnModel.getInterfaceList();

                if (itfc != null) {
                    List<InterfaceList.Interface> itf = itfc.getInterface();
                    for (org.epsg.openconfigurator.xmlbinding.projectfile.InterfaceList.Interface iit : itf) {
                        iit.setId(newNode.getInterface().getInterfaceUId());
                        iit.getModule().add(module);

                    }
                } else {
                    InterfaceList itfcLIst = new InterfaceList();
                    cnModel.setInterfaceList(itfcLIst);
                    InterfaceList.Interface itfcs = new InterfaceList.Interface();
                    itfcs.setId(newNode.getInterface().getInterfaceUId());
                    cnModel.getInterfaceList().getInterface().add(itfcs);
                    itfcs.getModule().add(module);

                }

            }

        } else {
            System.err.println("Invalid Module model");
        }
        HeadNodeInterface selectedNodeObj = newNode.getInterface();

        Module newModule = new Module(rootNode,
                selectedNodeObj.getNode().getProjectXml(), moduleObject,
                selectedNodeObj.getNode(),
                selectedModule.getISO15745ProfileContainer(), selectedNodeObj);

        try {
            OpenConfiguratorProjectUtils
                    .copyModuleConfigurationFile(selectedModule, newModule);
        } catch (IOException e1) {

            e1.printStackTrace();
        }

        Result res = OpenConfiguratorLibraryUtils.addModule(newModule);
        if (res.IsSuccessful()) {
            selectedNodeObj.getModuleCollection()
                    .put(Integer.valueOf(newModule.getPosition()), newModule);

            selectedNodeObj.getAddressCollection()
                    .put(Integer.valueOf(newModule.getAddress()), newModule);

            selectedNodeObj.getModuleNameCollection()
                    .put(newModule.getModuleName(), newModule);

            System.err.println("Module collection values ... "
                    + selectedNodeObj.getModuleCollection().values());

            try {
                OpenConfiguratorProjectUtils.addModuleNode(
                        selectedNodeObj.getNode(), selectedNodeObj, newModule);
            } catch (JDOMException | IOException e) {
                e.printStackTrace();
            }

        } else {
            System.err.println("ERROR occured while adding the module. "
                    + OpenConfiguratorLibraryUtils.getErrorMessage(res));
            PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR, res);

            // Remove the module.
            res = OpenConfiguratorLibraryUtils.removeModule(newModule);
            if (!res.IsSuccessful()) {
                if (res.GetErrorType() != ErrorCode.NODE_DOES_NOT_EXIST) {
                    // Show or print error message.
                    System.err
                            .println("ERROR occured while removin the module. "
                                    + OpenConfiguratorLibraryUtils
                                            .getErrorMessage(res));
                }
            }
        }

        if (newModule.canFirmwareAdded(newModule)) {
            if (addAvailableFirmware(newModule)) {
                System.out.println(
                        "Firmware file for module added successfully.");
            }
        }

        try {
            selectedNodeObj.getNode().getProject().refreshLocal(
                    IResource.DEPTH_INFINITE, new NullProgressMonitor());
        } catch (CoreException e) {
            System.err.println("unable to refresh the resource due to "
                    + e.getCause().getMessage());
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
                    if (!actualValue.equalsIgnoreCase(EMPTY_OBJ_ACTUAL_VALUE)) {
                        if (!object.getActualDefaultValue()
                                .equalsIgnoreCase(actualValue)) {
                            object.setActualValue(actualValue, false);
                            XddJdomOperation.updateActualValue(document, object,
                                    actualValue);
                        }
                    }
                } else {
                    PowerlinkSubobject subObj = object
                            .getSubObject((short) subObjectIdShort);
                    if (subObj != null) {
                        if (!actualValue
                                .equalsIgnoreCase(EMPTY_OBJ_ACTUAL_VALUE)) {
                            if (!subObj.getActualDefaultValue()
                                    .equalsIgnoreCase(actualValue)) {
                                subObj.setActualValue(actualValue, false);
                                XddJdomOperation.updateActualValue(document,
                                        subObj, actualValue);
                            }
                        }
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

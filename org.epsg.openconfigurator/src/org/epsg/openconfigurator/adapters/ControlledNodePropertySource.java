/*******************************************************************************
 * @file   ControlledNodePropertySource.java
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

package org.epsg.openconfigurator.adapters;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.epsg.openconfigurator.console.OpenConfiguratorMessageConsole;
import org.epsg.openconfigurator.lib.wrapper.NodeAssignment;
import org.epsg.openconfigurator.lib.wrapper.OpenConfiguratorCore;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.model.IAbstractNodeProperties;
import org.epsg.openconfigurator.model.IControlledNodeProperties;
import org.epsg.openconfigurator.model.INetworkProperties;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.util.IPowerlinkConstants;
import org.epsg.openconfigurator.util.OpenConfiguratorLibraryUtils;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectUtils;
import org.epsg.openconfigurator.xmlbinding.projectfile.TCN;

/**
 * Describes the node properties for a Controlled node.
 *
 * @see setNodeData
 *
 * @author Ramakrishnan P
 *
 */
public class ControlledNodePropertySource extends AbstractNodePropertySource
        implements IPropertySource {

    // Labels
    private static final String CN_NODE_TYPE_LABEL = "Node Type";
    private static final String CN_FORCED_MULTIPLEXED_CYCLE_LABEL = "Forced Multiplexed Cycle";
    private static final String CN_IS_MANDATORY_LABEL = "Is Mandatory";
    private static final String CN_AUTO_START_NODE_LABEL = "Is Autostart Node";
    private static final String CN_RESET_IN_OPERATIONAL_LABEL = "Reset In Operational";
    private static final String CN_VERIFY_APP_SW_VERSION_LABEL = "Do Verify App S/W Version";
    private static final String CN_AUTO_APP_SW_UPDATE_ALLOWED_LABEL = "Auto App S/W Update Allowed";
    private static final String CN_VERIFY_DEVICE_TYPE_LABEL = "Verify Device Type";
    private static final String CN_VERIFY_VENDOR_ID_LABEL = "Verify Vendor Id";
    private static final String CN_VERIFY_REVISION_NUMBER_LABEL = "Verify Revision Number";
    private static final String CN_VERIFY_PRODUCT_CODE_LABEL = "Verify Product Code";
    private static final String CN_VERIFY_SERIAL_NUMBER_LABEL = "Verify Serial Number";

    private static final String CN_POLL_RESPONSE_TIMEOUT_LABEL = "PollResponse Timeout ("
            + "\u00B5" + "s)";

    /**
     * Station types
     */
    private static final String[] NODE_TYPES = { "Normal", "Chained",
            "Multiplexed" };

    // Property descriptors.
    private static final ComboBoxPropertyDescriptor nodeTypeDescriptor = new ComboBoxPropertyDescriptor(
            IControlledNodeProperties.CN_NODE_TYPE_OBJECT, CN_NODE_TYPE_LABEL,
            NODE_TYPES);

    private static final TextPropertyDescriptor nodeIdEditableDescriptor = new TextPropertyDescriptor(
            IAbstractNodeProperties.NODE_ID_EDITABLE_OBJECT, NODE_ID_LABEL);
    private static final PropertyDescriptor nodeIDDescriptor = new PropertyDescriptor(
            IAbstractNodeProperties.NODE_ID_EDITABLE_OBJECT, NODE_ID_LABEL);
    private static final TextPropertyDescriptor forcedMultiplexedCycle = new TextPropertyDescriptor(
            IControlledNodeProperties.CN_FORCED_MULTIPLEXED_CYCLE_OBJECT,
            CN_FORCED_MULTIPLEXED_CYCLE_LABEL);
    private static final ComboBoxPropertyDescriptor isMandatory = new ComboBoxPropertyDescriptor(
            IControlledNodeProperties.CN_IS_MANDATORY_OBJECT,
            CN_IS_MANDATORY_LABEL, IPropertySourceSupport.YES_NO);
    private static final ComboBoxPropertyDescriptor autostartNode = new ComboBoxPropertyDescriptor(
            IControlledNodeProperties.CN_AUTO_START_NODE_OBJECT,
            CN_AUTO_START_NODE_LABEL, IPropertySourceSupport.YES_NO);
    private static final ComboBoxPropertyDescriptor resetInOperational = new ComboBoxPropertyDescriptor(
            IControlledNodeProperties.CN_RESET_IN_OPERATIONAL_OBJECT,
            CN_RESET_IN_OPERATIONAL_LABEL, IPropertySourceSupport.YES_NO);
    private static final ComboBoxPropertyDescriptor verifyAppSwVersion = new ComboBoxPropertyDescriptor(
            IControlledNodeProperties.CN_VERIFY_APP_SW_VERSION_OBJECT,
            CN_VERIFY_APP_SW_VERSION_LABEL, IPropertySourceSupport.YES_NO);
    private static final ComboBoxPropertyDescriptor autoAppSwUpdateAllowed = new ComboBoxPropertyDescriptor(
            IControlledNodeProperties.CN_AUTO_APP_SW_UPDATE_ALLOWED_OBJECT,
            CN_AUTO_APP_SW_UPDATE_ALLOWED_LABEL, IPropertySourceSupport.YES_NO);
    private static final ComboBoxPropertyDescriptor verifyDeviceType = new ComboBoxPropertyDescriptor(
            IControlledNodeProperties.CN_VERIFY_DEVICE_TYPE_OBJECT,
            CN_VERIFY_DEVICE_TYPE_LABEL, IPropertySourceSupport.YES_NO);
    private static final ComboBoxPropertyDescriptor verifyVendorId = new ComboBoxPropertyDescriptor(
            IControlledNodeProperties.CN_VERIFY_VENDOR_ID_OBJECT,
            CN_VERIFY_VENDOR_ID_LABEL, IPropertySourceSupport.YES_NO);
    private static final ComboBoxPropertyDescriptor verifyRevisionNumber = new ComboBoxPropertyDescriptor(
            IControlledNodeProperties.CN_VERIFY_REVISION_NUMBER_OBJECT,
            CN_VERIFY_REVISION_NUMBER_LABEL, IPropertySourceSupport.YES_NO);
    private static final ComboBoxPropertyDescriptor verifyProductCode = new ComboBoxPropertyDescriptor(
            IControlledNodeProperties.CN_VERIFY_PRODUCT_CODE_OBJECT,
            CN_VERIFY_PRODUCT_CODE_LABEL, IPropertySourceSupport.YES_NO);
    private static final ComboBoxPropertyDescriptor verifySerialNumber = new ComboBoxPropertyDescriptor(
            IControlledNodeProperties.CN_VERIFY_SERIAL_NUMBER_OBJECT,
            CN_VERIFY_SERIAL_NUMBER_LABEL, IPropertySourceSupport.YES_NO);

    private static final TextPropertyDescriptor presTimeoutDescriptor = new TextPropertyDescriptor(
            IControlledNodeProperties.CN_POLL_RESPONSE_TIMEOUT_OBJECT,
            CN_POLL_RESPONSE_TIMEOUT_LABEL);

    static {
        nodeTypeDescriptor.setCategory(IPropertySourceSupport.BASIC_CATEGORY);
        nodeIDDescriptor.setCategory(IPropertySourceSupport.BASIC_CATEGORY);

        forcedMultiplexedCycle
                .setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        forcedMultiplexedCycle
                .setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);

        isMandatory.setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        isMandatory.setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);
        isMandatory.setDescription(
                IControlledNodeProperties.CN_IS_MANDATORY_DESCRIPTION);

        autostartNode.setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        autostartNode.setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);
        autostartNode.setDescription(
                IControlledNodeProperties.CN_AUTO_START_NODE_DESCRIPTION);

        resetInOperational
                .setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        resetInOperational
                .setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);
        resetInOperational.setDescription(
                IControlledNodeProperties.CN_RESET_IN_OPERATIONAL_DESCRIPTION);

        verifyAppSwVersion
                .setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        verifyAppSwVersion
                .setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);
        verifyAppSwVersion.setDescription(
                IControlledNodeProperties.CN_VERIFY_APP_SW_VERSION_DESCRIPTION);

        autoAppSwUpdateAllowed
                .setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        autoAppSwUpdateAllowed
                .setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);
        verifyDeviceType.setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        verifyDeviceType
                .setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);
        verifyDeviceType.setDescription(
                IControlledNodeProperties.CN_VERIFY_DEVICE_TYPE_DESCRIPTION);

        verifyVendorId.setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        verifyVendorId
                .setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);

        verifyRevisionNumber
                .setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        verifyRevisionNumber
                .setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);

        verifyProductCode.setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        verifyProductCode
                .setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);

        verifySerialNumber
                .setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        verifySerialNumber
                .setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);

        presTimeoutDescriptor
                .setCategory(IPropertySourceSupport.BASIC_CATEGORY);
    }

    // Error messages.
    private static final String ERROR_PRES_TIMEOUT_CANNOT_BE_EMPTY = "PRes TimeOut value cannot be empty.";

    private Node cnNode;
    private TCN tcn;

    public ControlledNodePropertySource(Node cnNode) {
        super();
        setNodeData(cnNode);

        nameDescriptor.setCategory(IPropertySourceSupport.BASIC_CATEGORY);
        nodeErrorDescriptor.setCategory(IPropertySourceSupport.BASIC_CATEGORY);
        nodeIdEditableDescriptor
                .setCategory(IPropertySourceSupport.BASIC_CATEGORY);
        nodeIdEditableDescriptor.setValidator(new ICellEditorValidator() {

            @Override
            public String isValid(Object value) {
                return handleSetNodeId(value);
            }

        });
        configurationDescriptor
                .setCategory(IPropertySourceSupport.BASIC_CATEGORY);

        nodeTypeDescriptor.setValidator(new ICellEditorValidator() {
            @Override
            public String isValid(Object value) {
                return handleNodeTypeChange(value);
            }
        });

        forcedMultiplexedCycle.setValidator(new ICellEditorValidator() {
            @Override
            public String isValid(Object value) {
                return NOT_SUPPORTED;
            }
        });

        isMandatory.setValidator(new ICellEditorValidator() {
            @Override
            public String isValid(Object value) {
                return handleNodeAssignValue(
                        NodeAssignment.NMT_NODEASSIGN_MANDATORY_CN, value);
            }
        });

        autostartNode.setValidator(new ICellEditorValidator() {
            @Override
            public String isValid(Object value) {
                return handleNodeAssignValue(
                        NodeAssignment.NMT_NODEASSIGN_START_CN, value);
            }
        });

        resetInOperational.setValidator(new ICellEditorValidator() {
            @Override
            public String isValid(Object value) {
                return handleNodeAssignValue(
                        NodeAssignment.NMT_NODEASSIGN_KEEPALIVE, value);
            }
        });

        verifyAppSwVersion.setValidator(new ICellEditorValidator() {
            @Override
            public String isValid(Object value) {
                return handleNodeAssignValue(
                        NodeAssignment.NMT_NODEASSIGN_SWVERSIONCHECK, value);
            }
        });

        autoAppSwUpdateAllowed.setValidator(new ICellEditorValidator() {
            @Override
            public String isValid(Object value) {
                return handleNodeAssignValue(
                        NodeAssignment.NMT_NODEASSIGN_SWUPDATE, value);
            }
        });

        verifyDeviceType.setValidator(new ICellEditorValidator() {
            @Override
            public String isValid(Object value) {
                return NOT_SUPPORTED;
            }
        });
        verifyVendorId.setValidator(new ICellEditorValidator() {
            @Override
            public String isValid(Object value) {
                return NOT_SUPPORTED;
            }
        });

        verifyRevisionNumber.setValidator(new ICellEditorValidator() {
            @Override
            public String isValid(Object value) {
                return NOT_SUPPORTED;
            }
        });

        verifyProductCode.setValidator(new ICellEditorValidator() {
            @Override
            public String isValid(Object value) {
                return NOT_SUPPORTED;
            }
        });

        verifySerialNumber.setValidator(new ICellEditorValidator() {
            @Override
            public String isValid(Object value) {
                return NOT_SUPPORTED;
            }
        });

        isAsyncOnly.setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        isAsyncOnly.setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);

        isType1Router.setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        isType1Router.setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);
        isType2Router.setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        isType2Router.setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);
        forcedObjects.setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);

        presTimeoutDescriptor.setValidator(new ICellEditorValidator() {
            @Override
            public String isValid(Object value) {
                return handlePresTimeout(value);
            }
        });
    }

    /**
     * Adds the list of controlled node property descriptors.
     *
     * @param propertyList The list instance to be added.
     */
    private void addControlledNodePropertyDescriptors(
            List<IPropertyDescriptor> propertyList) {

        if (tcn == null) {
            return;
        }
        // checks whether the XDC import has occurred
        if (!cnNode.hasXdd()) {
            propertyList.add(nameDescriptor);
            propertyList.add(nodeIdEditableDescriptor);

            if (tcn.getPathToXDC() != null) {

                propertyList.add(configurationDescriptor);
            }

            propertyList.add(nodeTypeDescriptor);
            // ForcedMultiplexedCycle is not supported by POWERLINK stack
            // propertyList.add(forcedMultiplexedCycle);
            propertyList.add(isMandatory);
            propertyList.add(autostartNode);
            propertyList.add(resetInOperational);
            propertyList.add(verifyAppSwVersion);
            propertyList.add(autoAppSwUpdateAllowed);
            propertyList.add(verifyDeviceType);
            propertyList.add(verifyVendorId);
            propertyList.add(verifyRevisionNumber);
            propertyList.add(verifyProductCode);
            propertyList.add(verifySerialNumber);
            propertyList.add(isAsyncOnly);
            propertyList.add(isType1Router);
            propertyList.add(isType2Router);

            propertyList.add(presTimeoutDescriptor);
            // propertyList.add(lossSocToleranceDescriptor);
            if (tcn.getForcedObjects() != null) {
                propertyList.add(forcedObjects);
            }
        } else {
            propertyList.add(readOnlynameDescriptor);
            propertyList.add(nodeIDDescriptor);

            if (tcn.getPathToXDC() != null) {

                propertyList.add(configurationDescriptor);
            }
            propertyList.add(nodeErrorDescriptor);
        }
    }

    @Override
    public Object getEditableValue() {
        return tcn;
    }

    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        List<IPropertyDescriptor> propertyList = new ArrayList<IPropertyDescriptor>();
        addControlledNodePropertyDescriptors(propertyList);

        IPropertyDescriptor[] propertyDescriptorArray = {};
        propertyDescriptorArray = propertyList.toArray(propertyDescriptorArray);
        return propertyDescriptorArray;
    }

    @Override
    public Object getPropertyValue(Object id) {

        Object retObj = null;
        try {
            if (id instanceof String) {
                String objectId = (String) id;
                switch (objectId) {
                    case IAbstractNodeProperties.NODE_NAME_OBJECT:
                        retObj = cnNode.getName();
                        break;
                    case IAbstractNodeProperties.NODE_ID_EDITABLE_OBJECT:
                        retObj = cnNode.getNodeIdString();
                        break;
                    case IAbstractNodeProperties.NODE_CONIFG_OBJECT:
                        retObj = tcn.getPathToXDC();
                        break;
                    case IAbstractNodeProperties.NODE_ERROR_OBJECT:
                        retObj = IAbstractNodeProperties.NODE_ERROR_DESCRIPTION;
                        break;
                    case IControlledNodeProperties.CN_NODE_TYPE_OBJECT: {
                        int value = 0; // Normal Station.
                        if (tcn.isIsChained()) {
                            value = 1;
                        } else if (tcn.isIsMultiplexed()) {
                            value = 2;
                        }

                        retObj = new Integer(value);
                        break;
                    }
                    case IControlledNodeProperties.CN_FORCED_MULTIPLEXED_CYCLE_OBJECT:
                        String forcedMultiplexedCycle = String
                                .valueOf(tcn.getForcedMultiplexedCycle());
                        if (forcedMultiplexedCycle.isEmpty()) {
                            return "";
                        }
                        retObj = String
                                .valueOf(tcn.getForcedMultiplexedCycle());
                        break;
                    case IControlledNodeProperties.CN_IS_MANDATORY_OBJECT: {
                        int value = (tcn.isIsMandatory() == true) ? 0 : 1;
                        retObj = new Integer(value);
                        break;
                    }
                    case IControlledNodeProperties.CN_AUTO_START_NODE_OBJECT: {
                        int value = (tcn.isAutostartNode() == true) ? 0 : 1;
                        retObj = new Integer(value);
                        break;
                    }
                    case IControlledNodeProperties.CN_RESET_IN_OPERATIONAL_OBJECT: {
                        int value = (tcn.isResetInOperational() == true) ? 0
                                : 1;
                        retObj = new Integer(value);
                        break;
                    }
                    case IControlledNodeProperties.CN_VERIFY_APP_SW_VERSION_OBJECT: {
                        int value = (tcn.isVerifyAppSwVersion() == true) ? 0
                                : 1;
                        retObj = new Integer(value);
                        break;
                    }
                    case IControlledNodeProperties.CN_AUTO_APP_SW_UPDATE_ALLOWED_OBJECT: {
                        int value = (tcn.isAutoAppSwUpdateAllowed() == true) ? 0
                                : 1;
                        retObj = new Integer(value);
                        break;
                    }
                    case IControlledNodeProperties.CN_VERIFY_DEVICE_TYPE_OBJECT: {
                        int value = (tcn.isVerifyDeviceType() == true) ? 0 : 1;
                        retObj = new Integer(value);
                        break;
                    }
                    case IControlledNodeProperties.CN_VERIFY_VENDOR_ID_OBJECT: {
                        int value = (tcn.isVerifyVendorId() == true) ? 0 : 1;
                        retObj = new Integer(value);
                        break;
                    }
                    case IControlledNodeProperties.CN_VERIFY_REVISION_NUMBER_OBJECT: {
                        int value = (tcn.isVerifyRevisionNumber() == true) ? 0
                                : 1;
                        retObj = new Integer(value);
                        break;
                    }
                    case IControlledNodeProperties.CN_VERIFY_PRODUCT_CODE_OBJECT: {
                        int value = (tcn.isVerifyProductCode() == true) ? 0 : 1;
                        retObj = new Integer(value);
                        break;
                    }
                    case IControlledNodeProperties.CN_VERIFY_SERIAL_NUMBER_OBJECT: {
                        int value = (tcn.isVerifySerialNumber() == true) ? 0
                                : 1;
                        retObj = new Integer(value);
                        break;
                    }
                    case IAbstractNodeProperties.NODE_IS_ASYNC_ONLY_OBJECT: {
                        int value = (tcn.isIsAsyncOnly() == true) ? 0 : 1;
                        retObj = new Integer(value);
                        break;
                    }
                    case IAbstractNodeProperties.NODE_IS_TYPE1_ROUTER_OBJECT: {
                        int value = (tcn.isIsType1Router() == true) ? 0 : 1;
                        retObj = new Integer(value);
                        break;
                    }
                    case IAbstractNodeProperties.NODE_IS_TYPE2_ROUTER_OBJECT: {
                        int value = (tcn.isIsType2Router() == true) ? 0 : 1;
                        retObj = new Integer(value);
                        break;
                    }
                    case IAbstractNodeProperties.NODE_FORCED_OBJECTS_OBJECT:
                        String forcedObjectsString = cnNode
                                .getForcedObjectsString();
                        if (forcedObjectsString.isEmpty()) {
                            return "";
                        }
                        retObj = cnNode.getForcedObjectsString();
                        break;
                    case IControlledNodeProperties.CN_POLL_RESPONSE_TIMEOUT_OBJECT: {
                        long presTimeoutinNs = cnNode.getPresTimeoutvalue();
                        long presTimeoutInMs = presTimeoutinNs / 1000;
                        String value = String.valueOf(presTimeoutInMs);
                        if (value.isEmpty()) {
                            return "";
                        }
                        retObj = String.valueOf(presTimeoutInMs);
                        break;
                    }
                    default:
                        System.err.println(
                                "Invalid object string ID:" + objectId);
                        break;
                }
            } else {
                System.err.println("Invalid object ID:" + id);
            }
        } catch (Exception e) {
            OpenConfiguratorMessageConsole.getInstance().printErrorMessage(
                    "Property: " + id + " " + e.getMessage(),
                    cnNode.getNetworkId());
            retObj = StringUtils.EMPTY;
        }
        return retObj;
    }

    @Override
    protected String handleNodeAssignValue(NodeAssignment nodeAssign,
            Object value) {
        if (value instanceof Integer) {
            int val = ((Integer) value).intValue();
            boolean result = (val == 0) ? true : false;
            // TODO: validate the value with openCONFIGURATOR library.

            Result res = OpenConfiguratorLibraryUtils
                    .setNodeAssignment(nodeAssign, cnNode, result);
            if (!res.IsSuccessful()) {
                return OpenConfiguratorLibraryUtils.getErrorMessage(res);
            }
        } else {
            System.err.println(
                    "handleNodeAssignValue: Invalid value type:" + value);
        }
        return null;
    }

    /**
     * Handle node type changes.
     *
     * @param value The new node type.
     *
     * @return Returns a string indicating whether the given value is valid;
     *         null means valid, and non-null means invalid, with the result
     *         being the error message to display to the end user.
     */
    protected String handleNodeTypeChange(Object value) {
        if (value instanceof Integer) {
            int val = ((Integer) value).intValue();
            if (val == 0) { // Normal Station.
                Result res = OpenConfiguratorCore.GetInstance()
                        .ResetOperationMode(cnNode.getNetworkId(),
                                cnNode.getNodeId());
                if (!res.IsSuccessful()) {
                    return OpenConfiguratorLibraryUtils.getErrorMessage(res);
                }
            } else if (val == 1) { // Chained
                Result res = OpenConfiguratorCore.GetInstance()
                        .SetOperationModeChained(cnNode.getNetworkId(),
                                cnNode.getNodeId());
                if (!res.IsSuccessful()) {
                    return OpenConfiguratorLibraryUtils.getErrorMessage(res);
                }
            } else if (val == 2) { // Multiplexed
                Result res = OpenConfiguratorCore.GetInstance()
                        .SetOperationModeMultiplexed(cnNode.getNetworkId(),
                                cnNode.getNodeId(),
                                (short) tcn.getForcedMultiplexedCycle());
                if (!res.IsSuccessful()) {
                    return OpenConfiguratorLibraryUtils.getErrorMessage(res);
                }
            } else {
                System.err.println("Invalid node type:" + val);
            }
        } else {
            System.err.println("Invalid value type" + value);
        }
        return null;
    }

    /**
     * Handle PRes timeout changes in the properties.
     *
     * @param value New value for the PRes timeout.
     * @return Returns a string indicating whether the given value is valid;
     *         null means valid, and non-null means invalid, with the result
     *         being the error message to display to the end user.
     */
    protected String handlePresTimeout(Object value) {
        if (value instanceof String) {
            if (((String) value).isEmpty()) {
                return ERROR_PRES_TIMEOUT_CANNOT_BE_EMPTY;
            }
            // validate the value with openCONFIGURATOR library.
            long presTimeoutInNs = Long.decode((String) value).longValue()
                    * 1000;
            Result validateResult = OpenConfiguratorLibraryUtils
                    .validateSubobjectActualValue(cnNode.getNetworkId(),
                            IPowerlinkConstants.MN_DEFAULT_NODE_ID,
                            INetworkProperties.POLL_RESPONSE_TIMEOUT_OBJECT_ID,
                            cnNode.getNodeId(), String.valueOf(presTimeoutInNs),
                            false);
            if (!validateResult.IsSuccessful()) {
                return OpenConfiguratorLibraryUtils
                        .getErrorMessage(validateResult);
            }
        } else {
            System.err
                    .println("handlePresTimeout: Invalid value type:" + value);
        }

        return null;
    }

    /**
     * Handle NodeId changes in properties
     *
     * @param id New Id for the Node
     * @return Returns a string indicating whether the given value is valid;
     *         null means invalid, and non-null means valid, with the result
     *         being the error message to display to the end user.
     */
    private String handleSetNodeId(Object id) {
        if (id instanceof String) {
            if (((String) id).isEmpty()) {
                return ERROR_NODE_ID_CANNOT_BE_EMPTY;
            }
            try {
                short nodeIDvalue = Short.valueOf(((String) id));
                if ((nodeIDvalue == 0)
                        || (nodeIDvalue >= IPowerlinkConstants.MN_DEFAULT_NODE_ID)) {
                    return "Invalid node id for a Controlled node";
                }

                if (nodeIDvalue == cnNode.getNodeId()) {
                    return null;
                }

                boolean nodeIdAvailable = cnNode.getPowerlinkRootNode()
                        .isNodeIdAlreadyAvailable(nodeIDvalue);
                if (nodeIdAvailable) {
                    return "Node with id " + nodeIDvalue + " already exists.";
                }
            } catch (NumberFormatException ex) {
                return "Invalid node id for a Controlled node";
            }
        }
        return null;
    }

    /**
     * Handles Node name Changes in properties
     */
    @Override
    protected String handleSetNodeName(Object name) {
        if (name instanceof String) {
            String nodeName = ((String) name);
            if (nodeName.isEmpty()) {
                return ERROR_NODE_NAME_CANNOT_BE_EMPTY;
            }

            if (nodeName.charAt(0) == ' ') {
                return "Invalid name";
            }

            Result res = OpenConfiguratorCore.GetInstance().SetNodeName(
                    cnNode.getNetworkId(), cnNode.getNodeId(), nodeName);
            if (!res.IsSuccessful()) {
                return OpenConfiguratorLibraryUtils.getErrorMessage(res);
            }
        } else {
            System.err.println("handleSetNodeName: Invalid value type:" + name);
        }
        return null;
    }

    @Override
    public boolean isPropertySet(Object id) {
        return false;
    }

    @Override
    public void resetPropertyValue(Object id) {

    }

    /**
     * Set the node details if the property source instance to be re-used for a
     * new node.
     *
     * @param cnNode The node instance.
     */
    public void setNodeData(final Node cnNode) {
        this.cnNode = cnNode;

        if (cnNode.getNodeModel() instanceof TCN) {
            tcn = (TCN) cnNode.getNodeModel();
        } else {
            tcn = null;
        }
    }

    /**
     * sets the value to the Controlled node properties
     */
    @Override
    public void setPropertyValue(Object id, Object value) {
        Result res = new Result();
        try {
            if (id instanceof String) {
                String objectId = (String) id;
                switch (objectId) {
                    case IAbstractNodeProperties.NODE_NAME_OBJECT:
                        res = OpenConfiguratorCore.GetInstance().SetNodeName(
                                cnNode.getNetworkId(), cnNode.getNodeId(),
                                (String) value);
                        if (!res.IsSuccessful()) {
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printLibraryErrorMessage(res);
                        } else {
                            cnNode.setName((String) value);
                        }
                        break;
                    case IAbstractNodeProperties.NODE_ID_EDITABLE_OBJECT:
                        short nodeIDvalue = Short.valueOf(((String) value));

                        short oldNodeId = cnNode.getNodeId();
                        cnNode.getPowerlinkRootNode().setNodeId(oldNodeId,
                                nodeIDvalue);
                        break;
                    case IAbstractNodeProperties.NODE_CONIFG_OBJECT:
                        System.err.println(objectId + " made editable");
                        break;
                    case IControlledNodeProperties.CN_NODE_TYPE_OBJECT: {

                        if (value instanceof Integer) {
                            int val = ((Integer) value).intValue();
                            if (val == 0) { // Normal Station.
                                tcn.setIsChained(false);
                                tcn.setIsMultiplexed(false);
                            } else if (val == 1) {

                                tcn.setIsChained(true);
                                tcn.setIsMultiplexed(false);
                            } else if (val == 2) {

                                tcn.setIsChained(false);
                                tcn.setIsMultiplexed(true);
                            }

                            // Node Assignment values will be modified by the
                            // library. So refresh the project file data.
                            OpenConfiguratorProjectUtils
                                    .updateNodeAssignmentValues(cnNode);

                            // RPDO nodeID will be changed by the library. So
                            // refresh the node XDD data
                            OpenConfiguratorProjectUtils
                                    .persistNodeData(cnNode);

                            // Updates the generator attributes in project file.
                            OpenConfiguratorProjectUtils
                                    .updateGeneratorInfo(cnNode);
                        } else {
                            System.err.println("Invalid value type");
                        }
                        break;
                    }
                    case IControlledNodeProperties.CN_FORCED_MULTIPLEXED_CYCLE_OBJECT:

                        tcn.setForcedMultiplexedCycle(
                                Integer.decode((String) value));
                        OpenConfiguratorProjectUtils.updateNodeAttributeValue(
                                cnNode, objectId, (String) value);
                        break;
                    case IControlledNodeProperties.CN_IS_MANDATORY_OBJECT: {
                        if (value instanceof Integer) {
                            int val = ((Integer) value).intValue();
                            boolean result = (val == 0) ? true : false;
                            tcn.setIsMandatory(result);
                            OpenConfiguratorProjectUtils
                                    .updateNodeAttributeValue(cnNode, objectId,
                                            String.valueOf(result));
                        } else {
                            System.err.println("Invalid value type");
                        }
                        break;
                    }
                    case IControlledNodeProperties.CN_AUTO_START_NODE_OBJECT: {

                        if (value instanceof Integer) {
                            int val = ((Integer) value).intValue();
                            boolean result = (val == 0) ? true : false;
                            tcn.setAutostartNode(result);
                            OpenConfiguratorProjectUtils
                                    .updateNodeAttributeValue(cnNode, objectId,
                                            String.valueOf(result));
                        } else {
                            System.err.println("Invalid value type");
                        }
                        break;
                    }
                    case IControlledNodeProperties.CN_RESET_IN_OPERATIONAL_OBJECT: {
                        if (value instanceof Integer) {
                            int val = ((Integer) value).intValue();
                            boolean result = (val == 0) ? true : false;
                            tcn.setResetInOperational(result);
                            OpenConfiguratorProjectUtils
                                    .updateNodeAttributeValue(cnNode, objectId,
                                            String.valueOf(result));
                        } else {
                            System.err.println("Invalid value type");
                        }
                        break;
                    }
                    case IControlledNodeProperties.CN_VERIFY_APP_SW_VERSION_OBJECT: {
                        if (value instanceof Integer) {
                            int val = ((Integer) value).intValue();
                            boolean result = (val == 0) ? true : false;
                            tcn.setVerifyAppSwVersion(result);
                            OpenConfiguratorProjectUtils
                                    .updateNodeAttributeValue(cnNode, objectId,
                                            String.valueOf(result));
                        } else {
                            System.err.println("Invalid value type");
                        }
                        break;
                    }
                    case IControlledNodeProperties.CN_AUTO_APP_SW_UPDATE_ALLOWED_OBJECT: {
                        if (value instanceof Integer) {
                            int val = ((Integer) value).intValue();
                            boolean result = (val == 0) ? true : false;
                            tcn.setAutoAppSwUpdateAllowed(result);
                            OpenConfiguratorProjectUtils
                                    .updateNodeAttributeValue(cnNode, objectId,
                                            String.valueOf(result));
                        } else {
                            System.err.println("Invalid value type");
                        }
                        break;
                    }
                    case IControlledNodeProperties.CN_VERIFY_DEVICE_TYPE_OBJECT: {
                        if (value instanceof Integer) {
                            int val = ((Integer) value).intValue();
                            boolean result = (val == 0) ? true : false;
                            tcn.setVerifyDeviceType(result);
                            OpenConfiguratorProjectUtils
                                    .updateNodeAttributeValue(cnNode, objectId,
                                            String.valueOf(result));
                        } else {
                            System.err.println("Invalid value type");
                        }
                        break;
                    }
                    case IControlledNodeProperties.CN_VERIFY_VENDOR_ID_OBJECT: {
                        if (value instanceof Integer) {
                            int val = ((Integer) value).intValue();
                            boolean result = (val == 0) ? true : false;
                            tcn.setVerifyVendorId(result);
                            OpenConfiguratorProjectUtils
                                    .updateNodeAttributeValue(cnNode, objectId,
                                            String.valueOf(result));
                        } else {
                            System.err.println("Invalid value type");
                        }
                        break;
                    }
                    case IControlledNodeProperties.CN_VERIFY_REVISION_NUMBER_OBJECT: {
                        if (value instanceof Integer) {
                            int val = ((Integer) value).intValue();
                            boolean result = (val == 0) ? true : false;
                            tcn.setVerifyRevisionNumber(result);
                            OpenConfiguratorProjectUtils
                                    .updateNodeAttributeValue(cnNode, objectId,
                                            String.valueOf(result));
                        } else {
                            System.err.println("Invalid value type");
                        }
                        break;
                    }
                    case IControlledNodeProperties.CN_VERIFY_PRODUCT_CODE_OBJECT: {
                        if (value instanceof Integer) {
                            int val = ((Integer) value).intValue();
                            boolean result = (val == 0) ? true : false;
                            tcn.setVerifyProductCode(result);
                            OpenConfiguratorProjectUtils
                                    .updateNodeAttributeValue(cnNode, objectId,
                                            String.valueOf(result));
                        } else {
                            System.err.println("Invalid value type");
                        }
                        break;
                    }
                    case IControlledNodeProperties.CN_VERIFY_SERIAL_NUMBER_OBJECT: {
                        if (value instanceof Integer) {
                            int val = ((Integer) value).intValue();
                            boolean result = (val == 0) ? true : false;
                            tcn.setVerifySerialNumber(result);
                            OpenConfiguratorProjectUtils
                                    .updateNodeAttributeValue(cnNode, objectId,
                                            String.valueOf(result));
                        } else {
                            System.err.println("Invalid value type");
                        }
                        break;
                    }
                    case IAbstractNodeProperties.NODE_IS_ASYNC_ONLY_OBJECT: {
                        if (value instanceof Integer) {
                            int val = ((Integer) value).intValue();
                            boolean result = (val == 0) ? true : false;
                            tcn.setIsAsyncOnly(result);
                            OpenConfiguratorProjectUtils
                                    .updateNodeAttributeValue(cnNode, objectId,
                                            String.valueOf(result));
                        } else {
                            System.err.println("Invalid value type");
                        }
                        break;
                    }
                    case IAbstractNodeProperties.NODE_IS_TYPE1_ROUTER_OBJECT: {
                        if (value instanceof Integer) {
                            int val = ((Integer) value).intValue();
                            boolean result = (val == 0) ? true : false;
                            tcn.setIsType1Router(result);
                            OpenConfiguratorProjectUtils
                                    .updateNodeAttributeValue(cnNode, objectId,
                                            String.valueOf(result));
                        } else {
                            System.err.println("Invalid value type");
                        }
                        break;
                    }
                    case IAbstractNodeProperties.NODE_IS_TYPE2_ROUTER_OBJECT: {
                        if (value instanceof Integer) {
                            int val = ((Integer) value).intValue();
                            boolean result = (val == 0) ? true : false;
                            tcn.setIsType2Router(result);
                            OpenConfiguratorProjectUtils
                                    .updateNodeAttributeValue(cnNode, objectId,
                                            String.valueOf(result));
                        } else {
                            System.err.println("Invalid value type");
                        }
                        break;
                    }
                    case IAbstractNodeProperties.NODE_FORCED_OBJECTS_OBJECT:
                        // Ignore
                        break;
                    case IControlledNodeProperties.CN_POLL_RESPONSE_TIMEOUT_OBJECT: {
                        long presTimeoutInNs = Long.decode((String) value)
                                .longValue() * 1000;
                        res = OpenConfiguratorCore.GetInstance().SetPResTimeOut(
                                cnNode.getNetworkId(), cnNode.getNodeId(),
                                presTimeoutInNs);
                        if (res.IsSuccessful()) {
                            cnNode.setCnPresTimeout(
                                    String.valueOf(presTimeoutInNs));
                        } else {
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printLibraryErrorMessage(res);
                        }

                        break;
                    }
                    default:
                        System.err.println(
                                "Invalid object string ID:" + objectId);
                        break;
                }
            } else {
                System.err.println("Invalid object ID:" + id);
            }
        } catch (Exception e) {
            OpenConfiguratorMessageConsole.getInstance().printErrorMessage(
                    "Property: " + id + " " + e.getMessage(),
                    cnNode.getNetworkId());
        }

        try {
            cnNode.getProject().refreshLocal(IResource.DEPTH_INFINITE,
                    new NullProgressMonitor());
        } catch (CoreException e) {
            System.err.println("unable to refresh the resource due to "
                    + e.getCause().getMessage());
        }
    }
}

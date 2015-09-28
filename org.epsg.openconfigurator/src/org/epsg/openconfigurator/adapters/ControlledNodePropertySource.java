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

import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.epsg.openconfigurator.lib.wrapper.NodeAssignment;
import org.epsg.openconfigurator.lib.wrapper.OpenConfiguratorCore;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.model.IAbstractNodeProperties;
import org.epsg.openconfigurator.model.IControlledNodeProperties;
import org.epsg.openconfigurator.model.Node;
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

    private static final String CN_POLL_RESPONSE_MAX_LATENCY_LABEL = "PollResponse Max Latency(ns)";
    private static final String CN_POLL_RESPONSE_TIMEOUT_LABEL = "PollResponse Timeout(" + "\u00B5" + "s)";

    /**
     * Station types
     */
    private static final String[] NODE_TYPES = { "Normal", "Chained",
            "Multiplexed" };

    // Property descriptors.
    private static final ComboBoxPropertyDescriptor nodeTypeDescriptor = new ComboBoxPropertyDescriptor(
            IControlledNodeProperties.CN_NODE_TYPE_OBJECT, CN_NODE_TYPE_LABEL,
            NODE_TYPES);

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
    private static final TextPropertyDescriptor presMaxLatencyDescriptor = new TextPropertyDescriptor(
            IControlledNodeProperties.CN_POLL_RESPONSE_MAX_LATENCY_OBJECT,
            CN_POLL_RESPONSE_MAX_LATENCY_LABEL);

    private static final PropertyDescriptor presTimeoutDescriptor = new PropertyDescriptor(
            IControlledNodeProperties.CN_POLL_RESPONSE_TIMEOUT_OBJECT,
            CN_POLL_RESPONSE_TIMEOUT_LABEL);

    static {
        nodeTypeDescriptor.setCategory(IPropertySourceSupport.BASIC_CATEGORY);

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

        presMaxLatencyDescriptor
                .setCategory(IPropertySourceSupport.BASIC_CATEGORY);
        presMaxLatencyDescriptor.setDescription(
                IControlledNodeProperties.CN_POLL_RESPONSE_MAX_LATENCY_DESCRIPTION);
        presTimeoutDescriptor
                .setCategory(IPropertySourceSupport.BASIC_CATEGORY);
    }

    // Error messages.
    private static final String ERROR_PRES_MAX_LATENCY_CANNOT_BE_EMPTY = "PRes max latency value cannot be empty.";

    private Node cnNode;
    private TCN tcn;

    public ControlledNodePropertySource(Node cnNode) {
        super();
        setNodeData(cnNode);

        nameDescriptor.setCategory(IPropertySourceSupport.BASIC_CATEGORY);
        nodeIdDescriptor.setCategory(IPropertySourceSupport.BASIC_CATEGORY);
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

        presMaxLatencyDescriptor.setValidator(new ICellEditorValidator() {
            @Override
            public String isValid(Object value) {
                return handlePresMaxLatency(value);
            }
        });

        presTimeoutDescriptor.setValidator(new ICellEditorValidator() {
            @Override
            public String isValid(Object value) {
                return handlePresTimeout(value);
            }
        });

        lossSocToleranceDescriptor
                .setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);
        lossSocToleranceDescriptor
                .setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
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

        propertyList.add(nameDescriptor);
        propertyList.add(nodeIdDescriptor);

        if (tcn.getPathToXDC() != null) {

            propertyList.add(configurationDescriptor);
        }

        propertyList.add(nodeTypeDescriptor);
        propertyList.add(forcedMultiplexedCycle);
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

        propertyList.add(presMaxLatencyDescriptor);
        propertyList.add(presTimeoutDescriptor);
        // propertyList.add(lossSocToleranceDescriptor);
        if (tcn.getForcedObjects() != null) {
            propertyList.add(forcedObjects);
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
        if (id instanceof String) {
            String objectId = (String) id;
            switch (objectId) {
                case IAbstractNodeProperties.NODE_NAME_OBJECT:
                    retObj = cnNode.getName();
                    break;
                case IAbstractNodeProperties.NODE_ID_OBJECT:
                    retObj = tcn.getNodeID();
                    break;
                case IAbstractNodeProperties.NODE_CONIFG_OBJECT:
                    retObj = tcn.getPathToXDC();
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
                    retObj = String.valueOf(tcn.getForcedMultiplexedCycle());
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
                    int value = (tcn.isResetInOperational() == true) ? 0 : 1;
                    retObj = new Integer(value);
                    break;
                }
                case IControlledNodeProperties.CN_VERIFY_APP_SW_VERSION_OBJECT: {
                    int value = (tcn.isVerifyAppSwVersion() == true) ? 0 : 1;
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
                    int value = (tcn.isVerifyRevisionNumber() == true) ? 0 : 1;
                    retObj = new Integer(value);
                    break;
                }
                case IControlledNodeProperties.CN_VERIFY_PRODUCT_CODE_OBJECT: {
                    int value = (tcn.isVerifyProductCode() == true) ? 0 : 1;
                    retObj = new Integer(value);
                    break;
                }
                case IControlledNodeProperties.CN_VERIFY_SERIAL_NUMBER_OBJECT: {
                    int value = (tcn.isVerifySerialNumber() == true) ? 0 : 1;
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
                    retObj = cnNode.getForcedObjectsString();
                    break;
                case IControlledNodeProperties.CN_POLL_RESPONSE_MAX_LATENCY_OBJECT:
                    retObj = cnNode.getPresMaxLatencyValue();
                    break;
                case IControlledNodeProperties.CN_POLL_RESPONSE_TIMEOUT_OBJECT:
                    retObj = "Currently not supported. Use MN's 0x1F92/0x"
                            + Integer.toHexString(cnNode.getNodeId());
                    break;
                case IAbstractNodeProperties.NODE_LOSS_OF_SOC_TOLERANCE_OBJECT: {
                    try {
                        long val = Long.decode(cnNode.getLossOfSocTolerance());
                        long valInUs = val / 1000;
                        retObj = String.valueOf(valInUs);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                default:
                    System.err.println("Invalid object string ID:" + objectId);
                    break;
            }
        } else {
            System.err.println("Invalid object ID:" + id);
        }

        return retObj;
    }

    /**
     * Handles the loss of SoC tolerance assignment.
     *
     * @param value The new value of loss of SoC tolerance.
     *
     * @return Returns a string indicating whether the given value is valid;
     *         null means valid, and non-null means invalid, with the result
     *         being the error message to display to the end user.
     */
    @Override
    protected String handleLossOfSoCTolerance(Object value) {
        if (value instanceof String) {
            if (((String) value).isEmpty()) {
                return ERROR_LOSS_SOC_TOLERANCE_CANNOT_BE_EMPTY;
            }
            try {
                long longValue = Long.decode((String) value);

                Result res = OpenConfiguratorCore.GetInstance()
                        .SetLossOfSocTolerance(cnNode.getNetworkId(),
                                cnNode.getNodeId(), longValue);
                if (!res.IsSuccessful()) {
                    return res.GetErrorMessage();
                }
            } catch (NumberFormatException e) {
                return ERROR_INVALID_VALUE_LOSS_SOC_TOLERANCE;
            }
        } else {
            System.err.println(
                    "handleLossOfSoCTolerance: Invalid value type:" + value);
        }
        return null;
    }

    @Override
    protected String handleNodeAssignValue(NodeAssignment nodeAssign,
            Object value) {
        if (value instanceof Integer) {
            int val = ((Integer) value).intValue();
            boolean result = (val == 0) ? true : false;
            Result res = OpenConfiguratorLibraryUtils
                    .setNodeAssignment(nodeAssign, cnNode, result);
            if (!res.IsSuccessful()) {
                return res.GetErrorMessage();
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
                    return res.GetErrorMessage();
                }
            } else if (val == 1) { // Chained
                Result res = OpenConfiguratorCore.GetInstance()
                        .SetOperationModeChained(cnNode.getNetworkId(),
                                cnNode.getNodeId());
                if (!res.IsSuccessful()) {
                    return res.GetErrorMessage();
                }
            } else if (val == 2) { // Multiplexed
                Result res = OpenConfiguratorCore.GetInstance()
                        .SetOperationModeMultiplexed(cnNode.getNetworkId(),
                                cnNode.getNodeId(),
                                (short) tcn.getForcedMultiplexedCycle());
                if (!res.IsSuccessful()) {
                    return res.GetErrorMessage();
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
     * Handle PRes max latency changes in the properties.
     *
     * @param value New value for the PRes max latency.
     * @return Returns a string indicating whether the given value is valid;
     *         null means valid, and non-null means invalid, with the result
     *         being the error message to display to the end user.
     */
    protected String handlePresMaxLatency(Object value) {
        if (value instanceof String) {
            if (((String) value).isEmpty()) {
                return ERROR_PRES_MAX_LATENCY_CANNOT_BE_EMPTY;
            }
            Result res = OpenConfiguratorCore.GetInstance()
                    .SetSubObjectActualValue(cnNode.getNetworkId(),
                            cnNode.getNodeId(),
                            IControlledNodeProperties.CN_POLL_RESPONSE_MAX_LATENCY_OBJECT_ID,
                            IControlledNodeProperties.CN_POLL_RESPONSE_MAX_LATENCY_SUBOBJECT_ID,
                            (String) value);

            if (!res.IsSuccessful()) {
                return res.GetErrorMessage();
            }
        } else {
            System.err.println(
                    "handlePresMaxLatencyValue: Invalid value type:" + value);
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
        return "Currently not supported. Use MN's 0x1F92/0x"
                + Integer.toHexString(cnNode.getNodeId());
    }

    @Override
    protected String handleSetNodeName(Object name) {
        if (name instanceof String) {
            if (((String) name).isEmpty()) {
                return ERROR_NODE_NAME_CANNOT_BE_EMPTY;
            }

            Result res = OpenConfiguratorCore.GetInstance().SetNodeName(
                    cnNode.getNetworkId(), cnNode.getNodeId(), (String) name);
            if (!res.IsSuccessful()) {
                return res.GetErrorMessage();
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

    @Override
    public void setPropertyValue(Object id, Object value) {

        if (id instanceof String) {
            String objectId = (String) id;
            switch (objectId) {
                case IAbstractNodeProperties.NODE_NAME_OBJECT:
                    cnNode.setName((String) value);
                    break;
                case IAbstractNodeProperties.NODE_ID_OBJECT:
                    System.err.println(objectId + " made editable");
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
                        OpenConfiguratorProjectUtils.updateNodeAttributeValue(
                                cnNode, IControlledNodeProperties.CN_IS_CHAINED,
                                String.valueOf(tcn.isIsChained()));
                        OpenConfiguratorProjectUtils.updateNodeAttributeValue(
                                cnNode,
                                IControlledNodeProperties.CN_IS_MULTIPLEXED,
                                String.valueOf(tcn.isIsMultiplexed()));
                    } else {
                        System.err.println("Invalid value type");
                    }
                    break;
                }
                case IControlledNodeProperties.CN_FORCED_MULTIPLEXED_CYCLE_OBJECT:
                    try {
                        tcn.setForcedMultiplexedCycle(
                                Integer.decode((String) value));
                        OpenConfiguratorProjectUtils.updateNodeAttributeValue(
                                cnNode, objectId, (String) value);
                    } catch (NumberFormatException e) {
                        System.err.println("Number format exception" + e);
                    }
                    break;
                case IControlledNodeProperties.CN_IS_MANDATORY_OBJECT: {
                    if (value instanceof Integer) {
                        int val = ((Integer) value).intValue();
                        boolean result = (val == 0) ? true : false;
                        tcn.setIsMandatory(result);
                        OpenConfiguratorProjectUtils.updateNodeAttributeValue(
                                cnNode, objectId, String.valueOf(result));
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
                        OpenConfiguratorProjectUtils.updateNodeAttributeValue(
                                cnNode, objectId, String.valueOf(result));
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
                        OpenConfiguratorProjectUtils.updateNodeAttributeValue(
                                cnNode, objectId, String.valueOf(result));
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
                        OpenConfiguratorProjectUtils.updateNodeAttributeValue(
                                cnNode, objectId, String.valueOf(result));
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
                        OpenConfiguratorProjectUtils.updateNodeAttributeValue(
                                cnNode, objectId, String.valueOf(result));
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
                        OpenConfiguratorProjectUtils.updateNodeAttributeValue(
                                cnNode, objectId, String.valueOf(result));
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
                        OpenConfiguratorProjectUtils.updateNodeAttributeValue(
                                cnNode, objectId, String.valueOf(result));
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
                        OpenConfiguratorProjectUtils.updateNodeAttributeValue(
                                cnNode, objectId, String.valueOf(result));
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
                        OpenConfiguratorProjectUtils.updateNodeAttributeValue(
                                cnNode, objectId, String.valueOf(result));
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
                        OpenConfiguratorProjectUtils.updateNodeAttributeValue(
                                cnNode, objectId, String.valueOf(result));
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
                        OpenConfiguratorProjectUtils.updateNodeAttributeValue(
                                cnNode, objectId, String.valueOf(result));
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
                        OpenConfiguratorProjectUtils.updateNodeAttributeValue(
                                cnNode, objectId, String.valueOf(result));
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
                        OpenConfiguratorProjectUtils.updateNodeAttributeValue(
                                cnNode, objectId, String.valueOf(result));
                    } else {
                        System.err.println("Invalid value type");
                    }
                    break;
                }
                case IAbstractNodeProperties.NODE_FORCED_OBJECTS_OBJECT:
                    // Ignore
                    break;
                case IControlledNodeProperties.CN_POLL_RESPONSE_MAX_LATENCY_OBJECT: {
                    try {
                        cnNode.setCnPresMaxLatency(Long.decode((String) value));
                    } catch (NumberFormatException e) {
                        System.err.println(
                                objectId + " Number format exception" + e);
                    }
                    break;
                }
                case IAbstractNodeProperties.NODE_LOSS_OF_SOC_TOLERANCE_OBJECT: {
                    try {
                        cnNode.setLossOfSocTolerance(
                                Long.decode((String) value));
                    } catch (NumberFormatException e) {
                        System.err.println(
                                objectId + " Number format exception" + e);
                    }
                    break;
                }
                default:
                    System.err.println("Invalid object string ID:" + objectId);
                    break;
            }
        } else {
            System.err.println("Invalid object ID:" + id);
        }
    }
}

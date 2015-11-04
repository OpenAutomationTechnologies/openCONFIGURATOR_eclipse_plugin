/*******************************************************************************
 * @file   ManagingNodePropertySource.java
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
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.epsg.openconfigurator.lib.wrapper.NodeAssignment;
import org.epsg.openconfigurator.lib.wrapper.OpenConfiguratorCore;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.model.IAbstractNodeProperties;
import org.epsg.openconfigurator.model.IManagingNodeProperties;
import org.epsg.openconfigurator.model.INetworkProperties;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.util.OpenConfiguratorLibraryUtils;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectUtils;
import org.epsg.openconfigurator.xmlbinding.projectfile.TMN;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNetworkConfiguration;

/**
 * Describes the node properties for a Managing node.
 *
 * @see setNodeData
 * @author Ramakrishnan P
 *
 */
public class ManagingNodePropertySource extends AbstractNodePropertySource
        implements IPropertySource {

    // Labels
    private static final String[] MN_PROPERTY_LABEL_LIST = { "Transmits PRes",
            "Async Slot timeout(ns)", "ASnd Max Latency(ns)" };

    private static final String[] NETWORK_PROPERTY_LABEL_LIST = {
            "Cycle Time(" + "\u00B5" + "s)", "Async MTU size (bytes)",
            "Multiplexed Cycle Count", "Pre-Scaler" };

    private static final String MN_CATEGORY = "Managing Node";
    private static final String NETWORK_CATEGORY = "Network";

    private static final ComboBoxPropertyDescriptor transmitPres = new ComboBoxPropertyDescriptor(
            IManagingNodeProperties.MN_TRANSMIT_PRES_OBJECT,
            MN_PROPERTY_LABEL_LIST[0], IPropertySourceSupport.YES_NO);
    private static final TextPropertyDescriptor asyncSlotTimeout = new TextPropertyDescriptor(
            IManagingNodeProperties.MN_ASYNC_TIMEOUT_OBJECT,
            MN_PROPERTY_LABEL_LIST[1]);
    private static final TextPropertyDescriptor asndMaxNumber = new TextPropertyDescriptor(
            IManagingNodeProperties.MN_ASND_MAX_NR_OBJECT,
            MN_PROPERTY_LABEL_LIST[2]);

    private static final TextPropertyDescriptor cycleTime = new TextPropertyDescriptor(
            INetworkProperties.NET_CYCLE_TIME_OBJECT,
            NETWORK_PROPERTY_LABEL_LIST[0]);
    private static final TextPropertyDescriptor asyncMtu = new TextPropertyDescriptor(
            INetworkProperties.NET_ASYNC_MTU_OBJECT,
            NETWORK_PROPERTY_LABEL_LIST[1]);
    private static final TextPropertyDescriptor multiplexedCycleCnt = new TextPropertyDescriptor(
            INetworkProperties.NET_MUTLIPLEX_CYCLE_CNT_OBJECT,
            NETWORK_PROPERTY_LABEL_LIST[2]);
    private static final TextPropertyDescriptor preScaler = new TextPropertyDescriptor(
            INetworkProperties.NET_PRESCALER_OBJECT,
            NETWORK_PROPERTY_LABEL_LIST[3]);

    // Error messages
    private static final String ERROR_ASYNC_SLOT_TIMEOUT_CANNOT_BE_EMPTY = "Async. slot timeout cannot be empty.";
    private static final String INVALID_RANGE_ASYNC_SLOT_TIMEOUT = "Invalid range for Async. slot timeout.";
    private static final String ERROR_INVALID_VALUE_ASYNCT_SLOT_TIMEOUT = "Invalid value for Async. slot timeout.";

    private static final String ERROR_ASND_MAX_NR_CANNOT_BE_EMPTY = "ASnd max number cannot be empty.";
    private static final String INVALID_RANGE_ASND_MAX_NR = "Invalid range for ASnd max number.";
    private static final String ERROR_INVALID_VALUE_ASND_MAX_NR = "Invalid value for ASnd max number.";

    private static final String ERROR_CYCLE_TIME_CANNOT_BE_EMPTY = "Cycle-time cannot be empty.";
    private static final String INVALID_RANGE_CYCLE_TIME = "Invalid range for Cycle-time.";
    private static final String MINIMIUM_PLK_SUPPORTED_CYCLE_TIME = "Minumum Cycle-time should be 250("
            + "\u00B5" + "s) or above";
    private static final String ERROR_INVALID_VALUE_CYCLE_TIME = "Invalid value for Cycle-time.";

    private static final String ERROR_ASYNC_MTU_CANNOT_BE_EMPTY = "AsyncMtu cannot be empty.";
    private static final String INVALID_RANGE_ASYNC_MTU = "Invalid range for AsyncMtu.";
    private static final String ERROR_INVALID_VALUE_ASYNC_MTU = "Invalid value for AsyncMtu.";

    private static final String ERROR_MULTIPLEXED_CYCLE_CNT_CANNOT_BE_EMPTY = "Multiplexed Cycle Count cannot be empty.";
    private static final String INVALID_RANGE_MULTIPLEXED_CYCLE_CNT = "Invalid range for Multiplexed Cycle Count.";
    private static final String ERROR_INVALID_VALUE_MULTIPLEXED_CYCLE_CNT = "Invalid value for Multiplexed Cycle Count.";

    private static final String ERROR_PRE_SCALER_CANNOT_BE_EMPTY = "PreScaler cannot be empty.";
    private static final String INVALID_RANGE_PRE_SCALER = "Invalid range for PreScaler.";
    private static final String ERROR_INVALID_VALUE_PRE_SCALER = "Invalid value for PreScaler.";

    static {

        transmitPres.setCategory(MN_CATEGORY);
        transmitPres.setDescription(
                IManagingNodeProperties.MN_TRANSMIT_PRES_DESCRIPTION);

        asyncSlotTimeout.setCategory(MN_CATEGORY);
        asyncSlotTimeout
                .setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);
        asyncSlotTimeout.setDescription(
                IManagingNodeProperties.MN_ASYNC_SLOT_TIMEOUT_DESCRIPTION);

        asndMaxNumber.setCategory(MN_CATEGORY);
        asndMaxNumber.setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);
        asndMaxNumber.setDescription(
                IManagingNodeProperties.MN_ASND_MAX_NR_DESCRIPTION);

        cycleTime.setCategory(NETWORK_CATEGORY);
        cycleTime.setDescription(
                INetworkProperties.NETWORK_CYCLE_TIME_DESCRIPTION);

        asyncMtu.setCategory(NETWORK_CATEGORY);
        asyncMtu.setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);
        asyncMtu.setDescription(
                INetworkProperties.NETWORK_ASYNC_MTU_DESCRIPTION);

        multiplexedCycleCnt.setCategory(NETWORK_CATEGORY);
        multiplexedCycleCnt
                .setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);
        multiplexedCycleCnt.setDescription(
                INetworkProperties.NETWORK_MULTIPLEXED_CYCLE_CNT_DESCRIPTION);

        preScaler.setCategory(NETWORK_CATEGORY);
        preScaler.setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);
        preScaler.setDescription(
                INetworkProperties.NETWORK_PRE_SCALER_DESCRIPTION);

    }

    private Node mnNode;
    private TNetworkConfiguration networkConfiguration;
    private TMN mn;

    public ManagingNodePropertySource(Node mnNode) {
        super();
        setNodeData(mnNode);

        nameDescriptor.setCategory(MN_CATEGORY);
        nodeIdDescriptor.setCategory(MN_CATEGORY);
        configurationDescriptor.setCategory(MN_CATEGORY);

        transmitPres.setValidator(new ICellEditorValidator() {

            @Override
            public String isValid(Object value) {
                return handleNodeAssignValue(
                        NodeAssignment.NMT_NODEASSIGN_MN_PRES, value);
            }
        });

        asyncSlotTimeout.setValidator(new ICellEditorValidator() {

            @Override
            public String isValid(Object value) {
                return handleAsyncSlotTimeout(value);
            }
        });

        asndMaxNumber.setValidator(new ICellEditorValidator() {

            @Override
            public String isValid(Object value) {
                return handleASndMaxNumber(value);
            }
        });

        isAsyncOnly.setCategory(MN_CATEGORY);
        isAsyncOnly.setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);

        isType1Router.setCategory(MN_CATEGORY);
        isType1Router.setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);

        isType2Router.setCategory(MN_CATEGORY);
        isType2Router.setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);

        forcedObjects.setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);

        cycleTime.setValidator(new ICellEditorValidator() {

            @Override
            public String isValid(Object value) {
                return handleCycleTime(value);
            }
        });

        asyncMtu.setValidator(new ICellEditorValidator() {

            @Override
            public String isValid(Object value) {
                return handleAsyncMtu(value);
            }
        });

        multiplexedCycleCnt.setValidator(new ICellEditorValidator() {

            @Override
            public String isValid(Object value) {
                return handleMultiplexedCycleCnt(value);
            }
        });

        preScaler.setValidator(new ICellEditorValidator() {

            @Override
            public String isValid(Object value) {
                return handlePreScalerValue(value);
            }
        });

        lossSocToleranceDescriptor
                .setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);
        lossSocToleranceDescriptor.setCategory(MN_CATEGORY);
    }

    private void addManagingNodePropertyDescriptors(
            List<IPropertyDescriptor> propertyList) {
        if (mn == null) {
            return;
        }

        propertyList.add(nameDescriptor);
        propertyList.add(nodeIdDescriptor);

        if (mn.getPathToXDC() != null) {
            propertyList.add(configurationDescriptor);
        }

        propertyList.add(transmitPres);

        propertyList.add(asyncSlotTimeout);

        propertyList.add(asndMaxNumber);
        propertyList.add(isAsyncOnly);
        propertyList.add(isType1Router);
        propertyList.add(isType2Router);
        propertyList.add(lossSocToleranceDescriptor);
        if (mn.getForcedObjects() != null) {
            propertyList.add(forcedObjects);
        }
    }

    private void addNetworkPropertyDescriptors(
            List<IPropertyDescriptor> propertyList) {
        if (networkConfiguration == null) {
            return;
        }

        propertyList.add(cycleTime);
        propertyList.add(asyncMtu);
        // TODO check with other multiplexing parameters.
        propertyList.add(multiplexedCycleCnt);
        propertyList.add(preScaler);
    }

    @Override
    public Object getEditableValue() {
        return networkConfiguration;
    }

    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        List<IPropertyDescriptor> propertyList = new ArrayList<IPropertyDescriptor>();
        addNetworkPropertyDescriptors(propertyList);
        addManagingNodePropertyDescriptors(propertyList);

        IPropertyDescriptor[] propertyDescriptorArray = {};
        propertyDescriptorArray = propertyList.toArray(propertyDescriptorArray);
        return propertyDescriptorArray;
    }

    /**
     * Receives the properties value of Managing Node properties
     */
    @Override
    public Object getPropertyValue(Object id) {

        Object retObj = null;
        if (id instanceof String) {
            String objectId = (String) id;
            switch (objectId) {
                case IAbstractNodeProperties.NODE_NAME_OBJECT:
                    retObj = mnNode.getName();
                    break;
                case IAbstractNodeProperties.NODE_ID_READONLY_OBJECT:
                case IAbstractNodeProperties.NODE_ID_EDITABLE_OBJECT:
                    retObj = mnNode.getNodeIdString();
                    break;
                case IAbstractNodeProperties.NODE_CONIFG_OBJECT:
                    retObj = mn.getPathToXDC();
                    break;
                case IAbstractNodeProperties.NODE_LOSS_OF_SOC_TOLERANCE_OBJECT:
                    try {
                        if (!mnNode.getLossOfSocTolerance().isEmpty()) {
                            long val = Long
                                    .decode(mnNode.getLossOfSocTolerance());
                            long valInUs = val / 1000;
                            retObj = String.valueOf(valInUs);
                        } else {
                            retObj = StringUtils.EMPTY;
                        }
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    break;
                case IManagingNodeProperties.MN_TRANSMIT_PRES_OBJECT: {
                    int val = 0;
                    if (!mn.isTransmitsPRes()) {
                        val = 1;
                    }

                    retObj = new Integer(val);
                    break;
                }
                case IManagingNodeProperties.MN_ASYNC_TIMEOUT_OBJECT:
                    retObj = mnNode.getAsyncSlotTimeout();
                    break;
                case IManagingNodeProperties.MN_ASND_MAX_NR_OBJECT:
                    retObj = mnNode.getAsndMaxNumber();
                    break;
                case IAbstractNodeProperties.NODE_IS_ASYNC_ONLY_OBJECT: {
                    int val = 0;
                    if (!mn.isIsAsyncOnly()) {
                        val = 1;
                    }

                    retObj = new Integer(val);
                    break;
                }
                case IAbstractNodeProperties.NODE_IS_TYPE1_ROUTER_OBJECT: {
                    int val = 0;
                    if (!mn.isIsType1Router()) {
                        val = 1;
                    }

                    retObj = new Integer(val);
                    break;
                }
                case IAbstractNodeProperties.NODE_IS_TYPE2_ROUTER_OBJECT: {
                    int val = 0;
                    if (!mn.isIsType2Router()) {
                        val = 1;
                    }

                    retObj = new Integer(val);
                    break;
                }
                case IAbstractNodeProperties.NODE_FORCED_OBJECTS_OBJECT:
                    retObj = mnNode.getForcedObjectsString();
                    break;
                case INetworkProperties.NET_CYCLE_TIME_OBJECT:
                    retObj = mnNode.getCycleTime();
                    break;
                case INetworkProperties.NET_ASYNC_MTU_OBJECT:
                    retObj = mnNode.getAsyncMtu();
                    break;

                case INetworkProperties.NET_MUTLIPLEX_CYCLE_CNT_OBJECT:
                    retObj = mnNode.getMultiplexedCycleLength();
                    break;
                case INetworkProperties.NET_PRESCALER_OBJECT:
                    retObj = mnNode.getPrescaler();
                    break;
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
     * Handles the ASnd max number assignment.
     *
     * @param value New value for ASnd max number.
     *
     * @return Returns a string indicating whether the given value is valid;
     *         null means valid, and non-null means invalid, with the result
     *         being the error message to display to the end user.
     */
    protected String handleASndMaxNumber(Object value) {
        if (value instanceof String) {
            if (((String) value).isEmpty()) {
                return ERROR_ASND_MAX_NR_CANNOT_BE_EMPTY;
            }
            try {
                short resultVal = Short.decode((String) value);
                if ((resultVal < 1) || (resultVal > 254)) {
                    return INVALID_RANGE_ASND_MAX_NR;
                }

                Result res = OpenConfiguratorCore.GetInstance().SetAsndMaxNr(
                        mnNode.getNetworkId(), mnNode.getNodeId(), resultVal);
                if (!res.IsSuccessful()) {
                    return OpenConfiguratorLibraryUtils.getErrorMessage(res);
                }
            } catch (NumberFormatException e) {
                return ERROR_INVALID_VALUE_ASND_MAX_NR;
            }
        }

        return null;
    }

    /**
     * Handles the Async MTU assignment.
     *
     * @param value New value of AsyncMtu
     * @return Returns a string indicating whether the given value is valid;
     *         null means valid, and non-null means invalid, with the result
     *         being the error message to display to the end user.
     */
    protected String handleAsyncMtu(Object value) {
        if (value instanceof String) {
            if (((String) value).isEmpty()) {
                return ERROR_ASYNC_MTU_CANNOT_BE_EMPTY;
            }
            try {
                int asyncMtuValue = Integer.decode((String) value);
                if ((asyncMtuValue < 300) && (asyncMtuValue > 1500)) {
                    return INVALID_RANGE_ASYNC_MTU;
                }

                Result res = OpenConfiguratorCore.GetInstance()
                        .SetAsyncMtu(mnNode.getNetworkId(), asyncMtuValue);
                if (!res.IsSuccessful()) {
                    return OpenConfiguratorLibraryUtils.getErrorMessage(res);
                }

            } catch (NumberFormatException e) {
                return ERROR_INVALID_VALUE_ASYNC_MTU;
            }
        } else {
            System.err.println("ERROR invalid value type");
        }
        return null;
    }

    /**
     * Handles the AsyncSlot timeout assignment.
     *
     * @param value New value of AsyncSlot timeout.
     * @return Returns a string indicating whether the given value is valid;
     *         null means valid, and non-null means invalid, with the result
     *         being the error message to display to the end user.
     */
    protected String handleAsyncSlotTimeout(Object value) {
        if (value instanceof String) {
            if (((String) value).isEmpty()) {
                return ERROR_ASYNC_SLOT_TIMEOUT_CANNOT_BE_EMPTY;
            }
            try {
                long resultVal = Long.decode((String) value);
                if ((resultVal < 250)) {
                    return INVALID_RANGE_ASYNC_SLOT_TIMEOUT;
                }

                Result res = OpenConfiguratorCore.GetInstance()
                        .SetAsyncSlotTimeout(mnNode.getNetworkId(),
                                mnNode.getNodeId(), resultVal);
                if (!res.IsSuccessful()) {
                    return OpenConfiguratorLibraryUtils.getErrorMessage(res);
                }
            } catch (NumberFormatException e) {
                return ERROR_INVALID_VALUE_ASYNCT_SLOT_TIMEOUT;
            }
        }

        return null;
    }

    /**
     * Handles the cycle time modifications.
     *
     * @param value New value for Cycle time.
     * @return Returns a string indicating whether the given value is valid;
     *         null means valid, and non-null means invalid, with the result
     *         being the error message to display to the end user.
     */
    protected String handleCycleTime(Object value) {
        if (value instanceof String) {

            if (((String) value).isEmpty()) {
                return ERROR_CYCLE_TIME_CANNOT_BE_EMPTY;
            }
            try {
                long cycleTime = Long.decode((String) value);
                if ((cycleTime < 1)) {
                    return INVALID_RANGE_CYCLE_TIME;
                }

                if ((cycleTime < 250)) {
                    return MINIMIUM_PLK_SUPPORTED_CYCLE_TIME;
                }

                Result res = OpenConfiguratorCore.GetInstance()
                        .SetCycleTime(mnNode.getNetworkId(), cycleTime);
                if (!res.IsSuccessful()) {
                    return OpenConfiguratorLibraryUtils.getErrorMessage(res);
                }

            } catch (NumberFormatException e) {
                return ERROR_INVALID_VALUE_CYCLE_TIME;
            }
        } else {
            System.err.println("ERROR invalid value type");
        }
        return null;
    }

    /**
     * Handles LossofSOCTolerance modifications
     *
     * @param value New value for LossOfSoCTolerance
     */
    @Override
    protected String handleLossOfSoCTolerance(Object value) {

        if (value instanceof String) {
            if (((String) value).isEmpty()) {
                return ERROR_LOSS_SOC_TOLERANCE_CANNOT_BE_EMPTY;
            }
            try {
                long longValue = Long.decode((String) value);

                // Converted us to ns
                Result res = OpenConfiguratorCore.GetInstance()
                        .SetLossOfSocTolerance(mnNode.getNetworkId(),
                                mnNode.getNodeId(), longValue * 1000);
                if (!res.IsSuccessful()) {
                    return OpenConfiguratorLibraryUtils.getErrorMessage(res);
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

    /**
     * Handles the multiplexed cycle count modifications.
     *
     * @param value New value for multiplexed cycle count.
     * @return Returns a string indicating whether the given value is valid;
     *         null means valid, and non-null means invalid, with the result
     *         being the error message to display to the end user.
     */
    protected String handleMultiplexedCycleCnt(Object value) {
        if (value instanceof String) {
            if (((String) value).isEmpty()) {
                return ERROR_MULTIPLEXED_CYCLE_CNT_CANNOT_BE_EMPTY;
            }
            try {
                int multiplxCyclVal = Integer.decode((String) value);
                if ((multiplxCyclVal < 0)) {
                    return INVALID_RANGE_MULTIPLEXED_CYCLE_CNT;
                }

                Result res = OpenConfiguratorCore.GetInstance()
                        .SetMultiplexedCycleCount(mnNode.getNetworkId(),
                                multiplxCyclVal);
                if (!res.IsSuccessful()) {
                    return OpenConfiguratorLibraryUtils.getErrorMessage(res);
                }

            } catch (NumberFormatException e) {
                return ERROR_INVALID_VALUE_MULTIPLEXED_CYCLE_CNT;
            }
        } else {
            System.err.println("ERROR invalid value type");
        }
        return null;
    }

    /**
     * Handles the Node Assign Value modifications.
     *
     * @param nodeAssign
     * @param value New value for Node Assign.
     * @return Returns a string indicating whether the given value is valid;
     *         null means valid, and non-null means invalid, with the result
     *         being the error message to display to the end user.
     */
    @Override
    protected String handleNodeAssignValue(NodeAssignment nodeAssign,
            Object value) {
        if (value instanceof Integer) {
            int val = ((Integer) value).intValue();
            boolean result = (val == 0) ? true : false;
            Result res = OpenConfiguratorLibraryUtils
                    .setNodeAssignment(nodeAssign, mnNode, result);
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
     * Handles the Pre-Scaler value changes.
     *
     * @param value New value for Pre-Scaler.
     * @return Returns a string indicating whether the given value is valid;
     *         null means valid, and non-null means invalid, with the result
     *         being the error message to display to the end user.
     */
    protected String handlePreScalerValue(Object value) {
        if (value instanceof String) {
            if (((String) value).isEmpty()) {
                return ERROR_PRE_SCALER_CANNOT_BE_EMPTY;
            }
            try {
                int preScalarVal = Integer.decode((String) value);

                if ((preScalarVal < 0) && (preScalarVal > 1000)) {
                    return INVALID_RANGE_PRE_SCALER;
                }

                Result res = OpenConfiguratorCore.GetInstance()
                        .SetPrescaler(mnNode.getNetworkId(), preScalarVal);
                if (!res.IsSuccessful()) {
                    return OpenConfiguratorLibraryUtils.getErrorMessage(res);
                }

            } catch (NumberFormatException e) {
                return ERROR_INVALID_VALUE_PRE_SCALER;
            }
        }
        return null;
    }

    /**
     * Handles the Node Name changes.
     *
     * @param name New name for Node.
     * @return Returns a string indicating whether the given value is valid;
     *         null means valid, and non-null means invalid, with the result
     *         being the error message to display to the end user.
     */
    @Override
    protected String handleSetNodeName(Object name) {
        if (name instanceof String) {
            String nodeName = ((String) name);
            if (nodeName.isEmpty()) {
                return ERROR_NODE_NAME_CANNOT_BE_EMPTY;
            }

            // Space as first character is not allowed. ppc:tNonEmptyString
            if (nodeName.charAt(0) == ' ') {
                return "Invalid name";
            }

            Result res = OpenConfiguratorCore.GetInstance().SetNodeName(
                    mnNode.getNetworkId(), mnNode.getNodeId(), nodeName);
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
        // Ignore
    }

    /**
     * Set the node details if the property source instance to be re-used for a
     * new node.
     *
     * @param mnNode The node instance.
     */
    public void setNodeData(final Node mnNode) {
        this.mnNode = mnNode;
        if (mnNode.getNodeModel() instanceof TNetworkConfiguration) {
            networkConfiguration = (TNetworkConfiguration) mnNode
                    .getNodeModel();
            mn = networkConfiguration.getNodeCollection().getMN();
        } else {
            networkConfiguration = null;
            mn = null;
        }
    }

    /**
     * sets the Property value to the Managing Node Properties
     */
    @Override
    public void setPropertyValue(Object id, Object value) {

        if (id instanceof String) {
            String objectId = (String) id;
            switch (objectId) {
                case IAbstractNodeProperties.NODE_NAME_OBJECT:
                    mnNode.setName((String) value);
                    break;
                case IAbstractNodeProperties.NODE_ID_READONLY_OBJECT:
                case IAbstractNodeProperties.NODE_ID_EDITABLE_OBJECT:
                    System.err.println(objectId + " made editable");
                    break;
                case IAbstractNodeProperties.NODE_CONIFG_OBJECT:
                    System.err.println(objectId + " made editable");
                    break;
                case IAbstractNodeProperties.NODE_LOSS_OF_SOC_TOLERANCE_OBJECT:
                    try {
                        mnNode.setLossOfSocTolerance(
                                Long.decode((String) value) * 1000);
                    } catch (NumberFormatException e) {
                        System.err.println(
                                objectId + " Number format exception" + e);
                    }
                    break;
                case IManagingNodeProperties.MN_TRANSMIT_PRES_OBJECT:
                    if (value instanceof Integer) {
                        int val = ((Integer) value).intValue();
                        boolean result = (val == 0) ? true : false;
                        mn.setTransmitsPRes(result);
                        OpenConfiguratorProjectUtils.updateNodeAttributeValue(
                                mnNode, objectId, String.valueOf(result));
                    } else {
                        System.err.println("Invalid value type");
                    }

                    break;
                case IManagingNodeProperties.MN_ASYNC_TIMEOUT_OBJECT:
                    try {
                        mnNode.setAsyncSlotTimeout(Long.decode((String) value));
                    } catch (NumberFormatException e) {
                        System.err.println("Number format exception:" + e);
                    }
                    break;
                case IManagingNodeProperties.MN_ASND_MAX_NR_OBJECT:
                    try {
                        mnNode.setAsndMaxNumber(Short.decode((String) value));
                    } catch (NumberFormatException e) {
                        System.err.println("Number format exception" + e);
                    }
                    break;
                case IAbstractNodeProperties.NODE_IS_ASYNC_ONLY_OBJECT:
                    if (value instanceof Integer) {
                        int val = ((Integer) value).intValue();
                        boolean result = (val == 0) ? true : false;

                        mn.setIsAsyncOnly(result);
                        OpenConfiguratorProjectUtils.updateNodeAttributeValue(
                                mnNode, objectId, String.valueOf(result));
                    } else {
                        System.err.println("Invalid value type");
                    }
                    break;
                case IAbstractNodeProperties.NODE_IS_TYPE1_ROUTER_OBJECT:
                    if (value instanceof Integer) {
                        int val = ((Integer) value).intValue();
                        boolean result = (val == 0) ? true : false;

                        mn.setIsType1Router(result);
                        OpenConfiguratorProjectUtils.updateNodeAttributeValue(
                                mnNode, objectId, String.valueOf(result));
                    } else {
                        System.err.println("Invalid value type");
                    }
                    break;
                case IAbstractNodeProperties.NODE_IS_TYPE2_ROUTER_OBJECT:
                    if (value instanceof Integer) {
                        int val = ((Integer) value).intValue();
                        boolean result = (val == 0) ? true : false;

                        mn.setIsType2Router(result);
                        OpenConfiguratorProjectUtils.updateNodeAttributeValue(
                                mnNode, objectId, String.valueOf(result));
                    } else {
                        System.err.println("Invalid value type");
                    }
                    break;
                case IAbstractNodeProperties.NODE_FORCED_OBJECTS_OBJECT:
                    // ignore. Not editable.
                    break;
                case INetworkProperties.NET_CYCLE_TIME_OBJECT:

                    try {
                        Long cycleTimeValue = Long.decode((String) value);
                        mnNode.setCycleTime(cycleTimeValue);
                    } catch (NumberFormatException e) {
                        System.err.println("Number format exception" + e);
                    }

                    break;
                case INetworkProperties.NET_ASYNC_MTU_OBJECT:

                    try {
                        Integer asyncMtuValue = Integer.decode((String) value);
                        mnNode.setAsyncMtu(asyncMtuValue);
                    } catch (NumberFormatException e) {
                        System.err.println("Number format exception" + e);
                    }
                    break;

                case INetworkProperties.NET_MUTLIPLEX_CYCLE_CNT_OBJECT:
                    try {
                        Integer multiplxCyclCntVal = Integer
                                .decode((String) value);
                        mnNode.setMultiplexedCycleLength(multiplxCyclCntVal);
                    } catch (NumberFormatException e) {
                        System.err.println("Number format exception" + e);
                    }
                    break;
                case INetworkProperties.NET_PRESCALER_OBJECT:

                    try {
                        Integer preScalarVal = Integer.decode((String) value);
                        mnNode.setPrescaler(preScalarVal);

                    } catch (NumberFormatException e) {
                        System.err.println("Number format exception" + e);
                    }

                    break;
                default:
                    System.err.println("Invalid object string ID:" + objectId);
                    break;
            }
        } else {
            System.err.println("Invalid object ID:" + id);
        }
    }
}

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

import java.text.MessageFormat;
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
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.epsg.openconfigurator.console.OpenConfiguratorMessageConsole;
import org.epsg.openconfigurator.lib.wrapper.NodeAssignment;
import org.epsg.openconfigurator.lib.wrapper.OpenConfiguratorCore;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.model.IAbstractNodeProperties;
import org.epsg.openconfigurator.model.IManagingNodeProperties;
import org.epsg.openconfigurator.model.INetworkProperties;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.model.PowerlinkObject;
import org.epsg.openconfigurator.model.PowerlinkSubobject;
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

    private static final String CN_LOSS_OF_SOC_TOLERANCE_LABEL = "Loss of SoC Tolerance ("
            + "\u00B5" + "s)";

    // Labels
    private static final String[] MN_PROPERTY_LABEL_LIST = { "Transmits PRes",
            "Async Slot Timeout (ns)", "ASnd Max Latency (ns)" };

    private static final String[] NETWORK_PROPERTY_LABEL_LIST = {
            "Cycle Time (" + "\u00B5" + "s)", "Async MTU size (bytes)",
            "Multiplexed Cycle Count", "Pre-Scaler",
            CN_LOSS_OF_SOC_TOLERANCE_LABEL };

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
    private static final TextPropertyDescriptor lossSocToleranceDescriptor = new TextPropertyDescriptor(
            INetworkProperties.NET_LOSS_OF_SOC_TOLERANCE_OBJECT,
            CN_LOSS_OF_SOC_TOLERANCE_LABEL);

    // Error messages
    private static final String ERROR_ASYNC_SLOT_TIMEOUT_CANNOT_BE_EMPTY = "Async. slot timeout cannot be empty.";
    private static final String INVALID_RANGE_ASYNC_SLOT_TIMEOUT = "Invalid range for Async. slot timeout.";
    private static final String ERROR_INVALID_VALUE_ASYNCT_SLOT_TIMEOUT = "Invalid value for Async. slot timeout.";

    private static final String ERROR_ASND_MAX_NR_CANNOT_BE_EMPTY = "ASnd max number cannot be empty.";
    private static final String INVALID_RANGE_ASND_MAX_NR = "Invalid range for ASnd max number.";
    private static final String ERROR_INVALID_VALUE_ASND_MAX_NR = "Invalid value for ASnd max number.";

    private static final String ERROR_CYCLE_TIME_CANNOT_BE_EMPTY = "Cycle Time cannot be empty.";
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
    public static final String ERROR_LOSS_SOC_TOLERANCE_CANNOT_BE_EMPTY = "Loss of SoC tolerance value cannot be empty.";
    public static final String ERROR_INVALID_VALUE_LOSS_SOC_TOLERANCE = "Invalid Loss of SoC tolerance value.";
    public static final String INVALID_RANGE_LOSS_SOC_TOLERANCE = "[{0}] Actual value {1} with datatype UNSIGNED32 does not fit the datatype limits or format.";

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

        lossSocToleranceDescriptor.setDescription(
                INetworkProperties.LOSS_SOC_TOLERANCE_DESCRIPTION);
        lossSocToleranceDescriptor
                .setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);
        lossSocToleranceDescriptor.setCategory(NETWORK_CATEGORY);
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

        lossSocToleranceDescriptor.setValidator(new ICellEditorValidator() {
            @Override
            public String isValid(Object value) {
                return handleLossOfSoCTolerance(value);
            }
        });
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
        // AsndMaxnumber is not supported by POWERLINK stack
        // propertyList.add(asndMaxNumber);
        propertyList.add(isAsyncOnly);
        propertyList.add(isType1Router);
        propertyList.add(isType2Router);
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
        propertyList.add(lossSocToleranceDescriptor);
    }

    @Override
    public Object getEditableValue() {
        return networkConfiguration;
    }

    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        List<IPropertyDescriptor> propertyList = new ArrayList<>();
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
        try {
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
                    case INetworkProperties.NET_LOSS_OF_SOC_TOLERANCE_OBJECT: {
                        long val = networkConfiguration.getLossOfSocTolerance()
                                .longValue();
                        long valInUs = val / 1000;
                        retObj = String.valueOf(valInUs);
                        break;
                    }
                    case IManagingNodeProperties.MN_TRANSMIT_PRES_OBJECT: {
                        int val = 0;
                        if (!mn.isTransmitsPRes()) {
                            val = 1;
                        }

                        retObj = new Integer(val);
                        break;
                    }
                    case IManagingNodeProperties.MN_ASYNC_TIMEOUT_OBJECT:
                        String asyncSlotTimeOut = mnNode.getAsyncSlotTimeout();
                        if (asyncSlotTimeOut.isEmpty()) {
                            return "";
                        }
                        retObj = String
                                .valueOf(Integer.decode(asyncSlotTimeOut));
                        break;
                    case IManagingNodeProperties.MN_ASND_MAX_NR_OBJECT:
                        String asndMaxNumber = mnNode.getAsndMaxNumber();
                        if (asndMaxNumber.isEmpty()) {
                            return "";
                        }
                        retObj = asndMaxNumber;
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
                        String forcedObjectString = mnNode
                                .getForcedObjectsString();
                        if (forcedObjectString.isEmpty()) {
                            return "";
                        }
                        retObj = mnNode.getForcedObjectsString();
                        break;
                    case INetworkProperties.NET_CYCLE_TIME_OBJECT:
                        String cycleTime = mnNode.getCycleTime();
                        if (cycleTime.isEmpty()) {
                            return "";
                        }
                        retObj = String.valueOf(cycleTime);
                        break;
                    case INetworkProperties.NET_ASYNC_MTU_OBJECT:
                        String asyncMtu = mnNode.getAsyncMtu();
                        if (asyncMtu.isEmpty()) {
                            return "";
                        }
                        retObj = String.valueOf(Integer.decode(asyncMtu));
                        break;

                    case INetworkProperties.NET_MUTLIPLEX_CYCLE_CNT_OBJECT:
                        String multiplexCycleCount = mnNode
                                .getMultiplexedCycleCnt();
                        if (multiplexCycleCount.isEmpty()) {
                            return "";
                        }
                        retObj = String
                                .valueOf(Integer.decode(multiplexCycleCount));
                        break;
                    case INetworkProperties.NET_PRESCALER_OBJECT:
                        String prescaler = mnNode.getPrescaler();
                        if (prescaler.isEmpty()) {
                            return "";
                        }
                        retObj = String.valueOf(Integer.decode(prescaler));
                        break;
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
                    mnNode.getNetworkId());
            retObj = StringUtils.EMPTY;
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
                // validate the value with openCONFIGURATOR library.
                PowerlinkSubobject asndMaxNumberSubobj = mnNode
                        .getObjectDictionary().getSubObject(
                                IManagingNodeProperties.ASND_MAX_NR_OBJECT_ID,
                                IManagingNodeProperties.ASND_MAX_NR_SUBOBJECT_ID);
                if (asndMaxNumberSubobj == null) {
                    return AbstractNodePropertySource.ERROR_OBJECT_NOT_FOUND;
                }
                Result validateResult = OpenConfiguratorLibraryUtils
                        .validateSubobjectActualValue(mnNode.getNetworkId(),
                                mnNode.getNodeId(),
                                IManagingNodeProperties.ASND_MAX_NR_OBJECT_ID,
                                IManagingNodeProperties.ASND_MAX_NR_SUBOBJECT_ID,
                                String.valueOf(resultVal), false);

                if (!validateResult.IsSuccessful()) {
                    return OpenConfiguratorLibraryUtils
                            .getErrorMessage(validateResult);
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
                if ((asyncMtuValue < 300)) {
                    return INVALID_RANGE_ASYNC_MTU;
                }
                if ((asyncMtuValue > 1500)) {
                    return INVALID_RANGE_ASYNC_MTU;
                }
                // validate the value with openCONFIGURATOR library.
                PowerlinkSubobject asyncMtuSubobj = mnNode.getObjectDictionary()
                        .getSubObject(INetworkProperties.ASYNC_MTU_OBJECT_ID,
                                INetworkProperties.ASYNC_MTU_SUBOBJECT_ID);
                if (asyncMtuSubobj == null) {
                    return AbstractNodePropertySource.ERROR_OBJECT_NOT_FOUND;
                }
                Result validateResult = OpenConfiguratorLibraryUtils
                        .validateSubobjectActualValue(mnNode.getNetworkId(),
                                mnNode.getNodeId(),
                                INetworkProperties.ASYNC_MTU_OBJECT_ID,
                                INetworkProperties.ASYNC_MTU_SUBOBJECT_ID,
                                String.valueOf(asyncMtuValue), false);
                if (!validateResult.IsSuccessful()) {
                    return OpenConfiguratorLibraryUtils
                            .getErrorMessage(validateResult);
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
                // validate the value with openCONFIGURATOR library.
                PowerlinkSubobject asyncslotTimeoutSubobj = mnNode
                        .getObjectDictionary().getSubObject(
                                IManagingNodeProperties.ASYNC_SLOT_TIMEOUT_OBJECT_ID,
                                IManagingNodeProperties.ASYNC_SLOT_TIMEOUT_SUBOBJECT_ID);
                if (asyncslotTimeoutSubobj == null) {
                    return AbstractNodePropertySource.ERROR_OBJECT_NOT_FOUND;
                }
                Result validateResult = OpenConfiguratorLibraryUtils
                        .validateSubobjectActualValue(mnNode.getNetworkId(),
                                mnNode.getNodeId(),
                                IManagingNodeProperties.ASYNC_SLOT_TIMEOUT_OBJECT_ID,
                                IManagingNodeProperties.ASYNC_SLOT_TIMEOUT_SUBOBJECT_ID,
                                String.valueOf(resultVal), false);

                if (!validateResult.IsSuccessful()) {
                    return OpenConfiguratorLibraryUtils
                            .getErrorMessage(validateResult);
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
                // validate the value with openCONFIGURATOR library.
                PowerlinkObject cycleTimeObj = mnNode.getObjectDictionary()
                        .getObject(INetworkProperties.CYCLE_TIME_OBJECT_ID);
                if (cycleTimeObj == null) {
                    return AbstractNodePropertySource.ERROR_OBJECT_NOT_FOUND;
                }
                Result validateResult = OpenConfiguratorLibraryUtils
                        .validateObjectActualValue(mnNode.getNetworkId(),
                                mnNode.getNodeId(),
                                INetworkProperties.CYCLE_TIME_OBJECT_ID,
                                (String) value, false);
                if (!validateResult.IsSuccessful()) {
                    return OpenConfiguratorLibraryUtils
                            .getErrorMessage(validateResult);
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
    private String handleLossOfSoCTolerance(Object value) {

        if (value instanceof String) {
            if (((String) value).isEmpty()) {
                return ERROR_LOSS_SOC_TOLERANCE_CANNOT_BE_EMPTY;
            }
            try {
                long longValue = Long.decode((String) value);
                // validate the value
                Long maxValue = 4294967295L;
                if ((longValue * 1000) > maxValue) {
                    return MessageFormat.format(
                            INVALID_RANGE_LOSS_SOC_TOLERANCE,
                            mnNode.getNetworkId(), longValue);
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
                // validate the value with openCONFIGURATOR library.
                PowerlinkSubobject multiplxdCycleLngthSubObj = mnNode
                        .getObjectDictionary().getSubObject(
                                INetworkProperties.MUTLIPLEX_CYCLE_CNT_OBJECT_ID,
                                INetworkProperties.MUTLIPLEX_CYCLE_CNT_SUBOBJECT_ID);
                if (multiplxdCycleLngthSubObj == null) {
                    return AbstractNodePropertySource.ERROR_OBJECT_NOT_FOUND;
                }
                Result validateResult = OpenConfiguratorLibraryUtils
                        .validateSubobjectActualValue(mnNode.getNetworkId(),
                                mnNode.getNodeId(),
                                INetworkProperties.MUTLIPLEX_CYCLE_CNT_OBJECT_ID,
                                INetworkProperties.MUTLIPLEX_CYCLE_CNT_SUBOBJECT_ID,
                                String.valueOf(multiplxCyclVal), false);
                if (!validateResult.IsSuccessful()) {
                    return OpenConfiguratorLibraryUtils
                            .getErrorMessage(validateResult);
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
            // TODO: validate the value with openCONFIGURATOR library.

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

                if ((preScalarVal < 0)) {
                    return INVALID_RANGE_PRE_SCALER;
                }
                if ((preScalarVal > 1000)) {
                    return INVALID_RANGE_PRE_SCALER;
                }
                // validate the value with openCONFIGURATOR library.
                PowerlinkSubobject preScalerSubObj = mnNode
                        .getObjectDictionary()
                        .getSubObject(INetworkProperties.PRESCALER_OBJECT_ID,
                                INetworkProperties.PRESCALER_SUBOBJECT_ID);
                if (preScalerSubObj == null) {
                    return AbstractNodePropertySource.ERROR_OBJECT_NOT_FOUND;
                }
                Result validateResult = OpenConfiguratorLibraryUtils
                        .validateSubobjectActualValue(mnNode.getNetworkId(),
                                mnNode.getNodeId(),
                                INetworkProperties.PRESCALER_OBJECT_ID,
                                INetworkProperties.PRESCALER_SUBOBJECT_ID,
                                String.valueOf(preScalarVal), false);
                if (!validateResult.IsSuccessful()) {
                    return OpenConfiguratorLibraryUtils
                            .getErrorMessage(validateResult);
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
        Result res = new Result();
        try {
            if (id instanceof String) {
                String objectId = (String) id;
                switch (objectId) {
                    case IAbstractNodeProperties.NODE_NAME_OBJECT:
                        res = OpenConfiguratorCore.GetInstance().SetNodeName(
                                mnNode.getNetworkId(), mnNode.getNodeId(),
                                (String) value);
                        if (!res.IsSuccessful()) {
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printLibraryErrorMessage(res);
                        } else {
                            mnNode.setName((String) value);
                        }
                        break;
                    case IAbstractNodeProperties.NODE_ID_READONLY_OBJECT:
                    case IAbstractNodeProperties.NODE_ID_EDITABLE_OBJECT:
                        System.err.println(objectId + " made editable");
                        break;
                    case IAbstractNodeProperties.NODE_CONIFG_OBJECT:
                        System.err.println(objectId + " made editable");
                        break;
                    case INetworkProperties.NET_LOSS_OF_SOC_TOLERANCE_OBJECT:
                        // Converted us to ns
                        Long lossOfSocTolerance = Long.decode((String) value)
                                * 1000;
                        res = OpenConfiguratorCore.GetInstance()
                                .SetLossOfSocTolerance(mnNode.getNetworkId(),
                                        mnNode.getNodeId(), lossOfSocTolerance);
                        if (res.IsSuccessful()) {
                            mnNode.setLossOfSocTolerance(lossOfSocTolerance);
                        } else {
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printLibraryErrorMessage(res);
                        }
                        break;
                    case IManagingNodeProperties.MN_TRANSMIT_PRES_OBJECT:
                        if (value instanceof Integer) {
                            int val = ((Integer) value).intValue();
                            boolean result = (val == 0) ? true : false;
                            mn.setTransmitsPRes(result);
                            OpenConfiguratorProjectUtils
                                    .updateNodeAttributeValue(mnNode, objectId,
                                            String.valueOf(result));
                        } else {
                            System.err.println("Invalid value type");
                        }

                        break;
                    case IManagingNodeProperties.MN_ASYNC_TIMEOUT_OBJECT:
                        res = OpenConfiguratorCore.GetInstance()
                                .SetAsyncSlotTimeout(mnNode.getNetworkId(),
                                        mnNode.getNodeId(),
                                        Long.decode((String) value));
                        if (res.IsSuccessful()) {
                            mnNode.setAsyncSlotTimeout(
                                    Long.decode((String) value));
                        } else {
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printLibraryErrorMessage(res);
                        }

                        break;
                    case IManagingNodeProperties.MN_ASND_MAX_NR_OBJECT:
                        res = OpenConfiguratorCore.GetInstance().SetAsndMaxNr(
                                mnNode.getNetworkId(), mnNode.getNodeId(),
                                Short.decode((String) value));
                        if (res.IsSuccessful()) {
                            mnNode.setAsndMaxNumber(
                                    Short.decode((String) value));
                        } else {
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printLibraryErrorMessage(res);
                        }

                        break;
                    case IAbstractNodeProperties.NODE_IS_ASYNC_ONLY_OBJECT:
                        if (value instanceof Integer) {
                            int val = ((Integer) value).intValue();
                            boolean result = (val == 0) ? true : false;

                            mn.setIsAsyncOnly(result);
                            OpenConfiguratorProjectUtils
                                    .updateNodeAttributeValue(mnNode, objectId,
                                            String.valueOf(result));
                        } else {
                            System.err.println("Invalid value type");
                        }
                        break;
                    case IAbstractNodeProperties.NODE_IS_TYPE1_ROUTER_OBJECT:
                        if (value instanceof Integer) {
                            int val = ((Integer) value).intValue();
                            boolean result = (val == 0) ? true : false;

                            mn.setIsType1Router(result);
                            OpenConfiguratorProjectUtils
                                    .updateNodeAttributeValue(mnNode, objectId,
                                            String.valueOf(result));
                        } else {
                            System.err.println("Invalid value type");
                        }
                        break;
                    case IAbstractNodeProperties.NODE_IS_TYPE2_ROUTER_OBJECT:
                        if (value instanceof Integer) {
                            int val = ((Integer) value).intValue();
                            boolean result = (val == 0) ? true : false;

                            mn.setIsType2Router(result);
                            OpenConfiguratorProjectUtils
                                    .updateNodeAttributeValue(mnNode, objectId,
                                            String.valueOf(result));
                        } else {
                            System.err.println("Invalid value type");
                        }
                        break;
                    case IAbstractNodeProperties.NODE_FORCED_OBJECTS_OBJECT:
                        // ignore. Not editable.
                        break;
                    case INetworkProperties.NET_CYCLE_TIME_OBJECT:
                        Long cycleTimeValue = Long.decode((String) value);
                        res = OpenConfiguratorCore.GetInstance().SetCycleTime(
                                mnNode.getNetworkId(), cycleTimeValue);
                        if (res.IsSuccessful()) {
                            mnNode.setCycleTime(cycleTimeValue);
                        } else {
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printLibraryErrorMessage(res);
                        }

                        break;
                    case INetworkProperties.NET_ASYNC_MTU_OBJECT:
                        Integer asyncMtuValue = Integer.decode((String) value);
                        res = OpenConfiguratorCore.GetInstance().SetAsyncMtu(
                                mnNode.getNetworkId(), asyncMtuValue);
                        if (res.IsSuccessful()) {
                            mnNode.setAsyncMtu(asyncMtuValue);
                        } else {
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printLibraryErrorMessage(res);
                        }

                        break;

                    case INetworkProperties.NET_MUTLIPLEX_CYCLE_CNT_OBJECT:
                        Integer multiplxCyclCntVal = Integer
                                .decode((String) value);
                        res = OpenConfiguratorCore.GetInstance()
                                .SetMultiplexedCycleCount(mnNode.getNetworkId(),
                                        multiplxCyclCntVal);
                        if (res.IsSuccessful()) {
                            mnNode.setMultiplexedCycleLength(
                                    multiplxCyclCntVal);
                        } else {
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printLibraryErrorMessage(res);
                        }

                        break;
                    case INetworkProperties.NET_PRESCALER_OBJECT:

                        Integer preScalarVal = Integer.decode((String) value);
                        res = OpenConfiguratorCore.GetInstance().SetPrescaler(
                                mnNode.getNetworkId(), preScalarVal);
                        if (res.IsSuccessful()) {
                            mnNode.setPrescaler(preScalarVal);
                        } else {
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printLibraryErrorMessage(res);
                        }

                        break;
                    default:
                        System.err.println(
                                "Invalid object string ID:" + objectId);
                        break;
                }
            } else {
                System.err.println("Invalid object ID:" + id);
            }
        } catch (Exception e) {
            OpenConfiguratorMessageConsole.getInstance()
                    .printErrorMessage(e.getMessage(), mnNode.getNetworkId());
        }

        try {
            mnNode.getProject().refreshLocal(IResource.DEPTH_INFINITE,
                    new NullProgressMonitor());
        } catch (CoreException e) {
            System.err.println("unable to refresh the resource due to "
                    + e.getCause().getMessage());
        }
    }
}

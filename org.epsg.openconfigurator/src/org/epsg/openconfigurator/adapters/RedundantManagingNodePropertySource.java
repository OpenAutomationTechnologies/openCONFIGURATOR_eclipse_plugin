/*******************************************************************************
 * @file   RedundantManagingNodePropertySource.java
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
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySheetEntry;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.epsg.openconfigurator.console.OpenConfiguratorMessageConsole;
import org.epsg.openconfigurator.lib.wrapper.NodeAssignment;
import org.epsg.openconfigurator.lib.wrapper.OpenConfiguratorCore;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.model.IAbstractNodeProperties;
import org.epsg.openconfigurator.model.IRedundantManagingNodeProperties;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.model.PowerlinkSubobject;
import org.epsg.openconfigurator.util.IPowerlinkConstants;
import org.epsg.openconfigurator.util.OpenConfiguratorLibraryUtils;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectUtils;
import org.epsg.openconfigurator.xmlbinding.projectfile.TRMN;

/**
 * Describes the node properties for a Redundant Managing node.
 *
 * @see setNodeData
 * @author Ramakrishnan P
 *
 */
public class RedundantManagingNodePropertySource
        extends AbstractNodePropertySource implements IPropertySource {

    private static final String RMN_WAIT_NOT_ACTIVE_LABEL = "Wait Not Active ("
            + "\u00B5" + "s)";
    private static final String RMN_PRIORITY_LABEL = "Priority";

    private static final TextPropertyDescriptor waitNotActiveDescriptor = new TextPropertyDescriptor(
            IRedundantManagingNodeProperties.RMN_WAIT_NOT_ACTIVE_OBJECT,
            RMN_WAIT_NOT_ACTIVE_LABEL);
    private static final TextPropertyDescriptor priorityDescriptor = new TextPropertyDescriptor(
            IRedundantManagingNodeProperties.RMN_PRIORITY_OBJECT,
            RMN_PRIORITY_LABEL);
    private static final PropertyDescriptor nodeIDDescriptor = new PropertyDescriptor(
            IAbstractNodeProperties.NODE_ID_EDITABLE_OBJECT, NODE_ID_LABEL);
    private static final TextPropertyDescriptor nodeIdEditableDescriptor = new TextPropertyDescriptor(
            IAbstractNodeProperties.NODE_ID_EDITABLE_OBJECT, NODE_ID_LABEL);
    private static final String[] EXPERT_FILTER_FLAG = {
            IPropertySheetEntry.FILTER_ID_EXPERT };

    private static final String ERROR_WAIT_NOT_ACTIVE_CANNOT_BE_EMPTY = "Wait Not Active value cannot be empty.";
    private static final String INVALID_RANGE_WAIT_NOT_ACTIVE = "Invalid range for Wait Not Active.";
    private static final String ERROR_INVALID_VALUE_WAIT_NOT_ACTIVE = "Invalid value for Wait Not Active.";

    private static final String ERROR_RMN_PRIORITY_CANNOT_BE_EMPTY = "Priority value cannot be empty.";
    private static final String ERROR_INVALID_VALUE_RMN_PRIORITY = "Invalid value for Priority.";
    private static final String ERROR_INVALID_RMN_NODE_ID = "Invalid node ID for a Redundant Managing node.";
    private static final String ERROR_RMN_ID_RANGE = "Redundant Managing node with id 0-240 is not supported.";
    private static final String ERROR_RMN_ID_EXISTS = "Node with ID {0} already exists.";

    static {
        waitNotActiveDescriptor
                .setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        waitNotActiveDescriptor.setFilterFlags(EXPERT_FILTER_FLAG);
        waitNotActiveDescriptor.setDescription(
                IRedundantManagingNodeProperties.RMN_WAIT_NOT_ACTIVE_DESCRIPTION);

        priorityDescriptor
                .setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        priorityDescriptor.setFilterFlags(EXPERT_FILTER_FLAG);
        priorityDescriptor.setDescription(
                IRedundantManagingNodeProperties.RMN_PRIORITY_DESCRIPTION);
    }

    private Node redundantManagingNode;
    private TRMN rmn;

    public RedundantManagingNodePropertySource(Node redundantManagingNode) {
        super();
        setNodeData(redundantManagingNode);

        waitNotActiveDescriptor.setValidator(new ICellEditorValidator() {

            @Override
            public String isValid(Object value) {
                return handleWaitNotActive(value);
            }
        });

        priorityDescriptor.setValidator(new ICellEditorValidator() {

            @Override
            public String isValid(Object value) {
                return handleRmnPriority(value);
            }
        });

        nameDescriptor.setCategory(IPropertySourceSupport.BASIC_CATEGORY);
        nodeErrorDescriptor.setCategory(IPropertySourceSupport.BASIC_CATEGORY);
        nodeIDDescriptor.setCategory(IPropertySourceSupport.BASIC_CATEGORY);
        nodeIdEditableDescriptor
                .setCategory(IPropertySourceSupport.BASIC_CATEGORY);
        nodeIdEditableDescriptor.setValidator(new ICellEditorValidator() {

            @Override
            public String isValid(Object value) {

                return handleRmnNodeIdvalue(value);
            }

        });
        configurationDescriptor
                .setCategory(IPropertySourceSupport.BASIC_CATEGORY);

        isAsyncOnly.setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        isAsyncOnly.setFilterFlags(EXPERT_FILTER_FLAG);

        isType1Router.setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        isType1Router.setFilterFlags(EXPERT_FILTER_FLAG);
        isType2Router.setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        isType2Router.setFilterFlags(EXPERT_FILTER_FLAG);
        forcedObjects.setFilterFlags(EXPERT_FILTER_FLAG);
    }

    /**
     * Adds the List of Property Descriptors to the RMN Property
     *
     * @param propertyList
     */
    private void addRedundantNodePropertyDescriptors(
            List<IPropertyDescriptor> propertyList) {

        if (rmn == null) {
            return;
        }
        // checks whether XDC import of node has occurred
        if (!redundantManagingNode.hasError()) {
            propertyList.add(nameDescriptor);
            propertyList.add(nodeIdEditableDescriptor);

            if (rmn.getPathToXDC() != null) {

                propertyList.add(configurationDescriptor);
            }

            propertyList.add(waitNotActiveDescriptor);

            propertyList.add(priorityDescriptor);

            propertyList.add(isAsyncOnly);
            propertyList.add(isType1Router);
            propertyList.add(isType2Router);
            // propertyList.add(lossSocToleranceDescriptor);
            if (rmn.getForcedObjects() != null) {
                propertyList.add(forcedObjects);
            }
        } else {
            propertyList.add(readOnlynameDescriptor);
            propertyList.add(nodeIDDescriptor);

            if (rmn.getPathToXDC() != null) {

                propertyList.add(configurationDescriptor);
            }
            propertyList.add(nodeErrorDescriptor);
        }
    }

    @Override
    public Object getEditableValue() {
        return rmn;
    }

    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        List<IPropertyDescriptor> propertyList = new ArrayList<>();
        addRedundantNodePropertyDescriptors(propertyList);

        IPropertyDescriptor[] propertyDescriptorArray = {};
        propertyDescriptorArray = propertyList.toArray(propertyDescriptorArray);
        return propertyDescriptorArray;
    }

    /**
     * Receives the Property value to RMN properties
     */
    @Override
    public Object getPropertyValue(Object id) {
        Object retObj = null;
        try {
            if (id instanceof String) {
                String objectId = (String) id;
                switch (objectId) {
                    case IAbstractNodeProperties.NODE_NAME_OBJECT:
                        if (rmn.getName() != null) {
                            retObj = rmn.getName();
                        } else {
                            retObj = "";
                        }
                        break;
                    case IAbstractNodeProperties.NODE_ID_READONLY_OBJECT:
                    case IAbstractNodeProperties.NODE_ID_EDITABLE_OBJECT:
                        retObj = redundantManagingNode.getNodeIdString();
                        break;
                    case IAbstractNodeProperties.NODE_CONIFG_OBJECT:
                        retObj = rmn.getPathToXDC();
                        break;
                    case IAbstractNodeProperties.NODE_ERROR_OBJECT:
                        retObj = IAbstractNodeProperties.NODE_ERROR_DESCRIPTION;
                        break;
                    case IRedundantManagingNodeProperties.RMN_WAIT_NOT_ACTIVE_OBJECT:
                        String waitNotActive = redundantManagingNode
                                .getWaitNotActive();
                        if (waitNotActive.isEmpty()) {
                            return "";
                        }
                        retObj = String.valueOf(Integer.decode(waitNotActive));
                        break;
                    case IRedundantManagingNodeProperties.RMN_PRIORITY_OBJECT:
                        String priority = redundantManagingNode
                                .getRmnPriority();
                        if (priority.isEmpty()) {
                            return "";
                        }
                        retObj = String.valueOf(Integer.decode(priority));
                        break;
                    case IAbstractNodeProperties.NODE_IS_ASYNC_ONLY_OBJECT: {
                        int value = (rmn.isIsAsyncOnly() == true) ? 0 : 1;
                        retObj = Integer.valueOf(value);
                        break;
                    }
                    case IAbstractNodeProperties.NODE_IS_TYPE1_ROUTER_OBJECT: {
                        int value = (rmn.isIsType1Router() == true) ? 0 : 1;
                        retObj = Integer.valueOf(value);
                        break;
                    }
                    case IAbstractNodeProperties.NODE_IS_TYPE2_ROUTER_OBJECT: {
                        int value = (rmn.isIsType2Router() == true) ? 0 : 1;
                        retObj = Integer.valueOf(value);
                        break;
                    }
                    case IAbstractNodeProperties.NODE_FORCED_OBJECTS_OBJECT:
                        String forcedObjectString = redundantManagingNode
                                .getForcedObjectsString();
                        if (forcedObjectString.isEmpty()) {
                            return "";
                        }
                        retObj = redundantManagingNode.getForcedObjectsString();
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
                    redundantManagingNode.getNetworkId());
            retObj = StringUtils.EMPTY;
        }
        return retObj;
    }

    /**
     * Handles the Node assignment.
     *
     * @param value The new value of Node assignment.
     * @param nodeAssign
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

            Result res = OpenConfiguratorLibraryUtils.setNodeAssignment(
                    nodeAssign, redundantManagingNode, result);
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
     * Handles the RMN node ID value.
     *
     * @param id The new value of RMN node.
     *
     * @return Returns a string indicating whether the given value is valid;
     *         null means valid, and non-null means invalid, with the result
     *         being the error message to display to the end user.
     */
    private String handleRmnNodeIdvalue(Object id) {

        if (id instanceof String) {
            if (((String) id).isEmpty()) {
                return ERROR_NODE_ID_CANNOT_BE_EMPTY;
            }
            try {
                short nodeIDvalue = Short.valueOf(((String) id));
                if ((nodeIDvalue == 0)
                        || (nodeIDvalue == IPowerlinkConstants.MN_DEFAULT_NODE_ID)
                        || (nodeIDvalue > IPowerlinkConstants.RMN_MAX_NODE_ID)) {
                    return ERROR_INVALID_RMN_NODE_ID;
                }
                if (nodeIDvalue < IPowerlinkConstants.MN_DEFAULT_NODE_ID) {
                    return ERROR_RMN_ID_RANGE;
                }

                if (nodeIDvalue == redundantManagingNode.getNodeId()) {
                    return null;
                }

                boolean nodeIdAvailable = redundantManagingNode
                        .getPowerlinkRootNode()
                        .isNodeIdAlreadyAvailable(nodeIDvalue);
                if (nodeIdAvailable) {
                    return MessageFormat.format(ERROR_RMN_ID_EXISTS,
                            nodeIDvalue);
                }
            } catch (NumberFormatException ex) {
                return ERROR_INVALID_RMN_NODE_ID;
            }
        }

        return null;
    }

    /**
     * Handles the priority changes for an RMN.
     *
     * @param value New priority to be set.
     * @return Returns a string indicating whether the given value is valid;
     *         null means valid, and non-null means invalid, with the result
     *         being the error message to display to the end user.
     */
    protected String handleRmnPriority(Object value) {
        if (value instanceof String) {
            if (((String) value).isEmpty()) {
                return ERROR_RMN_PRIORITY_CANNOT_BE_EMPTY;
            }

            try {
                long longValue = Long.decode((String) value);
                // validate the value with openCONFIGURATOR library.
                PowerlinkSubobject rmnPrioritySubObj = redundantManagingNode
                        .getObjectDictionary().getSubObject(
                                IRedundantManagingNodeProperties.RMN_PRIORITY_OBJECT_ID,
                                IRedundantManagingNodeProperties.RMN_PRIORITY_SUBOBJECT_ID);
                if (rmnPrioritySubObj == null) {
                    return AbstractNodePropertySource.ERROR_OBJECT_NOT_FOUND;
                }
                Result validateResult = OpenConfiguratorLibraryUtils
                        .validateSubobjectActualValue(
                                redundantManagingNode.getNetworkId(),
                                redundantManagingNode.getNodeId(),
                                IRedundantManagingNodeProperties.RMN_PRIORITY_OBJECT_ID,
                                IRedundantManagingNodeProperties.RMN_PRIORITY_SUBOBJECT_ID,
                                String.valueOf(longValue), false);

                if (!validateResult.IsSuccessful()) {
                    return OpenConfiguratorLibraryUtils
                            .getErrorMessage(validateResult);
                }

            } catch (NumberFormatException e) {
                return ERROR_INVALID_VALUE_RMN_PRIORITY;
            }
        } else {
            System.err
                    .println("handleRmnPriority: Invalid value type:" + value);
        }
        return null;
    }

    /**
     * Handles Node name Modifications
     *
     * @param name new Name of Node
     * @return Returns a string indicating whether the given value is valid;
     *         null means valid, and non-null means invalid, with the result
     *         being the error message to display to the end user.
     */
    @Override
    protected String handleSetNodeName(Object name) {
        if (name instanceof String) {
            String nodeName = (String) name;
            if (nodeName.isEmpty()) {
                return ERROR_NODE_NAME_CANNOT_BE_EMPTY;
            }

            // Space as first character is not allowed. ppc:tNonEmptyString
            if (nodeName.charAt(0) == ' ') {
                return "Invalid name";
            }

            Result res = OpenConfiguratorCore.GetInstance().SetNodeName(
                    redundantManagingNode.getNetworkId(),
                    redundantManagingNode.getNodeId(), nodeName);
            if (!res.IsSuccessful()) {
                return OpenConfiguratorLibraryUtils.getErrorMessage(res);
            }
        } else {
            System.err.println("handleSetNodeName: Invalid value type:" + name);
        }
        return null;
    }

    /**
     * Handles the wait not active modifications.
     *
     * @param value New value for wait not active.
     *
     * @return Returns a string indicating whether the given value is valid;
     *         null means valid, and non-null means invalid, with the result
     *         being the error message to display to the end user.
     */
    protected String handleWaitNotActive(Object value) {
        if (value instanceof String) {
            if (((String) value).isEmpty()) {
                return ERROR_WAIT_NOT_ACTIVE_CANNOT_BE_EMPTY;
            }
            try {
                long longValue = Long.decode((String) value);

                // Check the minInclusive available in the Schema
                if (longValue < IPowerlinkConstants.RMN_MAX_NODE_ID) {
                    return INVALID_RANGE_WAIT_NOT_ACTIVE;
                }
                // validate the value with openCONFIGURATOR library.
                PowerlinkSubobject waitNotActiveSubObj = redundantManagingNode
                        .getObjectDictionary().getSubObject(
                                IRedundantManagingNodeProperties.RMN_WAIT_NOT_ACTIVE_OBJECT_ID,
                                IRedundantManagingNodeProperties.RMN_WAIT_NOT_ACTIVE_SUBOBJECT_ID);
                if (waitNotActiveSubObj == null) {
                    return AbstractNodePropertySource.ERROR_OBJECT_NOT_FOUND;
                }
                Result validateResult = OpenConfiguratorLibraryUtils
                        .validateSubobjectActualValue(
                                redundantManagingNode.getNetworkId(),
                                redundantManagingNode.getNodeId(),
                                IRedundantManagingNodeProperties.RMN_WAIT_NOT_ACTIVE_OBJECT_ID,
                                IRedundantManagingNodeProperties.RMN_WAIT_NOT_ACTIVE_SUBOBJECT_ID,
                                String.valueOf(longValue), false);
                if (!validateResult.IsSuccessful()) {
                    return OpenConfiguratorLibraryUtils
                            .getErrorMessage(validateResult);
                }

            } catch (NumberFormatException e) {
                return ERROR_INVALID_VALUE_WAIT_NOT_ACTIVE;
            }
        } else {
            System.err.println(
                    "handleWaitNotActive: Invalid value type:" + value);
        }
        return null;
    }

    @Override
    public boolean isPropertySet(Object id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void resetPropertyValue(Object id) {
        // TODO Auto-generated method stub
    }

    /**
     * Set the node details if the property source instance to be re-used for a
     * new node.
     *
     * @param redundantManagingNode The node instance.
     */
    public void setNodeData(final Node redundantManagingNode) {
        this.redundantManagingNode = redundantManagingNode;

        if (redundantManagingNode.getNodeModel() instanceof TRMN) {
            rmn = (TRMN) redundantManagingNode.getNodeModel();
        } else {
            rmn = null;
        }
    }

    /**
     * sets the value to the properties of RMN
     */
    @Override
    public void setPropertyValue(Object id, Object value) {
        Result res = new Result();
        try {
            if (id instanceof String) {
                String objectId = (String) id;
                switch (objectId) {
                    case IAbstractNodeProperties.NODE_NAME_OBJECT: {
                        res = OpenConfiguratorCore.GetInstance().SetNodeName(
                                redundantManagingNode.getNetworkId(),
                                redundantManagingNode.getNodeId(),
                                (String) value);
                        if (!res.IsSuccessful()) {
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printLibraryErrorMessage(res);
                        } else {
                            redundantManagingNode.setName((String) value);
                        }
                        break;
                    }
                    case IAbstractNodeProperties.NODE_ID_EDITABLE_OBJECT: {
                        short nodeIDvalue = Short.valueOf(((String) value));
                        short oldNodeId = redundantManagingNode.getNodeId();
                        redundantManagingNode.getPowerlinkRootNode()
                                .setNodeId(oldNodeId, nodeIDvalue);
                        break;
                    }
                    case IAbstractNodeProperties.NODE_CONIFG_OBJECT:
                        System.err.println(objectId + " made editable");
                        break;
                    case IRedundantManagingNodeProperties.RMN_WAIT_NOT_ACTIVE_OBJECT:
                        res = OpenConfiguratorCore.GetInstance()
                                .SetRedundantManagingNodeWaitNotActive(
                                        redundantManagingNode.getNetworkId(),
                                        redundantManagingNode.getNodeId(),
                                        Long.decode((String) value));
                        if (res.IsSuccessful()) {
                            redundantManagingNode.setRmnWaitNotActive(
                                    Long.decode((String) value));
                        } else {
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printLibraryErrorMessage(res);
                        }

                        break;
                    case IRedundantManagingNodeProperties.RMN_PRIORITY_OBJECT:
                        res = OpenConfiguratorCore.GetInstance()
                                .SetRedundantManagingNodePriority(
                                        redundantManagingNode.getNetworkId(),
                                        redundantManagingNode.getNodeId(),
                                        Long.decode((String) value));
                        if (res.IsSuccessful()) {
                            redundantManagingNode.setRmnPriority(
                                    Long.decode((String) value));
                        } else {
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printLibraryErrorMessage(res);
                        }

                        break;
                    case IAbstractNodeProperties.NODE_IS_ASYNC_ONLY_OBJECT: {
                        if (value instanceof Integer) {
                            int val = ((Integer) value).intValue();
                            boolean result = (val == 0) ? true : false;
                            rmn.setIsAsyncOnly(result);
                            OpenConfiguratorProjectUtils
                                    .updateNodeAttributeValue(
                                            redundantManagingNode, objectId,
                                            String.valueOf(result));
                        } else {
                            System.err
                                    .println(objectId + " Invalid value type");
                        }
                        break;
                    }
                    case IAbstractNodeProperties.NODE_IS_TYPE1_ROUTER_OBJECT: {
                        if (value instanceof Integer) {
                            int val = ((Integer) value).intValue();
                            boolean result = (val == 0) ? true : false;
                            rmn.setIsType1Router(result);
                            OpenConfiguratorProjectUtils
                                    .updateNodeAttributeValue(
                                            redundantManagingNode, objectId,
                                            String.valueOf(result));
                        } else {
                            System.err
                                    .println(objectId + " Invalid value type");
                        }
                        break;
                    }
                    case IAbstractNodeProperties.NODE_IS_TYPE2_ROUTER_OBJECT: {
                        if (value instanceof Integer) {
                            int val = ((Integer) value).intValue();
                            boolean result = (val == 0) ? true : false;
                            rmn.setIsType2Router(result);
                            OpenConfiguratorProjectUtils
                                    .updateNodeAttributeValue(
                                            redundantManagingNode, objectId,
                                            String.valueOf(result));
                        } else {
                            System.err
                                    .println(objectId + " Invalid value type");
                        }
                        break;
                    }
                    case IAbstractNodeProperties.NODE_FORCED_OBJECTS_OBJECT:
                        // Ignore
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
                    redundantManagingNode.getProject().getName());
        }
    }
}

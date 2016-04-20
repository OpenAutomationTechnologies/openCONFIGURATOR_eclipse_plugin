/*******************************************************************************
 * @file   AbstractNodePropertySource.java
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

import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.epsg.openconfigurator.lib.wrapper.NodeAssignment;
import org.epsg.openconfigurator.model.IAbstractNodeProperties;

/**
 * Abstract implementation of property source for any Node.
 *
 * @author Ramakrishnan P
 *
 */
public abstract class AbstractNodePropertySource {

    // Common label names
    private static final String NODE_NAME_LABEL = "Node Name";
    public static final String NODE_ID_LABEL = "Node ID";
    public static final String NODE_ERROR_LABEL = "Error";
    private static final String NODE_CONFIG_LABEL = "Configuration File";
    private static final String NODE_IS_ASYNC_ONLY_LABEL = "Is Async Only";
    private static final String NODE_IS_TYPE1_ROUTER_LABEL = "Is Type1 Router";
    private static final String NODE_IS_TYPE2_ROUTER_LABEL = "Is Type2 Router";
    private static final String NODE_FORCED_OBJECTS_LABEL = "Forced Objects";

    public static final String INTERFACE_UNIQUEID_LABEL = "Unique ID"; //$NON-NLS-1$
    public static final String INTERFACE_TYPE_LABEL = "Type"; //$NON-NLS-1$
    public static final String INTERFACE_MAX_MODULES_LABEL = "Max Modules"; //$NON-NLS-1$
    public static final String INTERFACE_UNUSED_SLOTS_LABEL = "Unused Slots"; //$NON-NLS-1$
    public static final String INTERFACE_MODULE_ADDRESSING_LABEL = "Module Addressing"; //$NON-NLS-1$
    public static final String INTERFACE_MULTIPLE_MODULES_LABEL = "Multiple Modules"; //$NON-NLS-1$

    // ERROR messages
    public static final String NOT_SUPPORTED = "Currently not supported.";
    public static final String ERROR_NODE_NAME_CANNOT_BE_EMPTY = "Node name cannot be empty.";
    public static final String ERROR_NODE_ID_CANNOT_BE_EMPTY = "Node ID cannot be empty.";
    public static final String ERROR_NODE_ID_EXCEEDS_RANGE = "Node ID exceeds the range.";
    public static final String ERROR_NODE_ID_DECEEDS_RANGE = "Node ID deceeds the range.";
    public static final String ERROR_OBJECT_NOT_FOUND = "Object not found!";

    protected TextPropertyDescriptor nameDescriptor = new TextPropertyDescriptor(
            IAbstractNodeProperties.NODE_NAME_OBJECT, NODE_NAME_LABEL);
    protected PropertyDescriptor readOnlynameDescriptor = new PropertyDescriptor(
            IAbstractNodeProperties.NODE_NAME_OBJECT, NODE_NAME_LABEL);
    protected PropertyDescriptor nodeIdDescriptor = new PropertyDescriptor(
            IAbstractNodeProperties.NODE_ID_READONLY_OBJECT, NODE_ID_LABEL);
    protected PropertyDescriptor configurationDescriptor = new PropertyDescriptor(
            IAbstractNodeProperties.NODE_CONIFG_OBJECT, NODE_CONFIG_LABEL);
    protected PropertyDescriptor nodeErrorDescriptor = new PropertyDescriptor(
            IAbstractNodeProperties.NODE_ERROR_OBJECT, NODE_ERROR_LABEL);

    protected PropertyDescriptor interfaceUniqueIdDescriptor = new PropertyDescriptor(
            IAbstractNodeProperties.INTERFACE_UNIQUEID_OBJECT,
            INTERFACE_UNIQUEID_LABEL);
    protected PropertyDescriptor interfaceTypeDescriptor = new PropertyDescriptor(
            IAbstractNodeProperties.INTERFACE_TYPE_OBJECT,
            INTERFACE_TYPE_LABEL);
    protected PropertyDescriptor interfaceMaxModulesDescriptor = new PropertyDescriptor(
            IAbstractNodeProperties.INTERFACE_MAX_MODULES_OBJECT,
            INTERFACE_MAX_MODULES_LABEL);
    protected PropertyDescriptor interfaceUnusedSlotsDescriptor = new PropertyDescriptor(
            IAbstractNodeProperties.INTERFACE_UNUSED_SLOTS_OBJECT,
            INTERFACE_UNUSED_SLOTS_LABEL);
    protected PropertyDescriptor interfaceModuleAddressingDescriptor = new PropertyDescriptor(
            IAbstractNodeProperties.INTERFACE_MODULE_ADDRESSING_OBJECT,
            INTERFACE_MODULE_ADDRESSING_LABEL);
    protected PropertyDescriptor interfaceMultipleModulesDescriptor = new PropertyDescriptor(
            IAbstractNodeProperties.INTERFACE_MULTIPLE_MODULES_OBJECT,
            INTERFACE_MULTIPLE_MODULES_LABEL);

    protected ComboBoxPropertyDescriptor isAsyncOnly = new ComboBoxPropertyDescriptor(
            IAbstractNodeProperties.NODE_IS_ASYNC_ONLY_OBJECT,
            NODE_IS_ASYNC_ONLY_LABEL, IPropertySourceSupport.YES_NO);

    protected ComboBoxPropertyDescriptor isType1Router = new ComboBoxPropertyDescriptor(
            IAbstractNodeProperties.NODE_IS_TYPE1_ROUTER_OBJECT,
            NODE_IS_TYPE1_ROUTER_LABEL, IPropertySourceSupport.YES_NO);

    protected ComboBoxPropertyDescriptor isType2Router = new ComboBoxPropertyDescriptor(
            IAbstractNodeProperties.NODE_IS_TYPE2_ROUTER_OBJECT,
            NODE_IS_TYPE2_ROUTER_LABEL, IPropertySourceSupport.YES_NO);

    protected PropertyDescriptor forcedObjects = new PropertyDescriptor(
            IAbstractNodeProperties.NODE_FORCED_OBJECTS_OBJECT,
            NODE_FORCED_OBJECTS_LABEL);

    AbstractNodePropertySource() {
        readOnlynameDescriptor
                .setCategory(IPropertySourceSupport.BASIC_CATEGORY);
        nameDescriptor.setDescription(
                IAbstractNodeProperties.NAME_OF_THE_SELECTED_NODE);
        nameDescriptor.setValidator(new ICellEditorValidator() {
            @Override
            public String isValid(Object value) {
                return handleSetNodeName(value);
            }
        });

        isAsyncOnly.setDescription(
                IAbstractNodeProperties.NODE_IS_ASYNC_ONLY_DESCRIPTION);
        isAsyncOnly.setValidator(new ICellEditorValidator() {

            @Override
            public String isValid(Object value) {
                return handleNodeAssignValue(
                        NodeAssignment.NMT_NODEASSIGN_ASYNCONLY_NODE, value);
            }
        });

        isType1Router.setDescription(
                IAbstractNodeProperties.NODE_ROUTER_TYPE1_DESCRIPTION);
        isType1Router.setValidator(new ICellEditorValidator() {

            @Override
            public String isValid(Object value) {
                return handleNodeAssignValue(NodeAssignment.NMT_NODEASSIGN_RT1,
                        value);
            }
        });

        isType2Router.setDescription(
                IAbstractNodeProperties.NODE_ROUTER_TYPE2_DESCRIPTION);
        isType2Router.setValidator(new ICellEditorValidator() {

            @Override
            public String isValid(Object value) {
                return handleNodeAssignValue(NodeAssignment.NMT_NODEASSIGN_RT2,
                        value);
            }
        });
    }

    /**
     * Handle node assignment via node properties.
     *
     * @param nodeAssign Value of node assignment.
     * @param value Yes or No.
     * @return Returns a string indicating whether the given value is valid;
     *         null means valid, and non-null means invalid, with the result
     *         being the error message to display to the end user.
     */
    protected abstract String handleNodeAssignValue(NodeAssignment nodeAssign,
            Object value);

    /**
     * Handles the name changes for the particular node.
     *
     * @param name The new name to be assigned.
     * @return Returns a string indicating whether the given value is valid;
     *         null means valid, and non-null means invalid, with the result
     *         being the error message to display to the end user.
     */
    protected abstract String handleSetNodeName(Object name);

}

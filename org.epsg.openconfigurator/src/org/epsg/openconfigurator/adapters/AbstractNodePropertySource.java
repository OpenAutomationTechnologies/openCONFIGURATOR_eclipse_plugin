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
    private static final String NODE_ID_LABEL = "Node ID";
    private static final String NODE_CONFIG_LABEL = "Configuration";
    private static final String NODE_IS_ASYNC_ONLY_LABEL = "Is Async Only";
    private static final String NODE_IS_TYPE1_ROUTER_LABEL = "Is Type1 Router";
    private static final String NODE_IS_TYPE2_ROUTER_LABEL = "Is Type2 Router";
    private static final String NODE_FORCED_OBJECTS_LABEL = "Forced Objects";
    private static final String CN_LOSS_OF_SOC_TOLERANCE_LABEL = "Loss of SoC Tolerance(" + "\u00B5" + "s)";

    // ERROR messages
    public static final String NOT_SUPPORTED = "Currently not supported";
    public static final String ERROR_NODE_NAME_CANNOT_BE_EMPTY = "Node name cannot be empty.";
    public static final String ERROR_LOSS_SOC_TOLERANCE_CANNOT_BE_EMPTY = "Loss of SoC tolerance value cannot be empty.";
    public static final String ERROR_INVALID_VALUE_LOSS_SOC_TOLERANCE = "Invalid Loss of SoC tolerance value";

    protected TextPropertyDescriptor nameDescriptor = new TextPropertyDescriptor(
            IAbstractNodeProperties.NODE_NAME_OBJECT, NODE_NAME_LABEL);
    protected PropertyDescriptor nodeIdDescriptor = new PropertyDescriptor(
            IAbstractNodeProperties.NODE_ID_OBJECT, NODE_ID_LABEL);
    protected PropertyDescriptor configurationDescriptor = new PropertyDescriptor(
            IAbstractNodeProperties.NODE_CONIFG_OBJECT, NODE_CONFIG_LABEL);

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

    protected TextPropertyDescriptor lossSocToleranceDescriptor = new TextPropertyDescriptor(
            IAbstractNodeProperties.NODE_LOSS_OF_SOC_TOLERANCE_OBJECT,
            CN_LOSS_OF_SOC_TOLERANCE_LABEL);

    AbstractNodePropertySource() {
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

        lossSocToleranceDescriptor.setDescription(
                IAbstractNodeProperties.LOSS_SOC_TOLERANCE_DESCRIPTION);

        lossSocToleranceDescriptor.setValidator(new ICellEditorValidator() {
            @Override
            public String isValid(Object value) {
                return handleLossOfSoCTolerance(value);
            }
        });

    }

    /**
     * Handle loss of SoC tolerance assignment.
     *
     * @param value Value to be set.
     *
     * @return Returns a string indicating whether the given value is valid;
     *         null means valid, and non-null means invalid, with the result
     *         being the error message to display to the end user.
     */
    protected abstract String handleLossOfSoCTolerance(Object value);

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

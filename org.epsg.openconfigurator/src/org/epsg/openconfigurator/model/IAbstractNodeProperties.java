/*******************************************************************************
 * @file   IAbstractNodeProperties.java
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

/**
 * Abstract node property objects.
 *
 * @author Ramakrishnan P
 *
 */
public interface IAbstractNodeProperties {
    // Common object names
    public static final String NODE_NAME_OBJECT = "name"; //$NON-NLS-1$

    // Intended only for project XML usage. @see NODE_ID_READONLY_OBJECT and
    // NODE_ID_EDITABLE_OBJECT
    public static final String NODE_ID_OBJECT = "nodeID"; //$NON-NLS-1$
    public static final String NODE_ID_READONLY_OBJECT = "node.NodeId.ReadOnly"; //$NON-NLS-1$
    public static final String NODE_ID_EDITABLE_OBJECT = "node.NodeId.Editable"; //$NON-NLS-1$
    public static final String NODE_ERROR_OBJECT = "node.NodeId.Error"; //$NON-NLS-1$

    public static final String INTERFACE_UNIQUEID_OBJECT = "node.Interface.UniqueID"; //$NON-NLS-1$
    public static final String INTERFACE_TYPE_OBJECT = "node.Interface.Type"; //$NON-NLS-1$
    public static final String INTERFACE_MAX_MODULES_OBJECT = "node.Interface.Maxmodules"; //$NON-NLS-1$
    public static final String INTERFACE_UNUSED_SLOTS_OBJECT = "node.Interface.Unusedslots"; //$NON-NLS-1$
    public static final String INTERFACE_MODULE_ADDRESSING_OBJECT = "node.Interface.Moduleaddressing"; //$NON-NLS-1$
    public static final String INTERFACE_MULTIPLE_MODULES_OBJECT = "node.Interface.Multiplemodules"; //$NON-NLS-1$

    public static final String MODULE_NAME_OBJECT = "name"; //$NON-NLS-1$
    public static final String MODULE_POSITION_OBJECT = "position"; //$NON-NLS-1$
    public static final String MODULE_ADDRESS_OBJECT = "address"; //$NON-NLS-1$
    public static final String MODULE_POSITION_READONLY_OBJECT = "node.Module.Position"; //$NON-NLS-1$
    public static final String MODULE_POSITION_EDITABLE_OBJECT = "node.Module.Editable"; //$NON-NLS-1$
    public static final String MODULE_PATH_TO_XDC_OBJECT = "node.Module.pathToXDC"; //$NON-NLS-1$
    public static final String MODULE_ADDRESS_READONLY_OBJECT = "node.Module.Address"; //$NON-NLS-1$
    public static final String MODULE_ENABLED_OBJECT = "node.Module.Enabled"; //$NON-NLS-1$

    public static final String NODE_CONIFG_OBJECT = "pathToXDC"; //$NON-NLS-1$
    public static final String NODE_IS_ASYNC_ONLY_OBJECT = "isAsyncOnly"; //$NON-NLS-1$
    public static final String NODE_IS_TYPE1_ROUTER_OBJECT = "isType1Router"; //$NON-NLS-1$
    public static final String NODE_IS_TYPE2_ROUTER_OBJECT = "isType2Router"; //$NON-NLS-1$
    public static final String NODE_FORCED_OBJECTS_OBJECT = "ForcedObjects"; //$NON-NLS-1$
    public static final String NODE_OBJECTS_OBJECT = "Object"; //$NON-NLS-1$
    public static final String NODE_OBJECTS_INDEX_OBJECT = "index"; //$NON-NLS-1$
    public static final String NODE_OBJECTS_SUBINDEX_OBJECT = "subindex"; //$NON-NLS-1$

    public static final String NAME_OF_THE_SELECTED_NODE = "Name of the node selected.";
    public static final String NODE_IS_ASYNC_ONLY_DESCRIPTION = "Yes -> AsyncOnly node. No -> Isochonrously accessed node. See 0x1F81, Bit 8.";
    public static final String NODE_ROUTER_TYPE1_DESCRIPTION = "Router type 1. See 0x1F81, Bit 10.";
    public static final String NODE_ROUTER_TYPE2_DESCRIPTION = "Router type 2. See 0x1F81, Bit 11.";
    public static final String NODE_ERROR_DESCRIPTION = "Invalid Configuration file.";

    public static final long LOSS_SOC_TOLERANCE_OBJECT_ID = 0x1C14;

    public static final long NODE_ASSIGNMENT_OBJECT_ID = 0x1F81;
}

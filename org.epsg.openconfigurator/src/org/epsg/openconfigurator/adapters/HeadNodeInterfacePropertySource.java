/*******************************************************************************
 * @file   HeadNodeInterfacePropertySource.java
 *
 * @author Sree Hari Vignesh, Kalycito Infotech Private Limited.
 *
 * @copyright (c) 2016, Kalycito Infotech Private Limited
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

import org.apache.commons.lang3.StringUtils;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.epsg.openconfigurator.console.OpenConfiguratorMessageConsole;
import org.epsg.openconfigurator.lib.wrapper.NodeAssignment;
import org.epsg.openconfigurator.model.HeadNodeInterface;
import org.epsg.openconfigurator.model.IAbstractNodeProperties;

/**
 * Describes the interface properties of modular head node.
 *
 * @author SreeHari
 *
 */
public class HeadNodeInterfacePropertySource extends AbstractNodePropertySource
        implements IPropertySource {

    private HeadNodeInterface devModularInterface;

    /**
     * Constructor that defines the value from XDD model.
     *
     * @param deviceModularModel XDD model of head node interface
     */
    public HeadNodeInterfacePropertySource(
            HeadNodeInterface deviceModularModel) {
        super();
        setInterfaceData(deviceModularModel);

    }

    // Property descriptors
    private void addInterfacePropertyDescriptors(
            List<IPropertyDescriptor> propertyList) {
        propertyList.add(interfaceUniqueIdDescriptor);
        propertyList.add(interfaceMaxModulesDescriptor);
        propertyList.add(interfaceTypeDescriptor);
        propertyList.add(interfaceModuleAddressingDescriptor);
        propertyList.add(interfaceUnusedSlotsDescriptor);
        propertyList.add(interfaceMultipleModulesDescriptor);
    }

    @Override
    public Object getEditableValue() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        List<IPropertyDescriptor> propertyList = new ArrayList<>();
        addInterfacePropertyDescriptors(propertyList);

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
                    case IAbstractNodeProperties.INTERFACE_UNIQUEID_OBJECT:

                        retObj = devModularInterface.getInterfaceUId();
                        break;
                    case IAbstractNodeProperties.INTERFACE_TYPE_OBJECT:

                        retObj = devModularInterface.getInterfaceType();
                        break;
                    case IAbstractNodeProperties.INTERFACE_MAX_MODULES_OBJECT:
                        if (devModularInterface.getMaxModules() != null) {
                            retObj = devModularInterface.getMaxModules();
                        } else {
                            retObj = "";
                        }
                        break;
                    case IAbstractNodeProperties.INTERFACE_MULTIPLE_MODULES_OBJECT:
                        if (devModularInterface.isMultipleModules()) {
                            retObj = "true";
                        } else {
                            retObj = "false";
                        }
                        break;
                    case IAbstractNodeProperties.INTERFACE_MODULE_ADDRESSING_OBJECT:

                        retObj = devModularInterface.getModuleAddressing();
                        break;
                    case IAbstractNodeProperties.INTERFACE_UNUSED_SLOTS_OBJECT:
                        if (devModularInterface.isInterfaceUnUsedSlots()) {
                            retObj = "true";
                        } else {
                            retObj = "false";
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
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            OpenConfiguratorMessageConsole.getInstance().printErrorMessage(
                    "Property: " + id + " " + e.getMessage(),
                    devModularInterface.getNode().getNetworkId());
            retObj = StringUtils.EMPTY;
        }
        return retObj;
    }

    @Override
    protected String handleNodeAssignValue(NodeAssignment nodeAssign,
            Object value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    protected String handleSetNodeName(Object name) {
        // TODO Auto-generated method stub
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
     * Sets the XDD data into java model.
     *
     * @param deviceModularModel XDD model of head node interface
     */
    public void setInterfaceData(final HeadNodeInterface deviceModularModel) {
        devModularInterface = deviceModularModel;
    }

    @Override
    public void setPropertyValue(Object id, Object value) {
        // TODO Auto-generated method stub

    }

}

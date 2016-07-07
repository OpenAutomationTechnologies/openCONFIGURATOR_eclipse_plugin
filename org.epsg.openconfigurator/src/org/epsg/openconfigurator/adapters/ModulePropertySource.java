/*******************************************************************************
 * @file   ModulePropertySource.java
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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.epsg.openconfigurator.lib.wrapper.NodeAssignment;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.model.IAbstractNodeProperties;
import org.epsg.openconfigurator.model.Module;
import org.epsg.openconfigurator.xmlbinding.projectfile.InterfaceList;
import org.epsg.openconfigurator.xmlbinding.xdd.TModuleAddressingHead;

/**
 * Describes the property descriptors of module.
 *
 * @author SreeHari
 *
 */
public class ModulePropertySource extends AbstractNodePropertySource
        implements IPropertySource {

    private static final String EMPTY_POSITION_ERROR_MESSAGE = "Position cannot be empty.";
    private static final String INVALID_POSITION_ERROR_MESSAGE = "The module type does not match the interface of node ";
    private static final String HIGHER_POSITION_ERROR_MESSAGE = "The position of module should not be greater than ";
    private static final String INVALID_MODULE_TYPE_ERROR_MESSAGE = "The module cannot be placed in position {0} due to invalid module type.";
    private static final String EMPTY_ADDRESS_ERROR_MESSAGE = "Address cannot be empty.";
    private static final String AVAILABLE_MODULE_ERROR_MESSAGE = "The position {0} already exists in {1}.";
    private static final String INVALID_ADDRESS_ERROR_MESSAGE = "Invalid Address.";
    private static final String HIGHER_ADDRESS_ERROR_MESSAGE = "The address of module should not exceed {0}";
    private static final String ADDRESS_EXISTS_ERROR_MESSAGE = "Module address already exists.";
    private static final String INVALID_NAME_ERROR_MESSAGE = "Invalid module name.";
    private static final String MODULE_EXISTS_ERROR_MESSAGE = "Module name already exists.";

    private Module module;
    private TModuleAddressingHead moduleAddressing;
    private InterfaceList.Interface.Module moduleObjModel;

    /**
     * Constructor to define the module properties from XDD model.
     *
     * @param moduleObj Instance of Module
     * @param moduleAddressing XDD instance of TModuleAddressingHead
     */
    public ModulePropertySource(Module moduleObj,
            TModuleAddressingHead moduleAddressing) {
        super();
        module = moduleObj;
        setModuledata(moduleObj, moduleAddressing);

        modulePositionTextDescriptor.setValidator(new ICellEditorValidator() {

            @Override
            public String isValid(Object value) {
                return handleModulePositionValue(value);

            }

        });

        moduleNameDescriptor.setValidator(new ICellEditorValidator() {

            @Override
            public String isValid(Object value) {
                return handleSetNodeName(value);
            }

        });

        moduleAddressTextDescriptor.setValidator(new ICellEditorValidator() {

            @Override
            public String isValid(Object value) {
                return handleSetModuleAddress(value);
            }

        });

        interfaceModuleAddressingDescriptor
                .setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        interfaceModuleAddressingDescriptor
                .setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);
        moduleNameDescriptor.setCategory(IPropertySourceSupport.BASIC_CATEGORY);
        modulePositionDescriptor
                .setCategory(IPropertySourceSupport.BASIC_CATEGORY);
        modulePositionTextDescriptor
                .setCategory(IPropertySourceSupport.BASIC_CATEGORY);
        modulePathToXDCDescriptor
                .setCategory(IPropertySourceSupport.BASIC_CATEGORY);
        moduleEnabledDescriptor
                .setCategory(IPropertySourceSupport.BASIC_CATEGORY);
        moduleAddressDescriptor
                .setCategory(IPropertySourceSupport.BASIC_CATEGORY);
        moduleAddressTextDescriptor
                .setCategory(IPropertySourceSupport.BASIC_CATEGORY);
    }

    private void addControlledNodeModulePropertyDescriptors(
            List<IPropertyDescriptor> propertyList) {

        moduleAddressing = module.getInterfaceOfModule().getModuleAddressing();

        if (moduleObjModel == null) {
            return;
        }
        propertyList.add(moduleNameDescriptor);
        if (isPositionOrAddressEditable()) {
            propertyList.add(moduleAddressTextDescriptor);
            propertyList.add(modulePositionTextDescriptor);
        } else {
            propertyList.add(moduleAddressDescriptor);
            propertyList.add(modulePositionDescriptor);
        }
        propertyList.add(modulePathToXDCDescriptor);
        propertyList.add(moduleEnabledDescriptor);
    }

    @Override
    public Object getEditableValue() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        List<IPropertyDescriptor> propertyList = new ArrayList<IPropertyDescriptor>();
        addControlledNodeModulePropertyDescriptors(propertyList);

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
                    case IAbstractNodeProperties.MODULE_NAME_OBJECT: {
                        retObj = moduleObjModel.getName();
                        break;
                    }
                    case IAbstractNodeProperties.MODULE_POSITION_EDITABLE_OBJECT:
                    case IAbstractNodeProperties.MODULE_POSITION_READONLY_OBJECT: {
                        retObj = String.valueOf(moduleObjModel.getPosition());
                        break;
                    }
                    case IAbstractNodeProperties.MODULE_ADDRESS_READONLY_OBJECT:
                    case IAbstractNodeProperties.MODULE_ADDRESS_OBJECT: {
                        retObj = String.valueOf(moduleObjModel.getAddress());
                        break;
                    }
                    case IAbstractNodeProperties.MODULE_PATH_TO_XDC_OBJECT: {
                        retObj = moduleObjModel.getPathToXDC();
                        break;

                    }
                    case IAbstractNodeProperties.MODULE_ENABLED_OBJECT: {
                        int value = (moduleObjModel.isEnabled() == true) ? 1
                                : 0;
                        boolean isvalue = new Integer(value) == 0 ? false
                                : true;
                        retObj = String.valueOf(isvalue);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            retObj = StringUtils.EMPTY;
        }
        return retObj;
    }

    private String handleModulePositionValue(Object value) {

        if (module != null) {
            Set<Integer> positionSet = module.getInterfaceOfModule()
                    .getModuleCollection().keySet();
            if (value instanceof String) {
                if (((String) value).isEmpty()) {
                    return EMPTY_POSITION_ERROR_MESSAGE;
                }

                String positn = (String) value;
                int val = Integer.valueOf(positn);

                if (val == 1) {
                    Module mod = module.getInterfaceOfModule()
                            .getModuleCollection().get(module.getPosition());
                    String moduleType = mod.getModuleInterface().getType();

                    String interfaceType = module.getInterfaceOfModule()
                            .getInterfaceType();
                    if (!(moduleType.equals(interfaceType))) {
                        return INVALID_POSITION_ERROR_MESSAGE
                                + module.getNode().getNodeId();
                    }
                }

                if ((val == 0) || (val > module.getMaxPosition())) {
                    return HIGHER_POSITION_ERROR_MESSAGE
                            + module.getMaxPosition();
                }

                if (val == module.getPosition()) {
                    return null;
                }

                for (Integer position : positionSet) {

                    if (position == val) {

                        return MessageFormat.format(
                                AVAILABLE_MODULE_ERROR_MESSAGE, val,
                                module.getInterfaceOfModule()
                                        .getInterfaceUId());
                        // if (!module.validateNewPosition(position)) {
                        //
                        // return "The module cannot be placed in position "
                        // + val + " due to invalid module type.";
                        //
                        // }

                    }

                }
            }

        }
        return null;
    }

    @Override
    protected String handleNodeAssignValue(NodeAssignment nodeAssign,
            Object value) {
        // TODO Auto-generated method stub
        return null;
    }

    private String handleSetModuleAddress(Object value) {
        if (module != null) {
            Set<Integer> addressSet = module.getInterfaceOfModule()
                    .getAddressCollection().keySet();
            if (value instanceof String) {
                if (((String) value).isEmpty()) {
                    return EMPTY_ADDRESS_ERROR_MESSAGE;
                }

                String address = (String) value;
                int val = Integer.valueOf(address);

                if (module.getInterfaceOfModule().getMaxModules() != null) {
                    if ((val == 0) || (val > module.getInterfaceOfModule()
                            .getMaxModules().intValue())) {
                        return INVALID_ADDRESS_ERROR_MESSAGE;
                    }
                }

                if ((val == 0) || (val > module.getMaxPosition())) {
                    return MessageFormat.format(HIGHER_ADDRESS_ERROR_MESSAGE,
                            module.getMaxAddress());
                }

                if (val == module.getAddress()) {
                    return null;
                }

                for (Integer addressVal : addressSet) {

                    if (addressVal == val) {
                        return ADDRESS_EXISTS_ERROR_MESSAGE;
                    }

                }
            }

        }
        return null;
    }

    @Override
    protected String handleSetNodeName(Object name) {
        if (name instanceof String) {
            String nodeName = ((String) name);
            if (nodeName.isEmpty()) {
                return ERROR_NODE_NAME_CANNOT_BE_EMPTY;
            }

            if (nodeName.charAt(0) == ' ') {
                return INVALID_NAME_ERROR_MESSAGE;
            }

            if (nodeName.equals(module.getModuleName())) {
                return null;
            }

            Collection<Module> moduleCollection = module.getInterfaceOfModule()
                    .getModuleCollection().values();
            List<String> moduleNameList = new ArrayList<>();
            for (Module module : moduleCollection) {
                String moduleName = module.getModuleName();
                moduleNameList.add(moduleName);
            }

            if (moduleNameList != null) {
                for (String modName : moduleNameList) {
                    if (name.equals(modName)) {
                        return MODULE_EXISTS_ERROR_MESSAGE;
                    }
                }
            }

        } else {
            System.err.println("handleSetNodeName: Invalid value type:" + name);
        }
        return null;
    }

    /**
     * Verifies for the availability of position in the modules of head node.
     *
     * @param newPosition Position of module to be verified.
     * @return <true> if position is available, <false> if not available.
     */
    public boolean isPositionAvailable(int newPosition) {

        boolean positionAvailable = false;
        Set<Integer> positionSet = module.getInterfaceOfModule()
                .getModuleCollection().keySet();

        for (Integer position : positionSet) {

            if (position == newPosition) {
                positionAvailable = true;
            }

        }
        return positionAvailable;
    }

    private boolean isPositionOrAddressEditable() {
        boolean editable = false;

        if (String.valueOf(moduleAddressing).equals("MANUAL")) {
            editable = true;
        } else {
            editable = false;
        }
        return editable;

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
     * Sets the XDD value into java model.
     *
     * @param moduleObj Instance of Module.
     * @param moduleAddressing Instance of TModuleAdrressingHead.
     */
    public void setModuledata(Module moduleObj,
            TModuleAddressingHead moduleAddressing) {
        Object moduleModel = moduleObj.getModuleModel();
        this.moduleAddressing = moduleAddressing;
        if (moduleModel instanceof InterfaceList.Interface.Module) {
            moduleObjModel = (InterfaceList.Interface.Module) moduleModel;
        } else {
            moduleObjModel = null;
        }
    }

    @Override
    public void setPropertyValue(Object id, Object value) {
        Result res = new Result();
        try {
            if (id instanceof String) {
                String objectId = (String) id;
                switch (objectId) {
                    case IAbstractNodeProperties.MODULE_NAME_OBJECT: {
                        // TODO: update in library.

                        module.setName((String) value);

                        break;
                    }
                    case IAbstractNodeProperties.MODULE_POSITION_EDITABLE_OBJECT: {

                        int newPosition = Integer.valueOf((String) value);
                        int oldPosition = module.getPosition();
                        // if (isPositionAvailable(newPosition)) {
                        //
                        // // module.moveModule(newPosition, oldPosition);
                        // // module.setPosition((String) value);
                        // } else {
                        // module.setPosition((String) value);
                        // }
                        module.setPosition((String) value);
                        break;
                    }
                    case IAbstractNodeProperties.MODULE_ADDRESS_OBJECT: {
                        module.setAddress((String) value);
                        break;
                    }
                    default:
                        System.err.println("Invalid module object");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            module.getNode().getProject().refreshLocal(IResource.DEPTH_INFINITE,
                    new NullProgressMonitor());
        } catch (CoreException e) {
            System.err.println("unable to refresh the resource due to "
                    + e.getCause().getMessage());
        }

    }

}

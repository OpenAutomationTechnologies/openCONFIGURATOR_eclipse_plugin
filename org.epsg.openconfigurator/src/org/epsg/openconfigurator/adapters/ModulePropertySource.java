package org.epsg.openconfigurator.adapters;

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

public class ModulePropertySource extends AbstractNodePropertySource
        implements IPropertySource {

    private Module module;

    private TModuleAddressingHead moduleAddressing;

    private InterfaceList.Interface.Module moduleObjModel;

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
    }

    private void addControlledNodeModulePropertyDescriptors(
            List<IPropertyDescriptor> propertyList) {

        moduleAddressing = module.getInterfaceOfModule().getModuleAddressing();

        if (moduleObjModel == null) {
            return;
        }
        propertyList.add(moduleNameDescriptor);
        if (isPositionEditable()) {
            propertyList.add(modulePositionTextDescriptor);
        } else {
            propertyList.add(modulePositionDescriptor);
        }
        // propertyList.add(moduleAddressDescriptor);
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
                    case IAbstractNodeProperties.MODULE_ADDRESS_OBJECT: {
                        retObj = "";
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
                    return "Position cannot be empty.";
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
                        return "Only power supply module could be placed at position 1.";
                    }
                }

                if ((val == 0) || (val > module.getMaxPosition())) {
                    return "Invalid Position.";
                }

                if (val == module.getPosition()) {
                    return null;
                }

                for (Integer position : positionSet) {

                    if (position == val) {
                        return "Position already available";
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
                        return "Module name already available.";
                    }
                }
            }
            // Result res = OpenConfiguratorCore.GetInstance().SetNodeName(
            // cnNode.getNetworkId(), cnNode.getNodeId(), nodeName);
            // if (!res.IsSuccessful()) {
            // return OpenConfiguratorLibraryUtils.getErrorMessage(res);
            // }

        } else {
            System.err.println("handleSetNodeName: Invalid value type:" + name);
        }
        return null;
    }

    private boolean isPositionEditable() {
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
                        module.setPosition((String) value);
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

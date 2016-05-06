package org.epsg.openconfigurator.adapters;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.epsg.openconfigurator.lib.wrapper.NodeAssignment;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.model.IAbstractNodeProperties;
import org.epsg.openconfigurator.model.Module;
import org.epsg.openconfigurator.xmlbinding.projectfile.InterfaceList;

public class ModulePropertySource extends AbstractNodePropertySource
        implements IPropertySource {

    private Module module;

    private InterfaceList.Interface.Module moduleObjModel;

    public ModulePropertySource(Module moduleObj) {
        super();
        module = moduleObj;
        setModuledata(moduleObj);
    }

    private void addControlledNodeModulePropertyDescriptors(
            List<IPropertyDescriptor> propertyList) {
        if (moduleObjModel == null) {
            return;
        }

        propertyList.add(moduleNameDescriptor);
        propertyList.add(modulePositionDescriptor);
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
                    case IAbstractNodeProperties.MODULE_POSITION_OBJECT: {
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
                        int value = (moduleObjModel.isEnabled() == true) ? 0
                                : 1;
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

    public void setModuledata(Module moduleObj) {
        Object moduleModel = moduleObj.getModuleModel();

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

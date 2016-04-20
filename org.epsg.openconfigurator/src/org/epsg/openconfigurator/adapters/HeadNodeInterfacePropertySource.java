package org.epsg.openconfigurator.adapters;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.epsg.openconfigurator.console.OpenConfiguratorMessageConsole;
import org.epsg.openconfigurator.lib.wrapper.NodeAssignment;
import org.epsg.openconfigurator.model.HeadNodeInterface;
import org.epsg.openconfigurator.model.IAbstractNodeProperties;

public class HeadNodeInterfacePropertySource extends AbstractNodePropertySource
        implements IPropertySource {

    private HeadNodeInterface devModularInterface;

    public HeadNodeInterfacePropertySource(
            HeadNodeInterface deviceModularModel) {
        super();
        setInterfaceData(deviceModularModel);

    }

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
        List<IPropertyDescriptor> propertyList = new ArrayList<IPropertyDescriptor>();
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

                        retObj = devModularInterface.getMaxModules();
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
                        if (devModularInterface.isUnUsedSlots()) {
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

    public void setInterfaceData(final HeadNodeInterface deviceModularModel) {
        devModularInterface = deviceModularModel;
    }

    @Override
    public void setPropertyValue(Object id, Object value) {
        // TODO Auto-generated method stub

    }

}

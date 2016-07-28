/*******************************************************************************
 * @file   ObjectPropertySource.java
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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.epsg.openconfigurator.console.OpenConfiguratorMessageConsole;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.model.PowerlinkObject;
import org.epsg.openconfigurator.util.OpenConfiguratorLibraryUtils;
import org.epsg.openconfigurator.xmlbinding.xdd.TObjectAccessType;
import org.epsg.openconfigurator.xmlbinding.xdd.TParameterGroup;
import org.epsg.openconfigurator.xmlbinding.xdd.TParameterList;

/**
 * Describes the properties for a POWERLINK object.
 *
 * @see setObjectData
 * @author Ramakrishnan P
 *
 */
public class ObjectPropertySource extends AbstractObjectPropertySource
        implements IPropertySource {

    private PowerlinkObject plkObject;

    public ObjectPropertySource(final PowerlinkObject plkObject) {
        setObjectData(plkObject);

        objectIdDescriptor.setCategory(IPropertySourceSupport.BASIC_CATEGORY);

        nameDescriptor.setCategory(IPropertySourceSupport.BASIC_CATEGORY);
        objectTypeDescriptor
                .setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        objectTypeDescriptor
                .setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);

        dataTypeDescriptor.setCategory(IPropertySourceSupport.BASIC_CATEGORY);

        lowLimitDescriptor
                .setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        lowLimitDescriptor
                .setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);

        highLimitDescriptor
                .setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        highLimitDescriptor
                .setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);

        accessTypeDescriptor.setCategory(IPropertySourceSupport.BASIC_CATEGORY);

        defaultValueDescriptor
                .setCategory(IPropertySourceSupport.BASIC_CATEGORY);
        actualValueReadOnlyDescriptor
                .setCategory(IPropertySourceSupport.BASIC_CATEGORY);
        actualValueEditableDescriptor
                .setCategory(IPropertySourceSupport.BASIC_CATEGORY);
        actualValueEditableDescriptor.setValidator(new ICellEditorValidator() {

            @Override
            public String isValid(Object value) {

                return handleActualValue(value);
            }
        });

        forceActualValue.setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        forceActualValue
                .setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);
        forceActualValue.setValidator(new ICellEditorValidator() {

            @Override
            public String isValid(Object value) {

                return handleForceActualValue(value);
            }
        });

        denotationDescriptor
                .setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        denotationDescriptor
                .setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);

        pdoMappingDescriptor.setCategory(IPropertySourceSupport.BASIC_CATEGORY);
        objFlagsDescriptor
                .setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        objFlagsDescriptor
                .setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);

        uniqueIDRefDescriptor
                .setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        uniqueIDRefDescriptor
                .setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);

        objectErrorDescriptor
                .setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        objectErrorDescriptor
                .setFilterFlags(IPropertySourceSupport.EXPERT_FILTER_FLAG);
    }

    private void addObjectPropertyDescriptors(
            List<IPropertyDescriptor> propertyList) {

        propertyList.add(objectIdDescriptor);
        propertyList.add(nameDescriptor);
        propertyList.add(objectTypeDescriptor);

        if (plkObject.getDataType() != null) {
            propertyList.add(dataTypeDescriptor);
        }
        if (!plkObject.getLowLimit().isEmpty()) {
            propertyList.add(lowLimitDescriptor);
        }
        if (!plkObject.getHighLimit().isEmpty()) {
            propertyList.add(highLimitDescriptor);
        }
        if (plkObject.getAccessType() != null) {
            propertyList.add(accessTypeDescriptor);
        }

        if (!plkObject.getDefaultValue().isEmpty()) {
            propertyList.add(defaultValueDescriptor);
        }

        if (isActualValueEditable()) {
            propertyList.add(actualValueEditableDescriptor);
            propertyList.add(forceActualValue);
        } else {
            if (!plkObject.getActualValue().isEmpty()) {
                propertyList.add(actualValueReadOnlyDescriptor);
            }
        }

        if (plkObject.getDenotation() != null) {
            propertyList.add(denotationDescriptor);
        }

        if (plkObject.getPdoMapping() != null) {
            propertyList.add(pdoMappingDescriptor);
        }

        if (plkObject.getObjFlags() != null) {
            propertyList.add(objFlagsDescriptor);
        }

        if (plkObject.getUniqueIDRef() != null) {
            propertyList.add(uniqueIDRefDescriptor);
        }

        if (!plkObject.getConfigurationError().isEmpty()) {
            propertyList.add(objectErrorDescriptor);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.eclipse.ui.views.properties.IPropertySource#getEditableValue()
     */
    @Override
    public Object getEditableValue() {
        return plkObject;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.eclipse.ui.views.properties.IPropertySource#getPropertyDescriptors()
     */
    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        List<IPropertyDescriptor> propertyList = new ArrayList<IPropertyDescriptor>();
        addObjectPropertyDescriptors(propertyList);

        IPropertyDescriptor[] propertyDescriptorArray = {};
        propertyDescriptorArray = propertyList.toArray(propertyDescriptorArray);
        return propertyDescriptorArray;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java.
     * lang.Object)
     */
    @Override
    public Object getPropertyValue(Object id) {
        Object retObj = null;
        if (id instanceof String) {
            String objectId = (String) id;
            switch (objectId) {
                case OBJ_INDEX_ID:
                    if (plkObject.isModuleObject()) {
                        long objectIndex = OpenConfiguratorLibraryUtils
                                .getModuleObjectsIndex(plkObject.getModule(),
                                        plkObject.getId());
                        if (objectIndex != 0) {
                            retObj = plkObject.getModuleObjectID(objectIndex);
                        }
                    } else {
                        retObj = plkObject.getIdHex();
                    }
                    break;
                case OBJ_NAME_ID:
                    retObj = plkObject.getName();
                    break;
                case OBJ_TYPE_ID:
                    retObj = String.valueOf(plkObject.getObjectType());
                    break;
                case OBJ_DATATYPE_ID:
                    retObj = plkObject.getDataTypeReadable();
                    break;
                case OBJ_LOW_LIMIT_ID:
                    retObj = plkObject.getLowLimit();
                    break;
                case OBJ_HIGH_LIMIT_ID:
                    retObj = plkObject.getHighLimit();
                    break;
                case OBJ_ACCESS_TYPE_ID:
                    retObj = plkObject.getAccessType().value();
                    break;
                case OBJ_DEFAULT_VALUE_ID:
                    retObj = plkObject.getDefaultValue();
                    break;
                case OBJ_ACTUAL_VALUE_READ_ONLY_ID:
                case OBJ_ACTUAL_VALUE_EDITABLE_ID: //$FALL-THROUGH$
                    retObj = plkObject.getActualValue();
                    break;
                case OBJ_FORCE_ACTUAL_VALUE_ID:

                    int val = (plkObject.isObjectForced() == true) ? 0 : 1;
                    retObj = new Integer(val);

                    break;
                case OBJ_DENOTATION_ID:
                    retObj = plkObject.getDenotation();
                    break;
                case OBJ_PDO_MAPPING_ID:
                    retObj = plkObject.getPdoMapping().value();
                    break;
                case OBJ_OBJFLAGS_ID:
                    retObj = plkObject.getObjFlags();
                    break;
                case OBJ_UNIQUEIDREF_ID:
                    // Get value of unique Id reference value from the POWERLINK
                    // object model.
                    Object uniqueIdRef = plkObject.getUniqueIDRef();
                    if (uniqueIdRef instanceof TParameterList.Parameter) {
                        TParameterList.Parameter param = (TParameterList.Parameter) uniqueIdRef;
                        retObj = param.getUniqueID();
                    } else if (uniqueIdRef instanceof TParameterGroup) {
                        TParameterGroup paramGroup = (TParameterGroup) uniqueIdRef;
                        retObj = paramGroup.getUniqueID();
                    } else {
                        retObj = StringUtils.EMPTY;
                    }
                    break;
                case OBJ_ERROR_ID:
                    retObj = plkObject.getConfigurationError();
                    break;
                default:
                    break;
            }
        }
        return retObj;
    }

    /**
     * Handles the actual value modifications.
     *
     * @param value The value to be set.
     * @return Returns a string indicating whether the given value is valid;
     *         null means valid, and non-null means invalid, with the result
     *         being the error message to display to the end user.
     */
    protected String handleActualValue(Object value) {
        if (isModuleObject()) {
            long newObjectIndex = OpenConfiguratorLibraryUtils
                    .getModuleObjectsIndex(plkObject.getModule(),
                            plkObject.getId());
            Result res = OpenConfiguratorLibraryUtils
                    .validateModuleObjectActualValue(plkObject, (String) value,
                            newObjectIndex);
            if (!res.IsSuccessful()) {
                return OpenConfiguratorLibraryUtils.getErrorMessage(res);
            }
        } else {
            Result res = OpenConfiguratorLibraryUtils
                    .validateObjectActualValue(plkObject, (String) value);
            if (!res.IsSuccessful()) {
                return OpenConfiguratorLibraryUtils.getErrorMessage(res);
            }
        }
        return null;
    }

    /**
     * Handles the Force actual value modifications.
     *
     * @param value The value to be set.
     * @return Returns a string indicating whether the given value is valid;
     *         null means valid, and non-null means invalid, with the result
     *         being the error message to display to the end user.
     */
    protected String handleForceActualValue(Object value) {
        if (plkObject.getActualValue() == null) {
            return "Set some value in the actual value field.";
        } else if (plkObject.getActualValue() != null) {
            if (isModuleObject()) {
                long newObjectIndex = OpenConfiguratorLibraryUtils
                        .getModuleObjectsIndex(plkObject.getModule(),
                                plkObject.getId());
                Result res = OpenConfiguratorLibraryUtils
                        .validateForceObjectActualValue(plkObject,
                                newObjectIndex);
                if (!res.IsSuccessful()) {
                    return OpenConfiguratorLibraryUtils.getErrorMessage(res);
                }
            } else {
                Result res = OpenConfiguratorLibraryUtils
                        .validateForceObjectActualValue(plkObject);
                if (!res.IsSuccessful()) {
                    return OpenConfiguratorLibraryUtils.getErrorMessage(res);
                }
            }
        }

        return null;
    }

    private boolean isActualValueEditable() {
        boolean retVal = false;
        // Only VAR type is allowed to be edited.
        if ((plkObject.getObjectType() != 7)) {
            return retVal;
        }

        if (plkObject.getDataType() == null) {
            return retVal;
        }

        if (plkObject.getAccessType() == null) {
            return retVal;
        }
        String accessType = plkObject.getAccessType().value();
        // Only RW and WO types are allowed to be edited.
        if (!(accessType.equalsIgnoreCase(TObjectAccessType.WO.value())
                || accessType.equalsIgnoreCase(TObjectAccessType.RW.value()))) {
            return retVal;
        }

        retVal = true;
        return retVal;
    }

    /**
     * Verifies the object belonging to module.
     *
     * @return <true> if it is a module object, <false> if it is a object of
     *         node.
     */
    public boolean isModuleObject() {
        return plkObject.isModuleObject();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.eclipse.ui.views.properties.IPropertySource#isPropertySet(java.lang.
     * Object)
     */
    @Override
    public boolean isPropertySet(Object id) {
        return true;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.eclipse.ui.views.properties.IPropertySource#resetPropertyValue(java.
     * lang.Object)
     */
    @Override
    public void resetPropertyValue(Object id) {
        try {
            if (id instanceof String) {
                String objectId = (String) id;
                switch (objectId) {
                    case OBJ_ACTUAL_VALUE_EDITABLE_ID: {
                        String defaultValue = plkObject.getDefaultValue();
                        if (isModuleObject()) {
                            long newObjectIndex = OpenConfiguratorLibraryUtils
                                    .getModuleObjectsIndex(
                                            plkObject.getModule(),
                                            plkObject.getId());
                            Result res = OpenConfiguratorLibraryUtils
                                    .setModuleObjectActualValue(plkObject,
                                            defaultValue, newObjectIndex);
                            if (!res.IsSuccessful()) {
                                OpenConfiguratorMessageConsole.getInstance()
                                        .printLibraryErrorMessage(res);
                            } else {
                                // Success - update the OBD
                                plkObject.setActualValue(defaultValue, true);
                            }
                        } else {
                            Result res = OpenConfiguratorLibraryUtils
                                    .setObjectActualValue(plkObject,
                                            defaultValue);
                            if (!res.IsSuccessful()) {
                                OpenConfiguratorMessageConsole.getInstance()
                                        .printLibraryErrorMessage(res);
                            } else {
                                // Success - update the OBD
                                plkObject.setActualValue(defaultValue, true);
                            }
                        }

                        break;
                    }

                    default:
                        // others are not editable.
                }
            }

        } catch (Exception e) {
            OpenConfiguratorMessageConsole.getInstance().printErrorMessage(
                    e.getMessage(), plkObject.getNode().getNetworkId());
        }
    }

    /**
     * Sets the POWERLINK object instance
     *
     * @param adaptableObject Instance of PowerlinkObject
     */
    public void setObjectData(PowerlinkObject adaptableObject) {
        plkObject = adaptableObject;
    }

    /**
     * Sets the value to the Object Properties
     */
    @Override
    public void setPropertyValue(Object id, Object value) {

        try {
            if (id instanceof String) {
                String objectId = (String) id;
                switch (objectId) {
                    case OBJ_ACTUAL_VALUE_EDITABLE_ID: {
                        if (isModuleObject()) {
                            long newObjectIndex = OpenConfiguratorLibraryUtils
                                    .getModuleObjectsIndex(
                                            plkObject.getModule(),
                                            plkObject.getId());
                            Result res = OpenConfiguratorLibraryUtils
                                    .setModuleObjectActualValue(plkObject,
                                            (String) value, newObjectIndex);
                            if (!res.IsSuccessful()) {
                                OpenConfiguratorMessageConsole.getInstance()
                                        .printLibraryErrorMessage(res);
                            } else {
                                // Success - update the OBD
                                plkObject.setActualValue((String) value, true);
                            }
                        } else {
                            Result res = OpenConfiguratorLibraryUtils
                                    .setObjectActualValue(plkObject,
                                            (String) value);
                            if (!res.IsSuccessful()) {
                                OpenConfiguratorMessageConsole.getInstance()
                                        .printLibraryErrorMessage(res);
                            } else {
                                // Success - update the OBD
                                plkObject.setActualValue((String) value, true);
                            }
                        }
                        break;
                    }
                    case OBJ_FORCE_ACTUAL_VALUE_ID: {

                        if (value instanceof Integer) {
                            int val = ((Integer) value).intValue();
                            boolean result = (val == 0) ? true : false;
                            if (isModuleObject()) {
                                long newObjectIndex = OpenConfiguratorLibraryUtils
                                        .getModuleObjectsIndex(
                                                plkObject.getModule(),
                                                plkObject.getId());
                                Result res = OpenConfiguratorLibraryUtils
                                        .forceObject(plkObject, result,
                                                newObjectIndex);
                                if (!res.IsSuccessful()) {
                                    OpenConfiguratorMessageConsole.getInstance()
                                            .printLibraryErrorMessage(res);
                                } else {
                                    // Success - update the OBD
                                    plkObject.forceActualValue(result, true);
                                }
                            } else {
                                Result res = OpenConfiguratorLibraryUtils
                                        .forceObject(plkObject, result);
                                if (!res.IsSuccessful()) {
                                    OpenConfiguratorMessageConsole.getInstance()
                                            .printLibraryErrorMessage(res);
                                } else {
                                    // Success - update the OBD
                                    // plkObject.forceActualValue(result, true);
                                }
                            }
                        } else {
                            System.err.println("Invalid value type" + value);
                        }

                        break;
                    }
                    default:
                        // others are not editable.
                }
            }

        } catch (Exception e) {
            OpenConfiguratorMessageConsole.getInstance().printErrorMessage(
                    e.getMessage(), plkObject.getNode().getNetworkId());
        }

        try {
            plkObject.getProject().refreshLocal(IResource.DEPTH_INFINITE,
                    new NullProgressMonitor());
        } catch (CoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

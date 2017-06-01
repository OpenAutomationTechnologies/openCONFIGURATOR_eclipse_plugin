/*******************************************************************************
 * @file   SubObjectPropertySource.java
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

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySheetEntry;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.epsg.openconfigurator.console.OpenConfiguratorMessageConsole;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.model.PowerlinkSubobject;
import org.epsg.openconfigurator.util.OpenConfiguratorLibraryUtils;
import org.epsg.openconfigurator.xmlbinding.xdd.TObjectAccessType;
import org.epsg.openconfigurator.xmlbinding.xdd.TParameterGroup;
import org.epsg.openconfigurator.xmlbinding.xdd.TParameterList;

/**
 * Describes the properties for a POWERLINK sub-object.
 *
 * @see setObjectData
 *
 * @author Ramakrishnan P
 *
 */
public class SubObjectPropertySource extends AbstractObjectPropertySource
        implements IPropertySource {

    public static final String OBJ_SUB_INDEX_ID = "Obj.SubIndexId"; //$NON-NLS-1$
    public static final String OBJ_SUB_INDEX_LABEL = "Sub-Object ID"; //$NON-NLS-1$
    private static final PropertyDescriptor subObjectIdDescriptor = new PropertyDescriptor(
            OBJ_SUB_INDEX_ID, OBJ_SUB_INDEX_LABEL);
    private static final String[] EXPERT_FILTER_FLAG = {
            IPropertySheetEntry.FILTER_ID_EXPERT };

    static {
        subObjectIdDescriptor
                .setCategory(IPropertySourceSupport.BASIC_CATEGORY);
    }

    private PowerlinkSubobject plkSubObject;

    /**
     * Constructor that describes the property descriptors of POWERLINK
     * sub-object.
     *
     * @param plkSubObject Instance of PowerlinkSubobject
     */
    public SubObjectPropertySource(final PowerlinkSubobject plkSubObject) {
        setSubObjectData(plkSubObject);

        objectIdDescriptor.setCategory(IPropertySourceSupport.BASIC_CATEGORY);
        nameDescriptor.setCategory(IPropertySourceSupport.BASIC_CATEGORY);
        objectTypeDescriptor
                .setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        objectTypeDescriptor.setFilterFlags(EXPERT_FILTER_FLAG);

        dataTypeDescriptor.setCategory(IPropertySourceSupport.BASIC_CATEGORY);

        lowLimitDescriptor
                .setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        lowLimitDescriptor.setFilterFlags(EXPERT_FILTER_FLAG);

        highLimitDescriptor
                .setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        highLimitDescriptor.setFilterFlags(EXPERT_FILTER_FLAG);

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
        forceActualValue.setFilterFlags(EXPERT_FILTER_FLAG);
        forceActualValue.setValidator(new ICellEditorValidator() {

            @Override
            public String isValid(Object value) {
                return handleForceActualValue(value);
            }
        });

        denotationDescriptor
                .setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        denotationDescriptor.setFilterFlags(EXPERT_FILTER_FLAG);

        pdoMappingDescriptor.setCategory(IPropertySourceSupport.BASIC_CATEGORY);
        objFlagsDescriptor
                .setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        objFlagsDescriptor.setFilterFlags(EXPERT_FILTER_FLAG);

        uniqueIDRefDescriptor
                .setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        uniqueIDRefDescriptor.setFilterFlags(EXPERT_FILTER_FLAG);

        objectErrorDescriptor
                .setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        objectErrorDescriptor.setFilterFlags(EXPERT_FILTER_FLAG);
    }

    private void addSubObjectPropertyDescriptors(
            List<IPropertyDescriptor> propertyList) {

        propertyList.add(objectIdDescriptor);
        propertyList.add(subObjectIdDescriptor);
        propertyList.add(nameDescriptor);
        propertyList.add(objectTypeDescriptor);

        if (plkSubObject.getDataType() != null) {
            propertyList.add(dataTypeDescriptor);
        }

        if (!plkSubObject.getLowLimit().isEmpty()) {
            propertyList.add(lowLimitDescriptor);
        }

        if (!plkSubObject.getHighLimit().isEmpty()) {
            propertyList.add(highLimitDescriptor);
        }

        if (plkSubObject.getAccessType() != null) {
            propertyList.add(accessTypeDescriptor);
        }

        if (!plkSubObject.getDefaultValue().isEmpty()) {
            propertyList.add(defaultValueDescriptor);
        }

        if (isActualValueEditable()) {
            propertyList.add(actualValueEditableDescriptor);
            propertyList.add(forceActualValue);
        } else {
            if (!plkSubObject.getActualValue().isEmpty()) {
                propertyList.add(actualValueReadOnlyDescriptor);
            }
        }

        if (plkSubObject.getDenotation() != null) {
            propertyList.add(denotationDescriptor);
        }

        if (plkSubObject.getPdoMappingObject() != null) {
            propertyList.add(pdoMappingDescriptor);
        }

        if (plkSubObject.getObjFlags() != null) {
            propertyList.add(objFlagsDescriptor);
        }

        if (plkSubObject.getUniqueIDRef() != null) {
            propertyList.add(uniqueIDRefDescriptor);
        }

        if (!plkSubObject.getConfigurationError().isEmpty()) {
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
        return plkSubObject;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.eclipse.ui.views.properties.IPropertySource#getPropertyDescriptors()
     */
    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        List<IPropertyDescriptor> propertyList = new ArrayList<>();
        addSubObjectPropertyDescriptors(propertyList);

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
                    if (plkSubObject.getObject().isModuleObject()) {
                        long objectIndex = OpenConfiguratorLibraryUtils
                                .getModuleObjectsIndex(
                                        plkSubObject.getObject().getModule(),
                                        plkSubObject.getObject().getId());
                        if (objectIndex != 0) {
                            retObj = plkSubObject.getObject()
                                    .getModuleObjectID(objectIndex);
                        }
                    } else {
                        retObj = plkSubObject.getObject().getIdHex();
                    }
                    break;
                case OBJ_SUB_INDEX_ID:
                    if (plkSubObject.isModule()) {
                        int subObjectIndex = OpenConfiguratorLibraryUtils
                                .getModuleObjectsSubIndex(
                                        plkSubObject.getModule(), plkSubObject,
                                        plkSubObject.getObject().getId());

                        retObj = plkSubObject
                                .getModuleSubObjectID(subObjectIndex);

                    } else {
                        retObj = plkSubObject.getIdHex();
                    }
                    break;
                case OBJ_NAME_ID:
                    retObj = plkSubObject.getName();
                    break;
                case OBJ_TYPE_ID:
                    retObj = String.valueOf(plkSubObject.getObjectType());
                    break;
                case OBJ_DATATYPE_ID:
                    retObj = plkSubObject.getDataTypeReadable();
                    break;
                case OBJ_LOW_LIMIT_ID:
                    retObj = plkSubObject.getLowLimit();
                    break;
                case OBJ_HIGH_LIMIT_ID:
                    retObj = plkSubObject.getHighLimit();
                    break;
                case OBJ_ACCESS_TYPE_ID:
                    retObj = plkSubObject.getAccessType().value();
                    break;
                case OBJ_DEFAULT_VALUE_ID:
                    retObj = plkSubObject.getDefaultValue();
                    break;
                case OBJ_ACTUAL_VALUE_READ_ONLY_ID:
                case OBJ_ACTUAL_VALUE_EDITABLE_ID: //$FALL-THROUGH$
                    retObj = plkSubObject.getActualValue();
                    break;
                case OBJ_FORCE_ACTUAL_VALUE_ID:
                    if (isModuleSubObject()) {
                        long newObjectIndex = OpenConfiguratorLibraryUtils
                                .getModuleObjectsIndex(plkSubObject.getModule(),
                                        plkSubObject.getObject().getId());
                        int newSubObjectIndex = OpenConfiguratorLibraryUtils
                                .getModuleObjectsSubIndex(
                                        plkSubObject.getModule(), plkSubObject,
                                        plkSubObject.getObject().getId());
                        int val = (plkSubObject.isModuleObjectForced(
                                newObjectIndex, newSubObjectIndex) == true) ? 0
                                        : 1;
                        retObj = Integer.valueOf(val);
                    } else {
                        int val = (plkSubObject.isObjectForced() == true) ? 0
                                : 1;
                        retObj = Integer.valueOf(val);
                    }
                    break;
                case OBJ_DENOTATION_ID:
                    retObj = plkSubObject.getDenotation();
                    break;
                case OBJ_PDO_MAPPING_ID:
                    retObj = plkSubObject.getPdoMappingObject().value();
                    break;
                case OBJ_OBJFLAGS_ID:
                    retObj = plkSubObject.getObjFlags();
                    break;
                case OBJ_UNIQUEIDREF_ID:
                    // Get value of unique Id reference value from the POWERLINK
                    // sub-object model.
                    Object uniqueIdRef = plkSubObject.getUniqueIDRef();
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
                    retObj = plkSubObject.getConfigurationError();
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
        if (isModuleSubObject()) {
            long newObjectIndex = OpenConfiguratorLibraryUtils
                    .getModuleObjectsIndex(plkSubObject.getModule(),
                            plkSubObject.getObject().getId());
            int newSubObjectIndex = OpenConfiguratorLibraryUtils
                    .getModuleObjectsSubIndex(plkSubObject.getModule(),
                            plkSubObject, plkSubObject.getObject().getId());
            Result res = OpenConfiguratorLibraryUtils
                    .validateModuleSubobjectActualValue(plkSubObject,
                            (String) value, newObjectIndex, newSubObjectIndex);
            if (!res.IsSuccessful()) {
                return OpenConfiguratorLibraryUtils.getErrorMessage(res);
            }
        } else {
            Result res = OpenConfiguratorLibraryUtils
                    .validateSubobjectActualValue(plkSubObject, (String) value);
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
        if (plkSubObject.getActualValue().isEmpty()) {
            return "Set some value in the actual value field.";
        }
        if (isModuleSubObject()) {
            long newObjectIndex = OpenConfiguratorLibraryUtils
                    .getModuleObjectsIndex(plkSubObject.getModule(),
                            plkSubObject.getObject().getId());
            int newSubObjectIndex = OpenConfiguratorLibraryUtils
                    .getModuleObjectSubIndex(plkSubObject.getModule(),
                            plkSubObject);
            Result res = OpenConfiguratorLibraryUtils
                    .validateForceSubObjectActualValue(plkSubObject,
                            newObjectIndex, newSubObjectIndex);
            if (!res.IsSuccessful()) {
                return OpenConfiguratorLibraryUtils.getErrorMessage(res);
            }
        } else {
            Result res = OpenConfiguratorLibraryUtils
                    .validateForceSubObjectActualValue(plkSubObject);
            if (!res.IsSuccessful()) {
                return OpenConfiguratorLibraryUtils.getErrorMessage(res);
            }
        }
        return null;
    }

    private boolean isActualValueEditable() {
        boolean retVal = false;
        // Only VAR type is allowed to be edited.
        if ((plkSubObject.getObjectType() != 7)) {
            return retVal;
        }

        if (plkSubObject.getDataType() == null) {
            return retVal;
        }

        if (plkSubObject.getAccessType() == null) {
            return retVal;
        }

        String accessType = plkSubObject.getAccessType().value();
        // Only RW and WO types are allowed to be edited.
        if (!(accessType.equalsIgnoreCase(TObjectAccessType.WO.value())
                || accessType.equalsIgnoreCase(TObjectAccessType.RW.value()))) {
            return retVal;
        }

        retVal = true;
        return retVal;
    }

    /**
     * Verifies the subObject with respect to module and controlled node.
     *
     * @return <true> if module sub-object, <false> if sub-object of node.
     */
    public boolean isModuleSubObject() {
        return plkSubObject.isModule();
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
        try {

            if (id instanceof String) {
                String objectId = (String) id;
                switch (objectId) {
                    case OBJ_ACTUAL_VALUE_EDITABLE_ID: {
                        String defaultValue = plkSubObject.getDefaultValue();
                        Result res = OpenConfiguratorLibraryUtils
                                .setSubObjectActualValue(plkSubObject,
                                        defaultValue);
                        if (!res.IsSuccessful()) {
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printLibraryErrorMessage(res);
                        } else {
                            // Success - update the OBD
                            plkSubObject.setActualValue(defaultValue, true);
                        }
                        break;
                    }

                    default:
                        // others are not editable.
                        break;
                }
            }

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            OpenConfiguratorMessageConsole.getInstance().printErrorMessage(
                    e.getMessage(),
                    plkSubObject.getNode().getProject().getName());
        }
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
                        String defaultValue = plkSubObject.getDefaultValue();
                        if (isModuleSubObject()) {
                            long newObjectIndex = OpenConfiguratorLibraryUtils
                                    .getModuleObjectsIndex(
                                            plkSubObject.getModule(),
                                            plkSubObject.getObject().getId());
                            int newSubObjectIndex = OpenConfiguratorLibraryUtils
                                    .getModuleObjectSubIndex(
                                            plkSubObject.getModule(),
                                            plkSubObject);
                            Result res = OpenConfiguratorLibraryUtils
                                    .setModuleSubObjectActualValue(plkSubObject,
                                            defaultValue, newObjectIndex,
                                            newSubObjectIndex);
                            if (!res.IsSuccessful()) {
                                OpenConfiguratorMessageConsole.getInstance()
                                        .printLibraryErrorMessage(res);
                            } else {
                                // Success - update the OBD
                                plkSubObject.setActualValue(defaultValue, true);
                            }
                        } else {
                            Result res = OpenConfiguratorLibraryUtils
                                    .setSubObjectActualValue(plkSubObject,
                                            defaultValue);
                            if (!res.IsSuccessful()) {
                                OpenConfiguratorMessageConsole.getInstance()
                                        .printLibraryErrorMessage(res);
                            } else {
                                // Success - update the OBD
                                plkSubObject.setActualValue(defaultValue, true);
                            }
                        }
                        break;
                    }

                    default:
                        // others are not editable.
                        break;
                }
            }

        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            OpenConfiguratorMessageConsole.getInstance().printErrorMessage(
                    e.getMessage(),
                    plkSubObject.getNode().getProject().getName());
        }
    }

    /**
     * sets the value to the Sub-Object Properties
     */
    @Override
    public void setPropertyValue(Object id, Object value) {
        try {

            if (id instanceof String) {
                String objectId = (String) id;
                switch (objectId) {
                    case OBJ_ACTUAL_VALUE_EDITABLE_ID: {
                        if (isModuleSubObject()) {
                            long newObjectIndex = OpenConfiguratorLibraryUtils
                                    .getModuleObjectsIndex(
                                            plkSubObject.getModule(),
                                            plkSubObject.getObject().getId());
                            int newSubObjectIndex = OpenConfiguratorLibraryUtils
                                    .getModuleObjectsSubIndex(
                                            plkSubObject.getModule(),
                                            plkSubObject,
                                            plkSubObject.getObject().getId());
                            Result res = OpenConfiguratorLibraryUtils
                                    .setModuleSubObjectActualValue(plkSubObject,
                                            (String) value, newObjectIndex,
                                            newSubObjectIndex);
                            if (!res.IsSuccessful()) {
                                OpenConfiguratorMessageConsole.getInstance()
                                        .printLibraryErrorMessage(res);
                            } else {
                                // Success - update the OBD
                                plkSubObject.setActualValue((String) value,
                                        true, isModuleSubObject());
                            }
                        } else {
                            Result res = OpenConfiguratorLibraryUtils
                                    .setSubObjectActualValue(plkSubObject,
                                            (String) value);
                            if (!res.IsSuccessful()) {
                                OpenConfiguratorMessageConsole.getInstance()
                                        .printLibraryErrorMessage(res);
                            } else {
                                // Success - update the OBD
                                plkSubObject.setActualValue((String) value,
                                        true);
                            }
                        }

                        break;
                    }
                    case OBJ_FORCE_ACTUAL_VALUE_ID: {
                        if (value instanceof Integer) {
                            int val = ((Integer) value).intValue();
                            boolean result = (val == 0) ? true : false;

                            if (isModuleSubObject()) {
                                long newObjectIndex = OpenConfiguratorLibraryUtils
                                        .getModuleObjectsIndex(
                                                plkSubObject.getModule(),
                                                plkSubObject.getObject()
                                                        .getId());
                                int newSubObjectIndex = OpenConfiguratorLibraryUtils
                                        .getModuleObjectSubIndex(
                                                plkSubObject.getModule(),
                                                plkSubObject);
                                Result res = OpenConfiguratorLibraryUtils
                                        .forceSubObject(plkSubObject, result,
                                                newObjectIndex,
                                                newSubObjectIndex);
                                if (!res.IsSuccessful()) {
                                    OpenConfiguratorMessageConsole.getInstance()
                                            .printLibraryErrorMessage(res);
                                } else {
                                    // Success - update the OBD
                                    plkSubObject.forceActualValue(
                                            plkSubObject.getModule(), result,
                                            true, newObjectIndex,
                                            newSubObjectIndex);
                                }
                            } else {
                                Result res = OpenConfiguratorLibraryUtils
                                        .forceSubObject(plkSubObject, result);
                                if (!res.IsSuccessful()) {
                                    OpenConfiguratorMessageConsole.getInstance()
                                            .printLibraryErrorMessage(res);
                                } else {
                                    // Success - update the OBD
                                    plkSubObject.forceActualValue(result, true);
                                }
                            }
                        } else {
                            System.err.println("Invalid value type");
                        }
                        break;
                    }
                    default:
                        // others are not editable.
                        break;
                }
            }

        } catch (RuntimeException e) {
            // RunTimeException is caught whenever an exception is caught during
            // run time to avoid unnecessary caught of exceptions.
            throw e;
        } catch (Exception e) {
            System.err.println("The proerty of sub object.." + e.getMessage());
            OpenConfiguratorMessageConsole.getInstance().printErrorMessage(
                    e.getMessage(),
                    plkSubObject.getNode().getProject().getName());
        }

        try {
            plkSubObject.getProject().refreshLocal(IResource.DEPTH_INFINITE,
                    new NullProgressMonitor());
        } catch (CoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Sets the Instance of PowerlinkSubobject
     *
     * @param adaptableObject Instance of PowerlinkSubobject.
     */
    public void setSubObjectData(PowerlinkSubobject adaptableObject) {
        plkSubObject = adaptableObject;
    }
}

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

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.epsg.openconfigurator.console.OpenConfiguratorMessageConsole;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.model.PowerlinkSubobject;
import org.epsg.openconfigurator.util.OpenConfiguratorLibraryUtils;
import org.epsg.openconfigurator.xmlbinding.xdd.TObject.SubObject;
import org.epsg.openconfigurator.xmlbinding.xdd.TObjectAccessType;
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

    static {
        subObjectIdDescriptor
                .setCategory(IPropertySourceSupport.BASIC_CATEGORY);
    }

    private SubObject subObject;
    private PowerlinkSubobject plkSubObject;

    public SubObjectPropertySource(final PowerlinkSubobject plkSubObject) {
        setSubObjectData(plkSubObject);

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
    }

    private void addSubObjectPropertyDescriptors(
            List<IPropertyDescriptor> propertyList) {

        propertyList.add(objectIdDescriptor);
        propertyList.add(subObjectIdDescriptor);
        propertyList.add(nameDescriptor);
        propertyList.add(objectTypeDescriptor);

        if (subObject.getDataType() != null) {
            propertyList.add(dataTypeDescriptor);
        }
        if (subObject.getLowLimit() != null) {
            propertyList.add(lowLimitDescriptor);
        }
        if (subObject.getHighLimit() != null) {
            propertyList.add(highLimitDescriptor);
        }
        if (subObject.getAccessType() != null) {
            propertyList.add(accessTypeDescriptor);
        }

        if (subObject.getDefaultValue() != null) {
            propertyList.add(defaultValueDescriptor);
        }

        if (isActualValueEditable()) {
            propertyList.add(actualValueEditableDescriptor);
            propertyList.add(forceActualValue);
        } else {
            if (subObject.getActualValue() != null) {
                propertyList.add(actualValueReadOnlyDescriptor);
            }
        }

        if (subObject.getDenotation() != null) {
            propertyList.add(denotationDescriptor);
        }

        if (subObject.getPDOmapping() != null) {
            propertyList.add(pdoMappingDescriptor);
        }
        if (subObject.getObjFlags() != null) {
            propertyList.add(objFlagsDescriptor);
        }
        if (subObject.getUniqueIDRef() != null) {
            propertyList.add(uniqueIDRefDescriptor);
        }
    }

    @Override
    public Object getEditableValue() {
        return subObject;
    }

    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        List<IPropertyDescriptor> propertyList = new ArrayList<IPropertyDescriptor>();
        addSubObjectPropertyDescriptors(propertyList);

        IPropertyDescriptor[] propertyDescriptorArray = {};
        propertyDescriptorArray = propertyList.toArray(propertyDescriptorArray);
        return propertyDescriptorArray;
    }

    @Override
    public Object getPropertyValue(Object id) {
        Object retObj = null;
        if (id instanceof String) {
            String objectId = (String) id;
            switch (objectId) {
                case OBJ_INDEX_ID:
                    retObj = plkSubObject.getObject().getIdHex();
                    break;
                case OBJ_SUB_INDEX_ID:
                    retObj = plkSubObject.getIdHex();
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

                    int val = (plkSubObject.isObjectForced() == true) ? 0 : 1;
                    retObj = new Integer(val);
                    break;
                case OBJ_DENOTATION_ID:
                    retObj = subObject.getDenotation();
                    break;
                case OBJ_PDO_MAPPING_ID:
                    retObj = plkSubObject.getPdoMapping().value();
                    break;
                case OBJ_OBJFLAGS_ID:
                    retObj = subObject.getObjFlags();
                    break;
                case OBJ_UNIQUEIDREF_ID:
                    // Get value of unique Id reference value from the POWERLINK
                    // sub-object model.
                    Object uniqueIdRef = plkSubObject.getUniqueIDRef();
                    if (uniqueIdRef instanceof TParameterList.Parameter) {
                        TParameterList.Parameter param = (TParameterList.Parameter) uniqueIdRef;
                        retObj = param.getUniqueID();
                    } else {
                        retObj = StringUtils.EMPTY;
                    }
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
        Result res = OpenConfiguratorLibraryUtils
                .validateSubobjectActualValue(plkSubObject, (String) value);
        if (!res.IsSuccessful()) {
            return OpenConfiguratorLibraryUtils.getErrorMessage(res);
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
        if (plkSubObject.getActualValue() == null) {
            return "Set some value in the actual value field.";
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
        if ((subObject.getObjectType() != 7)) {
            return retVal;
        }

        if (subObject.getDataType() == null) {
            return retVal;
        }

        if (subObject.getAccessType() == null) {
            return retVal;
        }
        String accessType = subObject.getAccessType().value();
        // Only RW and WO types are allowed to be edited.
        if (!(accessType.equalsIgnoreCase(TObjectAccessType.WO.value())
                || accessType.equalsIgnoreCase(TObjectAccessType.RW.value()))) {
            return retVal;
        }

        retVal = true;
        return retVal;
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
     * sets the value to the Sub-Object Properties
     */
    @Override
    public void setPropertyValue(Object id, Object value) {
        try {

            if (id instanceof String) {
                String objectId = (String) id;
                switch (objectId) {
                    case OBJ_ACTUAL_VALUE_EDITABLE_ID: {
                        Result res = OpenConfiguratorLibraryUtils
                                .setSubObjectActualValue(plkSubObject,
                                        (String) value);
                        if (!res.IsSuccessful()) {
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printLibraryErrorMessage(res);
                        } else {
                            // Success - update the OBD
                            plkSubObject.setActualValue((String) value, true);
                        }
                        break;
                    }
                    case OBJ_FORCE_ACTUAL_VALUE_ID: {
                        if (value instanceof Integer) {
                            int val = ((Integer) value).intValue();
                            boolean result = (val == 0) ? true : false;

                            Result res = OpenConfiguratorLibraryUtils
                                    .forceSubObject(plkSubObject, result);
                            if (!res.IsSuccessful()) {
                                OpenConfiguratorMessageConsole.getInstance()
                                        .printLibraryErrorMessage(res);
                            } else {
                                // Success - update the OBD
                                plkSubObject.forceActualValue(result, true);
                            }
                        } else {
                            System.err.println("Invalid value type");
                        }
                        break;
                    }
                    default:
                        // others are not editable.
                }
            }

        } catch (Exception e) {
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

    public void setSubObjectData(PowerlinkSubobject adaptableObject) {
        plkSubObject = adaptableObject;
        subObject = plkSubObject.getModel();
    }
}

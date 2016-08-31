/*******************************************************************************
 * @file   ParameterRefPropertySource.java
 *
 * @author Ramakrishnan Periyakaruppan, Kalycito Infotech Private Limited.
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySheetEntry;
import org.eclipse.ui.views.properties.IPropertySource;
import org.epsg.openconfigurator.console.OpenConfiguratorMessageConsole;
import org.epsg.openconfigurator.lib.wrapper.OpenConfiguratorCore;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.model.AllowedValues;
import org.epsg.openconfigurator.model.DataTypeChoice;
import org.epsg.openconfigurator.model.Parameter;
import org.epsg.openconfigurator.model.Parameter.ParameterAccess;
import org.epsg.openconfigurator.model.ParameterReference;
import org.epsg.openconfigurator.model.Range;
import org.epsg.openconfigurator.util.OpenConfiguratorLibraryUtils;
import org.jdom2.JDOMException;

/**
 * Describes the parameter reference property source.
 *
 * @author Ramakrishnan P
 *
 */
public class ParameterRefPropertySource extends AbstractParameterPropertySource
        implements IPropertySource {

    private static final String[] EXPERT_FILTER_FLAG = {
            IPropertySheetEntry.FILTER_ID_EXPERT };
    private ParameterReference paramRef;

    private String[] ALLOWED_VALUES;
    private ComboBoxPropertyDescriptor allowedValueDescriptor;

    /**
     * Parameter reference constructor to set the model data fro parameter
     * reference.
     *
     * @param paramRef The Instance of parameter reference
     */
    public ParameterRefPropertySource(final ParameterReference paramRef) {
        setModelData(paramRef);

        actualValueTextDescriptor.setValidator(new ICellEditorValidator() {

            @Override
            public String isValid(Object value) {
                return handleParameterReferenceActualValue(value);

            }

        });
        nameDescriptor.setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        nameDescriptor.setFilterFlags(EXPERT_FILTER_FLAG);
        accessTypeDescriptor
                .setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        accessTypeDescriptor.setFilterFlags(EXPERT_FILTER_FLAG);

        dataTypeDescriptor.setCategory(IPropertySourceSupport.BASIC_CATEGORY);
        defaultValueDescriptor
                .setCategory(IPropertySourceSupport.BASIC_CATEGORY);
        actualValueReadOnlyDescriptor
                .setCategory(IPropertySourceSupport.BASIC_CATEGORY);
        actualValueTextDescriptor
                .setCategory(IPropertySourceSupport.BASIC_CATEGORY);
        unitDescriptor.setCategory(IPropertySourceSupport.BASIC_CATEGORY);

        bitOffsetDescriptor
                .setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        bitOffsetDescriptor.setFilterFlags(EXPERT_FILTER_FLAG);
        uniqueIdDescriptor
                .setCategory(IPropertySourceSupport.ADVANCED_CATEGORY);
        uniqueIdDescriptor.setFilterFlags(EXPERT_FILTER_FLAG);
    }

    // Property descriptor to be displayed in the property page.
    private void addPropertyDescriptors(
            List<IPropertyDescriptor> propertyList) {
        propertyList.add(uniqueIdDescriptor);
        propertyList.add(nameDescriptor);
        propertyList.add(accessTypeDescriptor);

        setAllowedValues();

        allowedValueDescriptor = new ComboBoxPropertyDescriptor(
                PARAM_ACTUAL_VALUE_ALLOWED_VALUE_ID, PARAM_ACTUAL_VALUE_LABEL,
                ALLOWED_VALUES);
        allowedValueDescriptor
                .setCategory(IPropertySourceSupport.BASIC_CATEGORY);

        propertyList.add(dataTypeDescriptor);

        if (paramRef.getDefaultValue() != null) {
            propertyList.add(defaultValueDescriptor);
        }

        if (paramRef.isLocked()) {
            propertyList.add(actualValueReadOnlyDescriptor);
        } else {
            Parameter param = paramRef.getObjectDictionary()
                    .getParameter(paramRef.getUniqueId());
            ParameterAccess access = param.getAccess();
            if ((access == ParameterAccess.CONST)
                    || (access == ParameterAccess.NO_ACCESS)
                    || (access == ParameterAccess.READ)
                    || (access == ParameterAccess.UNDEFINED)) {
                propertyList.add(actualValueReadOnlyDescriptor);
            } else if ((paramRef.getAllowedValues().getValuesList() != null)
                    && !paramRef.getAllowedValues().getValuesList().isEmpty()) {
                propertyList.add(allowedValueDescriptor);
            } else {
                propertyList.add(actualValueTextDescriptor);
            }
        }

        if (paramRef.getUnitLabel() != null) {
            propertyList.add(unitDescriptor);
        }

        if (paramRef.getBitOffset() != null) {
            propertyList.add(bitOffsetDescriptor);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.eclipse.ui.views.properties.IPropertySource#getEditableValue()
     */
    @Override
    public Object getEditableValue() {
        return paramRef;
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
        addPropertyDescriptors(propertyList);

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
                case UNIQUE_ID:
                    retObj = paramRef.getUniqueId();
                    break;
                case PARAM_NAME_ID:
                    retObj = paramRef.getLabelDescription().getText();
                    break;
                case PARAM_DATATYPE_ID:
                    DataTypeChoice dtChoice = paramRef.getDataType();
                    if (dtChoice != null) {
                        switch (dtChoice.getChoiceType()) {
                            case SIMPLE:
                                retObj = dtChoice.getSimpleDataType()
                                        .toString();
                                break;
                            case STRUCT:
                                retObj = dtChoice.getStructDataType()
                                        .getUniqueId();
                                break;
                            case ARRAY:
                            case UNDEFINED:
                            default:
                                retObj = StringUtils.EMPTY;
                                break;
                        }
                    } else {
                        retObj = StringUtils.EMPTY;
                    }
                    break;
                case PARAM_ACCESS_TYPE_ID:
                    if (paramRef.getAccess() != null) {
                        retObj = paramRef.getAccess().toString();
                    }
                    break;
                case PARAM_DEFAULT_VALUE_ID:
                    retObj = paramRef.getDefaultValue();
                    break;
                case PARAM_ACTUAL_VALUE_ALLOWED_VALUE_ID: {
                    String actualValue = paramRef.getActualValue();
                    if (actualValue != null) {
                        for (int i = 0; i < ALLOWED_VALUES.length; i++) {
                            if (ALLOWED_VALUES[i].equals(actualValue)) {
                                System.err.println(
                                        "Get property value : actual value "
                                                + actualValue);
                                retObj = Integer.valueOf(i);
                                return retObj;
                            }
                        }
                    } else {

                        String val = ALLOWED_VALUES[0];
                        retObj = Integer.valueOf(0);
                        try {
                            paramRef.setActualValue(val);
                        } catch (JDOMException | IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                    }
                }
                    break;
                case PARAM_ACTUAL_VALUE_ID:
                case PARAM_ACTUAL_VALUE_READ_ONLY_ID:
                    if (paramRef.getActualValue() != null) {
                        retObj = paramRef.getActualValue();
                    } else {
                        retObj = StringUtils.EMPTY;
                    }
                    break;
                case PARAM_UNIT_ID:
                    retObj = paramRef.getUnitLabel().getText();
                    break;
                case PARAM_BIT_OFFSET_ID:
                    retObj = paramRef.getBitOffset().toString();
                    break;
                default:
                    System.err.println("Not supported!");
                    break;
            }
        }
        return retObj;
    }

    /**
     * Handles or validates the actual value of parameter reference on following
     * conditions.
     *
     * @param value The value given to be validated
     * @return The error statement for invalid actual value.
     */
    private String handleParameterReferenceActualValue(Object value) {
        String parameterRefUniqueID = paramRef.getUniqueId();
        Parameter parameter = paramRef.getObjectDictionary()
                .getParameter(parameterRefUniqueID);
        String actualvalue = (String) value;
        if (isModuleParameter()) {
            String newParameterName = OpenConfiguratorLibraryUtils
                    .getModuleParameterUniqueID(
                            paramRef.getObjectDictionary().getModule(),
                            paramRef.getUniqueId());
            Result res = OpenConfiguratorCore.GetInstance()
                    .SetParameterActualValue(paramRef.getNode().getNetworkId(),
                            paramRef.getNode().getNodeId(), newParameterName,
                            actualvalue);
            if (!res.IsSuccessful()) {
                return OpenConfiguratorLibraryUtils.getErrorMessage(res);
            }
        } else {
            Result res = OpenConfiguratorCore.GetInstance()
                    .SetParameterActualValue(paramRef.getNode().getNetworkId(),
                            paramRef.getNode().getNodeId(),
                            parameter.getUniqueId(), actualvalue);
            if (!res.IsSuccessful()) {
                return OpenConfiguratorLibraryUtils.getErrorMessage(res);
            }
        }
        try {
            if ((paramRef.getActualValue() != null)) {

                List<Range> rangeList = parameter.getRangeList();
                if (rangeList != null) {
                    for (Range range : rangeList) {
                        if (value instanceof String) {
                            String val = (String) value;
                            try {
                                int maxValue = Integer
                                        .parseInt(range.getMaxValue());

                                int minValue = Integer
                                        .parseInt(range.getMinValue());

                                if (Integer.parseInt(val) < minValue) {
                                    return "Actual value (" + val
                                            + ") does not fit within the range ("
                                            + minValue + " to " + maxValue
                                            + ")";
                                }
                                if (Integer.parseInt(val) > maxValue) {
                                    return "Actual value (" + val
                                            + ") does not fit within the range ("
                                            + minValue + " to " + maxValue
                                            + ")";
                                }
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                                return "Invalid actual value.";
                            }
                        }
                    }
                }

            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "Invalid actual value.";
        }

        return null;

    }

    public boolean isModuleParameter() {
        return paramRef.getObjectDictionary().isModule();
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
        // TODO Auto-generated method stub
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
        if (id instanceof String) {
            String objectId = (String) id;

            switch (objectId) {
                case PARAM_ACTUAL_VALUE_ALLOWED_VALUE_ID:
                case PARAM_ACTUAL_VALUE_ID:
                    try {
                        String defaultValue = paramRef.getDefaultValue();
                        String newParameterName = OpenConfiguratorLibraryUtils
                                .getModuleParameterUniqueID(
                                        paramRef.getObjectDictionary()
                                                .getModule(),
                                        paramRef.getUniqueId());
                        Result res = OpenConfiguratorCore.GetInstance()
                                .SetParameterActualValue(
                                        paramRef.getNode().getNetworkId(),
                                        paramRef.getNode().getNodeId(),
                                        newParameterName, defaultValue);
                        if (!res.IsSuccessful()) {
                            System.err.println(OpenConfiguratorLibraryUtils
                                    .getErrorMessage(res));
                        } else {
                            paramRef.setActualValue(defaultValue);
                        }
                    } catch (JDOMException | IOException e) {
                        OpenConfiguratorMessageConsole.getInstance()
                                .printErrorMessage(e.getCause().getMessage(),
                                        paramRef.getNode().getNetworkId());
                        e.printStackTrace();
                    }
                    break;

                default:
                    System.err.println(id + " not supported!");
                    break;
            }
        }
        System.err.println("Reset property value....");
    }

    private void setAllowedValues() {
        AllowedValues allowedValue = paramRef.getAllowedValues();
        List<String> values = allowedValue.getValuesList();
        List<String> valu = new ArrayList<>();
        for (String value : values) {
            if (value.contains("�")) {
                value = value.replace("�", "");
            }
            valu.add(value);
        }
        String[] val = valu.toArray(new String[0]);

        ALLOWED_VALUES = val;

    }

    /**
     * Set the parameter reference model data to the property source.
     *
     * @param paramRef
     */
    void setModelData(ParameterReference paramRef) {
        this.paramRef = paramRef;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public void setPropertyValue(Object id, Object value) {

        if (id instanceof String) {
            String objectId = (String) id;
            Result res = new Result();
            switch (objectId) {
                case PARAM_ACTUAL_VALUE_ID:
                    try {
                        String actualValue = (String) value;
                        if (isModuleParameter()) {
                            String newParameterName = OpenConfiguratorLibraryUtils
                                    .getModuleParameterUniqueID(
                                            paramRef.getObjectDictionary()
                                                    .getModule(),
                                            paramRef.getUniqueId());
                            res = OpenConfiguratorCore.GetInstance()
                                    .SetParameterActualValue(
                                            paramRef.getNode().getNetworkId(),
                                            paramRef.getNode().getNodeId(),
                                            newParameterName, actualValue);
                            if (!res.IsSuccessful()) {
                                System.err.println(OpenConfiguratorLibraryUtils
                                        .getErrorMessage(res));
                            } else {
                                paramRef.setActualValue((String) value);
                            }
                        } else {
                            res = OpenConfiguratorCore.GetInstance()
                                    .SetParameterActualValue(
                                            paramRef.getNode().getNetworkId(),
                                            paramRef.getNode().getNodeId(),
                                            paramRef.getUniqueId(),
                                            actualValue);
                            if (!res.IsSuccessful()) {
                                System.err.println(OpenConfiguratorLibraryUtils
                                        .getErrorMessage(res));
                            } else {
                                paramRef.setActualValue((String) value);
                            }
                        }

                    } catch (JDOMException | IOException e) {
                        OpenConfiguratorMessageConsole.getInstance()
                                .printErrorMessage(e.getCause().getMessage(),
                                        paramRef.getNode().getNetworkId());
                        e.printStackTrace();
                    }
                    break;
                case PARAM_ACTUAL_VALUE_ALLOWED_VALUE_ID:
                    try {
                        System.out.println("Value = = " + value);
                        if (value instanceof Integer) {
                            String val = ALLOWED_VALUES[(int) value];
                            System.out.println(
                                    "The selected allowed value = " + val);
                            if (isModuleParameter()) {
                                String newParameterName = OpenConfiguratorLibraryUtils
                                        .getModuleParameterUniqueID(
                                                paramRef.getObjectDictionary()
                                                        .getModule(),
                                                paramRef.getUniqueId());
                                res = OpenConfiguratorCore.GetInstance()
                                        .SetParameterActualValue(
                                                paramRef.getNode()
                                                        .getNetworkId(),
                                                paramRef.getNode().getNodeId(),
                                                newParameterName, val);
                                if (!res.IsSuccessful()) {
                                    System.err.println(
                                            OpenConfiguratorLibraryUtils
                                                    .getErrorMessage(res));
                                } else {
                                    System.out.println(
                                            "The selected allowed value = "
                                                    + val);
                                    paramRef.setActualValue(val);
                                    System.err.println(
                                            "The index of given value = "
                                                    + val.indexOf(val));
                                }

                            } else {
                                res = OpenConfiguratorCore.GetInstance()
                                        .SetParameterActualValue(
                                                paramRef.getNode()
                                                        .getNetworkId(),
                                                paramRef.getNode().getNodeId(),
                                                paramRef.getUniqueId(), val);
                                if (!res.IsSuccessful()) {
                                    System.err.println(
                                            OpenConfiguratorLibraryUtils
                                                    .getErrorMessage(res));
                                } else {
                                    System.out.println(
                                            "The selected allowed value = "
                                                    + val);
                                    paramRef.setActualValue(val);
                                    System.err.println(
                                            "The index of given value = "
                                                    + val.indexOf(val));
                                }

                            }
                        }
                    } catch (Exception e) {
                        OpenConfiguratorMessageConsole.getInstance()
                                .printErrorMessage(e.getCause().getMessage(),
                                        paramRef.getNode().getNetworkId());
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.err.println(id + " not supported!");
            }
        }

        try {
            paramRef.getNode().getProject().refreshLocal(
                    IResource.DEPTH_INFINITE, new NullProgressMonitor());
        } catch (CoreException e) {
            System.err.println("unable to refresh the resource due to "
                    + e.getCause().getMessage());
        }
    }

}

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

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.ui.views.properties.ComboBoxPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.epsg.openconfigurator.console.OpenConfiguratorMessageConsole;
import org.epsg.openconfigurator.lib.wrapper.OpenConfiguratorCore;
import org.epsg.openconfigurator.lib.wrapper.Result;
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

    private ParameterReference paramRef;

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
    }

    // Property descriptor to be displayed in the property page.
    private void addPropertyDescriptors(
            List<IPropertyDescriptor> propertyList) {
        propertyList.add(uniqueIdDescriptor);
        propertyList.add(nameDescriptor);
        propertyList.add(accessTypeDescriptor);

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
        List<IPropertyDescriptor> propertyList = new ArrayList<IPropertyDescriptor>();
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
                                        + minValue + " to " + maxValue + ")";
                            }
                            if (Integer.parseInt(val) > maxValue) {
                                return "Actual value (" + val
                                        + ") does not fit within the range ("
                                        + minValue + " to " + maxValue + ")";
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }
        return null;

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
        return false;
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
        // TODO Auto-generated method stub
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
                        res = OpenConfiguratorCore.GetInstance()
                                .SetParameterActualValue(
                                        paramRef.getNode().getNetworkId(),
                                        paramRef.getNode().getNodeId(),
                                        paramRef.getUniqueId(), actualValue);
                        if (!res.IsSuccessful()) {
                            System.err.println(OpenConfiguratorLibraryUtils
                                    .getErrorMessage(res));
                        } else {
                            paramRef.setActualValue((String) value);
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
            }
        }
    }

}

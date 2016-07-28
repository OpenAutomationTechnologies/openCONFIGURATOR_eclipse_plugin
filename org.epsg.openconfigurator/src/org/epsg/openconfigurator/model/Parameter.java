/*******************************************************************************
 * @file   Parameter.java
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

package org.epsg.openconfigurator.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.epsg.openconfigurator.xmlbinding.xdd.TParameterList;
import org.epsg.openconfigurator.xmlbinding.xdd.TParameterTemplate;
import org.epsg.openconfigurator.xmlbinding.xdd.TProperty;
import org.jdom2.JDOMException;

/**
 * Class to list the parameter and define the various parameter elements.
 *
 * @author Ramakrishnan P
 *
 */
public class Parameter implements IParameter {

    /**
     * Enum to list out available access option of parameter list.
     *
     * @author Ramakrishnan P
     *
     */
    public enum ParameterAccess {
        UNDEFINED("undefined"), NO_ACCESS("noAccess"), CONST("const"), READ(
                "read"), WRITE("write"), READ_WRITE(
                        "readWrite"), READ_WRITE_INPUT(
                                "readWriteInput"), READ_WRITE_OUTPUT(
                                        "readWriteOutput");

        /**
         * Get the values of ParameterAccess enum defined.
         *
         * @param v The enum to get the value.
         * @return The value of given enum as a parameter.
         */
        public static ParameterAccess fromValue(String v) {
            for (ParameterAccess c : ParameterAccess.values()) {
                if (c.value.equals(v)) {
                    return c;
                }
            }
            throw new IllegalArgumentException(v);
        }

        private final String value;

        private ParameterAccess(final String text) {
            value = text;
        }

        /*
         * (non-Javadoc)
         *
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return value;
        }
    }

    /**
     * Property class to list out the properties of parameter.
     *
     * @author Ramakrishnan P
     *
     */
    public class Property {
        private String name;
        private String value;

        /**
         * Constructor of Property to define name and value of available
         * parameter properties.
         *
         * @param name The name of parameter property
         * @param value The value given to update the name into the parameter.
         */
        public Property(final String name, final String value) {
            this.name = name;
            this.value = value;
        }

        /**
         * Constructor of Property with XDD model as input.
         *
         * @param propModel The XDD model of TProperty.
         */
        public Property(TProperty propModel) {
            if (propModel != null) {
                name = propModel.getName();
                value = propModel.getValue();
            } else {
                // ignore.
            }
        }

        /**
         * @return Name of parameter property.
         */
        public String getName() {
            return name;
        }

        /**
         * @return The value of parameter property.
         */
        public String getValue() {
            return value;
        }
    }

    /**
     * Instance of Node.
     */
    private Node node;

    /**
     * Instance of parameter Xpath.
     */
    private String xpath;

    /**
     * Instance of parameter actual value Xpath.
     */
    private String actualValueXpath;

    /**
     * Instance of TParameterTemplate XDD model.
     */
    private TParameterTemplate parameterTemplate;

    /**
     * Instance of LabelDescription
     */
    private LabelDescription label;

    /**
     * UniqueID of parameter
     */
    private String uniqueId;

    /**
     * Instance of parameter access enum.
     */
    private ParameterAccess access;

    /**
     * Instance of DataTypeChoice.
     */
    private DataTypeChoice dataType;

    // private Parameter conditionalParameter;
    // TODO: ConditionalSupport is ignored as of now.

    /**
     * Actual value of parameter.
     */
    private String actualValue;

    /**
     * Default value of parameter.
     */
    private String defaultValue;

    /**
     * Instance of LabelDescription to define the unitLabel element.
     */
    private LabelDescription unitLabel;

    /**
     * List of parameter property.
     */
    private List<Property> propertyList = new ArrayList<>();

    /**
     * Instance of parameter allowed values.
     */
    private AllowedValues allowedValues;

    /**
     * Constructor of parameter class to define values of parameter child
     * elements.
     *
     * @param nodeinstance instance of Node.
     * @param param Parameter XDD model.
     */
    public Parameter(Node nodeinstance, TParameterList.Parameter param) {
        if (param != null) {
            label = new LabelDescription(
                    param.getLabelOrDescriptionOrLabelRef());
            uniqueId = param.getUniqueID();

            if (param.getAccess() != null) {
                access = ParameterAccess.fromValue(param.getAccess());
            }

            node = nodeinstance;
            xpath = "//plk:parameter[@uniqueID='" + uniqueId + "']";
            actualValueXpath = xpath + "/plk:actualValue";

            dataType = new DataTypeChoice(param);

            Object paramTempladeIDRef = param.getTemplateIDRef();
            if (paramTempladeIDRef != null) {

                if (paramTempladeIDRef instanceof TParameterTemplate) {
                    TParameterTemplate parameteratemplateModel = (TParameterTemplate) paramTempladeIDRef;
                    parameterTemplate = parameteratemplateModel;
                }
            }
            if (param.getActualValue() != null) {
                actualValue = param.getActualValue().getValue();
            }

            if (param.getDefaultValue() != null) {
                defaultValue = param.getDefaultValue().getValue();
            }

            if (param.getUnit() != null) {
                unitLabel = new LabelDescription(
                        param.getUnit().getLabelOrDescriptionOrLabelRef());
            }

            for (TProperty propertyModel : param.getProperty()) {
                propertyList.add(new Property(propertyModel));
            }

            allowedValues = new AllowedValues(param.getAllowedValues());
        } else {
            // ignore.
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.epsg.openconfigurator.model.IParameter#getAccess()
     */
    @Override
    public ParameterAccess getAccess() {
        if (access == null) {
            if (parameterTemplate instanceof TParameterTemplate) {
                if (parameterTemplate.getAccess() != null) {
                    return ParameterAccess
                            .fromValue(parameterTemplate.getAccess());
                } else {
                    return ParameterAccess.UNDEFINED;
                }
            }
        }
        return access;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.epsg.openconfigurator.model.IParameter#getActualValue()
     */
    @Override
    public String getActualValue() {
        if ((actualValue == null) || (actualValue.isEmpty())) {
            if (parameterTemplate instanceof TParameterTemplate) {
                if (parameterTemplate.getActualValue() != null) {
                    return parameterTemplate.getActualValue().getValue();
                } else {
                    return StringUtils.EMPTY;
                }
            }
        } else if (actualValue.contains("Â")) {
            actualValue = actualValue.replace("Â", "");
        }
        return actualValue;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.epsg.openconfigurator.model.IParameter#getAllowedValues()
     */
    @Override
    public AllowedValues getAllowedValues() {
        if ((allowedValues == null)) {
            if (parameterTemplate instanceof TParameterTemplate) {
                if (parameterTemplate.getAllowedValues() != null) {
                    return new AllowedValues(
                            parameterTemplate.getAllowedValues());
                } else {
                    return null;
                }
            }
        }
        return allowedValues;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.epsg.openconfigurator.model.IParameter#getDataType()
     */
    @Override
    public DataTypeChoice getDataType() {
        return dataType;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.epsg.openconfigurator.model.IParameter#getDataTypeChoice()
     */
    @Override
    public DataTypeChoiceType getDataTypeChoice() {
        return dataType.getChoiceType();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.epsg.openconfigurator.model.IParameter#getDefaultValue()
     */
    @Override
    public String getDefaultValue() {
        if ((defaultValue == null) || (defaultValue.isEmpty())) {
            if (parameterTemplate instanceof TParameterTemplate) {
                if (parameterTemplate.getDefaultValue() != null) {
                    return parameterTemplate.getDefaultValue().getValue();
                } else {
                    return StringUtils.EMPTY;
                }
            }
        } else if (defaultValue.contains("Â")) {
            defaultValue = defaultValue.replace("Â", "");
        }
        return defaultValue;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.epsg.openconfigurator.model.IParameter#getLabelDescription()
     */
    @Override
    public LabelDescription getLabelDescription() {
        // TODO:Check for label of templateIDRef
        return label;
    }

    /**
     * @return The instance of node.
     */
    public Node getNode() {
        return node;
    }

    /**
     * @return The actual value element XPath of Parameter.
     */
    public String getParameterActualValueXpath() {
        return actualValueXpath;
    }

    /**
     * @return The allowed values of parameter template defined in the
     *         parameter.
     */
    public AllowedValues getParameterTemplateAllowedValues() {
        if (parameterTemplate.getAllowedValues() != null) {
            return new AllowedValues(parameterTemplate.getAllowedValues());
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.epsg.openconfigurator.model.IParameter#getPropertyList()
     */
    @Override
    public List<Property> getPropertyList() {
        return propertyList;
    }

    /**
     * @return The rangeList available within the parameter allowed values of
     *         XDD file.
     */
    public List<Range> getRangeList() {
        if (parameterTemplate != null) {
            if ((allowedValues.getRangeList() == null)
                    || (allowedValues.getRangeList().isEmpty())) {
                return getParameterTemplateAllowedValues().getRangeList();
            }
        }
        return allowedValues.getRangeList();
    }

    @Override
    public SimpleDataType getSimpleDataType() {
        return dataType.getSimpleDataType();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.epsg.openconfigurator.model.IParameter#getStructDataType()
     */
    @Override
    public StructType getStructDataType() {
        return dataType.getStructDataType();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.epsg.openconfigurator.model.IParameter#getUniqueId()
     */
    @Override
    public String getUniqueId() {
        return uniqueId;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.epsg.openconfigurator.model.IParameter#getUnitLabel()
     */
    @Override
    public LabelDescription getUnitLabel() {
        return unitLabel;
    }

    /**
     * @return The XPath of parameter based on UniqueID.
     */
    public String getXpath() {
        return xpath;
    }

    /**
     * Update the actual value element of parameter from the given value.
     *
     * @param value The value to be updated into the XDC file.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public void setActualValue(String value) throws JDOMException, IOException {
        actualValue = value;
        // Update the value in XDC file
        // OpenConfiguratorProjectUtils.updateParameterActualValue(node, this,
        // actualValue);
    }
}

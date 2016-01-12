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

import java.util.ArrayList;
import java.util.List;

import org.epsg.openconfigurator.xmlbinding.xdd.TParameterList;
import org.epsg.openconfigurator.xmlbinding.xdd.TProperty;

/**
 *
 * @author Ramakrishnan P
 *
 */
public class Parameter {

    public enum ParameterAccess {
        UNDEFINED("undefined"), NO_ACCESS("noAccess"), CONST("const"), READ(
                "read"), WRITE("write"), READ_WRITE(
                        "readWrite"), READ_WRITE_INPUT(
                                "readWriteInput"), READ_WRITE_OUTPUT(
                                        "readWriteOutput");

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

        @Override
        public String toString() {
            return value;
        }
    }

    public class Property {
        private String name;
        private String value;

        public Property(final String name, final String value) {
            this.name = name;
            this.value = value;
        }

        public Property(TProperty propModel) {
            if (propModel != null) {
                name = propModel.getName();
                value = propModel.getValue();
            } else {
                // ignore.
            }
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }
    }

    private LabelDescription label;
    private String uniqueId;
    private ParameterAccess access = ParameterAccess.READ;
    private DataTypeChoice dataType;
    // private Parameter conditionalParameter;
    // TODO: ConditionalSupport is ignored as of now.
    private String actualValue;
    private String defaultValue;

    private LabelDescription unitLabel;
    private List<Property> propertyList;
    private AllowedValues allowedValues;

    public Parameter(TParameterList.Parameter param) {
        if (param != null) {
            label = new LabelDescription(
                    param.getLabelOrDescriptionOrLabelRef());
            uniqueId = param.getUniqueID();

            if (param.getAccess() != null) {
                access = ParameterAccess.fromValue(param.getAccess());
            }

            dataType = new DataTypeChoice(param);

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

            propertyList = new ArrayList<>();
            for (TProperty propertyModel : param.getProperty()) {
                propertyList.add(new Property(propertyModel));
            }

            allowedValues = new AllowedValues(param.getAllowedValues());
        } else {
            // ignore.
        }
    }

    public ParameterAccess getAccess() {
        return access;
    }

    public String getActualValue() {
        return actualValue;
    }

    public AllowedValues getAllowedValues() {
        return allowedValues;
    }

    public DataTypeChoice getDataType() {
        return dataType;
    }

    public DataTypeChoiceType getDataTypeChoice() {
        return dataType.getChoiceType();
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public LabelDescription getLabel() {
        return label;
    }

    public LabelDescription getLabelDescription() {
        return label;
    }

    public List<Property> getPropertyList() {
        return propertyList;
    }

    public SimpleDataType getSimpleDataType() {
        return dataType.getSimpleDataType();
    }

    public StructType getStructDataType() {
        return dataType.getStructDataType();
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public LabelDescription getUnitLabel() {
        return unitLabel;
    }
}

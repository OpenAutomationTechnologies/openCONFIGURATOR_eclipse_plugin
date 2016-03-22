/*******************************************************************************
 * @file   ParameterReference.java
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
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.epsg.openconfigurator.model.Parameter.ParameterAccess;
import org.epsg.openconfigurator.model.Parameter.Property;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectUtils;
import org.epsg.openconfigurator.xmlbinding.xdd.TParameterGroup;
import org.epsg.openconfigurator.xmlbinding.xdd.TParameterList;
import org.jdom2.JDOMException;

/**
 *
 * @author Ramakrishnan P
 *
 */
public class ParameterReference implements IParameter {

    private Parameter parameter;

    private String actualValue;
    private boolean visible;
    private boolean locked;
    private BigInteger bitOffset;

    private Node node;
    private String xpath;
    private String uniqueIdRef;

    private ObjectDictionary objectDictionary;
    private ParameterGroup parameterGroup;

    public ParameterReference(Node nodeinstance, ParameterGroup parameterGroup,
            ObjectDictionary objectDictionary,
            TParameterGroup.ParameterRef parameterReferenceModel) {
        this.parameterGroup = parameterGroup;
        this.objectDictionary = objectDictionary;

        if (parameterReferenceModel != null) {
            actualValue = parameterReferenceModel.getActualValue();
            visible = parameterReferenceModel.isVisible();
            locked = parameterReferenceModel.isLocked();
            bitOffset = parameterReferenceModel.getBitOffset();
            Object paramModelObj = parameterReferenceModel.getUniqueIDRef();
            if (paramModelObj != null) {
                if (paramModelObj instanceof TParameterList.Parameter) {

                    TParameterList.Parameter paramModel = (TParameterList.Parameter) paramModelObj;
                    parameter = this.objectDictionary
                            .getParameter(paramModel.getUniqueID());
                    uniqueIdRef = paramModel.getUniqueID();
                }
            }
        }
        node = nodeinstance;

        xpath = parameterGroup.getXpath() + "/plk:parameterRef[@uniqueIDRef='"
                + uniqueIdRef + "']";

    }

    @Override
    public ParameterAccess getAccess() {
        if (parameter != null) {
            return parameter.getAccess();
        }

        return ParameterAccess.READ; // TODO: Confirm
    }

    @Override
    public String getActualValue() {
        if (actualValue != null) {
            return actualValue;
        } else {
            if (parameter != null) {
                return parameter.getActualValue();
            }
        }
        return null;
    }

    @Override
    public AllowedValues getAllowedValues() {
        if (parameter != null) {
            return parameter.getAllowedValues();
        }
        return null;
    }

    public BigInteger getBitOffset() {
        return bitOffset;
    }

    @Override
    public DataTypeChoice getDataType() {
        if (parameter != null) {
            return parameter.getDataType();
        }
        return null;
    }

    @Override
    public DataTypeChoiceType getDataTypeChoice() {
        if (parameter != null) {
            return parameter.getDataTypeChoice();
        }
        return null;
    }

    @Override
    public String getDefaultValue() {
        if (parameter != null) {
            return parameter.getDefaultValue();
        }
        return null;
    }

    @Override
    public LabelDescription getLabelDescription() {
        if (parameter != null) {
            return parameter.getLabelDescription();
        }
        return null;
    }

    /**
     * @return Instance of Node.
     */
    public Node getNode() {
        return node;
    }

    public ObjectDictionary getObjectDictionary() {
        return objectDictionary;
    }

    public ParameterGroup getParameterGroup() {
        return parameterGroup;
    }

    @Override
    public List<Property> getPropertyList() {
        if (parameter != null) {
            return parameter.getPropertyList();
        }
        return new ArrayList<Parameter.Property>();
    }

    @Override
    public SimpleDataType getSimpleDataType() {
        if (parameter != null) {
            return parameter.getSimpleDataType();
        }
        return null;
    }

    @Override
    public StructType getStructDataType() {
        if (parameter != null) {
            return parameter.getStructDataType();
        }
        return null;
    }

    @Override
    public String getUniqueId() {
        if (parameter != null) {
            return parameter.getUniqueId();
        } else {
            return null;
        }
    }

    @Override
    public LabelDescription getUnitLabel() {
        if (parameter != null) {
            return parameter.getUnitLabel();
        }
        return null;
    }

    /**
     * @return Xpath of parameter reference using parameter group xpath.
     */
    public String getXpath() {
        return xpath;
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean isVisible() {
        return visible;
    }

    /**
     * Update actual value attribute with the given value of parameter.
     *
     * @param value The value to be updated in the XDD/XDC file.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public void setActualValue(final String value)
            throws JDOMException, IOException {
        actualValue = value;
        OpenConfiguratorProjectUtils.updateParameterReferenceActualValue(node,
                this, actualValue);
    }
}

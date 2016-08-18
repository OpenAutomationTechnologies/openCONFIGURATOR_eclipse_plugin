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
 * Class to list the parameter references identified under parameter group.
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
    private TParameterGroup.ParameterRef parameterReference;

    /**
     * Parameter reference constructor initializes the attribute values from the
     * XDD model.
     *
     * @param nodeinstance Instance of node.
     * @param parameterGroup Instance of parameter group to list the parent
     *            element of parameter reference.
     * @param objectDictionary Object dictionary instance
     * @param parameterReferenceModel Parameter reference XDD model instance.
     */
    public ParameterReference(Node nodeinstance, ParameterGroup parameterGroup,
            ObjectDictionary objectDictionary,
            TParameterGroup.ParameterRef parameterReferenceModel) {
        this.parameterGroup = parameterGroup;
        this.objectDictionary = objectDictionary;

        if (parameterReferenceModel != null) {
            parameterReference = parameterReferenceModel;
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

    /*
     * (non-Javadoc)
     *
     * @see org.epsg.openconfigurator.model.IParameter#getAccess()
     */
    @Override
    public ParameterAccess getAccess() {
        if (parameter != null) {
            return parameter.getAccess();
        }

        return ParameterAccess.UNDEFINED; // TODO: Confirm
    }

    /*
     * (non-Javadoc)
     *
     * @see org.epsg.openconfigurator.model.IParameter#getActualValue()
     */
    @Override
    public String getActualValue() {
        if (parameter.getActualValue() != null) {
            return parameter.getActualValue();
        } else {
            if (parameter.getDefaultValue() != null) {
                parameter.getDefaultValue();
            } else {
                if (actualValue != null) {
                    if (actualValue.contains("Â")) {
                        actualValue = actualValue.replace("Â", "");
                    }
                    return actualValue;
                }
            }
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.epsg.openconfigurator.model.IParameter#getAllowedValues()
     */
    @Override
    public AllowedValues getAllowedValues() {
        if (parameter != null) {
            return parameter.getAllowedValues();
        }
        return null;
    }

    /**
     * @return Bit offset value of parameter reference.
     */
    public BigInteger getBitOffset() {
        return bitOffset;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.epsg.openconfigurator.model.IParameter#getDataType()
     */
    @Override
    public DataTypeChoice getDataType() {
        if (parameter != null) {
            return parameter.getDataType();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.epsg.openconfigurator.model.IParameter#getDataTypeChoice()
     */
    @Override
    public DataTypeChoiceType getDataTypeChoice() {
        if (parameter != null) {
            return parameter.getDataTypeChoice();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.epsg.openconfigurator.model.IParameter#getDefaultValue()
     */
    @Override
    public String getDefaultValue() {
        if (parameter != null) {
            return parameter.getDefaultValue();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.epsg.openconfigurator.model.IParameter#getLabelDescription()
     */
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

    /**
     * @return Object dictionary instance to receive the parameter based on
     *         unique ID
     */
    public ObjectDictionary getObjectDictionary() {
        return objectDictionary;
    }

    /**
     * @return The parameter group instance to identify the parameter reference
     */
    public ParameterGroup getParameterGroup() {
        return parameterGroup;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.epsg.openconfigurator.model.IParameter#getPropertyList()
     */
    @Override
    public List<Property> getPropertyList() {
        if (parameter != null) {
            return parameter.getPropertyList();
        }
        return new ArrayList<>();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.epsg.openconfigurator.model.IParameter#getSimpleDataType()
     */
    @Override
    public SimpleDataType getSimpleDataType() {
        if (parameter != null) {
            return parameter.getSimpleDataType();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.epsg.openconfigurator.model.IParameter#getStructDataType()
     */
    @Override
    public StructType getStructDataType() {
        if (parameter != null) {
            return parameter.getStructDataType();
        }
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.epsg.openconfigurator.model.IParameter#getUniqueId()
     */
    @Override
    public String getUniqueId() {
        if (parameter != null) {
            return parameter.getUniqueId();
        }
        return null;

    }

    /*
     * (non-Javadoc)
     *
     * @see org.epsg.openconfigurator.model.IParameter#getUnitLabel()
     */
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

    /**
     * @return The locked attribute value of parameter reference
     */
    public boolean isLocked() {
        return locked;
    }

    /**
     * @return The visible attribute value of parameter reference
     */
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
    public void setActualValue(String value) throws JDOMException, IOException {
        if (value.contains("Â")) {
            value = value.replace("Â", "");
        }
        actualValue = value;
        parameterReference.setActualValue(value);
        if (getObjectDictionary().isModule()) {
            Parameter param = getObjectDictionary().getParameter(getUniqueId());
            param.setActualValue(value);
            OpenConfiguratorProjectUtils.updateParameterActualValue(
                    getObjectDictionary().getModule(), param, actualValue);
        } else {
            Parameter param = getObjectDictionary().getParameter(getUniqueId());
            param.setActualValue(value);
            OpenConfiguratorProjectUtils.updateParameterActualValue(node, param,
                    actualValue);
        }

    }
}

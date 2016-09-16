/*******************************************************************************
 * @file   IParameter.java
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

import java.util.List;

import org.epsg.openconfigurator.model.Parameter.ParameterAccess;
import org.epsg.openconfigurator.model.Parameter.Property;

/**
 * Interface class to define the required elements of parameter.
 *
 * @author Ramakrishnan P
 *
 */
public interface IParameter {

    /**
     * @return Access of parameter.
     */
    public ParameterAccess getAccess();

    /**
     * @return Actual value of parameter.
     */
    public String getActualValue();

    /**
     * @return Allowed values of parameter.
     */
    public AllowedValues getAllowedValues();

    /**
     * @return Data type of parameter from the XDD model.
     */
    public DataTypeChoice getDataType();

    /**
     * @return The choice of data type from the parameter XDD model.
     */
    public DataTypeChoiceType getDataTypeChoice();

    /**
     * @return The default value of parameter.
     */
    public String getDefaultValue();

    /**
     * @return Label name of parameter.
     */
    public LabelDescription getLabelDescription();

    /**
     * @return List of properties that refer parameter list.
     */
    public List<Property> getPropertyList();

    /**
     * @return Simple data type available in the parameter XDD model.
     */
    public SimpleDataType getSimpleDataType();

    /**
     * @return The Complex data type of parameter in the XDD model.
     */
    public StructType getStructDataType();

    /**
     * @return The parameter unique ID.
     */
    public String getParameterUniqueId();

    /**
     * @return The Unit label of parameter.
     */
    public LabelDescription getUnitLabel();
}

/*******************************************************************************
 * @file   AbstractParameterPropertySource.java
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

import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

/**
 * Abstract implementation of property source for POWERLINK Parameter.
 *
 * @author Ramakrishnan P
 *
 */
public abstract class AbstractParameterPropertySource {

    public static final String UNIQUE_ID = "UniqueId"; //$NON-NLS-1$
    public static final String PARAM_NAME_ID = "Parameter.Name"; //$NON-NLS-1$
    public static final String PARAM_ACCESS_TYPE_ID = "Parameter.AccessType"; //$NON-NLS-1$
    public static final String PARAM_DEFAULT_VALUE_ID = "Parameter.DefaultValue"; //$NON-NLS-1$
    public static final String PARAM_BIT_OFFSET_ID = "Parameter.BitOffset"; //$NON-NLS-1$
    public static final String PARAM_ACTUAL_VALUE_ID = "Parameter.ActualValue"; //$NON-NLS-1$
    public static final String PARAM_ACTUAL_VALUE_READ_ONLY_ID = "Parameter.ActualValue.ReadOnly"; //$NON-NLS-1$
    public static final String PARAM_ACTUAL_VALUE_ALLOWED_VALUE_ID = "Parameter.ActualValue.AllowedValue"; //$NON-NLS-1$
    public static final String PARAM_UNIT_ID = "Parameter.Unit"; //$NON-NLS-1$
    public static final String PARAM_DATATYPE_ID = "Parameter.SimpleDataType"; //$NON-NLS-1$

    public static final String UNIQUE_ID_LABEL = "Unique ID";
    public static final String PARAM_NAME_LABEL = "Name";
    public static final String PARAM_ACCESS_TYPE_LABEL = "Access Type";
    public static final String PARAM_DEFAULT_VALUE_LABEL = "Default Value";
    public static final String PARAM_BIT_OFFSET_LABEL = "Bit Offset";
    public static final String PARAM_ACTUAL_VALUE_LABEL = "Actual Value";
    public static final String PARAM_UNIT_LABEL = "Unit";
    public static final String PARAM_DATATYPE_LABEL = "Data Type";

    protected static final PropertyDescriptor uniqueIdDescriptor = new PropertyDescriptor(
            UNIQUE_ID, UNIQUE_ID_LABEL);
    protected static final PropertyDescriptor nameDescriptor = new PropertyDescriptor(
            PARAM_NAME_ID, PARAM_NAME_LABEL);
    protected static final PropertyDescriptor accessTypeDescriptor = new PropertyDescriptor(
            PARAM_ACCESS_TYPE_ID, PARAM_ACCESS_TYPE_LABEL);
    protected static final PropertyDescriptor defaultValueDescriptor = new PropertyDescriptor(
            PARAM_DEFAULT_VALUE_ID, PARAM_DEFAULT_VALUE_LABEL);

    protected static final PropertyDescriptor bitOffsetDescriptor = new PropertyDescriptor(
            PARAM_BIT_OFFSET_ID, PARAM_BIT_OFFSET_LABEL);

    protected static final TextPropertyDescriptor actualValueTextDescriptor = new TextPropertyDescriptor(
            PARAM_ACTUAL_VALUE_ID, PARAM_ACTUAL_VALUE_LABEL);
    protected static final PropertyDescriptor actualValueReadOnlyDescriptor = new PropertyDescriptor(
            PARAM_ACTUAL_VALUE_READ_ONLY_ID, PARAM_ACTUAL_VALUE_LABEL);
    protected static final PropertyDescriptor unitDescriptor = new PropertyDescriptor(
            PARAM_UNIT_ID, PARAM_UNIT_LABEL);

    protected static final PropertyDescriptor dataTypeDescriptor = new PropertyDescriptor(
            PARAM_DATATYPE_ID, PARAM_DATATYPE_LABEL);
}

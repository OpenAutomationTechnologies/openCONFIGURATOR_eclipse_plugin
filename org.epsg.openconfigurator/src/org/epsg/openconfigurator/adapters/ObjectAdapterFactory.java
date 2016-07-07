/*******************************************************************************
 * @file   ObjectAdapterFactory.java
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

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.ui.views.properties.IPropertySource;
import org.epsg.openconfigurator.model.Parameter;
import org.epsg.openconfigurator.model.ParameterGroup;
import org.epsg.openconfigurator.model.ParameterReference;
import org.epsg.openconfigurator.model.PowerlinkObject;
import org.epsg.openconfigurator.model.PowerlinkSubobject;

/**
 * Factory implementation of property sources for POWERLNK Objects available for
 * any node.
 *
 * @author Ramakrishnan P
 *
 */
public class ObjectAdapterFactory implements IAdapterFactory {

    /**
     * Property source for objects.
     */
    private ObjectPropertySource objectPropertySource;

    /**
     * Property source for sub-objects.
     */
    private SubObjectPropertySource subObjectPropertySource;

    /**
     * Property source for Parameter reference.
     */
    private ParameterRefPropertySource parameterRefPropertySource;

    /**
     * Property source for Parameter.
     */
    private ParameterPropertySource parameterPropertySource;

    /**
     * Property source for Parameter group.
     */
    private ParameterGroupPropertySource parameterGroupPropertySource;

    @Override
    public Object getAdapter(Object adaptableObject, Class adapterType) {
        if (adapterType == IPropertySource.class) {

            if (adaptableObject instanceof PowerlinkObject) {
                if (objectPropertySource == null) {
                    objectPropertySource = new ObjectPropertySource(
                            (PowerlinkObject) adaptableObject);
                } else {
                    objectPropertySource
                            .setObjectData((PowerlinkObject) adaptableObject);
                }
                return objectPropertySource;
            } else if (adaptableObject instanceof PowerlinkSubobject) {
                if (subObjectPropertySource == null) {
                    subObjectPropertySource = new SubObjectPropertySource(
                            (PowerlinkSubobject) adaptableObject);
                } else {
                    subObjectPropertySource.setSubObjectData(
                            (PowerlinkSubobject) adaptableObject);
                }
                return subObjectPropertySource;
            } else if (adaptableObject instanceof ParameterReference) {
                if (parameterRefPropertySource == null) {
                    parameterRefPropertySource = new ParameterRefPropertySource(
                            (ParameterReference) adaptableObject);
                } else {
                    parameterRefPropertySource
                            .setModelData((ParameterReference) adaptableObject);
                }
                return parameterRefPropertySource;
            } else if (adaptableObject instanceof Parameter) {
                if (parameterPropertySource == null) {
                    parameterPropertySource = new ParameterPropertySource(
                            (Parameter) adaptableObject);
                } else {
                    parameterPropertySource
                            .setModelData((Parameter) adaptableObject);
                }
                return parameterPropertySource;
            } else if (adaptableObject instanceof ParameterGroup) {
                if (parameterGroupPropertySource == null) {
                    parameterGroupPropertySource = new ParameterGroupPropertySource(
                            (ParameterGroup) adaptableObject);
                } else {
                    parameterGroupPropertySource
                            .setModelData((ParameterGroup) adaptableObject);
                }
                return parameterGroupPropertySource;
            } else {
                System.err.println("Un supported");
            }
        }
        return null;
    }

    @Override
    public Class[] getAdapterList() {
        return new Class[] { IPropertySource.class };
    }
}

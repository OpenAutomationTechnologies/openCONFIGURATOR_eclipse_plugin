/*******************************************************************************
 * @file   TCNPropertySource.java
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

import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.IPropertySource;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;
import org.epsg.openconfigurator.xmlbinding.projectfile.TCN;

public class TCNPropertySource implements IPropertySource {

    private final TCN tcn;

    public TCNPropertySource(TCN adaptableObject) {
        tcn = adaptableObject;
    }

    @Override
    public Object getEditableValue() {
        return tcn;
    }

    @Override
    public IPropertyDescriptor[] getPropertyDescriptors() {
        return new IPropertyDescriptor[] {
                new TextPropertyDescriptor("NodeName", "Node Name"),
                new TextPropertyDescriptor("NodeID", "Node ID"),
                new TextPropertyDescriptor("XDCPath", "XDC Path"),
                new TextPropertyDescriptor("Forced", "Forced Multiplexed Cycle"),
                new TextPropertyDescriptor("Chained", "Chained") };
    }

    @Override
    public Object getPropertyValue(Object id) {
        if ("NodeName".equals(id)) {
            return tcn.getName();
        } else if ("NodeID".equals(id)) {
            return tcn.getNodeID();
        } else if ("XDCPath".equals(id)) {
            return tcn.getPathToXDC();
        } else if ("Forced".equals(id)) {
            return tcn.getForcedMultiplexedCycle();
        } else if ("Chained".equals(id)) {
            return tcn.isIsChained();
        }
        return null;
    }

    @Override
    public boolean isPropertySet(Object id) {
        return false;
    }

    @Override
    public void resetPropertyValue(Object id) {

    }

    @Override
    public void setPropertyValue(Object id, Object value) {
        if ("NodeName".equals(id)) {
            tcn.setName((String) value);
        } else if ("NodeID".equals(id)) {
            tcn.setNodeID((String) value);
        } else if ("XDCPath".equals(id)) {
            tcn.setPathToXDC((String) value);
        } else if ("Forced".equals(id)) {
            tcn.setForcedMultiplexedCycle((Integer) value);
        } else if ("Chained".equals(id)) {
            tcn.setIsChained((Boolean) value);
        }
    }

}

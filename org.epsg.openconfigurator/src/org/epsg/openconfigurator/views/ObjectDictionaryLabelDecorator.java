/*******************************************************************************
 * @file   ObjectDictionaryLabelDecorator.java
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
package org.epsg.openconfigurator.views;

import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.epsg.openconfigurator.model.PowerlinkObject;
import org.epsg.openconfigurator.model.PowerlinkSubobject;
import org.epsg.openconfigurator.resources.IPluginImages;
import org.epsg.openconfigurator.xmlbinding.xdd.TObjectAccessType;

/**
 * Decorates the Object Dictionary with overlay images.
 *
 * @author Ramakrishnan P
 *
 */
public class ObjectDictionaryLabelDecorator
        implements ILightweightLabelDecorator {

    public ObjectDictionaryLabelDecorator() {
    }

    @Override
    public void addListener(ILabelProviderListener listener) {
        // Do nothing
    }

    @Override
    public void decorate(Object element, IDecoration decoration) {
        if (element instanceof PowerlinkObject) {
            PowerlinkObject obj = (PowerlinkObject) element;
            // Set warning icon for forced objects.
            if (obj.isObjectForced()) {
                decoration.addOverlay(
                        org.epsg.openconfigurator.Activator.getImageDescriptor(
                                IPluginImages.OBD_OVERLAY_FORCED_OBJECTS_ICON),
                        IDecoration.BOTTOM_RIGHT);
            }

            // Only VAR type is allowed to be edited.
            if ((obj.getObjectType() == 7)) {
                TObjectAccessType accessType = obj.getModel().getAccessType();
                if (accessType != null) {
                    if ((accessType == TObjectAccessType.RO)
                            || (accessType == TObjectAccessType.CONST)) {
                        // Set lock icon for readonly objects
                        decoration.addOverlay(
                                org.epsg.openconfigurator.Activator
                                        .getImageDescriptor(
                                                IPluginImages.OBD_OVERLAY_LOCK_ICON),
                                IDecoration.BOTTOM_RIGHT);
                    }
                }
            }
            return;
        } else if (element instanceof PowerlinkSubobject) {
            PowerlinkSubobject subObj = (PowerlinkSubobject) element;

            // Set warning icon for forced sub-objects.
            if (subObj.isObjectForced()) {
                decoration.addOverlay(
                        org.epsg.openconfigurator.Activator.getImageDescriptor(
                                IPluginImages.OBD_OVERLAY_FORCED_OBJECTS_ICON),
                        IDecoration.BOTTOM_RIGHT);
            }

            // Only VAR type is allowed to be edited.
            if ((subObj.getModel().getObjectType() == 7)) {
                TObjectAccessType accessType = subObj.getModel()
                        .getAccessType();
                if (accessType != null) {
                    if ((accessType == TObjectAccessType.RO)
                            || (accessType == TObjectAccessType.CONST)) {
                        // Set lock icon for readonly sub-objects
                        decoration.addOverlay(
                                org.epsg.openconfigurator.Activator
                                        .getImageDescriptor(
                                                IPluginImages.OBD_OVERLAY_LOCK_ICON),
                                IDecoration.BOTTOM_RIGHT);
                    }
                }
            }
            return;
        }
    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean isLabelProperty(Object element, String property) {
        return true;
    }

    @Override
    public void removeListener(ILabelProviderListener listener) {
        // Do nothing
    }
}

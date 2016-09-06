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

import org.apache.commons.lang3.StringUtils;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ILightweightLabelDecorator;
import org.epsg.openconfigurator.model.Parameter.ParameterAccess;
import org.epsg.openconfigurator.model.ParameterReference;
import org.epsg.openconfigurator.model.PowerlinkObject;
import org.epsg.openconfigurator.model.PowerlinkSubobject;
import org.epsg.openconfigurator.resources.IPluginImages;
import org.epsg.openconfigurator.util.OpenConfiguratorLibraryUtils;
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

            // Set warning icon for forced module Objects
            if (obj.isModuleObject()) {
                long newObjectIndex = OpenConfiguratorLibraryUtils
                        .getModuleObjectsIndex(obj.getModule(), obj.getId());
                if (obj.isModuleObjectForced(newObjectIndex)) {
                    decoration.addOverlay(
                            org.epsg.openconfigurator.Activator
                                    .getImageDescriptor(
                                            IPluginImages.OBD_OVERLAY_FORCED_OBJECTS_ICON),
                            IDecoration.BOTTOM_RIGHT);
                }
            }
            // Only VAR type is allowed to be edited.
            if ((obj.getObjectType() == 7)) {
                TObjectAccessType accessType = obj.getAccessType();
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

            // Error overlay image if object configuration is invalid in
            // the XDC file.
            if (!obj.getConfigurationError().isEmpty()) {
                decoration.addOverlay(
                        org.epsg.openconfigurator.Activator.getImageDescriptor(
                                IPluginImages.ERROR_OVERLAY_NODE_ICON),
                        IDecoration.BOTTOM_LEFT);
            }
        } else if (element instanceof PowerlinkSubobject) {
            PowerlinkSubobject subObj = (PowerlinkSubobject) element;

            // Set warning icon for forced sub-objects.
            if (subObj.isObjectForced()) {
                decoration.addOverlay(
                        org.epsg.openconfigurator.Activator.getImageDescriptor(
                                IPluginImages.OBD_OVERLAY_FORCED_OBJECTS_ICON),
                        IDecoration.BOTTOM_RIGHT);
            }

            // Set warning icon for forced module sub-Objects
            if (subObj.isModule()) {
                long newObjectIndex = OpenConfiguratorLibraryUtils
                        .getModuleObjectsIndex(subObj.getModule(),
                                subObj.getObject().getId());
                int newSubObjectIndex = OpenConfiguratorLibraryUtils
                        .getModuleObjectsSubIndex(subObj.getModule(), subObj,
                                subObj.getObject().getId());
                if (subObj.isModuleObjectForced(newObjectIndex,
                        newSubObjectIndex)) {
                    decoration.addOverlay(
                            org.epsg.openconfigurator.Activator
                                    .getImageDescriptor(
                                            IPluginImages.OBD_OVERLAY_FORCED_OBJECTS_ICON),
                            IDecoration.BOTTOM_RIGHT);
                }
            }
            // Only VAR type is allowed to be edited.
            if ((subObj.getObjectType() == 7)) {
                TObjectAccessType accessType = subObj.getAccessType();
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

            // Error overlay image if sub-object configuration is invalid in
            // the XDC file.
            if (!subObj.getConfigurationError().isEmpty()) {
                decoration.addOverlay(
                        org.epsg.openconfigurator.Activator.getImageDescriptor(
                                IPluginImages.ERROR_OVERLAY_NODE_ICON),
                        IDecoration.BOTTOM_LEFT);
            }
        } else if (element instanceof ParameterReference) {
            ParameterReference paramRef = (ParameterReference) element;
            ParameterAccess access = paramRef.getAccess();
            if ((access == ParameterAccess.CONST)
                    || (access == ParameterAccess.READ)
                    || (access == ParameterAccess.UNDEFINED)
                    || (access == ParameterAccess.NO_ACCESS)
                    || paramRef.isLocked()) {
                decoration.addOverlay(
                        org.epsg.openconfigurator.Activator.getImageDescriptor(
                                IPluginImages.OBD_OVERLAY_LOCK_ICON),
                        IDecoration.BOTTOM_LEFT);
            }

            if (!((paramRef.getActualValue() == null)
                    || (paramRef.getActualValue().isEmpty())
                    || (paramRef.getActualValue() == StringUtils.EMPTY)
                    || (paramRef.getActualValue().equals("off")))) {
                decoration.addOverlay(
                        org.epsg.openconfigurator.Activator.getImageDescriptor(
                                IPluginImages.OBD_PARAMETER_ACTUAL_VALUE_OVERLAY_ICON),
                        IDecoration.BOTTOM_LEFT);
            }

        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
     */
    @Override
    public void dispose() {
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.
     * Object, java.lang.String)
     */
    @Override
    public boolean isLabelProperty(Object element, String property) {
        return true;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.
     * jface.viewers.ILabelProviderListener)
     */
    @Override
    public void removeListener(ILabelProviderListener listener) {
        // Do nothing
    }
}

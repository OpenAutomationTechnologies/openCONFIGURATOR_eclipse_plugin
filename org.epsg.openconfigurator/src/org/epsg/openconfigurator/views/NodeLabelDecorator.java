/*******************************************************************************
 * @file   NodeLabelDecorator.java
 *
 * @brief  Adds decorator images to the node.
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
import org.epsg.openconfigurator.model.Module;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.resources.IPluginImages;

/**
 * Decorator class for node.
 *
 * @author Ramakrishnan Periyakaruppan
 *
 */
public class NodeLabelDecorator implements ILightweightLabelDecorator {

    @Override
    public void addListener(ILabelProviderListener listener) {
        // TODO Auto-generated method stub
    }

    @Override
    public void decorate(Object element, IDecoration decoration) {
        if (element instanceof Node) {
            Node nodeObj = (Node) element;
            switch (nodeObj.getPlkOperationMode()) {
                case CHAINED:
                    decoration.addOverlay(
                            org.epsg.openconfigurator.Activator
                                    .getImageDescriptor(
                                            IPluginImages.OVERLAY_CHAINED_NODE_ICON),
                            IDecoration.BOTTOM_RIGHT);
                    break;
                case MULTIPLEXED:
                    decoration.addOverlay(
                            org.epsg.openconfigurator.Activator
                                    .getImageDescriptor(
                                            IPluginImages.OBD_OVERLAY_FORCED_OBJECTS_ICON),
                            IDecoration.BOTTOM_RIGHT);
                    break;
                case NORMAL:
                    break;
                default:
            }
            // Error overlay image to node with invalid XDC file.
            if (nodeObj.hasError()) {
                decoration.addOverlay(
                        org.epsg.openconfigurator.Activator.getImageDescriptor(
                                IPluginImages.ERROR_OVERLAY_NODE_ICON),
                        IDecoration.BOTTOM_LEFT);

            }
        } else if (element instanceof Module) {
            Module moduleObj = (Module) element;
            if (moduleObj.hasError()) {
                decoration.addOverlay(
                        org.epsg.openconfigurator.Activator.getImageDescriptor(
                                IPluginImages.ERROR_OVERLAY_NODE_ICON),
                        IDecoration.BOTTOM_LEFT);

            }
        }
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean isLabelProperty(Object element, String property) {
        return true;
    }

    @Override
    public void removeListener(ILabelProviderListener listener) {
        // TODO Auto-generated method stub
    }
}

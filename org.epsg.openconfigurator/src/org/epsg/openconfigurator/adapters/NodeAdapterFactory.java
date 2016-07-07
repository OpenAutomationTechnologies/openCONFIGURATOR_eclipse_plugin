/*******************************************************************************
 * @file   NodeAdapterFactory.java
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
import org.epsg.openconfigurator.model.HeadNodeInterface;
import org.epsg.openconfigurator.model.Module;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.xmlbinding.projectfile.InterfaceList;
import org.epsg.openconfigurator.xmlbinding.projectfile.TCN;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNetworkConfiguration;
import org.epsg.openconfigurator.xmlbinding.projectfile.TRMN;
import org.epsg.openconfigurator.xmlbinding.xdd.TModuleAddressingHead;

/**
 * Factory implementation of property sources for different types of Nodes
 * available in the openCONFIGURATOR project.
 *
 * @author Ramakrishnan P
 *
 */
public class NodeAdapterFactory implements IAdapterFactory {

    /**
     * Property source for an Managing node.
     */
    ManagingNodePropertySource mnPropertySource;

    /**
     * Property source for an Controlled node.
     */
    ControlledNodePropertySource cnPropertySource;

    /**
     * Property source for an Redundant managing node.
     */
    RedundantManagingNodePropertySource rmnPropertySource;

    /**
     * Property source for an Interface of Modular controlled node.
     */
    HeadNodeInterfacePropertySource interfacePropertySource;

    /**
     * Property source for an Module.
     */
    ModulePropertySource modulePropertySource;

    @Override
    public Object getAdapter(Object adaptableObject, Class adapterType) {
        if (adapterType == IPropertySource.class) {
            if (adaptableObject instanceof Node) {
                Node nodeObj = (Node) adaptableObject;
                // Hide property source,when the node is disabled.
                if (!nodeObj.isEnabled()) {
                    return null;
                }

                Object nodeModel = nodeObj.getNodeModel();
                if (nodeModel instanceof TNetworkConfiguration) {
                    if (mnPropertySource == null) {
                        mnPropertySource = new ManagingNodePropertySource(
                                nodeObj);
                    } else {
                        mnPropertySource.setNodeData(nodeObj);
                    }
                    return mnPropertySource;
                }
                if (nodeModel instanceof TCN) {
                    if (cnPropertySource == null) {
                        cnPropertySource = new ControlledNodePropertySource(
                                nodeObj);
                    } else {
                        cnPropertySource.setNodeData(nodeObj);
                    }
                    return cnPropertySource;
                }
                if (nodeModel instanceof TRMN) {
                    if (rmnPropertySource == null) {
                        rmnPropertySource = new RedundantManagingNodePropertySource(
                                nodeObj);
                    } else {
                        rmnPropertySource.setNodeData(nodeObj);
                    }
                    return rmnPropertySource;
                }
            } else if (adaptableObject instanceof HeadNodeInterface) {
                HeadNodeInterface headinterfaceObj = (HeadNodeInterface) adaptableObject;
                if (headinterfaceObj instanceof HeadNodeInterface) {

                    if (interfacePropertySource == null) {
                        interfacePropertySource = new HeadNodeInterfacePropertySource(
                                headinterfaceObj);
                    } else {
                        interfacePropertySource
                                .setInterfaceData(headinterfaceObj);
                    }
                    return interfacePropertySource;
                }

            } else if (adaptableObject instanceof Module) {
                Module moduleObj = (Module) adaptableObject;
                if (!moduleObj.isEnabled()) {
                    return null;
                }

                Object moduleObjectModel = moduleObj.getModuleModel();
                if (moduleObjectModel instanceof InterfaceList.Interface.Module) {
                    TModuleAddressingHead moduleaddressing = moduleObj
                            .getInterfaceOfModule().getModuleAddressing();

                    modulePropertySource = new ModulePropertySource(moduleObj,
                            moduleaddressing);
                    return modulePropertySource;
                }
            } else {
                System.err.println("No property source for " + adaptableObject);
            }

        }
        return null;

    }

    @Override
    public Class[] getAdapterList() {
        return new Class[] { IPropertySource.class };
    }
}

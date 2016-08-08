/*******************************************************************************
 * @file   ModuleManagement.java
 *
 * @author Sree Hari Vignesh, Kalycito Infotech Private Limited.
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

import java.util.ArrayList;
import java.util.List;

import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745Profile;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745ProfileContainer;
import org.epsg.openconfigurator.xmlbinding.xdd.Interface;
import org.epsg.openconfigurator.xmlbinding.xdd.InterfaceList;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyCommunicationNetworkPowerlinkModularHead;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDataType;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDevicePowerlinkModularHead;
import org.epsg.openconfigurator.xmlbinding.xdd.TApplicationLayersModularHead;
import org.epsg.openconfigurator.xmlbinding.xdd.TInterfaceList;
import org.epsg.openconfigurator.xmlbinding.xdd.TModuleInterface;
import org.epsg.openconfigurator.xmlbinding.xdd.TModuleManagement;

/**
 * Describes the module values from the XDD instance.
 *
 * @author SreeHari
 *
 */
public class ModuleManagement {

    private TApplicationLayersModularHead.ModuleManagement moduleManagement;
    private TModuleManagement moduleManagementModel;
    private TModuleInterface moduleInterface;
    private InterfaceList modularHeadInterface;
    private TInterfaceList devModuleInterfaceList;

    List<TInterfaceList.Interface> interfce = new ArrayList<>();

    List<Interface> interfacelist = new ArrayList<>();

    /**
     * Constructor that defines the module management from the XDD model.
     *
     * @param node Instance of node.
     * @param xddModel Instance of module XDD.
     */
    public ModuleManagement(Node node, ISO15745ProfileContainer xddModel) {
        setXddModel(xddModel);
    }

    /**
     * @return The interface list of modular node.
     */
    public List<Interface> getInterfacelist() {
        return interfacelist;
    }

    /**
     * @return Interface from the XDD model
     */
    public List<TInterfaceList.Interface> getInterfce() {
        return interfce;
    }

    /**
     * @return Modular head interface of connected module.
     */
    public InterfaceList getModularHeadInterface() {
        return modularHeadInterface;
    }

    /**
     * @return Interface of module from XDD instance.
     */
    public TModuleInterface getModuleInterface() {
        return moduleInterface;
    }

    /**
     * @return Module management from the XDD instance,
     */
    public TApplicationLayersModularHead.ModuleManagement getModuleManagement() {
        return moduleManagement;
    }

    /**
     * @return Model of module management from the XDD.
     */
    public TModuleManagement getModuleManagementModel() {
        return moduleManagementModel;
    }

    /**
     * Updates the XDD model of module into the modular node.
     *
     * @param xddModel XDD instance.
     */
    private void setXddModel(ISO15745ProfileContainer xddModel) {
        if (xddModel != null) {
            List<ISO15745Profile> profiles = xddModel.getISO15745Profile();
            for (ISO15745Profile profile : profiles) {
                ProfileBodyDataType profileBodyDatatype = profile
                        .getProfileBody();
                if (profileBodyDatatype instanceof ProfileBodyCommunicationNetworkPowerlinkModularHead) {
                    ProfileBodyCommunicationNetworkPowerlinkModularHead commProfile = (ProfileBodyCommunicationNetworkPowerlinkModularHead) profileBodyDatatype;
                    moduleManagement = commProfile.getApplicationLayers()
                            .getModuleManagement();
                    modularHeadInterface = moduleManagement.getInterfaceList();
                    interfacelist.addAll(modularHeadInterface.getInterface());
                }
                if (profileBodyDatatype instanceof ProfileBodyDevicePowerlinkModularHead) {
                    ProfileBodyDevicePowerlinkModularHead devProfile = (ProfileBodyDevicePowerlinkModularHead) profileBodyDatatype;
                    moduleManagementModel = devProfile.getDeviceManager()
                            .getModuleManagement();
                    moduleInterface = moduleManagementModel
                            .getModuleInterface();
                    devModuleInterfaceList = moduleManagementModel
                            .getInterfaceList();
                    interfce = devModuleInterfaceList.getInterface();

                }

            }
        }

    }
}

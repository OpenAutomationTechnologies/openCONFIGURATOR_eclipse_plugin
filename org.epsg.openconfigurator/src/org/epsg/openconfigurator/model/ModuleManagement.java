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
import org.epsg.openconfigurator.xmlbinding.xdd.TModuleInterface;
import org.epsg.openconfigurator.xmlbinding.xdd.TModuleManagement;

public class ModuleManagement {

    private TApplicationLayersModularHead.ModuleManagement moduleManagement;
    private TModuleManagement moduleManagementModel;
    private TModuleInterface moduleInterface;
    private InterfaceList modularHeadInterface;

    private Node node;

    List<Interface> interfacelist = new ArrayList<Interface>();

    public ModuleManagement(Node node, ISO15745ProfileContainer xddModel) {
        this.node = node;
        setXddModel(xddModel);
    }

    public List<Interface> getInterfacelist() {
        return interfacelist;
    }

    public InterfaceList getModularHeadInterface() {
        return modularHeadInterface;
    }

    public TModuleInterface getModuleInterface() {
        return moduleInterface;
    }

    public TApplicationLayersModularHead.ModuleManagement getModuleManagement() {
        return moduleManagement;
    }

    public TModuleManagement getModuleManagementModel() {
        return moduleManagementModel;
    }

    public void setInterfacelist(List<Interface> interfacelist) {
        this.interfacelist = interfacelist;
    }

    public void setModularHeadInterface(InterfaceList modularHeadInterface) {
        this.modularHeadInterface = modularHeadInterface;
    }

    public void setModuleInterface(TModuleInterface moduleInterface) {
        this.moduleInterface = moduleInterface;
    }

    public void setModuleManagement(
            TApplicationLayersModularHead.ModuleManagement moduleManagement) {
        this.moduleManagement = moduleManagement;
    }

    public void setModuleManagementModel(
            TModuleManagement moduleManagementModel) {
        this.moduleManagementModel = moduleManagementModel;
    }

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
                    System.out.println(
                            "The interface list available in modular head = "
                                    + interfacelist);

                }
                if (profileBodyDatatype instanceof ProfileBodyDevicePowerlinkModularHead) {
                    ProfileBodyDevicePowerlinkModularHead devProfile = (ProfileBodyDevicePowerlinkModularHead) profileBodyDatatype;
                    moduleManagementModel = devProfile.getDeviceManager()
                            .getModuleManagement();
                    moduleInterface = moduleManagementModel
                            .getModuleInterface();

                } else {
                    System.out.println("ERROR unhandled Profile body datatype"
                            + profileBodyDatatype);
                }
            }
        }

    }
}

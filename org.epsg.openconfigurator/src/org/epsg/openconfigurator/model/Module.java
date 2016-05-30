package org.epsg.openconfigurator.model;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.epsg.openconfigurator.event.NodePropertyChangeEvent;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectUtils;
import org.epsg.openconfigurator.xmlbinding.projectfile.InterfaceList;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745Profile;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745ProfileContainer;
import org.epsg.openconfigurator.xmlbinding.xdd.ModuleType;
import org.epsg.openconfigurator.xmlbinding.xdd.ModuleTypeList;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDataType;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDevicePowerlinkModularChild;
import org.epsg.openconfigurator.xmlbinding.xdd.TModuleInterface;
import org.jdom2.JDOMException;

public class Module {

    private PowerlinkRootNode rootNode;

    private IFile projectXml;

    private Object moduleModel;

    private Node node;

    private ISO15745ProfileContainer xddModel;

    private String moduleName;

    private int maxPosition;

    private String configurationError;

    private HeadNodeInterface interfaceObj;

    public Module() {
        rootNode = null;
        projectXml = null;
        moduleModel = null;
        node = null;
        xddModel = null;
        configurationError = "";
    }

    public Module(PowerlinkRootNode rootNode, IFile projectXml,
            Object nodeModel, Node node, ISO15745ProfileContainer xddModel,
            HeadNodeInterface interfaceObj) {
        if (nodeModel instanceof InterfaceList.Interface.Module) {
            this.rootNode = rootNode;
            this.projectXml = projectXml;
            moduleModel = nodeModel;
            this.node = node;
            this.xddModel = xddModel;
            this.interfaceObj = interfaceObj;

        } else {
            System.out.println("Other than child module");
        }

        if (xddModel == null) {
            configurationError = "XDD parse not successful";
        }

        if (nodeModel instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module module = (InterfaceList.Interface.Module) nodeModel;
            moduleName = module.getName();
        }
    }

    public String getAbsolutePathToXdc() {
        String pathToXdc = getProject().getLocation().toString();
        String xdcPath = getPathToXdc();
        pathToXdc = pathToXdc + IPath.SEPARATOR + xdcPath;
        return pathToXdc;
    }

    public int getAddress() {
        int address = 0;

        if (moduleModel == null) {
            return address;
        }

        if (moduleModel instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module module = (InterfaceList.Interface.Module) moduleModel;
            address = module.getAddress().intValue();
        }

        return address;
    }

    public HeadNodeInterface getInterfaceOfModule() {
        return interfaceObj;
    }

    public int getMaxPosition() {
        return maxPosition;
    }

    public TModuleInterface getModuleInterface() {
        if (xddModel != null) {
            List<ISO15745Profile> profiles = xddModel.getISO15745Profile();
            for (ISO15745Profile profile : profiles) {
                ProfileBodyDataType profileBodyDatatype = profile
                        .getProfileBody();
                if (profileBodyDatatype instanceof ProfileBodyDevicePowerlinkModularChild) {
                    ProfileBodyDevicePowerlinkModularChild modChild = (ProfileBodyDevicePowerlinkModularChild) profileBodyDatatype;
                    TModuleInterface modInterface = modChild.getDeviceManager()
                            .getModuleManagement().getModuleInterface();
                    return modInterface;
                }
            }
        }
        return null;
    }

    public Object getModuleModel() {
        return moduleModel;
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getModuleType() {
        ModuleTypeList moduletypeList = getModuleInterface()
                .getModuleTypeList();
        String moduleTypeName = StringUtils.EMPTY;
        if (moduletypeList != null) {
            List<ModuleType> moduleTypelist = moduletypeList.getModuleType();
            for (ModuleType module : moduleTypelist) {
                moduleTypeName = module.getType();
            }
        }
        return moduleTypeName;
    }

    public Node getNode() {
        return node;
    }

    public String getPathToXdc() {
        String pathToXdc = StringUtils.EMPTY;

        if (moduleModel == null) {
            return pathToXdc;
        }

        if (moduleModel instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module module = (InterfaceList.Interface.Module) moduleModel;
            pathToXdc = module.getPathToXDC();
        }

        return pathToXdc;
    }

    public int getPosition() {
        int position = 0;

        if (moduleModel == null) {
            return position;
        }

        if (moduleModel instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module module = (InterfaceList.Interface.Module) moduleModel;
            position = module.getPosition().intValue();
        }

        return position;
    }

    /**
     * @return Eclipse project associated with the module.
     */
    public IProject getProject() {
        return node.getProject();
    }

    public String getXpath() {

        String interfaceListTagXpath = node.getXpath() + "/oc:"
                + IControlledNodeProperties.INTERFACE_LIST_TAG;

        String interfaceXpath = interfaceListTagXpath + "/oc:"
                + IControlledNodeProperties.INTERFACE_TAG + "[@id='"
                + getInterfaceOfModule().getInterfaceUId() + "']" + "/oc:";

        interfaceXpath += IControlledNodeProperties.MODULE_TAG + "[@"
                + "position" + "='" + String.valueOf(getPosition()) + "']";

        return interfaceXpath;
    }

    public boolean isEnabled() {
        boolean enabled = true;

        if (moduleModel == null) {
            return enabled;
        }

        if (moduleModel instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module module = (InterfaceList.Interface.Module) moduleModel;
            enabled = module.isEnabled();
        }
        return enabled;
    }

    public void setError(String errorDescription) {
        configurationError = errorDescription;
    }

    public void setName(final String newName)
            throws JDOMException, IOException {
        OpenConfiguratorProjectUtils.updateModuleAttributeValue(this,
                IAbstractNodeProperties.MODULE_NAME_OBJECT, newName);

        if (moduleModel instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module module = (InterfaceList.Interface.Module) moduleModel;
            module.setName(newName);
            System.err.println(" ....1.....");
        } else {
            System.err.println("setName(newName); Unhandled node model type:"
                    + moduleModel);
        }

        rootNode.fireNodePropertyChanged(new NodePropertyChangeEvent(this));

    }

    /**
     * Set the path to the XDC. Example:
     * deviceConfiguration/000000_000Oplk_nodeId.xdc
     *
     * @param pathToXdc The relative path to the XDC.
     */
    public void setPathToXDC(final String pathToXdc) {
        if (moduleModel instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module module = (InterfaceList.Interface.Module) moduleModel;
            module.setPathToXDC(pathToXdc);

        }
    }

    public void setPosition(String value) throws JDOMException, IOException {
        OpenConfiguratorProjectUtils.updateModuleAttributeValue(this,
                IAbstractNodeProperties.MODULE_POSITION_OBJECT, value);
        int oldPosition = getPosition();
        System.err.println("oldPOsition..... .." + oldPosition);
        if (moduleModel instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module module = (InterfaceList.Interface.Module) moduleModel;
            int position = Integer.valueOf(value);
            module.setPosition(BigInteger.valueOf(position));
            getInterfaceOfModule().getModuleCollection().remove(oldPosition);
            getInterfaceOfModule().getModuleCollection().put(position, this);
        } else {
            System.err.println("Invalid module model.");
        }

        rootNode.fireNodePropertyChanged(new NodePropertyChangeEvent(this));
    }
}

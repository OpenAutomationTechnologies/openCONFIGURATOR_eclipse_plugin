/*******************************************************************************
 * @file   Module.java
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

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.epsg.openconfigurator.console.OpenConfiguratorMessageConsole;
import org.epsg.openconfigurator.event.NodePropertyChangeEvent;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.util.OpenConfiguratorLibraryUtils;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectUtils;
import org.epsg.openconfigurator.xmlbinding.projectfile.InterfaceList;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745Profile;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745ProfileContainer;
import org.epsg.openconfigurator.xmlbinding.xdd.ModuleType;
import org.epsg.openconfigurator.xmlbinding.xdd.ModuleTypeList;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDataType;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDevicePowerlinkModularChild;
import org.epsg.openconfigurator.xmlbinding.xdd.TModuleAddressingChild;
import org.epsg.openconfigurator.xmlbinding.xdd.TModuleInterface;
import org.jdom2.JDOMException;

/**
 * Describes the functional behavior of module.
 *
 * @author SreeHari
 *
 */
public class Module {

    private PowerlinkRootNode rootNode;

    private IFile projectXml;

    private Object moduleModel;

    private Node node;

    private ISO15745ProfileContainer xddModel;

    private final ObjectDictionary objectDictionary;

    private String moduleName;

    private int maxPosition;

    private int minPosition;

    private int minAddress;

    private int maxAddress;

    private int maxCount;

    private TModuleAddressingChild moduleAddressing;

    private String configurationError;

    private String xpath;

    private HeadNodeInterface interfaceObj;

    /**
     * Constructor that defines null values.
     */
    public Module() {
        rootNode = null;
        projectXml = null;
        moduleModel = null;
        node = null;
        xddModel = null;
        objectDictionary = null;
        configurationError = "";
    }

    /**
     * Constructor that constructs the module from the XDD.
     *
     * @param rootNode Instance of PowerlinkRootNode.
     * @param projectXml Project file instance to update the module.
     * @param nodeModel Instance of java model.
     * @param node Instance of node in which module is connected.
     * @param xddModel XDD instance of module.
     * @param interfaceObj Instance of interface in which module is connected.
     */
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

        xpath = "//plk:ApplicationProcess";

        objectDictionary = new ObjectDictionary(this, node, xddModel);

        if (nodeModel instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module module = (InterfaceList.Interface.Module) nodeModel;
            moduleName = module.getName();
        }
    }

    /**
     * Get error message on validating the module type while moving the modules.
     *
     * @param oldPosition The current position of the module to be moved.
     * @param newposition The new position to be updated
     * @return The error message of invalid module type.
     */
    public String errorOfMoveModuleDownPosition(int oldPosition,
            int newposition) {

        String preTypetoBeChecked = StringUtils.EMPTY;
        String prevModuleTypetoBeChecked = StringUtils.EMPTY;
        String nextonTypetoBeChecked = StringUtils.EMPTY;
        String nextTypetoBeChecked = StringUtils.EMPTY;

        Module currentModule = getInterfaceOfModule().getModuleCollection()
                .get(oldPosition);
        Module newModule = getInterfaceOfModule().getModuleCollection()
                .get(newposition);

        int prevModulePosition = currentModule
                .getPreviousModulePosition(oldPosition);
        int nextModulePosition = newModule.getNextModulePosition(newposition);

        Module nextModule = getInterfaceOfModule().getModuleCollection()
                .get(nextModulePosition);

        Module preModule = getInterfaceOfModule().getModuleCollection()
                .get(prevModulePosition);

        if (prevModulePosition != 0) {
            preTypetoBeChecked = preModule.getModuleInterface().getType();
            prevModuleTypetoBeChecked = newModule.getModuleType();
        }

        if (nextModulePosition != 0) {
            nextonTypetoBeChecked = currentModule.getModuleInterface()
                    .getType();
            nextTypetoBeChecked = nextModule.getModuleType();
        }

        if (!(nextonTypetoBeChecked.equalsIgnoreCase(nextTypetoBeChecked))) {
            return "Module cannot be moved. " + currentModule.getModuleName()
                    + " module does not match with type of module "
                    + nextModule.getModuleName();
        }

        if (!(preTypetoBeChecked.equalsIgnoreCase(prevModuleTypetoBeChecked))) {
            return "Module cannot be moved. " + preModule.getModuleName()
                    + " module does not match with type of module "
                    + newModule.getModuleName();
        }

        return StringUtils.EMPTY;
    }

    /**
     * Get error message on validating the module type while moving the modules.
     *
     * @param oldPosition The current position of the module to be moved.
     * @param newposition The new position to be updated
     * @return The error message of invalid module type.
     */
    public String errorOfMoveModuleUpPosition(int oldPosition,
            int newposition) {

        String nextTypetoBeChecked = StringUtils.EMPTY;
        String newModuleTypetoBeChecked = StringUtils.EMPTY;
        String previousTypetoBeChecked = StringUtils.EMPTY;
        String preModuleTypetoBeChecked = StringUtils.EMPTY;

        Module currentModule = getInterfaceOfModule().getModuleCollection()
                .get(oldPosition);
        Module newModule = getInterfaceOfModule().getModuleCollection()
                .get(newposition);

        int nextModulePosition = currentModule
                .getNextModulePosition(oldPosition);
        int previousModulePosition = newModule
                .getPreviousModulePosition(newposition);

        Module prevModule = getInterfaceOfModule().getModuleCollection()
                .get(previousModulePosition);

        Module nextModule = getInterfaceOfModule().getModuleCollection()
                .get(nextModulePosition);

        if (nextModulePosition != 0) {
            nextTypetoBeChecked = nextModule.getModuleInterface().getType();
            newModuleTypetoBeChecked = newModule.getModuleType();
        }

        if (previousModulePosition != 0) {
            previousTypetoBeChecked = currentModule.getModuleInterface()
                    .getType();
            preModuleTypetoBeChecked = prevModule.getModuleType();
        }

        if (!(previousTypetoBeChecked
                .equalsIgnoreCase(preModuleTypetoBeChecked))) {
            return "Module cannot be moved. " + currentModule.getModuleName()
                    + " module does not match with type of module "
                    + prevModule.getModuleName();
        }

        if (!(nextTypetoBeChecked.equalsIgnoreCase(newModuleTypetoBeChecked))) {
            return "Module cannot be moved. " + nextModule.getModuleName()
                    + " module does not match with type of module "
                    + newModule.getModuleName();
        }

        return StringUtils.EMPTY;
    }

    /**
     * @return The XDC path of module.
     */
    public String getAbsolutePathToXdc() {
        String pathToXdc = getProject().getLocation().toString();
        String xdcPath = getPathToXdc();
        pathToXdc = pathToXdc + IPath.SEPARATOR + xdcPath;
        return pathToXdc;
    }

    /**
     * @return the address of module.
     */
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

    /**
     * @return The Xpath of application process in module XDD.
     */
    public String getApplicationProcessXpath() {
        return xpath;
    }

    /**
     * @return The Module ID of module.
     */
    public String getChildID() {
        if (getModuleInterface() != null) {
            return getModuleInterface().getChildID();
        }
        return null;
    }

    /**
     * @return The Xpath of interface list in poroject file.
     */
    public String getInterfaceListTagXPath() {
        String interfaceListTagXpath = node.getXpath() + "/oc:"
                + IControlledNodeProperties.INTERFACE_LIST_TAG;
        return interfaceListTagXpath;
    }

    /**
     * @return The interface instance of module in which it is connected.
     */
    public HeadNodeInterface getInterfaceOfModule() {
        return interfaceObj;
    }

    /**
     * @return The Xpath of interface in project file.
     */
    public String getInterfaceTagXPath() {
        String interfaceListTagXpath = node.getXpath() + "/oc:"
                + IControlledNodeProperties.INTERFACE_LIST_TAG;
        String interfaceXpath = interfaceListTagXpath + "/oc:"
                + IControlledNodeProperties.INTERFACE_TAG + "[@id='"
                + getInterfaceOfModule().getInterfaceUId() + "']" + "/oc:";
        return interfaceXpath;
    }

    /**
     * @return The XDC model.
     */
    public ISO15745ProfileContainer getISO15745ProfileContainer() {
        return xddModel;
    }

    /**
     * @return Max address of module.
     */
    public int getMaxAddress() {

        if (getModuleInterface().getMaxAddress() != null) {
            maxAddress = getModuleInterface().getMaxAddress().intValue();
        }
        return maxAddress;
    }

    /**
     * @return the maximum count of module in the interface list.
     */
    public int getMaxCount() {
        maxCount = getModuleInterface().getMaxCount().intValue();
        return maxCount;
    }

    /**
     * @return The maximum position of module.
     */
    public int getMaxPosition() {
        if (getModuleInterface().getMaxPosition() != null) {
            maxPosition = getModuleInterface().getMaxPosition().intValue();
        } else {
            if (getInterfaceOfModule().getMaxModules() != null) {
                maxPosition = getInterfaceOfModule().getMaxModules().intValue();
            }
        }
        return maxPosition;
    }

    /**
     * @return Minimum address of module.
     */
    public int getMinAddress() {
        minAddress = getModuleInterface().getMinAddress().intValue();

        return minAddress;
    }

    /**
     * @return Minimum allowed position of module.
     */
    public int getMinPosition() {
        minPosition = getModuleInterface().getMinPosition().intValue();

        return minPosition;
    }

    /**
     * @return Value of module addressing in interface.
     */
    public TModuleAddressingChild getModuleAddressing() {
        moduleAddressing = getModuleInterface().getModuleAddressing();

        return moduleAddressing;
    }

    /**
     * @return The module interface in module.
     */
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

    /**
     * @return Model of module.
     */
    public Object getModuleModel() {
        return moduleModel;
    }

    /**
     * @return Name of Module.
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * @return Type of module that is connected to interface.
     */
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

    /**
     * @return The uniqueId of module.
     */
    public String getModuleUniqueId() {
        ModuleTypeList moduletypeList = getModuleInterface()
                .getModuleTypeList();
        String moduleTypeName = StringUtils.EMPTY;
        if (moduletypeList != null) {
            List<ModuleType> moduleTypelist = moduletypeList.getModuleType();
            for (ModuleType module : moduleTypelist) {
                moduleTypeName = module.getUniqueID();
            }
        }
        return moduleTypeName;
    }

    /**
     * @return Xpath of project file with respect to name of module.
     */
    public String getNameXpath() {

        String interfaceListTagXpath = node.getXpath() + "/oc:"
                + IControlledNodeProperties.INTERFACE_LIST_TAG;

        String interfaceXpath = interfaceListTagXpath + "/oc:"
                + IControlledNodeProperties.INTERFACE_TAG + "[@id='"
                + getInterfaceOfModule().getInterfaceUId() + "']" + "/oc:";

        interfaceXpath += IControlledNodeProperties.MODULE_TAG + "[@" + "name"
                + "='" + getModuleName() + "']";

        return interfaceXpath;
    }

    /**
     * Receives the next position of given module.
     *
     * @param position value of module position
     * @return next position of module.
     */
    public int getNextModulePosition(int position) {
        Set<Integer> positionCollections = getInterfaceOfModule()
                .getModuleCollection().keySet();
        int nextPosition = 0;
        for (Integer positionAvailable : positionCollections) {
            if (positionAvailable > position) {
                nextPosition = positionAvailable;
                break;
            }
        }
        return nextPosition;
    }

    /**
     * @return Instance of Node.
     */
    public Node getNode() {
        return node;
    }

    /**
     * @return Instance of Module object dictionary.
     */
    public ObjectDictionary getObjectDictionary() {
        return objectDictionary;
    }

    /**
     * @return The path of module XDC.
     */
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

    /**
     * @return Position of module
     */
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
     * Receives the previous position of given module.
     *
     * @param position Position of module
     * @return previous position of module.
     */
    public int getPreviousModulePosition(int position) {
        Set<Integer> positionCollections = getInterfaceOfModule()
                .getModuleCollection().keySet();
        int previousPosition = 0;
        for (Integer positionAvailable : positionCollections) {
            if (positionAvailable < position) {
                previousPosition = positionAvailable;
            }
        }
        return previousPosition;
    }

    /**
     * @return Eclipse project associated with the module.
     */
    public IProject getProject() {
        return node.getProject();
    }

    /**
     * @return Xpath of project file.
     */
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

    /**
     * @return <true> if module is enabled, <false> if disabled.
     */
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

    /**
     * Moves the module based on given position.
     *
     * @param newPosition position to be moved
     * @param oldPosition current position of module.
     */
    public void moveModule(int newPosition, int oldPosition) {

        Set<Integer> positionSet = getInterfaceOfModule().getModuleCollection()
                .keySet();

        if (newPosition > oldPosition) {
            List<Integer> positiontoBeMoved = new ArrayList<Integer>();
            for (Integer position : positionSet) {
                if (position >= oldPosition) {
                    if (position <= newPosition) {
                        positiontoBeMoved.add(position);
                    }
                }
            }
            System.err.println(
                    "Position to be moved...for new position greater than older..."
                            + positiontoBeMoved);
            for (Integer positionMove : positiontoBeMoved) {
                if (getPreviousModulePosition(positionMove) != 0) {
                    int newPositionToBeMoved = getPreviousModulePosition(
                            positionMove);
                    System.err.println("The new  previous position.."
                            + newPositionToBeMoved);
                    try {
                        setPosition(String.valueOf(newPositionToBeMoved));
                    } catch (JDOMException | IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

        } else if (newPosition < oldPosition) {
            List<Integer> positiontoBeMoved = new ArrayList<Integer>();
            for (Integer position : positionSet) {
                if (position <= oldPosition) {
                    if (position >= newPosition) {
                        positiontoBeMoved.add(position);
                    }
                }
            }
            System.err.println(
                    "Position to be moved..for new position less than older..."
                            + positiontoBeMoved);
            for (Integer positionMove : positiontoBeMoved) {
                if (getNextModulePosition(positionMove) != 0) {
                    int newPositionToBeMoved = getNextModulePosition(
                            positionMove);
                    System.err.println(
                            "The new  next position.." + newPositionToBeMoved);
                    try {
                        setPosition(String.valueOf(newPositionToBeMoved));
                    } catch (JDOMException | IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

        }

    }

    /**
     * Sets the address value to the module.
     *
     * @param value Address to be updated.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public void setAddress(String value) throws JDOMException, IOException {

        OpenConfiguratorProjectUtils.updateModuleAttributeValue(this,
                IAbstractNodeProperties.MODULE_ADDRESS_OBJECT, value);
        int oldAddress = getAddress();
        System.err.println("oldAddress..... .." + oldAddress);
        if (moduleModel instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module module = (InterfaceList.Interface.Module) moduleModel;
            int address = Integer.valueOf(value);
            module.setAddress(BigInteger.valueOf(address));
            getInterfaceOfModule().getAddressCollection().remove(oldAddress);
            getInterfaceOfModule().getAddressCollection().put(address, this);
        } else {
            System.err.println("Invalid module model.");
        }

        rootNode.fireNodePropertyChanged(new NodePropertyChangeEvent(this));

        Result res = new Result();

        res = OpenConfiguratorLibraryUtils.setModuleAddress(this);
        if (!res.IsSuccessful()) {
            OpenConfiguratorMessageConsole.getInstance()
                    .printLibraryErrorMessage(res);
            return;
        }

    }

    public void setAddresss(String value) throws JDOMException, IOException {

        OpenConfiguratorProjectUtils.swapModuleAttributeValue(this,
                IAbstractNodeProperties.MODULE_ADDRESS_OBJECT, value);
        int oldAddress = getAddress();
        System.err.println("oldAddress..... .." + oldAddress);
        if (moduleModel instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module module = (InterfaceList.Interface.Module) moduleModel;
            int address = Integer.valueOf(value);
            module.setAddress(BigInteger.valueOf(address));
            getInterfaceOfModule().getAddressCollection().remove(oldAddress);
            getInterfaceOfModule().getAddressCollection().put(address, this);
        } else {
            System.err.println("Invalid module model.");
        }

        rootNode.fireNodePropertyChanged(new NodePropertyChangeEvent(this));

    }

    /**
     * Updates the enable/disable state of module.
     *
     * @param enabled <true> if enabled, <false> if otherwise.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public void setEnabled(boolean enabled) throws JDOMException, IOException {

        Result res = new Result();

        res = OpenConfiguratorLibraryUtils.toggleEnableDisable(this);
        if (!res.IsSuccessful()) {
            OpenConfiguratorMessageConsole.getInstance()
                    .printLibraryErrorMessage(res);
            return;
        }

        if (moduleModel instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module module = (InterfaceList.Interface.Module) moduleModel;
            OpenConfiguratorProjectUtils.updateModuleAttributeValue(this,
                    "enabled", String.valueOf(enabled));
            module.setEnabled(enabled);
        }
    }

    /**
     * Sets the error flag to the module
     *
     * @param errorDescription The error message of error flag.
     */
    public void setError(String errorDescription) {
        configurationError = errorDescription;
    }

    /**
     * Updates the enabled and disabled state of module.
     *
     * @param enabled <true> if module enabled, <false> if disabled.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public void setModuleEnabled(boolean enabled)
            throws JDOMException, IOException {
        if (moduleModel instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module module = (InterfaceList.Interface.Module) moduleModel;
            OpenConfiguratorProjectUtils.updateModuleAttributeValue(this,
                    "enabled", String.valueOf(enabled));
            module.setEnabled(enabled);
        }
    }

    /**
     * Updates the name to the module.
     *
     * @param newName Name to be updated into module.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public void setName(final String newName)
            throws JDOMException, IOException {

        OpenConfiguratorProjectUtils.updateModuleAttributeValue(this,
                IAbstractNodeProperties.MODULE_NAME_OBJECT, newName);

        if (moduleModel instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module module = (InterfaceList.Interface.Module) moduleModel;
            module.setName(newName);

        } else {
            System.err.println("setName(newName); Unhandled node model type:"
                    + moduleModel);
        }

        OpenConfiguratorProjectUtils.updateModuleAttributeValue(this,
                IAbstractNodeProperties.MODULE_NAME_OBJECT, newName);

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

        } else {
            System.err.println("Invalid module model.");
        }
    }

    /**
     * Updates the position of module.
     *
     * @param value The position to be updated.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public void setPosition(String value) throws JDOMException, IOException {

        Result res = new Result();
        boolean validPosition = true;
        int newPosition = Integer.valueOf(value);
        int oldPosition = getPosition();
        int previousPosition = 0;
        int nextPosition = 0;
        Set<Integer> positionSet = getInterfaceOfModule().getModuleCollection()
                .keySet();

        for (Integer position : positionSet) {

            if (position < newPosition) {
                if (position != oldPosition) {
                    previousPosition = position;
                }
            }

            if (position > newPosition) {
                if (position != oldPosition) {
                    nextPosition = position;
                    break;
                }
            }

        }

        Module currentModule = getInterfaceOfModule().getModuleCollection()
                .get(oldPosition);

        String currentToPreviousModuleType = currentModule.getModuleInterface()
                .getType();
        String currentToNextModuleType = currentModule.getModuleType();

        System.err.println("PreviousPOsition11......" + previousPosition);
        System.err.println("NextPOsition13......" + nextPosition);

        if (previousPosition != 0) {
            Module previousModule = getInterfaceOfModule().getModuleCollection()
                    .get(previousPosition);
            String previousModuleType = previousModule.getModuleType();
            System.err.println(
                    "Previous mOdule type15....." + previousModuleType);
            System.err.println(
                    "Next module type16......" + currentToPreviousModuleType);
            if (previousModuleType.equals(currentToPreviousModuleType)) {
                validPosition = true;
                if (nextPosition != 0) {
                    Module nextModule = getInterfaceOfModule()
                            .getModuleCollection().get(nextPosition);
                    String nextModuleType = nextModule.getModuleInterface()
                            .getType();
                    System.err.println("Equals123......");
                    System.err.println("current module type ......"
                            + currentToNextModuleType);
                    System.err.println(
                            "Next module type ......" + nextModuleType);
                    if ((currentToNextModuleType.equals(nextModuleType))) {
                        System.err.println("Equals......");
                        validPosition = true;
                    } else {
                        System.err.println("Equals245......");
                        System.err.println("Not Equals......");
                        validPosition = false;
                    }
                }
                System.err.println("Eneteref into critical section....");
            } else {
                validPosition = false;
            }
        } else {
            if (nextPosition != 0) {
                Module nextModule = getInterfaceOfModule().getModuleCollection()
                        .get(nextPosition);
                String nextModuleType = nextModule.getModuleInterface()
                        .getType();

                if ((currentToNextModuleType.equals(nextModuleType))) {
                    System.err.println("Equals......");
                    validPosition = true;
                } else {
                    System.err.println("Equals245......");
                    System.err.println("Not Equals......");
                    validPosition = false;
                }
            }
        }
        System.err.println("Valid position = " + validPosition);
        if (!validPosition) {
            MessageDialog dialog = new MessageDialog(null,
                    "Modify module position", null,
                    "Modifying the position of module will disable the modules that does not match the module type.'",
                    MessageDialog.QUESTION, new String[] { "Yes", "No" }, 1);
            int result = dialog.open();
            if (result == 0) {
                OpenConfiguratorProjectUtils.updateModuleAttributeValue(this,
                        IAbstractNodeProperties.MODULE_POSITION_OBJECT, value);
                System.err.println("oldPOsition..... .." + oldPosition);
                if (moduleModel instanceof InterfaceList.Interface.Module) {
                    InterfaceList.Interface.Module module = (InterfaceList.Interface.Module) moduleModel;
                    int position = Integer.valueOf(value);

                    res = OpenConfiguratorLibraryUtils.moveModule(this,
                            oldPosition, position);
                    if (res.IsSuccessful()) {
                        module.setPosition(BigInteger.valueOf(position));
                        getInterfaceOfModule().getModuleCollection()
                                .remove(oldPosition);
                        getInterfaceOfModule().getModuleCollection()
                                .put(position, this);
                        if (String.valueOf(getInterfaceOfModule()
                                .getModuleAddressing()) == "POSITION") {
                            setAddress(value);
                        }
                    } else {
                        OpenConfiguratorMessageConsole.getInstance()
                                .printLibraryErrorMessage(res);
                    }
                } else {
                    System.err.println("Invalid module model.");
                }

                rootNode.fireNodePropertyChanged(
                        new NodePropertyChangeEvent(this));

                Module moduleTobeDisabled = getInterfaceOfModule()
                        .getModuleCollection().get(Integer.valueOf(value));
                moduleTobeDisabled.setEnabled(false);
                validatePreviousPosition(oldPosition);
                return;
            } else {
                return;
            }
        } else {
            OpenConfiguratorProjectUtils.updateModuleAttributeValue(this,
                    IAbstractNodeProperties.MODULE_POSITION_OBJECT, value);
            System.err.println("oldPOsition..... .." + oldPosition);
            if (moduleModel instanceof InterfaceList.Interface.Module) {
                InterfaceList.Interface.Module module = (InterfaceList.Interface.Module) moduleModel;
                int position = Integer.valueOf(value);

                res = OpenConfiguratorLibraryUtils.moveModule(this, oldPosition,
                        position);
                if (res.IsSuccessful()) {
                    module.setPosition(BigInteger.valueOf(position));
                    getInterfaceOfModule().getModuleCollection()
                            .remove(oldPosition);
                    getInterfaceOfModule().getModuleCollection().put(position,
                            this);
                    if (String.valueOf(getInterfaceOfModule()
                            .getModuleAddressing()) == "POSITION") {
                        setAddress(value);
                    }
                } else {
                    OpenConfiguratorMessageConsole.getInstance()
                            .printLibraryErrorMessage(res);

                }
            } else {
                System.err.println("Invalid module model.");
            }

            rootNode.fireNodePropertyChanged(new NodePropertyChangeEvent(this));

            validatePreviousPosition(oldPosition);

        }
    }

    /**
     * Updates the position of module
     *
     * @param value new position to be updated.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public void setPositions(String value) throws JDOMException, IOException {
        Result res = new Result();
        OpenConfiguratorProjectUtils.updateModuleAttributeValue(this,
                IAbstractNodeProperties.MODULE_POSITION_OBJECT, value);
        int oldPosition = getPosition();
        int position = Integer.valueOf(value);
        System.err.println("oldPOsition@#@$..... .." + oldPosition);
        System.err.println("NewPOsition@$#$..... .." + position);
        if (moduleModel instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module module = (InterfaceList.Interface.Module) moduleModel;

            res = OpenConfiguratorLibraryUtils.moveModule(this, oldPosition,
                    position);
            if (res.IsSuccessful()) {
                module.setPosition(BigInteger.valueOf(position));
                getInterfaceOfModule().getModuleCollection()
                        .remove(oldPosition);
                getInterfaceOfModule().getModuleCollection().put(position,
                        this);
                if (String.valueOf(getInterfaceOfModule()
                        .getModuleAddressing()) == "POSITION") {
                    setAddress(value);
                }
            } else {
                OpenConfiguratorMessageConsole.getInstance()
                        .printLibraryErrorMessage(res);

            }
        } else {
            System.err.println("Invalid module model.");
        }

        rootNode.fireNodePropertyChanged(new NodePropertyChangeEvent(this));

    }

    /**
     * Swap the modules based on the given position for addressing scheme manual
     *
     * @param oldPosition Current position to be moved
     * @param position New position to be updated
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public void swapManualPosition(int oldPosition, int position)
            throws IOException, JDOMException {
        Result res = OpenConfiguratorLibraryUtils.moveModule(this, oldPosition,
                position);
        if (!res.IsSuccessful()) {
            OpenConfiguratorMessageConsole.getInstance()
                    .printLibraryErrorMessage(res);
        }

        OpenConfiguratorProjectUtils.swapModuleAttributeValue(this,
                IAbstractNodeProperties.MODULE_POSITION_OBJECT,
                String.valueOf(position));

        Module newModule = getInterfaceOfModule().getModuleCollection()
                .get(position);

        OpenConfiguratorProjectUtils.swapModuleAttributeValue(newModule,
                IAbstractNodeProperties.MODULE_POSITION_OBJECT,
                String.valueOf(oldPosition));

        Object moduleObjModel = getModuleModel();
        if (moduleObjModel instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module moduleModelObj = (InterfaceList.Interface.Module) moduleObjModel;
            moduleModelObj.setPosition(BigInteger.valueOf(position));
            getInterfaceOfModule().getModuleCollection().put(position, this);
        }

        try {
            getProject().refreshLocal(IResource.DEPTH_INFINITE,
                    new NullProgressMonitor());
        } catch (CoreException e) {
            System.err.println("unable to refresh the resource due to "
                    + e.getCause().getMessage());
        }

        Object newmoduleObjModel = newModule.getModuleModel();
        if (newmoduleObjModel instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module newmoduleModelObj = (InterfaceList.Interface.Module) newmoduleObjModel;
            newmoduleModelObj.setPosition(BigInteger.valueOf(oldPosition));
            getInterfaceOfModule().getModuleCollection().put(oldPosition,
                    newModule);
        }
    }

    /**
     * Swap the modules based on the given position for addressing scheme
     * position.
     *
     * @param oldPosition Current position to be moved
     * @param position New position to be updated
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public void swapPosition(int oldPosition, int position)
            throws JDOMException, IOException {

        Result res = OpenConfiguratorLibraryUtils.moveModule(this, oldPosition,
                position);
        if (!res.IsSuccessful()) {
            OpenConfiguratorMessageConsole.getInstance()
                    .printLibraryErrorMessage(res);
        }

        OpenConfiguratorProjectUtils.swapModuleAttributeValue(this,
                IAbstractNodeProperties.MODULE_POSITION_OBJECT,
                String.valueOf(position));
        setAddresss(String.valueOf(position));

        Module newModule = getInterfaceOfModule().getModuleCollection()
                .get(position);

        OpenConfiguratorProjectUtils.swapModuleAttributeValue(newModule,
                IAbstractNodeProperties.MODULE_POSITION_OBJECT,
                String.valueOf(oldPosition));

        newModule.setAddresss(String.valueOf(oldPosition));

        Object moduleObjModel = getModuleModel();
        if (moduleObjModel instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module moduleModelObj = (InterfaceList.Interface.Module) moduleObjModel;
            moduleModelObj.setPosition(BigInteger.valueOf(position));

            getInterfaceOfModule().getModuleCollection().put(position, this);
        }

        Object newmoduleObjModel = newModule.getModuleModel();
        if (newmoduleObjModel instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module newmoduleModelObj = (InterfaceList.Interface.Module) newmoduleObjModel;
            newmoduleModelObj.setPosition(BigInteger.valueOf(oldPosition));

            getInterfaceOfModule().getModuleCollection().put(oldPosition,
                    newModule);
        }

        try {
            newModule.getProject().refreshLocal(IResource.DEPTH_INFINITE,
                    new NullProgressMonitor());
        } catch (CoreException e) {
            System.err.println("unable to refresh the resource due to "
                    + e.getCause().getMessage());
        }

    }

    /**
     * Validate the type of module on moving the module to its next position
     *
     * @param oldPosition The current position to be moved
     * @param newposition The new position of module to be updated.
     * @return <code>true</code> if module type is valid, <code>false</code> if
     *         invalid.
     */
    public boolean validateMoveModuleDownPosition(int oldPosition,
            int newposition) {
        boolean validModulePosition = true;

        String preTypetoBeChecked = StringUtils.EMPTY;
        String prevModuleTypetoBeChecked = StringUtils.EMPTY;
        String nextonTypetoBeChecked = StringUtils.EMPTY;
        String nextTypetoBeChecked = StringUtils.EMPTY;

        Module currentModule = getInterfaceOfModule().getModuleCollection()
                .get(oldPosition);
        Module newModule = getInterfaceOfModule().getModuleCollection()
                .get(newposition);

        int prevModulePosition = currentModule
                .getPreviousModulePosition(oldPosition);
        int nextModulePosition = newModule.getNextModulePosition(newposition);

        Module nextModule = getInterfaceOfModule().getModuleCollection()
                .get(nextModulePosition);

        Module preModule = getInterfaceOfModule().getModuleCollection()
                .get(prevModulePosition);

        if (prevModulePosition != 0) {
            preTypetoBeChecked = preModule.getModuleInterface().getType();
            prevModuleTypetoBeChecked = newModule.getModuleType();
        }

        if (nextModulePosition != 0) {
            nextonTypetoBeChecked = currentModule.getModuleInterface()
                    .getType();
            nextTypetoBeChecked = nextModule.getModuleType();
        }

        if (nextonTypetoBeChecked.equalsIgnoreCase(nextTypetoBeChecked)
                && preTypetoBeChecked
                        .equalsIgnoreCase(prevModuleTypetoBeChecked)) {
            validModulePosition = true;
        } else {
            validModulePosition = false;
        }
        System.err.println("Valid Module position.. " + validModulePosition);
        return validModulePosition;
    }

    /**
     * Validate the type of module on moving the module to its previous position
     *
     * @param oldPosition The current position to be moved
     * @param newposition The new position of module to be updated.
     * @return <code>true</code> if module type is valid, <code>false</code> if
     *         invalid.
     */
    public boolean validateMoveModuleUpPosition(int oldPosition,
            int newposition) {
        boolean validModulePosition = true;

        String nextTypetoBeChecked = StringUtils.EMPTY;
        String newModuleTypetoBeChecked = StringUtils.EMPTY;
        String previousTypetoBeChecked = StringUtils.EMPTY;
        String preModuleTypetoBeChecked = StringUtils.EMPTY;

        Module currentModule = getInterfaceOfModule().getModuleCollection()
                .get(oldPosition);
        Module newModule = getInterfaceOfModule().getModuleCollection()
                .get(newposition);

        int nextModulePosition = currentModule
                .getNextModulePosition(oldPosition);
        int previousModulePosition = newModule
                .getPreviousModulePosition(newposition);

        Module prevModule = getInterfaceOfModule().getModuleCollection()
                .get(previousModulePosition);

        Module nextModule = getInterfaceOfModule().getModuleCollection()
                .get(nextModulePosition);

        if (nextModulePosition != 0) {
            nextTypetoBeChecked = nextModule.getModuleInterface().getType();
            newModuleTypetoBeChecked = newModule.getModuleType();
        }

        if (previousModulePosition != 0) {
            previousTypetoBeChecked = currentModule.getModuleInterface()
                    .getType();
            preModuleTypetoBeChecked = prevModule.getModuleType();
        }

        if (previousTypetoBeChecked.equalsIgnoreCase(preModuleTypetoBeChecked)
                && nextTypetoBeChecked
                        .equalsIgnoreCase(newModuleTypetoBeChecked)) {
            validModulePosition = true;
        } else {
            validModulePosition = false;
        }
        System.err.println("Valid Module position.. " + validModulePosition);
        return validModulePosition;
    }

    /**
     * Validated the module type in the module list based on the modification in
     * position
     *
     * @param newPosition Position to be validatd.
     *
     * @return <true> if module type matches in new position, <false> if module
     *         type does not match.
     */
    public boolean validateNewPosition(Integer newPosition) {
        Set<Integer> positionSet = getInterfaceOfModule().getModuleCollection()
                .keySet();
        boolean validPosition = true;
        int previousPosition = 0;
        int nextPosition = 0;
        for (Integer position : positionSet) {
            if (newPosition == position) {
                if (position < newPosition) {
                    previousPosition = position;
                }

                if (position > newPosition) {
                    nextPosition = position;
                    break;
                }
            }
        }

        Module currentModule = getInterfaceOfModule().getModuleCollection()
                .get(newPosition);

        String currentToPreviousModuleType = currentModule.getModuleInterface()
                .getType();
        String currentToNextModuleType = currentModule.getModuleType();

        if (previousPosition != 0) {
            Module previousModule = getInterfaceOfModule().getModuleCollection()
                    .get(previousPosition);
            String previousModuleType = previousModule.getModuleType();
            System.err.println(
                    "Previous mOdule type15....." + previousModuleType);
            System.err.println(
                    "Next module type16......" + currentToPreviousModuleType);
            if (previousModuleType.equals(currentToPreviousModuleType)) {
                validPosition = true;
                if (nextPosition != 0) {
                    Module nextModule = getInterfaceOfModule()
                            .getModuleCollection().get(nextPosition);
                    String nextModuleType = nextModule.getModuleInterface()
                            .getType();
                    System.err.println("Equals123......");
                    System.err.println("current module type ......"
                            + currentToNextModuleType);
                    System.err.println(
                            "Next module type ......" + nextModuleType);
                    if ((currentToNextModuleType.equals(nextModuleType))) {
                        System.err.println("Equals......");
                        validPosition = true;
                    } else {
                        System.err.println("Equals245......");
                        System.err.println("Not Equals......");
                        validPosition = false;
                    }
                }
                System.err.println("Eneteref into critical section....");
            } else {
                validPosition = false;
            }
        } else {
            if (nextPosition != 0) {
                Module nextModule = getInterfaceOfModule().getModuleCollection()
                        .get(nextPosition);
                String nextModuleType = nextModule.getModuleInterface()
                        .getType();

                if ((currentToNextModuleType.equals(nextModuleType))) {
                    System.err.println("Equals......");
                    validPosition = true;
                } else {
                    System.err.println("Equals245......");
                    System.err.println("Not Equals......");
                    validPosition = false;
                }
            }
        }
        return validPosition;

    }

    /**
     * Validates the previous position of module based on given position.
     *
     * @param oldPosition old position of module.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    private void validatePreviousPosition(Integer oldPosition)
            throws JDOMException, IOException {
        int veryOldPosition = 0;
        int veryNextPosition = 0;
        Set<Integer> positionSet = getInterfaceOfModule().getModuleCollection()
                .keySet();
        List<Integer> positionToBeChecked = new ArrayList<>();
        for (Integer position : positionSet) {
            if (position < oldPosition) {
                Module mod = getInterfaceOfModule().getModuleCollection()
                        .get(position);
                if (mod.isEnabled()) {
                    veryOldPosition = position;
                }
            }

            if (position > oldPosition) {
                positionToBeChecked.add(position);
            }
        }

        if (veryOldPosition != 0) {
            Module previousModule = getInterfaceOfModule().getModuleCollection()
                    .get(veryOldPosition);
            String previousModuleType = previousModule.getModuleType();

            if (positionToBeChecked != null) {
                for (Integer nextPosition : positionToBeChecked) {
                    Module nextModule = getInterfaceOfModule()
                            .getModuleCollection().get(nextPosition);
                    String nextModuleType = nextModule.getModuleInterface()
                            .getType();
                    System.err.println(
                            "Previous mOdule type..." + previousModuleType);
                    System.err.println(
                            "Next module type ......" + nextModuleType);
                    if (nextModule.isEnabled()) {
                        if ((previousModuleType.equals(nextModuleType))) {
                            System.err.println("Equals......");
                            return;
                        } else {
                            System.err.println("Equals245......");
                            System.err.println("Not Equals......");
                            nextModule.setEnabled(false);
                        }
                    }
                }
            }

        }

    }

}

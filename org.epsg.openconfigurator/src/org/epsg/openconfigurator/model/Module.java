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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
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
import org.epsg.openconfigurator.xmlbinding.projectfile.InterfaceList.Interface.Module.ForcedObjects;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745Profile;
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745ProfileContainer;
import org.epsg.openconfigurator.xmlbinding.xdd.ModuleType;
import org.epsg.openconfigurator.xmlbinding.xdd.ModuleTypeList;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDataType;
import org.epsg.openconfigurator.xmlbinding.xdd.ProfileBodyDevicePowerlinkModularChild;
import org.epsg.openconfigurator.xmlbinding.xdd.TDataTypeList;
import org.epsg.openconfigurator.xmlbinding.xdd.TModuleAddressingChild;
import org.epsg.openconfigurator.xmlbinding.xdd.TModuleInterface;
import org.epsg.openconfigurator.xmlbinding.xdd.TVersion;
import org.jdom2.JDOMException;

/**
 * Describes the functional behavior of module.
 *
 * @author SreeHari
 *
 */
public class Module {

    public static final String MOVE_MODULE_ERROR = "Module cannot be moved because {0} with module type {1} does not match {2} module type {3}.";
    public static final String MOVE_MODULE_INTERFACE_ERROR = "Module cannot be moved because {0} with interface type {1} does not match {2} module type {3}.";

    private static final long IDENTLIST_OBJECT_INDEX = 4135;

    private static final long DOWNLOAD_CHILD_OBJECT_INDEX = 8021;

    private static String getValueofModularBit(String reverse) {
        String[] arrayString = reverse.split("");
        int arrayCount = arrayString.length;
        if (arrayCount <= 21) {
            return "0";
        }
        return arrayString[21];

    }

    private static boolean isModuleFirmwareBitSet(String defaultVal) {
        if (defaultVal.contains("0x")) {
            defaultVal = defaultVal.substring(2);
            String binValue = new BigInteger(defaultVal, 16).toString(2);
            String reverse = new StringBuffer(binValue).reverse().toString();
            if (getValueofModularBit(reverse).equalsIgnoreCase("1")) {
                return true;
            }
        } else {
            String binValue = new BigInteger(defaultVal, 16).toString(2);
            String reverse = new StringBuffer(binValue).reverse().toString();
            if (getValueofModularBit(reverse).equalsIgnoreCase("1")) {
                return true;
            }
        }
        return false;
    }

    private static void removeForcedObject(ForcedObjects forcedObjTag,
            org.epsg.openconfigurator.xmlbinding.projectfile.Object forceObj) {
        org.epsg.openconfigurator.xmlbinding.projectfile.Object tempForcedObjToBeRemoved = null;
        for (org.epsg.openconfigurator.xmlbinding.projectfile.Object tempForceObj : forcedObjTag
                .getObject()) {

            if (java.util.Arrays.equals(tempForceObj.getIndex(),
                    forceObj.getIndex())) {
                if (forceObj.getSubindex() == null) {
                    tempForcedObjToBeRemoved = tempForceObj;
                    break;
                }
                if (java.util.Arrays.equals(tempForceObj.getSubindex(),
                        forceObj.getSubindex())) {
                    tempForcedObjToBeRemoved = tempForceObj;
                    break;
                }
            }
        }

        if (tempForcedObjToBeRemoved != null) {
            forcedObjTag.getObject().remove(tempForcedObjToBeRemoved);
        }

    }

    /**
     * XDD instance of firmware.
     */
    private FirmwareFile xddFirmwareFile;

    private PowerlinkRootNode rootNode;

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

    private Map<FirmwareManager, Integer> moduleFirmwareCollection = new HashMap<>();

    /**
     * Constructor that defines null values.
     */
    public Module() {
        rootNode = null;
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
            // Implemented for future enhancement.
            // this.projectXml = projectXml;
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
        if (xddModel != null) {
            xddFirmwareFile = new FirmwareFile(xddModel);
        }
        if (nodeModel instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module module = (InterfaceList.Interface.Module) nodeModel;
            moduleName = module.getName();
        }
    }

    /**
     * Validates the firmware manager support to module
     *
     * @param selectedNodeOrModule Object instance of module
     * @return <code>true</code> if firmware can be added , <code>false</code>
     *         otherwise.
     */
    public boolean canFirmwareAdded(Object selectedNodeOrModule) {
        boolean canFirmwareAdded = false;
        if (selectedNodeOrModule instanceof Module) {
            Module module = (Module) selectedNodeOrModule;
            Node modularHeadNode = module.getNode();
            PowerlinkObject obj = modularHeadNode.getObjectDictionary()
                    .getObject(8066);
            if (obj != null) {
                String defaultVal = obj.getActualDefaultValue();
                if (!defaultVal.equalsIgnoreCase(StringUtils.EMPTY)) {
                    canFirmwareAdded = isModuleFirmwareBitSet(defaultVal);
                }
                if (canFirmwareAdded) {
                    PowerlinkObject identListObj = modularHeadNode
                            .getObjectDictionary()
                            .getObject(IDENTLIST_OBJECT_INDEX);
                    if (identListObj != null) {
                        PowerlinkSubobject identListSubobj = identListObj
                                .getSubObject((short) 01);
                        if (identListSubobj != null) {
                            String identListVal = identListSubobj
                                    .getActualDefaultValue();
                            if (identListVal.contains("0x")) {
                                identListVal = identListVal.substring(2);
                            }
                            PowerlinkObject identObj = modularHeadNode
                                    .getObjectDictionary().getObject(
                                            Integer.parseInt(identListVal, 16));
                            if (identObj != null) {
                                PowerlinkObject dowmnloadChildObj = modularHeadNode
                                        .getObjectDictionary()
                                        .getObject(DOWNLOAD_CHILD_OBJECT_INDEX);
                                if (dowmnloadChildObj != null) {
                                    PowerlinkSubobject dowmnloadChildSubObj = dowmnloadChildObj
                                            .getSubObject((short) 01);
                                    if (dowmnloadChildSubObj != null) {
                                        String downloadChildVal = dowmnloadChildSubObj
                                                .getActualDefaultValue();
                                        if (downloadChildVal.contains("0x")) {
                                            downloadChildVal = downloadChildVal
                                                    .substring(2);
                                        }
                                        PowerlinkObject childObj = modularHeadNode
                                                .getObjectDictionary()
                                                .getObject(Integer.parseInt(
                                                        downloadChildVal, 16));
                                        if (childObj != null) {
                                            return true;
                                        }

                                    } else {

                                        OpenConfiguratorMessageConsole
                                                .getInstance()
                                                .printErrorMessage(
                                                        "To support module firmware update the object '0x1F55/0x01' has to be present on the node '"
                                                                + modularHeadNode
                                                                        .getNodeIDWithName()
                                                                + "'. ",
                                                        node.getProject()
                                                                .getName());
                                        return false;
                                    }
                                } else {
                                    OpenConfiguratorMessageConsole.getInstance()
                                            .printErrorMessage(
                                                    "To support module firmware update the object '0x1F55' has to be present on the node '"
                                                            + modularHeadNode
                                                                    .getNodeIDWithName()
                                                            + "'. ",
                                                    node.getProject()
                                                            .getName());
                                    return false;
                                }
                            } else {
                                OpenConfiguratorMessageConsole.getInstance()
                                        .printErrorMessage(
                                                "To support module firmware update the object '0x"
                                                        + identListVal
                                                        + "' has to be present on the node '"
                                                        + modularHeadNode
                                                                .getNodeIDWithName()
                                                        + "'. ",
                                                node.getProject().getName());
                                return false;
                            }

                        } else {
                            OpenConfiguratorMessageConsole.getInstance()
                                    .printErrorMessage(
                                            "To support module firmware update the object '0x1027/0x01' has to be present on the node '"
                                                    + modularHeadNode
                                                            .getNodeIDWithName()
                                                    + "'. ",
                                            node.getProject().getName());
                            return false;
                        }
                    } else {
                        OpenConfiguratorMessageConsole.getInstance()
                                .printErrorMessage(
                                        "To support module firmware update the object '0x1027' has to be present on the node '"
                                                + modularHeadNode
                                                        .getNodeIDWithName()
                                                + "'. ",
                                        node.getProject().getName());
                        return false;
                    }
                } else {
                    OpenConfiguratorMessageConsole.getInstance()
                            .printErrorMessage(
                                    "To support module firmware update the object '0x1F82' with value '"
                                            + defaultVal
                                            + "' has to be present on the node '"
                                            + modularHeadNode
                                                    .getNodeIDWithName()
                                            + "'. ",
                                    node.getProject().getName());
                }

            } else {
                OpenConfiguratorMessageConsole.getInstance().printErrorMessage(
                        "To support module firmware update the object '0x1F82' has to be present on the node '"
                                + modularHeadNode.getNodeIDWithName() + "'. ",
                        node.getProject().getName());
            }
        }
        return false;
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

        String preTypeToBeChecked = StringUtils.EMPTY;
        String prevModuleTypeToBeChecked = StringUtils.EMPTY;
        String nextonTypeToBeChecked = StringUtils.EMPTY;
        String nextTypeToBeChecked = StringUtils.EMPTY;

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
            if (preModule.getModuleInterface() != null) {
                preTypeToBeChecked = preModule.getModuleInterface().getType();
            }
            prevModuleTypeToBeChecked = newModule.getModuleInterface()
                    .getType();
        } else {
            if (newModule.getModuleInterface() != null) {
                prevModuleTypeToBeChecked = newModule.getModuleInterface()
                        .getType();
            }
            preTypeToBeChecked = newModule.getInterfaceOfModule()
                    .getInterfaceType();
        }

        if (nextModulePosition != 0) {
            if (currentModule.getModuleInterface() != null) {
                nextonTypeToBeChecked = currentModule.getModuleInterface()
                        .getType();
            }
            nextTypeToBeChecked = nextModule.getModuleType();
        }

        if (!(nextonTypeToBeChecked.equalsIgnoreCase(nextTypeToBeChecked))) {

            return MessageFormat.format(MOVE_MODULE_ERROR,
                    currentModule.getModuleName(), nextonTypeToBeChecked,
                    nextModule.getModuleName(), nextTypeToBeChecked);
        }

        if (!(preTypeToBeChecked.equalsIgnoreCase(prevModuleTypeToBeChecked))) {

            if (preModule != null) {
                return MessageFormat.format(MOVE_MODULE_ERROR,
                        preModule.getModuleName(), preTypeToBeChecked,
                        newModule.getModuleName(), prevModuleTypeToBeChecked);

            }
            return MessageFormat.format(MOVE_MODULE_ERROR,
                    newModule.getInterfaceOfModule().getInterfaceUId(),
                    newModule.getInterfaceOfModule().getInterfaceType(),
                    newModule.getModuleName(), prevModuleTypeToBeChecked);
        }

        if (!(currentModule.getModuleInterface().getType()
                .equalsIgnoreCase(newModule.getModuleType()))) {
            return MessageFormat.format(MOVE_MODULE_ERROR,
                    currentModule.getModuleName(),
                    currentModule.getModuleInterface().getType(),
                    newModule.getModuleName(), newModule.getModuleType());
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

        String nextTypeToBeChecked = StringUtils.EMPTY;
        String newModuleTypeToBeChecked = StringUtils.EMPTY;
        String previousTypeToBeChecked = StringUtils.EMPTY;
        String preModuleTypeToBeChecked = StringUtils.EMPTY;

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
            if (nextModule.getModuleInterface() != null) {
                nextTypeToBeChecked = nextModule.getModuleInterface().getType();
            }
            newModuleTypeToBeChecked = newModule.getModuleType();
        }

        if (previousModulePosition != 0) {
            if (currentModule.getModuleInterface() != null) {
                previousTypeToBeChecked = currentModule.getModuleInterface()
                        .getType();
            }
            preModuleTypeToBeChecked = prevModule.getModuleType();
        } else {
            if (currentModule.getModuleInterface() != null) {
                previousTypeToBeChecked = currentModule.getModuleInterface()
                        .getType();
            }
            preModuleTypeToBeChecked = currentModule.getInterfaceOfModule()
                    .getInterfaceType();
        }

        if (!(previousTypeToBeChecked
                .equalsIgnoreCase(preModuleTypeToBeChecked))) {

            if (prevModule != null) {
                return MessageFormat.format(MOVE_MODULE_ERROR,
                        currentModule.getModuleName(), previousTypeToBeChecked,
                        prevModule.getModuleName(), preModuleTypeToBeChecked);
            }
            return MessageFormat.format(MOVE_MODULE_INTERFACE_ERROR,
                    currentModule.getModuleName(), previousTypeToBeChecked,
                    currentModule.getInterfaceOfModule().getInterfaceUId(),
                    currentModule.getInterfaceOfModule().getInterfaceType());
        }

        if (!(nextTypeToBeChecked.equalsIgnoreCase(newModuleTypeToBeChecked))) {
            return MessageFormat.format(MOVE_MODULE_ERROR,
                    nextModule.getModuleName(), nextTypeToBeChecked,
                    newModule.getModuleName(), newModuleTypeToBeChecked);
        }

        if (!(currentModule.getModuleType()
                .equalsIgnoreCase(newModule.getModuleInterface().getType()))) {
            return MessageFormat.format(MOVE_MODULE_ERROR,
                    currentModule.getModuleName(),
                    currentModule.getModuleType(), newModule.getModuleName(),
                    newModule.getModuleInterface().getType());
        }

        return StringUtils.EMPTY;
    }

    /**
     * Add/remove force object model in the project model.
     *
     * @param forceObj The forced object model.
     * @param force true to add and false to remove.
     */
    public void forceObjectActualValue(
            org.epsg.openconfigurator.xmlbinding.projectfile.Object forceObj,
            boolean force) {
        org.epsg.openconfigurator.xmlbinding.projectfile.InterfaceList.Interface.Module.ForcedObjects forcedObjTag = null;
        if (moduleModel instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module mod = (InterfaceList.Interface.Module) moduleModel;
            forcedObjTag = mod.getForcedObjects();
            if (force) {
                if (forcedObjTag == null) {
                    mod.setForcedObjects(
                            new org.epsg.openconfigurator.xmlbinding.projectfile.InterfaceList.Interface.Module.ForcedObjects());
                    forcedObjTag = mod.getForcedObjects();
                }
            } else {
                if (forcedObjTag != null) {
                    removeForcedObject(forcedObjTag, forceObj);
                }
                if (forcedObjTag != null) {
                    if (forcedObjTag.getObject().isEmpty()) {
                        mod.setForcedObjects(null);
                    }
                }
            }
        }

        if (force) {
            boolean alreadyForced = false;
            if (forcedObjTag != null) {
                for (org.epsg.openconfigurator.xmlbinding.projectfile.Object tempForceObj : forcedObjTag
                        .getObject()) {
                    if (java.util.Arrays.equals(tempForceObj.getIndex(),
                            forceObj.getIndex())) {
                        if (forceObj.getSubindex() == null) {
                            alreadyForced = true;
                            break;
                        }
                        if (java.util.Arrays.equals(tempForceObj.getSubindex(),
                                forceObj.getSubindex())) {
                            alreadyForced = true;
                            break;
                        }
                    }
                }
            }
            if (!alreadyForced) {
                if (forcedObjTag != null) {
                    forcedObjTag.getObject().add(forceObj);
                }
            }
        }
    }

    /**
     * @return The XDC path of module.
     */
    public String getAbsolutePathToXdc() {
        String pathToXdc = getProject().getLocation().toString();
        String xdcPath = getModulePathToXdc();
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
     * @return Error in configuration file.
     */
    public String getError() {
        return configurationError;
    }

    public String getForcedObjectsString() {
        String objectText = StringUtils.EMPTY;
        ForcedObjects forcedObjTag = null;
        if (moduleModel instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module net = (InterfaceList.Interface.Module) moduleModel;
            forcedObjTag = net.getForcedObjects();
        }

        if (forcedObjTag != null) {
            List<org.epsg.openconfigurator.xmlbinding.projectfile.Object> forcedObjList = forcedObjTag
                    .getObject();
            for (org.epsg.openconfigurator.xmlbinding.projectfile.Object obj : forcedObjList) {
                objectText = objectText.concat("0x");

                objectText = objectText.concat(
                        DatatypeConverter.printHexBinary(obj.getIndex()));
                if (obj.getSubindex() != null) {
                    objectText = objectText.concat("/0x");
                    objectText = objectText.concat(DatatypeConverter
                            .printHexBinary(obj.getSubindex()));
                }
                objectText = objectText.concat(";");
            }
        }
        return objectText;
    }

    /**
     * @return The Xpath of interface list in project file.
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
                + getInterfaceOfModule().getInterfaceUId() + "']";
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
     * @return Model of module.
     */
    public Object getModelOfModule() {
        return moduleModel;
    }

    /**
     * @return Value of module addressing in interface.
     */
    public TModuleAddressingChild getModuleAddressing() {
        moduleAddressing = TModuleAddressingChild.NEXT;
        if (getModuleInterface() != null) {
            moduleAddressing = getModuleInterface().getModuleAddressing();
        } else {
            System.err.println("Module with Invalid XDC.");
        }
        return moduleAddressing;
    }

    /**
     * @return List of firmware files added to Module
     */
    public Map<FirmwareManager, Integer> getModuleFirmwareCollection() {
        return moduleFirmwareCollection;
    }

    /**
     * @return The valid firmware file for module from the project file.
     */
    public List<FirmwareManager> getModuleFirmwareFileList() {
        List<FirmwareManager> fwList = new ArrayList<>();

        fwList.clear();

        Map<String, FirmwareManager> moduleDevRevisionList = new HashMap<>();

        for (FirmwareManager fwManager : getModuleFirmwareCollection()
                .keySet()) {
            moduleDevRevisionList.put(fwManager.getdevRevNumber(), fwManager);
            if (!moduleDevRevisionList.isEmpty()) {
                for (FirmwareManager fwMan : moduleDevRevisionList.values()) {
                    if (fwMan.getFirmwarefileVersion() < fwManager
                            .getFirmwarefileVersion()) {
                        moduleDevRevisionList.put(fwManager.getdevRevNumber(),
                                fwManager);
                    }
                }
            }
        }

        fwList.addAll(moduleDevRevisionList.values());

        return fwList;
    }

    /**
     * @return The valid firmware file name for module from the project file.
     */
    public List<String> getModuleFirmwareFileNameList() {
        List<String> fwList = new ArrayList<>();

        fwList.clear();

        for (FirmwareManager fwManager : getModuleFirmwareCollection()
                .keySet()) {
            String filename = FilenameUtils
                    .getName(fwManager.getFirmwareConfigPath());
            fwList.add(filename);

        }
        return fwList;
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
     * @return Name of Module.
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * @return The path of module XDC.
     */
    public String getModulePathToXdc() {
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
     * @return Type of module that is connected to interface.
     */
    public String getModuleType() {
        String moduleTypeName = StringUtils.EMPTY;
        if (getModuleInterface() != null) {
            ModuleTypeList moduleTypeCollection = getModuleInterface()
                    .getModuleTypeList();
            if (moduleTypeCollection != null) {
                List<ModuleType> moduleTypeList = moduleTypeCollection
                        .getModuleType();
                for (ModuleType module : moduleTypeList) {
                    moduleTypeName = module.getType();
                }
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
        TreeSet<Integer> positionList = new TreeSet<>();
        for (Integer positionAvailable : positionCollections) {
            positionList.add(positionAvailable);
        }
        for (Integer availablePosition : positionList) {
            if (availablePosition > position) {
                nextPosition = availablePosition;
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

    public String getParamUniqueID(Object uniqueIDRef) {
        String uniqueId = StringUtils.EMPTY;
        if (uniqueIDRef instanceof TDataTypeList.Struct) {
            TDataTypeList.Struct structDt = (TDataTypeList.Struct) uniqueIDRef;
            uniqueId = structDt.getUniqueID();

        }
        return uniqueId;
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
     * @return Product ID of the Module.
     */
    public String getProductId() {
        if (xddModel != null) {
            List<ISO15745Profile> profiles = xddModel.getISO15745Profile();
            for (ISO15745Profile profile : profiles) {
                ProfileBodyDataType profileBody = profile.getProfileBody();

                if (profileBody instanceof ProfileBodyDevicePowerlinkModularChild) {
                    ProfileBodyDevicePowerlinkModularChild devProfile = (ProfileBodyDevicePowerlinkModularChild) profileBody;
                    return devProfile.getDeviceIdentity().getProductID()
                            .getValue();
                }
            }
        }

        return StringUtils.EMPTY;
    }

    /**
     * @return The product Name of module.
     */
    public String getProductName() {
        if (xddModel != null) {
            List<ISO15745Profile> profiles = xddModel.getISO15745Profile();
            for (ISO15745Profile profile : profiles) {
                ProfileBodyDataType profileBody = profile.getProfileBody();

                if (profileBody instanceof ProfileBodyDevicePowerlinkModularChild) {
                    ProfileBodyDevicePowerlinkModularChild devProfile = (ProfileBodyDevicePowerlinkModularChild) profileBody;
                    if (devProfile.getDeviceIdentity() != null) {
                        return devProfile.getDeviceIdentity().getProductName()
                                .getValue();
                    }
                }
            }
        }

        return StringUtils.EMPTY;
    }

    /**
     * @return Eclipse project associated with the module.
     */
    public IProject getProject() {
        return node.getProject();
    }

    /**
     * @return Vendor ID of the Module.
     */
    public String getVendorId() {
        if (xddModel != null) {
            List<ISO15745Profile> profiles = xddModel.getISO15745Profile();
            for (ISO15745Profile profile : profiles) {
                ProfileBodyDataType profileBody = profile.getProfileBody();

                if (profileBody instanceof ProfileBodyDevicePowerlinkModularChild) {
                    ProfileBodyDevicePowerlinkModularChild devProfile = (ProfileBodyDevicePowerlinkModularChild) profileBody;
                    return devProfile.getDeviceIdentity().getVendorID()
                            .getValue();
                }
            }
        }

        return StringUtils.EMPTY;
    }

    /**
     * @return The vendor Name of module.
     */
    public String getVendorName() {
        if (xddModel != null) {
            List<ISO15745Profile> profiles = xddModel.getISO15745Profile();
            for (ISO15745Profile profile : profiles) {
                ProfileBodyDataType profileBody = profile.getProfileBody();
                if (profileBody instanceof ProfileBodyDevicePowerlinkModularChild) {
                    ProfileBodyDevicePowerlinkModularChild devProfile = (ProfileBodyDevicePowerlinkModularChild) profileBody;
                    if (devProfile.getDeviceIdentity() != null) {
                        return devProfile.getDeviceIdentity().getVendorName()
                                .getValue();
                    }
                }
            }
        }

        return StringUtils.EMPTY;
    }

    /**
     * @return The vendor ID value of module from XDD/XDC.
     */
    public String getVenIdValue() {
        String value = StringUtils.EMPTY;
        if (xddFirmwareFile != null) {
            value = xddFirmwareFile.getModuleVendorID();
        }
        return value;
    }

    /**
     * @return The Hardware, Software or Firmware version values.
     */
    public String getVersionValue(String versionType) {
        if (xddModel != null) {
            List<ISO15745Profile> profiles = xddModel.getISO15745Profile();
            for (ISO15745Profile profile : profiles) {
                ProfileBodyDataType profileBody = profile.getProfileBody();

                if (profileBody instanceof ProfileBodyDevicePowerlinkModularChild) {
                    ProfileBodyDevicePowerlinkModularChild devProfile = (ProfileBodyDevicePowerlinkModularChild) profileBody;
                    for (TVersion ver : devProfile.getDeviceIdentity()
                            .getVersion()) {
                        if (ver.getVersionType()
                                .equalsIgnoreCase(versionType)) {
                            return ver.getValue();
                        }
                    }
                }
            }
        }

        return StringUtils.EMPTY;
    }

    /**
     * @return Instance of FirmwareFile.
     */
    public FirmwareFile getXddFirmwareFile() {
        return xddFirmwareFile;
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
     * Checks for valid XDD model.
     *
     * @return <code>True</code> if node has no XDD/XDC file. <code>False</code>
     *         if module has XDD/XDC file
     */
    public boolean hasError() {
        if (configurationError == null) {
            return false;
        }
        return !configurationError.isEmpty();
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

    public boolean isObjectIdForced(long newObjectIndex) {
        ForcedObjects forcedObjTag = null;
        if (moduleModel instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module net = (InterfaceList.Interface.Module) moduleModel;
            forcedObjTag = net.getForcedObjects();
        }

        if (forcedObjTag == null) {
            return false;
        }

        boolean alreadyForced = false;
        for (org.epsg.openconfigurator.xmlbinding.projectfile.Object tempForceObj : forcedObjTag
                .getObject()) {
            String objectIndex = Long.toHexString(newObjectIndex);
            byte[] objectId = DatatypeConverter
                    .parseHexBinary(objectIndex.toUpperCase());

            if (java.util.Arrays.equals(tempForceObj.getIndex(), objectId)) {
                alreadyForced = true;
            }
        }

        return alreadyForced;
    }

    public boolean isObjectIdForced(long moduleObjectIndex,
            int moduleSubobjectindex) {
        ForcedObjects forcedObjTag = null;
        if (moduleModel instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module net = (InterfaceList.Interface.Module) moduleModel;
            forcedObjTag = net.getForcedObjects();
        }

        if (forcedObjTag == null) {
            return false;
        }

        boolean alreadyForced = false;
        for (org.epsg.openconfigurator.xmlbinding.projectfile.Object tempForceObj : forcedObjTag
                .getObject()) {
            String objectIndex = Long.toHexString(moduleObjectIndex);
            byte[] objectId = DatatypeConverter.parseHexBinary(objectIndex);
            String subobjectindex = Integer.toHexString(moduleSubobjectindex);
            if (moduleSubobjectindex < 16) {
                subobjectindex = "0" + subobjectindex;
            }
            byte[] subObjectId = DatatypeConverter
                    .parseHexBinary(subobjectindex.toUpperCase());
            if (java.util.Arrays.equals(tempForceObj.getIndex(), objectId)) {
                if (subObjectId == null) {
                    alreadyForced = true;
                } else if (java.util.Arrays.equals(tempForceObj.getSubindex(),
                        subObjectId)) {
                    alreadyForced = true;
                }
            }
        }

        return alreadyForced;
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
            List<Integer> positiontoBeMoved = new ArrayList<>();
            for (Integer position : positionSet) {
                if (position >= oldPosition) {
                    if (position <= newPosition) {
                        positiontoBeMoved.add(position);
                    }
                }
            }
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
            List<Integer> positiontoBeMoved = new ArrayList<>();
            for (Integer position : positionSet) {
                if (position <= oldPosition) {
                    if (position >= newPosition) {
                        positiontoBeMoved.add(position);
                    }
                }
            }

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

        if (moduleModel instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module module = (InterfaceList.Interface.Module) moduleModel;
            Integer address = Integer.valueOf(value);
            module.setAddress(BigInteger.valueOf(address));
            getInterfaceOfModule().getAddressCollection().remove(oldAddress);
            getInterfaceOfModule().getAddressCollection().put(address, this);
        } else {
            System.err.println("Invalid module model.");
        }

        rootNode.fireNodePropertyChanged(new NodePropertyChangeEvent(this));

        Result res = OpenConfiguratorLibraryUtils.setModuleAddress(this);
        if (!res.IsSuccessful()) {
            OpenConfiguratorMessageConsole.getInstance()
                    .printLibraryErrorMessage(res);
            return;
        }

    }

    public void setAddresss(String value) throws JDOMException, IOException {

        OpenConfiguratorProjectUtils.swapModuleAttributeValue(this,
                IAbstractNodeProperties.MODULE_ADDRESS_OBJECT, value);
        if (moduleModel instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module module = (InterfaceList.Interface.Module) moduleModel;
            Integer address = Integer.valueOf(value);
            module.setAddress(BigInteger.valueOf(address));
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
        if (!hasError()) {
            Result res = OpenConfiguratorLibraryUtils.toggleEnableDisable(this);
            if (!res.IsSuccessful()) {
                OpenConfiguratorMessageConsole.getInstance()
                        .printLibraryErrorMessage(res);
                return;
            }
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
            getInterfaceOfModule().getModuleNameCollection()
                    .remove(module.getName());
            getInterfaceOfModule().getModuleNameCollection().put(newName, this);
            module.setName(newName);

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

        boolean validPosition = true;
        Integer newPosition = Integer.valueOf(value);
        int oldPosition = getPosition();
        int previousPosition = 0;
        int nextPosition = 0;
        Set<Integer> positionSet = getInterfaceOfModule().getModuleCollection()
                .keySet();

        for (int position : positionSet) {

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

        if (previousPosition != 0) {
            Module previousModule = getInterfaceOfModule().getModuleCollection()
                    .get(previousPosition);
            String previousModuleType = previousModule.getModuleType();
            if (previousModuleType.equals(currentToPreviousModuleType)) {
                validPosition = true;
                if (nextPosition != 0) {
                    Module nextModule = getInterfaceOfModule()
                            .getModuleCollection().get(nextPosition);
                    String nextModuleType = nextModule.getModuleInterface()
                            .getType();
                    if ((currentToNextModuleType.equals(nextModuleType))) {
                        validPosition = true;
                    } else {
                        validPosition = false;
                    }
                }
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
                    validPosition = true;
                } else {
                    validPosition = false;
                }
            }
        }

        if (!validPosition) {
            MessageDialog dialog = new MessageDialog(null,
                    "Modify module position", null,
                    "Modifying the position of module will disable the modules that does not match the module type.'",
                    MessageDialog.WARNING, new String[] { "Yes", "No" }, 1);
            int result = dialog.open();
            if (result == 0) {
                OpenConfiguratorProjectUtils.updateModuleAttributeValue(this,
                        IAbstractNodeProperties.MODULE_POSITION_OBJECT, value);

                if (moduleModel instanceof InterfaceList.Interface.Module) {
                    InterfaceList.Interface.Module module = (InterfaceList.Interface.Module) moduleModel;
                    Integer position = Integer.valueOf(value);

                    Result res = OpenConfiguratorLibraryUtils.moveModule(this,
                            oldPosition, position);
                    if (res.IsSuccessful()) {
                        module.setPosition(BigInteger.valueOf(position));
                        getInterfaceOfModule().getModuleCollection()
                                .remove(oldPosition);
                        getInterfaceOfModule().getModuleCollection()
                                .put(position, this);
                        if (String
                                .valueOf(getInterfaceOfModule()
                                        .getModuleAddressing())
                                .equalsIgnoreCase("POSITION")) {
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
            }
            return;
        }
        OpenConfiguratorProjectUtils.updateModuleAttributeValue(this,
                IAbstractNodeProperties.MODULE_POSITION_OBJECT, value);
        if (moduleModel instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module module = (InterfaceList.Interface.Module) moduleModel;
            Integer position = Integer.valueOf(value);

            Result res = OpenConfiguratorLibraryUtils.moveModule(this,
                    oldPosition, position);
            if (res.IsSuccessful()) {
                module.setPosition(BigInteger.valueOf(position));
                getInterfaceOfModule().getModuleCollection()
                        .remove(oldPosition);
                getInterfaceOfModule().getModuleCollection().put(position,
                        this);
                if (String.valueOf(getInterfaceOfModule().getModuleAddressing())
                        .equalsIgnoreCase("POSITION")) {
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

    /**
     * Updates the position of module
     *
     * @param value new position to be updated.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public void setPositions(String value) throws JDOMException, IOException {

        OpenConfiguratorProjectUtils.updateModuleAttributeValue(this,
                IAbstractNodeProperties.MODULE_POSITION_OBJECT, value);
        int oldPosition = getPosition();
        Integer position = Integer.valueOf(value);
        if (moduleModel instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module module = (InterfaceList.Interface.Module) moduleModel;

            Result res = OpenConfiguratorLibraryUtils.moveModule(this,
                    oldPosition, position);
            if (res.IsSuccessful()) {
                module.setPosition(BigInteger.valueOf(position));
                getInterfaceOfModule().getModuleCollection()
                        .remove(oldPosition);
                getInterfaceOfModule().getModuleCollection().put(position,
                        this);
                if (String.valueOf(getInterfaceOfModule().getModuleAddressing())
                        .equalsIgnoreCase("POSITION")) {
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
        OpenConfiguratorProjectUtils.updateModuleConfigurationPath(this,
                position);
        OpenConfiguratorProjectUtils.swapModuleAttributeValue(this,
                IAbstractNodeProperties.NODE_CONIFG_OBJECT,
                getModulePathToXdc());

        Module newModule = getInterfaceOfModule().getModuleCollection()
                .get(position);

        OpenConfiguratorProjectUtils.swapModuleAttributeValue(newModule,
                IAbstractNodeProperties.MODULE_POSITION_OBJECT,
                String.valueOf(oldPosition));
        OpenConfiguratorProjectUtils.updateModuleConfigurationPath(newModule,
                oldPosition);
        OpenConfiguratorProjectUtils.swapModuleAttributeValue(newModule,
                IAbstractNodeProperties.NODE_CONIFG_OBJECT,
                newModule.getModulePathToXdc());

        Object moduleObjModel = getModelOfModule();
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

        Object newmoduleObjModel = newModule.getModelOfModule();
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
        OpenConfiguratorProjectUtils.updateModuleConfigurationPath(this,
                position);
        OpenConfiguratorProjectUtils.swapModuleAttributeValue(this,
                IAbstractNodeProperties.NODE_CONIFG_OBJECT,
                getModulePathToXdc());

        Module newModule = getInterfaceOfModule().getModuleCollection()
                .get(position);

        OpenConfiguratorProjectUtils.swapModuleAttributeValue(newModule,
                IAbstractNodeProperties.MODULE_POSITION_OBJECT,
                String.valueOf(oldPosition));

        newModule.setAddresss(String.valueOf(oldPosition));
        OpenConfiguratorProjectUtils.updateModuleConfigurationPath(newModule,
                oldPosition);
        OpenConfiguratorProjectUtils.swapModuleAttributeValue(newModule,
                IAbstractNodeProperties.NODE_CONIFG_OBJECT,
                newModule.getModulePathToXdc());

        Object moduleObjModel = getModelOfModule();
        if (moduleObjModel instanceof InterfaceList.Interface.Module) {
            InterfaceList.Interface.Module moduleModelObj = (InterfaceList.Interface.Module) moduleObjModel;
            moduleModelObj.setPosition(BigInteger.valueOf(position));

            getInterfaceOfModule().getModuleCollection().put(position, this);
        }

        Object newmoduleObjModel = newModule.getModelOfModule();
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

    public PowerlinkObject updateModuleObjectsFromLibrary(byte[] forcedObject) {

        for (PowerlinkObject obj : getObjectDictionary().getObjectsList()) {
            long newObjectIndex = OpenConfiguratorLibraryUtils
                    .getModuleObjectsIndex(obj.getModule(), obj.getId());
            String objectId = Long.toHexString(newObjectIndex);
            String objectIndex = DatatypeConverter.printHexBinary(forcedObject);
            if (objectId.equalsIgnoreCase(objectIndex)) {
                return obj;
            }
        }
        return null;

    }

    public PowerlinkSubobject updateModuleSubObjectsFromLibrary(byte[] obj,
            byte[] subObj) {
        for (PowerlinkObject object : getObjectDictionary().getObjectsList()) {
            long newObjectIndex = OpenConfiguratorLibraryUtils
                    .getModuleObjectsIndex(object.getModule(), object.getId());
            String objectId = Long.toHexString(newObjectIndex);
            String objectIndex = DatatypeConverter.printHexBinary(obj);

            if (objectId.equalsIgnoreCase(objectIndex)) {

                for (PowerlinkSubobject subObject : object.getSubObjects()) {
                    int newSubObjectIndex = OpenConfiguratorLibraryUtils
                            .getModuleObjectsSubIndex(subObject.getModule(),
                                    subObject, subObject.getObject().getId());
                    String subobjectId = Long.toHexString(newSubObjectIndex);

                    if (newSubObjectIndex < 10) {
                        subobjectId = "0" + subobjectId;
                    }

                    String subobjectIndex = DatatypeConverter
                            .printHexBinary(subObj);
                    System.err.println("Sub ObjectId..." + subobjectId
                            + "sub Object Index..." + subobjectIndex);
                    if (subobjectId.equalsIgnoreCase(subobjectIndex)) {
                        return subObject;
                    }
                }
            }
        }

        return null;
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
            preTypetoBeChecked = newModule.getModuleInterface().getType();
            prevModuleTypetoBeChecked = preModule.getModuleType();
        } else {
            prevModuleTypetoBeChecked = newModule.getModuleType();
            preTypetoBeChecked = newModule.getInterfaceOfModule()
                    .getInterfaceType();
        }

        if (nextModulePosition != 0) {
            nextonTypetoBeChecked = nextModule.getModuleInterface().getType();
            nextTypetoBeChecked = currentModule.getModuleType();
        }

        if (nextonTypetoBeChecked.equalsIgnoreCase(nextTypetoBeChecked)
                && preTypetoBeChecked
                        .equalsIgnoreCase(prevModuleTypetoBeChecked)
                && (currentModule.getModuleInterface().getType()
                        .equalsIgnoreCase(newModule.getModuleType()))) {
            validModulePosition = true;
        } else {
            validModulePosition = false;
        }
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
            if (nextModule.getModuleInterface() != null) {
                nextTypetoBeChecked = nextModule.getModuleInterface().getType();
            }
            newModuleTypetoBeChecked = newModule.getModuleType();
        }

        if (previousModulePosition != 0) {
            previousTypetoBeChecked = currentModule.getModuleInterface()
                    .getType();
            preModuleTypetoBeChecked = prevModule.getModuleType();
        } else {
            previousTypetoBeChecked = currentModule.getModuleInterface()
                    .getType();
            preModuleTypetoBeChecked = currentModule.getInterfaceOfModule()
                    .getInterfaceType();
        }

        if (previousTypetoBeChecked.equalsIgnoreCase(preModuleTypetoBeChecked)
                && nextTypetoBeChecked
                        .equalsIgnoreCase(newModuleTypetoBeChecked)
                && (currentModule.getModuleType().equalsIgnoreCase(
                        newModule.getModuleInterface().getType()))) {
            validModulePosition = true;
        } else {
            validModulePosition = false;
        }
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
    public boolean validateNewPosition(int newPosition) {
        Set<Integer> positionSet = getInterfaceOfModule().getModuleCollection()
                .keySet();
        boolean validPosition = true;
        int previousPosition = 0;
        int nextPosition = 0;
        for (int position : positionSet) {

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

            if (previousModuleType.equals(currentToPreviousModuleType)) {
                validPosition = true;
                if (nextPosition != 0) {
                    Module nextModule = getInterfaceOfModule()
                            .getModuleCollection().get(nextPosition);
                    String nextModuleType = nextModule.getModuleInterface()
                            .getType();
                    if ((currentToNextModuleType.equals(nextModuleType))) {
                        validPosition = true;
                    } else {
                        validPosition = false;
                    }
                }

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
                    validPosition = true;
                } else {
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

            for (Integer nextPosition : positionToBeChecked) {
                Module nextModule = getInterfaceOfModule().getModuleCollection()
                        .get(nextPosition);
                String nextModuleType = nextModule.getModuleInterface()
                        .getType();
                if (nextModule.isEnabled()) {
                    if ((previousModuleType.equals(nextModuleType))) {
                        return;
                    }
                    nextModule.setEnabled(false);
                }
            }

        }

    }

}

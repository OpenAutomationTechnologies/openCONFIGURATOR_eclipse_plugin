/*******************************************************************************
 * @file   OpenCONFIGURATORProjectUtils.java
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

package org.epsg.openconfigurator.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.epsg.openconfigurator.console.OpenConfiguratorMessageConsole;
import org.epsg.openconfigurator.lib.wrapper.NodeAssignment;
import org.epsg.openconfigurator.lib.wrapper.OpenConfiguratorCore;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.lib.wrapper.StringCollection;
import org.epsg.openconfigurator.model.FirmwareManager;
import org.epsg.openconfigurator.model.HeadNodeInterface;
import org.epsg.openconfigurator.model.IPowerlinkProjectSupport;
import org.epsg.openconfigurator.model.Module;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.model.Parameter;
import org.epsg.openconfigurator.model.ParameterReference;
import org.epsg.openconfigurator.model.PdoChannel;
import org.epsg.openconfigurator.model.PowerlinkObject;
import org.epsg.openconfigurator.model.PowerlinkSubobject;
import org.epsg.openconfigurator.xmlbinding.projectfile.FirmwareList.Firmware;
import org.epsg.openconfigurator.xmlbinding.projectfile.OpenCONFIGURATORProject;
import org.epsg.openconfigurator.xmlbinding.projectfile.TAutoGenerationSettings;
import org.epsg.openconfigurator.xmlbinding.projectfile.TCN;
import org.epsg.openconfigurator.xmlbinding.projectfile.TGenerator;
import org.epsg.openconfigurator.xmlbinding.projectfile.TKeyValuePair;
import org.epsg.openconfigurator.xmlbinding.projectfile.TMN;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNetworkConfiguration;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNodeCollection;
import org.epsg.openconfigurator.xmlbinding.projectfile.TPath;
import org.epsg.openconfigurator.xmlbinding.projectfile.TProjectConfiguration;
import org.epsg.openconfigurator.xmlbinding.projectfile.TRMN;
import org.epsg.openconfigurator.xmloperation.JDomUtil;
import org.epsg.openconfigurator.xmloperation.ProjectJDomOperation;
import org.epsg.openconfigurator.xmloperation.XddJdomOperation;
import org.jdom2.JDOMException;

/**
 * Updates the values of node and module into XDC or project file.
 *
 * @author SreeHari
 *
 */
public final class OpenConfiguratorProjectUtils {

    public static final String PATH_SETTINGS_DEFAULT_PATH_ID = "defaultOutputPath"; ////$NON-NLS-1$
    public static final String PATH_SETTINGS_DEFAUTL_PATH_VALUE = "output"; ////$NON-NLS-1$

    public static final String AUTO_GENERATION_SETTINGS_ALL_ID = "all"; ////$NON-NLS-1$
    public static final String AUTO_GENERATION_SETTINGS_NONE_ID = "none"; ////$NON-NLS-1$
    public static final String AUTO_GENERATION_SETTINGS_CUSTOM_ID = "custom"; ////$NON-NLS-1$

    public static final String GENERATOR_VENDOR = "Kalycito Infotech Private Limited & Bernecker + Rainer Industrie Elektronik Ges.m.b.H."; ////$NON-NLS-1$
    public static final String GENERATOR_TOOL_NAME = "Ethernet POWERLINK openCONFIGURATOR"; ////$NON-NLS-1$
    public static final String GENERATOR_TOOL_VERSION = "2.1.1"; ////$NON-NLS-1$
    public static final String SYSTEM_USER_NAME_ID = "user.name";
    public static final String MODIFIED_ON_ATTRIBUTE = "modifiedOn";
    public static final String TOOL_VERSION_ATTRIBUTE = "toolVersion";
    public static final String MODIFIED_BY_ATTRIBUTE = "modifiedBy";

    public static ArrayList<String> defaultBuildConfigurationIdList;

    private static final String UPGRADE_MESSAGE = "Upgrading openCONFIGURATOR project version {0} to version {1}.";

    static {
        // Fetch the list of default buildConfigurationIDs from the
        // openconfigurator-core library.
        OpenConfiguratorProjectUtils.defaultBuildConfigurationIdList = new ArrayList<>();
        StringCollection support = new StringCollection();
        Result libApiRes = OpenConfiguratorCore.GetInstance()
                .GetSupportedSettingIds(support);
        if (!libApiRes.IsSuccessful()) {
            // TODO: Display a dialog to report it to the user
            System.err.println("GetSupportedSettingIds failed with error: "
                    + OpenConfiguratorLibraryUtils.getErrorMessage(libApiRes));
        }

        for (int i = 0; i < support.size();) {
            OpenConfiguratorProjectUtils.defaultBuildConfigurationIdList
                    .add(support.get(i));
            break;
        }
    }

    /**
     * Adds the details of firmware list element into project source file.
     *
     * @param firmwareMngr Instance of Firmware manager to retrieve the value of
     *            firmware.
     * @param nodeOrModuleObj Instance of Node or Module.
     * @param firmwareObj Instance of firmware object model.
     * @throws IOException Error with XDC/XDD file modification.
     * @throws JDOMException Error with time modifications.
     */
    public static void addFirmwareList(FirmwareManager firmwareMngr,
            Object nodeOrModuleObj, Firmware firmwareObj)
            throws JDOMException, IOException {
        String projectXmlLocation = firmwareMngr.getProjectXml().getLocation()
                .toString();
        File xmlFile = new File(projectXmlLocation);
        org.jdom2.Document document = JDomUtil.getXmlDocument(xmlFile);

        ProjectJDomOperation.addFirmwareList(document, nodeOrModuleObj,
                firmwareMngr, firmwareObj);

        JDomUtil.writeToProjectXmlDocument(document, xmlFile);
        // Updates generator attributes in project file.

        if (nodeOrModuleObj instanceof Node) {
            Node node = (Node) nodeOrModuleObj;
            updateGeneratorInfo(node);
        } else if (nodeOrModuleObj instanceof Module) {
            Module module = (Module) nodeOrModuleObj;
            updateGeneratorInfo(module.getNode());
        }

    }

    /**
     * Adds the module of head node interface into the project file.
     *
     * @param node Instance of Node
     * @param headinterface Instance of head node interface in which the module
     *            is to be connected.
     * @param module Instance of Module.
     * @throws IOException Error with XDC/XDD file modification.
     * @throws JDOMException Error with time modifications.
     */
    public static void addModuleNode(Node node, HeadNodeInterface headinterface,
            Module module) throws JDOMException, IOException {
        String projectXmlLocation = node.getProjectXml().getLocation()
                .toString();
        File xmlFile = new File(projectXmlLocation);
        org.jdom2.Document document = JDomUtil.getXmlDocument(xmlFile);

        ProjectJDomOperation.addInterfaceList(document, node, headinterface,
                module);

        JDomUtil.writeToProjectXmlDocument(document, xmlFile);
        // Updates generator attributes in project file.
        updateGeneratorInfo(node);
    }

    /**
     * Copies the firmware files based on the latest version and hardware
     * variant of firmware
     *
     * @param firmwareMngr Instance of Firmware manager
     * @throws IOException Error with XDC/XDD file modification.
     */
    public static void copyFirmwareFiles(FirmwareManager firmwareMngr)
            throws IOException {
        java.nio.file.Path projectRootPath = firmwareMngr.getProject()
                .getLocation().toFile().toPath();
        String path = projectRootPath.toString() + IPath.SEPARATOR
                + firmwareMngr.getFirmwareConfigPath();
        java.nio.file.Path firmwareFile = new File(path).toPath();
        String newFirmwareFileName = StringUtils.EMPTY;
        if (firmwareFile != null) {
            if ((firmwareFile.getFileName() != null)) {
                String newFirmwareName = firmwareMngr.getNewFirmwareFileName();

                newFirmwareFileName = newFirmwareName
                        + IPowerlinkProjectSupport.FIRMWARE_EXTENSION;

                String targetConfigurationPath = projectRootPath.toString()
                        + IPath.SEPARATOR
                        + IPowerlinkProjectSupport.DEFAULT_OUTPUT_DIR
                        + IPath.SEPARATOR
                        + IPowerlinkProjectSupport.FIRMWARE_OUTPUT_DIRECTORY
                        + IPath.SEPARATOR + newFirmwareFileName;
                String targetDirectoryPath = projectRootPath.toString()
                        + IPath.SEPARATOR
                        + IPowerlinkProjectSupport.DEFAULT_OUTPUT_DIR;

                String firmwareDirPath = targetDirectoryPath + IPath.SEPARATOR
                        + IPowerlinkProjectSupport.FIRMWARE_OUTPUT_DIRECTORY;

                File firmwareOutputDir = new File(firmwareDirPath);

                if (!firmwareOutputDir.exists()) {
                    java.nio.file.Files
                            .createDirectories(Paths.get(firmwareDirPath));
                } else {
                    System.out
                            .println("Fw directory available in the project.");
                }

                java.nio.file.Files.copy(firmwareFile,
                        new java.io.File(targetConfigurationPath).toPath(),
                        java.nio.file.StandardCopyOption.COPY_ATTRIBUTES,
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING,
                        java.nio.file.LinkOption.NOFOLLOW_LINKS);

                java.nio.file.Path pathRelative = projectRootPath
                        .relativize(Paths.get(targetConfigurationPath));

                String outputFirmware = projectRootPath.toString()
                        + IPath.SEPARATOR + pathRelative;

                File updatedFirmwareFile = new File(outputFirmware);
                System.out.println("updatedfile file path ==="
                        + updatedFirmwareFile.getAbsolutePath());

                if (!firmwareMngr.isKeepXmlHeader()) {
                    if (OpenConfiguratorProjectUtils
                            .removeXmlheader(updatedFirmwareFile)) {
                        System.out.println(
                                "XML header value of firmware file is removed successfully.");
                    } else {
                        System.err.println(
                                "Removing XML header from firmware file not successfull.");
                    }
                }

            }

        }

    }

    /**
     * Removes the node from the node collection provided and from the project
     * XML file.
     *
     * @param nodeCollection The list of available node.
     * @param node The node to be removed.
     * @param monitor Monitor instance to report the progress.
     * @return <code>True</code> if successful and <code>False</code> otherwise.
     * @throws IOException Error with XDC/XDD file modification.
     * @throws JDOMException Error with time modifications.
     */
    public static boolean deleteNode(Map<Short, Node> nodeCollection, Node node,
            IProgressMonitor monitor) throws JDOMException, IOException {

        boolean retVal = false;
        if (node == null) {
            return retVal;
        }

        IProject currentProject = node.getProject();
        if (currentProject == null) {
            System.err.println("Current project null. returned null");
            return retVal;
        }

        Node mnNode = nodeCollection
                .get(IPowerlinkConstants.MN_DEFAULT_NODE_ID);

        // Remove from the viewer node collection.
        Object nodeObjectModel = node.getNodeModel();
        if (nodeObjectModel instanceof TRMN) {
            TRMN rMN = (TRMN) nodeObjectModel;
            short nodeId = Short.parseShort(rMN.getNodeID());
            nodeCollection.remove(nodeId);
            retVal = true;
        } else if (nodeObjectModel instanceof TCN) {
            TCN cnNode = (TCN) nodeObjectModel;
            short nodeId = Short.parseShort(cnNode.getNodeID());
            nodeCollection.remove(nodeId);
            retVal = true;
        } else {
            System.err.println("Un-supported node" + nodeObjectModel);
            return false;
        }

        // Remove from the openconfigurator model.
        if (mnNode.getNodeModel() instanceof TNetworkConfiguration) {
            TNetworkConfiguration net = (TNetworkConfiguration) mnNode
                    .getNodeModel();
            if (nodeObjectModel instanceof TRMN) {
                List<TRMN> rmn = net.getNodeCollection().getRMN();
                rmn.remove(nodeObjectModel);
            } else if (nodeObjectModel instanceof TCN) {
                List<TCN> cn = net.getNodeCollection().getCN();
                cn.remove(nodeObjectModel);
            } else {
                System.err.println("Remove from openCONF model failed. Node ID:"
                        + node.getCnNodeId() + " modelType:" + nodeObjectModel);
                return false;
            }
        } else {
            System.err.println("Node model has been changed");
        }

        // Remove from the openconfigurator project xml.
        String projectXmlLocation = node.getProjectXml().getLocation()
                .toString();
        File xmlFile = new File(projectXmlLocation);

        org.jdom2.Document document = JDomUtil.getXmlDocument(xmlFile);

        ProjectJDomOperation.deleteNode(document, node);

        JDomUtil.writeToProjectXmlDocument(document, xmlFile);

        // Delete the XDC file from the deviceConfiguration directory.
        // File localFile = new File(node.getAbsolutePathToXdc());
        // retVal = localFile.delete();
        Files.delete(Paths.get(node.getAbsolutePathToXdc()));

        try {
            node.getProject().refreshLocal(IResource.DEPTH_INFINITE,
                    new NullProgressMonitor());
        } catch (CoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Updates generator attributes in project file.
        updateGeneratorInfo(node);
        return retVal;
    }

    public static void forceActualValue(Module module, Node node,
            PowerlinkObject powerlinkObject, PowerlinkSubobject subObject,
            boolean force, long newObjectIndex)
            throws JDOMException, IOException {
        String projectXmlLocation = node.getProjectXml().getLocation()
                .toString();
        File xmlFile = new File(projectXmlLocation);
        org.jdom2.Document document = JDomUtil.getXmlDocument(xmlFile);

        if (force) {
            ProjectJDomOperation.forceActualValue(document, module,
                    powerlinkObject, subObject, newObjectIndex, 0);
        } else {
            ProjectJDomOperation.removeForcedObject(document, module,
                    powerlinkObject, subObject, newObjectIndex, 0);
        }
        JDomUtil.writeToProjectXmlDocument(document, xmlFile);
        // Updates generator attributes in project file.
        updateGeneratorInfo(node);

    }

    public static void forceActualValue(Module module, Node node,
            PowerlinkObject object, PowerlinkSubobject powerlinkSubobject,
            boolean force, long newObjectIndex, int newSubObjectIndex)
            throws JDOMException, IOException {
        String projectXmlLocation = node.getProjectXml().getLocation()
                .toString();
        File xmlFile = new File(projectXmlLocation);
        org.jdom2.Document document = JDomUtil.getXmlDocument(xmlFile);

        if (force) {
            ProjectJDomOperation.forceActualValue(document, module, object,
                    powerlinkSubobject, newObjectIndex, newSubObjectIndex);
        } else {
            ProjectJDomOperation.removeForcedObject(document, module, object,
                    powerlinkSubobject, newObjectIndex, newSubObjectIndex);
        }
        JDomUtil.writeToProjectXmlDocument(document, xmlFile);
        // Updates generator attributes in project file.
        updateGeneratorInfo(node);

    }

    /**
     * Force actual value of the Object and writes in the project file
     *
     * @param node The node for which the object is forced.
     * @param object The POWERLINK object to be forced.
     * @param subObject The sub-object to be forced. Can be null.
     * @param force True to force and False to remove.
     * @throws IOException Error with XDC/XDD file modification.
     * @throws JDOMException Error with time modifications
     */
    public static void forceActualValue(final Node node, PowerlinkObject object,
            PowerlinkSubobject subObject, boolean force)
            throws JDOMException, IOException {
        String projectXmlLocation = node.getProjectXml().getLocation()
                .toString();
        File xmlFile = new File(projectXmlLocation);
        org.jdom2.Document document = JDomUtil.getXmlDocument(xmlFile);

        if (force) {
            ProjectJDomOperation.forceActualValue(document, node, object,
                    subObject);
        } else {
            ProjectJDomOperation.removeForcedObject(document, node, object,
                    subObject);
        }
        JDomUtil.writeToProjectXmlDocument(document, xmlFile);
        // Updates generator attributes in project file.
        updateGeneratorInfo(node);
    }

    /**
     * Get the active TAutoGenerationSettings instance from the project model.
     *
     * @param project OpenCONFIGURATORProject instance
     * @return TAutoGenerationSettings instance, null otherwise
     */
    public static TAutoGenerationSettings getActiveAutoGenerationSetting(
            OpenCONFIGURATORProject project) {
        String activeAutoGenerationSetting = project.getProjectConfiguration()
                .getActiveAutoGenerationSetting();
        List<TAutoGenerationSettings> agList = project.getProjectConfiguration()
                .getAutoGenerationSettings();
        for (TAutoGenerationSettings ag : agList) {
            if (ag.getId().equals(activeAutoGenerationSetting)) {
                return ag;
            }
        }

        return null;
    }

    /**
     * Get the current date to update XDD/XDC file modification date.
     *
     * @return Current date.
     */
    private static String getCurrentDate() {
        SimpleDateFormat sdfDate = new SimpleDateFormat(
                IPowerlinkConstants.DATE_FORMAT);
        String strDate = sdfDate.format(new Date());
        return strDate;
    }

    /**
     * Get the current time to update XDD/XDC file modification time.
     *
     * @return Current Time.
     */
    private static String getCurrentTime() {
        SimpleDateFormat sdfDate = new SimpleDateFormat(
                IPowerlinkConstants.TIME_FORMAT);
        String strDate = sdfDate.format(new Date());
        return strDate;
    }

    /**
     * Get the current time and date to update in the project XML file.
     *
     * @return Current time and date.
     */
    public static String getCurrentTimeandDate() {
        SimpleDateFormat sdfDate = new SimpleDateFormat(
                IPowerlinkConstants.DATE_TIME_FORMAT);
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    public static String getFolderName(File projectSystemFile)
            throws JDOMException, IOException {
        File xmlFile = projectSystemFile;
        org.jdom2.Document document = JDomUtil.getXmlDocument(xmlFile);
        return ProjectJDomOperation.getParent(document);
    }

    /**
     * Finds the settingId in the list of agSettings and returns the
     * TKeyValuePair instance
     *
     * @param agSettings autoGenerationSettings instance
     * @param settingId The setting to be searched in the list
     * @return TKeyValuePair buildConfigurationSetting, null otherwise
     */
    public static TKeyValuePair getSetting(TAutoGenerationSettings agSettings,
            final String settingId) {

        List<TKeyValuePair> settingsList = agSettings.getSetting();
        for (TKeyValuePair setting : settingsList) {
            if (setting.getName().equalsIgnoreCase(settingId)) {
                return setting;
            }
        }

        return null;
    }

    /**
     * Finds the id in the list of pathSettings and returns the TPath instance
     *
     * @param pathSettings autoGenerationSettings instance
     * @param id The setting to be searched in the list
     * @return TPath path, null otherwise
     */
    public static TPath getTPath(
            TProjectConfiguration.PathSettings pathSettings, final String id) {
        List<TPath> pathList = pathSettings.getPath();
        for (TPath path : pathList) {
            if (path.getId().equalsIgnoreCase(id)) {
                return path;
            }
        }

        return null;
    }

    /**
     * Returns the current Gregorian Calendar time. If any exception returns
     * null;
     *
     * @return XMLGregorianCalendar, null otherwise
     */
    public static XMLGregorianCalendar getXMLGregorianCalendarNow() {
        XMLGregorianCalendar now = null;

        try {
            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            DatatypeFactory datatypeFactory;
            datatypeFactory = DatatypeFactory.newInstance();
            now = datatypeFactory.newXMLGregorianCalendar(gregorianCalendar);
        } catch (DatatypeConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return now;
    }

    /**
     * Imports the firmware file into project path.
     *
     * @param firmwareFilePath The path of file to be imported into project.
     * @param firmwareMngr Instance of Firmware manager.
     * @throws IOException Errors with XDC file modifications.
     */
    public static void importFirmwareFile(Path firmwareFilePath,
            FirmwareManager firmwareMngr) throws IOException {
        IProject project = firmwareMngr.getProject();
        java.nio.file.Path firmwareImportFile = firmwareFilePath;
        java.nio.file.Path projectRootPath = project.getLocation().toFile()
                .toPath();
        String targetImportPath = StringUtils.EMPTY;
        if (firmwareFilePath != null) {
            if (firmwareFilePath.getFileName() != null) {
                File devFirmwareDirectory = new File(String
                        .valueOf(projectRootPath.toString() + IPath.SEPARATOR
                                + IPowerlinkProjectSupport.DEVICE_FIRMWARE_DIR));

                if (!devFirmwareDirectory.exists()) {
                    System.err.println("The directory does not exists....");
                    devFirmwareDirectory.mkdir();
                }

                targetImportPath = String.valueOf(projectRootPath.toString()
                        + IPath.SEPARATOR
                        + IPowerlinkProjectSupport.DEVICE_FIRMWARE_DIR
                        + IPath.SEPARATOR
                        + String.valueOf(firmwareFilePath.getFileName()));

                System.err.println("Source Directory: " + firmwareFilePath);
                System.err.println("Target Directory: " + targetImportPath);

                firmwareImportFile = java.nio.file.Files.copy(
                        new java.io.File(firmwareImportFile.toString())
                                .toPath(),
                        new java.io.File(targetImportPath).toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING,
                        java.nio.file.StandardCopyOption.COPY_ATTRIBUTES,
                        java.nio.file.LinkOption.NOFOLLOW_LINKS);

                java.nio.file.Path pathRelative = projectRootPath
                        .relativize(Paths.get(targetImportPath));

                String relativePath = pathRelative.toString();
                relativePath = relativePath.replace('\\', '/');

                firmwareMngr.setUri(relativePath);

            }
        } else {
            System.err.println(
                    "Firmware file not avilable in the specified path.");
        }

    }

    /**
     * Import the configuration file of module into project.
     *
     * @param newModule Instance of Module.
     * @throws IOException Errors with XDC file modifications.
     */
    public static void importModuleConfigurationFile(Module newModule)
            throws IOException {
        java.nio.file.Path moduleImportFile = new File(
                newModule.getModulePathToXdc()).toPath();
        java.nio.file.Path projectRootPath = newModule.getProject()
                .getLocation().toFile().toPath();
        String extensionXdd = StringUtils.EMPTY;
        if (moduleImportFile != null) {
            if ((moduleImportFile.getFileName() != null)) {
                extensionXdd = FilenameUtils.removeExtension(
                        moduleImportFile.getFileName().toString());

                extensionXdd += "_" + newModule.getPosition()
                        + IPowerlinkProjectSupport.XDC_EXTENSION;

                java.nio.file.Path nodeImportFile = new File(
                        newModule.getNode().getPathToXDC()).toPath();
                String nodeName = StringUtils.EMPTY;
                if (nodeImportFile != null) {
                    if ((nodeImportFile.getFileName() != null)) {
                        nodeName = FilenameUtils.removeExtension(
                                String.valueOf(nodeImportFile.getFileName()));

                        String targetConfigurationPath = projectRootPath
                                .toString() + IPath.SEPARATOR
                                + IPowerlinkProjectSupport.DEVICE_CONFIGURATION_DIR
                                + IPath.SEPARATOR + nodeName + IPath.SEPARATOR
                                + extensionXdd;
                        String targetDirectoryPath = projectRootPath.toString()
                                + IPath.SEPARATOR
                                + IPowerlinkProjectSupport.DEVICE_CONFIGURATION_DIR;

                        java.nio.file.Files
                                .createDirectories(Paths.get(targetDirectoryPath
                                        + IPath.SEPARATOR + nodeName));

                        java.nio.file.Files.copy(moduleImportFile,
                                new java.io.File(targetConfigurationPath)
                                        .toPath(),
                                java.nio.file.StandardCopyOption.COPY_ATTRIBUTES,
                                java.nio.file.StandardCopyOption.REPLACE_EXISTING,
                                java.nio.file.LinkOption.NOFOLLOW_LINKS);

                        java.nio.file.Path pathRelative = projectRootPath
                                .relativize(Paths.get(targetConfigurationPath));

                        String relativePath = pathRelative.toString();
                        relativePath = relativePath.replace('\\', '/');

                        newModule.setPathToXDC(relativePath);
                    }
                }
            }

        }
    }

    /**
     * Import node configuration file into the workspace. The path to the XDC
     * will be absolute and after import into project the path to XDC will be
     * update to relative.
     *
     * @param newNode The node XDC to be imported.
     * @throws Error with XDC/XDD file modification.
     */
    public static void importNodeConfigurationFile(Node newNode)
            throws IOException {

        java.nio.file.Path nodeImportFile = new File(newNode.getPathToXDC())
                .toPath();
        java.nio.file.Path projectRootPath = newNode.getProject().getLocation()
                .toFile().toPath();
        String targetImportPath = StringUtils.EMPTY;
        if (nodeImportFile != null) {
            if ((nodeImportFile.getFileName() != null)) {
                File importDirectory = new File(String
                        .valueOf(projectRootPath.toString() + IPath.SEPARATOR
                                + IPowerlinkProjectSupport.DEVICE_IMPORT_DIR));
                if (!importDirectory.exists()) {
                    System.err.println("The directory does not exists....");
                    importDirectory.mkdir();
                }

                File configDirectory = new File(String.valueOf(projectRootPath
                        .toString() + IPath.SEPARATOR
                        + IPowerlinkProjectSupport.DEVICE_CONFIGURATION_DIR));
                if (!configDirectory.exists()) {
                    System.err.println("The directory does not exists....");
                    configDirectory.mkdir();
                }

                File outputDirectory = new File(String
                        .valueOf(projectRootPath.toString() + IPath.SEPARATOR
                                + IPowerlinkProjectSupport.DEFAULT_OUTPUT_DIR));
                if (!outputDirectory.exists()) {
                    System.err.println("The directory does not exists....");
                    outputDirectory.mkdir();
                }

                targetImportPath = String
                        .valueOf(projectRootPath.toString() + IPath.SEPARATOR
                                + IPowerlinkProjectSupport.DEVICE_IMPORT_DIR
                                + IPath.SEPARATOR
                                + String.valueOf(nodeImportFile.getFileName()));

                // Copy the Node configuration to deviceImport dir

                nodeImportFile = java.nio.file.Files.copy(
                        new java.io.File(nodeImportFile.toString()).toPath(),
                        new java.io.File(targetImportPath).toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING,
                        java.nio.file.StandardCopyOption.COPY_ATTRIBUTES,
                        java.nio.file.LinkOption.NOFOLLOW_LINKS);

                // Rename the XDD to XDC and copy the deviceImport MN XDD to
                // deviceConfiguration dir
                String extensionXdd = StringUtils.EMPTY;
                if (nodeImportFile != null) {
                    if ((nodeImportFile.getFileName() != null)) {
                        extensionXdd = FilenameUtils.removeExtension(
                                String.valueOf(nodeImportFile.getFileName()));

                        // Append node ID and the 'XDC' extension to the
                        // configuration
                        // file.
                        extensionXdd += "_" + newNode.getCnNodeId()
                                + IPowerlinkProjectSupport.XDC_EXTENSION;

                        String targetConfigurationPath = projectRootPath
                                .toString() + IPath.SEPARATOR
                                + IPowerlinkProjectSupport.DEVICE_CONFIGURATION_DIR
                                + IPath.SEPARATOR + extensionXdd;

                        java.nio.file.Files.copy(nodeImportFile,
                                new java.io.File(targetConfigurationPath)
                                        .toPath(),
                                java.nio.file.StandardCopyOption.REPLACE_EXISTING,
                                java.nio.file.StandardCopyOption.COPY_ATTRIBUTES,
                                java.nio.file.LinkOption.NOFOLLOW_LINKS);

                        // Set the relative path to the CN object
                        java.nio.file.Path pathRelative = projectRootPath
                                .relativize(Paths.get(targetConfigurationPath));

                        String relativePath = pathRelative.toString();
                        relativePath = relativePath.replace('\\', '/');

                        newNode.setPathToXDC(relativePath);
                    }
                }
            }
        }
    }

    private static void importNodeXDCFile(Node node) throws IOException {
        java.nio.file.Path nodeImportFile = new File(
                node.getAbsolutePathToXdc()).toPath();
        java.nio.file.Path projectRootPath = node.getProject().getLocation()
                .toFile().toPath();
        String targetImportPath = StringUtils.EMPTY;
        if (nodeImportFile != null) {
            if ((nodeImportFile.getFileName() != null)) {
                targetImportPath = String
                        .valueOf(projectRootPath.toString() + IPath.SEPARATOR
                                + IPowerlinkProjectSupport.DEFAULT_OUTPUT_DIR
                                + IPath.SEPARATOR
                                + String.valueOf(nodeImportFile.getFileName()));

                // Copy the Node configuration to deviceImport dir
                // The declared local variable performs copy operation and will
                // not be used anywhere.
                nodeImportFile = java.nio.file.Files.copy(
                        new java.io.File(nodeImportFile.toString()).toPath(),
                        new java.io.File(targetImportPath).toPath(),
                        java.nio.file.StandardCopyOption.REPLACE_EXISTING,
                        java.nio.file.StandardCopyOption.COPY_ATTRIBUTES,
                        java.nio.file.LinkOption.NOFOLLOW_LINKS);
            }
        }
    }

    /**
     * Verify the availability of path from the project configuration
     *
     * @param pathSettingsModel Instance of XDD model.
     * @param id Id of project.
     * @return <code>true</code> if path is present, <code>false</code>
     *         otherwise.
     */
    public static boolean isPathIdAlreadyPresent(
            TProjectConfiguration.PathSettings pathSettingsModel,
            final String id) {
        List<TPath> pathList = pathSettingsModel.getPath();
        for (TPath path : pathList) {
            if (path.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Create a Default openCONFIGURATOR project instance.
     *
     * @param mn MN node instance that has to be added by default.
     * @return new OpenCONFIGURATORProject instance / null if any errors
     */
    public static OpenCONFIGURATORProject newDefaultOpenCONFIGURATORProject(
            final TMN mn) {

        if (mn == null) {
            return null;
        }

        OpenCONFIGURATORProject openConfiguratorProject = new OpenCONFIGURATORProject();
        TGenerator tGenerator = new TGenerator();
        tGenerator.setVendor(OpenConfiguratorProjectUtils.GENERATOR_VENDOR);
        tGenerator.setCreatedBy(System
                .getProperty(OpenConfiguratorProjectUtils.SYSTEM_USER_NAME_ID)
                .trim());
        tGenerator
                .setToolName(OpenConfiguratorProjectUtils.GENERATOR_TOOL_NAME);
        tGenerator.setToolVersion(
                OpenConfiguratorProjectUtils.GENERATOR_TOOL_VERSION);
        tGenerator.setCreatedOn(
                OpenConfiguratorProjectUtils.getXMLGregorianCalendarNow());
        tGenerator.setModifiedOn(
                OpenConfiguratorProjectUtils.getXMLGregorianCalendarNow());
        tGenerator.setModifiedBy(System
                .getProperty(OpenConfiguratorProjectUtils.SYSTEM_USER_NAME_ID)
                .trim());
        openConfiguratorProject.setGenerator(tGenerator);

        // Add default project configurations
        TProjectConfiguration tProjectConfiguration = new TProjectConfiguration();
        tProjectConfiguration.setActiveAutoGenerationSetting(
                OpenConfiguratorProjectUtils.AUTO_GENERATION_SETTINGS_ALL_ID);

        // Add default output path
        TProjectConfiguration.PathSettings pathSettings = new TProjectConfiguration.PathSettings();
        java.util.List<TPath> pathList = pathSettings.getPath();
        TPath path = new TPath();
        path.setId(OpenConfiguratorProjectUtils.PATH_SETTINGS_DEFAULT_PATH_ID);
        path.setPath(
                OpenConfiguratorProjectUtils.PATH_SETTINGS_DEFAUTL_PATH_VALUE);
        pathList.add(path);
        tProjectConfiguration.setPathSettings(pathSettings);

        // Auto generation settings
        java.util.List<TAutoGenerationSettings> autoGenerationSettingsList = tProjectConfiguration
                .getAutoGenerationSettings();
        TAutoGenerationSettings tAutoGenerationSettings_all = new TAutoGenerationSettings();
        tAutoGenerationSettings_all.setId(
                OpenConfiguratorProjectUtils.AUTO_GENERATION_SETTINGS_ALL_ID);

        java.util.List<TKeyValuePair> allSettingsList = tAutoGenerationSettings_all
                .getSetting();
        for (String buildConfiguration : OpenConfiguratorProjectUtils.defaultBuildConfigurationIdList) {
            TKeyValuePair buildConfig = new TKeyValuePair();
            buildConfig.setName(buildConfiguration.trim());
            buildConfig.setValue("");
            buildConfig.setEnabled(true);

            allSettingsList.add(buildConfig);
        }

        autoGenerationSettingsList.add(tAutoGenerationSettings_all);

        TAutoGenerationSettings tAutoGenerationSettings_none = new TAutoGenerationSettings();
        tAutoGenerationSettings_none.setId(
                OpenConfiguratorProjectUtils.AUTO_GENERATION_SETTINGS_NONE_ID);
        autoGenerationSettingsList.add(tAutoGenerationSettings_none);

        TAutoGenerationSettings tAutoGenerationSettings_custom = new TAutoGenerationSettings();
        tAutoGenerationSettings_custom.setId(
                OpenConfiguratorProjectUtils.AUTO_GENERATION_SETTINGS_CUSTOM_ID);
        autoGenerationSettingsList.add(tAutoGenerationSettings_custom);

        // Add Network configurations
        TNodeCollection nc = new TNodeCollection();
        nc.setMN(mn);

        TNetworkConfiguration tNetworkConfiguration = new TNetworkConfiguration();
        tNetworkConfiguration.setNodeCollection(nc);

        openConfiguratorProject.setProjectConfiguration(tProjectConfiguration);
        openConfiguratorProject.setNetworkConfiguration(tNetworkConfiguration);

        return openConfiguratorProject;
    }

    /**
     * Persists the nodes actual value into the XDC.
     *
     * @param node The node instance.
     * @return Result from the library.
     * @throws Error with XDC/XDD file modification.
     * @throws JDOMException Error with time modifications
     */
    public static Result persistNodeData(Node node)
            throws JDOMException, IOException {

        File xdcFile = new File(node.getAbsolutePathToXdc());

        org.jdom2.Document document = JDomUtil.getXmlDocument(xdcFile);

        // Delete the actual value from the model.
        for (PowerlinkObject obj : node.getObjectDictionary()
                .getObjectsList()) {
            List<PowerlinkSubobject> subObjList = obj.getSubObjects();
            if (!subObjList.isEmpty()) {
                for (PowerlinkSubobject subObj : subObjList) {
                    subObj.deleteActualValue();
                }
            } else {
                obj.deleteActualValue();
            }
        }

        // Delete the actual value field on all objects available in the
        // file.
        XddJdomOperation.deleteActualValues(document);

        // Prepare the Java based object collection.
        java.util.LinkedHashMap<java.util.Map.Entry<Long, Integer>, String> objectJCollection = new LinkedHashMap<>();
        Result res = OpenConfiguratorLibraryUtils
                .getObjectsWithActualValue(node, objectJCollection);
        if (!res.IsSuccessful()) {
            OpenConfiguratorMessageConsole.getInstance()
                    .printLibraryErrorMessage(res);
            return res;
        }

        node.writeObjectActualValues(objectJCollection, document);

        writeToXddXmlDocument(document, xdcFile);

        return res;
    }

    /**
     * Removes the connected modules in the XDD file
     *
     * @param node Instance Of Node
     * @param module Instance of Module
     * @param finalModuleCheck Boolean value of final module
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public static void removeConnectedModulesList(Node node, Module module,
            boolean finalModuleCheck) throws JDOMException, IOException {
        File xdcFile = new File(node.getAbsolutePathToXdc());
        org.jdom2.Document document = JDomUtil.getXmlDocument(xdcFile);

        XddJdomOperation.deleteConnectedModules(document, module,
                finalModuleCheck);

        writeToXddXmlDocument(document, xdcFile);

        // Updates generator attributes in project file.
        updateGeneratorInfo(module.getNode());

    }

    public static void removeIDEConfiguration(IFile projectFile)
            throws JDOMException, IOException {
        String projectXmlLocation = projectFile.getLocation().toString();
        File xmlFile = new File(projectXmlLocation);
        org.jdom2.Document document = JDomUtil.getXmlDocument(xmlFile);
        ProjectJDomOperation.removeIDEConfigurationSettings(document);

        JDomUtil.writeToProjectXmlDocument(document, xmlFile);
    }

    private static boolean removeXmlheader(File updatedFirmwareFile)
            throws IOException {
        BufferedReader bufferedRdr = null;
        BufferedWriter bufferedWriter = null;
        FileWriter fileWriter = null;
        boolean isFirmwareFileUpdate = false;
        try {
            bufferedRdr = new BufferedReader(
                    new FileReader(updatedFirmwareFile));
            String firmwareHeaderLines = StringUtils.EMPTY;
            String firmwareline = StringUtils.EMPTY;
            while ((firmwareHeaderLines = bufferedRdr.readLine()) != null) {
                firmwareline += firmwareHeaderLines + "\n";
            }
            int firmwareEndIndex = firmwareline.indexOf(">");
            // Removes the header of firmware file.
            firmwareline = firmwareline.substring(firmwareEndIndex + 1);
            fileWriter = new FileWriter(updatedFirmwareFile);
            bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(firmwareline);
            isFirmwareFileUpdate = true;
        } catch (RuntimeException e) {
            if (bufferedRdr != null) {
                bufferedRdr.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (fileWriter != null) {
                fileWriter.close();
            }
            throw e;
        } catch (Exception e) {
            if (bufferedRdr != null) {
                bufferedRdr.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (fileWriter != null) {
                fileWriter.close();
            }
            e.printStackTrace();
        } finally {
            if (bufferedRdr != null) {
                bufferedRdr.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (fileWriter != null) {
                fileWriter.close();
            }

        }
        return isFirmwareFileUpdate;
    }

    /**
     * Update the position and address of module in the project file for move
     * up/down menu actions.
     *
     * @param module Instance of module to which the project file has to be
     *            updated.
     * @param attributeName Name of the attribute to be updated.
     * @param attributeValue Value to be updated.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public static void swapModuleAttributeValue(Module module,
            String attributeName, String attributeValue)
            throws IOException, JDOMException {
        String projectXmlLocation = module.getNode().getProjectXml()
                .getLocation().toString();
        File xmlFile = new File(projectXmlLocation);

        org.jdom2.Document document = JDomUtil.getXmlDocument(xmlFile);
        System.err.println("The position value == " + attributeValue
                + " module name.." + module.getModuleName());
        ProjectJDomOperation.swapModuleAttributeValue(document, module,
                attributeName, attributeValue);

        JDomUtil.writeToProjectXmlDocument(document, xmlFile);

        System.err.println("Successfully updted in the project file.....");
        // Updates generator attributes in project file.
        updateGeneratorInfo(module.getNode());

    }

    /**
     * Updated the connected module in the XDD file.
     *
     * @param node Instance Of Node
     * @param module Instance of Module
     * @param moduleCollection collection of module
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public static void updateConnectedModuleList(Node node,
            HeadNodeInterface headNodeInterface,
            Map<Integer, Module> moduleCollection)
            throws JDOMException, IOException {
        File xdcFile = new File(node.getAbsolutePathToXdc());
        org.jdom2.Document document = JDomUtil.getXmlDocument(xdcFile);

        XddJdomOperation.addConnectedModules(document, headNodeInterface,
                moduleCollection);

        writeToXddXmlDocument(document, xdcFile);

        // Updates generator attributes in project file.
        updateGeneratorInfo(node);

    }

    /**
     * Update date, time, tool version and modified by attributes on project
     * file.
     *
     * @param node Instance of node.
     * @throws IOException Error with XDC/XDD file modification.
     * @throws JDOMException Error with time modifications
     */
    public static void updateGeneratorInfo(Node node)
            throws JDOMException, IOException {
        String projectXmlLocation = node.getProjectXml().getLocation()
                .toString();
        File xmlFile = new File(projectXmlLocation);

        org.jdom2.Document document = JDomUtil.getXmlDocument(xmlFile);

        ProjectJDomOperation.updateGeneratorAttribute(document,
                MODIFIED_ON_ATTRIBUTE, getCurrentTimeandDate());

        ProjectJDomOperation.updateGeneratorAttribute(document,
                TOOL_VERSION_ATTRIBUTE, GENERATOR_TOOL_VERSION);
        String modifiedByName = System
                .getProperty(OpenConfiguratorProjectUtils.SYSTEM_USER_NAME_ID);
        ProjectJDomOperation.updateGeneratorAttribute(document,
                MODIFIED_BY_ATTRIBUTE, modifiedByName);

        OpenCONFIGURATORProject openConfiguratorProject = node
                .getCurrentProject();
        updateGeneratorInformation(openConfiguratorProject);

        JDomUtil.writeToProjectXmlDocument(document, xmlFile);
    }

    /**
     * Update the Generator informations to the current values.
     *
     * @param project The current openCONFIGURATOR project instance.
     */
    public static void updateGeneratorInformation(
            OpenCONFIGURATORProject project) {

        TGenerator generator = project.getGenerator();

        if (generator == null) {
            return;
        }

        String createdBy = generator.getCreatedBy();
        if ((createdBy == null) || createdBy.isEmpty()) {
            generator.setCreatedBy(System
                    .getProperty(
                            OpenConfiguratorProjectUtils.SYSTEM_USER_NAME_ID)
                    .trim());
        }

        generator.setToolVersion(
                OpenConfiguratorProjectUtils.GENERATOR_TOOL_VERSION);
        generator.setModifiedOn(
                OpenConfiguratorProjectUtils.getXMLGregorianCalendarNow());
        generator.setModifiedBy(System
                .getProperty(OpenConfiguratorProjectUtils.SYSTEM_USER_NAME_ID)
                .trim());
    }

    /**
     * Updated the attribute of module in the project file.
     *
     * @param module Instance of Module
     * @param attributeName Name of attribute to be updated.
     * @param attributeValue value to be updated.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public static void updateModuleAttributeValue(final Module module,
            final String attributeName, final String attributeValue)
            throws JDOMException, IOException {

        String projectXmlLocation = module.getNode().getProjectXml()
                .getLocation().toString();
        File xmlFile = new File(projectXmlLocation);

        org.jdom2.Document document = JDomUtil.getXmlDocument(xmlFile);
        System.err.println("The position value == " + attributeValue
                + " module name.." + module.getModuleName());
        ProjectJDomOperation.updateModuleAttributeValue(document, module,
                attributeName, attributeValue);

        JDomUtil.writeToProjectXmlDocument(document, xmlFile);

        System.err.println("Successfully updted in the project file.....");
        // Updates generator attributes in project file.
        updateGeneratorInfo(module.getNode());
    }

    public static void updateModuleConfigurationPath(Module module,
            int position) throws JDOMException, IOException {

        java.nio.file.Path nodeImportFile = new File(
                module.getNode().getPathToXDC()).toPath();
        if (module.getModulePathToXdc() != null) {
            java.nio.file.Path moduleImportFile = new File(
                    module.getModulePathToXdc()).toPath();

            java.nio.file.Path projectRootPath = module.getProject()
                    .getLocation().toFile().toPath();
            String xddNameWithExtension = StringUtils.EMPTY;
            if (moduleImportFile != null) {
                if ((moduleImportFile.getFileName() != null)) {
                    xddNameWithExtension = String
                            .valueOf(moduleImportFile.getFileName());
                    String oldNodeSuffix = "_" + module.getPosition()
                            + IPowerlinkProjectSupport.XDC_EXTENSION;

                    String xddFileNameWithNoSuffix = xddNameWithExtension
                            .substring(0, xddNameWithExtension.length()
                                    - oldNodeSuffix.length());

                    System.err.println("XDD file name with no suffix....."
                            + xddFileNameWithNoSuffix);

                    // Append node ID and the 'XDC' extension to the
                    // configuration
                    // file.
                    String xddFileNameWithSuffix = xddFileNameWithNoSuffix + "_"
                            + position + IPowerlinkProjectSupport.XDC_EXTENSION;
                    String nodeName = StringUtils.EMPTY;
                    if (nodeImportFile != null) {
                        if (nodeImportFile.getFileName() != null) {
                            nodeName = FilenameUtils.removeExtension(String
                                    .valueOf(nodeImportFile.getFileName()));

                            String targetConfigurationPath = projectRootPath
                                    .toString() + IPath.SEPARATOR
                                    + IPowerlinkProjectSupport.DEVICE_CONFIGURATION_DIR
                                    + IPath.SEPARATOR + nodeName
                                    + IPath.SEPARATOR + xddFileNameWithSuffix;

                            System.out.println("Target configuration path ..."
                                    + targetConfigurationPath);

                            java.nio.file.Path pathRelative = projectRootPath
                                    .relativize(
                                            Paths.get(targetConfigurationPath));
                            File unModifiedfile = new File(
                                    projectRootPath + "/" + moduleImportFile);
                            System.err.println("Unmodified file path ==="
                                    + unModifiedfile.getAbsolutePath());

                            File updatedfile = new File(
                                    projectRootPath + "/" + pathRelative);
                            System.err.println("updatedfile file path ==="
                                    + updatedfile.getAbsolutePath());

                            if (unModifiedfile.renameTo(updatedfile)) {
                                System.out.println("Name of File '"
                                        + unModifiedfile.getName()
                                        + "' is modified as '"
                                        + updatedfile.getName()
                                        + "' with respect to change in nodeId.");
                            } else {
                                System.err.println("File name not modified.");
                            }
                            String relativePath = pathRelative.toString();
                            relativePath = relativePath.replace('\\', '/');
                            System.out.println(
                                    "The relatiove path = " + relativePath);

                            // Set the relative path to the CN object
                            module.setPathToXDC(relativePath);
                            System.err.println("The module get Path to XDC.."
                                    + module.getModulePathToXdc());
                        }
                    }
                }
            }
        }
        // Updates generator attributes in project file.
        updateGeneratorInfo(module.getNode());
    }

    /**
     * Updates the index of module object in XDC file
     *
     * @param module Instance of Module.
     * @param moduleObjectIndex Index of object from the library
     * @param object Instance of POWERLINK object
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public static void updateModuleObjectIndex(Module module,
            long moduleObjectIndex, PowerlinkObject object)
            throws JDOMException, IOException {
        File xdcFile = new File(module.getAbsolutePathToXdc());
        org.jdom2.Document document = JDomUtil.getXmlDocument(xdcFile);

        XddJdomOperation.updateModuleObjectIndex(document, module,
                moduleObjectIndex, object);

        writeToXddXmlDocument(document, xdcFile);

        // Updates generator attributes in project file.
        updateGeneratorInfo(module.getNode());

    }

    /**
     * Updates the object of module into the head node.
     *
     * @param node Instance of Node.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public static Status updateModuleObjectInNode(Node node,
            IProgressMonitor monitor) throws JDOMException, IOException {
        monitor.subTask("Export module node XDC:");
        importNodeXDCFile(node);
        File xdcFile = new File(node.getOutputPathToXdc());
        org.jdom2.Document document = JDomUtil.getXmlDocument(xdcFile);

        XddJdomOperation.updateModuleObjectInNode(document, node);

        XddJdomOperation.updateNumberOfEntries(document, node);

        XddJdomOperation.addConnectedModules(document, node.getInterface(),
                node.getInterface().getModuleCollection());

        writeToXddXmlDocument(document, xdcFile);

        // Updates generator attributes in project file.
        updateGeneratorInfo(node);
        monitor.done();
        if (monitor.isCanceled()) {
            return new Status(IStatus.OK,
                    org.epsg.openconfigurator.Activator.PLUGIN_ID, "Cancelled",
                    null);
        }

        return new Status(IStatus.OK,
                org.epsg.openconfigurator.Activator.PLUGIN_ID, "OK", null);
    }

    /**
     * Updates the sub-object of module into node.
     *
     * @param node Instance of Node
     * @param module Instance of Module.
     * @param index Index of object
     * @param subObject Instance of POWERLINK sub-object.
     * @param subIndex sub-index of sub-object.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public static void updateModuleSubObjectInNode(Node node, Module module,
            long index, PowerlinkSubobject subObject, int subIndex)
            throws JDOMException, IOException {
        File xdcFile = new File(node.getOutputPathToXdc());
        org.jdom2.Document document = JDomUtil.getXmlDocument(xdcFile);

        XddJdomOperation.updateModuleSubObjectInNode(document, module,
                subObject, node, index, subIndex);

        writeToXddXmlDocument(document, xdcFile);

        // Updates generator attributes in project file.
        updateGeneratorInfo(node);

    }

    /**
     * Update the network configuration attributes in the project XML file.
     *
     * @param node The node
     * @param attributeName The attribute tag name.
     * @param attributeValue The value to be applied.
     * @throws IOException Error with XDC/XDD file modification.
     * @throws JDOMException Error with time modifications
     */
    public static void updateNetworkAttributeValue(final Node node,
            final String attributeName, final String attributeValue)
            throws JDOMException, IOException {

        String projectXmlLocation = node.getProjectXml().getLocation()
                .toString();
        File xmlFile = new File(projectXmlLocation);

        org.jdom2.Document document = JDomUtil.getXmlDocument(xmlFile);

        ProjectJDomOperation.updateNetworkAttributeValue(document,
                attributeName, attributeValue);

        JDomUtil.writeToProjectXmlDocument(document, xmlFile);

        // Updates generator attributes in project file.
        updateGeneratorInfo(node);
    }

    /**
     * Persists the node Assignment values in the model and the project XML
     * file.
     *
     * Note: User has to updates the generator attributes in project file.
     *
     * @param node The node instance.
     * @throws IOException Error with XDC/XDD file modification.
     * @throws JDOMException Error with time modifications
     */
    public static void updateNodeAssignmentValues(final Node node)
            throws JDOMException, IOException {
        long nodeAssignmentValue = OpenConfiguratorLibraryUtils
                .getNodeAssignment(node);

        String projectXmlLocation = node.getProjectXml().getLocation()
                .toString();
        File xmlFile = new File(projectXmlLocation);

        org.jdom2.Document document = JDomUtil.getXmlDocument(xmlFile);

        NodeAssignment[] nodeAssignList = NodeAssignment.values();

        for (NodeAssignment na : nodeAssignList) {
            boolean nodeAssignmentEnabled = false;
            if ((nodeAssignmentValue & na.swigValue()) == na.swigValue()) {
                nodeAssignmentEnabled = true;
            } else {
                nodeAssignmentEnabled = false;
            }

            // Update in the model immediately.
            boolean updatedInTheModel = node.setNodeAssignment(na,
                    nodeAssignmentEnabled);

            if (updatedInTheModel) {
                String attributeName = Node.getNodeAssignment(na);
                if (attributeName.isEmpty()) {
                    continue;
                }

                ProjectJDomOperation.updateNodeAttributeValue(document, node,
                        attributeName, String.valueOf(nodeAssignmentEnabled));
            }
        }

        JDomUtil.writeToProjectXmlDocument(document, xmlFile);
    }

    /**
     * Update the node attributes in the project XML file.
     *
     * @param node The node to apply the attributes.
     * @param attributeName The attribute tag name.
     * @param attributeValue The value to be applied.
     * @throws IOException Error with XDC/XDD file modification.
     * @throws JDOMException Error with time modifications
     */
    public static void updateNodeAttributeValue(final Node node,
            final String attributeName, final String attributeValue)
            throws JDOMException, IOException {

        String projectXmlLocation = node.getProjectXml().getLocation()
                .toString();
        File xmlFile = new File(projectXmlLocation);

        org.jdom2.Document document = JDomUtil.getXmlDocument(xmlFile);

        ProjectJDomOperation.updateNodeAttributeValue(document, node,
                attributeName, attributeValue);

        JDomUtil.writeToProjectXmlDocument(document, xmlFile);
        // Updates generator attributes in project file.
        updateGeneratorInfo(node);
    }

    /**
     * Updates the node configuration file with the new node id.
     *
     * @param node The node instance.
     *
     * @param newNodeId The new node id to be set.
     *
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public static void updateNodeConfigurationPath(Node node, String newNodeId)
            throws IOException, JDOMException {

        java.nio.file.Path nodeImportFile = new File(node.getPathToXDC())
                .toPath();

        java.nio.file.Path projectRootPath = node.getProject().getLocation()
                .toFile().toPath();

        String xddNameWithExtension = StringUtils.EMPTY;
        if (nodeImportFile != null) {
            if ((nodeImportFile.getFileName() != null)) {
                xddNameWithExtension = String
                        .valueOf(nodeImportFile.getFileName());
                String oldNodeSuffix = "_" + node.getCnNodeId()
                        + IPowerlinkProjectSupport.XDC_EXTENSION;

                String xddFileNameWithNoSuffix = xddNameWithExtension.substring(
                        0,
                        xddNameWithExtension.length() - oldNodeSuffix.length());

                // Append node ID and the 'XDC' extension to the configuration
                // file.
                String xddFileNameWithSuffix = xddFileNameWithNoSuffix + "_"
                        + newNodeId + IPowerlinkProjectSupport.XDC_EXTENSION;

                String targetConfigurationPath = String
                        .valueOf(projectRootPath.toString() + IPath.SEPARATOR
                                + IPowerlinkProjectSupport.DEVICE_CONFIGURATION_DIR
                                + IPath.SEPARATOR + xddFileNameWithSuffix);

                java.nio.file.Path pathRelative = projectRootPath
                        .relativize(Paths.get(targetConfigurationPath));
                File unModifiedfile = new File(
                        projectRootPath + "/" + nodeImportFile);
                File updatedfile = new File(
                        projectRootPath + "/" + pathRelative);

                Files.move(unModifiedfile.toPath(), updatedfile.toPath(),
                        StandardCopyOption.REPLACE_EXISTING);

                String relativePath = pathRelative.toString();
                relativePath = relativePath.replace('\\', '/');

                // Set the relative path to the CN object
                node.setPathToXDC(relativePath);
            }
        }
        // Updates generator attributes in project file.
        updateGeneratorInfo(node);
    }

    public static void updateObjectAttributeActualValue(Module module,
            PowerlinkObject powerlinkObject, String actualValue)
            throws IOException, JDOMException {
        File xdcFile = new File(module.getAbsolutePathToXdc());
        org.jdom2.Document document = JDomUtil.getXmlDocument(xdcFile);

        XddJdomOperation.updateActualValue(document, powerlinkObject,
                actualValue);

        writeToXddXmlDocument(document, xdcFile);

        // Updates generator attributes in project file.
        updateGeneratorInfo(module.getNode());

    }

    public static void updateObjectAttributeActualValue(Module module,
            PowerlinkSubobject object, String actualValue)
            throws IOException, JDOMException {
        File xdcFile = new File(module.getAbsolutePathToXdc());
        org.jdom2.Document document = JDomUtil.getXmlDocument(xdcFile);

        XddJdomOperation.updateActualValue(document, object, actualValue);

        writeToXddXmlDocument(document, xdcFile);

        // Updates generator attributes in project file.
        updateGeneratorInfo(module.getNode());

    }

    /**
     * Update the actual value of object in XDD/XDC file.
     *
     * @param node The node instance
     * @param object The object instance
     * @param actualValue The modified value of object
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications
     */
    public static void updateObjectAttributeActualValue(final Node node,
            final PowerlinkObject object, String actualValue)
            throws IOException, JDOMException {
        File xdcFile = new File(node.getAbsolutePathToXdc());
        org.jdom2.Document document = JDomUtil.getXmlDocument(xdcFile);

        XddJdomOperation.updateActualValue(document, object, actualValue);

        writeToXddXmlDocument(document, xdcFile);

        // Updates generator attributes in project file.
        updateGeneratorInfo(node);
    }

    /**
     * Update the actual value of sub-object in XDD/XDC file.
     *
     * @param node The node instance
     * @param object The sub-object instance
     * @param actualValue The modified value of object
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications
     */
    public static void updateObjectAttributeActualValue(final Node node,
            final PowerlinkSubobject object, String actualValue)
            throws IOException, JDOMException {
        File xdcFile = new File(node.getAbsolutePathToXdc());
        org.jdom2.Document document = JDomUtil.getXmlDocument(xdcFile);

        XddJdomOperation.updateActualValue(document, object, actualValue);

        writeToXddXmlDocument(document, xdcFile);

        // Updates generator attributes in project file.
        updateGeneratorInfo(node);
    }

    /**
     * Update actual value of parameter into the device configuration file.
     *
     * @param module Instance of Module.
     * @param parameter Parameter instance
     * @param actualValue The value to be updated into the parameter.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public static void updateParameterActualValue(Module module,
            Parameter parameter, String actualValue)
            throws JDOMException, IOException {
        File xdcFile = new File(module.getAbsolutePathToXdc());
        org.jdom2.Document document = JDomUtil.getXmlDocument(xdcFile);

        XddJdomOperation.updateParameterActualValue(document, parameter,
                actualValue);

        writeToXddXmlDocument(document, xdcFile);

        // Updates generator attributes in project file.
        updateGeneratorInfo(module.getNode());
    }

    /**
     * Update actual value of parameter into the device configuration file.
     *
     * @param node Instance of Node.
     * @param parameter Parameter instance
     * @param actualValue The value to be updated into the parameter.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public static void updateParameterActualValue(Node node,
            Parameter parameter, String actualValue)
            throws JDOMException, IOException {
        File xdcFile = new File(node.getAbsolutePathToXdc());
        org.jdom2.Document document = JDomUtil.getXmlDocument(xdcFile);

        XddJdomOperation.updateParameterActualValue(document, parameter,
                actualValue);

        writeToXddXmlDocument(document, xdcFile);

        // Updates generator attributes in project file.
        updateGeneratorInfo(node);

    }

    /**
     * Update actual value of parameter reference into the device configuration
     * file.
     *
     * @param node Instance of Node.
     * @param parameterReference ParameterReference instance
     * @param actualValue The value to be updated into the parameter.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public static void updateParameterReferenceActualValue(Node node,
            ParameterReference parameterReference, String actualValue)
            throws JDOMException, IOException {
        File xdcFile = new File(node.getAbsolutePathToXdc());
        org.jdom2.Document document = JDomUtil.getXmlDocument(xdcFile);

        XddJdomOperation.updateParameterReferenceActualValue(document,
                parameterReference, actualValue);

        writeToXddXmlDocument(document, xdcFile);

        // Updates generator attributes in project file.
        updateGeneratorInfo(node);

    }

    /**
     * Updates the actual values available in the mapping param of the given PDO
     * channel.
     *
     * @param pdoChannel The channel to be updated.
     * @return The result from the library.
     * @throws IOExceptionErrors with XDC file modifications.
     * @throws JDOMException Errors with time modifications
     */
    public static Result updatePdoChannelActualValue(
            final PdoChannel pdoChannel) throws JDOMException, IOException {

        Node node = pdoChannel.getNode();
        File xdcFile = new File(node.getAbsolutePathToXdc());

        org.jdom2.Document document = JDomUtil.getXmlDocument(xdcFile);

        // Delete the mapping actual values from the model.
        List<PowerlinkSubobject> subObjList = pdoChannel.getMappingParam()
                .getSubObjects();
        if (!subObjList.isEmpty()) {
            for (PowerlinkSubobject subObj : subObjList) {
                subObj.deleteActualValue();
            }
        }

        // Delete the mapping actual values from the XDC file.
        XddJdomOperation.deletePowerlinkObjectActualValue(document,
                pdoChannel.getMappingParam());

        // Prepare the Java based object collection.
        java.util.LinkedHashMap<java.util.Map.Entry<Long, Integer>, String> objectJCollection = new LinkedHashMap<>();
        Result res = OpenConfiguratorLibraryUtils
                .getChannelObjectsWithActualValue(pdoChannel,
                        objectJCollection);
        if (!res.IsSuccessful()) {
            OpenConfiguratorMessageConsole.getInstance()
                    .printLibraryErrorMessage(res);
            return res;
        }

        node.writeObjectActualValues(objectJCollection, document);

        writeToXddXmlDocument(document, xdcFile);
        // Updates generator attributes in project file.
        updateGeneratorInfo(node);

        try {
            node.getProject().refreshLocal(IResource.DEPTH_INFINITE,
                    new NullProgressMonitor());
        } catch (CoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return res;
    }

    /**
     * Update the source file of project during import operation
     *
     * @param projectSystemFile2 Instance of project XML file.
     * @throws IOExceptionErrors with XDC file modifications.
     * @throws JDOMException Errors with time modifications
     */
    public static void updateProjectSourceFile(File projectSystemFile2)
            throws JDOMException, IOException {
        File xmlFile = projectSystemFile2;
        org.jdom2.Document document = JDomUtil.getXmlDocument(xmlFile);

        ProjectJDomOperation.removeIDEConfigurationSettings(document);

        ProjectJDomOperation.updateOutputPath(document);

        ProjectJDomOperation.updateAutoGenerationSettings(document);

        // ProjectJDomOperation.updatePathToXDC(document);

        JDomUtil.writeToProjectXmlDocument(document, xmlFile);

    }

    /**
     * Updates the XDC path of project XML file during import operation
     *
     * @param projectSystemFile Instance of project XML file.
     * @throws IOExceptionErrors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public static void updateProjectXDCSourceFile(File projectSystemFile)
            throws JDOMException, IOException {
        File xmlFile = projectSystemFile;
        org.jdom2.Document document = JDomUtil.getXmlDocument(xmlFile);

        ProjectJDomOperation.updateMNPathTOXDC(document);

        ProjectJDomOperation.updateRMNPathTOXDC(document);

        ProjectJDomOperation.updateCNPathTOXDC(document);

        // ProjectJDomOperation.updatePathToXDC(document);

        JDomUtil.writeToProjectXmlDocument(document, xmlFile);
    }

    /**
     * Upgrade openCONFIGURATOR project to conform with 2.0 schema. Add default
     * fields for AutoGeneration Settings.
     *
     * @param project OpenCONFIGURATOR project
     */
    public static boolean upgradeOpenConfiguratorProject(
            OpenCONFIGURATORProject project) {

        if ((project == null) || (project.getGenerator() == null)
                || (project.getGenerator().getToolVersion() == null)) {
            return false;
        }
        TGenerator generator = project.getGenerator();
        String toolVersionAvailable = generator.getToolVersion();
        if (toolVersionAvailable.equalsIgnoreCase("2.0.0-pre-release")) {
            OpenConfiguratorMessageConsole.getInstance()
                    .printInfoMessage(MessageFormat.format(UPGRADE_MESSAGE,
                            toolVersionAvailable, GENERATOR_TOOL_VERSION), "");

            generator.setToolName(GENERATOR_TOOL_NAME);
            generator.setVendor(GENERATOR_VENDOR);
            OpenConfiguratorProjectUtils.updateGeneratorInformation(project);
            return true;
        } else if ((toolVersionAvailable.equalsIgnoreCase("1.4.1")
                || toolVersionAvailable.equalsIgnoreCase("1.4.0")
                || toolVersionAvailable.equalsIgnoreCase("1.0"))) {
            OpenConfiguratorMessageConsole.getInstance()
                    .printInfoMessage(MessageFormat.format(UPGRADE_MESSAGE,
                            toolVersionAvailable, GENERATOR_TOOL_VERSION), "");

            generator.setToolName(GENERATOR_TOOL_NAME);
            generator.setVendor(GENERATOR_VENDOR);

            TProjectConfiguration projectConfiguration = project
                    .getProjectConfiguration();
            if (projectConfiguration != null) {
                java.util.List<TAutoGenerationSettings> autoGenerationSettingsList = projectConfiguration
                        .getAutoGenerationSettings();
                for (TAutoGenerationSettings agSettings : autoGenerationSettingsList) {
                    if (agSettings.getId().equalsIgnoreCase(
                            OpenConfiguratorProjectUtils.AUTO_GENERATION_SETTINGS_ALL_ID)) {
                        List<TKeyValuePair> settingsList = agSettings
                                .getSetting();

                        for (String buildConfiguration : OpenConfiguratorProjectUtils.defaultBuildConfigurationIdList) {
                            boolean buildConfigurationAvailable = false;
                            for (TKeyValuePair setting : settingsList) {
                                if (setting.getName()
                                        .equalsIgnoreCase(buildConfiguration)) {
                                    buildConfigurationAvailable = true;
                                    break;
                                }
                            }

                            if (!buildConfigurationAvailable) {
                                TKeyValuePair buildConfig = new TKeyValuePair();
                                buildConfig.setName(buildConfiguration.trim());
                                buildConfig.setValue("");
                                buildConfig.setEnabled(true);

                                settingsList.add(buildConfig);
                            }

                        }
                    }
                }

                TAutoGenerationSettings customAgSettings = new TAutoGenerationSettings();
                customAgSettings.setId(
                        OpenConfiguratorProjectUtils.AUTO_GENERATION_SETTINGS_CUSTOM_ID);
                autoGenerationSettingsList.add(customAgSettings);

            } else {
                // TODO create a new Project configuration. This might not
                // be
                // the case. Since it is schema validated.
            }

            TNetworkConfiguration net = project.getNetworkConfiguration();
            if (net != null) {
                net.setAsyncMTU(null);
                net.setCycleTime(null);
                net.setMultiplexedCycleLength(null);
                net.setPrescaler(null);
            }
            if (net != null) {
                if (net.getNodeCollection() != null) {
                    TNodeCollection nodeColl = net.getNodeCollection();
                    if (nodeColl != null) {
                        TMN mn = nodeColl.getMN();
                        if (mn != null) {
                            mn.setASndMaxNumber(null);
                            mn.setAsyncSlotTimeout(null);
                        }
                    }
                }
            }
            OpenConfiguratorProjectUtils.updateGeneratorInformation(project);
            return true;
        } else {
            return false;
        }

    }

    /**
     * Write the values modified into XDD/XDC file.
     *
     * @param document The Document instance.
     * @param xmlFile The instance of XDD/XDC file.
     * @throws IOException Errors with XDC file modifications.
     */

    public static void writeToXddXmlDocument(org.jdom2.Document document,
            final File xmlFile) throws IOException {
        String name = System
                .getProperty(OpenConfiguratorProjectUtils.SYSTEM_USER_NAME_ID);
        XddJdomOperation.updateFileModifiedTime(document, getCurrentTime());
        XddJdomOperation.updateFileModifiedDate(document, getCurrentDate());
        XddJdomOperation.updateFileModifiedBy(document, name);
        JDomUtil.writeToProjectXmlDocument(document, xmlFile);
    }
}

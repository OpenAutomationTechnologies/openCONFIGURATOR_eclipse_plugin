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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.io.FilenameUtils;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.epsg.openconfigurator.console.OpenConfiguratorMessageConsole;
import org.epsg.openconfigurator.lib.wrapper.NodeAssignment;
import org.epsg.openconfigurator.lib.wrapper.OpenConfiguratorCore;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.lib.wrapper.StringCollection;
import org.epsg.openconfigurator.model.IPowerlinkProjectSupport;
import org.epsg.openconfigurator.model.Node;
import org.epsg.openconfigurator.model.PdoChannel;
import org.epsg.openconfigurator.model.PowerlinkObject;
import org.epsg.openconfigurator.model.PowerlinkSubobject;
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

public final class OpenConfiguratorProjectUtils {

    public static final String PATH_SETTINGS_DEFAULT_PATH_ID = "defaultOutputPath"; ////$NON-NLS-1$
    public static final String PATH_SETTINGS_DEFAUTL_PATH_VALUE = "output"; ////$NON-NLS-1$

    public static final String AUTO_GENERATION_SETTINGS_ALL_ID = "all"; ////$NON-NLS-1$
    public static final String AUTO_GENERATION_SETTINGS_NONE_ID = "none"; ////$NON-NLS-1$
    public static final String AUTO_GENERATION_SETTINGS_CUSTOM_ID = "custom"; ////$NON-NLS-1$

    public static final String GENERATOR_VENDOR = "Kalycito Infotech Private Limited & Bernecker + Rainer Industrie Elektronik Ges.m.b.H."; ////$NON-NLS-1$
    public static final String GENERATOR_TOOL_NAME = "Ethernet POWERLINK openCONFIGURATOR"; ////$NON-NLS-1$
    public static final String GENERATOR_TOOL_VERSION = "2.0.0"; ////$NON-NLS-1$
    public static final String SYSTEM_USER_NAME_ID = "user.name";

    private static ArrayList<String> defaultBuildConfigurationIdList;

    private static final String UPGRADE_MESSAGE = "Upgrading openCONFIGURATOR project version {0} to version {1}.";

    static {
        // Fetch the list of default buildConfigurationIDs from the
        // openconfigurator-core library.
        OpenConfiguratorProjectUtils.defaultBuildConfigurationIdList = new ArrayList<String>();
        StringCollection support = new StringCollection();
        Result libApiRes = OpenConfiguratorCore.GetInstance()
                .GetSupportedSettingIds(support);
        if (!libApiRes.IsSuccessful()) {
            // TODO: Display a dialog to report it to the user
            System.err.println("GetSupportedSettingIds failed with error: "
                    + OpenConfiguratorLibraryUtils.getErrorMessage(libApiRes));
        }

        for (int i = 0; i < support.size(); i++) {
            OpenConfiguratorProjectUtils.defaultBuildConfigurationIdList
                    .add(support.get(i));
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

        Node mnNode = nodeCollection.get(new Short((short) 240));

        // Remove from the viewer node collection.
        Object nodeObjectModel = node.getNodeModel();
        if (nodeObjectModel instanceof TRMN) {
            TRMN rMN = (TRMN) nodeObjectModel;
            nodeCollection.remove(rMN.getNodeID());
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
                        + node.getNodeId() + " modelType:" + nodeObjectModel);
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

        JDomUtil.writeToXmlDocument(document, xmlFile);

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

        return retVal;
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
        JDomUtil.writeToXmlDocument(document, xmlFile);
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
        System.out.println("Import path: " + nodeImportFile.toString());
        java.nio.file.Path projectRootPath = newNode.getProject().getLocation()
                .toFile().toPath();
        String targetImportPath = new String(projectRootPath.toString()
                + IPath.SEPARATOR + IPowerlinkProjectSupport.DEVICE_IMPORT_DIR
                + IPath.SEPARATOR + nodeImportFile.getFileName().toString());

        // Copy the Node configuration to deviceImport dir

        nodeImportFile = java.nio.file.Files.copy(
                new java.io.File(nodeImportFile.toString()).toPath(),
                new java.io.File(targetImportPath).toPath(),
                java.nio.file.StandardCopyOption.REPLACE_EXISTING,
                java.nio.file.StandardCopyOption.COPY_ATTRIBUTES,
                java.nio.file.LinkOption.NOFOLLOW_LINKS);

        // Rename the XDD to XDC and copy the deviceImport MN XDD to
        // deviceConfiguration dir
        String extensionXdd = FilenameUtils
                .removeExtension(nodeImportFile.getFileName().toString());

        // Append node ID and the 'XDC' extension to the configuration file.
        extensionXdd += "_" + newNode.getNodeId()
                + IPowerlinkProjectSupport.XDC_EXTENSION;

        String targetConfigurationPath = new String(
                projectRootPath.toString() + IPath.SEPARATOR
                        + IPowerlinkProjectSupport.DEVICE_CONFIGURATION_DIR
                        + IPath.SEPARATOR + extensionXdd);

        java.nio.file.Files.copy(nodeImportFile,
                new java.io.File(targetConfigurationPath).toPath(),
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

        File xmlFile = new File(node.getAbsolutePathToXdc());

        org.jdom2.Document document = JDomUtil.getXmlDocument(xmlFile);

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
        XddJdomOperation.deleteActualValue(document);

        // Prepare the Java based object collection.
        java.util.LinkedHashMap<java.util.Map.Entry<Long, Integer>, String> objectJCollection = new LinkedHashMap<Map.Entry<Long, Integer>, String>();
        Result res = OpenConfiguratorLibraryUtils
                .getObjectsWithActualValue(node, objectJCollection);
        if (!res.IsSuccessful()) {
            OpenConfiguratorMessageConsole.getInstance().printErrorMessage(
                    OpenConfiguratorLibraryUtils.getErrorMessage(res));
            return res;
        }

        node.writeObjectActualValues(objectJCollection, document);

        JDomUtil.writeToXmlDocument(document, xmlFile);

        return res;
    }

    /**
     * Saves the actual value changes in the library to the XDD/XDC.
     *
     * @param nodeCollection The node list.
     * @param monitor Progress monitor instance.
     * @return The result from the library.
     * @throws Error with XDC/XDD file modification.
     * @throws JDOMException Error with time modifications
     */
    public static Result persistNodes(final Map<Short, Node> nodeCollection,
            IProgressMonitor monitor) throws JDOMException, IOException {
        Result res = new Result();

        for (Map.Entry<Short, Node> entry : nodeCollection.entrySet()) {

            Node node = entry.getValue();
            if (node == null) {
                System.err.println("Node" + entry.getKey() + " is null");
                continue;
            }
            if (node.hasXdd()) {
                OpenConfiguratorMessageConsole.getInstance().printErrorMessage(
                        "The Node " + "'" + node.getNodeIDWithName() + "'"
                                + " has invalid XDD file.");
                continue;
            }
            if (monitor.isCanceled()) {
                throw new OperationCanceledException(
                        "Operation cancelled by user. Not all data is saved to the XDC.");
            }

            System.out.println(
                    entry.getKey() + "----" + node.getAbsolutePathToXdc());

            monitor.subTask("Updating node:" + node.getNodeIDWithName() + " ->"
                    + node.getPathToXDC());

            res = persistNodeData(node);
            if (!res.IsSuccessful()) {
                // Continue operation for other nodes
                continue;
            }

            updateNodeAssignmentValues(node);
            monitor.worked(1);
        }
        return res;
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

        JDomUtil.writeToXmlDocument(document, xmlFile);

        try {
            node.getProject().refreshLocal(IResource.DEPTH_INFINITE,
                    new NullProgressMonitor());
        } catch (CoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Persists the node Assignment values in the model and the project XML
     * file.
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

        JDomUtil.writeToXmlDocument(document, xmlFile);
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

        JDomUtil.writeToXmlDocument(document, xmlFile);

        try {
            node.getProject().refreshLocal(IResource.DEPTH_INFINITE,
                    new NullProgressMonitor());
        } catch (CoreException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Updates the node configuration file with the new node id.
     *
     * @param node The node instance.
     *
     * @param newNodeId The new node id to be set.
     *
     * @throws IOException Errors with XDC file modifications.
     */
    public static void updateNodeConfigurationPath(Node node, String newNodeId)
            throws IOException {

        java.nio.file.Path nodeImportFile = new File(node.getPathToXDC())
                .toPath();

        java.nio.file.Path projectRootPath = node.getProject().getLocation()
                .toFile().toPath();

        String xddNameWithExtension = nodeImportFile.getFileName().toString();
        String oldNodeSuffix = "_" + node.getNodeId()
                + IPowerlinkProjectSupport.XDC_EXTENSION;

        String xddFileNameWithNoSuffix = xddNameWithExtension.substring(0,
                xddNameWithExtension.length() - oldNodeSuffix.length());

        // Append node ID and the 'XDC' extension to the configuration file.
        String xddFileNameWithSuffix = xddFileNameWithNoSuffix + "_" + newNodeId
                + IPowerlinkProjectSupport.XDC_EXTENSION;

        String targetConfigurationPath = new String(
                projectRootPath.toString() + IPath.SEPARATOR
                        + IPowerlinkProjectSupport.DEVICE_CONFIGURATION_DIR
                        + IPath.SEPARATOR + xddFileNameWithSuffix);

        java.nio.file.Path pathRelative = projectRootPath
                .relativize(Paths.get(targetConfigurationPath));
        File unModifiedfile = new File(projectRootPath + "/" + nodeImportFile);
        File updatedfile = new File(projectRootPath + "/" + pathRelative);

        Files.move(unModifiedfile.toPath(), updatedfile.toPath(),
                StandardCopyOption.REPLACE_EXISTING);

        String relativePath = pathRelative.toString();
        relativePath = relativePath.replace('\\', '/');

        // Set the relative path to the CN object
        node.setPathToXDC(relativePath);

    }

    public static void updateObjectAttributeActualValue(final Node node,
            final PowerlinkObject object, String actualValue)
                    throws IOException, JDOMException {
        File xmlFile = new File(node.getAbsolutePathToXdc());
        org.jdom2.Document document = JDomUtil.getXmlDocument(xmlFile);

        XddJdomOperation.updateActualValue(document, object, actualValue);

        JDomUtil.writeToXmlDocument(document, xmlFile);
    }

    public static void updateObjectAttributeActualValue(final Node node,
            final PowerlinkSubobject object, String actualValue)
                    throws IOException, JDOMException {
        File xmlFile = new File(node.getAbsolutePathToXdc());
        org.jdom2.Document document = JDomUtil.getXmlDocument(xmlFile);

        XddJdomOperation.updateActualValue(document, object, actualValue);

        JDomUtil.writeToXmlDocument(document, xmlFile);
    }

    /**
     * Updates the actual values available in the mapping param of the given PDO
     * channel.
     *
     * @param pdoChannel The channel to be updated.
     * @return The result from the library.
     * @throws JDOMException
     * @throws IOException
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
        java.util.LinkedHashMap<java.util.Map.Entry<Long, Integer>, String> objectJCollection = new LinkedHashMap<Map.Entry<Long, Integer>, String>();
        Result res = OpenConfiguratorLibraryUtils
                .getChannelObjectsWithActualValue(pdoChannel,
                        objectJCollection);
        if (!res.IsSuccessful()) {
            OpenConfiguratorMessageConsole.getInstance().printErrorMessage(
                    OpenConfiguratorLibraryUtils.getErrorMessage(res));
            return res;
        }

        node.writeObjectActualValues(objectJCollection, document);

        JDomUtil.writeToXmlDocument(document, xdcFile);

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
     * Upgrade openCONFIGURATOR project to conform with 2.0 schema. Add default
     * fields for AutoGeneration Settings.
     *
     * @param project OpenCONFIGURATOR project
     */
    public static boolean upgradeOpenConfiguratorProject(
            OpenCONFIGURATORProject project) {
        TGenerator generator = project.getGenerator();
        if ((project == null) || (generator == null)
                || (generator.getToolVersion() == null)) {
            return false;
        }

        String toolVersionAvailable = generator.getToolVersion();
        if (toolVersionAvailable.equalsIgnoreCase("2.0.0-pre-release")) {
            OpenConfiguratorMessageConsole.getInstance()
                    .printInfoMessage(MessageFormat.format(UPGRADE_MESSAGE,
                            toolVersionAvailable, GENERATOR_TOOL_VERSION));

            generator.setToolName(GENERATOR_TOOL_NAME);
            generator.setVendor(GENERATOR_VENDOR);
            OpenConfiguratorProjectUtils.updateGeneratorInformation(project);
            return true;
        } else if ((toolVersionAvailable.equalsIgnoreCase("1.4.1")
                || toolVersionAvailable.equalsIgnoreCase("1.4.0"))) {
            OpenConfiguratorMessageConsole.getInstance()
                    .printInfoMessage(MessageFormat.format(UPGRADE_MESSAGE,
                            toolVersionAvailable, GENERATOR_TOOL_VERSION));

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
                // TODO create a new Project configuration. This might not be
                // the case. Since it is schema validated.
            }

            TNetworkConfiguration net = project.getNetworkConfiguration();
            if (net != null) {
                net.setAsyncMTU(null);
                net.setCycleTime(null);
                net.setMultiplexedCycleLength(null);
                net.setPrescaler(null);
            }

            TNodeCollection nodeColl = net.getNodeCollection();
            if (nodeColl != null) {
                TMN mn = nodeColl.getMN();
                if (mn != null) {
                    mn.setASndMaxNumber(null);
                    mn.setAsyncSlotTimeout(null);
                }
            }
            OpenConfiguratorProjectUtils.updateGeneratorInformation(project);
        } else {
            return false;
        }

        return true;
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

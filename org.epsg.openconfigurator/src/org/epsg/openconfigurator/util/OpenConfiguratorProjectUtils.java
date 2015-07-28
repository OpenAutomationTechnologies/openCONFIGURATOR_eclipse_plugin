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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FilenameUtils;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.epsg.openconfigurator.lib.wrapper.OpenConfiguratorCore;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.lib.wrapper.StringCollection;
import org.epsg.openconfigurator.model.IPowerlinkProjectSupport;
import org.epsg.openconfigurator.model.Node;
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
import org.epsg.openconfigurator.xmlbinding.xdd.ISO15745ProfileContainer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public final class OpenConfiguratorProjectUtils {

    public static final String PATH_SETTINGS_DEFAULT_PATH_ID = "defaultOutputPath"; ////$NON-NLS-1$
    public static final String PATH_SETTINGS_DEFAUTL_PATH_VALUE = "output"; ////$NON-NLS-1$

    public static final String AUTO_GENERATION_SETTINGS_ALL_ID = "all"; ////$NON-NLS-1$
    public static final String AUTO_GENERATION_SETTINGS_NONE_ID = "none"; ////$NON-NLS-1$
    public static final String AUTO_GENERATION_SETTINGS_CUSTOM_ID = "custom"; ////$NON-NLS-1$

    public static final String GENERATOR_VENDOR = "Kalycito Infotech Private Limited &amp; Bernecker + Rainer Industrie Elektronik Ges.m.b.H."; ////$NON-NLS-1$
    public static final String GENERATOR_TOOL_NAME = "openCONFIGURATOR"; ////$NON-NLS-1$
    public static final String GENERATOR_TOOL_VERSION = "1.5.0"; ////$NON-NLS-1$
    public static final String SYSTEM_USER_NAME_ID = "user.name";

    private static ArrayList<String> defaultBuildConfigurationIdList;
    private static final String INVALID_XDC_CONTENTS_ERROR = "Invalid XDD/XDC exists in the project. Node configuration specified for the Node: {0} is invalid. XDC Path: {1}";
    private static final String XDC_FILE_NOT_FOUND_ERROR = "XDD/XDC file for the node: {0} does not exists in the project. XDC Path: {1}";

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
                    + libApiRes.GetErrorMessage());
        }

        for (int i = 0; i < support.size(); i++) {
            OpenConfiguratorProjectUtils.defaultBuildConfigurationIdList
                    .add(support.get(i));
        }
    }

    /**
     * Add node into the node collection and the node list.
     *
     * @param nodeList The list of available nodes.
     * @param nodeCollection The node collection instance.
     * @param node New node.
     * @return <code>True</code> if successful and <code>False</code> otherwise.
     * @throws IOException
     */
    public static boolean addNode(Map<Short, Node> nodeList,
            TNodeCollection nodeCollection, Node node) throws IOException {

        importNodeConfigurationFile(node);

        Object nodeModel = node.getNodeModel();
        if (nodeModel instanceof TCN) {
            TCN cn = (TCN) nodeModel;
            nodeCollection.getCN().add(cn);
        } else if (nodeModel instanceof TRMN) {
            TRMN rmn = (TRMN) nodeModel;
            nodeCollection.getRMN().add(rmn);
        } else {
            // Invalid node
            return false;
        }

        nodeList.put(new Short(node.getNodeId()), node);

        return true;
    }

    /**
     * Removes the node from the node collection provided and from the project
     * XML file.
     *
     * @param nodeCollection The list of available node.
     * @param node The node to be removed.
     * @param monitor Monitor instance to report the progress.
     * @return <code>True</code> if successful and <code>False</code> otherwise.
     */
    public static boolean deleteNode(Map<Short, Node> nodeCollection, Node node,
            IProgressMonitor monitor) {
        // FIXME: Proper return type and handle all returns.
        boolean retVal = false;
        if (node == null) {
            return retVal;
        }

        IProject currentProject = node.getProject();
        if (currentProject == null) {
            System.err.println("Current project null. returned null");
            return retVal;
        }

        File localFile = new File(node.getAbsolutePathToXdc());
        if (!localFile.exists()) {
            System.err.println("XDC file does not exists");
            return retVal;
        } else {
            retVal = localFile.delete();
            if (retVal) {
                try {

                    currentProject.refreshLocal(IResource.DEPTH_INFINITE,
                            monitor);
                } catch (CoreException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    return false;
                }
            } else {
                System.err.println("File delete unsuccessful. File:"
                        + node.getAbsolutePathToXdc());
            }
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

        return retVal;
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
     * @throws IOException
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

    /**
     * Import the nodes available in the project XML file. Add the created node
     * into the nodeCollection.
     *
     * @param projectFile The project file instance.
     * @param networkCfg The network configuration instance from the project XML
     *            file.
     * @param nodeCollection The node collection list used for the project.
     * @param monitor The monitor instance to display the current status.
     * @return Status of the import nodes.
     */
    public static Status importNodes(IFile projectFile,
            TNetworkConfiguration networkCfg, Map<Short, Node> nodeCollection,
            IProgressMonitor monitor) {
        Node processingNode = new Node();

        try {
            // MN section
            {
                TMN mnNode = networkCfg.getNodeCollection().getMN();

                monitor.subTask("Import MN node XDC:" + mnNode.getName() + "("
                        + mnNode.getNodeID() + ")");

                File mnXddFile = new File(projectFile.getProject().getLocation()
                        + File.separator + mnNode.getPathToXDC());
                System.out.println(
                        "MN XDD file path:" + mnXddFile.getAbsolutePath());
                processingNode = new Node(projectFile, networkCfg, null);

                ISO15745ProfileContainer xdd = XddMarshaller
                        .unmarshallXDDFile(mnXddFile);
                // add TNetworkManagement Not TMN
                Node newNode = new Node(projectFile, networkCfg, xdd);
                processingNode = newNode;

                Result res = OpenConfiguratorLibraryUtils.addNode(
                        newNode.getNetworkId(), newNode.getNodeId(), newNode);
                if (res.IsSuccessful()) {
                    nodeCollection.put(newNode.getNodeId(), newNode);
                } else {
                    return new Status(IStatus.ERROR,
                            org.epsg.openconfigurator.Activator.PLUGIN_ID,
                            OpenConfiguratorLibraryUtils.getErrorMessage(res),
                            null);
                }
            }

            // Import the CN nodes
            for (TCN cnNode : networkCfg.getNodeCollection().getCN()) {

                monitor.subTask("Import CN node XDC:" + cnNode.getName() + "("
                        + cnNode.getNodeID() + ")");

                File cnXddFile = new File(projectFile.getProject().getLocation()
                        + File.separator + cnNode.getPathToXDC());
                System.out.println(
                        "CN XDD file path:" + cnXddFile.getAbsolutePath());

                processingNode = new Node(projectFile, cnNode, null);

                ISO15745ProfileContainer xdd = XddMarshaller
                        .unmarshallXDDFile(cnXddFile);
                Node newNode = new Node(projectFile, cnNode, xdd);
                processingNode = newNode;

                Result res = OpenConfiguratorLibraryUtils.addNode(
                        newNode.getNetworkId(), new Short(newNode.getNodeId()),
                        newNode);
                if (res.IsSuccessful()) {
                    nodeCollection.put(new Short(newNode.getNodeId()), newNode);
                } else {
                    return new Status(IStatus.ERROR,
                            org.epsg.openconfigurator.Activator.PLUGIN_ID,
                            OpenConfiguratorLibraryUtils.getErrorMessage(res),
                            null);
                }
                monitor.worked(1);
            }

            // Import the RMN nodes
            for (TRMN rmnNode : networkCfg.getNodeCollection().getRMN()) {

                monitor.subTask("Import RMN node XDC:" + rmnNode.getName() + "("
                        + rmnNode.getNodeID() + ")");

                File rmnXddFile = new File(
                        projectFile.getProject().getLocation() + File.separator
                                + rmnNode.getPathToXDC());
                System.out.println(
                        "RMN XDD file path:" + rmnXddFile.getAbsolutePath());
                processingNode = new Node(projectFile, rmnNode, null);
                ISO15745ProfileContainer xdd = XddMarshaller
                        .unmarshallXDDFile(rmnXddFile);

                Node newNode = new Node(projectFile, rmnNode, xdd);
                processingNode = newNode;

                Result res = OpenConfiguratorLibraryUtils.addNode(
                        newNode.getNetworkId(), newNode.getNodeId(), newNode);
                if (res.IsSuccessful()) {
                    nodeCollection.put(new Short(newNode.getNodeId()), newNode);
                } else {
                    return new Status(IStatus.ERROR,
                            org.epsg.openconfigurator.Activator.PLUGIN_ID,
                            OpenConfiguratorLibraryUtils.getErrorMessage(res),
                            null);
                }
                monitor.worked(1);
            }

        } catch (JAXBException | SAXException | ParserConfigurationException
                | UnsupportedEncodingException e) {
            e.printStackTrace();

            String xdcPath = processingNode.getPathToXDC();

            String errorMessage = MessageFormat.format(
                    INVALID_XDC_CONTENTS_ERROR,
                    processingNode.getNodeIDWithName(), xdcPath);
            return new Status(IStatus.ERROR,
                    org.epsg.openconfigurator.Activator.PLUGIN_ID, errorMessage,
                    e);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
            String xdcPath = processingNode.getPathToXDC();

            String errorMessage = MessageFormat.format(XDC_FILE_NOT_FOUND_ERROR,
                    processingNode.getNodeIDWithName(), xdcPath);

            return new Status(IStatus.ERROR,
                    org.epsg.openconfigurator.Activator.PLUGIN_ID, errorMessage,
                    e1);
        }

        monitor.done();
        return new Status(IStatus.OK,
                org.epsg.openconfigurator.Activator.PLUGIN_ID, "OK", null);
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
                .getProperty(OpenConfiguratorProjectUtils.SYSTEM_USER_NAME_ID));
        tGenerator
                .setToolName(OpenConfiguratorProjectUtils.GENERATOR_TOOL_NAME);
        tGenerator.setToolVersion(
                OpenConfiguratorProjectUtils.GENERATOR_TOOL_VERSION);
        tGenerator.setCreatedOn(
                OpenConfiguratorProjectUtils.getXMLGregorianCalendarNow());
        tGenerator.setModifiedOn(
                OpenConfiguratorProjectUtils.getXMLGregorianCalendarNow());
        tGenerator.setModifiedBy(System
                .getProperty(OpenConfiguratorProjectUtils.SYSTEM_USER_NAME_ID));
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
            buildConfig.setName(buildConfiguration);
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
     * Update the Generator informations to the current values.
     *
     * @param project
     */
    public static void updateGeneratorInformation(
            OpenCONFIGURATORProject project) {
        project.getGenerator().setToolVersion(
                OpenConfiguratorProjectUtils.GENERATOR_TOOL_VERSION);
        project.getGenerator().setModifiedOn(
                OpenConfiguratorProjectUtils.getXMLGregorianCalendarNow());
        project.getGenerator().setModifiedBy(System
                .getProperty(OpenConfiguratorProjectUtils.SYSTEM_USER_NAME_ID));
    }

    /**
     * Update the network configuration attributes in the project XML file.
     *
     * @param node The node
     * @param attributeName The attribute tag name.
     * @param attributeValue The value to be applied.
     */
    public static void updateNetworkAttributeValue(final Node node,
            final String attributeName, final String attributeValue) {
        try {

            String projectXmlLocation = node.getProjectXml().getLocation()
                    .toString();
            File xmlFile = new File(projectXmlLocation);

            Reader reader = new InputStreamReader(new FileInputStream(xmlFile),
                    IPowerlinkProjectSupport.UTF8_ENCODING);
            InputSource input = new InputSource(reader);
            input.setSystemId(xmlFile.toURI().toString());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Update the node attributes in the project XML file.
     *
     * @param node The node to apply the attributes.
     * @param attributeName The attribute tag name.
     * @param attributeValue The value to be applied.
     */
    public static void updateNodeAttributeValue(final Node node,
            final String attributeName, final String attributeValue) {
        try {

            String projectXmlLocation = node.getProjectXml().getLocation()
                    .toString();
            File xmlFile = new File(projectXmlLocation);

            Reader reader = new InputStreamReader(new FileInputStream(xmlFile),
                    IPowerlinkProjectSupport.UTF8_ENCODING);
            InputSource input = new InputSource(reader);
            input.setSystemId(xmlFile.toURI().toString());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void updateObjectAttributeValue(final Node node,
            final String objectId, final boolean isSubObject,
            final String subObjectId, final String actualValue) {
        try {

            File xmlFile = new File(node.getAbsolutePathToXdc());

            Reader reader = new InputStreamReader(new FileInputStream(xmlFile),
                    IPowerlinkProjectSupport.UTF8_ENCODING);
            InputSource input = new InputSource(reader);
            input.setSystemId(xmlFile.toURI().toString());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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

        if (!(project.getGenerator().getToolVersion().equalsIgnoreCase("1.4.1")
                || project.getGenerator().getToolVersion()
                        .equalsIgnoreCase("1.4.0"))) {
            return false;
        }

        System.out.println(
                "Upgrading openCONFIGURATOR project to current version."
                        + project.getGenerator().getToolVersion());

        TProjectConfiguration projectConfiguration = project
                .getProjectConfiguration();
        if (projectConfiguration != null) {
            java.util.List<TAutoGenerationSettings> autoGenerationSettingsList = projectConfiguration
                    .getAutoGenerationSettings();
            for (TAutoGenerationSettings agSettings : autoGenerationSettingsList) {
                if (agSettings.getId().equalsIgnoreCase(
                        OpenConfiguratorProjectUtils.AUTO_GENERATION_SETTINGS_ALL_ID)) {
                    List<TKeyValuePair> settingsList = agSettings.getSetting();

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
                            buildConfig.setName(buildConfiguration);
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
            // TODO create a new Project configuration. This might not be the
            // case. Since it is schema
            // validated.
        }

        OpenConfiguratorProjectUtils.updateGeneratorInformation(project);
        return true;
    }
}

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

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.epsg.openconfigurator.lib.wrapper.OpenConfiguratorCore;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.lib.wrapper.StringCollection;
import org.epsg.openconfigurator.xmlbinding.projectfile.OpenCONFIGURATORProject;
import org.epsg.openconfigurator.xmlbinding.projectfile.TAutoGenerationSettings;
import org.epsg.openconfigurator.xmlbinding.projectfile.TGenerator;
import org.epsg.openconfigurator.xmlbinding.projectfile.TKeyValuePair;
import org.epsg.openconfigurator.xmlbinding.projectfile.TMN;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNetworkConfiguration;
import org.epsg.openconfigurator.xmlbinding.projectfile.TNodeCollection;
import org.epsg.openconfigurator.xmlbinding.projectfile.TPath;
import org.epsg.openconfigurator.xmlbinding.projectfile.TProjectConfiguration;

public final class OpenCONFIGURATORProjectUtils {

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

  static {
    // Fetch the list of default buildConfigurationIDs from the openconfigurator-core library.
    defaultBuildConfigurationIdList = new ArrayList<String>();
    StringCollection support = new StringCollection();
    Result libApiRes = OpenConfiguratorCore.GetInstance().GetSupportedSettingIds(support);
    if (!libApiRes.IsSuccessful()) {
      // TODO: Display a dialog to report it to the user
      System.err
          .println("GetSupportedSettingIds failed with error: " + libApiRes.GetErrorMessage());
    }

    for (int i = 0; i < support.size(); i++) {
      defaultBuildConfigurationIdList.add(support.get(i));
    }
  }

  public static boolean isPathIdAlreadyPresent(
      TProjectConfiguration.PathSettings pathSettingsModel, final String id) {
    List<TPath> pathList = pathSettingsModel.getPath();
    for (TPath path : pathList) {
      if (path.getId().equalsIgnoreCase(id)) {
        return true;
      }
    }
    return false;
  }

  /**
   * @brief Create a Default openCONFIGURATOR project instance.
   *
   * @param mn MN node instance that has to be added by default.
   * @return new OpenCONFIGURATORProject instance / null if any errors
   */
  public static OpenCONFIGURATORProject newDefaultOpenCONFIGURATORProject(final TMN mn) {

    if (mn == null)
      return null;

    OpenCONFIGURATORProject openConfiguratorProject = new OpenCONFIGURATORProject();
    TGenerator tGenerator = new TGenerator();
    tGenerator.setVendor(GENERATOR_VENDOR);
    tGenerator.setCreatedBy(System.getProperty(SYSTEM_USER_NAME_ID));
    tGenerator.setToolName(GENERATOR_TOOL_NAME);
    tGenerator.setToolVersion(GENERATOR_TOOL_VERSION);
    tGenerator.setCreatedOn(getXMLGregorianCalendarNow());
    tGenerator.setModifiedOn(getXMLGregorianCalendarNow());
    tGenerator.setModifiedBy(System.getProperty(SYSTEM_USER_NAME_ID));
    openConfiguratorProject.setGenerator(tGenerator);

    // Add default project configurations
    TProjectConfiguration tProjectConfiguration = new TProjectConfiguration();
    tProjectConfiguration.setActiveAutoGenerationSetting(AUTO_GENERATION_SETTINGS_ALL_ID);

    // Add default output path
    TProjectConfiguration.PathSettings pathSettings = new TProjectConfiguration.PathSettings();
    java.util.List<TPath> pathList = pathSettings.getPath();
    TPath path = new TPath();
    path.setId(PATH_SETTINGS_DEFAULT_PATH_ID);
    path.setPath(PATH_SETTINGS_DEFAUTL_PATH_VALUE);
    pathList.add(path);
    tProjectConfiguration.setPathSettings(pathSettings);

    // Auto generation settings
    java.util.List<TAutoGenerationSettings> autoGenerationSettingsList = tProjectConfiguration
        .getAutoGenerationSettings();
    TAutoGenerationSettings tAutoGenerationSettings_all = new TAutoGenerationSettings();
    tAutoGenerationSettings_all.setId(AUTO_GENERATION_SETTINGS_ALL_ID);

    java.util.List<TKeyValuePair> allSettingsList = tAutoGenerationSettings_all.getSetting();
    for (String buildConfiguration : defaultBuildConfigurationIdList) {
      TKeyValuePair buildConfig = new TKeyValuePair();
      buildConfig.setName(buildConfiguration);
      buildConfig.setValue("");
      buildConfig.setEnabled(true);

      allSettingsList.add(buildConfig);
    }

    autoGenerationSettingsList.add(tAutoGenerationSettings_all);

    TAutoGenerationSettings tAutoGenerationSettings_none = new TAutoGenerationSettings();
    tAutoGenerationSettings_none.setId(AUTO_GENERATION_SETTINGS_NONE_ID);
    autoGenerationSettingsList.add(tAutoGenerationSettings_none);

    TAutoGenerationSettings tAutoGenerationSettings_custom = new TAutoGenerationSettings();
    tAutoGenerationSettings_custom.setId(AUTO_GENERATION_SETTINGS_CUSTOM_ID);
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
   * @brief Fix problems in the openCONFIGURATOR project AutoGeneration Settings type.
   *
   * @param[in,out] project
   */
  public static boolean fixOpenCONFIGURATORProject(OpenCONFIGURATORProject project) {
    if (project == null || project.getGenerator() == null
        || project.getGenerator().getToolVersion() == null) {
      return false;
    }

    if (!(project.getGenerator().getToolVersion().equalsIgnoreCase("1.4.1") || project
        .getGenerator().getToolVersion().equalsIgnoreCase("1.4.0"))) {
      System.out.println("Version check fails" + project.getGenerator().getToolVersion());
      return false;
    }

    TProjectConfiguration projectConfiguration = project.getProjectConfiguration();
    if (projectConfiguration != null) {
      java.util.List<TAutoGenerationSettings> autoGenerationSettingsList = projectConfiguration
          .getAutoGenerationSettings();
      for (TAutoGenerationSettings agSettings : autoGenerationSettingsList) {
        if (agSettings.getId().equalsIgnoreCase(AUTO_GENERATION_SETTINGS_ALL_ID)) {
          List<TKeyValuePair> settingsList = agSettings.getSetting();

          for (String buildConfiguration : defaultBuildConfigurationIdList) {
            boolean buildConfigurationAvailable = false;
            for (TKeyValuePair setting : settingsList) {
              if (setting.getName().equalsIgnoreCase(buildConfiguration)) {
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
      customAgSettings.setId(AUTO_GENERATION_SETTINGS_CUSTOM_ID);
      autoGenerationSettingsList.add(customAgSettings);

    } else {
      // TODO create a new Project configuration. This might not be the case. Since it is schema
      // validated.
    }

    OpenCONFIGURATORProjectUtils.updateGeneratorInformation(project);
    return true;
  }

  /**
   * @brief Update the Generator informations to the current values.
   *
   * @param[in,out] project
   */
  public static void updateGeneratorInformation(OpenCONFIGURATORProject project) {
    project.getGenerator().setToolVersion(GENERATOR_TOOL_VERSION);
    project.getGenerator().setModifiedOn(getXMLGregorianCalendarNow());
    project.getGenerator().setModifiedBy(System.getProperty(SYSTEM_USER_NAME_ID));
  }

  /**
   * Returns the current Gregorian Calendar time. If any exception returns null;
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
   * Finds the settingId in the list of agSettings and returns the TKeyValuePair instance
   *
   * @param agSettings autoGenerationSettings instance
   * @param settingId The setting to be searched in the list
   * @return TKeyValuePair buildConfigurationSetting, null otherwise
   */
  public static TKeyValuePair getSetting(TAutoGenerationSettings agSettings, final String settingId) {

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
  private static TPath getTPath(TProjectConfiguration.PathSettings pathSettings, final String id) {
    List<TPath> pathList = pathSettings.getPath();
    for (TPath path : pathList) {
      if (path.getId().equalsIgnoreCase(id)) {
        return path;
      }
    }

    return null;
  }
}

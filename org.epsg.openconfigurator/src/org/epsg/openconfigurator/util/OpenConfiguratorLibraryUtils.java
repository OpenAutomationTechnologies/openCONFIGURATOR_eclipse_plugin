/*******************************************************************************
 * @file   OpenCONFIGURATORLibraryUtils.java
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

import java.io.IOException;

import org.apache.commons.lang.SystemUtils;
import org.epsg.openconfigurator.lib.wrapper.OpenConfiguratorCore;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.resources.IOpenConfiguratorResource;
import org.epsg.openconfigurator.xmlbinding.projectfile.OpenCONFIGURATORProject;
import org.epsg.openconfigurator.xmlbinding.projectfile.TAutoGenerationSettings;
import org.epsg.openconfigurator.xmlbinding.projectfile.TKeyValuePair;
import org.epsg.openconfigurator.xmlbinding.projectfile.TProjectConfiguration;

/**
 * An utility class to group all the methods that are used to communicate with
 * the library.
 *
 * @author Ramakrishnan P
 *
 */
public class OpenConfiguratorLibraryUtils {

    /**
     * Add the configurations in openCONFIGURTOR project into the Network
     * available in the library.
     *
     * @param projectModel openCONFIGURATOR project instance.
     * @param networkId The Network ID.
     *
     * @return Result from the openCONFIGURATOR library.
     */
    public static Result addOpenCONFIGURATORProject(
            final OpenCONFIGURATORProject projectModel,
            final String networkId) {

        Result libApiRes = OpenConfiguratorLibraryUtils.addProjectConfiguration(
                projectModel.getProjectConfiguration(), networkId);

        return libApiRes;
    }

    /**
     * Add the project configuration details from {@link TProjectConfiguration}
     * into the openCONFIGUATOR core library.
     *
     * @param projectConfiguration TProjectConfiguration instance.
     * @param networkId The Network ID.
     *
     * @return Result from the openCONFIGURATOR library.
     */
    private static Result addProjectConfiguration(
            final TProjectConfiguration projectConfiguration,
            final String networkId) {

        // AutoGenerationSettings set into the library
        java.util.List<TAutoGenerationSettings> agSettingsList = projectConfiguration
                .getAutoGenerationSettings();
        Result libApiRes = new Result();
        for (TAutoGenerationSettings agSetting : agSettingsList) {

            libApiRes = OpenConfiguratorCore.GetInstance()
                    .CreateConfiguration(networkId, agSetting.getId());
            if (!libApiRes.IsSuccessful()) {
                return libApiRes;
            }

            java.util.List<TKeyValuePair> settingList = agSetting.getSetting();
            for (TKeyValuePair setting : settingList) {

                libApiRes = OpenConfiguratorCore.GetInstance()
                        .CreateConfigurationSetting(networkId,
                                agSetting.getId(), setting.getName(),
                                setting.getValue());
                if (!libApiRes.IsSuccessful()) {
                    return libApiRes;
                }

                libApiRes = OpenConfiguratorCore.GetInstance()
                        .SetConfigurationSettingEnabled(networkId,
                                agSetting.getId(), setting.getName(),
                                setting.isEnabled());
                if (!libApiRes.IsSuccessful()) {
                    return libApiRes;
                }
            }
        }

        // ActiveAutoGenerationSetting
        libApiRes = OpenConfiguratorCore.GetInstance().SetActiveConfiguration(
                networkId,
                projectConfiguration.getActiveAutoGenerationSetting());

        return libApiRes;
    }

    /**
     * Return the error message in a format from the openCONFIGURATOR result API
     * library.
     *
     * @param result The result from openCONFIGURATOR library.
     * @return The error message from the result instance.
     */
    public static String getErrorMessage(final Result result) {
        String errorMessage = "Code:" + result.GetErrorType().ordinal() + "\t"
                + result.GetErrorMessage();
        System.out.println(result.GetErrorType().name());
        return errorMessage;
    }

    /**
     * Initializes the openCONFIGURATOR library with configurations. Example:
     * Boost logging configuration.
     *
     * @return Result The result from openCONFIGURATOR library.
     *
     * @throws IOException if the configuration files are not found.
     */
    public static Result initOpenConfiguratorLibrary() throws IOException {
        OpenConfiguratorCore core = OpenConfiguratorCore.GetInstance();
        String boostLogSettingsFile = new String();

        boostLogSettingsFile = org.epsg.openconfigurator.Activator
                .getAbsolutePath(
                        IOpenConfiguratorResource.BOOST_LOG_CONFIGURATION);

        System.out.println("Path: " + boostLogSettingsFile);
        // Init logger class with configuration file path
        Result libApiRes = core.InitLoggingConfiguration(boostLogSettingsFile);
        if (!libApiRes.IsSuccessful()) {
            return libApiRes;
        }

        // Add future initializations.

        return libApiRes;
    }

    /**
     * Loads openCONFIGURATOR and its dependent libraries.
     *
     * @throws UnsatisfiedLinkError If the library is not found in the class
     *             path.
     * @throws SecurityException if a security manager exists and its checkLink
     *             method doesn't allow loading of the specified dynamic library
     */
    public static void loadOpenConfiguratorLibrary()
            throws UnsatisfiedLinkError, SecurityException {
        if (SystemUtils.IS_OS_LINUX) {
            System.loadLibrary("boost_date_time"); //$NON-NLS-1$
            System.loadLibrary("boost_system"); //$NON-NLS-1$
            System.loadLibrary("boost_chrono"); //$NON-NLS-1$
            System.loadLibrary("boost_filesystem"); //$NON-NLS-1$
            System.loadLibrary("boost_thread"); //$NON-NLS-1$
            System.loadLibrary("boost_log"); //$NON-NLS-1$
            System.loadLibrary("boost_log_setup"); //$NON-NLS-1$
        } else if (SystemUtils.IS_OS_WINDOWS) {
            System.loadLibrary("boost_date_time-vc110-mt-1_54"); //$NON-NLS-1$
            System.loadLibrary("boost_system-vc110-mt-1_54"); //$NON-NLS-1$
            System.loadLibrary("boost_chrono-vc110-mt-1_54"); //$NON-NLS-1$
            System.loadLibrary("boost_filesystem-vc110-mt-1_54"); //$NON-NLS-1$
            System.loadLibrary("boost_thread-vc110-mt-1_54"); //$NON-NLS-1$
            System.loadLibrary("boost_log-vc110-mt-1_54"); //$NON-NLS-1$
            System.loadLibrary("boost_log_setup-vc110-mt-1_54"); //$NON-NLS-1$
        }
        System.loadLibrary("openconfigurator_core_lib"); //$NON-NLS-1$
        System.loadLibrary("openconfigurator_core_wrapper_java"); //$NON-NLS-1$
    }
}

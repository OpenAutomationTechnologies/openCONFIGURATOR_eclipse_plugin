/*******************************************************************************
 * @file   Activator.java
 *
 * @brief  The activator controls the plug-in life cycle.
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

package org.epsg.openconfigurator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.lang.SystemUtils;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.epsg.openconfigurator.console.LogFileTailListener;
import org.epsg.openconfigurator.console.OpenConfiguratorMessageConsole;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.resources.IOpenConfiguratorResource;
import org.epsg.openconfigurator.util.OpenConfiguratorLibraryUtils;
import org.epsg.openconfigurator.util.PluginErrorDialogUtils;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "org.epsg.openconfigurator"; //$NON-NLS-1$
    private static final String PLUGIN_DEPENDENT_LIBRARY_LOAD_ERROR = "openCONFIGURATOR plugin\n Error loading shared libraries";

    private static final String FILE_NOT_FOUND = "Requested file not found in the plugin. File: {0}";

    // The shared instance
    private static Activator plugin;

    /**
     * Get the absolute path of the file available within the plugin. The
     * resources shall be one of IOpenConfiguratorResource.
     *
     * @param relativePath The relative path of the file.
     * @return The absolute path of the requested file.
     * @throws IOException The path could not be resolved.
     */
    public static String getAbsolutePath(final String relativePath)
            throws IOException {
        Bundle bundle = Activator.plugin.getBundle();
        URL fileURL = bundle.getEntry(relativePath);
        if (fileURL == null) {
            throw new IOException(MessageFormat.format(Activator.FILE_NOT_FOUND,
                    relativePath));
        }

        String absolutePath = null;

        absolutePath = FileLocator.resolve(fileURL).getPath();

        // Remove the '/' from the path in windows.
        if (SystemUtils.IS_OS_WINDOWS) {
            if ((absolutePath != null) && (absolutePath.length() > 1)) {
                absolutePath = absolutePath.substring(1);
            }
        }
        return absolutePath;
    }

    /**
     * Returns the shared instance
     *
     * @return the shared instance
     */
    public static Activator getDefault() {
        return Activator.plugin;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in
     * relative path
     *
     * @param path the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return AbstractUIPlugin.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
                path);
    }

    private Tailer tailer;

    /**
     * The constructor
     */
    public Activator() {
    }

    /*
     * (non-Javadoc)
     *
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.
     * BundleContext )
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        Activator.plugin = this;

        // Load openCONFIGURATOR core libraries
        try {
            OpenConfiguratorLibraryUtils.loadOpenConfiguratorLibrary();
        } catch (UnsatisfiedLinkError | SecurityException e) {
            e.printStackTrace();
            OpenConfiguratorMessageConsole.getInstance().printErrorMessage(
                    Activator.PLUGIN_DEPENDENT_LIBRARY_LOAD_ERROR
                            + e.getMessage(),
                    "");
            PluginErrorDialogUtils.displayErrorMessageDialog(
                    Activator.PLUGIN_DEPENDENT_LIBRARY_LOAD_ERROR, e);
        }

        // Initialize openCONFIGURATOR library
        Result libApiRes = OpenConfiguratorLibraryUtils
                .initOpenConfiguratorLibrary();
        if (!libApiRes.IsSuccessful()) {
            // Report error to the user using the dialog.
            OpenConfiguratorMessageConsole.getInstance()
                    .printLibraryErrorMessage(libApiRes);
            PluginErrorDialogUtils.showMessageWindow(MessageDialog.ERROR,
                    libApiRes);
        }

        // Read the log files written by the library and update in the console
        String logPath = ResourcesPlugin.getWorkspace().getRoot().getLocation()
                .toString() + IOpenConfiguratorResource.LIBRARY_LOG_FILE_PATH;
        tailer = Tailer.create(new File(logPath), new LogFileTailListener(),
                1000);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.
     * BundleContext )
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        Activator.plugin = null;
        tailer.stop();
        super.stop(context);
    }
}

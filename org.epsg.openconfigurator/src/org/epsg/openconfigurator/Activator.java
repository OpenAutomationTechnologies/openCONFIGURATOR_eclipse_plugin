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

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.lang.SystemUtils;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.epsg.openconfigurator.util.PluginErrorDialogUtils;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "org.epsg.openconfigurator"; //$NON-NLS-1$
    private static final String PLUGIN_DEPENDENT_LIBRARY_LOAD_ERROR = "openCONFIGURATOR plugin\nError loading shared libraries";

    // The shared instance
    private static Activator plugin;

    /**
     * Returns the shared instance
     *
     * @return the shared instance
     */
    public static Activator getDefault() {
        return Activator.plugin;
    }

    /**
     * @return Returns the path of the default CN XDD available within the
     *         plugin
     */
    public static URI getDefaultCnXDD() {
        Bundle bundle = Activator.plugin.getBundle();
        URL fileURL = bundle.getEntry("resources/openPOWERLINK_CN.xdd");
        URI defaultCnXdd = null;
        try {
            defaultCnXdd = FileLocator.resolve(fileURL).toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return defaultCnXdd;
    }

    /**
     * @return Returns the path of the default MN XDD available within the
     *         plugin
     */
    public static URI getDefaultMnXDD() {
        Bundle bundle = Activator.plugin.getBundle();
        URL fileURL = bundle.getEntry("resources/openPOWERLINK_MN.xdd");
        URI defaultMnXddURI = null;
        try {
            defaultMnXddURI = FileLocator.resolve(fileURL).toURI();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return defaultMnXddURI;
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

    /**
     * @return Returns the openCONFIGURATOR project schema file
     */
    public static URI getProjectSchemaFile() {
        Bundle bundle = Activator.plugin.getBundle();
        URL fileURL = bundle
                .getEntry("resources/OC_ProjectFile/openCONFIGURATOR.xsd");
        URI filePath = null;
        try {
            filePath = FileLocator.resolve(fileURL).toURI();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return filePath;
    }

    /**
     * @return Returns the schema file for the XDD/XDC
     */
    public static URI getXddSchemaFile() {
        Bundle bundle = Activator.plugin.getBundle();
        URL fileURL = bundle.getEntry("resources/xddschema/Powerlink_Main.xsd");
        URI filePath = null;
        try {
            filePath = FileLocator.resolve(fileURL).toURI();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return filePath;
    }

    /**
     * The constructor
     */
    public Activator() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
     * )
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        Activator.plugin = this;

        // Load openCONFIGURATOR core libraries
        try {
            if (SystemUtils.IS_OS_LINUX) {
                System.loadLibrary("boost_date_time");
                System.loadLibrary("boost_system");
                System.loadLibrary("boost_chrono");
                System.loadLibrary("boost_filesystem");
                System.loadLibrary("boost_thread");
                System.loadLibrary("boost_log");
                System.loadLibrary("boost_log_setup");
            } else if (SystemUtils.IS_OS_WINDOWS) {
                System.loadLibrary("boost_date_time-vc110-mt-1_54");
                System.loadLibrary("boost_system-vc110-mt-1_54");
                System.loadLibrary("boost_chrono-vc110-mt-1_54");
                System.loadLibrary("boost_filesystem-vc110-mt-1_54");
                System.loadLibrary("boost_thread-vc110-mt-1_54");
                System.loadLibrary("boost_log-vc110-mt-1_54");
                System.loadLibrary("boost_log_setup-vc110-mt-1_54");
            }
            System.loadLibrary("openconfigurator_core_lib");
            System.loadLibrary("openconfigurator_core_wrapper_java");
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
            PluginErrorDialogUtils.displayErrorMessageDialog(Activator.plugin
                    .getWorkbench().getActiveWorkbenchWindow().getShell(),
                    Activator.PLUGIN_DEPENDENT_LIBRARY_LOAD_ERROR, e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
     * )
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        Activator.plugin = null;
        super.stop(context);
    }

}

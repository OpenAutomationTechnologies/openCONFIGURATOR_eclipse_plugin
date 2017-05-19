/*******************************************************************************
 * @file   IOpenConfiguratorResource.java
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

package org.epsg.openconfigurator.resources;

/**
 * An interface to list all the resources available in the openCONFIGURATOR
 * plugin.
 *
 * @author Ramakrishnan P
 *
 */
public interface IOpenConfiguratorResource {

    /**
     * Log file path in the workspace.
     */
    public static final String LIBRARY_LOG_FILE_PATH = "/.metadata/.plugins/"
            + org.epsg.openconfigurator.Activator.PLUGIN_ID + "/"
            + org.epsg.openconfigurator.Activator.PLUGIN_ID + ".0.log";

    /**
     * Logging configuration file for BOOST.Log.
     */
    public static final String BOOST_LOG_CONFIGURATION = "resources/boost_log_settings.ini"; //$NON-NLS-1$

    /**
     * openCONFIGURATOR project schema.
     */
    public static final String PROJECT_SCHEMA = "resources/OC_ProjectFile/openCONFIGURATOR.xsd"; //$NON-NLS-1$

    /**
     * XDD schema.
     */
    public static final String XDD_SCHEMA = "resources/xddschema/Powerlink_Main.xsd"; //$NON-NLS-1$

    /**
     * Firmware schema.
     */
    public static final String FIRMWARE_SCHEMA = "resources/firmwareschema/fw_schema.xsd"; //$NON-NLS-1$

    /**
     * Xap schema.
     */
    public static final String XAP_SCHEMA = "resources/xapSchema/xap_schema.xsd"; //$NON-NLS-1$

    /**
     * Default MN XDD.
     */
    public static final String DEFAULT_MN_XDD = "resources/00000000_POWERLINK_CiA302-4_MN.xdd"; //$NON-NLS-1$

    /**
     * Default CN XDD.
     */
    public static final String DEFAULT_CN_XDD = "resources/00000000_POWERLINK_CiA401_CN.xdd"; //$NON-NLS-1$
}

/*******************************************************************************
 * @file   IPluginImages.java
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
 * An interface to list all available images.
 *
 * @author Ramakrishnan P
 *
 */
public interface IPluginImages {

    public final static String LEGACY_OPENCONFIGURATOR_ICON = "icons/openconfigurator.gif";
    public final static String CONFIGURATION_ICON = "icons/releng_gears.gif";
    public final static String ETHERNET_POWERLINK_ICON = "icons/window_16.gif";
    public final static String REFRESH_ICON = "icons/refresh.gif";
    public final static String PROPERTIES_ICON = "icons/properties.gif";
    public final static String RMN_ICON = "icons/rmn.gif";
    public final static String MN_ICON = "icons/mn.gif";
    public final static String CN_ICON = "icons/cn.gif";
    public final static String CN_DISABLED_ICON = "icons/cn_disabled.gif";
    public final static String OBD_ICON = "icons/obd.gif";
    public final static String MAPPING_ICON = "icons/mapping.gif";
    public final static String DISABLE_NODE_ICON = "icons/disable.gif";
}

/*******************************************************************************
 * @file   IPowerlinkProjectSupport.java
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

package org.epsg.openconfigurator.model;

/**
 * Project support constants.
 *
 * @author Ramakrishnan P
 *
 */
public interface IPowerlinkProjectSupport {

    public static final String DEVICE_IMPORT_DIR = "deviceImport";
    public static final String MODULAR_HEAD_DIR = "ModularHead";
    public static final String DEVICE_CONFIGURATION_DIR = "deviceConfiguration";
    public static final String DEVICE_FIRMWARE_DIR = "deviceFirmware";
    public static final String DEFAULT_OUTPUT_DIR = "output";

    public static final String UTF8_ENCODING = "UTF-8";

    public static final String XDC_EXTENSION = ".xdc"; ////$NON-NLS-1$
    public static final String XDD_EXTENSION = ".xdd"; ////$NON-NLS-1$
    public static final String DEFAULT_XDD_FILTER_EXTENSION = "*.xdd;*.XDD"; ////$NON-NLS-1$
    public static final String DEFAULT_XDC_FILTER_EXTENSION = "*.xdc;*.XDC"; ////$NON-NLS-1$
    public static final String DEFAULT_XDD_FILTER_NAME_EXTENSION = "XML Device Description(*.xdd)"; ////$NON-NLS-1$
    public static final String DEFAULT_XDC_FILTER_NAME_EXTENSION = "XML Device Configuration(*.xdc)"; ////$NON-NLS-1$

}

/*******************************************************************************
 * @file   IControlledNodeProperties.java
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
 * Controlled node property objects.
 *
 * @author Ramakrishnan P
 *
 */
public interface IControlledNodeProperties extends IAbstractNodeProperties {

    public static final String CN_TAG = "CN"; //$NON-NLS-1$
    public static final String INTERFACE_LIST_TAG = "InterfaceList"; //$NON-NLS-1$
    public static final String FIRMWARE_LIST_TAG = "FirmwareList"; //$NON-NLS-1$
    public static final String INTERFACE_TAG = "Interface"; //$NON-NLS-1$
    public static final String FIRMWARE_TAG = "Firmware"; //$NON-NLS-1$
    public static final String FIRMWARE_URI = "URI"; //$NON-NLS-1$
    public static final String FIRMWARE_VENDOR_ID = "vendorId"; //$NON-NLS-1$
    public static final String FIRMWARE_PRODUCT_NUMBER = "productNumber"; //$NON-NLS-1$
    public static final String FIRMWARE_DEVICE_REVISION = "deviceRevision"; //$NON-NLS-1$
    public static final String FIRMWARE_DATE = "date"; //$NON-NLS-1$
    public static final String FIRMWARE_TIME = "time"; //$NON-NLS-1$
    public static final String FIRMWARE_KEEP_HEADER = "keepHeader"; //$NON-NLS-1$
    public static final String FIRMWARE_LOCKED = "locked"; //$NON-NLS-1$
    public static final String INTERFACE_ID = "id"; //$NON-NLS-1$
    public static final String MODULE_TAG = "Module"; //$NON-NLS-1$
    public static final String MODULE_NAME = "name"; //$NON-NLS-1$
    public static final String MODULE_POSITION = "position"; //$NON-NLS-1$
    public static final String MODULE_ADDRESS = "address"; //$NON-NLS-1$
    public static final String MODULE_PATH_TO_XDC = "pathToXDC"; //$NON-NLS-1$
    public static final String MODULE_ENABLED = "enabled"; //$NON-NLS-1$

    public static final String CN_ENABLED = "enabled"; //$NON-NLS-1$
    public static final String CN_NODE_TYPE_OBJECT = "Cn.NodeType"; //$NON-NLS-1$
    public static final String CN_IS_CHAINED = "isChained"; //$NON-NLS-1$
    public static final String CN_IS_MULTIPLEXED = "isMultiplexed"; //$NON-NLS-1$

    public static final String CN_FORCED_MULTIPLEXED_CYCLE_OBJECT = "forcedMultiplexedCycle"; //$NON-NLS-1$

    public static final String CN_IS_MANDATORY_OBJECT = "isMandatory"; //$NON-NLS-1$
    public static final String CN_AUTO_START_NODE_OBJECT = "autostartNode"; //$NON-NLS-1$
    public static final String CN_RESET_IN_OPERATIONAL_OBJECT = "resetInOperational"; //$NON-NLS-1$
    public static final String CN_VERIFY_APP_SW_VERSION_OBJECT = "verifyAppSwVersion"; //$NON-NLS-1$
    public static final String CN_AUTO_APP_SW_UPDATE_ALLOWED_OBJECT = "autoAppSwUpdateAllowed"; //$NON-NLS-1$
    public static final String CN_VERIFY_DEVICE_TYPE_OBJECT = "verifyDeviceType"; //$NON-NLS-1$
    public static final String CN_VERIFY_VENDOR_ID_OBJECT = "verifyVendorId"; //$NON-NLS-1$
    public static final String CN_VERIFY_REVISION_NUMBER_OBJECT = "verifyRevisionNumber"; //$NON-NLS-1$
    public static final String CN_VERIFY_PRODUCT_CODE_OBJECT = "verifyProductCode"; //$NON-NLS-1$
    public static final String CN_VERIFY_SERIAL_NUMBER_OBJECT = "verifySerialNumber"; /////$NON-NLS-1$

    public static final String CN_POLL_RESPONSE_TIMEOUT_OBJECT = "presTimeout"; //$NON-NLS-1$

    /** Descriptions **/
    public static final String CN_IS_MANDATORY_DESCRIPTION = "Yes -> Mandatory CN. No -> Optional CN. See  0x1F81, Bit 3.";
    public static final String CN_AUTO_START_NODE_DESCRIPTION = "Yes -> Automatically configure and start this node. See  0x1F81, Bit 2.";
    public static final String CN_RESET_IN_OPERATIONAL_DESCRIPTION = "Yes -> MN may reset node in CS_OPERATIONAL. No -> MN must not reset. See  0x1F81, Bit 4.";
    public static final String CN_VERIFY_APP_SW_VERSION_DESCRIPTION = "Yes -> Application software version verification is required. See  0x1F81, Bit 5.";
    public static final String CN_VERIFY_DEVICE_TYPE_DESCRIPTION = "Yes -> Automatic update of application software allowed. See 0x1F81, Bit 6.";

}

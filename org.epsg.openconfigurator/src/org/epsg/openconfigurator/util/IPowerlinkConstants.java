/*******************************************************************************
 * @file   IPowerlinkConstants.java
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

/**
 * POWERLINK constants.
 *
 * @author Ramakrishnan P
 *
 */
public interface IPowerlinkConstants {

    public static final short INVALID_NODE_ID = 0x0; // 0
    public static final short MN_DEFAULT_NODE_ID = 0xF0; // 240
    public static final short CN_MIN_NODE_ID = 0x01; // 1
    public static final short CN_MAX_NODE_ID = 0xEF; // 239
    public static final short RMN_MIN_NODE_ID = 0xF1; // 251
    public static final short RMN_MAX_NODE_ID = 0xFA; // 250 - Not sure
    public static final short BROADCAST_NODE_ID = 0xFF; // 255
    public static final short DUMMY_NODE_ID = 0xFC; // 252
    public static final short ROUTER_TYPE1_DEFAUTL_NODE_ID = 0xFE; // 254
    public static final short DIAGNOSTIC_DEVICE_DEFAULT_NODE_ID = 0xFD; // 253

    //Object index constants.
    public static final int COMMUNICATION_PROFILE_START_INDEX = 0x1000;
    public static final int COMMUNICATION_PROFILE_END_INDEX = 0x1FFF;
    public static final int MANUFACTURER_PROFILE_START_INDEX = 0x2000;
    public static final int MANUFACTURER_PROFILE_END_INDEX = 0x5FFF;
    public static final int STANDARDISED_DEVICE_PROFILE_START_INDEX = 0x6000;
    public static final int STANDARDISED_DEVICE_PROFILE_END_INDEX = 0x9FFF;
    public static final int STANDARDISED_INTERFACE_PROFILE_START_INDEX = 0xA000;
    public static final int STANDARDISED_INTERFACE_PROFILE_END_INDEX = 0xBFFF;
}

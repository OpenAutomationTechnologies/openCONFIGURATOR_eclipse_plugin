/*******************************************************************************
 * @file   INetworkProperties.java
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
 * Properties related to network.
 *
 * @author Ramakrishnan P
 *
 */
public interface INetworkProperties {

    public static final String NET_CYCLE_TIME_OBJECT = "cycleTime"; //$NON-NLS-1$
    public static final String NET_ASYNC_MTU_OBJECT = "asyncMTU"; //$NON-NLS-1$
    public static final String NET_MUTLIPLEX_CYCLE_CNT_OBJECT = "multiplexedCycleLength"; //$NON-NLS-1$
    public static final String NET_PRESCALER_OBJECT = "prescaler"; //$NON-NLS-1$
    public static final String NET_LOSS_OF_SOC_TOLERANCE_OBJECT = "lossSocTolerance"; //$NON-NLS-1$
    public static final String NET_LOSS_OF_SOC_TOLERANCE_ATTRIBUTE_NAME = "lossOfSocTolerance"; //$NON-NLS-1$

    public static final String NETWORK_CYCLE_TIME_DESCRIPTION = "The cycle-time of the POWERLINK network in "
            + "\u00B5" + "s. See 0x1006.";
    public static final String NETWORK_ASYNC_MTU_DESCRIPTION = "The MTU for the async. slot in bytes. See 0x1F98/0x8.";
    public static final String NETWORK_MULTIPLEXED_CYCLE_CNT_DESCRIPTION = "The length of the multiplexed cycle. See 0x1F98/0x7.";
    public static final String NETWORK_PRE_SCALER_DESCRIPTION = "Toggle rate for the SoC PS flag. See 0x1F98/0x9.";
    public static final String LOSS_SOC_TOLERANCE_DESCRIPTION = "A tolerance interval in [ns] to be applied by CN's Loss of SoC error recognition. See 0x1C14.";

    public static final long CYCLE_TIME_OBJECT_ID = 0x1006;

    public static final long ASYNC_MTU_OBJECT_ID = 0x1F98;
    public static final short ASYNC_MTU_SUBOBJECT_ID = 0x08;

    public static final long MUTLIPLEX_CYCLE_CNT_OBJECT_ID = 0x1F98;
    public static final short MUTLIPLEX_CYCLE_CNT_SUBOBJECT_ID = 0x07;

    public static final long PRESCALER_OBJECT_ID = 0x1F98;
    public static final short PRESCALER_SUBOBJECT_ID = 0x09;

    public static final long POLL_RESPONSE_TIMEOUT_OBJECT_ID = 0x1F92;

    public static final long LOSS_SOC_TOLERANCE_OBJECT_ID = 0x1C14;
}

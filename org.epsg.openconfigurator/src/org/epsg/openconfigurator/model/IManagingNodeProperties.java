/*******************************************************************************
 * @file   IManagingNodeProperties.java
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
 * Managing node property objects.
 *
 * @author Ramakrishnan P
 *
 */
public interface IManagingNodeProperties extends IAbstractNodeProperties {

    public static final String MN_TAG = "MN";
    public static final String MN_TRANSMIT_PRES_OBJECT = "transmitsPRes"; //$NON-NLS-1$
    public static final String MN_ASYNC_TIMEOUT_OBJECT = "asyncSlotTimeout"; //$NON-NLS-1$
    public static final String MN_ASND_MAX_NR_OBJECT = "aSndMaxNumber"; //$NON-NLS-1$

    public static final String MN_TRANSMIT_PRES_DESCRIPTION = "Yes -> MN transmits PRes. No -> MN does not transmit PRes. See 0x1F81, Bit 12.";
    public static final String MN_ASYNC_SLOT_TIMEOUT_DESCRIPTION = "The async. slot timeout in ns. See 0x1F8A/0x2.";
    public static final String MN_ASND_MAX_NR_DESCRIPTION = "The max. no. of ASnd-Frames which can be sent in the async. phase. See 0x1F8A/0x3.";

    public static final long ASYNC_SLOT_TIMEOUT_OBJECT_ID = 0x1F8A;
    public static final short ASYNC_SLOT_TIMEOUT_SUBOBJECT_ID = 0x02;

    public static final long ASND_MAX_NR_OBJECT_ID = 0x1F8A;
    public static final short ASND_MAX_NR_SUBOBJECT_ID = 0x03;
}

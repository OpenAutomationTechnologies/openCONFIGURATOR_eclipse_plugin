/*******************************************************************************
 * @file   IRedundantManagingNodeProperties.java
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
 * Redundant managing node properties.
 *
 * @author Ramakrishnan P
 *
 */
public interface IRedundantManagingNodeProperties
        extends IAbstractNodeProperties {

    public static final String RMN_TAG = "RMN";
    public static final String RMN_WAIT_NOT_ACTIVE_OBJECT = "waitNotActive"; //$NON-NLS-1$
    public static final String RMN_PRIORITY_OBJECT = "priority"; //$NON-NLS-1$

    public static final String RMN_WAIT_NOT_ACTIVE_DESCRIPTION = "Describes the time interval in " + "\u00B5" + "s that the RMN shall remain in state 'NOT_ACTIVE' and listen for POWERLINK frames on the network before it changes over to 'PRE_OPERATIONAL_1'.";
    public static final String RMN_PRIORITY_DESCRIPTION = "Describes the priority that the RMN will have during switch-over (in case of AMN failure). Value 1 is the highest priority value.";

    //
    public static final long RMN_WAIT_NOT_ACTIVE_OBJECT_ID = 0x1F89;
    public static final short RMN_WAIT_NOT_ACTIVE_SUBOBJECT_ID = 0x01;

    public static final long RMN_PRIORITY_OBJECT_ID = 0x1F89;
    public static final short RMN_PRIORITY_SUBOBJECT_ID = 0xA;
}

/*******************************************************************************
 * @file   PdoChannel.java
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

import org.epsg.openconfigurator.console.OpenConfiguratorMessageConsole;

/**
 * A wrapper base type for creating a PDO Channel.
 *
 * @see PdoType
 * @see RpdoChannel
 * @see TpdoChannel
 *
 * @author Ramakrishnan P
 *
 */
public class PdoChannel {

    /**
     * The communication param object.
     */
    private final PowerlinkObject communicationParam;

    /**
     * The mapping param object.
     */
    private final PowerlinkObject mappingParam;

    /**
     * The node linked with the channel.
     */
    private final Node node;

    /**
     * PDO Type.
     */
    private final PdoType pdoType;

    /**
     * Channel name with object ID.
     */
    private final String nameWithObjectId;

    /**
     * Channel name.
     */
    private final String name;

    /**
     * Channel number.
     */
    private final int channelNumber;

    PdoChannel(Node node, PdoType pdoType, PowerlinkObject communicationParam,
            PowerlinkObject mappingParam) {
        this.node = node;
        this.pdoType = pdoType;
        this.communicationParam = communicationParam;
        this.mappingParam = mappingParam;
        channelNumber = Integer.decode(
                "0x" + this.communicationParam.getIdRaw().substring(2, 4));

        name = this.pdoType.toString() + "#" + getChannelNumber();
        nameWithObjectId = name + " " + "(" + mappingParam.getIdHex() + ")";
    }

    /**
     * @return Channel number of PDO channel
     */
    public int getChannelNumber() {
        return channelNumber;
    }

    /**
     * @return Communication param object of the PDO channel.
     */
    public PowerlinkObject getCommunicationParam() {
        return communicationParam;
    }

    /**
     * @return Number of enable entries in the PDO Channel.
     */
    public int getEnabledNumberofEntries() {
        PowerlinkSubobject nrEntriesSubObj = mappingParam
                .getSubObject((short) 0);
        if (nrEntriesSubObj == null) {
            return 0;
        }

        String value = nrEntriesSubObj.getActualValue();
        if (value == null) {
            value = nrEntriesSubObj.getDefaultValue();
        }

        if (value == null) {
            System.err.println("No default/actual value");
            return 0;
        }

        if (value.equals("")) {
            return 0;
        }

        Integer nrEntries = Integer.decode(value);
        return nrEntries.intValue();
    }

    /**
     * @return Mapping param object of the PDO channel.
     */
    public PowerlinkObject getMappingParam() {
        return mappingParam;
    }

    /**
     * @return Mapping Version of the PDO Channel.
     */
    public String getMappingVersion() {
        PowerlinkSubobject mappingVersionSubObj = communicationParam
                .getSubObject((short) 2);
        if (mappingVersionSubObj == null) {
            return null;
        }

        String mappingVersion = mappingVersionSubObj.getActualValue();
        if (mappingVersion == null) {
            mappingVersion = mappingVersionSubObj.getDefaultValue();
        }

        return mappingVersion;
    }

    /**
     * @return Name of PDO channel.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the next Sub Object based on sub-index.
     *
     * @param currentSubObject
     * @return The object below to it in the object dictionary.
     */
    public PowerlinkSubobject getNextSubObject(
            PowerlinkSubobject currentSubObject) {

        int nextrowindex = (currentSubObject.getId() + 1);

        return currentSubObject.getObject().getSubObject((short) nextrowindex);
    }

    /**
     * @return Node linked to the channel.
     */
    public Node getNode() {
        return node;
    }

    /**
     * @see PdoType
     * @return PDO type.
     */
    public PdoType getPdoType() {
        return pdoType;
    }

    /**
     * Get the previous Sub Object based on sub-index.
     *
     * @param currentSubObject Current subobject.
     *
     * @return The object above to it in the object dictionary.
     */
    public PowerlinkSubobject getPreviousSubObject(
            PowerlinkSubobject currentSubObject) {

        short previousrowindex = (short) (currentSubObject.getId() - 1);

        return currentSubObject.getObject().getSubObject(previousrowindex);
    }

    /**
     * @return The target node Id value.
     */
    public short getTargetNodeId() {
        PowerlinkSubobject nodeIdSubObj = communicationParam
                .getSubObject((short) 1);
        short nodeIdValue = -1;
        if (nodeIdSubObj != null) {
            String targetNodeId = nodeIdSubObj.getActualDefaultValue();

            if (targetNodeId.isEmpty()) {
                targetNodeId = "0";
            }

            try {
                nodeIdValue = Integer.decode(targetNodeId).shortValue();
            } catch (NumberFormatException ex) {
                OpenConfiguratorMessageConsole.getInstance().printErrorMessage(
                        "Invalid Target node Id for channel" + getText(),
                        getNode().getProject().getName());
            }
        } else {
            OpenConfiguratorMessageConsole.getInstance().printErrorMessage(
                    "Subobject " + communicationParam.getIdHex()
                            + "/0x01 not found.",
                    getNode().getProject().getName());
        }

        return nodeIdValue;
    }

    /**
     *
     * @return Channel name with Object Id.
     */
    public String getText() {
        return nameWithObjectId;
    }
}

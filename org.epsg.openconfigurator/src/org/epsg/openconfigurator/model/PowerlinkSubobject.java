/*******************************************************************************
 * @file   PowerlinkSubobject.java
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

import javax.xml.bind.DatatypeConverter;

import org.eclipse.core.resources.IProject;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectUtils;
import org.epsg.openconfigurator.xmlbinding.xdd.TObject;

/**
 * Wrapper class for a POWERLINK sub-object.
 *
 * @author Ramakrishnan P
 *
 */
public class PowerlinkSubobject {

    /**
     * Node associated with this object.
     */
    private Node nodeInstance;

    /**
     * Object associated with the sub-object.
     */
    private PowerlinkObject object;

    /**
     * SubObject model from the XDC.
     */
    private TObject.SubObject subObject;

    /**
     * Associated Eclipse project.
     */
    private IProject project;

    /**
     * SubObject ID in hex without 0x.
     */
    private final String subobjectIdRaw; // SubObject ID without 0x prefix

    /**
     * SubObject ID.
     */
    private final short subobjectIdShort;

    /**
     * SubObject ID in hex with 0x.
     */
    private final String subobjectId;

    /**
     * XPath to find this SubObject in the XDC.
     */
    private final String xpath;

    /**
     * Name of the SubObject with ID.
     */
    private final String readableName;

    /**
     * Datatype in the human readable format.
     */
    private final String dataType;

    /**
     * Constructs a POWERLINK SubObject.
     *
     * @param nodeInstance Node linked with the subobject.
     * @param object The Object linked with the subobject.
     * @param subObject The SubObject model available in the XDC.
     */
    public PowerlinkSubobject(Node nodeInstance, PowerlinkObject object,
            TObject.SubObject subObject) {
        this.nodeInstance = nodeInstance;
        if (nodeInstance != null) {
            project = nodeInstance.getProject();
        }

        this.object = object;
        this.subObject = subObject;

        subobjectIdRaw = DatatypeConverter
                .printHexBinary(this.subObject.getSubIndex());
        subobjectIdShort = Short.parseShort(subobjectIdRaw, 16);
        subobjectId = "0x" + subobjectIdRaw;
        readableName = (this.subObject.getName() + " (" + subobjectId + ")");
        xpath = "//plk:Object[@index='" + getObjectIdRaw() + "']"
                + "/plk:SubObject[@subIndex='" + subobjectIdRaw + "']";
        if (this.subObject.getDataType() != null) {
            dataType = ObjectDatatype.getDatatypeName(DatatypeConverter
                    .printHexBinary(getSubObject().getDataType()));
        } else {
            dataType = "";
        }
    }

    /**
     * @return The actual value or default value if actual value is not
     *         available.
     */
    public String getActualDefaultValue() {
        if (getActualValue() != null) {
            return getActualValue();
        }

        if (getDefaultValue() != null) {
            return getDefaultValue();
        }

        return null;
    }

    public String getActualValue() {
        return subObject.getActualValue();
    }

    public String getDatatype() {
        return dataType;
    }

    public String getNetworkId() {
        return project.getName();
    }

    public Node getNode() {
        return nodeInstance;
    }

    public short getNodeId() {
        return nodeInstance.getNodeId();
    }

    public Object getNodeModel() {
        return nodeInstance.getNodeModel();
    }

    public long getObjectId() {
        return object.getObjectId();
    }

    public String getObjectIdRaw() {
        return object.getObjectIdRaw();
    }

    public String getObjectIndex() {
        return object.getObjectIndex();
    }

    public IProject getProject() {
        return project;
    }

    public short getSubobjecId() {
        return subobjectIdShort;
    }

    public TObject.SubObject getSubObject() {
        return subObject;
    }

    public String getSubobjectIdRaw() {
        return subobjectIdRaw;
    }

    public String getSubobjectIndex() {
        return subobjectId;
    }

    public String getText() {
        return readableName;
    }

    public String getXpath() {
        return xpath;
    }

    /**
     * Set the actual value to this subobject.
     *
     * @param actualValue The value to be set.
     * @param writeToXdc Writes the value immediately to XDC.
     */
    public void setActualValue(final String actualValue, boolean writeToXdc) {

        if (writeToXdc) {
            OpenConfiguratorProjectUtils.updateObjectAttributeValue(getNode(),
                    getObjectIdRaw(), true, getSubobjectIdRaw(), actualValue);
        }

        subObject.setActualValue(actualValue);
    }
}

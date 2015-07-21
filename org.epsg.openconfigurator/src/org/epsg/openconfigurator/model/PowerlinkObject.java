/*******************************************************************************
 * @file   PowerlinkObject.java
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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.eclipse.core.resources.IProject;
import org.epsg.openconfigurator.xmlbinding.xdd.TObject;

/**
 * Wrapper class for a POWERLINK object.
 *
 * @author Ramakrishnan P
 *
 */
public class PowerlinkObject {

    /**
     * Node associated with this object.
     */
    private final Node nodeInstance;

    /**
     * Object model from the XDC.
     */
    private final TObject object;

    /**
     * Associated Eclipse project.
     */
    private final IProject project;

    /**
     * List of sub-objects available in the node.
     */
    private final List<PowerlinkSubobject> subObjectsList = new ArrayList<PowerlinkSubobject>();

    /**
     * Object ID in hex without 0x.
     */
    private final String objectIdRaw;

    /**
     * Object ID.
     */
    private final long objectIdL;

    /**
     * Object ID in hex with 0x.
     */
    private final String objectId;

    /**
     * XPath to find this object in the XDC.
     */
    private final String xpath;

    /**
     * Name of the object with ID.
     */
    private final String readableName; // Object name is not modifiable

    /**
     * Datatype in the human readable format.
     */
    private final String dataType;

    /**
     * Constructs a POWERLINK object.
     *
     * @param nodeInstance Node linked with the object.
     * @param object The Object model available in the XDC.
     */
    public PowerlinkObject(Node nodeInstance, TObject object) {
        if ((nodeInstance == null) || (object == null)) {
            throw new IllegalArgumentException();
        }

        this.nodeInstance = nodeInstance;
        project = nodeInstance.getProject();

        this.object = object;
        objectIdRaw = DatatypeConverter.printHexBinary(this.object.getIndex());
        objectIdL = Long.parseLong(objectIdRaw, 16);
        objectId = "0x" + objectIdRaw;
        readableName = (this.object.getName() + " (" + objectId + ")");
        xpath = "//plk:Object[@index='" + objectIdRaw + "']";
        if (this.object.getDataType() != null) {
            dataType = ObjectDatatype.getDatatypeName(DatatypeConverter
                    .printHexBinary(this.object.getDataType()));
        } else {
            dataType = "";
        }

        // Calculate the subobjects available in this object.
        for (TObject.SubObject subObject : this.object.getSubObject()) {
            PowerlinkSubobject obj = new PowerlinkSubobject(nodeInstance, this,
                    subObject);
            subObjectsList.add(obj);
        }
    }

    public String getActualValue() {
        return object.getActualValue();
    }

    public String getDataType() {
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

    public TObject getObject() {
        return object;
    }

    public long getObjectId() {
        return objectIdL;
    }

    public String getObjectIdRaw() {
        return objectIdRaw;
    }

    public String getObjectIndex() {
        return objectId;
    }

    public IProject getProject() {
        return project;
    }

    /**
     * The subObject instance for the given ID, null if the subobject ID is not
     * found.
     *
     * @param subObjectId SubObject ID ranges from 0x00 to 0xFE
     * @return The subObject instance.
     */
    public PowerlinkSubobject getSubObject(short subObjectId) {
        for (PowerlinkSubobject subObj : subObjectsList) {
            if (subObj.getSubobjecId() == subObjectId) {
                return subObj;
            }
        }
        return null;
    }

    /**
     * @return The list of subobjects.
     */
    public List<PowerlinkSubobject> getSubObjects() {
        return subObjectsList;
    }

    /**
     * @return The name of the object with ID.
     */
    public String getText() {
        return readableName;
    }

    /**
     * @return The XPath to find this object in the XDC.
     */
    public String getXpath() {
        return xpath;
    }

    /**
     * Set the actual value to this object.
     *
     * @param actualValue The value to be set.
     * @param writeToXdc Writes the value to the XDC immediately.
     */
    public void setActualValue(final String actualValue, boolean writeToXdc) {

        object.setActualValue(actualValue);
    }
}

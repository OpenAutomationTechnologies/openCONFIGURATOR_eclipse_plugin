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

import java.io.IOException;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.IProject;
import org.epsg.openconfigurator.lib.wrapper.OpenConfiguratorCore;
import org.epsg.openconfigurator.lib.wrapper.Result;
import org.epsg.openconfigurator.util.OpenConfiguratorLibraryUtils;
import org.epsg.openconfigurator.util.OpenConfiguratorProjectUtils;
import org.epsg.openconfigurator.xmlbinding.xdd.TObject;
import org.epsg.openconfigurator.xmlbinding.xdd.TObjectAccessType;
import org.epsg.openconfigurator.xmlbinding.xdd.TObjectPDOMapping;
import org.jdom2.JDOMException;

/**
 * Wrapper class for a POWERLINK sub-object.
 *
 * @author Ramakrishnan P
 *
 */
public class PowerlinkSubobject extends AbstractPowerlinkObject {

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
     * Flag to indicate that this object is TPDO mappable or not.
     */
    private boolean isTpdoMappable = false;

    /**
     * Flag to indicate that this object is RPDO mappable or not.
     */
    private boolean isRpdoMappable = false;

    /**
     * Constructs a POWERLINK SubObject.
     *
     * @param nodeInstance Node linked with the subobject.
     * @param object The Object linked with the subobject.
     * @param subObject The SubObject model available in the XDC.
     */
    public PowerlinkSubobject(Node nodeInstance, PowerlinkObject object,
            TObject.SubObject subObject) {
        super(nodeInstance);

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
        xpath = object.getXpath() + "/plk:SubObject[@subIndex='"
                + subobjectIdRaw + "']";
        if (this.subObject.getDataType() != null) {
            dataType = ObjectDatatype.getDatatypeName(
                    DatatypeConverter.printHexBinary(getModel().getDataType()));
        } else {
            dataType = "";
        }

        TObjectPDOMapping pdoMapping = subObject.getPDOmapping();
        TObjectAccessType accessType = subObject.getAccessType();
        if (((pdoMapping == TObjectPDOMapping.DEFAULT)
                || (pdoMapping == TObjectPDOMapping.OPTIONAL)
                || (pdoMapping == TObjectPDOMapping.RPDO))) {

            if (subObject.getUniqueIDRef() != null) {
                isRpdoMappable = true;
            } else {
                if ((accessType == TObjectAccessType.RW)
                        || (accessType == TObjectAccessType.WO)) {
                    isRpdoMappable = true;
                }
            }

        } else if (((pdoMapping == TObjectPDOMapping.DEFAULT)
                || (pdoMapping == TObjectPDOMapping.OPTIONAL)
                || (pdoMapping == TObjectPDOMapping.TPDO))) {
            if (subObject.getUniqueIDRef() != null) {
                isTpdoMappable = true;
            } else {
                if ((accessType == TObjectAccessType.RO)
                        || (accessType == TObjectAccessType.RW)) {
                    isTpdoMappable = true;
                }
            }
        }
    }

    /**
     * Deletes the actual value of given sub object of the node.
     *
     * Note: This does not delete from the XML file.
     */
    public void deleteActualValue() {
        subObject.setActualValue(null);
    }

    /**
     * Add the force configurations to the project.
     *
     * @param force True to add and false to remove.
     * @param writeToXdc True to write the changes to the XDC file.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public synchronized void forceActualValue(boolean force, boolean writeToXdc)
            throws JDOMException, IOException {

        if (writeToXdc) {
            OpenConfiguratorProjectUtils.forceActualValue(getNode(), object,
                    this, force);
        }

        org.epsg.openconfigurator.xmlbinding.projectfile.Object forcedObj = new org.epsg.openconfigurator.xmlbinding.projectfile.Object();
        forcedObj.setIndex(object.getModel().getIndex());
        forcedObj.setSubindex(subObject.getSubIndex());

        nodeInstance.forceObjectActualValue(forcedObj, force);
    }

    /**
     * @return Access type of sub-object.
     */
    public TObjectAccessType getAccessType() {
        return subObject.getAccessType();
    }

    /**
     * @return The actual value or default value if actual value is not
     *         available.
     */
    public String getActualDefaultValue() {
        String actualValue = subObject.getActualValue();
        if ((actualValue != null) && !actualValue.isEmpty()) {
            return actualValue;
        }

        String defaultValue = subObject.getDefaultValue();
        if (defaultValue != null) {
            return defaultValue;
        }

        return StringUtils.EMPTY;
    }

    /**
     * @return Actual value of sub object.
     */
    public String getActualValue() {
        String actualValue = subObject.getActualValue();
        if (actualValue == null) {
            actualValue = StringUtils.EMPTY;
        }
        return actualValue;
    }

    /**
     * @return data type of sub-object.
     */
    public String getDatatype() {
        return dataType;
    }

    /**
     * @return Default value of sub object.
     */
    public String getDefaultValue() {
        String defaultValue = subObject.getDefaultValue();
        if (defaultValue == null) {
            defaultValue = StringUtils.EMPTY;
        }
        return defaultValue;
    }

    /**
     * @return Model of sub object.
     */
    public TObject.SubObject getModel() {
        return subObject;
    }

    /**
     * @return Network id of node.
     */
    public String getNetworkId() {
        return nodeInstance.getNetworkId();
    }

    /**
     * @return Instance of node.
     */
    public Node getNode() {
        return nodeInstance;
    }

    /**
     * @return Id of the node.
     */
    public short getNodeId() {
        return nodeInstance.getNodeId();
    }

    /**
     * @return Node model of node.
     */
    public Object getNodeModel() {
        return nodeInstance.getNodeModel();
    }

    /**
     * @return Object
     */
    public PowerlinkObject getObject() {
        return object;
    }

    /**
     * @return Id of the object.
     */
    public long getObjectId() {
        return object.getObjectId();
    }

    /**
     * @return Id of the object without hexadecimal notation (0x).
     */
    public String getObjectIdRaw() {
        return object.getObjectIdRaw();
    }

    /**
     * @return Index of the object.
     */
    public String getObjectIndex() {
        return object.getObjectIndex();
    }

    /**
     * @return PDO mapping of sub object.
     */
    public TObjectPDOMapping getPdoMapping() {
        return subObject.getPDOmapping();
    }

    /**
     * @return Instance of project.
     */
    public IProject getProject() {
        return project;
    }

    /**
     * @return the size of the object based on the datatype.
     */
    public long getSize() {
        long size[] = new long[2];
        Result res = OpenConfiguratorCore.GetInstance().GetObjectSize(
                nodeInstance.getNetworkId(), nodeInstance.getNodeId(),
                object.getObjectId(), size);
        if (!res.IsSuccessful()) {
            System.err.println("Error getting the Size "
                    + OpenConfiguratorLibraryUtils.getErrorMessage(res));
        } else {
            return size[0];
        }

        return 0;
    }

    /**
     * @return Id of sub-object.
     */
    public short getSubobjecId() {
        return subobjectIdShort;
    }

    /**
     * @return Readable ID of sub-object.
     */
    public String getSubobjectIdRaw() {
        return subobjectIdRaw;
    }

    /**
     * @return Index of sub object.
     */
    public String getSubobjectIndex() {
        return subobjectId;
    }

    /**
     * @return Readable name of sub object.
     */
    public String getText() {
        return readableName;
    }

    /**
     * @return Returns the unique name of the object by adding the object name,
     *         id and sub-object name and id.
     */
    public String getUniqueName() {
        return object.getModel().getName() + "_" + subObject.getName() + "("
                + object.getObjectIndex() + "/" + subobjectId + ")";
    }

    /**
     * @return Xpath.
     */
    public String getXpath() {
        return xpath;
    }

    /**
     * Checks for forced objects.
     *
     * @return <code>True</code> if object is forced. <code>False</code> if
     *         object is not forced
     */
    public boolean isObjectForced() {
        return nodeInstance.isObjectIdForced(object.getModel().getIndex(),
                subObject.getSubIndex());
    }

    /**
     * @return <code>True</code> if object is RPDO mappable. <code>False</code>
     *         if object is not.
     */
    public boolean isRpdoMappable() {
        return isRpdoMappable;
    }

    /**
     * @return <code>True</code> if object is TPDO mappable. <code>False</code>
     *         if object is not.
     */
    public boolean isTpdoMappable() {
        return isTpdoMappable;
    }

    /**
     * Set the actual value to this subobject.
     *
     * @param actualValue The value to be set.
     * @param writeToXdc Writes the value immediately to XDC.
     * @throws IOException Errors with XDC file modifications.
     * @throws JDOMException Errors with time modifications.
     */
    public void setActualValue(final String actualValue, boolean writeToXdc)
            throws JDOMException, IOException {

        TObjectAccessType accessType = getModel().getAccessType();
        if (accessType != null) {
            if ((accessType == TObjectAccessType.RO)
                    || (accessType == TObjectAccessType.CONST)) {
                throw new RuntimeException("Restricted access to sub-object "
                        + "'" + subObject.getName() + "'"
                        + " to set the actual value.");
            }
        }

        subObject.setActualValue(actualValue);

        if (writeToXdc) {
            OpenConfiguratorProjectUtils.updateObjectAttributeActualValue(
                    getNode(), this, actualValue);
        }
    }
}

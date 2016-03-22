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
import org.epsg.openconfigurator.xmlbinding.xdd.TObjectExtensionHead;
import org.epsg.openconfigurator.xmlbinding.xdd.TObjectPDOMapping;
import org.jdom2.JDOMException;

/**
 * Wrapper class for a POWERLINK sub-object.
 *
 * @author Ramakrishnan P
 *
 */
public class PowerlinkSubobject extends AbstractPowerlinkObject
        implements IPowerlinkSubObject {

    /**
     * Object associated with the sub-object.
     */
    private final PowerlinkObject object;

    /**
     * Associated Eclipse project.
     */
    private final IProject project;

    /**
     * SubObject ID in hex without 0x.
     */
    private final String idRaw; // SubObject ID without 0x prefix

    /**
     * Index of sub-Object
     */
    private final byte[] idByte;

    /**
     * SubObject ID.
     */
    private final short idShort;

    /**
     * SubObject ID in hex with 0x.
     */
    private final String idHex;

    /**
     * XPath to find this SubObject in the XDC.
     */
    private final String xpath;

    /**
     * Datatype in the human readable format.
     */
    private final String dataTypeReadable;

    /**
     * Flag to indicate that this object is TPDO mappable or not.
     */
    private boolean isTpdoMappable = false;

    /**
     * Flag to indicate that this object is RPDO mappable or not.
     */
    private boolean isRpdoMappable = false;

    /**
     * Error message string to identify configuration error of POWERLINK
     * sub-object.
     */
    private String configurationError;

    /**
     * POWERLINK sub-object capable of mapping with PDO channels
     */
    private final TObjectPDOMapping pdoMapping;

    /**
     * Access type of POWERLINK sub-object from XDC model.
     */
    private final TObjectAccessType accessType;

    /**
     * Actual value of POWERLINK sub-object from the TObject model.
     */
    private String actualValue;

    /**
     * Default value of POWERLINK sub-object from the TObject model
     */
    private final String defaultValue;

    /**
     * Name of the SubObject.
     */
    private final String name;

    /**
     * Unique ID value of POWERLINK sub-object.
     */
    private final Object uniqueIDRef;

    /**
     * Higher value limit of POWERLINK sub-object given in the XDD/XDC file.
     */
    private final String highLimit;

    /**
     * Lower value of limit of POWERLINK sub-object given in the XDD/XDC file.
     */
    private final String lowLimit;

    /**
     * Type of POWERLINK sub-object given in the XDD/XDC file.
     */
    private final short objectType;

    /**
     * The data type of POWERLINK sub-object given in the XDD/XDC file.
     */
    private final byte[] dataType;

    /**
     * The denotation variable of POWERLINK sub-object.
     */
    private final String denotation;

    /**
     * The flag object for POWERLINK sub-object.
     */
    private final byte[] objFlags;

    /**
     * Constructs a POWERLINK SubObject based on TObject.SubObject model
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
        } else {
            project = null;
        }

        this.object = object;
        idByte = subObject.getSubIndex();
        idRaw = DatatypeConverter.printHexBinary(idByte);
        idShort = Short.parseShort(idRaw, 16);
        idHex = "0x" + idRaw;
        name = subObject.getName();
        xpath = object.getXpath() + "/plk:SubObject[@subIndex='" + idRaw + "']";

        denotation = subObject.getDenotation();
        objFlags = subObject.getObjFlags();

        objectType = subObject.getObjectType();
        dataType = subObject.getDataType();
        if (dataType != null) {
            dataTypeReadable = ObjectDatatype.getDatatypeName(
                    DatatypeConverter.printHexBinary(dataType));
        } else {
            dataTypeReadable = StringUtils.EMPTY;
        }

        highLimit = subObject.getHighLimit();
        lowLimit = subObject.getLowLimit();

        actualValue = subObject.getActualValue();
        defaultValue = subObject.getDefaultValue();

        pdoMapping = subObject.getPDOmapping();
        accessType = subObject.getAccessType();
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

        }
        if (((pdoMapping == TObjectPDOMapping.DEFAULT)
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

        uniqueIDRef = subObject.getUniqueIDRef();
    }

    /**
     * Constructs a POWERLINK sub-object based on TObjectExtensionHead.SubObject
     * model
     *
     * @param nodeInstance Node linked with the subobject.
     * @param object The Object linked with the subobject.
     * @param subObject The SubObject model available in the XDC.
     */
    public PowerlinkSubobject(Node nodeInstance, PowerlinkObject object,
            TObjectExtensionHead.SubObject subObject) {
        super(nodeInstance);

        if (nodeInstance != null) {
            project = nodeInstance.getProject();
        } else {
            project = null;
        }

        this.object = object;
        idByte = subObject.getSubIndex();
        idRaw = DatatypeConverter.printHexBinary(idByte);
        idShort = Short.parseShort(idRaw, 16);
        idHex = "0x" + idRaw;
        name = subObject.getName();
        xpath = object.getXpath() + "/plk:SubObject[@subIndex='" + idRaw + "']";

        denotation = subObject.getDenotation();
        objFlags = subObject.getObjFlags();

        objectType = subObject.getObjectType();
        dataType = subObject.getDataType();
        if (dataType != null) {
            dataTypeReadable = ObjectDatatype.getDatatypeName(
                    DatatypeConverter.printHexBinary(dataType));
        } else {
            dataTypeReadable = StringUtils.EMPTY;
        }

        highLimit = subObject.getHighLimit();
        lowLimit = subObject.getLowLimit();

        actualValue = subObject.getActualValue();
        defaultValue = subObject.getDefaultValue();

        pdoMapping = subObject.getPDOmapping();
        accessType = subObject.getAccessType();
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
        }
        if (((pdoMapping == TObjectPDOMapping.DEFAULT)
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

        uniqueIDRef = subObject.getUniqueIDRef();
    }

    /**
     * Deletes the actual value of given sub object of the node.
     *
     * Note: This does not delete from the XML file.
     */
    public void deleteActualValue() {
        actualValue = null;
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
        forcedObj.setIndex(object.getIndex());
        forcedObj.setSubindex(idByte);

        nodeInstance.forceObjectActualValue(forcedObj, force);
    }

    /**
     * @return Access type of sub-object.
     */
    @Override
    public TObjectAccessType getAccessType() {
        return accessType;
    }

    /**
     * @return The actual value or default value if actual value is not
     *         available.
     */
    @Override
    public String getActualDefaultValue() {
        if ((actualValue != null) && !actualValue.isEmpty()) {
            return actualValue;
        }

        if (defaultValue != null) {
            return defaultValue;
        }

        return StringUtils.EMPTY;
    }

    /**
     * @return Actual value of sub object.
     */
    @Override
    public String getActualValue() {
        String value = actualValue;
        if (value == null) {
            value = StringUtils.EMPTY;
        }
        return value;
    }

    /**
     * Returns error message string to identify configuration error of POWERLINK
     * sub-object
     */
    @Override
    public String getConfigurationError() {
        String cfgError = configurationError;
        if (cfgError == null) {
            cfgError = StringUtils.EMPTY;
        }
        return cfgError;
    }

    /**
     * @return Data type of the sub-object.
     */
    public byte[] getDataType() {
        return dataType;
    }

    /**
     * @return data type of sub-object.
     */
    @Override
    public String getDataTypeReadable() {
        return dataTypeReadable;
    }

    /**
     * @return Default value of sub object.
     */
    @Override
    public String getDefaultValue() {
        String value = defaultValue;
        if (value == null) {
            value = StringUtils.EMPTY;
        }
        return value;
    }

    /**
     * @return Denotation variable of POWERLINK sub-object given in the XDD/XDC
     *         file.
     */
    public String getDenotation() {
        return denotation;
    }

    /**
     * Returns maximum value range of POWERLINK sub-object
     */
    @Override
    public String getHighLimit() {
        String value = highLimit;
        if (value == null) {
            value = StringUtils.EMPTY;
        }
        return value;
    }

    /**
     * Returns ID of POWERLINK sub-object
     */
    @Override
    public short getId() {
        return idShort;
    }

    /**
     * Returns ID of POWERLINK sub-object in hexadecimal format.
     */
    @Override
    public String getIdHex() {
        return idHex;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.epsg.openconfigurator.model.IPowerlinkBaseObject#getIdRaw()
     */
    @Override
    public String getIdRaw() {
        return idRaw;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.epsg.openconfigurator.model.IPowerlinkBaseObject#getLowLimit()
     */
    @Override
    public String getLowLimit() {
        String value = lowLimit;
        if (value == null) {
            value = StringUtils.EMPTY;
        }
        return value;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.epsg.openconfigurator.model.IPowerlinkBaseObject#getModel()
     */
    @Override
    public Object getModel() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.epsg.openconfigurator.model.IPowerlinkBaseObject#getName()
     */
    @Override
    public String getName() {
        String value = name;
        if (value == null) {
            value = StringUtils.EMPTY;
        }
        return value;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.epsg.openconfigurator.model.IPowerlinkBaseObject#getNameWithId()
     */
    @Override
    public String getNameWithId() {
        return (getName() + " (" + getIdHex() + ")");
    }

    /**
     * @return Network id of node.
     */
    @Override
    public String getNetworkId() {
        return nodeInstance.getNetworkId();
    }

    /**
     * @return Instance of node.
     */
    @Override
    public Node getNode() {
        return nodeInstance;
    }

    /**
     * @return Id of the node.
     */
    @Override
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
    @Override
    public PowerlinkObject getObject() {
        return object;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.epsg.openconfigurator.model.IPowerlinkBaseObject#getObjectType()
     */
    @Override
    public short getObjectType() {
        return objectType;
    }

    /**
     * @return Flag variable of POWERLINK sub-object from the given XDD/XDC
     *         file.
     */
    public byte[] getObjFlags() {
        return objFlags;
    }

    /**
     * @return PDO mapping of sub object.
     */
    @Override
    public TObjectPDOMapping getPdoMapping() {
        return pdoMapping;
    }

    /**
     * @return Instance of project.
     */
    @Override
    public IProject getProject() {
        return project;
    }

    /**
     * @return the size of the object based on the datatype.
     */
    public long getSize() {
        long size[] = new long[2];
        Result res = OpenConfiguratorCore.GetInstance().GetObjectSize(
                getNetworkId(), getNodeId(), object.getId(), size);
        if (!res.IsSuccessful()) {
            System.err.println("Error getting the Size "
                    + OpenConfiguratorLibraryUtils.getErrorMessage(res));
        } else {
            return size[0];
        }

        return 0;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.epsg.openconfigurator.model.IPowerlinkBaseObject#getUniqueIDRef()
     */
    @Override
    public Object getUniqueIDRef() {
        return uniqueIDRef;
    }

    /**
     * @return Returns the unique name of the object by adding the object name,
     *         id and sub-object name and id.
     */
    public String getUniqueName() {
        return object.getName() + "_" + getName() + "(" + object.getIdHex()
                + "/" + getIdHex() + ")";
    }

    /**
     * @return Xpath.
     */
    @Override
    public String getXpath() {
        return xpath;
    }

    /**
     * Checks for forced objects.
     *
     * @return <code>True</code> if object is forced. <code>False</code> if
     *         object is not forced
     */
    @Override
    public boolean isObjectForced() {
        return nodeInstance.isObjectIdForced(object.getIndex(), idByte);
    }

    /**
     * @return <code>True</code> if object is RPDO mappable. <code>False</code>
     *         if object is not.
     */
    @Override
    public boolean isRpdoMappable() {
        return isRpdoMappable;
    }

    /**
     * @return <code>True</code> if object is TPDO mappable. <code>False</code>
     *         if object is not.
     */
    @Override
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
    @Override
    public void setActualValue(final String actualValue, boolean writeToXdc)
            throws JDOMException, IOException {

        TObjectAccessType accessType = getAccessType();
        if (accessType != null) {
            if ((accessType == TObjectAccessType.RO)
                    || (accessType == TObjectAccessType.CONST)) {
                throw new RuntimeException("Restricted access to sub-object "
                        + "'" + getName() + "'" + " to set the actual value.");
            }
        }

        this.actualValue = actualValue;

        if (writeToXdc) {
            OpenConfiguratorProjectUtils.updateObjectAttributeActualValue(
                    getNode(), this, actualValue);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.epsg.openconfigurator.model.IPowerlinkBaseObject#setError(java.lang.
     * String)
     */
    @Override
    public void setError(String errorMessage) {
        configurationError = errorMessage;
    }
}

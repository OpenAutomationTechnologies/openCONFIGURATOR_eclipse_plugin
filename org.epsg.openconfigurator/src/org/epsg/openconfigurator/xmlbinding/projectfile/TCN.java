
package org.epsg.openconfigurator.xmlbinding.projectfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * A concrete type for a POWERLINK CN.
 *
 * <p>
 * Java class for tCN complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="tCN"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://sourceforge.net/projects/openconf/configuration}tAbstractNode"&gt;
 *       &lt;sequence&gt;
 *         &lt;element ref="{http://sourceforge.net/projects/openconf/configuration}InterfaceList" minOccurs="0"/&gt;
 *         &lt;element ref="{http://sourceforge.net/projects/openconf/configuration}FirmwareList" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" type="{http://ethernet-powerlink.org/POWERLINK}tNonEmptyString" default="POWERLINK CN" /&gt;
 *       &lt;attribute name="nodeID" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;union memberTypes=" {http://sourceforge.net/projects/openconf/configuration}tRegularCNNodeID"&gt;
 *           &lt;/union&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="pathToXDC" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyURI"&gt;
 *             &lt;minLength value="1"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="isMultiplexed" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="forcedMultiplexedCycle" type="{http://sourceforge.net/projects/openconf/configuration}tMultiplexedCycleLength" default="0" /&gt;
 *       &lt;attribute name="isChained" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="isMandatory" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="autostartNode" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
 *       &lt;attribute name="resetInOperational" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="verifyAppSwVersion" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="autoAppSwUpdateAllowed" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="verifyDeviceType" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
 *       &lt;attribute name="verifyVendorId" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="verifyRevisionNumber" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="verifyProductCode" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="verifySerialNumber" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="enabled" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tCN", propOrder = { "interfaceList", "firmwareList" })
public class TCN extends TAbstractNode {

    @XmlElement(name = "InterfaceList")
    protected InterfaceList interfaceList;
    @XmlElement(name = "FirmwareList")
    protected FirmwareList firmwareList;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "nodeID", required = true)
    protected String nodeID;
    @XmlAttribute(name = "pathToXDC", required = true)
    protected String pathToXDC;
    @XmlAttribute(name = "isMultiplexed")
    protected Boolean isMultiplexed;
    @XmlAttribute(name = "forcedMultiplexedCycle")
    protected Integer forcedMultiplexedCycle;
    @XmlAttribute(name = "isChained")
    protected Boolean isChained;
    @XmlAttribute(name = "isMandatory")
    protected Boolean isMandatory;
    @XmlAttribute(name = "autostartNode")
    protected Boolean autostartNode;
    @XmlAttribute(name = "resetInOperational")
    protected Boolean resetInOperational;
    @XmlAttribute(name = "verifyAppSwVersion")
    protected Boolean verifyAppSwVersion;
    @XmlAttribute(name = "autoAppSwUpdateAllowed")
    protected Boolean autoAppSwUpdateAllowed;
    @XmlAttribute(name = "verifyDeviceType")
    protected Boolean verifyDeviceType;
    @XmlAttribute(name = "verifyVendorId")
    protected Boolean verifyVendorId;
    @XmlAttribute(name = "verifyRevisionNumber")
    protected Boolean verifyRevisionNumber;
    @XmlAttribute(name = "verifyProductCode")
    protected Boolean verifyProductCode;
    @XmlAttribute(name = "verifySerialNumber")
    protected Boolean verifySerialNumber;
    @XmlAttribute(name = "enabled")
    protected Boolean enabled;

    /**
     * Gets the value of the firmwareList property.
     *
     * @return possible object is {@link FirmwareList }
     *
     */
    public FirmwareList getFirmwareList() {
        return firmwareList;
    }

    /**
     * Gets the value of the forcedMultiplexedCycle property.
     *
     * @return possible object is {@link Integer }
     *
     */
    public int getForcedMultiplexedCycle() {
        if (forcedMultiplexedCycle == null) {
            return 0;
        } else {
            return forcedMultiplexedCycle;
        }
    }

    /**
     * Gets the value of the interfaceList property.
     *
     * @return possible object is {@link InterfaceList }
     *
     */
    public InterfaceList getInterfaceList() {
        return interfaceList;
    }

    /**
     * Gets the value of the name property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getName() {
        if (name == null) {
            return "POWERLINK CN";
        } else {
            return name;
        }
    }

    /**
     * Gets the value of the nodeID property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getNodeID() {
        return nodeID;
    }

    /**
     * Gets the value of the pathToXDC property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getPathToXDC() {
        return pathToXDC;
    }

    /**
     * Gets the value of the autoAppSwUpdateAllowed property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isAutoAppSwUpdateAllowed() {
        if (autoAppSwUpdateAllowed == null) {
            return false;
        } else {
            return autoAppSwUpdateAllowed;
        }
    }

    /**
     * Gets the value of the autostartNode property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isAutostartNode() {
        if (autostartNode == null) {
            return true;
        } else {
            return autostartNode;
        }
    }

    /**
     * Gets the value of the enabled property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isEnabled() {
        if (enabled == null) {
            return true;
        } else {
            return enabled;
        }
    }

    /**
     * Gets the value of the isChained property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isIsChained() {
        if (isChained == null) {
            return false;
        } else {
            return isChained;
        }
    }

    /**
     * Gets the value of the isMandatory property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isIsMandatory() {
        if (isMandatory == null) {
            return false;
        } else {
            return isMandatory;
        }
    }

    /**
     * Gets the value of the isMultiplexed property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isIsMultiplexed() {
        if (isMultiplexed == null) {
            return false;
        } else {
            return isMultiplexed;
        }
    }

    /**
     * Gets the value of the resetInOperational property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isResetInOperational() {
        if (resetInOperational == null) {
            return false;
        } else {
            return resetInOperational;
        }
    }

    /**
     * Gets the value of the verifyAppSwVersion property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isVerifyAppSwVersion() {
        if (verifyAppSwVersion == null) {
            return false;
        } else {
            return verifyAppSwVersion;
        }
    }

    /**
     * Gets the value of the verifyDeviceType property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isVerifyDeviceType() {
        if (verifyDeviceType == null) {
            return true;
        } else {
            return verifyDeviceType;
        }
    }

    /**
     * Gets the value of the verifyProductCode property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isVerifyProductCode() {
        if (verifyProductCode == null) {
            return false;
        } else {
            return verifyProductCode;
        }
    }

    /**
     * Gets the value of the verifyRevisionNumber property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isVerifyRevisionNumber() {
        if (verifyRevisionNumber == null) {
            return false;
        } else {
            return verifyRevisionNumber;
        }
    }

    /**
     * Gets the value of the verifySerialNumber property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isVerifySerialNumber() {
        if (verifySerialNumber == null) {
            return false;
        } else {
            return verifySerialNumber;
        }
    }

    /**
     * Gets the value of the verifyVendorId property.
     *
     * @return possible object is {@link Boolean }
     *
     */
    public boolean isVerifyVendorId() {
        if (verifyVendorId == null) {
            return false;
        } else {
            return verifyVendorId;
        }
    }

    /**
     * Sets the value of the autoAppSwUpdateAllowed property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setAutoAppSwUpdateAllowed(Boolean value) {
        autoAppSwUpdateAllowed = value;
    }

    /**
     * Sets the value of the autostartNode property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setAutostartNode(Boolean value) {
        autostartNode = value;
    }

    /**
     * Sets the value of the enabled property.
     * 
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setEnabled(Boolean value) {
        enabled = value;
    }

    /**
     * Sets the value of the firmwareList property.
     *
     * @param value allowed object is {@link FirmwareList }
     *
     */
    public void setFirmwareList(FirmwareList value) {
        firmwareList = value;
    }

    /**
     * Sets the value of the forcedMultiplexedCycle property.
     *
     * @param value allowed object is {@link Integer }
     *
     */
    public void setForcedMultiplexedCycle(Integer value) {
        forcedMultiplexedCycle = value;
    }

    /**
     * Sets the value of the interfaceList property.
     *
     * @param value allowed object is {@link InterfaceList }
     *
     */
    public void setInterfaceList(InterfaceList value) {
        interfaceList = value;
    }

    /**
     * Sets the value of the isChained property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setIsChained(Boolean value) {
        isChained = value;
    }

    /**
     * Sets the value of the isMandatory property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setIsMandatory(Boolean value) {
        isMandatory = value;
    }

    /**
     * Sets the value of the isMultiplexed property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setIsMultiplexed(Boolean value) {
        isMultiplexed = value;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setName(String value) {
        name = value;
    }

    /**
     * Sets the value of the nodeID property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setNodeID(String value) {
        nodeID = value;
    }

    /**
     * Sets the value of the pathToXDC property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setPathToXDC(String value) {
        pathToXDC = value;
    }

    /**
     * Sets the value of the resetInOperational property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setResetInOperational(Boolean value) {
        resetInOperational = value;
    }

    /**
     * Sets the value of the verifyAppSwVersion property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setVerifyAppSwVersion(Boolean value) {
        verifyAppSwVersion = value;
    }

    /**
     * Sets the value of the verifyDeviceType property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setVerifyDeviceType(Boolean value) {
        verifyDeviceType = value;
    }

    /**
     * Sets the value of the verifyProductCode property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setVerifyProductCode(Boolean value) {
        verifyProductCode = value;
    }

    /**
     * Sets the value of the verifyRevisionNumber property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setVerifyRevisionNumber(Boolean value) {
        verifyRevisionNumber = value;
    }

    /**
     * Sets the value of the verifySerialNumber property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setVerifySerialNumber(Boolean value) {
        verifySerialNumber = value;
    }

    /**
     * Sets the value of the verifyVendorId property.
     *
     * @param value allowed object is {@link Boolean }
     *
     */
    public void setVerifyVendorId(Boolean value) {
        verifyVendorId = value;
    }

}

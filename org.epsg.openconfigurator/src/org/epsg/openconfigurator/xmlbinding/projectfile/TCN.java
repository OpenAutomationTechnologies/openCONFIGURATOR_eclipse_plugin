
package org.epsg.openconfigurator.xmlbinding.projectfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * A concrete type for a POWERLINK CN. 
 * 
 * <p>Java-Klasse für tCN complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="tCN"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://sourceforge.net/projects/openconf/configuration}tAbstractNode"&gt;
 *       &lt;attribute name="nodeID" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;union memberTypes=" {http://sourceforge.net/projects/openconf/configuration}tRegularCNNodeID {http://sourceforge.net/projects/openconf/configuration}tDefaultRedundantMNNodeID {http://sourceforge.net/projects/openconf/configuration}tDiagnosticNodeID"&gt;
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
 *       &lt;attribute name="resetInOperational" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
 *       &lt;attribute name="verifyAppSwVersion" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="autoAppSwUpdateAllowed" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="verifyDeviceType" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
 *       &lt;attribute name="verifyVendorId" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="verifyRevisionNumber" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="verifyProductCode" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="verifySerialNumber" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tCN")
public class TCN
    extends TAbstractNode
{

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

    /**
     * Ruft den Wert der nodeID-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNodeID() {
        return nodeID;
    }

    /**
     * Legt den Wert der nodeID-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNodeID(String value) {
        this.nodeID = value;
    }

    /**
     * Ruft den Wert der pathToXDC-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPathToXDC() {
        return pathToXDC;
    }

    /**
     * Legt den Wert der pathToXDC-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPathToXDC(String value) {
        this.pathToXDC = value;
    }

    /**
     * Ruft den Wert der isMultiplexed-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der isMultiplexed-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsMultiplexed(Boolean value) {
        this.isMultiplexed = value;
    }

    /**
     * Ruft den Wert der forcedMultiplexedCycle-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getForcedMultiplexedCycle() {
        if (forcedMultiplexedCycle == null) {
            return  0;
        } else {
            return forcedMultiplexedCycle;
        }
    }

    /**
     * Legt den Wert der forcedMultiplexedCycle-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setForcedMultiplexedCycle(Integer value) {
        this.forcedMultiplexedCycle = value;
    }

    /**
     * Ruft den Wert der isChained-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der isChained-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsChained(Boolean value) {
        this.isChained = value;
    }

    /**
     * Ruft den Wert der isMandatory-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der isMandatory-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsMandatory(Boolean value) {
        this.isMandatory = value;
    }

    /**
     * Ruft den Wert der autostartNode-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der autostartNode-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAutostartNode(Boolean value) {
        this.autostartNode = value;
    }

    /**
     * Ruft den Wert der resetInOperational-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isResetInOperational() {
        if (resetInOperational == null) {
            return true;
        } else {
            return resetInOperational;
        }
    }

    /**
     * Legt den Wert der resetInOperational-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setResetInOperational(Boolean value) {
        this.resetInOperational = value;
    }

    /**
     * Ruft den Wert der verifyAppSwVersion-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der verifyAppSwVersion-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVerifyAppSwVersion(Boolean value) {
        this.verifyAppSwVersion = value;
    }

    /**
     * Ruft den Wert der autoAppSwUpdateAllowed-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der autoAppSwUpdateAllowed-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAutoAppSwUpdateAllowed(Boolean value) {
        this.autoAppSwUpdateAllowed = value;
    }

    /**
     * Ruft den Wert der verifyDeviceType-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der verifyDeviceType-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVerifyDeviceType(Boolean value) {
        this.verifyDeviceType = value;
    }

    /**
     * Ruft den Wert der verifyVendorId-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der verifyVendorId-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVerifyVendorId(Boolean value) {
        this.verifyVendorId = value;
    }

    /**
     * Ruft den Wert der verifyRevisionNumber-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der verifyRevisionNumber-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVerifyRevisionNumber(Boolean value) {
        this.verifyRevisionNumber = value;
    }

    /**
     * Ruft den Wert der verifyProductCode-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der verifyProductCode-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVerifyProductCode(Boolean value) {
        this.verifyProductCode = value;
    }

    /**
     * Ruft den Wert der verifySerialNumber-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
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
     * Legt den Wert der verifySerialNumber-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setVerifySerialNumber(Boolean value) {
        this.verifySerialNumber = value;
    }

}

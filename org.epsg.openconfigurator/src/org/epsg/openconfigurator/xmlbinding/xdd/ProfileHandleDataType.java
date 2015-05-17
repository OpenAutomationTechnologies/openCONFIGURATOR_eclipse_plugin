
package org.epsg.openconfigurator.xmlbinding.xdd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProfileHandle_DataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProfileHandle_DataType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ProfileIdentification" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ProfileRevision" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ProfileLocation" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProfileHandle_DataType", propOrder = {
    "profileIdentification",
    "profileRevision",
    "profileLocation"
})
public class ProfileHandleDataType {

    @XmlElement(name = "ProfileIdentification", required = true)
    protected String profileIdentification;
    @XmlElement(name = "ProfileRevision", required = true)
    protected String profileRevision;
    @XmlElement(name = "ProfileLocation")
    @XmlSchemaType(name = "anyURI")
    protected String profileLocation;

    /**
     * Gets the value of the profileIdentification property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProfileIdentification() {
        return profileIdentification;
    }

    /**
     * Sets the value of the profileIdentification property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProfileIdentification(String value) {
        this.profileIdentification = value;
    }

    /**
     * Gets the value of the profileRevision property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProfileRevision() {
        return profileRevision;
    }

    /**
     * Sets the value of the profileRevision property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProfileRevision(String value) {
        this.profileRevision = value;
    }

    /**
     * Gets the value of the profileLocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProfileLocation() {
        return profileLocation;
    }

    /**
     * Sets the value of the profileLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProfileLocation(String value) {
        this.profileLocation = value;
    }

}

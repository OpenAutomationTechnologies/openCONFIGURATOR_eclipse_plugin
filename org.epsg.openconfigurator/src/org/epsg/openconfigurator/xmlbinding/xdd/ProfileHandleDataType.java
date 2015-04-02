
package org.epsg.openconfigurator.xmlbinding.xdd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für ProfileHandle_DataType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
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
     * Ruft den Wert der profileIdentification-Eigenschaft ab.
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
     * Legt den Wert der profileIdentification-Eigenschaft fest.
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
     * Ruft den Wert der profileRevision-Eigenschaft ab.
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
     * Legt den Wert der profileRevision-Eigenschaft fest.
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
     * Ruft den Wert der profileLocation-Eigenschaft ab.
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
     * Legt den Wert der profileLocation-Eigenschaft fest.
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

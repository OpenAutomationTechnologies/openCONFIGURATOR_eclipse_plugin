
package org.epsg.openconfigurator.xmlbinding.xdd;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ISO15745Reference_DataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ISO15745Reference_DataType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ISO15745Part" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/&gt;
 *         &lt;element name="ISO15745Edition" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/&gt;
 *         &lt;element name="ProfileTechnology" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ISO15745Reference_DataType", propOrder = {
    "iso15745Part",
    "iso15745Edition",
    "profileTechnology"
})
public class ISO15745ReferenceDataType {

    @XmlElement(name = "ISO15745Part", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger iso15745Part;
    @XmlElement(name = "ISO15745Edition", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger iso15745Edition;
    @XmlElement(name = "ProfileTechnology", required = true)
    protected String profileTechnology;

    /**
     * Gets the value of the iso15745Part property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getISO15745Part() {
        return iso15745Part;
    }

    /**
     * Sets the value of the iso15745Part property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setISO15745Part(BigInteger value) {
        this.iso15745Part = value;
    }

    /**
     * Gets the value of the iso15745Edition property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getISO15745Edition() {
        return iso15745Edition;
    }

    /**
     * Sets the value of the iso15745Edition property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setISO15745Edition(BigInteger value) {
        this.iso15745Edition = value;
    }

    /**
     * Gets the value of the profileTechnology property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProfileTechnology() {
        return profileTechnology;
    }

    /**
     * Sets the value of the profileTechnology property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProfileTechnology(String value) {
        this.profileTechnology = value;
    }

}

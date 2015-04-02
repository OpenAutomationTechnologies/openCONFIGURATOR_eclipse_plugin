
package org.epsg.openconfigurator.xmlbinding.xdd;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für ISO15745Reference_DataType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
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
     * Ruft den Wert der iso15745Part-Eigenschaft ab.
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
     * Legt den Wert der iso15745Part-Eigenschaft fest.
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
     * Ruft den Wert der iso15745Edition-Eigenschaft ab.
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
     * Legt den Wert der iso15745Edition-Eigenschaft fest.
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
     * Ruft den Wert der profileTechnology-Eigenschaft ab.
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
     * Legt den Wert der profileTechnology-Eigenschaft fest.
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

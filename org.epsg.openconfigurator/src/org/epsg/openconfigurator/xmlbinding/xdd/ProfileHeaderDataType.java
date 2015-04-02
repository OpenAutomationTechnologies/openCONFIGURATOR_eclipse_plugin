
package org.epsg.openconfigurator.xmlbinding.xdd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java-Klasse für ProfileHeader_DataType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="ProfileHeader_DataType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ProfileIdentification" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ProfileRevision" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ProfileName" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ProfileSource" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ProfileClassID" type="{http://www.ethernet-powerlink.org}ProfileClassID_DataType"/&gt;
 *         &lt;element name="ProfileDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="AdditionalInformation" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/&gt;
 *         &lt;element name="ISO15745Reference" type="{http://www.ethernet-powerlink.org}ISO15745Reference_DataType"/&gt;
 *         &lt;element name="IASInterfaceType" type="{http://www.ethernet-powerlink.org}IASInterface_DataType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProfileHeader_DataType", propOrder = {
    "profileIdentification",
    "profileRevision",
    "profileName",
    "profileSource",
    "profileClassID",
    "profileDate",
    "additionalInformation",
    "iso15745Reference",
    "iasInterfaceType"
})
public class ProfileHeaderDataType {

    @XmlElement(name = "ProfileIdentification", required = true)
    protected String profileIdentification;
    @XmlElement(name = "ProfileRevision", required = true)
    protected String profileRevision;
    @XmlElement(name = "ProfileName", required = true)
    protected String profileName;
    @XmlElement(name = "ProfileSource", required = true)
    protected String profileSource;
    @XmlElement(name = "ProfileClassID", required = true)
    @XmlSchemaType(name = "string")
    protected ProfileClassIDDataType profileClassID;
    @XmlElement(name = "ProfileDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar profileDate;
    @XmlElement(name = "AdditionalInformation")
    @XmlSchemaType(name = "anyURI")
    protected String additionalInformation;
    @XmlElement(name = "ISO15745Reference", required = true)
    protected ISO15745ReferenceDataType iso15745Reference;
    @XmlElement(name = "IASInterfaceType")
    @XmlSchemaType(name = "anySimpleType")
    protected List<String> iasInterfaceType;

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
     * Ruft den Wert der profileName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProfileName() {
        return profileName;
    }

    /**
     * Legt den Wert der profileName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProfileName(String value) {
        this.profileName = value;
    }

    /**
     * Ruft den Wert der profileSource-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProfileSource() {
        return profileSource;
    }

    /**
     * Legt den Wert der profileSource-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProfileSource(String value) {
        this.profileSource = value;
    }

    /**
     * Ruft den Wert der profileClassID-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ProfileClassIDDataType }
     *     
     */
    public ProfileClassIDDataType getProfileClassID() {
        return profileClassID;
    }

    /**
     * Legt den Wert der profileClassID-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ProfileClassIDDataType }
     *     
     */
    public void setProfileClassID(ProfileClassIDDataType value) {
        this.profileClassID = value;
    }

    /**
     * Ruft den Wert der profileDate-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getProfileDate() {
        return profileDate;
    }

    /**
     * Legt den Wert der profileDate-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setProfileDate(XMLGregorianCalendar value) {
        this.profileDate = value;
    }

    /**
     * Ruft den Wert der additionalInformation-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalInformation() {
        return additionalInformation;
    }

    /**
     * Legt den Wert der additionalInformation-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalInformation(String value) {
        this.additionalInformation = value;
    }

    /**
     * Ruft den Wert der iso15745Reference-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link ISO15745ReferenceDataType }
     *     
     */
    public ISO15745ReferenceDataType getISO15745Reference() {
        return iso15745Reference;
    }

    /**
     * Legt den Wert der iso15745Reference-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link ISO15745ReferenceDataType }
     *     
     */
    public void setISO15745Reference(ISO15745ReferenceDataType value) {
        this.iso15745Reference = value;
    }

    /**
     * Gets the value of the iasInterfaceType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the iasInterfaceType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getIASInterfaceType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getIASInterfaceType() {
        if (iasInterfaceType == null) {
            iasInterfaceType = new ArrayList<String>();
        }
        return this.iasInterfaceType;
    }

}

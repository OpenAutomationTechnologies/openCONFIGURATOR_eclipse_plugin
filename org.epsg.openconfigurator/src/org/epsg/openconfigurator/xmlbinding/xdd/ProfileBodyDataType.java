
package org.epsg.openconfigurator.xmlbinding.xdd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java-Klasse für ProfileBody_DataType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="ProfileBody_DataType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="formatName" type="{http://www.w3.org/2001/XMLSchema}string" fixed="DDXML" /&gt;
 *       &lt;attribute name="formatVersion" type="{http://www.w3.org/2001/XMLSchema}string" fixed="2.0" /&gt;
 *       &lt;attribute name="fileName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="fileCreator" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="fileCreationDate" use="required" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
 *       &lt;attribute name="fileCreationTime" type="{http://www.w3.org/2001/XMLSchema}time" /&gt;
 *       &lt;attribute name="fileModificationDate" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
 *       &lt;attribute name="fileModificationTime" type="{http://www.w3.org/2001/XMLSchema}time" /&gt;
 *       &lt;attribute name="fileModifiedBy" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="fileVersion" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="supportedLanguages"&gt;
 *         &lt;simpleType&gt;
 *           &lt;list itemType="{http://www.w3.org/2001/XMLSchema}language" /&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProfileBody_DataType")
@XmlSeeAlso({
    ProfileBodyDevicePowerlink.class,
    ProfileBodyCommunicationNetworkPowerlink.class
})
public abstract class ProfileBodyDataType {

    @XmlAttribute(name = "formatName")
    protected String formatName;
    @XmlAttribute(name = "formatVersion")
    protected String formatVersion;
    @XmlAttribute(name = "fileName", required = true)
    protected String fileName;
    @XmlAttribute(name = "fileCreator", required = true)
    protected String fileCreator;
    @XmlAttribute(name = "fileCreationDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fileCreationDate;
    @XmlAttribute(name = "fileCreationTime")
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar fileCreationTime;
    @XmlAttribute(name = "fileModificationDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fileModificationDate;
    @XmlAttribute(name = "fileModificationTime")
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar fileModificationTime;
    @XmlAttribute(name = "fileModifiedBy")
    protected String fileModifiedBy;
    @XmlAttribute(name = "fileVersion", required = true)
    protected String fileVersion;
    @XmlAttribute(name = "supportedLanguages")
    protected List<String> supportedLanguages;

    /**
     * Ruft den Wert der formatName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormatName() {
        if (formatName == null) {
            return "DDXML";
        } else {
            return formatName;
        }
    }

    /**
     * Legt den Wert der formatName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormatName(String value) {
        this.formatName = value;
    }

    /**
     * Ruft den Wert der formatVersion-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormatVersion() {
        if (formatVersion == null) {
            return "2.0";
        } else {
            return formatVersion;
        }
    }

    /**
     * Legt den Wert der formatVersion-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormatVersion(String value) {
        this.formatVersion = value;
    }

    /**
     * Ruft den Wert der fileName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Legt den Wert der fileName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileName(String value) {
        this.fileName = value;
    }

    /**
     * Ruft den Wert der fileCreator-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileCreator() {
        return fileCreator;
    }

    /**
     * Legt den Wert der fileCreator-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileCreator(String value) {
        this.fileCreator = value;
    }

    /**
     * Ruft den Wert der fileCreationDate-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFileCreationDate() {
        return fileCreationDate;
    }

    /**
     * Legt den Wert der fileCreationDate-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFileCreationDate(XMLGregorianCalendar value) {
        this.fileCreationDate = value;
    }

    /**
     * Ruft den Wert der fileCreationTime-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFileCreationTime() {
        return fileCreationTime;
    }

    /**
     * Legt den Wert der fileCreationTime-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFileCreationTime(XMLGregorianCalendar value) {
        this.fileCreationTime = value;
    }

    /**
     * Ruft den Wert der fileModificationDate-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFileModificationDate() {
        return fileModificationDate;
    }

    /**
     * Legt den Wert der fileModificationDate-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFileModificationDate(XMLGregorianCalendar value) {
        this.fileModificationDate = value;
    }

    /**
     * Ruft den Wert der fileModificationTime-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFileModificationTime() {
        return fileModificationTime;
    }

    /**
     * Legt den Wert der fileModificationTime-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFileModificationTime(XMLGregorianCalendar value) {
        this.fileModificationTime = value;
    }

    /**
     * Ruft den Wert der fileModifiedBy-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileModifiedBy() {
        return fileModifiedBy;
    }

    /**
     * Legt den Wert der fileModifiedBy-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileModifiedBy(String value) {
        this.fileModifiedBy = value;
    }

    /**
     * Ruft den Wert der fileVersion-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileVersion() {
        return fileVersion;
    }

    /**
     * Legt den Wert der fileVersion-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileVersion(String value) {
        this.fileVersion = value;
    }

    /**
     * Gets the value of the supportedLanguages property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the supportedLanguages property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSupportedLanguages().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getSupportedLanguages() {
        if (supportedLanguages == null) {
            supportedLanguages = new ArrayList<String>();
        }
        return this.supportedLanguages;
    }

}

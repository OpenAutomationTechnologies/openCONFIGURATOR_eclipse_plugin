
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
 * <p>Java class for ProfileBody_DataType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
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
 *       &lt;attribute name="fileModifiedBy" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="fileModificationDate" type="{http://www.w3.org/2001/XMLSchema}date" /&gt;
 *       &lt;attribute name="fileModificationTime" type="{http://www.w3.org/2001/XMLSchema}time" /&gt;
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
    ProfileBodyCommunicationNetworkPowerlink.class,
    ProfileBodyDevicePowerlinkModularHead.class,
    ProfileBodyDevicePowerlinkModularChild.class,
    ProfileBodyCommunicationNetworkPowerlinkModularHead.class,
    ProfileBodyCommunicationNetworkPowerlinkModularChild.class
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
    @XmlAttribute(name = "fileModifiedBy")
    protected String fileModifiedBy;
    @XmlAttribute(name = "fileModificationDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar fileModificationDate;
    @XmlAttribute(name = "fileModificationTime")
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar fileModificationTime;
    @XmlAttribute(name = "fileVersion", required = true)
    protected String fileVersion;
    @XmlAttribute(name = "supportedLanguages")
    protected List<String> supportedLanguages;

    /**
     * Gets the value of the formatName property.
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
     * Sets the value of the formatName property.
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
     * Gets the value of the formatVersion property.
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
     * Sets the value of the formatVersion property.
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
     * Gets the value of the fileName property.
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
     * Sets the value of the fileName property.
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
     * Gets the value of the fileCreator property.
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
     * Sets the value of the fileCreator property.
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
     * Gets the value of the fileCreationDate property.
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
     * Sets the value of the fileCreationDate property.
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
     * Gets the value of the fileCreationTime property.
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
     * Sets the value of the fileCreationTime property.
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
     * Gets the value of the fileModifiedBy property.
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
     * Sets the value of the fileModifiedBy property.
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
     * Gets the value of the fileModificationDate property.
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
     * Sets the value of the fileModificationDate property.
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
     * Gets the value of the fileModificationTime property.
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
     * Sets the value of the fileModificationTime property.
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
     * Gets the value of the fileVersion property.
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
     * Sets the value of the fileVersion property.
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

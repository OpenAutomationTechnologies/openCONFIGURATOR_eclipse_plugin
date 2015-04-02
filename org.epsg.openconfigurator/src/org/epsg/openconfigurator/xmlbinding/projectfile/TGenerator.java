
package org.epsg.openconfigurator.xmlbinding.projectfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Specifies information about the tool that generated this
 *                 project-file.
 * 
 * <p>Java-Klasse für tGenerator complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="tGenerator"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="vendor" use="required" type="{http://ethernet-powerlink.org/POWERLINK}tNonEmptyString" /&gt;
 *       &lt;attribute name="toolName" use="required" type="{http://ethernet-powerlink.org/POWERLINK}tNonEmptyString" /&gt;
 *       &lt;attribute name="toolVersion" use="required" type="{http://ethernet-powerlink.org/POWERLINK}tNonEmptyString" /&gt;
 *       &lt;attribute name="createdBy" type="{http://ethernet-powerlink.org/POWERLINK}tNonEmptyString" /&gt;
 *       &lt;attribute name="createdOn" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" /&gt;
 *       &lt;attribute name="modifiedBy" type="{http://ethernet-powerlink.org/POWERLINK}tNonEmptyString" /&gt;
 *       &lt;attribute name="modifiedOn" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tGenerator")
public class TGenerator {

    @XmlAttribute(name = "vendor", required = true)
    protected String vendor;
    @XmlAttribute(name = "toolName", required = true)
    protected String toolName;
    @XmlAttribute(name = "toolVersion", required = true)
    protected String toolVersion;
    @XmlAttribute(name = "createdBy")
    protected String createdBy;
    @XmlAttribute(name = "createdOn", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar createdOn;
    @XmlAttribute(name = "modifiedBy")
    protected String modifiedBy;
    @XmlAttribute(name = "modifiedOn", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar modifiedOn;

    /**
     * Ruft den Wert der vendor-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVendor() {
        return vendor;
    }

    /**
     * Legt den Wert der vendor-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVendor(String value) {
        this.vendor = value;
    }

    /**
     * Ruft den Wert der toolName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToolName() {
        return toolName;
    }

    /**
     * Legt den Wert der toolName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToolName(String value) {
        this.toolName = value;
    }

    /**
     * Ruft den Wert der toolVersion-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToolVersion() {
        return toolVersion;
    }

    /**
     * Legt den Wert der toolVersion-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToolVersion(String value) {
        this.toolVersion = value;
    }

    /**
     * Ruft den Wert der createdBy-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Legt den Wert der createdBy-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreatedBy(String value) {
        this.createdBy = value;
    }

    /**
     * Ruft den Wert der createdOn-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreatedOn() {
        return createdOn;
    }

    /**
     * Legt den Wert der createdOn-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCreatedOn(XMLGregorianCalendar value) {
        this.createdOn = value;
    }

    /**
     * Ruft den Wert der modifiedBy-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModifiedBy() {
        return modifiedBy;
    }

    /**
     * Legt den Wert der modifiedBy-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModifiedBy(String value) {
        this.modifiedBy = value;
    }

    /**
     * Ruft den Wert der modifiedOn-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getModifiedOn() {
        return modifiedOn;
    }

    /**
     * Legt den Wert der modifiedOn-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setModifiedOn(XMLGregorianCalendar value) {
        this.modifiedOn = value;
    }

}


package org.epsg.openconfigurator.xmlbinding.xdd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java-Klasse für t_count complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="t_count"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;group ref="{http://www.ethernet-powerlink.org}g_labels" minOccurs="0"/&gt;
 *         &lt;element name="defaultValue" type="{http://www.ethernet-powerlink.org}t_value"/&gt;
 *         &lt;element name="allowedValues" type="{http://www.ethernet-powerlink.org}t_allowedValues" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="uniqueID" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *       &lt;attribute name="access" default="read"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *             &lt;enumeration value="const"/&gt;
 *             &lt;enumeration value="read"/&gt;
 *             &lt;enumeration value="write"/&gt;
 *             &lt;enumeration value="readWrite"/&gt;
 *             &lt;enumeration value="noAccess"/&gt;
 *           &lt;/restriction&gt;
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
@XmlType(name = "t_count", propOrder = {
    "labelOrDescriptionOrLabelRef",
    "defaultValue",
    "allowedValues"
})
public class TCount {

    @XmlElements({
        @XmlElement(name = "label", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Label.class),
        @XmlElement(name = "description", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Description.class),
        @XmlElement(name = "labelRef", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.LabelRef.class),
        @XmlElement(name = "descriptionRef", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.DescriptionRef.class)
    })
    protected List<Object> labelOrDescriptionOrLabelRef;
    @XmlElement(required = true)
    protected TValue defaultValue;
    protected TAllowedValues allowedValues;
    @XmlAttribute(name = "uniqueID", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String uniqueID;
    @XmlAttribute(name = "access")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String access;

    /**
     * Gets the value of the labelOrDescriptionOrLabelRef property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the labelOrDescriptionOrLabelRef property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLabelOrDescriptionOrLabelRef().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Label }
     * {@link org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Description }
     * {@link org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.LabelRef }
     * {@link org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.DescriptionRef }
     * 
     * 
     */
    public List<Object> getLabelOrDescriptionOrLabelRef() {
        if (labelOrDescriptionOrLabelRef == null) {
            labelOrDescriptionOrLabelRef = new ArrayList<Object>();
        }
        return this.labelOrDescriptionOrLabelRef;
    }

    /**
     * Ruft den Wert der defaultValue-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TValue }
     *     
     */
    public TValue getDefaultValue() {
        return defaultValue;
    }

    /**
     * Legt den Wert der defaultValue-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TValue }
     *     
     */
    public void setDefaultValue(TValue value) {
        this.defaultValue = value;
    }

    /**
     * Ruft den Wert der allowedValues-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TAllowedValues }
     *     
     */
    public TAllowedValues getAllowedValues() {
        return allowedValues;
    }

    /**
     * Legt den Wert der allowedValues-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TAllowedValues }
     *     
     */
    public void setAllowedValues(TAllowedValues value) {
        this.allowedValues = value;
    }

    /**
     * Ruft den Wert der uniqueID-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniqueID() {
        return uniqueID;
    }

    /**
     * Legt den Wert der uniqueID-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniqueID(String value) {
        this.uniqueID = value;
    }

    /**
     * Ruft den Wert der access-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccess() {
        if (access == null) {
            return "read";
        } else {
            return access;
        }
    }

    /**
     * Legt den Wert der access-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccess(String value) {
        this.access = value;
    }

}

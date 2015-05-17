
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
 * <p>Java class for t_varDeclaration complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_varDeclaration"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;group ref="{http://www.ethernet-powerlink.org}g_labels" minOccurs="0"/&gt;
 *         &lt;choice&gt;
 *           &lt;group ref="{http://www.ethernet-powerlink.org}g_simple"/&gt;
 *           &lt;element name="dataTypeIDRef" type="{http://www.ethernet-powerlink.org}t_dataTypeIDRef"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="uniqueID" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *       &lt;attribute name="size" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="initialValue" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_varDeclaration", propOrder = {
    "labelOrDescriptionOrLabelRef",
    "bool",
    "bitstring",
    "_byte",
    "_char",
    "word",
    "dword",
    "lword",
    "sint",
    "_int",
    "dint",
    "lint",
    "usint",
    "uint",
    "udint",
    "ulint",
    "real",
    "lreal",
    "string",
    "wstring",
    "dataTypeIDRef"
})
public class TVarDeclaration {

    @XmlElements({
        @XmlElement(name = "label", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Label.class),
        @XmlElement(name = "description", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Description.class),
        @XmlElement(name = "labelRef", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.LabelRef.class),
        @XmlElement(name = "descriptionRef", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.DescriptionRef.class)
    })
    protected List<Object> labelOrDescriptionOrLabelRef;
    @XmlElement(name = "BOOL")
    protected Object bool;
    @XmlElement(name = "BITSTRING")
    protected Object bitstring;
    @XmlElement(name = "BYTE")
    protected Object _byte;
    @XmlElement(name = "CHAR")
    protected Object _char;
    @XmlElement(name = "WORD")
    protected Object word;
    @XmlElement(name = "DWORD")
    protected Object dword;
    @XmlElement(name = "LWORD")
    protected Object lword;
    @XmlElement(name = "SINT")
    protected Object sint;
    @XmlElement(name = "INT")
    protected Object _int;
    @XmlElement(name = "DINT")
    protected Object dint;
    @XmlElement(name = "LINT")
    protected Object lint;
    @XmlElement(name = "USINT")
    protected Object usint;
    @XmlElement(name = "UINT")
    protected Object uint;
    @XmlElement(name = "UDINT")
    protected Object udint;
    @XmlElement(name = "ULINT")
    protected Object ulint;
    @XmlElement(name = "REAL")
    protected Object real;
    @XmlElement(name = "LREAL")
    protected Object lreal;
    @XmlElement(name = "STRING")
    protected Object string;
    @XmlElement(name = "WSTRING")
    protected Object wstring;
    protected TDataTypeIDRef dataTypeIDRef;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "uniqueID", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String uniqueID;
    @XmlAttribute(name = "size")
    protected String size;
    @XmlAttribute(name = "initialValue")
    protected String initialValue;

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
     * Gets the value of the bool property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getBOOL() {
        return bool;
    }

    /**
     * Sets the value of the bool property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setBOOL(Object value) {
        this.bool = value;
    }

    /**
     * Gets the value of the bitstring property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getBITSTRING() {
        return bitstring;
    }

    /**
     * Sets the value of the bitstring property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setBITSTRING(Object value) {
        this.bitstring = value;
    }

    /**
     * Gets the value of the byte property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getBYTE() {
        return _byte;
    }

    /**
     * Sets the value of the byte property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setBYTE(Object value) {
        this._byte = value;
    }

    /**
     * Gets the value of the char property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getCHAR() {
        return _char;
    }

    /**
     * Sets the value of the char property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setCHAR(Object value) {
        this._char = value;
    }

    /**
     * Gets the value of the word property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getWORD() {
        return word;
    }

    /**
     * Sets the value of the word property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setWORD(Object value) {
        this.word = value;
    }

    /**
     * Gets the value of the dword property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getDWORD() {
        return dword;
    }

    /**
     * Sets the value of the dword property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setDWORD(Object value) {
        this.dword = value;
    }

    /**
     * Gets the value of the lword property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getLWORD() {
        return lword;
    }

    /**
     * Sets the value of the lword property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setLWORD(Object value) {
        this.lword = value;
    }

    /**
     * Gets the value of the sint property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getSINT() {
        return sint;
    }

    /**
     * Sets the value of the sint property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setSINT(Object value) {
        this.sint = value;
    }

    /**
     * Gets the value of the int property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getINT() {
        return _int;
    }

    /**
     * Sets the value of the int property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setINT(Object value) {
        this._int = value;
    }

    /**
     * Gets the value of the dint property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getDINT() {
        return dint;
    }

    /**
     * Sets the value of the dint property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setDINT(Object value) {
        this.dint = value;
    }

    /**
     * Gets the value of the lint property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getLINT() {
        return lint;
    }

    /**
     * Sets the value of the lint property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setLINT(Object value) {
        this.lint = value;
    }

    /**
     * Gets the value of the usint property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUSINT() {
        return usint;
    }

    /**
     * Sets the value of the usint property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUSINT(Object value) {
        this.usint = value;
    }

    /**
     * Gets the value of the uint property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUINT() {
        return uint;
    }

    /**
     * Sets the value of the uint property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUINT(Object value) {
        this.uint = value;
    }

    /**
     * Gets the value of the udint property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUDINT() {
        return udint;
    }

    /**
     * Sets the value of the udint property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUDINT(Object value) {
        this.udint = value;
    }

    /**
     * Gets the value of the ulint property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getULINT() {
        return ulint;
    }

    /**
     * Sets the value of the ulint property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setULINT(Object value) {
        this.ulint = value;
    }

    /**
     * Gets the value of the real property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getREAL() {
        return real;
    }

    /**
     * Sets the value of the real property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setREAL(Object value) {
        this.real = value;
    }

    /**
     * Gets the value of the lreal property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getLREAL() {
        return lreal;
    }

    /**
     * Sets the value of the lreal property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setLREAL(Object value) {
        this.lreal = value;
    }

    /**
     * Gets the value of the string property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getSTRING() {
        return string;
    }

    /**
     * Sets the value of the string property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setSTRING(Object value) {
        this.string = value;
    }

    /**
     * Gets the value of the wstring property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getWSTRING() {
        return wstring;
    }

    /**
     * Sets the value of the wstring property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setWSTRING(Object value) {
        this.wstring = value;
    }

    /**
     * Gets the value of the dataTypeIDRef property.
     * 
     * @return
     *     possible object is
     *     {@link TDataTypeIDRef }
     *     
     */
    public TDataTypeIDRef getDataTypeIDRef() {
        return dataTypeIDRef;
    }

    /**
     * Sets the value of the dataTypeIDRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link TDataTypeIDRef }
     *     
     */
    public void setDataTypeIDRef(TDataTypeIDRef value) {
        this.dataTypeIDRef = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the uniqueID property.
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
     * Sets the value of the uniqueID property.
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
     * Gets the value of the size property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSize() {
        return size;
    }

    /**
     * Sets the value of the size property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSize(String value) {
        this.size = value;
    }

    /**
     * Gets the value of the initialValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInitialValue() {
        return initialValue;
    }

    /**
     * Sets the value of the initialValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInitialValue(String value) {
        this.initialValue = value;
    }

}


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
 * <p>Java-Klasse für t_dataTypeList complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="t_dataTypeList"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice maxOccurs="unbounded"&gt;
 *           &lt;element name="array"&gt;
 *             &lt;complexType&gt;
 *               &lt;complexContent&gt;
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                   &lt;sequence&gt;
 *                     &lt;group ref="{http://www.ethernet-powerlink.org}g_labels" minOccurs="0"/&gt;
 *                     &lt;element name="subrange" type="{http://www.ethernet-powerlink.org}t_subrange" maxOccurs="unbounded"/&gt;
 *                     &lt;choice&gt;
 *                       &lt;group ref="{http://www.ethernet-powerlink.org}g_simple"/&gt;
 *                       &lt;element name="dataTypeIDRef" type="{http://www.ethernet-powerlink.org}t_dataTypeIDRef"/&gt;
 *                     &lt;/choice&gt;
 *                   &lt;/sequence&gt;
 *                   &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                   &lt;attribute name="uniqueID" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *                 &lt;/restriction&gt;
 *               &lt;/complexContent&gt;
 *             &lt;/complexType&gt;
 *           &lt;/element&gt;
 *           &lt;element name="struct"&gt;
 *             &lt;complexType&gt;
 *               &lt;complexContent&gt;
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                   &lt;sequence&gt;
 *                     &lt;group ref="{http://www.ethernet-powerlink.org}g_labels" minOccurs="0"/&gt;
 *                     &lt;element name="varDeclaration" type="{http://www.ethernet-powerlink.org}t_varDeclaration" maxOccurs="unbounded"/&gt;
 *                   &lt;/sequence&gt;
 *                   &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                   &lt;attribute name="uniqueID" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *                 &lt;/restriction&gt;
 *               &lt;/complexContent&gt;
 *             &lt;/complexType&gt;
 *           &lt;/element&gt;
 *           &lt;element name="enum"&gt;
 *             &lt;complexType&gt;
 *               &lt;complexContent&gt;
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                   &lt;sequence&gt;
 *                     &lt;group ref="{http://www.ethernet-powerlink.org}g_labels" minOccurs="0"/&gt;
 *                     &lt;element name="enumValue" type="{http://www.ethernet-powerlink.org}t_enumValue" maxOccurs="unbounded"/&gt;
 *                     &lt;group ref="{http://www.ethernet-powerlink.org}g_simple" minOccurs="0"/&gt;
 *                   &lt;/sequence&gt;
 *                   &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                   &lt;attribute name="uniqueID" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *                   &lt;attribute name="size" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;/restriction&gt;
 *               &lt;/complexContent&gt;
 *             &lt;/complexType&gt;
 *           &lt;/element&gt;
 *           &lt;element name="derived"&gt;
 *             &lt;complexType&gt;
 *               &lt;complexContent&gt;
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                   &lt;sequence&gt;
 *                     &lt;group ref="{http://www.ethernet-powerlink.org}g_labels" minOccurs="0"/&gt;
 *                     &lt;element name="count" type="{http://www.ethernet-powerlink.org}t_count" minOccurs="0"/&gt;
 *                     &lt;choice&gt;
 *                       &lt;group ref="{http://www.ethernet-powerlink.org}g_simple"/&gt;
 *                       &lt;element name="dataTypeIDRef" type="{http://www.ethernet-powerlink.org}t_dataTypeIDRef"/&gt;
 *                     &lt;/choice&gt;
 *                   &lt;/sequence&gt;
 *                   &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                   &lt;attribute name="uniqueID" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *                   &lt;attribute name="description" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                 &lt;/restriction&gt;
 *               &lt;/complexContent&gt;
 *             &lt;/complexType&gt;
 *           &lt;/element&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_dataTypeList", propOrder = {
    "arrayOrStructOrEnum"
})
public class TDataTypeList {

    @XmlElements({
        @XmlElement(name = "array", type = TDataTypeList.Array.class),
        @XmlElement(name = "struct", type = TDataTypeList.Struct.class),
        @XmlElement(name = "enum", type = TDataTypeList.Enum.class),
        @XmlElement(name = "derived", type = TDataTypeList.Derived.class)
    })
    protected List<Object> arrayOrStructOrEnum;

    /**
     * Gets the value of the arrayOrStructOrEnum property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the arrayOrStructOrEnum property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getArrayOrStructOrEnum().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TDataTypeList.Array }
     * {@link TDataTypeList.Struct }
     * {@link TDataTypeList.Enum }
     * {@link TDataTypeList.Derived }
     * 
     * 
     */
    public List<Object> getArrayOrStructOrEnum() {
        if (arrayOrStructOrEnum == null) {
            arrayOrStructOrEnum = new ArrayList<Object>();
        }
        return this.arrayOrStructOrEnum;
    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;group ref="{http://www.ethernet-powerlink.org}g_labels" minOccurs="0"/&gt;
     *         &lt;element name="subrange" type="{http://www.ethernet-powerlink.org}t_subrange" maxOccurs="unbounded"/&gt;
     *         &lt;choice&gt;
     *           &lt;group ref="{http://www.ethernet-powerlink.org}g_simple"/&gt;
     *           &lt;element name="dataTypeIDRef" type="{http://www.ethernet-powerlink.org}t_dataTypeIDRef"/&gt;
     *         &lt;/choice&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="uniqueID" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "labelOrDescriptionOrLabelRef",
        "subrange",
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
    public static class Array {

        @XmlElements({
            @XmlElement(name = "label", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Label.class),
            @XmlElement(name = "description", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Description.class),
            @XmlElement(name = "labelRef", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.LabelRef.class),
            @XmlElement(name = "descriptionRef", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.DescriptionRef.class)
        })
        protected List<Object> labelOrDescriptionOrLabelRef;
        @XmlElement(required = true)
        protected List<TSubrange> subrange;
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
         * Gets the value of the subrange property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the subrange property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSubrange().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TSubrange }
         * 
         * 
         */
        public List<TSubrange> getSubrange() {
            if (subrange == null) {
                subrange = new ArrayList<TSubrange>();
            }
            return this.subrange;
        }

        /**
         * Ruft den Wert der bool-Eigenschaft ab.
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
         * Legt den Wert der bool-Eigenschaft fest.
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
         * Ruft den Wert der bitstring-Eigenschaft ab.
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
         * Legt den Wert der bitstring-Eigenschaft fest.
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
         * Ruft den Wert der byte-Eigenschaft ab.
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
         * Legt den Wert der byte-Eigenschaft fest.
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
         * Ruft den Wert der char-Eigenschaft ab.
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
         * Legt den Wert der char-Eigenschaft fest.
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
         * Ruft den Wert der word-Eigenschaft ab.
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
         * Legt den Wert der word-Eigenschaft fest.
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
         * Ruft den Wert der dword-Eigenschaft ab.
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
         * Legt den Wert der dword-Eigenschaft fest.
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
         * Ruft den Wert der lword-Eigenschaft ab.
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
         * Legt den Wert der lword-Eigenschaft fest.
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
         * Ruft den Wert der sint-Eigenschaft ab.
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
         * Legt den Wert der sint-Eigenschaft fest.
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
         * Ruft den Wert der int-Eigenschaft ab.
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
         * Legt den Wert der int-Eigenschaft fest.
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
         * Ruft den Wert der dint-Eigenschaft ab.
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
         * Legt den Wert der dint-Eigenschaft fest.
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
         * Ruft den Wert der lint-Eigenschaft ab.
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
         * Legt den Wert der lint-Eigenschaft fest.
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
         * Ruft den Wert der usint-Eigenschaft ab.
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
         * Legt den Wert der usint-Eigenschaft fest.
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
         * Ruft den Wert der uint-Eigenschaft ab.
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
         * Legt den Wert der uint-Eigenschaft fest.
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
         * Ruft den Wert der udint-Eigenschaft ab.
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
         * Legt den Wert der udint-Eigenschaft fest.
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
         * Ruft den Wert der ulint-Eigenschaft ab.
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
         * Legt den Wert der ulint-Eigenschaft fest.
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
         * Ruft den Wert der real-Eigenschaft ab.
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
         * Legt den Wert der real-Eigenschaft fest.
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
         * Ruft den Wert der lreal-Eigenschaft ab.
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
         * Legt den Wert der lreal-Eigenschaft fest.
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
         * Ruft den Wert der string-Eigenschaft ab.
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
         * Legt den Wert der string-Eigenschaft fest.
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
         * Ruft den Wert der wstring-Eigenschaft ab.
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
         * Legt den Wert der wstring-Eigenschaft fest.
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
         * Ruft den Wert der dataTypeIDRef-Eigenschaft ab.
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
         * Legt den Wert der dataTypeIDRef-Eigenschaft fest.
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
         * Ruft den Wert der name-Eigenschaft ab.
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
         * Legt den Wert der name-Eigenschaft fest.
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

    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;group ref="{http://www.ethernet-powerlink.org}g_labels" minOccurs="0"/&gt;
     *         &lt;element name="count" type="{http://www.ethernet-powerlink.org}t_count" minOccurs="0"/&gt;
     *         &lt;choice&gt;
     *           &lt;group ref="{http://www.ethernet-powerlink.org}g_simple"/&gt;
     *           &lt;element name="dataTypeIDRef" type="{http://www.ethernet-powerlink.org}t_dataTypeIDRef"/&gt;
     *         &lt;/choice&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="uniqueID" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
     *       &lt;attribute name="description" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "labelOrDescriptionOrLabelRef",
        "count",
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
    public static class Derived {

        @XmlElements({
            @XmlElement(name = "label", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Label.class),
            @XmlElement(name = "description", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Description.class),
            @XmlElement(name = "labelRef", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.LabelRef.class),
            @XmlElement(name = "descriptionRef", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.DescriptionRef.class)
        })
        protected List<Object> labelOrDescriptionOrLabelRef;
        protected TCount count;
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
        @XmlAttribute(name = "description")
        protected String description;

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
         * Ruft den Wert der count-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link TCount }
         *     
         */
        public TCount getCount() {
            return count;
        }

        /**
         * Legt den Wert der count-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link TCount }
         *     
         */
        public void setCount(TCount value) {
            this.count = value;
        }

        /**
         * Ruft den Wert der bool-Eigenschaft ab.
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
         * Legt den Wert der bool-Eigenschaft fest.
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
         * Ruft den Wert der bitstring-Eigenschaft ab.
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
         * Legt den Wert der bitstring-Eigenschaft fest.
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
         * Ruft den Wert der byte-Eigenschaft ab.
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
         * Legt den Wert der byte-Eigenschaft fest.
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
         * Ruft den Wert der char-Eigenschaft ab.
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
         * Legt den Wert der char-Eigenschaft fest.
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
         * Ruft den Wert der word-Eigenschaft ab.
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
         * Legt den Wert der word-Eigenschaft fest.
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
         * Ruft den Wert der dword-Eigenschaft ab.
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
         * Legt den Wert der dword-Eigenschaft fest.
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
         * Ruft den Wert der lword-Eigenschaft ab.
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
         * Legt den Wert der lword-Eigenschaft fest.
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
         * Ruft den Wert der sint-Eigenschaft ab.
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
         * Legt den Wert der sint-Eigenschaft fest.
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
         * Ruft den Wert der int-Eigenschaft ab.
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
         * Legt den Wert der int-Eigenschaft fest.
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
         * Ruft den Wert der dint-Eigenschaft ab.
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
         * Legt den Wert der dint-Eigenschaft fest.
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
         * Ruft den Wert der lint-Eigenschaft ab.
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
         * Legt den Wert der lint-Eigenschaft fest.
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
         * Ruft den Wert der usint-Eigenschaft ab.
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
         * Legt den Wert der usint-Eigenschaft fest.
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
         * Ruft den Wert der uint-Eigenschaft ab.
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
         * Legt den Wert der uint-Eigenschaft fest.
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
         * Ruft den Wert der udint-Eigenschaft ab.
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
         * Legt den Wert der udint-Eigenschaft fest.
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
         * Ruft den Wert der ulint-Eigenschaft ab.
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
         * Legt den Wert der ulint-Eigenschaft fest.
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
         * Ruft den Wert der real-Eigenschaft ab.
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
         * Legt den Wert der real-Eigenschaft fest.
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
         * Ruft den Wert der lreal-Eigenschaft ab.
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
         * Legt den Wert der lreal-Eigenschaft fest.
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
         * Ruft den Wert der string-Eigenschaft ab.
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
         * Legt den Wert der string-Eigenschaft fest.
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
         * Ruft den Wert der wstring-Eigenschaft ab.
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
         * Legt den Wert der wstring-Eigenschaft fest.
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
         * Ruft den Wert der dataTypeIDRef-Eigenschaft ab.
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
         * Legt den Wert der dataTypeIDRef-Eigenschaft fest.
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
         * Ruft den Wert der name-Eigenschaft ab.
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
         * Legt den Wert der name-Eigenschaft fest.
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
         * Ruft den Wert der description-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDescription() {
            return description;
        }

        /**
         * Legt den Wert der description-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDescription(String value) {
            this.description = value;
        }

    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;group ref="{http://www.ethernet-powerlink.org}g_labels" minOccurs="0"/&gt;
     *         &lt;element name="enumValue" type="{http://www.ethernet-powerlink.org}t_enumValue" maxOccurs="unbounded"/&gt;
     *         &lt;group ref="{http://www.ethernet-powerlink.org}g_simple" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="uniqueID" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
     *       &lt;attribute name="size" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "labelOrDescriptionOrLabelRef",
        "enumValue",
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
        "wstring"
    })
    public static class Enum {

        @XmlElements({
            @XmlElement(name = "label", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Label.class),
            @XmlElement(name = "description", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Description.class),
            @XmlElement(name = "labelRef", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.LabelRef.class),
            @XmlElement(name = "descriptionRef", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.DescriptionRef.class)
        })
        protected List<Object> labelOrDescriptionOrLabelRef;
        @XmlElement(required = true)
        protected List<TEnumValue> enumValue;
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
        @XmlAttribute(name = "name", required = true)
        protected String name;
        @XmlAttribute(name = "uniqueID", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlID
        @XmlSchemaType(name = "ID")
        protected String uniqueID;
        @XmlAttribute(name = "size")
        protected String size;

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
         * Gets the value of the enumValue property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the enumValue property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEnumValue().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TEnumValue }
         * 
         * 
         */
        public List<TEnumValue> getEnumValue() {
            if (enumValue == null) {
                enumValue = new ArrayList<TEnumValue>();
            }
            return this.enumValue;
        }

        /**
         * Ruft den Wert der bool-Eigenschaft ab.
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
         * Legt den Wert der bool-Eigenschaft fest.
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
         * Ruft den Wert der bitstring-Eigenschaft ab.
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
         * Legt den Wert der bitstring-Eigenschaft fest.
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
         * Ruft den Wert der byte-Eigenschaft ab.
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
         * Legt den Wert der byte-Eigenschaft fest.
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
         * Ruft den Wert der char-Eigenschaft ab.
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
         * Legt den Wert der char-Eigenschaft fest.
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
         * Ruft den Wert der word-Eigenschaft ab.
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
         * Legt den Wert der word-Eigenschaft fest.
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
         * Ruft den Wert der dword-Eigenschaft ab.
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
         * Legt den Wert der dword-Eigenschaft fest.
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
         * Ruft den Wert der lword-Eigenschaft ab.
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
         * Legt den Wert der lword-Eigenschaft fest.
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
         * Ruft den Wert der sint-Eigenschaft ab.
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
         * Legt den Wert der sint-Eigenschaft fest.
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
         * Ruft den Wert der int-Eigenschaft ab.
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
         * Legt den Wert der int-Eigenschaft fest.
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
         * Ruft den Wert der dint-Eigenschaft ab.
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
         * Legt den Wert der dint-Eigenschaft fest.
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
         * Ruft den Wert der lint-Eigenschaft ab.
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
         * Legt den Wert der lint-Eigenschaft fest.
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
         * Ruft den Wert der usint-Eigenschaft ab.
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
         * Legt den Wert der usint-Eigenschaft fest.
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
         * Ruft den Wert der uint-Eigenschaft ab.
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
         * Legt den Wert der uint-Eigenschaft fest.
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
         * Ruft den Wert der udint-Eigenschaft ab.
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
         * Legt den Wert der udint-Eigenschaft fest.
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
         * Ruft den Wert der ulint-Eigenschaft ab.
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
         * Legt den Wert der ulint-Eigenschaft fest.
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
         * Ruft den Wert der real-Eigenschaft ab.
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
         * Legt den Wert der real-Eigenschaft fest.
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
         * Ruft den Wert der lreal-Eigenschaft ab.
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
         * Legt den Wert der lreal-Eigenschaft fest.
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
         * Ruft den Wert der string-Eigenschaft ab.
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
         * Legt den Wert der string-Eigenschaft fest.
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
         * Ruft den Wert der wstring-Eigenschaft ab.
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
         * Legt den Wert der wstring-Eigenschaft fest.
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
         * Ruft den Wert der name-Eigenschaft ab.
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
         * Legt den Wert der name-Eigenschaft fest.
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
         * Ruft den Wert der size-Eigenschaft ab.
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
         * Legt den Wert der size-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSize(String value) {
            this.size = value;
        }

    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;group ref="{http://www.ethernet-powerlink.org}g_labels" minOccurs="0"/&gt;
     *         &lt;element name="varDeclaration" type="{http://www.ethernet-powerlink.org}t_varDeclaration" maxOccurs="unbounded"/&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="uniqueID" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "labelOrDescriptionOrLabelRef",
        "varDeclaration"
    })
    public static class Struct {

        @XmlElements({
            @XmlElement(name = "label", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Label.class),
            @XmlElement(name = "description", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Description.class),
            @XmlElement(name = "labelRef", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.LabelRef.class),
            @XmlElement(name = "descriptionRef", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.DescriptionRef.class)
        })
        protected List<Object> labelOrDescriptionOrLabelRef;
        @XmlElement(required = true)
        protected List<TVarDeclaration> varDeclaration;
        @XmlAttribute(name = "name", required = true)
        protected String name;
        @XmlAttribute(name = "uniqueID", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlID
        @XmlSchemaType(name = "ID")
        protected String uniqueID;

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
         * Gets the value of the varDeclaration property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the varDeclaration property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getVarDeclaration().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TVarDeclaration }
         * 
         * 
         */
        public List<TVarDeclaration> getVarDeclaration() {
            if (varDeclaration == null) {
                varDeclaration = new ArrayList<TVarDeclaration>();
            }
            return this.varDeclaration;
        }

        /**
         * Ruft den Wert der name-Eigenschaft ab.
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
         * Legt den Wert der name-Eigenschaft fest.
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

    }

}

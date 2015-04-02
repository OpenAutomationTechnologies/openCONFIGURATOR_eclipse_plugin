
package org.epsg.openconfigurator.xmlbinding.xdd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java-Klasse für t_Object complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="t_Object"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SubObject" maxOccurs="255" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attGroup ref="{http://www.ethernet-powerlink.org}ag_Powerlink_Object"/&gt;
 *                 &lt;attribute name="subIndex" use="required" type="{http://www.w3.org/2001/XMLSchema}hexBinary" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attGroup ref="{http://www.ethernet-powerlink.org}ag_Powerlink_Object"/&gt;
 *       &lt;attribute name="index" use="required" type="{http://www.w3.org/2001/XMLSchema}hexBinary" /&gt;
 *       &lt;attribute name="subNumber" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_Object", propOrder = {
    "subObject"
})
public class TObject {

    @XmlElement(name = "SubObject")
    protected List<TObject.SubObject> subObject;
    @XmlAttribute(name = "index", required = true)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] index;
    @XmlAttribute(name = "subNumber")
    @XmlSchemaType(name = "unsignedByte")
    protected Short subNumber;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "objectType", required = true)
    @XmlSchemaType(name = "unsignedByte")
    protected short objectType;
    @XmlAttribute(name = "dataType")
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] dataType;
    @XmlAttribute(name = "lowLimit")
    protected String lowLimit;
    @XmlAttribute(name = "highLimit")
    protected String highLimit;
    @XmlAttribute(name = "accessType")
    protected TObjectAccessType accessType;
    @XmlAttribute(name = "defaultValue")
    protected String defaultValue;
    @XmlAttribute(name = "actualValue")
    protected String actualValue;
    @XmlAttribute(name = "denotation")
    protected String denotation;
    @XmlAttribute(name = "PDOmapping")
    protected TObjectPDOMapping pdOmapping;
    @XmlAttribute(name = "objFlags")
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] objFlags;
    @XmlAttribute(name = "uniqueIDRef")
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object uniqueIDRef;

    /**
     * Gets the value of the subObject property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subObject property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubObject().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TObject.SubObject }
     * 
     * 
     */
    public List<TObject.SubObject> getSubObject() {
        if (subObject == null) {
            subObject = new ArrayList<TObject.SubObject>();
        }
        return this.subObject;
    }

    /**
     * Ruft den Wert der index-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getIndex() {
        return index;
    }

    /**
     * Legt den Wert der index-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndex(byte[] value) {
        this.index = value;
    }

    /**
     * Ruft den Wert der subNumber-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getSubNumber() {
        return subNumber;
    }

    /**
     * Legt den Wert der subNumber-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setSubNumber(Short value) {
        this.subNumber = value;
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
     * Ruft den Wert der objectType-Eigenschaft ab.
     * 
     */
    public short getObjectType() {
        return objectType;
    }

    /**
     * Legt den Wert der objectType-Eigenschaft fest.
     * 
     */
    public void setObjectType(short value) {
        this.objectType = value;
    }

    /**
     * Ruft den Wert der dataType-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getDataType() {
        return dataType;
    }

    /**
     * Legt den Wert der dataType-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataType(byte[] value) {
        this.dataType = value;
    }

    /**
     * Ruft den Wert der lowLimit-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLowLimit() {
        return lowLimit;
    }

    /**
     * Legt den Wert der lowLimit-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLowLimit(String value) {
        this.lowLimit = value;
    }

    /**
     * Ruft den Wert der highLimit-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHighLimit() {
        return highLimit;
    }

    /**
     * Legt den Wert der highLimit-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHighLimit(String value) {
        this.highLimit = value;
    }

    /**
     * Ruft den Wert der accessType-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TObjectAccessType }
     *     
     */
    public TObjectAccessType getAccessType() {
        return accessType;
    }

    /**
     * Legt den Wert der accessType-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TObjectAccessType }
     *     
     */
    public void setAccessType(TObjectAccessType value) {
        this.accessType = value;
    }

    /**
     * Ruft den Wert der defaultValue-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * Legt den Wert der defaultValue-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDefaultValue(String value) {
        this.defaultValue = value;
    }

    /**
     * Ruft den Wert der actualValue-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActualValue() {
        return actualValue;
    }

    /**
     * Legt den Wert der actualValue-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActualValue(String value) {
        this.actualValue = value;
    }

    /**
     * Ruft den Wert der denotation-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenotation() {
        return denotation;
    }

    /**
     * Legt den Wert der denotation-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenotation(String value) {
        this.denotation = value;
    }

    /**
     * Ruft den Wert der pdOmapping-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TObjectPDOMapping }
     *     
     */
    public TObjectPDOMapping getPDOmapping() {
        return pdOmapping;
    }

    /**
     * Legt den Wert der pdOmapping-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TObjectPDOMapping }
     *     
     */
    public void setPDOmapping(TObjectPDOMapping value) {
        this.pdOmapping = value;
    }

    /**
     * Ruft den Wert der objFlags-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getObjFlags() {
        return objFlags;
    }

    /**
     * Legt den Wert der objFlags-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjFlags(byte[] value) {
        this.objFlags = value;
    }

    /**
     * Ruft den Wert der uniqueIDRef-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUniqueIDRef() {
        return uniqueIDRef;
    }

    /**
     * Legt den Wert der uniqueIDRef-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUniqueIDRef(Object value) {
        this.uniqueIDRef = value;
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
     *       &lt;attGroup ref="{http://www.ethernet-powerlink.org}ag_Powerlink_Object"/&gt;
     *       &lt;attribute name="subIndex" use="required" type="{http://www.w3.org/2001/XMLSchema}hexBinary" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class SubObject {

        @XmlAttribute(name = "subIndex", required = true)
        @XmlJavaTypeAdapter(HexBinaryAdapter.class)
        @XmlSchemaType(name = "hexBinary")
        protected byte[] subIndex;
        @XmlAttribute(name = "name", required = true)
        protected String name;
        @XmlAttribute(name = "objectType", required = true)
        @XmlSchemaType(name = "unsignedByte")
        protected short objectType;
        @XmlAttribute(name = "dataType")
        @XmlJavaTypeAdapter(HexBinaryAdapter.class)
        @XmlSchemaType(name = "hexBinary")
        protected byte[] dataType;
        @XmlAttribute(name = "lowLimit")
        protected String lowLimit;
        @XmlAttribute(name = "highLimit")
        protected String highLimit;
        @XmlAttribute(name = "accessType")
        protected TObjectAccessType accessType;
        @XmlAttribute(name = "defaultValue")
        protected String defaultValue;
        @XmlAttribute(name = "actualValue")
        protected String actualValue;
        @XmlAttribute(name = "denotation")
        protected String denotation;
        @XmlAttribute(name = "PDOmapping")
        protected TObjectPDOMapping pdOmapping;
        @XmlAttribute(name = "objFlags")
        @XmlJavaTypeAdapter(HexBinaryAdapter.class)
        @XmlSchemaType(name = "hexBinary")
        protected byte[] objFlags;
        @XmlAttribute(name = "uniqueIDRef")
        @XmlIDREF
        @XmlSchemaType(name = "IDREF")
        protected Object uniqueIDRef;

        /**
         * Ruft den Wert der subIndex-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public byte[] getSubIndex() {
            return subIndex;
        }

        /**
         * Legt den Wert der subIndex-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSubIndex(byte[] value) {
            this.subIndex = value;
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
         * Ruft den Wert der objectType-Eigenschaft ab.
         * 
         */
        public short getObjectType() {
            return objectType;
        }

        /**
         * Legt den Wert der objectType-Eigenschaft fest.
         * 
         */
        public void setObjectType(short value) {
            this.objectType = value;
        }

        /**
         * Ruft den Wert der dataType-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public byte[] getDataType() {
            return dataType;
        }

        /**
         * Legt den Wert der dataType-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDataType(byte[] value) {
            this.dataType = value;
        }

        /**
         * Ruft den Wert der lowLimit-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLowLimit() {
            return lowLimit;
        }

        /**
         * Legt den Wert der lowLimit-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLowLimit(String value) {
            this.lowLimit = value;
        }

        /**
         * Ruft den Wert der highLimit-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHighLimit() {
            return highLimit;
        }

        /**
         * Legt den Wert der highLimit-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHighLimit(String value) {
            this.highLimit = value;
        }

        /**
         * Ruft den Wert der accessType-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link TObjectAccessType }
         *     
         */
        public TObjectAccessType getAccessType() {
            return accessType;
        }

        /**
         * Legt den Wert der accessType-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link TObjectAccessType }
         *     
         */
        public void setAccessType(TObjectAccessType value) {
            this.accessType = value;
        }

        /**
         * Ruft den Wert der defaultValue-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDefaultValue() {
            return defaultValue;
        }

        /**
         * Legt den Wert der defaultValue-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDefaultValue(String value) {
            this.defaultValue = value;
        }

        /**
         * Ruft den Wert der actualValue-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getActualValue() {
            return actualValue;
        }

        /**
         * Legt den Wert der actualValue-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setActualValue(String value) {
            this.actualValue = value;
        }

        /**
         * Ruft den Wert der denotation-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDenotation() {
            return denotation;
        }

        /**
         * Legt den Wert der denotation-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDenotation(String value) {
            this.denotation = value;
        }

        /**
         * Ruft den Wert der pdOmapping-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link TObjectPDOMapping }
         *     
         */
        public TObjectPDOMapping getPDOmapping() {
            return pdOmapping;
        }

        /**
         * Legt den Wert der pdOmapping-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link TObjectPDOMapping }
         *     
         */
        public void setPDOmapping(TObjectPDOMapping value) {
            this.pdOmapping = value;
        }

        /**
         * Ruft den Wert der objFlags-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public byte[] getObjFlags() {
            return objFlags;
        }

        /**
         * Legt den Wert der objFlags-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setObjFlags(byte[] value) {
            this.objFlags = value;
        }

        /**
         * Ruft den Wert der uniqueIDRef-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Object }
         *     
         */
        public Object getUniqueIDRef() {
            return uniqueIDRef;
        }

        /**
         * Legt den Wert der uniqueIDRef-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Object }
         *     
         */
        public void setUniqueIDRef(Object value) {
            this.uniqueIDRef = value;
        }

    }

}

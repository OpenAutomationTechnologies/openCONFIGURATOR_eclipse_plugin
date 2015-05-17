
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
 * <p>Java class for t_Object complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
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
     * Gets the value of the index property.
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
     * Sets the value of the index property.
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
     * Gets the value of the subNumber property.
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
     * Sets the value of the subNumber property.
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
     * Gets the value of the objectType property.
     * 
     */
    public short getObjectType() {
        return objectType;
    }

    /**
     * Sets the value of the objectType property.
     * 
     */
    public void setObjectType(short value) {
        this.objectType = value;
    }

    /**
     * Gets the value of the dataType property.
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
     * Sets the value of the dataType property.
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
     * Gets the value of the lowLimit property.
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
     * Sets the value of the lowLimit property.
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
     * Gets the value of the highLimit property.
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
     * Sets the value of the highLimit property.
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
     * Gets the value of the accessType property.
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
     * Sets the value of the accessType property.
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
     * Gets the value of the defaultValue property.
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
     * Sets the value of the defaultValue property.
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
     * Gets the value of the actualValue property.
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
     * Sets the value of the actualValue property.
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
     * Gets the value of the denotation property.
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
     * Sets the value of the denotation property.
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
     * Gets the value of the pdOmapping property.
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
     * Sets the value of the pdOmapping property.
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
     * Gets the value of the objFlags property.
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
     * Sets the value of the objFlags property.
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
     * Gets the value of the uniqueIDRef property.
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
     * Sets the value of the uniqueIDRef property.
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
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
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
         * Gets the value of the subIndex property.
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
         * Sets the value of the subIndex property.
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
         * Gets the value of the objectType property.
         * 
         */
        public short getObjectType() {
            return objectType;
        }

        /**
         * Sets the value of the objectType property.
         * 
         */
        public void setObjectType(short value) {
            this.objectType = value;
        }

        /**
         * Gets the value of the dataType property.
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
         * Sets the value of the dataType property.
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
         * Gets the value of the lowLimit property.
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
         * Sets the value of the lowLimit property.
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
         * Gets the value of the highLimit property.
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
         * Sets the value of the highLimit property.
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
         * Gets the value of the accessType property.
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
         * Sets the value of the accessType property.
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
         * Gets the value of the defaultValue property.
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
         * Sets the value of the defaultValue property.
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
         * Gets the value of the actualValue property.
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
         * Sets the value of the actualValue property.
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
         * Gets the value of the denotation property.
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
         * Sets the value of the denotation property.
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
         * Gets the value of the pdOmapping property.
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
         * Sets the value of the pdOmapping property.
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
         * Gets the value of the objFlags property.
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
         * Sets the value of the objFlags property.
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
         * Gets the value of the uniqueIDRef property.
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
         * Sets the value of the uniqueIDRef property.
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

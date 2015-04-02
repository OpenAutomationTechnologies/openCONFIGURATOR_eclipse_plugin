
package org.epsg.openconfigurator.xmlbinding.xdd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für ErrorConstant_DataType complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="ErrorConstant_DataType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;group ref="{http://www.ethernet-powerlink.org}g_labels" minOccurs="0"/&gt;
 *         &lt;element name="addInfo" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;group ref="{http://www.ethernet-powerlink.org}g_labels" minOccurs="0"/&gt;
 *                   &lt;element name="value" maxOccurs="unbounded" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;group ref="{http://www.ethernet-powerlink.org}g_labels" minOccurs="0"/&gt;
 *                           &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="bitOffset" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" /&gt;
 *                 &lt;attribute name="len" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" /&gt;
 *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ErrorConstant_DataType", propOrder = {
    "labelOrDescriptionOrLabelRef",
    "addInfo"
})
public class ErrorConstantDataType {

    @XmlElements({
        @XmlElement(name = "label", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Label.class),
        @XmlElement(name = "description", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Description.class),
        @XmlElement(name = "labelRef", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.LabelRef.class),
        @XmlElement(name = "descriptionRef", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.DescriptionRef.class)
    })
    protected List<Object> labelOrDescriptionOrLabelRef;
    protected List<ErrorConstantDataType.AddInfo> addInfo;
    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "value", required = true)
    protected String value;

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
     * Gets the value of the addInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ErrorConstantDataType.AddInfo }
     * 
     * 
     */
    public List<ErrorConstantDataType.AddInfo> getAddInfo() {
        if (addInfo == null) {
            addInfo = new ArrayList<ErrorConstantDataType.AddInfo>();
        }
        return this.addInfo;
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
     * Ruft den Wert der value-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Legt den Wert der value-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
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
     *         &lt;element name="value" maxOccurs="unbounded" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;group ref="{http://www.ethernet-powerlink.org}g_labels" minOccurs="0"/&gt;
     *                 &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="bitOffset" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" /&gt;
     *       &lt;attribute name="len" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" /&gt;
     *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
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
        "value"
    })
    public static class AddInfo {

        @XmlElements({
            @XmlElement(name = "label", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Label.class),
            @XmlElement(name = "description", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Description.class),
            @XmlElement(name = "labelRef", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.LabelRef.class),
            @XmlElement(name = "descriptionRef", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.DescriptionRef.class)
        })
        protected List<Object> labelOrDescriptionOrLabelRef;
        protected List<ErrorConstantDataType.AddInfo.Value> value;
        @XmlAttribute(name = "bitOffset", required = true)
        @XmlSchemaType(name = "unsignedByte")
        protected short bitOffset;
        @XmlAttribute(name = "len", required = true)
        @XmlSchemaType(name = "unsignedByte")
        protected short len;
        @XmlAttribute(name = "name", required = true)
        protected String name;

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
         * Gets the value of the value property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the value property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getValue().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ErrorConstantDataType.AddInfo.Value }
         * 
         * 
         */
        public List<ErrorConstantDataType.AddInfo.Value> getValue() {
            if (value == null) {
                value = new ArrayList<ErrorConstantDataType.AddInfo.Value>();
            }
            return this.value;
        }

        /**
         * Ruft den Wert der bitOffset-Eigenschaft ab.
         * 
         */
        public short getBitOffset() {
            return bitOffset;
        }

        /**
         * Legt den Wert der bitOffset-Eigenschaft fest.
         * 
         */
        public void setBitOffset(short value) {
            this.bitOffset = value;
        }

        /**
         * Ruft den Wert der len-Eigenschaft ab.
         * 
         */
        public short getLen() {
            return len;
        }

        /**
         * Legt den Wert der len-Eigenschaft fest.
         * 
         */
        public void setLen(short value) {
            this.len = value;
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
         * <p>Java-Klasse für anonymous complex type.
         * 
         * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
         * 
         * <pre>
         * &lt;complexType&gt;
         *   &lt;complexContent&gt;
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *       &lt;group ref="{http://www.ethernet-powerlink.org}g_labels" minOccurs="0"/&gt;
         *       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *     &lt;/restriction&gt;
         *   &lt;/complexContent&gt;
         * &lt;/complexType&gt;
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "labelOrDescriptionOrLabelRef"
        })
        public static class Value {

            @XmlElements({
                @XmlElement(name = "label", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Label.class),
                @XmlElement(name = "description", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Description.class),
                @XmlElement(name = "labelRef", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.LabelRef.class),
                @XmlElement(name = "descriptionRef", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.DescriptionRef.class)
            })
            protected List<Object> labelOrDescriptionOrLabelRef;
            @XmlAttribute(name = "value", required = true)
            protected String value;
            @XmlAttribute(name = "name", required = true)
            protected String name;

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
             * Ruft den Wert der value-Eigenschaft ab.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Legt den Wert der value-Eigenschaft fest.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
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

        }

    }

}

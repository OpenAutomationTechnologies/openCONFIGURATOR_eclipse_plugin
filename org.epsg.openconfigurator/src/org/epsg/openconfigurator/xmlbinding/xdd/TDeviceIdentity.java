
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
import javax.xml.bind.annotation.XmlValue;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java-Klasse für t_DeviceIdentity complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="t_DeviceIdentity"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="vendorName"&gt;
 *           &lt;complexType&gt;
 *             &lt;simpleContent&gt;
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *                 &lt;attribute name="readOnly" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
 *               &lt;/extension&gt;
 *             &lt;/simpleContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="vendorID" type="{http://www.ethernet-powerlink.org}t_vendorID" minOccurs="0"/&gt;
 *         &lt;element name="vendorText" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;group ref="{http://www.ethernet-powerlink.org}g_labels"/&gt;
 *                 &lt;attribute name="readOnly" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="deviceFamily" type="{http://www.ethernet-powerlink.org}t_deviceFamily" minOccurs="0"/&gt;
 *         &lt;element name="productFamily" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;simpleContent&gt;
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *                 &lt;attribute name="readOnly" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
 *               &lt;/extension&gt;
 *             &lt;/simpleContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="productName"&gt;
 *           &lt;complexType&gt;
 *             &lt;simpleContent&gt;
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *                 &lt;attribute name="readOnly" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
 *               &lt;/extension&gt;
 *             &lt;/simpleContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="productID" type="{http://www.ethernet-powerlink.org}t_productID" minOccurs="0"/&gt;
 *         &lt;element name="productText" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;group ref="{http://www.ethernet-powerlink.org}g_labels"/&gt;
 *                 &lt;attribute name="readOnly" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="orderNumber" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;simpleContent&gt;
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *                 &lt;attribute name="readOnly" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
 *               &lt;/extension&gt;
 *             &lt;/simpleContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="version" type="{http://www.ethernet-powerlink.org}t_version" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="buildDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *         &lt;element name="specificationRevision" type="{http://www.ethernet-powerlink.org}t_specificationRevision" minOccurs="0"/&gt;
 *         &lt;element name="instanceName" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;simpleContent&gt;
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
 *                 &lt;attribute name="readOnly" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *               &lt;/extension&gt;
 *             &lt;/simpleContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_DeviceIdentity", propOrder = {
    "vendorName",
    "vendorID",
    "vendorText",
    "deviceFamily",
    "productFamily",
    "productName",
    "productID",
    "productText",
    "orderNumber",
    "version",
    "buildDate",
    "specificationRevision",
    "instanceName"
})
public class TDeviceIdentity {

    @XmlElement(required = true)
    protected TDeviceIdentity.VendorName vendorName;
    protected TVendorID vendorID;
    protected TDeviceIdentity.VendorText vendorText;
    protected TDeviceFamily deviceFamily;
    protected TDeviceIdentity.ProductFamily productFamily;
    @XmlElement(required = true)
    protected TDeviceIdentity.ProductName productName;
    protected TProductID productID;
    protected TDeviceIdentity.ProductText productText;
    protected List<TDeviceIdentity.OrderNumber> orderNumber;
    protected List<TVersion> version;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar buildDate;
    protected TSpecificationRevision specificationRevision;
    protected TDeviceIdentity.InstanceName instanceName;

    /**
     * Ruft den Wert der vendorName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TDeviceIdentity.VendorName }
     *     
     */
    public TDeviceIdentity.VendorName getVendorName() {
        return vendorName;
    }

    /**
     * Legt den Wert der vendorName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TDeviceIdentity.VendorName }
     *     
     */
    public void setVendorName(TDeviceIdentity.VendorName value) {
        this.vendorName = value;
    }

    /**
     * Ruft den Wert der vendorID-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TVendorID }
     *     
     */
    public TVendorID getVendorID() {
        return vendorID;
    }

    /**
     * Legt den Wert der vendorID-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TVendorID }
     *     
     */
    public void setVendorID(TVendorID value) {
        this.vendorID = value;
    }

    /**
     * Ruft den Wert der vendorText-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TDeviceIdentity.VendorText }
     *     
     */
    public TDeviceIdentity.VendorText getVendorText() {
        return vendorText;
    }

    /**
     * Legt den Wert der vendorText-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TDeviceIdentity.VendorText }
     *     
     */
    public void setVendorText(TDeviceIdentity.VendorText value) {
        this.vendorText = value;
    }

    /**
     * Ruft den Wert der deviceFamily-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TDeviceFamily }
     *     
     */
    public TDeviceFamily getDeviceFamily() {
        return deviceFamily;
    }

    /**
     * Legt den Wert der deviceFamily-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TDeviceFamily }
     *     
     */
    public void setDeviceFamily(TDeviceFamily value) {
        this.deviceFamily = value;
    }

    /**
     * Ruft den Wert der productFamily-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TDeviceIdentity.ProductFamily }
     *     
     */
    public TDeviceIdentity.ProductFamily getProductFamily() {
        return productFamily;
    }

    /**
     * Legt den Wert der productFamily-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TDeviceIdentity.ProductFamily }
     *     
     */
    public void setProductFamily(TDeviceIdentity.ProductFamily value) {
        this.productFamily = value;
    }

    /**
     * Ruft den Wert der productName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TDeviceIdentity.ProductName }
     *     
     */
    public TDeviceIdentity.ProductName getProductName() {
        return productName;
    }

    /**
     * Legt den Wert der productName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TDeviceIdentity.ProductName }
     *     
     */
    public void setProductName(TDeviceIdentity.ProductName value) {
        this.productName = value;
    }

    /**
     * Ruft den Wert der productID-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TProductID }
     *     
     */
    public TProductID getProductID() {
        return productID;
    }

    /**
     * Legt den Wert der productID-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TProductID }
     *     
     */
    public void setProductID(TProductID value) {
        this.productID = value;
    }

    /**
     * Ruft den Wert der productText-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TDeviceIdentity.ProductText }
     *     
     */
    public TDeviceIdentity.ProductText getProductText() {
        return productText;
    }

    /**
     * Legt den Wert der productText-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TDeviceIdentity.ProductText }
     *     
     */
    public void setProductText(TDeviceIdentity.ProductText value) {
        this.productText = value;
    }

    /**
     * Gets the value of the orderNumber property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orderNumber property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrderNumber().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TDeviceIdentity.OrderNumber }
     * 
     * 
     */
    public List<TDeviceIdentity.OrderNumber> getOrderNumber() {
        if (orderNumber == null) {
            orderNumber = new ArrayList<TDeviceIdentity.OrderNumber>();
        }
        return this.orderNumber;
    }

    /**
     * Gets the value of the version property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the version property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVersion().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TVersion }
     * 
     * 
     */
    public List<TVersion> getVersion() {
        if (version == null) {
            version = new ArrayList<TVersion>();
        }
        return this.version;
    }

    /**
     * Ruft den Wert der buildDate-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBuildDate() {
        return buildDate;
    }

    /**
     * Legt den Wert der buildDate-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBuildDate(XMLGregorianCalendar value) {
        this.buildDate = value;
    }

    /**
     * Ruft den Wert der specificationRevision-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TSpecificationRevision }
     *     
     */
    public TSpecificationRevision getSpecificationRevision() {
        return specificationRevision;
    }

    /**
     * Legt den Wert der specificationRevision-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TSpecificationRevision }
     *     
     */
    public void setSpecificationRevision(TSpecificationRevision value) {
        this.specificationRevision = value;
    }

    /**
     * Ruft den Wert der instanceName-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TDeviceIdentity.InstanceName }
     *     
     */
    public TDeviceIdentity.InstanceName getInstanceName() {
        return instanceName;
    }

    /**
     * Legt den Wert der instanceName-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TDeviceIdentity.InstanceName }
     *     
     */
    public void setInstanceName(TDeviceIdentity.InstanceName value) {
        this.instanceName = value;
    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;simpleContent&gt;
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
     *       &lt;attribute name="readOnly" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
     *     &lt;/extension&gt;
     *   &lt;/simpleContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class InstanceName {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "readOnly")
        protected Boolean readOnly;

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
         * Ruft den Wert der readOnly-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isReadOnly() {
            if (readOnly == null) {
                return false;
            } else {
                return readOnly;
            }
        }

        /**
         * Legt den Wert der readOnly-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setReadOnly(Boolean value) {
            this.readOnly = value;
        }

    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;simpleContent&gt;
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
     *       &lt;attribute name="readOnly" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
     *     &lt;/extension&gt;
     *   &lt;/simpleContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class OrderNumber {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "readOnly")
        protected Boolean readOnly;

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
         * Ruft den Wert der readOnly-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isReadOnly() {
            if (readOnly == null) {
                return true;
            } else {
                return readOnly;
            }
        }

        /**
         * Legt den Wert der readOnly-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setReadOnly(Boolean value) {
            this.readOnly = value;
        }

    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;simpleContent&gt;
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
     *       &lt;attribute name="readOnly" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
     *     &lt;/extension&gt;
     *   &lt;/simpleContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class ProductFamily {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "readOnly")
        protected Boolean readOnly;

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
         * Ruft den Wert der readOnly-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isReadOnly() {
            if (readOnly == null) {
                return true;
            } else {
                return readOnly;
            }
        }

        /**
         * Legt den Wert der readOnly-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setReadOnly(Boolean value) {
            this.readOnly = value;
        }

    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;simpleContent&gt;
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
     *       &lt;attribute name="readOnly" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
     *     &lt;/extension&gt;
     *   &lt;/simpleContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class ProductName {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "readOnly")
        protected Boolean readOnly;

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
         * Ruft den Wert der readOnly-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isReadOnly() {
            if (readOnly == null) {
                return true;
            } else {
                return readOnly;
            }
        }

        /**
         * Legt den Wert der readOnly-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setReadOnly(Boolean value) {
            this.readOnly = value;
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
     *       &lt;group ref="{http://www.ethernet-powerlink.org}g_labels"/&gt;
     *       &lt;attribute name="readOnly" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
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
    public static class ProductText {

        @XmlElements({
            @XmlElement(name = "label", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Label.class),
            @XmlElement(name = "description", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Description.class),
            @XmlElement(name = "labelRef", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.LabelRef.class),
            @XmlElement(name = "descriptionRef", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.DescriptionRef.class)
        })
        protected List<Object> labelOrDescriptionOrLabelRef;
        @XmlAttribute(name = "readOnly")
        protected Boolean readOnly;

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
         * Ruft den Wert der readOnly-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isReadOnly() {
            if (readOnly == null) {
                return true;
            } else {
                return readOnly;
            }
        }

        /**
         * Legt den Wert der readOnly-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setReadOnly(Boolean value) {
            this.readOnly = value;
        }

    }


    /**
     * <p>Java-Klasse für anonymous complex type.
     * 
     * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;simpleContent&gt;
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
     *       &lt;attribute name="readOnly" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
     *     &lt;/extension&gt;
     *   &lt;/simpleContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class VendorName {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "readOnly")
        protected Boolean readOnly;

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
         * Ruft den Wert der readOnly-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isReadOnly() {
            if (readOnly == null) {
                return true;
            } else {
                return readOnly;
            }
        }

        /**
         * Legt den Wert der readOnly-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setReadOnly(Boolean value) {
            this.readOnly = value;
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
     *       &lt;group ref="{http://www.ethernet-powerlink.org}g_labels"/&gt;
     *       &lt;attribute name="readOnly" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
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
    public static class VendorText {

        @XmlElements({
            @XmlElement(name = "label", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Label.class),
            @XmlElement(name = "description", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Description.class),
            @XmlElement(name = "labelRef", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.LabelRef.class),
            @XmlElement(name = "descriptionRef", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.DescriptionRef.class)
        })
        protected List<Object> labelOrDescriptionOrLabelRef;
        @XmlAttribute(name = "readOnly")
        protected Boolean readOnly;

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
         * Ruft den Wert der readOnly-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isReadOnly() {
            if (readOnly == null) {
                return true;
            } else {
                return readOnly;
            }
        }

        /**
         * Legt den Wert der readOnly-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setReadOnly(Boolean value) {
            this.readOnly = value;
        }

    }

}

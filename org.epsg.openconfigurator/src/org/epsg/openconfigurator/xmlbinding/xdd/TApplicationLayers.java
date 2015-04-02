
package org.epsg.openconfigurator.xmlbinding.xdd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java-Klasse für t_ApplicationLayers complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="t_ApplicationLayers"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="identity" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="vendorID" type="{http://www.ethernet-powerlink.org}t_vendorID" minOccurs="0"/&gt;
 *                   &lt;element name="deviceFamily" type="{http://www.ethernet-powerlink.org}t_deviceFamily" minOccurs="0"/&gt;
 *                   &lt;element name="productID" type="{http://www.ethernet-powerlink.org}t_productID" minOccurs="0"/&gt;
 *                   &lt;element name="version" type="{http://www.ethernet-powerlink.org}t_version" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                   &lt;element name="buildDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
 *                   &lt;element name="specificationRevision" type="{http://www.ethernet-powerlink.org}t_specificationRevision" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="DataTypeList"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="defType" type="{http://www.ethernet-powerlink.org}t_DataTypes" maxOccurs="unbounded"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="ObjectList"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Object" type="{http://www.ethernet-powerlink.org}t_Object" maxOccurs="65535"/&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="mandatoryObjects" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                 &lt;attribute name="optionalObjects" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                 &lt;attribute name="manufacturerObjects" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="dynamicChannels" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="dynamicChannel" type="{http://www.ethernet-powerlink.org}t_dynamicChannel" maxOccurs="unbounded"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="conformanceClass" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="communicationEntityType" type="{http://www.w3.org/2001/XMLSchema}NMTOKENS" default="slave" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_ApplicationLayers", propOrder = {
    "identity",
    "dataTypeList",
    "objectList",
    "dynamicChannels"
})
public class TApplicationLayers {

    protected TApplicationLayers.Identity identity;
    @XmlElement(name = "DataTypeList", required = true)
    protected TApplicationLayers.DataTypeList dataTypeList;
    @XmlElement(name = "ObjectList", required = true)
    protected TApplicationLayers.ObjectList objectList;
    protected TApplicationLayers.DynamicChannels dynamicChannels;
    @XmlAttribute(name = "conformanceClass")
    protected String conformanceClass;
    @XmlAttribute(name = "communicationEntityType")
    @XmlSchemaType(name = "NMTOKENS")
    protected List<String> communicationEntityType;

    /**
     * Ruft den Wert der identity-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TApplicationLayers.Identity }
     *     
     */
    public TApplicationLayers.Identity getIdentity() {
        return identity;
    }

    /**
     * Legt den Wert der identity-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TApplicationLayers.Identity }
     *     
     */
    public void setIdentity(TApplicationLayers.Identity value) {
        this.identity = value;
    }

    /**
     * Ruft den Wert der dataTypeList-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TApplicationLayers.DataTypeList }
     *     
     */
    public TApplicationLayers.DataTypeList getDataTypeList() {
        return dataTypeList;
    }

    /**
     * Legt den Wert der dataTypeList-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TApplicationLayers.DataTypeList }
     *     
     */
    public void setDataTypeList(TApplicationLayers.DataTypeList value) {
        this.dataTypeList = value;
    }

    /**
     * Ruft den Wert der objectList-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TApplicationLayers.ObjectList }
     *     
     */
    public TApplicationLayers.ObjectList getObjectList() {
        return objectList;
    }

    /**
     * Legt den Wert der objectList-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TApplicationLayers.ObjectList }
     *     
     */
    public void setObjectList(TApplicationLayers.ObjectList value) {
        this.objectList = value;
    }

    /**
     * Ruft den Wert der dynamicChannels-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TApplicationLayers.DynamicChannels }
     *     
     */
    public TApplicationLayers.DynamicChannels getDynamicChannels() {
        return dynamicChannels;
    }

    /**
     * Legt den Wert der dynamicChannels-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TApplicationLayers.DynamicChannels }
     *     
     */
    public void setDynamicChannels(TApplicationLayers.DynamicChannels value) {
        this.dynamicChannels = value;
    }

    /**
     * Ruft den Wert der conformanceClass-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConformanceClass() {
        return conformanceClass;
    }

    /**
     * Legt den Wert der conformanceClass-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConformanceClass(String value) {
        this.conformanceClass = value;
    }

    /**
     * Gets the value of the communicationEntityType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the communicationEntityType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCommunicationEntityType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getCommunicationEntityType() {
        if (communicationEntityType == null) {
            communicationEntityType = new ArrayList<String>();
        }
        return this.communicationEntityType;
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
     *         &lt;element name="defType" type="{http://www.ethernet-powerlink.org}t_DataTypes" maxOccurs="unbounded"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "defType"
    })
    public static class DataTypeList {

        @XmlElement(required = true)
        protected List<TDataTypes> defType;

        /**
         * Gets the value of the defType property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the defType property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDefType().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TDataTypes }
         * 
         * 
         */
        public List<TDataTypes> getDefType() {
            if (defType == null) {
                defType = new ArrayList<TDataTypes>();
            }
            return this.defType;
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
     *         &lt;element name="dynamicChannel" type="{http://www.ethernet-powerlink.org}t_dynamicChannel" maxOccurs="unbounded"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "dynamicChannel"
    })
    public static class DynamicChannels {

        @XmlElement(required = true)
        protected List<TDynamicChannel> dynamicChannel;

        /**
         * Gets the value of the dynamicChannel property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the dynamicChannel property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getDynamicChannel().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TDynamicChannel }
         * 
         * 
         */
        public List<TDynamicChannel> getDynamicChannel() {
            if (dynamicChannel == null) {
                dynamicChannel = new ArrayList<TDynamicChannel>();
            }
            return this.dynamicChannel;
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
     *         &lt;element name="vendorID" type="{http://www.ethernet-powerlink.org}t_vendorID" minOccurs="0"/&gt;
     *         &lt;element name="deviceFamily" type="{http://www.ethernet-powerlink.org}t_deviceFamily" minOccurs="0"/&gt;
     *         &lt;element name="productID" type="{http://www.ethernet-powerlink.org}t_productID" minOccurs="0"/&gt;
     *         &lt;element name="version" type="{http://www.ethernet-powerlink.org}t_version" maxOccurs="unbounded" minOccurs="0"/&gt;
     *         &lt;element name="buildDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/&gt;
     *         &lt;element name="specificationRevision" type="{http://www.ethernet-powerlink.org}t_specificationRevision" minOccurs="0"/&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "vendorID",
        "deviceFamily",
        "productID",
        "version",
        "buildDate",
        "specificationRevision"
    })
    public static class Identity {

        protected TVendorID vendorID;
        protected TDeviceFamily deviceFamily;
        protected TProductID productID;
        protected List<TVersion> version;
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar buildDate;
        protected TSpecificationRevision specificationRevision;

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
     *         &lt;element name="Object" type="{http://www.ethernet-powerlink.org}t_Object" maxOccurs="65535"/&gt;
     *       &lt;/sequence&gt;
     *       &lt;attribute name="mandatoryObjects" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *       &lt;attribute name="optionalObjects" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *       &lt;attribute name="manufacturerObjects" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "object"
    })
    public static class ObjectList {

        @XmlElement(name = "Object", required = true)
        protected List<TObject> object;
        @XmlAttribute(name = "mandatoryObjects")
        @XmlSchemaType(name = "unsignedInt")
        protected Long mandatoryObjects;
        @XmlAttribute(name = "optionalObjects")
        @XmlSchemaType(name = "unsignedInt")
        protected Long optionalObjects;
        @XmlAttribute(name = "manufacturerObjects")
        @XmlSchemaType(name = "unsignedInt")
        protected Long manufacturerObjects;

        /**
         * Gets the value of the object property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the object property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getObject().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TObject }
         * 
         * 
         */
        public List<TObject> getObject() {
            if (object == null) {
                object = new ArrayList<TObject>();
            }
            return this.object;
        }

        /**
         * Ruft den Wert der mandatoryObjects-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getMandatoryObjects() {
            return mandatoryObjects;
        }

        /**
         * Legt den Wert der mandatoryObjects-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setMandatoryObjects(Long value) {
            this.mandatoryObjects = value;
        }

        /**
         * Ruft den Wert der optionalObjects-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getOptionalObjects() {
            return optionalObjects;
        }

        /**
         * Legt den Wert der optionalObjects-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setOptionalObjects(Long value) {
            this.optionalObjects = value;
        }

        /**
         * Ruft den Wert der manufacturerObjects-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getManufacturerObjects() {
            return manufacturerObjects;
        }

        /**
         * Legt den Wert der manufacturerObjects-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setManufacturerObjects(Long value) {
            this.manufacturerObjects = value;
        }

    }

}


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
 * <p>Java class for t_ApplicationLayers_Extension complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_ApplicationLayers_Extension"&gt;
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
 *         &lt;element name="ObjectList"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="Object" type="{http://www.ethernet-powerlink.org}t_Object_Extension" maxOccurs="65535"/&gt;
 *                 &lt;/sequence&gt;
 *                 &lt;attribute name="mandatoryObjects" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                 &lt;attribute name="optionalObjects" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                 &lt;attribute name="manufacturerObjects" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
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
@XmlType(name = "t_ApplicationLayers_Extension", propOrder = {
    "identity",
    "objectList"
})
public class TApplicationLayersExtension {

    protected TApplicationLayersExtension.Identity identity;
    @XmlElement(name = "ObjectList", required = true)
    protected TApplicationLayersExtension.ObjectList objectList;
    @XmlAttribute(name = "conformanceClass")
    protected String conformanceClass;
    @XmlAttribute(name = "communicationEntityType")
    @XmlSchemaType(name = "NMTOKENS")
    protected List<String> communicationEntityType;

    /**
     * Gets the value of the identity property.
     * 
     * @return
     *     possible object is
     *     {@link TApplicationLayersExtension.Identity }
     *     
     */
    public TApplicationLayersExtension.Identity getIdentity() {
        return identity;
    }

    /**
     * Sets the value of the identity property.
     * 
     * @param value
     *     allowed object is
     *     {@link TApplicationLayersExtension.Identity }
     *     
     */
    public void setIdentity(TApplicationLayersExtension.Identity value) {
        this.identity = value;
    }

    /**
     * Gets the value of the objectList property.
     * 
     * @return
     *     possible object is
     *     {@link TApplicationLayersExtension.ObjectList }
     *     
     */
    public TApplicationLayersExtension.ObjectList getObjectList() {
        return objectList;
    }

    /**
     * Sets the value of the objectList property.
     * 
     * @param value
     *     allowed object is
     *     {@link TApplicationLayersExtension.ObjectList }
     *     
     */
    public void setObjectList(TApplicationLayersExtension.ObjectList value) {
        this.objectList = value;
    }

    /**
     * Gets the value of the conformanceClass property.
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
     * Sets the value of the conformanceClass property.
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
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
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
         * Gets the value of the vendorID property.
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
         * Sets the value of the vendorID property.
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
         * Gets the value of the deviceFamily property.
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
         * Sets the value of the deviceFamily property.
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
         * Gets the value of the productID property.
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
         * Sets the value of the productID property.
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
         * Gets the value of the buildDate property.
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
         * Sets the value of the buildDate property.
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
         * Gets the value of the specificationRevision property.
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
         * Sets the value of the specificationRevision property.
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
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="Object" type="{http://www.ethernet-powerlink.org}t_Object_Extension" maxOccurs="65535"/&gt;
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
        protected List<TObjectExtension> object;
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
         * {@link TObjectExtension }
         * 
         * 
         */
        public List<TObjectExtension> getObject() {
            if (object == null) {
                object = new ArrayList<TObjectExtension>();
            }
            return this.object;
        }

        /**
         * Gets the value of the mandatoryObjects property.
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
         * Sets the value of the mandatoryObjects property.
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
         * Gets the value of the optionalObjects property.
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
         * Sets the value of the optionalObjects property.
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
         * Gets the value of the manufacturerObjects property.
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
         * Sets the value of the manufacturerObjects property.
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

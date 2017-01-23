
package org.epsg.openconfigurator.xmlbinding.projectfile;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


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
 *         &lt;element name="Firmware" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;attribute name="URI" use="required"&gt;
 *                   &lt;simpleType&gt;
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyURI"&gt;
 *                       &lt;minLength value="1"/&gt;
 *                     &lt;/restriction&gt;
 *                   &lt;/simpleType&gt;
 *                 &lt;/attribute&gt;
 *                 &lt;attribute name="vendorId" use="required"&gt;
 *                   &lt;simpleType&gt;
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}hexBinary"&gt;
 *                       &lt;minLength value="4"/&gt;
 *                       &lt;maxLength value="4"/&gt;
 *                     &lt;/restriction&gt;
 *                   &lt;/simpleType&gt;
 *                 &lt;/attribute&gt;
 *                 &lt;attribute name="productNumber" use="required"&gt;
 *                   &lt;simpleType&gt;
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}hexBinary"&gt;
 *                       &lt;minLength value="4"/&gt;
 *                       &lt;maxLength value="4"/&gt;
 *                     &lt;/restriction&gt;
 *                   &lt;/simpleType&gt;
 *                 &lt;/attribute&gt;
 *                 &lt;attribute name="deviceRevision" use="required"&gt;
 *                   &lt;simpleType&gt;
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}hexBinary"&gt;
 *                       &lt;minLength value="4"/&gt;
 *                       &lt;maxLength value="4"/&gt;
 *                     &lt;/restriction&gt;
 *                   &lt;/simpleType&gt;
 *                 &lt;/attribute&gt;
 *                 &lt;attribute name="date" use="required"&gt;
 *                   &lt;simpleType&gt;
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}hexBinary"&gt;
 *                       &lt;minLength value="4"/&gt;
 *                       &lt;maxLength value="4"/&gt;
 *                     &lt;/restriction&gt;
 *                   &lt;/simpleType&gt;
 *                 &lt;/attribute&gt;
 *                 &lt;attribute name="time" use="required"&gt;
 *                   &lt;simpleType&gt;
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}hexBinary"&gt;
 *                       &lt;minLength value="4"/&gt;
 *                       &lt;maxLength value="4"/&gt;
 *                     &lt;/restriction&gt;
 *                   &lt;/simpleType&gt;
 *                 &lt;/attribute&gt;
 *                 &lt;attribute name="keepHeader" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *                 &lt;attribute name="locked" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
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
@XmlType(name = "", propOrder = {
    "firmware"
})
@XmlRootElement(name = "FirmwareList")
public class FirmwareList {

    @XmlElement(name = "Firmware", required = true)
    protected List<FirmwareList.Firmware> firmware;

    /**
     * Gets the value of the firmware property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the firmware property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFirmware().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FirmwareList.Firmware }
     * 
     * 
     */
    public List<FirmwareList.Firmware> getFirmware() {
        if (firmware == null) {
            firmware = new ArrayList<FirmwareList.Firmware>();
        }
        return this.firmware;
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
     *       &lt;attribute name="URI" use="required"&gt;
     *         &lt;simpleType&gt;
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyURI"&gt;
     *             &lt;minLength value="1"/&gt;
     *           &lt;/restriction&gt;
     *         &lt;/simpleType&gt;
     *       &lt;/attribute&gt;
     *       &lt;attribute name="vendorId" use="required"&gt;
     *         &lt;simpleType&gt;
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}hexBinary"&gt;
     *             &lt;minLength value="4"/&gt;
     *             &lt;maxLength value="4"/&gt;
     *           &lt;/restriction&gt;
     *         &lt;/simpleType&gt;
     *       &lt;/attribute&gt;
     *       &lt;attribute name="productNumber" use="required"&gt;
     *         &lt;simpleType&gt;
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}hexBinary"&gt;
     *             &lt;minLength value="4"/&gt;
     *             &lt;maxLength value="4"/&gt;
     *           &lt;/restriction&gt;
     *         &lt;/simpleType&gt;
     *       &lt;/attribute&gt;
     *       &lt;attribute name="deviceRevision" use="required"&gt;
     *         &lt;simpleType&gt;
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}hexBinary"&gt;
     *             &lt;minLength value="4"/&gt;
     *             &lt;maxLength value="4"/&gt;
     *           &lt;/restriction&gt;
     *         &lt;/simpleType&gt;
     *       &lt;/attribute&gt;
     *       &lt;attribute name="date" use="required"&gt;
     *         &lt;simpleType&gt;
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}hexBinary"&gt;
     *             &lt;minLength value="4"/&gt;
     *             &lt;maxLength value="4"/&gt;
     *           &lt;/restriction&gt;
     *         &lt;/simpleType&gt;
     *       &lt;/attribute&gt;
     *       &lt;attribute name="time" use="required"&gt;
     *         &lt;simpleType&gt;
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}hexBinary"&gt;
     *             &lt;minLength value="4"/&gt;
     *             &lt;maxLength value="4"/&gt;
     *           &lt;/restriction&gt;
     *         &lt;/simpleType&gt;
     *       &lt;/attribute&gt;
     *       &lt;attribute name="keepHeader" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
     *       &lt;attribute name="locked" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Firmware {

        @XmlAttribute(name = "URI", required = true)
        protected String uri;
        @XmlAttribute(name = "vendorId", required = true)
        @XmlJavaTypeAdapter(HexBinaryAdapter.class)
        protected byte[] vendorId;
        @XmlAttribute(name = "productNumber", required = true)
        @XmlJavaTypeAdapter(HexBinaryAdapter.class)
        protected byte[] productNumber;
        @XmlAttribute(name = "deviceRevision", required = true)
        @XmlJavaTypeAdapter(HexBinaryAdapter.class)
        protected byte[] deviceRevision;
        @XmlAttribute(name = "date", required = true)
        @XmlJavaTypeAdapter(HexBinaryAdapter.class)
        protected byte[] date;
        @XmlAttribute(name = "time", required = true)
        @XmlJavaTypeAdapter(HexBinaryAdapter.class)
        protected byte[] time;
        @XmlAttribute(name = "keepHeader")
        protected Boolean keepHeader;
        @XmlAttribute(name = "locked")
        protected Boolean locked;

        /**
         * Gets the value of the uri property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getURI() {
            return uri;
        }

        /**
         * Sets the value of the uri property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setURI(String value) {
            this.uri = value;
        }

        /**
         * Gets the value of the vendorId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public byte[] getVendorId() {
            return vendorId;
        }

        /**
         * Sets the value of the vendorId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setVendorId(byte[] value) {
            this.vendorId = value;
        }

        /**
         * Gets the value of the productNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public byte[] getProductNumber() {
            return productNumber;
        }

        /**
         * Sets the value of the productNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProductNumber(byte[] value) {
            this.productNumber = value;
        }

        /**
         * Gets the value of the deviceRevision property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public byte[] getDeviceRevision() {
            return deviceRevision;
        }

        /**
         * Sets the value of the deviceRevision property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDeviceRevision(byte[] value) {
            this.deviceRevision = value;
        }

        /**
         * Gets the value of the date property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public byte[] getDate() {
            return date;
        }

        /**
         * Sets the value of the date property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDate(byte[] value) {
            this.date = value;
        }

        /**
         * Gets the value of the time property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public byte[] getTime() {
            return time;
        }

        /**
         * Sets the value of the time property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTime(byte[] value) {
            this.time = value;
        }

        /**
         * Gets the value of the keepHeader property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isKeepHeader() {
            if (keepHeader == null) {
                return false;
            } else {
                return keepHeader;
            }
        }

        /**
         * Sets the value of the keepHeader property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setKeepHeader(Boolean value) {
            this.keepHeader = value;
        }

        /**
         * Gets the value of the locked property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isLocked() {
            if (locked == null) {
                return false;
            } else {
                return locked;
            }
        }

        /**
         * Sets the value of the locked property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setLocked(Boolean value) {
            this.locked = value;
        }

    }

}

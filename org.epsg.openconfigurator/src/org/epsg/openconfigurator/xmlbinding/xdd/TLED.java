
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
 * <p>Java-Klasse für t_LED complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="t_LED"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;group ref="{http://www.ethernet-powerlink.org}g_labels"/&gt;
 *         &lt;element name="LEDstate" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;group ref="{http://www.ethernet-powerlink.org}g_labels"/&gt;
 *                 &lt;attribute name="uniqueID" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *                 &lt;attribute name="state" use="required"&gt;
 *                   &lt;simpleType&gt;
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                       &lt;enumeration value="on"/&gt;
 *                       &lt;enumeration value="off"/&gt;
 *                       &lt;enumeration value="flashing"/&gt;
 *                     &lt;/restriction&gt;
 *                   &lt;/simpleType&gt;
 *                 &lt;/attribute&gt;
 *                 &lt;attribute name="LEDcolor" use="required"&gt;
 *                   &lt;simpleType&gt;
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                       &lt;enumeration value="green"/&gt;
 *                       &lt;enumeration value="amber"/&gt;
 *                       &lt;enumeration value="red"/&gt;
 *                     &lt;/restriction&gt;
 *                   &lt;/simpleType&gt;
 *                 &lt;/attribute&gt;
 *                 &lt;attribute name="flashingPeriod" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *                 &lt;attribute name="impulsWidth" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" default="50" /&gt;
 *                 &lt;attribute name="numberOfImpulses" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" default="1" /&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="LEDcolors" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="monocolor"/&gt;
 *             &lt;enumeration value="bicolor"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="LEDtype"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="IO"/&gt;
 *             &lt;enumeration value="device"/&gt;
 *             &lt;enumeration value="communication"/&gt;
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
@XmlType(name = "t_LED", propOrder = {
    "labelOrDescriptionOrLabelRef",
    "leDstate"
})
public class TLED {

    @XmlElements({
        @XmlElement(name = "label", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Label.class),
        @XmlElement(name = "description", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Description.class),
        @XmlElement(name = "labelRef", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.LabelRef.class),
        @XmlElement(name = "descriptionRef", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.DescriptionRef.class)
    })
    protected List<Object> labelOrDescriptionOrLabelRef;
    @XmlElement(name = "LEDstate", required = true)
    protected List<TLED.LEDstate> leDstate;
    @XmlAttribute(name = "LEDcolors", required = true)
    protected String leDcolors;
    @XmlAttribute(name = "LEDtype")
    protected String leDtype;

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
     * Gets the value of the leDstate property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the leDstate property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLEDstate().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TLED.LEDstate }
     * 
     * 
     */
    public List<TLED.LEDstate> getLEDstate() {
        if (leDstate == null) {
            leDstate = new ArrayList<TLED.LEDstate>();
        }
        return this.leDstate;
    }

    /**
     * Ruft den Wert der leDcolors-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLEDcolors() {
        return leDcolors;
    }

    /**
     * Legt den Wert der leDcolors-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLEDcolors(String value) {
        this.leDcolors = value;
    }

    /**
     * Ruft den Wert der leDtype-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLEDtype() {
        return leDtype;
    }

    /**
     * Legt den Wert der leDtype-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLEDtype(String value) {
        this.leDtype = value;
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
     *       &lt;attribute name="uniqueID" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
     *       &lt;attribute name="state" use="required"&gt;
     *         &lt;simpleType&gt;
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *             &lt;enumeration value="on"/&gt;
     *             &lt;enumeration value="off"/&gt;
     *             &lt;enumeration value="flashing"/&gt;
     *           &lt;/restriction&gt;
     *         &lt;/simpleType&gt;
     *       &lt;/attribute&gt;
     *       &lt;attribute name="LEDcolor" use="required"&gt;
     *         &lt;simpleType&gt;
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *             &lt;enumeration value="green"/&gt;
     *             &lt;enumeration value="amber"/&gt;
     *             &lt;enumeration value="red"/&gt;
     *           &lt;/restriction&gt;
     *         &lt;/simpleType&gt;
     *       &lt;/attribute&gt;
     *       &lt;attribute name="flashingPeriod" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
     *       &lt;attribute name="impulsWidth" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" default="50" /&gt;
     *       &lt;attribute name="numberOfImpulses" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" default="1" /&gt;
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
    public static class LEDstate {

        @XmlElements({
            @XmlElement(name = "label", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Label.class),
            @XmlElement(name = "description", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.Description.class),
            @XmlElement(name = "labelRef", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.LabelRef.class),
            @XmlElement(name = "descriptionRef", type = org.epsg.openconfigurator.xmlbinding.xdd.ErrorBitDataType.DescriptionRef.class)
        })
        protected List<Object> labelOrDescriptionOrLabelRef;
        @XmlAttribute(name = "uniqueID", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlID
        @XmlSchemaType(name = "ID")
        protected String uniqueID;
        @XmlAttribute(name = "state", required = true)
        protected String state;
        @XmlAttribute(name = "LEDcolor", required = true)
        protected String leDcolor;
        @XmlAttribute(name = "flashingPeriod")
        @XmlSchemaType(name = "unsignedInt")
        protected Long flashingPeriod;
        @XmlAttribute(name = "impulsWidth")
        @XmlSchemaType(name = "unsignedByte")
        protected Short impulsWidth;
        @XmlAttribute(name = "numberOfImpulses")
        @XmlSchemaType(name = "unsignedByte")
        protected Short numberOfImpulses;

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
         * Ruft den Wert der state-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getState() {
            return state;
        }

        /**
         * Legt den Wert der state-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setState(String value) {
            this.state = value;
        }

        /**
         * Ruft den Wert der leDcolor-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLEDcolor() {
            return leDcolor;
        }

        /**
         * Legt den Wert der leDcolor-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLEDcolor(String value) {
            this.leDcolor = value;
        }

        /**
         * Ruft den Wert der flashingPeriod-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getFlashingPeriod() {
            return flashingPeriod;
        }

        /**
         * Legt den Wert der flashingPeriod-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setFlashingPeriod(Long value) {
            this.flashingPeriod = value;
        }

        /**
         * Ruft den Wert der impulsWidth-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Short }
         *     
         */
        public short getImpulsWidth() {
            if (impulsWidth == null) {
                return ((short) 50);
            } else {
                return impulsWidth;
            }
        }

        /**
         * Legt den Wert der impulsWidth-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Short }
         *     
         */
        public void setImpulsWidth(Short value) {
            this.impulsWidth = value;
        }

        /**
         * Ruft den Wert der numberOfImpulses-Eigenschaft ab.
         * 
         * @return
         *     possible object is
         *     {@link Short }
         *     
         */
        public short getNumberOfImpulses() {
            if (numberOfImpulses == null) {
                return ((short) 1);
            } else {
                return numberOfImpulses;
            }
        }

        /**
         * Legt den Wert der numberOfImpulses-Eigenschaft fest.
         * 
         * @param value
         *     allowed object is
         *     {@link Short }
         *     
         */
        public void setNumberOfImpulses(Short value) {
            this.numberOfImpulses = value;
        }

    }

}

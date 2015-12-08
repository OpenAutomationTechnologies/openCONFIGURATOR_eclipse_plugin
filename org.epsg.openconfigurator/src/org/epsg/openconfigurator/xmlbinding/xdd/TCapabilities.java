
package org.epsg.openconfigurator.xmlbinding.xdd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for t_capabilities complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_capabilities"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="characteristicsList" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="category" minOccurs="0"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;group ref="{http://www.ethernet-powerlink.org}g_labels"/&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                   &lt;element name="characteristic" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;sequence&gt;
 *                             &lt;element name="characteristicName"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;group ref="{http://www.ethernet-powerlink.org}g_labels"/&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                             &lt;element name="characteristicContent" maxOccurs="unbounded"&gt;
 *                               &lt;complexType&gt;
 *                                 &lt;complexContent&gt;
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                                     &lt;group ref="{http://www.ethernet-powerlink.org}g_labels"/&gt;
 *                                   &lt;/restriction&gt;
 *                                 &lt;/complexContent&gt;
 *                               &lt;/complexType&gt;
 *                             &lt;/element&gt;
 *                           &lt;/sequence&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="standardComplianceList" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="compliantWith" maxOccurs="unbounded"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;group ref="{http://www.ethernet-powerlink.org}g_labels"/&gt;
 *                           &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                           &lt;attribute name="range" default="international"&gt;
 *                             &lt;simpleType&gt;
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *                                 &lt;enumeration value="international"/&gt;
 *                                 &lt;enumeration value="internal"/&gt;
 *                               &lt;/restriction&gt;
 *                             &lt;/simpleType&gt;
 *                           &lt;/attribute&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
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
@XmlType(name = "t_capabilities", propOrder = {
    "characteristicsList",
    "standardComplianceList"
})
public class TCapabilities {

    @XmlElement(required = true)
    protected List<TCapabilities.CharacteristicsList> characteristicsList;
    protected TCapabilities.StandardComplianceList standardComplianceList;

    /**
     * Gets the value of the characteristicsList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the characteristicsList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCharacteristicsList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TCapabilities.CharacteristicsList }
     * 
     * 
     */
    public List<TCapabilities.CharacteristicsList> getCharacteristicsList() {
        if (characteristicsList == null) {
            characteristicsList = new ArrayList<TCapabilities.CharacteristicsList>();
        }
        return this.characteristicsList;
    }

    /**
     * Gets the value of the standardComplianceList property.
     * 
     * @return
     *     possible object is
     *     {@link TCapabilities.StandardComplianceList }
     *     
     */
    public TCapabilities.StandardComplianceList getStandardComplianceList() {
        return standardComplianceList;
    }

    /**
     * Sets the value of the standardComplianceList property.
     * 
     * @param value
     *     allowed object is
     *     {@link TCapabilities.StandardComplianceList }
     *     
     */
    public void setStandardComplianceList(TCapabilities.StandardComplianceList value) {
        this.standardComplianceList = value;
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
     *         &lt;element name="category" minOccurs="0"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;group ref="{http://www.ethernet-powerlink.org}g_labels"/&gt;
     *               &lt;/restriction&gt;
     *             &lt;/complexContent&gt;
     *           &lt;/complexType&gt;
     *         &lt;/element&gt;
     *         &lt;element name="characteristic" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;sequence&gt;
     *                   &lt;element name="characteristicName"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;group ref="{http://www.ethernet-powerlink.org}g_labels"/&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                   &lt;element name="characteristicContent" maxOccurs="unbounded"&gt;
     *                     &lt;complexType&gt;
     *                       &lt;complexContent&gt;
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                           &lt;group ref="{http://www.ethernet-powerlink.org}g_labels"/&gt;
     *                         &lt;/restriction&gt;
     *                       &lt;/complexContent&gt;
     *                     &lt;/complexType&gt;
     *                   &lt;/element&gt;
     *                 &lt;/sequence&gt;
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
        "category",
        "characteristic"
    })
    public static class CharacteristicsList {

        protected TCapabilities.CharacteristicsList.Category category;
        @XmlElement(required = true)
        protected List<TCapabilities.CharacteristicsList.Characteristic> characteristic;

        /**
         * Gets the value of the category property.
         * 
         * @return
         *     possible object is
         *     {@link TCapabilities.CharacteristicsList.Category }
         *     
         */
        public TCapabilities.CharacteristicsList.Category getCategory() {
            return category;
        }

        /**
         * Sets the value of the category property.
         * 
         * @param value
         *     allowed object is
         *     {@link TCapabilities.CharacteristicsList.Category }
         *     
         */
        public void setCategory(TCapabilities.CharacteristicsList.Category value) {
            this.category = value;
        }

        /**
         * Gets the value of the characteristic property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the characteristic property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCharacteristic().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TCapabilities.CharacteristicsList.Characteristic }
         * 
         * 
         */
        public List<TCapabilities.CharacteristicsList.Characteristic> getCharacteristic() {
            if (characteristic == null) {
                characteristic = new ArrayList<TCapabilities.CharacteristicsList.Characteristic>();
            }
            return this.characteristic;
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
         *       &lt;group ref="{http://www.ethernet-powerlink.org}g_labels"/&gt;
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
        public static class Category {

            @XmlElements({
                @XmlElement(name = "label", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.Label.class),
                @XmlElement(name = "description", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.Description.class),
                @XmlElement(name = "labelRef", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.LabelRef.class),
                @XmlElement(name = "descriptionRef", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.DescriptionRef.class)
            })
            protected List<Object> labelOrDescriptionOrLabelRef;

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
             * {@link org.epsg.openconfigurator.xmlbinding.xdd.Connector.Label }
             * {@link org.epsg.openconfigurator.xmlbinding.xdd.Connector.Description }
             * {@link org.epsg.openconfigurator.xmlbinding.xdd.Connector.LabelRef }
             * {@link org.epsg.openconfigurator.xmlbinding.xdd.Connector.DescriptionRef }
             * 
             * 
             */
            public List<Object> getLabelOrDescriptionOrLabelRef() {
                if (labelOrDescriptionOrLabelRef == null) {
                    labelOrDescriptionOrLabelRef = new ArrayList<Object>();
                }
                return this.labelOrDescriptionOrLabelRef;
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
         *         &lt;element name="characteristicName"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;group ref="{http://www.ethernet-powerlink.org}g_labels"/&gt;
         *               &lt;/restriction&gt;
         *             &lt;/complexContent&gt;
         *           &lt;/complexType&gt;
         *         &lt;/element&gt;
         *         &lt;element name="characteristicContent" maxOccurs="unbounded"&gt;
         *           &lt;complexType&gt;
         *             &lt;complexContent&gt;
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
         *                 &lt;group ref="{http://www.ethernet-powerlink.org}g_labels"/&gt;
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
            "characteristicName",
            "characteristicContent"
        })
        public static class Characteristic {

            @XmlElement(required = true)
            protected TCapabilities.CharacteristicsList.Characteristic.CharacteristicName characteristicName;
            @XmlElement(required = true)
            protected List<TCapabilities.CharacteristicsList.Characteristic.CharacteristicContent> characteristicContent;

            /**
             * Gets the value of the characteristicName property.
             * 
             * @return
             *     possible object is
             *     {@link TCapabilities.CharacteristicsList.Characteristic.CharacteristicName }
             *     
             */
            public TCapabilities.CharacteristicsList.Characteristic.CharacteristicName getCharacteristicName() {
                return characteristicName;
            }

            /**
             * Sets the value of the characteristicName property.
             * 
             * @param value
             *     allowed object is
             *     {@link TCapabilities.CharacteristicsList.Characteristic.CharacteristicName }
             *     
             */
            public void setCharacteristicName(TCapabilities.CharacteristicsList.Characteristic.CharacteristicName value) {
                this.characteristicName = value;
            }

            /**
             * Gets the value of the characteristicContent property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the characteristicContent property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getCharacteristicContent().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link TCapabilities.CharacteristicsList.Characteristic.CharacteristicContent }
             * 
             * 
             */
            public List<TCapabilities.CharacteristicsList.Characteristic.CharacteristicContent> getCharacteristicContent() {
                if (characteristicContent == null) {
                    characteristicContent = new ArrayList<TCapabilities.CharacteristicsList.Characteristic.CharacteristicContent>();
                }
                return this.characteristicContent;
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
             *       &lt;group ref="{http://www.ethernet-powerlink.org}g_labels"/&gt;
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
            public static class CharacteristicContent {

                @XmlElements({
                    @XmlElement(name = "label", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.Label.class),
                    @XmlElement(name = "description", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.Description.class),
                    @XmlElement(name = "labelRef", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.LabelRef.class),
                    @XmlElement(name = "descriptionRef", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.DescriptionRef.class)
                })
                protected List<Object> labelOrDescriptionOrLabelRef;

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
                 * {@link org.epsg.openconfigurator.xmlbinding.xdd.Connector.Label }
                 * {@link org.epsg.openconfigurator.xmlbinding.xdd.Connector.Description }
                 * {@link org.epsg.openconfigurator.xmlbinding.xdd.Connector.LabelRef }
                 * {@link org.epsg.openconfigurator.xmlbinding.xdd.Connector.DescriptionRef }
                 * 
                 * 
                 */
                public List<Object> getLabelOrDescriptionOrLabelRef() {
                    if (labelOrDescriptionOrLabelRef == null) {
                        labelOrDescriptionOrLabelRef = new ArrayList<Object>();
                    }
                    return this.labelOrDescriptionOrLabelRef;
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
             *       &lt;group ref="{http://www.ethernet-powerlink.org}g_labels"/&gt;
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
            public static class CharacteristicName {

                @XmlElements({
                    @XmlElement(name = "label", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.Label.class),
                    @XmlElement(name = "description", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.Description.class),
                    @XmlElement(name = "labelRef", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.LabelRef.class),
                    @XmlElement(name = "descriptionRef", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.DescriptionRef.class)
                })
                protected List<Object> labelOrDescriptionOrLabelRef;

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
                 * {@link org.epsg.openconfigurator.xmlbinding.xdd.Connector.Label }
                 * {@link org.epsg.openconfigurator.xmlbinding.xdd.Connector.Description }
                 * {@link org.epsg.openconfigurator.xmlbinding.xdd.Connector.LabelRef }
                 * {@link org.epsg.openconfigurator.xmlbinding.xdd.Connector.DescriptionRef }
                 * 
                 * 
                 */
                public List<Object> getLabelOrDescriptionOrLabelRef() {
                    if (labelOrDescriptionOrLabelRef == null) {
                        labelOrDescriptionOrLabelRef = new ArrayList<Object>();
                    }
                    return this.labelOrDescriptionOrLabelRef;
                }

            }

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
     *         &lt;element name="compliantWith" maxOccurs="unbounded"&gt;
     *           &lt;complexType&gt;
     *             &lt;complexContent&gt;
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *                 &lt;group ref="{http://www.ethernet-powerlink.org}g_labels"/&gt;
     *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *                 &lt;attribute name="range" default="international"&gt;
     *                   &lt;simpleType&gt;
     *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
     *                       &lt;enumeration value="international"/&gt;
     *                       &lt;enumeration value="internal"/&gt;
     *                     &lt;/restriction&gt;
     *                   &lt;/simpleType&gt;
     *                 &lt;/attribute&gt;
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
        "compliantWith"
    })
    public static class StandardComplianceList {

        @XmlElement(required = true)
        protected List<TCapabilities.StandardComplianceList.CompliantWith> compliantWith;

        /**
         * Gets the value of the compliantWith property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the compliantWith property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getCompliantWith().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TCapabilities.StandardComplianceList.CompliantWith }
         * 
         * 
         */
        public List<TCapabilities.StandardComplianceList.CompliantWith> getCompliantWith() {
            if (compliantWith == null) {
                compliantWith = new ArrayList<TCapabilities.StandardComplianceList.CompliantWith>();
            }
            return this.compliantWith;
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
         *       &lt;group ref="{http://www.ethernet-powerlink.org}g_labels"/&gt;
         *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
         *       &lt;attribute name="range" default="international"&gt;
         *         &lt;simpleType&gt;
         *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
         *             &lt;enumeration value="international"/&gt;
         *             &lt;enumeration value="internal"/&gt;
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
        @XmlType(name = "", propOrder = {
            "labelOrDescriptionOrLabelRef"
        })
        public static class CompliantWith {

            @XmlElements({
                @XmlElement(name = "label", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.Label.class),
                @XmlElement(name = "description", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.Description.class),
                @XmlElement(name = "labelRef", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.LabelRef.class),
                @XmlElement(name = "descriptionRef", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.DescriptionRef.class)
            })
            protected List<Object> labelOrDescriptionOrLabelRef;
            @XmlAttribute(name = "name", required = true)
            protected String name;
            @XmlAttribute(name = "range")
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            protected String range;

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
             * {@link org.epsg.openconfigurator.xmlbinding.xdd.Connector.Label }
             * {@link org.epsg.openconfigurator.xmlbinding.xdd.Connector.Description }
             * {@link org.epsg.openconfigurator.xmlbinding.xdd.Connector.LabelRef }
             * {@link org.epsg.openconfigurator.xmlbinding.xdd.Connector.DescriptionRef }
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
             * Gets the value of the range property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRange() {
                if (range == null) {
                    return "international";
                } else {
                    return range;
                }
            }

            /**
             * Sets the value of the range property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRange(String value) {
                this.range = value;
            }

        }

    }

}

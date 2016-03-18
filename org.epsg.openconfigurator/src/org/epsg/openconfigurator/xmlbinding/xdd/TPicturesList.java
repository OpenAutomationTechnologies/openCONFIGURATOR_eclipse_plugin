
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
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for t_picturesList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_picturesList"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="picture" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;group ref="{http://www.ethernet-powerlink.org}g_labels"/&gt;
 *                 &lt;attribute name="URI" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" /&gt;
 *                 &lt;attribute name="type" default="none"&gt;
 *                   &lt;simpleType&gt;
 *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *                       &lt;enumeration value="frontPicture"/&gt;
 *                       &lt;enumeration value="icon"/&gt;
 *                       &lt;enumeration value="additional"/&gt;
 *                       &lt;enumeration value="none"/&gt;
 *                     &lt;/restriction&gt;
 *                   &lt;/simpleType&gt;
 *                 &lt;/attribute&gt;
 *                 &lt;attribute name="number" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
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
@XmlType(name = "t_picturesList", propOrder = {
    "picture"
})
public class TPicturesList {

    @XmlElement(required = true)
    protected List<TPicturesList.Picture> picture;

    /**
     * Gets the value of the picture property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the picture property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPicture().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TPicturesList.Picture }
     * 
     * 
     */
    public List<TPicturesList.Picture> getPicture() {
        if (picture == null) {
            picture = new ArrayList<TPicturesList.Picture>();
        }
        return this.picture;
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
     *       &lt;attribute name="URI" use="required" type="{http://www.w3.org/2001/XMLSchema}anyURI" /&gt;
     *       &lt;attribute name="type" default="none"&gt;
     *         &lt;simpleType&gt;
     *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
     *             &lt;enumeration value="frontPicture"/&gt;
     *             &lt;enumeration value="icon"/&gt;
     *             &lt;enumeration value="additional"/&gt;
     *             &lt;enumeration value="none"/&gt;
     *           &lt;/restriction&gt;
     *         &lt;/simpleType&gt;
     *       &lt;/attribute&gt;
     *       &lt;attribute name="number" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
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
    public static class Picture {

        @XmlElements({
            @XmlElement(name = "label", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.Label.class),
            @XmlElement(name = "description", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.Description.class),
            @XmlElement(name = "labelRef", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.LabelRef.class),
            @XmlElement(name = "descriptionRef", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.DescriptionRef.class)
        })
        protected List<Object> labelOrDescriptionOrLabelRef;
        @XmlAttribute(name = "URI", required = true)
        @XmlSchemaType(name = "anyURI")
        protected String uri;
        @XmlAttribute(name = "type")
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String type;
        @XmlAttribute(name = "number")
        @XmlSchemaType(name = "unsignedInt")
        protected Long number;

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
         * Gets the value of the type property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getType() {
            if (type == null) {
                return "none";
            } else {
                return type;
            }
        }

        /**
         * Sets the value of the type property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setType(String value) {
            this.type = value;
        }

        /**
         * Gets the value of the number property.
         * 
         * @return
         *     possible object is
         *     {@link Long }
         *     
         */
        public Long getNumber() {
            return number;
        }

        /**
         * Sets the value of the number property.
         * 
         * @param value
         *     allowed object is
         *     {@link Long }
         *     
         */
        public void setNumber(Long value) {
            this.number = value;
        }

    }

}

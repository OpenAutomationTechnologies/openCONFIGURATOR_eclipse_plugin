
package org.epsg.openconfigurator.xmlbinding.xdd;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
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
 *       &lt;group ref="{http://www.ethernet-powerlink.org}g_labels"/&gt;
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="posX" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *       &lt;attribute name="posY" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *       &lt;attribute name="connectorType" type="{http://www.w3.org/2001/XMLSchema}string" default="POWERLINK" /&gt;
 *       &lt;attribute name="interfaceIDRef" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="positioning" default="remote"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="remote"/&gt;
 *             &lt;enumeration value="localAbove"/&gt;
 *             &lt;enumeration value="localBelow"/&gt;
 *             &lt;enumeration value="localLeft"/&gt;
 *             &lt;enumeration value="localRight"/&gt;
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
@XmlRootElement(name = "connector")
public class Connector {

    @XmlElements({
        @XmlElement(name = "label", type = Connector.Label.class),
        @XmlElement(name = "description", type = Connector.Description.class),
        @XmlElement(name = "labelRef", type = Connector.LabelRef.class),
        @XmlElement(name = "descriptionRef", type = Connector.DescriptionRef.class)
    })
    protected List<Object> labelOrDescriptionOrLabelRef;
    @XmlAttribute(name = "id", required = true)
    protected String id;
    @XmlAttribute(name = "posX")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger posX;
    @XmlAttribute(name = "posY")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger posY;
    @XmlAttribute(name = "connectorType")
    protected String connectorType;
    @XmlAttribute(name = "interfaceIDRef")
    protected String interfaceIDRef;
    @XmlAttribute(name = "positioning")
    protected String positioning;

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
     * {@link Connector.Label }
     * {@link Connector.Description }
     * {@link Connector.LabelRef }
     * {@link Connector.DescriptionRef }
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
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the posX property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPosX() {
        return posX;
    }

    /**
     * Sets the value of the posX property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPosX(BigInteger value) {
        this.posX = value;
    }

    /**
     * Gets the value of the posY property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPosY() {
        return posY;
    }

    /**
     * Sets the value of the posY property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPosY(BigInteger value) {
        this.posY = value;
    }

    /**
     * Gets the value of the connectorType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConnectorType() {
        if (connectorType == null) {
            return "POWERLINK";
        } else {
            return connectorType;
        }
    }

    /**
     * Sets the value of the connectorType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConnectorType(String value) {
        this.connectorType = value;
    }

    /**
     * Gets the value of the interfaceIDRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInterfaceIDRef() {
        return interfaceIDRef;
    }

    /**
     * Sets the value of the interfaceIDRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInterfaceIDRef(String value) {
        this.interfaceIDRef = value;
    }

    /**
     * Gets the value of the positioning property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPositioning() {
        if (positioning == null) {
            return "remote";
        } else {
            return positioning;
        }
    }

    /**
     * Sets the value of the positioning property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPositioning(String value) {
        this.positioning = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;simpleContent&gt;
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
     *       &lt;attribute name="lang" use="required" type="{http://www.w3.org/2001/XMLSchema}language" /&gt;
     *       &lt;attribute name="URI" type="{http://www.w3.org/2001/XMLSchema}anyURI" /&gt;
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
    public static class Description {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "lang", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "language")
        protected String lang;
        @XmlAttribute(name = "URI")
        @XmlSchemaType(name = "anyURI")
        protected String uri;

        /**
         * Gets the value of the value property.
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
         * Sets the value of the value property.
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
         * Gets the value of the lang property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLang() {
            return lang;
        }

        /**
         * Sets the value of the lang property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLang(String value) {
            this.lang = value;
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

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;simpleContent&gt;
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;anyURI"&gt;
     *       &lt;attribute name="dictID" use="required" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" /&gt;
     *       &lt;attribute name="textID" use="required" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" /&gt;
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
    public static class DescriptionRef {

        @XmlValue
        @XmlSchemaType(name = "anyURI")
        protected String value;
        @XmlAttribute(name = "dictID", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "NMTOKEN")
        protected String dictID;
        @XmlAttribute(name = "textID", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "NMTOKEN")
        protected String textID;

        /**
         * Gets the value of the value property.
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
         * Sets the value of the value property.
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
         * Gets the value of the dictID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDictID() {
            return dictID;
        }

        /**
         * Sets the value of the dictID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDictID(String value) {
            this.dictID = value;
        }

        /**
         * Gets the value of the textID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTextID() {
            return textID;
        }

        /**
         * Sets the value of the textID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTextID(String value) {
            this.textID = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;simpleContent&gt;
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;string"&gt;
     *       &lt;attribute name="lang" use="required" type="{http://www.w3.org/2001/XMLSchema}language" /&gt;
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
    public static class Label {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "lang", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "language")
        protected String lang;

        /**
         * Gets the value of the value property.
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
         * Sets the value of the value property.
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
         * Gets the value of the lang property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLang() {
            return lang;
        }

        /**
         * Sets the value of the lang property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLang(String value) {
            this.lang = value;
        }

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;simpleContent&gt;
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;anyURI"&gt;
     *       &lt;attribute name="dictID" use="required" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" /&gt;
     *       &lt;attribute name="textID" use="required" type="{http://www.w3.org/2001/XMLSchema}NMTOKEN" /&gt;
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
    public static class LabelRef {

        @XmlValue
        @XmlSchemaType(name = "anyURI")
        protected String value;
        @XmlAttribute(name = "dictID", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "NMTOKEN")
        protected String dictID;
        @XmlAttribute(name = "textID", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "NMTOKEN")
        protected String textID;

        /**
         * Gets the value of the value property.
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
         * Sets the value of the value property.
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
         * Gets the value of the dictID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDictID() {
            return dictID;
        }

        /**
         * Sets the value of the dictID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDictID(String value) {
            this.dictID = value;
        }

        /**
         * Gets the value of the textID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTextID() {
            return textID;
        }

        /**
         * Sets the value of the textID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTextID(String value) {
            this.textID = value;
        }

    }

}

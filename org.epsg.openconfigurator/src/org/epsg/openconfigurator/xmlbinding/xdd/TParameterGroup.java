
package org.epsg.openconfigurator.xmlbinding.xdd;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for t_parameterGroup complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_parameterGroup"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;group ref="{http://www.ethernet-powerlink.org}g_labels"/&gt;
 *         &lt;choice maxOccurs="unbounded"&gt;
 *           &lt;element name="parameterGroup" type="{http://www.ethernet-powerlink.org}t_parameterGroup" maxOccurs="unbounded" minOccurs="0"/&gt;
 *           &lt;element name="parameterRef" maxOccurs="unbounded" minOccurs="0"&gt;
 *             &lt;complexType&gt;
 *               &lt;complexContent&gt;
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                   &lt;attribute name="uniqueIDRef" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" /&gt;
 *                   &lt;attribute name="actualValue" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *                   &lt;attribute name="visible" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *                   &lt;attribute name="locked" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *                   &lt;attribute name="bitOffset" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" default="0" /&gt;
 *                 &lt;/restriction&gt;
 *               &lt;/complexContent&gt;
 *             &lt;/complexType&gt;
 *           &lt;/element&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="uniqueID" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *       &lt;attribute name="kindOfAccess" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="groupLevelVisible" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="conditionalUniqueIDRef" type="{http://www.w3.org/2001/XMLSchema}IDREF" /&gt;
 *       &lt;attribute name="conditionalValue" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="bitOffset" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" default="0" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_parameterGroup", propOrder = {
    "labelOrDescriptionOrLabelRef",
    "parameterGroupOrParameterRef"
})
public class TParameterGroup {

    @XmlElements({
        @XmlElement(name = "label", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.Label.class),
        @XmlElement(name = "description", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.Description.class),
        @XmlElement(name = "labelRef", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.LabelRef.class),
        @XmlElement(name = "descriptionRef", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.DescriptionRef.class)
    })
    protected List<Object> labelOrDescriptionOrLabelRef;
    @XmlElements({
        @XmlElement(name = "parameterGroup", type = TParameterGroup.class),
        @XmlElement(name = "parameterRef", type = TParameterGroup.ParameterRef.class)
    })
    protected List<Object> parameterGroupOrParameterRef;
    @XmlAttribute(name = "uniqueID", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String uniqueID;
    @XmlAttribute(name = "kindOfAccess")
    protected String kindOfAccess;
    @XmlAttribute(name = "groupLevelVisible")
    protected Boolean groupLevelVisible;
    @XmlAttribute(name = "conditionalUniqueIDRef")
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object conditionalUniqueIDRef;
    @XmlAttribute(name = "conditionalValue")
    protected String conditionalValue;
    @XmlAttribute(name = "bitOffset")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger bitOffset;

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
     * Gets the value of the parameterGroupOrParameterRef property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parameterGroupOrParameterRef property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParameterGroupOrParameterRef().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TParameterGroup }
     * {@link TParameterGroup.ParameterRef }
     * 
     * 
     */
    public List<Object> getParameterGroupOrParameterRef() {
        if (parameterGroupOrParameterRef == null) {
            parameterGroupOrParameterRef = new ArrayList<Object>();
        }
        return this.parameterGroupOrParameterRef;
    }

    /**
     * Gets the value of the uniqueID property.
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
     * Sets the value of the uniqueID property.
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
     * Gets the value of the kindOfAccess property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKindOfAccess() {
        return kindOfAccess;
    }

    /**
     * Sets the value of the kindOfAccess property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKindOfAccess(String value) {
        this.kindOfAccess = value;
    }

    /**
     * Gets the value of the groupLevelVisible property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isGroupLevelVisible() {
        if (groupLevelVisible == null) {
            return false;
        } else {
            return groupLevelVisible;
        }
    }

    /**
     * Sets the value of the groupLevelVisible property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setGroupLevelVisible(Boolean value) {
        this.groupLevelVisible = value;
    }

    /**
     * Gets the value of the conditionalUniqueIDRef property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getConditionalUniqueIDRef() {
        return conditionalUniqueIDRef;
    }

    /**
     * Sets the value of the conditionalUniqueIDRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setConditionalUniqueIDRef(Object value) {
        this.conditionalUniqueIDRef = value;
    }

    /**
     * Gets the value of the conditionalValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConditionalValue() {
        return conditionalValue;
    }

    /**
     * Sets the value of the conditionalValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConditionalValue(String value) {
        this.conditionalValue = value;
    }

    /**
     * Gets the value of the bitOffset property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getBitOffset() {
        if (bitOffset == null) {
            return new BigInteger("0");
        } else {
            return bitOffset;
        }
    }

    /**
     * Sets the value of the bitOffset property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setBitOffset(BigInteger value) {
        this.bitOffset = value;
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
     *       &lt;attribute name="uniqueIDRef" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" /&gt;
     *       &lt;attribute name="actualValue" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
     *       &lt;attribute name="visible" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
     *       &lt;attribute name="locked" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
     *       &lt;attribute name="bitOffset" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" default="0" /&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class ParameterRef {

        @XmlAttribute(name = "uniqueIDRef", required = true)
        @XmlIDREF
        @XmlSchemaType(name = "IDREF")
        protected Object uniqueIDRef;
        @XmlAttribute(name = "actualValue")
        protected String actualValue;
        @XmlAttribute(name = "visible")
        protected Boolean visible;
        @XmlAttribute(name = "locked")
        protected Boolean locked;
        @XmlAttribute(name = "bitOffset")
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger bitOffset;

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
         * Gets the value of the visible property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public boolean isVisible() {
            if (visible == null) {
                return false;
            } else {
                return visible;
            }
        }

        /**
         * Sets the value of the visible property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setVisible(Boolean value) {
            this.visible = value;
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

        /**
         * Gets the value of the bitOffset property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getBitOffset() {
            if (bitOffset == null) {
                return new BigInteger("0");
            } else {
                return bitOffset;
            }
        }

        /**
         * Sets the value of the bitOffset property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setBitOffset(BigInteger value) {
            this.bitOffset = value;
        }

    }

}

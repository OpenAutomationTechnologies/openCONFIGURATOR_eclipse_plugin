
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
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for t_modularChildConnector complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_modularChildConnector"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;group ref="{http://www.ethernet-powerlink.org}g_labels"/&gt;
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" /&gt;
 *       &lt;attribute name="posX" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *       &lt;attribute name="posY" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *       &lt;attribute name="connectorType" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="interfaceIDRef" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
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
@XmlType(name = "t_modularChildConnector", propOrder = {
    "labelOrDescriptionOrLabelRef"
})
public class TModularChildConnector {

    @XmlElements({
        @XmlElement(name = "label", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.Label.class),
        @XmlElement(name = "description", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.Description.class),
        @XmlElement(name = "labelRef", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.LabelRef.class),
        @XmlElement(name = "descriptionRef", type = org.epsg.openconfigurator.xmlbinding.xdd.Connector.DescriptionRef.class)
    })
    protected List<Object> labelOrDescriptionOrLabelRef;
    @XmlAttribute(name = "id", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String id;
    @XmlAttribute(name = "posX")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger posX;
    @XmlAttribute(name = "posY")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger posY;
    @XmlAttribute(name = "connectorType", required = true)
    protected String connectorType;
    @XmlAttribute(name = "interfaceIDRef", required = true)
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
        return connectorType;
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

}

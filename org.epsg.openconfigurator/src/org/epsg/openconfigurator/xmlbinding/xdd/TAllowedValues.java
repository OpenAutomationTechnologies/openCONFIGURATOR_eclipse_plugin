
package org.epsg.openconfigurator.xmlbinding.xdd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for t_allowedValues complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_allowedValues"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="value" type="{http://www.ethernet-powerlink.org}t_value" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="range" type="{http://www.ethernet-powerlink.org}t_range" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="templateIDRef" type="{http://www.w3.org/2001/XMLSchema}IDREF" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_allowedValues", propOrder = {
    "value",
    "range"
})
public class TAllowedValues {

    protected List<TValue> value;
    protected List<TRange> range;
    @XmlAttribute(name = "templateIDRef")
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object templateIDRef;

    /**
     * Gets the value of the value property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the value property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TValue }
     * 
     * 
     */
    public List<TValue> getValue() {
        if (value == null) {
            value = new ArrayList<TValue>();
        }
        return this.value;
    }

    /**
     * Gets the value of the range property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the range property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRange().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TRange }
     * 
     * 
     */
    public List<TRange> getRange() {
        if (range == null) {
            range = new ArrayList<TRange>();
        }
        return this.range;
    }

    /**
     * Gets the value of the templateIDRef property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getTemplateIDRef() {
        return templateIDRef;
    }

    /**
     * Sets the value of the templateIDRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setTemplateIDRef(Object value) {
        this.templateIDRef = value;
    }

}

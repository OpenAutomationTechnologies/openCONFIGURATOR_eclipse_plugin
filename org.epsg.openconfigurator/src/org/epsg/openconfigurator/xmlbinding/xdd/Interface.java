
package org.epsg.openconfigurator.xmlbinding.xdd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element ref="{http://www.ethernet-powerlink.org}rangeList"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="uniqueIDRef" use="required" type="{http://www.w3.org/2001/XMLSchema}IDREF" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "rangeList"
})
@XmlRootElement(name = "interface")
public class Interface {

    @XmlElement(required = true)
    protected RangeList rangeList;
    @XmlAttribute(name = "uniqueIDRef", required = true)
    @XmlIDREF
    @XmlSchemaType(name = "IDREF")
    protected Object uniqueIDRef;

    /**
     * An interface shall define a range list to define the indices that can be assigned to module objects.
     *                     
     * 
     * @return
     *     possible object is
     *     {@link RangeList }
     *     
     */
    public RangeList getRangeList() {
        return rangeList;
    }

    /**
     * Sets the value of the rangeList property.
     * 
     * @param value
     *     allowed object is
     *     {@link RangeList }
     *     
     */
    public void setRangeList(RangeList value) {
        this.rangeList = value;
    }

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

}


package org.epsg.openconfigurator.xmlbinding.xdd;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *       &lt;attribute name="childIDRef" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="position" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
 *       &lt;attribute name="address" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "connectedModule")
public class ConnectedModule {

    @XmlAttribute(name = "childIDRef", required = true)
    protected String childIDRef;
    @XmlAttribute(name = "position", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger position;
    @XmlAttribute(name = "address")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger address;

    /**
     * Gets the value of the childIDRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChildIDRef() {
        return childIDRef;
    }

    /**
     * Sets the value of the childIDRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChildIDRef(String value) {
        this.childIDRef = value;
    }

    /**
     * Gets the value of the position property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPosition() {
        return position;
    }

    /**
     * Sets the value of the position property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPosition(BigInteger value) {
        this.position = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setAddress(BigInteger value) {
        this.address = value;
    }

}

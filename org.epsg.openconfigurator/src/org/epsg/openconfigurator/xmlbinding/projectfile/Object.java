
package org.epsg.openconfigurator.xmlbinding.projectfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
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
 *       &lt;attribute name="index" type="{http://ethernet-powerlink.org/POWERLINK}tParameterIndex" /&gt;
 *       &lt;attribute name="subindex" type="{http://ethernet-powerlink.org/POWERLINK}tParameterSubIndex" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "Object")
public class Object {

    @XmlAttribute(name = "index")
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    protected byte[] index;
    @XmlAttribute(name = "subindex")
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    protected byte[] subindex;

    /**
     * Gets the value of the index property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getIndex() {
        return index;
    }

    /**
     * Sets the value of the index property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndex(byte[] value) {
        this.index = value;
    }

    /**
     * Gets the value of the subindex property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getSubindex() {
        return subindex;
    }

    /**
     * Sets the value of the subindex property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubindex(byte[] value) {
        this.subindex = value;
    }

}

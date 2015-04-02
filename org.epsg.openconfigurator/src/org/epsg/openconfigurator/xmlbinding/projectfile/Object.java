
package org.epsg.openconfigurator.xmlbinding.projectfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java-Klasse für anonymous complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
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
     * Ruft den Wert der index-Eigenschaft ab.
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
     * Legt den Wert der index-Eigenschaft fest.
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
     * Ruft den Wert der subindex-Eigenschaft ab.
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
     * Legt den Wert der subindex-Eigenschaft fest.
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

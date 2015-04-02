
package org.epsg.openconfigurator.xmlbinding.xdd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für t_subrange complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="t_subrange"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="lowerLimit" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *       &lt;attribute name="upperLimit" use="required" type="{http://www.w3.org/2001/XMLSchema}long" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_subrange")
public class TSubrange {

    @XmlAttribute(name = "lowerLimit", required = true)
    protected long lowerLimit;
    @XmlAttribute(name = "upperLimit", required = true)
    protected long upperLimit;

    /**
     * Ruft den Wert der lowerLimit-Eigenschaft ab.
     * 
     */
    public long getLowerLimit() {
        return lowerLimit;
    }

    /**
     * Legt den Wert der lowerLimit-Eigenschaft fest.
     * 
     */
    public void setLowerLimit(long value) {
        this.lowerLimit = value;
    }

    /**
     * Ruft den Wert der upperLimit-Eigenschaft ab.
     * 
     */
    public long getUpperLimit() {
        return upperLimit;
    }

    /**
     * Legt den Wert der upperLimit-Eigenschaft fest.
     * 
     */
    public void setUpperLimit(long value) {
        this.upperLimit = value;
    }

}


package org.epsg.openconfigurator.xmlbinding.xdd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für t_CNFeatures complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="t_CNFeatures"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="DLLCNFeatureMultiplex" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="DLLCNPResChaining" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="NMTCNSoC2PReq" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_CNFeatures")
public class TCNFeatures {

    @XmlAttribute(name = "DLLCNFeatureMultiplex")
    protected Boolean dllcnFeatureMultiplex;
    @XmlAttribute(name = "DLLCNPResChaining")
    protected Boolean dllcnpResChaining;
    @XmlAttribute(name = "NMTCNSoC2PReq", required = true)
    @XmlSchemaType(name = "unsignedInt")
    protected long nmtcnSoC2PReq;

    /**
     * Ruft den Wert der dllcnFeatureMultiplex-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isDLLCNFeatureMultiplex() {
        if (dllcnFeatureMultiplex == null) {
            return false;
        } else {
            return dllcnFeatureMultiplex;
        }
    }

    /**
     * Legt den Wert der dllcnFeatureMultiplex-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDLLCNFeatureMultiplex(Boolean value) {
        this.dllcnFeatureMultiplex = value;
    }

    /**
     * Ruft den Wert der dllcnpResChaining-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isDLLCNPResChaining() {
        if (dllcnpResChaining == null) {
            return false;
        } else {
            return dllcnpResChaining;
        }
    }

    /**
     * Legt den Wert der dllcnpResChaining-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDLLCNPResChaining(Boolean value) {
        this.dllcnpResChaining = value;
    }

    /**
     * Ruft den Wert der nmtcnSoC2PReq-Eigenschaft ab.
     * 
     */
    public long getNMTCNSoC2PReq() {
        return nmtcnSoC2PReq;
    }

    /**
     * Legt den Wert der nmtcnSoC2PReq-Eigenschaft fest.
     * 
     */
    public void setNMTCNSoC2PReq(long value) {
        this.nmtcnSoC2PReq = value;
    }

}


package org.epsg.openconfigurator.xmlbinding.xdd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for t_CNFeatures complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
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
     * Gets the value of the dllcnFeatureMultiplex property.
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
     * Sets the value of the dllcnFeatureMultiplex property.
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
     * Gets the value of the dllcnpResChaining property.
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
     * Sets the value of the dllcnpResChaining property.
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
     * Gets the value of the nmtcnSoC2PReq property.
     * 
     */
    public long getNMTCNSoC2PReq() {
        return nmtcnSoC2PReq;
    }

    /**
     * Sets the value of the nmtcnSoC2PReq property.
     * 
     */
    public void setNMTCNSoC2PReq(long value) {
        this.nmtcnSoC2PReq = value;
    }

}

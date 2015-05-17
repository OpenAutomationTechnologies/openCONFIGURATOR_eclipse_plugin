
package org.epsg.openconfigurator.xmlbinding.xdd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for t_MNFeatures complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_MNFeatures"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="DLLErrMNMultipleMN" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="DLLMNFeatureMultiplex" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
 *       &lt;attribute name="DLLMNPResChaining" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="DLLMNFeaturePResTx" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" /&gt;
 *       &lt;attribute name="NMTMNASnd2SoC" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *       &lt;attribute name="NMTMNBasicEthernet" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="NMTMNMultiplCycMax" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" default="0" /&gt;
 *       &lt;attribute name="NMTMNPRes2PReq" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *       &lt;attribute name="NMTMNPRes2PRes" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *       &lt;attribute name="NMTMNPResRx2SoA" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *       &lt;attribute name="NMTMNPResTx2SoA" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *       &lt;attribute name="NMTMNSoA2ASndTx" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *       &lt;attribute name="NMTMNSoC2PReq" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *       &lt;attribute name="NMTNetTime" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="NMTNetTimeIsRealTime" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="NMTRelativeTime" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="NMTSimpleBoot" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" /&gt;
 *       &lt;attribute name="PDOTPDOChannels" type="{http://www.w3.org/2001/XMLSchema}unsignedShort" default="256" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_MNFeatures")
public class TMNFeatures {

    @XmlAttribute(name = "DLLErrMNMultipleMN")
    protected Boolean dllErrMNMultipleMN;
    @XmlAttribute(name = "DLLMNFeatureMultiplex")
    protected Boolean dllmnFeatureMultiplex;
    @XmlAttribute(name = "DLLMNPResChaining")
    protected Boolean dllmnpResChaining;
    @XmlAttribute(name = "DLLMNFeaturePResTx")
    protected Boolean dllmnFeaturePResTx;
    @XmlAttribute(name = "NMTMNASnd2SoC", required = true)
    @XmlSchemaType(name = "unsignedInt")
    protected long nmtmnaSnd2SoC;
    @XmlAttribute(name = "NMTMNBasicEthernet")
    protected Boolean nmtmnBasicEthernet;
    @XmlAttribute(name = "NMTMNMultiplCycMax")
    @XmlSchemaType(name = "unsignedByte")
    protected Short nmtmnMultiplCycMax;
    @XmlAttribute(name = "NMTMNPRes2PReq", required = true)
    @XmlSchemaType(name = "unsignedInt")
    protected long nmtmnpRes2PReq;
    @XmlAttribute(name = "NMTMNPRes2PRes", required = true)
    @XmlSchemaType(name = "unsignedInt")
    protected long nmtmnpRes2PRes;
    @XmlAttribute(name = "NMTMNPResRx2SoA", required = true)
    @XmlSchemaType(name = "unsignedInt")
    protected long nmtmnpResRx2SoA;
    @XmlAttribute(name = "NMTMNPResTx2SoA", required = true)
    @XmlSchemaType(name = "unsignedInt")
    protected long nmtmnpResTx2SoA;
    @XmlAttribute(name = "NMTMNSoA2ASndTx", required = true)
    @XmlSchemaType(name = "unsignedInt")
    protected long nmtmnSoA2ASndTx;
    @XmlAttribute(name = "NMTMNSoC2PReq", required = true)
    @XmlSchemaType(name = "unsignedInt")
    protected long nmtmnSoC2PReq;
    @XmlAttribute(name = "NMTNetTime")
    protected Boolean nmtNetTime;
    @XmlAttribute(name = "NMTNetTimeIsRealTime")
    protected Boolean nmtNetTimeIsRealTime;
    @XmlAttribute(name = "NMTRelativeTime")
    protected Boolean nmtRelativeTime;
    @XmlAttribute(name = "NMTSimpleBoot", required = true)
    protected boolean nmtSimpleBoot;
    @XmlAttribute(name = "PDOTPDOChannels")
    @XmlSchemaType(name = "unsignedShort")
    protected Integer pdotpdoChannels;

    /**
     * Gets the value of the dllErrMNMultipleMN property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isDLLErrMNMultipleMN() {
        if (dllErrMNMultipleMN == null) {
            return false;
        } else {
            return dllErrMNMultipleMN;
        }
    }

    /**
     * Sets the value of the dllErrMNMultipleMN property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDLLErrMNMultipleMN(Boolean value) {
        this.dllErrMNMultipleMN = value;
    }

    /**
     * Gets the value of the dllmnFeatureMultiplex property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isDLLMNFeatureMultiplex() {
        if (dllmnFeatureMultiplex == null) {
            return true;
        } else {
            return dllmnFeatureMultiplex;
        }
    }

    /**
     * Sets the value of the dllmnFeatureMultiplex property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDLLMNFeatureMultiplex(Boolean value) {
        this.dllmnFeatureMultiplex = value;
    }

    /**
     * Gets the value of the dllmnpResChaining property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isDLLMNPResChaining() {
        if (dllmnpResChaining == null) {
            return false;
        } else {
            return dllmnpResChaining;
        }
    }

    /**
     * Sets the value of the dllmnpResChaining property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDLLMNPResChaining(Boolean value) {
        this.dllmnpResChaining = value;
    }

    /**
     * Gets the value of the dllmnFeaturePResTx property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isDLLMNFeaturePResTx() {
        if (dllmnFeaturePResTx == null) {
            return true;
        } else {
            return dllmnFeaturePResTx;
        }
    }

    /**
     * Sets the value of the dllmnFeaturePResTx property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDLLMNFeaturePResTx(Boolean value) {
        this.dllmnFeaturePResTx = value;
    }

    /**
     * Gets the value of the nmtmnaSnd2SoC property.
     * 
     */
    public long getNMTMNASnd2SoC() {
        return nmtmnaSnd2SoC;
    }

    /**
     * Sets the value of the nmtmnaSnd2SoC property.
     * 
     */
    public void setNMTMNASnd2SoC(long value) {
        this.nmtmnaSnd2SoC = value;
    }

    /**
     * Gets the value of the nmtmnBasicEthernet property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isNMTMNBasicEthernet() {
        if (nmtmnBasicEthernet == null) {
            return false;
        } else {
            return nmtmnBasicEthernet;
        }
    }

    /**
     * Sets the value of the nmtmnBasicEthernet property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNMTMNBasicEthernet(Boolean value) {
        this.nmtmnBasicEthernet = value;
    }

    /**
     * Gets the value of the nmtmnMultiplCycMax property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public short getNMTMNMultiplCycMax() {
        if (nmtmnMultiplCycMax == null) {
            return ((short) 0);
        } else {
            return nmtmnMultiplCycMax;
        }
    }

    /**
     * Sets the value of the nmtmnMultiplCycMax property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setNMTMNMultiplCycMax(Short value) {
        this.nmtmnMultiplCycMax = value;
    }

    /**
     * Gets the value of the nmtmnpRes2PReq property.
     * 
     */
    public long getNMTMNPRes2PReq() {
        return nmtmnpRes2PReq;
    }

    /**
     * Sets the value of the nmtmnpRes2PReq property.
     * 
     */
    public void setNMTMNPRes2PReq(long value) {
        this.nmtmnpRes2PReq = value;
    }

    /**
     * Gets the value of the nmtmnpRes2PRes property.
     * 
     */
    public long getNMTMNPRes2PRes() {
        return nmtmnpRes2PRes;
    }

    /**
     * Sets the value of the nmtmnpRes2PRes property.
     * 
     */
    public void setNMTMNPRes2PRes(long value) {
        this.nmtmnpRes2PRes = value;
    }

    /**
     * Gets the value of the nmtmnpResRx2SoA property.
     * 
     */
    public long getNMTMNPResRx2SoA() {
        return nmtmnpResRx2SoA;
    }

    /**
     * Sets the value of the nmtmnpResRx2SoA property.
     * 
     */
    public void setNMTMNPResRx2SoA(long value) {
        this.nmtmnpResRx2SoA = value;
    }

    /**
     * Gets the value of the nmtmnpResTx2SoA property.
     * 
     */
    public long getNMTMNPResTx2SoA() {
        return nmtmnpResTx2SoA;
    }

    /**
     * Sets the value of the nmtmnpResTx2SoA property.
     * 
     */
    public void setNMTMNPResTx2SoA(long value) {
        this.nmtmnpResTx2SoA = value;
    }

    /**
     * Gets the value of the nmtmnSoA2ASndTx property.
     * 
     */
    public long getNMTMNSoA2ASndTx() {
        return nmtmnSoA2ASndTx;
    }

    /**
     * Sets the value of the nmtmnSoA2ASndTx property.
     * 
     */
    public void setNMTMNSoA2ASndTx(long value) {
        this.nmtmnSoA2ASndTx = value;
    }

    /**
     * Gets the value of the nmtmnSoC2PReq property.
     * 
     */
    public long getNMTMNSoC2PReq() {
        return nmtmnSoC2PReq;
    }

    /**
     * Sets the value of the nmtmnSoC2PReq property.
     * 
     */
    public void setNMTMNSoC2PReq(long value) {
        this.nmtmnSoC2PReq = value;
    }

    /**
     * Gets the value of the nmtNetTime property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isNMTNetTime() {
        if (nmtNetTime == null) {
            return false;
        } else {
            return nmtNetTime;
        }
    }

    /**
     * Sets the value of the nmtNetTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNMTNetTime(Boolean value) {
        this.nmtNetTime = value;
    }

    /**
     * Gets the value of the nmtNetTimeIsRealTime property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isNMTNetTimeIsRealTime() {
        if (nmtNetTimeIsRealTime == null) {
            return false;
        } else {
            return nmtNetTimeIsRealTime;
        }
    }

    /**
     * Sets the value of the nmtNetTimeIsRealTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNMTNetTimeIsRealTime(Boolean value) {
        this.nmtNetTimeIsRealTime = value;
    }

    /**
     * Gets the value of the nmtRelativeTime property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isNMTRelativeTime() {
        if (nmtRelativeTime == null) {
            return false;
        } else {
            return nmtRelativeTime;
        }
    }

    /**
     * Sets the value of the nmtRelativeTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNMTRelativeTime(Boolean value) {
        this.nmtRelativeTime = value;
    }

    /**
     * Gets the value of the nmtSimpleBoot property.
     * 
     */
    public boolean isNMTSimpleBoot() {
        return nmtSimpleBoot;
    }

    /**
     * Sets the value of the nmtSimpleBoot property.
     * 
     */
    public void setNMTSimpleBoot(boolean value) {
        this.nmtSimpleBoot = value;
    }

    /**
     * Gets the value of the pdotpdoChannels property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getPDOTPDOChannels() {
        if (pdotpdoChannels == null) {
            return  256;
        } else {
            return pdotpdoChannels;
        }
    }

    /**
     * Sets the value of the pdotpdoChannels property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPDOTPDOChannels(Integer value) {
        this.pdotpdoChannels = value;
    }

}


package org.epsg.openconfigurator.xmlbinding.xdd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für t_MNFeatures complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
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
     * Ruft den Wert der dllErrMNMultipleMN-Eigenschaft ab.
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
     * Legt den Wert der dllErrMNMultipleMN-Eigenschaft fest.
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
     * Ruft den Wert der dllmnFeatureMultiplex-Eigenschaft ab.
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
     * Legt den Wert der dllmnFeatureMultiplex-Eigenschaft fest.
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
     * Ruft den Wert der dllmnpResChaining-Eigenschaft ab.
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
     * Legt den Wert der dllmnpResChaining-Eigenschaft fest.
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
     * Ruft den Wert der dllmnFeaturePResTx-Eigenschaft ab.
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
     * Legt den Wert der dllmnFeaturePResTx-Eigenschaft fest.
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
     * Ruft den Wert der nmtmnaSnd2SoC-Eigenschaft ab.
     * 
     */
    public long getNMTMNASnd2SoC() {
        return nmtmnaSnd2SoC;
    }

    /**
     * Legt den Wert der nmtmnaSnd2SoC-Eigenschaft fest.
     * 
     */
    public void setNMTMNASnd2SoC(long value) {
        this.nmtmnaSnd2SoC = value;
    }

    /**
     * Ruft den Wert der nmtmnBasicEthernet-Eigenschaft ab.
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
     * Legt den Wert der nmtmnBasicEthernet-Eigenschaft fest.
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
     * Ruft den Wert der nmtmnMultiplCycMax-Eigenschaft ab.
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
     * Legt den Wert der nmtmnMultiplCycMax-Eigenschaft fest.
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
     * Ruft den Wert der nmtmnpRes2PReq-Eigenschaft ab.
     * 
     */
    public long getNMTMNPRes2PReq() {
        return nmtmnpRes2PReq;
    }

    /**
     * Legt den Wert der nmtmnpRes2PReq-Eigenschaft fest.
     * 
     */
    public void setNMTMNPRes2PReq(long value) {
        this.nmtmnpRes2PReq = value;
    }

    /**
     * Ruft den Wert der nmtmnpRes2PRes-Eigenschaft ab.
     * 
     */
    public long getNMTMNPRes2PRes() {
        return nmtmnpRes2PRes;
    }

    /**
     * Legt den Wert der nmtmnpRes2PRes-Eigenschaft fest.
     * 
     */
    public void setNMTMNPRes2PRes(long value) {
        this.nmtmnpRes2PRes = value;
    }

    /**
     * Ruft den Wert der nmtmnpResRx2SoA-Eigenschaft ab.
     * 
     */
    public long getNMTMNPResRx2SoA() {
        return nmtmnpResRx2SoA;
    }

    /**
     * Legt den Wert der nmtmnpResRx2SoA-Eigenschaft fest.
     * 
     */
    public void setNMTMNPResRx2SoA(long value) {
        this.nmtmnpResRx2SoA = value;
    }

    /**
     * Ruft den Wert der nmtmnpResTx2SoA-Eigenschaft ab.
     * 
     */
    public long getNMTMNPResTx2SoA() {
        return nmtmnpResTx2SoA;
    }

    /**
     * Legt den Wert der nmtmnpResTx2SoA-Eigenschaft fest.
     * 
     */
    public void setNMTMNPResTx2SoA(long value) {
        this.nmtmnpResTx2SoA = value;
    }

    /**
     * Ruft den Wert der nmtmnSoA2ASndTx-Eigenschaft ab.
     * 
     */
    public long getNMTMNSoA2ASndTx() {
        return nmtmnSoA2ASndTx;
    }

    /**
     * Legt den Wert der nmtmnSoA2ASndTx-Eigenschaft fest.
     * 
     */
    public void setNMTMNSoA2ASndTx(long value) {
        this.nmtmnSoA2ASndTx = value;
    }

    /**
     * Ruft den Wert der nmtmnSoC2PReq-Eigenschaft ab.
     * 
     */
    public long getNMTMNSoC2PReq() {
        return nmtmnSoC2PReq;
    }

    /**
     * Legt den Wert der nmtmnSoC2PReq-Eigenschaft fest.
     * 
     */
    public void setNMTMNSoC2PReq(long value) {
        this.nmtmnSoC2PReq = value;
    }

    /**
     * Ruft den Wert der nmtNetTime-Eigenschaft ab.
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
     * Legt den Wert der nmtNetTime-Eigenschaft fest.
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
     * Ruft den Wert der nmtNetTimeIsRealTime-Eigenschaft ab.
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
     * Legt den Wert der nmtNetTimeIsRealTime-Eigenschaft fest.
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
     * Ruft den Wert der nmtRelativeTime-Eigenschaft ab.
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
     * Legt den Wert der nmtRelativeTime-Eigenschaft fest.
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
     * Ruft den Wert der nmtSimpleBoot-Eigenschaft ab.
     * 
     */
    public boolean isNMTSimpleBoot() {
        return nmtSimpleBoot;
    }

    /**
     * Legt den Wert der nmtSimpleBoot-Eigenschaft fest.
     * 
     */
    public void setNMTSimpleBoot(boolean value) {
        this.nmtSimpleBoot = value;
    }

    /**
     * Ruft den Wert der pdotpdoChannels-Eigenschaft ab.
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
     * Legt den Wert der pdotpdoChannels-Eigenschaft fest.
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

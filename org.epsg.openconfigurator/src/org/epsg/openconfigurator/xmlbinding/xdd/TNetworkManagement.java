
package org.epsg.openconfigurator.xmlbinding.xdd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für t_NetworkManagement complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="t_NetworkManagement"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GeneralFeatures" type="{http://www.ethernet-powerlink.org}t_GeneralFeatures"/&gt;
 *         &lt;element name="MNFeatures" type="{http://www.ethernet-powerlink.org}t_MNFeatures" minOccurs="0"/&gt;
 *         &lt;element name="CNFeatures" type="{http://www.ethernet-powerlink.org}t_CNFeatures" minOccurs="0"/&gt;
 *         &lt;element name="deviceCommissioning" type="{http://www.ethernet-powerlink.org}t_deviceCommissioning" minOccurs="0"/&gt;
 *         &lt;element name="Diagnostic" type="{http://www.ethernet-powerlink.org}t_Diagnostic" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_NetworkManagement", propOrder = {
    "generalFeatures",
    "mnFeatures",
    "cnFeatures",
    "deviceCommissioning",
    "diagnostic"
})
public class TNetworkManagement {

    @XmlElement(name = "GeneralFeatures", required = true)
    protected TGeneralFeatures generalFeatures;
    @XmlElement(name = "MNFeatures")
    protected TMNFeatures mnFeatures;
    @XmlElement(name = "CNFeatures")
    protected TCNFeatures cnFeatures;
    protected TDeviceCommissioning deviceCommissioning;
    @XmlElement(name = "Diagnostic")
    protected TDiagnostic diagnostic;

    /**
     * Ruft den Wert der generalFeatures-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TGeneralFeatures }
     *     
     */
    public TGeneralFeatures getGeneralFeatures() {
        return generalFeatures;
    }

    /**
     * Legt den Wert der generalFeatures-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TGeneralFeatures }
     *     
     */
    public void setGeneralFeatures(TGeneralFeatures value) {
        this.generalFeatures = value;
    }

    /**
     * Ruft den Wert der mnFeatures-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TMNFeatures }
     *     
     */
    public TMNFeatures getMNFeatures() {
        return mnFeatures;
    }

    /**
     * Legt den Wert der mnFeatures-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TMNFeatures }
     *     
     */
    public void setMNFeatures(TMNFeatures value) {
        this.mnFeatures = value;
    }

    /**
     * Ruft den Wert der cnFeatures-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TCNFeatures }
     *     
     */
    public TCNFeatures getCNFeatures() {
        return cnFeatures;
    }

    /**
     * Legt den Wert der cnFeatures-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TCNFeatures }
     *     
     */
    public void setCNFeatures(TCNFeatures value) {
        this.cnFeatures = value;
    }

    /**
     * Ruft den Wert der deviceCommissioning-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TDeviceCommissioning }
     *     
     */
    public TDeviceCommissioning getDeviceCommissioning() {
        return deviceCommissioning;
    }

    /**
     * Legt den Wert der deviceCommissioning-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TDeviceCommissioning }
     *     
     */
    public void setDeviceCommissioning(TDeviceCommissioning value) {
        this.deviceCommissioning = value;
    }

    /**
     * Ruft den Wert der diagnostic-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TDiagnostic }
     *     
     */
    public TDiagnostic getDiagnostic() {
        return diagnostic;
    }

    /**
     * Legt den Wert der diagnostic-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TDiagnostic }
     *     
     */
    public void setDiagnostic(TDiagnostic value) {
        this.diagnostic = value;
    }

}

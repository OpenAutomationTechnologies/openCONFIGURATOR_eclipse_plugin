
package org.epsg.openconfigurator.xmlbinding.xdd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for t_NetworkManagement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
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
     * Gets the value of the generalFeatures property.
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
     * Sets the value of the generalFeatures property.
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
     * Gets the value of the mnFeatures property.
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
     * Sets the value of the mnFeatures property.
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
     * Gets the value of the cnFeatures property.
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
     * Sets the value of the cnFeatures property.
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
     * Gets the value of the deviceCommissioning property.
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
     * Sets the value of the deviceCommissioning property.
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
     * Gets the value of the diagnostic property.
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
     * Sets the value of the diagnostic property.
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

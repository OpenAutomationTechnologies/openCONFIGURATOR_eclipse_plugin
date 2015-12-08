
package org.epsg.openconfigurator.xmlbinding.xdd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for t_DeviceManager_Modular_Child complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_DeviceManager_Modular_Child"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="indicatorList" type="{http://www.ethernet-powerlink.org}t_indicatorList" minOccurs="0"/&gt;
 *         &lt;element name="moduleManagement" type="{http://www.ethernet-powerlink.org}t_moduleManagement_Modular_Child"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_DeviceManager_Modular_Child", propOrder = {
    "indicatorList",
    "moduleManagement"
})
public class TDeviceManagerModularChild {

    protected TIndicatorList indicatorList;
    @XmlElement(required = true)
    protected TModuleManagementModularChild moduleManagement;

    /**
     * Gets the value of the indicatorList property.
     * 
     * @return
     *     possible object is
     *     {@link TIndicatorList }
     *     
     */
    public TIndicatorList getIndicatorList() {
        return indicatorList;
    }

    /**
     * Sets the value of the indicatorList property.
     * 
     * @param value
     *     allowed object is
     *     {@link TIndicatorList }
     *     
     */
    public void setIndicatorList(TIndicatorList value) {
        this.indicatorList = value;
    }

    /**
     * Gets the value of the moduleManagement property.
     * 
     * @return
     *     possible object is
     *     {@link TModuleManagementModularChild }
     *     
     */
    public TModuleManagementModularChild getModuleManagement() {
        return moduleManagement;
    }

    /**
     * Sets the value of the moduleManagement property.
     * 
     * @param value
     *     allowed object is
     *     {@link TModuleManagementModularChild }
     *     
     */
    public void setModuleManagement(TModuleManagementModularChild value) {
        this.moduleManagement = value;
    }

}

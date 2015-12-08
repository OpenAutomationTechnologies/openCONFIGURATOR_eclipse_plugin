
package org.epsg.openconfigurator.xmlbinding.xdd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for t_moduleManagement complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_moduleManagement"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="interfaceList" type="{http://www.ethernet-powerlink.org}t_interfaceList"/&gt;
 *         &lt;element name="moduleInterface" type="{http://www.ethernet-powerlink.org}t_moduleInterface" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_moduleManagement", propOrder = {
    "interfaceList",
    "moduleInterface"
})
public class TModuleManagement {

    @XmlElement(required = true)
    protected TInterfaceList interfaceList;
    protected TModuleInterface moduleInterface;

    /**
     * Gets the value of the interfaceList property.
     * 
     * @return
     *     possible object is
     *     {@link TInterfaceList }
     *     
     */
    public TInterfaceList getInterfaceList() {
        return interfaceList;
    }

    /**
     * Sets the value of the interfaceList property.
     * 
     * @param value
     *     allowed object is
     *     {@link TInterfaceList }
     *     
     */
    public void setInterfaceList(TInterfaceList value) {
        this.interfaceList = value;
    }

    /**
     * Gets the value of the moduleInterface property.
     * 
     * @return
     *     possible object is
     *     {@link TModuleInterface }
     *     
     */
    public TModuleInterface getModuleInterface() {
        return moduleInterface;
    }

    /**
     * Sets the value of the moduleInterface property.
     * 
     * @param value
     *     allowed object is
     *     {@link TModuleInterface }
     *     
     */
    public void setModuleInterface(TModuleInterface value) {
        this.moduleInterface = value;
    }

}


package org.epsg.openconfigurator.xmlbinding.xdd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für t_DeviceManager complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="t_DeviceManager"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="indicatorList" type="{http://www.ethernet-powerlink.org}t_indicatorList" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_DeviceManager", propOrder = {
    "indicatorList"
})
public class TDeviceManager {

    protected TIndicatorList indicatorList;

    /**
     * Ruft den Wert der indicatorList-Eigenschaft ab.
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
     * Legt den Wert der indicatorList-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TIndicatorList }
     *     
     */
    public void setIndicatorList(TIndicatorList value) {
        this.indicatorList = value;
    }

}

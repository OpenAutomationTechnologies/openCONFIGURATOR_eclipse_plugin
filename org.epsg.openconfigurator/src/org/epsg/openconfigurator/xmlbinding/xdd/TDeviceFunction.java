
package org.epsg.openconfigurator.xmlbinding.xdd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for t_DeviceFunction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_DeviceFunction"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="capabilities" type="{http://www.ethernet-powerlink.org}t_capabilities"/&gt;
 *         &lt;element name="picturesList" type="{http://www.ethernet-powerlink.org}t_picturesList" minOccurs="0"/&gt;
 *         &lt;element name="dictionaryList" type="{http://www.ethernet-powerlink.org}t_dictionaryList" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_DeviceFunction", propOrder = {
    "capabilities",
    "picturesList",
    "dictionaryList"
})
public class TDeviceFunction {

    @XmlElement(required = true)
    protected TCapabilities capabilities;
    protected TPicturesList picturesList;
    protected TDictionaryList dictionaryList;

    /**
     * Gets the value of the capabilities property.
     * 
     * @return
     *     possible object is
     *     {@link TCapabilities }
     *     
     */
    public TCapabilities getCapabilities() {
        return capabilities;
    }

    /**
     * Sets the value of the capabilities property.
     * 
     * @param value
     *     allowed object is
     *     {@link TCapabilities }
     *     
     */
    public void setCapabilities(TCapabilities value) {
        this.capabilities = value;
    }

    /**
     * Gets the value of the picturesList property.
     * 
     * @return
     *     possible object is
     *     {@link TPicturesList }
     *     
     */
    public TPicturesList getPicturesList() {
        return picturesList;
    }

    /**
     * Sets the value of the picturesList property.
     * 
     * @param value
     *     allowed object is
     *     {@link TPicturesList }
     *     
     */
    public void setPicturesList(TPicturesList value) {
        this.picturesList = value;
    }

    /**
     * Gets the value of the dictionaryList property.
     * 
     * @return
     *     possible object is
     *     {@link TDictionaryList }
     *     
     */
    public TDictionaryList getDictionaryList() {
        return dictionaryList;
    }

    /**
     * Sets the value of the dictionaryList property.
     * 
     * @param value
     *     allowed object is
     *     {@link TDictionaryList }
     *     
     */
    public void setDictionaryList(TDictionaryList value) {
        this.dictionaryList = value;
    }

}

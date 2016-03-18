
package org.epsg.openconfigurator.xmlbinding.xdd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for t_modularChildDeviceFunction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_modularChildDeviceFunction"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="capabilities" type="{http://www.ethernet-powerlink.org}t_capabilities" minOccurs="0"/&gt;
 *         &lt;element name="picturesList" type="{http://www.ethernet-powerlink.org}t_picturesList" minOccurs="0"/&gt;
 *         &lt;element name="dictionaryList" type="{http://www.ethernet-powerlink.org}t_dictionaryList" minOccurs="0"/&gt;
 *         &lt;element name="connectorList" type="{http://www.ethernet-powerlink.org}t_modularChildConnectorList" minOccurs="0"/&gt;
 *         &lt;element name="firmwareList" type="{http://www.ethernet-powerlink.org}t_firmwareList" minOccurs="0"/&gt;
 *         &lt;element name="classificationList" type="{http://www.ethernet-powerlink.org}t_classificationList" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_modularChildDeviceFunction", propOrder = {
    "capabilities",
    "picturesList",
    "dictionaryList",
    "connectorList",
    "firmwareList",
    "classificationList"
})
public class TModularChildDeviceFunction {

    protected TCapabilities capabilities;
    protected TPicturesList picturesList;
    protected TDictionaryList dictionaryList;
    protected TModularChildConnectorList connectorList;
    protected TFirmwareList firmwareList;
    protected TClassificationList classificationList;

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

    /**
     * Gets the value of the connectorList property.
     * 
     * @return
     *     possible object is
     *     {@link TModularChildConnectorList }
     *     
     */
    public TModularChildConnectorList getConnectorList() {
        return connectorList;
    }

    /**
     * Sets the value of the connectorList property.
     * 
     * @param value
     *     allowed object is
     *     {@link TModularChildConnectorList }
     *     
     */
    public void setConnectorList(TModularChildConnectorList value) {
        this.connectorList = value;
    }

    /**
     * Gets the value of the firmwareList property.
     * 
     * @return
     *     possible object is
     *     {@link TFirmwareList }
     *     
     */
    public TFirmwareList getFirmwareList() {
        return firmwareList;
    }

    /**
     * Sets the value of the firmwareList property.
     * 
     * @param value
     *     allowed object is
     *     {@link TFirmwareList }
     *     
     */
    public void setFirmwareList(TFirmwareList value) {
        this.firmwareList = value;
    }

    /**
     * Gets the value of the classificationList property.
     * 
     * @return
     *     possible object is
     *     {@link TClassificationList }
     *     
     */
    public TClassificationList getClassificationList() {
        return classificationList;
    }

    /**
     * Sets the value of the classificationList property.
     * 
     * @param value
     *     allowed object is
     *     {@link TClassificationList }
     *     
     */
    public void setClassificationList(TClassificationList value) {
        this.classificationList = value;
    }

}

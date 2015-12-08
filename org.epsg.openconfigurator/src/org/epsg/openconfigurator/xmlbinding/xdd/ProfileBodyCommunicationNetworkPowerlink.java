
package org.epsg.openconfigurator.xmlbinding.xdd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProfileBody_CommunicationNetwork_Powerlink complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProfileBody_CommunicationNetwork_Powerlink"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://www.ethernet-powerlink.org}ProfileBody_DataType"&gt;
 *       &lt;choice&gt;
 *         &lt;group ref="{http://www.ethernet-powerlink.org}g_ProfileBody_CommunicationNetwork_Powerlink"/&gt;
 *         &lt;element name="ExternalProfileHandle" type="{http://www.ethernet-powerlink.org}ProfileHandle_DataType" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/choice&gt;
 *       &lt;attribute name="specificationVersion" type="{http://www.ethernet-powerlink.org}t_specificationVersion" default="1.0.4" /&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProfileBody_CommunicationNetwork_Powerlink", propOrder = {
    "applicationLayers",
    "transportLayers",
    "networkManagement",
    "externalProfileHandle"
})
public class ProfileBodyCommunicationNetworkPowerlink
    extends ProfileBodyDataType
{

    @XmlElement(name = "ApplicationLayers")
    protected TApplicationLayers applicationLayers;
    @XmlElement(name = "TransportLayers")
    protected Object transportLayers;
    @XmlElement(name = "NetworkManagement")
    protected TNetworkManagement networkManagement;
    @XmlElement(name = "ExternalProfileHandle")
    protected List<ProfileHandleDataType> externalProfileHandle;
    @XmlAttribute(name = "specificationVersion")
    protected String specificationVersion;

    /**
     * Gets the value of the applicationLayers property.
     * 
     * @return
     *     possible object is
     *     {@link TApplicationLayers }
     *     
     */
    public TApplicationLayers getApplicationLayers() {
        return applicationLayers;
    }

    /**
     * Sets the value of the applicationLayers property.
     * 
     * @param value
     *     allowed object is
     *     {@link TApplicationLayers }
     *     
     */
    public void setApplicationLayers(TApplicationLayers value) {
        this.applicationLayers = value;
    }

    /**
     * Gets the value of the transportLayers property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getTransportLayers() {
        return transportLayers;
    }

    /**
     * Sets the value of the transportLayers property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setTransportLayers(Object value) {
        this.transportLayers = value;
    }

    /**
     * Gets the value of the networkManagement property.
     * 
     * @return
     *     possible object is
     *     {@link TNetworkManagement }
     *     
     */
    public TNetworkManagement getNetworkManagement() {
        return networkManagement;
    }

    /**
     * Sets the value of the networkManagement property.
     * 
     * @param value
     *     allowed object is
     *     {@link TNetworkManagement }
     *     
     */
    public void setNetworkManagement(TNetworkManagement value) {
        this.networkManagement = value;
    }

    /**
     * Gets the value of the externalProfileHandle property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the externalProfileHandle property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getExternalProfileHandle().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProfileHandleDataType }
     * 
     * 
     */
    public List<ProfileHandleDataType> getExternalProfileHandle() {
        if (externalProfileHandle == null) {
            externalProfileHandle = new ArrayList<ProfileHandleDataType>();
        }
        return this.externalProfileHandle;
    }

    /**
     * Gets the value of the specificationVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpecificationVersion() {
        if (specificationVersion == null) {
            return "1.0.4";
        } else {
            return specificationVersion;
        }
    }

    /**
     * Sets the value of the specificationVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpecificationVersion(String value) {
        this.specificationVersion = value;
    }

}

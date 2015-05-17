
package org.epsg.openconfigurator.xmlbinding.xdd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for t_deviceCommissioning complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_deviceCommissioning"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="networkName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="nodeID" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" /&gt;
 *       &lt;attribute name="nodeName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="nodeType" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKENS"&gt;
 *             &lt;enumeration value="MN"/&gt;
 *             &lt;enumeration value="CN"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="usedNetworkInterface" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" default="0" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_deviceCommissioning")
public class TDeviceCommissioning {

    @XmlAttribute(name = "networkName", required = true)
    protected String networkName;
    @XmlAttribute(name = "nodeID", required = true)
    @XmlSchemaType(name = "unsignedByte")
    protected short nodeID;
    @XmlAttribute(name = "nodeName", required = true)
    protected String nodeName;
    @XmlAttribute(name = "nodeType", required = true)
    protected List<String> nodeType;
    @XmlAttribute(name = "usedNetworkInterface")
    @XmlSchemaType(name = "unsignedByte")
    protected Short usedNetworkInterface;

    /**
     * Gets the value of the networkName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNetworkName() {
        return networkName;
    }

    /**
     * Sets the value of the networkName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNetworkName(String value) {
        this.networkName = value;
    }

    /**
     * Gets the value of the nodeID property.
     * 
     */
    public short getNodeID() {
        return nodeID;
    }

    /**
     * Sets the value of the nodeID property.
     * 
     */
    public void setNodeID(short value) {
        this.nodeID = value;
    }

    /**
     * Gets the value of the nodeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNodeName() {
        return nodeName;
    }

    /**
     * Sets the value of the nodeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNodeName(String value) {
        this.nodeName = value;
    }

    /**
     * Gets the value of the nodeType property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nodeType property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNodeType().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getNodeType() {
        if (nodeType == null) {
            nodeType = new ArrayList<String>();
        }
        return this.nodeType;
    }

    /**
     * Gets the value of the usedNetworkInterface property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public short getUsedNetworkInterface() {
        if (usedNetworkInterface == null) {
            return ((short) 0);
        } else {
            return usedNetworkInterface;
        }
    }

    /**
     * Sets the value of the usedNetworkInterface property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setUsedNetworkInterface(Short value) {
        this.usedNetworkInterface = value;
    }

}

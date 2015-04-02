
package org.epsg.openconfigurator.xmlbinding.xdd;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für t_deviceCommissioning complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
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
     * Ruft den Wert der networkName-Eigenschaft ab.
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
     * Legt den Wert der networkName-Eigenschaft fest.
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
     * Ruft den Wert der nodeID-Eigenschaft ab.
     * 
     */
    public short getNodeID() {
        return nodeID;
    }

    /**
     * Legt den Wert der nodeID-Eigenschaft fest.
     * 
     */
    public void setNodeID(short value) {
        this.nodeID = value;
    }

    /**
     * Ruft den Wert der nodeName-Eigenschaft ab.
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
     * Legt den Wert der nodeName-Eigenschaft fest.
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
     * Ruft den Wert der usedNetworkInterface-Eigenschaft ab.
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
     * Legt den Wert der usedNetworkInterface-Eigenschaft fest.
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

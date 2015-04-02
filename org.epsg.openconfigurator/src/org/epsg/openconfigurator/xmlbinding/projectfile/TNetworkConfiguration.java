
package org.epsg.openconfigurator.xmlbinding.projectfile;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * Element to define communication relationships in a POWERLINK network. I.e.:
 *                 How is an object transferred from node X to node Y. 
 * 
 * <p>Java-Klasse für tNetworkConfiguration complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="tNetworkConfiguration"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="NodeCollection" type="{http://sourceforge.net/projects/openconf/configuration}tNodeCollection"/&gt;
 *         &lt;element name="CommunicationRelationships" type="{http://sourceforge.net/projects/openconf/configuration}tCommunicationRelationships" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="cycleTime" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
 *       &lt;attribute name="asyncMTU"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}unsignedShort"&gt;
 *             &lt;minInclusive value="300"/&gt;
 *             &lt;maxInclusive value="1500"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="multiplexedCycleLength" type="{http://sourceforge.net/projects/openconf/configuration}tMultiplexedCycleLength" default="0" /&gt;
 *       &lt;attribute name="prescaler"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}unsignedShort"&gt;
 *             &lt;minInclusive value="0"/&gt;
 *             &lt;maxInclusive value="1000"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tNetworkConfiguration", propOrder = {
    "nodeCollection",
    "communicationRelationships"
})
public class TNetworkConfiguration {

    @XmlElement(name = "NodeCollection", required = true)
    protected TNodeCollection nodeCollection;
    @XmlElement(name = "CommunicationRelationships")
    protected TCommunicationRelationships communicationRelationships;
    @XmlAttribute(name = "cycleTime")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger cycleTime;
    @XmlAttribute(name = "asyncMTU")
    protected Integer asyncMTU;
    @XmlAttribute(name = "multiplexedCycleLength")
    protected Integer multiplexedCycleLength;
    @XmlAttribute(name = "prescaler")
    protected Integer prescaler;

    /**
     * Ruft den Wert der nodeCollection-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TNodeCollection }
     *     
     */
    public TNodeCollection getNodeCollection() {
        return nodeCollection;
    }

    /**
     * Legt den Wert der nodeCollection-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TNodeCollection }
     *     
     */
    public void setNodeCollection(TNodeCollection value) {
        this.nodeCollection = value;
    }

    /**
     * Ruft den Wert der communicationRelationships-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link TCommunicationRelationships }
     *     
     */
    public TCommunicationRelationships getCommunicationRelationships() {
        return communicationRelationships;
    }

    /**
     * Legt den Wert der communicationRelationships-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link TCommunicationRelationships }
     *     
     */
    public void setCommunicationRelationships(TCommunicationRelationships value) {
        this.communicationRelationships = value;
    }

    /**
     * Ruft den Wert der cycleTime-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getCycleTime() {
        return cycleTime;
    }

    /**
     * Legt den Wert der cycleTime-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setCycleTime(BigInteger value) {
        this.cycleTime = value;
    }

    /**
     * Ruft den Wert der asyncMTU-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAsyncMTU() {
        return asyncMTU;
    }

    /**
     * Legt den Wert der asyncMTU-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAsyncMTU(Integer value) {
        this.asyncMTU = value;
    }

    /**
     * Ruft den Wert der multiplexedCycleLength-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getMultiplexedCycleLength() {
        if (multiplexedCycleLength == null) {
            return  0;
        } else {
            return multiplexedCycleLength;
        }
    }

    /**
     * Legt den Wert der multiplexedCycleLength-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMultiplexedCycleLength(Integer value) {
        this.multiplexedCycleLength = value;
    }

    /**
     * Ruft den Wert der prescaler-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPrescaler() {
        return prescaler;
    }

    /**
     * Legt den Wert der prescaler-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPrescaler(Integer value) {
        this.prescaler = value;
    }

}

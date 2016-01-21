
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
 * <p>Java class for tNetworkConfiguration complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
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
 *       &lt;attribute name="lossOfSocTolerance" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" default="100000" /&gt;
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
    @XmlAttribute(name = "lossOfSocTolerance")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger lossOfSocTolerance;

    /**
     * Gets the value of the nodeCollection property.
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
     * Sets the value of the nodeCollection property.
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
     * Gets the value of the communicationRelationships property.
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
     * Sets the value of the communicationRelationships property.
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
     * Gets the value of the cycleTime property.
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
     * Sets the value of the cycleTime property.
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
     * Gets the value of the asyncMTU property.
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
     * Sets the value of the asyncMTU property.
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
     * Gets the value of the multiplexedCycleLength property.
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
     * Sets the value of the multiplexedCycleLength property.
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
     * Gets the value of the prescaler property.
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
     * Sets the value of the prescaler property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPrescaler(Integer value) {
        this.prescaler = value;
    }

    /**
     * Gets the value of the lossOfSocTolerance property.
     *
     * @return
     *     possible object is
     *     {@link BigInteger }
     *
     */
    public BigInteger getLossOfSocTolerance() {
        if (lossOfSocTolerance == null) {
            return new BigInteger("100000");
        } else {
            return lossOfSocTolerance;
        }
    }

    /**
     * Sets the value of the lossOfSocTolerance property.
     *
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *
     */
    public void setLossOfSocTolerance(BigInteger value) {
        this.lossOfSocTolerance = value;
    }

}

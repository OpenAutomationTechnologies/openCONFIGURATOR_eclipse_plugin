
package org.epsg.openconfigurator.xmlbinding.projectfile;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 *
 *
 * <pre>
 * &lt;?xml version="1.0" encoding="UTF-8"?&gt;&lt;p xmlns:oc="http://sourceforge.net/projects/openconf/configuration" xmlns:ppc="http://ethernet-powerlink.org/POWERLINK" xmlns:xs="http://www.w3.org/2001/XMLSchema"&gt; One Node to rule them all,&lt;br/&gt; One Node to find them,&lt;br/&gt; One Node to bring them all and in the
 *                     darkness bind them.&lt;br/&gt; In the Land of Mordor where the Shadows lie. &lt;/p&gt;
 * </pre>
 *  A concrete type for a
 *                 POWERLINK MN.
 *
 * <p>Java class for tMN complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="tMN"&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{http://sourceforge.net/projects/openconf/configuration}tAbstractNode"&gt;
 *       &lt;attribute name="nodeID" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" fixed="240" /&gt;
 *       &lt;attribute name="pathToXDC"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyURI"&gt;
 *             &lt;minLength value="1"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="transmitsPRes" type="{http://www.w3.org/2001/XMLSchema}boolean" default="false" /&gt;
 *       &lt;attribute name="asyncSlotTimeout"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}unsignedInt"&gt;
 *             &lt;minInclusive value="250"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="aSndMaxNumber" default="1"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}unsignedByte"&gt;
 *             &lt;minInclusive value="1"/&gt;
 *             &lt;maxInclusive value="254"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "tMN")
public class TMN
    extends TAbstractNode
{

    @XmlAttribute(name = "nodeID", required = true)
    @XmlSchemaType(name = "unsignedByte")
    protected short nodeID;
    @XmlAttribute(name = "pathToXDC")
    protected String pathToXDC;
    @XmlAttribute(name = "transmitsPRes")
    protected Boolean transmitsPRes;
    @XmlAttribute(name = "asyncSlotTimeout")
    protected Long asyncSlotTimeout;
    @XmlAttribute(name = "aSndMaxNumber")
    protected Short aSndMaxNumber;

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
     * Gets the value of the pathToXDC property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPathToXDC() {
        return pathToXDC;
    }

    /**
     * Sets the value of the pathToXDC property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPathToXDC(String value) {
        this.pathToXDC = value;
    }

    /**
     * Gets the value of the transmitsPRes property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public boolean isTransmitsPRes() {
        if (transmitsPRes == null) {
            return false;
        } else {
            return transmitsPRes;
        }
    }

    /**
     * Sets the value of the transmitsPRes property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setTransmitsPRes(Boolean value) {
        this.transmitsPRes = value;
    }

    /**
     * Gets the value of the asyncSlotTimeout property.
     *
     * @return
     *     possible object is
     *     {@link Long }
     *
     */
    public Long getAsyncSlotTimeout() {
        return asyncSlotTimeout;
    }

    /**
     * Sets the value of the asyncSlotTimeout property.
     *
     * @param value
     *     allowed object is
     *     {@link Long }
     *
     */
    public void setAsyncSlotTimeout(Long value) {
        this.asyncSlotTimeout = value;
    }

    /**
     * Gets the value of the aSndMaxNumber property.
     *
     * @return
     *     possible object is
     *     {@link Short }
     *
     */
    public short getASndMaxNumber() {
        if (aSndMaxNumber == null) {
            return ((short) 1);
        } else {
            return aSndMaxNumber;
        }
    }

    /**
     * Sets the value of the aSndMaxNumber property.
     *
     * @param value
     *     allowed object is
     *     {@link Short }
     *
     */
    public void setASndMaxNumber(Short value) {
        this.aSndMaxNumber = value;
    }

}

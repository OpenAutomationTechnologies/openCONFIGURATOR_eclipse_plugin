
package org.epsg.openconfigurator.xmlbinding.xap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="Name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="dataType" use="required" type="{http://ethernet-powerlink.org/POWERLINK/PI}tdataType" /&gt;
 *       &lt;attribute name="dataSize" use="required" type="{http://ethernet-powerlink.org/POWERLINK/PI}tdataSize" /&gt;
 *       &lt;attribute name="PIOffset" use="required" type="{http://ethernet-powerlink.org/POWERLINK/PI}tPIOffset" /&gt;
 *       &lt;attribute name="BitOffset" type="{http://ethernet-powerlink.org/POWERLINK/PI}tBitOffset" default="0x00" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "Channel")
public class Channel {

    @XmlAttribute(name = "Name", required = true)
    protected String name;
    @XmlAttribute(name = "dataType", required = true)
    protected TdataType dataType;
    @XmlAttribute(name = "dataSize", required = true)
    protected byte dataSize;
    @XmlAttribute(name = "PIOffset", required = true)
    protected String piOffset;
    @XmlAttribute(name = "BitOffset")
    protected String bitOffset;

    /**
     * Gets the value of the name property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the dataType property.
     *
     * @return
     *     possible object is
     *     {@link TdataType }
     *
     */
    public TdataType getDataType() {
        return dataType;
    }

    /**
     * Sets the value of the dataType property.
     *
     * @param value
     *     allowed object is
     *     {@link TdataType }
     *
     */
    public void setDataType(TdataType value) {
        this.dataType = value;
    }

    /**
     * Gets the value of the dataSize property.
     *
     */
    public byte getDataSize() {
        return dataSize;
    }

    /**
     * Sets the value of the dataSize property.
     *
     */
    public void setDataSize(byte value) {
        this.dataSize = value;
    }

    /**
     * Gets the value of the piOffset property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPIOffset() {
        return piOffset;
    }

    /**
     * Sets the value of the piOffset property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPIOffset(String value) {
        this.piOffset = value;
    }

    /**
     * Gets the value of the bitOffset property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getBitOffset() {
        if (bitOffset == null) {
            return "0x00";
        } else {
            return bitOffset;
        }
    }

    /**
     * Sets the value of the bitOffset property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBitOffset(String value) {
        this.bitOffset = value;
    }

}

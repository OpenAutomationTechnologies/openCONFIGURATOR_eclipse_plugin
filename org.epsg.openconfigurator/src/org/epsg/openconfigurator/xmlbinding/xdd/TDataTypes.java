
package org.epsg.openconfigurator.xmlbinding.xdd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for t_DataTypes complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="t_DataTypes"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="Boolean" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *         &lt;element name="Integer8" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *         &lt;element name="Integer16" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *         &lt;element name="Integer32" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *         &lt;element name="Integer24" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *         &lt;element name="Integer40" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *         &lt;element name="Integer48" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *         &lt;element name="Integer56" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *         &lt;element name="Integer64" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *         &lt;element name="Unsigned8" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *         &lt;element name="Unsigned16" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *         &lt;element name="Unsigned32" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *         &lt;element name="Unsigned24" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *         &lt;element name="Unsigned40" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *         &lt;element name="Unsigned48" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *         &lt;element name="Unsigned56" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *         &lt;element name="Unsigned64" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *         &lt;element name="Real32" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *         &lt;element name="Real64" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *         &lt;element name="Visible_String" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *         &lt;element name="Octet_String" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *         &lt;element name="Unicode_String" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *         &lt;element name="Time_of_Day" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *         &lt;element name="Time_Diff" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *         &lt;element name="Domain" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *         &lt;element name="MAC_ADDRESS" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *         &lt;element name="IP_ADDRESS" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *         &lt;element name="NETTIME" type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *       &lt;/choice&gt;
 *       &lt;attribute name="dataType" use="required" type="{http://www.w3.org/2001/XMLSchema}hexBinary" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_DataTypes", propOrder = {
    "_boolean",
    "integer8",
    "integer16",
    "integer32",
    "integer24",
    "integer40",
    "integer48",
    "integer56",
    "integer64",
    "unsigned8",
    "unsigned16",
    "unsigned32",
    "unsigned24",
    "unsigned40",
    "unsigned48",
    "unsigned56",
    "unsigned64",
    "real32",
    "real64",
    "visibleString",
    "octetString",
    "unicodeString",
    "timeOfDay",
    "timeDiff",
    "domain",
    "macaddress",
    "ipaddress",
    "nettime"
})
public class TDataTypes {

    @XmlElement(name = "Boolean")
    protected Object _boolean;
    @XmlElement(name = "Integer8")
    protected Object integer8;
    @XmlElement(name = "Integer16")
    protected Object integer16;
    @XmlElement(name = "Integer32")
    protected Object integer32;
    @XmlElement(name = "Integer24")
    protected Object integer24;
    @XmlElement(name = "Integer40")
    protected Object integer40;
    @XmlElement(name = "Integer48")
    protected Object integer48;
    @XmlElement(name = "Integer56")
    protected Object integer56;
    @XmlElement(name = "Integer64")
    protected Object integer64;
    @XmlElement(name = "Unsigned8")
    protected Object unsigned8;
    @XmlElement(name = "Unsigned16")
    protected Object unsigned16;
    @XmlElement(name = "Unsigned32")
    protected Object unsigned32;
    @XmlElement(name = "Unsigned24")
    protected Object unsigned24;
    @XmlElement(name = "Unsigned40")
    protected Object unsigned40;
    @XmlElement(name = "Unsigned48")
    protected Object unsigned48;
    @XmlElement(name = "Unsigned56")
    protected Object unsigned56;
    @XmlElement(name = "Unsigned64")
    protected Object unsigned64;
    @XmlElement(name = "Real32")
    protected Object real32;
    @XmlElement(name = "Real64")
    protected Object real64;
    @XmlElement(name = "Visible_String")
    protected Object visibleString;
    @XmlElement(name = "Octet_String")
    protected Object octetString;
    @XmlElement(name = "Unicode_String")
    protected Object unicodeString;
    @XmlElement(name = "Time_of_Day")
    protected Object timeOfDay;
    @XmlElement(name = "Time_Diff")
    protected Object timeDiff;
    @XmlElement(name = "Domain")
    protected Object domain;
    @XmlElement(name = "MAC_ADDRESS")
    protected Object macaddress;
    @XmlElement(name = "IP_ADDRESS")
    protected Object ipaddress;
    @XmlElement(name = "NETTIME")
    protected Object nettime;
    @XmlAttribute(name = "dataType", required = true)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] dataType;

    /**
     * Gets the value of the boolean property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getBoolean() {
        return _boolean;
    }

    /**
     * Sets the value of the boolean property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setBoolean(Object value) {
        this._boolean = value;
    }

    /**
     * Gets the value of the integer8 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getInteger8() {
        return integer8;
    }

    /**
     * Sets the value of the integer8 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setInteger8(Object value) {
        this.integer8 = value;
    }

    /**
     * Gets the value of the integer16 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getInteger16() {
        return integer16;
    }

    /**
     * Sets the value of the integer16 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setInteger16(Object value) {
        this.integer16 = value;
    }

    /**
     * Gets the value of the integer32 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getInteger32() {
        return integer32;
    }

    /**
     * Sets the value of the integer32 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setInteger32(Object value) {
        this.integer32 = value;
    }

    /**
     * Gets the value of the integer24 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getInteger24() {
        return integer24;
    }

    /**
     * Sets the value of the integer24 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setInteger24(Object value) {
        this.integer24 = value;
    }

    /**
     * Gets the value of the integer40 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getInteger40() {
        return integer40;
    }

    /**
     * Sets the value of the integer40 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setInteger40(Object value) {
        this.integer40 = value;
    }

    /**
     * Gets the value of the integer48 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getInteger48() {
        return integer48;
    }

    /**
     * Sets the value of the integer48 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setInteger48(Object value) {
        this.integer48 = value;
    }

    /**
     * Gets the value of the integer56 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getInteger56() {
        return integer56;
    }

    /**
     * Sets the value of the integer56 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setInteger56(Object value) {
        this.integer56 = value;
    }

    /**
     * Gets the value of the integer64 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getInteger64() {
        return integer64;
    }

    /**
     * Sets the value of the integer64 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setInteger64(Object value) {
        this.integer64 = value;
    }

    /**
     * Gets the value of the unsigned8 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUnsigned8() {
        return unsigned8;
    }

    /**
     * Sets the value of the unsigned8 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUnsigned8(Object value) {
        this.unsigned8 = value;
    }

    /**
     * Gets the value of the unsigned16 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUnsigned16() {
        return unsigned16;
    }

    /**
     * Sets the value of the unsigned16 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUnsigned16(Object value) {
        this.unsigned16 = value;
    }

    /**
     * Gets the value of the unsigned32 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUnsigned32() {
        return unsigned32;
    }

    /**
     * Sets the value of the unsigned32 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUnsigned32(Object value) {
        this.unsigned32 = value;
    }

    /**
     * Gets the value of the unsigned24 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUnsigned24() {
        return unsigned24;
    }

    /**
     * Sets the value of the unsigned24 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUnsigned24(Object value) {
        this.unsigned24 = value;
    }

    /**
     * Gets the value of the unsigned40 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUnsigned40() {
        return unsigned40;
    }

    /**
     * Sets the value of the unsigned40 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUnsigned40(Object value) {
        this.unsigned40 = value;
    }

    /**
     * Gets the value of the unsigned48 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUnsigned48() {
        return unsigned48;
    }

    /**
     * Sets the value of the unsigned48 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUnsigned48(Object value) {
        this.unsigned48 = value;
    }

    /**
     * Gets the value of the unsigned56 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUnsigned56() {
        return unsigned56;
    }

    /**
     * Sets the value of the unsigned56 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUnsigned56(Object value) {
        this.unsigned56 = value;
    }

    /**
     * Gets the value of the unsigned64 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUnsigned64() {
        return unsigned64;
    }

    /**
     * Sets the value of the unsigned64 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUnsigned64(Object value) {
        this.unsigned64 = value;
    }

    /**
     * Gets the value of the real32 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getReal32() {
        return real32;
    }

    /**
     * Sets the value of the real32 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setReal32(Object value) {
        this.real32 = value;
    }

    /**
     * Gets the value of the real64 property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getReal64() {
        return real64;
    }

    /**
     * Sets the value of the real64 property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setReal64(Object value) {
        this.real64 = value;
    }

    /**
     * Gets the value of the visibleString property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getVisibleString() {
        return visibleString;
    }

    /**
     * Sets the value of the visibleString property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setVisibleString(Object value) {
        this.visibleString = value;
    }

    /**
     * Gets the value of the octetString property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getOctetString() {
        return octetString;
    }

    /**
     * Sets the value of the octetString property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setOctetString(Object value) {
        this.octetString = value;
    }

    /**
     * Gets the value of the unicodeString property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getUnicodeString() {
        return unicodeString;
    }

    /**
     * Sets the value of the unicodeString property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setUnicodeString(Object value) {
        this.unicodeString = value;
    }

    /**
     * Gets the value of the timeOfDay property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getTimeOfDay() {
        return timeOfDay;
    }

    /**
     * Sets the value of the timeOfDay property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setTimeOfDay(Object value) {
        this.timeOfDay = value;
    }

    /**
     * Gets the value of the timeDiff property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getTimeDiff() {
        return timeDiff;
    }

    /**
     * Sets the value of the timeDiff property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setTimeDiff(Object value) {
        this.timeDiff = value;
    }

    /**
     * Gets the value of the domain property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getDomain() {
        return domain;
    }

    /**
     * Sets the value of the domain property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setDomain(Object value) {
        this.domain = value;
    }

    /**
     * Gets the value of the macaddress property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getMACADDRESS() {
        return macaddress;
    }

    /**
     * Sets the value of the macaddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setMACADDRESS(Object value) {
        this.macaddress = value;
    }

    /**
     * Gets the value of the ipaddress property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getIPADDRESS() {
        return ipaddress;
    }

    /**
     * Sets the value of the ipaddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setIPADDRESS(Object value) {
        this.ipaddress = value;
    }

    /**
     * Gets the value of the nettime property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getNETTIME() {
        return nettime;
    }

    /**
     * Sets the value of the nettime property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setNETTIME(Object value) {
        this.nettime = value;
    }

    /**
     * Gets the value of the dataType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getDataType() {
        return dataType;
    }

    /**
     * Sets the value of the dataType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataType(byte[] value) {
        this.dataType = value;
    }

}

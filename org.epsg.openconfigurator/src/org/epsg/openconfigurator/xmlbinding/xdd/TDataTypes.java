
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
 * <p>Java-Klasse für t_DataTypes complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
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
     * Ruft den Wert der boolean-Eigenschaft ab.
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
     * Legt den Wert der boolean-Eigenschaft fest.
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
     * Ruft den Wert der integer8-Eigenschaft ab.
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
     * Legt den Wert der integer8-Eigenschaft fest.
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
     * Ruft den Wert der integer16-Eigenschaft ab.
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
     * Legt den Wert der integer16-Eigenschaft fest.
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
     * Ruft den Wert der integer32-Eigenschaft ab.
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
     * Legt den Wert der integer32-Eigenschaft fest.
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
     * Ruft den Wert der integer24-Eigenschaft ab.
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
     * Legt den Wert der integer24-Eigenschaft fest.
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
     * Ruft den Wert der integer40-Eigenschaft ab.
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
     * Legt den Wert der integer40-Eigenschaft fest.
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
     * Ruft den Wert der integer48-Eigenschaft ab.
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
     * Legt den Wert der integer48-Eigenschaft fest.
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
     * Ruft den Wert der integer56-Eigenschaft ab.
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
     * Legt den Wert der integer56-Eigenschaft fest.
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
     * Ruft den Wert der integer64-Eigenschaft ab.
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
     * Legt den Wert der integer64-Eigenschaft fest.
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
     * Ruft den Wert der unsigned8-Eigenschaft ab.
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
     * Legt den Wert der unsigned8-Eigenschaft fest.
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
     * Ruft den Wert der unsigned16-Eigenschaft ab.
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
     * Legt den Wert der unsigned16-Eigenschaft fest.
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
     * Ruft den Wert der unsigned32-Eigenschaft ab.
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
     * Legt den Wert der unsigned32-Eigenschaft fest.
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
     * Ruft den Wert der unsigned24-Eigenschaft ab.
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
     * Legt den Wert der unsigned24-Eigenschaft fest.
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
     * Ruft den Wert der unsigned40-Eigenschaft ab.
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
     * Legt den Wert der unsigned40-Eigenschaft fest.
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
     * Ruft den Wert der unsigned48-Eigenschaft ab.
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
     * Legt den Wert der unsigned48-Eigenschaft fest.
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
     * Ruft den Wert der unsigned56-Eigenschaft ab.
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
     * Legt den Wert der unsigned56-Eigenschaft fest.
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
     * Ruft den Wert der unsigned64-Eigenschaft ab.
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
     * Legt den Wert der unsigned64-Eigenschaft fest.
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
     * Ruft den Wert der real32-Eigenschaft ab.
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
     * Legt den Wert der real32-Eigenschaft fest.
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
     * Ruft den Wert der real64-Eigenschaft ab.
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
     * Legt den Wert der real64-Eigenschaft fest.
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
     * Ruft den Wert der visibleString-Eigenschaft ab.
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
     * Legt den Wert der visibleString-Eigenschaft fest.
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
     * Ruft den Wert der octetString-Eigenschaft ab.
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
     * Legt den Wert der octetString-Eigenschaft fest.
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
     * Ruft den Wert der unicodeString-Eigenschaft ab.
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
     * Legt den Wert der unicodeString-Eigenschaft fest.
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
     * Ruft den Wert der timeOfDay-Eigenschaft ab.
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
     * Legt den Wert der timeOfDay-Eigenschaft fest.
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
     * Ruft den Wert der timeDiff-Eigenschaft ab.
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
     * Legt den Wert der timeDiff-Eigenschaft fest.
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
     * Ruft den Wert der domain-Eigenschaft ab.
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
     * Legt den Wert der domain-Eigenschaft fest.
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
     * Ruft den Wert der macaddress-Eigenschaft ab.
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
     * Legt den Wert der macaddress-Eigenschaft fest.
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
     * Ruft den Wert der ipaddress-Eigenschaft ab.
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
     * Legt den Wert der ipaddress-Eigenschaft fest.
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
     * Ruft den Wert der nettime-Eigenschaft ab.
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
     * Legt den Wert der nettime-Eigenschaft fest.
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
     * Ruft den Wert der dataType-Eigenschaft ab.
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
     * Legt den Wert der dataType-Eigenschaft fest.
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

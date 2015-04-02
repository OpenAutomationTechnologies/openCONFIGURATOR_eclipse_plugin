
package org.epsg.openconfigurator.xmlbinding.xdd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java-Klasse für t_dynamicChannel complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="t_dynamicChannel"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="dataType" use="required" type="{http://www.w3.org/2001/XMLSchema}hexBinary" /&gt;
 *       &lt;attribute name="accessType" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *             &lt;enumeration value="readOnly"/&gt;
 *             &lt;enumeration value="writeOnly"/&gt;
 *             &lt;enumeration value="readWriteOutput"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="startIndex" use="required" type="{http://www.w3.org/2001/XMLSchema}hexBinary" /&gt;
 *       &lt;attribute name="endIndex" use="required" type="{http://www.w3.org/2001/XMLSchema}hexBinary" /&gt;
 *       &lt;attribute name="maxNumber" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *       &lt;attribute name="addressOffset" use="required" type="{http://www.w3.org/2001/XMLSchema}hexBinary" /&gt;
 *       &lt;attribute name="bitAlignment" type="{http://www.w3.org/2001/XMLSchema}unsignedByte" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "t_dynamicChannel")
public class TDynamicChannel {

    @XmlAttribute(name = "dataType", required = true)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] dataType;
    @XmlAttribute(name = "accessType", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String accessType;
    @XmlAttribute(name = "startIndex", required = true)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] startIndex;
    @XmlAttribute(name = "endIndex", required = true)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] endIndex;
    @XmlAttribute(name = "maxNumber", required = true)
    @XmlSchemaType(name = "unsignedInt")
    protected long maxNumber;
    @XmlAttribute(name = "addressOffset", required = true)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] addressOffset;
    @XmlAttribute(name = "bitAlignment")
    @XmlSchemaType(name = "unsignedByte")
    protected Short bitAlignment;

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

    /**
     * Ruft den Wert der accessType-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccessType() {
        return accessType;
    }

    /**
     * Legt den Wert der accessType-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccessType(String value) {
        this.accessType = value;
    }

    /**
     * Ruft den Wert der startIndex-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getStartIndex() {
        return startIndex;
    }

    /**
     * Legt den Wert der startIndex-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartIndex(byte[] value) {
        this.startIndex = value;
    }

    /**
     * Ruft den Wert der endIndex-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getEndIndex() {
        return endIndex;
    }

    /**
     * Legt den Wert der endIndex-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndIndex(byte[] value) {
        this.endIndex = value;
    }

    /**
     * Ruft den Wert der maxNumber-Eigenschaft ab.
     * 
     */
    public long getMaxNumber() {
        return maxNumber;
    }

    /**
     * Legt den Wert der maxNumber-Eigenschaft fest.
     * 
     */
    public void setMaxNumber(long value) {
        this.maxNumber = value;
    }

    /**
     * Ruft den Wert der addressOffset-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getAddressOffset() {
        return addressOffset;
    }

    /**
     * Legt den Wert der addressOffset-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddressOffset(byte[] value) {
        this.addressOffset = value;
    }

    /**
     * Ruft den Wert der bitAlignment-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getBitAlignment() {
        return bitAlignment;
    }

    /**
     * Legt den Wert der bitAlignment-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setBitAlignment(Short value) {
        this.bitAlignment = value;
    }

}

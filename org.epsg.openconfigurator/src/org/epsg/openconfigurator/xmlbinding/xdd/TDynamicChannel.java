
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
 * <p>Java class for t_dynamicChannel complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
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

    /**
     * Gets the value of the accessType property.
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
     * Sets the value of the accessType property.
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
     * Gets the value of the startIndex property.
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
     * Sets the value of the startIndex property.
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
     * Gets the value of the endIndex property.
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
     * Sets the value of the endIndex property.
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
     * Gets the value of the maxNumber property.
     * 
     */
    public long getMaxNumber() {
        return maxNumber;
    }

    /**
     * Sets the value of the maxNumber property.
     * 
     */
    public void setMaxNumber(long value) {
        this.maxNumber = value;
    }

    /**
     * Gets the value of the addressOffset property.
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
     * Sets the value of the addressOffset property.
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
     * Gets the value of the bitAlignment property.
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
     * Sets the value of the bitAlignment property.
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

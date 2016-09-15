
package org.epsg.openconfigurator.xmlbinding.xdd;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="baseIndex" use="required" type="{http://www.ethernet-powerlink.org}t_Index" /&gt;
 *       &lt;attribute name="maxIndex" type="{http://www.ethernet-powerlink.org}t_Index" /&gt;
 *       &lt;attribute name="maxSubIndex" use="required" type="{http://www.ethernet-powerlink.org}t_SubIndex" /&gt;
 *       &lt;attribute name="sortMode" use="required" type="{http://www.ethernet-powerlink.org}t_sortMode" /&gt;
 *       &lt;attribute name="sortNumber" use="required" type="{http://www.ethernet-powerlink.org}t_addressingAttribute" /&gt;
 *       &lt;attribute name="sortStep" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" default="1" /&gt;
 *       &lt;attribute name="PDOmapping" type="{http://www.ethernet-powerlink.org}t_ObjectPDOMapping" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "range")
public class Range {

    @XmlAttribute(name = "name", required = true)
    protected String name;
    @XmlAttribute(name = "baseIndex", required = true)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    protected byte[] baseIndex;
    @XmlAttribute(name = "maxIndex")
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    protected byte[] maxIndex;
    @XmlAttribute(name = "maxSubIndex", required = true)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    protected byte[] maxSubIndex;
    @XmlAttribute(name = "sortMode", required = true)
    protected TSortMode sortMode;
    @XmlAttribute(name = "sortNumber", required = true)
    protected TAddressingAttribute sortNumber;
    @XmlAttribute(name = "sortStep")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger sortStep;
    @XmlAttribute(name = "PDOmapping")
    protected TObjectPDOMapping pdOmapping;

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
     * Gets the value of the baseIndex property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getBaseIndex() {
        return baseIndex;
    }

    /**
     * Sets the value of the baseIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBaseIndex(byte[] value) {
        this.baseIndex = value;
    }

    /**
     * Gets the value of the maxIndex property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getMaxIndex() {
        return maxIndex;
    }

    /**
     * Sets the value of the maxIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxIndex(byte[] value) {
        this.maxIndex = value;
    }

    /**
     * Gets the value of the maxSubIndex property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getMaxSubIndex() {
        return maxSubIndex;
    }

    /**
     * Sets the value of the maxSubIndex property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxSubIndex(byte[] value) {
        this.maxSubIndex = value;
    }

    /**
     * Gets the value of the sortMode property.
     * 
     * @return
     *     possible object is
     *     {@link TSortMode }
     *     
     */
    public TSortMode getSortMode() {
        return sortMode;
    }

    /**
     * Sets the value of the sortMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link TSortMode }
     *     
     */
    public void setSortMode(TSortMode value) {
        this.sortMode = value;
    }

    /**
     * Gets the value of the sortNumber property.
     * 
     * @return
     *     possible object is
     *     {@link TAddressingAttribute }
     *     
     */
    public TAddressingAttribute getSortNumber() {
        return sortNumber;
    }

    /**
     * Sets the value of the sortNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link TAddressingAttribute }
     *     
     */
    public void setSortNumber(TAddressingAttribute value) {
        this.sortNumber = value;
    }

    /**
     * Gets the value of the sortStep property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSortStep() {
        if (sortStep == null) {
            return new BigInteger("1");
        } else {
            return sortStep;
        }
    }

    /**
     * Sets the value of the sortStep property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSortStep(BigInteger value) {
        this.sortStep = value;
    }

    /**
     * Gets the value of the pdOmapping property.
     * 
     * @return
     *     possible object is
     *     {@link TObjectPDOMapping }
     *     
     */
    public TObjectPDOMapping getPDOmapping() {
        return pdOmapping;
    }

    /**
     * Sets the value of the pdOmapping property.
     * 
     * @param value
     *     allowed object is
     *     {@link TObjectPDOMapping }
     *     
     */
    public void setPDOmapping(TObjectPDOMapping value) {
        this.pdOmapping = value;
    }

}

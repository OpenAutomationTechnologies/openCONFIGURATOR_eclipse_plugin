
package org.epsg.openconfigurator.xmlbinding.firmware;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for anonymous complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="Ver" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
 *       &lt;attribute name="Ven" use="required"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;pattern value="0x[0-9A-Fa-f]{8,8}"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="Var" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *       &lt;attribute name="Use" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" fixed="fw" /&gt;
 *       &lt;attribute name="Rem" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="Mat"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;minLength value="0"/&gt;
 *             &lt;maxLength value="18"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="Len" use="required" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" /&gt;
 *       &lt;attribute name="KeepXmlheader"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte"&gt;
 *             &lt;enumeration value="0"/&gt;
 *             &lt;enumeration value="1"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="Fct" use="required" type="{http://www.w3.org/2001/XMLSchema}string" fixed="_" /&gt;
 *       &lt;attribute name="Dev" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *       &lt;attribute name="Date"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;pattern value="[0-3]{1}[1-9]{1}.[0-1]{1}[1-2]{1}.[0-9]{4}"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="Chk"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;pattern value="0x[0-9A-Fa-f]{4,4}"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *       &lt;attribute name="ApplSwTime" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *       &lt;attribute name="ApplSwDate" use="required" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "Firmware")
public class Firmware {

    @XmlAttribute(name = "Ver", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger ver;
    @XmlAttribute(name = "Ven", required = true)
    protected String ven;
    @XmlAttribute(name = "Var", required = true)
    @XmlSchemaType(name = "unsignedInt")
    protected long var;
    @XmlAttribute(name = "Use", required = true)
    @XmlSchemaType(name = "anySimpleType")
    protected String use;
    @XmlAttribute(name = "Rem")
    protected String rem;
    @XmlAttribute(name = "Mat")
    protected String mat;
    @XmlAttribute(name = "Len", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger len;
    @XmlAttribute(name = "KeepXmlheader")
    protected Byte keepXmlheader;
    @XmlAttribute(name = "Fct", required = true)
    protected String fct;
    @XmlAttribute(name = "Dev", required = true)
    @XmlSchemaType(name = "unsignedInt")
    protected long dev;
    @XmlAttribute(name = "Date")
    protected String date;
    @XmlAttribute(name = "Chk")
    protected String chk;
    @XmlAttribute(name = "ApplSwTime", required = true)
    @XmlSchemaType(name = "unsignedInt")
    protected long applSwTime;
    @XmlAttribute(name = "ApplSwDate", required = true)
    @XmlSchemaType(name = "unsignedInt")
    protected long applSwDate;

    /**
     * Gets the value of the applSwDate property.
     *
     */
    public long getApplSwDate() {
        return applSwDate;
    }

    /**
     * Gets the value of the applSwTime property.
     *
     */
    public long getApplSwTime() {
        return applSwTime;
    }

    /**
     * Gets the value of the chk property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getChk() {
        return chk;
    }

    /**
     * Gets the value of the date property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets the value of the dev property.
     *
     */
    public long getDev() {
        return dev;
    }

    /**
     * Gets the value of the fct property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getFct() {
        if (fct == null) {
            return "_";
        } else {
            return fct;
        }
    }

    /**
     * Gets the value of the keepXmlheader property.
     *
     * @return possible object is {@link Byte }
     *
     */
    public Byte getKeepXmlheader() {
        return keepXmlheader;
    }

    /**
     * Gets the value of the len property.
     *
     * @return possible object is {@link BigInteger }
     *
     */
    public BigInteger getLen() {
        return len;
    }

    /**
     * Gets the value of the mat property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getMat() {
        return mat;
    }

    /**
     * Gets the value of the rem property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getRem() {
        return rem;
    }

    /**
     * Gets the value of the use property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getUse() {
        if (use == null) {
            return "fw";
        } else {
            return use;
        }
    }

    /**
     * Gets the value of the var property.
     *
     */
    public long getVar() {
        return var;
    }

    /**
     * Gets the value of the ven property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getVen() {
        return ven;
    }

    /**
     * Gets the value of the ver property.
     *
     * @return possible object is {@link BigInteger }
     *
     */
    public BigInteger getVer() {
        return ver;
    }

    /**
     * Sets the value of the applSwDate property.
     *
     */
    public void setApplSwDate(long value) {
        applSwDate = value;
    }

    /**
     * Sets the value of the applSwTime property.
     *
     */
    public void setApplSwTime(long value) {
        applSwTime = value;
    }

    /**
     * Sets the value of the chk property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setChk(String value) {
        chk = value;
    }

    /**
     * Sets the value of the date property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDate(String value) {
        date = value;
    }

    /**
     * Sets the value of the dev property.
     *
     */
    public void setDev(long value) {
        dev = value;
    }

    /**
     * Sets the value of the fct property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setFct(String value) {
        fct = value;
    }

    /**
     * Sets the value of the keepXmlheader property.
     *
     * @param value allowed object is {@link Byte }
     *
     */
    public void setKeepXmlheader(Byte value) {
        keepXmlheader = value;
    }

    /**
     * Sets the value of the len property.
     *
     * @param value allowed object is {@link BigInteger }
     *
     */
    public void setLen(BigInteger value) {
        len = value;
    }

    /**
     * Sets the value of the mat property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setMat(String value) {
        mat = value;
    }

    /**
     * Sets the value of the rem property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setRem(String value) {
        rem = value;
    }

    /**
     * Sets the value of the use property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setUse(String value) {
        use = value;
    }

    /**
     * Sets the value of the var property.
     *
     */
    public void setVar(long value) {
        var = value;
    }

    /**
     * Sets the value of the ven property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setVen(String value) {
        ven = value;
    }

    /**
     * Sets the value of the ver property.
     *
     * @param value allowed object is {@link BigInteger }
     *
     */
    public void setVer(BigInteger value) {
        ver = value;
    }

}

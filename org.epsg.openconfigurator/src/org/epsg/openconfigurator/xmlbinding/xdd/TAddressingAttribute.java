
package org.epsg.openconfigurator.xmlbinding.xdd;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for t_addressingAttribute.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="t_addressingAttribute"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *     &lt;enumeration value="continuous"/&gt;
 *     &lt;enumeration value="address"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "t_addressingAttribute")
@XmlEnum
public enum TAddressingAttribute {


    /**
     * A new index or subindex is created following the other.
     * 
     */
    @XmlEnumValue("continuous")
    CONTINUOUS("continuous"),

    /**
     * A new index is created depending on the address of the module.
     * 					This address can be assigned according to moduleAddressing attribute in the moduleInterface element.
     * 
     */
    @XmlEnumValue("address")
    ADDRESS("address");
    private final String value;

    TAddressingAttribute(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TAddressingAttribute fromValue(String v) {
        for (TAddressingAttribute c: TAddressingAttribute.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

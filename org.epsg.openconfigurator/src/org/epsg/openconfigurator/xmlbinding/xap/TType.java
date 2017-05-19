
package org.epsg.openconfigurator.xmlbinding.xap;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tType.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="tType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="input"/&gt;
 *     &lt;enumeration value="output"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 *
 */
@XmlType(name = "tType")
@XmlEnum
public enum TType {

    @XmlEnumValue("input")
    INPUT("input"),
    @XmlEnumValue("output")
    OUTPUT("output");
    private final String value;

    TType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TType fromValue(String v) {
        for (TType c: TType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}


package org.epsg.openconfigurator.xmlbinding.xdd;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for t_sortMode.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="t_sortMode"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *     &lt;enumeration value="index"/&gt;
 *     &lt;enumeration value="subindex"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "t_sortMode")
@XmlEnum
public enum TSortMode {

    @XmlEnumValue("index")
    INDEX("index"),
    @XmlEnumValue("subindex")
    SUBINDEX("subindex");
    private final String value;

    TSortMode(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TSortMode fromValue(String v) {
        for (TSortMode c: TSortMode.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

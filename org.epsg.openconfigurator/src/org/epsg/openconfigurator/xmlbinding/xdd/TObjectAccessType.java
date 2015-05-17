
package org.epsg.openconfigurator.xmlbinding.xdd;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for t_ObjectAccessType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="t_ObjectAccessType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *     &lt;enumeration value="ro"/&gt;
 *     &lt;enumeration value="wo"/&gt;
 *     &lt;enumeration value="rw"/&gt;
 *     &lt;enumeration value="const"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "t_ObjectAccessType")
@XmlEnum
public enum TObjectAccessType {


    /**
     * Object is read-only and can change.
     * 
     */
    @XmlEnumValue("ro")
    RO("ro"),

    /**
     * Object is write-only.
     * 
     */
    @XmlEnumValue("wo")
    WO("wo"),

    /**
     * Object can be read and written to.
     * 
     */
    @XmlEnumValue("rw")
    RW("rw"),

    /**
     * Object is read-only and cannot change.
     * 
     */
    @XmlEnumValue("const")
    CONST("const");
    private final String value;

    TObjectAccessType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TObjectAccessType fromValue(String v) {
        for (TObjectAccessType c: TObjectAccessType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

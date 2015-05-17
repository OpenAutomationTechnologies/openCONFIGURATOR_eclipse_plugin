
package org.epsg.openconfigurator.xmlbinding.xdd;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for t_ObjectPDOMapping.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="t_ObjectPDOMapping"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN"&gt;
 *     &lt;enumeration value="no"/&gt;
 *     &lt;enumeration value="default"/&gt;
 *     &lt;enumeration value="optional"/&gt;
 *     &lt;enumeration value="TPDO"/&gt;
 *     &lt;enumeration value="RPDO"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "t_ObjectPDOMapping")
@XmlEnum
public enum TObjectPDOMapping {


    /**
     * Object cannot be mapped to a PDO.
     * 
     */
    @XmlEnumValue("no")
    NO("no"),

    /**
     * Object is by default mapped to a PDO.
     * 
     */
    @XmlEnumValue("default")
    DEFAULT("default"),

    /**
     * Object can be mapped to any PDO.
     * 
     */
    @XmlEnumValue("optional")
    OPTIONAL("optional"),

    /**
     * Object can be mapped to a Transmit PDO.
     * 
     */
    TPDO("TPDO"),

    /**
     * Object can be mapped to a Receive PDO.
     * 
     */
    RPDO("RPDO");
    private final String value;

    TObjectPDOMapping(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TObjectPDOMapping fromValue(String v) {
        for (TObjectPDOMapping c: TObjectPDOMapping.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

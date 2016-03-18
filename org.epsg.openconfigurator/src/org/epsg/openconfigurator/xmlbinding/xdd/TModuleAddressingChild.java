
package org.epsg.openconfigurator.xmlbinding.xdd;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for t_moduleAddressingChild.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="t_moduleAddressingChild"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="manual"/&gt;
 *     &lt;enumeration value="position"/&gt;
 *     &lt;enumeration value="next"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "t_moduleAddressingChild")
@XmlEnum
public enum TModuleAddressingChild {


    /**
     * 
     * 						Module address can be assigned manually by the user. Requires modular head interface attribute module addressing to be manual.
     * 						
     * 
     */
    @XmlEnumValue("manual")
    MANUAL("manual"),

    /**
     * 
     * 						Module address shall be assigned according to the position of the module.
     * 						
     * 
     */
    @XmlEnumValue("position")
    POSITION("position"),

    /**
     * 
     * 						Module address shall be assigned to the next address after its preceding module address.
     * 						
     * 
     */
    @XmlEnumValue("next")
    NEXT("next");
    private final String value;

    TModuleAddressingChild(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TModuleAddressingChild fromValue(String v) {
        for (TModuleAddressingChild c: TModuleAddressingChild.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

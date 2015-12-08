
package org.epsg.openconfigurator.xmlbinding.xdd;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for t_moduleAddressingHead.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="t_moduleAddressingHead"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="manual"/&gt;
 *     &lt;enumeration value="position"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "t_moduleAddressingHead")
@XmlEnum
public enum TModuleAddressingHead {


    /**
     * 
     *                     User can provide an address for the module manually.
     *                     
     * 
     */
    @XmlEnumValue("manual")
    MANUAL("manual"),

    /**
     * 
     *                     Modules shall receive an address which is equal with the position on the bus.
     *                     
     * 
     */
    @XmlEnumValue("position")
    POSITION("position");
    private final String value;

    TModuleAddressingHead(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TModuleAddressingHead fromValue(String v) {
        for (TModuleAddressingHead c: TModuleAddressingHead.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

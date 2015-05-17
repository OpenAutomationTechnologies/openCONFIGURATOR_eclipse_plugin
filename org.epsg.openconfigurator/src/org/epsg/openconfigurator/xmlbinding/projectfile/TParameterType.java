
package org.epsg.openconfigurator.xmlbinding.projectfile;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tParameterType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="tParameterType"&gt;
 *   &lt;restriction base="{http://ethernet-powerlink.org/POWERLINK}tNodeAssignment"&gt;
 *     &lt;enumeration value="MN"/&gt;
 *     &lt;enumeration value="CN"/&gt;
 *     &lt;enumeration value="MNCN"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "tParameterType", namespace = "http://ethernet-powerlink.org/POWERLINK")
@XmlEnum(TNodeAssignment.class)
public enum TParameterType {

    MN,
    CN,
    MNCN;

    public String value() {
        return name();
    }

    public static TParameterType fromValue(String v) {
        return valueOf(v);
    }

}

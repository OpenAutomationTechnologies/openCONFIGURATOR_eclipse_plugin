
package org.epsg.openconfigurator.xmlbinding.projectfile;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tNodeAssignment.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="tNodeAssignment"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="MN"/&gt;
 *     &lt;enumeration value="CN"/&gt;
 *     &lt;enumeration value="MNCN"/&gt;
 *     &lt;enumeration value="NONE"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "tNodeAssignment", namespace = "http://ethernet-powerlink.org/POWERLINK")
@XmlEnum
public enum TNodeAssignment {

    MN,
    CN,
    MNCN,
    NONE;

    public String value() {
        return name();
    }

    public static TNodeAssignment fromValue(String v) {
        return valueOf(v);
    }

}

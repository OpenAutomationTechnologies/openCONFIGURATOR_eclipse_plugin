
package org.epsg.openconfigurator.xmlbinding.projectfile;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für tNodeAssignment.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
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

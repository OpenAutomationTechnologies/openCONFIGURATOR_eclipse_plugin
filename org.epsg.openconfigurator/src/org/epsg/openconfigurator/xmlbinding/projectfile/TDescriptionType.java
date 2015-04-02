
package org.epsg.openconfigurator.xmlbinding.projectfile;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für tDescriptionType.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="tDescriptionType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="SHORT"/&gt;
 *     &lt;enumeration value="LONG"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "tDescriptionType", namespace = "http://ethernet-powerlink.org/POWERLINK")
@XmlEnum
public enum TDescriptionType {

    SHORT,
    LONG;

    public String value() {
        return name();
    }

    public static TDescriptionType fromValue(String v) {
        return valueOf(v);
    }

}

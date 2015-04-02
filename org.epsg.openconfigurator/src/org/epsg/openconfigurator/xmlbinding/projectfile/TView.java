
package org.epsg.openconfigurator.xmlbinding.projectfile;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für tView.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="tView"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="BASIC"/&gt;
 *     &lt;enumeration value="ADVANCED"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "tView")
@XmlEnum
public enum TView {

    BASIC,
    ADVANCED;

    public String value() {
        return name();
    }

    public static TView fromValue(String v) {
        return valueOf(v);
    }

}

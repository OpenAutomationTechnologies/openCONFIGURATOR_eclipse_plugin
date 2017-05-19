
package org.epsg.openconfigurator.xmlbinding.projectfile;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tPathEnum.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="tPathEnum"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="CONFIG_TEXT"/&gt;
 *     &lt;enumeration value="CONFIG_BINARY"/&gt;
 *     &lt;enumeration value="CONFIG_CHAR_ARRAY"/&gt;
 *     &lt;enumeration value="XML_PROCESS_IMAGE"/&gt;
 *     &lt;enumeration value="C_PROCESS_IMAGE"/&gt;
 *     &lt;enumeration value="CSHARP_PROCESS_IMAGE"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "tPathEnum")
@XmlEnum
public enum TPathEnum {

    CONFIG_TEXT,
    CONFIG_BINARY,
    CONFIG_CHAR_ARRAY,
    XML_PROCESS_IMAGE,
    C_PROCESS_IMAGE,
    CSHARP_PROCESS_IMAGE;

    public String value() {
        return name();
    }

    public static TPathEnum fromValue(String v) {
        return valueOf(v);
    }

}

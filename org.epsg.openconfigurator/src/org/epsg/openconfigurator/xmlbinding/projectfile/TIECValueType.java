
package org.epsg.openconfigurator.xmlbinding.projectfile;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tIECValueType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="tIECValueType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="BOOL"/&gt;
 *     &lt;enumeration value="BITSTRING"/&gt;
 *     &lt;enumeration value="BYTE"/&gt;
 *     &lt;enumeration value="CHAR"/&gt;
 *     &lt;enumeration value="WORD"/&gt;
 *     &lt;enumeration value="DWORD"/&gt;
 *     &lt;enumeration value="LWORD"/&gt;
 *     &lt;enumeration value="SINT"/&gt;
 *     &lt;enumeration value="INT"/&gt;
 *     &lt;enumeration value="DINT"/&gt;
 *     &lt;enumeration value="LINT"/&gt;
 *     &lt;enumeration value="USINT"/&gt;
 *     &lt;enumeration value="UINT"/&gt;
 *     &lt;enumeration value="UDINT"/&gt;
 *     &lt;enumeration value="ULINT"/&gt;
 *     &lt;enumeration value="REAL"/&gt;
 *     &lt;enumeration value="LREAL"/&gt;
 *     &lt;enumeration value="STRING"/&gt;
 *     &lt;enumeration value="WSTRING"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "tIECValueType", namespace = "http://ethernet-powerlink.org/POWERLINK")
@XmlEnum
public enum TIECValueType {

    BOOL,
    BITSTRING,
    BYTE,
    CHAR,
    WORD,
    DWORD,
    LWORD,
    SINT,
    INT,
    DINT,
    LINT,
    USINT,
    UINT,
    UDINT,
    ULINT,
    REAL,
    LREAL,
    STRING,
    WSTRING;

    public String value() {
        return name();
    }

    public static TIECValueType fromValue(String v) {
        return valueOf(v);
    }

}

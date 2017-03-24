
package org.epsg.openconfigurator.xmlbinding.xap;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for tdataType.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="tdataType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="Integer8"/&gt;
 *     &lt;enumeration value="Integer16"/&gt;
 *     &lt;enumeration value="Integer32"/&gt;
 *     &lt;enumeration value="Integer64"/&gt;
 *     &lt;enumeration value="Unsigned8"/&gt;
 *     &lt;enumeration value="Unsigned16"/&gt;
 *     &lt;enumeration value="Unsigned32"/&gt;
 *     &lt;enumeration value="Unsigned64"/&gt;
 *     &lt;enumeration value="REAL"/&gt;
 *     &lt;enumeration value="LREAL"/&gt;
 *     &lt;enumeration value="BITSTRING"/&gt;
 *     &lt;enumeration value="BOOL"/&gt;
 *     &lt;enumeration value="BYTE"/&gt;
 *     &lt;enumeration value="CHAR"/&gt;
 *     &lt;enumeration value="WORD"/&gt;
 *     &lt;enumeration value="DWORD"/&gt;
 *     &lt;enumeration value="LWORD"/&gt;
 *     &lt;enumeration value="STRING"/&gt;
 *     &lt;enumeration value="WSTRING"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 *
 */
@XmlType(name = "tdataType")
@XmlEnum
public enum TdataType {

    @XmlEnumValue("Integer8")
    INTEGER_8("Integer8"),
    @XmlEnumValue("Integer16")
    INTEGER_16("Integer16"),
    @XmlEnumValue("Integer32")
    INTEGER_32("Integer32"),
    @XmlEnumValue("Integer64")
    INTEGER_64("Integer64"),
    @XmlEnumValue("Unsigned8")
    UNSIGNED_8("Unsigned8"),
    @XmlEnumValue("Unsigned16")
    UNSIGNED_16("Unsigned16"),
    @XmlEnumValue("Unsigned32")
    UNSIGNED_32("Unsigned32"),
    @XmlEnumValue("Unsigned64")
    UNSIGNED_64("Unsigned64"),
    REAL("REAL"),
    LREAL("LREAL"),
    BITSTRING("BITSTRING"),
    BOOL("BOOL"),
    BYTE("BYTE"),
    CHAR("CHAR"),
    WORD("WORD"),
    DWORD("DWORD"),
    LWORD("LWORD"),
    STRING("STRING"),
    WSTRING("WSTRING");
    private final String value;

    TdataType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TdataType fromValue(String v) {
        for (TdataType c: TdataType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

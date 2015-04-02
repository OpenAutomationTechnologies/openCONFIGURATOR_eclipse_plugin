
package org.epsg.openconfigurator.xmlbinding.xdd;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für ProfileClassID_DataType.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * <p>
 * <pre>
 * &lt;simpleType name="ProfileClassID_DataType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="AIP"/&gt;
 *     &lt;enumeration value="Process"/&gt;
 *     &lt;enumeration value="InformationExchange"/&gt;
 *     &lt;enumeration value="Resource"/&gt;
 *     &lt;enumeration value="Device"/&gt;
 *     &lt;enumeration value="CommunicationNetwork"/&gt;
 *     &lt;enumeration value="Equipment"/&gt;
 *     &lt;enumeration value="Human"/&gt;
 *     &lt;enumeration value="Material"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ProfileClassID_DataType")
@XmlEnum
public enum ProfileClassIDDataType {

    AIP("AIP"),
    @XmlEnumValue("Process")
    PROCESS("Process"),
    @XmlEnumValue("InformationExchange")
    INFORMATION_EXCHANGE("InformationExchange"),
    @XmlEnumValue("Resource")
    RESOURCE("Resource"),
    @XmlEnumValue("Device")
    DEVICE("Device"),
    @XmlEnumValue("CommunicationNetwork")
    COMMUNICATION_NETWORK("CommunicationNetwork"),
    @XmlEnumValue("Equipment")
    EQUIPMENT("Equipment"),
    @XmlEnumValue("Human")
    HUMAN("Human"),
    @XmlEnumValue("Material")
    MATERIAL("Material");
    private final String value;

    ProfileClassIDDataType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProfileClassIDDataType fromValue(String v) {
        for (ProfileClassIDDataType c: ProfileClassIDDataType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

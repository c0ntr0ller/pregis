
package ru.gosuslugi.dom.schema.integration._8_7_0_4.house_management;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HeatingSystemType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="HeatingSystemType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Opened"/>
 *     &lt;enumeration value="Closed"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "HeatingSystemType")
@XmlEnum
public enum HeatingSystemType {

    @XmlEnumValue("Opened")
    OPENED("Opened"),
    @XmlEnumValue("Closed")
    CLOSED("Closed");
    private final String value;

    HeatingSystemType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static HeatingSystemType fromValue(String v) {
        for (HeatingSystemType c: HeatingSystemType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

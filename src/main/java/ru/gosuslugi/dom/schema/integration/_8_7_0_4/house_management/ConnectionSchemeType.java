
package ru.gosuslugi.dom.schema.integration._8_7_0_4.house_management;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConnectionSchemeType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ConnectionSchemeType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Dependent"/>
 *     &lt;enumeration value="Independent"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ConnectionSchemeType")
@XmlEnum
public enum ConnectionSchemeType {

    @XmlEnumValue("Dependent")
    DEPENDENT("Dependent"),
    @XmlEnumValue("Independent")
    INDEPENDENT("Independent");
    private final String value;

    ConnectionSchemeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ConnectionSchemeType fromValue(String v) {
        for (ConnectionSchemeType c: ConnectionSchemeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}

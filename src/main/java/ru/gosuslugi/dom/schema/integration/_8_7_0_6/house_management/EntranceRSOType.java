
package ru.gosuslugi.dom.schema.integration._8_7_0_6.house_management;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Подъезд (для импорта от РСО)
 * 
 * <p>Java class for EntranceRSOType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EntranceRSOType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EntranceNum">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
 *               &lt;minInclusive value="1"/>
 *               &lt;maxInclusive value="99"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EntranceRSOType", propOrder = {
    "entranceNum"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_7_0_6.house_management.ImportHouseRSORequest.ApartmentHouse.EntranceToCreate.class
})
public class EntranceRSOType {

    @XmlElement(name = "EntranceNum")
    protected byte entranceNum;

    /**
     * Gets the value of the entranceNum property.
     * 
     */
    public byte getEntranceNum() {
        return entranceNum;
    }

    /**
     * Sets the value of the entranceNum property.
     * 
     */
    public void setEntranceNum(byte value) {
        this.entranceNum = value;
    }

}

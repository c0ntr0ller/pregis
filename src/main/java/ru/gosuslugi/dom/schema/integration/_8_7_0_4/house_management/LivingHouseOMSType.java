
package ru.gosuslugi.dom.schema.integration._8_7_0_4.house_management;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Жилой дом (для импорта от ОМС)
 * 
 * <p>Java class for LivingHouseOMSType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LivingHouseOMSType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BasicCharacteristicts" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}HouseBasicOMSType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LivingHouseOMSType", propOrder = {
    "basicCharacteristicts"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_7_0_4.house_management.ImportHouseOMSRequest.LivingHouse.LivingHouseToCreate.class
})
public class LivingHouseOMSType {

    @XmlElement(name = "BasicCharacteristicts", required = true)
    protected HouseBasicOMSType basicCharacteristicts;

    /**
     * Gets the value of the basicCharacteristicts property.
     * 
     * @return
     *     possible object is
     *     {@link HouseBasicOMSType }
     *     
     */
    public HouseBasicOMSType getBasicCharacteristicts() {
        return basicCharacteristicts;
    }

    /**
     * Sets the value of the basicCharacteristicts property.
     * 
     * @param value
     *     allowed object is
     *     {@link HouseBasicOMSType }
     *     
     */
    public void setBasicCharacteristicts(HouseBasicOMSType value) {
        this.basicCharacteristicts = value;
    }

}

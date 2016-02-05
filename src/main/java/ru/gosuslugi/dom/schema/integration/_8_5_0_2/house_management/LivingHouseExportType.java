
package ru.gosuslugi.dom.schema.integration._8_5_0_2.house_management;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_5_0.NsiRef;


/**
 * Жилой дом (для экспорта)
 * 
 * <p>Java class for LivingHouseExportType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LivingHouseExportType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BasicCharacteristicts" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}HouseBasicExportType"/>
 *         &lt;element name="ResidentialHouseType" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}nsiRef" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LivingHouseExportType", propOrder = {
    "basicCharacteristicts",
    "residentialHouseType"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_5_0_2.house_management.ExportHouseResultType.LivingHouse.class
})
public class LivingHouseExportType {

    @XmlElement(name = "BasicCharacteristicts", required = true)
    protected HouseBasicExportType basicCharacteristicts;
    @XmlElement(name = "ResidentialHouseType")
    protected NsiRef residentialHouseType;

    /**
     * Gets the value of the basicCharacteristicts property.
     * 
     * @return
     *     possible object is
     *     {@link HouseBasicExportType }
     *     
     */
    public HouseBasicExportType getBasicCharacteristicts() {
        return basicCharacteristicts;
    }

    /**
     * Sets the value of the basicCharacteristicts property.
     * 
     * @param value
     *     allowed object is
     *     {@link HouseBasicExportType }
     *     
     */
    public void setBasicCharacteristicts(HouseBasicExportType value) {
        this.basicCharacteristicts = value;
    }

    /**
     * Gets the value of the residentialHouseType property.
     * 
     * @return
     *     possible object is
     *     {@link NsiRef }
     *     
     */
    public NsiRef getResidentialHouseType() {
        return residentialHouseType;
    }

    /**
     * Sets the value of the residentialHouseType property.
     * 
     * @param value
     *     allowed object is
     *     {@link NsiRef }
     *     
     */
    public void setResidentialHouseType(NsiRef value) {
        this.residentialHouseType = value;
    }

}

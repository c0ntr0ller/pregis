
package ru.gosuslugi.dom.schema.integration._8_7_0_4.house_management;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_7_0.NsiRef;


/**
 * Многоквартирный дом (обновление данных для УО)
 * 
 * <p>Java class for ApartmentHouseUpdateUOType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApartmentHouseUpdateUOType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BasicCharacteristicts" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}HouseBasicUpdateUOType"/>
 *         &lt;element name="UndergroundFloorCount" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}FloorType" minOccurs="0"/>
 *         &lt;element name="MinFloorCount" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
 *               &lt;maxInclusive value="99"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="OverhaulFormingKind" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}nsiRef" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApartmentHouseUpdateUOType", propOrder = {
    "basicCharacteristicts",
    "undergroundFloorCount",
    "minFloorCount",
    "overhaulFormingKind"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_7_0_4.house_management.ImportHouseUORequest.ApartmentHouse.ApartmentHouseToUpdate.class
})
public class ApartmentHouseUpdateUOType {

    @XmlElement(name = "BasicCharacteristicts", required = true)
    protected HouseBasicUpdateUOType basicCharacteristicts;
    @XmlElement(name = "UndergroundFloorCount")
    protected String undergroundFloorCount;
    @XmlElement(name = "MinFloorCount")
    protected Byte minFloorCount;
    @XmlElement(name = "OverhaulFormingKind")
    protected NsiRef overhaulFormingKind;

    /**
     * Gets the value of the basicCharacteristicts property.
     * 
     * @return
     *     possible object is
     *     {@link HouseBasicUpdateUOType }
     *     
     */
    public HouseBasicUpdateUOType getBasicCharacteristicts() {
        return basicCharacteristicts;
    }

    /**
     * Sets the value of the basicCharacteristicts property.
     * 
     * @param value
     *     allowed object is
     *     {@link HouseBasicUpdateUOType }
     *     
     */
    public void setBasicCharacteristicts(HouseBasicUpdateUOType value) {
        this.basicCharacteristicts = value;
    }

    /**
     * Gets the value of the undergroundFloorCount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUndergroundFloorCount() {
        return undergroundFloorCount;
    }

    /**
     * Sets the value of the undergroundFloorCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUndergroundFloorCount(String value) {
        this.undergroundFloorCount = value;
    }

    /**
     * Gets the value of the minFloorCount property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getMinFloorCount() {
        return minFloorCount;
    }

    /**
     * Sets the value of the minFloorCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setMinFloorCount(Byte value) {
        this.minFloorCount = value;
    }

    /**
     * Gets the value of the overhaulFormingKind property.
     * 
     * @return
     *     possible object is
     *     {@link NsiRef }
     *     
     */
    public NsiRef getOverhaulFormingKind() {
        return overhaulFormingKind;
    }

    /**
     * Sets the value of the overhaulFormingKind property.
     * 
     * @param value
     *     allowed object is
     *     {@link NsiRef }
     *     
     */
    public void setOverhaulFormingKind(NsiRef value) {
        this.overhaulFormingKind = value;
    }

}


package ru.gosuslugi.dom.schema.integration._8_5_0_2.house_management;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_5_0.NsiRef;


/**
 * Многоквартирный дом (для импорта от ОМС)
 * 
 * <p>Java class for ApartmentHouseOMSType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApartmentHouseOMSType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BasicCharacteristicts" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}HouseBasicOMSType"/>
 *         &lt;element name="BuiltUpArea" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}BuiltUpAreaType" minOccurs="0"/>
 *         &lt;element name="UndergroundFloorCount" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}FloorType"/>
 *         &lt;element name="MinFloorCount" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
 *               &lt;maxInclusive value="99"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="OverhaulYear" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}YearType" minOccurs="0"/>
 *         &lt;element name="OverhaulFormingKind" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}nsiRef" minOccurs="0"/>
 *         &lt;element name="NonResidentialSquare" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}NonResidentialSquareType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApartmentHouseOMSType", propOrder = {
    "basicCharacteristicts",
    "builtUpArea",
    "undergroundFloorCount",
    "minFloorCount",
    "overhaulYear",
    "overhaulFormingKind",
    "nonResidentialSquare"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_5_0_2.house_management.ImportHouseOMSRequest.ApartmentHouse.ApartmentHouseToCreate.class
})
public class ApartmentHouseOMSType {

    @XmlElement(name = "BasicCharacteristicts", required = true)
    protected HouseBasicOMSType basicCharacteristicts;
    @XmlElement(name = "BuiltUpArea")
    protected BigDecimal builtUpArea;
    @XmlElement(name = "UndergroundFloorCount", required = true)
    protected String undergroundFloorCount;
    @XmlElement(name = "MinFloorCount")
    protected Byte minFloorCount;
    @XmlElement(name = "OverhaulYear")
    protected Short overhaulYear;
    @XmlElement(name = "OverhaulFormingKind")
    protected NsiRef overhaulFormingKind;
    @XmlElement(name = "NonResidentialSquare", required = true)
    protected BigDecimal nonResidentialSquare;

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

    /**
     * Gets the value of the builtUpArea property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBuiltUpArea() {
        return builtUpArea;
    }

    /**
     * Sets the value of the builtUpArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBuiltUpArea(BigDecimal value) {
        this.builtUpArea = value;
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
     * Gets the value of the overhaulYear property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getOverhaulYear() {
        return overhaulYear;
    }

    /**
     * Sets the value of the overhaulYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setOverhaulYear(Short value) {
        this.overhaulYear = value;
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

    /**
     * Gets the value of the nonResidentialSquare property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNonResidentialSquare() {
        return nonResidentialSquare;
    }

    /**
     * Sets the value of the nonResidentialSquare property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNonResidentialSquare(BigDecimal value) {
        this.nonResidentialSquare = value;
    }

}

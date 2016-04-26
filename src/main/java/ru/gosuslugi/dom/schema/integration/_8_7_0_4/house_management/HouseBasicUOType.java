
package ru.gosuslugi.dom.schema.integration._8_7_0_4.house_management;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_7_0.NsiRef;
import ru.gosuslugi.dom.schema.integration._8_7_0.OKTMORefType;


/**
 * Основные характеристики дома (для импорта от УО)
 * 
 * <p>Java class for HouseBasicUOType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HouseBasicUOType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}GKN_EGRP_KeyType">
 *       &lt;sequence>
 *         &lt;element name="FIASHouseGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}FIASHouseGUIDType"/>
 *         &lt;element name="TotalSquare" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}TotalSquareType"/>
 *         &lt;element name="State" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}nsiRef"/>
 *         &lt;element name="UsedYear" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}UsedYearType"/>
 *         &lt;element name="FloorCount" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}FloorType"/>
 *         &lt;element name="OKTMO" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}OKTMORefType" minOccurs="0"/>
 *         &lt;element name="OlsonTZ" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}nsiRef"/>
 *         &lt;element name="ResidentialSquare" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/house-management/}ResidentialSquareType"/>
 *         &lt;element name="CulturalHeritage" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HouseBasicUOType", propOrder = {
    "fiasHouseGuid",
    "totalSquare",
    "state",
    "usedYear",
    "floorCount",
    "oktmo",
    "olsonTZ",
    "residentialSquare",
    "culturalHeritage"
})
public class HouseBasicUOType
    extends GKNEGRPKeyType
{

    @XmlElement(name = "FIASHouseGuid", required = true)
    protected String fiasHouseGuid;
    @XmlElement(name = "TotalSquare", required = true)
    protected BigDecimal totalSquare;
    @XmlElement(name = "State", required = true)
    protected NsiRef state;
    @XmlElement(name = "UsedYear")
    protected short usedYear;
    @XmlElement(name = "FloorCount", required = true)
    protected String floorCount;
    @XmlElement(name = "OKTMO")
    protected OKTMORefType oktmo;
    @XmlElement(name = "OlsonTZ", required = true)
    protected NsiRef olsonTZ;
    @XmlElement(name = "ResidentialSquare", required = true)
    protected BigDecimal residentialSquare;
    @XmlElement(name = "CulturalHeritage")
    protected boolean culturalHeritage;

    /**
     * Gets the value of the fiasHouseGuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFIASHouseGuid() {
        return fiasHouseGuid;
    }

    /**
     * Sets the value of the fiasHouseGuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFIASHouseGuid(String value) {
        this.fiasHouseGuid = value;
    }

    /**
     * Gets the value of the totalSquare property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalSquare() {
        return totalSquare;
    }

    /**
     * Sets the value of the totalSquare property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalSquare(BigDecimal value) {
        this.totalSquare = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link NsiRef }
     *     
     */
    public NsiRef getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link NsiRef }
     *     
     */
    public void setState(NsiRef value) {
        this.state = value;
    }

    /**
     * Gets the value of the usedYear property.
     * 
     */
    public short getUsedYear() {
        return usedYear;
    }

    /**
     * Sets the value of the usedYear property.
     * 
     */
    public void setUsedYear(short value) {
        this.usedYear = value;
    }

    /**
     * Gets the value of the floorCount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFloorCount() {
        return floorCount;
    }

    /**
     * Sets the value of the floorCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFloorCount(String value) {
        this.floorCount = value;
    }

    /**
     * Gets the value of the oktmo property.
     * 
     * @return
     *     possible object is
     *     {@link OKTMORefType }
     *     
     */
    public OKTMORefType getOKTMO() {
        return oktmo;
    }

    /**
     * Sets the value of the oktmo property.
     * 
     * @param value
     *     allowed object is
     *     {@link OKTMORefType }
     *     
     */
    public void setOKTMO(OKTMORefType value) {
        this.oktmo = value;
    }

    /**
     * Gets the value of the olsonTZ property.
     * 
     * @return
     *     possible object is
     *     {@link NsiRef }
     *     
     */
    public NsiRef getOlsonTZ() {
        return olsonTZ;
    }

    /**
     * Sets the value of the olsonTZ property.
     * 
     * @param value
     *     allowed object is
     *     {@link NsiRef }
     *     
     */
    public void setOlsonTZ(NsiRef value) {
        this.olsonTZ = value;
    }

    /**
     * Gets the value of the residentialSquare property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getResidentialSquare() {
        return residentialSquare;
    }

    /**
     * Sets the value of the residentialSquare property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setResidentialSquare(BigDecimal value) {
        this.residentialSquare = value;
    }

    /**
     * Gets the value of the culturalHeritage property.
     * 
     */
    public boolean isCulturalHeritage() {
        return culturalHeritage;
    }

    /**
     * Sets the value of the culturalHeritage property.
     * 
     */
    public void setCulturalHeritage(boolean value) {
        this.culturalHeritage = value;
    }

}

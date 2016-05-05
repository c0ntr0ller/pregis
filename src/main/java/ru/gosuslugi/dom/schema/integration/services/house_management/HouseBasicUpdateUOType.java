
package ru.gosuslugi.dom.schema.integration.services.house_management;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration.base.NsiRef;
import ru.gosuslugi.dom.schema.integration.base.OKTMORefType;


/**
 * Основные характеристики дома (обновление данных для УО)
 * 
 * <p>Java class for HouseBasicUpdateUOType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HouseBasicUpdateUOType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}GKN_EGRP_KeyType">
 *       &lt;sequence>
 *         &lt;element name="FIASHouseGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}FIASHouseGUIDType"/>
 *         &lt;element name="TotalSquare" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}TotalSquareType" minOccurs="0"/>
 *         &lt;element name="State" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}nsiRef" minOccurs="0"/>
 *         &lt;element name="UsedYear" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}UsedYearType" minOccurs="0"/>
 *         &lt;element name="FloorCount" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}FloorType" minOccurs="0"/>
 *         &lt;element name="OKTMO" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}OKTMORefType" minOccurs="0"/>
 *         &lt;element name="OlsonTZ" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}nsiRef" minOccurs="0"/>
 *         &lt;element name="ResidentialSquare" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}ResidentialSquareType" minOccurs="0"/>
 *         &lt;element name="CulturalHeritage" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HouseBasicUpdateUOType", propOrder = {
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
public class HouseBasicUpdateUOType
    extends GKNEGRPKeyType
{

    @XmlElement(name = "FIASHouseGuid", required = true)
    protected String fiasHouseGuid;
    @XmlElement(name = "TotalSquare")
    protected BigDecimal totalSquare;
    @XmlElement(name = "State")
    protected NsiRef state;
    @XmlElement(name = "UsedYear")
    protected Short usedYear;
    @XmlElement(name = "FloorCount")
    protected String floorCount;
    @XmlElement(name = "OKTMO")
    protected OKTMORefType oktmo;
    @XmlElement(name = "OlsonTZ")
    protected NsiRef olsonTZ;
    @XmlElement(name = "ResidentialSquare")
    protected BigDecimal residentialSquare;
    @XmlElement(name = "CulturalHeritage")
    protected Boolean culturalHeritage;

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
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getUsedYear() {
        return usedYear;
    }

    /**
     * Sets the value of the usedYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setUsedYear(Short value) {
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
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isCulturalHeritage() {
        return culturalHeritage;
    }

    /**
     * Sets the value of the culturalHeritage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setCulturalHeritage(Boolean value) {
        this.culturalHeritage = value;
    }

}


package ru.gosuslugi.dom.schema.integration._8_5_0_2.house_management;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import ru.gosuslugi.dom.schema.integration._8_5_0.AttachmentType;
import ru.gosuslugi.dom.schema.integration._8_5_0.NsiRef;


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
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}GKNType">
 *       &lt;sequence>
 *         &lt;element name="FIASHouseGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}FIASHouseGUIDType"/>
 *         &lt;element name="TotalSquare" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}TotalSquareType" minOccurs="0"/>
 *         &lt;element name="State" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}nsiRef" minOccurs="0"/>
 *         &lt;element name="InnerWallMaterial" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}nsiRef" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ProjectSeries" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="255"/>
 *               &lt;minLength value="1"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ProjectType" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}nsiRef" minOccurs="0"/>
 *         &lt;element name="BuildingYear" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}YearType">
 *               &lt;minInclusive value="1600"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="UsedYear" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}UsedYearType" minOccurs="0"/>
 *         &lt;element name="TotalWear" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;fractionDigits value="2"/>
 *               &lt;totalDigits value="5"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="FloorCount" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}FloorType" minOccurs="0"/>
 *         &lt;element name="Energy" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="EnergyDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                   &lt;element name="EnergyCategory" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}nsiRef" minOccurs="0"/>
 *                   &lt;element name="ConfirmDoc" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}AttachmentType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="OlsonTZ" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}nsiRef" minOccurs="0"/>
 *         &lt;element name="ResidentialSquare" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/house-management/}ResidentialSquareType" minOccurs="0"/>
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
    "innerWallMaterial",
    "projectSeries",
    "projectType",
    "buildingYear",
    "usedYear",
    "totalWear",
    "floorCount",
    "energy",
    "olsonTZ",
    "residentialSquare",
    "culturalHeritage"
})
public class HouseBasicUpdateUOType
    extends GKNType
{

    @XmlElement(name = "FIASHouseGuid", required = true)
    protected String fiasHouseGuid;
    @XmlElement(name = "TotalSquare")
    protected BigDecimal totalSquare;
    @XmlElement(name = "State")
    protected NsiRef state;
    @XmlElement(name = "InnerWallMaterial")
    protected List<NsiRef> innerWallMaterial;
    @XmlElement(name = "ProjectSeries")
    protected String projectSeries;
    @XmlElement(name = "ProjectType")
    protected NsiRef projectType;
    @XmlElement(name = "BuildingYear")
    protected Short buildingYear;
    @XmlElement(name = "UsedYear")
    protected Short usedYear;
    @XmlElement(name = "TotalWear")
    protected BigDecimal totalWear;
    @XmlElement(name = "FloorCount")
    protected String floorCount;
    @XmlElement(name = "Energy")
    protected HouseBasicUpdateUOType.Energy energy;
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
     * Gets the value of the innerWallMaterial property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the innerWallMaterial property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInnerWallMaterial().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NsiRef }
     * 
     * 
     */
    public List<NsiRef> getInnerWallMaterial() {
        if (innerWallMaterial == null) {
            innerWallMaterial = new ArrayList<NsiRef>();
        }
        return this.innerWallMaterial;
    }

    /**
     * Gets the value of the projectSeries property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProjectSeries() {
        return projectSeries;
    }

    /**
     * Sets the value of the projectSeries property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProjectSeries(String value) {
        this.projectSeries = value;
    }

    /**
     * Gets the value of the projectType property.
     * 
     * @return
     *     possible object is
     *     {@link NsiRef }
     *     
     */
    public NsiRef getProjectType() {
        return projectType;
    }

    /**
     * Sets the value of the projectType property.
     * 
     * @param value
     *     allowed object is
     *     {@link NsiRef }
     *     
     */
    public void setProjectType(NsiRef value) {
        this.projectType = value;
    }

    /**
     * Gets the value of the buildingYear property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getBuildingYear() {
        return buildingYear;
    }

    /**
     * Sets the value of the buildingYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setBuildingYear(Short value) {
        this.buildingYear = value;
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
     * Gets the value of the totalWear property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalWear() {
        return totalWear;
    }

    /**
     * Sets the value of the totalWear property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalWear(BigDecimal value) {
        this.totalWear = value;
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
     * Gets the value of the energy property.
     * 
     * @return
     *     possible object is
     *     {@link HouseBasicUpdateUOType.Energy }
     *     
     */
    public HouseBasicUpdateUOType.Energy getEnergy() {
        return energy;
    }

    /**
     * Sets the value of the energy property.
     * 
     * @param value
     *     allowed object is
     *     {@link HouseBasicUpdateUOType.Energy }
     *     
     */
    public void setEnergy(HouseBasicUpdateUOType.Energy value) {
        this.energy = value;
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


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="EnergyDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="EnergyCategory" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}nsiRef" minOccurs="0"/>
     *         &lt;element name="ConfirmDoc" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}AttachmentType" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "energyDate",
        "energyCategory",
        "confirmDoc"
    })
    public static class Energy {

        @XmlElement(name = "EnergyDate")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar energyDate;
        @XmlElement(name = "EnergyCategory")
        protected NsiRef energyCategory;
        @XmlElement(name = "ConfirmDoc")
        protected List<AttachmentType> confirmDoc;

        /**
         * Gets the value of the energyDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getEnergyDate() {
            return energyDate;
        }

        /**
         * Sets the value of the energyDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setEnergyDate(XMLGregorianCalendar value) {
            this.energyDate = value;
        }

        /**
         * Gets the value of the energyCategory property.
         * 
         * @return
         *     possible object is
         *     {@link NsiRef }
         *     
         */
        public NsiRef getEnergyCategory() {
            return energyCategory;
        }

        /**
         * Sets the value of the energyCategory property.
         * 
         * @param value
         *     allowed object is
         *     {@link NsiRef }
         *     
         */
        public void setEnergyCategory(NsiRef value) {
            this.energyCategory = value;
        }

        /**
         * Gets the value of the confirmDoc property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the confirmDoc property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getConfirmDoc().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AttachmentType }
         * 
         * 
         */
        public List<AttachmentType> getConfirmDoc() {
            if (confirmDoc == null) {
                confirmDoc = new ArrayList<AttachmentType>();
            }
            return this.confirmDoc;
        }

    }

}

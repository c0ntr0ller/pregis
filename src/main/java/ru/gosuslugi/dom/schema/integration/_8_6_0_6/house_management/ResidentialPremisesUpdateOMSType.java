
package ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_6_0.NsiRef;


/**
 * Жилое помещение (обновление данных для ОМС)
 * 
 * <p>Java class for ResidentialPremisesUpdateOMSType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResidentialPremisesUpdateOMSType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}PremisesBasicUpdateOMSType">
 *       &lt;sequence>
 *         &lt;element name="EntranceNum">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
 *               &lt;maxInclusive value="99"/>
 *               &lt;minInclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PremisesCharacteristic" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}nsiRef" minOccurs="0"/>
 *         &lt;element name="RoomsNum" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}nsiRef" minOccurs="0"/>
 *         &lt;element name="GrossArea" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}PremisesAreaType" minOccurs="0"/>
 *         &lt;element name="TotalArea" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}PremisesAreaType" minOccurs="0"/>
 *         &lt;element name="ResidentialHouseType" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}nsiRef" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResidentialPremisesUpdateOMSType", propOrder = {
    "entranceNum",
    "premisesCharacteristic",
    "roomsNum",
    "grossArea",
    "totalArea",
    "residentialHouseType"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ImportHouseOMSRequest.ApartmentHouse.ResidentialPremises.ResidentialPremisesToUpdate.class
})
public class ResidentialPremisesUpdateOMSType
    extends PremisesBasicUpdateOMSType
{

    @XmlElement(name = "EntranceNum")
    protected byte entranceNum;
    @XmlElement(name = "PremisesCharacteristic")
    protected NsiRef premisesCharacteristic;
    @XmlElement(name = "RoomsNum")
    protected NsiRef roomsNum;
    @XmlElement(name = "GrossArea")
    protected BigDecimal grossArea;
    @XmlElement(name = "TotalArea")
    protected BigDecimal totalArea;
    @XmlElement(name = "ResidentialHouseType")
    protected NsiRef residentialHouseType;

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

    /**
     * Gets the value of the premisesCharacteristic property.
     * 
     * @return
     *     possible object is
     *     {@link NsiRef }
     *     
     */
    public NsiRef getPremisesCharacteristic() {
        return premisesCharacteristic;
    }

    /**
     * Sets the value of the premisesCharacteristic property.
     * 
     * @param value
     *     allowed object is
     *     {@link NsiRef }
     *     
     */
    public void setPremisesCharacteristic(NsiRef value) {
        this.premisesCharacteristic = value;
    }

    /**
     * Gets the value of the roomsNum property.
     * 
     * @return
     *     possible object is
     *     {@link NsiRef }
     *     
     */
    public NsiRef getRoomsNum() {
        return roomsNum;
    }

    /**
     * Sets the value of the roomsNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link NsiRef }
     *     
     */
    public void setRoomsNum(NsiRef value) {
        this.roomsNum = value;
    }

    /**
     * Gets the value of the grossArea property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getGrossArea() {
        return grossArea;
    }

    /**
     * Sets the value of the grossArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setGrossArea(BigDecimal value) {
        this.grossArea = value;
    }

    /**
     * Gets the value of the totalArea property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalArea() {
        return totalArea;
    }

    /**
     * Sets the value of the totalArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalArea(BigDecimal value) {
        this.totalArea = value;
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

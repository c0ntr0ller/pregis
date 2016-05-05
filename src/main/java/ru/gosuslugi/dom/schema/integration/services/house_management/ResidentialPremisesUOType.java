
package ru.gosuslugi.dom.schema.integration.services.house_management;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration.base.NsiRef;


/**
 * Жилое помещение (для импорта от УО)
 * 
 * <p>Java class for ResidentialPremisesUOType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResidentialPremisesUOType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}PremisesBasicUOType">
 *       &lt;sequence>
 *         &lt;element name="EntranceNum">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
 *               &lt;maxInclusive value="99"/>
 *               &lt;minInclusive value="0"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PremisesCharacteristic" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}nsiRef"/>
 *         &lt;element name="TotalArea" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}PremisesAreaType"/>
 *         &lt;element name="GrossArea" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}PremisesAreaType"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResidentialPremisesUOType", propOrder = {
    "entranceNum",
    "premisesCharacteristic",
    "totalArea",
    "grossArea"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration.services.house_management.ImportHouseUORequest.ApartmentHouse.ResidentialPremises.ResidentialPremisesToCreate.class
})
public class ResidentialPremisesUOType
    extends PremisesBasicUOType
{

    @XmlElement(name = "EntranceNum")
    protected byte entranceNum;
    @XmlElement(name = "PremisesCharacteristic", required = true)
    protected NsiRef premisesCharacteristic;
    @XmlElement(name = "TotalArea", required = true)
    protected BigDecimal totalArea;
    @XmlElement(name = "GrossArea", required = true)
    protected BigDecimal grossArea;

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

}


package ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_6_0.NsiRef;


/**
 * Нежилое помещение (обновление данных для ОМС)
 * 
 * <p>Java class for NonResidentialPremisesUpdateOMSType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NonResidentialPremisesUpdateOMSType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}PremisesBasicUpdateOMSType">
 *       &lt;sequence>
 *         &lt;element name="Purpose" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}nsiRef" minOccurs="0"/>
 *         &lt;element name="Position" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}nsiRef" minOccurs="0"/>
 *         &lt;element name="TotalArea" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}PremisesAreaType" minOccurs="0"/>
 *         &lt;element name="IsCommonProperty" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NonResidentialPremisesUpdateOMSType", propOrder = {
    "purpose",
    "position",
    "totalArea",
    "isCommonProperty"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ImportHouseOMSRequest.ApartmentHouse.NonResidentialPremiseToUpdate.class
})
public class NonResidentialPremisesUpdateOMSType
    extends PremisesBasicUpdateOMSType
{

    @XmlElement(name = "Purpose")
    protected NsiRef purpose;
    @XmlElement(name = "Position")
    protected NsiRef position;
    @XmlElement(name = "TotalArea")
    protected BigDecimal totalArea;
    @XmlElement(name = "IsCommonProperty")
    protected Boolean isCommonProperty;

    /**
     * Gets the value of the purpose property.
     * 
     * @return
     *     possible object is
     *     {@link NsiRef }
     *     
     */
    public NsiRef getPurpose() {
        return purpose;
    }

    /**
     * Sets the value of the purpose property.
     * 
     * @param value
     *     allowed object is
     *     {@link NsiRef }
     *     
     */
    public void setPurpose(NsiRef value) {
        this.purpose = value;
    }

    /**
     * Gets the value of the position property.
     * 
     * @return
     *     possible object is
     *     {@link NsiRef }
     *     
     */
    public NsiRef getPosition() {
        return position;
    }

    /**
     * Sets the value of the position property.
     * 
     * @param value
     *     allowed object is
     *     {@link NsiRef }
     *     
     */
    public void setPosition(NsiRef value) {
        this.position = value;
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
     * Gets the value of the isCommonProperty property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsCommonProperty() {
        return isCommonProperty;
    }

    /**
     * Sets the value of the isCommonProperty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsCommonProperty(Boolean value) {
        this.isCommonProperty = value;
    }

}

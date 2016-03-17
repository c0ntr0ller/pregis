
package ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Нежилое помещение (для импорта от РСО)
 * 
 * <p>Java class for NonResidentialPremisesRSOType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NonResidentialPremisesRSOType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/house-management/}PremisesBasicRSOType">
 *       &lt;sequence>
 *         &lt;element name="TotalArea" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}PremisesAreaType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NonResidentialPremisesRSOType", propOrder = {
    "totalArea"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ImportHouseRSORequest.ApartmentHouse.NonResidentialPremiseToCreate.class
})
public class NonResidentialPremisesRSOType
    extends PremisesBasicRSOType
{

    @XmlElement(name = "TotalArea")
    protected BigDecimal totalArea;

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

}

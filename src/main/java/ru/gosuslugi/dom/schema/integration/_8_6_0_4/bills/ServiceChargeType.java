
package ru.gosuslugi.dom.schema.integration._8_6_0_4.bills;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Начисление по услуге
 * 
 * <p>Java class for ServiceChargeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceChargeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MoneyRecalculation" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}MoneyType" minOccurs="0"/>
 *         &lt;element name="MoneyDiscount" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}MoneyPositiveType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceChargeType", propOrder = {
    "moneyRecalculation",
    "moneyDiscount"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_6_0_4.bills.PDServiceChargeType.AdditionalServiceExt.class
})
public class ServiceChargeType {

    @XmlElement(name = "MoneyRecalculation")
    protected BigDecimal moneyRecalculation;
    @XmlElement(name = "MoneyDiscount")
    protected BigDecimal moneyDiscount;

    /**
     * Gets the value of the moneyRecalculation property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMoneyRecalculation() {
        return moneyRecalculation;
    }

    /**
     * Sets the value of the moneyRecalculation property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMoneyRecalculation(BigDecimal value) {
        this.moneyRecalculation = value;
    }

    /**
     * Gets the value of the moneyDiscount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMoneyDiscount() {
        return moneyDiscount;
    }

    /**
     * Sets the value of the moneyDiscount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMoneyDiscount(BigDecimal value) {
        this.moneyDiscount = value;
    }

}

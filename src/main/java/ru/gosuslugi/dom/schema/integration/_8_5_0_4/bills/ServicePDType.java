
package ru.gosuslugi.dom.schema.integration._8_5_0_4.bills;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_5_0.NsiRef;


/**
 * Потребление по услуге
 * 
 * <p>Java class for ServicePDType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServicePDType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ServiceType" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}nsiRef"/>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}OKEI" minOccurs="0"/>
 *         &lt;element name="Rate" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}SmallMoneyPositiveType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServicePDType", propOrder = {
    "serviceType",
    "okei",
    "rate"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_5_0_4.bills.PDServiceChargeType.HousingService.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_4.bills.PDServiceChargeType.AdditionalService.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_4.bills.PDServiceChargeType.AdditionalServiceExt.TechService.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_4.bills.PDServiceChargeType.MunicipalService.class
})
public class ServicePDType {

    @XmlElement(name = "ServiceType", required = true)
    protected NsiRef serviceType;
    @XmlElement(name = "OKEI", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.4/")
    protected String okei;
    @XmlElement(name = "Rate", required = true)
    protected BigDecimal rate;

    /**
     * Gets the value of the serviceType property.
     * 
     * @return
     *     possible object is
     *     {@link NsiRef }
     *     
     */
    public NsiRef getServiceType() {
        return serviceType;
    }

    /**
     * Sets the value of the serviceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link NsiRef }
     *     
     */
    public void setServiceType(NsiRef value) {
        this.serviceType = value;
    }

    /**
     * Gets the value of the okei property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOKEI() {
        return okei;
    }

    /**
     * Sets the value of the okei property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOKEI(String value) {
        this.okei = value;
    }

    /**
     * Gets the value of the rate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * Sets the value of the rate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRate(BigDecimal value) {
        this.rate = value;
    }

}

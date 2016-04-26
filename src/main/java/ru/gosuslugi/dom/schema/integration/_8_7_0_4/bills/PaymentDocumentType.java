
package ru.gosuslugi.dom.schema.integration._8_7_0_4.bills;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_7_0.PaymentInformationType;


/**
 * Платежный документ (ПД)
 * 
 * <p>Java class for PaymentDocumentType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentDocumentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}AccountGuid"/>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}PaymentDocumentNumber" minOccurs="0"/>
 *         &lt;element name="PaymentInformation" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}PaymentInformationType"/>
 *         &lt;element name="AddressInfo">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="LivingPersonsNumber" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
 *                         &lt;maxInclusive value="99"/>
 *                         &lt;minInclusive value="0"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="ResidentialSquare" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}PremisesAreaType" minOccurs="0"/>
 *                   &lt;element name="HeatedArea" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}PremisesAreaType" minOccurs="0"/>
 *                   &lt;element name="TotalSquare" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}PremisesAreaType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ChargeInfo" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/bills/}PDServiceChargeType">
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;choice>
 *           &lt;element name="Expose" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *           &lt;element name="Withdraw" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;/choice>
 *         &lt;element name="DebtPreviousPeriods" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;minInclusive value="-99999999.99"/>
 *               &lt;maxInclusive value="99999999.99"/>
 *               &lt;fractionDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="AdvanceBllingPeriod" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;minInclusive value="-99999999.99"/>
 *               &lt;maxInclusive value="99999999.99"/>
 *               &lt;fractionDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="totalPiecemealPaymentSum" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;minInclusive value="0"/>
 *               &lt;maxInclusive value="9999999999.99"/>
 *               &lt;fractionDigits value="2"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentDocumentType", propOrder = {
    "accountGuid",
    "paymentDocumentNumber",
    "paymentInformation",
    "addressInfo",
    "chargeInfo",
    "expose",
    "withdraw",
    "debtPreviousPeriods",
    "advanceBllingPeriod",
    "totalPiecemealPaymentSum"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_7_0_4.bills.ExportPaymentDocumentResultType.PaymentDocument.class,
    ru.gosuslugi.dom.schema.integration._8_7_0_4.bills.ImportPaymentDocumentRequest.PaymentDocument.class
})
public class PaymentDocumentType {

    @XmlElement(name = "AccountGuid", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", required = true)
    protected String accountGuid;
    @XmlElement(name = "PaymentDocumentNumber", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/")
    protected String paymentDocumentNumber;
    @XmlElement(name = "PaymentInformation", required = true)
    protected PaymentInformationType paymentInformation;
    @XmlElement(name = "AddressInfo", required = true)
    protected PaymentDocumentType.AddressInfo addressInfo;
    @XmlElement(name = "ChargeInfo", required = true)
    protected List<PaymentDocumentType.ChargeInfo> chargeInfo;
    @XmlElement(name = "Expose")
    protected Boolean expose;
    @XmlElement(name = "Withdraw")
    protected Boolean withdraw;
    @XmlElement(name = "DebtPreviousPeriods")
    protected BigDecimal debtPreviousPeriods;
    @XmlElement(name = "AdvanceBllingPeriod")
    protected BigDecimal advanceBllingPeriod;
    protected BigDecimal totalPiecemealPaymentSum;

    /**
     * Gets the value of the accountGuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountGuid() {
        return accountGuid;
    }

    /**
     * Sets the value of the accountGuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountGuid(String value) {
        this.accountGuid = value;
    }

    /**
     * Gets the value of the paymentDocumentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentDocumentNumber() {
        return paymentDocumentNumber;
    }

    /**
     * Sets the value of the paymentDocumentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentDocumentNumber(String value) {
        this.paymentDocumentNumber = value;
    }

    /**
     * Gets the value of the paymentInformation property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentInformationType }
     *     
     */
    public PaymentInformationType getPaymentInformation() {
        return paymentInformation;
    }

    /**
     * Sets the value of the paymentInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentInformationType }
     *     
     */
    public void setPaymentInformation(PaymentInformationType value) {
        this.paymentInformation = value;
    }

    /**
     * Gets the value of the addressInfo property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentDocumentType.AddressInfo }
     *     
     */
    public PaymentDocumentType.AddressInfo getAddressInfo() {
        return addressInfo;
    }

    /**
     * Sets the value of the addressInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentDocumentType.AddressInfo }
     *     
     */
    public void setAddressInfo(PaymentDocumentType.AddressInfo value) {
        this.addressInfo = value;
    }

    /**
     * Gets the value of the chargeInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the chargeInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChargeInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PaymentDocumentType.ChargeInfo }
     * 
     * 
     */
    public List<PaymentDocumentType.ChargeInfo> getChargeInfo() {
        if (chargeInfo == null) {
            chargeInfo = new ArrayList<PaymentDocumentType.ChargeInfo>();
        }
        return this.chargeInfo;
    }

    /**
     * Gets the value of the expose property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isExpose() {
        return expose;
    }

    /**
     * Sets the value of the expose property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setExpose(Boolean value) {
        this.expose = value;
    }

    /**
     * Gets the value of the withdraw property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isWithdraw() {
        return withdraw;
    }

    /**
     * Sets the value of the withdraw property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setWithdraw(Boolean value) {
        this.withdraw = value;
    }

    /**
     * Gets the value of the debtPreviousPeriods property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDebtPreviousPeriods() {
        return debtPreviousPeriods;
    }

    /**
     * Sets the value of the debtPreviousPeriods property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDebtPreviousPeriods(BigDecimal value) {
        this.debtPreviousPeriods = value;
    }

    /**
     * Gets the value of the advanceBllingPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAdvanceBllingPeriod() {
        return advanceBllingPeriod;
    }

    /**
     * Sets the value of the advanceBllingPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAdvanceBllingPeriod(BigDecimal value) {
        this.advanceBllingPeriod = value;
    }

    /**
     * Gets the value of the totalPiecemealPaymentSum property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTotalPiecemealPaymentSum() {
        return totalPiecemealPaymentSum;
    }

    /**
     * Sets the value of the totalPiecemealPaymentSum property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTotalPiecemealPaymentSum(BigDecimal value) {
        this.totalPiecemealPaymentSum = value;
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
     *         &lt;element name="LivingPersonsNumber" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
     *               &lt;maxInclusive value="99"/>
     *               &lt;minInclusive value="0"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="ResidentialSquare" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}PremisesAreaType" minOccurs="0"/>
     *         &lt;element name="HeatedArea" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}PremisesAreaType" minOccurs="0"/>
     *         &lt;element name="TotalSquare" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}PremisesAreaType"/>
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
        "livingPersonsNumber",
        "residentialSquare",
        "heatedArea",
        "totalSquare"
    })
    public static class AddressInfo {

        @XmlElement(name = "LivingPersonsNumber")
        protected Byte livingPersonsNumber;
        @XmlElement(name = "ResidentialSquare")
        protected BigDecimal residentialSquare;
        @XmlElement(name = "HeatedArea")
        protected BigDecimal heatedArea;
        @XmlElement(name = "TotalSquare", required = true)
        protected BigDecimal totalSquare;

        /**
         * Gets the value of the livingPersonsNumber property.
         * 
         * @return
         *     possible object is
         *     {@link Byte }
         *     
         */
        public Byte getLivingPersonsNumber() {
            return livingPersonsNumber;
        }

        /**
         * Sets the value of the livingPersonsNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link Byte }
         *     
         */
        public void setLivingPersonsNumber(Byte value) {
            this.livingPersonsNumber = value;
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
         * Gets the value of the heatedArea property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getHeatedArea() {
            return heatedArea;
        }

        /**
         * Sets the value of the heatedArea property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setHeatedArea(BigDecimal value) {
            this.heatedArea = value;
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

    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/bills/}PDServiceChargeType">
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class ChargeInfo
        extends PDServiceChargeType
    {


    }

}

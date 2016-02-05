
package ru.gosuslugi.dom.schema.integration._8_5_0_2.payment;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_5_0.PaymentInformationType;


/**
 * Реквизиты и свойства платежного документа
 * 
 * <p>Java class for PaymentDocumentDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentDocumentDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PaymentDocumentGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}GUIDType"/>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}PaymentDocumentNumber"/>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}AccountNumber"/>
 *         &lt;element name="PaymentInformation" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}PaymentInformationType"/>
 *         &lt;choice>
 *           &lt;element name="Expose" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *           &lt;element name="Withdraw" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;/choice>
 *         &lt;element name="Reminder" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}MoneyPositiveType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentDocumentDetailsType", propOrder = {
    "paymentDocumentGuid",
    "paymentDocumentNumber",
    "accountNumber",
    "paymentInformation",
    "expose",
    "withdraw",
    "reminder"
})
public class PaymentDocumentDetailsType {

    @XmlElement(name = "PaymentDocumentGuid", required = true)
    protected String paymentDocumentGuid;
    @XmlElement(name = "PaymentDocumentNumber", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", required = true)
    protected String paymentDocumentNumber;
    @XmlElement(name = "AccountNumber", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", required = true)
    protected String accountNumber;
    @XmlElement(name = "PaymentInformation", required = true)
    protected PaymentInformationType paymentInformation;
    @XmlElement(name = "Expose")
    protected Boolean expose;
    @XmlElement(name = "Withdraw")
    protected Boolean withdraw;
    @XmlElement(name = "Reminder", required = true)
    protected BigDecimal reminder;

    /**
     * Gets the value of the paymentDocumentGuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentDocumentGuid() {
        return paymentDocumentGuid;
    }

    /**
     * Sets the value of the paymentDocumentGuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentDocumentGuid(String value) {
        this.paymentDocumentGuid = value;
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
     * Gets the value of the accountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Sets the value of the accountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountNumber(String value) {
        this.accountNumber = value;
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
     * Gets the value of the reminder property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getReminder() {
        return reminder;
    }

    /**
     * Sets the value of the reminder property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setReminder(BigDecimal value) {
        this.reminder = value;
    }

}

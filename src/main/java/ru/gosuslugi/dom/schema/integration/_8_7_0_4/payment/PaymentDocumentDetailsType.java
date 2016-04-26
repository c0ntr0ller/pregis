
package ru.gosuslugi.dom.schema.integration._8_7_0_4.payment;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_7_0.ErrorMessageType;
import ru.gosuslugi.dom.schema.integration._8_7_0.FIOType;
import ru.gosuslugi.dom.schema.integration._8_7_0.PaymentInformationType;


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
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}PaymentDocumentID"/>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}AccountNumber"/>
 *         &lt;element name="ExecutorInformation">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}INN"/>
 *                   &lt;choice>
 *                     &lt;element name="Entpr" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}FIOType"/>
 *                     &lt;element name="Legal">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}KPP"/>
 *                               &lt;element name="Name">
 *                                 &lt;simpleType>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                     &lt;maxLength value="160"/>
 *                                     &lt;minLength value="1"/>
 *                                   &lt;/restriction>
 *                                 &lt;/simpleType>
 *                               &lt;/element>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                   &lt;/choice>
 *                   &lt;element name="PaymentInformation">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}PaymentInformationType">
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="MailingAddress">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="1000"/>
 *                         &lt;minLength value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;choice>
 *           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}ErrorMessage"/>
 *           &lt;sequence>
 *             &lt;element name="Reminder" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MoneyPositiveType" minOccurs="0"/>
 *             &lt;element name="Debt" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MoneyPositiveType" minOccurs="0"/>
 *           &lt;/sequence>
 *         &lt;/choice>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/payment/}Purpose" minOccurs="0"/>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/payment/}PDServiceName" minOccurs="0"/>
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
    "paymentDocumentID",
    "accountNumber",
    "executorInformation",
    "errorMessage",
    "reminder",
    "debt",
    "purpose",
    "pdServiceName"
})
public class PaymentDocumentDetailsType {

    @XmlElement(name = "PaymentDocumentID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", required = true)
    protected String paymentDocumentID;
    @XmlElement(name = "AccountNumber", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", required = true)
    protected String accountNumber;
    @XmlElement(name = "ExecutorInformation", required = true)
    protected PaymentDocumentDetailsType.ExecutorInformation executorInformation;
    @XmlElement(name = "ErrorMessage", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/")
    protected ErrorMessageType errorMessage;
    @XmlElement(name = "Reminder")
    protected BigDecimal reminder;
    @XmlElement(name = "Debt")
    protected BigDecimal debt;
    @XmlElement(name = "Purpose")
    protected String purpose;
    @XmlElement(name = "PDServiceName")
    protected String pdServiceName;

    /**
     * Gets the value of the paymentDocumentID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentDocumentID() {
        return paymentDocumentID;
    }

    /**
     * Sets the value of the paymentDocumentID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentDocumentID(String value) {
        this.paymentDocumentID = value;
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
     * Gets the value of the executorInformation property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentDocumentDetailsType.ExecutorInformation }
     *     
     */
    public PaymentDocumentDetailsType.ExecutorInformation getExecutorInformation() {
        return executorInformation;
    }

    /**
     * Sets the value of the executorInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentDocumentDetailsType.ExecutorInformation }
     *     
     */
    public void setExecutorInformation(PaymentDocumentDetailsType.ExecutorInformation value) {
        this.executorInformation = value;
    }

    /**
     * Gets the value of the errorMessage property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorMessageType }
     *     
     */
    public ErrorMessageType getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the value of the errorMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorMessageType }
     *     
     */
    public void setErrorMessage(ErrorMessageType value) {
        this.errorMessage = value;
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

    /**
     * Gets the value of the debt property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDebt() {
        return debt;
    }

    /**
     * Sets the value of the debt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDebt(BigDecimal value) {
        this.debt = value;
    }

    /**
     * Gets the value of the purpose property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * Sets the value of the purpose property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPurpose(String value) {
        this.purpose = value;
    }

    /**
     * Gets the value of the pdServiceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPDServiceName() {
        return pdServiceName;
    }

    /**
     * Sets the value of the pdServiceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPDServiceName(String value) {
        this.pdServiceName = value;
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
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}INN"/>
     *         &lt;choice>
     *           &lt;element name="Entpr" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}FIOType"/>
     *           &lt;element name="Legal">
     *             &lt;complexType>
     *               &lt;complexContent>
     *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                   &lt;sequence>
     *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}KPP"/>
     *                     &lt;element name="Name">
     *                       &lt;simpleType>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                           &lt;maxLength value="160"/>
     *                           &lt;minLength value="1"/>
     *                         &lt;/restriction>
     *                       &lt;/simpleType>
     *                     &lt;/element>
     *                   &lt;/sequence>
     *                 &lt;/restriction>
     *               &lt;/complexContent>
     *             &lt;/complexType>
     *           &lt;/element>
     *         &lt;/choice>
     *         &lt;element name="PaymentInformation">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}PaymentInformationType">
     *               &lt;/extension>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="MailingAddress">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="1000"/>
     *               &lt;minLength value="1"/>
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
    @XmlType(name = "", propOrder = {
        "inn",
        "entpr",
        "legal",
        "paymentInformation",
        "mailingAddress"
    })
    public static class ExecutorInformation {

        @XmlElement(name = "INN", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", required = true)
        protected String inn;
        @XmlElement(name = "Entpr")
        protected FIOType entpr;
        @XmlElement(name = "Legal")
        protected PaymentDocumentDetailsType.ExecutorInformation.Legal legal;
        @XmlElement(name = "PaymentInformation", required = true)
        protected PaymentDocumentDetailsType.ExecutorInformation.PaymentInformation paymentInformation;
        @XmlElement(name = "MailingAddress", required = true)
        protected String mailingAddress;

        /**
         * Gets the value of the inn property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getINN() {
            return inn;
        }

        /**
         * Sets the value of the inn property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setINN(String value) {
            this.inn = value;
        }

        /**
         * Gets the value of the entpr property.
         * 
         * @return
         *     possible object is
         *     {@link FIOType }
         *     
         */
        public FIOType getEntpr() {
            return entpr;
        }

        /**
         * Sets the value of the entpr property.
         * 
         * @param value
         *     allowed object is
         *     {@link FIOType }
         *     
         */
        public void setEntpr(FIOType value) {
            this.entpr = value;
        }

        /**
         * Gets the value of the legal property.
         * 
         * @return
         *     possible object is
         *     {@link PaymentDocumentDetailsType.ExecutorInformation.Legal }
         *     
         */
        public PaymentDocumentDetailsType.ExecutorInformation.Legal getLegal() {
            return legal;
        }

        /**
         * Sets the value of the legal property.
         * 
         * @param value
         *     allowed object is
         *     {@link PaymentDocumentDetailsType.ExecutorInformation.Legal }
         *     
         */
        public void setLegal(PaymentDocumentDetailsType.ExecutorInformation.Legal value) {
            this.legal = value;
        }

        /**
         * Gets the value of the paymentInformation property.
         * 
         * @return
         *     possible object is
         *     {@link PaymentDocumentDetailsType.ExecutorInformation.PaymentInformation }
         *     
         */
        public PaymentDocumentDetailsType.ExecutorInformation.PaymentInformation getPaymentInformation() {
            return paymentInformation;
        }

        /**
         * Sets the value of the paymentInformation property.
         * 
         * @param value
         *     allowed object is
         *     {@link PaymentDocumentDetailsType.ExecutorInformation.PaymentInformation }
         *     
         */
        public void setPaymentInformation(PaymentDocumentDetailsType.ExecutorInformation.PaymentInformation value) {
            this.paymentInformation = value;
        }

        /**
         * Gets the value of the mailingAddress property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMailingAddress() {
            return mailingAddress;
        }

        /**
         * Sets the value of the mailingAddress property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMailingAddress(String value) {
            this.mailingAddress = value;
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
         *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}KPP"/>
         *         &lt;element name="Name">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;maxLength value="160"/>
         *               &lt;minLength value="1"/>
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
        @XmlType(name = "", propOrder = {
            "kpp",
            "name"
        })
        public static class Legal {

            @XmlElement(name = "KPP", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", required = true)
            protected String kpp;
            @XmlElement(name = "Name", required = true)
            protected String name;

            /**
             * Gets the value of the kpp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKPP() {
                return kpp;
            }

            /**
             * Sets the value of the kpp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKPP(String value) {
                this.kpp = value;
            }

            /**
             * Gets the value of the name property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getName() {
                return name;
            }

            /**
             * Sets the value of the name property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setName(String value) {
                this.name = value;
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
         *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}PaymentInformationType">
         *     &lt;/extension>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class PaymentInformation
            extends PaymentInformationType
        {


        }

    }

}

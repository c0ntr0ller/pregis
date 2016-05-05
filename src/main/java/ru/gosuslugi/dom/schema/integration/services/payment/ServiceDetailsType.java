
package ru.gosuslugi.dom.schema.integration.services.payment;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration.base.ErrorMessageType;
import ru.gosuslugi.dom.schema.integration.base.FIOType;
import ru.gosuslugi.dom.schema.integration.base.PaymentInformationType;


/**
 * Сведения о жилищно-коммунальной услуге
 * 
 * <p>Java class for ServiceDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}AccountNumber" minOccurs="0"/>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}ServiceID"/>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}UnifiedAccountNumber" minOccurs="0"/>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}PaymentDocumentNumber" minOccurs="0"/>
 *         &lt;element name="ExecutorInformation">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}INN"/>
 *                   &lt;choice>
 *                     &lt;element name="Entpr" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}FIOType"/>
 *                     &lt;element name="Legal">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}KPP"/>
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
 *                         &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}PaymentInformationType">
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="MailingAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;choice>
 *           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}ErrorMessage"/>
 *           &lt;sequence>
 *             &lt;element name="Reminder" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}MoneyPositiveType" minOccurs="0"/>
 *             &lt;element name="Debt" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}MoneyPositiveType" minOccurs="0"/>
 *           &lt;/sequence>
 *         &lt;/choice>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/payment/}Purpose" minOccurs="0"/>
 *         &lt;element name="ServiceName" minOccurs="0">
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
@XmlType(name = "ServiceDetailsType", propOrder = {
    "accountNumber",
    "serviceID",
    "unifiedAccountNumber",
    "paymentDocumentNumber",
    "executorInformation",
    "errorMessage",
    "reminder",
    "debt",
    "purpose",
    "serviceName"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration.services.payment.ChargeType.Service.class
})
public class ServiceDetailsType {

    @XmlElement(name = "AccountNumber", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/")
    protected String accountNumber;
    @XmlElement(name = "ServiceID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", required = true)
    protected String serviceID;
    @XmlElement(name = "UnifiedAccountNumber", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/")
    protected String unifiedAccountNumber;
    @XmlElement(name = "PaymentDocumentNumber", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/")
    protected String paymentDocumentNumber;
    @XmlElement(name = "ExecutorInformation", required = true)
    protected ServiceDetailsType.ExecutorInformation executorInformation;
    @XmlElement(name = "ErrorMessage", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/")
    protected ErrorMessageType errorMessage;
    @XmlElement(name = "Reminder")
    protected BigDecimal reminder;
    @XmlElement(name = "Debt")
    protected BigDecimal debt;
    @XmlElement(name = "Purpose")
    protected String purpose;
    @XmlElement(name = "ServiceName")
    protected String serviceName;

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
     * Gets the value of the serviceID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceID() {
        return serviceID;
    }

    /**
     * Sets the value of the serviceID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceID(String value) {
        this.serviceID = value;
    }

    /**
     * Gets the value of the unifiedAccountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnifiedAccountNumber() {
        return unifiedAccountNumber;
    }

    /**
     * Sets the value of the unifiedAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnifiedAccountNumber(String value) {
        this.unifiedAccountNumber = value;
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
     * Gets the value of the executorInformation property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceDetailsType.ExecutorInformation }
     *     
     */
    public ServiceDetailsType.ExecutorInformation getExecutorInformation() {
        return executorInformation;
    }

    /**
     * Sets the value of the executorInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceDetailsType.ExecutorInformation }
     *     
     */
    public void setExecutorInformation(ServiceDetailsType.ExecutorInformation value) {
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
     * Gets the value of the serviceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * Sets the value of the serviceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceName(String value) {
        this.serviceName = value;
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
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}INN"/>
     *         &lt;choice>
     *           &lt;element name="Entpr" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}FIOType"/>
     *           &lt;element name="Legal">
     *             &lt;complexType>
     *               &lt;complexContent>
     *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                   &lt;sequence>
     *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}KPP"/>
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
     *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}PaymentInformationType">
     *               &lt;/extension>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="MailingAddress" type="{http://www.w3.org/2001/XMLSchema}string"/>
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

        @XmlElement(name = "INN", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", required = true)
        protected String inn;
        @XmlElement(name = "Entpr")
        protected FIOType entpr;
        @XmlElement(name = "Legal")
        protected ServiceDetailsType.ExecutorInformation.Legal legal;
        @XmlElement(name = "PaymentInformation", required = true)
        protected ServiceDetailsType.ExecutorInformation.PaymentInformation paymentInformation;
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
         *     {@link ServiceDetailsType.ExecutorInformation.Legal }
         *     
         */
        public ServiceDetailsType.ExecutorInformation.Legal getLegal() {
            return legal;
        }

        /**
         * Sets the value of the legal property.
         * 
         * @param value
         *     allowed object is
         *     {@link ServiceDetailsType.ExecutorInformation.Legal }
         *     
         */
        public void setLegal(ServiceDetailsType.ExecutorInformation.Legal value) {
            this.legal = value;
        }

        /**
         * Gets the value of the paymentInformation property.
         * 
         * @return
         *     possible object is
         *     {@link ServiceDetailsType.ExecutorInformation.PaymentInformation }
         *     
         */
        public ServiceDetailsType.ExecutorInformation.PaymentInformation getPaymentInformation() {
            return paymentInformation;
        }

        /**
         * Sets the value of the paymentInformation property.
         * 
         * @param value
         *     allowed object is
         *     {@link ServiceDetailsType.ExecutorInformation.PaymentInformation }
         *     
         */
        public void setPaymentInformation(ServiceDetailsType.ExecutorInformation.PaymentInformation value) {
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
         *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}KPP"/>
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

            @XmlElement(name = "KPP", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", required = true)
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
         *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}PaymentInformationType">
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

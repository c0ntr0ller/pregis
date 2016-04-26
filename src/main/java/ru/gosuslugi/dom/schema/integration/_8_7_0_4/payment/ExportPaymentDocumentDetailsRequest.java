
package ru.gosuslugi.dom.schema.integration._8_7_0_4.payment;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_7_0.BaseType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}BaseType">
 *       &lt;sequence>
 *         &lt;choice>
 *           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}PaymentDocumentID"/>
 *           &lt;sequence>
 *             &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}Year" minOccurs="0"/>
 *             &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}Month" minOccurs="0"/>
 *             &lt;choice>
 *               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}UnifiedAccountNumber"/>
 *               &lt;sequence>
 *                 &lt;element name="FIASHouseGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}FIASHouseGUIDType"/>
 *                 &lt;choice>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}AccountNumber"/>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}PaymentDocumentNumber"/>
 *                 &lt;/choice>
 *               &lt;/sequence>
 *               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}ServiceID"/>
 *             &lt;/choice>
 *           &lt;/sequence>
 *         &lt;/choice>
 *         &lt;element name="AmountRequired" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/payment/}Individual"/>
 *                   &lt;element name="Legal">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}INN"/>
 *                             &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}KPP"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "paymentDocumentID",
    "year",
    "month",
    "unifiedAccountNumber",
    "fiasHouseGuid",
    "accountNumber",
    "paymentDocumentNumber",
    "serviceID",
    "amountRequired"
})
@XmlRootElement(name = "exportPaymentDocumentDetailsRequest")
public class ExportPaymentDocumentDetailsRequest
    extends BaseType
{

    @XmlElement(name = "PaymentDocumentID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/")
    protected String paymentDocumentID;
    @XmlElement(name = "Year", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/")
    protected Short year;
    @XmlElement(name = "Month", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/")
    protected Integer month;
    @XmlElement(name = "UnifiedAccountNumber", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/")
    protected String unifiedAccountNumber;
    @XmlElement(name = "FIASHouseGuid")
    protected String fiasHouseGuid;
    @XmlElement(name = "AccountNumber", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/")
    protected String accountNumber;
    @XmlElement(name = "PaymentDocumentNumber", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/")
    protected String paymentDocumentNumber;
    @XmlElement(name = "ServiceID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/")
    protected String serviceID;
    @XmlElement(name = "AmountRequired")
    protected ExportPaymentDocumentDetailsRequest.AmountRequired amountRequired;

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
     * Gets the value of the year property.
     * 
     * @return
     *     possible object is
     *     {@link Short }
     *     
     */
    public Short getYear() {
        return year;
    }

    /**
     * Sets the value of the year property.
     * 
     * @param value
     *     allowed object is
     *     {@link Short }
     *     
     */
    public void setYear(Short value) {
        this.year = value;
    }

    /**
     * Gets the value of the month property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMonth() {
        return month;
    }

    /**
     * Sets the value of the month property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMonth(Integer value) {
        this.month = value;
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
     * Gets the value of the fiasHouseGuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFIASHouseGuid() {
        return fiasHouseGuid;
    }

    /**
     * Sets the value of the fiasHouseGuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFIASHouseGuid(String value) {
        this.fiasHouseGuid = value;
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
     * Gets the value of the amountRequired property.
     * 
     * @return
     *     possible object is
     *     {@link ExportPaymentDocumentDetailsRequest.AmountRequired }
     *     
     */
    public ExportPaymentDocumentDetailsRequest.AmountRequired getAmountRequired() {
        return amountRequired;
    }

    /**
     * Sets the value of the amountRequired property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExportPaymentDocumentDetailsRequest.AmountRequired }
     *     
     */
    public void setAmountRequired(ExportPaymentDocumentDetailsRequest.AmountRequired value) {
        this.amountRequired = value;
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
     *       &lt;choice>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/payment/}Individual"/>
     *         &lt;element name="Legal">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}INN"/>
     *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}KPP"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/choice>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "individual",
        "legal"
    })
    public static class AmountRequired {

        @XmlElement(name = "Individual")
        protected Individual individual;
        @XmlElement(name = "Legal")
        protected ExportPaymentDocumentDetailsRequest.AmountRequired.Legal legal;

        /**
         * Gets the value of the individual property.
         * 
         * @return
         *     possible object is
         *     {@link Individual }
         *     
         */
        public Individual getIndividual() {
            return individual;
        }

        /**
         * Sets the value of the individual property.
         * 
         * @param value
         *     allowed object is
         *     {@link Individual }
         *     
         */
        public void setIndividual(Individual value) {
            this.individual = value;
        }

        /**
         * Gets the value of the legal property.
         * 
         * @return
         *     possible object is
         *     {@link ExportPaymentDocumentDetailsRequest.AmountRequired.Legal }
         *     
         */
        public ExportPaymentDocumentDetailsRequest.AmountRequired.Legal getLegal() {
            return legal;
        }

        /**
         * Sets the value of the legal property.
         * 
         * @param value
         *     allowed object is
         *     {@link ExportPaymentDocumentDetailsRequest.AmountRequired.Legal }
         *     
         */
        public void setLegal(ExportPaymentDocumentDetailsRequest.AmountRequired.Legal value) {
            this.legal = value;
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
         *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}KPP"/>
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
            "kpp"
        })
        public static class Legal {

            @XmlElement(name = "INN", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", required = true)
            protected String inn;
            @XmlElement(name = "KPP", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", required = true)
            protected String kpp;

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

        }

    }

}

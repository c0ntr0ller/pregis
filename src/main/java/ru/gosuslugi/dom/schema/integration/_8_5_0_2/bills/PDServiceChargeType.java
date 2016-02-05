
package ru.gosuslugi.dom.schema.integration._8_5_0_2.bills;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_5_0.NsiRef;


/**
 * Потребление и начисление по услуге  в ПД
 * 
 * <p>Java class for PDServiceChargeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PDServiceChargeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="HousingService">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/bills/}ServicePDType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/bills/}ServiceCharge"/>
 *                 &lt;/sequence>
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="AdditionalService">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/bills/}ServicePDType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/bills/}ServiceCharge"/>
 *                 &lt;/sequence>
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="AdditionalServiceExt">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/bills/}ServiceChargeType">
 *                 &lt;sequence>
 *                   &lt;element name="ServiceType" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}nsiRef"/>
 *                   &lt;element name="TechService" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/bills/}ServicePDType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="MunicipalService">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/bills/}ServicePDType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/bills/}ServiceCharge"/>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/bills/}PiecemealPayment"/>
 *                   &lt;element name="PaymentRecalculation" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="recalculationReason" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="sum">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                                   &lt;fractionDigits value="2"/>
 *                                   &lt;minInclusive value="0"/>
 *                                   &lt;maxInclusive value="9999999999.99"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/extension>
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
@XmlType(name = "PDServiceChargeType", propOrder = {
    "housingService",
    "additionalService",
    "additionalServiceExt",
    "municipalService"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_5_0_2.bills.PaymentDocumentType.ChargeInfo.class
})
public class PDServiceChargeType {

    @XmlElement(name = "HousingService")
    protected PDServiceChargeType.HousingService housingService;
    @XmlElement(name = "AdditionalService")
    protected PDServiceChargeType.AdditionalService additionalService;
    @XmlElement(name = "AdditionalServiceExt")
    protected PDServiceChargeType.AdditionalServiceExt additionalServiceExt;
    @XmlElement(name = "MunicipalService")
    protected PDServiceChargeType.MunicipalService municipalService;

    /**
     * Gets the value of the housingService property.
     * 
     * @return
     *     possible object is
     *     {@link PDServiceChargeType.HousingService }
     *     
     */
    public PDServiceChargeType.HousingService getHousingService() {
        return housingService;
    }

    /**
     * Sets the value of the housingService property.
     * 
     * @param value
     *     allowed object is
     *     {@link PDServiceChargeType.HousingService }
     *     
     */
    public void setHousingService(PDServiceChargeType.HousingService value) {
        this.housingService = value;
    }

    /**
     * Gets the value of the additionalService property.
     * 
     * @return
     *     possible object is
     *     {@link PDServiceChargeType.AdditionalService }
     *     
     */
    public PDServiceChargeType.AdditionalService getAdditionalService() {
        return additionalService;
    }

    /**
     * Sets the value of the additionalService property.
     * 
     * @param value
     *     allowed object is
     *     {@link PDServiceChargeType.AdditionalService }
     *     
     */
    public void setAdditionalService(PDServiceChargeType.AdditionalService value) {
        this.additionalService = value;
    }

    /**
     * Gets the value of the additionalServiceExt property.
     * 
     * @return
     *     possible object is
     *     {@link PDServiceChargeType.AdditionalServiceExt }
     *     
     */
    public PDServiceChargeType.AdditionalServiceExt getAdditionalServiceExt() {
        return additionalServiceExt;
    }

    /**
     * Sets the value of the additionalServiceExt property.
     * 
     * @param value
     *     allowed object is
     *     {@link PDServiceChargeType.AdditionalServiceExt }
     *     
     */
    public void setAdditionalServiceExt(PDServiceChargeType.AdditionalServiceExt value) {
        this.additionalServiceExt = value;
    }

    /**
     * Gets the value of the municipalService property.
     * 
     * @return
     *     possible object is
     *     {@link PDServiceChargeType.MunicipalService }
     *     
     */
    public PDServiceChargeType.MunicipalService getMunicipalService() {
        return municipalService;
    }

    /**
     * Sets the value of the municipalService property.
     * 
     * @param value
     *     allowed object is
     *     {@link PDServiceChargeType.MunicipalService }
     *     
     */
    public void setMunicipalService(PDServiceChargeType.MunicipalService value) {
        this.municipalService = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/bills/}ServicePDType">
     *       &lt;sequence>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/bills/}ServiceCharge"/>
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
        "serviceCharge"
    })
    public static class AdditionalService
        extends ServicePDType
    {

        @XmlElement(name = "ServiceCharge", required = true)
        protected ServiceChargeType serviceCharge;

        /**
         * Gets the value of the serviceCharge property.
         * 
         * @return
         *     possible object is
         *     {@link ServiceChargeType }
         *     
         */
        public ServiceChargeType getServiceCharge() {
            return serviceCharge;
        }

        /**
         * Sets the value of the serviceCharge property.
         * 
         * @param value
         *     allowed object is
         *     {@link ServiceChargeType }
         *     
         */
        public void setServiceCharge(ServiceChargeType value) {
            this.serviceCharge = value;
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
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/bills/}ServiceChargeType">
     *       &lt;sequence>
     *         &lt;element name="ServiceType" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}nsiRef"/>
     *         &lt;element name="TechService" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/bills/}ServicePDType" maxOccurs="unbounded"/>
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
        "serviceType",
        "techService"
    })
    public static class AdditionalServiceExt
        extends ServiceChargeType
    {

        @XmlElement(name = "ServiceType", required = true)
        protected NsiRef serviceType;
        @XmlElement(name = "TechService", required = true)
        protected List<ServicePDType> techService;

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
         * Gets the value of the techService property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the techService property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTechService().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ServicePDType }
         * 
         * 
         */
        public List<ServicePDType> getTechService() {
            if (techService == null) {
                techService = new ArrayList<ServicePDType>();
            }
            return this.techService;
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
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/bills/}ServicePDType">
     *       &lt;sequence>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/bills/}ServiceCharge"/>
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
        "serviceCharge"
    })
    public static class HousingService
        extends ServicePDType
    {

        @XmlElement(name = "ServiceCharge", required = true)
        protected ServiceChargeType serviceCharge;

        /**
         * Gets the value of the serviceCharge property.
         * 
         * @return
         *     possible object is
         *     {@link ServiceChargeType }
         *     
         */
        public ServiceChargeType getServiceCharge() {
            return serviceCharge;
        }

        /**
         * Sets the value of the serviceCharge property.
         * 
         * @param value
         *     allowed object is
         *     {@link ServiceChargeType }
         *     
         */
        public void setServiceCharge(ServiceChargeType value) {
            this.serviceCharge = value;
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
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/bills/}ServicePDType">
     *       &lt;sequence>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/bills/}ServiceCharge"/>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/bills/}PiecemealPayment"/>
     *         &lt;element name="PaymentRecalculation" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="recalculationReason" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="sum">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *                         &lt;fractionDigits value="2"/>
     *                         &lt;minInclusive value="0"/>
     *                         &lt;maxInclusive value="9999999999.99"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                 &lt;/sequence>
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
        "serviceCharge",
        "piecemealPayment",
        "paymentRecalculation"
    })
    public static class MunicipalService
        extends ServicePDType
    {

        @XmlElement(name = "ServiceCharge", required = true)
        protected ServiceChargeType serviceCharge;
        @XmlElement(name = "PiecemealPayment", required = true)
        protected PiecemealPayment piecemealPayment;
        @XmlElement(name = "PaymentRecalculation")
        protected PDServiceChargeType.MunicipalService.PaymentRecalculation paymentRecalculation;

        /**
         * Перерасчеты и субсидии
         * 
         * @return
         *     possible object is
         *     {@link ServiceChargeType }
         *     
         */
        public ServiceChargeType getServiceCharge() {
            return serviceCharge;
        }

        /**
         * Sets the value of the serviceCharge property.
         * 
         * @param value
         *     allowed object is
         *     {@link ServiceChargeType }
         *     
         */
        public void setServiceCharge(ServiceChargeType value) {
            this.serviceCharge = value;
        }

        /**
         * Рассрочка
         * 
         * @return
         *     possible object is
         *     {@link PiecemealPayment }
         *     
         */
        public PiecemealPayment getPiecemealPayment() {
            return piecemealPayment;
        }

        /**
         * Sets the value of the piecemealPayment property.
         * 
         * @param value
         *     allowed object is
         *     {@link PiecemealPayment }
         *     
         */
        public void setPiecemealPayment(PiecemealPayment value) {
            this.piecemealPayment = value;
        }

        /**
         * Gets the value of the paymentRecalculation property.
         * 
         * @return
         *     possible object is
         *     {@link PDServiceChargeType.MunicipalService.PaymentRecalculation }
         *     
         */
        public PDServiceChargeType.MunicipalService.PaymentRecalculation getPaymentRecalculation() {
            return paymentRecalculation;
        }

        /**
         * Sets the value of the paymentRecalculation property.
         * 
         * @param value
         *     allowed object is
         *     {@link PDServiceChargeType.MunicipalService.PaymentRecalculation }
         *     
         */
        public void setPaymentRecalculation(PDServiceChargeType.MunicipalService.PaymentRecalculation value) {
            this.paymentRecalculation = value;
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
         *         &lt;element name="recalculationReason" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="sum">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
         *               &lt;fractionDigits value="2"/>
         *               &lt;minInclusive value="0"/>
         *               &lt;maxInclusive value="9999999999.99"/>
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
            "recalculationReason",
            "sum"
        })
        public static class PaymentRecalculation {

            @XmlElement(required = true)
            protected String recalculationReason;
            @XmlElement(required = true)
            protected BigDecimal sum;

            /**
             * Gets the value of the recalculationReason property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRecalculationReason() {
                return recalculationReason;
            }

            /**
             * Sets the value of the recalculationReason property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRecalculationReason(String value) {
                this.recalculationReason = value;
            }

            /**
             * Gets the value of the sum property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getSum() {
                return sum;
            }

            /**
             * Sets the value of the sum property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setSum(BigDecimal value) {
                this.sum = value;
            }

        }

    }

}

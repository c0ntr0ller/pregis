
package ru.gosuslugi.dom.schema.integration.services.house_management;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import ru.gosuslugi.dom.schema.integration.base.NsiRef;


/**
 * Предмет договора
 * 
 * <p>Java class for ContractSubjectType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContractSubjectType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ServiceType">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}nsiRef">
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="MunicipalResource">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}nsiRef">
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="HeatingSystemType" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}HeatingSystemType" minOccurs="0"/>
 *         &lt;element name="ConnectionScheme" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/house-management/}ConnectionSchemeType" minOccurs="0"/>
 *         &lt;element name="StartSupplyDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="EndSupplyDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="Quality" maxOccurs="100" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="QualityIndicator" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}nsiRef"/>
 *                   &lt;element name="IndicatorValue">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;choice>
 *                               &lt;element name="Number" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}VolumeType"/>
 *                               &lt;sequence>
 *                                 &lt;element name="StartRange" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}VolumeType"/>
 *                                 &lt;element name="EndRange" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}VolumeType"/>
 *                               &lt;/sequence>
 *                             &lt;/choice>
 *                             &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}OKEI"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="OtherQualityIndicator" maxOccurs="100" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="IndicatorName" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}String250Type"/>
 *                   &lt;element name="Number" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}VolumeType"/>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}OKEI"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
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
@XmlType(name = "ContractSubjectType", propOrder = {
    "serviceType",
    "municipalResource",
    "heatingSystemType",
    "connectionScheme",
    "startSupplyDate",
    "endSupplyDate",
    "quality",
    "otherQualityIndicator"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration.services.house_management.SupplyResourceContractType.IsContract.ContractSubject.class,
    ru.gosuslugi.dom.schema.integration.services.house_management.SupplyResourceContractType.IsNotContract.ContractSubject.class
})
public class ContractSubjectType {

    @XmlElement(name = "ServiceType", required = true)
    protected ContractSubjectType.ServiceType serviceType;
    @XmlElement(name = "MunicipalResource", required = true)
    protected ContractSubjectType.MunicipalResource municipalResource;
    @XmlElement(name = "HeatingSystemType")
    @XmlSchemaType(name = "string")
    protected HeatingSystemType heatingSystemType;
    @XmlElement(name = "ConnectionScheme")
    @XmlSchemaType(name = "string")
    protected ConnectionSchemeType connectionScheme;
    @XmlElement(name = "StartSupplyDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startSupplyDate;
    @XmlElement(name = "EndSupplyDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar endSupplyDate;
    @XmlElement(name = "Quality")
    protected List<ContractSubjectType.Quality> quality;
    @XmlElement(name = "OtherQualityIndicator")
    protected List<ContractSubjectType.OtherQualityIndicator> otherQualityIndicator;

    /**
     * Gets the value of the serviceType property.
     * 
     * @return
     *     possible object is
     *     {@link ContractSubjectType.ServiceType }
     *     
     */
    public ContractSubjectType.ServiceType getServiceType() {
        return serviceType;
    }

    /**
     * Sets the value of the serviceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractSubjectType.ServiceType }
     *     
     */
    public void setServiceType(ContractSubjectType.ServiceType value) {
        this.serviceType = value;
    }

    /**
     * Gets the value of the municipalResource property.
     * 
     * @return
     *     possible object is
     *     {@link ContractSubjectType.MunicipalResource }
     *     
     */
    public ContractSubjectType.MunicipalResource getMunicipalResource() {
        return municipalResource;
    }

    /**
     * Sets the value of the municipalResource property.
     * 
     * @param value
     *     allowed object is
     *     {@link ContractSubjectType.MunicipalResource }
     *     
     */
    public void setMunicipalResource(ContractSubjectType.MunicipalResource value) {
        this.municipalResource = value;
    }

    /**
     * Gets the value of the heatingSystemType property.
     * 
     * @return
     *     possible object is
     *     {@link HeatingSystemType }
     *     
     */
    public HeatingSystemType getHeatingSystemType() {
        return heatingSystemType;
    }

    /**
     * Sets the value of the heatingSystemType property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeatingSystemType }
     *     
     */
    public void setHeatingSystemType(HeatingSystemType value) {
        this.heatingSystemType = value;
    }

    /**
     * Gets the value of the connectionScheme property.
     * 
     * @return
     *     possible object is
     *     {@link ConnectionSchemeType }
     *     
     */
    public ConnectionSchemeType getConnectionScheme() {
        return connectionScheme;
    }

    /**
     * Sets the value of the connectionScheme property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConnectionSchemeType }
     *     
     */
    public void setConnectionScheme(ConnectionSchemeType value) {
        this.connectionScheme = value;
    }

    /**
     * Gets the value of the startSupplyDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartSupplyDate() {
        return startSupplyDate;
    }

    /**
     * Sets the value of the startSupplyDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartSupplyDate(XMLGregorianCalendar value) {
        this.startSupplyDate = value;
    }

    /**
     * Gets the value of the endSupplyDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndSupplyDate() {
        return endSupplyDate;
    }

    /**
     * Sets the value of the endSupplyDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndSupplyDate(XMLGregorianCalendar value) {
        this.endSupplyDate = value;
    }

    /**
     * Gets the value of the quality property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the quality property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getQuality().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContractSubjectType.Quality }
     * 
     * 
     */
    public List<ContractSubjectType.Quality> getQuality() {
        if (quality == null) {
            quality = new ArrayList<ContractSubjectType.Quality>();
        }
        return this.quality;
    }

    /**
     * Gets the value of the otherQualityIndicator property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the otherQualityIndicator property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOtherQualityIndicator().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContractSubjectType.OtherQualityIndicator }
     * 
     * 
     */
    public List<ContractSubjectType.OtherQualityIndicator> getOtherQualityIndicator() {
        if (otherQualityIndicator == null) {
            otherQualityIndicator = new ArrayList<ContractSubjectType.OtherQualityIndicator>();
        }
        return this.otherQualityIndicator;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}nsiRef">
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class MunicipalResource
        extends NsiRef
    {


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
     *         &lt;element name="IndicatorName" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}String250Type"/>
     *         &lt;element name="Number" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}VolumeType"/>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}OKEI"/>
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
        "indicatorName",
        "number",
        "okei"
    })
    public static class OtherQualityIndicator {

        @XmlElement(name = "IndicatorName", required = true)
        protected String indicatorName;
        @XmlElement(name = "Number", required = true)
        protected BigDecimal number;
        @XmlElement(name = "OKEI", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", required = true)
        protected String okei;

        /**
         * Gets the value of the indicatorName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getIndicatorName() {
            return indicatorName;
        }

        /**
         * Sets the value of the indicatorName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setIndicatorName(String value) {
            this.indicatorName = value;
        }

        /**
         * Gets the value of the number property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getNumber() {
            return number;
        }

        /**
         * Sets the value of the number property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setNumber(BigDecimal value) {
            this.number = value;
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
     *         &lt;element name="QualityIndicator" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}nsiRef"/>
     *         &lt;element name="IndicatorValue">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;choice>
     *                     &lt;element name="Number" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}VolumeType"/>
     *                     &lt;sequence>
     *                       &lt;element name="StartRange" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}VolumeType"/>
     *                       &lt;element name="EndRange" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}VolumeType"/>
     *                     &lt;/sequence>
     *                   &lt;/choice>
     *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}OKEI"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
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
        "qualityIndicator",
        "indicatorValue"
    })
    public static class Quality {

        @XmlElement(name = "QualityIndicator", required = true)
        protected NsiRef qualityIndicator;
        @XmlElement(name = "IndicatorValue", required = true)
        protected ContractSubjectType.Quality.IndicatorValue indicatorValue;

        /**
         * Gets the value of the qualityIndicator property.
         * 
         * @return
         *     possible object is
         *     {@link NsiRef }
         *     
         */
        public NsiRef getQualityIndicator() {
            return qualityIndicator;
        }

        /**
         * Sets the value of the qualityIndicator property.
         * 
         * @param value
         *     allowed object is
         *     {@link NsiRef }
         *     
         */
        public void setQualityIndicator(NsiRef value) {
            this.qualityIndicator = value;
        }

        /**
         * Gets the value of the indicatorValue property.
         * 
         * @return
         *     possible object is
         *     {@link ContractSubjectType.Quality.IndicatorValue }
         *     
         */
        public ContractSubjectType.Quality.IndicatorValue getIndicatorValue() {
            return indicatorValue;
        }

        /**
         * Sets the value of the indicatorValue property.
         * 
         * @param value
         *     allowed object is
         *     {@link ContractSubjectType.Quality.IndicatorValue }
         *     
         */
        public void setIndicatorValue(ContractSubjectType.Quality.IndicatorValue value) {
            this.indicatorValue = value;
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
         *         &lt;choice>
         *           &lt;element name="Number" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}VolumeType"/>
         *           &lt;sequence>
         *             &lt;element name="StartRange" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}VolumeType"/>
         *             &lt;element name="EndRange" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}VolumeType"/>
         *           &lt;/sequence>
         *         &lt;/choice>
         *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}OKEI"/>
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
            "number",
            "startRange",
            "endRange",
            "okei"
        })
        public static class IndicatorValue {

            @XmlElement(name = "Number")
            protected BigDecimal number;
            @XmlElement(name = "StartRange")
            protected BigDecimal startRange;
            @XmlElement(name = "EndRange")
            protected BigDecimal endRange;
            @XmlElement(name = "OKEI", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", required = true)
            protected String okei;

            /**
             * Gets the value of the number property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getNumber() {
                return number;
            }

            /**
             * Sets the value of the number property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setNumber(BigDecimal value) {
                this.number = value;
            }

            /**
             * Gets the value of the startRange property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getStartRange() {
                return startRange;
            }

            /**
             * Sets the value of the startRange property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setStartRange(BigDecimal value) {
                this.startRange = value;
            }

            /**
             * Gets the value of the endRange property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getEndRange() {
                return endRange;
            }

            /**
             * Sets the value of the endRange property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setEndRange(BigDecimal value) {
                this.endRange = value;
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
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}nsiRef">
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class ServiceType
        extends NsiRef
    {


    }

}

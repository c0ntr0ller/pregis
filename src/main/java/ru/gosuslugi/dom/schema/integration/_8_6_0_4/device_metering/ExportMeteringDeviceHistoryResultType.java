
package ru.gosuslugi.dom.schema.integration._8_6_0_4.device_metering;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_6_0.ElectricMeteringValueKindType;
import ru.gosuslugi.dom.schema.integration._8_6_0.ElectricMeteringValueType;
import ru.gosuslugi.dom.schema.integration._8_6_0.NsiRef;
import ru.gosuslugi.dom.schema.integration._8_6_0.OneRateMeteringValueKindType;
import ru.gosuslugi.dom.schema.integration._8_6_0.OneRateMeteringValueType;


/**
 * <p>Java class for exportMeteringDeviceHistoryResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="exportMeteringDeviceHistoryResultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MeteringDeviceRootGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}MeteringDeviceGUIDType"/>
 *         &lt;element name="MunicipalResource" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}GUIDType"/>
 *         &lt;choice>
 *           &lt;element name="OneRateDeviceValue">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="BaseValue" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}OneRateMeteringValueType"/>
 *                     &lt;element name="Values" minOccurs="0">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}OneRateMeteringValueKindType">
 *                             &lt;sequence>
 *                               &lt;element name="excludeISValues" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                             &lt;/sequence>
 *                           &lt;/extension>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="ArchivedValues" minOccurs="0">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="ArchivingReason" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}nsiRef"/>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="ElectricDeviceValue">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="BaseValue" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}ElectricMeteringValueType"/>
 *                     &lt;element name="Values" minOccurs="0">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}ElectricMeteringValueKindType">
 *                             &lt;sequence>
 *                               &lt;element name="excludeISValues" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                             &lt;/sequence>
 *                           &lt;/extension>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="ArchivedValues" minOccurs="0">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="ArchivingReason" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}nsiRef"/>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "exportMeteringDeviceHistoryResultType", propOrder = {
    "meteringDeviceRootGUID",
    "municipalResource",
    "oneRateDeviceValue",
    "electricDeviceValue"
})
public class ExportMeteringDeviceHistoryResultType {

    @XmlElement(name = "MeteringDeviceRootGUID", required = true)
    protected String meteringDeviceRootGUID;
    @XmlElement(name = "MunicipalResource", required = true)
    protected String municipalResource;
    @XmlElement(name = "OneRateDeviceValue")
    protected ExportMeteringDeviceHistoryResultType.OneRateDeviceValue oneRateDeviceValue;
    @XmlElement(name = "ElectricDeviceValue")
    protected ExportMeteringDeviceHistoryResultType.ElectricDeviceValue electricDeviceValue;

    /**
     * Gets the value of the meteringDeviceRootGUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMeteringDeviceRootGUID() {
        return meteringDeviceRootGUID;
    }

    /**
     * Sets the value of the meteringDeviceRootGUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMeteringDeviceRootGUID(String value) {
        this.meteringDeviceRootGUID = value;
    }

    /**
     * Gets the value of the municipalResource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMunicipalResource() {
        return municipalResource;
    }

    /**
     * Sets the value of the municipalResource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMunicipalResource(String value) {
        this.municipalResource = value;
    }

    /**
     * Gets the value of the oneRateDeviceValue property.
     * 
     * @return
     *     possible object is
     *     {@link ExportMeteringDeviceHistoryResultType.OneRateDeviceValue }
     *     
     */
    public ExportMeteringDeviceHistoryResultType.OneRateDeviceValue getOneRateDeviceValue() {
        return oneRateDeviceValue;
    }

    /**
     * Sets the value of the oneRateDeviceValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExportMeteringDeviceHistoryResultType.OneRateDeviceValue }
     *     
     */
    public void setOneRateDeviceValue(ExportMeteringDeviceHistoryResultType.OneRateDeviceValue value) {
        this.oneRateDeviceValue = value;
    }

    /**
     * Gets the value of the electricDeviceValue property.
     * 
     * @return
     *     possible object is
     *     {@link ExportMeteringDeviceHistoryResultType.ElectricDeviceValue }
     *     
     */
    public ExportMeteringDeviceHistoryResultType.ElectricDeviceValue getElectricDeviceValue() {
        return electricDeviceValue;
    }

    /**
     * Sets the value of the electricDeviceValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExportMeteringDeviceHistoryResultType.ElectricDeviceValue }
     *     
     */
    public void setElectricDeviceValue(ExportMeteringDeviceHistoryResultType.ElectricDeviceValue value) {
        this.electricDeviceValue = value;
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
     *         &lt;element name="BaseValue" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}ElectricMeteringValueType"/>
     *         &lt;element name="Values" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}ElectricMeteringValueKindType">
     *                 &lt;sequence>
     *                   &lt;element name="excludeISValues" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/extension>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="ArchivedValues" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ArchivingReason" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}nsiRef"/>
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
        "baseValue",
        "values",
        "archivedValues"
    })
    public static class ElectricDeviceValue {

        @XmlElement(name = "BaseValue", required = true)
        protected ElectricMeteringValueType baseValue;
        @XmlElement(name = "Values")
        protected ExportMeteringDeviceHistoryResultType.ElectricDeviceValue.Values values;
        @XmlElement(name = "ArchivedValues")
        protected ExportMeteringDeviceHistoryResultType.ElectricDeviceValue.ArchivedValues archivedValues;

        /**
         * Gets the value of the baseValue property.
         * 
         * @return
         *     possible object is
         *     {@link ElectricMeteringValueType }
         *     
         */
        public ElectricMeteringValueType getBaseValue() {
            return baseValue;
        }

        /**
         * Sets the value of the baseValue property.
         * 
         * @param value
         *     allowed object is
         *     {@link ElectricMeteringValueType }
         *     
         */
        public void setBaseValue(ElectricMeteringValueType value) {
            this.baseValue = value;
        }

        /**
         * Gets the value of the values property.
         * 
         * @return
         *     possible object is
         *     {@link ExportMeteringDeviceHistoryResultType.ElectricDeviceValue.Values }
         *     
         */
        public ExportMeteringDeviceHistoryResultType.ElectricDeviceValue.Values getValues() {
            return values;
        }

        /**
         * Sets the value of the values property.
         * 
         * @param value
         *     allowed object is
         *     {@link ExportMeteringDeviceHistoryResultType.ElectricDeviceValue.Values }
         *     
         */
        public void setValues(ExportMeteringDeviceHistoryResultType.ElectricDeviceValue.Values value) {
            this.values = value;
        }

        /**
         * Gets the value of the archivedValues property.
         * 
         * @return
         *     possible object is
         *     {@link ExportMeteringDeviceHistoryResultType.ElectricDeviceValue.ArchivedValues }
         *     
         */
        public ExportMeteringDeviceHistoryResultType.ElectricDeviceValue.ArchivedValues getArchivedValues() {
            return archivedValues;
        }

        /**
         * Sets the value of the archivedValues property.
         * 
         * @param value
         *     allowed object is
         *     {@link ExportMeteringDeviceHistoryResultType.ElectricDeviceValue.ArchivedValues }
         *     
         */
        public void setArchivedValues(ExportMeteringDeviceHistoryResultType.ElectricDeviceValue.ArchivedValues value) {
            this.archivedValues = value;
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
         *         &lt;element name="ArchivingReason" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}nsiRef"/>
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
            "archivingReason"
        })
        public static class ArchivedValues {

            @XmlElement(name = "ArchivingReason", required = true)
            protected NsiRef archivingReason;

            /**
             * Gets the value of the archivingReason property.
             * 
             * @return
             *     possible object is
             *     {@link NsiRef }
             *     
             */
            public NsiRef getArchivingReason() {
                return archivingReason;
            }

            /**
             * Sets the value of the archivingReason property.
             * 
             * @param value
             *     allowed object is
             *     {@link NsiRef }
             *     
             */
            public void setArchivingReason(NsiRef value) {
                this.archivingReason = value;
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
         *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}ElectricMeteringValueKindType">
         *       &lt;sequence>
         *         &lt;element name="excludeISValues" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
            "excludeISValues"
        })
        public static class Values
            extends ElectricMeteringValueKindType
        {

            protected Boolean excludeISValues;

            /**
             * Gets the value of the excludeISValues property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isExcludeISValues() {
                return excludeISValues;
            }

            /**
             * Sets the value of the excludeISValues property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setExcludeISValues(Boolean value) {
                this.excludeISValues = value;
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
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="BaseValue" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}OneRateMeteringValueType"/>
     *         &lt;element name="Values" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}OneRateMeteringValueKindType">
     *                 &lt;sequence>
     *                   &lt;element name="excludeISValues" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/extension>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="ArchivedValues" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ArchivingReason" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}nsiRef"/>
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
        "baseValue",
        "values",
        "archivedValues"
    })
    public static class OneRateDeviceValue {

        @XmlElement(name = "BaseValue", required = true)
        protected OneRateMeteringValueType baseValue;
        @XmlElement(name = "Values")
        protected ExportMeteringDeviceHistoryResultType.OneRateDeviceValue.Values values;
        @XmlElement(name = "ArchivedValues")
        protected ExportMeteringDeviceHistoryResultType.OneRateDeviceValue.ArchivedValues archivedValues;

        /**
         * Gets the value of the baseValue property.
         * 
         * @return
         *     possible object is
         *     {@link OneRateMeteringValueType }
         *     
         */
        public OneRateMeteringValueType getBaseValue() {
            return baseValue;
        }

        /**
         * Sets the value of the baseValue property.
         * 
         * @param value
         *     allowed object is
         *     {@link OneRateMeteringValueType }
         *     
         */
        public void setBaseValue(OneRateMeteringValueType value) {
            this.baseValue = value;
        }

        /**
         * Gets the value of the values property.
         * 
         * @return
         *     possible object is
         *     {@link ExportMeteringDeviceHistoryResultType.OneRateDeviceValue.Values }
         *     
         */
        public ExportMeteringDeviceHistoryResultType.OneRateDeviceValue.Values getValues() {
            return values;
        }

        /**
         * Sets the value of the values property.
         * 
         * @param value
         *     allowed object is
         *     {@link ExportMeteringDeviceHistoryResultType.OneRateDeviceValue.Values }
         *     
         */
        public void setValues(ExportMeteringDeviceHistoryResultType.OneRateDeviceValue.Values value) {
            this.values = value;
        }

        /**
         * Gets the value of the archivedValues property.
         * 
         * @return
         *     possible object is
         *     {@link ExportMeteringDeviceHistoryResultType.OneRateDeviceValue.ArchivedValues }
         *     
         */
        public ExportMeteringDeviceHistoryResultType.OneRateDeviceValue.ArchivedValues getArchivedValues() {
            return archivedValues;
        }

        /**
         * Sets the value of the archivedValues property.
         * 
         * @param value
         *     allowed object is
         *     {@link ExportMeteringDeviceHistoryResultType.OneRateDeviceValue.ArchivedValues }
         *     
         */
        public void setArchivedValues(ExportMeteringDeviceHistoryResultType.OneRateDeviceValue.ArchivedValues value) {
            this.archivedValues = value;
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
         *         &lt;element name="ArchivingReason" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}nsiRef"/>
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
            "archivingReason"
        })
        public static class ArchivedValues {

            @XmlElement(name = "ArchivingReason", required = true)
            protected NsiRef archivingReason;

            /**
             * Gets the value of the archivingReason property.
             * 
             * @return
             *     possible object is
             *     {@link NsiRef }
             *     
             */
            public NsiRef getArchivingReason() {
                return archivingReason;
            }

            /**
             * Sets the value of the archivingReason property.
             * 
             * @param value
             *     allowed object is
             *     {@link NsiRef }
             *     
             */
            public void setArchivingReason(NsiRef value) {
                this.archivingReason = value;
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
         *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}OneRateMeteringValueKindType">
         *       &lt;sequence>
         *         &lt;element name="excludeISValues" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
            "excludeISValues"
        })
        public static class Values
            extends OneRateMeteringValueKindType
        {

            protected Boolean excludeISValues;

            /**
             * Gets the value of the excludeISValues property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isExcludeISValues() {
                return excludeISValues;
            }

            /**
             * Sets the value of the excludeISValues property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setExcludeISValues(Boolean value) {
                this.excludeISValues = value;
            }

        }

    }

}

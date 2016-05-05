
package ru.gosuslugi.dom.schema.integration.services.device_metering;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration.base.BaseType;
import ru.gosuslugi.dom.schema.integration.base.ElectricMeteringValueType;
import ru.gosuslugi.dom.schema.integration.base.NsiRef;
import ru.gosuslugi.dom.schema.integration.base.OneRateMeteringValueType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}BaseType">
 *       &lt;sequence>
 *         &lt;element name="FIASHouseGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}GUIDType"/>
 *         &lt;element name="MeteringDevicesValues" maxOccurs="1000">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="MeteringDeviceRootGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}MeteringDeviceGUIDType"/>
 *                   &lt;element name="AccountGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}GUIDType" minOccurs="0"/>
 *                   &lt;choice>
 *                     &lt;element name="OneRateDeviceValue">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="CurrentValue" minOccurs="0">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}OneRateMeteringValueType">
 *                                       &lt;sequence>
 *                                         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}TransportGUID"/>
 *                                       &lt;/sequence>
 *                                     &lt;/extension>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                               &lt;element name="ControlValue" maxOccurs="unbounded" minOccurs="0">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}OneRateMeteringValueType">
 *                                       &lt;sequence>
 *                                         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}TransportGUID"/>
 *                                       &lt;/sequence>
 *                                     &lt;/extension>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                               &lt;element name="VerificationValue" maxOccurs="unbounded" minOccurs="0">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                       &lt;sequence>
 *                                         &lt;element name="StartVerificationValue" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}OneRateMeteringValueType"/>
 *                                         &lt;element name="EndVerificationValue" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}OneRateMeteringValueType"/>
 *                                         &lt;choice>
 *                                           &lt;element name="PlannedVerification" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                                           &lt;element name="VerificationReason" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}nsiRef"/>
 *                                         &lt;/choice>
 *                                         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}TransportGUID"/>
 *                                       &lt;/sequence>
 *                                     &lt;/restriction>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="ElectricDeviceValue">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="CurrentValue" minOccurs="0">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}ElectricMeteringValueType">
 *                                       &lt;sequence>
 *                                         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}TransportGUID"/>
 *                                       &lt;/sequence>
 *                                     &lt;/extension>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                               &lt;element name="ControlValue" maxOccurs="unbounded" minOccurs="0">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}ElectricMeteringValueType">
 *                                       &lt;sequence>
 *                                         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}TransportGUID"/>
 *                                       &lt;/sequence>
 *                                     &lt;/extension>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                               &lt;element name="VerificationValue" maxOccurs="unbounded" minOccurs="0">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                       &lt;sequence>
 *                                         &lt;element name="StartVerificationValue" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}ElectricMeteringValueType"/>
 *                                         &lt;element name="EndVerificationValue" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}ElectricMeteringValueType"/>
 *                                         &lt;choice>
 *                                           &lt;element name="PlannedVerification" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                                           &lt;element name="VerificationReason" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}nsiRef"/>
 *                                         &lt;/choice>
 *                                         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}TransportGUID"/>
 *                                       &lt;/sequence>
 *                                     &lt;/restriction>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                   &lt;/choice>
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
    "fiasHouseGuid",
    "meteringDevicesValues"
})
@XmlRootElement(name = "importMeteringDeviceValuesRequest")
public class ImportMeteringDeviceValuesRequest
    extends BaseType
{

    @XmlElement(name = "FIASHouseGuid", required = true)
    protected String fiasHouseGuid;
    @XmlElement(name = "MeteringDevicesValues", required = true)
    protected List<ImportMeteringDeviceValuesRequest.MeteringDevicesValues> meteringDevicesValues;

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
     * Gets the value of the meteringDevicesValues property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the meteringDevicesValues property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMeteringDevicesValues().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ImportMeteringDeviceValuesRequest.MeteringDevicesValues }
     * 
     * 
     */
    public List<ImportMeteringDeviceValuesRequest.MeteringDevicesValues> getMeteringDevicesValues() {
        if (meteringDevicesValues == null) {
            meteringDevicesValues = new ArrayList<ImportMeteringDeviceValuesRequest.MeteringDevicesValues>();
        }
        return this.meteringDevicesValues;
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
     *         &lt;element name="MeteringDeviceRootGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}MeteringDeviceGUIDType"/>
     *         &lt;element name="AccountGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}GUIDType" minOccurs="0"/>
     *         &lt;choice>
     *           &lt;element name="OneRateDeviceValue">
     *             &lt;complexType>
     *               &lt;complexContent>
     *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                   &lt;sequence>
     *                     &lt;element name="CurrentValue" minOccurs="0">
     *                       &lt;complexType>
     *                         &lt;complexContent>
     *                           &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}OneRateMeteringValueType">
     *                             &lt;sequence>
     *                               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}TransportGUID"/>
     *                             &lt;/sequence>
     *                           &lt;/extension>
     *                         &lt;/complexContent>
     *                       &lt;/complexType>
     *                     &lt;/element>
     *                     &lt;element name="ControlValue" maxOccurs="unbounded" minOccurs="0">
     *                       &lt;complexType>
     *                         &lt;complexContent>
     *                           &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}OneRateMeteringValueType">
     *                             &lt;sequence>
     *                               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}TransportGUID"/>
     *                             &lt;/sequence>
     *                           &lt;/extension>
     *                         &lt;/complexContent>
     *                       &lt;/complexType>
     *                     &lt;/element>
     *                     &lt;element name="VerificationValue" maxOccurs="unbounded" minOccurs="0">
     *                       &lt;complexType>
     *                         &lt;complexContent>
     *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                             &lt;sequence>
     *                               &lt;element name="StartVerificationValue" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}OneRateMeteringValueType"/>
     *                               &lt;element name="EndVerificationValue" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}OneRateMeteringValueType"/>
     *                               &lt;choice>
     *                                 &lt;element name="PlannedVerification" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *                                 &lt;element name="VerificationReason" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}nsiRef"/>
     *                               &lt;/choice>
     *                               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}TransportGUID"/>
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
     *                     &lt;element name="CurrentValue" minOccurs="0">
     *                       &lt;complexType>
     *                         &lt;complexContent>
     *                           &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}ElectricMeteringValueType">
     *                             &lt;sequence>
     *                               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}TransportGUID"/>
     *                             &lt;/sequence>
     *                           &lt;/extension>
     *                         &lt;/complexContent>
     *                       &lt;/complexType>
     *                     &lt;/element>
     *                     &lt;element name="ControlValue" maxOccurs="unbounded" minOccurs="0">
     *                       &lt;complexType>
     *                         &lt;complexContent>
     *                           &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}ElectricMeteringValueType">
     *                             &lt;sequence>
     *                               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}TransportGUID"/>
     *                             &lt;/sequence>
     *                           &lt;/extension>
     *                         &lt;/complexContent>
     *                       &lt;/complexType>
     *                     &lt;/element>
     *                     &lt;element name="VerificationValue" maxOccurs="unbounded" minOccurs="0">
     *                       &lt;complexType>
     *                         &lt;complexContent>
     *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                             &lt;sequence>
     *                               &lt;element name="StartVerificationValue" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}ElectricMeteringValueType"/>
     *                               &lt;element name="EndVerificationValue" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}ElectricMeteringValueType"/>
     *                               &lt;choice>
     *                                 &lt;element name="PlannedVerification" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *                                 &lt;element name="VerificationReason" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}nsiRef"/>
     *                               &lt;/choice>
     *                               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}TransportGUID"/>
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
    @XmlType(name = "", propOrder = {
        "meteringDeviceRootGUID",
        "accountGUID",
        "oneRateDeviceValue",
        "electricDeviceValue"
    })
    public static class MeteringDevicesValues {

        @XmlElement(name = "MeteringDeviceRootGUID", required = true)
        protected String meteringDeviceRootGUID;
        @XmlElement(name = "AccountGUID")
        protected String accountGUID;
        @XmlElement(name = "OneRateDeviceValue")
        protected ImportMeteringDeviceValuesRequest.MeteringDevicesValues.OneRateDeviceValue oneRateDeviceValue;
        @XmlElement(name = "ElectricDeviceValue")
        protected ImportMeteringDeviceValuesRequest.MeteringDevicesValues.ElectricDeviceValue electricDeviceValue;

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
         * Gets the value of the accountGUID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAccountGUID() {
            return accountGUID;
        }

        /**
         * Sets the value of the accountGUID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAccountGUID(String value) {
            this.accountGUID = value;
        }

        /**
         * Gets the value of the oneRateDeviceValue property.
         * 
         * @return
         *     possible object is
         *     {@link ImportMeteringDeviceValuesRequest.MeteringDevicesValues.OneRateDeviceValue }
         *     
         */
        public ImportMeteringDeviceValuesRequest.MeteringDevicesValues.OneRateDeviceValue getOneRateDeviceValue() {
            return oneRateDeviceValue;
        }

        /**
         * Sets the value of the oneRateDeviceValue property.
         * 
         * @param value
         *     allowed object is
         *     {@link ImportMeteringDeviceValuesRequest.MeteringDevicesValues.OneRateDeviceValue }
         *     
         */
        public void setOneRateDeviceValue(ImportMeteringDeviceValuesRequest.MeteringDevicesValues.OneRateDeviceValue value) {
            this.oneRateDeviceValue = value;
        }

        /**
         * Gets the value of the electricDeviceValue property.
         * 
         * @return
         *     possible object is
         *     {@link ImportMeteringDeviceValuesRequest.MeteringDevicesValues.ElectricDeviceValue }
         *     
         */
        public ImportMeteringDeviceValuesRequest.MeteringDevicesValues.ElectricDeviceValue getElectricDeviceValue() {
            return electricDeviceValue;
        }

        /**
         * Sets the value of the electricDeviceValue property.
         * 
         * @param value
         *     allowed object is
         *     {@link ImportMeteringDeviceValuesRequest.MeteringDevicesValues.ElectricDeviceValue }
         *     
         */
        public void setElectricDeviceValue(ImportMeteringDeviceValuesRequest.MeteringDevicesValues.ElectricDeviceValue value) {
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
         *         &lt;element name="CurrentValue" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}ElectricMeteringValueType">
         *                 &lt;sequence>
         *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}TransportGUID"/>
         *                 &lt;/sequence>
         *               &lt;/extension>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="ControlValue" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}ElectricMeteringValueType">
         *                 &lt;sequence>
         *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}TransportGUID"/>
         *                 &lt;/sequence>
         *               &lt;/extension>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="VerificationValue" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="StartVerificationValue" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}ElectricMeteringValueType"/>
         *                   &lt;element name="EndVerificationValue" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}ElectricMeteringValueType"/>
         *                   &lt;choice>
         *                     &lt;element name="PlannedVerification" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
         *                     &lt;element name="VerificationReason" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}nsiRef"/>
         *                   &lt;/choice>
         *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}TransportGUID"/>
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
            "currentValue",
            "controlValue",
            "verificationValue"
        })
        public static class ElectricDeviceValue {

            @XmlElement(name = "CurrentValue")
            protected ImportMeteringDeviceValuesRequest.MeteringDevicesValues.ElectricDeviceValue.CurrentValue currentValue;
            @XmlElement(name = "ControlValue")
            protected List<ImportMeteringDeviceValuesRequest.MeteringDevicesValues.ElectricDeviceValue.ControlValue> controlValue;
            @XmlElement(name = "VerificationValue")
            protected List<ImportMeteringDeviceValuesRequest.MeteringDevicesValues.ElectricDeviceValue.VerificationValue> verificationValue;

            /**
             * Gets the value of the currentValue property.
             * 
             * @return
             *     possible object is
             *     {@link ImportMeteringDeviceValuesRequest.MeteringDevicesValues.ElectricDeviceValue.CurrentValue }
             *     
             */
            public ImportMeteringDeviceValuesRequest.MeteringDevicesValues.ElectricDeviceValue.CurrentValue getCurrentValue() {
                return currentValue;
            }

            /**
             * Sets the value of the currentValue property.
             * 
             * @param value
             *     allowed object is
             *     {@link ImportMeteringDeviceValuesRequest.MeteringDevicesValues.ElectricDeviceValue.CurrentValue }
             *     
             */
            public void setCurrentValue(ImportMeteringDeviceValuesRequest.MeteringDevicesValues.ElectricDeviceValue.CurrentValue value) {
                this.currentValue = value;
            }

            /**
             * Gets the value of the controlValue property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the controlValue property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getControlValue().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ImportMeteringDeviceValuesRequest.MeteringDevicesValues.ElectricDeviceValue.ControlValue }
             * 
             * 
             */
            public List<ImportMeteringDeviceValuesRequest.MeteringDevicesValues.ElectricDeviceValue.ControlValue> getControlValue() {
                if (controlValue == null) {
                    controlValue = new ArrayList<ImportMeteringDeviceValuesRequest.MeteringDevicesValues.ElectricDeviceValue.ControlValue>();
                }
                return this.controlValue;
            }

            /**
             * Gets the value of the verificationValue property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the verificationValue property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getVerificationValue().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ImportMeteringDeviceValuesRequest.MeteringDevicesValues.ElectricDeviceValue.VerificationValue }
             * 
             * 
             */
            public List<ImportMeteringDeviceValuesRequest.MeteringDevicesValues.ElectricDeviceValue.VerificationValue> getVerificationValue() {
                if (verificationValue == null) {
                    verificationValue = new ArrayList<ImportMeteringDeviceValuesRequest.MeteringDevicesValues.ElectricDeviceValue.VerificationValue>();
                }
                return this.verificationValue;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}ElectricMeteringValueType">
             *       &lt;sequence>
             *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}TransportGUID"/>
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
                "transportGUID"
            })
            public static class ControlValue
                extends ElectricMeteringValueType
            {

                @XmlElement(name = "TransportGUID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", required = true)
                protected String transportGUID;

                /**
                 * Gets the value of the transportGUID property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTransportGUID() {
                    return transportGUID;
                }

                /**
                 * Sets the value of the transportGUID property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTransportGUID(String value) {
                    this.transportGUID = value;
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
             *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}ElectricMeteringValueType">
             *       &lt;sequence>
             *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}TransportGUID"/>
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
                "transportGUID"
            })
            public static class CurrentValue
                extends ElectricMeteringValueType
            {

                @XmlElement(name = "TransportGUID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", required = true)
                protected String transportGUID;

                /**
                 * Gets the value of the transportGUID property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTransportGUID() {
                    return transportGUID;
                }

                /**
                 * Sets the value of the transportGUID property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTransportGUID(String value) {
                    this.transportGUID = value;
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
             *         &lt;element name="StartVerificationValue" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}ElectricMeteringValueType"/>
             *         &lt;element name="EndVerificationValue" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}ElectricMeteringValueType"/>
             *         &lt;choice>
             *           &lt;element name="PlannedVerification" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
             *           &lt;element name="VerificationReason" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}nsiRef"/>
             *         &lt;/choice>
             *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}TransportGUID"/>
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
                "startVerificationValue",
                "endVerificationValue",
                "plannedVerification",
                "verificationReason",
                "transportGUID"
            })
            public static class VerificationValue {

                @XmlElement(name = "StartVerificationValue", required = true)
                protected ElectricMeteringValueType startVerificationValue;
                @XmlElement(name = "EndVerificationValue", required = true)
                protected ElectricMeteringValueType endVerificationValue;
                @XmlElement(name = "PlannedVerification")
                protected Boolean plannedVerification;
                @XmlElement(name = "VerificationReason")
                protected NsiRef verificationReason;
                @XmlElement(name = "TransportGUID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", required = true)
                protected String transportGUID;

                /**
                 * Gets the value of the startVerificationValue property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link ElectricMeteringValueType }
                 *     
                 */
                public ElectricMeteringValueType getStartVerificationValue() {
                    return startVerificationValue;
                }

                /**
                 * Sets the value of the startVerificationValue property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link ElectricMeteringValueType }
                 *     
                 */
                public void setStartVerificationValue(ElectricMeteringValueType value) {
                    this.startVerificationValue = value;
                }

                /**
                 * Gets the value of the endVerificationValue property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link ElectricMeteringValueType }
                 *     
                 */
                public ElectricMeteringValueType getEndVerificationValue() {
                    return endVerificationValue;
                }

                /**
                 * Sets the value of the endVerificationValue property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link ElectricMeteringValueType }
                 *     
                 */
                public void setEndVerificationValue(ElectricMeteringValueType value) {
                    this.endVerificationValue = value;
                }

                /**
                 * Gets the value of the plannedVerification property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Boolean }
                 *     
                 */
                public Boolean isPlannedVerification() {
                    return plannedVerification;
                }

                /**
                 * Sets the value of the plannedVerification property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Boolean }
                 *     
                 */
                public void setPlannedVerification(Boolean value) {
                    this.plannedVerification = value;
                }

                /**
                 * Gets the value of the verificationReason property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link NsiRef }
                 *     
                 */
                public NsiRef getVerificationReason() {
                    return verificationReason;
                }

                /**
                 * Sets the value of the verificationReason property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link NsiRef }
                 *     
                 */
                public void setVerificationReason(NsiRef value) {
                    this.verificationReason = value;
                }

                /**
                 * Gets the value of the transportGUID property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTransportGUID() {
                    return transportGUID;
                }

                /**
                 * Sets the value of the transportGUID property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTransportGUID(String value) {
                    this.transportGUID = value;
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
         *         &lt;element name="CurrentValue" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}OneRateMeteringValueType">
         *                 &lt;sequence>
         *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}TransportGUID"/>
         *                 &lt;/sequence>
         *               &lt;/extension>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="ControlValue" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}OneRateMeteringValueType">
         *                 &lt;sequence>
         *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}TransportGUID"/>
         *                 &lt;/sequence>
         *               &lt;/extension>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="VerificationValue" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="StartVerificationValue" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}OneRateMeteringValueType"/>
         *                   &lt;element name="EndVerificationValue" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}OneRateMeteringValueType"/>
         *                   &lt;choice>
         *                     &lt;element name="PlannedVerification" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
         *                     &lt;element name="VerificationReason" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}nsiRef"/>
         *                   &lt;/choice>
         *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}TransportGUID"/>
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
            "currentValue",
            "controlValue",
            "verificationValue"
        })
        public static class OneRateDeviceValue {

            @XmlElement(name = "CurrentValue")
            protected ImportMeteringDeviceValuesRequest.MeteringDevicesValues.OneRateDeviceValue.CurrentValue currentValue;
            @XmlElement(name = "ControlValue")
            protected List<ImportMeteringDeviceValuesRequest.MeteringDevicesValues.OneRateDeviceValue.ControlValue> controlValue;
            @XmlElement(name = "VerificationValue")
            protected List<ImportMeteringDeviceValuesRequest.MeteringDevicesValues.OneRateDeviceValue.VerificationValue> verificationValue;

            /**
             * Gets the value of the currentValue property.
             * 
             * @return
             *     possible object is
             *     {@link ImportMeteringDeviceValuesRequest.MeteringDevicesValues.OneRateDeviceValue.CurrentValue }
             *     
             */
            public ImportMeteringDeviceValuesRequest.MeteringDevicesValues.OneRateDeviceValue.CurrentValue getCurrentValue() {
                return currentValue;
            }

            /**
             * Sets the value of the currentValue property.
             * 
             * @param value
             *     allowed object is
             *     {@link ImportMeteringDeviceValuesRequest.MeteringDevicesValues.OneRateDeviceValue.CurrentValue }
             *     
             */
            public void setCurrentValue(ImportMeteringDeviceValuesRequest.MeteringDevicesValues.OneRateDeviceValue.CurrentValue value) {
                this.currentValue = value;
            }

            /**
             * Gets the value of the controlValue property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the controlValue property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getControlValue().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ImportMeteringDeviceValuesRequest.MeteringDevicesValues.OneRateDeviceValue.ControlValue }
             * 
             * 
             */
            public List<ImportMeteringDeviceValuesRequest.MeteringDevicesValues.OneRateDeviceValue.ControlValue> getControlValue() {
                if (controlValue == null) {
                    controlValue = new ArrayList<ImportMeteringDeviceValuesRequest.MeteringDevicesValues.OneRateDeviceValue.ControlValue>();
                }
                return this.controlValue;
            }

            /**
             * Gets the value of the verificationValue property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the verificationValue property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getVerificationValue().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ImportMeteringDeviceValuesRequest.MeteringDevicesValues.OneRateDeviceValue.VerificationValue }
             * 
             * 
             */
            public List<ImportMeteringDeviceValuesRequest.MeteringDevicesValues.OneRateDeviceValue.VerificationValue> getVerificationValue() {
                if (verificationValue == null) {
                    verificationValue = new ArrayList<ImportMeteringDeviceValuesRequest.MeteringDevicesValues.OneRateDeviceValue.VerificationValue>();
                }
                return this.verificationValue;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}OneRateMeteringValueType">
             *       &lt;sequence>
             *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}TransportGUID"/>
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
                "transportGUID"
            })
            public static class ControlValue
                extends OneRateMeteringValueType
            {

                @XmlElement(name = "TransportGUID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", required = true)
                protected String transportGUID;

                /**
                 * Gets the value of the transportGUID property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTransportGUID() {
                    return transportGUID;
                }

                /**
                 * Sets the value of the transportGUID property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTransportGUID(String value) {
                    this.transportGUID = value;
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
             *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}OneRateMeteringValueType">
             *       &lt;sequence>
             *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}TransportGUID"/>
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
                "transportGUID"
            })
            public static class CurrentValue
                extends OneRateMeteringValueType
            {

                @XmlElement(name = "TransportGUID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", required = true)
                protected String transportGUID;

                /**
                 * Gets the value of the transportGUID property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTransportGUID() {
                    return transportGUID;
                }

                /**
                 * Sets the value of the transportGUID property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTransportGUID(String value) {
                    this.transportGUID = value;
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
             *         &lt;element name="StartVerificationValue" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}OneRateMeteringValueType"/>
             *         &lt;element name="EndVerificationValue" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}OneRateMeteringValueType"/>
             *         &lt;choice>
             *           &lt;element name="PlannedVerification" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
             *           &lt;element name="VerificationReason" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}nsiRef"/>
             *         &lt;/choice>
             *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}TransportGUID"/>
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
                "startVerificationValue",
                "endVerificationValue",
                "plannedVerification",
                "verificationReason",
                "transportGUID"
            })
            public static class VerificationValue {

                @XmlElement(name = "StartVerificationValue", required = true)
                protected OneRateMeteringValueType startVerificationValue;
                @XmlElement(name = "EndVerificationValue", required = true)
                protected OneRateMeteringValueType endVerificationValue;
                @XmlElement(name = "PlannedVerification")
                protected Boolean plannedVerification;
                @XmlElement(name = "VerificationReason")
                protected NsiRef verificationReason;
                @XmlElement(name = "TransportGUID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", required = true)
                protected String transportGUID;

                /**
                 * Gets the value of the startVerificationValue property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link OneRateMeteringValueType }
                 *     
                 */
                public OneRateMeteringValueType getStartVerificationValue() {
                    return startVerificationValue;
                }

                /**
                 * Sets the value of the startVerificationValue property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link OneRateMeteringValueType }
                 *     
                 */
                public void setStartVerificationValue(OneRateMeteringValueType value) {
                    this.startVerificationValue = value;
                }

                /**
                 * Gets the value of the endVerificationValue property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link OneRateMeteringValueType }
                 *     
                 */
                public OneRateMeteringValueType getEndVerificationValue() {
                    return endVerificationValue;
                }

                /**
                 * Sets the value of the endVerificationValue property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link OneRateMeteringValueType }
                 *     
                 */
                public void setEndVerificationValue(OneRateMeteringValueType value) {
                    this.endVerificationValue = value;
                }

                /**
                 * Gets the value of the plannedVerification property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Boolean }
                 *     
                 */
                public Boolean isPlannedVerification() {
                    return plannedVerification;
                }

                /**
                 * Sets the value of the plannedVerification property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Boolean }
                 *     
                 */
                public void setPlannedVerification(Boolean value) {
                    this.plannedVerification = value;
                }

                /**
                 * Gets the value of the verificationReason property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link NsiRef }
                 *     
                 */
                public NsiRef getVerificationReason() {
                    return verificationReason;
                }

                /**
                 * Sets the value of the verificationReason property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link NsiRef }
                 *     
                 */
                public void setVerificationReason(NsiRef value) {
                    this.verificationReason = value;
                }

                /**
                 * Gets the value of the transportGUID property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getTransportGUID() {
                    return transportGUID;
                }

                /**
                 * Sets the value of the transportGUID property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setTransportGUID(String value) {
                    this.transportGUID = value;
                }

            }

        }

    }

}

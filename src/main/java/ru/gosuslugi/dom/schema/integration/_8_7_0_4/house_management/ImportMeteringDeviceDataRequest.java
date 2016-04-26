
package ru.gosuslugi.dom.schema.integration._8_7_0_4.house_management;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import ru.gosuslugi.dom.schema.integration._8_7_0.BaseType;
import ru.gosuslugi.dom.schema.integration._8_7_0.ElectricMeteringDeviceType;
import ru.gosuslugi.dom.schema.integration._8_7_0.NsiRef;
import ru.gosuslugi.dom.schema.integration._8_7_0.OneRateMeteringDeviceType;


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
 *         &lt;element name="FIASHouseGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType"/>
 *         &lt;element name="MeteringDevice" maxOccurs="100">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}TransportGUID"/>
 *                   &lt;choice>
 *                     &lt;element name="DeviceDataToCreate">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;choice>
 *                               &lt;element name="OneRateMeteringDevice">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}OneRateMeteringDeviceType">
 *                                       &lt;sequence>
 *                                         &lt;element name="MunicipalResource" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}nsiRef"/>
 *                                       &lt;/sequence>
 *                                     &lt;/extension>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                               &lt;element name="ElectricMeteringDevice">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}ElectricMeteringDeviceType">
 *                                       &lt;sequence>
 *                                         &lt;element name="MunicipalResource" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}nsiRef"/>
 *                                       &lt;/sequence>
 *                                     &lt;/extension>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                             &lt;/choice>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="DeviceDataToUpdate">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="MeteringDeviceVersionGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MeteringDeviceGUIDType"/>
 *                               &lt;element name="MeteringDeviceStamp" minOccurs="0">
 *                                 &lt;simpleType>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                     &lt;maxLength value="20"/>
 *                                   &lt;/restriction>
 *                                 &lt;/simpleType>
 *                               &lt;/element>
 *                               &lt;element name="ManualModeMetering" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                               &lt;element name="AccountGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType" maxOccurs="unbounded" minOccurs="0"/>
 *                               &lt;choice minOccurs="0">
 *                                 &lt;element name="ArchiveDevice">
 *                                   &lt;complexType>
 *                                     &lt;complexContent>
 *                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                         &lt;sequence>
 *                                           &lt;element name="ArchivingReason" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}nsiRef"/>
 *                                         &lt;/sequence>
 *                                       &lt;/restriction>
 *                                     &lt;/complexContent>
 *                                   &lt;/complexType>
 *                                 &lt;/element>
 *                                 &lt;element name="ReplaceDevice">
 *                                   &lt;complexType>
 *                                     &lt;complexContent>
 *                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                         &lt;sequence>
 *                                           &lt;element name="PlannedVerification" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                                           &lt;choice>
 *                                             &lt;element name="OneRateDeviceValue">
 *                                               &lt;complexType>
 *                                                 &lt;complexContent>
 *                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                     &lt;sequence>
 *                                                       &lt;element name="MeteringValue" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MeteringValueType"/>
 *                                                       &lt;element name="beginDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                                                     &lt;/sequence>
 *                                                   &lt;/restriction>
 *                                                 &lt;/complexContent>
 *                                               &lt;/complexType>
 *                                             &lt;/element>
 *                                             &lt;element name="ElectricDeviceValue">
 *                                               &lt;complexType>
 *                                                 &lt;complexContent>
 *                                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                                     &lt;sequence>
 *                                                       &lt;element name="MeteringValueT1" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MeteringValueType"/>
 *                                                       &lt;element name="MeteringValueT2" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MeteringValueType" minOccurs="0"/>
 *                                                       &lt;element name="MeteringValueT3" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MeteringValueType" minOccurs="0"/>
 *                                                       &lt;element name="beginDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                                                     &lt;/sequence>
 *                                                   &lt;/restriction>
 *                                                 &lt;/complexContent>
 *                                               &lt;/complexType>
 *                                             &lt;/element>
 *                                           &lt;/choice>
 *                                           &lt;element name="ReplacingMeteringDeviceVersionGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MeteringDeviceGUIDType"/>
 *                                         &lt;/sequence>
 *                                       &lt;/restriction>
 *                                     &lt;/complexContent>
 *                                   &lt;/complexType>
 *                                 &lt;/element>
 *                               &lt;/choice>
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
    "meteringDevice"
})
@XmlRootElement(name = "importMeteringDeviceDataRequest")
public class ImportMeteringDeviceDataRequest
    extends BaseType
{

    @XmlElement(name = "FIASHouseGuid", required = true)
    protected String fiasHouseGuid;
    @XmlElement(name = "MeteringDevice", required = true)
    protected List<ImportMeteringDeviceDataRequest.MeteringDevice> meteringDevice;

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
     * Gets the value of the meteringDevice property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the meteringDevice property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMeteringDevice().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ImportMeteringDeviceDataRequest.MeteringDevice }
     * 
     * 
     */
    public List<ImportMeteringDeviceDataRequest.MeteringDevice> getMeteringDevice() {
        if (meteringDevice == null) {
            meteringDevice = new ArrayList<ImportMeteringDeviceDataRequest.MeteringDevice>();
        }
        return this.meteringDevice;
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
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}TransportGUID"/>
     *         &lt;choice>
     *           &lt;element name="DeviceDataToCreate">
     *             &lt;complexType>
     *               &lt;complexContent>
     *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                   &lt;choice>
     *                     &lt;element name="OneRateMeteringDevice">
     *                       &lt;complexType>
     *                         &lt;complexContent>
     *                           &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}OneRateMeteringDeviceType">
     *                             &lt;sequence>
     *                               &lt;element name="MunicipalResource" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}nsiRef"/>
     *                             &lt;/sequence>
     *                           &lt;/extension>
     *                         &lt;/complexContent>
     *                       &lt;/complexType>
     *                     &lt;/element>
     *                     &lt;element name="ElectricMeteringDevice">
     *                       &lt;complexType>
     *                         &lt;complexContent>
     *                           &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}ElectricMeteringDeviceType">
     *                             &lt;sequence>
     *                               &lt;element name="MunicipalResource" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}nsiRef"/>
     *                             &lt;/sequence>
     *                           &lt;/extension>
     *                         &lt;/complexContent>
     *                       &lt;/complexType>
     *                     &lt;/element>
     *                   &lt;/choice>
     *                 &lt;/restriction>
     *               &lt;/complexContent>
     *             &lt;/complexType>
     *           &lt;/element>
     *           &lt;element name="DeviceDataToUpdate">
     *             &lt;complexType>
     *               &lt;complexContent>
     *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                   &lt;sequence>
     *                     &lt;element name="MeteringDeviceVersionGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MeteringDeviceGUIDType"/>
     *                     &lt;element name="MeteringDeviceStamp" minOccurs="0">
     *                       &lt;simpleType>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                           &lt;maxLength value="20"/>
     *                         &lt;/restriction>
     *                       &lt;/simpleType>
     *                     &lt;/element>
     *                     &lt;element name="ManualModeMetering" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *                     &lt;element name="AccountGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType" maxOccurs="unbounded" minOccurs="0"/>
     *                     &lt;choice minOccurs="0">
     *                       &lt;element name="ArchiveDevice">
     *                         &lt;complexType>
     *                           &lt;complexContent>
     *                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                               &lt;sequence>
     *                                 &lt;element name="ArchivingReason" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}nsiRef"/>
     *                               &lt;/sequence>
     *                             &lt;/restriction>
     *                           &lt;/complexContent>
     *                         &lt;/complexType>
     *                       &lt;/element>
     *                       &lt;element name="ReplaceDevice">
     *                         &lt;complexType>
     *                           &lt;complexContent>
     *                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                               &lt;sequence>
     *                                 &lt;element name="PlannedVerification" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *                                 &lt;choice>
     *                                   &lt;element name="OneRateDeviceValue">
     *                                     &lt;complexType>
     *                                       &lt;complexContent>
     *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                           &lt;sequence>
     *                                             &lt;element name="MeteringValue" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MeteringValueType"/>
     *                                             &lt;element name="beginDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *                                           &lt;/sequence>
     *                                         &lt;/restriction>
     *                                       &lt;/complexContent>
     *                                     &lt;/complexType>
     *                                   &lt;/element>
     *                                   &lt;element name="ElectricDeviceValue">
     *                                     &lt;complexType>
     *                                       &lt;complexContent>
     *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                           &lt;sequence>
     *                                             &lt;element name="MeteringValueT1" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MeteringValueType"/>
     *                                             &lt;element name="MeteringValueT2" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MeteringValueType" minOccurs="0"/>
     *                                             &lt;element name="MeteringValueT3" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MeteringValueType" minOccurs="0"/>
     *                                             &lt;element name="beginDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *                                           &lt;/sequence>
     *                                         &lt;/restriction>
     *                                       &lt;/complexContent>
     *                                     &lt;/complexType>
     *                                   &lt;/element>
     *                                 &lt;/choice>
     *                                 &lt;element name="ReplacingMeteringDeviceVersionGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MeteringDeviceGUIDType"/>
     *                               &lt;/sequence>
     *                             &lt;/restriction>
     *                           &lt;/complexContent>
     *                         &lt;/complexType>
     *                       &lt;/element>
     *                     &lt;/choice>
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
        "transportGUID",
        "deviceDataToCreate",
        "deviceDataToUpdate"
    })
    public static class MeteringDevice {

        @XmlElement(name = "TransportGUID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", required = true)
        protected String transportGUID;
        @XmlElement(name = "DeviceDataToCreate")
        protected ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate deviceDataToCreate;
        @XmlElement(name = "DeviceDataToUpdate")
        protected ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate deviceDataToUpdate;

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

        /**
         * Gets the value of the deviceDataToCreate property.
         * 
         * @return
         *     possible object is
         *     {@link ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate }
         *     
         */
        public ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate getDeviceDataToCreate() {
            return deviceDataToCreate;
        }

        /**
         * Sets the value of the deviceDataToCreate property.
         * 
         * @param value
         *     allowed object is
         *     {@link ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate }
         *     
         */
        public void setDeviceDataToCreate(ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate value) {
            this.deviceDataToCreate = value;
        }

        /**
         * Gets the value of the deviceDataToUpdate property.
         * 
         * @return
         *     possible object is
         *     {@link ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate }
         *     
         */
        public ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate getDeviceDataToUpdate() {
            return deviceDataToUpdate;
        }

        /**
         * Sets the value of the deviceDataToUpdate property.
         * 
         * @param value
         *     allowed object is
         *     {@link ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate }
         *     
         */
        public void setDeviceDataToUpdate(ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate value) {
            this.deviceDataToUpdate = value;
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
         *         &lt;element name="OneRateMeteringDevice">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}OneRateMeteringDeviceType">
         *                 &lt;sequence>
         *                   &lt;element name="MunicipalResource" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}nsiRef"/>
         *                 &lt;/sequence>
         *               &lt;/extension>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="ElectricMeteringDevice">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}ElectricMeteringDeviceType">
         *                 &lt;sequence>
         *                   &lt;element name="MunicipalResource" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}nsiRef"/>
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
        @XmlType(name = "", propOrder = {
            "oneRateMeteringDevice",
            "electricMeteringDevice"
        })
        public static class DeviceDataToCreate {

            @XmlElement(name = "OneRateMeteringDevice")
            protected ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate.OneRateMeteringDevice oneRateMeteringDevice;
            @XmlElement(name = "ElectricMeteringDevice")
            protected ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate.ElectricMeteringDevice electricMeteringDevice;

            /**
             * Gets the value of the oneRateMeteringDevice property.
             * 
             * @return
             *     possible object is
             *     {@link ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate.OneRateMeteringDevice }
             *     
             */
            public ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate.OneRateMeteringDevice getOneRateMeteringDevice() {
                return oneRateMeteringDevice;
            }

            /**
             * Sets the value of the oneRateMeteringDevice property.
             * 
             * @param value
             *     allowed object is
             *     {@link ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate.OneRateMeteringDevice }
             *     
             */
            public void setOneRateMeteringDevice(ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate.OneRateMeteringDevice value) {
                this.oneRateMeteringDevice = value;
            }

            /**
             * Gets the value of the electricMeteringDevice property.
             * 
             * @return
             *     possible object is
             *     {@link ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate.ElectricMeteringDevice }
             *     
             */
            public ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate.ElectricMeteringDevice getElectricMeteringDevice() {
                return electricMeteringDevice;
            }

            /**
             * Sets the value of the electricMeteringDevice property.
             * 
             * @param value
             *     allowed object is
             *     {@link ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate.ElectricMeteringDevice }
             *     
             */
            public void setElectricMeteringDevice(ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate.ElectricMeteringDevice value) {
                this.electricMeteringDevice = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}ElectricMeteringDeviceType">
             *       &lt;sequence>
             *         &lt;element name="MunicipalResource" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}nsiRef"/>
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
                "municipalResource"
            })
            public static class ElectricMeteringDevice
                extends ElectricMeteringDeviceType
            {

                @XmlElement(name = "MunicipalResource", required = true)
                protected NsiRef municipalResource;

                /**
                 * Gets the value of the municipalResource property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link NsiRef }
                 *     
                 */
                public NsiRef getMunicipalResource() {
                    return municipalResource;
                }

                /**
                 * Sets the value of the municipalResource property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link NsiRef }
                 *     
                 */
                public void setMunicipalResource(NsiRef value) {
                    this.municipalResource = value;
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
             *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}OneRateMeteringDeviceType">
             *       &lt;sequence>
             *         &lt;element name="MunicipalResource" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}nsiRef"/>
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
                "municipalResource"
            })
            public static class OneRateMeteringDevice
                extends OneRateMeteringDeviceType
            {

                @XmlElement(name = "MunicipalResource", required = true)
                protected NsiRef municipalResource;

                /**
                 * Gets the value of the municipalResource property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link NsiRef }
                 *     
                 */
                public NsiRef getMunicipalResource() {
                    return municipalResource;
                }

                /**
                 * Sets the value of the municipalResource property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link NsiRef }
                 *     
                 */
                public void setMunicipalResource(NsiRef value) {
                    this.municipalResource = value;
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
         *         &lt;element name="MeteringDeviceVersionGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MeteringDeviceGUIDType"/>
         *         &lt;element name="MeteringDeviceStamp" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;maxLength value="20"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="ManualModeMetering" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
         *         &lt;element name="AccountGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType" maxOccurs="unbounded" minOccurs="0"/>
         *         &lt;choice minOccurs="0">
         *           &lt;element name="ArchiveDevice">
         *             &lt;complexType>
         *               &lt;complexContent>
         *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                   &lt;sequence>
         *                     &lt;element name="ArchivingReason" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}nsiRef"/>
         *                   &lt;/sequence>
         *                 &lt;/restriction>
         *               &lt;/complexContent>
         *             &lt;/complexType>
         *           &lt;/element>
         *           &lt;element name="ReplaceDevice">
         *             &lt;complexType>
         *               &lt;complexContent>
         *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                   &lt;sequence>
         *                     &lt;element name="PlannedVerification" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
         *                     &lt;choice>
         *                       &lt;element name="OneRateDeviceValue">
         *                         &lt;complexType>
         *                           &lt;complexContent>
         *                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                               &lt;sequence>
         *                                 &lt;element name="MeteringValue" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MeteringValueType"/>
         *                                 &lt;element name="beginDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
         *                               &lt;/sequence>
         *                             &lt;/restriction>
         *                           &lt;/complexContent>
         *                         &lt;/complexType>
         *                       &lt;/element>
         *                       &lt;element name="ElectricDeviceValue">
         *                         &lt;complexType>
         *                           &lt;complexContent>
         *                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                               &lt;sequence>
         *                                 &lt;element name="MeteringValueT1" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MeteringValueType"/>
         *                                 &lt;element name="MeteringValueT2" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MeteringValueType" minOccurs="0"/>
         *                                 &lt;element name="MeteringValueT3" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MeteringValueType" minOccurs="0"/>
         *                                 &lt;element name="beginDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
         *                               &lt;/sequence>
         *                             &lt;/restriction>
         *                           &lt;/complexContent>
         *                         &lt;/complexType>
         *                       &lt;/element>
         *                     &lt;/choice>
         *                     &lt;element name="ReplacingMeteringDeviceVersionGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MeteringDeviceGUIDType"/>
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
            "meteringDeviceVersionGUID",
            "meteringDeviceStamp",
            "manualModeMetering",
            "accountGUID",
            "archiveDevice",
            "replaceDevice"
        })
        public static class DeviceDataToUpdate {

            @XmlElement(name = "MeteringDeviceVersionGUID", required = true)
            protected String meteringDeviceVersionGUID;
            @XmlElement(name = "MeteringDeviceStamp")
            protected String meteringDeviceStamp;
            @XmlElement(name = "ManualModeMetering")
            protected Boolean manualModeMetering;
            @XmlElement(name = "AccountGUID")
            protected List<String> accountGUID;
            @XmlElement(name = "ArchiveDevice")
            protected ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate.ArchiveDevice archiveDevice;
            @XmlElement(name = "ReplaceDevice")
            protected ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate.ReplaceDevice replaceDevice;

            /**
             * Gets the value of the meteringDeviceVersionGUID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMeteringDeviceVersionGUID() {
                return meteringDeviceVersionGUID;
            }

            /**
             * Sets the value of the meteringDeviceVersionGUID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMeteringDeviceVersionGUID(String value) {
                this.meteringDeviceVersionGUID = value;
            }

            /**
             * Gets the value of the meteringDeviceStamp property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getMeteringDeviceStamp() {
                return meteringDeviceStamp;
            }

            /**
             * Sets the value of the meteringDeviceStamp property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setMeteringDeviceStamp(String value) {
                this.meteringDeviceStamp = value;
            }

            /**
             * Gets the value of the manualModeMetering property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isManualModeMetering() {
                return manualModeMetering;
            }

            /**
             * Sets the value of the manualModeMetering property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setManualModeMetering(Boolean value) {
                this.manualModeMetering = value;
            }

            /**
             * Gets the value of the accountGUID property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the accountGUID property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getAccountGUID().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link String }
             * 
             * 
             */
            public List<String> getAccountGUID() {
                if (accountGUID == null) {
                    accountGUID = new ArrayList<String>();
                }
                return this.accountGUID;
            }

            /**
             * Gets the value of the archiveDevice property.
             * 
             * @return
             *     possible object is
             *     {@link ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate.ArchiveDevice }
             *     
             */
            public ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate.ArchiveDevice getArchiveDevice() {
                return archiveDevice;
            }

            /**
             * Sets the value of the archiveDevice property.
             * 
             * @param value
             *     allowed object is
             *     {@link ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate.ArchiveDevice }
             *     
             */
            public void setArchiveDevice(ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate.ArchiveDevice value) {
                this.archiveDevice = value;
            }

            /**
             * Gets the value of the replaceDevice property.
             * 
             * @return
             *     possible object is
             *     {@link ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate.ReplaceDevice }
             *     
             */
            public ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate.ReplaceDevice getReplaceDevice() {
                return replaceDevice;
            }

            /**
             * Sets the value of the replaceDevice property.
             * 
             * @param value
             *     allowed object is
             *     {@link ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate.ReplaceDevice }
             *     
             */
            public void setReplaceDevice(ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate.ReplaceDevice value) {
                this.replaceDevice = value;
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
             *         &lt;element name="ArchivingReason" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}nsiRef"/>
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
            public static class ArchiveDevice {

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
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="PlannedVerification" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
             *         &lt;choice>
             *           &lt;element name="OneRateDeviceValue">
             *             &lt;complexType>
             *               &lt;complexContent>
             *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                   &lt;sequence>
             *                     &lt;element name="MeteringValue" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MeteringValueType"/>
             *                     &lt;element name="beginDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
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
             *                     &lt;element name="MeteringValueT1" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MeteringValueType"/>
             *                     &lt;element name="MeteringValueT2" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MeteringValueType" minOccurs="0"/>
             *                     &lt;element name="MeteringValueT3" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MeteringValueType" minOccurs="0"/>
             *                     &lt;element name="beginDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
             *                   &lt;/sequence>
             *                 &lt;/restriction>
             *               &lt;/complexContent>
             *             &lt;/complexType>
             *           &lt;/element>
             *         &lt;/choice>
             *         &lt;element name="ReplacingMeteringDeviceVersionGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MeteringDeviceGUIDType"/>
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
                "plannedVerification",
                "oneRateDeviceValue",
                "electricDeviceValue",
                "replacingMeteringDeviceVersionGUID"
            })
            public static class ReplaceDevice {

                @XmlElement(name = "PlannedVerification")
                protected Boolean plannedVerification;
                @XmlElement(name = "OneRateDeviceValue")
                protected ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate.ReplaceDevice.OneRateDeviceValue oneRateDeviceValue;
                @XmlElement(name = "ElectricDeviceValue")
                protected ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate.ReplaceDevice.ElectricDeviceValue electricDeviceValue;
                @XmlElement(name = "ReplacingMeteringDeviceVersionGUID", required = true)
                protected String replacingMeteringDeviceVersionGUID;

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
                 * Gets the value of the oneRateDeviceValue property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate.ReplaceDevice.OneRateDeviceValue }
                 *     
                 */
                public ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate.ReplaceDevice.OneRateDeviceValue getOneRateDeviceValue() {
                    return oneRateDeviceValue;
                }

                /**
                 * Sets the value of the oneRateDeviceValue property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate.ReplaceDevice.OneRateDeviceValue }
                 *     
                 */
                public void setOneRateDeviceValue(ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate.ReplaceDevice.OneRateDeviceValue value) {
                    this.oneRateDeviceValue = value;
                }

                /**
                 * Gets the value of the electricDeviceValue property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate.ReplaceDevice.ElectricDeviceValue }
                 *     
                 */
                public ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate.ReplaceDevice.ElectricDeviceValue getElectricDeviceValue() {
                    return electricDeviceValue;
                }

                /**
                 * Sets the value of the electricDeviceValue property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate.ReplaceDevice.ElectricDeviceValue }
                 *     
                 */
                public void setElectricDeviceValue(ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToUpdate.ReplaceDevice.ElectricDeviceValue value) {
                    this.electricDeviceValue = value;
                }

                /**
                 * Gets the value of the replacingMeteringDeviceVersionGUID property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getReplacingMeteringDeviceVersionGUID() {
                    return replacingMeteringDeviceVersionGUID;
                }

                /**
                 * Sets the value of the replacingMeteringDeviceVersionGUID property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setReplacingMeteringDeviceVersionGUID(String value) {
                    this.replacingMeteringDeviceVersionGUID = value;
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
                 *         &lt;element name="MeteringValueT1" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MeteringValueType"/>
                 *         &lt;element name="MeteringValueT2" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MeteringValueType" minOccurs="0"/>
                 *         &lt;element name="MeteringValueT3" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MeteringValueType" minOccurs="0"/>
                 *         &lt;element name="beginDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
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
                    "meteringValueT1",
                    "meteringValueT2",
                    "meteringValueT3",
                    "beginDate"
                })
                public static class ElectricDeviceValue {

                    @XmlElement(name = "MeteringValueT1", required = true)
                    protected BigDecimal meteringValueT1;
                    @XmlElement(name = "MeteringValueT2")
                    protected BigDecimal meteringValueT2;
                    @XmlElement(name = "MeteringValueT3")
                    protected BigDecimal meteringValueT3;
                    @XmlElement(required = true)
                    @XmlSchemaType(name = "date")
                    protected XMLGregorianCalendar beginDate;

                    /**
                     * Gets the value of the meteringValueT1 property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public BigDecimal getMeteringValueT1() {
                        return meteringValueT1;
                    }

                    /**
                     * Sets the value of the meteringValueT1 property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public void setMeteringValueT1(BigDecimal value) {
                        this.meteringValueT1 = value;
                    }

                    /**
                     * Gets the value of the meteringValueT2 property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public BigDecimal getMeteringValueT2() {
                        return meteringValueT2;
                    }

                    /**
                     * Sets the value of the meteringValueT2 property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public void setMeteringValueT2(BigDecimal value) {
                        this.meteringValueT2 = value;
                    }

                    /**
                     * Gets the value of the meteringValueT3 property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public BigDecimal getMeteringValueT3() {
                        return meteringValueT3;
                    }

                    /**
                     * Sets the value of the meteringValueT3 property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public void setMeteringValueT3(BigDecimal value) {
                        this.meteringValueT3 = value;
                    }

                    /**
                     * Gets the value of the beginDate property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link XMLGregorianCalendar }
                     *     
                     */
                    public XMLGregorianCalendar getBeginDate() {
                        return beginDate;
                    }

                    /**
                     * Sets the value of the beginDate property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link XMLGregorianCalendar }
                     *     
                     */
                    public void setBeginDate(XMLGregorianCalendar value) {
                        this.beginDate = value;
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
                 *         &lt;element name="MeteringValue" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MeteringValueType"/>
                 *         &lt;element name="beginDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
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
                    "meteringValue",
                    "beginDate"
                })
                public static class OneRateDeviceValue {

                    @XmlElement(name = "MeteringValue", required = true)
                    protected BigDecimal meteringValue;
                    @XmlElement(required = true)
                    @XmlSchemaType(name = "date")
                    protected XMLGregorianCalendar beginDate;

                    /**
                     * Gets the value of the meteringValue property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public BigDecimal getMeteringValue() {
                        return meteringValue;
                    }

                    /**
                     * Sets the value of the meteringValue property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link BigDecimal }
                     *     
                     */
                    public void setMeteringValue(BigDecimal value) {
                        this.meteringValue = value;
                    }

                    /**
                     * Gets the value of the beginDate property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link XMLGregorianCalendar }
                     *     
                     */
                    public XMLGregorianCalendar getBeginDate() {
                        return beginDate;
                    }

                    /**
                     * Sets the value of the beginDate property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link XMLGregorianCalendar }
                     *     
                     */
                    public void setBeginDate(XMLGregorianCalendar value) {
                        this.beginDate = value;
                    }

                }

            }

        }

    }

}

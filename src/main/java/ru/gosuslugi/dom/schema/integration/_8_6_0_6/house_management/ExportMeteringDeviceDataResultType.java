
package ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import ru.gosuslugi.dom.schema.integration._8_6_0.MeteringDeviceBasicCharacteristicsType;


/**
 * <p>Java class for exportMeteringDeviceDataResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="exportMeteringDeviceDataResultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MeteringDeviceRootGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}GUIDType"/>
 *         &lt;element name="StatusRootDoc">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="Active"/>
 *               &lt;enumeration value="Archival"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="MeteringDeviceVersionGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}MeteringDeviceGUIDType"/>
 *         &lt;element name="VersionNumber" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="StatusVersion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="UpdateDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="MunicipalResource" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}GUIDType"/>
 *         &lt;choice>
 *           &lt;element name="OneRateMeteringDevice">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="BasicChatacteristicts" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}MeteringDeviceBasicCharacteristicsType"/>
 *                     &lt;element name="BaseValue" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}MeteringValueType"/>
 *                     &lt;element name="ReadoutDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                     &lt;element name="ReadingsSource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="ElectricMeteringDevice">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="BasicChatacteristicts" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}MeteringDeviceBasicCharacteristicsType"/>
 *                     &lt;element name="BaseValueT1" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}MeteringValueType"/>
 *                     &lt;element name="BaseValueT2" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}MeteringValueType" minOccurs="0"/>
 *                     &lt;element name="BaseValueT3" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}MeteringValueType" minOccurs="0"/>
 *                     &lt;element name="ReadoutDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                     &lt;element name="ReadingsSource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "exportMeteringDeviceDataResultType", propOrder = {
    "meteringDeviceRootGUID",
    "statusRootDoc",
    "meteringDeviceVersionGUID",
    "versionNumber",
    "statusVersion",
    "updateDateTime",
    "municipalResource",
    "oneRateMeteringDevice",
    "electricMeteringDevice"
})
public class ExportMeteringDeviceDataResultType {

    @XmlElement(name = "MeteringDeviceRootGUID", required = true)
    protected String meteringDeviceRootGUID;
    @XmlElement(name = "StatusRootDoc", required = true)
    protected String statusRootDoc;
    @XmlElement(name = "MeteringDeviceVersionGUID", required = true)
    protected String meteringDeviceVersionGUID;
    @XmlElement(name = "VersionNumber", required = true)
    protected BigInteger versionNumber;
    @XmlElement(name = "StatusVersion", required = true)
    protected String statusVersion;
    @XmlElement(name = "UpdateDateTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar updateDateTime;
    @XmlElement(name = "MunicipalResource", required = true)
    protected String municipalResource;
    @XmlElement(name = "OneRateMeteringDevice")
    protected ExportMeteringDeviceDataResultType.OneRateMeteringDevice oneRateMeteringDevice;
    @XmlElement(name = "ElectricMeteringDevice")
    protected ExportMeteringDeviceDataResultType.ElectricMeteringDevice electricMeteringDevice;

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
     * Gets the value of the statusRootDoc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusRootDoc() {
        return statusRootDoc;
    }

    /**
     * Sets the value of the statusRootDoc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusRootDoc(String value) {
        this.statusRootDoc = value;
    }

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
     * Gets the value of the versionNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getVersionNumber() {
        return versionNumber;
    }

    /**
     * Sets the value of the versionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setVersionNumber(BigInteger value) {
        this.versionNumber = value;
    }

    /**
     * Gets the value of the statusVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusVersion() {
        return statusVersion;
    }

    /**
     * Sets the value of the statusVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusVersion(String value) {
        this.statusVersion = value;
    }

    /**
     * Gets the value of the updateDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getUpdateDateTime() {
        return updateDateTime;
    }

    /**
     * Sets the value of the updateDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setUpdateDateTime(XMLGregorianCalendar value) {
        this.updateDateTime = value;
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
     * Gets the value of the oneRateMeteringDevice property.
     * 
     * @return
     *     possible object is
     *     {@link ExportMeteringDeviceDataResultType.OneRateMeteringDevice }
     *     
     */
    public ExportMeteringDeviceDataResultType.OneRateMeteringDevice getOneRateMeteringDevice() {
        return oneRateMeteringDevice;
    }

    /**
     * Sets the value of the oneRateMeteringDevice property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExportMeteringDeviceDataResultType.OneRateMeteringDevice }
     *     
     */
    public void setOneRateMeteringDevice(ExportMeteringDeviceDataResultType.OneRateMeteringDevice value) {
        this.oneRateMeteringDevice = value;
    }

    /**
     * Gets the value of the electricMeteringDevice property.
     * 
     * @return
     *     possible object is
     *     {@link ExportMeteringDeviceDataResultType.ElectricMeteringDevice }
     *     
     */
    public ExportMeteringDeviceDataResultType.ElectricMeteringDevice getElectricMeteringDevice() {
        return electricMeteringDevice;
    }

    /**
     * Sets the value of the electricMeteringDevice property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExportMeteringDeviceDataResultType.ElectricMeteringDevice }
     *     
     */
    public void setElectricMeteringDevice(ExportMeteringDeviceDataResultType.ElectricMeteringDevice value) {
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
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="BasicChatacteristicts" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}MeteringDeviceBasicCharacteristicsType"/>
     *         &lt;element name="BaseValueT1" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}MeteringValueType"/>
     *         &lt;element name="BaseValueT2" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}MeteringValueType" minOccurs="0"/>
     *         &lt;element name="BaseValueT3" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}MeteringValueType" minOccurs="0"/>
     *         &lt;element name="ReadoutDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *         &lt;element name="ReadingsSource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "basicChatacteristicts",
        "baseValueT1",
        "baseValueT2",
        "baseValueT3",
        "readoutDate",
        "readingsSource"
    })
    public static class ElectricMeteringDevice {

        @XmlElement(name = "BasicChatacteristicts", required = true)
        protected MeteringDeviceBasicCharacteristicsType basicChatacteristicts;
        @XmlElement(name = "BaseValueT1", required = true)
        protected BigDecimal baseValueT1;
        @XmlElement(name = "BaseValueT2")
        protected BigDecimal baseValueT2;
        @XmlElement(name = "BaseValueT3")
        protected BigDecimal baseValueT3;
        @XmlElement(name = "ReadoutDate", required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar readoutDate;
        @XmlElement(name = "ReadingsSource")
        protected String readingsSource;

        /**
         * Gets the value of the basicChatacteristicts property.
         * 
         * @return
         *     possible object is
         *     {@link MeteringDeviceBasicCharacteristicsType }
         *     
         */
        public MeteringDeviceBasicCharacteristicsType getBasicChatacteristicts() {
            return basicChatacteristicts;
        }

        /**
         * Sets the value of the basicChatacteristicts property.
         * 
         * @param value
         *     allowed object is
         *     {@link MeteringDeviceBasicCharacteristicsType }
         *     
         */
        public void setBasicChatacteristicts(MeteringDeviceBasicCharacteristicsType value) {
            this.basicChatacteristicts = value;
        }

        /**
         * Gets the value of the baseValueT1 property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getBaseValueT1() {
            return baseValueT1;
        }

        /**
         * Sets the value of the baseValueT1 property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setBaseValueT1(BigDecimal value) {
            this.baseValueT1 = value;
        }

        /**
         * Gets the value of the baseValueT2 property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getBaseValueT2() {
            return baseValueT2;
        }

        /**
         * Sets the value of the baseValueT2 property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setBaseValueT2(BigDecimal value) {
            this.baseValueT2 = value;
        }

        /**
         * Gets the value of the baseValueT3 property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getBaseValueT3() {
            return baseValueT3;
        }

        /**
         * Sets the value of the baseValueT3 property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setBaseValueT3(BigDecimal value) {
            this.baseValueT3 = value;
        }

        /**
         * Gets the value of the readoutDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getReadoutDate() {
            return readoutDate;
        }

        /**
         * Sets the value of the readoutDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setReadoutDate(XMLGregorianCalendar value) {
            this.readoutDate = value;
        }

        /**
         * Gets the value of the readingsSource property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getReadingsSource() {
            return readingsSource;
        }

        /**
         * Sets the value of the readingsSource property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setReadingsSource(String value) {
            this.readingsSource = value;
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
     *         &lt;element name="BasicChatacteristicts" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}MeteringDeviceBasicCharacteristicsType"/>
     *         &lt;element name="BaseValue" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}MeteringValueType"/>
     *         &lt;element name="ReadoutDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *         &lt;element name="ReadingsSource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "basicChatacteristicts",
        "baseValue",
        "readoutDate",
        "readingsSource"
    })
    public static class OneRateMeteringDevice {

        @XmlElement(name = "BasicChatacteristicts", required = true)
        protected MeteringDeviceBasicCharacteristicsType basicChatacteristicts;
        @XmlElement(name = "BaseValue", required = true)
        protected BigDecimal baseValue;
        @XmlElement(name = "ReadoutDate", required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar readoutDate;
        @XmlElement(name = "ReadingsSource")
        protected String readingsSource;

        /**
         * Gets the value of the basicChatacteristicts property.
         * 
         * @return
         *     possible object is
         *     {@link MeteringDeviceBasicCharacteristicsType }
         *     
         */
        public MeteringDeviceBasicCharacteristicsType getBasicChatacteristicts() {
            return basicChatacteristicts;
        }

        /**
         * Sets the value of the basicChatacteristicts property.
         * 
         * @param value
         *     allowed object is
         *     {@link MeteringDeviceBasicCharacteristicsType }
         *     
         */
        public void setBasicChatacteristicts(MeteringDeviceBasicCharacteristicsType value) {
            this.basicChatacteristicts = value;
        }

        /**
         * Gets the value of the baseValue property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getBaseValue() {
            return baseValue;
        }

        /**
         * Sets the value of the baseValue property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setBaseValue(BigDecimal value) {
            this.baseValue = value;
        }

        /**
         * Gets the value of the readoutDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getReadoutDate() {
            return readoutDate;
        }

        /**
         * Sets the value of the readoutDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setReadoutDate(XMLGregorianCalendar value) {
            this.readoutDate = value;
        }

        /**
         * Gets the value of the readingsSource property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getReadingsSource() {
            return readingsSource;
        }

        /**
         * Sets the value of the readingsSource property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setReadingsSource(String value) {
            this.readingsSource = value;
        }

    }

}

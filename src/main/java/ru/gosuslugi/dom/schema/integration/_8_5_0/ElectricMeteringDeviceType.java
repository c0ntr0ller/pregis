
package ru.gosuslugi.dom.schema.integration._8_5_0;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Прибор учета электрической энергии.
 * 
 * <p>Java class for ElectricMeteringDeviceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ElectricMeteringDeviceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BasicChatacteristicts" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}MeteringDeviceBasicCharacteristicsType"/>
 *         &lt;element name="BaseValueT1" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}MeteringValueType"/>
 *         &lt;element name="BaseValueT2" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}MeteringValueType" minOccurs="0"/>
 *         &lt;element name="BaseValueT3" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}MeteringValueType" minOccurs="0"/>
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
@XmlType(name = "ElectricMeteringDeviceType", propOrder = {
    "basicChatacteristicts",
    "baseValueT1",
    "baseValueT2",
    "baseValueT3",
    "readoutDate",
    "readingsSource"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_5_0_2.house_management.ImportMeteringDeviceDataRequest.MeteringDevice.DeviceDataToCreate.ElectricMeteringDevice.class
})
public class ElectricMeteringDeviceType {

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

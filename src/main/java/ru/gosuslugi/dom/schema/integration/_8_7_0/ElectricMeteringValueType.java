
package ru.gosuslugi.dom.schema.integration._8_7_0;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Показания ПУ учета электрической энергии.
 * 
 * <p>Java class for ElectricMeteringValueType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ElectricMeteringValueType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MeteringValueT1" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MeteringValueType"/>
 *         &lt;element name="MeteringValueT2" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MeteringValueType" minOccurs="0"/>
 *         &lt;element name="MeteringValueT3" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}MeteringValueType" minOccurs="0"/>
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
@XmlType(name = "ElectricMeteringValueType", propOrder = {
    "meteringValueT1",
    "meteringValueT2",
    "meteringValueT3",
    "readoutDate",
    "readingsSource"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_7_0.ElectricMeteringValueKindType.CurrentValue.class,
    ru.gosuslugi.dom.schema.integration._8_7_0.ElectricMeteringValueKindType.ControlValue.class,
    ru.gosuslugi.dom.schema.integration._8_7_0_4.device_metering.ImportMeteringDeviceValuesRequest.MeteringDevicesValues.ElectricDeviceValue.CurrentValue.class,
    ru.gosuslugi.dom.schema.integration._8_7_0_4.device_metering.ImportMeteringDeviceValuesRequest.MeteringDevicesValues.ElectricDeviceValue.ControlValue.class
})
public class ElectricMeteringValueType {

    @XmlElement(name = "MeteringValueT1", required = true)
    protected BigDecimal meteringValueT1;
    @XmlElement(name = "MeteringValueT2")
    protected BigDecimal meteringValueT2;
    @XmlElement(name = "MeteringValueT3")
    protected BigDecimal meteringValueT3;
    @XmlElement(name = "ReadoutDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar readoutDate;
    @XmlElement(name = "ReadingsSource")
    protected String readingsSource;

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

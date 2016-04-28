
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
 * Показания ПУ (газ, вода, тепловая энергия).
 * 
 * <p>Java class for OneRateMeteringValueType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OneRateMeteringValueType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MeteringValue" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}MeteringValueType"/>
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
@XmlType(name = "OneRateMeteringValueType", propOrder = {
    "meteringValue",
    "readoutDate",
    "readingsSource"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_7_0.OneRateMeteringValueKindType.CurrentValue.class,
    ru.gosuslugi.dom.schema.integration._8_7_0.OneRateMeteringValueKindType.ControlValue.class,
    ru.gosuslugi.dom.schema.integration._8_7_0_6.device_metering.ImportMeteringDeviceValuesRequest.MeteringDevicesValues.OneRateDeviceValue.CurrentValue.class,
    ru.gosuslugi.dom.schema.integration._8_7_0_6.device_metering.ImportMeteringDeviceValuesRequest.MeteringDevicesValues.OneRateDeviceValue.ControlValue.class
})
public class OneRateMeteringValueType {

    @XmlElement(name = "MeteringValue", required = true)
    protected BigDecimal meteringValue;
    @XmlElement(name = "ReadoutDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar readoutDate;
    @XmlElement(name = "ReadingsSource")
    protected String readingsSource;

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

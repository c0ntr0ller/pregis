
package ru.gosuslugi.dom.schema.integration.services.disclosure;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TimesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TimesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HourFrom">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;minInclusive value="0"/>
 *               &lt;maxInclusive value="23"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="MinuteFrom">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;minInclusive value="0"/>
 *               &lt;maxInclusive value="59"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="HourTo">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;minInclusive value="0"/>
 *               &lt;maxInclusive value="23"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="MinuteTo">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;minInclusive value="0"/>
 *               &lt;maxInclusive value="59"/>
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
@XmlType(name = "TimesType", propOrder = {
    "hourFrom",
    "minuteFrom",
    "hourTo",
    "minuteTo"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration.services.disclosure.Section11 .GeneralInformation.Timetable.DayTimetable.WorkDay.ReceptionOfCitizens.class
})
public class TimesType {

    @XmlElement(name = "HourFrom", required = true)
    protected BigDecimal hourFrom;
    @XmlElement(name = "MinuteFrom", required = true)
    protected BigDecimal minuteFrom;
    @XmlElement(name = "HourTo", required = true)
    protected BigDecimal hourTo;
    @XmlElement(name = "MinuteTo", required = true)
    protected BigDecimal minuteTo;

    /**
     * Gets the value of the hourFrom property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getHourFrom() {
        return hourFrom;
    }

    /**
     * Sets the value of the hourFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setHourFrom(BigDecimal value) {
        this.hourFrom = value;
    }

    /**
     * Gets the value of the minuteFrom property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMinuteFrom() {
        return minuteFrom;
    }

    /**
     * Sets the value of the minuteFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMinuteFrom(BigDecimal value) {
        this.minuteFrom = value;
    }

    /**
     * Gets the value of the hourTo property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getHourTo() {
        return hourTo;
    }

    /**
     * Sets the value of the hourTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setHourTo(BigDecimal value) {
        this.hourTo = value;
    }

    /**
     * Gets the value of the minuteTo property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMinuteTo() {
        return minuteTo;
    }

    /**
     * Sets the value of the minuteTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMinuteTo(BigDecimal value) {
        this.minuteTo = value;
    }

}


package ru.gosuslugi.dom.schema.integration._8_6_0;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Жилищная услуга
 * 
 * <p>Java class for HousingServicesAttributesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HousingServicesAttributesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HSGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}GUIDType"/>
 *         &lt;element name="HSType" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}GUIDType"/>
 *         &lt;element name="StartDateFrom" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="StartDateTo" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="UpdateDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="Rate" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}VolumeType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HousingServicesAttributesType", propOrder = {
    "hsguid",
    "hsType",
    "startDateFrom",
    "startDateTo",
    "updateDateTime",
    "rate"
})
public class HousingServicesAttributesType {

    @XmlElement(name = "HSGUID", required = true)
    protected String hsguid;
    @XmlElement(name = "HSType", required = true)
    protected String hsType;
    @XmlElement(name = "StartDateFrom", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startDateFrom;
    @XmlElement(name = "StartDateTo", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startDateTo;
    @XmlElement(name = "UpdateDateTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar updateDateTime;
    @XmlElement(name = "Rate", required = true)
    protected BigDecimal rate;

    /**
     * Gets the value of the hsguid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHSGUID() {
        return hsguid;
    }

    /**
     * Sets the value of the hsguid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHSGUID(String value) {
        this.hsguid = value;
    }

    /**
     * Gets the value of the hsType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHSType() {
        return hsType;
    }

    /**
     * Sets the value of the hsType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHSType(String value) {
        this.hsType = value;
    }

    /**
     * Gets the value of the startDateFrom property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDateFrom() {
        return startDateFrom;
    }

    /**
     * Sets the value of the startDateFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDateFrom(XMLGregorianCalendar value) {
        this.startDateFrom = value;
    }

    /**
     * Gets the value of the startDateTo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDateTo() {
        return startDateTo;
    }

    /**
     * Sets the value of the startDateTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDateTo(XMLGregorianCalendar value) {
        this.startDateTo = value;
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
     * Gets the value of the rate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * Sets the value of the rate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRate(BigDecimal value) {
        this.rate = value;
    }

}

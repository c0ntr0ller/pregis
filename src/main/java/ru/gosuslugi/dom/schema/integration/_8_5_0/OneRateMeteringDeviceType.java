
package ru.gosuslugi.dom.schema.integration._8_5_0;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Прибор учета однотарифный. 
 * 
 * <p>Java class for OneRateMeteringDeviceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OneRateMeteringDeviceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BasicChatacteristicts" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}MeteringDeviceBasicCharacteristicsType"/>
 *         &lt;element name="BaseValue" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}MeteringValueType"/>
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
@XmlType(name = "OneRateMeteringDeviceType", propOrder = {
    "basicChatacteristicts",
    "baseValue",
    "readoutDate",
    "readingsSource"
})
public class OneRateMeteringDeviceType {

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

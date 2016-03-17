
package ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DateDetailsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DateDetailsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PeriodMetering" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="StartDate">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
 *                         &lt;maxInclusive value="30"/>
 *                         &lt;minInclusive value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;choice>
 *                     &lt;element name="EndDate">
 *                       &lt;simpleType>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
 *                           &lt;maxInclusive value="31"/>
 *                           &lt;minInclusive value="2"/>
 *                         &lt;/restriction>
 *                       &lt;/simpleType>
 *                     &lt;/element>
 *                     &lt;element name="LastDay" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                   &lt;/choice>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="PaymentDate" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;choice>
 *                     &lt;element name="StartDate">
 *                       &lt;simpleType>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
 *                           &lt;maxInclusive value="30"/>
 *                           &lt;minInclusive value="1"/>
 *                         &lt;/restriction>
 *                       &lt;/simpleType>
 *                     &lt;/element>
 *                     &lt;element name="LastDay" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                   &lt;/choice>
 *                   &lt;choice>
 *                     &lt;element name="CurrentMounth" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                     &lt;element name="NextMounth" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                   &lt;/choice>
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
@XmlType(name = "DateDetailsType", propOrder = {
    "periodMetering",
    "paymentDate"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.CharterType.DateDetails.class
})
public class DateDetailsType {

    @XmlElement(name = "PeriodMetering")
    protected DateDetailsType.PeriodMetering periodMetering;
    @XmlElement(name = "PaymentDate")
    protected DateDetailsType.PaymentDate paymentDate;

    /**
     * Gets the value of the periodMetering property.
     * 
     * @return
     *     possible object is
     *     {@link DateDetailsType.PeriodMetering }
     *     
     */
    public DateDetailsType.PeriodMetering getPeriodMetering() {
        return periodMetering;
    }

    /**
     * Sets the value of the periodMetering property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateDetailsType.PeriodMetering }
     *     
     */
    public void setPeriodMetering(DateDetailsType.PeriodMetering value) {
        this.periodMetering = value;
    }

    /**
     * Gets the value of the paymentDate property.
     * 
     * @return
     *     possible object is
     *     {@link DateDetailsType.PaymentDate }
     *     
     */
    public DateDetailsType.PaymentDate getPaymentDate() {
        return paymentDate;
    }

    /**
     * Sets the value of the paymentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateDetailsType.PaymentDate }
     *     
     */
    public void setPaymentDate(DateDetailsType.PaymentDate value) {
        this.paymentDate = value;
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
     *         &lt;choice>
     *           &lt;element name="StartDate">
     *             &lt;simpleType>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
     *                 &lt;maxInclusive value="30"/>
     *                 &lt;minInclusive value="1"/>
     *               &lt;/restriction>
     *             &lt;/simpleType>
     *           &lt;/element>
     *           &lt;element name="LastDay" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *         &lt;/choice>
     *         &lt;choice>
     *           &lt;element name="CurrentMounth" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *           &lt;element name="NextMounth" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
        "startDate",
        "lastDay",
        "currentMounth",
        "nextMounth"
    })
    public static class PaymentDate {

        @XmlElement(name = "StartDate")
        protected Byte startDate;
        @XmlElement(name = "LastDay")
        protected Boolean lastDay;
        @XmlElement(name = "CurrentMounth")
        protected Boolean currentMounth;
        @XmlElement(name = "NextMounth")
        protected Boolean nextMounth;

        /**
         * Gets the value of the startDate property.
         * 
         * @return
         *     possible object is
         *     {@link Byte }
         *     
         */
        public Byte getStartDate() {
            return startDate;
        }

        /**
         * Sets the value of the startDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link Byte }
         *     
         */
        public void setStartDate(Byte value) {
            this.startDate = value;
        }

        /**
         * Gets the value of the lastDay property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isLastDay() {
            return lastDay;
        }

        /**
         * Sets the value of the lastDay property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setLastDay(Boolean value) {
            this.lastDay = value;
        }

        /**
         * Gets the value of the currentMounth property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isCurrentMounth() {
            return currentMounth;
        }

        /**
         * Sets the value of the currentMounth property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setCurrentMounth(Boolean value) {
            this.currentMounth = value;
        }

        /**
         * Gets the value of the nextMounth property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isNextMounth() {
            return nextMounth;
        }

        /**
         * Sets the value of the nextMounth property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setNextMounth(Boolean value) {
            this.nextMounth = value;
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
     *         &lt;element name="StartDate">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
     *               &lt;maxInclusive value="30"/>
     *               &lt;minInclusive value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;choice>
     *           &lt;element name="EndDate">
     *             &lt;simpleType>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
     *                 &lt;maxInclusive value="31"/>
     *                 &lt;minInclusive value="2"/>
     *               &lt;/restriction>
     *             &lt;/simpleType>
     *           &lt;/element>
     *           &lt;element name="LastDay" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
        "startDate",
        "endDate",
        "lastDay"
    })
    public static class PeriodMetering {

        @XmlElement(name = "StartDate")
        protected byte startDate;
        @XmlElement(name = "EndDate")
        protected Byte endDate;
        @XmlElement(name = "LastDay")
        protected Boolean lastDay;

        /**
         * Gets the value of the startDate property.
         * 
         */
        public byte getStartDate() {
            return startDate;
        }

        /**
         * Sets the value of the startDate property.
         * 
         */
        public void setStartDate(byte value) {
            this.startDate = value;
        }

        /**
         * Gets the value of the endDate property.
         * 
         * @return
         *     possible object is
         *     {@link Byte }
         *     
         */
        public Byte getEndDate() {
            return endDate;
        }

        /**
         * Sets the value of the endDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link Byte }
         *     
         */
        public void setEndDate(Byte value) {
            this.endDate = value;
        }

        /**
         * Gets the value of the lastDay property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isLastDay() {
            return lastDay;
        }

        /**
         * Sets the value of the lastDay property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setLastDay(Boolean value) {
            this.lastDay = value;
        }

    }

}

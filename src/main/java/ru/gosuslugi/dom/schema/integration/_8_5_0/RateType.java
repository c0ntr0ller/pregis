
package ru.gosuslugi.dom.schema.integration._8_5_0;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип тарификации
 * 
 * <p>Java class for RateType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RateType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="SingleRate">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="T1" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}VolumeType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="DoubleRate">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="T1" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}VolumeType"/>
 *                   &lt;element name="T2" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}VolumeType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="TripleRate">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="T1" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}VolumeType"/>
 *                   &lt;element name="T2" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}VolumeType"/>
 *                   &lt;element name="T3" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}VolumeType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
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
@XmlType(name = "RateType", propOrder = {
    "singleRate",
    "doubleRate",
    "tripleRate"
})
public class RateType {

    @XmlElement(name = "SingleRate")
    protected RateType.SingleRate singleRate;
    @XmlElement(name = "DoubleRate")
    protected RateType.DoubleRate doubleRate;
    @XmlElement(name = "TripleRate")
    protected RateType.TripleRate tripleRate;

    /**
     * Gets the value of the singleRate property.
     * 
     * @return
     *     possible object is
     *     {@link RateType.SingleRate }
     *     
     */
    public RateType.SingleRate getSingleRate() {
        return singleRate;
    }

    /**
     * Sets the value of the singleRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link RateType.SingleRate }
     *     
     */
    public void setSingleRate(RateType.SingleRate value) {
        this.singleRate = value;
    }

    /**
     * Gets the value of the doubleRate property.
     * 
     * @return
     *     possible object is
     *     {@link RateType.DoubleRate }
     *     
     */
    public RateType.DoubleRate getDoubleRate() {
        return doubleRate;
    }

    /**
     * Sets the value of the doubleRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link RateType.DoubleRate }
     *     
     */
    public void setDoubleRate(RateType.DoubleRate value) {
        this.doubleRate = value;
    }

    /**
     * Gets the value of the tripleRate property.
     * 
     * @return
     *     possible object is
     *     {@link RateType.TripleRate }
     *     
     */
    public RateType.TripleRate getTripleRate() {
        return tripleRate;
    }

    /**
     * Sets the value of the tripleRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link RateType.TripleRate }
     *     
     */
    public void setTripleRate(RateType.TripleRate value) {
        this.tripleRate = value;
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
     *         &lt;element name="T1" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}VolumeType"/>
     *         &lt;element name="T2" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}VolumeType"/>
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
        "t1",
        "t2"
    })
    public static class DoubleRate {

        @XmlElement(name = "T1", required = true)
        protected BigDecimal t1;
        @XmlElement(name = "T2", required = true)
        protected BigDecimal t2;

        /**
         * Gets the value of the t1 property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getT1() {
            return t1;
        }

        /**
         * Sets the value of the t1 property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setT1(BigDecimal value) {
            this.t1 = value;
        }

        /**
         * Gets the value of the t2 property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getT2() {
            return t2;
        }

        /**
         * Sets the value of the t2 property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setT2(BigDecimal value) {
            this.t2 = value;
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
     *         &lt;element name="T1" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}VolumeType"/>
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
        "t1"
    })
    public static class SingleRate {

        @XmlElement(name = "T1", required = true)
        protected BigDecimal t1;

        /**
         * Gets the value of the t1 property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getT1() {
            return t1;
        }

        /**
         * Sets the value of the t1 property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setT1(BigDecimal value) {
            this.t1 = value;
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
     *         &lt;element name="T1" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}VolumeType"/>
     *         &lt;element name="T2" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}VolumeType"/>
     *         &lt;element name="T3" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}VolumeType"/>
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
        "t1",
        "t2",
        "t3"
    })
    public static class TripleRate {

        @XmlElement(name = "T1", required = true)
        protected BigDecimal t1;
        @XmlElement(name = "T2", required = true)
        protected BigDecimal t2;
        @XmlElement(name = "T3", required = true)
        protected BigDecimal t3;

        /**
         * Gets the value of the t1 property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getT1() {
            return t1;
        }

        /**
         * Sets the value of the t1 property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setT1(BigDecimal value) {
            this.t1 = value;
        }

        /**
         * Gets the value of the t2 property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getT2() {
            return t2;
        }

        /**
         * Sets the value of the t2 property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setT2(BigDecimal value) {
            this.t2 = value;
        }

        /**
         * Gets the value of the t3 property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getT3() {
            return t3;
        }

        /**
         * Sets the value of the t3 property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setT3(BigDecimal value) {
            this.t3 = value;
        }

    }

}

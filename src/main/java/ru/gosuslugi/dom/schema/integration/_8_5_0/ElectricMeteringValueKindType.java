
package ru.gosuslugi.dom.schema.integration._8_5_0;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Виды показаний ПУ электрической энергии.
 * 
 * <p>Java class for ElectricMeteringValueKindType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ElectricMeteringValueKindType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CurrentValue" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}ElectricMeteringValueType">
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ControlValue" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}ElectricMeteringValueType">
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="VerificationValue" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="StartVerificationValue" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}ElectricMeteringValueType"/>
 *                   &lt;element name="EndVerificationValue" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}ElectricMeteringValueType"/>
 *                   &lt;choice>
 *                     &lt;element name="PlannedVerification" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                     &lt;element name="VerificationReason" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}nsiRef"/>
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
@XmlType(name = "ElectricMeteringValueKindType", propOrder = {
    "currentValue",
    "controlValue",
    "verificationValue"
})
public class ElectricMeteringValueKindType {

    @XmlElement(name = "CurrentValue")
    protected List<ElectricMeteringValueKindType.CurrentValue> currentValue;
    @XmlElement(name = "ControlValue")
    protected List<ElectricMeteringValueKindType.ControlValue> controlValue;
    @XmlElement(name = "VerificationValue")
    protected List<ElectricMeteringValueKindType.VerificationValue> verificationValue;

    /**
     * Gets the value of the currentValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the currentValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCurrentValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ElectricMeteringValueKindType.CurrentValue }
     * 
     * 
     */
    public List<ElectricMeteringValueKindType.CurrentValue> getCurrentValue() {
        if (currentValue == null) {
            currentValue = new ArrayList<ElectricMeteringValueKindType.CurrentValue>();
        }
        return this.currentValue;
    }

    /**
     * Gets the value of the controlValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the controlValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getControlValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ElectricMeteringValueKindType.ControlValue }
     * 
     * 
     */
    public List<ElectricMeteringValueKindType.ControlValue> getControlValue() {
        if (controlValue == null) {
            controlValue = new ArrayList<ElectricMeteringValueKindType.ControlValue>();
        }
        return this.controlValue;
    }

    /**
     * Gets the value of the verificationValue property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the verificationValue property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVerificationValue().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ElectricMeteringValueKindType.VerificationValue }
     * 
     * 
     */
    public List<ElectricMeteringValueKindType.VerificationValue> getVerificationValue() {
        if (verificationValue == null) {
            verificationValue = new ArrayList<ElectricMeteringValueKindType.VerificationValue>();
        }
        return this.verificationValue;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}ElectricMeteringValueType">
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class ControlValue
        extends ElectricMeteringValueType
    {


    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}ElectricMeteringValueType">
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class CurrentValue
        extends ElectricMeteringValueType
    {


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
     *         &lt;element name="StartVerificationValue" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}ElectricMeteringValueType"/>
     *         &lt;element name="EndVerificationValue" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}ElectricMeteringValueType"/>
     *         &lt;choice>
     *           &lt;element name="PlannedVerification" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *           &lt;element name="VerificationReason" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}nsiRef"/>
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
        "startVerificationValue",
        "endVerificationValue",
        "plannedVerification",
        "verificationReason"
    })
    public static class VerificationValue {

        @XmlElement(name = "StartVerificationValue", required = true)
        protected ElectricMeteringValueType startVerificationValue;
        @XmlElement(name = "EndVerificationValue", required = true)
        protected ElectricMeteringValueType endVerificationValue;
        @XmlElement(name = "PlannedVerification")
        protected Boolean plannedVerification;
        @XmlElement(name = "VerificationReason")
        protected NsiRef verificationReason;

        /**
         * Gets the value of the startVerificationValue property.
         * 
         * @return
         *     possible object is
         *     {@link ElectricMeteringValueType }
         *     
         */
        public ElectricMeteringValueType getStartVerificationValue() {
            return startVerificationValue;
        }

        /**
         * Sets the value of the startVerificationValue property.
         * 
         * @param value
         *     allowed object is
         *     {@link ElectricMeteringValueType }
         *     
         */
        public void setStartVerificationValue(ElectricMeteringValueType value) {
            this.startVerificationValue = value;
        }

        /**
         * Gets the value of the endVerificationValue property.
         * 
         * @return
         *     possible object is
         *     {@link ElectricMeteringValueType }
         *     
         */
        public ElectricMeteringValueType getEndVerificationValue() {
            return endVerificationValue;
        }

        /**
         * Sets the value of the endVerificationValue property.
         * 
         * @param value
         *     allowed object is
         *     {@link ElectricMeteringValueType }
         *     
         */
        public void setEndVerificationValue(ElectricMeteringValueType value) {
            this.endVerificationValue = value;
        }

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
         * Gets the value of the verificationReason property.
         * 
         * @return
         *     possible object is
         *     {@link NsiRef }
         *     
         */
        public NsiRef getVerificationReason() {
            return verificationReason;
        }

        /**
         * Sets the value of the verificationReason property.
         * 
         * @param value
         *     allowed object is
         *     {@link NsiRef }
         *     
         */
        public void setVerificationReason(NsiRef value) {
            this.verificationReason = value;
        }

    }

}


package ru.gosuslugi.dom.schema.integration._8_5_0_2.bills;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_5_0.ErrorMessageType;


/**
 * Платежный документ
 * 
 * <p>Java class for exportPaymentDocumentResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="exportPaymentDocumentResultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}ErrorMessage"/>
 *         &lt;element name="PaymentDocument">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/bills/}PaymentDocumentType">
 *                 &lt;sequence>
 *                   &lt;element name="PaymentDocumentGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}GUIDType"/>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}PaymentDocumentNumber"/>
 *                 &lt;/sequence>
 *               &lt;/extension>
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
@XmlType(name = "exportPaymentDocumentResultType", propOrder = {
    "errorMessage",
    "paymentDocument"
})
public class ExportPaymentDocumentResultType {

    @XmlElement(name = "ErrorMessage", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/")
    protected ErrorMessageType errorMessage;
    @XmlElement(name = "PaymentDocument")
    protected ExportPaymentDocumentResultType.PaymentDocument paymentDocument;

    /**
     * Gets the value of the errorMessage property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorMessageType }
     *     
     */
    public ErrorMessageType getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the value of the errorMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorMessageType }
     *     
     */
    public void setErrorMessage(ErrorMessageType value) {
        this.errorMessage = value;
    }

    /**
     * Gets the value of the paymentDocument property.
     * 
     * @return
     *     possible object is
     *     {@link ExportPaymentDocumentResultType.PaymentDocument }
     *     
     */
    public ExportPaymentDocumentResultType.PaymentDocument getPaymentDocument() {
        return paymentDocument;
    }

    /**
     * Sets the value of the paymentDocument property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExportPaymentDocumentResultType.PaymentDocument }
     *     
     */
    public void setPaymentDocument(ExportPaymentDocumentResultType.PaymentDocument value) {
        this.paymentDocument = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/bills/}PaymentDocumentType">
     *       &lt;sequence>
     *         &lt;element name="PaymentDocumentGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}GUIDType"/>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}PaymentDocumentNumber"/>
     *       &lt;/sequence>
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "paymentDocumentGuid",
        "paymentDocumentNumber"
    })
    public static class PaymentDocument
        extends PaymentDocumentType
    {

        @XmlElement(name = "PaymentDocumentGuid", required = true)
        protected String paymentDocumentGuid;
        @XmlElement(name = "PaymentDocumentNumber", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.2/", required = true)
        protected String paymentDocumentNumber;

        /**
         * Gets the value of the paymentDocumentGuid property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPaymentDocumentGuid() {
            return paymentDocumentGuid;
        }

        /**
         * Sets the value of the paymentDocumentGuid property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPaymentDocumentGuid(String value) {
            this.paymentDocumentGuid = value;
        }

        /**
         * Gets the value of the paymentDocumentNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPaymentDocumentNumber() {
            return paymentDocumentNumber;
        }

        /**
         * Sets the value of the paymentDocumentNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPaymentDocumentNumber(String value) {
            this.paymentDocumentNumber = value;
        }

    }

}

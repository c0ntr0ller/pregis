
package ru.gosuslugi.dom.schema.integration.services.bills;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration.base.ErrorMessageType;


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
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}ErrorMessage"/>
 *         &lt;element name="PaymentDocument">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/bills/}PaymentDocumentType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}PaymentDocumentID"/>
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

    @XmlElement(name = "ErrorMessage", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/")
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
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/bills/}PaymentDocumentType">
     *       &lt;sequence>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}PaymentDocumentID"/>
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
        "paymentDocumentID"
    })
    public static class PaymentDocument
        extends PaymentDocumentType
    {

        @XmlElement(name = "PaymentDocumentID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.7/", required = true)
        protected String paymentDocumentID;

        /**
         * Gets the value of the paymentDocumentID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPaymentDocumentID() {
            return paymentDocumentID;
        }

        /**
         * Sets the value of the paymentDocumentID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPaymentDocumentID(String value) {
            this.paymentDocumentID = value;
        }

    }

}

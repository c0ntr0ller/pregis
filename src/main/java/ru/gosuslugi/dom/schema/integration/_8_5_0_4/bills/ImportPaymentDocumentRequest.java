
package ru.gosuslugi.dom.schema.integration._8_5_0_4.bills;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_5_0.BaseType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}BaseType">
 *       &lt;sequence>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}Month"/>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}Year"/>
 *         &lt;element name="PaymentDocument" maxOccurs="1000">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/bills/}PaymentDocumentType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}TransportGUID"/>
 *                   &lt;element name="PaymentDocumentGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}GUIDType" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "month",
    "year",
    "paymentDocument"
})
@XmlRootElement(name = "importPaymentDocumentRequest")
public class ImportPaymentDocumentRequest
    extends BaseType
{

    @XmlElement(name = "Month", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.4/")
    protected int month;
    @XmlElement(name = "Year", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.4/")
    protected short year;
    @XmlElement(name = "PaymentDocument", required = true)
    protected List<ImportPaymentDocumentRequest.PaymentDocument> paymentDocument;

    /**
     * Gets the value of the month property.
     * 
     */
    public int getMonth() {
        return month;
    }

    /**
     * Sets the value of the month property.
     * 
     */
    public void setMonth(int value) {
        this.month = value;
    }

    /**
     * Gets the value of the year property.
     * 
     */
    public short getYear() {
        return year;
    }

    /**
     * Sets the value of the year property.
     * 
     */
    public void setYear(short value) {
        this.year = value;
    }

    /**
     * Gets the value of the paymentDocument property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paymentDocument property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPaymentDocument().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ImportPaymentDocumentRequest.PaymentDocument }
     * 
     * 
     */
    public List<ImportPaymentDocumentRequest.PaymentDocument> getPaymentDocument() {
        if (paymentDocument == null) {
            paymentDocument = new ArrayList<ImportPaymentDocumentRequest.PaymentDocument>();
        }
        return this.paymentDocument;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/bills/}PaymentDocumentType">
     *       &lt;sequence>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}TransportGUID"/>
     *         &lt;element name="PaymentDocumentGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}GUIDType" minOccurs="0"/>
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
        "transportGUID",
        "paymentDocumentGuid"
    })
    public static class PaymentDocument
        extends PaymentDocumentType
    {

        @XmlElement(name = "TransportGUID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.4/", required = true)
        protected String transportGUID;
        @XmlElement(name = "PaymentDocumentGuid")
        protected String paymentDocumentGuid;

        /**
         * Gets the value of the transportGUID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTransportGUID() {
            return transportGUID;
        }

        /**
         * Sets the value of the transportGUID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTransportGUID(String value) {
            this.transportGUID = value;
        }

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

    }

}
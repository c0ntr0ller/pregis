
package ru.gosuslugi.dom.schema.integration.base;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Запрос на квитирование
 * 
 * <p>Java class for AcknowledgmentRequestInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AcknowledgmentRequestInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}OrderID"/>
 *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}PaymentDocumentID"/>
 *         &lt;choice>
 *           &lt;element name="HSType" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}GUIDType"/>
 *           &lt;element name="MSType" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}GUIDType"/>
 *           &lt;element name="ASType" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}GUIDType"/>
 *         &lt;/choice>
 *         &lt;element name="Amount">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *               &lt;minInclusive value="1"/>
 *               &lt;totalDigits value="13"/>
 *               &lt;fractionDigits value="2"/>
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
@XmlType(name = "AcknowledgmentRequestInfoType", propOrder = {
    "orderID",
    "paymentDocumentID",
    "hsType",
    "msType",
    "asType",
    "amount"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration.services.bills.ImportAcknowledgmentRequest.AcknowledgmentRequestInfo.class
})
public class AcknowledgmentRequestInfoType {

    @XmlElement(name = "OrderID", required = true)
    protected String orderID;
    @XmlElement(name = "PaymentDocumentID", required = true)
    protected String paymentDocumentID;
    @XmlElement(name = "HSType")
    protected String hsType;
    @XmlElement(name = "MSType")
    protected String msType;
    @XmlElement(name = "ASType")
    protected String asType;
    @XmlElement(name = "Amount", required = true)
    protected BigDecimal amount;

    /**
     * Уникальный идентификатор извещения
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderID() {
        return orderID;
    }

    /**
     * Sets the value of the orderID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderID(String value) {
        this.orderID = value;
    }

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
     * Gets the value of the msType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMSType() {
        return msType;
    }

    /**
     * Sets the value of the msType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMSType(String value) {
        this.msType = value;
    }

    /**
     * Gets the value of the asType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASType() {
        return asType;
    }

    /**
     * Sets the value of the asType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASType(String value) {
        this.asType = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAmount(BigDecimal value) {
        this.amount = value;
    }

}

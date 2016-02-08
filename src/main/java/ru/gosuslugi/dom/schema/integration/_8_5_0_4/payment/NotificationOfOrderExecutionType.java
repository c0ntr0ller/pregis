
package ru.gosuslugi.dom.schema.integration._8_5_0_4.payment;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Извещение о принятии к исполнению распоряжения
 * 
 * <p>Java class for NotificationOfOrderExecutionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NotificationOfOrderExecutionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SupplierInfo">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="SupplierID">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="25"/>
 *                         &lt;minLength value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="SupplierName">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="160"/>
 *                         &lt;minLength value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="RecipientInfo">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="RecipientINN" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}INNType"/>
 *                   &lt;element name="RecipientKPP" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}KPPType" minOccurs="0"/>
 *                   &lt;element name="BankName">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="160"/>
 *                         &lt;minLength value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="RecipientBIK" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}BIKType"/>
 *                   &lt;element name="CorrespondentBankAccount" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}AccountType"/>
 *                   &lt;element name="RecipientAccount" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}AccountType"/>
 *                   &lt;element name="RecipientName">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="160"/>
 *                         &lt;minLength value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="OrderInfo">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/payment/}OrderID"/>
 *                   &lt;element name="PaymentDocumentGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}GUIDType"/>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}AccountNumber" minOccurs="0"/>
 *                   &lt;element name="OrderNum">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="9"/>
 *                         &lt;minLength value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="OrderDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                   &lt;element name="Amount" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}MoneyPositiveType"/>
 *                   &lt;element name="PaymentPurpose">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="210"/>
 *                         &lt;minLength value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="Comment" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;maxLength value="210"/>
 *                         &lt;minLength value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
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
@XmlType(name = "NotificationOfOrderExecutionType", propOrder = {
    "supplierInfo",
    "recipientInfo",
    "orderInfo"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_5_0_4.payment.ExportNotificationsOfOrderExecutionResultType.NotificationOfOrderExecutionWithStatus.class,
    ru.gosuslugi.dom.schema.integration._8_5_0_4.payment.ImportNotificationsOfOrderExecutionRequest.NotificationOfOrderExecutionType.class
})
public class NotificationOfOrderExecutionType {

    @XmlElement(name = "SupplierInfo", required = true)
    protected NotificationOfOrderExecutionType.SupplierInfo supplierInfo;
    @XmlElement(name = "RecipientInfo", required = true)
    protected NotificationOfOrderExecutionType.RecipientInfo recipientInfo;
    @XmlElement(name = "OrderInfo", required = true)
    protected NotificationOfOrderExecutionType.OrderInfo orderInfo;

    /**
     * Gets the value of the supplierInfo property.
     * 
     * @return
     *     possible object is
     *     {@link NotificationOfOrderExecutionType.SupplierInfo }
     *     
     */
    public NotificationOfOrderExecutionType.SupplierInfo getSupplierInfo() {
        return supplierInfo;
    }

    /**
     * Sets the value of the supplierInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificationOfOrderExecutionType.SupplierInfo }
     *     
     */
    public void setSupplierInfo(NotificationOfOrderExecutionType.SupplierInfo value) {
        this.supplierInfo = value;
    }

    /**
     * Gets the value of the recipientInfo property.
     * 
     * @return
     *     possible object is
     *     {@link NotificationOfOrderExecutionType.RecipientInfo }
     *     
     */
    public NotificationOfOrderExecutionType.RecipientInfo getRecipientInfo() {
        return recipientInfo;
    }

    /**
     * Sets the value of the recipientInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificationOfOrderExecutionType.RecipientInfo }
     *     
     */
    public void setRecipientInfo(NotificationOfOrderExecutionType.RecipientInfo value) {
        this.recipientInfo = value;
    }

    /**
     * Gets the value of the orderInfo property.
     * 
     * @return
     *     possible object is
     *     {@link NotificationOfOrderExecutionType.OrderInfo }
     *     
     */
    public NotificationOfOrderExecutionType.OrderInfo getOrderInfo() {
        return orderInfo;
    }

    /**
     * Sets the value of the orderInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificationOfOrderExecutionType.OrderInfo }
     *     
     */
    public void setOrderInfo(NotificationOfOrderExecutionType.OrderInfo value) {
        this.orderInfo = value;
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
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/payment/}OrderID"/>
     *         &lt;element name="PaymentDocumentGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}GUIDType"/>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}AccountNumber" minOccurs="0"/>
     *         &lt;element name="OrderNum">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="9"/>
     *               &lt;minLength value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="OrderDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *         &lt;element name="Amount" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}MoneyPositiveType"/>
     *         &lt;element name="PaymentPurpose">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="210"/>
     *               &lt;minLength value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="Comment" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="210"/>
     *               &lt;minLength value="1"/>
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
    @XmlType(name = "", propOrder = {
        "orderID",
        "paymentDocumentGuid",
        "accountNumber",
        "orderNum",
        "orderDate",
        "amount",
        "paymentPurpose",
        "comment"
    })
    public static class OrderInfo {

        @XmlElement(name = "OrderID", required = true)
        protected String orderID;
        @XmlElement(name = "PaymentDocumentGuid", required = true)
        protected String paymentDocumentGuid;
        @XmlElement(name = "AccountNumber", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.4/")
        protected String accountNumber;
        @XmlElement(name = "OrderNum", required = true)
        protected String orderNum;
        @XmlElement(name = "OrderDate", required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar orderDate;
        @XmlElement(name = "Amount", required = true)
        protected BigDecimal amount;
        @XmlElement(name = "PaymentPurpose", required = true)
        protected String paymentPurpose;
        @XmlElement(name = "Comment")
        protected String comment;

        /**
         * Gets the value of the orderID property.
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
         * Gets the value of the accountNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAccountNumber() {
            return accountNumber;
        }

        /**
         * Sets the value of the accountNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAccountNumber(String value) {
            this.accountNumber = value;
        }

        /**
         * Gets the value of the orderNum property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOrderNum() {
            return orderNum;
        }

        /**
         * Sets the value of the orderNum property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOrderNum(String value) {
            this.orderNum = value;
        }

        /**
         * Gets the value of the orderDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getOrderDate() {
            return orderDate;
        }

        /**
         * Sets the value of the orderDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setOrderDate(XMLGregorianCalendar value) {
            this.orderDate = value;
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

        /**
         * Gets the value of the paymentPurpose property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPaymentPurpose() {
            return paymentPurpose;
        }

        /**
         * Sets the value of the paymentPurpose property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPaymentPurpose(String value) {
            this.paymentPurpose = value;
        }

        /**
         * Gets the value of the comment property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getComment() {
            return comment;
        }

        /**
         * Sets the value of the comment property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setComment(String value) {
            this.comment = value;
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
     *         &lt;element name="RecipientINN" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}INNType"/>
     *         &lt;element name="RecipientKPP" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}KPPType" minOccurs="0"/>
     *         &lt;element name="BankName">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="160"/>
     *               &lt;minLength value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="RecipientBIK" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}BIKType"/>
     *         &lt;element name="CorrespondentBankAccount" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}AccountType"/>
     *         &lt;element name="RecipientAccount" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}AccountType"/>
     *         &lt;element name="RecipientName">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="160"/>
     *               &lt;minLength value="1"/>
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
    @XmlType(name = "", propOrder = {
        "recipientINN",
        "recipientKPP",
        "bankName",
        "recipientBIK",
        "correspondentBankAccount",
        "recipientAccount",
        "recipientName"
    })
    public static class RecipientInfo {

        @XmlElement(name = "RecipientINN", required = true)
        protected String recipientINN;
        @XmlElement(name = "RecipientKPP")
        protected String recipientKPP;
        @XmlElement(name = "BankName", required = true)
        protected String bankName;
        @XmlElement(name = "RecipientBIK", required = true)
        protected String recipientBIK;
        @XmlElement(name = "CorrespondentBankAccount", required = true)
        protected String correspondentBankAccount;
        @XmlElement(name = "RecipientAccount", required = true)
        protected String recipientAccount;
        @XmlElement(name = "RecipientName", required = true)
        protected String recipientName;

        /**
         * Gets the value of the recipientINN property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRecipientINN() {
            return recipientINN;
        }

        /**
         * Sets the value of the recipientINN property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRecipientINN(String value) {
            this.recipientINN = value;
        }

        /**
         * Gets the value of the recipientKPP property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRecipientKPP() {
            return recipientKPP;
        }

        /**
         * Sets the value of the recipientKPP property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRecipientKPP(String value) {
            this.recipientKPP = value;
        }

        /**
         * Gets the value of the bankName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBankName() {
            return bankName;
        }

        /**
         * Sets the value of the bankName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBankName(String value) {
            this.bankName = value;
        }

        /**
         * Gets the value of the recipientBIK property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRecipientBIK() {
            return recipientBIK;
        }

        /**
         * Sets the value of the recipientBIK property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRecipientBIK(String value) {
            this.recipientBIK = value;
        }

        /**
         * Gets the value of the correspondentBankAccount property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCorrespondentBankAccount() {
            return correspondentBankAccount;
        }

        /**
         * Sets the value of the correspondentBankAccount property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCorrespondentBankAccount(String value) {
            this.correspondentBankAccount = value;
        }

        /**
         * Gets the value of the recipientAccount property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRecipientAccount() {
            return recipientAccount;
        }

        /**
         * Sets the value of the recipientAccount property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRecipientAccount(String value) {
            this.recipientAccount = value;
        }

        /**
         * Gets the value of the recipientName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRecipientName() {
            return recipientName;
        }

        /**
         * Sets the value of the recipientName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRecipientName(String value) {
            this.recipientName = value;
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
     *         &lt;element name="SupplierID">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="25"/>
     *               &lt;minLength value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="SupplierName">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;maxLength value="160"/>
     *               &lt;minLength value="1"/>
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
    @XmlType(name = "", propOrder = {
        "supplierID",
        "supplierName"
    })
    public static class SupplierInfo {

        @XmlElement(name = "SupplierID", required = true)
        protected String supplierID;
        @XmlElement(name = "SupplierName", required = true)
        protected String supplierName;

        /**
         * Gets the value of the supplierID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSupplierID() {
            return supplierID;
        }

        /**
         * Sets the value of the supplierID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSupplierID(String value) {
            this.supplierID = value;
        }

        /**
         * Gets the value of the supplierName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSupplierName() {
            return supplierName;
        }

        /**
         * Sets the value of the supplierName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSupplierName(String value) {
            this.supplierName = value;
        }

    }

}

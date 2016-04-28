
package ru.gosuslugi.dom.schema.integration._8_7_0_6.bills;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import ru.gosuslugi.dom.schema.integration._8_7_0.AcknowledgmentRequestInfoType;
import ru.gosuslugi.dom.schema.integration._8_7_0.NotificationOfOrderExecutionType;


/**
 * <p>Java class for exportNotificationsOfOrderExecutionResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="exportNotificationsOfOrderExecutionResultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NotificationOfOrderExecutionWithStatus" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}NotificationOfOrderExecutionType">
 *                 &lt;sequence>
 *                   &lt;element name="Status">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                         &lt;enumeration value="Новый"/>
 *                         &lt;enumeration value="Предварительно сквитирован"/>
 *                         &lt;enumeration value="Сквитирован"/>
 *                         &lt;enumeration value="Частично сквитирован"/>
 *                         &lt;enumeration value="Аннулирован"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="CreationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                   &lt;element name="AcknowledgmentRequestsList">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="AcknowledgmentRequest" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}AcknowledgmentRequestInfoType" maxOccurs="unbounded"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="AcknowledgmentAmount" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}AmountType"/>
 *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}CheckingAccount"/>
 *                 &lt;/sequence>
 *               &lt;/extension>
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
@XmlType(name = "exportNotificationsOfOrderExecutionResultType", propOrder = {
    "notificationOfOrderExecutionWithStatus"
})
public class ExportNotificationsOfOrderExecutionResultType {

    @XmlElement(name = "NotificationOfOrderExecutionWithStatus", required = true)
    protected List<ExportNotificationsOfOrderExecutionResultType.NotificationOfOrderExecutionWithStatus> notificationOfOrderExecutionWithStatus;

    /**
     * Gets the value of the notificationOfOrderExecutionWithStatus property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the notificationOfOrderExecutionWithStatus property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNotificationOfOrderExecutionWithStatus().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ExportNotificationsOfOrderExecutionResultType.NotificationOfOrderExecutionWithStatus }
     * 
     * 
     */
    public List<ExportNotificationsOfOrderExecutionResultType.NotificationOfOrderExecutionWithStatus> getNotificationOfOrderExecutionWithStatus() {
        if (notificationOfOrderExecutionWithStatus == null) {
            notificationOfOrderExecutionWithStatus = new ArrayList<ExportNotificationsOfOrderExecutionResultType.NotificationOfOrderExecutionWithStatus>();
        }
        return this.notificationOfOrderExecutionWithStatus;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}NotificationOfOrderExecutionType">
     *       &lt;sequence>
     *         &lt;element name="Status">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;enumeration value="Новый"/>
     *               &lt;enumeration value="Предварительно сквитирован"/>
     *               &lt;enumeration value="Сквитирован"/>
     *               &lt;enumeration value="Частично сквитирован"/>
     *               &lt;enumeration value="Аннулирован"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="CreationDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *         &lt;element name="AcknowledgmentRequestsList">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="AcknowledgmentRequest" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}AcknowledgmentRequestInfoType" maxOccurs="unbounded"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="AcknowledgmentAmount" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}AmountType"/>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}CheckingAccount"/>
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
        "status",
        "creationDate",
        "acknowledgmentRequestsList",
        "acknowledgmentAmount",
        "checkingAccount"
    })
    public static class NotificationOfOrderExecutionWithStatus
        extends NotificationOfOrderExecutionType
    {

        @XmlElement(name = "Status", required = true)
        protected String status;
        @XmlElement(name = "CreationDate", required = true)
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar creationDate;
        @XmlElement(name = "AcknowledgmentRequestsList", required = true)
        protected ExportNotificationsOfOrderExecutionResultType.NotificationOfOrderExecutionWithStatus.AcknowledgmentRequestsList acknowledgmentRequestsList;
        @XmlElement(name = "AcknowledgmentAmount")
        protected long acknowledgmentAmount;
        @XmlElement(name = "CheckingAccount", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.6/", required = true)
        protected String checkingAccount;

        /**
         * Gets the value of the status property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStatus() {
            return status;
        }

        /**
         * Sets the value of the status property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStatus(String value) {
            this.status = value;
        }

        /**
         * Gets the value of the creationDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getCreationDate() {
            return creationDate;
        }

        /**
         * Sets the value of the creationDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setCreationDate(XMLGregorianCalendar value) {
            this.creationDate = value;
        }

        /**
         * Gets the value of the acknowledgmentRequestsList property.
         * 
         * @return
         *     possible object is
         *     {@link ExportNotificationsOfOrderExecutionResultType.NotificationOfOrderExecutionWithStatus.AcknowledgmentRequestsList }
         *     
         */
        public ExportNotificationsOfOrderExecutionResultType.NotificationOfOrderExecutionWithStatus.AcknowledgmentRequestsList getAcknowledgmentRequestsList() {
            return acknowledgmentRequestsList;
        }

        /**
         * Sets the value of the acknowledgmentRequestsList property.
         * 
         * @param value
         *     allowed object is
         *     {@link ExportNotificationsOfOrderExecutionResultType.NotificationOfOrderExecutionWithStatus.AcknowledgmentRequestsList }
         *     
         */
        public void setAcknowledgmentRequestsList(ExportNotificationsOfOrderExecutionResultType.NotificationOfOrderExecutionWithStatus.AcknowledgmentRequestsList value) {
            this.acknowledgmentRequestsList = value;
        }

        /**
         * Gets the value of the acknowledgmentAmount property.
         * 
         */
        public long getAcknowledgmentAmount() {
            return acknowledgmentAmount;
        }

        /**
         * Sets the value of the acknowledgmentAmount property.
         * 
         */
        public void setAcknowledgmentAmount(long value) {
            this.acknowledgmentAmount = value;
        }

        /**
         * Gets the value of the checkingAccount property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCheckingAccount() {
            return checkingAccount;
        }

        /**
         * Sets the value of the checkingAccount property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCheckingAccount(String value) {
            this.checkingAccount = value;
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
         *         &lt;element name="AcknowledgmentRequest" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}AcknowledgmentRequestInfoType" maxOccurs="unbounded"/>
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
            "acknowledgmentRequest"
        })
        public static class AcknowledgmentRequestsList {

            @XmlElement(name = "AcknowledgmentRequest", required = true)
            protected List<AcknowledgmentRequestInfoType> acknowledgmentRequest;

            /**
             * Gets the value of the acknowledgmentRequest property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the acknowledgmentRequest property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getAcknowledgmentRequest().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link AcknowledgmentRequestInfoType }
             * 
             * 
             */
            public List<AcknowledgmentRequestInfoType> getAcknowledgmentRequest() {
                if (acknowledgmentRequest == null) {
                    acknowledgmentRequest = new ArrayList<AcknowledgmentRequestInfoType>();
                }
                return this.acknowledgmentRequest;
            }

        }

    }

}

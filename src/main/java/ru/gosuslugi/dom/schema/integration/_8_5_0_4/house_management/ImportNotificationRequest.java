
package ru.gosuslugi.dom.schema.integration._8_5_0_4.house_management;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import ru.gosuslugi.dom.schema.integration._8_5_0.AttachmentType;
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
 *         &lt;element name="notification" maxOccurs="100">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;sequence>
 *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}TransportGUID"/>
 *                     &lt;element name="NotificationGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}GUIDType" minOccurs="0"/>
 *                   &lt;/sequence>
 *                   &lt;choice>
 *                     &lt;element name="Create">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="Topic">
 *                                 &lt;simpleType>
 *                                   &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}LongTextType">
 *                                     &lt;maxLength value="200"/>
 *                                     &lt;minLength value="1"/>
 *                                   &lt;/restriction>
 *                                 &lt;/simpleType>
 *                               &lt;/element>
 *                               &lt;element name="IsImportant" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                               &lt;element name="content">
 *                                 &lt;simpleType>
 *                                   &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}LongTextType">
 *                                     &lt;minLength value="1"/>
 *                                   &lt;/restriction>
 *                                 &lt;/simpleType>
 *                               &lt;/element>
 *                               &lt;choice>
 *                                 &lt;element name="IsAll" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                                 &lt;element name="FIASHouseGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}GUIDType" maxOccurs="unbounded"/>
 *                               &lt;/choice>
 *                               &lt;choice>
 *                                 &lt;element name="IsNotLimit" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                                 &lt;sequence>
 *                                   &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *                                   &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                                 &lt;/sequence>
 *                               &lt;/choice>
 *                               &lt;element name="Attachment" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}AttachmentType" maxOccurs="unbounded" minOccurs="0"/>
 *                               &lt;element name="IsShipOff" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="IsShipOff" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                     &lt;element name="DeleteNotification" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/house-management/}DeleteDocType"/>
 *                   &lt;/choice>
 *                 &lt;/sequence>
 *               &lt;/restriction>
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
    "notification"
})
@XmlRootElement(name = "importNotificationRequest")
public class ImportNotificationRequest
    extends BaseType
{

    @XmlElement(required = true)
    protected List<ImportNotificationRequest.Notification> notification;

    /**
     * Gets the value of the notification property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the notification property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNotification().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ImportNotificationRequest.Notification }
     * 
     * 
     */
    public List<ImportNotificationRequest.Notification> getNotification() {
        if (notification == null) {
            notification = new ArrayList<ImportNotificationRequest.Notification>();
        }
        return this.notification;
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
     *         &lt;sequence>
     *           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}TransportGUID"/>
     *           &lt;element name="NotificationGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}GUIDType" minOccurs="0"/>
     *         &lt;/sequence>
     *         &lt;choice>
     *           &lt;element name="Create">
     *             &lt;complexType>
     *               &lt;complexContent>
     *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                   &lt;sequence>
     *                     &lt;element name="Topic">
     *                       &lt;simpleType>
     *                         &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}LongTextType">
     *                           &lt;maxLength value="200"/>
     *                           &lt;minLength value="1"/>
     *                         &lt;/restriction>
     *                       &lt;/simpleType>
     *                     &lt;/element>
     *                     &lt;element name="IsImportant" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *                     &lt;element name="content">
     *                       &lt;simpleType>
     *                         &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}LongTextType">
     *                           &lt;minLength value="1"/>
     *                         &lt;/restriction>
     *                       &lt;/simpleType>
     *                     &lt;/element>
     *                     &lt;choice>
     *                       &lt;element name="IsAll" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *                       &lt;element name="FIASHouseGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}GUIDType" maxOccurs="unbounded"/>
     *                     &lt;/choice>
     *                     &lt;choice>
     *                       &lt;element name="IsNotLimit" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *                       &lt;sequence>
     *                         &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
     *                         &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
     *                       &lt;/sequence>
     *                     &lt;/choice>
     *                     &lt;element name="Attachment" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}AttachmentType" maxOccurs="unbounded" minOccurs="0"/>
     *                     &lt;element name="IsShipOff" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *                   &lt;/sequence>
     *                 &lt;/restriction>
     *               &lt;/complexContent>
     *             &lt;/complexType>
     *           &lt;/element>
     *           &lt;element name="IsShipOff" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *           &lt;element name="DeleteNotification" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/house-management/}DeleteDocType"/>
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
        "transportGUID",
        "notificationGUID",
        "create",
        "isShipOff",
        "deleteNotification"
    })
    public static class Notification {

        @XmlElement(name = "TransportGUID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.5.0.4/", required = true)
        protected String transportGUID;
        @XmlElement(name = "NotificationGUID")
        protected String notificationGUID;
        @XmlElement(name = "Create")
        protected ImportNotificationRequest.Notification.Create create;
        @XmlElement(name = "IsShipOff")
        protected Boolean isShipOff;
        @XmlElement(name = "DeleteNotification")
        protected DeleteDocType deleteNotification;

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
         * Gets the value of the notificationGUID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNotificationGUID() {
            return notificationGUID;
        }

        /**
         * Sets the value of the notificationGUID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNotificationGUID(String value) {
            this.notificationGUID = value;
        }

        /**
         * Gets the value of the create property.
         * 
         * @return
         *     possible object is
         *     {@link ImportNotificationRequest.Notification.Create }
         *     
         */
        public ImportNotificationRequest.Notification.Create getCreate() {
            return create;
        }

        /**
         * Sets the value of the create property.
         * 
         * @param value
         *     allowed object is
         *     {@link ImportNotificationRequest.Notification.Create }
         *     
         */
        public void setCreate(ImportNotificationRequest.Notification.Create value) {
            this.create = value;
        }

        /**
         * Gets the value of the isShipOff property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isIsShipOff() {
            return isShipOff;
        }

        /**
         * Sets the value of the isShipOff property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setIsShipOff(Boolean value) {
            this.isShipOff = value;
        }

        /**
         * Gets the value of the deleteNotification property.
         * 
         * @return
         *     possible object is
         *     {@link DeleteDocType }
         *     
         */
        public DeleteDocType getDeleteNotification() {
            return deleteNotification;
        }

        /**
         * Sets the value of the deleteNotification property.
         * 
         * @param value
         *     allowed object is
         *     {@link DeleteDocType }
         *     
         */
        public void setDeleteNotification(DeleteDocType value) {
            this.deleteNotification = value;
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
         *         &lt;element name="Topic">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}LongTextType">
         *               &lt;maxLength value="200"/>
         *               &lt;minLength value="1"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="IsImportant" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
         *         &lt;element name="content">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}LongTextType">
         *               &lt;minLength value="1"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;choice>
         *           &lt;element name="IsAll" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
         *           &lt;element name="FIASHouseGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}GUIDType" maxOccurs="unbounded"/>
         *         &lt;/choice>
         *         &lt;choice>
         *           &lt;element name="IsNotLimit" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
         *           &lt;sequence>
         *             &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
         *             &lt;element name="EndDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
         *           &lt;/sequence>
         *         &lt;/choice>
         *         &lt;element name="Attachment" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}AttachmentType" maxOccurs="unbounded" minOccurs="0"/>
         *         &lt;element name="IsShipOff" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
            "topic",
            "isImportant",
            "content",
            "isAll",
            "fiasHouseGuid",
            "isNotLimit",
            "startDate",
            "endDate",
            "attachment",
            "isShipOff"
        })
        public static class Create {

            @XmlElement(name = "Topic", required = true)
            protected String topic;
            @XmlElement(name = "IsImportant")
            protected Boolean isImportant;
            @XmlElement(required = true)
            protected String content;
            @XmlElement(name = "IsAll")
            protected Boolean isAll;
            @XmlElement(name = "FIASHouseGuid")
            protected List<String> fiasHouseGuid;
            @XmlElement(name = "IsNotLimit")
            protected Boolean isNotLimit;
            @XmlElement(name = "StartDate")
            @XmlSchemaType(name = "dateTime")
            protected XMLGregorianCalendar startDate;
            @XmlElement(name = "EndDate")
            @XmlSchemaType(name = "dateTime")
            protected XMLGregorianCalendar endDate;
            @XmlElement(name = "Attachment")
            protected List<AttachmentType> attachment;
            @XmlElement(name = "IsShipOff")
            protected Boolean isShipOff;

            /**
             * Gets the value of the topic property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTopic() {
                return topic;
            }

            /**
             * Sets the value of the topic property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTopic(String value) {
                this.topic = value;
            }

            /**
             * Gets the value of the isImportant property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isIsImportant() {
                return isImportant;
            }

            /**
             * Sets the value of the isImportant property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setIsImportant(Boolean value) {
                this.isImportant = value;
            }

            /**
             * Gets the value of the content property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getContent() {
                return content;
            }

            /**
             * Sets the value of the content property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setContent(String value) {
                this.content = value;
            }

            /**
             * Gets the value of the isAll property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isIsAll() {
                return isAll;
            }

            /**
             * Sets the value of the isAll property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setIsAll(Boolean value) {
                this.isAll = value;
            }

            /**
             * Gets the value of the fiasHouseGuid property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the fiasHouseGuid property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getFIASHouseGuid().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link String }
             * 
             * 
             */
            public List<String> getFIASHouseGuid() {
                if (fiasHouseGuid == null) {
                    fiasHouseGuid = new ArrayList<String>();
                }
                return this.fiasHouseGuid;
            }

            /**
             * Gets the value of the isNotLimit property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isIsNotLimit() {
                return isNotLimit;
            }

            /**
             * Sets the value of the isNotLimit property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setIsNotLimit(Boolean value) {
                this.isNotLimit = value;
            }

            /**
             * Gets the value of the startDate property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getStartDate() {
                return startDate;
            }

            /**
             * Sets the value of the startDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setStartDate(XMLGregorianCalendar value) {
                this.startDate = value;
            }

            /**
             * Gets the value of the endDate property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getEndDate() {
                return endDate;
            }

            /**
             * Sets the value of the endDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setEndDate(XMLGregorianCalendar value) {
                this.endDate = value;
            }

            /**
             * Gets the value of the attachment property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the attachment property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getAttachment().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link AttachmentType }
             * 
             * 
             */
            public List<AttachmentType> getAttachment() {
                if (attachment == null) {
                    attachment = new ArrayList<AttachmentType>();
                }
                return this.attachment;
            }

            /**
             * Gets the value of the isShipOff property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isIsShipOff() {
                return isShipOff;
            }

            /**
             * Sets the value of the isShipOff property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setIsShipOff(Boolean value) {
                this.isShipOff = value;
            }

        }

    }

}

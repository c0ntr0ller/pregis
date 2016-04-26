
package ru.gosuslugi.dom.schema.integration._8_7_0_4.payment;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_7_0.ErrorMessageType;


/**
 * Информация, необходимая для внесения платы
 * 
 * <p>Java class for ChargeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ChargeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice minOccurs="0">
 *           &lt;element name="PaymentDocument" maxOccurs="unbounded">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}PaymentDocumentNumber" minOccurs="0"/>
 *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}UnifiedAccountNumber" minOccurs="0"/>
 *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}AccountNumber" minOccurs="0"/>
 *                     &lt;element name="ConsumerInformation" minOccurs="0">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;choice minOccurs="0">
 *                                 &lt;element name="Entpr">
 *                                   &lt;complexType>
 *                                     &lt;complexContent>
 *                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                         &lt;sequence>
 *                                           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}INN"/>
 *                                         &lt;/sequence>
 *                                       &lt;/restriction>
 *                                     &lt;/complexContent>
 *                                   &lt;/complexType>
 *                                 &lt;/element>
 *                                 &lt;element name="Legal">
 *                                   &lt;complexType>
 *                                     &lt;complexContent>
 *                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                         &lt;sequence>
 *                                           &lt;element name="Name">
 *                                             &lt;simpleType>
 *                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                                 &lt;maxLength value="160"/>
 *                                                 &lt;minLength value="1"/>
 *                                               &lt;/restriction>
 *                                             &lt;/simpleType>
 *                                           &lt;/element>
 *                                           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}KPP"/>
 *                                           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}INN"/>
 *                                         &lt;/sequence>
 *                                       &lt;/restriction>
 *                                     &lt;/complexContent>
 *                                   &lt;/complexType>
 *                                 &lt;/element>
 *                               &lt;/choice>
 *                               &lt;element name="address" maxOccurs="unbounded">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                       &lt;sequence>
 *                                         &lt;element name="region" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                         &lt;element name="area" minOccurs="0">
 *                                           &lt;simpleType>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                               &lt;minLength value="1"/>
 *                                               &lt;maxLength value="120"/>
 *                                             &lt;/restriction>
 *                                           &lt;/simpleType>
 *                                         &lt;/element>
 *                                         &lt;element name="city" minOccurs="0">
 *                                           &lt;simpleType>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                               &lt;maxLength value="120"/>
 *                                               &lt;minLength value="1"/>
 *                                             &lt;/restriction>
 *                                           &lt;/simpleType>
 *                                         &lt;/element>
 *                                         &lt;element name="place" minOccurs="0">
 *                                           &lt;simpleType>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                               &lt;minLength value="1"/>
 *                                               &lt;maxLength value="120"/>
 *                                             &lt;/restriction>
 *                                           &lt;/simpleType>
 *                                         &lt;/element>
 *                                         &lt;element name="street" minOccurs="0">
 *                                           &lt;simpleType>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                               &lt;minLength value="1"/>
 *                                               &lt;maxLength value="120"/>
 *                                             &lt;/restriction>
 *                                           &lt;/simpleType>
 *                                         &lt;/element>
 *                                         &lt;element name="housenum" minOccurs="0">
 *                                           &lt;simpleType>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                               &lt;minLength value="1"/>
 *                                               &lt;maxLength value="20"/>
 *                                             &lt;/restriction>
 *                                           &lt;/simpleType>
 *                                         &lt;/element>
 *                                         &lt;element name="FIASHouseGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}FIASHouseGUIDType"/>
 *                                         &lt;element name="buildnum" minOccurs="0">
 *                                           &lt;simpleType>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                               &lt;minLength value="1"/>
 *                                               &lt;maxLength value="10"/>
 *                                             &lt;/restriction>
 *                                           &lt;/simpleType>
 *                                         &lt;/element>
 *                                         &lt;element name="strucnum" minOccurs="0">
 *                                           &lt;simpleType>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                               &lt;minLength value="1"/>
 *                                               &lt;maxLength value="10"/>
 *                                             &lt;/restriction>
 *                                           &lt;/simpleType>
 *                                         &lt;/element>
 *                                         &lt;element name="apartment" minOccurs="0">
 *                                           &lt;simpleType>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                               &lt;minLength value="1"/>
 *                                               &lt;maxLength value="255"/>
 *                                             &lt;/restriction>
 *                                           &lt;/simpleType>
 *                                         &lt;/element>
 *                                         &lt;element name="placement" minOccurs="0">
 *                                           &lt;simpleType>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                               &lt;minLength value="1"/>
 *                                               &lt;maxLength value="255"/>
 *                                             &lt;/restriction>
 *                                           &lt;/simpleType>
 *                                         &lt;/element>
 *                                         &lt;element name="address_string">
 *                                           &lt;simpleType>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                               &lt;maxLength value="2500"/>
 *                                               &lt;minLength value="1"/>
 *                                             &lt;/restriction>
 *                                           &lt;/simpleType>
 *                                         &lt;/element>
 *                                       &lt;/sequence>
 *                                     &lt;/restriction>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;choice>
 *                       &lt;element name="PaymentDocumentDetails" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/payment/}PaymentDocumentDetailsType"/>
 *                       &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}ErrorMessage"/>
 *                     &lt;/choice>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="Service" maxOccurs="unbounded">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/payment/}ServiceDetailsType">
 *                   &lt;sequence>
 *                     &lt;element name="ConsumerInformation" minOccurs="0">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;choice minOccurs="0">
 *                                 &lt;element name="Entpr">
 *                                   &lt;complexType>
 *                                     &lt;complexContent>
 *                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                         &lt;sequence>
 *                                           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}INN"/>
 *                                         &lt;/sequence>
 *                                       &lt;/restriction>
 *                                     &lt;/complexContent>
 *                                   &lt;/complexType>
 *                                 &lt;/element>
 *                                 &lt;element name="Legal">
 *                                   &lt;complexType>
 *                                     &lt;complexContent>
 *                                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                         &lt;sequence>
 *                                           &lt;element name="Name">
 *                                             &lt;simpleType>
 *                                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                                 &lt;maxLength value="160"/>
 *                                                 &lt;minLength value="1"/>
 *                                               &lt;/restriction>
 *                                             &lt;/simpleType>
 *                                           &lt;/element>
 *                                           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}KPP"/>
 *                                           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}INN"/>
 *                                         &lt;/sequence>
 *                                       &lt;/restriction>
 *                                     &lt;/complexContent>
 *                                   &lt;/complexType>
 *                                 &lt;/element>
 *                               &lt;/choice>
 *                               &lt;element name="address" maxOccurs="unbounded">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                       &lt;sequence>
 *                                         &lt;element name="region" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                                         &lt;element name="area" minOccurs="0">
 *                                           &lt;simpleType>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                               &lt;minLength value="1"/>
 *                                               &lt;maxLength value="120"/>
 *                                             &lt;/restriction>
 *                                           &lt;/simpleType>
 *                                         &lt;/element>
 *                                         &lt;element name="city" minOccurs="0">
 *                                           &lt;simpleType>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                               &lt;maxLength value="120"/>
 *                                               &lt;minLength value="1"/>
 *                                             &lt;/restriction>
 *                                           &lt;/simpleType>
 *                                         &lt;/element>
 *                                         &lt;element name="place" minOccurs="0">
 *                                           &lt;simpleType>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                               &lt;minLength value="1"/>
 *                                               &lt;maxLength value="120"/>
 *                                             &lt;/restriction>
 *                                           &lt;/simpleType>
 *                                         &lt;/element>
 *                                         &lt;element name="street" minOccurs="0">
 *                                           &lt;simpleType>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                               &lt;minLength value="1"/>
 *                                               &lt;maxLength value="120"/>
 *                                             &lt;/restriction>
 *                                           &lt;/simpleType>
 *                                         &lt;/element>
 *                                         &lt;element name="housenum" minOccurs="0">
 *                                           &lt;simpleType>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                               &lt;minLength value="1"/>
 *                                               &lt;maxLength value="20"/>
 *                                             &lt;/restriction>
 *                                           &lt;/simpleType>
 *                                         &lt;/element>
 *                                         &lt;element name="FIASHouseGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}FIASHouseGUIDType"/>
 *                                         &lt;element name="buildnum" minOccurs="0">
 *                                           &lt;simpleType>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                               &lt;minLength value="1"/>
 *                                               &lt;maxLength value="10"/>
 *                                             &lt;/restriction>
 *                                           &lt;/simpleType>
 *                                         &lt;/element>
 *                                         &lt;element name="strucnum" minOccurs="0">
 *                                           &lt;simpleType>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                               &lt;minLength value="1"/>
 *                                               &lt;maxLength value="10"/>
 *                                             &lt;/restriction>
 *                                           &lt;/simpleType>
 *                                         &lt;/element>
 *                                         &lt;element name="apartment" minOccurs="0">
 *                                           &lt;simpleType>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                               &lt;minLength value="1"/>
 *                                               &lt;maxLength value="255"/>
 *                                             &lt;/restriction>
 *                                           &lt;/simpleType>
 *                                         &lt;/element>
 *                                         &lt;element name="placement" minOccurs="0">
 *                                           &lt;simpleType>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                               &lt;minLength value="1"/>
 *                                               &lt;maxLength value="255"/>
 *                                             &lt;/restriction>
 *                                           &lt;/simpleType>
 *                                         &lt;/element>
 *                                         &lt;element name="address_string">
 *                                           &lt;simpleType>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                               &lt;maxLength value="2500"/>
 *                                               &lt;minLength value="1"/>
 *                                             &lt;/restriction>
 *                                           &lt;/simpleType>
 *                                         &lt;/element>
 *                                       &lt;/sequence>
 *                                     &lt;/restriction>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                   &lt;/sequence>
 *                 &lt;/extension>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/choice>
 *         &lt;sequence>
 *           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}Year"/>
 *           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}Month"/>
 *         &lt;/sequence>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ChargeType", propOrder = {
    "paymentDocument",
    "service",
    "year",
    "month"
})
public class ChargeType {

    @XmlElement(name = "PaymentDocument")
    protected List<ChargeType.PaymentDocument> paymentDocument;
    @XmlElement(name = "Service")
    protected List<ChargeType.Service> service;
    @XmlElement(name = "Year", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/")
    protected short year;
    @XmlElement(name = "Month", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/")
    protected int month;

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
     * {@link ChargeType.PaymentDocument }
     * 
     * 
     */
    public List<ChargeType.PaymentDocument> getPaymentDocument() {
        if (paymentDocument == null) {
            paymentDocument = new ArrayList<ChargeType.PaymentDocument>();
        }
        return this.paymentDocument;
    }

    /**
     * Gets the value of the service property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the service property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getService().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ChargeType.Service }
     * 
     * 
     */
    public List<ChargeType.Service> getService() {
        if (service == null) {
            service = new ArrayList<ChargeType.Service>();
        }
        return this.service;
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
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}PaymentDocumentNumber" minOccurs="0"/>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}UnifiedAccountNumber" minOccurs="0"/>
     *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}AccountNumber" minOccurs="0"/>
     *         &lt;element name="ConsumerInformation" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;choice minOccurs="0">
     *                     &lt;element name="Entpr">
     *                       &lt;complexType>
     *                         &lt;complexContent>
     *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                             &lt;sequence>
     *                               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}INN"/>
     *                             &lt;/sequence>
     *                           &lt;/restriction>
     *                         &lt;/complexContent>
     *                       &lt;/complexType>
     *                     &lt;/element>
     *                     &lt;element name="Legal">
     *                       &lt;complexType>
     *                         &lt;complexContent>
     *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                             &lt;sequence>
     *                               &lt;element name="Name">
     *                                 &lt;simpleType>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                     &lt;maxLength value="160"/>
     *                                     &lt;minLength value="1"/>
     *                                   &lt;/restriction>
     *                                 &lt;/simpleType>
     *                               &lt;/element>
     *                               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}KPP"/>
     *                               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}INN"/>
     *                             &lt;/sequence>
     *                           &lt;/restriction>
     *                         &lt;/complexContent>
     *                       &lt;/complexType>
     *                     &lt;/element>
     *                   &lt;/choice>
     *                   &lt;element name="address" maxOccurs="unbounded">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="region" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="area" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;minLength value="1"/>
     *                                   &lt;maxLength value="120"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="city" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;maxLength value="120"/>
     *                                   &lt;minLength value="1"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="place" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;minLength value="1"/>
     *                                   &lt;maxLength value="120"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="street" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;minLength value="1"/>
     *                                   &lt;maxLength value="120"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="housenum" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;minLength value="1"/>
     *                                   &lt;maxLength value="20"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="FIASHouseGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}FIASHouseGUIDType"/>
     *                             &lt;element name="buildnum" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;minLength value="1"/>
     *                                   &lt;maxLength value="10"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="strucnum" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;minLength value="1"/>
     *                                   &lt;maxLength value="10"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="apartment" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;minLength value="1"/>
     *                                   &lt;maxLength value="255"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="placement" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;minLength value="1"/>
     *                                   &lt;maxLength value="255"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="address_string">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;maxLength value="2500"/>
     *                                   &lt;minLength value="1"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;choice>
     *           &lt;element name="PaymentDocumentDetails" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/payment/}PaymentDocumentDetailsType"/>
     *           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}ErrorMessage"/>
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
        "paymentDocumentNumber",
        "unifiedAccountNumber",
        "accountNumber",
        "consumerInformation",
        "paymentDocumentDetails",
        "errorMessage"
    })
    public static class PaymentDocument {

        @XmlElement(name = "PaymentDocumentNumber", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/")
        protected String paymentDocumentNumber;
        @XmlElement(name = "UnifiedAccountNumber", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/")
        protected String unifiedAccountNumber;
        @XmlElement(name = "AccountNumber", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/")
        protected String accountNumber;
        @XmlElement(name = "ConsumerInformation")
        protected ChargeType.PaymentDocument.ConsumerInformation consumerInformation;
        @XmlElement(name = "PaymentDocumentDetails")
        protected PaymentDocumentDetailsType paymentDocumentDetails;
        @XmlElement(name = "ErrorMessage", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/")
        protected ErrorMessageType errorMessage;

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

        /**
         * Gets the value of the unifiedAccountNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUnifiedAccountNumber() {
            return unifiedAccountNumber;
        }

        /**
         * Sets the value of the unifiedAccountNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUnifiedAccountNumber(String value) {
            this.unifiedAccountNumber = value;
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
         * Gets the value of the consumerInformation property.
         * 
         * @return
         *     possible object is
         *     {@link ChargeType.PaymentDocument.ConsumerInformation }
         *     
         */
        public ChargeType.PaymentDocument.ConsumerInformation getConsumerInformation() {
            return consumerInformation;
        }

        /**
         * Sets the value of the consumerInformation property.
         * 
         * @param value
         *     allowed object is
         *     {@link ChargeType.PaymentDocument.ConsumerInformation }
         *     
         */
        public void setConsumerInformation(ChargeType.PaymentDocument.ConsumerInformation value) {
            this.consumerInformation = value;
        }

        /**
         * Gets the value of the paymentDocumentDetails property.
         * 
         * @return
         *     possible object is
         *     {@link PaymentDocumentDetailsType }
         *     
         */
        public PaymentDocumentDetailsType getPaymentDocumentDetails() {
            return paymentDocumentDetails;
        }

        /**
         * Sets the value of the paymentDocumentDetails property.
         * 
         * @param value
         *     allowed object is
         *     {@link PaymentDocumentDetailsType }
         *     
         */
        public void setPaymentDocumentDetails(PaymentDocumentDetailsType value) {
            this.paymentDocumentDetails = value;
        }

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
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;choice minOccurs="0">
         *           &lt;element name="Entpr">
         *             &lt;complexType>
         *               &lt;complexContent>
         *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                   &lt;sequence>
         *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}INN"/>
         *                   &lt;/sequence>
         *                 &lt;/restriction>
         *               &lt;/complexContent>
         *             &lt;/complexType>
         *           &lt;/element>
         *           &lt;element name="Legal">
         *             &lt;complexType>
         *               &lt;complexContent>
         *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                   &lt;sequence>
         *                     &lt;element name="Name">
         *                       &lt;simpleType>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                           &lt;maxLength value="160"/>
         *                           &lt;minLength value="1"/>
         *                         &lt;/restriction>
         *                       &lt;/simpleType>
         *                     &lt;/element>
         *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}KPP"/>
         *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}INN"/>
         *                   &lt;/sequence>
         *                 &lt;/restriction>
         *               &lt;/complexContent>
         *             &lt;/complexType>
         *           &lt;/element>
         *         &lt;/choice>
         *         &lt;element name="address" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="region" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="area" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;minLength value="1"/>
         *                         &lt;maxLength value="120"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="city" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;maxLength value="120"/>
         *                         &lt;minLength value="1"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="place" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;minLength value="1"/>
         *                         &lt;maxLength value="120"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="street" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;minLength value="1"/>
         *                         &lt;maxLength value="120"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="housenum" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;minLength value="1"/>
         *                         &lt;maxLength value="20"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="FIASHouseGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}FIASHouseGUIDType"/>
         *                   &lt;element name="buildnum" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;minLength value="1"/>
         *                         &lt;maxLength value="10"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="strucnum" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;minLength value="1"/>
         *                         &lt;maxLength value="10"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="apartment" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;minLength value="1"/>
         *                         &lt;maxLength value="255"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="placement" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;minLength value="1"/>
         *                         &lt;maxLength value="255"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="address_string">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;maxLength value="2500"/>
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
        @XmlType(name = "", propOrder = {
            "entpr",
            "legal",
            "address"
        })
        public static class ConsumerInformation {

            @XmlElement(name = "Entpr")
            protected ChargeType.PaymentDocument.ConsumerInformation.Entpr entpr;
            @XmlElement(name = "Legal")
            protected ChargeType.PaymentDocument.ConsumerInformation.Legal legal;
            @XmlElement(required = true)
            protected List<ChargeType.PaymentDocument.ConsumerInformation.Address> address;

            /**
             * Gets the value of the entpr property.
             * 
             * @return
             *     possible object is
             *     {@link ChargeType.PaymentDocument.ConsumerInformation.Entpr }
             *     
             */
            public ChargeType.PaymentDocument.ConsumerInformation.Entpr getEntpr() {
                return entpr;
            }

            /**
             * Sets the value of the entpr property.
             * 
             * @param value
             *     allowed object is
             *     {@link ChargeType.PaymentDocument.ConsumerInformation.Entpr }
             *     
             */
            public void setEntpr(ChargeType.PaymentDocument.ConsumerInformation.Entpr value) {
                this.entpr = value;
            }

            /**
             * Gets the value of the legal property.
             * 
             * @return
             *     possible object is
             *     {@link ChargeType.PaymentDocument.ConsumerInformation.Legal }
             *     
             */
            public ChargeType.PaymentDocument.ConsumerInformation.Legal getLegal() {
                return legal;
            }

            /**
             * Sets the value of the legal property.
             * 
             * @param value
             *     allowed object is
             *     {@link ChargeType.PaymentDocument.ConsumerInformation.Legal }
             *     
             */
            public void setLegal(ChargeType.PaymentDocument.ConsumerInformation.Legal value) {
                this.legal = value;
            }

            /**
             * Gets the value of the address property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the address property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getAddress().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ChargeType.PaymentDocument.ConsumerInformation.Address }
             * 
             * 
             */
            public List<ChargeType.PaymentDocument.ConsumerInformation.Address> getAddress() {
                if (address == null) {
                    address = new ArrayList<ChargeType.PaymentDocument.ConsumerInformation.Address>();
                }
                return this.address;
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
             *         &lt;element name="region" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="area" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;minLength value="1"/>
             *               &lt;maxLength value="120"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="city" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;maxLength value="120"/>
             *               &lt;minLength value="1"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="place" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;minLength value="1"/>
             *               &lt;maxLength value="120"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="street" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;minLength value="1"/>
             *               &lt;maxLength value="120"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="housenum" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;minLength value="1"/>
             *               &lt;maxLength value="20"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="FIASHouseGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}FIASHouseGUIDType"/>
             *         &lt;element name="buildnum" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;minLength value="1"/>
             *               &lt;maxLength value="10"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="strucnum" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;minLength value="1"/>
             *               &lt;maxLength value="10"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="apartment" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;minLength value="1"/>
             *               &lt;maxLength value="255"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="placement" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;minLength value="1"/>
             *               &lt;maxLength value="255"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="address_string">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;maxLength value="2500"/>
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
                "region",
                "area",
                "city",
                "place",
                "street",
                "housenum",
                "fiasHouseGuid",
                "buildnum",
                "strucnum",
                "apartment",
                "placement",
                "addressString"
            })
            public static class Address {

                @XmlElement(required = true)
                protected String region;
                protected String area;
                protected String city;
                protected String place;
                protected String street;
                protected String housenum;
                @XmlElement(name = "FIASHouseGuid", required = true)
                protected String fiasHouseGuid;
                protected String buildnum;
                protected String strucnum;
                protected String apartment;
                protected String placement;
                @XmlElement(name = "address_string", required = true)
                protected String addressString;

                /**
                 * Gets the value of the region property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getRegion() {
                    return region;
                }

                /**
                 * Sets the value of the region property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setRegion(String value) {
                    this.region = value;
                }

                /**
                 * Gets the value of the area property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getArea() {
                    return area;
                }

                /**
                 * Sets the value of the area property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setArea(String value) {
                    this.area = value;
                }

                /**
                 * Gets the value of the city property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getCity() {
                    return city;
                }

                /**
                 * Sets the value of the city property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setCity(String value) {
                    this.city = value;
                }

                /**
                 * Gets the value of the place property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPlace() {
                    return place;
                }

                /**
                 * Sets the value of the place property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPlace(String value) {
                    this.place = value;
                }

                /**
                 * Gets the value of the street property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getStreet() {
                    return street;
                }

                /**
                 * Sets the value of the street property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setStreet(String value) {
                    this.street = value;
                }

                /**
                 * Gets the value of the housenum property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getHousenum() {
                    return housenum;
                }

                /**
                 * Sets the value of the housenum property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setHousenum(String value) {
                    this.housenum = value;
                }

                /**
                 * Gets the value of the fiasHouseGuid property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getFIASHouseGuid() {
                    return fiasHouseGuid;
                }

                /**
                 * Sets the value of the fiasHouseGuid property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setFIASHouseGuid(String value) {
                    this.fiasHouseGuid = value;
                }

                /**
                 * Gets the value of the buildnum property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getBuildnum() {
                    return buildnum;
                }

                /**
                 * Sets the value of the buildnum property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setBuildnum(String value) {
                    this.buildnum = value;
                }

                /**
                 * Gets the value of the strucnum property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getStrucnum() {
                    return strucnum;
                }

                /**
                 * Sets the value of the strucnum property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setStrucnum(String value) {
                    this.strucnum = value;
                }

                /**
                 * Gets the value of the apartment property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getApartment() {
                    return apartment;
                }

                /**
                 * Sets the value of the apartment property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setApartment(String value) {
                    this.apartment = value;
                }

                /**
                 * Gets the value of the placement property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPlacement() {
                    return placement;
                }

                /**
                 * Sets the value of the placement property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPlacement(String value) {
                    this.placement = value;
                }

                /**
                 * Gets the value of the addressString property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAddressString() {
                    return addressString;
                }

                /**
                 * Sets the value of the addressString property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAddressString(String value) {
                    this.addressString = value;
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
             *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}INN"/>
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
                "inn"
            })
            public static class Entpr {

                @XmlElement(name = "INN", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", required = true)
                protected String inn;

                /**
                 * Gets the value of the inn property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getINN() {
                    return inn;
                }

                /**
                 * Sets the value of the inn property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setINN(String value) {
                    this.inn = value;
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
             *         &lt;element name="Name">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;maxLength value="160"/>
             *               &lt;minLength value="1"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}KPP"/>
             *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}INN"/>
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
                "name",
                "kpp",
                "inn"
            })
            public static class Legal {

                @XmlElement(name = "Name", required = true)
                protected String name;
                @XmlElement(name = "KPP", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", required = true)
                protected String kpp;
                @XmlElement(name = "INN", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", required = true)
                protected String inn;

                /**
                 * Gets the value of the name property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getName() {
                    return name;
                }

                /**
                 * Sets the value of the name property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setName(String value) {
                    this.name = value;
                }

                /**
                 * Gets the value of the kpp property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getKPP() {
                    return kpp;
                }

                /**
                 * Sets the value of the kpp property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setKPP(String value) {
                    this.kpp = value;
                }

                /**
                 * Gets the value of the inn property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getINN() {
                    return inn;
                }

                /**
                 * Sets the value of the inn property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setINN(String value) {
                    this.inn = value;
                }

            }

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
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/payment/}ServiceDetailsType">
     *       &lt;sequence>
     *         &lt;element name="ConsumerInformation" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;choice minOccurs="0">
     *                     &lt;element name="Entpr">
     *                       &lt;complexType>
     *                         &lt;complexContent>
     *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                             &lt;sequence>
     *                               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}INN"/>
     *                             &lt;/sequence>
     *                           &lt;/restriction>
     *                         &lt;/complexContent>
     *                       &lt;/complexType>
     *                     &lt;/element>
     *                     &lt;element name="Legal">
     *                       &lt;complexType>
     *                         &lt;complexContent>
     *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                             &lt;sequence>
     *                               &lt;element name="Name">
     *                                 &lt;simpleType>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                     &lt;maxLength value="160"/>
     *                                     &lt;minLength value="1"/>
     *                                   &lt;/restriction>
     *                                 &lt;/simpleType>
     *                               &lt;/element>
     *                               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}KPP"/>
     *                               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}INN"/>
     *                             &lt;/sequence>
     *                           &lt;/restriction>
     *                         &lt;/complexContent>
     *                       &lt;/complexType>
     *                     &lt;/element>
     *                   &lt;/choice>
     *                   &lt;element name="address" maxOccurs="unbounded">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="region" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                             &lt;element name="area" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;minLength value="1"/>
     *                                   &lt;maxLength value="120"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="city" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;maxLength value="120"/>
     *                                   &lt;minLength value="1"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="place" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;minLength value="1"/>
     *                                   &lt;maxLength value="120"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="street" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;minLength value="1"/>
     *                                   &lt;maxLength value="120"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="housenum" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;minLength value="1"/>
     *                                   &lt;maxLength value="20"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="FIASHouseGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}FIASHouseGUIDType"/>
     *                             &lt;element name="buildnum" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;minLength value="1"/>
     *                                   &lt;maxLength value="10"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="strucnum" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;minLength value="1"/>
     *                                   &lt;maxLength value="10"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="apartment" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;minLength value="1"/>
     *                                   &lt;maxLength value="255"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="placement" minOccurs="0">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;minLength value="1"/>
     *                                   &lt;maxLength value="255"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                             &lt;element name="address_string">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                   &lt;maxLength value="2500"/>
     *                                   &lt;minLength value="1"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
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
        "consumerInformation"
    })
    public static class Service
        extends ServiceDetailsType
    {

        @XmlElement(name = "ConsumerInformation")
        protected ChargeType.Service.ConsumerInformation consumerInformation;

        /**
         * Gets the value of the consumerInformation property.
         * 
         * @return
         *     possible object is
         *     {@link ChargeType.Service.ConsumerInformation }
         *     
         */
        public ChargeType.Service.ConsumerInformation getConsumerInformation() {
            return consumerInformation;
        }

        /**
         * Sets the value of the consumerInformation property.
         * 
         * @param value
         *     allowed object is
         *     {@link ChargeType.Service.ConsumerInformation }
         *     
         */
        public void setConsumerInformation(ChargeType.Service.ConsumerInformation value) {
            this.consumerInformation = value;
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
         *         &lt;choice minOccurs="0">
         *           &lt;element name="Entpr">
         *             &lt;complexType>
         *               &lt;complexContent>
         *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                   &lt;sequence>
         *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}INN"/>
         *                   &lt;/sequence>
         *                 &lt;/restriction>
         *               &lt;/complexContent>
         *             &lt;/complexType>
         *           &lt;/element>
         *           &lt;element name="Legal">
         *             &lt;complexType>
         *               &lt;complexContent>
         *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                   &lt;sequence>
         *                     &lt;element name="Name">
         *                       &lt;simpleType>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                           &lt;maxLength value="160"/>
         *                           &lt;minLength value="1"/>
         *                         &lt;/restriction>
         *                       &lt;/simpleType>
         *                     &lt;/element>
         *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}KPP"/>
         *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}INN"/>
         *                   &lt;/sequence>
         *                 &lt;/restriction>
         *               &lt;/complexContent>
         *             &lt;/complexType>
         *           &lt;/element>
         *         &lt;/choice>
         *         &lt;element name="address" maxOccurs="unbounded">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="region" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *                   &lt;element name="area" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;minLength value="1"/>
         *                         &lt;maxLength value="120"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="city" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;maxLength value="120"/>
         *                         &lt;minLength value="1"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="place" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;minLength value="1"/>
         *                         &lt;maxLength value="120"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="street" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;minLength value="1"/>
         *                         &lt;maxLength value="120"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="housenum" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;minLength value="1"/>
         *                         &lt;maxLength value="20"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="FIASHouseGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}FIASHouseGUIDType"/>
         *                   &lt;element name="buildnum" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;minLength value="1"/>
         *                         &lt;maxLength value="10"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="strucnum" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;minLength value="1"/>
         *                         &lt;maxLength value="10"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="apartment" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;minLength value="1"/>
         *                         &lt;maxLength value="255"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="placement" minOccurs="0">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;minLength value="1"/>
         *                         &lt;maxLength value="255"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                   &lt;element name="address_string">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                         &lt;maxLength value="2500"/>
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
        @XmlType(name = "", propOrder = {
            "entpr",
            "legal",
            "address"
        })
        public static class ConsumerInformation {

            @XmlElement(name = "Entpr")
            protected ChargeType.Service.ConsumerInformation.Entpr entpr;
            @XmlElement(name = "Legal")
            protected ChargeType.Service.ConsumerInformation.Legal legal;
            @XmlElement(required = true)
            protected List<ChargeType.Service.ConsumerInformation.Address> address;

            /**
             * Gets the value of the entpr property.
             * 
             * @return
             *     possible object is
             *     {@link ChargeType.Service.ConsumerInformation.Entpr }
             *     
             */
            public ChargeType.Service.ConsumerInformation.Entpr getEntpr() {
                return entpr;
            }

            /**
             * Sets the value of the entpr property.
             * 
             * @param value
             *     allowed object is
             *     {@link ChargeType.Service.ConsumerInformation.Entpr }
             *     
             */
            public void setEntpr(ChargeType.Service.ConsumerInformation.Entpr value) {
                this.entpr = value;
            }

            /**
             * Gets the value of the legal property.
             * 
             * @return
             *     possible object is
             *     {@link ChargeType.Service.ConsumerInformation.Legal }
             *     
             */
            public ChargeType.Service.ConsumerInformation.Legal getLegal() {
                return legal;
            }

            /**
             * Sets the value of the legal property.
             * 
             * @param value
             *     allowed object is
             *     {@link ChargeType.Service.ConsumerInformation.Legal }
             *     
             */
            public void setLegal(ChargeType.Service.ConsumerInformation.Legal value) {
                this.legal = value;
            }

            /**
             * Gets the value of the address property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the address property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getAddress().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link ChargeType.Service.ConsumerInformation.Address }
             * 
             * 
             */
            public List<ChargeType.Service.ConsumerInformation.Address> getAddress() {
                if (address == null) {
                    address = new ArrayList<ChargeType.Service.ConsumerInformation.Address>();
                }
                return this.address;
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
             *         &lt;element name="region" type="{http://www.w3.org/2001/XMLSchema}string"/>
             *         &lt;element name="area" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;minLength value="1"/>
             *               &lt;maxLength value="120"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="city" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;maxLength value="120"/>
             *               &lt;minLength value="1"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="place" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;minLength value="1"/>
             *               &lt;maxLength value="120"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="street" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;minLength value="1"/>
             *               &lt;maxLength value="120"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="housenum" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;minLength value="1"/>
             *               &lt;maxLength value="20"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="FIASHouseGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}FIASHouseGUIDType"/>
             *         &lt;element name="buildnum" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;minLength value="1"/>
             *               &lt;maxLength value="10"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="strucnum" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;minLength value="1"/>
             *               &lt;maxLength value="10"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="apartment" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;minLength value="1"/>
             *               &lt;maxLength value="255"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="placement" minOccurs="0">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;minLength value="1"/>
             *               &lt;maxLength value="255"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element name="address_string">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;maxLength value="2500"/>
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
                "region",
                "area",
                "city",
                "place",
                "street",
                "housenum",
                "fiasHouseGuid",
                "buildnum",
                "strucnum",
                "apartment",
                "placement",
                "addressString"
            })
            public static class Address {

                @XmlElement(required = true)
                protected String region;
                protected String area;
                protected String city;
                protected String place;
                protected String street;
                protected String housenum;
                @XmlElement(name = "FIASHouseGuid", required = true)
                protected String fiasHouseGuid;
                protected String buildnum;
                protected String strucnum;
                protected String apartment;
                protected String placement;
                @XmlElement(name = "address_string", required = true)
                protected String addressString;

                /**
                 * Gets the value of the region property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getRegion() {
                    return region;
                }

                /**
                 * Sets the value of the region property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setRegion(String value) {
                    this.region = value;
                }

                /**
                 * Gets the value of the area property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getArea() {
                    return area;
                }

                /**
                 * Sets the value of the area property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setArea(String value) {
                    this.area = value;
                }

                /**
                 * Gets the value of the city property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getCity() {
                    return city;
                }

                /**
                 * Sets the value of the city property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setCity(String value) {
                    this.city = value;
                }

                /**
                 * Gets the value of the place property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPlace() {
                    return place;
                }

                /**
                 * Sets the value of the place property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPlace(String value) {
                    this.place = value;
                }

                /**
                 * Gets the value of the street property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getStreet() {
                    return street;
                }

                /**
                 * Sets the value of the street property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setStreet(String value) {
                    this.street = value;
                }

                /**
                 * Gets the value of the housenum property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getHousenum() {
                    return housenum;
                }

                /**
                 * Sets the value of the housenum property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setHousenum(String value) {
                    this.housenum = value;
                }

                /**
                 * Gets the value of the fiasHouseGuid property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getFIASHouseGuid() {
                    return fiasHouseGuid;
                }

                /**
                 * Sets the value of the fiasHouseGuid property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setFIASHouseGuid(String value) {
                    this.fiasHouseGuid = value;
                }

                /**
                 * Gets the value of the buildnum property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getBuildnum() {
                    return buildnum;
                }

                /**
                 * Sets the value of the buildnum property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setBuildnum(String value) {
                    this.buildnum = value;
                }

                /**
                 * Gets the value of the strucnum property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getStrucnum() {
                    return strucnum;
                }

                /**
                 * Sets the value of the strucnum property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setStrucnum(String value) {
                    this.strucnum = value;
                }

                /**
                 * Gets the value of the apartment property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getApartment() {
                    return apartment;
                }

                /**
                 * Sets the value of the apartment property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setApartment(String value) {
                    this.apartment = value;
                }

                /**
                 * Gets the value of the placement property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPlacement() {
                    return placement;
                }

                /**
                 * Sets the value of the placement property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPlacement(String value) {
                    this.placement = value;
                }

                /**
                 * Gets the value of the addressString property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getAddressString() {
                    return addressString;
                }

                /**
                 * Sets the value of the addressString property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setAddressString(String value) {
                    this.addressString = value;
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
             *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}INN"/>
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
                "inn"
            })
            public static class Entpr {

                @XmlElement(name = "INN", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", required = true)
                protected String inn;

                /**
                 * Gets the value of the inn property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getINN() {
                    return inn;
                }

                /**
                 * Sets the value of the inn property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setINN(String value) {
                    this.inn = value;
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
             *         &lt;element name="Name">
             *           &lt;simpleType>
             *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *               &lt;maxLength value="160"/>
             *               &lt;minLength value="1"/>
             *             &lt;/restriction>
             *           &lt;/simpleType>
             *         &lt;/element>
             *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}KPP"/>
             *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}INN"/>
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
                "name",
                "kpp",
                "inn"
            })
            public static class Legal {

                @XmlElement(name = "Name", required = true)
                protected String name;
                @XmlElement(name = "KPP", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", required = true)
                protected String kpp;
                @XmlElement(name = "INN", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", required = true)
                protected String inn;

                /**
                 * Gets the value of the name property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getName() {
                    return name;
                }

                /**
                 * Sets the value of the name property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setName(String value) {
                    this.name = value;
                }

                /**
                 * Gets the value of the kpp property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getKPP() {
                    return kpp;
                }

                /**
                 * Sets the value of the kpp property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setKPP(String value) {
                    this.kpp = value;
                }

                /**
                 * Gets the value of the inn property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getINN() {
                    return inn;
                }

                /**
                 * Sets the value of the inn property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setINN(String value) {
                    this.inn = value;
                }

            }

        }

    }

}

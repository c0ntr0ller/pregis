
package ru.gosuslugi.dom.schema.integration._8_6_0_4.disclosure;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="Overhaul">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Owner">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Name">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}String255Type">
 *                                   &lt;minLength value="1"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}INN"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="OneSqMFee" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}SmallMoneyPositiveType"/>
 *                   &lt;element name="OwnersMeetingProtocol">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                             &lt;element name="Number">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}String255Type">
 *                                   &lt;minLength value="1"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="AdditionalInfo" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/disclosure/}String1500Type" minOccurs="0"/>
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
    "overhaul"
})
@XmlRootElement(name = "Section2_4")
public class Section24 {

    @XmlElement(name = "Overhaul", required = true)
    protected Section24 .Overhaul overhaul;

    /**
     * Gets the value of the overhaul property.
     * 
     * @return
     *     possible object is
     *     {@link Section24 .Overhaul }
     *     
     */
    public Section24 .Overhaul getOverhaul() {
        return overhaul;
    }

    /**
     * Sets the value of the overhaul property.
     * 
     * @param value
     *     allowed object is
     *     {@link Section24 .Overhaul }
     *     
     */
    public void setOverhaul(Section24 .Overhaul value) {
        this.overhaul = value;
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
     *         &lt;element name="Owner">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Name">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}String255Type">
     *                         &lt;minLength value="1"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}INN"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="OneSqMFee" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}SmallMoneyPositiveType"/>
     *         &lt;element name="OwnersMeetingProtocol">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *                   &lt;element name="Number">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}String255Type">
     *                         &lt;minLength value="1"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="AdditionalInfo" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/disclosure/}String1500Type" minOccurs="0"/>
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
        "owner",
        "oneSqMFee",
        "ownersMeetingProtocol",
        "additionalInfo"
    })
    public static class Overhaul {

        @XmlElement(name = "Owner", required = true)
        protected Section24 .Overhaul.Owner owner;
        @XmlElement(name = "OneSqMFee", required = true)
        protected BigDecimal oneSqMFee;
        @XmlElement(name = "OwnersMeetingProtocol", required = true)
        protected Section24 .Overhaul.OwnersMeetingProtocol ownersMeetingProtocol;
        @XmlElement(name = "AdditionalInfo")
        protected String additionalInfo;

        /**
         * Gets the value of the owner property.
         * 
         * @return
         *     possible object is
         *     {@link Section24 .Overhaul.Owner }
         *     
         */
        public Section24 .Overhaul.Owner getOwner() {
            return owner;
        }

        /**
         * Sets the value of the owner property.
         * 
         * @param value
         *     allowed object is
         *     {@link Section24 .Overhaul.Owner }
         *     
         */
        public void setOwner(Section24 .Overhaul.Owner value) {
            this.owner = value;
        }

        /**
         * Gets the value of the oneSqMFee property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getOneSqMFee() {
            return oneSqMFee;
        }

        /**
         * Sets the value of the oneSqMFee property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setOneSqMFee(BigDecimal value) {
            this.oneSqMFee = value;
        }

        /**
         * Gets the value of the ownersMeetingProtocol property.
         * 
         * @return
         *     possible object is
         *     {@link Section24 .Overhaul.OwnersMeetingProtocol }
         *     
         */
        public Section24 .Overhaul.OwnersMeetingProtocol getOwnersMeetingProtocol() {
            return ownersMeetingProtocol;
        }

        /**
         * Sets the value of the ownersMeetingProtocol property.
         * 
         * @param value
         *     allowed object is
         *     {@link Section24 .Overhaul.OwnersMeetingProtocol }
         *     
         */
        public void setOwnersMeetingProtocol(Section24 .Overhaul.OwnersMeetingProtocol value) {
            this.ownersMeetingProtocol = value;
        }

        /**
         * Gets the value of the additionalInfo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getAdditionalInfo() {
            return additionalInfo;
        }

        /**
         * Sets the value of the additionalInfo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setAdditionalInfo(String value) {
            this.additionalInfo = value;
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
         *             &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}String255Type">
         *               &lt;minLength value="1"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}INN"/>
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
            "inn"
        })
        public static class Owner {

            @XmlElement(name = "Name", required = true)
            protected String name;
            @XmlElement(name = "INN", namespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.4/", required = true)
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
         *         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
         *         &lt;element name="Number">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.4/}String255Type">
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
            "date",
            "number"
        })
        public static class OwnersMeetingProtocol {

            @XmlElement(name = "Date", required = true)
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar date;
            @XmlElement(name = "Number", required = true)
            protected String number;

            /**
             * Gets the value of the date property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getDate() {
                return date;
            }

            /**
             * Sets the value of the date property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setDate(XMLGregorianCalendar value) {
                this.date = value;
            }

            /**
             * Gets the value of the number property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNumber() {
                return number;
            }

            /**
             * Sets the value of the number property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNumber(String value) {
                this.number = value;
            }

        }

    }

}

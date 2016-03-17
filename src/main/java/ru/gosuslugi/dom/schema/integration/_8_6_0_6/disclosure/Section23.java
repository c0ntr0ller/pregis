
package ru.gosuslugi.dom.schema.integration._8_6_0_6.disclosure;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="CommonProperty" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Name">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}String255Type">
 *                         &lt;minLength value="1"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="Purpose" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/disclosure/}String1500Type"/>
 *                   &lt;element name="Square" minOccurs="0">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                         &lt;minInclusive value="0"/>
 *                         &lt;totalDigits value="10"/>
 *                         &lt;fractionDigits value="2"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="ThirdPartyUsage" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Name">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}String255Type">
 *                                   &lt;minLength value="1"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}INN"/>
 *                             &lt;element name="Contract">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                                       &lt;element name="Number">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}String255Type">
 *                                             &lt;minLength value="1"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="ContractBeginDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                             &lt;element name="UsageCostPerMonth" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}SmallMoneyPositiveType"/>
 *                             &lt;element name="OwnersMeetingProtocol">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                                       &lt;element name="Number">
 *                                         &lt;simpleType>
 *                                           &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}String255Type">
 *                                             &lt;minLength value="1"/>
 *                                           &lt;/restriction>
 *                                         &lt;/simpleType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
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
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "commonProperty"
})
@XmlRootElement(name = "Section2_3")
public class Section23 {

    @XmlElement(name = "CommonProperty", required = true)
    protected List<Section23 .CommonProperty> commonProperty;

    /**
     * Gets the value of the commonProperty property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the commonProperty property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCommonProperty().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Section23 .CommonProperty }
     * 
     * 
     */
    public List<Section23 .CommonProperty> getCommonProperty() {
        if (commonProperty == null) {
            commonProperty = new ArrayList<Section23 .CommonProperty>();
        }
        return this.commonProperty;
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
     *             &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}String255Type">
     *               &lt;minLength value="1"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="Purpose" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/disclosure/}String1500Type"/>
     *         &lt;element name="Square" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *               &lt;minInclusive value="0"/>
     *               &lt;totalDigits value="10"/>
     *               &lt;fractionDigits value="2"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="ThirdPartyUsage" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Name">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}String255Type">
     *                         &lt;minLength value="1"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}INN"/>
     *                   &lt;element name="Contract">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *                             &lt;element name="Number">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}String255Type">
     *                                   &lt;minLength value="1"/>
     *                                 &lt;/restriction>
     *                               &lt;/simpleType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="ContractBeginDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *                   &lt;element name="UsageCostPerMonth" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}SmallMoneyPositiveType"/>
     *                   &lt;element name="OwnersMeetingProtocol">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *                             &lt;element name="Number">
     *                               &lt;simpleType>
     *                                 &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}String255Type">
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
        "purpose",
        "square",
        "thirdPartyUsage"
    })
    public static class CommonProperty {

        @XmlElement(name = "Name", required = true)
        protected String name;
        @XmlElement(name = "Purpose", required = true)
        protected String purpose;
        @XmlElement(name = "Square")
        protected BigDecimal square;
        @XmlElement(name = "ThirdPartyUsage")
        protected Section23 .CommonProperty.ThirdPartyUsage thirdPartyUsage;

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
         * Gets the value of the purpose property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPurpose() {
            return purpose;
        }

        /**
         * Sets the value of the purpose property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPurpose(String value) {
            this.purpose = value;
        }

        /**
         * Gets the value of the square property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getSquare() {
            return square;
        }

        /**
         * Sets the value of the square property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setSquare(BigDecimal value) {
            this.square = value;
        }

        /**
         * Gets the value of the thirdPartyUsage property.
         * 
         * @return
         *     possible object is
         *     {@link Section23 .CommonProperty.ThirdPartyUsage }
         *     
         */
        public Section23 .CommonProperty.ThirdPartyUsage getThirdPartyUsage() {
            return thirdPartyUsage;
        }

        /**
         * Sets the value of the thirdPartyUsage property.
         * 
         * @param value
         *     allowed object is
         *     {@link Section23 .CommonProperty.ThirdPartyUsage }
         *     
         */
        public void setThirdPartyUsage(Section23 .CommonProperty.ThirdPartyUsage value) {
            this.thirdPartyUsage = value;
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
         *             &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}String255Type">
         *               &lt;minLength value="1"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}INN"/>
         *         &lt;element name="Contract">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
         *                   &lt;element name="Number">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}String255Type">
         *                         &lt;minLength value="1"/>
         *                       &lt;/restriction>
         *                     &lt;/simpleType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="ContractBeginDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
         *         &lt;element name="UsageCostPerMonth" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}SmallMoneyPositiveType"/>
         *         &lt;element name="OwnersMeetingProtocol">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
         *                   &lt;element name="Number">
         *                     &lt;simpleType>
         *                       &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}String255Type">
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
            "name",
            "inn",
            "contract",
            "contractBeginDate",
            "usageCostPerMonth",
            "ownersMeetingProtocol"
        })
        public static class ThirdPartyUsage {

            @XmlElement(name = "Name", required = true)
            protected String name;
            @XmlElement(name = "INN", namespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.6/", required = true)
            protected String inn;
            @XmlElement(name = "Contract", required = true)
            protected Section23 .CommonProperty.ThirdPartyUsage.Contract contract;
            @XmlElement(name = "ContractBeginDate", required = true)
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar contractBeginDate;
            @XmlElement(name = "UsageCostPerMonth", required = true)
            protected BigDecimal usageCostPerMonth;
            @XmlElement(name = "OwnersMeetingProtocol", required = true)
            protected Section23 .CommonProperty.ThirdPartyUsage.OwnersMeetingProtocol ownersMeetingProtocol;

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

            /**
             * Gets the value of the contract property.
             * 
             * @return
             *     possible object is
             *     {@link Section23 .CommonProperty.ThirdPartyUsage.Contract }
             *     
             */
            public Section23 .CommonProperty.ThirdPartyUsage.Contract getContract() {
                return contract;
            }

            /**
             * Sets the value of the contract property.
             * 
             * @param value
             *     allowed object is
             *     {@link Section23 .CommonProperty.ThirdPartyUsage.Contract }
             *     
             */
            public void setContract(Section23 .CommonProperty.ThirdPartyUsage.Contract value) {
                this.contract = value;
            }

            /**
             * Gets the value of the contractBeginDate property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getContractBeginDate() {
                return contractBeginDate;
            }

            /**
             * Sets the value of the contractBeginDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setContractBeginDate(XMLGregorianCalendar value) {
                this.contractBeginDate = value;
            }

            /**
             * Gets the value of the usageCostPerMonth property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getUsageCostPerMonth() {
                return usageCostPerMonth;
            }

            /**
             * Sets the value of the usageCostPerMonth property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setUsageCostPerMonth(BigDecimal value) {
                this.usageCostPerMonth = value;
            }

            /**
             * Gets the value of the ownersMeetingProtocol property.
             * 
             * @return
             *     possible object is
             *     {@link Section23 .CommonProperty.ThirdPartyUsage.OwnersMeetingProtocol }
             *     
             */
            public Section23 .CommonProperty.ThirdPartyUsage.OwnersMeetingProtocol getOwnersMeetingProtocol() {
                return ownersMeetingProtocol;
            }

            /**
             * Sets the value of the ownersMeetingProtocol property.
             * 
             * @param value
             *     allowed object is
             *     {@link Section23 .CommonProperty.ThirdPartyUsage.OwnersMeetingProtocol }
             *     
             */
            public void setOwnersMeetingProtocol(Section23 .CommonProperty.ThirdPartyUsage.OwnersMeetingProtocol value) {
                this.ownersMeetingProtocol = value;
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
             *             &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}String255Type">
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
            public static class Contract {

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
             *             &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}String255Type">
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

}

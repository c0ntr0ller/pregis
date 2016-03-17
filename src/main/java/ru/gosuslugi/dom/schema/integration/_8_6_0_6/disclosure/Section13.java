
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
import ru.gosuslugi.dom.schema.integration._8_6_0.AttachmentType;
import ru.gosuslugi.dom.schema.integration._8_6_0.NsiRef;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/disclosure/}SubSection1ElementType">
 *       &lt;choice>
 *         &lt;element name="BringToJusticeFact" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="PersonToResponse">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;choice>
 *                               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}OGRN"/>
 *                               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}OGRNIP"/>
 *                             &lt;/choice>
 *                             &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}INN"/>
 *                             &lt;element name="KPP" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}KPPType" minOccurs="0"/>
 *                             &lt;element name="PersonKind" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}nsiRef"/>
 *                             &lt;element name="JobTitle" minOccurs="0">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;minLength value="1"/>
 *                                   &lt;maxLength value="255"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="Subject" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/disclosure/}String1500Type"/>
 *                   &lt;element name="SupervisoryAuthority" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/disclosure/}String1500Type"/>
 *                   &lt;element name="PenaltyAmount" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}SmallMoneyType"/>
 *                   &lt;element name="OffenceDoc">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}AttachmentType">
 *                           &lt;sequence>
 *                             &lt;element name="OffenceDocName" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/disclosure/}String1500Type"/>
 *                             &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                           &lt;/sequence>
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="EventsToCorrectViolations" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/disclosure/}String1500Type"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="NoBringToJusticeFact" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "bringToJusticeFact",
    "noBringToJusticeFact"
})
@XmlRootElement(name = "Section1_3")
public class Section13
    extends SubSection1ElementType
{

    @XmlElement(name = "BringToJusticeFact")
    protected List<Section13 .BringToJusticeFact> bringToJusticeFact;
    @XmlElement(name = "NoBringToJusticeFact")
    protected Boolean noBringToJusticeFact;

    /**
     * Gets the value of the bringToJusticeFact property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bringToJusticeFact property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBringToJusticeFact().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Section13 .BringToJusticeFact }
     * 
     * 
     */
    public List<Section13 .BringToJusticeFact> getBringToJusticeFact() {
        if (bringToJusticeFact == null) {
            bringToJusticeFact = new ArrayList<Section13 .BringToJusticeFact>();
        }
        return this.bringToJusticeFact;
    }

    /**
     * Gets the value of the noBringToJusticeFact property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isNoBringToJusticeFact() {
        return noBringToJusticeFact;
    }

    /**
     * Sets the value of the noBringToJusticeFact property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNoBringToJusticeFact(Boolean value) {
        this.noBringToJusticeFact = value;
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
     *         &lt;element name="PersonToResponse">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;choice>
     *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}OGRN"/>
     *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}OGRNIP"/>
     *                   &lt;/choice>
     *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}INN"/>
     *                   &lt;element name="KPP" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}KPPType" minOccurs="0"/>
     *                   &lt;element name="PersonKind" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}nsiRef"/>
     *                   &lt;element name="JobTitle" minOccurs="0">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;minLength value="1"/>
     *                         &lt;maxLength value="255"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Subject" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/disclosure/}String1500Type"/>
     *         &lt;element name="SupervisoryAuthority" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/disclosure/}String1500Type"/>
     *         &lt;element name="PenaltyAmount" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}SmallMoneyType"/>
     *         &lt;element name="OffenceDoc">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}AttachmentType">
     *                 &lt;sequence>
     *                   &lt;element name="OffenceDocName" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/disclosure/}String1500Type"/>
     *                   &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *                 &lt;/sequence>
     *               &lt;/extension>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="EventsToCorrectViolations" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/disclosure/}String1500Type"/>
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
        "personToResponse",
        "subject",
        "supervisoryAuthority",
        "penaltyAmount",
        "offenceDoc",
        "eventsToCorrectViolations"
    })
    public static class BringToJusticeFact {

        @XmlElement(name = "PersonToResponse", required = true)
        protected Section13 .BringToJusticeFact.PersonToResponse personToResponse;
        @XmlElement(name = "Subject", required = true)
        protected String subject;
        @XmlElement(name = "SupervisoryAuthority", required = true)
        protected String supervisoryAuthority;
        @XmlElement(name = "PenaltyAmount", required = true)
        protected BigDecimal penaltyAmount;
        @XmlElement(name = "OffenceDoc", required = true)
        protected Section13 .BringToJusticeFact.OffenceDoc offenceDoc;
        @XmlElement(name = "EventsToCorrectViolations", required = true)
        protected String eventsToCorrectViolations;

        /**
         * Gets the value of the personToResponse property.
         * 
         * @return
         *     possible object is
         *     {@link Section13 .BringToJusticeFact.PersonToResponse }
         *     
         */
        public Section13 .BringToJusticeFact.PersonToResponse getPersonToResponse() {
            return personToResponse;
        }

        /**
         * Sets the value of the personToResponse property.
         * 
         * @param value
         *     allowed object is
         *     {@link Section13 .BringToJusticeFact.PersonToResponse }
         *     
         */
        public void setPersonToResponse(Section13 .BringToJusticeFact.PersonToResponse value) {
            this.personToResponse = value;
        }

        /**
         * Gets the value of the subject property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSubject() {
            return subject;
        }

        /**
         * Sets the value of the subject property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSubject(String value) {
            this.subject = value;
        }

        /**
         * Gets the value of the supervisoryAuthority property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSupervisoryAuthority() {
            return supervisoryAuthority;
        }

        /**
         * Sets the value of the supervisoryAuthority property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSupervisoryAuthority(String value) {
            this.supervisoryAuthority = value;
        }

        /**
         * Gets the value of the penaltyAmount property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getPenaltyAmount() {
            return penaltyAmount;
        }

        /**
         * Sets the value of the penaltyAmount property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setPenaltyAmount(BigDecimal value) {
            this.penaltyAmount = value;
        }

        /**
         * Gets the value of the offenceDoc property.
         * 
         * @return
         *     possible object is
         *     {@link Section13 .BringToJusticeFact.OffenceDoc }
         *     
         */
        public Section13 .BringToJusticeFact.OffenceDoc getOffenceDoc() {
            return offenceDoc;
        }

        /**
         * Sets the value of the offenceDoc property.
         * 
         * @param value
         *     allowed object is
         *     {@link Section13 .BringToJusticeFact.OffenceDoc }
         *     
         */
        public void setOffenceDoc(Section13 .BringToJusticeFact.OffenceDoc value) {
            this.offenceDoc = value;
        }

        /**
         * Gets the value of the eventsToCorrectViolations property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEventsToCorrectViolations() {
            return eventsToCorrectViolations;
        }

        /**
         * Sets the value of the eventsToCorrectViolations property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEventsToCorrectViolations(String value) {
            this.eventsToCorrectViolations = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}AttachmentType">
         *       &lt;sequence>
         *         &lt;element name="OffenceDocName" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/disclosure/}String1500Type"/>
         *         &lt;element name="Date" type="{http://www.w3.org/2001/XMLSchema}date"/>
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
            "offenceDocName",
            "date"
        })
        public static class OffenceDoc
            extends AttachmentType
        {

            @XmlElement(name = "OffenceDocName", required = true)
            protected String offenceDocName;
            @XmlElement(name = "Date", required = true)
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar date;

            /**
             * Gets the value of the offenceDocName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getOffenceDocName() {
                return offenceDocName;
            }

            /**
             * Sets the value of the offenceDocName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setOffenceDocName(String value) {
                this.offenceDocName = value;
            }

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
         *         &lt;choice>
         *           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}OGRN"/>
         *           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}OGRNIP"/>
         *         &lt;/choice>
         *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}INN"/>
         *         &lt;element name="KPP" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}KPPType" minOccurs="0"/>
         *         &lt;element name="PersonKind" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}nsiRef"/>
         *         &lt;element name="JobTitle" minOccurs="0">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;minLength value="1"/>
         *               &lt;maxLength value="255"/>
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
            "ogrn",
            "ogrnip",
            "inn",
            "kpp",
            "personKind",
            "jobTitle"
        })
        public static class PersonToResponse {

            @XmlElement(name = "OGRN", namespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.6/")
            protected String ogrn;
            @XmlElement(name = "OGRNIP", namespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.6/")
            protected String ogrnip;
            @XmlElement(name = "INN", namespace = "http://dom.gosuslugi.ru/schema/integration/8.6.0.6/", required = true)
            protected String inn;
            @XmlElement(name = "KPP")
            protected String kpp;
            @XmlElement(name = "PersonKind", required = true)
            protected NsiRef personKind;
            @XmlElement(name = "JobTitle")
            protected String jobTitle;

            /**
             * Gets the value of the ogrn property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getOGRN() {
                return ogrn;
            }

            /**
             * Sets the value of the ogrn property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setOGRN(String value) {
                this.ogrn = value;
            }

            /**
             * Gets the value of the ogrnip property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getOGRNIP() {
                return ogrnip;
            }

            /**
             * Sets the value of the ogrnip property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setOGRNIP(String value) {
                this.ogrnip = value;
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
             * Gets the value of the personKind property.
             * 
             * @return
             *     possible object is
             *     {@link NsiRef }
             *     
             */
            public NsiRef getPersonKind() {
                return personKind;
            }

            /**
             * Sets the value of the personKind property.
             * 
             * @param value
             *     allowed object is
             *     {@link NsiRef }
             *     
             */
            public void setPersonKind(NsiRef value) {
                this.personKind = value;
            }

            /**
             * Gets the value of the jobTitle property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getJobTitle() {
                return jobTitle;
            }

            /**
             * Sets the value of the jobTitle property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setJobTitle(String value) {
                this.jobTitle = value;
            }

        }

    }

}

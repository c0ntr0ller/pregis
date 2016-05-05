
package ru.gosuslugi.dom.schema.integration.services.disclosure;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/disclosure/}SubSection1ElementType">
 *       &lt;sequence>
 *         &lt;element name="GeneralInformation">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Director">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Surname">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;minLength value="1"/>
 *                                   &lt;maxLength value="60"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="FirstName">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;minLength value="1"/>
 *                                   &lt;maxLength value="60"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="Patronymic">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                   &lt;minLength value="1"/>
 *                                   &lt;maxLength value="60"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="ChairmanPhones" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/disclosure/}String1000Type"/>
 *                   &lt;element name="Timetable" maxOccurs="7" minOccurs="7">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="DayTimetable">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;choice>
 *                                       &lt;element name="WorkDay">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                               &lt;sequence>
 *                                                 &lt;element name="WorkingTime" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/disclosure/}TimesType"/>
 *                                                 &lt;element name="BreakingTime" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/disclosure/}TimesType" minOccurs="0"/>
 *                                                 &lt;element name="ReceptionOfCitizens" minOccurs="0">
 *                                                   &lt;complexType>
 *                                                     &lt;complexContent>
 *                                                       &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/disclosure/}TimesType">
 *                                                         &lt;choice>
 *                                                           &lt;element name="EachWeek" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                                                           &lt;element name="Comment" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/disclosure/}String1000Type" minOccurs="0"/>
 *                                                         &lt;/choice>
 *                                                       &lt;/extension>
 *                                                     &lt;/complexContent>
 *                                                   &lt;/complexType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/restriction>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                       &lt;element name="Holiday" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                                     &lt;/choice>
 *                                     &lt;attribute name="weekDay">
 *                                       &lt;simpleType>
 *                                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                                           &lt;enumeration value="1"/>
 *                                           &lt;enumeration value="2"/>
 *                                           &lt;enumeration value="3"/>
 *                                           &lt;enumeration value="4"/>
 *                                           &lt;enumeration value="5"/>
 *                                           &lt;enumeration value="6"/>
 *                                           &lt;enumeration value="7"/>
 *                                         &lt;/restriction>
 *                                       &lt;/simpleType>
 *                                     &lt;/attribute>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="DispatchService">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;choice>
 *                               &lt;element name="Address" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}FIASHouseGUIDType"/>
 *                               &lt;element name="AOGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}GUIDType" minOccurs="0"/>
 *                             &lt;/choice>
 *                             &lt;element name="Phones" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}String255Type" minOccurs="0"/>
 *                             &lt;element name="Timetable" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/disclosure/}String1500Type" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="FederalSubjectShare">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                         &lt;minInclusive value="0"/>
 *                         &lt;fractionDigits value="2"/>
 *                         &lt;totalDigits value="5"/>
 *                         &lt;maxInclusive value="100.00"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="MunicipalityShare">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
 *                         &lt;minInclusive value="0"/>
 *                         &lt;fractionDigits value="2"/>
 *                         &lt;totalDigits value="5"/>
 *                         &lt;maxInclusive value="100.00"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="RegularStaffing">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="AdministrativeStaff">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
 *                                   &lt;minInclusive value="0"/>
 *                                   &lt;maxInclusive value="9999"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="Engineers">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
 *                                   &lt;minInclusive value="0"/>
 *                                   &lt;maxInclusive value="9999"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="Workers">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
 *                                   &lt;minInclusive value="0"/>
 *                                   &lt;maxInclusive value="9999"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="MembershipInSelfRegulatoryOrganization" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="OrgName" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/disclosure/}String1000Type"/>
 *                             &lt;element name="OGRN" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}OGRNType"/>
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
    "generalInformation"
})
@XmlRootElement(name = "Section1_1")
public class Section11
    extends SubSection1ElementType
{

    @XmlElement(name = "GeneralInformation", required = true)
    protected Section11 .GeneralInformation generalInformation;

    /**
     * Gets the value of the generalInformation property.
     * 
     * @return
     *     possible object is
     *     {@link Section11 .GeneralInformation }
     *     
     */
    public Section11 .GeneralInformation getGeneralInformation() {
        return generalInformation;
    }

    /**
     * Sets the value of the generalInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Section11 .GeneralInformation }
     *     
     */
    public void setGeneralInformation(Section11 .GeneralInformation value) {
        this.generalInformation = value;
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
     *         &lt;element name="Director">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Surname">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;minLength value="1"/>
     *                         &lt;maxLength value="60"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="FirstName">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;minLength value="1"/>
     *                         &lt;maxLength value="60"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="Patronymic">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                         &lt;minLength value="1"/>
     *                         &lt;maxLength value="60"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="ChairmanPhones" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/disclosure/}String1000Type"/>
     *         &lt;element name="Timetable" maxOccurs="7" minOccurs="7">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="DayTimetable">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;choice>
     *                             &lt;element name="WorkDay">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                                     &lt;sequence>
     *                                       &lt;element name="WorkingTime" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/disclosure/}TimesType"/>
     *                                       &lt;element name="BreakingTime" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/disclosure/}TimesType" minOccurs="0"/>
     *                                       &lt;element name="ReceptionOfCitizens" minOccurs="0">
     *                                         &lt;complexType>
     *                                           &lt;complexContent>
     *                                             &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/disclosure/}TimesType">
     *                                               &lt;choice>
     *                                                 &lt;element name="EachWeek" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *                                                 &lt;element name="Comment" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/disclosure/}String1000Type" minOccurs="0"/>
     *                                               &lt;/choice>
     *                                             &lt;/extension>
     *                                           &lt;/complexContent>
     *                                         &lt;/complexType>
     *                                       &lt;/element>
     *                                     &lt;/sequence>
     *                                   &lt;/restriction>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                             &lt;element name="Holiday" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *                           &lt;/choice>
     *                           &lt;attribute name="weekDay">
     *                             &lt;simpleType>
     *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *                                 &lt;enumeration value="1"/>
     *                                 &lt;enumeration value="2"/>
     *                                 &lt;enumeration value="3"/>
     *                                 &lt;enumeration value="4"/>
     *                                 &lt;enumeration value="5"/>
     *                                 &lt;enumeration value="6"/>
     *                                 &lt;enumeration value="7"/>
     *                               &lt;/restriction>
     *                             &lt;/simpleType>
     *                           &lt;/attribute>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="DispatchService">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;choice>
     *                     &lt;element name="Address" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}FIASHouseGUIDType"/>
     *                     &lt;element name="AOGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}GUIDType" minOccurs="0"/>
     *                   &lt;/choice>
     *                   &lt;element name="Phones" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}String255Type" minOccurs="0"/>
     *                   &lt;element name="Timetable" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/disclosure/}String1500Type" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="FederalSubjectShare">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *               &lt;minInclusive value="0"/>
     *               &lt;fractionDigits value="2"/>
     *               &lt;totalDigits value="5"/>
     *               &lt;maxInclusive value="100.00"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="MunicipalityShare">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}decimal">
     *               &lt;minInclusive value="0"/>
     *               &lt;fractionDigits value="2"/>
     *               &lt;totalDigits value="5"/>
     *               &lt;maxInclusive value="100.00"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="RegularStaffing">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="AdministrativeStaff">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
     *                         &lt;minInclusive value="0"/>
     *                         &lt;maxInclusive value="9999"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="Engineers">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
     *                         &lt;minInclusive value="0"/>
     *                         &lt;maxInclusive value="9999"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="Workers">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
     *                         &lt;minInclusive value="0"/>
     *                         &lt;maxInclusive value="9999"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="MembershipInSelfRegulatoryOrganization" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="OrgName" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/disclosure/}String1000Type"/>
     *                   &lt;element name="OGRN" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}OGRNType"/>
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
        "director",
        "chairmanPhones",
        "timetable",
        "dispatchService",
        "federalSubjectShare",
        "municipalityShare",
        "regularStaffing",
        "membershipInSelfRegulatoryOrganization"
    })
    public static class GeneralInformation {

        @XmlElement(name = "Director", required = true)
        protected Section11 .GeneralInformation.Director director;
        @XmlElement(name = "ChairmanPhones", required = true)
        protected String chairmanPhones;
        @XmlElement(name = "Timetable", required = true)
        protected List<Section11 .GeneralInformation.Timetable> timetable;
        @XmlElement(name = "DispatchService", required = true)
        protected Section11 .GeneralInformation.DispatchService dispatchService;
        @XmlElement(name = "FederalSubjectShare", required = true)
        protected BigDecimal federalSubjectShare;
        @XmlElement(name = "MunicipalityShare", required = true)
        protected BigDecimal municipalityShare;
        @XmlElement(name = "RegularStaffing", required = true)
        protected Section11 .GeneralInformation.RegularStaffing regularStaffing;
        @XmlElement(name = "MembershipInSelfRegulatoryOrganization")
        protected Section11 .GeneralInformation.MembershipInSelfRegulatoryOrganization membershipInSelfRegulatoryOrganization;

        /**
         * Gets the value of the director property.
         * 
         * @return
         *     possible object is
         *     {@link Section11 .GeneralInformation.Director }
         *     
         */
        public Section11 .GeneralInformation.Director getDirector() {
            return director;
        }

        /**
         * Sets the value of the director property.
         * 
         * @param value
         *     allowed object is
         *     {@link Section11 .GeneralInformation.Director }
         *     
         */
        public void setDirector(Section11 .GeneralInformation.Director value) {
            this.director = value;
        }

        /**
         * Gets the value of the chairmanPhones property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getChairmanPhones() {
            return chairmanPhones;
        }

        /**
         * Sets the value of the chairmanPhones property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setChairmanPhones(String value) {
            this.chairmanPhones = value;
        }

        /**
         * Gets the value of the timetable property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the timetable property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTimetable().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Section11 .GeneralInformation.Timetable }
         * 
         * 
         */
        public List<Section11 .GeneralInformation.Timetable> getTimetable() {
            if (timetable == null) {
                timetable = new ArrayList<Section11 .GeneralInformation.Timetable>();
            }
            return this.timetable;
        }

        /**
         * Gets the value of the dispatchService property.
         * 
         * @return
         *     possible object is
         *     {@link Section11 .GeneralInformation.DispatchService }
         *     
         */
        public Section11 .GeneralInformation.DispatchService getDispatchService() {
            return dispatchService;
        }

        /**
         * Sets the value of the dispatchService property.
         * 
         * @param value
         *     allowed object is
         *     {@link Section11 .GeneralInformation.DispatchService }
         *     
         */
        public void setDispatchService(Section11 .GeneralInformation.DispatchService value) {
            this.dispatchService = value;
        }

        /**
         * Gets the value of the federalSubjectShare property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getFederalSubjectShare() {
            return federalSubjectShare;
        }

        /**
         * Sets the value of the federalSubjectShare property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setFederalSubjectShare(BigDecimal value) {
            this.federalSubjectShare = value;
        }

        /**
         * Gets the value of the municipalityShare property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getMunicipalityShare() {
            return municipalityShare;
        }

        /**
         * Sets the value of the municipalityShare property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setMunicipalityShare(BigDecimal value) {
            this.municipalityShare = value;
        }

        /**
         * Gets the value of the regularStaffing property.
         * 
         * @return
         *     possible object is
         *     {@link Section11 .GeneralInformation.RegularStaffing }
         *     
         */
        public Section11 .GeneralInformation.RegularStaffing getRegularStaffing() {
            return regularStaffing;
        }

        /**
         * Sets the value of the regularStaffing property.
         * 
         * @param value
         *     allowed object is
         *     {@link Section11 .GeneralInformation.RegularStaffing }
         *     
         */
        public void setRegularStaffing(Section11 .GeneralInformation.RegularStaffing value) {
            this.regularStaffing = value;
        }

        /**
         * Gets the value of the membershipInSelfRegulatoryOrganization property.
         * 
         * @return
         *     possible object is
         *     {@link Section11 .GeneralInformation.MembershipInSelfRegulatoryOrganization }
         *     
         */
        public Section11 .GeneralInformation.MembershipInSelfRegulatoryOrganization getMembershipInSelfRegulatoryOrganization() {
            return membershipInSelfRegulatoryOrganization;
        }

        /**
         * Sets the value of the membershipInSelfRegulatoryOrganization property.
         * 
         * @param value
         *     allowed object is
         *     {@link Section11 .GeneralInformation.MembershipInSelfRegulatoryOrganization }
         *     
         */
        public void setMembershipInSelfRegulatoryOrganization(Section11 .GeneralInformation.MembershipInSelfRegulatoryOrganization value) {
            this.membershipInSelfRegulatoryOrganization = value;
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
         *         &lt;element name="Surname">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;minLength value="1"/>
         *               &lt;maxLength value="60"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="FirstName">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;minLength value="1"/>
         *               &lt;maxLength value="60"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="Patronymic">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *               &lt;minLength value="1"/>
         *               &lt;maxLength value="60"/>
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
            "surname",
            "firstName",
            "patronymic"
        })
        public static class Director {

            @XmlElement(name = "Surname", required = true)
            protected String surname;
            @XmlElement(name = "FirstName", required = true)
            protected String firstName;
            @XmlElement(name = "Patronymic", required = true)
            protected String patronymic;

            /**
             * Gets the value of the surname property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSurname() {
                return surname;
            }

            /**
             * Sets the value of the surname property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSurname(String value) {
                this.surname = value;
            }

            /**
             * Gets the value of the firstName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFirstName() {
                return firstName;
            }

            /**
             * Sets the value of the firstName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFirstName(String value) {
                this.firstName = value;
            }

            /**
             * Gets the value of the patronymic property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPatronymic() {
                return patronymic;
            }

            /**
             * Sets the value of the patronymic property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPatronymic(String value) {
                this.patronymic = value;
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
         *           &lt;element name="Address" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}FIASHouseGUIDType"/>
         *           &lt;element name="AOGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}GUIDType" minOccurs="0"/>
         *         &lt;/choice>
         *         &lt;element name="Phones" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}String255Type" minOccurs="0"/>
         *         &lt;element name="Timetable" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/disclosure/}String1500Type" minOccurs="0"/>
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
            "address",
            "aoguid",
            "phones",
            "timetable"
        })
        public static class DispatchService {

            @XmlElement(name = "Address")
            protected String address;
            @XmlElement(name = "AOGUID")
            protected String aoguid;
            @XmlElement(name = "Phones")
            protected String phones;
            @XmlElement(name = "Timetable")
            protected String timetable;

            /**
             * Gets the value of the address property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAddress() {
                return address;
            }

            /**
             * Sets the value of the address property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAddress(String value) {
                this.address = value;
            }

            /**
             * Gets the value of the aoguid property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getAOGUID() {
                return aoguid;
            }

            /**
             * Sets the value of the aoguid property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setAOGUID(String value) {
                this.aoguid = value;
            }

            /**
             * Gets the value of the phones property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPhones() {
                return phones;
            }

            /**
             * Sets the value of the phones property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPhones(String value) {
                this.phones = value;
            }

            /**
             * Gets the value of the timetable property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTimetable() {
                return timetable;
            }

            /**
             * Sets the value of the timetable property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTimetable(String value) {
                this.timetable = value;
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
         *         &lt;element name="OrgName" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/disclosure/}String1000Type"/>
         *         &lt;element name="OGRN" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/}OGRNType"/>
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
            "orgName",
            "ogrn"
        })
        public static class MembershipInSelfRegulatoryOrganization {

            @XmlElement(name = "OrgName", required = true)
            protected String orgName;
            @XmlElement(name = "OGRN", required = true)
            protected String ogrn;

            /**
             * Gets the value of the orgName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getOrgName() {
                return orgName;
            }

            /**
             * Sets the value of the orgName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setOrgName(String value) {
                this.orgName = value;
            }

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
         *         &lt;element name="AdministrativeStaff">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
         *               &lt;minInclusive value="0"/>
         *               &lt;maxInclusive value="9999"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="Engineers">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
         *               &lt;minInclusive value="0"/>
         *               &lt;maxInclusive value="9999"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="Workers">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
         *               &lt;minInclusive value="0"/>
         *               &lt;maxInclusive value="9999"/>
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
            "administrativeStaff",
            "engineers",
            "workers"
        })
        public static class RegularStaffing {

            @XmlElement(name = "AdministrativeStaff")
            protected int administrativeStaff;
            @XmlElement(name = "Engineers")
            protected int engineers;
            @XmlElement(name = "Workers")
            protected int workers;

            /**
             * Gets the value of the administrativeStaff property.
             * 
             */
            public int getAdministrativeStaff() {
                return administrativeStaff;
            }

            /**
             * Sets the value of the administrativeStaff property.
             * 
             */
            public void setAdministrativeStaff(int value) {
                this.administrativeStaff = value;
            }

            /**
             * Gets the value of the engineers property.
             * 
             */
            public int getEngineers() {
                return engineers;
            }

            /**
             * Sets the value of the engineers property.
             * 
             */
            public void setEngineers(int value) {
                this.engineers = value;
            }

            /**
             * Gets the value of the workers property.
             * 
             */
            public int getWorkers() {
                return workers;
            }

            /**
             * Sets the value of the workers property.
             * 
             */
            public void setWorkers(int value) {
                this.workers = value;
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
         *         &lt;element name="DayTimetable">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;choice>
         *                   &lt;element name="WorkDay">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                           &lt;sequence>
         *                             &lt;element name="WorkingTime" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/disclosure/}TimesType"/>
         *                             &lt;element name="BreakingTime" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/disclosure/}TimesType" minOccurs="0"/>
         *                             &lt;element name="ReceptionOfCitizens" minOccurs="0">
         *                               &lt;complexType>
         *                                 &lt;complexContent>
         *                                   &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/disclosure/}TimesType">
         *                                     &lt;choice>
         *                                       &lt;element name="EachWeek" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
         *                                       &lt;element name="Comment" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/disclosure/}String1000Type" minOccurs="0"/>
         *                                     &lt;/choice>
         *                                   &lt;/extension>
         *                                 &lt;/complexContent>
         *                               &lt;/complexType>
         *                             &lt;/element>
         *                           &lt;/sequence>
         *                         &lt;/restriction>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                   &lt;element name="Holiday" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
         *                 &lt;/choice>
         *                 &lt;attribute name="weekDay">
         *                   &lt;simpleType>
         *                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
         *                       &lt;enumeration value="1"/>
         *                       &lt;enumeration value="2"/>
         *                       &lt;enumeration value="3"/>
         *                       &lt;enumeration value="4"/>
         *                       &lt;enumeration value="5"/>
         *                       &lt;enumeration value="6"/>
         *                       &lt;enumeration value="7"/>
         *                     &lt;/restriction>
         *                   &lt;/simpleType>
         *                 &lt;/attribute>
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
            "dayTimetable"
        })
        public static class Timetable {

            @XmlElement(name = "DayTimetable", required = true)
            protected Section11 .GeneralInformation.Timetable.DayTimetable dayTimetable;

            /**
             * Gets the value of the dayTimetable property.
             * 
             * @return
             *     possible object is
             *     {@link Section11 .GeneralInformation.Timetable.DayTimetable }
             *     
             */
            public Section11 .GeneralInformation.Timetable.DayTimetable getDayTimetable() {
                return dayTimetable;
            }

            /**
             * Sets the value of the dayTimetable property.
             * 
             * @param value
             *     allowed object is
             *     {@link Section11 .GeneralInformation.Timetable.DayTimetable }
             *     
             */
            public void setDayTimetable(Section11 .GeneralInformation.Timetable.DayTimetable value) {
                this.dayTimetable = value;
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
             *       &lt;choice>
             *         &lt;element name="WorkDay">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *                 &lt;sequence>
             *                   &lt;element name="WorkingTime" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/disclosure/}TimesType"/>
             *                   &lt;element name="BreakingTime" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/disclosure/}TimesType" minOccurs="0"/>
             *                   &lt;element name="ReceptionOfCitizens" minOccurs="0">
             *                     &lt;complexType>
             *                       &lt;complexContent>
             *                         &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/disclosure/}TimesType">
             *                           &lt;choice>
             *                             &lt;element name="EachWeek" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
             *                             &lt;element name="Comment" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/disclosure/}String1000Type" minOccurs="0"/>
             *                           &lt;/choice>
             *                         &lt;/extension>
             *                       &lt;/complexContent>
             *                     &lt;/complexType>
             *                   &lt;/element>
             *                 &lt;/sequence>
             *               &lt;/restriction>
             *             &lt;/complexContent>
             *           &lt;/complexType>
             *         &lt;/element>
             *         &lt;element name="Holiday" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
             *       &lt;/choice>
             *       &lt;attribute name="weekDay">
             *         &lt;simpleType>
             *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
             *             &lt;enumeration value="1"/>
             *             &lt;enumeration value="2"/>
             *             &lt;enumeration value="3"/>
             *             &lt;enumeration value="4"/>
             *             &lt;enumeration value="5"/>
             *             &lt;enumeration value="6"/>
             *             &lt;enumeration value="7"/>
             *           &lt;/restriction>
             *         &lt;/simpleType>
             *       &lt;/attribute>
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "workDay",
                "holiday"
            })
            public static class DayTimetable {

                @XmlElement(name = "WorkDay")
                protected Section11 .GeneralInformation.Timetable.DayTimetable.WorkDay workDay;
                @XmlElement(name = "Holiday")
                protected Boolean holiday;
                @XmlAttribute(name = "weekDay")
                protected String weekDay;

                /**
                 * Gets the value of the workDay property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Section11 .GeneralInformation.Timetable.DayTimetable.WorkDay }
                 *     
                 */
                public Section11 .GeneralInformation.Timetable.DayTimetable.WorkDay getWorkDay() {
                    return workDay;
                }

                /**
                 * Sets the value of the workDay property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Section11 .GeneralInformation.Timetable.DayTimetable.WorkDay }
                 *     
                 */
                public void setWorkDay(Section11 .GeneralInformation.Timetable.DayTimetable.WorkDay value) {
                    this.workDay = value;
                }

                /**
                 * Gets the value of the holiday property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Boolean }
                 *     
                 */
                public Boolean isHoliday() {
                    return holiday;
                }

                /**
                 * Sets the value of the holiday property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Boolean }
                 *     
                 */
                public void setHoliday(Boolean value) {
                    this.holiday = value;
                }

                /**
                 * Gets the value of the weekDay property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getWeekDay() {
                    return weekDay;
                }

                /**
                 * Sets the value of the weekDay property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setWeekDay(String value) {
                    this.weekDay = value;
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
                 *         &lt;element name="WorkingTime" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/disclosure/}TimesType"/>
                 *         &lt;element name="BreakingTime" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/disclosure/}TimesType" minOccurs="0"/>
                 *         &lt;element name="ReceptionOfCitizens" minOccurs="0">
                 *           &lt;complexType>
                 *             &lt;complexContent>
                 *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/disclosure/}TimesType">
                 *                 &lt;choice>
                 *                   &lt;element name="EachWeek" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
                 *                   &lt;element name="Comment" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/disclosure/}String1000Type" minOccurs="0"/>
                 *                 &lt;/choice>
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
                @XmlType(name = "", propOrder = {
                    "workingTime",
                    "breakingTime",
                    "receptionOfCitizens"
                })
                public static class WorkDay {

                    @XmlElement(name = "WorkingTime", required = true)
                    protected TimesType workingTime;
                    @XmlElement(name = "BreakingTime")
                    protected TimesType breakingTime;
                    @XmlElement(name = "ReceptionOfCitizens")
                    protected Section11 .GeneralInformation.Timetable.DayTimetable.WorkDay.ReceptionOfCitizens receptionOfCitizens;

                    /**
                     * Gets the value of the workingTime property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link TimesType }
                     *     
                     */
                    public TimesType getWorkingTime() {
                        return workingTime;
                    }

                    /**
                     * Sets the value of the workingTime property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link TimesType }
                     *     
                     */
                    public void setWorkingTime(TimesType value) {
                        this.workingTime = value;
                    }

                    /**
                     * Gets the value of the breakingTime property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link TimesType }
                     *     
                     */
                    public TimesType getBreakingTime() {
                        return breakingTime;
                    }

                    /**
                     * Sets the value of the breakingTime property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link TimesType }
                     *     
                     */
                    public void setBreakingTime(TimesType value) {
                        this.breakingTime = value;
                    }

                    /**
                     * Gets the value of the receptionOfCitizens property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link Section11 .GeneralInformation.Timetable.DayTimetable.WorkDay.ReceptionOfCitizens }
                     *     
                     */
                    public Section11 .GeneralInformation.Timetable.DayTimetable.WorkDay.ReceptionOfCitizens getReceptionOfCitizens() {
                        return receptionOfCitizens;
                    }

                    /**
                     * Sets the value of the receptionOfCitizens property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link Section11 .GeneralInformation.Timetable.DayTimetable.WorkDay.ReceptionOfCitizens }
                     *     
                     */
                    public void setReceptionOfCitizens(Section11 .GeneralInformation.Timetable.DayTimetable.WorkDay.ReceptionOfCitizens value) {
                        this.receptionOfCitizens = value;
                    }


                    /**
                     * <p>Java class for anonymous complex type.
                     * 
                     * <p>The following schema fragment specifies the expected content contained within this class.
                     * 
                     * <pre>
                     * &lt;complexType>
                     *   &lt;complexContent>
                     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/disclosure/}TimesType">
                     *       &lt;choice>
                     *         &lt;element name="EachWeek" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
                     *         &lt;element name="Comment" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.7/disclosure/}String1000Type" minOccurs="0"/>
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
                        "eachWeek",
                        "comment"
                    })
                    public static class ReceptionOfCitizens
                        extends TimesType
                    {

                        @XmlElement(name = "EachWeek")
                        protected Boolean eachWeek;
                        @XmlElement(name = "Comment")
                        protected String comment;

                        /**
                         * Gets the value of the eachWeek property.
                         * 
                         * @return
                         *     possible object is
                         *     {@link Boolean }
                         *     
                         */
                        public Boolean isEachWeek() {
                            return eachWeek;
                        }

                        /**
                         * Sets the value of the eachWeek property.
                         * 
                         * @param value
                         *     allowed object is
                         *     {@link Boolean }
                         *     
                         */
                        public void setEachWeek(Boolean value) {
                            this.eachWeek = value;
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

                }

            }

        }

    }

}

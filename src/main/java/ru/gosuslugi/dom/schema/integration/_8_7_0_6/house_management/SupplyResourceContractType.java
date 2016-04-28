
package ru.gosuslugi.dom.schema.integration._8_7_0_6.house_management;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import ru.gosuslugi.dom.schema.integration._8_7_0.AttachmentType;
import ru.gosuslugi.dom.schema.integration._8_7_0.IndType;
import ru.gosuslugi.dom.schema.integration._8_7_0.NsiRef;
import ru.gosuslugi.dom.schema.integration._8_7_0.RegOrgType;


/**
 * Договор ресурсоснабжения
 * 
 * <p>Java class for SupplyResourceContractType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SupplyResourceContractType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ComptetionDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="ContractSubject" maxOccurs="100">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ServiceType">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}nsiRef">
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="MunicipalResource">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}nsiRef">
 *                         &lt;/extension>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="HeatingSystemType" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/house-management/}HeatingSystemType" minOccurs="0"/>
 *                   &lt;element name="ConnectionScheme" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/house-management/}ConnectionSchemeType" minOccurs="0"/>
 *                   &lt;element name="StartSupplyDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                   &lt;element name="EndSupplyDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                   &lt;element name="Quality" maxOccurs="100" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="QualityIndicator" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}nsiRef"/>
 *                             &lt;element name="IndicatorValue">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;choice>
 *                                         &lt;element name="Number" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}VolumeType"/>
 *                                         &lt;sequence>
 *                                           &lt;element name="StartRange" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}VolumeType"/>
 *                                           &lt;element name="EndRange" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}VolumeType"/>
 *                                         &lt;/sequence>
 *                                       &lt;/choice>
 *                                       &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}OKEI"/>
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
 *                   &lt;element name="OtherQualityIndicator" maxOccurs="100" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="IndicatorName" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}String250Type"/>
 *                             &lt;element name="Number" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}VolumeType"/>
 *                             &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}OKEI"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="PlannedVolume" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Volume" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}VolumeType"/>
 *                             &lt;element name="Unit" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}OKEIType"/>
 *                             &lt;element name="FeedingMode">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}String250Type">
 *                                   &lt;maxLength value="250"/>
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
 *         &lt;element name="Period">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Start">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="StartDate">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
 *                                   &lt;maxInclusive value="30"/>
 *                                   &lt;minInclusive value="1"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="NextMonth" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="End">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="EndDate">
 *                               &lt;simpleType>
 *                                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
 *                                   &lt;maxInclusive value="30"/>
 *                                   &lt;minInclusive value="1"/>
 *                                 &lt;/restriction>
 *                               &lt;/simpleType>
 *                             &lt;/element>
 *                             &lt;element name="NextMonth" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
 *         &lt;element name="ContractBase" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}nsiRef" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="Offer" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *           &lt;element name="AgentOwners">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;choice>
 *                     &lt;element name="RegOrg" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}RegOrgType"/>
 *                     &lt;element name="Ind" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}IndType"/>
 *                   &lt;/choice>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="Owner">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;choice>
 *                     &lt;element name="RegOrg" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}RegOrgType"/>
 *                     &lt;element name="Ind">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}IndType">
 *                           &lt;/extension>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                   &lt;/choice>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="Renter">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;choice>
 *                     &lt;element name="RegOrg" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}RegOrgType"/>
 *                     &lt;element name="Ind">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}IndType">
 *                           &lt;/extension>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                   &lt;/choice>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="Organization">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}RegOrgType">
 *                 &lt;/extension>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/choice>
 *         &lt;element name="CountingResource" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="R"/>
 *               &lt;enumeration value="P"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ObjectAddress" maxOccurs="1000" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/house-management/}ObjectAddressType">
 *                 &lt;sequence>
 *                   &lt;element name="Pair" maxOccurs="100">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="ServiceType">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}nsiRef">
 *                                   &lt;/extension>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="MunicipalResource">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}nsiRef">
 *                                   &lt;/extension>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="StartSupplyDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                             &lt;element name="EndSupplyDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="TemperatureChart" maxOccurs="1000" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="OutsideTemperature" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *                   &lt;element name="FlowLineTemperature" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *                   &lt;element name="OppositeLineTemperature" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;choice>
 *           &lt;element name="IsContract">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="ContractNumber">
 *                       &lt;simpleType>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                           &lt;minLength value="1"/>
 *                           &lt;maxLength value="30"/>
 *                         &lt;/restriction>
 *                       &lt;/simpleType>
 *                     &lt;/element>
 *                     &lt;element name="SigningDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                     &lt;element name="EffectiveDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                     &lt;element name="ContractAttachment" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}AttachmentType" maxOccurs="100"/>
 *                     &lt;sequence>
 *                       &lt;element name="BillingDate">
 *                         &lt;simpleType>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
 *                             &lt;maxInclusive value="30"/>
 *                             &lt;minInclusive value="1"/>
 *                           &lt;/restriction>
 *                         &lt;/simpleType>
 *                       &lt;/element>
 *                       &lt;element name="PaymentDate">
 *                         &lt;simpleType>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
 *                             &lt;maxInclusive value="30"/>
 *                             &lt;minInclusive value="1"/>
 *                           &lt;/restriction>
 *                         &lt;/simpleType>
 *                       &lt;/element>
 *                       &lt;element name="ProvidingInformationDate">
 *                         &lt;simpleType>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
 *                             &lt;minInclusive value="1"/>
 *                             &lt;maxInclusive value="30"/>
 *                           &lt;/restriction>
 *                         &lt;/simpleType>
 *                       &lt;/element>
 *                     &lt;/sequence>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *           &lt;element name="IsNotContract">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="ContractNumber" minOccurs="0">
 *                       &lt;simpleType>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *                           &lt;minLength value="1"/>
 *                           &lt;maxLength value="30"/>
 *                         &lt;/restriction>
 *                       &lt;/simpleType>
 *                     &lt;/element>
 *                     &lt;element name="SigningDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                     &lt;element name="EffectiveDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *                     &lt;element name="ContractAttachment" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}AttachmentType" maxOccurs="100" minOccurs="0"/>
 *                     &lt;sequence>
 *                       &lt;element name="BillingDate" minOccurs="0">
 *                         &lt;simpleType>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
 *                             &lt;maxInclusive value="30"/>
 *                             &lt;minInclusive value="1"/>
 *                           &lt;/restriction>
 *                         &lt;/simpleType>
 *                       &lt;/element>
 *                       &lt;element name="PaymentDate" minOccurs="0">
 *                         &lt;simpleType>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
 *                             &lt;maxInclusive value="30"/>
 *                             &lt;minInclusive value="1"/>
 *                           &lt;/restriction>
 *                         &lt;/simpleType>
 *                       &lt;/element>
 *                       &lt;element name="ProvidingInformationDate" minOccurs="0">
 *                         &lt;simpleType>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
 *                             &lt;minInclusive value="1"/>
 *                             &lt;maxInclusive value="30"/>
 *                           &lt;/restriction>
 *                         &lt;/simpleType>
 *                       &lt;/element>
 *                     &lt;/sequence>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
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
@XmlType(name = "SupplyResourceContractType", propOrder = {
    "comptetionDate",
    "contractSubject",
    "period",
    "contractBase",
    "offer",
    "agentOwners",
    "owner",
    "renter",
    "organization",
    "countingResource",
    "objectAddress",
    "temperatureChart",
    "isContract",
    "isNotContract"
})
@XmlSeeAlso({
    ExportSupplyResourceContractResultType.class
})
public class SupplyResourceContractType {

    @XmlElement(name = "ComptetionDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar comptetionDate;
    @XmlElement(name = "ContractSubject", required = true)
    protected List<SupplyResourceContractType.ContractSubject> contractSubject;
    @XmlElement(name = "Period", required = true)
    protected SupplyResourceContractType.Period period;
    @XmlElement(name = "ContractBase")
    protected NsiRef contractBase;
    @XmlElement(name = "Offer")
    protected Boolean offer;
    @XmlElement(name = "AgentOwners")
    protected SupplyResourceContractType.AgentOwners agentOwners;
    @XmlElement(name = "Owner")
    protected SupplyResourceContractType.Owner owner;
    @XmlElement(name = "Renter")
    protected SupplyResourceContractType.Renter renter;
    @XmlElement(name = "Organization")
    protected SupplyResourceContractType.Organization organization;
    @XmlElement(name = "CountingResource")
    protected String countingResource;
    @XmlElement(name = "ObjectAddress")
    protected List<SupplyResourceContractType.ObjectAddress> objectAddress;
    @XmlElement(name = "TemperatureChart")
    protected List<SupplyResourceContractType.TemperatureChart> temperatureChart;
    @XmlElement(name = "IsContract")
    protected SupplyResourceContractType.IsContract isContract;
    @XmlElement(name = "IsNotContract")
    protected SupplyResourceContractType.IsNotContract isNotContract;

    /**
     * Gets the value of the comptetionDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getComptetionDate() {
        return comptetionDate;
    }

    /**
     * Sets the value of the comptetionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setComptetionDate(XMLGregorianCalendar value) {
        this.comptetionDate = value;
    }

    /**
     * Gets the value of the contractSubject property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contractSubject property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContractSubject().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SupplyResourceContractType.ContractSubject }
     * 
     * 
     */
    public List<SupplyResourceContractType.ContractSubject> getContractSubject() {
        if (contractSubject == null) {
            contractSubject = new ArrayList<SupplyResourceContractType.ContractSubject>();
        }
        return this.contractSubject;
    }

    /**
     * Gets the value of the period property.
     * 
     * @return
     *     possible object is
     *     {@link SupplyResourceContractType.Period }
     *     
     */
    public SupplyResourceContractType.Period getPeriod() {
        return period;
    }

    /**
     * Sets the value of the period property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplyResourceContractType.Period }
     *     
     */
    public void setPeriod(SupplyResourceContractType.Period value) {
        this.period = value;
    }

    /**
     * Gets the value of the contractBase property.
     * 
     * @return
     *     possible object is
     *     {@link NsiRef }
     *     
     */
    public NsiRef getContractBase() {
        return contractBase;
    }

    /**
     * Sets the value of the contractBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link NsiRef }
     *     
     */
    public void setContractBase(NsiRef value) {
        this.contractBase = value;
    }

    /**
     * Gets the value of the offer property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isOffer() {
        return offer;
    }

    /**
     * Sets the value of the offer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOffer(Boolean value) {
        this.offer = value;
    }

    /**
     * Gets the value of the agentOwners property.
     * 
     * @return
     *     possible object is
     *     {@link SupplyResourceContractType.AgentOwners }
     *     
     */
    public SupplyResourceContractType.AgentOwners getAgentOwners() {
        return agentOwners;
    }

    /**
     * Sets the value of the agentOwners property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplyResourceContractType.AgentOwners }
     *     
     */
    public void setAgentOwners(SupplyResourceContractType.AgentOwners value) {
        this.agentOwners = value;
    }

    /**
     * Gets the value of the owner property.
     * 
     * @return
     *     possible object is
     *     {@link SupplyResourceContractType.Owner }
     *     
     */
    public SupplyResourceContractType.Owner getOwner() {
        return owner;
    }

    /**
     * Sets the value of the owner property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplyResourceContractType.Owner }
     *     
     */
    public void setOwner(SupplyResourceContractType.Owner value) {
        this.owner = value;
    }

    /**
     * Gets the value of the renter property.
     * 
     * @return
     *     possible object is
     *     {@link SupplyResourceContractType.Renter }
     *     
     */
    public SupplyResourceContractType.Renter getRenter() {
        return renter;
    }

    /**
     * Sets the value of the renter property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplyResourceContractType.Renter }
     *     
     */
    public void setRenter(SupplyResourceContractType.Renter value) {
        this.renter = value;
    }

    /**
     * Gets the value of the organization property.
     * 
     * @return
     *     possible object is
     *     {@link SupplyResourceContractType.Organization }
     *     
     */
    public SupplyResourceContractType.Organization getOrganization() {
        return organization;
    }

    /**
     * Sets the value of the organization property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplyResourceContractType.Organization }
     *     
     */
    public void setOrganization(SupplyResourceContractType.Organization value) {
        this.organization = value;
    }

    /**
     * Gets the value of the countingResource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountingResource() {
        return countingResource;
    }

    /**
     * Sets the value of the countingResource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountingResource(String value) {
        this.countingResource = value;
    }

    /**
     * Gets the value of the objectAddress property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the objectAddress property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getObjectAddress().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SupplyResourceContractType.ObjectAddress }
     * 
     * 
     */
    public List<SupplyResourceContractType.ObjectAddress> getObjectAddress() {
        if (objectAddress == null) {
            objectAddress = new ArrayList<SupplyResourceContractType.ObjectAddress>();
        }
        return this.objectAddress;
    }

    /**
     * Gets the value of the temperatureChart property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the temperatureChart property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTemperatureChart().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SupplyResourceContractType.TemperatureChart }
     * 
     * 
     */
    public List<SupplyResourceContractType.TemperatureChart> getTemperatureChart() {
        if (temperatureChart == null) {
            temperatureChart = new ArrayList<SupplyResourceContractType.TemperatureChart>();
        }
        return this.temperatureChart;
    }

    /**
     * Gets the value of the isContract property.
     * 
     * @return
     *     possible object is
     *     {@link SupplyResourceContractType.IsContract }
     *     
     */
    public SupplyResourceContractType.IsContract getIsContract() {
        return isContract;
    }

    /**
     * Sets the value of the isContract property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplyResourceContractType.IsContract }
     *     
     */
    public void setIsContract(SupplyResourceContractType.IsContract value) {
        this.isContract = value;
    }

    /**
     * Gets the value of the isNotContract property.
     * 
     * @return
     *     possible object is
     *     {@link SupplyResourceContractType.IsNotContract }
     *     
     */
    public SupplyResourceContractType.IsNotContract getIsNotContract() {
        return isNotContract;
    }

    /**
     * Sets the value of the isNotContract property.
     * 
     * @param value
     *     allowed object is
     *     {@link SupplyResourceContractType.IsNotContract }
     *     
     */
    public void setIsNotContract(SupplyResourceContractType.IsNotContract value) {
        this.isNotContract = value;
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
     *         &lt;element name="RegOrg" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}RegOrgType"/>
     *         &lt;element name="Ind" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}IndType"/>
     *       &lt;/choice>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "regOrg",
        "ind"
    })
    public static class AgentOwners {

        @XmlElement(name = "RegOrg")
        protected RegOrgType regOrg;
        @XmlElement(name = "Ind")
        protected IndType ind;

        /**
         * Gets the value of the regOrg property.
         * 
         * @return
         *     possible object is
         *     {@link RegOrgType }
         *     
         */
        public RegOrgType getRegOrg() {
            return regOrg;
        }

        /**
         * Sets the value of the regOrg property.
         * 
         * @param value
         *     allowed object is
         *     {@link RegOrgType }
         *     
         */
        public void setRegOrg(RegOrgType value) {
            this.regOrg = value;
        }

        /**
         * Gets the value of the ind property.
         * 
         * @return
         *     possible object is
         *     {@link IndType }
         *     
         */
        public IndType getInd() {
            return ind;
        }

        /**
         * Sets the value of the ind property.
         * 
         * @param value
         *     allowed object is
         *     {@link IndType }
         *     
         */
        public void setInd(IndType value) {
            this.ind = value;
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
     *         &lt;element name="ServiceType">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}nsiRef">
     *               &lt;/extension>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="MunicipalResource">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}nsiRef">
     *               &lt;/extension>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="HeatingSystemType" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/house-management/}HeatingSystemType" minOccurs="0"/>
     *         &lt;element name="ConnectionScheme" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/house-management/}ConnectionSchemeType" minOccurs="0"/>
     *         &lt;element name="StartSupplyDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *         &lt;element name="EndSupplyDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *         &lt;element name="Quality" maxOccurs="100" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="QualityIndicator" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}nsiRef"/>
     *                   &lt;element name="IndicatorValue">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;choice>
     *                               &lt;element name="Number" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}VolumeType"/>
     *                               &lt;sequence>
     *                                 &lt;element name="StartRange" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}VolumeType"/>
     *                                 &lt;element name="EndRange" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}VolumeType"/>
     *                               &lt;/sequence>
     *                             &lt;/choice>
     *                             &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}OKEI"/>
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
     *         &lt;element name="OtherQualityIndicator" maxOccurs="100" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="IndicatorName" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}String250Type"/>
     *                   &lt;element name="Number" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}VolumeType"/>
     *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}OKEI"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="PlannedVolume" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Volume" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}VolumeType"/>
     *                   &lt;element name="Unit" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}OKEIType"/>
     *                   &lt;element name="FeedingMode">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}String250Type">
     *                         &lt;maxLength value="250"/>
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
        "serviceType",
        "municipalResource",
        "heatingSystemType",
        "connectionScheme",
        "startSupplyDate",
        "endSupplyDate",
        "quality",
        "otherQualityIndicator",
        "plannedVolume"
    })
    public static class ContractSubject {

        @XmlElement(name = "ServiceType", required = true)
        protected SupplyResourceContractType.ContractSubject.ServiceType serviceType;
        @XmlElement(name = "MunicipalResource", required = true)
        protected SupplyResourceContractType.ContractSubject.MunicipalResource municipalResource;
        @XmlElement(name = "HeatingSystemType")
        @XmlSchemaType(name = "string")
        protected HeatingSystemType heatingSystemType;
        @XmlElement(name = "ConnectionScheme")
        @XmlSchemaType(name = "string")
        protected ConnectionSchemeType connectionScheme;
        @XmlElement(name = "StartSupplyDate", required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar startSupplyDate;
        @XmlElement(name = "EndSupplyDate", required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar endSupplyDate;
        @XmlElement(name = "Quality")
        protected List<SupplyResourceContractType.ContractSubject.Quality> quality;
        @XmlElement(name = "OtherQualityIndicator")
        protected List<SupplyResourceContractType.ContractSubject.OtherQualityIndicator> otherQualityIndicator;
        @XmlElement(name = "PlannedVolume")
        protected SupplyResourceContractType.ContractSubject.PlannedVolume plannedVolume;

        /**
         * Gets the value of the serviceType property.
         * 
         * @return
         *     possible object is
         *     {@link SupplyResourceContractType.ContractSubject.ServiceType }
         *     
         */
        public SupplyResourceContractType.ContractSubject.ServiceType getServiceType() {
            return serviceType;
        }

        /**
         * Sets the value of the serviceType property.
         * 
         * @param value
         *     allowed object is
         *     {@link SupplyResourceContractType.ContractSubject.ServiceType }
         *     
         */
        public void setServiceType(SupplyResourceContractType.ContractSubject.ServiceType value) {
            this.serviceType = value;
        }

        /**
         * Gets the value of the municipalResource property.
         * 
         * @return
         *     possible object is
         *     {@link SupplyResourceContractType.ContractSubject.MunicipalResource }
         *     
         */
        public SupplyResourceContractType.ContractSubject.MunicipalResource getMunicipalResource() {
            return municipalResource;
        }

        /**
         * Sets the value of the municipalResource property.
         * 
         * @param value
         *     allowed object is
         *     {@link SupplyResourceContractType.ContractSubject.MunicipalResource }
         *     
         */
        public void setMunicipalResource(SupplyResourceContractType.ContractSubject.MunicipalResource value) {
            this.municipalResource = value;
        }

        /**
         * Gets the value of the heatingSystemType property.
         * 
         * @return
         *     possible object is
         *     {@link HeatingSystemType }
         *     
         */
        public HeatingSystemType getHeatingSystemType() {
            return heatingSystemType;
        }

        /**
         * Sets the value of the heatingSystemType property.
         * 
         * @param value
         *     allowed object is
         *     {@link HeatingSystemType }
         *     
         */
        public void setHeatingSystemType(HeatingSystemType value) {
            this.heatingSystemType = value;
        }

        /**
         * Gets the value of the connectionScheme property.
         * 
         * @return
         *     possible object is
         *     {@link ConnectionSchemeType }
         *     
         */
        public ConnectionSchemeType getConnectionScheme() {
            return connectionScheme;
        }

        /**
         * Sets the value of the connectionScheme property.
         * 
         * @param value
         *     allowed object is
         *     {@link ConnectionSchemeType }
         *     
         */
        public void setConnectionScheme(ConnectionSchemeType value) {
            this.connectionScheme = value;
        }

        /**
         * Gets the value of the startSupplyDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getStartSupplyDate() {
            return startSupplyDate;
        }

        /**
         * Sets the value of the startSupplyDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setStartSupplyDate(XMLGregorianCalendar value) {
            this.startSupplyDate = value;
        }

        /**
         * Gets the value of the endSupplyDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getEndSupplyDate() {
            return endSupplyDate;
        }

        /**
         * Sets the value of the endSupplyDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setEndSupplyDate(XMLGregorianCalendar value) {
            this.endSupplyDate = value;
        }

        /**
         * Gets the value of the quality property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the quality property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getQuality().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link SupplyResourceContractType.ContractSubject.Quality }
         * 
         * 
         */
        public List<SupplyResourceContractType.ContractSubject.Quality> getQuality() {
            if (quality == null) {
                quality = new ArrayList<SupplyResourceContractType.ContractSubject.Quality>();
            }
            return this.quality;
        }

        /**
         * Gets the value of the otherQualityIndicator property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the otherQualityIndicator property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getOtherQualityIndicator().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link SupplyResourceContractType.ContractSubject.OtherQualityIndicator }
         * 
         * 
         */
        public List<SupplyResourceContractType.ContractSubject.OtherQualityIndicator> getOtherQualityIndicator() {
            if (otherQualityIndicator == null) {
                otherQualityIndicator = new ArrayList<SupplyResourceContractType.ContractSubject.OtherQualityIndicator>();
            }
            return this.otherQualityIndicator;
        }

        /**
         * Gets the value of the plannedVolume property.
         * 
         * @return
         *     possible object is
         *     {@link SupplyResourceContractType.ContractSubject.PlannedVolume }
         *     
         */
        public SupplyResourceContractType.ContractSubject.PlannedVolume getPlannedVolume() {
            return plannedVolume;
        }

        /**
         * Sets the value of the plannedVolume property.
         * 
         * @param value
         *     allowed object is
         *     {@link SupplyResourceContractType.ContractSubject.PlannedVolume }
         *     
         */
        public void setPlannedVolume(SupplyResourceContractType.ContractSubject.PlannedVolume value) {
            this.plannedVolume = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}nsiRef">
         *     &lt;/extension>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class MunicipalResource
            extends NsiRef
        {


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
         *         &lt;element name="IndicatorName" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}String250Type"/>
         *         &lt;element name="Number" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}VolumeType"/>
         *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}OKEI"/>
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
            "indicatorName",
            "number",
            "okei"
        })
        public static class OtherQualityIndicator {

            @XmlElement(name = "IndicatorName", required = true)
            protected String indicatorName;
            @XmlElement(name = "Number", required = true)
            protected BigDecimal number;
            @XmlElement(name = "OKEI", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.6/", required = true)
            protected String okei;

            /**
             * Gets the value of the indicatorName property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getIndicatorName() {
                return indicatorName;
            }

            /**
             * Sets the value of the indicatorName property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setIndicatorName(String value) {
                this.indicatorName = value;
            }

            /**
             * Gets the value of the number property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getNumber() {
                return number;
            }

            /**
             * Sets the value of the number property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setNumber(BigDecimal value) {
                this.number = value;
            }

            /**
             * Gets the value of the okei property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getOKEI() {
                return okei;
            }

            /**
             * Sets the value of the okei property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setOKEI(String value) {
                this.okei = value;
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
         *         &lt;element name="Volume" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}VolumeType"/>
         *         &lt;element name="Unit" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}OKEIType"/>
         *         &lt;element name="FeedingMode">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}String250Type">
         *               &lt;maxLength value="250"/>
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
            "volume",
            "unit",
            "feedingMode"
        })
        public static class PlannedVolume {

            @XmlElement(name = "Volume", required = true)
            protected BigDecimal volume;
            @XmlElement(name = "Unit", required = true)
            protected String unit;
            @XmlElement(name = "FeedingMode", required = true)
            protected String feedingMode;

            /**
             * Gets the value of the volume property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getVolume() {
                return volume;
            }

            /**
             * Sets the value of the volume property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setVolume(BigDecimal value) {
                this.volume = value;
            }

            /**
             * Gets the value of the unit property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getUnit() {
                return unit;
            }

            /**
             * Sets the value of the unit property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setUnit(String value) {
                this.unit = value;
            }

            /**
             * Gets the value of the feedingMode property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getFeedingMode() {
                return feedingMode;
            }

            /**
             * Sets the value of the feedingMode property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setFeedingMode(String value) {
                this.feedingMode = value;
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
         *         &lt;element name="QualityIndicator" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}nsiRef"/>
         *         &lt;element name="IndicatorValue">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;choice>
         *                     &lt;element name="Number" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}VolumeType"/>
         *                     &lt;sequence>
         *                       &lt;element name="StartRange" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}VolumeType"/>
         *                       &lt;element name="EndRange" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}VolumeType"/>
         *                     &lt;/sequence>
         *                   &lt;/choice>
         *                   &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}OKEI"/>
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
            "qualityIndicator",
            "indicatorValue"
        })
        public static class Quality {

            @XmlElement(name = "QualityIndicator", required = true)
            protected NsiRef qualityIndicator;
            @XmlElement(name = "IndicatorValue", required = true)
            protected SupplyResourceContractType.ContractSubject.Quality.IndicatorValue indicatorValue;

            /**
             * Gets the value of the qualityIndicator property.
             * 
             * @return
             *     possible object is
             *     {@link NsiRef }
             *     
             */
            public NsiRef getQualityIndicator() {
                return qualityIndicator;
            }

            /**
             * Sets the value of the qualityIndicator property.
             * 
             * @param value
             *     allowed object is
             *     {@link NsiRef }
             *     
             */
            public void setQualityIndicator(NsiRef value) {
                this.qualityIndicator = value;
            }

            /**
             * Gets the value of the indicatorValue property.
             * 
             * @return
             *     possible object is
             *     {@link SupplyResourceContractType.ContractSubject.Quality.IndicatorValue }
             *     
             */
            public SupplyResourceContractType.ContractSubject.Quality.IndicatorValue getIndicatorValue() {
                return indicatorValue;
            }

            /**
             * Sets the value of the indicatorValue property.
             * 
             * @param value
             *     allowed object is
             *     {@link SupplyResourceContractType.ContractSubject.Quality.IndicatorValue }
             *     
             */
            public void setIndicatorValue(SupplyResourceContractType.ContractSubject.Quality.IndicatorValue value) {
                this.indicatorValue = value;
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
             *           &lt;element name="Number" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}VolumeType"/>
             *           &lt;sequence>
             *             &lt;element name="StartRange" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}VolumeType"/>
             *             &lt;element name="EndRange" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}VolumeType"/>
             *           &lt;/sequence>
             *         &lt;/choice>
             *         &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}OKEI"/>
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
                "number",
                "startRange",
                "endRange",
                "okei"
            })
            public static class IndicatorValue {

                @XmlElement(name = "Number")
                protected BigDecimal number;
                @XmlElement(name = "StartRange")
                protected BigDecimal startRange;
                @XmlElement(name = "EndRange")
                protected BigDecimal endRange;
                @XmlElement(name = "OKEI", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.6/", required = true)
                protected String okei;

                /**
                 * Gets the value of the number property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getNumber() {
                    return number;
                }

                /**
                 * Sets the value of the number property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setNumber(BigDecimal value) {
                    this.number = value;
                }

                /**
                 * Gets the value of the startRange property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getStartRange() {
                    return startRange;
                }

                /**
                 * Sets the value of the startRange property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setStartRange(BigDecimal value) {
                    this.startRange = value;
                }

                /**
                 * Gets the value of the endRange property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getEndRange() {
                    return endRange;
                }

                /**
                 * Sets the value of the endRange property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setEndRange(BigDecimal value) {
                    this.endRange = value;
                }

                /**
                 * Gets the value of the okei property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getOKEI() {
                    return okei;
                }

                /**
                 * Sets the value of the okei property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setOKEI(String value) {
                    this.okei = value;
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
         *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}nsiRef">
         *     &lt;/extension>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class ServiceType
            extends NsiRef
        {


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
     *         &lt;element name="ContractNumber">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;minLength value="1"/>
     *               &lt;maxLength value="30"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="SigningDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *         &lt;element name="EffectiveDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *         &lt;element name="ContractAttachment" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}AttachmentType" maxOccurs="100"/>
     *         &lt;sequence>
     *           &lt;element name="BillingDate">
     *             &lt;simpleType>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
     *                 &lt;maxInclusive value="30"/>
     *                 &lt;minInclusive value="1"/>
     *               &lt;/restriction>
     *             &lt;/simpleType>
     *           &lt;/element>
     *           &lt;element name="PaymentDate">
     *             &lt;simpleType>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
     *                 &lt;maxInclusive value="30"/>
     *                 &lt;minInclusive value="1"/>
     *               &lt;/restriction>
     *             &lt;/simpleType>
     *           &lt;/element>
     *           &lt;element name="ProvidingInformationDate">
     *             &lt;simpleType>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
     *                 &lt;minInclusive value="1"/>
     *                 &lt;maxInclusive value="30"/>
     *               &lt;/restriction>
     *             &lt;/simpleType>
     *           &lt;/element>
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
    @XmlType(name = "", propOrder = {
        "contractNumber",
        "signingDate",
        "effectiveDate",
        "contractAttachment",
        "billingDate",
        "paymentDate",
        "providingInformationDate"
    })
    public static class IsContract {

        @XmlElement(name = "ContractNumber", required = true)
        protected String contractNumber;
        @XmlElement(name = "SigningDate", required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar signingDate;
        @XmlElement(name = "EffectiveDate", required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar effectiveDate;
        @XmlElement(name = "ContractAttachment", required = true)
        protected List<AttachmentType> contractAttachment;
        @XmlElement(name = "BillingDate")
        protected byte billingDate;
        @XmlElement(name = "PaymentDate")
        protected byte paymentDate;
        @XmlElement(name = "ProvidingInformationDate")
        protected byte providingInformationDate;

        /**
         * Gets the value of the contractNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getContractNumber() {
            return contractNumber;
        }

        /**
         * Sets the value of the contractNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setContractNumber(String value) {
            this.contractNumber = value;
        }

        /**
         * Gets the value of the signingDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getSigningDate() {
            return signingDate;
        }

        /**
         * Sets the value of the signingDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setSigningDate(XMLGregorianCalendar value) {
            this.signingDate = value;
        }

        /**
         * Gets the value of the effectiveDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getEffectiveDate() {
            return effectiveDate;
        }

        /**
         * Sets the value of the effectiveDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setEffectiveDate(XMLGregorianCalendar value) {
            this.effectiveDate = value;
        }

        /**
         * Gets the value of the contractAttachment property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the contractAttachment property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getContractAttachment().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AttachmentType }
         * 
         * 
         */
        public List<AttachmentType> getContractAttachment() {
            if (contractAttachment == null) {
                contractAttachment = new ArrayList<AttachmentType>();
            }
            return this.contractAttachment;
        }

        /**
         * Gets the value of the billingDate property.
         * 
         */
        public byte getBillingDate() {
            return billingDate;
        }

        /**
         * Sets the value of the billingDate property.
         * 
         */
        public void setBillingDate(byte value) {
            this.billingDate = value;
        }

        /**
         * Gets the value of the paymentDate property.
         * 
         */
        public byte getPaymentDate() {
            return paymentDate;
        }

        /**
         * Sets the value of the paymentDate property.
         * 
         */
        public void setPaymentDate(byte value) {
            this.paymentDate = value;
        }

        /**
         * Gets the value of the providingInformationDate property.
         * 
         */
        public byte getProvidingInformationDate() {
            return providingInformationDate;
        }

        /**
         * Sets the value of the providingInformationDate property.
         * 
         */
        public void setProvidingInformationDate(byte value) {
            this.providingInformationDate = value;
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
     *         &lt;element name="ContractNumber" minOccurs="0">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
     *               &lt;minLength value="1"/>
     *               &lt;maxLength value="30"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="SigningDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="EffectiveDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
     *         &lt;element name="ContractAttachment" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}AttachmentType" maxOccurs="100" minOccurs="0"/>
     *         &lt;sequence>
     *           &lt;element name="BillingDate" minOccurs="0">
     *             &lt;simpleType>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
     *                 &lt;maxInclusive value="30"/>
     *                 &lt;minInclusive value="1"/>
     *               &lt;/restriction>
     *             &lt;/simpleType>
     *           &lt;/element>
     *           &lt;element name="PaymentDate" minOccurs="0">
     *             &lt;simpleType>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
     *                 &lt;maxInclusive value="30"/>
     *                 &lt;minInclusive value="1"/>
     *               &lt;/restriction>
     *             &lt;/simpleType>
     *           &lt;/element>
     *           &lt;element name="ProvidingInformationDate" minOccurs="0">
     *             &lt;simpleType>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
     *                 &lt;minInclusive value="1"/>
     *                 &lt;maxInclusive value="30"/>
     *               &lt;/restriction>
     *             &lt;/simpleType>
     *           &lt;/element>
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
    @XmlType(name = "", propOrder = {
        "contractNumber",
        "signingDate",
        "effectiveDate",
        "contractAttachment",
        "billingDate",
        "paymentDate",
        "providingInformationDate"
    })
    public static class IsNotContract {

        @XmlElement(name = "ContractNumber")
        protected String contractNumber;
        @XmlElement(name = "SigningDate")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar signingDate;
        @XmlElement(name = "EffectiveDate")
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar effectiveDate;
        @XmlElement(name = "ContractAttachment")
        protected List<AttachmentType> contractAttachment;
        @XmlElement(name = "BillingDate")
        protected Byte billingDate;
        @XmlElement(name = "PaymentDate")
        protected Byte paymentDate;
        @XmlElement(name = "ProvidingInformationDate")
        protected Byte providingInformationDate;

        /**
         * Gets the value of the contractNumber property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getContractNumber() {
            return contractNumber;
        }

        /**
         * Sets the value of the contractNumber property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setContractNumber(String value) {
            this.contractNumber = value;
        }

        /**
         * Gets the value of the signingDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getSigningDate() {
            return signingDate;
        }

        /**
         * Sets the value of the signingDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setSigningDate(XMLGregorianCalendar value) {
            this.signingDate = value;
        }

        /**
         * Gets the value of the effectiveDate property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getEffectiveDate() {
            return effectiveDate;
        }

        /**
         * Sets the value of the effectiveDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setEffectiveDate(XMLGregorianCalendar value) {
            this.effectiveDate = value;
        }

        /**
         * Gets the value of the contractAttachment property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the contractAttachment property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getContractAttachment().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AttachmentType }
         * 
         * 
         */
        public List<AttachmentType> getContractAttachment() {
            if (contractAttachment == null) {
                contractAttachment = new ArrayList<AttachmentType>();
            }
            return this.contractAttachment;
        }

        /**
         * Gets the value of the billingDate property.
         * 
         * @return
         *     possible object is
         *     {@link Byte }
         *     
         */
        public Byte getBillingDate() {
            return billingDate;
        }

        /**
         * Sets the value of the billingDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link Byte }
         *     
         */
        public void setBillingDate(Byte value) {
            this.billingDate = value;
        }

        /**
         * Gets the value of the paymentDate property.
         * 
         * @return
         *     possible object is
         *     {@link Byte }
         *     
         */
        public Byte getPaymentDate() {
            return paymentDate;
        }

        /**
         * Sets the value of the paymentDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link Byte }
         *     
         */
        public void setPaymentDate(Byte value) {
            this.paymentDate = value;
        }

        /**
         * Gets the value of the providingInformationDate property.
         * 
         * @return
         *     possible object is
         *     {@link Byte }
         *     
         */
        public Byte getProvidingInformationDate() {
            return providingInformationDate;
        }

        /**
         * Sets the value of the providingInformationDate property.
         * 
         * @param value
         *     allowed object is
         *     {@link Byte }
         *     
         */
        public void setProvidingInformationDate(Byte value) {
            this.providingInformationDate = value;
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
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/house-management/}ObjectAddressType">
     *       &lt;sequence>
     *         &lt;element name="Pair" maxOccurs="100">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ServiceType">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}nsiRef">
     *                         &lt;/extension>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="MunicipalResource">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}nsiRef">
     *                         &lt;/extension>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="StartSupplyDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *                   &lt;element name="EndSupplyDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
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
        "pair"
    })
    public static class ObjectAddress
        extends ObjectAddressType
    {

        @XmlElement(name = "Pair", required = true)
        protected List<SupplyResourceContractType.ObjectAddress.Pair> pair;

        /**
         * Gets the value of the pair property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the pair property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPair().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link SupplyResourceContractType.ObjectAddress.Pair }
         * 
         * 
         */
        public List<SupplyResourceContractType.ObjectAddress.Pair> getPair() {
            if (pair == null) {
                pair = new ArrayList<SupplyResourceContractType.ObjectAddress.Pair>();
            }
            return this.pair;
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
         *         &lt;element name="ServiceType">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}nsiRef">
         *               &lt;/extension>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="MunicipalResource">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}nsiRef">
         *               &lt;/extension>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="StartSupplyDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
         *         &lt;element name="EndSupplyDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
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
            "serviceType",
            "municipalResource",
            "startSupplyDate",
            "endSupplyDate"
        })
        public static class Pair {

            @XmlElement(name = "ServiceType", required = true)
            protected SupplyResourceContractType.ObjectAddress.Pair.ServiceType serviceType;
            @XmlElement(name = "MunicipalResource", required = true)
            protected SupplyResourceContractType.ObjectAddress.Pair.MunicipalResource municipalResource;
            @XmlElement(name = "StartSupplyDate", required = true)
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar startSupplyDate;
            @XmlElement(name = "EndSupplyDate", required = true)
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar endSupplyDate;

            /**
             * Gets the value of the serviceType property.
             * 
             * @return
             *     possible object is
             *     {@link SupplyResourceContractType.ObjectAddress.Pair.ServiceType }
             *     
             */
            public SupplyResourceContractType.ObjectAddress.Pair.ServiceType getServiceType() {
                return serviceType;
            }

            /**
             * Sets the value of the serviceType property.
             * 
             * @param value
             *     allowed object is
             *     {@link SupplyResourceContractType.ObjectAddress.Pair.ServiceType }
             *     
             */
            public void setServiceType(SupplyResourceContractType.ObjectAddress.Pair.ServiceType value) {
                this.serviceType = value;
            }

            /**
             * Gets the value of the municipalResource property.
             * 
             * @return
             *     possible object is
             *     {@link SupplyResourceContractType.ObjectAddress.Pair.MunicipalResource }
             *     
             */
            public SupplyResourceContractType.ObjectAddress.Pair.MunicipalResource getMunicipalResource() {
                return municipalResource;
            }

            /**
             * Sets the value of the municipalResource property.
             * 
             * @param value
             *     allowed object is
             *     {@link SupplyResourceContractType.ObjectAddress.Pair.MunicipalResource }
             *     
             */
            public void setMunicipalResource(SupplyResourceContractType.ObjectAddress.Pair.MunicipalResource value) {
                this.municipalResource = value;
            }

            /**
             * Gets the value of the startSupplyDate property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getStartSupplyDate() {
                return startSupplyDate;
            }

            /**
             * Sets the value of the startSupplyDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setStartSupplyDate(XMLGregorianCalendar value) {
                this.startSupplyDate = value;
            }

            /**
             * Gets the value of the endSupplyDate property.
             * 
             * @return
             *     possible object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public XMLGregorianCalendar getEndSupplyDate() {
                return endSupplyDate;
            }

            /**
             * Sets the value of the endSupplyDate property.
             * 
             * @param value
             *     allowed object is
             *     {@link XMLGregorianCalendar }
             *     
             */
            public void setEndSupplyDate(XMLGregorianCalendar value) {
                this.endSupplyDate = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}nsiRef">
             *     &lt;/extension>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class MunicipalResource
                extends NsiRef
            {


            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}nsiRef">
             *     &lt;/extension>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class ServiceType
                extends NsiRef
            {


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
     *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}RegOrgType">
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Organization
        extends RegOrgType
    {


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
     *         &lt;element name="RegOrg" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}RegOrgType"/>
     *         &lt;element name="Ind">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}IndType">
     *               &lt;/extension>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/choice>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "regOrg",
        "ind"
    })
    public static class Owner {

        @XmlElement(name = "RegOrg")
        protected RegOrgType regOrg;
        @XmlElement(name = "Ind")
        protected SupplyResourceContractType.Owner.Ind ind;

        /**
         * Gets the value of the regOrg property.
         * 
         * @return
         *     possible object is
         *     {@link RegOrgType }
         *     
         */
        public RegOrgType getRegOrg() {
            return regOrg;
        }

        /**
         * Sets the value of the regOrg property.
         * 
         * @param value
         *     allowed object is
         *     {@link RegOrgType }
         *     
         */
        public void setRegOrg(RegOrgType value) {
            this.regOrg = value;
        }

        /**
         * Gets the value of the ind property.
         * 
         * @return
         *     possible object is
         *     {@link SupplyResourceContractType.Owner.Ind }
         *     
         */
        public SupplyResourceContractType.Owner.Ind getInd() {
            return ind;
        }

        /**
         * Sets the value of the ind property.
         * 
         * @param value
         *     allowed object is
         *     {@link SupplyResourceContractType.Owner.Ind }
         *     
         */
        public void setInd(SupplyResourceContractType.Owner.Ind value) {
            this.ind = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}IndType">
         *     &lt;/extension>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Ind
            extends IndType
        {


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
     *         &lt;element name="Start">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="StartDate">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
     *                         &lt;maxInclusive value="30"/>
     *                         &lt;minInclusive value="1"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="NextMonth" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="End">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="EndDate">
     *                     &lt;simpleType>
     *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
     *                         &lt;maxInclusive value="30"/>
     *                         &lt;minInclusive value="1"/>
     *                       &lt;/restriction>
     *                     &lt;/simpleType>
     *                   &lt;/element>
     *                   &lt;element name="NextMonth" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
        "start",
        "end"
    })
    public static class Period {

        @XmlElement(name = "Start", required = true)
        protected SupplyResourceContractType.Period.Start start;
        @XmlElement(name = "End", required = true)
        protected SupplyResourceContractType.Period.End end;

        /**
         * Gets the value of the start property.
         * 
         * @return
         *     possible object is
         *     {@link SupplyResourceContractType.Period.Start }
         *     
         */
        public SupplyResourceContractType.Period.Start getStart() {
            return start;
        }

        /**
         * Sets the value of the start property.
         * 
         * @param value
         *     allowed object is
         *     {@link SupplyResourceContractType.Period.Start }
         *     
         */
        public void setStart(SupplyResourceContractType.Period.Start value) {
            this.start = value;
        }

        /**
         * Gets the value of the end property.
         * 
         * @return
         *     possible object is
         *     {@link SupplyResourceContractType.Period.End }
         *     
         */
        public SupplyResourceContractType.Period.End getEnd() {
            return end;
        }

        /**
         * Sets the value of the end property.
         * 
         * @param value
         *     allowed object is
         *     {@link SupplyResourceContractType.Period.End }
         *     
         */
        public void setEnd(SupplyResourceContractType.Period.End value) {
            this.end = value;
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
         *         &lt;element name="EndDate">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
         *               &lt;maxInclusive value="30"/>
         *               &lt;minInclusive value="1"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="NextMonth" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
            "endDate",
            "nextMonth"
        })
        public static class End {

            @XmlElement(name = "EndDate")
            protected byte endDate;
            @XmlElement(name = "NextMonth")
            protected Boolean nextMonth;

            /**
             * Gets the value of the endDate property.
             * 
             */
            public byte getEndDate() {
                return endDate;
            }

            /**
             * Sets the value of the endDate property.
             * 
             */
            public void setEndDate(byte value) {
                this.endDate = value;
            }

            /**
             * Gets the value of the nextMonth property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isNextMonth() {
                return nextMonth;
            }

            /**
             * Sets the value of the nextMonth property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setNextMonth(Boolean value) {
                this.nextMonth = value;
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
         *         &lt;element name="StartDate">
         *           &lt;simpleType>
         *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}byte">
         *               &lt;maxInclusive value="30"/>
         *               &lt;minInclusive value="1"/>
         *             &lt;/restriction>
         *           &lt;/simpleType>
         *         &lt;/element>
         *         &lt;element name="NextMonth" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
            "startDate",
            "nextMonth"
        })
        public static class Start {

            @XmlElement(name = "StartDate")
            protected byte startDate;
            @XmlElement(name = "NextMonth")
            protected Boolean nextMonth;

            /**
             * Gets the value of the startDate property.
             * 
             */
            public byte getStartDate() {
                return startDate;
            }

            /**
             * Sets the value of the startDate property.
             * 
             */
            public void setStartDate(byte value) {
                this.startDate = value;
            }

            /**
             * Gets the value of the nextMonth property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isNextMonth() {
                return nextMonth;
            }

            /**
             * Sets the value of the nextMonth property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setNextMonth(Boolean value) {
                this.nextMonth = value;
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
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;choice>
     *         &lt;element name="RegOrg" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}RegOrgType"/>
     *         &lt;element name="Ind">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}IndType">
     *               &lt;/extension>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/choice>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "regOrg",
        "ind"
    })
    public static class Renter {

        @XmlElement(name = "RegOrg")
        protected RegOrgType regOrg;
        @XmlElement(name = "Ind")
        protected SupplyResourceContractType.Renter.Ind ind;

        /**
         * Gets the value of the regOrg property.
         * 
         * @return
         *     possible object is
         *     {@link RegOrgType }
         *     
         */
        public RegOrgType getRegOrg() {
            return regOrg;
        }

        /**
         * Sets the value of the regOrg property.
         * 
         * @param value
         *     allowed object is
         *     {@link RegOrgType }
         *     
         */
        public void setRegOrg(RegOrgType value) {
            this.regOrg = value;
        }

        /**
         * Gets the value of the ind property.
         * 
         * @return
         *     possible object is
         *     {@link SupplyResourceContractType.Renter.Ind }
         *     
         */
        public SupplyResourceContractType.Renter.Ind getInd() {
            return ind;
        }

        /**
         * Sets the value of the ind property.
         * 
         * @param value
         *     allowed object is
         *     {@link SupplyResourceContractType.Renter.Ind }
         *     
         */
        public void setInd(SupplyResourceContractType.Renter.Ind value) {
            this.ind = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}IndType">
         *     &lt;/extension>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Ind
            extends IndType
        {


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
     *         &lt;element name="OutsideTemperature" type="{http://www.w3.org/2001/XMLSchema}int"/>
     *         &lt;element name="FlowLineTemperature" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
     *         &lt;element name="OppositeLineTemperature" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
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
        "outsideTemperature",
        "flowLineTemperature",
        "oppositeLineTemperature"
    })
    public static class TemperatureChart {

        @XmlElement(name = "OutsideTemperature")
        protected int outsideTemperature;
        @XmlElement(name = "FlowLineTemperature", required = true)
        @XmlSchemaType(name = "positiveInteger")
        protected BigInteger flowLineTemperature;
        @XmlElement(name = "OppositeLineTemperature", required = true)
        @XmlSchemaType(name = "positiveInteger")
        protected BigInteger oppositeLineTemperature;

        /**
         * Gets the value of the outsideTemperature property.
         * 
         */
        public int getOutsideTemperature() {
            return outsideTemperature;
        }

        /**
         * Sets the value of the outsideTemperature property.
         * 
         */
        public void setOutsideTemperature(int value) {
            this.outsideTemperature = value;
        }

        /**
         * Gets the value of the flowLineTemperature property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getFlowLineTemperature() {
            return flowLineTemperature;
        }

        /**
         * Sets the value of the flowLineTemperature property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setFlowLineTemperature(BigInteger value) {
            this.flowLineTemperature = value;
        }

        /**
         * Gets the value of the oppositeLineTemperature property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getOppositeLineTemperature() {
            return oppositeLineTemperature;
        }

        /**
         * Sets the value of the oppositeLineTemperature property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setOppositeLineTemperature(BigInteger value) {
            this.oppositeLineTemperature = value;
        }

    }

}

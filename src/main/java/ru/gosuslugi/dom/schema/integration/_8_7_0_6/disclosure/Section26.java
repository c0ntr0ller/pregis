
package ru.gosuslugi.dom.schema.integration._8_7_0_6.disclosure;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_7_0.NsiRef;


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
 *         &lt;element name="SummaryInfo">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="CarryoversOfFunds">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="BeginOfPeriod">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="ConsumersPrepayments" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
 *                                       &lt;element name="ConsumersOverpayment" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
 *                                       &lt;element name="ConsumersDebt" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="EndOfPeriod">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="ConsumersPrepayments" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
 *                                       &lt;element name="ConsumersOverpayment" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
 *                                       &lt;element name="ConsumersDebt" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
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
 *                   &lt;element name="Charges">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="HouseMaintenance" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
 *                             &lt;element name="CurrentRepairs" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
 *                             &lt;element name="ServiceManagement" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="MoneyRecieved">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="MoneyFromOwnersOrTenants" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
 *                             &lt;element name="ContributionsFromOwnersOrTenants" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
 *                             &lt;element name="Subsidies" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
 *                             &lt;element name="FundsFromCommonPropertyUse" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
 *                             &lt;element name="MiscellaneousIncome" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="TotalBalance" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="CompletedWorkOrService" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="WorkRepairType" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}nsiRef"/>
 *                   &lt;element name="WorkRepair" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/disclosure/}String1500Type"/>
 *                   &lt;element name="AnnualActualCost" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
 *                   &lt;element name="WorkPeriod" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}nsiRef"/>
 *                   &lt;element name="UnitCost" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="WorkQualityClaims">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Received">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
 *                         &lt;maxInclusive value="9999"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="Successful">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
 *                         &lt;maxInclusive value="9999"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="Denied">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
 *                         &lt;maxInclusive value="9999"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="RecalculationSum" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="MunicipalServicesSummary" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="CarryoversOfFunds">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="BeginOfPeriod">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="ConsumersPrepayments" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
 *                                       &lt;element name="ConsumersDebt" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
 *                                       &lt;element name="ConsumersOverpayment" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
 *                                     &lt;/sequence>
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="EndOfPeriod">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="ConsumersPrepayments" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
 *                                       &lt;element name="ConsumersDebt" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
 *                                       &lt;element name="ConsumersOverpayment" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
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
 *         &lt;element name="MunicipalService" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Kind" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}nsiRef"/>
 *                   &lt;element name="TotalConsumed" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}VolumeType"/>
 *                   &lt;element name="ChargesForCustomers" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
 *                   &lt;element name="PaidByCustomers" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
 *                   &lt;element name="DebtOfCustomers" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
 *                   &lt;element name="SupplierCharged" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
 *                   &lt;element name="SupplierPaid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
 *                   &lt;element name="SupplierDebt" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
 *                   &lt;element name="SupplierFinesAndPenalties" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="MunicipalServiceQualityClaims" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Received">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
 *                         &lt;maxInclusive value="9999"/>
 *                         &lt;minInclusive value="0"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="Successful">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
 *                         &lt;maxInclusive value="9999"/>
 *                         &lt;minInclusive value="0"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="Denied">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
 *                         &lt;maxInclusive value="9999"/>
 *                         &lt;minInclusive value="0"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="RecalculationSum" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="WorkOnClaims" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ClaimsSent">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
 *                         &lt;maxInclusive value="9999"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="LawsuitSent">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
 *                         &lt;maxInclusive value="9999"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="ClaimsMoneyRecieved" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
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
    "summaryInfo",
    "completedWorkOrService",
    "workQualityClaims",
    "municipalServicesSummary",
    "municipalService",
    "municipalServiceQualityClaims",
    "workOnClaims"
})
@XmlRootElement(name = "Section2_6")
public class Section26 {

    @XmlElement(name = "SummaryInfo", required = true)
    protected Section26 .SummaryInfo summaryInfo;
    @XmlElement(name = "CompletedWorkOrService")
    protected List<Section26 .CompletedWorkOrService> completedWorkOrService;
    @XmlElement(name = "WorkQualityClaims", required = true)
    protected Section26 .WorkQualityClaims workQualityClaims;
    @XmlElement(name = "MunicipalServicesSummary")
    protected Section26 .MunicipalServicesSummary municipalServicesSummary;
    @XmlElement(name = "MunicipalService")
    protected List<Section26 .MunicipalService> municipalService;
    @XmlElement(name = "MunicipalServiceQualityClaims")
    protected Section26 .MunicipalServiceQualityClaims municipalServiceQualityClaims;
    @XmlElement(name = "WorkOnClaims")
    protected Section26 .WorkOnClaims workOnClaims;

    /**
     * Gets the value of the summaryInfo property.
     * 
     * @return
     *     possible object is
     *     {@link Section26 .SummaryInfo }
     *     
     */
    public Section26 .SummaryInfo getSummaryInfo() {
        return summaryInfo;
    }

    /**
     * Sets the value of the summaryInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Section26 .SummaryInfo }
     *     
     */
    public void setSummaryInfo(Section26 .SummaryInfo value) {
        this.summaryInfo = value;
    }

    /**
     * Gets the value of the completedWorkOrService property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the completedWorkOrService property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCompletedWorkOrService().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Section26 .CompletedWorkOrService }
     * 
     * 
     */
    public List<Section26 .CompletedWorkOrService> getCompletedWorkOrService() {
        if (completedWorkOrService == null) {
            completedWorkOrService = new ArrayList<Section26 .CompletedWorkOrService>();
        }
        return this.completedWorkOrService;
    }

    /**
     * Gets the value of the workQualityClaims property.
     * 
     * @return
     *     possible object is
     *     {@link Section26 .WorkQualityClaims }
     *     
     */
    public Section26 .WorkQualityClaims getWorkQualityClaims() {
        return workQualityClaims;
    }

    /**
     * Sets the value of the workQualityClaims property.
     * 
     * @param value
     *     allowed object is
     *     {@link Section26 .WorkQualityClaims }
     *     
     */
    public void setWorkQualityClaims(Section26 .WorkQualityClaims value) {
        this.workQualityClaims = value;
    }

    /**
     * Gets the value of the municipalServicesSummary property.
     * 
     * @return
     *     possible object is
     *     {@link Section26 .MunicipalServicesSummary }
     *     
     */
    public Section26 .MunicipalServicesSummary getMunicipalServicesSummary() {
        return municipalServicesSummary;
    }

    /**
     * Sets the value of the municipalServicesSummary property.
     * 
     * @param value
     *     allowed object is
     *     {@link Section26 .MunicipalServicesSummary }
     *     
     */
    public void setMunicipalServicesSummary(Section26 .MunicipalServicesSummary value) {
        this.municipalServicesSummary = value;
    }

    /**
     * Gets the value of the municipalService property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the municipalService property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMunicipalService().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Section26 .MunicipalService }
     * 
     * 
     */
    public List<Section26 .MunicipalService> getMunicipalService() {
        if (municipalService == null) {
            municipalService = new ArrayList<Section26 .MunicipalService>();
        }
        return this.municipalService;
    }

    /**
     * Gets the value of the municipalServiceQualityClaims property.
     * 
     * @return
     *     possible object is
     *     {@link Section26 .MunicipalServiceQualityClaims }
     *     
     */
    public Section26 .MunicipalServiceQualityClaims getMunicipalServiceQualityClaims() {
        return municipalServiceQualityClaims;
    }

    /**
     * Sets the value of the municipalServiceQualityClaims property.
     * 
     * @param value
     *     allowed object is
     *     {@link Section26 .MunicipalServiceQualityClaims }
     *     
     */
    public void setMunicipalServiceQualityClaims(Section26 .MunicipalServiceQualityClaims value) {
        this.municipalServiceQualityClaims = value;
    }

    /**
     * Gets the value of the workOnClaims property.
     * 
     * @return
     *     possible object is
     *     {@link Section26 .WorkOnClaims }
     *     
     */
    public Section26 .WorkOnClaims getWorkOnClaims() {
        return workOnClaims;
    }

    /**
     * Sets the value of the workOnClaims property.
     * 
     * @param value
     *     allowed object is
     *     {@link Section26 .WorkOnClaims }
     *     
     */
    public void setWorkOnClaims(Section26 .WorkOnClaims value) {
        this.workOnClaims = value;
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
     *         &lt;element name="WorkRepairType" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}nsiRef"/>
     *         &lt;element name="WorkRepair" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/disclosure/}String1500Type"/>
     *         &lt;element name="AnnualActualCost" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
     *         &lt;element name="WorkPeriod" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}nsiRef"/>
     *         &lt;element name="UnitCost" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
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
        "workRepairType",
        "workRepair",
        "annualActualCost",
        "workPeriod",
        "unitCost"
    })
    public static class CompletedWorkOrService {

        @XmlElement(name = "WorkRepairType", required = true)
        protected NsiRef workRepairType;
        @XmlElement(name = "WorkRepair", required = true)
        protected String workRepair;
        @XmlElement(name = "AnnualActualCost", required = true)
        protected BigDecimal annualActualCost;
        @XmlElement(name = "WorkPeriod", required = true)
        protected NsiRef workPeriod;
        @XmlElement(name = "UnitCost", required = true)
        protected BigDecimal unitCost;

        /**
         * Gets the value of the workRepairType property.
         * 
         * @return
         *     possible object is
         *     {@link NsiRef }
         *     
         */
        public NsiRef getWorkRepairType() {
            return workRepairType;
        }

        /**
         * Sets the value of the workRepairType property.
         * 
         * @param value
         *     allowed object is
         *     {@link NsiRef }
         *     
         */
        public void setWorkRepairType(NsiRef value) {
            this.workRepairType = value;
        }

        /**
         * Gets the value of the workRepair property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getWorkRepair() {
            return workRepair;
        }

        /**
         * Sets the value of the workRepair property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setWorkRepair(String value) {
            this.workRepair = value;
        }

        /**
         * Gets the value of the annualActualCost property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getAnnualActualCost() {
            return annualActualCost;
        }

        /**
         * Sets the value of the annualActualCost property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setAnnualActualCost(BigDecimal value) {
            this.annualActualCost = value;
        }

        /**
         * Gets the value of the workPeriod property.
         * 
         * @return
         *     possible object is
         *     {@link NsiRef }
         *     
         */
        public NsiRef getWorkPeriod() {
            return workPeriod;
        }

        /**
         * Sets the value of the workPeriod property.
         * 
         * @param value
         *     allowed object is
         *     {@link NsiRef }
         *     
         */
        public void setWorkPeriod(NsiRef value) {
            this.workPeriod = value;
        }

        /**
         * Gets the value of the unitCost property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getUnitCost() {
            return unitCost;
        }

        /**
         * Sets the value of the unitCost property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setUnitCost(BigDecimal value) {
            this.unitCost = value;
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
     *         &lt;element name="Kind" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}nsiRef"/>
     *         &lt;element name="TotalConsumed" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}VolumeType"/>
     *         &lt;element name="ChargesForCustomers" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
     *         &lt;element name="PaidByCustomers" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
     *         &lt;element name="DebtOfCustomers" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
     *         &lt;element name="SupplierCharged" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
     *         &lt;element name="SupplierPaid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
     *         &lt;element name="SupplierDebt" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
     *         &lt;element name="SupplierFinesAndPenalties" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
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
        "kind",
        "totalConsumed",
        "chargesForCustomers",
        "paidByCustomers",
        "debtOfCustomers",
        "supplierCharged",
        "supplierPaid",
        "supplierDebt",
        "supplierFinesAndPenalties"
    })
    public static class MunicipalService {

        @XmlElement(name = "Kind", required = true)
        protected NsiRef kind;
        @XmlElement(name = "TotalConsumed", required = true)
        protected BigDecimal totalConsumed;
        @XmlElement(name = "ChargesForCustomers", required = true)
        protected BigDecimal chargesForCustomers;
        @XmlElement(name = "PaidByCustomers", required = true)
        protected BigDecimal paidByCustomers;
        @XmlElement(name = "DebtOfCustomers", required = true)
        protected BigDecimal debtOfCustomers;
        @XmlElement(name = "SupplierCharged", required = true)
        protected BigDecimal supplierCharged;
        @XmlElement(name = "SupplierPaid", required = true)
        protected BigDecimal supplierPaid;
        @XmlElement(name = "SupplierDebt", required = true)
        protected BigDecimal supplierDebt;
        @XmlElement(name = "SupplierFinesAndPenalties", required = true)
        protected BigDecimal supplierFinesAndPenalties;

        /**
         * Gets the value of the kind property.
         * 
         * @return
         *     possible object is
         *     {@link NsiRef }
         *     
         */
        public NsiRef getKind() {
            return kind;
        }

        /**
         * Sets the value of the kind property.
         * 
         * @param value
         *     allowed object is
         *     {@link NsiRef }
         *     
         */
        public void setKind(NsiRef value) {
            this.kind = value;
        }

        /**
         * Gets the value of the totalConsumed property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getTotalConsumed() {
            return totalConsumed;
        }

        /**
         * Sets the value of the totalConsumed property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setTotalConsumed(BigDecimal value) {
            this.totalConsumed = value;
        }

        /**
         * Gets the value of the chargesForCustomers property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getChargesForCustomers() {
            return chargesForCustomers;
        }

        /**
         * Sets the value of the chargesForCustomers property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setChargesForCustomers(BigDecimal value) {
            this.chargesForCustomers = value;
        }

        /**
         * Gets the value of the paidByCustomers property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getPaidByCustomers() {
            return paidByCustomers;
        }

        /**
         * Sets the value of the paidByCustomers property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setPaidByCustomers(BigDecimal value) {
            this.paidByCustomers = value;
        }

        /**
         * Gets the value of the debtOfCustomers property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getDebtOfCustomers() {
            return debtOfCustomers;
        }

        /**
         * Sets the value of the debtOfCustomers property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setDebtOfCustomers(BigDecimal value) {
            this.debtOfCustomers = value;
        }

        /**
         * Gets the value of the supplierCharged property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getSupplierCharged() {
            return supplierCharged;
        }

        /**
         * Sets the value of the supplierCharged property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setSupplierCharged(BigDecimal value) {
            this.supplierCharged = value;
        }

        /**
         * Gets the value of the supplierPaid property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getSupplierPaid() {
            return supplierPaid;
        }

        /**
         * Sets the value of the supplierPaid property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setSupplierPaid(BigDecimal value) {
            this.supplierPaid = value;
        }

        /**
         * Gets the value of the supplierDebt property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getSupplierDebt() {
            return supplierDebt;
        }

        /**
         * Sets the value of the supplierDebt property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setSupplierDebt(BigDecimal value) {
            this.supplierDebt = value;
        }

        /**
         * Gets the value of the supplierFinesAndPenalties property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getSupplierFinesAndPenalties() {
            return supplierFinesAndPenalties;
        }

        /**
         * Sets the value of the supplierFinesAndPenalties property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setSupplierFinesAndPenalties(BigDecimal value) {
            this.supplierFinesAndPenalties = value;
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
     *         &lt;element name="Received">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
     *               &lt;maxInclusive value="9999"/>
     *               &lt;minInclusive value="0"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="Successful">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
     *               &lt;maxInclusive value="9999"/>
     *               &lt;minInclusive value="0"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="Denied">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
     *               &lt;maxInclusive value="9999"/>
     *               &lt;minInclusive value="0"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="RecalculationSum" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
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
        "received",
        "successful",
        "denied",
        "recalculationSum"
    })
    public static class MunicipalServiceQualityClaims {

        @XmlElement(name = "Received")
        protected int received;
        @XmlElement(name = "Successful")
        protected int successful;
        @XmlElement(name = "Denied")
        protected int denied;
        @XmlElement(name = "RecalculationSum", required = true)
        protected BigDecimal recalculationSum;

        /**
         * Gets the value of the received property.
         * 
         */
        public int getReceived() {
            return received;
        }

        /**
         * Sets the value of the received property.
         * 
         */
        public void setReceived(int value) {
            this.received = value;
        }

        /**
         * Gets the value of the successful property.
         * 
         */
        public int getSuccessful() {
            return successful;
        }

        /**
         * Sets the value of the successful property.
         * 
         */
        public void setSuccessful(int value) {
            this.successful = value;
        }

        /**
         * Gets the value of the denied property.
         * 
         */
        public int getDenied() {
            return denied;
        }

        /**
         * Sets the value of the denied property.
         * 
         */
        public void setDenied(int value) {
            this.denied = value;
        }

        /**
         * Gets the value of the recalculationSum property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getRecalculationSum() {
            return recalculationSum;
        }

        /**
         * Sets the value of the recalculationSum property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setRecalculationSum(BigDecimal value) {
            this.recalculationSum = value;
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
     *         &lt;element name="CarryoversOfFunds">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="BeginOfPeriod">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="ConsumersPrepayments" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
     *                             &lt;element name="ConsumersDebt" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
     *                             &lt;element name="ConsumersOverpayment" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="EndOfPeriod">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="ConsumersPrepayments" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
     *                             &lt;element name="ConsumersDebt" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
     *                             &lt;element name="ConsumersOverpayment" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
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
        "carryoversOfFunds"
    })
    public static class MunicipalServicesSummary {

        @XmlElement(name = "CarryoversOfFunds", required = true)
        protected Section26 .MunicipalServicesSummary.CarryoversOfFunds carryoversOfFunds;

        /**
         * Gets the value of the carryoversOfFunds property.
         * 
         * @return
         *     possible object is
         *     {@link Section26 .MunicipalServicesSummary.CarryoversOfFunds }
         *     
         */
        public Section26 .MunicipalServicesSummary.CarryoversOfFunds getCarryoversOfFunds() {
            return carryoversOfFunds;
        }

        /**
         * Sets the value of the carryoversOfFunds property.
         * 
         * @param value
         *     allowed object is
         *     {@link Section26 .MunicipalServicesSummary.CarryoversOfFunds }
         *     
         */
        public void setCarryoversOfFunds(Section26 .MunicipalServicesSummary.CarryoversOfFunds value) {
            this.carryoversOfFunds = value;
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
         *         &lt;element name="BeginOfPeriod">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="ConsumersPrepayments" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
         *                   &lt;element name="ConsumersDebt" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
         *                   &lt;element name="ConsumersOverpayment" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="EndOfPeriod">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="ConsumersPrepayments" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
         *                   &lt;element name="ConsumersDebt" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
         *                   &lt;element name="ConsumersOverpayment" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
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
            "beginOfPeriod",
            "endOfPeriod"
        })
        public static class CarryoversOfFunds {

            @XmlElement(name = "BeginOfPeriod", required = true)
            protected Section26 .MunicipalServicesSummary.CarryoversOfFunds.BeginOfPeriod beginOfPeriod;
            @XmlElement(name = "EndOfPeriod", required = true)
            protected Section26 .MunicipalServicesSummary.CarryoversOfFunds.EndOfPeriod endOfPeriod;

            /**
             * Gets the value of the beginOfPeriod property.
             * 
             * @return
             *     possible object is
             *     {@link Section26 .MunicipalServicesSummary.CarryoversOfFunds.BeginOfPeriod }
             *     
             */
            public Section26 .MunicipalServicesSummary.CarryoversOfFunds.BeginOfPeriod getBeginOfPeriod() {
                return beginOfPeriod;
            }

            /**
             * Sets the value of the beginOfPeriod property.
             * 
             * @param value
             *     allowed object is
             *     {@link Section26 .MunicipalServicesSummary.CarryoversOfFunds.BeginOfPeriod }
             *     
             */
            public void setBeginOfPeriod(Section26 .MunicipalServicesSummary.CarryoversOfFunds.BeginOfPeriod value) {
                this.beginOfPeriod = value;
            }

            /**
             * Gets the value of the endOfPeriod property.
             * 
             * @return
             *     possible object is
             *     {@link Section26 .MunicipalServicesSummary.CarryoversOfFunds.EndOfPeriod }
             *     
             */
            public Section26 .MunicipalServicesSummary.CarryoversOfFunds.EndOfPeriod getEndOfPeriod() {
                return endOfPeriod;
            }

            /**
             * Sets the value of the endOfPeriod property.
             * 
             * @param value
             *     allowed object is
             *     {@link Section26 .MunicipalServicesSummary.CarryoversOfFunds.EndOfPeriod }
             *     
             */
            public void setEndOfPeriod(Section26 .MunicipalServicesSummary.CarryoversOfFunds.EndOfPeriod value) {
                this.endOfPeriod = value;
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
             *         &lt;element name="ConsumersPrepayments" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
             *         &lt;element name="ConsumersDebt" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
             *         &lt;element name="ConsumersOverpayment" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
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
                "consumersPrepayments",
                "consumersDebt",
                "consumersOverpayment"
            })
            public static class BeginOfPeriod {

                @XmlElement(name = "ConsumersPrepayments", required = true)
                protected BigDecimal consumersPrepayments;
                @XmlElement(name = "ConsumersDebt", required = true)
                protected BigDecimal consumersDebt;
                @XmlElement(name = "ConsumersOverpayment", required = true)
                protected BigDecimal consumersOverpayment;

                /**
                 * Gets the value of the consumersPrepayments property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getConsumersPrepayments() {
                    return consumersPrepayments;
                }

                /**
                 * Sets the value of the consumersPrepayments property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setConsumersPrepayments(BigDecimal value) {
                    this.consumersPrepayments = value;
                }

                /**
                 * Gets the value of the consumersDebt property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getConsumersDebt() {
                    return consumersDebt;
                }

                /**
                 * Sets the value of the consumersDebt property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setConsumersDebt(BigDecimal value) {
                    this.consumersDebt = value;
                }

                /**
                 * Gets the value of the consumersOverpayment property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getConsumersOverpayment() {
                    return consumersOverpayment;
                }

                /**
                 * Sets the value of the consumersOverpayment property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setConsumersOverpayment(BigDecimal value) {
                    this.consumersOverpayment = value;
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
             *         &lt;element name="ConsumersPrepayments" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
             *         &lt;element name="ConsumersDebt" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
             *         &lt;element name="ConsumersOverpayment" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
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
                "consumersPrepayments",
                "consumersDebt",
                "consumersOverpayment"
            })
            public static class EndOfPeriod {

                @XmlElement(name = "ConsumersPrepayments", required = true)
                protected BigDecimal consumersPrepayments;
                @XmlElement(name = "ConsumersDebt", required = true)
                protected BigDecimal consumersDebt;
                @XmlElement(name = "ConsumersOverpayment", required = true)
                protected BigDecimal consumersOverpayment;

                /**
                 * Gets the value of the consumersPrepayments property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getConsumersPrepayments() {
                    return consumersPrepayments;
                }

                /**
                 * Sets the value of the consumersPrepayments property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setConsumersPrepayments(BigDecimal value) {
                    this.consumersPrepayments = value;
                }

                /**
                 * Gets the value of the consumersDebt property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getConsumersDebt() {
                    return consumersDebt;
                }

                /**
                 * Sets the value of the consumersDebt property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setConsumersDebt(BigDecimal value) {
                    this.consumersDebt = value;
                }

                /**
                 * Gets the value of the consumersOverpayment property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getConsumersOverpayment() {
                    return consumersOverpayment;
                }

                /**
                 * Sets the value of the consumersOverpayment property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setConsumersOverpayment(BigDecimal value) {
                    this.consumersOverpayment = value;
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
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="CarryoversOfFunds">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="BeginOfPeriod">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="ConsumersPrepayments" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
     *                             &lt;element name="ConsumersOverpayment" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
     *                             &lt;element name="ConsumersDebt" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
     *                           &lt;/sequence>
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="EndOfPeriod">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="ConsumersPrepayments" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
     *                             &lt;element name="ConsumersOverpayment" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
     *                             &lt;element name="ConsumersDebt" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
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
     *         &lt;element name="Charges">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="HouseMaintenance" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
     *                   &lt;element name="CurrentRepairs" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
     *                   &lt;element name="ServiceManagement" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="MoneyRecieved">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="MoneyFromOwnersOrTenants" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
     *                   &lt;element name="ContributionsFromOwnersOrTenants" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
     *                   &lt;element name="Subsidies" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
     *                   &lt;element name="FundsFromCommonPropertyUse" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
     *                   &lt;element name="MiscellaneousIncome" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="TotalBalance" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
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
        "carryoversOfFunds",
        "charges",
        "moneyRecieved",
        "totalBalance"
    })
    public static class SummaryInfo {

        @XmlElement(name = "CarryoversOfFunds", required = true)
        protected Section26 .SummaryInfo.CarryoversOfFunds carryoversOfFunds;
        @XmlElement(name = "Charges", required = true)
        protected Section26 .SummaryInfo.Charges charges;
        @XmlElement(name = "MoneyRecieved", required = true)
        protected Section26 .SummaryInfo.MoneyRecieved moneyRecieved;
        @XmlElement(name = "TotalBalance", required = true)
        protected BigDecimal totalBalance;

        /**
         * Gets the value of the carryoversOfFunds property.
         * 
         * @return
         *     possible object is
         *     {@link Section26 .SummaryInfo.CarryoversOfFunds }
         *     
         */
        public Section26 .SummaryInfo.CarryoversOfFunds getCarryoversOfFunds() {
            return carryoversOfFunds;
        }

        /**
         * Sets the value of the carryoversOfFunds property.
         * 
         * @param value
         *     allowed object is
         *     {@link Section26 .SummaryInfo.CarryoversOfFunds }
         *     
         */
        public void setCarryoversOfFunds(Section26 .SummaryInfo.CarryoversOfFunds value) {
            this.carryoversOfFunds = value;
        }

        /**
         * Gets the value of the charges property.
         * 
         * @return
         *     possible object is
         *     {@link Section26 .SummaryInfo.Charges }
         *     
         */
        public Section26 .SummaryInfo.Charges getCharges() {
            return charges;
        }

        /**
         * Sets the value of the charges property.
         * 
         * @param value
         *     allowed object is
         *     {@link Section26 .SummaryInfo.Charges }
         *     
         */
        public void setCharges(Section26 .SummaryInfo.Charges value) {
            this.charges = value;
        }

        /**
         * Gets the value of the moneyRecieved property.
         * 
         * @return
         *     possible object is
         *     {@link Section26 .SummaryInfo.MoneyRecieved }
         *     
         */
        public Section26 .SummaryInfo.MoneyRecieved getMoneyRecieved() {
            return moneyRecieved;
        }

        /**
         * Sets the value of the moneyRecieved property.
         * 
         * @param value
         *     allowed object is
         *     {@link Section26 .SummaryInfo.MoneyRecieved }
         *     
         */
        public void setMoneyRecieved(Section26 .SummaryInfo.MoneyRecieved value) {
            this.moneyRecieved = value;
        }

        /**
         * Gets the value of the totalBalance property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getTotalBalance() {
            return totalBalance;
        }

        /**
         * Sets the value of the totalBalance property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setTotalBalance(BigDecimal value) {
            this.totalBalance = value;
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
         *         &lt;element name="BeginOfPeriod">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="ConsumersPrepayments" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
         *                   &lt;element name="ConsumersOverpayment" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
         *                   &lt;element name="ConsumersDebt" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
         *                 &lt;/sequence>
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="EndOfPeriod">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="ConsumersPrepayments" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
         *                   &lt;element name="ConsumersOverpayment" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
         *                   &lt;element name="ConsumersDebt" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
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
            "beginOfPeriod",
            "endOfPeriod"
        })
        public static class CarryoversOfFunds {

            @XmlElement(name = "BeginOfPeriod", required = true)
            protected Section26 .SummaryInfo.CarryoversOfFunds.BeginOfPeriod beginOfPeriod;
            @XmlElement(name = "EndOfPeriod", required = true)
            protected Section26 .SummaryInfo.CarryoversOfFunds.EndOfPeriod endOfPeriod;

            /**
             * Gets the value of the beginOfPeriod property.
             * 
             * @return
             *     possible object is
             *     {@link Section26 .SummaryInfo.CarryoversOfFunds.BeginOfPeriod }
             *     
             */
            public Section26 .SummaryInfo.CarryoversOfFunds.BeginOfPeriod getBeginOfPeriod() {
                return beginOfPeriod;
            }

            /**
             * Sets the value of the beginOfPeriod property.
             * 
             * @param value
             *     allowed object is
             *     {@link Section26 .SummaryInfo.CarryoversOfFunds.BeginOfPeriod }
             *     
             */
            public void setBeginOfPeriod(Section26 .SummaryInfo.CarryoversOfFunds.BeginOfPeriod value) {
                this.beginOfPeriod = value;
            }

            /**
             * Gets the value of the endOfPeriod property.
             * 
             * @return
             *     possible object is
             *     {@link Section26 .SummaryInfo.CarryoversOfFunds.EndOfPeriod }
             *     
             */
            public Section26 .SummaryInfo.CarryoversOfFunds.EndOfPeriod getEndOfPeriod() {
                return endOfPeriod;
            }

            /**
             * Sets the value of the endOfPeriod property.
             * 
             * @param value
             *     allowed object is
             *     {@link Section26 .SummaryInfo.CarryoversOfFunds.EndOfPeriod }
             *     
             */
            public void setEndOfPeriod(Section26 .SummaryInfo.CarryoversOfFunds.EndOfPeriod value) {
                this.endOfPeriod = value;
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
             *         &lt;element name="ConsumersPrepayments" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
             *         &lt;element name="ConsumersOverpayment" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
             *         &lt;element name="ConsumersDebt" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
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
                "consumersPrepayments",
                "consumersOverpayment",
                "consumersDebt"
            })
            public static class BeginOfPeriod {

                @XmlElement(name = "ConsumersPrepayments", required = true)
                protected BigDecimal consumersPrepayments;
                @XmlElement(name = "ConsumersOverpayment", required = true)
                protected BigDecimal consumersOverpayment;
                @XmlElement(name = "ConsumersDebt", required = true)
                protected BigDecimal consumersDebt;

                /**
                 * Gets the value of the consumersPrepayments property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getConsumersPrepayments() {
                    return consumersPrepayments;
                }

                /**
                 * Sets the value of the consumersPrepayments property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setConsumersPrepayments(BigDecimal value) {
                    this.consumersPrepayments = value;
                }

                /**
                 * Gets the value of the consumersOverpayment property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getConsumersOverpayment() {
                    return consumersOverpayment;
                }

                /**
                 * Sets the value of the consumersOverpayment property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setConsumersOverpayment(BigDecimal value) {
                    this.consumersOverpayment = value;
                }

                /**
                 * Gets the value of the consumersDebt property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getConsumersDebt() {
                    return consumersDebt;
                }

                /**
                 * Sets the value of the consumersDebt property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setConsumersDebt(BigDecimal value) {
                    this.consumersDebt = value;
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
             *         &lt;element name="ConsumersPrepayments" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
             *         &lt;element name="ConsumersOverpayment" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
             *         &lt;element name="ConsumersDebt" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
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
                "consumersPrepayments",
                "consumersOverpayment",
                "consumersDebt"
            })
            public static class EndOfPeriod {

                @XmlElement(name = "ConsumersPrepayments", required = true)
                protected BigDecimal consumersPrepayments;
                @XmlElement(name = "ConsumersOverpayment", required = true)
                protected BigDecimal consumersOverpayment;
                @XmlElement(name = "ConsumersDebt", required = true)
                protected BigDecimal consumersDebt;

                /**
                 * Gets the value of the consumersPrepayments property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getConsumersPrepayments() {
                    return consumersPrepayments;
                }

                /**
                 * Sets the value of the consumersPrepayments property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setConsumersPrepayments(BigDecimal value) {
                    this.consumersPrepayments = value;
                }

                /**
                 * Gets the value of the consumersOverpayment property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getConsumersOverpayment() {
                    return consumersOverpayment;
                }

                /**
                 * Sets the value of the consumersOverpayment property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setConsumersOverpayment(BigDecimal value) {
                    this.consumersOverpayment = value;
                }

                /**
                 * Gets the value of the consumersDebt property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getConsumersDebt() {
                    return consumersDebt;
                }

                /**
                 * Sets the value of the consumersDebt property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setConsumersDebt(BigDecimal value) {
                    this.consumersDebt = value;
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
         *       &lt;sequence>
         *         &lt;element name="HouseMaintenance" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
         *         &lt;element name="CurrentRepairs" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
         *         &lt;element name="ServiceManagement" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
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
            "houseMaintenance",
            "currentRepairs",
            "serviceManagement"
        })
        public static class Charges {

            @XmlElement(name = "HouseMaintenance", required = true)
            protected BigDecimal houseMaintenance;
            @XmlElement(name = "CurrentRepairs", required = true)
            protected BigDecimal currentRepairs;
            @XmlElement(name = "ServiceManagement", required = true)
            protected BigDecimal serviceManagement;

            /**
             * Gets the value of the houseMaintenance property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getHouseMaintenance() {
                return houseMaintenance;
            }

            /**
             * Sets the value of the houseMaintenance property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setHouseMaintenance(BigDecimal value) {
                this.houseMaintenance = value;
            }

            /**
             * Gets the value of the currentRepairs property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getCurrentRepairs() {
                return currentRepairs;
            }

            /**
             * Sets the value of the currentRepairs property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setCurrentRepairs(BigDecimal value) {
                this.currentRepairs = value;
            }

            /**
             * Gets the value of the serviceManagement property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getServiceManagement() {
                return serviceManagement;
            }

            /**
             * Sets the value of the serviceManagement property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setServiceManagement(BigDecimal value) {
                this.serviceManagement = value;
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
         *         &lt;element name="MoneyFromOwnersOrTenants" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
         *         &lt;element name="ContributionsFromOwnersOrTenants" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
         *         &lt;element name="Subsidies" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
         *         &lt;element name="FundsFromCommonPropertyUse" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
         *         &lt;element name="MiscellaneousIncome" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
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
            "moneyFromOwnersOrTenants",
            "contributionsFromOwnersOrTenants",
            "subsidies",
            "fundsFromCommonPropertyUse",
            "miscellaneousIncome"
        })
        public static class MoneyRecieved {

            @XmlElement(name = "MoneyFromOwnersOrTenants", required = true)
            protected BigDecimal moneyFromOwnersOrTenants;
            @XmlElement(name = "ContributionsFromOwnersOrTenants", required = true)
            protected BigDecimal contributionsFromOwnersOrTenants;
            @XmlElement(name = "Subsidies", required = true)
            protected BigDecimal subsidies;
            @XmlElement(name = "FundsFromCommonPropertyUse", required = true)
            protected BigDecimal fundsFromCommonPropertyUse;
            @XmlElement(name = "MiscellaneousIncome", required = true)
            protected BigDecimal miscellaneousIncome;

            /**
             * Gets the value of the moneyFromOwnersOrTenants property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getMoneyFromOwnersOrTenants() {
                return moneyFromOwnersOrTenants;
            }

            /**
             * Sets the value of the moneyFromOwnersOrTenants property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setMoneyFromOwnersOrTenants(BigDecimal value) {
                this.moneyFromOwnersOrTenants = value;
            }

            /**
             * Gets the value of the contributionsFromOwnersOrTenants property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getContributionsFromOwnersOrTenants() {
                return contributionsFromOwnersOrTenants;
            }

            /**
             * Sets the value of the contributionsFromOwnersOrTenants property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setContributionsFromOwnersOrTenants(BigDecimal value) {
                this.contributionsFromOwnersOrTenants = value;
            }

            /**
             * Gets the value of the subsidies property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getSubsidies() {
                return subsidies;
            }

            /**
             * Sets the value of the subsidies property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setSubsidies(BigDecimal value) {
                this.subsidies = value;
            }

            /**
             * Gets the value of the fundsFromCommonPropertyUse property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getFundsFromCommonPropertyUse() {
                return fundsFromCommonPropertyUse;
            }

            /**
             * Sets the value of the fundsFromCommonPropertyUse property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setFundsFromCommonPropertyUse(BigDecimal value) {
                this.fundsFromCommonPropertyUse = value;
            }

            /**
             * Gets the value of the miscellaneousIncome property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getMiscellaneousIncome() {
                return miscellaneousIncome;
            }

            /**
             * Sets the value of the miscellaneousIncome property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setMiscellaneousIncome(BigDecimal value) {
                this.miscellaneousIncome = value;
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
     *       &lt;sequence>
     *         &lt;element name="ClaimsSent">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
     *               &lt;maxInclusive value="9999"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="LawsuitSent">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
     *               &lt;maxInclusive value="9999"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="ClaimsMoneyRecieved" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
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
        "claimsSent",
        "lawsuitSent",
        "claimsMoneyRecieved"
    })
    public static class WorkOnClaims {

        @XmlElement(name = "ClaimsSent")
        protected int claimsSent;
        @XmlElement(name = "LawsuitSent")
        protected int lawsuitSent;
        @XmlElement(name = "ClaimsMoneyRecieved", required = true)
        protected BigDecimal claimsMoneyRecieved;

        /**
         * Gets the value of the claimsSent property.
         * 
         */
        public int getClaimsSent() {
            return claimsSent;
        }

        /**
         * Sets the value of the claimsSent property.
         * 
         */
        public void setClaimsSent(int value) {
            this.claimsSent = value;
        }

        /**
         * Gets the value of the lawsuitSent property.
         * 
         */
        public int getLawsuitSent() {
            return lawsuitSent;
        }

        /**
         * Sets the value of the lawsuitSent property.
         * 
         */
        public void setLawsuitSent(int value) {
            this.lawsuitSent = value;
        }

        /**
         * Gets the value of the claimsMoneyRecieved property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getClaimsMoneyRecieved() {
            return claimsMoneyRecieved;
        }

        /**
         * Sets the value of the claimsMoneyRecieved property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setClaimsMoneyRecieved(BigDecimal value) {
            this.claimsMoneyRecieved = value;
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
     *         &lt;element name="Received">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
     *               &lt;maxInclusive value="9999"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="Successful">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
     *               &lt;maxInclusive value="9999"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="Denied">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
     *               &lt;maxInclusive value="9999"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="RecalculationSum" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}SmallMoneyPositiveType"/>
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
        "received",
        "successful",
        "denied",
        "recalculationSum"
    })
    public static class WorkQualityClaims {

        @XmlElement(name = "Received")
        protected int received;
        @XmlElement(name = "Successful")
        protected int successful;
        @XmlElement(name = "Denied")
        protected int denied;
        @XmlElement(name = "RecalculationSum", required = true)
        protected BigDecimal recalculationSum;

        /**
         * Gets the value of the received property.
         * 
         */
        public int getReceived() {
            return received;
        }

        /**
         * Sets the value of the received property.
         * 
         */
        public void setReceived(int value) {
            this.received = value;
        }

        /**
         * Gets the value of the successful property.
         * 
         */
        public int getSuccessful() {
            return successful;
        }

        /**
         * Sets the value of the successful property.
         * 
         */
        public void setSuccessful(int value) {
            this.successful = value;
        }

        /**
         * Gets the value of the denied property.
         * 
         */
        public int getDenied() {
            return denied;
        }

        /**
         * Sets the value of the denied property.
         * 
         */
        public void setDenied(int value) {
            this.denied = value;
        }

        /**
         * Gets the value of the recalculationSum property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getRecalculationSum() {
            return recalculationSum;
        }

        /**
         * Sets the value of the recalculationSum property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setRecalculationSum(BigDecimal value) {
            this.recalculationSum = value;
        }

    }

}

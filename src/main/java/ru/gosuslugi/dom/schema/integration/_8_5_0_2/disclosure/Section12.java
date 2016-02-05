
package ru.gosuslugi.dom.schema.integration._8_5_0_2.disclosure;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.datatype.XMLGregorianCalendar;
import ru.gosuslugi.dom.schema.integration._8_5_0.AttachmentType;
import ru.gosuslugi.dom.schema.integration._8_5_0.NsiRef;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/disclosure/}SubSection1ElementType">
 *       &lt;sequence>
 *         &lt;element name="FinancialActivityInformation">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="AnnualFinancialStatements">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="BaseDocuments" maxOccurs="4" minOccurs="4">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}AttachmentType">
 *                                     &lt;sequence>
 *                                       &lt;element name="FormOfFinancialStatement">
 *                                         &lt;complexType>
 *                                           &lt;complexContent>
 *                                             &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}nsiRef">
 *                                               &lt;sequence>
 *                                                 &lt;element name="BaseDocumentTypes">
 *                                                   &lt;simpleType>
 *                                                     &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}nsiCodeType">
 *                                                       &lt;enumeration value="1"/>
 *                                                       &lt;enumeration value="2"/>
 *                                                       &lt;enumeration value="3"/>
 *                                                       &lt;enumeration value="4"/>
 *                                                     &lt;/restriction>
 *                                                   &lt;/simpleType>
 *                                                 &lt;/element>
 *                                               &lt;/sequence>
 *                                             &lt;/extension>
 *                                           &lt;/complexContent>
 *                                         &lt;/complexType>
 *                                       &lt;/element>
 *                                     &lt;/sequence>
 *                                   &lt;/extension>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="Optional" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}AttachmentType">
 *                                     &lt;sequence>
 *                                       &lt;element name="FormOfFinancialStatement" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}nsiRef"/>
 *                                     &lt;/sequence>
 *                                   &lt;/extension>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="ManagementServiceIncome" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}SmallMoneyPositiveType"/>
 *                   &lt;element name="ManagementServiceFlow" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}SmallMoneyPositiveType"/>
 *                   &lt;element name="SummaryOutstanding">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="ThermalEnergy">
 *                               &lt;complexType>
 *                                 &lt;simpleContent>
 *                                   &lt;extension base="&lt;http://dom.gosuslugi.ru/schema/integration/8.5.0.2/>SmallMoneyPositiveType">
 *                                     &lt;attribute name="Heating" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}SmallMoneyPositiveType" />
 *                                     &lt;attribute name="HotWater" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}SmallMoneyPositiveType" />
 *                                   &lt;/extension>
 *                                 &lt;/simpleContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                             &lt;element name="HotWater" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}SmallMoneyPositiveType"/>
 *                             &lt;element name="ColdWater" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}SmallMoneyPositiveType"/>
 *                             &lt;element name="Drainage" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}SmallMoneyPositiveType"/>
 *                             &lt;element name="GasSupply" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}SmallMoneyPositiveType"/>
 *                             &lt;element name="ElectricalEnergy" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}SmallMoneyPositiveType"/>
 *                             &lt;element name="OtherResourcesOrServices" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}SmallMoneyPositiveType"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="Estimates" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}AttachmentType" minOccurs="0"/>
 *                   &lt;element name="EstimatesReport" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}AttachmentType" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="ReportPeriodBeginDate" use="required" type="{http://www.w3.org/2001/XMLSchema}date" />
 *       &lt;attribute name="ReportPeriodEndDate" use="required" type="{http://www.w3.org/2001/XMLSchema}date" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "financialActivityInformation"
})
@XmlRootElement(name = "Section1_2")
public class Section12
    extends SubSection1ElementType
{

    @XmlElement(name = "FinancialActivityInformation", required = true)
    protected Section12 .FinancialActivityInformation financialActivityInformation;
    @XmlAttribute(name = "ReportPeriodBeginDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar reportPeriodBeginDate;
    @XmlAttribute(name = "ReportPeriodEndDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar reportPeriodEndDate;

    /**
     * Gets the value of the financialActivityInformation property.
     * 
     * @return
     *     possible object is
     *     {@link Section12 .FinancialActivityInformation }
     *     
     */
    public Section12 .FinancialActivityInformation getFinancialActivityInformation() {
        return financialActivityInformation;
    }

    /**
     * Sets the value of the financialActivityInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Section12 .FinancialActivityInformation }
     *     
     */
    public void setFinancialActivityInformation(Section12 .FinancialActivityInformation value) {
        this.financialActivityInformation = value;
    }

    /**
     * Gets the value of the reportPeriodBeginDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReportPeriodBeginDate() {
        return reportPeriodBeginDate;
    }

    /**
     * Sets the value of the reportPeriodBeginDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReportPeriodBeginDate(XMLGregorianCalendar value) {
        this.reportPeriodBeginDate = value;
    }

    /**
     * Gets the value of the reportPeriodEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getReportPeriodEndDate() {
        return reportPeriodEndDate;
    }

    /**
     * Sets the value of the reportPeriodEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setReportPeriodEndDate(XMLGregorianCalendar value) {
        this.reportPeriodEndDate = value;
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
     *         &lt;element name="AnnualFinancialStatements">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="BaseDocuments" maxOccurs="4" minOccurs="4">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}AttachmentType">
     *                           &lt;sequence>
     *                             &lt;element name="FormOfFinancialStatement">
     *                               &lt;complexType>
     *                                 &lt;complexContent>
     *                                   &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}nsiRef">
     *                                     &lt;sequence>
     *                                       &lt;element name="BaseDocumentTypes">
     *                                         &lt;simpleType>
     *                                           &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}nsiCodeType">
     *                                             &lt;enumeration value="1"/>
     *                                             &lt;enumeration value="2"/>
     *                                             &lt;enumeration value="3"/>
     *                                             &lt;enumeration value="4"/>
     *                                           &lt;/restriction>
     *                                         &lt;/simpleType>
     *                                       &lt;/element>
     *                                     &lt;/sequence>
     *                                   &lt;/extension>
     *                                 &lt;/complexContent>
     *                               &lt;/complexType>
     *                             &lt;/element>
     *                           &lt;/sequence>
     *                         &lt;/extension>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="Optional" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}AttachmentType">
     *                           &lt;sequence>
     *                             &lt;element name="FormOfFinancialStatement" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}nsiRef"/>
     *                           &lt;/sequence>
     *                         &lt;/extension>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="ManagementServiceIncome" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}SmallMoneyPositiveType"/>
     *         &lt;element name="ManagementServiceFlow" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}SmallMoneyPositiveType"/>
     *         &lt;element name="SummaryOutstanding">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ThermalEnergy">
     *                     &lt;complexType>
     *                       &lt;simpleContent>
     *                         &lt;extension base="&lt;http://dom.gosuslugi.ru/schema/integration/8.5.0.2/>SmallMoneyPositiveType">
     *                           &lt;attribute name="Heating" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}SmallMoneyPositiveType" />
     *                           &lt;attribute name="HotWater" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}SmallMoneyPositiveType" />
     *                         &lt;/extension>
     *                       &lt;/simpleContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                   &lt;element name="HotWater" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}SmallMoneyPositiveType"/>
     *                   &lt;element name="ColdWater" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}SmallMoneyPositiveType"/>
     *                   &lt;element name="Drainage" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}SmallMoneyPositiveType"/>
     *                   &lt;element name="GasSupply" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}SmallMoneyPositiveType"/>
     *                   &lt;element name="ElectricalEnergy" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}SmallMoneyPositiveType"/>
     *                   &lt;element name="OtherResourcesOrServices" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}SmallMoneyPositiveType"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Estimates" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}AttachmentType" minOccurs="0"/>
     *         &lt;element name="EstimatesReport" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}AttachmentType" minOccurs="0"/>
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
        "annualFinancialStatements",
        "managementServiceIncome",
        "managementServiceFlow",
        "summaryOutstanding",
        "estimates",
        "estimatesReport"
    })
    public static class FinancialActivityInformation {

        @XmlElement(name = "AnnualFinancialStatements", required = true)
        protected Section12 .FinancialActivityInformation.AnnualFinancialStatements annualFinancialStatements;
        @XmlElement(name = "ManagementServiceIncome", required = true)
        protected BigDecimal managementServiceIncome;
        @XmlElement(name = "ManagementServiceFlow", required = true)
        protected BigDecimal managementServiceFlow;
        @XmlElement(name = "SummaryOutstanding", required = true)
        protected Section12 .FinancialActivityInformation.SummaryOutstanding summaryOutstanding;
        @XmlElement(name = "Estimates")
        protected AttachmentType estimates;
        @XmlElement(name = "EstimatesReport")
        protected AttachmentType estimatesReport;

        /**
         * Gets the value of the annualFinancialStatements property.
         * 
         * @return
         *     possible object is
         *     {@link Section12 .FinancialActivityInformation.AnnualFinancialStatements }
         *     
         */
        public Section12 .FinancialActivityInformation.AnnualFinancialStatements getAnnualFinancialStatements() {
            return annualFinancialStatements;
        }

        /**
         * Sets the value of the annualFinancialStatements property.
         * 
         * @param value
         *     allowed object is
         *     {@link Section12 .FinancialActivityInformation.AnnualFinancialStatements }
         *     
         */
        public void setAnnualFinancialStatements(Section12 .FinancialActivityInformation.AnnualFinancialStatements value) {
            this.annualFinancialStatements = value;
        }

        /**
         * Gets the value of the managementServiceIncome property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getManagementServiceIncome() {
            return managementServiceIncome;
        }

        /**
         * Sets the value of the managementServiceIncome property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setManagementServiceIncome(BigDecimal value) {
            this.managementServiceIncome = value;
        }

        /**
         * Gets the value of the managementServiceFlow property.
         * 
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *     
         */
        public BigDecimal getManagementServiceFlow() {
            return managementServiceFlow;
        }

        /**
         * Sets the value of the managementServiceFlow property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setManagementServiceFlow(BigDecimal value) {
            this.managementServiceFlow = value;
        }

        /**
         * Gets the value of the summaryOutstanding property.
         * 
         * @return
         *     possible object is
         *     {@link Section12 .FinancialActivityInformation.SummaryOutstanding }
         *     
         */
        public Section12 .FinancialActivityInformation.SummaryOutstanding getSummaryOutstanding() {
            return summaryOutstanding;
        }

        /**
         * Sets the value of the summaryOutstanding property.
         * 
         * @param value
         *     allowed object is
         *     {@link Section12 .FinancialActivityInformation.SummaryOutstanding }
         *     
         */
        public void setSummaryOutstanding(Section12 .FinancialActivityInformation.SummaryOutstanding value) {
            this.summaryOutstanding = value;
        }

        /**
         * Gets the value of the estimates property.
         * 
         * @return
         *     possible object is
         *     {@link AttachmentType }
         *     
         */
        public AttachmentType getEstimates() {
            return estimates;
        }

        /**
         * Sets the value of the estimates property.
         * 
         * @param value
         *     allowed object is
         *     {@link AttachmentType }
         *     
         */
        public void setEstimates(AttachmentType value) {
            this.estimates = value;
        }

        /**
         * Gets the value of the estimatesReport property.
         * 
         * @return
         *     possible object is
         *     {@link AttachmentType }
         *     
         */
        public AttachmentType getEstimatesReport() {
            return estimatesReport;
        }

        /**
         * Sets the value of the estimatesReport property.
         * 
         * @param value
         *     allowed object is
         *     {@link AttachmentType }
         *     
         */
        public void setEstimatesReport(AttachmentType value) {
            this.estimatesReport = value;
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
         *         &lt;element name="BaseDocuments" maxOccurs="4" minOccurs="4">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}AttachmentType">
         *                 &lt;sequence>
         *                   &lt;element name="FormOfFinancialStatement">
         *                     &lt;complexType>
         *                       &lt;complexContent>
         *                         &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}nsiRef">
         *                           &lt;sequence>
         *                             &lt;element name="BaseDocumentTypes">
         *                               &lt;simpleType>
         *                                 &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}nsiCodeType">
         *                                   &lt;enumeration value="1"/>
         *                                   &lt;enumeration value="2"/>
         *                                   &lt;enumeration value="3"/>
         *                                   &lt;enumeration value="4"/>
         *                                 &lt;/restriction>
         *                               &lt;/simpleType>
         *                             &lt;/element>
         *                           &lt;/sequence>
         *                         &lt;/extension>
         *                       &lt;/complexContent>
         *                     &lt;/complexType>
         *                   &lt;/element>
         *                 &lt;/sequence>
         *               &lt;/extension>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="Optional" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}AttachmentType">
         *                 &lt;sequence>
         *                   &lt;element name="FormOfFinancialStatement" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}nsiRef"/>
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
        @XmlType(name = "", propOrder = {
            "baseDocuments",
            "optional"
        })
        public static class AnnualFinancialStatements {

            @XmlElement(name = "BaseDocuments", required = true)
            protected List<Section12 .FinancialActivityInformation.AnnualFinancialStatements.BaseDocuments> baseDocuments;
            @XmlElement(name = "Optional")
            protected List<Section12 .FinancialActivityInformation.AnnualFinancialStatements.Optional> optional;

            /**
             * Gets the value of the baseDocuments property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the baseDocuments property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getBaseDocuments().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Section12 .FinancialActivityInformation.AnnualFinancialStatements.BaseDocuments }
             * 
             * 
             */
            public List<Section12 .FinancialActivityInformation.AnnualFinancialStatements.BaseDocuments> getBaseDocuments() {
                if (baseDocuments == null) {
                    baseDocuments = new ArrayList<Section12 .FinancialActivityInformation.AnnualFinancialStatements.BaseDocuments>();
                }
                return this.baseDocuments;
            }

            /**
             * Gets the value of the optional property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the optional property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getOptional().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Section12 .FinancialActivityInformation.AnnualFinancialStatements.Optional }
             * 
             * 
             */
            public List<Section12 .FinancialActivityInformation.AnnualFinancialStatements.Optional> getOptional() {
                if (optional == null) {
                    optional = new ArrayList<Section12 .FinancialActivityInformation.AnnualFinancialStatements.Optional>();
                }
                return this.optional;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}AttachmentType">
             *       &lt;sequence>
             *         &lt;element name="FormOfFinancialStatement">
             *           &lt;complexType>
             *             &lt;complexContent>
             *               &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}nsiRef">
             *                 &lt;sequence>
             *                   &lt;element name="BaseDocumentTypes">
             *                     &lt;simpleType>
             *                       &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}nsiCodeType">
             *                         &lt;enumeration value="1"/>
             *                         &lt;enumeration value="2"/>
             *                         &lt;enumeration value="3"/>
             *                         &lt;enumeration value="4"/>
             *                       &lt;/restriction>
             *                     &lt;/simpleType>
             *                   &lt;/element>
             *                 &lt;/sequence>
             *               &lt;/extension>
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
                "formOfFinancialStatement"
            })
            public static class BaseDocuments
                extends AttachmentType
            {

                @XmlElement(name = "FormOfFinancialStatement", required = true)
                protected Section12 .FinancialActivityInformation.AnnualFinancialStatements.BaseDocuments.FormOfFinancialStatement formOfFinancialStatement;

                /**
                 * Gets the value of the formOfFinancialStatement property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Section12 .FinancialActivityInformation.AnnualFinancialStatements.BaseDocuments.FormOfFinancialStatement }
                 *     
                 */
                public Section12 .FinancialActivityInformation.AnnualFinancialStatements.BaseDocuments.FormOfFinancialStatement getFormOfFinancialStatement() {
                    return formOfFinancialStatement;
                }

                /**
                 * Sets the value of the formOfFinancialStatement property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Section12 .FinancialActivityInformation.AnnualFinancialStatements.BaseDocuments.FormOfFinancialStatement }
                 *     
                 */
                public void setFormOfFinancialStatement(Section12 .FinancialActivityInformation.AnnualFinancialStatements.BaseDocuments.FormOfFinancialStatement value) {
                    this.formOfFinancialStatement = value;
                }


                /**
                 * <p>Java class for anonymous complex type.
                 * 
                 * <p>The following schema fragment specifies the expected content contained within this class.
                 * 
                 * <pre>
                 * &lt;complexType>
                 *   &lt;complexContent>
                 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}nsiRef">
                 *       &lt;sequence>
                 *         &lt;element name="BaseDocumentTypes">
                 *           &lt;simpleType>
                 *             &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}nsiCodeType">
                 *               &lt;enumeration value="1"/>
                 *               &lt;enumeration value="2"/>
                 *               &lt;enumeration value="3"/>
                 *               &lt;enumeration value="4"/>
                 *             &lt;/restriction>
                 *           &lt;/simpleType>
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
                    "baseDocumentTypes"
                })
                public static class FormOfFinancialStatement
                    extends NsiRef
                {

                    @XmlElement(name = "BaseDocumentTypes", required = true)
                    protected String baseDocumentTypes;

                    /**
                     * Gets the value of the baseDocumentTypes property.
                     * 
                     * @return
                     *     possible object is
                     *     {@link String }
                     *     
                     */
                    public String getBaseDocumentTypes() {
                        return baseDocumentTypes;
                    }

                    /**
                     * Sets the value of the baseDocumentTypes property.
                     * 
                     * @param value
                     *     allowed object is
                     *     {@link String }
                     *     
                     */
                    public void setBaseDocumentTypes(String value) {
                        this.baseDocumentTypes = value;
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
             *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}AttachmentType">
             *       &lt;sequence>
             *         &lt;element name="FormOfFinancialStatement" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}nsiRef"/>
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
                "formOfFinancialStatement"
            })
            public static class Optional
                extends AttachmentType
            {

                @XmlElement(name = "FormOfFinancialStatement", required = true)
                protected NsiRef formOfFinancialStatement;

                /**
                 * Gets the value of the formOfFinancialStatement property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link NsiRef }
                 *     
                 */
                public NsiRef getFormOfFinancialStatement() {
                    return formOfFinancialStatement;
                }

                /**
                 * Sets the value of the formOfFinancialStatement property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link NsiRef }
                 *     
                 */
                public void setFormOfFinancialStatement(NsiRef value) {
                    this.formOfFinancialStatement = value;
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
         *         &lt;element name="ThermalEnergy">
         *           &lt;complexType>
         *             &lt;simpleContent>
         *               &lt;extension base="&lt;http://dom.gosuslugi.ru/schema/integration/8.5.0.2/>SmallMoneyPositiveType">
         *                 &lt;attribute name="Heating" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}SmallMoneyPositiveType" />
         *                 &lt;attribute name="HotWater" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}SmallMoneyPositiveType" />
         *               &lt;/extension>
         *             &lt;/simpleContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *         &lt;element name="HotWater" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}SmallMoneyPositiveType"/>
         *         &lt;element name="ColdWater" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}SmallMoneyPositiveType"/>
         *         &lt;element name="Drainage" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}SmallMoneyPositiveType"/>
         *         &lt;element name="GasSupply" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}SmallMoneyPositiveType"/>
         *         &lt;element name="ElectricalEnergy" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}SmallMoneyPositiveType"/>
         *         &lt;element name="OtherResourcesOrServices" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}SmallMoneyPositiveType"/>
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
            "thermalEnergy",
            "hotWater",
            "coldWater",
            "drainage",
            "gasSupply",
            "electricalEnergy",
            "otherResourcesOrServices"
        })
        public static class SummaryOutstanding {

            @XmlElement(name = "ThermalEnergy", required = true)
            protected Section12 .FinancialActivityInformation.SummaryOutstanding.ThermalEnergy thermalEnergy;
            @XmlElement(name = "HotWater", required = true)
            protected BigDecimal hotWater;
            @XmlElement(name = "ColdWater", required = true)
            protected BigDecimal coldWater;
            @XmlElement(name = "Drainage", required = true)
            protected BigDecimal drainage;
            @XmlElement(name = "GasSupply", required = true)
            protected BigDecimal gasSupply;
            @XmlElement(name = "ElectricalEnergy", required = true)
            protected BigDecimal electricalEnergy;
            @XmlElement(name = "OtherResourcesOrServices", required = true)
            protected BigDecimal otherResourcesOrServices;

            /**
             * Gets the value of the thermalEnergy property.
             * 
             * @return
             *     possible object is
             *     {@link Section12 .FinancialActivityInformation.SummaryOutstanding.ThermalEnergy }
             *     
             */
            public Section12 .FinancialActivityInformation.SummaryOutstanding.ThermalEnergy getThermalEnergy() {
                return thermalEnergy;
            }

            /**
             * Sets the value of the thermalEnergy property.
             * 
             * @param value
             *     allowed object is
             *     {@link Section12 .FinancialActivityInformation.SummaryOutstanding.ThermalEnergy }
             *     
             */
            public void setThermalEnergy(Section12 .FinancialActivityInformation.SummaryOutstanding.ThermalEnergy value) {
                this.thermalEnergy = value;
            }

            /**
             * Gets the value of the hotWater property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getHotWater() {
                return hotWater;
            }

            /**
             * Sets the value of the hotWater property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setHotWater(BigDecimal value) {
                this.hotWater = value;
            }

            /**
             * Gets the value of the coldWater property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getColdWater() {
                return coldWater;
            }

            /**
             * Sets the value of the coldWater property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setColdWater(BigDecimal value) {
                this.coldWater = value;
            }

            /**
             * Gets the value of the drainage property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getDrainage() {
                return drainage;
            }

            /**
             * Sets the value of the drainage property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setDrainage(BigDecimal value) {
                this.drainage = value;
            }

            /**
             * Gets the value of the gasSupply property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getGasSupply() {
                return gasSupply;
            }

            /**
             * Sets the value of the gasSupply property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setGasSupply(BigDecimal value) {
                this.gasSupply = value;
            }

            /**
             * Gets the value of the electricalEnergy property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getElectricalEnergy() {
                return electricalEnergy;
            }

            /**
             * Sets the value of the electricalEnergy property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setElectricalEnergy(BigDecimal value) {
                this.electricalEnergy = value;
            }

            /**
             * Gets the value of the otherResourcesOrServices property.
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getOtherResourcesOrServices() {
                return otherResourcesOrServices;
            }

            /**
             * Sets the value of the otherResourcesOrServices property.
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setOtherResourcesOrServices(BigDecimal value) {
                this.otherResourcesOrServices = value;
            }


            /**
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;simpleContent>
             *     &lt;extension base="&lt;http://dom.gosuslugi.ru/schema/integration/8.5.0.2/>SmallMoneyPositiveType">
             *       &lt;attribute name="Heating" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}SmallMoneyPositiveType" />
             *       &lt;attribute name="HotWater" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.2/}SmallMoneyPositiveType" />
             *     &lt;/extension>
             *   &lt;/simpleContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "value"
            })
            public static class ThermalEnergy {

                @XmlValue
                protected BigDecimal value;
                @XmlAttribute(name = "Heating")
                protected BigDecimal heating;
                @XmlAttribute(name = "HotWater")
                protected BigDecimal hotWater;

                /**
                 * Неотрицательная маленькая сумма
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getValue() {
                    return value;
                }

                /**
                 * Sets the value of the value property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setValue(BigDecimal value) {
                    this.value = value;
                }

                /**
                 * Gets the value of the heating property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getHeating() {
                    return heating;
                }

                /**
                 * Sets the value of the heating property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setHeating(BigDecimal value) {
                    this.heating = value;
                }

                /**
                 * Gets the value of the hotWater property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigDecimal }
                 *     
                 */
                public BigDecimal getHotWater() {
                    return hotWater;
                }

                /**
                 * Sets the value of the hotWater property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigDecimal }
                 *     
                 */
                public void setHotWater(BigDecimal value) {
                    this.hotWater = value;
                }

            }

        }

    }

}

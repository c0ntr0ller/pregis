
package ru.gosuslugi.dom.schema.integration._8_6_0_6.inspection;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import ru.gosuslugi.dom.schema.integration._8_6_0.NsiRef;


/**
 * Плановая проверка (пункт плана проверок)
 * 
 * <p>Java class for PlannedExaminationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PlannedExaminationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NumberInPlan">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/inspection/}NumberType">
 *               &lt;totalDigits value="3"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="Subject" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/inspection/}ScheduledExaminationSubjectInfoType"/>
 *         &lt;element name="Objective" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/inspection/}String2000Type"/>
 *         &lt;element name="Base" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}nsiRef"/>
 *         &lt;element name="AdditionalInfoAboutExamBase" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/inspection/}String2000Type" minOccurs="0"/>
 *         &lt;element name="LastExaminationEndDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="DateFrom" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="Duration">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element name="WorkDays">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}double">
 *                         &lt;minInclusive value="0"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="WorkHours">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}double">
 *                         &lt;minInclusive value="0"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ExaminationForm" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/}nsiRef"/>
 *         &lt;element name="CooperationWith" type="{http://dom.gosuslugi.ru/schema/integration/8.6.0.6/inspection/}String2000Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PlannedExaminationType", propOrder = {
    "numberInPlan",
    "subject",
    "objective",
    "base",
    "additionalInfoAboutExamBase",
    "lastExaminationEndDate",
    "dateFrom",
    "duration",
    "examinationForm",
    "cooperationWith"
})
@XmlSeeAlso({
    ru.gosuslugi.dom.schema.integration._8_6_0_6.inspection.ExportInspectionPlanResultType.PlannedExamination.PlannedExaminationInfo.class
})
public class PlannedExaminationType {

    @XmlElement(name = "NumberInPlan")
    protected int numberInPlan;
    @XmlElement(name = "Subject", required = true)
    protected ScheduledExaminationSubjectInfoType subject;
    @XmlElement(name = "Objective", required = true)
    protected String objective;
    @XmlElement(name = "Base", required = true)
    protected NsiRef base;
    @XmlElement(name = "AdditionalInfoAboutExamBase")
    protected String additionalInfoAboutExamBase;
    @XmlElement(name = "LastExaminationEndDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar lastExaminationEndDate;
    @XmlElement(name = "DateFrom", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dateFrom;
    @XmlElement(name = "Duration", required = true)
    protected PlannedExaminationType.Duration duration;
    @XmlElement(name = "ExaminationForm", required = true)
    protected NsiRef examinationForm;
    @XmlElement(name = "CooperationWith")
    protected String cooperationWith;

    /**
     * Gets the value of the numberInPlan property.
     * 
     */
    public int getNumberInPlan() {
        return numberInPlan;
    }

    /**
     * Sets the value of the numberInPlan property.
     * 
     */
    public void setNumberInPlan(int value) {
        this.numberInPlan = value;
    }

    /**
     * Gets the value of the subject property.
     * 
     * @return
     *     possible object is
     *     {@link ScheduledExaminationSubjectInfoType }
     *     
     */
    public ScheduledExaminationSubjectInfoType getSubject() {
        return subject;
    }

    /**
     * Sets the value of the subject property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScheduledExaminationSubjectInfoType }
     *     
     */
    public void setSubject(ScheduledExaminationSubjectInfoType value) {
        this.subject = value;
    }

    /**
     * Gets the value of the objective property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjective() {
        return objective;
    }

    /**
     * Sets the value of the objective property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjective(String value) {
        this.objective = value;
    }

    /**
     * Gets the value of the base property.
     * 
     * @return
     *     possible object is
     *     {@link NsiRef }
     *     
     */
    public NsiRef getBase() {
        return base;
    }

    /**
     * Sets the value of the base property.
     * 
     * @param value
     *     allowed object is
     *     {@link NsiRef }
     *     
     */
    public void setBase(NsiRef value) {
        this.base = value;
    }

    /**
     * Gets the value of the additionalInfoAboutExamBase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalInfoAboutExamBase() {
        return additionalInfoAboutExamBase;
    }

    /**
     * Sets the value of the additionalInfoAboutExamBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalInfoAboutExamBase(String value) {
        this.additionalInfoAboutExamBase = value;
    }

    /**
     * Gets the value of the lastExaminationEndDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLastExaminationEndDate() {
        return lastExaminationEndDate;
    }

    /**
     * Sets the value of the lastExaminationEndDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLastExaminationEndDate(XMLGregorianCalendar value) {
        this.lastExaminationEndDate = value;
    }

    /**
     * Gets the value of the dateFrom property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateFrom() {
        return dateFrom;
    }

    /**
     * Sets the value of the dateFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateFrom(XMLGregorianCalendar value) {
        this.dateFrom = value;
    }

    /**
     * Gets the value of the duration property.
     * 
     * @return
     *     possible object is
     *     {@link PlannedExaminationType.Duration }
     *     
     */
    public PlannedExaminationType.Duration getDuration() {
        return duration;
    }

    /**
     * Sets the value of the duration property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlannedExaminationType.Duration }
     *     
     */
    public void setDuration(PlannedExaminationType.Duration value) {
        this.duration = value;
    }

    /**
     * Gets the value of the examinationForm property.
     * 
     * @return
     *     possible object is
     *     {@link NsiRef }
     *     
     */
    public NsiRef getExaminationForm() {
        return examinationForm;
    }

    /**
     * Sets the value of the examinationForm property.
     * 
     * @param value
     *     allowed object is
     *     {@link NsiRef }
     *     
     */
    public void setExaminationForm(NsiRef value) {
        this.examinationForm = value;
    }

    /**
     * Gets the value of the cooperationWith property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCooperationWith() {
        return cooperationWith;
    }

    /**
     * Sets the value of the cooperationWith property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCooperationWith(String value) {
        this.cooperationWith = value;
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
     *         &lt;element name="WorkDays">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}double">
     *               &lt;minInclusive value="0"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="WorkHours">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}double">
     *               &lt;minInclusive value="0"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
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
        "workDays",
        "workHours"
    })
    public static class Duration {

        @XmlElement(name = "WorkDays")
        protected Double workDays;
        @XmlElement(name = "WorkHours")
        protected Double workHours;

        /**
         * Gets the value of the workDays property.
         * 
         * @return
         *     possible object is
         *     {@link Double }
         *     
         */
        public Double getWorkDays() {
            return workDays;
        }

        /**
         * Sets the value of the workDays property.
         * 
         * @param value
         *     allowed object is
         *     {@link Double }
         *     
         */
        public void setWorkDays(Double value) {
            this.workDays = value;
        }

        /**
         * Gets the value of the workHours property.
         * 
         * @return
         *     possible object is
         *     {@link Double }
         *     
         */
        public Double getWorkHours() {
            return workHours;
        }

        /**
         * Sets the value of the workHours property.
         * 
         * @param value
         *     allowed object is
         *     {@link Double }
         *     
         */
        public void setWorkHours(Double value) {
            this.workHours = value;
        }

    }

}


package ru.gosuslugi.dom.schema.integration._8_5_0_4.inspection;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import ru.gosuslugi.dom.schema.integration._8_5_0.AttachmentType;
import ru.gosuslugi.dom.schema.integration._8_5_0.NsiRef;


/**
 * Проверка.
 * 
 * <p>Java class for ExaminationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExaminationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ExaminationForm" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}nsiRef"/>
 *         &lt;element name="OrderNumber" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/inspection/}String255Type" minOccurs="0"/>
 *         &lt;element name="OrderDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="ExaminationTypeType">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element name="Scheduled">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Subject" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/inspection/}ScheduledExaminationSubjectInfoType"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="Unscheduled">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="Subject" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/inspection/}UnscheduledExaminationSubjectInfoType"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="OversightActivitiesRef" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}nsiRef"/>
 *         &lt;element name="Base" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}nsiRef"/>
 *         &lt;element name="AdditionalInfoAboutExamBase" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/inspection/}String2000Type" minOccurs="0"/>
 *         &lt;element name="Objective" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/inspection/}String2000Type"/>
 *         &lt;element name="From" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="To" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="Duration">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;choice>
 *                   &lt;element name="WorkDays">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/inspection/}TimeUnitCountType">
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="WorkHours">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/inspection/}TimeUnitCountType">
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                 &lt;/choice>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Object" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}nsiRef" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Tasks" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/inspection/}String2000Type" minOccurs="0"/>
 *         &lt;element name="AdditionalInfo" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/inspection/}String2000Type" minOccurs="0"/>
 *         &lt;element name="ExecutingInfo" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Event" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/inspection/}ExaminationEventType" maxOccurs="unbounded"/>
 *                   &lt;element name="Place" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/inspection/}ExaminationPlaceType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="FinishedInfo" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Result" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/inspection/}ExaminationResultType"/>
 *                   &lt;element name="OtherDocument" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}AttachmentType" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="CancelledInfo" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/inspection/}CancelledInfoType" minOccurs="0"/>
 *         &lt;element name="Attachments" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}AttachmentType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExaminationType", propOrder = {
    "examinationForm",
    "orderNumber",
    "orderDate",
    "examinationTypeType",
    "oversightActivitiesRef",
    "base",
    "additionalInfoAboutExamBase",
    "objective",
    "from",
    "to",
    "duration",
    "object",
    "tasks",
    "additionalInfo",
    "executingInfo",
    "finishedInfo",
    "cancelledInfo",
    "attachments"
})
public class ExaminationType {

    @XmlElement(name = "ExaminationForm", required = true)
    protected NsiRef examinationForm;
    @XmlElement(name = "OrderNumber")
    protected String orderNumber;
    @XmlElement(name = "OrderDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar orderDate;
    @XmlElement(name = "ExaminationTypeType", required = true)
    protected ExaminationType.ExaminationTypeType examinationTypeType;
    @XmlElement(name = "OversightActivitiesRef", required = true)
    protected NsiRef oversightActivitiesRef;
    @XmlElement(name = "Base", required = true)
    protected NsiRef base;
    @XmlElement(name = "AdditionalInfoAboutExamBase")
    protected String additionalInfoAboutExamBase;
    @XmlElement(name = "Objective", required = true)
    protected String objective;
    @XmlElement(name = "From")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar from;
    @XmlElement(name = "To")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar to;
    @XmlElement(name = "Duration", required = true)
    protected ExaminationType.Duration duration;
    @XmlElement(name = "Object")
    protected List<NsiRef> object;
    @XmlElement(name = "Tasks")
    protected String tasks;
    @XmlElement(name = "AdditionalInfo")
    protected String additionalInfo;
    @XmlElement(name = "ExecutingInfo")
    protected ExaminationType.ExecutingInfo executingInfo;
    @XmlElement(name = "FinishedInfo")
    protected ExaminationType.FinishedInfo finishedInfo;
    @XmlElement(name = "CancelledInfo")
    protected CancelledInfoType cancelledInfo;
    @XmlElement(name = "Attachments")
    protected List<AttachmentType> attachments;

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
     * Gets the value of the orderNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * Sets the value of the orderNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderNumber(String value) {
        this.orderNumber = value;
    }

    /**
     * Gets the value of the orderDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOrderDate() {
        return orderDate;
    }

    /**
     * Sets the value of the orderDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOrderDate(XMLGregorianCalendar value) {
        this.orderDate = value;
    }

    /**
     * Gets the value of the examinationTypeType property.
     * 
     * @return
     *     possible object is
     *     {@link ExaminationType.ExaminationTypeType }
     *     
     */
    public ExaminationType.ExaminationTypeType getExaminationTypeType() {
        return examinationTypeType;
    }

    /**
     * Sets the value of the examinationTypeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExaminationType.ExaminationTypeType }
     *     
     */
    public void setExaminationTypeType(ExaminationType.ExaminationTypeType value) {
        this.examinationTypeType = value;
    }

    /**
     * Gets the value of the oversightActivitiesRef property.
     * 
     * @return
     *     possible object is
     *     {@link NsiRef }
     *     
     */
    public NsiRef getOversightActivitiesRef() {
        return oversightActivitiesRef;
    }

    /**
     * Sets the value of the oversightActivitiesRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link NsiRef }
     *     
     */
    public void setOversightActivitiesRef(NsiRef value) {
        this.oversightActivitiesRef = value;
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
     * Gets the value of the from property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFrom() {
        return from;
    }

    /**
     * Sets the value of the from property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFrom(XMLGregorianCalendar value) {
        this.from = value;
    }

    /**
     * Gets the value of the to property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTo() {
        return to;
    }

    /**
     * Sets the value of the to property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTo(XMLGregorianCalendar value) {
        this.to = value;
    }

    /**
     * Gets the value of the duration property.
     * 
     * @return
     *     possible object is
     *     {@link ExaminationType.Duration }
     *     
     */
    public ExaminationType.Duration getDuration() {
        return duration;
    }

    /**
     * Sets the value of the duration property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExaminationType.Duration }
     *     
     */
    public void setDuration(ExaminationType.Duration value) {
        this.duration = value;
    }

    /**
     * Gets the value of the object property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the object property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getObject().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NsiRef }
     * 
     * 
     */
    public List<NsiRef> getObject() {
        if (object == null) {
            object = new ArrayList<NsiRef>();
        }
        return this.object;
    }

    /**
     * Gets the value of the tasks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTasks() {
        return tasks;
    }

    /**
     * Sets the value of the tasks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTasks(String value) {
        this.tasks = value;
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
     * Gets the value of the executingInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ExaminationType.ExecutingInfo }
     *     
     */
    public ExaminationType.ExecutingInfo getExecutingInfo() {
        return executingInfo;
    }

    /**
     * Sets the value of the executingInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExaminationType.ExecutingInfo }
     *     
     */
    public void setExecutingInfo(ExaminationType.ExecutingInfo value) {
        this.executingInfo = value;
    }

    /**
     * Gets the value of the finishedInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ExaminationType.FinishedInfo }
     *     
     */
    public ExaminationType.FinishedInfo getFinishedInfo() {
        return finishedInfo;
    }

    /**
     * Sets the value of the finishedInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExaminationType.FinishedInfo }
     *     
     */
    public void setFinishedInfo(ExaminationType.FinishedInfo value) {
        this.finishedInfo = value;
    }

    /**
     * Gets the value of the cancelledInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CancelledInfoType }
     *     
     */
    public CancelledInfoType getCancelledInfo() {
        return cancelledInfo;
    }

    /**
     * Sets the value of the cancelledInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CancelledInfoType }
     *     
     */
    public void setCancelledInfo(CancelledInfoType value) {
        this.cancelledInfo = value;
    }

    /**
     * Gets the value of the attachments property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attachments property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttachments().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AttachmentType }
     * 
     * 
     */
    public List<AttachmentType> getAttachments() {
        if (attachments == null) {
            attachments = new ArrayList<AttachmentType>();
        }
        return this.attachments;
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
     *             &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/inspection/}TimeUnitCountType">
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="WorkHours">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/inspection/}TimeUnitCountType">
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
     *         &lt;element name="Scheduled">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Subject" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/inspection/}ScheduledExaminationSubjectInfoType"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Unscheduled">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="Subject" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/inspection/}UnscheduledExaminationSubjectInfoType"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
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
        "scheduled",
        "unscheduled"
    })
    public static class ExaminationTypeType {

        @XmlElement(name = "Scheduled")
        protected ExaminationType.ExaminationTypeType.Scheduled scheduled;
        @XmlElement(name = "Unscheduled")
        protected ExaminationType.ExaminationTypeType.Unscheduled unscheduled;

        /**
         * Gets the value of the scheduled property.
         * 
         * @return
         *     possible object is
         *     {@link ExaminationType.ExaminationTypeType.Scheduled }
         *     
         */
        public ExaminationType.ExaminationTypeType.Scheduled getScheduled() {
            return scheduled;
        }

        /**
         * Sets the value of the scheduled property.
         * 
         * @param value
         *     allowed object is
         *     {@link ExaminationType.ExaminationTypeType.Scheduled }
         *     
         */
        public void setScheduled(ExaminationType.ExaminationTypeType.Scheduled value) {
            this.scheduled = value;
        }

        /**
         * Gets the value of the unscheduled property.
         * 
         * @return
         *     possible object is
         *     {@link ExaminationType.ExaminationTypeType.Unscheduled }
         *     
         */
        public ExaminationType.ExaminationTypeType.Unscheduled getUnscheduled() {
            return unscheduled;
        }

        /**
         * Sets the value of the unscheduled property.
         * 
         * @param value
         *     allowed object is
         *     {@link ExaminationType.ExaminationTypeType.Unscheduled }
         *     
         */
        public void setUnscheduled(ExaminationType.ExaminationTypeType.Unscheduled value) {
            this.unscheduled = value;
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
         *         &lt;element name="Subject" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/inspection/}ScheduledExaminationSubjectInfoType"/>
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
            "subject"
        })
        public static class Scheduled {

            @XmlElement(name = "Subject", required = true)
            protected ScheduledExaminationSubjectInfoType subject;

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
         *         &lt;element name="Subject" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/inspection/}UnscheduledExaminationSubjectInfoType"/>
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
            "subject"
        })
        public static class Unscheduled {

            @XmlElement(name = "Subject", required = true)
            protected UnscheduledExaminationSubjectInfoType subject;

            /**
             * Gets the value of the subject property.
             * 
             * @return
             *     possible object is
             *     {@link UnscheduledExaminationSubjectInfoType }
             *     
             */
            public UnscheduledExaminationSubjectInfoType getSubject() {
                return subject;
            }

            /**
             * Sets the value of the subject property.
             * 
             * @param value
             *     allowed object is
             *     {@link UnscheduledExaminationSubjectInfoType }
             *     
             */
            public void setSubject(UnscheduledExaminationSubjectInfoType value) {
                this.subject = value;
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
     *         &lt;element name="Event" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/inspection/}ExaminationEventType" maxOccurs="unbounded"/>
     *         &lt;element name="Place" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/inspection/}ExaminationPlaceType" maxOccurs="unbounded" minOccurs="0"/>
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
        "event",
        "place"
    })
    public static class ExecutingInfo {

        @XmlElement(name = "Event", required = true)
        protected List<ExaminationEventType> event;
        @XmlElement(name = "Place")
        protected List<ExaminationPlaceType> place;

        /**
         * Gets the value of the event property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the event property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEvent().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ExaminationEventType }
         * 
         * 
         */
        public List<ExaminationEventType> getEvent() {
            if (event == null) {
                event = new ArrayList<ExaminationEventType>();
            }
            return this.event;
        }

        /**
         * Gets the value of the place property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the place property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getPlace().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ExaminationPlaceType }
         * 
         * 
         */
        public List<ExaminationPlaceType> getPlace() {
            if (place == null) {
                place = new ArrayList<ExaminationPlaceType>();
            }
            return this.place;
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
     *         &lt;element name="Result" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/inspection/}ExaminationResultType"/>
     *         &lt;element name="OtherDocument" type="{http://dom.gosuslugi.ru/schema/integration/8.5.0.4/}AttachmentType" maxOccurs="unbounded"/>
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
        "result",
        "otherDocument"
    })
    public static class FinishedInfo {

        @XmlElement(name = "Result", required = true)
        protected ExaminationResultType result;
        @XmlElement(name = "OtherDocument", required = true)
        protected List<AttachmentType> otherDocument;

        /**
         * Gets the value of the result property.
         * 
         * @return
         *     possible object is
         *     {@link ExaminationResultType }
         *     
         */
        public ExaminationResultType getResult() {
            return result;
        }

        /**
         * Sets the value of the result property.
         * 
         * @param value
         *     allowed object is
         *     {@link ExaminationResultType }
         *     
         */
        public void setResult(ExaminationResultType value) {
            this.result = value;
        }

        /**
         * Gets the value of the otherDocument property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the otherDocument property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getOtherDocument().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link AttachmentType }
         * 
         * 
         */
        public List<AttachmentType> getOtherDocument() {
            if (otherDocument == null) {
                otherDocument = new ArrayList<AttachmentType>();
            }
            return this.otherDocument;
        }

    }

}

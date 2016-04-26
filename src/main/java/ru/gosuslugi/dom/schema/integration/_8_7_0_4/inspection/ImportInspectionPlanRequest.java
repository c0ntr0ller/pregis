
package ru.gosuslugi.dom.schema.integration._8_7_0_4.inspection;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_7_0.BaseType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}BaseType">
 *       &lt;sequence>
 *         &lt;element name="ImportInspectionPlan" maxOccurs="1000">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;sequence>
 *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}TransportGUID"/>
 *                     &lt;element name="InspectionPlanGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType" minOccurs="0"/>
 *                   &lt;/sequence>
 *                   &lt;element name="Sign" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                   &lt;element name="InspectionPlan" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/inspection/}InspectionPlanType" minOccurs="0"/>
 *                   &lt;element name="ImportPlannedExamination" maxOccurs="1000" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;sequence>
 *                               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}TransportGUID"/>
 *                               &lt;element name="PlannedExaminationGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType" minOccurs="0"/>
 *                             &lt;/sequence>
 *                             &lt;element name="Publish" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                             &lt;element name="PlannedExamination" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/inspection/}PlannedExaminationType" minOccurs="0"/>
 *                             &lt;element name="DeletePlannedExamination" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                             &lt;element name="CancelPlannedExamination" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/inspection/}CancelledInfoType" minOccurs="0"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="DeleteInspectionPlan" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
    "importInspectionPlan"
})
@XmlRootElement(name = "importInspectionPlanRequest")
public class ImportInspectionPlanRequest
    extends BaseType
{

    @XmlElement(name = "ImportInspectionPlan", required = true)
    protected List<ImportInspectionPlanRequest.ImportInspectionPlan> importInspectionPlan;

    /**
     * Gets the value of the importInspectionPlan property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the importInspectionPlan property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getImportInspectionPlan().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ImportInspectionPlanRequest.ImportInspectionPlan }
     * 
     * 
     */
    public List<ImportInspectionPlanRequest.ImportInspectionPlan> getImportInspectionPlan() {
        if (importInspectionPlan == null) {
            importInspectionPlan = new ArrayList<ImportInspectionPlanRequest.ImportInspectionPlan>();
        }
        return this.importInspectionPlan;
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
     *         &lt;sequence>
     *           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}TransportGUID"/>
     *           &lt;element name="InspectionPlanGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType" minOccurs="0"/>
     *         &lt;/sequence>
     *         &lt;element name="Sign" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *         &lt;element name="InspectionPlan" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/inspection/}InspectionPlanType" minOccurs="0"/>
     *         &lt;element name="ImportPlannedExamination" maxOccurs="1000" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;sequence>
     *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}TransportGUID"/>
     *                     &lt;element name="PlannedExaminationGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType" minOccurs="0"/>
     *                   &lt;/sequence>
     *                   &lt;element name="Publish" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *                   &lt;element name="PlannedExamination" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/inspection/}PlannedExaminationType" minOccurs="0"/>
     *                   &lt;element name="DeletePlannedExamination" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *                   &lt;element name="CancelPlannedExamination" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/inspection/}CancelledInfoType" minOccurs="0"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="DeleteInspectionPlan" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
        "transportGUID",
        "inspectionPlanGuid",
        "sign",
        "inspectionPlan",
        "importPlannedExamination",
        "deleteInspectionPlan"
    })
    public static class ImportInspectionPlan {

        @XmlElement(name = "TransportGUID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", required = true)
        protected String transportGUID;
        @XmlElement(name = "InspectionPlanGuid")
        protected String inspectionPlanGuid;
        @XmlElement(name = "Sign")
        protected Boolean sign;
        @XmlElement(name = "InspectionPlan")
        protected InspectionPlanType inspectionPlan;
        @XmlElement(name = "ImportPlannedExamination")
        protected List<ImportInspectionPlanRequest.ImportInspectionPlan.ImportPlannedExamination> importPlannedExamination;
        @XmlElement(name = "DeleteInspectionPlan")
        protected Boolean deleteInspectionPlan;

        /**
         * Gets the value of the transportGUID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTransportGUID() {
            return transportGUID;
        }

        /**
         * Sets the value of the transportGUID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTransportGUID(String value) {
            this.transportGUID = value;
        }

        /**
         * Gets the value of the inspectionPlanGuid property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getInspectionPlanGuid() {
            return inspectionPlanGuid;
        }

        /**
         * Sets the value of the inspectionPlanGuid property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setInspectionPlanGuid(String value) {
            this.inspectionPlanGuid = value;
        }

        /**
         * Gets the value of the sign property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isSign() {
            return sign;
        }

        /**
         * Sets the value of the sign property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setSign(Boolean value) {
            this.sign = value;
        }

        /**
         * Gets the value of the inspectionPlan property.
         * 
         * @return
         *     possible object is
         *     {@link InspectionPlanType }
         *     
         */
        public InspectionPlanType getInspectionPlan() {
            return inspectionPlan;
        }

        /**
         * Sets the value of the inspectionPlan property.
         * 
         * @param value
         *     allowed object is
         *     {@link InspectionPlanType }
         *     
         */
        public void setInspectionPlan(InspectionPlanType value) {
            this.inspectionPlan = value;
        }

        /**
         * Gets the value of the importPlannedExamination property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the importPlannedExamination property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getImportPlannedExamination().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ImportInspectionPlanRequest.ImportInspectionPlan.ImportPlannedExamination }
         * 
         * 
         */
        public List<ImportInspectionPlanRequest.ImportInspectionPlan.ImportPlannedExamination> getImportPlannedExamination() {
            if (importPlannedExamination == null) {
                importPlannedExamination = new ArrayList<ImportInspectionPlanRequest.ImportInspectionPlan.ImportPlannedExamination>();
            }
            return this.importPlannedExamination;
        }

        /**
         * Gets the value of the deleteInspectionPlan property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isDeleteInspectionPlan() {
            return deleteInspectionPlan;
        }

        /**
         * Sets the value of the deleteInspectionPlan property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setDeleteInspectionPlan(Boolean value) {
            this.deleteInspectionPlan = value;
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
         *         &lt;sequence>
         *           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}TransportGUID"/>
         *           &lt;element name="PlannedExaminationGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/}GUIDType" minOccurs="0"/>
         *         &lt;/sequence>
         *         &lt;element name="Publish" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
         *         &lt;element name="PlannedExamination" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/inspection/}PlannedExaminationType" minOccurs="0"/>
         *         &lt;element name="DeletePlannedExamination" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
         *         &lt;element name="CancelPlannedExamination" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.4/inspection/}CancelledInfoType" minOccurs="0"/>
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
            "transportGUID",
            "plannedExaminationGuid",
            "publish",
            "plannedExamination",
            "deletePlannedExamination",
            "cancelPlannedExamination"
        })
        public static class ImportPlannedExamination {

            @XmlElement(name = "TransportGUID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.4/", required = true)
            protected String transportGUID;
            @XmlElement(name = "PlannedExaminationGuid")
            protected String plannedExaminationGuid;
            @XmlElement(name = "Publish")
            protected Boolean publish;
            @XmlElement(name = "PlannedExamination")
            protected PlannedExaminationType plannedExamination;
            @XmlElement(name = "DeletePlannedExamination")
            protected Boolean deletePlannedExamination;
            @XmlElement(name = "CancelPlannedExamination")
            protected CancelledInfoType cancelPlannedExamination;

            /**
             * Gets the value of the transportGUID property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getTransportGUID() {
                return transportGUID;
            }

            /**
             * Sets the value of the transportGUID property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setTransportGUID(String value) {
                this.transportGUID = value;
            }

            /**
             * Gets the value of the plannedExaminationGuid property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPlannedExaminationGuid() {
                return plannedExaminationGuid;
            }

            /**
             * Sets the value of the plannedExaminationGuid property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPlannedExaminationGuid(String value) {
                this.plannedExaminationGuid = value;
            }

            /**
             * Gets the value of the publish property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isPublish() {
                return publish;
            }

            /**
             * Sets the value of the publish property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setPublish(Boolean value) {
                this.publish = value;
            }

            /**
             * Gets the value of the plannedExamination property.
             * 
             * @return
             *     possible object is
             *     {@link PlannedExaminationType }
             *     
             */
            public PlannedExaminationType getPlannedExamination() {
                return plannedExamination;
            }

            /**
             * Sets the value of the plannedExamination property.
             * 
             * @param value
             *     allowed object is
             *     {@link PlannedExaminationType }
             *     
             */
            public void setPlannedExamination(PlannedExaminationType value) {
                this.plannedExamination = value;
            }

            /**
             * Gets the value of the deletePlannedExamination property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isDeletePlannedExamination() {
                return deletePlannedExamination;
            }

            /**
             * Sets the value of the deletePlannedExamination property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setDeletePlannedExamination(Boolean value) {
                this.deletePlannedExamination = value;
            }

            /**
             * Gets the value of the cancelPlannedExamination property.
             * 
             * @return
             *     possible object is
             *     {@link CancelledInfoType }
             *     
             */
            public CancelledInfoType getCancelPlannedExamination() {
                return cancelPlannedExamination;
            }

            /**
             * Sets the value of the cancelPlannedExamination property.
             * 
             * @param value
             *     allowed object is
             *     {@link CancelledInfoType }
             *     
             */
            public void setCancelPlannedExamination(CancelledInfoType value) {
                this.cancelPlannedExamination = value;
            }

        }

    }

}

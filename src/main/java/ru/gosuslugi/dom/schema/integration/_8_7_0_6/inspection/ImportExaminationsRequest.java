
package ru.gosuslugi.dom.schema.integration._8_7_0_6.inspection;

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
 *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}BaseType">
 *       &lt;sequence>
 *         &lt;element name="ImportExamination" maxOccurs="1000">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;sequence>
 *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}TransportGUID"/>
 *                     &lt;element name="ExaminationGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}GUIDType" minOccurs="0"/>
 *                   &lt;/sequence>
 *                   &lt;element name="Publish" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                   &lt;element name="Examination" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/inspection/}ExaminationType" minOccurs="0"/>
 *                   &lt;element name="DeleteExamination" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                   &lt;element name="ImportPrecept" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;sequence>
 *                               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}TransportGUID"/>
 *                               &lt;element name="PreceptGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}GUIDType" minOccurs="0"/>
 *                             &lt;/sequence>
 *                             &lt;choice>
 *                               &lt;element name="Precept" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/inspection/}PreceptType"/>
 *                               &lt;element name="CancelPrecept">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                       &lt;sequence>
 *                                         &lt;element name="CancelledInfo" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/inspection/}CancelledInfoWithAttachmentsType"/>
 *                                       &lt;/sequence>
 *                                     &lt;/restriction>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                               &lt;element name="DeletePrecept" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                               &lt;element name="FulfiledPrecept" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                             &lt;/choice>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="ImportOffence" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;sequence>
 *                               &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}TransportGUID"/>
 *                               &lt;element name="OffenceGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}GUIDType" minOccurs="0"/>
 *                             &lt;/sequence>
 *                             &lt;choice>
 *                               &lt;element name="Offence" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/inspection/}OffenceType"/>
 *                               &lt;element name="CancelOffence">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                       &lt;sequence>
 *                                         &lt;element name="CancelledInfo" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/inspection/}CancelledInfoWithAttachmentsType"/>
 *                                       &lt;/sequence>
 *                                     &lt;/restriction>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                               &lt;element name="FulfiledOffence" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                               &lt;element name="DeleteOffence" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                             &lt;/choice>
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
    "importExamination"
})
@XmlRootElement(name = "importExaminationsRequest")
public class ImportExaminationsRequest
    extends BaseType
{

    @XmlElement(name = "ImportExamination", required = true)
    protected List<ImportExaminationsRequest.ImportExamination> importExamination;

    /**
     * Gets the value of the importExamination property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the importExamination property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getImportExamination().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ImportExaminationsRequest.ImportExamination }
     * 
     * 
     */
    public List<ImportExaminationsRequest.ImportExamination> getImportExamination() {
        if (importExamination == null) {
            importExamination = new ArrayList<ImportExaminationsRequest.ImportExamination>();
        }
        return this.importExamination;
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
     *           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}TransportGUID"/>
     *           &lt;element name="ExaminationGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}GUIDType" minOccurs="0"/>
     *         &lt;/sequence>
     *         &lt;element name="Publish" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *         &lt;element name="Examination" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/inspection/}ExaminationType" minOccurs="0"/>
     *         &lt;element name="DeleteExamination" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *         &lt;element name="ImportPrecept" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;sequence>
     *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}TransportGUID"/>
     *                     &lt;element name="PreceptGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}GUIDType" minOccurs="0"/>
     *                   &lt;/sequence>
     *                   &lt;choice>
     *                     &lt;element name="Precept" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/inspection/}PreceptType"/>
     *                     &lt;element name="CancelPrecept">
     *                       &lt;complexType>
     *                         &lt;complexContent>
     *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                             &lt;sequence>
     *                               &lt;element name="CancelledInfo" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/inspection/}CancelledInfoWithAttachmentsType"/>
     *                             &lt;/sequence>
     *                           &lt;/restriction>
     *                         &lt;/complexContent>
     *                       &lt;/complexType>
     *                     &lt;/element>
     *                     &lt;element name="DeletePrecept" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *                     &lt;element name="FulfiledPrecept" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *                   &lt;/choice>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="ImportOffence" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;sequence>
     *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}TransportGUID"/>
     *                     &lt;element name="OffenceGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}GUIDType" minOccurs="0"/>
     *                   &lt;/sequence>
     *                   &lt;choice>
     *                     &lt;element name="Offence" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/inspection/}OffenceType"/>
     *                     &lt;element name="CancelOffence">
     *                       &lt;complexType>
     *                         &lt;complexContent>
     *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                             &lt;sequence>
     *                               &lt;element name="CancelledInfo" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/inspection/}CancelledInfoWithAttachmentsType"/>
     *                             &lt;/sequence>
     *                           &lt;/restriction>
     *                         &lt;/complexContent>
     *                       &lt;/complexType>
     *                     &lt;/element>
     *                     &lt;element name="FulfiledOffence" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *                     &lt;element name="DeleteOffence" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
     *                   &lt;/choice>
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
        "transportGUID",
        "examinationGuid",
        "publish",
        "examination",
        "deleteExamination",
        "importPrecept",
        "importOffence"
    })
    public static class ImportExamination {

        @XmlElement(name = "TransportGUID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.6/", required = true)
        protected String transportGUID;
        @XmlElement(name = "ExaminationGuid")
        protected String examinationGuid;
        @XmlElement(name = "Publish")
        protected Boolean publish;
        @XmlElement(name = "Examination")
        protected ExaminationType examination;
        @XmlElement(name = "DeleteExamination")
        protected Boolean deleteExamination;
        @XmlElement(name = "ImportPrecept")
        protected List<ImportExaminationsRequest.ImportExamination.ImportPrecept> importPrecept;
        @XmlElement(name = "ImportOffence")
        protected List<ImportExaminationsRequest.ImportExamination.ImportOffence> importOffence;

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
         * Gets the value of the examinationGuid property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getExaminationGuid() {
            return examinationGuid;
        }

        /**
         * Sets the value of the examinationGuid property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setExaminationGuid(String value) {
            this.examinationGuid = value;
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
         * Gets the value of the examination property.
         * 
         * @return
         *     possible object is
         *     {@link ExaminationType }
         *     
         */
        public ExaminationType getExamination() {
            return examination;
        }

        /**
         * Sets the value of the examination property.
         * 
         * @param value
         *     allowed object is
         *     {@link ExaminationType }
         *     
         */
        public void setExamination(ExaminationType value) {
            this.examination = value;
        }

        /**
         * Gets the value of the deleteExamination property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isDeleteExamination() {
            return deleteExamination;
        }

        /**
         * Sets the value of the deleteExamination property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setDeleteExamination(Boolean value) {
            this.deleteExamination = value;
        }

        /**
         * Gets the value of the importPrecept property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the importPrecept property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getImportPrecept().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ImportExaminationsRequest.ImportExamination.ImportPrecept }
         * 
         * 
         */
        public List<ImportExaminationsRequest.ImportExamination.ImportPrecept> getImportPrecept() {
            if (importPrecept == null) {
                importPrecept = new ArrayList<ImportExaminationsRequest.ImportExamination.ImportPrecept>();
            }
            return this.importPrecept;
        }

        /**
         * Gets the value of the importOffence property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the importOffence property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getImportOffence().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link ImportExaminationsRequest.ImportExamination.ImportOffence }
         * 
         * 
         */
        public List<ImportExaminationsRequest.ImportExamination.ImportOffence> getImportOffence() {
            if (importOffence == null) {
                importOffence = new ArrayList<ImportExaminationsRequest.ImportExamination.ImportOffence>();
            }
            return this.importOffence;
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
         *           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}TransportGUID"/>
         *           &lt;element name="OffenceGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}GUIDType" minOccurs="0"/>
         *         &lt;/sequence>
         *         &lt;choice>
         *           &lt;element name="Offence" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/inspection/}OffenceType"/>
         *           &lt;element name="CancelOffence">
         *             &lt;complexType>
         *               &lt;complexContent>
         *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                   &lt;sequence>
         *                     &lt;element name="CancelledInfo" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/inspection/}CancelledInfoWithAttachmentsType"/>
         *                   &lt;/sequence>
         *                 &lt;/restriction>
         *               &lt;/complexContent>
         *             &lt;/complexType>
         *           &lt;/element>
         *           &lt;element name="FulfiledOffence" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
         *           &lt;element name="DeleteOffence" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
        @XmlType(name = "", propOrder = {
            "transportGUID",
            "offenceGuid",
            "offence",
            "cancelOffence",
            "fulfiledOffence",
            "deleteOffence"
        })
        public static class ImportOffence {

            @XmlElement(name = "TransportGUID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.6/", required = true)
            protected String transportGUID;
            @XmlElement(name = "OffenceGuid")
            protected String offenceGuid;
            @XmlElement(name = "Offence")
            protected OffenceType offence;
            @XmlElement(name = "CancelOffence")
            protected ImportExaminationsRequest.ImportExamination.ImportOffence.CancelOffence cancelOffence;
            @XmlElement(name = "FulfiledOffence")
            protected Boolean fulfiledOffence;
            @XmlElement(name = "DeleteOffence")
            protected Boolean deleteOffence;

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
             * Gets the value of the offenceGuid property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getOffenceGuid() {
                return offenceGuid;
            }

            /**
             * Sets the value of the offenceGuid property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setOffenceGuid(String value) {
                this.offenceGuid = value;
            }

            /**
             * Gets the value of the offence property.
             * 
             * @return
             *     possible object is
             *     {@link OffenceType }
             *     
             */
            public OffenceType getOffence() {
                return offence;
            }

            /**
             * Sets the value of the offence property.
             * 
             * @param value
             *     allowed object is
             *     {@link OffenceType }
             *     
             */
            public void setOffence(OffenceType value) {
                this.offence = value;
            }

            /**
             * Gets the value of the cancelOffence property.
             * 
             * @return
             *     possible object is
             *     {@link ImportExaminationsRequest.ImportExamination.ImportOffence.CancelOffence }
             *     
             */
            public ImportExaminationsRequest.ImportExamination.ImportOffence.CancelOffence getCancelOffence() {
                return cancelOffence;
            }

            /**
             * Sets the value of the cancelOffence property.
             * 
             * @param value
             *     allowed object is
             *     {@link ImportExaminationsRequest.ImportExamination.ImportOffence.CancelOffence }
             *     
             */
            public void setCancelOffence(ImportExaminationsRequest.ImportExamination.ImportOffence.CancelOffence value) {
                this.cancelOffence = value;
            }

            /**
             * Gets the value of the fulfiledOffence property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isFulfiledOffence() {
                return fulfiledOffence;
            }

            /**
             * Sets the value of the fulfiledOffence property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setFulfiledOffence(Boolean value) {
                this.fulfiledOffence = value;
            }

            /**
             * Gets the value of the deleteOffence property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isDeleteOffence() {
                return deleteOffence;
            }

            /**
             * Sets the value of the deleteOffence property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setDeleteOffence(Boolean value) {
                this.deleteOffence = value;
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
             *         &lt;element name="CancelledInfo" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/inspection/}CancelledInfoWithAttachmentsType"/>
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
                "cancelledInfo"
            })
            public static class CancelOffence {

                @XmlElement(name = "CancelledInfo", required = true)
                protected CancelledInfoWithAttachmentsType cancelledInfo;

                /**
                 * Gets the value of the cancelledInfo property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link CancelledInfoWithAttachmentsType }
                 *     
                 */
                public CancelledInfoWithAttachmentsType getCancelledInfo() {
                    return cancelledInfo;
                }

                /**
                 * Sets the value of the cancelledInfo property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link CancelledInfoWithAttachmentsType }
                 *     
                 */
                public void setCancelledInfo(CancelledInfoWithAttachmentsType value) {
                    this.cancelledInfo = value;
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
         *         &lt;sequence>
         *           &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}TransportGUID"/>
         *           &lt;element name="PreceptGuid" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}GUIDType" minOccurs="0"/>
         *         &lt;/sequence>
         *         &lt;choice>
         *           &lt;element name="Precept" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/inspection/}PreceptType"/>
         *           &lt;element name="CancelPrecept">
         *             &lt;complexType>
         *               &lt;complexContent>
         *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                   &lt;sequence>
         *                     &lt;element name="CancelledInfo" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/inspection/}CancelledInfoWithAttachmentsType"/>
         *                   &lt;/sequence>
         *                 &lt;/restriction>
         *               &lt;/complexContent>
         *             &lt;/complexType>
         *           &lt;/element>
         *           &lt;element name="DeletePrecept" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
         *           &lt;element name="FulfiledPrecept" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
        @XmlType(name = "", propOrder = {
            "transportGUID",
            "preceptGuid",
            "precept",
            "cancelPrecept",
            "deletePrecept",
            "fulfiledPrecept"
        })
        public static class ImportPrecept {

            @XmlElement(name = "TransportGUID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.6/", required = true)
            protected String transportGUID;
            @XmlElement(name = "PreceptGuid")
            protected String preceptGuid;
            @XmlElement(name = "Precept")
            protected PreceptType precept;
            @XmlElement(name = "CancelPrecept")
            protected ImportExaminationsRequest.ImportExamination.ImportPrecept.CancelPrecept cancelPrecept;
            @XmlElement(name = "DeletePrecept")
            protected Boolean deletePrecept;
            @XmlElement(name = "FulfiledPrecept")
            protected Boolean fulfiledPrecept;

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
             * Gets the value of the preceptGuid property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getPreceptGuid() {
                return preceptGuid;
            }

            /**
             * Sets the value of the preceptGuid property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setPreceptGuid(String value) {
                this.preceptGuid = value;
            }

            /**
             * Gets the value of the precept property.
             * 
             * @return
             *     possible object is
             *     {@link PreceptType }
             *     
             */
            public PreceptType getPrecept() {
                return precept;
            }

            /**
             * Sets the value of the precept property.
             * 
             * @param value
             *     allowed object is
             *     {@link PreceptType }
             *     
             */
            public void setPrecept(PreceptType value) {
                this.precept = value;
            }

            /**
             * Gets the value of the cancelPrecept property.
             * 
             * @return
             *     possible object is
             *     {@link ImportExaminationsRequest.ImportExamination.ImportPrecept.CancelPrecept }
             *     
             */
            public ImportExaminationsRequest.ImportExamination.ImportPrecept.CancelPrecept getCancelPrecept() {
                return cancelPrecept;
            }

            /**
             * Sets the value of the cancelPrecept property.
             * 
             * @param value
             *     allowed object is
             *     {@link ImportExaminationsRequest.ImportExamination.ImportPrecept.CancelPrecept }
             *     
             */
            public void setCancelPrecept(ImportExaminationsRequest.ImportExamination.ImportPrecept.CancelPrecept value) {
                this.cancelPrecept = value;
            }

            /**
             * Gets the value of the deletePrecept property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isDeletePrecept() {
                return deletePrecept;
            }

            /**
             * Sets the value of the deletePrecept property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setDeletePrecept(Boolean value) {
                this.deletePrecept = value;
            }

            /**
             * Gets the value of the fulfiledPrecept property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isFulfiledPrecept() {
                return fulfiledPrecept;
            }

            /**
             * Sets the value of the fulfiledPrecept property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setFulfiledPrecept(Boolean value) {
                this.fulfiledPrecept = value;
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
             *         &lt;element name="CancelledInfo" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/inspection/}CancelledInfoWithAttachmentsType"/>
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
                "cancelledInfo"
            })
            public static class CancelPrecept {

                @XmlElement(name = "CancelledInfo", required = true)
                protected CancelledInfoWithAttachmentsType cancelledInfo;

                /**
                 * Gets the value of the cancelledInfo property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link CancelledInfoWithAttachmentsType }
                 *     
                 */
                public CancelledInfoWithAttachmentsType getCancelledInfo() {
                    return cancelledInfo;
                }

                /**
                 * Sets the value of the cancelledInfo property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link CancelledInfoWithAttachmentsType }
                 *     
                 */
                public void setCancelledInfo(CancelledInfoWithAttachmentsType value) {
                    this.cancelledInfo = value;
                }

            }

        }

    }

}

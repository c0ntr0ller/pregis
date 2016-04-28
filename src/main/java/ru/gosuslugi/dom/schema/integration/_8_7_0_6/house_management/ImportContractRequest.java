
package ru.gosuslugi.dom.schema.integration._8_7_0_6.house_management;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import ru.gosuslugi.dom.schema.integration._8_7_0.AttachmentType;
import ru.gosuslugi.dom.schema.integration._8_7_0.BaseType;
import ru.gosuslugi.dom.schema.integration._8_7_0.NsiRef;


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
 *         &lt;element name="Contract" maxOccurs="100">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;sequence>
 *                     &lt;element ref="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}TransportGUID"/>
 *                     &lt;element name="ContractVersionGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}GUIDType" minOccurs="0"/>
 *                   &lt;/sequence>
 *                   &lt;choice>
 *                     &lt;element name="PlacingContract">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/house-management/}ContractType">
 *                             &lt;sequence>
 *                               &lt;element name="LicenseRequest" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                             &lt;/sequence>
 *                           &lt;/extension>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="ApprovalContract" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/house-management/}ApprovalType"/>
 *                     &lt;element name="RollOverContract">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/house-management/}RollOverType">
 *                             &lt;sequence>
 *                               &lt;element name="LicenseRequest" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                             &lt;/sequence>
 *                           &lt;/extension>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="TerminateContract">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/house-management/}TerminateType">
 *                             &lt;sequence>
 *                               &lt;element name="ReasonRef" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}nsiRef"/>
 *                               &lt;element name="TerminateAttachment" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}AttachmentType" maxOccurs="unbounded"/>
 *                             &lt;/sequence>
 *                           &lt;/extension>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="AnnulmentContract">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/house-management/}AnnulmentType">
 *                           &lt;/extension>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                   &lt;/choice>
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
    "contract"
})
@XmlRootElement(name = "importContractRequest")
public class ImportContractRequest
    extends BaseType
{

    @XmlElement(name = "Contract", required = true)
    protected List<ImportContractRequest.Contract> contract;

    /**
     * Gets the value of the contract property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contract property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContract().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ImportContractRequest.Contract }
     * 
     * 
     */
    public List<ImportContractRequest.Contract> getContract() {
        if (contract == null) {
            contract = new ArrayList<ImportContractRequest.Contract>();
        }
        return this.contract;
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
     *           &lt;element name="ContractVersionGUID" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}GUIDType" minOccurs="0"/>
     *         &lt;/sequence>
     *         &lt;choice>
     *           &lt;element name="PlacingContract">
     *             &lt;complexType>
     *               &lt;complexContent>
     *                 &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/house-management/}ContractType">
     *                   &lt;sequence>
     *                     &lt;element name="LicenseRequest" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *                   &lt;/sequence>
     *                 &lt;/extension>
     *               &lt;/complexContent>
     *             &lt;/complexType>
     *           &lt;/element>
     *           &lt;element name="ApprovalContract" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/house-management/}ApprovalType"/>
     *           &lt;element name="RollOverContract">
     *             &lt;complexType>
     *               &lt;complexContent>
     *                 &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/house-management/}RollOverType">
     *                   &lt;sequence>
     *                     &lt;element name="LicenseRequest" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *                   &lt;/sequence>
     *                 &lt;/extension>
     *               &lt;/complexContent>
     *             &lt;/complexType>
     *           &lt;/element>
     *           &lt;element name="TerminateContract">
     *             &lt;complexType>
     *               &lt;complexContent>
     *                 &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/house-management/}TerminateType">
     *                   &lt;sequence>
     *                     &lt;element name="ReasonRef" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}nsiRef"/>
     *                     &lt;element name="TerminateAttachment" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}AttachmentType" maxOccurs="unbounded"/>
     *                   &lt;/sequence>
     *                 &lt;/extension>
     *               &lt;/complexContent>
     *             &lt;/complexType>
     *           &lt;/element>
     *           &lt;element name="AnnulmentContract">
     *             &lt;complexType>
     *               &lt;complexContent>
     *                 &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/house-management/}AnnulmentType">
     *                 &lt;/extension>
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
    @XmlType(name = "", propOrder = {
        "transportGUID",
        "contractVersionGUID",
        "placingContract",
        "approvalContract",
        "rollOverContract",
        "terminateContract",
        "annulmentContract"
    })
    public static class Contract {

        @XmlElement(name = "TransportGUID", namespace = "http://dom.gosuslugi.ru/schema/integration/8.7.0.6/", required = true)
        protected String transportGUID;
        @XmlElement(name = "ContractVersionGUID")
        protected String contractVersionGUID;
        @XmlElement(name = "PlacingContract")
        protected ImportContractRequest.Contract.PlacingContract placingContract;
        @XmlElement(name = "ApprovalContract")
        protected ApprovalType approvalContract;
        @XmlElement(name = "RollOverContract")
        protected ImportContractRequest.Contract.RollOverContract rollOverContract;
        @XmlElement(name = "TerminateContract")
        protected ImportContractRequest.Contract.TerminateContract terminateContract;
        @XmlElement(name = "AnnulmentContract")
        protected ImportContractRequest.Contract.AnnulmentContract annulmentContract;

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
         * Gets the value of the contractVersionGUID property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getContractVersionGUID() {
            return contractVersionGUID;
        }

        /**
         * Sets the value of the contractVersionGUID property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setContractVersionGUID(String value) {
            this.contractVersionGUID = value;
        }

        /**
         * Gets the value of the placingContract property.
         * 
         * @return
         *     possible object is
         *     {@link ImportContractRequest.Contract.PlacingContract }
         *     
         */
        public ImportContractRequest.Contract.PlacingContract getPlacingContract() {
            return placingContract;
        }

        /**
         * Sets the value of the placingContract property.
         * 
         * @param value
         *     allowed object is
         *     {@link ImportContractRequest.Contract.PlacingContract }
         *     
         */
        public void setPlacingContract(ImportContractRequest.Contract.PlacingContract value) {
            this.placingContract = value;
        }

        /**
         * Gets the value of the approvalContract property.
         * 
         * @return
         *     possible object is
         *     {@link ApprovalType }
         *     
         */
        public ApprovalType getApprovalContract() {
            return approvalContract;
        }

        /**
         * Sets the value of the approvalContract property.
         * 
         * @param value
         *     allowed object is
         *     {@link ApprovalType }
         *     
         */
        public void setApprovalContract(ApprovalType value) {
            this.approvalContract = value;
        }

        /**
         * Gets the value of the rollOverContract property.
         * 
         * @return
         *     possible object is
         *     {@link ImportContractRequest.Contract.RollOverContract }
         *     
         */
        public ImportContractRequest.Contract.RollOverContract getRollOverContract() {
            return rollOverContract;
        }

        /**
         * Sets the value of the rollOverContract property.
         * 
         * @param value
         *     allowed object is
         *     {@link ImportContractRequest.Contract.RollOverContract }
         *     
         */
        public void setRollOverContract(ImportContractRequest.Contract.RollOverContract value) {
            this.rollOverContract = value;
        }

        /**
         * Gets the value of the terminateContract property.
         * 
         * @return
         *     possible object is
         *     {@link ImportContractRequest.Contract.TerminateContract }
         *     
         */
        public ImportContractRequest.Contract.TerminateContract getTerminateContract() {
            return terminateContract;
        }

        /**
         * Sets the value of the terminateContract property.
         * 
         * @param value
         *     allowed object is
         *     {@link ImportContractRequest.Contract.TerminateContract }
         *     
         */
        public void setTerminateContract(ImportContractRequest.Contract.TerminateContract value) {
            this.terminateContract = value;
        }

        /**
         * Gets the value of the annulmentContract property.
         * 
         * @return
         *     possible object is
         *     {@link ImportContractRequest.Contract.AnnulmentContract }
         *     
         */
        public ImportContractRequest.Contract.AnnulmentContract getAnnulmentContract() {
            return annulmentContract;
        }

        /**
         * Sets the value of the annulmentContract property.
         * 
         * @param value
         *     allowed object is
         *     {@link ImportContractRequest.Contract.AnnulmentContract }
         *     
         */
        public void setAnnulmentContract(ImportContractRequest.Contract.AnnulmentContract value) {
            this.annulmentContract = value;
        }


        /**
         * <p>Java class for anonymous complex type.
         * 
         * <p>The following schema fragment specifies the expected content contained within this class.
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/house-management/}AnnulmentType">
         *     &lt;/extension>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class AnnulmentContract
            extends AnnulmentType
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
         *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/house-management/}ContractType">
         *       &lt;sequence>
         *         &lt;element name="LicenseRequest" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
            "licenseRequest"
        })
        public static class PlacingContract
            extends ContractType
        {

            @XmlElement(name = "LicenseRequest")
            protected Boolean licenseRequest;

            /**
             * Gets the value of the licenseRequest property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isLicenseRequest() {
                return licenseRequest;
            }

            /**
             * Sets the value of the licenseRequest property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setLicenseRequest(Boolean value) {
                this.licenseRequest = value;
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
         *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/house-management/}RollOverType">
         *       &lt;sequence>
         *         &lt;element name="LicenseRequest" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
            "licenseRequest"
        })
        public static class RollOverContract
            extends RollOverType
        {

            @XmlElement(name = "LicenseRequest")
            protected Boolean licenseRequest;

            /**
             * Gets the value of the licenseRequest property.
             * 
             * @return
             *     possible object is
             *     {@link Boolean }
             *     
             */
            public Boolean isLicenseRequest() {
                return licenseRequest;
            }

            /**
             * Sets the value of the licenseRequest property.
             * 
             * @param value
             *     allowed object is
             *     {@link Boolean }
             *     
             */
            public void setLicenseRequest(Boolean value) {
                this.licenseRequest = value;
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
         *     &lt;extension base="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/house-management/}TerminateType">
         *       &lt;sequence>
         *         &lt;element name="ReasonRef" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}nsiRef"/>
         *         &lt;element name="TerminateAttachment" type="{http://dom.gosuslugi.ru/schema/integration/8.7.0.6/}AttachmentType" maxOccurs="unbounded"/>
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
            "reasonRef",
            "terminateAttachment"
        })
        public static class TerminateContract
            extends TerminateType
        {

            @XmlElement(name = "ReasonRef", required = true)
            protected NsiRef reasonRef;
            @XmlElement(name = "TerminateAttachment", required = true)
            protected List<AttachmentType> terminateAttachment;

            /**
             * Gets the value of the reasonRef property.
             * 
             * @return
             *     possible object is
             *     {@link NsiRef }
             *     
             */
            public NsiRef getReasonRef() {
                return reasonRef;
            }

            /**
             * Sets the value of the reasonRef property.
             * 
             * @param value
             *     allowed object is
             *     {@link NsiRef }
             *     
             */
            public void setReasonRef(NsiRef value) {
                this.reasonRef = value;
            }

            /**
             * Gets the value of the terminateAttachment property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the terminateAttachment property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getTerminateAttachment().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link AttachmentType }
             * 
             * 
             */
            public List<AttachmentType> getTerminateAttachment() {
                if (terminateAttachment == null) {
                    terminateAttachment = new ArrayList<AttachmentType>();
                }
                return this.terminateAttachment;
            }

        }

    }

}

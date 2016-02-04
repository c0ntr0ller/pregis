
package ru.gosuslugi.dom.schema.integration._8_5_0;

import org.w3._2000._09.xmldsig_.SignatureType;
import ru.gosuslugi.dom.schema.integration._8_5_0_2.nsi.*;
import ru.gosuslugi.dom.schema.integration._8_5_0_2.organizations_registry.*;

import javax.xml.bind.annotation.*;


/**
 * Базовый тип бизнес-сообщения с подписью
 * 
 * <p>Java class for BaseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BaseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.w3.org/2000/09/xmldsig#}Signature" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseType", propOrder = {
    "signature"
})
@XmlSeeAlso({
//        ImportAcknowledgmentRequest.class,
//        ImportNotificationsOfOrderExecutionCancellationRequest.class,
//        ImportNotificationsOfOrderExecutionRequest.class,
//        ExportPaymentDocumentDetailsResult.class,
//        ExportNotificationsOfOrderExecutionResult.class,
//        ExportNotificationsOfOrderExecutionRequest.class,
//        ExportPaymentDocumentDetailsRequest.class,
//        ExportInspectionPlansResult.class,
//        ExportExaminationsResult.class,
//        ImportInspectionPlanRequest.class,
//        ImportExaminationsRequest.class,
//        ExportExaminationsRequest.class,
//        ExportInspectionPlansRequest.class,
//        ImportHMServicesTarifsRequest.class,
//        ExportMSRSOResult.class,
//        ExportWorkingListRequest.class,
//        ImportWorkingListRequest.class,
//        ImportWorkingPlanRequest.class,
//        ExportHMServicesTarifsResult.class,
//        ExportMSRSORequest.class,
//        ExportWorkingListResult.class,
//        ExportWorkingPlanRequest.class,
//        ExportWorkingPlanResult.class,
//        ExportCompletedWorksResult.class,
//        ExportCompletedWorksRequest.class,
//        ExportHMServicesTarifsRequest.class,
//        ImportCompletedWorksRequest.class,
//        ImportMSRSORequest.class,
        ExportDataProviderResult.class,
        ImportDataProviderRequest.class,
        ImportSubsidiaryRequest.class,
        ExportDataProviderRequest.class,
        ExportOrgRegistryResult.class,
        ExportOrgRegistryRequest.class,
        ImportAdditionalServicesRequest.class,
        ExportNsiItemResult.class,
        ExportNsiListResult.class,
        ExportNsiItemRequest.class,
        ImportOrganizationWorksRequest.class,
        ImportMunicipalServicesRequest.class,
        ExportNsiListRequest.class,
//        ImportContractRequest.class,
//        ExportAccountResult.class,
//        ImportVotingProtocolRequest.class,
//        ExportHouseRequest.class,
//        ExportShareEncbrDataRequest.class,
//        ExportMeteringDeviceDataRequest.class,
//        ExportStatusPublicPropertyContractResult.class,
//        ExportCAChResult.class,
//        ExportStatusPublicPropertyContractRequest.class,
//        ExportCAChRequest.class,
//        ImportPublicPropertyContractRequest.class,
//        ExportMeteringDeviceDataResult.class,
//        ImportShareEncbrDataRequest.class,
//        ImportHouseUORequest.class,
//        ExportStatusCAChRequest.class,
//        ImportNotificationRequest.class,
//        ExportVotingProtocolRequest.class,
//        ImportMeteringDeviceDataRequest.class,
//        ImportHouseOMSRequest.class,
//        ExportShareEncbrDataResult.class,
//        ImportAccountRequest.class,
//        ImportHouseRSORequest.class,
//        ExportVotingProtocolResult.class,
//        ExportHouseResult.class,
//        FaultHouseManagement.class,
//        ExportStatusCAChResult.class,
//        ExportAccountRequest.class,
//        ru.gosuslugi.dom.schema.integration._8_5_0_2.house_management.ImportResult.class,
//        ImportCharterRequest.class,
//        ImportOKIRequest.class,
//        ExportOKIResult.class,
//        ExportOKIRequest.class,
//        ExportMeteringDeviceHistoryRequest.class,
//        ExportMeteringDeviceHistoryResult.class,
//        ImportMeteringDeviceValuesRequest.class,
//        ExportOrgPeriodResult.class,
//        ExportPaymentDocumentRequest.class,
//        CloseHousePaymentPeriodRequest.class,
//        ExportPaymentDocumentResult.class,
//        ExportOrgPeriodRequest.class,
//        ImportPaymentDocumentRequest.class,
//        OpenOrgPaymentPeriodRequest.class,
//        ExportDisqualifiedPersonResult.class,
//        ExportLicenseResult.class,
//        ExportDisqualifiedPersonRequest.class,
//        ExportLicenseRequest.class,
//        ImportRSORequest.class,
        ru.gosuslugi.dom.schema.integration._8_5_0.ImportResult.class,
//        ImportDisclosureRequest.class,
        BaseAsyncResponseType.class

//        Original
//    ImportAdditionalServicesRequest.class,
//    ExportNsiItemResult.class,
//    ExportNsiListResult.class,
//    ExportNsiItemRequest.class,
//    ImportOrganizationWorksRequest.class,
//    ImportMunicipalServicesRequest.class,
//    ExportNsiListRequest.class,
//    BaseAsyncResponseType.class,
//    ImportResult.class
})
public class BaseType {

    @XmlElement(name = "Signature", namespace = "http://www.w3.org/2000/09/xmldsig#")
    protected SignatureType signature;
    @XmlAttribute(name = "Id")
    @XmlSchemaType(name = "anySimpleType")
    protected String id;

    /**
     * Gets the value of the signature property.
     * 
     * @return
     *     possible object is
     *     {@link SignatureType }
     *     
     */
    public SignatureType getSignature() {
        return signature;
    }

    /**
     * Sets the value of the signature property.
     * 
     * @param value
     *     allowed object is
     *     {@link SignatureType }
     *     
     */
    public void setSignature(SignatureType value) {
        this.signature = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}

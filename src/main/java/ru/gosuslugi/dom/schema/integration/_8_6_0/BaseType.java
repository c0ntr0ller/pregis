
package ru.gosuslugi.dom.schema.integration._8_6_0;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import org.w3._2000._09.xmldsig_.SignatureType;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.bills.CloseHousePaymentPeriodRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.bills.ExportNotificationsOfOrderExecutionRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.bills.ExportNotificationsOfOrderExecutionResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.bills.ExportOrgPeriodRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.bills.ExportOrgPeriodResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.bills.ExportPaymentDocumentRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.bills.ExportPaymentDocumentResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.bills.ImportAcknowledgmentRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.bills.ImportPaymentDocumentRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.bills.OpenOrgPaymentPeriodRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.device_metering.ExportMeteringDeviceHistoryRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.device_metering.ExportMeteringDeviceHistoryResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.device_metering.ImportMeteringDeviceValuesRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.disclosure.ImportDisclosureRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.fas.ImportRSORequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ExportAccountRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ExportAccountResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ExportCAChRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ExportCAChResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ExportHouseRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ExportHouseResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ExportMeteringDeviceDataRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ExportMeteringDeviceDataResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ExportShareEncbrDataRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ExportShareEncbrDataResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ExportStatusCAChRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ExportStatusCAChResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ExportStatusPublicPropertyContractRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ExportStatusPublicPropertyContractResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ExportVotingProtocolRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ExportVotingProtocolResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.FaultHouseManagement;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ImportAccountRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ImportCharterRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ImportContractRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ImportHouseOMSRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ImportHouseRSORequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ImportHouseUORequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ImportMeteringDeviceDataRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ImportNotificationRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ImportPublicPropertyContractRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ImportShareEncbrDataRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ImportVotingProtocolRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.infrastructure.ExportOKIRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.infrastructure.ExportOKIResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.infrastructure.ImportOKIRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.inspection.ExportExaminationsRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.inspection.ExportExaminationsResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.inspection.ExportInspectionPlansRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.inspection.ExportInspectionPlansResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.inspection.ImportExaminationsRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.inspection.ImportInspectionPlanRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.licenses.ExportDisqualifiedPersonRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.licenses.ExportDisqualifiedPersonResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.licenses.ExportLicenseRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.licenses.ExportLicenseResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.nsi.ExportDataProviderNsiItemRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.nsi.ImportAdditionalServicesRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.nsi.ImportMunicipalServicesRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.nsi.ImportOrganizationWorksRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.nsi_common.ExportNsiItemRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.nsi_common.ExportNsiListRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.nsi_common.ExportNsiListResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.organizations_registry.ImportSubsidiaryRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.organizations_registry_common.ExportDataProviderRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.organizations_registry_common.ExportDataProviderResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.organizations_registry_common.ExportOrgRegistryRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.organizations_registry_common.ExportOrgRegistryResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.organizations_registry_common.ImportDataProviderRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.payment.ExportPaymentDocumentDetailsRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.payment.ExportPaymentDocumentDetailsResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.payment.ImportNotificationsOfOrderExecutionCancellationRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.payment.ImportNotificationsOfOrderExecutionRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.services.ExportCompletedWorksRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.services.ExportCompletedWorksResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.services.ExportHMServicesTarifsRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.services.ExportHMServicesTarifsResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.services.ExportMSRSORequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.services.ExportMSRSOResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.services.ExportWorkingListRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.services.ExportWorkingListResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.services.ExportWorkingPlanRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.services.ExportWorkingPlanResult;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.services.ImportCompletedWorksRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.services.ImportMSRSORequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.services.ImportWorkingListRequest;
import ru.gosuslugi.dom.schema.integration._8_6_0_6.services.ImportWorkingPlanRequest;


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
    ImportOKIRequest.class,
    ExportOKIResult.class,
    ExportOKIRequest.class,
    ImportAdditionalServicesRequest.class,
    ru.gosuslugi.dom.schema.integration._8_6_0_6.nsi.ExportNsiItemResult.class,
    ExportDataProviderNsiItemRequest.class,
    ImportOrganizationWorksRequest.class,
    ImportMunicipalServicesRequest.class,
    ImportAcknowledgmentRequest.class,
    ExportOrgPeriodResult.class,
    ExportPaymentDocumentRequest.class,
    CloseHousePaymentPeriodRequest.class,
    ExportNotificationsOfOrderExecutionResult.class,
    ExportPaymentDocumentResult.class,
    ExportOrgPeriodRequest.class,
    ImportPaymentDocumentRequest.class,
    OpenOrgPaymentPeriodRequest.class,
    ExportNotificationsOfOrderExecutionRequest.class,
    ExportMSRSOResult.class,
    ExportWorkingListRequest.class,
    ImportWorkingListRequest.class,
    ImportWorkingPlanRequest.class,
    ExportHMServicesTarifsResult.class,
    ExportMSRSORequest.class,
    ExportWorkingListResult.class,
    ExportWorkingPlanRequest.class,
    ExportWorkingPlanResult.class,
    ExportCompletedWorksResult.class,
    ExportCompletedWorksRequest.class,
    ExportHMServicesTarifsRequest.class,
    ImportCompletedWorksRequest.class,
    ImportMSRSORequest.class,
    ExportDataProviderResult.class,
    ImportDataProviderRequest.class,
    ExportDataProviderRequest.class,
    ExportOrgRegistryResult.class,
    ExportOrgRegistryRequest.class,
    ImportRSORequest.class,
    ExportMeteringDeviceHistoryRequest.class,
    ExportMeteringDeviceHistoryResult.class,
    ImportMeteringDeviceValuesRequest.class,
    ImportDisclosureRequest.class,
    ImportNotificationsOfOrderExecutionCancellationRequest.class,
    ImportNotificationsOfOrderExecutionRequest.class,
    ExportPaymentDocumentDetailsResult.class,
    ExportPaymentDocumentDetailsRequest.class,
    ExportInspectionPlansResult.class,
    ExportExaminationsResult.class,
    ImportInspectionPlanRequest.class,
    ImportExaminationsRequest.class,
    ExportExaminationsRequest.class,
    ExportInspectionPlansRequest.class,
    ImportContractRequest.class,
    ExportAccountResult.class,
    ImportVotingProtocolRequest.class,
    ExportHouseRequest.class,
    ExportShareEncbrDataRequest.class,
    ExportMeteringDeviceDataRequest.class,
    ExportStatusPublicPropertyContractResult.class,
    ExportCAChResult.class,
    ExportStatusPublicPropertyContractRequest.class,
    ExportCAChRequest.class,
    ImportPublicPropertyContractRequest.class,
    ExportMeteringDeviceDataResult.class,
    ImportShareEncbrDataRequest.class,
    ImportHouseUORequest.class,
    ExportStatusCAChRequest.class,
    ImportNotificationRequest.class,
    ExportVotingProtocolRequest.class,
    ImportMeteringDeviceDataRequest.class,
    ImportHouseOMSRequest.class,
    ExportShareEncbrDataResult.class,
    ImportAccountRequest.class,
    ImportHouseRSORequest.class,
    ExportVotingProtocolResult.class,
    ExportHouseResult.class,
    FaultHouseManagement.class,
    ExportStatusCAChResult.class,
    ExportAccountRequest.class,
    ru.gosuslugi.dom.schema.integration._8_6_0_6.house_management.ImportResult.class,
    ImportCharterRequest.class,
    ru.gosuslugi.dom.schema.integration._8_6_0_6.nsi_common.ExportNsiItemResult.class,
    ExportNsiListResult.class,
    ExportNsiItemRequest.class,
    ExportNsiListRequest.class,
    ImportSubsidiaryRequest.class,
    ru.gosuslugi.dom.schema.integration._8_6_0.ImportResult.class,
    ExportDisqualifiedPersonResult.class,
    ExportLicenseResult.class,
    ExportDisqualifiedPersonRequest.class,
    ExportLicenseRequest.class,
    BaseAsyncResponseType.class
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
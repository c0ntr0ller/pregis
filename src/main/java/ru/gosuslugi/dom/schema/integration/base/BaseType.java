
package ru.gosuslugi.dom.schema.integration.base;

import org.w3._2000._09.xmldsig_.SignatureType;
import ru.gosuslugi.dom.schema.integration.services.bills.*;
import ru.gosuslugi.dom.schema.integration.services.device_metering.ExportMeteringDeviceHistoryRequest;
import ru.gosuslugi.dom.schema.integration.services.device_metering.ExportMeteringDeviceHistoryResult;
import ru.gosuslugi.dom.schema.integration.services.device_metering.ImportMeteringDeviceValuesRequest;
import ru.gosuslugi.dom.schema.integration.services.disclosure.ImportDisclosureRequest;
import ru.gosuslugi.dom.schema.integration.services.fas.ImportRSORequest;
import ru.gosuslugi.dom.schema.integration.services.house_management.*;
import ru.gosuslugi.dom.schema.integration.services.infrastructure.ExportOKIRequest;
import ru.gosuslugi.dom.schema.integration.services.infrastructure.ExportOKIResult;
import ru.gosuslugi.dom.schema.integration.services.infrastructure.ImportOKIRequest;
import ru.gosuslugi.dom.schema.integration.services.inspection.*;
import ru.gosuslugi.dom.schema.integration.services.licenses.ExportDisqualifiedPersonRequest;
import ru.gosuslugi.dom.schema.integration.services.licenses.ExportDisqualifiedPersonResult;
import ru.gosuslugi.dom.schema.integration.services.licenses.ExportLicenseRequest;
import ru.gosuslugi.dom.schema.integration.services.licenses.ExportLicenseResult;
import ru.gosuslugi.dom.schema.integration.services.nsi.ExportDataProviderNsiItemRequest;
import ru.gosuslugi.dom.schema.integration.services.nsi.ImportAdditionalServicesRequest;
import ru.gosuslugi.dom.schema.integration.services.nsi.ImportMunicipalServicesRequest;
import ru.gosuslugi.dom.schema.integration.services.nsi.ImportOrganizationWorksRequest;
import ru.gosuslugi.dom.schema.integration.services.nsi_common.ExportNsiItemRequest;
import ru.gosuslugi.dom.schema.integration.services.nsi_common.ExportNsiListRequest;
import ru.gosuslugi.dom.schema.integration.services.nsi_common.ExportNsiListResult;
import ru.gosuslugi.dom.schema.integration.services.organizations_registry.ImportSubsidiaryRequest;
import ru.gosuslugi.dom.schema.integration.services.organizations_registry_common.ExportDataProviderRequest;
import ru.gosuslugi.dom.schema.integration.services.organizations_registry_common.ExportDataProviderResult;
import ru.gosuslugi.dom.schema.integration.services.organizations_registry_common.ExportOrgRegistryRequest;
import ru.gosuslugi.dom.schema.integration.services.organizations_registry_common.ExportOrgRegistryResult;
import ru.gosuslugi.dom.schema.integration.services.payment.ExportPaymentDocumentDetailsRequest;
import ru.gosuslugi.dom.schema.integration.services.payment.ExportPaymentDocumentDetailsResult;
import ru.gosuslugi.dom.schema.integration.services.payment.ImportNotificationsOfOrderExecutionCancellationRequest;
import ru.gosuslugi.dom.schema.integration.services.payment.ImportNotificationsOfOrderExecutionRequest;
import ru.gosuslugi.dom.schema.integration.services.services.*;

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
    ImportRSORequest.class,
    ImportDisclosureRequest.class,
    ExportMeteringDeviceHistoryRequest.class,
    ExportMeteringDeviceHistoryResult.class,
    ImportMeteringDeviceValuesRequest.class,
    ImportAdditionalServicesRequest.class,
    ru.gosuslugi.dom.schema.integration.services.nsi.ExportNsiItemResult.class,
    ExportDataProviderNsiItemRequest.class,
    ImportOrganizationWorksRequest.class,
    ImportMunicipalServicesRequest.class,
    ExportDataProviderResult.class,
    ExportDataProviderRequest.class,
    ExportOrgRegistryResult.class,
    ExportOrgRegistryRequest.class,
    ImportContractRequest.class,
    ExportAccountResult.class,
    ImportVotingProtocolRequest.class,
    ExportHouseRequest.class,
    ImportSupplyResourceContractRequest.class,
    ExportMeteringDeviceDataRequest.class,
    ExportStatusPublicPropertyContractResult.class,
    ExportCAChResult.class,
    ExportSupplyResourceContractResult.class,
    ExportStatusPublicPropertyContractRequest.class,
    ExportCAChRequest.class,
    ExportCAChAsyncRequest.class,
    ImportPublicPropertyContractRequest.class,
    ExportMeteringDeviceDataResult.class,
    ImportHouseUORequest.class,
    ExportStatusCAChRequest.class,
    ImportNotificationRequest.class,
    ExportVotingProtocolRequest.class,
    ImportMeteringDeviceDataRequest.class,
    ImportHouseOMSRequest.class,
    ImportAccountRequest.class,
    ImportHouseRSORequest.class,
    ExportVotingProtocolResult.class,
    ExportHouseResult.class,
    ExportSupplyResourceContractRequest.class,
    ExportStatusCAChResult.class,
    ExportAccountRequest.class,
    ru.gosuslugi.dom.schema.integration.services.house_management.ImportResult.class,
    ImportCharterRequest.class,
    ImportOKIRequest.class,
    ExportOKIResult.class,
    ExportOKIRequest.class,
    ExportWorkingListRequest.class,
    ImportWorkingListRequest.class,
    ImportWorkingPlanRequest.class,
    ExportHMServicesTarifsResult.class,
    ExportWorkingListResult.class,
    ExportWorkingPlanRequest.class,
    ExportWorkingPlanResult.class,
    ExportCompletedWorksResult.class,
    ExportCompletedWorksRequest.class,
    ExportHMServicesTarifsRequest.class,
    ImportCompletedWorksRequest.class,
    ImportSubsidiaryRequest.class,
    ImportNotificationsOfOrderExecutionCancellationRequest.class,
    ImportNotificationsOfOrderExecutionRequest.class,
    ExportPaymentDocumentDetailsResult.class,
    ExportPaymentDocumentDetailsRequest.class,
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
    ExportDisqualifiedPersonResult.class,
    ExportLicenseResult.class,
    ExportDisqualifiedPersonRequest.class,
    ExportLicenseRequest.class,
    ExportInspectionPlansResult.class,
    ExportExaminationsResult.class,
    ImportInspectionPlanRequest.class,
    ImportExaminationsRequest.class,
    ExportExaminationsRequest.class,
    ExportInspectionPlansRequest.class,
    ru.gosuslugi.dom.schema.integration.services.nsi_common.ExportNsiItemResult.class,
    ExportNsiListResult.class,
    ExportNsiItemRequest.class,
    ExportNsiListRequest.class,
    BaseAsyncResponseType.class,
    ru.gosuslugi.dom.schema.integration.base.ImportResult.class
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

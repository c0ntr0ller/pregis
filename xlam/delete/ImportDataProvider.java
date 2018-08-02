//package ru.prog_matik.java.pregis.services.organizations.common.service;
//
//import org.apache.log4j.Logger;
//import ru.gosuslugi.dom.schema.integration.base.ISRequestHeader;
//import ru.gosuslugi.dom.schema.integration.base.ImportResult;
//import ru.gosuslugi.dom.schema.integration.base.RegOrgType;
//import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
//import ru.gosuslugi.dom.schema.integration._8_7_0_6.organizations_registry_common.ImportDataProviderRequest;
//import ru.gosuslugi.dom.schema.integration._8_7_0_6.organizations_registry_common_service.Fault;
//import SaveToBaseMessages;
//import OtherFormat;
//
//import javax.xml.ws.Holder;
//
///**
// * Класс получает "SenderID".
// * Created by andryha on 16.02.2016.
// */
//public class ImportDataProvider {
//
//    private Logger logger = Logger.getLogger(ImportDataProvider.class);
//
//    private static final String NAME_METHOD = "importDataProvider";
//    private Holder<ResultHeader> resultHolder = new Holder<>();
//
//    public ImportResult allocateSenderID(String orgRootEntityGUID) {
//
//        RegOrgType regOrgType = new RegOrgType();
//        regOrgType.setOrgRootEntityGUID(orgRootEntityGUID);
//
//        ImportDataProviderRequest.DataProvider.AllocateSenderID allocateSenderID = new ImportDataProviderRequest.DataProvider.AllocateSenderID();
//        allocateSenderID.setRegOrg(regOrgType);
//
//        ImportDataProviderRequest.DataProvider provider = new ImportDataProviderRequest.DataProvider();
//        provider.setAllocateSenderID(allocateSenderID);
//        provider.setTransportGUID(OtherFormat.getRandomGUID());
//
//        ImportDataProviderRequest request = new ImportDataProviderRequest();
//        request.setId("signed-data-container");
//        request.getDataProvider().add(0, provider);
//
//
//        return getImportDataProviderRequest(request);
//    }
//
//    public ImportResult removeSenderID(String senderID) {
//
//        ImportDataProviderRequest.DataProvider.RemoveSenderID removeSenderID = new ImportDataProviderRequest.DataProvider.RemoveSenderID();
//        removeSenderID.setSenderID(senderID);
//
//        ImportDataProviderRequest.DataProvider provider = new ImportDataProviderRequest.DataProvider();
//        provider.setRemoveSenderID(removeSenderID);
//        provider.setTransportGUID(OtherFormat.getRandomGUID());
//
//        ImportDataProviderRequest request = new ImportDataProviderRequest();
//        request.setId("signed-data-container");
//        request.getDataProvider().add(0, provider);
//
//        return getImportDataProviderRequest(request);
//    }
//
//    private ImportResult getImportDataProviderRequest(ImportDataProviderRequest request) {
//
//        SaveToBaseMessages saveToBase = new SaveToBaseMessages();
//
//        OrgRegistryTransfer transfer = new OrgRegistryTransfer();
//
//        ISRequestHeader header = getHeader();
//
//        ImportResult result;
//
//        try {
//            result = transfer.importDataProvider(request, header, resultHolder);
//        } catch (Fault fault) {
//            saveToBase.setRequestError(header, NAME_METHOD, fault);
//            logger.error(fault.getMessage());
//            fault.printStackTrace();
//            return null;
//        }
//
//        saveToBase.setRequest(header, NAME_METHOD);
//
//        saveToBase.setResult(resultHolder.value, NAME_METHOD, result.getErrorMessage());
//
//        logger.info("Successful.");
//
//        return result;
//    }
//
//    /**
//     * Метод формирует заголовок сообщения.
//     *
//     * @return ISRequestHeader
//     */
//    private ISRequestHeader getHeader() {
//
//        ISRequestHeader isRequestHeader = new ISRequestHeader();
//        isRequestHeader.setDate(OtherFormat.getDateNow());
//        isRequestHeader.setMessageGUID(OtherFormat.getRandomGUID());
//
//        return isRequestHeader;
//    }
//
//
//
//}

package ru.progmatik.java.pregis.services.house;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.*;
import ru.gosuslugi.dom.schema.integration.house_management.*;
import ru.gosuslugi.dom.schema.integration.house_management_service_async.Fault;
import ru.gosuslugi.dom.schema.integration.house_management_service_async.HouseManagementPortsTypeAsync;
import ru.gosuslugi.dom.schema.integration.house_management_service_async.HouseManagementServiceAsync;
import ru.gosuslugi.dom.schema.integration.nsi_base.NsiRef;
import ru.progmatik.java.pregis.other.*;
import ru.progmatik.java.pregis.services.house_management.HouseManagementAsyncResultWaiter;

import javax.xml.ws.Holder;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/** класс служит для запроса списка контрактов по дому и манипулирования им
 * АХТУНГ! importContractData не работает ввиду дебилизма ГИС ЖКХ изза требований обязательности Аттачментов к договору управления!!!
 * Created by Администратор on 06.07.2017.
 */
public class ExportCAChData {
    private static final Logger LOGGER = Logger.getLogger(ExportCAChData.class);

    private static final String NAME_METHOD_IMPORT = "importContractData";
    private static final String NAME_METHOD_EXPORT = "exportCAChData";

    private final HouseManagementPortsTypeAsync port;
    private final AnswerProcessing answerProcessing;
    // Статус ошибок:
    //  1 - ошибок нет.
    //  0 - ошибки есть, но работа продолжена.
    // -1 - ошибка дальнейшая работа не возможна.
    private int errorStatus;
        
    public ExportCAChData(AnswerProcessing answerProcessing) {

        if(answerProcessing != null) {
            this.answerProcessing = answerProcessing;
        }else{
            this.answerProcessing = new AnswerProcessing();
        }

        final HouseManagementServiceAsync service = UrlLoader.instance().getUrlMap().get("homeManagementAsync") == null ? new HouseManagementServiceAsync()
                : new HouseManagementServiceAsync(UrlLoader.instance().getUrlMap().get("homeManagementAsync"));

        port = service.getHouseManagementPortAsync();
        OtherFormat.setPortSettings(service, port);

        errorStatus = 1;
    }

    /**
     * Метод формирует запрос exportCAChAsyncRequest на получение данных по дому, в которые входят уже имеющиеся контракты
     * @param fias ФИАС дома
     * @return
     */
    private ExportCAChAsyncRequest getExportHouseContractsRequest(String fias) throws SQLException {
        ExportCAChAsyncRequest request = new ExportCAChAsyncRequest();

        ExportCAChRequestCriteriaType exportCAChRequestCriteriaType = new ExportCAChRequestCriteriaType();
        exportCAChRequestCriteriaType.setFIASHouseGuid(fias);
        exportCAChRequestCriteriaType.setUOGUID(OrgsSettings.getOrgPPAGUID());

        exportCAChRequestCriteriaType.setLastVersionOnly(true);

        request.getCriteria().add(exportCAChRequestCriteriaType);


        request.setId(OtherFormat.getId());
        request.setVersion(request.getVersion());

        return request;
    }

    /**
     * метод вызывает ГИС жкх и получает контракты по дому
     * @param fias
     * @return
     * @throws SQLException
     */
    public GetStateResult callExportHouseContracts(final String fias) throws SQLException {
        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD_EXPORT);
        final Holder<ResultHeader> resultHolder = new Holder<>();

        final RequestHeader headerRequest = OtherFormat.getRequestHeader();

        ResultHeader resultHeader = null;
        Holder<ResultHeader> headerHolder = new Holder<>();
        ErrorMessageType resultErrorMessage = new ErrorMessageType();

        GetStateResult result = null;
        HouseManagementAsyncResultWaiter houseManagementAsyncResultWaiter = null;

        try {
            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
            AckRequest ackRequest = port.exportCAChData(getExportHouseContractsRequest(fias), headerRequest, resultHolder);
            answerProcessing.sendMessageToClient(TextForLog.REQUEST_SENDED);

            answerProcessing.sendToBaseAndAnotherError(NAME_METHOD_EXPORT, headerRequest, null,null, LOGGER);

            // ждем данные
            if (ackRequest != null) {
                houseManagementAsyncResultWaiter = new HouseManagementAsyncResultWaiter(ackRequest, NAME_METHOD_EXPORT, answerProcessing, port);

                result = houseManagementAsyncResultWaiter.getRequestResult();

                if (result != null) {
                    resultHeader = houseManagementAsyncResultWaiter.getHeaderHolder().value;
                    resultErrorMessage = result.getErrorMessage();
                }
                else{
                    resultErrorMessage.setErrorCode("ПреГИС");
                    resultErrorMessage.setDescription("Запрос не вернул данных");
                }
            }

        } catch (Fault fault) {
            answerProcessing.sendServerErrorToClient(NAME_METHOD_EXPORT, headerRequest, LOGGER, fault);
            setErrorStatus(-1);
        }

        answerProcessing.sendToBaseAndAnotherError(NAME_METHOD_EXPORT, null, resultHeader ,resultErrorMessage, LOGGER);
        return result;
    }

    public ExportCAChResultType.Contract getApprovedContractByFias(String fias) throws SQLException {
        ru.gosuslugi.dom.schema.integration.house_management.GetStateResult resultContract = callExportHouseContracts(fias);

        // если нет контрактов - выдаем ошибку
        if(resultContract == null || resultContract.getExportCAChResult() == null || resultContract.getExportCAChResult().size() == 0){
            answerProcessing.sendMessageToClient("Не найден договор управления в ГИС ЖКХ, сверка не производится");
            return null;
        }

        return getApprovedContract(resultContract);
    }

    /**
     * метод полуает активный контракт из готового ответа на запрос к ГИС
     * @param stateResult
     * @return
     */
    public ExportCAChResultType.Contract getApprovedContract(ru.gosuslugi.dom.schema.integration.house_management.GetStateResult stateResult) {
        // если нет контрактов - выдаем ошибку
        if (stateResult == null || stateResult.getExportCAChResult() == null || stateResult.getExportCAChResult().size() == 0) {
            answerProcessing.sendMessageToClient("Не найден договор управления в ГИС ЖКХ!");
            return null;
        }

        // получаем действующий контракт
        ExportCAChResultType.Contract curContract = null;
        for (ExportCAChResultType caChResultType : stateResult.getExportCAChResult()) {
            if (caChResultType.getContract() != null && caChResultType.getContract().getContractStatus() != null) {
                if (caChResultType.getContract().getContractStatus().value().equalsIgnoreCase("Approved")) {
                    curContract = caChResultType.getContract();
                }
            }
        }
        if (curContract == null) {
            curContract = stateResult.getExportCAChResult().get(0).getContract();
        }

        if (curContract == null) {
            answerProcessing.sendMessageToClient("Не найден ни один договор управления на доме в ГИС ЖКХ");
            return null;
        }

        return curContract;
    }

    /**
     * Метод выдает мапу с УИДами услуг и самими объектами услуг по дому
     * @param curContract текущий контрак дома
     * @return
     * @throws SQLException
     */
    public HashMap<String, NsiRef> getHouseServices(ExportCAChResultType.Contract curContract) throws SQLException {

        HashMap<String, NsiRef> nsiMap = new HashMap<>();

        for(ExportCAChResultType.Contract.ContractObject contractObject : curContract.getContractObject()){
            for(ExportCAChResultType.Contract.ContractObject.HouseService houseService: contractObject.getHouseService()){
                if(!nsiMap.containsKey(houseService.getServiceType().getGUID()))
                nsiMap.put(houseService.getServiceType().getGUID(), houseService.getServiceType());
            }

            for(ExportCAChResultType.Contract.ContractObject.AddService addService: contractObject.getAddService()){
                if(!nsiMap.containsKey(addService.getServiceType().getGUID()))
                    nsiMap.put(addService.getServiceType().getGUID(), addService.getServiceType());
            }
        }

        return nsiMap;
    }

    /**
     * Метод формирует запрос на создание новых услуг по дому и передает его дальше
     * @param fias
     * @param absentServices
     */
    public void callCreateNewContracts(String fias, HashMap<String, NsiRef> absentServices, GetStateResult stateResult) throws SQLException {
        // получаем действующий контракт
        ExportCAChResultType.Contract curContract = null;
        for(ExportCAChResultType caChResultType:stateResult.getExportCAChResult()){
            if(caChResultType.getContract().getContractStatus().equals("Approved")){
                curContract = caChResultType.getContract();
            }
        }
        if(curContract == null){
            curContract = stateResult.getExportCAChResult().get(0).getContract();
        }

        ImportContractRequest.Contract.EditContract.ContractObject.Add addContract = new ImportContractRequest.Contract.EditContract.ContractObject.Add();

        addContract.setFIASHouseGuid(fias);
        addContract.setStartDate(curContract.getEffectiveDate());
        addContract.setEndDate(curContract.getPlanDateComptetion());
        for(Map.Entry<String, NsiRef> absentService: absentServices.entrySet()) {
            ImportContractRequest.Contract.EditContract.ContractObject.Add.AddService addService = new ImportContractRequest.Contract.EditContract.ContractObject.Add.AddService();

            addService.setServiceType(absentService.getValue());
            addService.setStartDate(curContract.getEffectiveDate());
            addService.setEndDate(curContract.getPlanDateComptetion());

            BaseServiceType baseServiceType = new BaseServiceType();
            baseServiceType.setCurrentDoc(true);
            addService.setBaseService(baseServiceType);

            addContract.getAddService().add(addService);
        }

        BaseServiceType baseMService = new BaseServiceType();
        baseMService.setCurrentDoc(true);

        addContract.setBaseMService(baseMService);

        ImportContractRequest.Contract.EditContract.ContractObject contractObject = new ImportContractRequest.Contract.EditContract.ContractObject();
        contractObject.setAdd(addContract);
        contractObject.setTransportGUID(OtherFormat.getRandomGUID());


        ImportContractRequest.Contract.EditContract editContract = new ImportContractRequest.Contract.EditContract();
        editContract.getContractObject().add(contractObject);
        editContract.setDocNum(curContract.getDocNum());
        editContract.setSigningDate(curContract.getSigningDate());
        editContract.setEffectiveDate(curContract.getSigningDate());
        editContract.setPlanDateComptetion(curContract.getPlanDateComptetion());
        editContract.setContractVersionGUID(curContract.getContractVersionGUID());
        // editContract.setContractBase(curContract.getContractBase());

// необяз.        editContract.setValidity(curContract.getValidity());
        editContract.setCooperative(curContract.getCooperative());
        editContract.setMunicipalHousing(curContract.getMunicipalHousing());
        editContract.setBuildingOwner(curContract.getBuildingOwner());

        if(editContract.getCooperative() == null &&
                editContract.getMunicipalHousing() == null &&
                editContract.getBuildingOwner() == null){
            editContract.setOwners(true);
        }

        //! editContract.getAgreementAttachment().addAll(curContract.getAgreementAttachment());
        editContract.getContractAttachment().addAll(curContract.getContractAttachment());
        for (AttachmentType attachmentType: editContract.getContractAttachment()) {
            attachmentType.setAttachmentHASH(null);
        }
        editContract.setContractBase(curContract.getContractBase());

// необяз.        editContract.setDateDetails(curContract.getDateDetails());

        ImportContractRequest.Contract contract = new ImportContractRequest.Contract();
        contract.setEditContract(editContract);
        contract.setTransportGUID(OtherFormat.getRandomGUID());

        ImportContractRequest importContractRequest = new ImportContractRequest();
        importContractRequest.getContract().add(contract);

        importContractRequest.setId(OtherFormat.getId());
        importContractRequest.setVersion(importContractRequest.getVersion());

        createNewContracts(importContractRequest);
    }

    /**
     * Метод вызывает ГИС ЖКХ для создания новых услуг по дому
     * @param importContractRequest
     */
    private void createNewContracts(ImportContractRequest importContractRequest) throws SQLException {
        answerProcessing.sendMessageToClient(TextForLog.FORMED_REQUEST + NAME_METHOD_IMPORT);
        final Holder<ResultHeader> resultHolder = new Holder<>();

        final RequestHeader headerRequest = OtherFormat.getRequestHeader();

        ResultHeader resultHeader = null;
        Holder<ResultHeader> headerHolder = new Holder<>();
        ErrorMessageType resultErrorMessage = new ErrorMessageType();

        GetStateResult result = null;
        HouseManagementAsyncResultWaiter houseManagementAsyncResultWaiter = null;

        try {
            answerProcessing.sendMessageToClient(TextForLog.SENDING_REQUEST);
            AckRequest ackRequest = port.importContractData(importContractRequest, headerRequest, resultHolder);
            answerProcessing.sendMessageToClient(TextForLog.REQUEST_SENDED);

            answerProcessing.sendToBaseAndAnotherError(NAME_METHOD_IMPORT, headerRequest, null,null, LOGGER);

            // ждем данные
            if (ackRequest != null) {
                houseManagementAsyncResultWaiter = new HouseManagementAsyncResultWaiter(ackRequest, NAME_METHOD_IMPORT, answerProcessing, port);

                result = houseManagementAsyncResultWaiter.getRequestResult();

                if (result != null) {
                    resultHeader = houseManagementAsyncResultWaiter.getHeaderHolder().value;
                    resultErrorMessage = result.getErrorMessage();
                }
                else{
                    resultErrorMessage.setErrorCode("ПреГИС");
                    resultErrorMessage.setDescription("Запрос не вернул данных");
                }
            }

        } catch (Fault fault) {
            answerProcessing.sendServerErrorToClient(NAME_METHOD_IMPORT, headerRequest, LOGGER, fault);
            setErrorStatus(-1);
        }

        answerProcessing.sendToBaseAndAnotherError(NAME_METHOD_IMPORT, null, resultHeader ,resultErrorMessage, LOGGER);
    }

    /**
     * Метод, возвращает код ошибки.
     *
     * @return код ошибки (от -1 до 1).
     */
    private int getErrorStatus() {
        return errorStatus;
    }

    /**
     * Метод, принимает код ошибки, если код по приоритету ниже, то статус получает этот код,
     * иначе код ошибки остаётся прежним.
     *
     * @param errorNumber код ошибки.
     */
    private void setErrorStatus(final int errorNumber) {
        if (errorNumber < errorStatus) {
            errorStatus = errorNumber;
        }
    }
}

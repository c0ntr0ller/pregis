package ru.progmatik.java.pregis.services.organizations;

import ru.gosuslugi.dom.schema.integration.organizations_registry_common.ExportOrgRegistryRequest;
import ru.gosuslugi.dom.schema.integration.organizations_registry_common.ExportOrgRegistryResultType;
import ru.gosuslugi.dom.schema.integration.organizations_registry_common.GetStateResult;
import ru.progmatik.java.pregis.connectiondb.localdb.organization.OrganizationDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.organization.OrganizationDataSet;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.ResourcesUtil;

import java.sql.SQLException;

/**
 * Класс для запроса "экспорт сведений об организациях" ("hcs-organizations-registry-service").
 */
public class ExportOrgRegistry {

    private AnswerProcessing answerProcessing;

    public ExportOrgRegistry(AnswerProcessing answerProcessing) {
        this.answerProcessing = answerProcessing;
    }

    /**
     * Метод, получает данные текущей организации.
     * @return сформированный запрос.
     * @throws PreGISException
     */
    public ExportOrgRegistryRequest getExportOrgRegistryRequest() throws PreGISException {

        ExportOrgRegistryRequest.SearchCriteria criteria = new ExportOrgRegistryRequest.SearchCriteria();
        criteria.setOGRN(ResourcesUtil.instance().getOGRNCompany());

        ExportOrgRegistryRequest exportOrgRegistryRequest = new ExportOrgRegistryRequest();
        exportOrgRegistryRequest.setId("signed-data-container");
        exportOrgRegistryRequest.setVersion(exportOrgRegistryRequest.getVersion());
//        list.setOGRN("1116027009702"); // Test ООО "ЖЭУ-1" какой-то г. Иваново.
//        list.setKPP("540201001"); // Test
//        list.setOGRN("1125476111903");


        if (criteria.getOGRN() == null && criteria.getOGRNIP() == null &&
                criteria.getOrgRootEntityGUID() == null && criteria.getOrgVersionGUID() == null)
            throw new PreGISException("Не указан обязательный параметр!\n" +
                    "Укажите, пожалуйста, один из обязательных параметров:\n" +
                    "\"ОГРН\", \"ОГРНИП\", \"Идентификатор версии записи в реестре организаций\"\n" +
                    "или \"Идентификатор корневой сущности организации в реестре организаций\"");

        exportOrgRegistryRequest.getSearchCriteria().add(criteria);


        return exportOrgRegistryRequest;
    }

    /**
     * Метод получает идентификатор с помощью асинхронного порта
     * @throws PreGISException
     * @throws SQLException
     */
    public void exportOrgRegistry() throws PreGISException, SQLException {
        GetStateResult result = OrgRegistryAsyncPort.callExportOrgRegistry(getExportOrgRegistryRequest(), answerProcessing);
        if(result.getExportOrgRegistryResult() != null && result.getExportOrgRegistryResult().size() > 0){

            final OrganizationDAO organizationDAO = new OrganizationDAO();

            ExportOrgRegistryResultType exportOrgRegistryResult = result.getExportOrgRegistryResult().get(0); // пока берем первую, надо переделать для РКЦ
            OrganizationDataSet dataSet = new OrganizationDataSet(
                    exportOrgRegistryResult.getOrgVersion().getLegal().getFullName(),
                    exportOrgRegistryResult.getOrgVersion().getLegal().getShortName(),
                    exportOrgRegistryResult.getOrgVersion().getLegal().getOGRN(),
                    exportOrgRegistryResult.getOrgVersion().getLegal().getINN(),
                    exportOrgRegistryResult.getOrgVersion().getLegal().getKPP(),
                    exportOrgRegistryResult.getOrgRootEntityGUID(),
                    exportOrgRegistryResult.getOrgPPAGUID(),
                    ResourcesUtil.instance().getCompanyRole(), // Роль УО
                    ResourcesUtil.instance().getCompanyGradId(), // Идентификатор в БД ГРАД
                    exportOrgRegistryResult.getOrgVersion().getOrgVersionGUID()); // Примечание


            organizationDAO.addOrganization(dataSet);

            answerProcessing.sendOkMessageToClient("");
            answerProcessing.sendOkMessageToClient("Идентификатор зарегистрированной организации успешно получен!");

        }
        else {
            answerProcessing.sendErrorToClientNotException("ГИС ЖКХ не вернула идентификатор зарегистрированной организации!");
        }
    }
}

package ru.progmatik.java.pregis.services.organizations;

import ru.gosuslugi.dom.schema.integration.nsi_base.NsiRef;
import ru.gosuslugi.dom.schema.integration.organizations_registry_common.ExportOrgRegistryRequest;
import ru.gosuslugi.dom.schema.integration.organizations_registry_common.ExportOrgRegistryResultType;
import ru.gosuslugi.dom.schema.integration.organizations_registry_common.GetStateResult;
import ru.progmatik.java.pregis.connectiondb.grad.organization.OrganizationGRADDAO;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.model.Organization;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.other.OrgsSettings;
import ru.progmatik.java.pregis.other.ResourcesUtil;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Класс для запроса "экспорт сведений об организациях" ("hcs-organizations-registry-service").
 */
public class ExportOrgRegistry {

    private AnswerProcessing answerProcessing;

    public ExportOrgRegistry(AnswerProcessing answerProcessing) throws PreGISException {

        this.answerProcessing = answerProcessing;
        OrgsSettings orgsSettings = OrgsSettings.instance();
    }

    /**
     * Метод, получает данные текущей организации.
     * @return сформированный запрос.
     * @throws PreGISException
     */
    private ExportOrgRegistryRequest getExportOrgRegistryRequest(String ogrn) throws PreGISException {

        ExportOrgRegistryRequest.SearchCriteria criteria = new ExportOrgRegistryRequest.SearchCriteria();
        if(ogrn == null || ogrn.isEmpty()) {
            ogrn = ResourcesUtil.instance().getOGRNCompany();
        }

        if(ogrn.length() == 13) {
            criteria.setOGRN(ogrn);
        }else if(ogrn.length() == 15){
            criteria.setOGRNIP(ogrn);
        }else{
            answerProcessing.sendErrorToClientNotException("Неверная длина строки ОГРН/ОГРНИП");
            return null;
        }

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
        GetStateResult result = OrgRegistryAsyncPort.callExportOrgRegistry(getExportOrgRegistryRequest(null), answerProcessing);
        if(result != null && result.getExportOrgRegistryResult() != null && result.getExportOrgRegistryResult().size() > 0){

            // final OrganizationDAO organizationDAO = new OrganizationDAO();

            ExportOrgRegistryResultType exportOrgRegistryResult = result.getExportOrgRegistryResult().get(0); // пока берем первую, надо переделать для РКЦ
            Organization dataSet = new Organization(
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


            // organizationDAO.addOrganization(dataSet);
            OrganizationGRADDAO.setOrgToGrad(dataSet, answerProcessing);

            HashMap<Integer, Organization> orgsMap = new HashMap<>();
            orgsMap.put(ResourcesUtil.instance().getCompanyGradId(), dataSet);

            OrgsSettings.setOrgsMap(orgsMap);

            answerProcessing.sendOkMessageToClient("");
            answerProcessing.sendOkMessageToClient("Идентификатор зарегистрированной организации успешно получен!");

        }
        else {
            answerProcessing.sendErrorToClientNotException("ГИС ЖКХ не вернула идентификатор зарегистрированной организации!");
        }
    }


    /**
     * метод получает список организаций из БД, проверяет его и сохраняет в объекте орагнизаций.
     * Если проверка не пройдена - отсылает на обновление методу exportOrgsRegistry(
     * @throws PreGISException
     * @throws SQLException
     */
    public void fillOrgsMap() throws PreGISException, SQLException {
        StringBuilder errString = new StringBuilder();
        boolean needRefresh = false;
        answerProcessing.sendOkMessageToClient("Запрос идентификаторов организаций в Град");

        Map<Integer, Organization> orgs = OrganizationGRADDAO.getOrgsList(ResourcesUtil.instance().getCompanyGradId(), answerProcessing);

        if(orgs.size() > 0) {
            for (Organization organization : orgs.values()) {
                if (organization.getOgrn() != null && !organization.getOgrn().isEmpty()) {
                    if (organization.getOrgPPAGUID() == null || organization.getOrgPPAGUID().isEmpty()) {
                        needRefresh = true;
                    }
                } else {
                    errString.append(String.format("В Град не задан ОГРН для организации ИД %d \n", organization.getGradId()));
                }
            }
        }else{
            throw new PreGISException(String.format("В Град не задана организация ИД %d, инициализация списка...", ResourcesUtil.instance().getCompanyGradId()));
        }

        if(errString.length() > 0){
            throw new PreGISException(errString.toString());
        }

        // если нужно обновить список в ГИС - запускаем обновление
        if(needRefresh){
            exportOrgsRegistry(orgs);
        }
        else { // иначе - просто заносим список в объект настроек
            OrgsSettings.setOrgsMap(orgs);
        }
    }

    /**
     * перегруженный метод для вызова exportOrgsRegistry без списка организаций
     * @throws PreGISException
     * @throws SQLException
     */
    public void exportOrgsRegistry() throws PreGISException, SQLException {
        exportOrgsRegistry(null);
    }

    /**
     * метод получает список организаций (или создает его, если его нет, из БД Град) и получает из ГИС информацию по орагнизациям из списка
     * @param orgs
     * @throws PreGISException
     * @throws SQLException
     */
    private void exportOrgsRegistry(Map<Integer, Organization> orgs) throws PreGISException, SQLException {
        answerProcessing.sendOkMessageToClient("Запуск получения идентификаторов организаций из ГИС");

        if(orgs == null) orgs = OrganizationGRADDAO.getOrgsList(ResourcesUtil.instance().getCompanyGradId(), answerProcessing);

        for (Organization organization: orgs.values()) {

            if(organization.getOgrn() != null && !organization.getOgrn().isEmpty()) {
                answerProcessing.sendOkMessageToClient(String.format("Запрос идентификатора организации %s в ГИС ЖКХ", organization.getFullName()));
                GetStateResult result = OrgRegistryAsyncPort.callExportOrgRegistry(getExportOrgRegistryRequest(organization.getOgrn()), answerProcessing);

                if(result != null && result.getExportOrgRegistryResult() != null && result.getExportOrgRegistryResult().size() > 0) {
                    ExportOrgRegistryResultType exportOrgRegistryResult = result.getExportOrgRegistryResult().get(0);

                    organization.setFullName(exportOrgRegistryResult.getOrgVersion().getLegal().getFullName());
                    organization.setShortName(exportOrgRegistryResult.getOrgVersion().getLegal().getShortName());
                    organization.setInn(exportOrgRegistryResult.getOrgVersion().getLegal().getINN());
                    organization.setKpp(exportOrgRegistryResult.getOrgVersion().getLegal().getKPP());
                    organization.setOrgRootEntityGUID(exportOrgRegistryResult.getOrgRootEntityGUID());
                    organization.setOrgPPAGUID(exportOrgRegistryResult.getOrgPPAGUID());
                    organization.setRole(String.join(",", exportOrgRegistryResult.getOrganizationRoles().stream().map(NsiRef::getName).collect(Collectors.toList())));
                    organization.setDescription(exportOrgRegistryResult.getOrgVersion().getOrgVersionGUID()); // Примечание
                }else{
                    throw new PreGISException(String.format("Не удалось получить информацию по организации с ОГРН %s", organization.getOgrn()));
                }
            }
            else{
                answerProcessing.sendOkMessageToClient(String.format("В Град не задан ОГРН для организации ИД %d", organization.getGradId()));
            }
        }

        if(orgs.size() > 0){
            answerProcessing.sendOkMessageToClient("Запуск сохранения идентификаторов организаций в Град");
            OrganizationGRADDAO.setOrgListToGrad(orgs, answerProcessing);
            OrgsSettings.setOrgsMap(orgs);
        }
    }

    /**
     *
     * @return
     */
    public Map<String, Organization> getOrgsVersionFromGis(List<String> ogrnList){
        Map<String, Organization> ogrn2Org = new HashMap<>();


        for(String ogrn:ogrnList){
            if(ogrn.length() == 13 || ogrn.length() == 15) {
                try {

                    GetStateResult result = OrgRegistryAsyncPort.callExportOrgRegistry(getExportOrgRegistryRequest(ogrn), answerProcessing);

                    if (result != null) {
                        ExportOrgRegistryResultType exportOrgRegistryResult = result.getExportOrgRegistryResult().get(0);
                        if (!exportOrgRegistryResult.getOrgVersion().getOrgVersionGUID().isEmpty()) {
                            Organization organization = new Organization();
                            if(exportOrgRegistryResult.getOrgVersion().getLegal() != null) {
                                organization.setFullName(exportOrgRegistryResult.getOrgVersion().getLegal().getFullName());
                                organization.setShortName(exportOrgRegistryResult.getOrgVersion().getLegal().getShortName());
                                organization.setInn(exportOrgRegistryResult.getOrgVersion().getLegal().getINN());
                                organization.setKpp(exportOrgRegistryResult.getOrgVersion().getLegal().getKPP());
                            }
                            if(exportOrgRegistryResult.getOrgVersion().getEntrp() != null) {
                                organization.setFullName(exportOrgRegistryResult.getOrgVersion().getEntrp().getSurname() + " " + exportOrgRegistryResult.getOrgVersion().getEntrp().getFirstName() + " " + exportOrgRegistryResult.getOrgVersion().getEntrp().getPatronymic());
                                organization.setShortName(exportOrgRegistryResult.getOrgVersion().getEntrp().getSurname() + " " + exportOrgRegistryResult.getOrgVersion().getEntrp().getFirstName().substring(0, 1) + "." + exportOrgRegistryResult.getOrgVersion().getEntrp().getPatronymic().substring(0, 1) + ".");
                                organization.setInn(exportOrgRegistryResult.getOrgVersion().getEntrp().getINN());
                            }
                            organization.setOrgRootEntityGUID(exportOrgRegistryResult.getOrgRootEntityGUID());
                            organization.setOrgPPAGUID(exportOrgRegistryResult.getOrgPPAGUID());
                            if(exportOrgRegistryResult.getOrganizationRoles() != null) {
                                organization.setRole(String.join(",", exportOrgRegistryResult.getOrganizationRoles().stream().map(NsiRef::getName).collect(Collectors.toList())));
                            }
                            organization.setOrgVersionGUID(exportOrgRegistryResult.getOrgVersion().getOrgVersionGUID());
                            organization.setDescription(exportOrgRegistryResult.getOrgVersion().getOrgVersionGUID()); // Примечание

                            ogrn2Org.put(ogrn, organization);
                        }
                    }
                } catch (PreGISException e) {
                    answerProcessing.sendErrorToClientNotException("ГИС ЖКХ не вернула идентификаторы организаций!");
                }
            }else{
                answerProcessing.sendErrorToClientNotException(String.format("Неверная длина ОГРН/ОГРНИП %s", ogrn));
            }

        }
        return ogrn2Org;
    }
}

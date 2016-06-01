package ru.progmatik.java.pregis.services.nsi;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.NsiElementFieldType;
import ru.gosuslugi.dom.schema.integration.base.NsiElementNsiRefFieldType;
import ru.gosuslugi.dom.schema.integration.base.NsiElementStringFieldType;
import ru.gosuslugi.dom.schema.integration.base.NsiElementType;
import ru.gosuslugi.dom.schema.integration.services.nsi.ExportNsiItemResult;
import ru.progmatik.java.pregis.connectiondb.ConnectionBaseGRAD;
import ru.progmatik.java.pregis.connectiondb.reference.ReferenceItemDataSet;
import ru.progmatik.java.pregis.connectiondb.reference.ReferenceItemGRADDAO;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.services.nsi.common.service.ExportNsiItem;
import ru.progmatik.java.pregis.services.nsi.common.service.NsiListGroupEnum;
import ru.progmatik.java.pregis.services.nsi.service.ExportDataProviderNsiItem;
import ru.progmatik.java.web.servlets.socket.ClientService;

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * Класс, синхронизирует справочники:
 * 1 - Дополнительные услуги.
 * 51 - Коммунальные услуги.
 * 59 - Работы и услуги организации.
 * <p>
 * Позволяет импортировать сведения справочников 1, 51 и 59, и связать их текстовые наименования с
 * дополнительными атрибутами, проклассифицировать их по общесистемным справочникам ГИС ЖКХ.
 * Справочники являются версионными (каждой изменение возвращает новый GUID записи справочника).
 */
public class UpdateReference {

    private static final Logger LOGGER = Logger.getLogger(UpdateReference.class);

    private ClientService clientService;
    private AnswerProcessing answerProcessing;
    // лист в который будут добавляться элементы не найденные в БД, для последующего добавления или обновления.
    private ArrayList<NsiElementType> listForAdd = new ArrayList<>();
    private TreeSet<BigInteger> codeNsiItemsSet = new TreeSet<>();

    public UpdateReference(ClientService clientService, AnswerProcessing answerProcessing) {
        this.clientService = clientService;
        this.answerProcessing = answerProcessing;
    }

    public void updateAllDataProviderNsiItem() {

        clientService.sendMessage("Обновляю справочник 51 - \"Коммунальные услуги\".");
        if (updateDataProviderNsiItem(51)) {
            clientService.sendMessage("Справочник 51 - \"Коммунальные услуги\": успешно обновлен!");
        } else {
            clientService.sendMessage("Возникли ошибки.\nСправочник 51 - \"Коммунальные услуги\": не обновлен!");
        }

        clientService.sendMessage("Обновляю справочник 59 - \"Работы и услуги организации\".");
        if (updateDataProviderNsiItem(59)) {
            clientService.sendMessage("Справочник 59 - \"Работы и услуги организации\": успешно обновлен!");
        } else {
            clientService.sendMessage("Возникли ошибки.\nСправочник 59 - \"Работы и услуги организации\": не обновлен!");
        }

        clientService.sendMessage("Обновляю справочник 1 - \"Дополнительные услуги\".");
        if (updateDataProviderNsiItem(1)) {
            clientService.sendMessage("Справочник 1 - \"Дополнительные услуги\": успешно обновлен!");
        } else {
            clientService.sendMessage("Возникли ошибки.\nСправочник 1 - \"Дополнительные услуги\": не обновлен!");
        }

    }

    /**
     * Метод, получает код справочника и обновляет его в БД.
     *
     * @param codeNsi код справочника (1, 51, 59)
     */
    public boolean updateDataProviderNsiItem(int codeNsi) {

        listForAdd.clear(); // Очищаем очередь для добавления если в ней что то осталось с предыдущего раза.

        try (Connection connection = ConnectionBaseGRAD.instance().getConnection()) {
            ReferenceItemGRADDAO gradDAO = new ReferenceItemGRADDAO(connection);

            ExportDataProviderNsiItem dataProviderNsiItem = new ExportDataProviderNsiItem(clientService, answerProcessing);
            ExportNsiItemResult resultNsi = dataProviderNsiItem.callExportDataProviderNsiItem(codeNsi);

            clientService.sendMessage("Получаю справочники из БД с кодом: " + codeNsi);
            Map<String, ReferenceItemDataSet> mapNsiWithCodeNsi = gradDAO.getMapItemsCodeParent(codeNsi);

            if (mapNsiWithCodeNsi == null || resultNsi == null) {
                clientService.sendMessage("Возникли ошибки, справочники не обновлены!");
                return false;

            } else {

                checkElementInBase(resultNsi, mapNsiWithCodeNsi);

                if (listForAdd.size() > 0) { // если есть элементы для добавления, только тогда запустим формирование объекта пригодного для сохранения в БД.

                    clientService.sendMessage("Загружаю дополнительные справочники...");
                    ArrayList<ru.gosuslugi.dom.schema.integration.services.nsi_common.ExportNsiItemResult> nsiItemResults = loadOtherNsi();

                    clientService.sendMessage("Обновляю справочники в БД...");
                    addItemsInDB(mapNsiWithCodeNsi, codeNsi, nsiItemResults, gradDAO);
                }
            }

        } catch (SQLException e) {
            answerProcessing.sendClientErrorToClient(clientService, LOGGER, e);
            LOGGER.error(e);
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Метод, ищет в справочнике соответствие.
     *
     * @param result     полученный объект от ГИС ЖКХ.
     * @param mapDataSet содержатся записи справочника в БД.
     */
    private void checkElementInBase(ExportNsiItemResult result, Map<String, ReferenceItemDataSet> mapDataSet) {

        List<NsiElementType> nsiElements = result.getNsiItem().getNsiElement();
        for (NsiElementType nsiElement : nsiElements) {
            if (nsiElement.isIsActual()) {
                if (!mapDataSet.containsKey(nsiElement.getCode()) ||
                        !mapDataSet.get(nsiElement.getCode()).getUiid().equals(nsiElement.getGUID())) { // проверяем есть элемент в базе по коду справочника и guid'a
                    listForAdd.add(nsiElement); // элемент не найденили GUID не соответствует добавляем в лист для обновления в БД.
                }
            }
        }
    }

    /**
     * Метод, создает пригодный для БД объект и добавляет его.
     *
     * @param mapDataSet     элементы уже имеющееся в БД.
     * @param nsiItemResults список полученных справочников, на которые имеются ссылки.
     */
    private void addItemsInDB(Map<String, ReferenceItemDataSet> mapDataSet, Integer codeParent,
                              ArrayList<ru.gosuslugi.dom.schema.integration.services.nsi_common.ExportNsiItemResult> nsiItemResults,
                              ReferenceItemGRADDAO gradDAO) {

        NsiElementStringFieldType stringType;
        NsiElementNsiRefFieldType refFieldType;

        for (NsiElementType nsiElement : listForAdd) { // просматриваем каждый филд
            if (nsiElement.isIsActual()) {

                ReferenceItemDataSet dataSet;

                if (mapDataSet.containsKey(nsiElement.getCode())) { // проверяем если есть эелемент с таким кодом, то копируем его, при дабавлении в БД он обновится
                    dataSet = mapDataSet.get(nsiElement.getCode());
                } else { // если нет создаем новый
                    dataSet = new ReferenceItemDataSet();
                }

                dataSet.setCode(nsiElement.getCode());
                dataSet.setUiid(nsiElement.getGUID());
                dataSet.setCodeParent(codeParent);

                for (NsiElementFieldType field : nsiElement.getNsiElementField()) {
                    switch (field.getName()) {
                        case "Наименование работы в системе":
                            stringType = (NsiElementStringFieldType) field;
                            dataSet.setName(stringType.getValue());
                            break;
                        case "Вид дополнительной услуги":
                            stringType = (NsiElementStringFieldType) field;
                            dataSet.setName(stringType.getValue());
                            break;
                        case "Главная коммунальная услуга":
                            stringType = (NsiElementStringFieldType) field;
                            dataSet.setName(stringType.getValue());
                            break;
                        case "Вид коммунальной услуги":
                            refFieldType = (NsiElementNsiRefFieldType) field;
                            getGroupName(dataSet, refFieldType, nsiItemResults);
                            break;
                        case "Вид работ":
                            refFieldType = (NsiElementNsiRefFieldType) field;
                            getGroupName(dataSet, refFieldType, nsiItemResults);
                            break;
                    } // switch
                } // for
                gradDAO.addItem(dataSet);
                clientService.sendMessage("Добавлен новый элемент в справочник.");
            } // if
        } // for
    }

    /**
     * Метод, получает нужные ссылки на справочники.
     *
     * @param listElementTypes готовый лист с кодами справочников, которые необходимо загрузить.
     */
    private void getCodeNsiItem(ArrayList<NsiElementType> listElementTypes) {

        NsiElementNsiRefFieldType refFieldType;
        for (NsiElementType elementType : listElementTypes) {
            for (NsiElementFieldType field : elementType.getNsiElementField()) {
                switch (field.getName()) {
                    case "Вид коммунальной услуги":
                        refFieldType = (NsiElementNsiRefFieldType) field;
                        codeNsiItemsSet.add(refFieldType.getNsiRef().getNsiItemRegistryNumber());
                        break;
                    case "Вид работ":
                        refFieldType = (NsiElementNsiRefFieldType) field;
                        codeNsiItemsSet.add(refFieldType.getNsiRef().getNsiItemRegistryNumber());
                        break;
                } // switch
            } // for
        }

    }

    /**
     * Метод, загружает остальные справочники, для формирования объекта.
     */
    private ArrayList<ru.gosuslugi.dom.schema.integration.services.nsi_common.ExportNsiItemResult> loadOtherNsi() {

        getCodeNsiItem(listForAdd);
        ExportNsiItem exportNsiItem = new ExportNsiItem(clientService, answerProcessing);
        ArrayList<ru.gosuslugi.dom.schema.integration.services.nsi_common.ExportNsiItemResult> nsiItemResults = new ArrayList<>();

        for (BigInteger codeNsi : codeNsiItemsSet) {
            nsiItemResults.add(exportNsiItem.callExportNsiItem(NsiListGroupEnum.NSI, codeNsi));
        }
        return nsiItemResults;
    }

    /**
     * Метод, извлекает ссылку на другой справочник, находит название с другого справочника и задаёт объекту.
     *
     * @param dataSet        объект для параметров.
     * @param refFieldType   ссылка на справочник.
     * @param nsiItemResults список справочников.
     */
    private void getGroupName(ReferenceItemDataSet dataSet, NsiElementNsiRefFieldType refFieldType,
                              ArrayList<ru.gosuslugi.dom.schema.integration.services.nsi_common.ExportNsiItemResult> nsiItemResults) {

        boolean checkFind = true; // Если не найден нужный справочник станет true и выдаст пользователю информацию

        for (ru.gosuslugi.dom.schema.integration.services.nsi_common.ExportNsiItemResult nsiItemResult : nsiItemResults) {

            if (nsiItemResult.getNsiItem().getNsiItemRegistryNumber().compareTo(refFieldType.getNsiRef().getNsiItemRegistryNumber()) == 0) {

                List<NsiElementType> nsiElements = nsiItemResult.getNsiItem().getNsiElement();

                for (NsiElementType nsiElement : nsiElements) {
                    if (nsiElement.isIsActual()) {
                        if ((nsiElement.getCode().equals(refFieldType.getNsiRef().getRef().getCode())) &&
                                (nsiElement.getGUID().equals(refFieldType.getNsiRef().getRef().getGUID()))) {
                            NsiElementStringFieldType stringType;
                            for (NsiElementFieldType field : nsiElement.getNsiElementField()) {
                                switch (field.getName()) {
                                    case "Вид коммунальной услуги":
                                        stringType = (NsiElementStringFieldType) field;
                                        dataSet.setGroupName(stringType.getValue());
                                        checkFind = false;
                                        break;
                                    case "Вид работ":
                                        stringType = (NsiElementStringFieldType) field;
                                        dataSet.setGroupName(stringType.getValue());
                                        checkFind = false;
                                        break;
                                } // switch
                            } // for
                        }
                    }
                }
            }
        }

        if (checkFind) {
            clientService.sendMessage("Не удалось найти родительский элемент!\n" +
                    "Справочник: " + refFieldType.getNsiRef().getNsiItemRegistryNumber() + "\n" +
                    "Код элемента: " + refFieldType.getNsiRef().getRef().getCode() + "\n" +
                    "GUID элемента: " + refFieldType.getNsiRef().getRef().getGUID());

            LOGGER.info("Не удалось найти родительский элемент!\n" +
                    "Имя элемента: " + refFieldType.getName() + "\n" +
                    "Справочник: " + refFieldType.getNsiRef().getNsiItemRegistryNumber() + "\n" +
                    "Код элемента: " + refFieldType.getNsiRef().getRef().getCode() + "\n" +
                    "GUID элемента: " + refFieldType.getNsiRef().getRef().getGUID());
        }
    }
}

package ru.progmatik.java.pregis.services.nsi;
/*
    Принцип такой: справочники 1, 51 и 59 находятся в другом модули ГИС ЖКХ чем остальные, они имеют структуру разную,
    чем однотипные справочники NSI и NSIDAO. Нет возможности обрабатывать их в одном месте.
    Так же, есть справочники которые, необходимо использовать в коде, а есть которые выводяться пользователю в ГРАДе.
    "SERVICES_GIS_JKH" таблица содержится в БД ГРАД и отображается пользователю для формирования ПД.
    "SPR_NSI" - основная таблица в которой содержаться справочники.
    "NSI_FOR_DOWNLOAD" - таблица содержит перечень справочников для загрузки и слово, по окоторому будет извлечено значение из справочника
    "SPR_NSI_TYPE" - содержит тип справочника НСИ или НСИРАО (NSI, NSIRAO).
    Соответственно нет возможности обрабатывать и хранить всю информацию в одной таблице, лучше их разделить.
    NSI_DATA_PROVIDER_FOR_DOWNLOAD - хранит перечень стороних справочников (не 1, 51 и 59), для загрузки.
    NSI_DATA_PROVIDER_FOR_EXTRACT - хранит код справочника и ключевлое слово для извлечения нужного поля.
    Т.е. если нужно загрузить дополнительный справочник типа 50, то необходимо указать его в таблице NSI_DATA_PROVIDER_FOR_DOWNLOAD 50 NSI.
    Далее в таблице NSI_DATA_PROVIDER_FOR_EXTRACT укажим код справочника 50 и ключевое слово для получения нужных полей "Вид жилищной  услуги".
 */

/*
    Что бы добавить справочник нужно в таблицы добавить данные о них.
    Если справочник системный (будет использован только внутри программы), то в таблицу «NSI_FOR_DOWNLOAD»,
    нужно добавить информацию: код справочника, ключевое слово по которому будет извлечена информация, тип справочника (NSI или NSIRAO).
    Если справочник, который отображается пользователю для связки данных с ПД,
    то в таблицу «NSI_DATA_PROVIDER_FOR_DOWNLOAD» нужно добавить справочник который будет загружаться,
    код и тип справочника, например: 50 NSI. А в таблицу «NSI_DATA_PROVIDER_FOR_EXTRACT»,
    добавить информацию о извлечение данных: код справочника, ключевое слово, например: 50 Вид жилищной услуги.
    При обновлении справочников это информация будет учтена и загружена.

 */

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.*;
import ru.gosuslugi.dom.schema.integration.services.nsi.ExportNsiItemResult;
import ru.progmatik.java.pregis.connectiondb.grad.reference.ReferenceItemDataSet;
import ru.progmatik.java.pregis.connectiondb.grad.reference.ReferenceItemGRADDAO;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ReferenceDownloadNSIDataSet;
import ru.progmatik.java.pregis.connectiondb.localdb.reference.ReferenceNSI;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.services.nsi.common.service.ExportNsiItem;
import ru.progmatik.java.pregis.services.nsi.common.service.NsiListGroupEnum;
import ru.progmatik.java.pregis.services.nsi.service.ExportDataProviderNsiItem;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.*;

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

    private final AnswerProcessing answerProcessing;
    // лист в который будут добавляться элементы не найденные в БД, для последующего добавления или обновления.
    private ArrayList<NsiElementType> listForAdd = new ArrayList<>();
    private TreeSet<BigInteger> codeNsiItemsSet = new TreeSet<>();
    private ReferenceItemGRADDAO gradDAO;

    public UpdateReference(AnswerProcessing answerProcessing) throws SQLException {
        this.answerProcessing = answerProcessing;
        gradDAO = new ReferenceItemGRADDAO();
    }

    public void updateAllDataProviderNsiItem() throws SQLException, PreGISException {

        int countDone = 0;

        answerProcessing.sendMessageToClient("Обновляю справочник 51 - \"Коммунальные услуги\".");
        if (updateDataProviderNsiItem("51")) {
            answerProcessing.sendMessageToClient("Справочник 51 - \"Коммунальные услуги\": успешно обновлен!");
            countDone++;
        } else {
            answerProcessing.sendErrorToClientNotException("Возникли ошибки.\nСправочник 51 - \"Коммунальные услуги\": не обновлен!");
            answerProcessing.sendMessageToClient("::clearLabelText");
        }

        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient("Обновляю справочник 59 - \"Работы и услуги организации\".");
        if (updateDataProviderNsiItem("59")) {
            answerProcessing.sendMessageToClient("Справочник 59 - \"Работы и услуги организации\": успешно обновлен!");
            countDone++;
        } else {
            answerProcessing.sendErrorToClientNotException("Возникли ошибки.\nСправочник 59 - \"Работы и услуги организации\": не обновлен!");
            answerProcessing.sendMessageToClient("::clearLabelText");
        }

        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient("Обновляю справочник 1 - \"Дополнительные услуги\".");
        if (updateDataProviderNsiItem("1")) {
            answerProcessing.sendMessageToClient("Справочник 1 - \"Дополнительные услуги\": успешно обновлен!");
            countDone++;
        } else {
            answerProcessing.sendErrorToClientNotException("Возникли ошибки.\nСправочник 1 - \"Дополнительные услуги\" не обновлен!");
            answerProcessing.sendMessageToClient("::clearLabelText");
        }

        ReferenceNSI referenceNSI = new ReferenceNSI(answerProcessing);

        if (countDone == 3 && referenceNSI.updateNSIFromTableList() && updateOtherNsiForProviderNsi()) {
            answerProcessing.sendOkMessageToClient("\nСправочники успешно обновлены!");
        } else if (countDone < 4 && countDone > 0) {
            answerProcessing.sendInformationToClientAndLog("\nСправочники обновлены с ошибками!", LOGGER);
        } else if (countDone == 0) {
            answerProcessing.sendErrorToClientNotException("\nВозникли ошибки! Справочники не обновлены!");
        }
    }

    /**
     * Метод, получает код справочника и обновляет его в БД.
     *
     * @param codeNsi код справочника (1, 51, 59)
     */
    public boolean updateDataProviderNsiItem(String codeNsi) throws SQLException {

        listForAdd.clear(); // Очищаем очередь для добавления если в ней что то осталось с предыдущего раза.

//        try {
//        ReferenceItemGRADDAO gradDAO = new ReferenceItemGRADDAO();

        ExportDataProviderNsiItem dataProviderNsiItem = new ExportDataProviderNsiItem(answerProcessing);
        ExportNsiItemResult resultNsi = dataProviderNsiItem.callExportDataProviderNsiItem(codeNsi);

        answerProcessing.sendMessageToClient("");
        answerProcessing.sendMessageToClient("Получаю справочники из БД с кодом: " + codeNsi);
        Map<String, ReferenceItemDataSet> mapNsiWithCodeNsi = gradDAO.getMapItemsCodeParent(codeNsi);

        if (mapNsiWithCodeNsi == null || resultNsi == null) {
//                answerProcessing.sendMessageToClient("Возникли ошибки, справочники не обновлены!");
            return false;

        } else {

            checkElementInBase(resultNsi, mapNsiWithCodeNsi);

            if (listForAdd.size() > 0) { // если есть элементы для добавления, только тогда запустим формирование объекта пригодного для сохранения в БД.

                answerProcessing.sendMessageToClient("Загружаю дополнительные справочники...");
                ArrayList<ru.gosuslugi.dom.schema.integration.services.nsi_common.ExportNsiItemResult> nsiItemResults = loadOtherNsi();

                answerProcessing.sendMessageToClient("Обновляю справочники в БД...");
                addItemsInDB(mapNsiWithCodeNsi, codeNsi, nsiItemResults);
            }
        }

//        } catch (SQLException e) {
//            answerProcessing.sendErrorToClient("updateDataProviderNsiItem(): ", "\"Синхронизации справочников\" ", LOGGER, e);
//            return false;
//        }
        return true;
    }

    /**
     * Метод, будет загружать справочник в таблицу БД ГРАД, которая отображается у пользователя.
     */
    public boolean updateOtherNsiForProviderNsi() throws SQLException {

//        ReferenceItemGRADDAO gradDAO = new ReferenceItemGRADDAO();
//        Написать запрос в БД получить все справочники и обновить
//        listForAdd.clear(); // Очищаем очередь для добавления если в ней что то осталось с предыдущего раза.
        String nsiDataForLog = "";
        ArrayList<ReferenceDownloadNSIDataSet> nsiDataForDownload = gradDAO.getNsiDataForDownload();
        ExportNsiItem exportNsiItem = new ExportNsiItem(answerProcessing);

        for (ReferenceDownloadNSIDataSet nsiDataSet : nsiDataForDownload) {
            listForAdd.clear(); // Очищаем очередь для добавления если в ней что то осталось с предыдущего раза.

            nsiDataForLog = nsiDataSet.getNsiType().getNsi() + "-" + nsiDataSet.getCode();
//            nsiDataForLog = nsiDataSet.getNsiType().getNsi() + "-" + nsiDataSet.getCode() + " \"" + nsiDataSet.getWorldForExtract() + "\"";
            answerProcessing.sendMessageToClient("Обновляю справочник " + nsiDataForLog + ".");
            ru.gosuslugi.dom.schema.integration.services.nsi_common.ExportNsiItemResult resultNsi = exportNsiItem.callExportNsiItem(nsiDataSet.getNsiType(), new BigInteger(nsiDataSet.getCode()));

//            answerProcessing.sendMessageToClient("Получаю справочники из БД с кодом: " + nsiDataSet.getCode());
            Map<String, ReferenceItemDataSet> mapNsiWithCodeNsi = gradDAO.getMapItemsCodeParent(nsiDataSet.getCode());

            if (mapNsiWithCodeNsi == null || resultNsi == null) {
                answerProcessing.sendErrorToClientNotException("Возникли ошибки. Справочник " + nsiDataForLog + " не обновлен!");
//                answerProcessing.sendMessageToClient("Возникли ошибки, справочники не обновлены!");
                return false;

            } else {

//            поиск совпадений в таблице
                List<NsiElementType> nsiElements = resultNsi.getNsiItem().getNsiElement();
                for (NsiElementType nsiElement : nsiElements) {
                    if (nsiElement.isIsActual()) {
                        if (!mapNsiWithCodeNsi.containsKey(nsiElement.getCode()) ||
                                !mapNsiWithCodeNsi.get(nsiElement.getCode()).getGuid().equals(nsiElement.getGUID())) { // проверяем есть элемент в базе по коду справочника и guid'a
                            listForAdd.add(nsiElement); // элемент не найденили GUID не соответствует добавляем в лист для обновления в БД.
                        }
                    }
                }

                if (listForAdd.size() > 0) { // если есть элементы для добавления, только тогда запустим формирование объекта пригодного для сохранения в БД.

                    answerProcessing.sendMessageToClient("Обновляю справочники в БД...");
                    addItemsInDB(mapNsiWithCodeNsi, nsiDataSet.getCode(), null);
                }
            }
            answerProcessing.sendMessageToClient("Справочник " + nsiDataForLog + ": успешно обновлен!");
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
                        !mapDataSet.get(nsiElement.getCode()).getGuid().equals(nsiElement.getGUID())) { // проверяем есть элемент в базе по коду справочника и guid'a
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
    private void addItemsInDB(Map<String, ReferenceItemDataSet> mapDataSet, String codeParent,
                              ArrayList<ru.gosuslugi.dom.schema.integration.services.nsi_common.ExportNsiItemResult> nsiItemResults) throws SQLException {

        NsiElementStringFieldType stringType = null;
        NsiElementNsiRefFieldType refFieldType = null;
        HashMap<String, ArrayList<String>> nsiDataForExecute = gradDAO.getNsiDataForExecute();

        for (NsiElementType nsiElement : listForAdd) { // просматриваем каждый филд
            if (nsiElement.isIsActual()) {

                ReferenceItemDataSet dataSet;

                if (mapDataSet.containsKey(nsiElement.getCode())) { // проверяем если есть эелемент с таким кодом, то копируем его, при дабавлении в БД он обновится
                    dataSet = mapDataSet.get(nsiElement.getCode());
                } else { // если нет создаем новый
                    dataSet = new ReferenceItemDataSet();
                }

                dataSet.setCode(nsiElement.getCode());
                dataSet.setGuid(nsiElement.getGUID());
                dataSet.setCodeParent(codeParent);

                String tempFieldName = "";

                for (NsiElementFieldType field : nsiElement.getNsiElementField()) {

                    if (field instanceof NsiElementStringFieldType) {
                        if (nsiDataForExecute.get(codeParent).contains(field.getName())) {
                            stringType = (NsiElementStringFieldType) field;
                            dataSet.setName(stringType.getValue());
                            tempFieldName = stringType.getName();
                        }
                    } else if (field instanceof NsiElementNsiRefFieldType) {
                        if (nsiDataForExecute.get(codeParent).contains(field.getName())) {
                            refFieldType = (NsiElementNsiRefFieldType) field;
                            getGroupName(dataSet, refFieldType, nsiItemResults, nsiDataForExecute);
                        }
                    }



//                    Замена
//                    switch (field.getName()) {
//                        case "Наименование работы в системе":
//                            stringType = (NsiElementStringFieldType) field;
//                            dataSet.setName(stringType.getValue());
//                            break;
//                        case "Вид дополнительной услуги":
//                            stringType = (NsiElementStringFieldType) field;
//                            dataSet.setName(stringType.getValue());
//                            break;
//                        case "Главная коммунальная услуга":
//                            stringType = (NsiElementStringFieldType) field;
//                            dataSet.setName(stringType.getValue());
//                            break;
//                        case "Вид коммунальной услуги":
//                            refFieldType = (NsiElementNsiRefFieldType) field;
//                            getGroupName(dataSet, refFieldType, nsiItemResults);
//                            break;
//                        case "Вид работ":
//                            refFieldType = (NsiElementNsiRefFieldType) field;
//                            getGroupName(dataSet, refFieldType, nsiItemResults);
//                            break;
//                    } // switch
                } // for
                if (dataSet.getGroupName() == null || dataSet.getGroupName().isEmpty()) {
                    dataSet.setGroupName(tempFieldName);
                }
                gradDAO.addItem(dataSet);
                answerProcessing.sendMessageToClient("\nДобавлен новый элемент в справочник:\n" + dataSet.getCode() + " - " + dataSet.getName());
//                answerProcessing.sendMessageToClient("Добавлен новый элемент в справочник.");
            } // if
        } // for
    }

    /**
     * Метод, получает нужные ссылки на справочники.
     *
     * @param listElementTypes готовый лист с кодами справочников, которые необходимо загрузить.
     */
    private void getCodeNsiItem(ArrayList<NsiElementType> listElementTypes) throws SQLException {

        NsiElementNsiRefFieldType refFieldType;
        HashMap<String, ArrayList<String>> nsiDataForExecute = gradDAO.getNsiDataForExecute();
        for (NsiElementType elementType : listElementTypes) {
            for (NsiElementFieldType field : elementType.getNsiElementField()) {
                if (field instanceof NsiElementNsiRefFieldType) {
                    for (Map.Entry<String, ArrayList<String>> entry : nsiDataForExecute.entrySet()) {
                        if (entry.getValue().contains(field.getName())) {
                            refFieldType = (NsiElementNsiRefFieldType) field;
                            codeNsiItemsSet.add(refFieldType.getNsiRef().getNsiItemRegistryNumber());
                        }
                    }
                }

//                switch (field.getName()) {
//                    case "Вид коммунальной услуги":
//                        refFieldType = (NsiElementNsiRefFieldType) field;
//                        codeNsiItemsSet.add(refFieldType.getNsiRef().getNsiItemRegistryNumber());
//                        break;
//                    case "Вид работ":
//                        refFieldType = (NsiElementNsiRefFieldType) field;
//                        codeNsiItemsSet.add(refFieldType.getNsiRef().getNsiItemRegistryNumber());
//                        break;
//                } // switch
            } // for
        }

    }

    /**
     * Метод, загружает остальные справочники, для формирования объекта.
     */
    private ArrayList<ru.gosuslugi.dom.schema.integration.services.nsi_common.ExportNsiItemResult> loadOtherNsi() throws SQLException {

        getCodeNsiItem(listForAdd);
        ArrayList<ru.gosuslugi.dom.schema.integration.services.nsi_common.ExportNsiItemResult> nsiItemResults = new ArrayList<>();

        if (codeNsiItemsSet.size() > 0) {
            ExportNsiItem exportNsiItem = new ExportNsiItem(answerProcessing);
            for (BigInteger codeNsi : codeNsiItemsSet) {
                nsiItemResults.add(exportNsiItem.callExportNsiItem(NsiListGroupEnum.NSI, codeNsi));
            }
        }
        return nsiItemResults;
    }

    /**
     * Метод, извлекает ссылку на другой справочник, находит название с другого справочника и задаёт объекту.
     *  @param dataSet        объект для параметров.
     * @param refFieldType   ссылка на справочник.
     * @param nsiItemResults список справочников.
     * @param nsiDataForExecute
     */
    private void getGroupName(ReferenceItemDataSet dataSet, NsiElementNsiRefFieldType refFieldType,
                              ArrayList<ru.gosuslugi.dom.schema.integration.services.nsi_common.ExportNsiItemResult> nsiItemResults, HashMap<String, ArrayList<String>> nsiDataForExecute) {

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
                                if (nsiDataForExecute.containsKey(nsiItemResult.getNsiItem().getNsiItemRegistryNumber().toString()) &&
                                        nsiDataForExecute.get(nsiItemResult.getNsiItem().getNsiItemRegistryNumber().toString()).contains(field.getName())) {
                                    stringType = (NsiElementStringFieldType) field;
                                    dataSet.setGroupName(stringType.getValue());
                                    checkFind = false;
                                }

//                                Замена
//                                switch (field.getName()) {
//                                    case "Вид коммунальной услуги":
//                                        stringType = (NsiElementStringFieldType) field;
//                                        dataSet.setGroupName(stringType.getValue());
//                                        checkFind = false;
//                                        break;
//                                    case "Вид работ":
//                                        stringType = (NsiElementStringFieldType) field;
//                                        dataSet.setGroupName(stringType.getValue());
//                                        checkFind = false;
//                                        break;
//                                } // switch
                            } // for
                        }
                    }
                }
            }
        }

        if (checkFind) {
            answerProcessing.sendMessageToClient("Не удалось найти родительский элемент!\n" +
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

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
 * Класс, синхронизирует справочники.
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

    /**
     * Метод, получает справочник Коммунальных услуг.
     */
    public void update51() {

        clientService.sendMessage("Справочник: Коммунальные услуги");
        try (Connection connection = ConnectionBaseGRAD.instance().getConnection()) {
            ReferenceItemGRADDAO gradDAO = new ReferenceItemGRADDAO(connection);

            ExportDataProviderNsiItem dataProviderNsiItem = new ExportDataProviderNsiItem(clientService, answerProcessing);
            ExportNsiItemResult result51 = dataProviderNsiItem.callExportDataProviderNsiItem(51);

            Map<String, ReferenceItemDataSet> map51 = gradDAO.getMapItemsCodeParent(51);

            if (map51 == null) {
                clientService.sendMessage("Возникли ошибки, справочники не обновлены!");
                return;
            }

            checkElementInBase(result51, map51);

            if (listForAdd.size() > 0) { // если есть элементы для добавления, только тогда запустим формирование объекта пригодного для сохранения в БД.

                ArrayList<ru.gosuslugi.dom.schema.integration.services.nsi_common.ExportNsiItemResult> nsiItemResults = loadOtherNsi();

                addItemsInDB(map51, nsiItemResults);

            }
//            list51.

        } catch (SQLException e) {
            LOGGER.error(e);
            e.printStackTrace();
        }
    }

    /**
     * Метод, ищет в справочнике соответствие.
     * @param result полученный объект от ГИС ЖКХ.
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
     * @param mapDataSet элементы уже имеющееся в БД.
     * @param nsiItemResults список полученных справочников, на которые имеются ссылки.
     */
    private void addItemsInDB(Map<String, ReferenceItemDataSet> mapDataSet, Integer codeParent,
                              ArrayList<ru.gosuslugi.dom.schema.integration.services.nsi_common.ExportNsiItemResult> nsiItemResults) {

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
                            dataSet.setName(stringType.getName());
                            break;
                        case "Вид дополнительной услуги":
                            stringType = (NsiElementStringFieldType) field;
                            dataSet.setName(stringType.getName());
                            break;
                        case "Главная коммунальная услуга":
                            stringType = (NsiElementStringFieldType) field;
                            dataSet.setName(stringType.getName());
                            break;
                        case "Вид коммунальной услуги" :
                            refFieldType = (NsiElementNsiRefFieldType) field;
                            codeNsiItemsSet.add(refFieldType.getNsiRef().getNsiItemRegistryNumber());
                            break;
                        case "Вид работ" :
                            refFieldType = (NsiElementNsiRefFieldType) field;
                            codeNsiItemsSet.add(refFieldType.getNsiRef().getNsiItemRegistryNumber());
                            break;
                    } // switch
                } // for
            } // if
        } // for
    }

    /**
     * Метод, получает нужные ссылки на справочники.
     * @param listElementTypes готовый лист с кодами справочников, которые необходимо загрузить.
     */
    private void getCodeNsiItem(ArrayList<NsiElementType> listElementTypes) {

        NsiElementNsiRefFieldType refFieldType;
        for (NsiElementType elementType : listElementTypes) {
            for (NsiElementFieldType field : elementType.getNsiElementField()) {
                switch (field.getName()) {
                    case "Вид коммунальной услуги" :
                        refFieldType = (NsiElementNsiRefFieldType) field;
                        codeNsiItemsSet.add(refFieldType.getNsiRef().getNsiItemRegistryNumber());
                        break;
                    case "Вид работ" :
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
     * @param dataSet объект для параметров.
     * @param refFieldType ссылка на справочник.
     * @param nsiItemResults список справочников.
     */
    private void getGroupName(ReferenceItemDataSet dataSet, NsiElementNsiRefFieldType refFieldType,
                              ArrayList<ru.gosuslugi.dom.schema.integration.services.nsi_common.ExportNsiItemResult> nsiItemResults) {

        for (ru.gosuslugi.dom.schema.integration.services.nsi_common.ExportNsiItemResult nsiItemResult : nsiItemResults) {
            nsiItemResult.setNsiItem();
        }

    }
}

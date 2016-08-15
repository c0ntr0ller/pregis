/*
    Что бы добавить справочник нужно в таблицы добавить данные о них.
    Если справочник системный (будет использован только внутри программы), то в таблицу «NSI_FOR_DOWNLOAD»,
    нужно добавить информацию: код справочника, ключевое слово по которому будет извлечена информация, тип справочника (NSI или NSIRAO).
    Например: 22 Причина закрытия лицевого счета.
    При обновлении справочников это информация будет учтена и загружена.
 */
package ru.progmatik.java.pregis.connectiondb.localdb.reference;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.*;
import ru.gosuslugi.dom.schema.integration.services.nsi_common.ExportNsiItemResult;
import ru.progmatik.java.pregis.connectiondb.grad.reference.ReferenceItemDataSet;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.services.nsi.common.service.ExportNsiItem;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Класс, работает со справочником НСИ 95 "Документ, удостоверяющий личность".
 * Запрашивает у ГИС ЖКХ справочник, создаёт таблицу в БД, заносит в неё данные.
 * Если нет элементов в справочнике, запросит у ГИС ЖКХ, если есть выдаст из БД.
 */
public class ReferenceNSI {

    private static final Logger LOGGER = Logger.getLogger(ReferenceNSI.class);
    private final ReferenceNSIDAO nsiDao;
    private final AnswerProcessing answerProcessing;


    public ReferenceNSI(AnswerProcessing answerProcessing) throws SQLException {
        this.answerProcessing = answerProcessing;
        nsiDao = new ReferenceNSIDAO();

    }

    /**
     * Метод, получает из БД все типы документов из справочника НСИ 95, ноходит по имени нужный документ,
     * формирует объект "ru.gosuslugi.dom.schema.integration.base.NsiRef", пригодный для создания абонента.
     *
     * @param nameTypeDocument имя типа документа.
     * @return объект пригодный для создания абонента.
     * @throws PreGISException
     * @throws SQLException
     */
    public ru.gosuslugi.dom.schema.integration.base.NsiRef getTypeDocumentNsiRef(String nameTypeDocument) throws PreGISException, SQLException {

        String nsiCode = "95";

        return getNsiRef(nsiCode, nameTypeDocument);
    }

    /**
     * Метод, получает справочник для ГИС ЖКХ из таблице, ноходит по коду и имени нужный элемент,
     * формирует объект "ru.gosuslugi.dom.schema.integration.base.NsiRef", пригодный для создания абонента.
     *
     * @param nsiParentCode код справочника.
     * @param nameElement   имя нужного элемента.
     * @return объект пригодный для указания справочника ГИС ЖКХ.
     * @throws PreGISException
     * @throws SQLException
     */
    public ru.gosuslugi.dom.schema.integration.base.NsiRef getNsiRef(String nsiParentCode, String nameElement) throws PreGISException, SQLException {

        ArrayList<ReferenceItemDataSet> allItems = nsiDao.getAllItemsCodeParent(nsiParentCode);

        if (allItems.size() == 0) {
            updateNSIFromTableList();
            allItems = nsiDao.getAllItemsCodeParent(nsiParentCode);
        }

        for (ReferenceItemDataSet item : allItems) {
            if (nameElement.equalsIgnoreCase(item.getName())) {
                ru.gosuslugi.dom.schema.integration.base.NsiRef nsiRef = new NsiRef();
                nsiRef.setCode(item.getCode());
                nsiRef.setGUID(item.getGuid());
                nsiRef.setName(item.getName());
                return nsiRef;
            }
        }
        return null;
    }

    /**
     * Метод, обновляет справочники, найденные в таблице NSI_FOR_DOWNLOAD.
     *
     * @throws SQLException
     */
    public boolean updateNSIFromTableList() throws SQLException, PreGISException {

        ArrayList<ReferenceDownloadNSIDataSet> nsiListForDownload = nsiDao.getNsiForDownload();
        boolean isError = true;
        for (ReferenceDownloadNSIDataSet nsiDataSet : nsiListForDownload) {
            String nsiDataForLog = nsiDataSet.getNsiType().getNsi() + "-" + nsiDataSet.getCode() + " \"" + nsiDataSet.getWorldForExtract() + "\"";
            answerProcessing.sendMessageToClient("");
            answerProcessing.sendMessageToClient("Обновляю справочник " + nsiDataForLog + ".");

            if (updateNSI(nsiDataSet)) {
                answerProcessing.sendMessageToClient("Справочник " + nsiDataForLog + ": успешно обновлен!");
            } else {
                answerProcessing.sendErrorToClientNotException("Возникли ошибки. Справочник " + nsiDataForLog + " не обновлен!");
                isError = false;
            }
        }
        return isError;
    }

    /**
     * Метод, обновляет справочник.
     *
     * @return true если обновление прошло успешно, false - если справочники не удалось обновить.
     * @throws PreGISException
     * @throws SQLException
     */
    private boolean updateNSI(ReferenceDownloadNSIDataSet downloadNsiDataSet) throws PreGISException, SQLException {

        Map<String, ReferenceItemDataSet> mapItems = nsiDao.getMapItemsCodeParent(downloadNsiDataSet.getCode());
        ExportNsiItem nsiItem = new ExportNsiItem(answerProcessing);
        ExportNsiItemResult exportNsiItemResult = nsiItem.callExportNsiItem(downloadNsiDataSet.getNsiType(), new BigInteger(downloadNsiDataSet.getCode()));
        if (exportNsiItemResult == null) {
            throw new PreGISException("Невозможно получить справочник " + downloadNsiDataSet.getCode() + " из ГИС ЖКХ.");
        }
        String parenCode = exportNsiItemResult.getNsiItem().getNsiItemRegistryNumber().toString();
        for (NsiElementType itemNsi : exportNsiItemResult.getNsiItem().getNsiElement()) {
            if (itemNsi.isIsActual()) {
                if (!mapItems.containsKey(itemNsi.getCode()) ||
                        !mapItems.get(itemNsi.getCode()).getGuid().equals(itemNsi.getGUID())) {

                    for (NsiElementFieldType itemFieldType : itemNsi.getNsiElementField()) {

                        if (downloadNsiDataSet.getWorldForExtract().equals(itemFieldType.getName())) {

                            if (itemFieldType instanceof NsiElementStringFieldType) {
                                NsiElementStringFieldType fieldType = (NsiElementStringFieldType) itemFieldType;
                                if (downloadNsiDataSet.getWorldForExtract().equals(fieldType.getName())) {

                                    ReferenceItemDataSet dataSet = null;
                                    if (mapItems.containsKey(itemNsi.getCode())) {
                                        dataSet = mapItems.get(itemNsi.getCode());
                                    } else {
                                        dataSet = new ReferenceItemDataSet();
                                    }
                                    dataSet.setName(fieldType.getValue());
                                    dataSet.setCode(itemNsi.getCode());
                                    dataSet.setGuid(itemNsi.getGUID());
                                    dataSet.setGroupName(fieldType.getName());
                                    dataSet.setCodeParent(parenCode);
                                    nsiDao.addItem(dataSet);
                                    answerProcessing.sendMessageToClient("\nДобавлен новый элемент в справочник:\n" + dataSet.getCode() + " - " + dataSet.getName());
                                }
                            } else if (itemFieldType instanceof NsiElementIntegerFieldType) {
                                NsiElementIntegerFieldType fieldType = (NsiElementIntegerFieldType) itemFieldType;
                                if (downloadNsiDataSet.getWorldForExtract().equals(fieldType.getName())) {

                                    ReferenceItemDataSet dataSet = null;
                                    if (mapItems.containsKey(itemNsi.getCode())) {
                                        dataSet = mapItems.get(itemNsi.getCode());
                                    } else {
                                        dataSet = new ReferenceItemDataSet();
                                    }
                                    dataSet.setName(fieldType.getValue().toString());
                                    dataSet.setCode(itemNsi.getCode());
                                    dataSet.setGuid(itemNsi.getGUID());
                                    dataSet.setGroupName(fieldType.getName());
                                    dataSet.setCodeParent(parenCode);
                                    nsiDao.addItem(dataSet);
                                    answerProcessing.sendMessageToClient("\nДобавлен новый элемент в справочник:\n" + dataSet.getCode() + " - " + dataSet.getName());
                                }
                            }
                        }
                    }
                }
            }
        }
        answerProcessing.sendMessageToClient("");
        return true;
    }

}

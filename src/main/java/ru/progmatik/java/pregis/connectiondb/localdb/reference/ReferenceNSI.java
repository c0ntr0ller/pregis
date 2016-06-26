package ru.progmatik.java.pregis.connectiondb.localdb.reference;

import org.apache.log4j.Logger;
import ru.gosuslugi.dom.schema.integration.base.NsiElementStringFieldType;
import ru.gosuslugi.dom.schema.integration.base.NsiElementType;
import ru.gosuslugi.dom.schema.integration.base.NsiRef;
import ru.gosuslugi.dom.schema.integration.services.nsi_common.ExportNsiItemResult;
import ru.progmatik.java.pregis.connectiondb.grad.reference.ReferenceItemDataSet;
import ru.progmatik.java.pregis.exception.PreGISException;
import ru.progmatik.java.pregis.other.AnswerProcessing;
import ru.progmatik.java.pregis.services.nsi.common.service.ExportNsiItem;
import ru.progmatik.java.pregis.services.nsi.common.service.NsiListGroupEnum;

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
     * @param nameTypeDocument имя типа документа.
     * @return объект пригодный для создания абонента.
     * @throws PreGISException
     * @throws SQLException
     */
    public ru.gosuslugi.dom.schema.integration.base.NsiRef getTypeDocumentNsiRef(String nameTypeDocument) throws PreGISException, SQLException {

        String nsiCode = "95";

        ArrayList<ReferenceItemDataSet> allItems = nsiDao.getAllItemsCodeParent(nsiCode);

        if (allItems.size() == 0) {
            updateNSI(NsiListGroupEnum.NSI, nsiCode);
            allItems = nsiDao.getAllItems();
        }

        for (ReferenceItemDataSet item : allItems) {
            if (nameTypeDocument.equalsIgnoreCase(item.getName())) {
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
     * Метод, обновляет справочник.
     * @param nsiType тип справочника NIS или NSIRAO.
     * @param nsiCode код справочника.
     * @return true если обновление прошло успешно, false - если справочники не удалось обновить.
     * @throws PreGISException
     * @throws SQLException
     */
    public boolean updateNSI(NsiListGroupEnum nsiType, String nsiCode) throws PreGISException, SQLException {

        Map<String, ReferenceItemDataSet> mapItems = nsiDao.getMapItemsCodeParent(nsiCode);
        ExportNsiItem nsiItem = new ExportNsiItem(answerProcessing);
        ExportNsiItemResult exportNsiItemResult = nsiItem.callExportNsiItem(nsiType, new BigInteger(nsiCode));
        if (exportNsiItemResult == null) {
            throw new PreGISException("Невозможно получить справочник " + nsiCode + " из ГИС ЖКХ.");
        }
        String parenCode = exportNsiItemResult.getNsiItem().getNsiItemRegistryNumber().toString();
        for (NsiElementType itemNsi : exportNsiItemResult.getNsiItem().getNsiElement()) {
            if (itemNsi.isIsActual()) {
                if (!mapItems.containsKey(itemNsi.getCode()) ||
                        !mapItems.get(itemNsi.getCode()).getGuid().equals(itemNsi.getGUID())) {

                    NsiElementStringFieldType fieldType = (NsiElementStringFieldType) itemNsi.getNsiElementField().get(0);
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
            }
        }
        answerProcessing.sendMessageToClient("");
        return true;
    }
}

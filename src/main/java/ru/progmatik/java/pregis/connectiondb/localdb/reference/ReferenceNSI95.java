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
public class ReferenceNSI95 {

    private static final Logger LOGGER = Logger.getLogger(ReferenceNSI95.class);
    private final AnswerProcessing answerProcessing;

    public ReferenceNSI95(AnswerProcessing answerProcessing) {
        this.answerProcessing = answerProcessing;

    }

    public ru.gosuslugi.dom.schema.integration.base.NsiRef getNsiRef(String nameTypeDocument) throws PreGISException, SQLException {

        ReferenceNSI95DAO nsi95 = new ReferenceNSI95DAO();
        ArrayList<ReferenceItemDataSet> allItems = nsi95.getAllItems();

        if (allItems.size() == 0) {
            updateNSI95(nsi95);
            allItems = nsi95.getAllItems();
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

    public boolean updateNSI95(ReferenceNSI95DAO nsi95) throws PreGISException, SQLException {

        Map<Integer, ReferenceItemDataSet> mapItems = nsi95.getMapItems();
        ExportNsiItem nsiItem = new ExportNsiItem(answerProcessing);
        ExportNsiItemResult exportNsiItemResult = nsiItem.callExportNsiItem(NsiListGroupEnum.NSI, new BigInteger("95"));
        if (exportNsiItemResult == null) {
            throw new PreGISException("Невозможно получить справочник НСИ-95 из ГИС ЖКХ.");
        }
        Integer parenCode = Integer.valueOf(exportNsiItemResult.getNsiItem().getNsiItemRegistryNumber().toString());
        for (NsiElementType itemNsi : exportNsiItemResult.getNsiItem().getNsiElement()) {
            if (itemNsi.isIsActual()) {
                if (!mapItems.containsKey(Integer.parseInt(itemNsi.getCode())) ||
                        !mapItems.get(Integer.parseInt(itemNsi.getCode())).getGuid().equals(itemNsi.getGUID())) {
                    NsiElementStringFieldType fieldType = (NsiElementStringFieldType) itemNsi.getNsiElementField().get(0);
                    ReferenceItemDataSet dataSet = null;
                    if (mapItems.containsKey(Integer.parseInt(itemNsi.getCode()))) {
                        dataSet = mapItems.get(Integer.parseInt(itemNsi.getCode()));
                    } else {
                        dataSet = new ReferenceItemDataSet();
                    }
                    dataSet.setName(fieldType.getValue());
                    dataSet.setCode(itemNsi.getCode());
                    dataSet.setGuid(itemNsi.getGUID());
                    dataSet.setGroupName(fieldType.getName());
                    dataSet.setCodeParent(parenCode);
                    nsi95.addItem(dataSet);
                    answerProcessing.sendMessageToClient("\nДобавлен новый элемент в справочник:\n" + dataSet.getCode() + " - " + dataSet.getName());
                }
            }
        }
        answerProcessing.sendMessageToClient("");
        return true;
    }
}

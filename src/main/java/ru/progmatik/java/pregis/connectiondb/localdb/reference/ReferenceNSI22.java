package ru.progmatik.java.pregis.connectiondb.localdb.reference;

import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.other.AnswerProcessing;

/**
 * Класс, работает со справочником НСИ 22 "Причина закрытия лицевого счета".
 * Запрашивает у ГИС ЖКХ справочник, создаёт таблицу в БД, заносит в неё данные.
 * Если нет элементов в справочнике, запросит у ГИС ЖКХ, если есть выдаст из БД.
 */
public class ReferenceNSI22 {

    private static final Logger LOGGER = Logger.getLogger(ReferenceNSI22.class);
    private final AnswerProcessing answerProcessing;

    public ReferenceNSI22(AnswerProcessing answerProcessing) {
        this.answerProcessing = answerProcessing;
    }
}

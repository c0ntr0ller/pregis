package ru.progmatik.java.pregis.connectiondb.localdb.message;

import org.apache.log4j.Logger;
import ru.progmatik.java.pregis.connectiondb.ConnectionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Класс, создаёт таблицу "SPR_NAME_METHOD", если она не найдена.
 */
class NameMethodDAO {

    private static final Logger LOGGER = Logger.getLogger(NameMethodDAO.class);

    private static final String TABLE_NAME_SPR_NAME_METHOD = "SPR_NAME_METHOD";

    private static final String SQL_CREATE_TABLE_SPR_NAME_METHOD = "CREATE TABLE IF NOT EXISTS SPR_NAME_METHOD (" +
            "ID IDENTITY NOT NULL PRIMARY KEY, " +
            "NAME_METHOD VARCHAR_IGNORECASE(150) NOT NULL, " +
            "NAME_METHOD_RU VARCHAR_IGNORECASE(150) NOT NULL); " +

            "COMMENT ON TABLE PUBLIC.SPR_NAME_METHOD IS 'Таблица хранит перечень названий операций обмена для сервиса ГИС ЖКХ.'; " +
            "COMMENT ON COLUMN SPR_NAME_METHOD.ID IS 'Идентификатор записей.'; " +
            "COMMENT ON COLUMN SPR_NAME_METHOD.NAME_METHOD IS 'Имя метода. Содержится в заголовке сообщения, в документации, в коде Java.'; " +
            "COMMENT ON COLUMN SPR_NAME_METHOD.NAME_METHOD_RU IS 'Полное имя метода на русском языке.'; " +

            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('getState', 'Получить данные асинхронный вызов.'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('exportOrgRegistry', 'Экспорт сведений об организациях'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('exportDataProvider', 'Экспорт сведений о поставщиках данных'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('importDataProvider', 'Импорт сведений о поставщиках данных'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('exportNsiList', 'Получить перечень справочников с указанием даты последнего изменения каждого из них. ВИ_НСИ_ППС.'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('exportNsiItem', 'Экспорт данных справочника. ВИ_НСИ_ПДС.'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('exportDataProviderNsiItem', 'Экспортирует справочники №1, 51, 59 поставщика информации.'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('importAdditionalServices', 'Импортировать данные справочника 1 \"Дополнительные услуги\". ВИ_НСИ_ИДС_1.'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('importMunicipalServices', 'Импортировать данные справочника 51 \"Коммунальные услуги\". ВИ_НСИ_ИДС_51.'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('importOrganizationWorks', 'Импортировать данные справочника 59 \"Работы и услуги организации\". ВИ_НСИ_ИДС_59.'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('exportHouseData', 'Экспорт данных дома. ВИ_ИРАО_ДОМ_ЭКСП.'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('importHouseUOData', 'Импорт данных дома для полномочия УО'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('importHouseRSOData', 'Импорт данных дома для полномочия РСО'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('importHouseOMSData', 'Импорт данных дома для полномочия ОМС'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('exportAccountData', 'Получить перечень лицевых счетов. ВИ_ИЛС_РЛС.'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('importAccountData', 'Передать данные лицевых счетов. ВИ_ИЛС_СЗЛС.'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('exportMeteringDeviceData', 'Получить перечень ПУ.'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('importMeteringDeviceData', 'Передать данные ПУ'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('exportMeteringDeviceHistory', 'Получить историю показаний ПУ.'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('importMeteringDeviceValues', 'Передать показания ПУ.'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('exportPaymentDocumentData', 'Экспорт сведений о платежных документах'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('importPaymentDocumentData', 'Импорт сведений о платежных документах'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('exportOrgPeriodData', 'Получить расчетные периоды организации. ВИ_ИЛС_РПО_ЭКСП.'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('openOrgPaymentPeriod', 'Открыть расчетный период организации. ВИ_ИЛС_РПО_ОТК.'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('closeHousePaymentPeriod', 'Закрыть расчетный период дома. ВИ_ИЛС_РПД_ЗАК.'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('exportPaymentDocumentDetails', 'Экспорт реквизитов платежных документов (модуль payment)'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('importSubsidiary', 'Импорт сведений об обособленном подразделении'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('exportCAChData', 'Экспорт договоров управления, уставов, доп. соглашений.'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('exportStatusCAChData', 'Экспорт статусов договоров управления, уставов, доп. соглашений.'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('exportSupplyResourceContractData', 'Экспорт договоров ресурсоснабжения.'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('importContractData', 'Импорт договора управления'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('importCharterData', 'Импорт устава'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('exportStatusPublicPropertyContract', 'Экспорт списка ДОИ.'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('importPublicPropertyContract', 'Импорт ДОИ (Договор на пользование общим имуществом)'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('exportVotingProtocol', 'Экспорт протокола ОСС.'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('importVotingProtocol', 'Импорт протокола ОСС'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('exportShareEncbrData', 'Экспорт долей собственности и обременений. ВИ_ИРАО_ЭКСП_ДОЛЯОБР.'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('importShareEncbrData', 'Импорт долей собственности и обременений. ВИ_ИРАО_ИМП_ДОЛЯОБР.'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('importNotificationData', 'Импорт новостей для информирования граждан. ВИ_ИО_И_1.'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('importAccountIndividualServices', 'Импорт индивидуальных услуг лицевого счета'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('exportAccountIndividualServices', 'Экспорт индивидуальных услуг лицевого счета'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('ImportSupplierNotificationsOfOrderExecutionRequest', 'Импорт пакета документов «Извещение о принятии к исполнению распоряжения»'); " +
            "INSERT INTO PUBLIC.SPR_NAME_METHOD(NAME_METHOD, NAME_METHOD_RU) VALUES('ImportNotificationsOfOrderExecution', 'Пакет извещений о принятии к исполнению распоряжений (СД_ИОПЛАТА_ИЗВ_ЗАП).'); ";

    /**
     * Конструктор проверяет, существует таблица или нет. Если таблицы нет, то создаст её.
     * @throws SQLException
     */
    NameMethodDAO() throws SQLException {
        if (!ConnectionDB.instance().tableExist(TABLE_NAME_SPR_NAME_METHOD.toUpperCase())) {
            ConnectionDB.instance().sendSqlRequest(SQL_CREATE_TABLE_SPR_NAME_METHOD);
        }
    }

    /**
     * Метод, получает ИД записи по имени метода.
     * @param nameMethod имя метода.
     * @param connection подключение к локальной БД.
     * @return ИД записи.
     * @throws SQLException
     */
    int getMethodId(String nameMethod, Connection connection) throws SQLException {

        try (PreparedStatement ps  = connection.prepareStatement("SELECT ID FROM SPR_NAME_METHOD WHERE NAME_METHOD = ?")) {
            ps.setString(1, nameMethod);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    throw new java.sql.SQLException("Не найдено " + nameMethod + " в таблице \"SPR_NAME_METHOD.NAME_METHOD\"!");
                }
            }
        }
    }
}

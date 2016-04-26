package ru.prog_matik.java.pregis.services.organizations.common.service;

import ru.gosuslugi.dom.schema.integration._8_7_0.ISRequestHeader;
import ru.gosuslugi.dom.schema.integration._8_7_0.ResultHeader;
import ru.gosuslugi.dom.schema.integration._8_7_0_4.organizations_registry_common.ExportDataProviderRequest;
import ru.gosuslugi.dom.schema.integration._8_7_0_4.organizations_registry_common.ExportDataProviderResult;
import ru.gosuslugi.dom.schema.integration._8_7_0_4.organizations_registry_common.ExportOrgRegistryRequest;
import ru.gosuslugi.dom.schema.integration._8_7_0_4.organizations_registry_common.ExportOrgRegistryResult;
import ru.gosuslugi.dom.schema.integration._8_7_0_4.organizations_registry_common_service.Fault;
import ru.gosuslugi.dom.schema.integration._8_7_0_4.organizations_registry_common_service.RegOrgPortsType;
import ru.gosuslugi.dom.schema.integration._8_7_0_4.organizations_registry_common_service.RegOrgService;
import ru.prog_matik.java.pregis.other.OtherFormat;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

/**
 * Класс содержит параметры и реализует методы для обмена информацией по модулю "hcs-organizations-registry-common-service". Синхронный.
 * Наследуется от интерфейса "RegOrgPortsType".
 */
class OrgRegistryTransfer implements RegOrgPortsType {

    /*
    Гайд по получению "SenderID":
        Найти организацию(например по ОГРН) методом exportOrgRegistry.
        В ответ получите в теге orgRootEntityGUID,
        который собственно и нужно использовать в элементе orgRootEntityGUID метода importDataProvider.
     */

    /**
     * Метод формирует запрос и отправляет на серавер.
     * Экспорт сведений об организациях.
     * Первый вызываемый метод для получения "SenderID".
     *
     * @param header - Обязательный загаловок сообщения (Дата, идентификатор сообщения).
     * @param exportOrgRegistryRequest - Тело письма содержащее бизнес логику.
     * @param header0 - Заголовок письма полученый в ответ от сервера.
     * @return ExportOrgRegistryResult
     * @throws Fault
     */
    @Override
    public ExportOrgRegistryResult exportOrgRegistry(ExportOrgRegistryRequest exportOrgRegistryRequest, ISRequestHeader header, Holder<ResultHeader> header0) throws Fault {

        return getPort().exportOrgRegistry(exportOrgRegistryRequest, header, header0);
    }

    /**
     * Метод формирует запрос и отправляет на серавер.
     * Импорт сведений о поставщиках данных.
     *
     * @param header - Обязательный загаловок сообщения (Дата, идентификатор сообщения).
     * @param importDataProviderRequest - Тело письма содержащее бизнес логику.
     * @param header0 - Заголовок письма полученый в ответ от сервера.
     * @return ImportResult
     * @throws Fault
     */
//    @Override
//    public ImportResult importDataProvider(ImportDataProviderRequest importDataProviderRequest, ISRequestHeader header, Holder<ResultHeader> header0) throws Fault {
//
//        return getPort().importDataProvider(importDataProviderRequest, header, header0);
//    }

    /**
     * Метод формирует запрос и отправляет на серавер.
     * Экспорт сведений о поставщиках данных.
     *
     * @param header - Обязательный загаловок сообщения (Дата, идентификатор сообщения).
     * @param exportDataProviderRequest - Тело письма содержащее бизнес логику.
     * @param header0 - Заголовок письма полученый в ответ от сервера.
     * @return ExportDataProviderResult
     * @throws Fault
     */
    @Override
    public ExportDataProviderResult exportDataProvider(ExportDataProviderRequest exportDataProviderRequest, ISRequestHeader header, Holder<ResultHeader> header0) throws Fault {

        return getPort().exportDataProvider(exportDataProviderRequest, header, header0);
    }

    private RegOrgPortsType getPort() {

        RegOrgService service = new RegOrgService();
        RegOrgPortsType port = service.getRegOrgPort();

        BindingProvider provider = (BindingProvider) port;
        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, OtherFormat.USER_NAME);
        provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, OtherFormat.PASSWORD);
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, RegOrgService.__getWsdlLocation().toString());

        return port;
    }

}

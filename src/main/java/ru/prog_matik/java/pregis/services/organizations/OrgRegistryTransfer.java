package ru.prog_matik.java.pregis.services.organizations;

import ru.gosuslugi.dom.schema.integration._8_5_0.ISRequestHeader;
import ru.gosuslugi.dom.schema.integration._8_5_0.ImportResult;
import ru.gosuslugi.dom.schema.integration._8_5_0.ResultHeader;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.organizations_registry.*;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.organizations_registry_service.Fault;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.organizations_registry_service.RegOrgPortsType;
import ru.gosuslugi.dom.schema.integration._8_5_0_4.organizations_registry_service.RegOrgService;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;
import java.util.ResourceBundle;

/**
 * Класс содержит параметры и реализует методы для обмена информацией по модулю "hcs-organizations-registry-service".
 * Наследуется от интерфейса "RegOrgPortsType".
 */
class OrgRegistryTransfer implements RegOrgPortsType {

    private static final String USER_NAME = ResourceBundle.getBundle("application").getString("config.ws.user");
    private static final String PASSWORD = ResourceBundle.getBundle("application").getString("config.ws.password");

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

        RegOrgService service = new RegOrgService();
        RegOrgPortsType port = service.getRegOrgPort();

        BindingProvider provider = (BindingProvider) port;
        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, USER_NAME);
        provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, PASSWORD);
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, RegOrgService.__getWsdlLocation().toString());

        return port.exportOrgRegistry(exportOrgRegistryRequest, header, header0);
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
    @Override
    public ImportResult importDataProvider(ImportDataProviderRequest importDataProviderRequest, ISRequestHeader header, Holder<ResultHeader> header0) throws Fault {

        RegOrgService service = new RegOrgService();
        RegOrgPortsType port = service.getRegOrgPort();

        BindingProvider provider = (BindingProvider) port;
        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, USER_NAME);
        provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, PASSWORD);
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, RegOrgService.__getWsdlLocation().toString());

        return port.importDataProvider(importDataProviderRequest, header, header0);
    }

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

        RegOrgService service = new RegOrgService();
        RegOrgPortsType port = service.getRegOrgPort();

        BindingProvider provider = (BindingProvider) port;
        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, USER_NAME);
        provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, PASSWORD);
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, RegOrgService.__getWsdlLocation().toString());

        return port.exportDataProvider(exportDataProviderRequest, header, header0);
    }

    /**
     * Метод формирует запрос и отправляет на серавер.
     * Импорт сведений об обособленном подразделении.
     *
     * @param header - Обязательный загаловок сообщения (Дата, идентификатор сообщения).
     * @param importSubsidiaryRequest - Тело письма содержащее бизнес логику.
     * @param header0 - Заголовок письма полученый в ответ от сервера.
     * @return ImportResult
     * @throws Fault
     */
    @Override
    public ImportResult importSubsidiary(ImportSubsidiaryRequest importSubsidiaryRequest, ISRequestHeader header, Holder<ResultHeader> header0) throws Fault {

        RegOrgService service = new RegOrgService();
        RegOrgPortsType port = service.getRegOrgPort();

        BindingProvider provider = (BindingProvider) port;
        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, USER_NAME);
        provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, PASSWORD);
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, RegOrgService.__getWsdlLocation().toString());

        return port.importSubsidiary(importSubsidiaryRequest, header, header0);
    }
}

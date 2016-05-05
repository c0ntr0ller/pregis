package ru.prog_matik.java.pregis.services.organizations.service;

import ru.gosuslugi.dom.schema.integration.base.ImportResult;
import ru.gosuslugi.dom.schema.integration.base.RequestHeader;
import ru.gosuslugi.dom.schema.integration.base.ResultHeader;
import ru.gosuslugi.dom.schema.integration.services.organizations_registry.ImportSubsidiaryRequest;
import ru.gosuslugi.dom.schema.integration.services.organizations_registry_service.Fault;
import ru.gosuslugi.dom.schema.integration.services.organizations_registry_service.RegOrgPortsType;
import ru.gosuslugi.dom.schema.integration.services.organizations_registry_service.RegOrgService;
import ru.prog_matik.java.pregis.other.OtherFormat;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.Holder;

/**
 * Класс реализует сервис "Сервис обмена сведениями о поставщиках информации".
 * Содержит параметры и реализует методы для обмена информацией по модулю "hcs-organizations-registry-service". Синхронный.
 * Служит подключением.
 * Created by andryha on 28.02.2016.
 */
public class RegOrgPortsTypeImpl implements RegOrgPortsType {

    /**
     * Метод формирует запрос и отправляет на серавер.
     * Импорт сведений об обособленном подразделении.
     * Упразднен.
     *
     * @param header - Обязательный загаловок сообщения (Дата, идентификатор сообщения).
     * @param importSubsidiaryRequest - Тело письма содержащее бизнес логику.
     * @param header0 - Заголовок письма полученый в ответ от сервера.
     * @return ImportResult
     * @throws ru.gosuslugi.dom.schema.integration.services.organizations_registry_service.Fault
     */
    @Override
    public ImportResult importSubsidiary(ImportSubsidiaryRequest importSubsidiaryRequest, RequestHeader header, Holder<ResultHeader> header0) throws Fault {

        RegOrgService service = new RegOrgService();
        RegOrgPortsType port = service.getRegOrgPort();

        BindingProvider provider = (BindingProvider) port;
        provider.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, OtherFormat.USER_NAME);
        provider.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, OtherFormat.PASSWORD);
        provider.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, RegOrgService.__getWsdlLocation().toString());

        return port.importSubsidiary(importSubsidiaryRequest, header, header0);
    }
}

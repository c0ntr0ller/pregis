# ПреГИС #

Порядок подключения к использованию API на сайте "ГИС ЖКХ".

### Основные задачи ###

* Анализ XML.
* Отправка данных через сервис API.
* Получение данных через сервис API.
* Подготовка структуры базы данных.
* Реализовать удобный Web интерфейс.

### Программа должна обладать следующими характеристиками ###
 
* Иметь свой интерфейс.
* Иметь стабильность (свою базу данных).
* Инициализацию (иметь возможность получение ранее внесенных данных через API).

### Предоставлен доступ ###

* Адрес тестового стенда (для доступа в личные кабинеты тестовых поставщиков информации): http://54.76.42.99:60080 

Регламент и форматы информационного взаимодействия внешних информационных систем с ГИС ЖКХ»: https://dom.gosuslugi.ru/#/regulations 

* Добавлены тестовые поставщики информации: 

* Адреса эндпоинтов. 

**Внимание!** Подключение к тестовому стенду защищено; передача данных осуществляется по протоколу HTTPS с использованием сертификата, приложенного к заявке на регистрацию.


**Сервис обмена сведениями о начислениях**

https://54.76.42.99:60045/ext-bus-bills-service/services/Bills
https://54.76.42.99:60045/ext-bus-bills-service/services/BillsAsync

**Сервис обмена сведениями о показаниях приборов учета**

https://54.76.42.99:60045/ext-bus-device-metering-service/services/DeviceMeteringAsync
https://54.76.42.99:60045/ext-bus-device-metering-service/services/DeviceMetering

**Сервис обмена сведениями о жилищном фонде**

https://54.76.42.99:60045/ext-bus-home-management-service/services/HomeManagementAsync
https://54.76.42.99:60045/ext-bus-home-management-service/services/HomeManagement

**Сервис НСИ**

https://54.76.42.99:60045/ext-bus-nsi-service/services/NsiAsync
https://54.76.42.99:60045/ext-bus-nsi-service/services/Nsi

**Сервис обмена сведениями об услугах**

https://54.76.42.99:60045/ext-bus-organization-service/services/OrganizationAsync
https://54.76.42.99:60045/ext-bus-organization-service/services/Organization

**Сервис обмена сведениями о поставщиках информации**

https://54.76.42.99:60045/ext-bus-org-registry-service/services/OrgRegistry
https://54.76.42.99:60045/ext-bus-org-registry-service/services/OrgRegistryAsync

**Сервис обмена сведениями об инспектировании жилищного фонда**

https://54.76.42.99:60045/ext-bus-inspection-service/services/Inspection
https://54.76.42.99:60045/ext-bus-inspection-service/services/InspectionAsync

**Сервис обмена сведениями об объектах коммунальной инфраструктуры**

https://54.76.42.99:60045/ext-bus-rki-service/services/Infrastructure
https://54.76.42.99:60045/ext-bus-rki-service/services/InfrastructureAsync

**Сервис экспорта сведений о лицензировании деятельности управляющих компаний**

https://54.76.42.99:60045/ext-bus-licenses-service/services/Licenses
https://54.76.42.99:60045/ext-bus-licenses-service/services/LicensesAsync

**Сервис обмена сведениями об оплате и квитировании**

https://54.76.42.99:60045/ext-bus-payment-service/services/PaymentAsync

**Файловый сервис**	https://54.76.42.99:60045
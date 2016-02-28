@echo off
@rem Необходимо указать каталог, куда будут сформированы файлы для Java.
@rem Необходимо указать каталог, откуда будут взяты файлы wsdl.
@rem Например: wsdl_import.bat "C:\andryha\project\workspace_for_git\PreGIS\src\main\java" "C:\andryha\project\workspace_for_git\PreGIS\src\main\resources\wsdl" "8.6.0.4"
@echo.
@echo Формирования Java объектов из wsdl файлов сервиса ГИС ЖКХ.
@echo.
@if "-%1"=="-" @goto :ERROR
@if "-%2"=="-" @goto :ERROR
@if "-%3"=="-" @goto :ERROR
@set WSDLPATHOUT=%~1
@set WSDLPATHIN=%~2
@set WSDLVERSION=%~3
@echo Указан каталог, куда сформируются файлы для Java %WSDLPATHOUT%
@echo Указан каталог, откуда будут браться файлы wsdl %WSDLPATHIN%
@echo.
@echo Сервис обмена сведениями о начислениях. Асинхронный.
@echo Web-адрес сервиса: https://54.76.42.99:60045/ext-bus-bills-service/services/BillsAsync
wsimport -d %WSDLPATHOUT% -extension -Xnocompile -Xendorsed -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-bills-service-async-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-bills-service/services/BillsAsync
@echo.
@echo Сервис обмена сведениями о начислениях. Синхронный.
@echo Web-адрес сервиса: https://54.76.42.99:60045/ext-bus-bills-service/services/Bills
wsimport -d %WSDLPATHOUT% -extension -Xnocompile -Xendorsed -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-bills-service-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-bills-service/services/Bills
@echo.
@echo Сервис обмена сведениями о показаниях приборов учета. Асинхронный.
@echo Web-адрес сервиса: https://54.76.42.99:60045/ext-bus-device-metering-service/services/DeviceMeteringAsync
wsimport -d %WSDLPATHOUT% -extension -Xnocompile -Xendorsed -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-device-metering-service-async-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-device-metering-service/services/DeviceMeteringAsync
@echo.
@echo Сервис обмена сведениями о показаниях приборов учета. Синхронный.
@echo Web-адрес сервиса: https://54.76.42.99:60045/ext-bus-device-metering-service/services/DeviceMetering
wsimport -d %WSDLPATHOUT% -extension -Xnocompile -Xendorsed -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-device-metering-service-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-device-metering-service/services/DeviceMetering
@echo.
@echo Сервис обмена сведениями о жилищном фонде. Асинхронный.
@echo Web-адрес сервиса: https://54.76.42.99:60045/ext-bus-home-management-service/services/HomeManagementAsync
wsimport -d %WSDLPATHOUT% -extension -Xnocompile -Xendorsed -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-house-management-service-async-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-home-management-service/services/HomeManagementAsync
@echo.
@echo Сервис обмена сведениями о жилищном фонде. Синхронный.
@echo Web-адрес сервиса: https://54.76.42.99:60045/ext-bus-home-management-service/services/HomeManagement
wsimport -d %WSDLPATHOUT% -extension -Xnocompile -Xendorsed -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-house-management-service-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-home-management-service/services/HomeManagement
@echo.
@echo Сервис общесистемной НСИ. Асинхронный.
@echo Web-адрес сервиса: https://54.76.42.99:60045/ext-bus-nsi-common-service/services/NsiCommonAsync
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-nsi-common-service-async-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-nsi-common-service/services/NsiCommonAsync
@echo.
@echo Сервис общесистемной НСИ. Синхронный.
@echo Web-адрес сервиса: https://54.76.42.99:60045/ext-bus-nsi-common-service/services/NsiCommon
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-nsi-common-service-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-nsi-common-service/services/NsiCommon
@echo.
@echo Сервис частной НСИ. Асинхронный.
@echo Web-адрес сервиса: https://54.76.42.99:60045/ext-bus-nsi-service/services/NsiAsync
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-nsi-service-async-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-nsi-service/services/NsiAsync
@echo.
@echo Сервис частной НСИ. Синхронный.
@echo Web-адрес сервиса: https://54.76.42.99:60045/ext-bus-nsi-service/services/Nsi
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-nsi-service-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-nsi-service/services/Nsi
@echo.
@echo Сервис обмена сведениями об услугах. Асинхронный.
@echo Web-адрес сервиса: https://54.76.42.99:60045/ext-bus-organization-service/services/OrganizationAsync
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-services-service-async-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-organization-service/services/OrganizationAsync
@echo.
@echo Сервис обмена сведениями об услугах. Синхронный.
@echo Web-адрес сервиса: https://54.76.42.99:60045/ext-bus-organization-service/services/Organization
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-services-service-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-organization-service/services/Organization
@echo.
@echo Сервис обмена сведениями о поставщиках информации. Асинхронный.
@echo Web-адрес сервиса: https://54.76.42.99:60045/ext-bus-org-registry-common-service/services/OrgRegistryCommonAsync
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-organizations-registry-common-service-async-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-org-registry-common-service/services/OrgRegistryCommonAsync
@echo.
@echo Сервис обмена сведениями о поставщиках информации. Синхронный.
@echo Web-адрес сервиса: https://54.76.42.99:60045/ext-bus-org-registry-common-service/services/OrgRegistryCommon
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-organizations-registry-common-service-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-org-registry-common-service/services/OrgRegistryCommon
@echo.
@echo Сервис импорта сведений в реестр организаций. Асинхронный.
@echo Web-адрес сервиса: https://54.76.42.99:60045/ext-bus-org-registry-service/services/OrgRegistryAsync
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-organizations-registry-service-async-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-org-registry-service/services/OrgRegistryAsync
@echo.
@echo Сервис импорта сведений в реестр организаций. Синхронный.
@echo Web-адрес сервиса: https://54.76.42.99:60045/ext-bus-org-registry-service/services/OrgRegistry
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-organizations-registry-service-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-org-registry-service/services/OrgRegistry
@echo.
@echo Сервис обмена сведениями об инспектировании жилищного фонда. Асинхронный.
@echo Web-адрес сервиса: https://54.76.42.99:60045/ext-bus-inspection-service/services/InspectionAsync
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-inspection-service-async-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-inspection-service/services/InspectionAsync
@echo.
@echo Сервис обмена сведениями об инспектировании жилищного фонда. Синхронный.
@echo Web-адрес сервиса: https://54.76.42.99:60045/ext-bus-inspection-service/services/Inspection
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-inspection-service-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-inspection-service/services/Inspection
@echo.
@echo Сервис обмена сведениями об объектах коммунальной инфраструктуры. Асинхронный.
@echo Web-адрес сервиса: https://54.76.42.99:60045/ext-bus-rki-service/services/InfrastructureAsync
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-infrastructure-service-async-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-rki-service/services/InfrastructureAsync
@echo.
@echo Сервис обмена сведениями об объектах коммунальной инфраструктуры. Синхронный.
@echo Web-адрес сервиса: https://54.76.42.99:60045/ext-bus-rki-service/services/Infrastructure
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-infrastructure-service-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-rki-service/services/Infrastructure
@echo.
@echo Сервис экспорта сведений о лицензировании деятельности управляющих компаний. Асинхронный.
@echo Web-адрес сервиса: https://54.76.42.99:60045/ext-bus-licenses-service/services/LicensesAsync
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-licenses-service-async-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-licenses-service/services/LicensesAsync
@echo.
@echo Сервис экспорта сведений о лицензировании деятельности управляющих компаний. Синхронный.
@echo Web-адрес сервиса: https://54.76.42.99:60045/ext-bus-licenses-service/services/Licenses
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-licenses-service-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-licenses-service/services/Licenses
@echo.
@echo Сервис обмена сведениями об оплате и квитировании. Только Асинхронный.
@echo Web-адрес сервиса: https://54.76.42.99:60045/ext-bus-payment-service/services/PaymentAsync
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-payment-service-async-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-payment-service/services/PaymentAsync
@echo.
@echo Сервис обмена сведениями о деятельности управляющих организаций. Только Асинхронный.
@echo Web-адрес сервиса: https://54.76.42.99:60045/ext-bus-information-disclosure-service/services/DisclosureAsync
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-disclosure-service-async-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-information-disclosure-service/services/DisclosureAsync
@echo.
@echo Сервис обмена сведениями о лицах, осуществляющих поставки ресурсов. Только Асинхронный.
@echo Web-адрес сервиса: https://54.76.42.99:60045/ext-bus-fas-service/services/FASAsync
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-fas-service-async-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-fas-service/services/FASAsync
@echo.
@echo Все wsdl схемы для правильного формирования пространств имен.
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -Xendorsed -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\*.wsdl
@echo.
@echo Выход.
@goto :EOF

:ERROR
@if "-%1"=="-" @echo Не указан каталог для формирования файлов для Java!
@if "-%2"=="-" @echo Не указан каталог из которого брать wsdl файлы!
@if "-%3"=="-" @echo Не указана версия файлов wsdl для сервиса ГИС ЖКХ!
@echo.
@echo Выход.
@goto :EOF


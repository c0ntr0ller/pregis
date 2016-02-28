@echo off
@rem ����室��� 㪠���� ��⠫��, �㤠 ���� ��ନ஢��� 䠩�� ��� Java.
@rem ����室��� 㪠���� ��⠫��, ��㤠 ���� ����� 䠩�� wsdl.
@rem ���ਬ��: wsdl_import.bat "C:\andryha\project\workspace_for_git\PreGIS\src\main\java" "C:\andryha\project\workspace_for_git\PreGIS\src\main\resources\wsdl" "8.6.0.4"
@echo.
@echo ��ନ஢���� Java ��ꥪ⮢ �� wsdl 䠩��� �ࢨ� ��� ���.
@echo.
@if "-%1"=="-" @goto :ERROR
@if "-%2"=="-" @goto :ERROR
@if "-%3"=="-" @goto :ERROR
@set WSDLPATHOUT=%~1
@set WSDLPATHIN=%~2
@set WSDLVERSION=%~3
@echo ������ ��⠫��, �㤠 ��ନ������ 䠩�� ��� Java %WSDLPATHOUT%
@echo ������ ��⠫��, ��㤠 ���� ������ 䠩�� wsdl %WSDLPATHIN%
@echo.
@echo ��ࢨ� ������ ᢥ����ﬨ � ���᫥����. �ᨭ�஭��.
@echo Web-���� �ࢨ�: https://54.76.42.99:60045/ext-bus-bills-service/services/BillsAsync
wsimport -d %WSDLPATHOUT% -extension -Xnocompile -Xendorsed -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-bills-service-async-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-bills-service/services/BillsAsync
@echo.
@echo ��ࢨ� ������ ᢥ����ﬨ � ���᫥����. ����஭��.
@echo Web-���� �ࢨ�: https://54.76.42.99:60045/ext-bus-bills-service/services/Bills
wsimport -d %WSDLPATHOUT% -extension -Xnocompile -Xendorsed -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-bills-service-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-bills-service/services/Bills
@echo.
@echo ��ࢨ� ������ ᢥ����ﬨ � ���������� �ਡ�஢ ���. �ᨭ�஭��.
@echo Web-���� �ࢨ�: https://54.76.42.99:60045/ext-bus-device-metering-service/services/DeviceMeteringAsync
wsimport -d %WSDLPATHOUT% -extension -Xnocompile -Xendorsed -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-device-metering-service-async-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-device-metering-service/services/DeviceMeteringAsync
@echo.
@echo ��ࢨ� ������ ᢥ����ﬨ � ���������� �ਡ�஢ ���. ����஭��.
@echo Web-���� �ࢨ�: https://54.76.42.99:60045/ext-bus-device-metering-service/services/DeviceMetering
wsimport -d %WSDLPATHOUT% -extension -Xnocompile -Xendorsed -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-device-metering-service-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-device-metering-service/services/DeviceMetering
@echo.
@echo ��ࢨ� ������ ᢥ����ﬨ � ����魮� 䮭��. �ᨭ�஭��.
@echo Web-���� �ࢨ�: https://54.76.42.99:60045/ext-bus-home-management-service/services/HomeManagementAsync
wsimport -d %WSDLPATHOUT% -extension -Xnocompile -Xendorsed -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-house-management-service-async-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-home-management-service/services/HomeManagementAsync
@echo.
@echo ��ࢨ� ������ ᢥ����ﬨ � ����魮� 䮭��. ����஭��.
@echo Web-���� �ࢨ�: https://54.76.42.99:60045/ext-bus-home-management-service/services/HomeManagement
wsimport -d %WSDLPATHOUT% -extension -Xnocompile -Xendorsed -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-house-management-service-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-home-management-service/services/HomeManagement
@echo.
@echo ��ࢨ� �����⥬��� ���. �ᨭ�஭��.
@echo Web-���� �ࢨ�: https://54.76.42.99:60045/ext-bus-nsi-common-service/services/NsiCommonAsync
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-nsi-common-service-async-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-nsi-common-service/services/NsiCommonAsync
@echo.
@echo ��ࢨ� �����⥬��� ���. ����஭��.
@echo Web-���� �ࢨ�: https://54.76.42.99:60045/ext-bus-nsi-common-service/services/NsiCommon
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-nsi-common-service-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-nsi-common-service/services/NsiCommon
@echo.
@echo ��ࢨ� ��⭮� ���. �ᨭ�஭��.
@echo Web-���� �ࢨ�: https://54.76.42.99:60045/ext-bus-nsi-service/services/NsiAsync
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-nsi-service-async-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-nsi-service/services/NsiAsync
@echo.
@echo ��ࢨ� ��⭮� ���. ����஭��.
@echo Web-���� �ࢨ�: https://54.76.42.99:60045/ext-bus-nsi-service/services/Nsi
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-nsi-service-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-nsi-service/services/Nsi
@echo.
@echo ��ࢨ� ������ ᢥ����ﬨ �� ��㣠�. �ᨭ�஭��.
@echo Web-���� �ࢨ�: https://54.76.42.99:60045/ext-bus-organization-service/services/OrganizationAsync
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-services-service-async-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-organization-service/services/OrganizationAsync
@echo.
@echo ��ࢨ� ������ ᢥ����ﬨ �� ��㣠�. ����஭��.
@echo Web-���� �ࢨ�: https://54.76.42.99:60045/ext-bus-organization-service/services/Organization
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-services-service-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-organization-service/services/Organization
@echo.
@echo ��ࢨ� ������ ᢥ����ﬨ � ���⠢騪�� ���ଠ樨. �ᨭ�஭��.
@echo Web-���� �ࢨ�: https://54.76.42.99:60045/ext-bus-org-registry-common-service/services/OrgRegistryCommonAsync
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-organizations-registry-common-service-async-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-org-registry-common-service/services/OrgRegistryCommonAsync
@echo.
@echo ��ࢨ� ������ ᢥ����ﬨ � ���⠢騪�� ���ଠ樨. ����஭��.
@echo Web-���� �ࢨ�: https://54.76.42.99:60045/ext-bus-org-registry-common-service/services/OrgRegistryCommon
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-organizations-registry-common-service-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-org-registry-common-service/services/OrgRegistryCommon
@echo.
@echo ��ࢨ� ������ ᢥ����� � ॥��� �࣠����権. �ᨭ�஭��.
@echo Web-���� �ࢨ�: https://54.76.42.99:60045/ext-bus-org-registry-service/services/OrgRegistryAsync
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-organizations-registry-service-async-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-org-registry-service/services/OrgRegistryAsync
@echo.
@echo ��ࢨ� ������ ᢥ����� � ॥��� �࣠����権. ����஭��.
@echo Web-���� �ࢨ�: https://54.76.42.99:60045/ext-bus-org-registry-service/services/OrgRegistry
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-organizations-registry-service-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-org-registry-service/services/OrgRegistry
@echo.
@echo ��ࢨ� ������ ᢥ����ﬨ �� ��ᯥ��஢���� ����魮�� 䮭��. �ᨭ�஭��.
@echo Web-���� �ࢨ�: https://54.76.42.99:60045/ext-bus-inspection-service/services/InspectionAsync
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-inspection-service-async-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-inspection-service/services/InspectionAsync
@echo.
@echo ��ࢨ� ������ ᢥ����ﬨ �� ��ᯥ��஢���� ����魮�� 䮭��. ����஭��.
@echo Web-���� �ࢨ�: https://54.76.42.99:60045/ext-bus-inspection-service/services/Inspection
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-inspection-service-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-inspection-service/services/Inspection
@echo.
@echo ��ࢨ� ������ ᢥ����ﬨ �� ��ꥪ�� ����㭠�쭮� ������������. �ᨭ�஭��.
@echo Web-���� �ࢨ�: https://54.76.42.99:60045/ext-bus-rki-service/services/InfrastructureAsync
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-infrastructure-service-async-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-rki-service/services/InfrastructureAsync
@echo.
@echo ��ࢨ� ������ ᢥ����ﬨ �� ��ꥪ�� ����㭠�쭮� ������������. ����஭��.
@echo Web-���� �ࢨ�: https://54.76.42.99:60045/ext-bus-rki-service/services/Infrastructure
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-infrastructure-service-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-rki-service/services/Infrastructure
@echo.
@echo ��ࢨ� �ᯮ�� ᢥ����� � ��業��஢���� ���⥫쭮�� �ࠢ����� ��������. �ᨭ�஭��.
@echo Web-���� �ࢨ�: https://54.76.42.99:60045/ext-bus-licenses-service/services/LicensesAsync
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-licenses-service-async-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-licenses-service/services/LicensesAsync
@echo.
@echo ��ࢨ� �ᯮ�� ᢥ����� � ��業��஢���� ���⥫쭮�� �ࠢ����� ��������. ����஭��.
@echo Web-���� �ࢨ�: https://54.76.42.99:60045/ext-bus-licenses-service/services/Licenses
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-licenses-service-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-licenses-service/services/Licenses
@echo.
@echo ��ࢨ� ������ ᢥ����ﬨ �� ����� � ����஢����. ���쪮 �ᨭ�஭��.
@echo Web-���� �ࢨ�: https://54.76.42.99:60045/ext-bus-payment-service/services/PaymentAsync
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-payment-service-async-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-payment-service/services/PaymentAsync
@echo.
@echo ��ࢨ� ������ ᢥ����ﬨ � ���⥫쭮�� �ࠢ����� �࣠����権. ���쪮 �ᨭ�஭��.
@echo Web-���� �ࢨ�: https://54.76.42.99:60045/ext-bus-information-disclosure-service/services/DisclosureAsync
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-disclosure-service-async-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-information-disclosure-service/services/DisclosureAsync
@echo.
@echo ��ࢨ� ������ ᢥ����ﬨ � ����, �����⢫���� ���⠢�� ����ᮢ. ���쪮 �ᨭ�஭��.
@echo Web-���� �ࢨ�: https://54.76.42.99:60045/ext-bus-fas-service/services/FASAsync
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\hcs-fas-service-async-%WSDLVERSION%.wsdl -wsdllocation https://54.76.42.99:60045/ext-bus-fas-service/services/FASAsync
@echo.
@echo �� wsdl �奬� ��� �ࠢ��쭮�� �ନ஢���� ����࠭�� ����.
wsimport -d %WSDLPATHOUT% -extension -Xdebug -Xnocompile -Xendorsed -XadditionalHeaders -Xno-addressing-databinding -keep -encoding UTF-8 -verbose %WSDLPATHIN%\*.wsdl
@echo.
@echo ��室.
@goto :EOF

:ERROR
@if "-%1"=="-" @echo �� 㪠��� ��⠫�� ��� �ନ஢���� 䠩��� ��� Java!
@if "-%2"=="-" @echo �� 㪠��� ��⠫�� �� ���ண� ���� wsdl 䠩��!
@if "-%3"=="-" @echo �� 㪠���� ����� 䠩��� wsdl ��� �ࢨ� ��� ���!
@echo.
@echo ��室.
@goto :EOF


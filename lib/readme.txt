порядок обновления форматов:
1. скачиваем новые форматы
2. распаковываем архив
3. из папки "hcs_wsdl_xsd_v.<номер версии>" переносим все вложенные папки в папку c:\Java\giswsdl\src\main\resources\wsdl
4. запускаем скрипт c:\Java\giswsdl\script\maven_jar_add_ppak.bat - он создаст новые классы в сорцах проекта giswsdl
5. открываем проект giswsdl и запускаем такс pakage в Мавене
6. запускаем скрипт c:\Java\giswsdl\script\wsdl_import_ppak.bat - он перепакует c:\Java\giswsdl\target\giswsdl-3.10.jar
7. копируем c:\Java\giswsdl\target\giswsdl-3.10.jar в нашу папку lib
8. запускаем pregis и проверяем обмены

добавление в maven
mvn install:install-file -Dfile=/home/bek/Java/pregis_b/lib/giswsdl-3.11.jar -DgroupId=ru.progmatik.java -DartifactId=giswsdl -Dversion=3.11 -Dpackaging=jar
в pom не должно быть ссылок на сам файл, все возьмется из локального репозитория
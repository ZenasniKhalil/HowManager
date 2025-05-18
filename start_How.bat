@echo off
REM Définir les valeurs par défaut
REM Hibernate Dialect - Par défaut H2
REM Pour H2 :
REM HIBERNATE_DIALECT="org.hibernate.dialect.H2Dialect"
REM Si autre que H2 -> sql.init : never

REM Pour PostgreSQL :
REM HIBERNATE_DIALECT="org.hibernate.dialect.PostgreSQLDialect"

REM Pour MySQL :
REM HIBERNATE_DIALECT="org.hibernate.dialect.MySQLDialect"

REM Décommenter et adapter selon la base utilisée
SET "DB_URL=%~1"
IF "%DB_URL%"=="" SET "DB_URL=jdbc:h2:mem:testdb"

SET "DB_USER=%~2"
IF "%DB_USER%"=="" SET "DB_USER=demo"

SET "DB_PASS=%~3"
IF "%DB_PASS%"=="" SET "DB_PASS="

SET "SERVER_PORT=%~4"
IF "%SERVER_PORT%"=="" SET "SERVER_PORT=8080"

SET "HIBERNATE_DIALECT=%~5"
IF "%HIBERNATE_DIALECT%"=="" SET "HIBERNATE_DIALECT=org.hibernate.dialect.H2Dialect"

REM Lancer l'application Java avec les paramètres
java -jar HowManager.jar ^
--spring.datasource.url=%DB_URL% ^
--spring.datasource.username=%DB_USER% ^
--spring.datasource.password=%DB_PASS% ^
--server.port=%SERVER_PORT% ^
--spring.jpa.properties.hibernate.dialect=%HIBERNATE_DIALECT% ^
--logging.level.org.hibernate.SQL=DEBUG ^
--logging.level.org.hibernate.type=TRACE ^
--logging.level.org.springframework.jdbc=DEBUG
--spring.sql.init.mode=always


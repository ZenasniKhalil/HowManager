#!/bin/bash

# Base de données par défaut : H2
#jdbc:sqldialect://adresse-serveur:port/nom-base
DB_URL=${1:-"jdbc:h2:mem:testdb"}
DB_USER=${2:-"demo"}
DB_PASS=${3:-""}
SERVER_PORT=${4:-8080} #TOMCAT 

# Hibernate Dialect - Par défaut H2
# Pour H2 :
# HIBERNATE_DIALECT="org.hibernate.dialect.H2Dialect"
# Si autre que H2 -> sql.init : never

# Pour PostgreSQL :
# HIBERNATE_DIALECT="org.hibernate.dialect.PostgreSQLDialect"

# Pour MySQL :
# HIBERNATE_DIALECT="org.hibernate.dialect.MySQLDialect"

# Décommenter et adapter selon la base utilisée
HIBERNATE_DIALECT=${5:-"org.hibernate.dialect.H2Dialect"}

java -jar HowManager.jar \
--spring.datasource.url=$DB_URL \ 
--spring.datasource.username=$DB_USER \
--spring.datasource.password=$DB_PASS \
--server.port=$SERVER_PORT \
--spring.jpa.properties.hibernate.dialect=$HIBERNATE_DIALECT\
--logging.level.org.hibernate.SQL=DEBUG\
--logging.level.org.hibernate.type=TRACE\
--logging.level.org.springframework.jdbc=DEBUG
--spring.sql.init.mode=always

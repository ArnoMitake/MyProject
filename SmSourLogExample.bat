@echo off
chcp 65001 > nul
@Title SmSourLogExampleMain
set JAVA_HOME=D:\Program Files\java\jdk1.8.0_321
java -cp "bin;model;lib/HikariCP-4.0.3.jar;lib/mssql-jdbc-9.2.1.jre8.jar;lib/commons-lang3-3.7.jar;lib/slf4j-api-1.7.22.jar;lib/logback-classic-1.2.6.jar;lib/logback-core-1.2.6.jar" SmSourLogExample
pause



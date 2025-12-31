@echo off
REM Set Java 17 for this project only
set JAVA_HOME=C:\Program Files\Amazon Corretto\jdk17.0.17_10
set PATH=%JAVA_HOME%\bin;%PATH%

echo Using Java 17 for this project:
java -version
echo.
echo Building project...
mvn clean install

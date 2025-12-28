@echo off
REM Set Java 21 for this project only
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.9.10-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%

echo Using Java 21 for this project:
java -version
echo.
echo Building project...
mvn clean install

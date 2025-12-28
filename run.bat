@echo off
REM Set Java 21 for this project only
set JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.9.10-hotspot
set PATH=%JAVA_HOME%\bin;%PATH%

echo Using Java 21 for this project:
java -version
echo.

REM Run the command passed as argument, or default to spring-boot:run
if "%1"=="" (
    echo Starting Spring Boot application...
    mvn spring-boot:run
) else (
    mvn %*
)

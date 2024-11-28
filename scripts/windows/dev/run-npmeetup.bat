@echo off
set JAVA_HOME=C:\Program Files\Java\jdk-22
set PATH=%JAVA_HOME%\bin;%PATH%
set SPRING_PROFILES_ACTIVE=dev

java -version
echo.
echo EXECUTING JAVA JAR...
echo.
cd ../../../
java -jar target/national-park-meetup-1.0.0.jar
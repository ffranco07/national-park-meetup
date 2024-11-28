@echo off
set MAVEN_HOME=C:\Users\franc\.m2\wrapper\dists\apache-maven-3.9.9
set JAVA_HOME=C:\Program Files\Java\jdk-22
set PATH=%MAVEN_HOME%\bin;%JAVA_HOME%\bin;%PATH%
set SPRING_PROFILES_ACTIVE=dev

java -version
echo.
echo EXECUTING MVN PACKAGE...
echo.
cd ../../../

REM Skip integration tests
REM mvn package -Pdev -DskipITs
mvn package -DskipTests

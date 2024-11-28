@echo off
set MAVEN_HOME=C:\Users\franc\.m2\wrapper\dists\apache-maven-3.9.9
set JAVA_HOME=C:\Program Files\Java\jdk-22
set PATH=%MAVEN_HOME%\bin;%JAVA_HOME%\bin;%PATH%
set SPRING_PROFILES_ACTIVE=dev

java -version
echo.
echo EXECUTING MVN TEST ...
echo.
cd ../../../

echo.
echo Removing H2 database data/db/npmeetup_db.mv.db ...
echo.
rm data/db/npmeetup_db.mv.db

REM echo Running NPMeetupIntegrationTest...
REM mvn -Dtest=NPMeetupIntegrationTest test

echo.
echo Running National Park Meetup Integration and JUnit Tests ...
echo.
mvn test -Dtest=NPMeetupIntegrationTest,UserServiceFunctionalTest,LocationServiceFunctionalTest,ProximityServiceFunctionalTest,UserServiceImplTest,LocationServiceImplTest,ProximityServiceImplTest

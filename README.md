# National Park Meetup Application

## Problem Statement

Design an application which tracks mobile device users as they move through 
a set of known locations, for example, all National Parks.

This data would be sent to your application in near real-time with a user 
and location tuple.

The level of design should be sufficient for passing off to a more junior 
engineer for implementation.

Application coded should address following requirements:

1) Where is the user currently? 
2) What other users are nearby?

## Technolgy Stack Used For Implementation

The **national-park-meetup** project was made using **Spring Boot 3.3.3**, 
**Spring Security 6.3.3**, **Spring Data JPA 3.3.3**, **Spring Data REST 4.3.3**, 
**Spring GraphQL 1.3.2** and **Docker 26.1.4**. 

Spring Security leverages JSON Web Tokens (**JWTs 0.11.5**) to 
securly access REST API endpoints.

The database used is **H2**. It uses a persistant file database located 
at **data/db/npmeetup_db.mv.db**

## Configuration

### Configuration Files

The folder **src/resources/** contains configuration files for the 
**national-park-meetup** application.  Some of these files are:

* **src/resources/application.properties** - Main configuration file. 
It contains application properties to change admin username/password, 
host server port number, H2 data source properties, Spring Data JPA properties, 
Spring Security properties, and application logging levels.

* **src/resources/schema.sql** - H2 schema file. 
It defines table defintions and SQL logic to initialize the H2 database upon 
application startup.

* **src/resources/logback.xml** - Logger configuration file. 
It defines several application console and file loggers that log to following 
locations: **log/app.log**, **log/error.log**, **log/message.log**, 
**log/service.log**, and **log/test.log**.

* **src/resources/graphql/schema.graphqls** - GraphQL schema file.
It defines object types, queries, and mutations to communicate with backend 
GraphQL API endpoints.  The use of GraphQL was introduced in the project to 
handle batch and bulk reads/writes of application data.

* **src/resources/xlsx/Users.xlsx - Excel file. 
This file is used to initialize **users** table in H2 database upon inital 
application startup only.

* **src/resources/xlsx/National_Parks.xlsx - Excel file. This file is used 
to initialize **locations** table in H2 database upon inital application startup only.

Both of these spreadsheets are also used to randomly set **user_location** table data 
upon initial application startup only.

## How to run

There are several ways to run the application. You can run it from the command 
line with included Maven Wrapper, Maven, Docker, or IDE (Ex. Visual Studio Code). 
On Windows, Maven is typically installed in ${user.home}/.m2/wrapper/dists folder.

### Maven Wrapper

#### Using the Maven Plugin

Go to the root folder of the application and type:
```bash
$ chmod +x scripts/mvnw
$ scripts/mvnw spring-boot:run

``
#### Using Windows batch scripts

Or you can build the JAR (with embedded Tomcat server) with 
```bash
$ cd scripts/windows/dev
$ clean-npmeetup.bat

$ cd scripts/windows/dev
$ package-npmeetup.bat

$ cd scripts/windows/dev
$ install-npmeetup.bat

$ cd scripts/windows/dev
$ run-npmeetup.bat

```

#### Using Executable Jar

Also, you can manually run the JAR file
```bash
$ java -jar target/national-park-meetup-1.0.0.jar

```

### Maven

Open a terminal and run the following commands to ensure that you have valid 
versions of Java JDK 22 and Maven 3.9.9 installed:

```bash
$ java version "22.0.2" 2024-07-16
Java(TM) SE Runtime Environment (build 22.0.2+9-70)
Java HotSpot(TM) 64-Bit Server VM (build 22.0.2+9-70, mixed mode, sharing)

```

```bash
$ mvn -version
Apache Maven 3.9.9 (8e8579a9e76f7d015ee5ec7bfcdc97d260186937)
Maven home: /usr/local/Cellar/maven/3.9.9/libexec

```

#### Using the Maven Plugin

The Spring Boot Maven plugin includes a run goal that can be used 
to quickly compile and run your application. 
Applications run in an exploded form, as they do in your IDE. The 
following example shows a typical Maven command to run a Spring Boot application:
 
```bash
$ mvn spring-boot:run
``` 

#### Using Executable Jar

To create an executable jar run:

```bash
$ mvn clean package
``` 

To run that application, use the java -jar command, as follows:

```bash
$ java -jar target/national-park-meetup-1.0.0.jar
```

To exit the application, press **ctrl-c**.

### Docker

It is possible to run **national-park-meetup** using Docker:

Build Docker image:
```bash
$ mvn clean package
$ docker build -t national-park-meetup:dev -f docker/Dockerfile .
```

Run Docker container:
```bash
$ docker run --rm -i -p 8070:8070 \
      --name national-park-meetup \
      national-park-meetup:dev
```

##### Helper script

It is possible to run all of the above with helper script:

```bash
$ chmod +x scripts/run_docker.sh
$ scripts/run_docker.sh
```

## Docker 

Folder **docker** contains:

* **docker/national-park-meetup/Dockerfile** - Docker build file for 
executing national-park-meetup Docker image. 
Instructions to build artifacts, copy build artifacts to docker image 
and then run app on proper port with proper configuration file.

## Util Scripts

* **scripts/run_docker.sh.sh** - util script for running national-park-meetup 
Docker container using **docker/Dockerfile**

## Tests

Tests can be run by executing following command from the root of the project:

```bash
$ cd scripts/windows/dev
$ test-npmeetup.bat

```
Please note that the H2 database located at data/db/npmeetup_db will be deleted 
and then recreated within test-npmeetup.bat script to ensure successful execution 
of functional and unit tests.

## Swagger API Docs

Swagger REST API documentaion is available at `http://localhost:8070/docs/index.html`

## Helper Tools

### REST scripts

REST scripts can be executed manually to invoke API endpoints. Each script first 
calls the authentication service to generate access tokens, which are then 
included in the curl command headers.

* **scripts/windows/dev/REST/UserService/GetUser/run.sh** - Invoke getUser 
REST endpoint with mobile number path parameter to get user details.

* **scripts/windows/dev/REST/UserService/GetAllUsers/run.sh** - Invoke getAllUsers 
REST endpoint to get all user details.

* **scripts/windows/dev/REST/UserService/CreateUser/run.sh** - Invoke createUser
REST endpoint with User json request payload to create a user.

* **scripts/windows/dev/REST/UserService/UpdateUser/run.sh** - Invoke updateUser
REST endpoint with mobile number path parameter and User json request payload 
to update a user.

* **scripts/windows/dev/REST/UserService/DeleteUser/run.sh** - Invoke deleteUser
REST endpoint with mobile nubmer path parameter to delete a user.

* **scripts/windows/dev/REST/LocationService/GetCurrentUserLocation/run.sh** - Invoke
getCurrentUserLocation REST endpoint with mobile number request parameter to find 
a current user's location at a national park.

* **scripts/windows/dev/REST/ProximityService/GetNearbyUsers/run.sh** - Invoke
getNearbyUsers REST endpoint with mobile number request parameter and 
radius (in kilometers) to return all nearby user locations within radius distance
of target mobile number user location.

### GraphQL scripts

GraphQL scripts can be executed manually to invoke API endpoints. Each script 
first calls the authentication service to generate access tokens, which are 
then included in the curl command headers.

* **scripts/windows/dev/GraphQL/User/Query/GetUserByMobileNumber/run.sh** - Invoke
User resolver getUserByMobileNumber GraphQL endpoint with mobile number argument 
to get user details.

* **scripts/windows/dev/GraphQL/User/Mutation/AddUser/run.sh** - Invoke
User resolver addUser GraphQL endpoint with arguments to add a new user.

* **scripts/windows/dev/GraphQL/User/Mutation/RemoveUser/run.sh** - Invoke
User resolver removeUser GraphQL endpoint with mobile number argument to remove a new user.

### HAL REST Browser

Go to the web browser and visit `http://localhost:8070/h2-console`

In field **JDBC URL** input 
```
jdbc:h2:file:./data/db/npmeetup_db

```
Default username is sa and leave password blank to connect. 

In `/src/main/resources/application.properties` file it is possible to change both
web interface url path, h2-console autentication credentials, as well as the datasource url.
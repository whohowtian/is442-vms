<h1 align="center">Vendor Management Application</h1>
<p align="center">A Vendor Management Application Project for Quantum Leap Corporation to help streamline the new vendor application workflow.</p>

## Prerequistes
Please ensure the following is downloaded before running the application.
- Install [NodeJS v18.0](https://nodejs.org/en/) or later
- Install [JDK 17](https://www.oracle.com/java/technologies/downloads/) or later
- Install [MongoDB Compass](https://www.mongodb.com/products/compass)
- Install [Java Extension Pack](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack) on Visual Studio Code

## Steps
### Connect to database
Open MongoDB Compass and input the following under the URI field. Click Connect.
	mongodb+srv://vmsStringbootUsr:XE3HfPeVUMPIqoor@cluster0.ldxjqem.mongodb.net/test?authMechanism=SCRAM-SHA-1

### Run backend server and Vue application
To run the entire application, do the following:
1. From the root folder, cd into src folder.
2. Run the applications with the following commands on terminal:
```
build.bat
```

To run the application separately, do the following:
1. From the root folder, cd into src/backend folder.
```
mvnw spring-boot:run
```
2. Open a new terminal, cd into src/frontend folder.
```
npm install
npm run dev
```

## Functionalities
### Employee
- 
### Vendor

## Frameowrk & Libraries
### Backend
- MongoDB
- Java with Spring Boot
### Frontend
- Vue


## 3. User Workflow

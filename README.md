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
```
mongodb+srv://vmsStringbootUsr:XE3HfPeVUMPIqoor@cluster0.ldxjqem.mongodb.net/test?authMechanism=SCRAM-SHA-1
```

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

### Access
1. [Backend](http://localhost:8080/)
2. [Frontend](http://localhost:5173/)
3. [API Documentation](http://localhost:8080/swagger-ui/index.html)


## Functionalities
### All Users
- Login and logout
- Set and reset password

### Vendor
- View my account: View my account
- View and edit forms assigned to me
- Check status of forms
- Download approved forms as PDFs:  Download a single form, download all approved forms as a ZIP file

### Admin
- View all accounts:  CRUD functions for any accounts, able to change user type
- Create customisable Form Template: CRUD functions for form templates
- Assign a Workflow: Start a new workflow by assigning form to vendor or admin, archive workflows
- Download approved forms as PDFs: Download a single form, download multiple forms from the same vendor as a ZIP file
- Send email reminder: Send an email reminder manually to any vendor when form submission deadline is near

### Approver
- Approve and Reject Workflow: Approve workflow and save form in database automatically, reject workflow and include reason for rejection
- Download approved forms as PDFs:  Download a single form, download multiple forms from the same vendor as a ZIP file


## Framework & Libraries
### Backend
- MongoDB
- Java with Spring Boot
### Frontend
- Vue

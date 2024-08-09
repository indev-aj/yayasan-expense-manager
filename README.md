# Yayasan Expense Manager

## Prerequisites

1. **Download and install NVM for Windows**
   - Get the latest release from the [NVM for Windows releases page](https://github.com/coreybutler/nvm-windows/releases/tag/1.1.12).

2. **Install Node.js version 20**
   - Open your terminal and run:
     ```sh
     nvm install 20
     ```

3. **Set Node.js version 20 as default**
   - Run:
     ```sh
     nvm use 20
     ```

## Getting Started

1. **Clone the repository**
   - Run:
     ```sh
     git clone https://github.com/indev-aj/yayasan-expense-manager.git
     ```

2. **Install dependencies**
   - Navigate to the project directory:
     ```sh
     cd yayasan-expense-manager
     ```
   - Install the required packages:
     ```sh
     npm install
     ```

3. **Start the development server**
   - Run:
     ```sh
     npm run dev
     ```

4. **Open the API/expense-manager folder in IntelliJ**
   - Use IntelliJ to open the `api/expense-manager` folder for further development.
  
# Database Setup Guide

## Database Setup (IntelliJ)

1. **Download Oracle Database 21c Express Edition**
   - Download from [Oracle's official website](https://www.oracle.com/database/technologies/xe-downloads.html).
   - Install the database and take a screenshot of the last page of the installation wizard, as it provides the connection details.

2. **Update Maven Configuration**
   - Add the following dependency to your `pom.xml` file:
     ```xml
     <dependency>
         <groupId>com.oracle.database.jdbc</groupId>
         <artifactId>ojdbc8</artifactId>
         <version>19.8.0.0</version>
     </dependency>
     ```

3. **Configure `application.properties`**
   - Update your `application.properties` file with the following settings:
     ```
     spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/XEPDB1
     spring.datasource.username=yayasanpeneraju
     spring.datasource.password=my_password
     spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
     
     spring.jpa.database-platform=org.hibernate.dialect.OracleDialect
     spring.jpa.hibernate.ddl-auto=update
     ```

4. **Run Maven Commands**
   - Execute the following commands in your terminal:
     ```sh
     mvn clean install
     mvn spring-boot:run
     ```

## Database Setup (Oracle)

1. **Use a Database Management Tool**
   - Download and install a database management tool such as [DBeaver Community Edition](https://dbeaver.io/download/).

2. **Create a New Database Connection**
   - In your database management tool, select `Database > New Database Connection > Oracle`.
   - Connect to the default database. Note that the database name might differ based on your installation.

3. **Create Schema and User**
   - Run the following script to create a schema and user `yayasanpeneraju`:
     ```sql
     CREATE USER yayasanpeneraju IDENTIFIED BY 1234
     DEFAULT TABLESPACE users
     TEMPORARY TABLESPACE temp
     QUOTA UNLIMITED ON users;
     ```

4. **Connect to the New User**
   - Use the following credentials to connect:
     - **Username:** yayasanpeneraju
     - **Password:** 1234

5. **Grant Privileges and Create Table**
   - Run the following script to grant privileges and create a table:
     ```sql
     GRANT DBA TO yayasanpeneraju;
     GRANT CONNECT, RESOURCE TO yayasanpeneraju;

     CREATE TABLE ADM_USER (
         id NUMBER GENERATED BY DEFAULT AS IDENTITY,
         name VARCHAR2(100),
         username VARCHAR2(100) UNIQUE,
         password VARCHAR2(100),
         PRIMARY KEY (id)
     );

     INSERT INTO ADM_USER (name, username, password) VALUES ('user1', 'user1', 'password1');
     INSERT INTO ADM_USER (name, username, password) VALUES ('user2', 'user2', 'password2');
     INSERT INTO ADM_USER (name, username, password) VALUES ('user3', 'user3', 'password3');
     ```

6. **Verify the Data**
   - In your database management tool, navigate to `Schemas > YAYASANPENERAJU > TABLE > ADM_USER`.
   - Verify that the table `ADM_USER` contains the three inserted records.

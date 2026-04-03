==========================================
           NET BANKING APPLICATION
==========================================

-----------------------
Project Overview
-----------------------
This Java-based Net Banking Application demonstrates a complete 
real-time banking system using:

- Object-Oriented Programming (OOP)
- Networking (Client–Server using Sockets)
- JDBC (MySQL Database Connectivity)
- Swing (Graphical User Interface)

The application allows users to safely perform:

- Check Balance
- Deposit Amount
- Withdraw Amount

Each operation is validated using a secure Account Number + Account Holder Name
verification to ensure safe banking access.

The architecture follows a client–server model where:
- The server handles all business logic and database operations.
- The client provides a user-friendly Swing-based interface.

-------------------------
Key Features
-------------------------

✔ Account Validation  
   - Validates both account number and account holder name before processing requests  
   - Prevents unauthorized access  

✔ Real-Time Banking Operations  
   - Deposit, withdraw, and balance check with live MySQL updates  

✔ Client–Server Architecture  
   - Server runs continuously and handles multiple clients (multithreading enabled)  
   - Client communicates via TCP sockets  

✔ Secure JDBC Operations  
   - Uses PreparedStatement to prevent SQL injection and ensure safe database operations  

✔ Error Handling  
   - Invalid account number or name mismatch  
   - Insufficient balance  
   - Network errors  
   - Invalid operations  

✔ GUI (Swing)  
   - Clean and simple interface  
   - Responsive design  
   - Displays real-time transaction results  

--------------------------
Learning Outcomes
--------------------------

By completing this project, you will learn:

- Client–server application development using Java Socket Programming  
- Integration of Java applications with MySQL using JDBC  
- Secure database handling using PreparedStatement  
- GUI development using Java Swing components  
- Application of OOP concepts like abstraction and encapsulation  
- Handling multiple clients using multithreading  

---------------------------
Technologies Used
---------------------------

1. Core Java (OOP)  
   - Banking logic implemented in BankService.java  
   - Modular and structured code design  

2. Java Networking (TCP Sockets)  
   - Communication between BankClient.java and BankServer.java  
   - Uses DataInputStream and DataOutputStream  

3. JDBC (Database Connectivity)  
   - DBConnection.java manages database connection  
   - CRUD operations handled in BankService.java  

4. MySQL Database  
   - Stores account details securely  
   - Ensures account validation  

5. Java Swing (Frontend GUI)  
   - Built using JFrame, JButton, JTextField, JTextArea, JScrollPane  
   - Provides interactive user interface  

----------------------------
Architecture
----------------------------

   Client (Swing UI)  <--------->  Server (Java + JDBC)  <--------->  MySQL Database
            TCP Socket Communication              SQL Queries


-----------------------------
Project Structure
-----------------------------

NetBankingApplication
│
├── lib
│   └── mysql-connector-java-8.0.20.jar
│
├── src
│   ├── DBConnection.java        (Database connection)
│   ├── BankService.java         (Business logic)
│   ├── BankServer.java          (Server-side logic)
│   └── BankClient.java          (Client GUI)
│
└── README.md

------------------------------------------
Database Setup 
------------------------------------------

Create database:

CREATE DATABASE netbank;

USE netbank;

Create table:

CREATE TABLE accounts (
    acc_no INT PRIMARY KEY,
    name   VARCHAR(50),
    balance DOUBLE
);

Insert sample values:

INSERT INTO accounts VALUES 
(1001, 'Amit', 5000),
(1002, 'Sneha', 7000),
(1003, 'Rahul', 12000);

------------------------------------------
How to Compile and Run
------------------------------------------

Step 1 — Open Command Prompt  
Navigate to project folder:

cd D:\NetBankingApp\src

Step 2 — Compile the project:

javac -cp .;../lib/mysql-connector-java-8.0.20.jar *.java

Step 3 — Run the Server:

java -cp .;../lib/mysql-connector-java-8.0.20.jar BankServer  

(Keep this running)

Step 4 — Run the Client (new terminal):

java -cp .;../lib/mysql-connector-java-8.0.20.jar BankClient

------------------------------------------
Important Notes
------------------------------------------

- Run BankServer BEFORE BankClient  
- Ensure MySQL service is running  
- Account name must match the account number  
- Verify database credentials in DBConnection.java  
- Close both server and client to stop execution  

------------------------------------------
End of Documentation
------------------------------------------
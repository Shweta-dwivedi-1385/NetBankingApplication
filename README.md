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

Each operation is validated with a secure *Account Number + Account Holder Name*
verification to ensure safe banking access.

The architecture follows a client–server model where:
- The *server* manages all business logic and database operations.
- The *client* provides a user-friendly Swing-based interface.

-------------------------
Key Features
-------------------------

✔ *Account Validation*  
   - Validates account number AND account holder name before all operations.  
   - Blocks unauthorized access instantly.

✔ *Real-Time Banking Operations*  
   - Deposit, withdraw, and check balance operate through live MySQL updates.

✔ *Client–Server Architecture*  
   - Server runs continuously and handles multiple clients (multithreading enabled).  
   - Client connects using TCP sockets.

✔ *Secure JDBC Operations*  
   - Uses PreparedStatement to prevent unwanted chnanes or misuse of the database.

✔ *Error Handling*  
   - Wrong account number / name mismatch  
   - Network errors  
   - Insufficient balance  
   - Invalid commands  

✔ *Modern GUI (Swing)*  
   - Clean interface  
   - Responsive design  
   - Real-time output updates  

--------------------------
Learning Outcomes
--------------------------

By completing this project, you will learn to:

- Build client–server applications using *Java Socket Programming*
- Connect Java applications to MySQL using *JDBC*
- Use *PreparedStatement* for secure database operations
- Develop GUI applications using *Swing components*
- Apply OOP concepts like abstraction, encapsulation, and modular design
- Handle multithreaded server requests

---------------------------
Technologies Used
---------------------------

1. Core Java (OOP)
   - Banking logic implemented in BankService.java
   - Encapsulation & abstraction used throughout codebase

2. Java Networking (TCP Sockets)
   - BankClient.java ↔ BankServer.java
   - DataInputStream & DataOutputStream for real-time messaging

3. JDBC (Database Connectivity)
   - DBConnection.java connects Java with MySQL
   - CRUD operations performed in BankService.java

4. MySQL Database
   - Stores account details securely
   - Validates account holder name & account number

5. Java Swing (Frontend GUI)
   - Built using JFrame, JButton, JTextField, JTextArea, JScrollPane
   - Provides intuitive user interface

----------------------------
Architecture
----------------------------

   ┌─────────────────────┐     TCP      ┌──────────────────────┐     SQL     ┌───────────────┐
   │   Java Swing Client        │ <---------> │      Java Server            │ <---------->|     MySQL		    |
   │   (BankClient.java)        │             │   (BankServer + JDBC)       │             | (accounts table)   |
   └─────────────────────┘              └──────────────────────┘             └───────────────┘


-----------------------------
Project Structure
-----------------------------

D:\NetBankingApp
│
├── lib\
│   └── mysql-connector-java-8.0.20.jar
│
├── src\
│   ├── DBConnection.java        ← Connects to MySQL
│   ├── BankService.java         ← Validates account, deposit, withdraw
│   ├── BankServer.java          ← Handles client requests
│   └── BankClient.java          ← Swing GUI client
│
└── README.txt

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

- *Run BankServer BEFORE BankClient*
- Ensure MySQL service is running
- Account name MUST match the account number
- Database credentials inside DBConnection.java must be correct
- Close both server & client windows to stop operations

------------------------------------------
End of Documentation
------------------------------------------
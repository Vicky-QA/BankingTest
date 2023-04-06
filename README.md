**#Banking System**

**Overview**

The Banking System is an API for the Accounts module that enables users to carry out all operations, including account creation, account deletion, money withdrawals, and money deposits.

**Tech Stack Used:**

    Java8, Maven, RestClient,, JUnit5
    Mockserver library for faking server
    Used JUnit5 Assertions in test cases
    

**How to run:**

    Clone the repository
    Open project in IntelliJ
    Restore packages
    Build project
    Run BankingTest class for the result
    
    
**Project structure:**  

		src/main/java - contains the entity class and exception class
		src/test/java - contains the automation test script.
		

**Test cases covered:**

1. A user can have as many accounts as they want.
2. A user can create and delete accounts.
3. A user can deposit and withdraw from accounts.
4. An account cannot have less than $100 at any time in an account.
5. A user cannot withdraw more than 90% of their total balance from an account in a single
transaction.
6. A user cannot deposit more than $10,000 in a single transaction.


**Dummy API's for the above test cases:**

#1. POST (Creates a new account): localhost:8080/banking/account  (Request Payload is required)

#2. PUT (Updates an existing account): localhost:8080/banking/account (Request Payload is required) 

#3. DELETE (Deletes an account): localhost:8080/banking/account?accountNum={27119728627}

#4. GET (Get all customers account): localhost:8080/banking/all-cust-accts

#5. GET (Get all accounts of one particular customer): localhost:8080/banking/cust-accounts?customerName={Vikram}

#6. GET (Get account of a particular account): localhost:8080/banking/account?accountNum={27119728627}

#7. PUT (Withdraw amount from the account): localhost:8080/banking/withdraw?withdrawAmount=10&accountNum=91603512227

#8. PUT (Deposit amount to the account): localhost:8080/banking/deposit?depositAmount=100&accountNum=91603512227

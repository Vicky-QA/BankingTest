#Banking System

Overview

The Banking System is an API for the Accounts module that enables users to carry out all operations, including account creation, account deletion, money withdrawals, and money deposits.

Tech Stack Used

    Java8, Maven, RestClient,, JUnit5
    Mockserver library for faking server
    Used JUnit5 Assertions in test cases

How to run

    Clone the repository
    Open project in IntelliJ
    Restore packages
    Build project
    Run BankingTest class for the result

Test cases covered:
1. A user can have as many accounts as they want.
2. A user can create and delete accounts.
3. A user can deposit and withdraw from accounts.
4. An account cannot have less than $100 at any time in an account.
5. A user cannot withdraw more than 90% of their total balance from an account in a single
transaction.
6. A user cannot deposit more than $10,000 in a single transaction.

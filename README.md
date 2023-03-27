#API

#1. POST (Creates a new account): localhost:8080/banking/account
  Payload:
  {
    "custName":"Vikram",
    "dob":"1990-09-19",
    "email":"samplemail1090@gmail.com",
    "accounts":[
        {
            "balanceAmt":"1000.00"
        }
    ]
}

#2. PUT (Updates an existing account): localhost:8080/banking/account
  Payload:
  {
    "custName":"Vikram",
    "dob":"1990-09-19",
    "email":"sample0821@gmail.com",
    "accounts":[
        {
            "balanceAmt":"2000.00"
        }
    ]
}

#3. DELETE (Deletes an account): localhost:8080/banking/account?accountNum={27119728627}

#4. GET (Get all customers account): localhost:8080/banking/all-cust-accts

#5. GET (Get all accounts of one particular customer): localhost:8080/banking/cust-accounts?customerName={Vikram}

#6. GET (Get account of a particular account): localhost:8080/banking/account?accountNum={27119728627}

#7. PUT (Withdraw amount from the account): localhost:8080/banking/withdraw?withdrawAmount=10&accountNum=91603512227

#8. PUT (Deposit amount to the account): localhost:8080/banking/deposit?depositAmount=100&accountNum=91603512227

#Swagger Ui url:
http://localhost:8080/swagger-ui/index.html
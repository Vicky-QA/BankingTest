package com.banking.controller;

import com.banking.entity.Account;
import com.banking.entity.Customer;
import com.banking.exception.BadRequestException;
import com.banking.repository.AccountRepository;
import com.banking.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/banking")
public class BankingController {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private AccountRepository accountRepo;

    private static double depositLimit = 10000.0d;
    private static double minBalInAcc = 100.0d;

    @GetMapping("/account")
    public ResponseEntity<Account> getCustomer(@RequestParam("accountNum") Long account) {
        Account response = accountRepo.findByAccountNum(account);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all-cust-accts")
    public ResponseEntity<List<Customer>> getAllCustomerAccts() {
        List<Customer> custAccts = customerRepo.findAll();
        return ResponseEntity.ok(custAccts);
    }

    @GetMapping("/cust-accounts")
    public ResponseEntity<List<Customer>> getCustomerAcct(@RequestParam("customerName") String customerName) {
        List<Customer> custAccts = customerRepo.findByCustName(customerName);
        return ResponseEntity.ok(custAccts);
    }

    @PostMapping("/account")
    public ResponseEntity<Customer> createCustAcct(@RequestBody @Validated Customer customer) throws BadRequestException {
        if(depositLimit < customer.getAccounts().get(0).getBalanceAmt()) {
            throw new BadRequestException("Deposit amount should be less than $10000.0 per transaction.", HttpStatus.BAD_REQUEST);
        }
        customer.getAccounts().get(0).setAccountNum(generateUUIDNo());
        Customer response = customerRepo.save(customer);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/withdraw")
    public ResponseEntity<Account> custAcctWithdraw(@RequestParam("withdrawAmount") double withdrawAmount, @RequestParam("accountNum") Long accountNum) throws BadRequestException {
        Account acct = accountRepo.findByAccountNum(accountNum);
        if(minBalInAcc > (acct.getBalanceAmt() - withdrawAmount) ) {
            throw new BadRequestException("Account balance should not be less than $100.0. Please withdraw lesser amount.", HttpStatus.BAD_REQUEST);
        }
        if(withdrawAmount > (acct.getBalanceAmt() * 90/100) ) {
            throw new BadRequestException("Cannot withdraw more than 90% of balance amount from the account. Please withdraw lesser amount.", HttpStatus.BAD_REQUEST);
        }
        acct.setBalanceAmt(acct.getBalanceAmt() - withdrawAmount);
        Account response = accountRepo.save(acct);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/deposit")
    public ResponseEntity<Account> custAcctDeposit(@RequestParam("depositAmount") double depositAmount, @RequestParam("accountNum") Long accountNum) throws BadRequestException {
        Account acct = accountRepo.findByAccountNum(accountNum);
        if(depositLimit < depositAmount) {
            throw new BadRequestException("Deposit amount should be less than $10000.0 per transaction.", HttpStatus.BAD_REQUEST);
        }
        acct.setBalanceAmt(acct.getBalanceAmt() + depositAmount);
        Account response = accountRepo.save(acct);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/account")
    public ResponseEntity<Customer> updateCustAcct(@RequestBody @Validated Customer customer) {
        Customer response = customerRepo.save(customer);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/account")
    public ResponseEntity<Account> deleteCustAcct(@RequestParam("accountNum") Long accountNum) {
        Account delAcct = null;
        List<Customer> customerList = customerRepo.findAll();
        for (Customer cust : customerList) {
            for(Account acct : cust.getAccounts()) {
                if(accountNum.equals(acct.getAccountNum())) {
                    accountRepo.delete(acct);
                    cust.setAccounts(null);
                    customerRepo.saveAll(customerList);
                    return ResponseEntity.ok(acct);
                }
            }
        }
        return ResponseEntity.internalServerError().build();
    }

    public Long generateUUIDNo() {
        return ThreadLocalRandom.current().nextLong(10000000000L, 99999999999L);
    }
}

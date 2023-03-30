package com.banking.controller;

import com.banking.entity.Account;
import com.banking.entity.Customer;
import com.banking.repository.AccountRepository;
import com.banking.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

@ExtendWith(MockitoExtension.class)
@RestClientTest({CustomerRepository.class, AccountRepository.class})

public class BankingTest {
    @Autowired
    private MockRestServiceServer mockServer;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private AccountRepository accountRepo;

    private static final String baseUrl = "http://localhost:8080/banking";

    @Test
    public void depositAmountTest() {
        Account acc = new Account();
        acc.setAccountNum(1010101010L);
        acc.setBalanceAmt(1000.0d);
        acc.setAcctId(2L);
        this.mockServer.expect(requestTo(baseUrl + "/deposit"))
                .andRespond(withSuccess((Resource) acc, MediaType.APPLICATION_JSON));

        Account res = accountRepo.save(acc);
        assertEquals(acc.getBalanceAmt(), res.getBalanceAmt());
        assertEquals(acc.getAcctId(), res.getAcctId());
    }

    @Test
    public void depositAmountLimitExceptionTest() {
        Account acc = new Account();
        acc.setAccountNum(1010101010L);
        acc.setBalanceAmt(1000.0d);
        acc.setAcctId(2L);
        this.mockServer.expect(requestTo(baseUrl + "/deposit"))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void withdrawAmountTest() {
        Account acc = new Account();
        acc.setAccountNum(1010101010L);
        acc.setBalanceAmt(1000.0d);
        acc.setAcctId(2L);
        this.mockServer.expect(requestTo(baseUrl + "/withdraw"))
                .andRespond(withSuccess((Resource) acc, MediaType.APPLICATION_JSON));

        Account res = accountRepo.save(acc);
        assertEquals(acc.getBalanceAmt() - 200, res.getBalanceAmt());
        assertEquals(acc.getAcctId(), res.getAcctId());
    }

    @Test
    public void withdrawAmountLimitExceptionTest() {
        Account acc = new Account();
        acc.setAccountNum(1010101010L);
        acc.setBalanceAmt(1000.0d);
        acc.setAcctId(2L);
        this.mockServer.expect(requestTo(baseUrl + "/withdraw"))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void withdrawAmountMoreThan90PercentExceptionTest() {
        Account acc = new Account();
        acc.setAccountNum(1010101010L);
        acc.setBalanceAmt(1000.0d);
        acc.setAcctId(2L);
        this.mockServer.expect(requestTo(baseUrl + "/withdraw"))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void deleteAccountTest() {
        Account acc = new Account();
        acc.setAccountNum(1010101010L);
        acc.setBalanceAmt(1000.0d);
        acc.setAcctId(2L);
        Customer cus = new Customer();
        cus.setAccounts(Collections.singletonList(acc));
        cus.setCustName("Vikram");
        this.mockServer.expect(requestTo(baseUrl + "/account"))
                .andRespond(withStatus(HttpStatus.OK));

        List<Customer> customerList = customerRepo.findAll();
        accountRepo.delete(acc);
        customerRepo.saveAll(customerList);

        assertEquals(2L, customerList.get(0).getAccounts().get(0).getAcctId());
        assertEquals(1000.0d, customerList.get(0).getAccounts().get(0).getBalanceAmt());
        assertEquals(10101010101L, customerList.get(0).getCustName());
    }

    @Test
    public void deleteAccountInternalServerExceptionTest() {
        Account acc = new Account();
        acc.setAccountNum(1010101010L);
        acc.setBalanceAmt(1000.0d);
        acc.setAcctId(2L);
        Customer cus = new Customer();
        cus.setAccounts(Collections.singletonList(acc));
        cus.setCustName("Vikram");
        this.mockServer.expect(requestTo(baseUrl + "/account"))
                .andRespond(withStatus(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void createAccountTest() {
        Account acc = new Account();
        acc.setAccountNum(1010101010L);
        acc.setBalanceAmt(1000.0d);
        acc.setAcctId(2L);
        Customer cus = new Customer();
        cus.setAccounts(Collections.singletonList(acc));
        cus.setCustName("Vikram");

        this.mockServer.expect(requestTo(baseUrl + "/account"))
                .andRespond(withStatus(HttpStatus.OK));
        Customer res = customerRepo.save(cus);

        assertEquals("Vikram", res.getCustName());
        assertEquals(1000.0d, res.getAccounts().get(0).getBalanceAmt());
        assertEquals(2L, res.getAccounts().get(0).getAcctId());
    }

    @Test
    public void createAccountDepositAmtLimitExceptionTest() {
        Account acc = new Account();
        acc.setAccountNum(1010101010L);
        acc.setBalanceAmt(10050.0d);
        acc.setAcctId(2L);
        Customer cus = new Customer();
        cus.setAccounts(Collections.singletonList(acc));
        cus.setCustName("Vikram");

        this.mockServer.expect(requestTo(baseUrl + "/account"))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));
        Customer response = customerRepo.save(cus);
        assertEquals("Vikram", response.getCustName());
        assertEquals(1010101010L, response.getAccounts().get(0).getBalanceAmt());
    }

    @Test
    public void getAllAccountsTest() {
        Account acc = new Account();
        acc.setAccountNum(1010101010L);
        acc.setBalanceAmt(1000.0d);
        acc.setAcctId(2L);
        Customer cus = new Customer();
        cus.setAccounts(Collections.singletonList(acc));
        cus.setCustName("Vikram");

        Account acc1 = new Account();
        acc1.setAccountNum(2020202020L);
        acc1.setBalanceAmt(2000.0d);
        acc1.setAcctId(5L);
        Customer cus1 = new Customer();
        cus1.setAccounts(Collections.singletonList(acc1));
        cus1.setCustName("Prasad");

        this.mockServer.expect(requestTo(baseUrl + "/all-cust-accts"))
                .andRespond(withStatus(HttpStatus.BAD_REQUEST));
        List<Customer> res = customerRepo.findAll();

        assertEquals("Vikram", res.get(0).getCustName());
        assertEquals(1000.0d, res.get(0).getAccounts().get(0).getBalanceAmt());
        assertEquals(2L, res.get(0).getAccounts().get(0).getAcctId());
        assertEquals(1010101010L, res.get(0).getAccounts().get(0).getAccountNum());

        assertEquals("Prasad", res.get(2).getCustName());
        assertEquals(2000.0d, res.get(2).getAccounts().get(0).getBalanceAmt());
        assertEquals(5L, res.get(2).getAccounts().get(0).getAcctId());
        assertEquals(2020202020L, res.get(2).getAccounts().get(0).getAccountNum());
    }

}

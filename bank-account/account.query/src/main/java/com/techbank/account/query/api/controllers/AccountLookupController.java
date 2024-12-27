package com.techbank.account.query.api.controllers;

import com.techbank.account.query.api.dto.AccountLookupResponse;
import com.techbank.account.query.api.queries.*;
import com.techbank.account.query.domain.BankAccount;
import com.techbank.cqrs.core.infrastructure.QueryDispacther;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/bankAccountLookup")
public class AccountLookupController {

    private final QueryDispacther queryDispacther;
    private static final Logger logger = Logger.getLogger(AccountLookupController.class.getName());

    @Autowired
    public AccountLookupController(QueryDispacther queryDispacther) {
        this.queryDispacther = queryDispacther;
    }

    @GetMapping
    public ResponseEntity<AccountLookupResponse> getAllAccounts() {
        try {
            List<BankAccount> bankAccounts = queryDispacther.send(new FindAllAccountsQuery());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new AccountLookupResponse("Found %d accounts".formatted(bankAccounts.size()), bankAccounts));
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AccountLookupResponse("Bank accounts not found for reason: " + e.getMessage()));
        }
    }

    @GetMapping(path = "by-id/{id}")
    public ResponseEntity<AccountLookupResponse> getById(@PathVariable(value = "id") String id) {
        try {
            List<BankAccount> bankAccounts = queryDispacther.send(new FindAccountByIdQuery(id));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new AccountLookupResponse("Found %d accounts".formatted(bankAccounts.size()), bankAccounts));
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AccountLookupResponse("Bank accounts not found for reason: " + e.getMessage()));
        }
    }

    @GetMapping(path = "by-holder/{holder}")
    public ResponseEntity<AccountLookupResponse> getByHolder(@PathVariable(value = "holder") String holder) {
        try {
            List<BankAccount> bankAccounts = queryDispacther.send(new FindAccountByHolderQuery(holder));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new AccountLookupResponse("Found %d accounts".formatted(bankAccounts.size()), bankAccounts));
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AccountLookupResponse("Bank accounts not found for reason: " + e.getMessage()));
        }
    }

    @GetMapping(path = "with-balance/{equality-type}/{balance}")
    public ResponseEntity<AccountLookupResponse> getByBalance(
            @PathVariable(value = "equality-type") EqualityType equalityType,
            @PathVariable(value = "balance") double balance) {
        try {
            List<BankAccount> bankAccounts = queryDispacther.send(
                    new FindAccountsWithBalanceQuery(balance, equalityType));
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new AccountLookupResponse("Found %d accounts".formatted(bankAccounts.size()), bankAccounts));
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new AccountLookupResponse("Bank accounts not found for reason: " + e.getMessage()));
        }
    }
}

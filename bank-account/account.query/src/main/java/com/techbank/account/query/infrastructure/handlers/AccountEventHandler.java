package com.techbank.account.query.infrastructure.handlers;

import com.techbank.account.common.events.AccountClosedEvent;
import com.techbank.account.common.events.AccountOpenedEvent;
import com.techbank.account.common.events.FundsDepositedEvent;
import com.techbank.account.common.events.FundsWithdrawnEvent;
import com.techbank.account.query.domain.BankAccount;
import com.techbank.account.query.domain.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountEventHandler implements EventHandler {

    private final BankAccountRepository repository;

    @Autowired
    public AccountEventHandler(BankAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public void on(AccountOpenedEvent event) {
        BankAccount bankAccount = BankAccount.builder()
                .id(event.getId())
                .accountHolder(event.getAccountHolder())
                .accountType(event.getAccountType())
                .creationDate(event.getCreatedDate())
                .balance(event.getOpeningBalance())
                .build();
        repository.save(bankAccount);
    }

    @Override
    public void on(FundsDepositedEvent event) {
        repository.findById(event.getId())
                .ifPresent(bankAccount -> {
                    double currentBalance = bankAccount.getBalance();
                    bankAccount.setBalance(currentBalance + event.getAmount());
                    repository.save(bankAccount);
                });
    }

    @Override
    public void on(FundsWithdrawnEvent event) {
        repository.findById(event.getId())
                .ifPresent(bankAccount -> {
                    double currentBalance = bankAccount.getBalance();
                    bankAccount.setBalance(currentBalance - event.getAmount());
                    repository.save(bankAccount);
                });
    }

    @Override
    public void on(AccountClosedEvent event) {
        repository.deleteById(event.getId());
    }
}

package com.techbank.account.command.domain;

import com.techbank.account.command.api.commands.OpenAccountCommand;
import com.techbank.account.common.events.AccountClosedEvent;
import com.techbank.account.common.events.AccountOpenedEvent;
import com.techbank.account.common.events.FundsDepositedEvent;
import com.techbank.account.common.events.FundsWithdrawnEvent;
import com.techbank.cqrs.core.domain.AggregateRoot;

import java.util.Date;

public class AccountAggregate extends AggregateRoot {
    private Boolean active;
    private double balance;

    public AccountAggregate(OpenAccountCommand command) {
        raiseEvent(new AccountOpenedEvent(
                command.getId(),
                1,
                command.getAccountHolder(),
                command.getAccountType(),
                new Date(),
                command.getOpeningBalance()
        ));
    }

    public void apply(AccountOpenedEvent event) {
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void apply(FundsDepositedEvent event) {
        this.id = event.getId();
        this.balance += event.getAmount();
    }

    public void apply(FundsWithdrawnEvent event) {
        this.id = event.getId();
        this.balance -= event.getAmount();
    }

    public void apply(AccountClosedEvent event) {
        this.id = event.getId();
        this.active = false;
    }

    public void depositFunds(double amount) {
        if(!this.active) throw new IllegalStateException("Current account is closed!");
        if(amount <= 0) throw new IllegalStateException("The deposit amount must be greater than zero.");
        raiseEvent(new FundsDepositedEvent(
                this.id, getVersion(), amount
        ));
    }

    public void withdrawFunds(double amount) {
        if(!this.active) throw new IllegalStateException("Current account is closed!");
        raiseEvent(new FundsWithdrawnEvent(
                this.id, getVersion(), amount
        ));
    }

    public void close() {
        if(!this.active) throw new IllegalStateException("Account is already closed!");
        raiseEvent(new AccountClosedEvent(this.id, getVersion()));
    }

    public double getBalance() {
        return balance;
    }

    public Boolean isActive() {
        return active;
    }
}

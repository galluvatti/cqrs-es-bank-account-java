package com.techbank.account.query.infrastructure.handlers;


import com.techbank.account.query.domain.events.AccountClosedEvent;
import com.techbank.account.query.domain.events.AccountOpenedEvent;
import com.techbank.account.query.domain.events.FundsDepositedEvent;
import com.techbank.account.query.domain.events.FundsWithdrawnEvent;

public interface EventHandler {
    void on(AccountOpenedEvent event);

    void on(FundsDepositedEvent event);

    void on(FundsWithdrawnEvent event);

    void on(AccountClosedEvent event);
}

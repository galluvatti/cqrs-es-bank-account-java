package com.techbank.account.common.events;

import com.techbank.account.common.dto.AccountType;
import com.techbank.cqrs.core.events.BaseEvent;

import java.util.Date;

public class AccountOpenedEvent extends BaseEvent {
    private String accountHolder;
    private AccountType accountType;
    private Date createdDate;
    private double openingBalance;

    public AccountOpenedEvent(String id, int version, String accountHolder, AccountType accountType, Date createdDate, double openingBalance) {
        super(id, version);
        this.accountHolder = accountHolder;
        this.accountType = accountType;
        this.createdDate = createdDate;
        this.openingBalance = openingBalance;
    }
}

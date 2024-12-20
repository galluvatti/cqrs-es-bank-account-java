package com.techbank.account.common.events;

import com.techbank.cqrs.core.events.BaseEvent;

public class FundsDepositedEvent extends BaseEvent {
    private double amount;

    public FundsDepositedEvent(String id, int version, double amount) {
        super(id, version);
        this.amount = amount;
    }
}

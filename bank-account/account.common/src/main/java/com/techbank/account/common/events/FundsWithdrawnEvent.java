package com.techbank.account.common.events;

import com.techbank.cqrs.core.events.BaseEvent;

public class FundsWithdrawnEvent extends BaseEvent {
    private double amount;

    public FundsWithdrawnEvent(String id, int version, double amount) {
        super(id, version);
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}

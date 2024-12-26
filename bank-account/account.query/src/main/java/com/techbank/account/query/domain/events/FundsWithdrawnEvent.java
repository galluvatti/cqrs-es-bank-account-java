package com.techbank.account.query.domain.events;

public class FundsWithdrawnEvent {
    private String id;
    private int version;
    private double amount;

    public FundsWithdrawnEvent(String id, int version, double amount) {
        this.id = id;
        this.version = version;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public int getVersion() {
        return version;
    }
}


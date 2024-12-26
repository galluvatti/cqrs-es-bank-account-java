package com.techbank.account.query.domain.events;

public class AccountClosedEvent {
    private String id;
    private int version;

    public AccountClosedEvent(String id, int version) {
        this.id = id;
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public int getVersion() {
        return version;
    }
}


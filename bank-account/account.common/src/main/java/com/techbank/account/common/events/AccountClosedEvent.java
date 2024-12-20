package com.techbank.account.common.events;

import com.techbank.cqrs.core.events.BaseEvent;

public class AccountClosedEvent extends BaseEvent {

    public AccountClosedEvent(String id, int version) {
        super(id, version);
    }
}

package com.techbank.cqrs.core.events;

import com.techbank.cqrs.core.messages.Message;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public abstract class BaseEvent extends Message {
    private final int version;

    public BaseEvent(String id, int version) {
        super(id);
        this.version = version;
    }
}

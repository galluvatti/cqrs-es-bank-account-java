package com.techbank.cqrs.core.messages;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public abstract class Message {
    private final String id;

    public Message(String id) {
        this.id = id;
    }
}

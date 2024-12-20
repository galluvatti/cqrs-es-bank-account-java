package com.techbank.cqrs.core.commands;

import com.techbank.cqrs.core.messages.Message;


public abstract class BaseCommand extends Message {
    public BaseCommand(String id) {
        super(id);
    }
}

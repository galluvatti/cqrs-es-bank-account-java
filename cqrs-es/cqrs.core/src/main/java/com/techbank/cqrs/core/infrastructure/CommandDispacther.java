package com.techbank.cqrs.core.infrastructure;

import com.techbank.cqrs.core.commands.BaseCommand;
import com.techbank.cqrs.core.commands.CommandHandler;

public interface CommandDispacther {
    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandler<T> handler);

    void send(BaseCommand command);
}

package com.techbank.account.command.infrastructure;

import com.techbank.cqrs.core.commands.BaseCommand;
import com.techbank.cqrs.core.commands.CommandHandler;
import com.techbank.cqrs.core.infrastructure.CommandDispacther;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

@Service
public class AccountCommandDispatcher implements CommandDispacther {

    private final Map<Class<? extends BaseCommand>, CommandHandler> routes = new HashMap<>();

    @Override
    public <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandler<T> handler) {
        routes.put(type, handler);
    }

    @Override
    public void send(BaseCommand command) {
        CommandHandler handler = routes.get(command.getClass());
        if (handler == null)
            throw new RuntimeException(MessageFormat.format("No handler found for the command {0}", command.getClass()));
        handler.handle(command);
    }
}

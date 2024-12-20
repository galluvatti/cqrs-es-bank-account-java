package com.techbank.account.command.infrastructure;

import com.techbank.account.command.api.commands.CloseAccountCommand;
import com.techbank.cqrs.core.commands.BaseCommand;
import com.techbank.cqrs.core.commands.CommandHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountCommandDispatcherTest {

    @Mock
    private MyCommandHandler handler;

    @Test
    void shouldRegisterAndInvokeACommandHandler() {
        CloseAccountCommand command = new CloseAccountCommand("id");
        AccountCommandDispatcher dispatcher = new AccountCommandDispatcher();

        dispatcher.registerHandler(CloseAccountCommand.class, handler);
        dispatcher.send(command);

        verify(handler).handle(command);
    }

    static class MyCommandHandler implements CommandHandler<CloseAccountCommand> {

        @Override
        public void handle(CloseAccountCommand command) {

        }
    }
}
package com.techbank.account.command.api.commands;

import com.techbank.account.command.domain.AccountAggregate;
import com.techbank.account.common.dto.AccountType;
import com.techbank.cqrs.core.handlers.EventSourcingHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountCommandHandlerTest {

    @Mock
    private EventSourcingHandler<AccountAggregate> eventSourcingHandler;

    @Test
    void handleOpenAccount() {
        OpenAccountCommand command = new OpenAccountCommand(
                "aggregateId", "holder", AccountType.CURRENT, 1000);
        new AccountCommandHandler(eventSourcingHandler).handle(command);

        verify(eventSourcingHandler).save(any());
    }

    @Test
    void handleDepositFunds() {
        String aggregateId = "aggregateId";
        int fundsToDeposit = 100;
        DepositFundsCommand command = new DepositFundsCommand(aggregateId, fundsToDeposit);
        AccountAggregate aggregate = mock(AccountAggregate.class);
        when(eventSourcingHandler.getById(aggregateId)).thenReturn(aggregate);

        new AccountCommandHandler(eventSourcingHandler).handle(command);

        verify(eventSourcingHandler).getById(aggregateId);
        verify(aggregate).depositFunds(fundsToDeposit);
        verify(eventSourcingHandler).save(any());
    }

    @Test
    void handleWithdrawFunds() {
        String aggregateId = "aggregateId";
        int fundsToWithdraw = 100;
        WithdrawFundsCommand command = new WithdrawFundsCommand(aggregateId, fundsToWithdraw);
        AccountAggregate aggregate = mock(AccountAggregate.class);
        when(eventSourcingHandler.getById(aggregateId)).thenReturn(aggregate);

        new AccountCommandHandler(eventSourcingHandler).handle(command);

        verify(eventSourcingHandler).getById(aggregateId);
        verify(aggregate).withdrawFunds(fundsToWithdraw);
        verify(eventSourcingHandler).save(any());
    }

    @Test
    void handleCloseAccount() {
        String aggregateId = "aggregateId";
        CloseAccountCommand command = new CloseAccountCommand(aggregateId);
        AccountAggregate aggregate = mock(AccountAggregate.class);
        when(eventSourcingHandler.getById(aggregateId)).thenReturn(aggregate);

        new AccountCommandHandler(eventSourcingHandler).handle(command);

        verify(eventSourcingHandler).getById(aggregateId);
        verify(eventSourcingHandler).save(any());
    }
}
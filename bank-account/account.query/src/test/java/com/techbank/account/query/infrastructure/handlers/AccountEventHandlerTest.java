package com.techbank.account.query.infrastructure.handlers;

import com.techbank.account.common.dto.AccountType;
import com.techbank.account.common.events.AccountClosedEvent;
import com.techbank.account.common.events.AccountOpenedEvent;
import com.techbank.account.common.events.FundsDepositedEvent;
import com.techbank.account.common.events.FundsWithdrawnEvent;
import com.techbank.account.query.domain.BankAccount;
import com.techbank.account.query.domain.BankAccountRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountEventHandlerTest {

    @Mock
    private BankAccountRepository repository;

    @Test
    void onAccountOpenedEvent() {
        new AccountEventHandler(repository).on(
                new AccountOpenedEvent(
                        "id",
                        1,
                        "holder",
                        AccountType.CURRENT,
                        new Date(),
                        1000
                ));

        verify(repository).save(any(BankAccount.class));
    }

    @Test
    void onFundsDepositedEvent() {
        String bankAccountId = "id";
        BankAccount bankAccount = mock(BankAccount.class);
        when(repository.findById(bankAccountId)).thenReturn(Optional.of(bankAccount));
        when(bankAccount.getBalance()).thenReturn(1000d);

        new AccountEventHandler(repository).on(
                new FundsDepositedEvent(
                        bankAccountId,
                        1,
                        1000
                ));

        verify(repository).save(any(BankAccount.class));
    }

    @Test
    void doNothingOnFundsDepositedEventWithUnexistingID() {
        String bankAccountId = "id";
        when(repository.findById(bankAccountId)).thenReturn(Optional.empty());

        new AccountEventHandler(repository).on(
                new FundsDepositedEvent(
                        bankAccountId,
                        1,
                        1000
                ));

        verify(repository, times(0)).save(any());
    }

    @Test
    void onFundsWithdrawnEvent() {
        String bankAccountId = "id";
        BankAccount bankAccount = mock(BankAccount.class);
        when(repository.findById(bankAccountId)).thenReturn(Optional.of(bankAccount));
        when(bankAccount.getBalance()).thenReturn(1000d);

        new AccountEventHandler(repository).on(
                new FundsWithdrawnEvent(
                        "id",
                        1,
                        1000
                ));

        verify(repository).save(any(BankAccount.class));
    }

    @Test
    void doNothingOnFundsWithdrawnEventWithUnexistingID() {
        String bankAccountId = "id";
        when(repository.findById(bankAccountId)).thenReturn(Optional.empty());

        new AccountEventHandler(repository).on(
                new FundsDepositedEvent(
                        bankAccountId,
                        1,
                        1000
                ));

        verify(repository, times(0)).save(any());
    }

    @Test
    void onAccountClosedEvent() {
        String bankAccountId = "id";
        new AccountEventHandler(repository).on(
                new AccountClosedEvent(
                        bankAccountId,
                        1
                ));

        verify(repository).deleteById(bankAccountId);
    }
}
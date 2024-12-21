package com.techbank.account.command.domain;

import com.techbank.account.command.api.commands.CloseAccountCommand;
import com.techbank.account.command.api.commands.OpenAccountCommand;
import com.techbank.account.common.dto.AccountType;
import com.techbank.account.common.events.AccountClosedEvent;
import com.techbank.account.common.events.AccountOpenedEvent;
import com.techbank.account.common.events.FundsDepositedEvent;
import com.techbank.account.common.events.FundsWithdrawnEvent;
import com.techbank.cqrs.core.events.BaseEvent;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountAggregateTest {

    @Test
    void shouldOpenAnAccount() {
        double initialBalance = 1000;
        AccountAggregate aggregate = new AccountAggregate(
                new OpenAccountCommand(
                        "id",
                        "holder",
                        AccountType.CURRENT,
                        initialBalance
                )
        );
        List<BaseEvent> changes = aggregate.getUncommitedChanges();
        assertThat(changes.size()).isEqualTo(1);
        assertThat(changes.get(0).getClass()).isEqualTo(AccountOpenedEvent.class);

        assertThat(aggregate.getBalance()).isEqualTo(initialBalance);
    }

    @Test
    void shouldDepositFunds() {
        int initialBalance = 1000;
        AccountAggregate aggregate = anAccountWithBalance(initialBalance);
        double fundsToAdd = 500.24;
        aggregate.depositFunds(fundsToAdd);

        assertThat(aggregate.getBalance()).isEqualTo(initialBalance + fundsToAdd);

        List<BaseEvent> changes = aggregate.getUncommitedChanges();
        assertThat(changes.get(changes.size() - 1).getClass()).isEqualTo(FundsDepositedEvent.class);
    }

    @Test
    void shouldNotDepositNegativeFunds() {
        int initialBalance = 1000;
        AccountAggregate aggregate = anAccountWithBalance(initialBalance);
        double fundsToAdd = -500.24;
        assertThatThrownBy(
                () -> aggregate.depositFunds(fundsToAdd))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("The deposit amount must be greater than zero.");
    }

    @Test
    void shouldNotDepositOnAClosedAccount() {
        AccountAggregate aggregate = aClosedAccount();
        assertThatThrownBy(
                () -> aggregate.depositFunds(100))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Current account is closed!");
    }

    @Test
    void shouldWithdrawFunds() {
        int initialBalance = 1000;
        AccountAggregate aggregate = anAccountWithBalance(initialBalance);
        double fundsToWithdraw = 500.24;
        aggregate.withdrawFunds(fundsToWithdraw);

        assertThat(aggregate.getBalance()).isEqualTo(initialBalance - fundsToWithdraw);

        List<BaseEvent> changes = aggregate.getUncommitedChanges();
        assertThat(changes.get(changes.size() - 1).getClass()).isEqualTo(FundsWithdrawnEvent.class);
    }

    @Test
    void shouldNotWithdrawOnAClosedAccount() {
        AccountAggregate aggregate = aClosedAccount();
        assertThatThrownBy(
                () -> aggregate.withdrawFunds(100))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Current account is closed!");
    }

    @Test
    void shouldClose() {
        AccountAggregate aggregate = anAccountWithBalance(1000);
        aggregate.close();

        assertThat(aggregate.isActive()).isFalse();

        List<BaseEvent> changes = aggregate.getUncommitedChanges();
        assertThat(changes.get(changes.size() - 1).getClass()).isEqualTo(AccountClosedEvent.class);
    }

    @Test
    void shouldNotCloseAnAlreadyClosedAccount() {
        AccountAggregate aggregate = aClosedAccount();
        assertThatThrownBy(
                aggregate::close)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Account is already closed!");
    }

    private AccountAggregate anAccountWithBalance(int balance) {
        return new AccountAggregate(
                new OpenAccountCommand(
                        "id",
                        "holder",
                        AccountType.CURRENT,
                        balance
                )
        );
    }

    private AccountAggregate aClosedAccount() {
        AccountAggregate aggregate = new AccountAggregate(
                new OpenAccountCommand(
                        "id",
                        "holder",
                        AccountType.CURRENT,
                        100
                )
        );
        aggregate.close();
        return aggregate;
    }
}
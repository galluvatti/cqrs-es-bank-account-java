package com.techbank.account.query.infrastructure.consumers;

import com.techbank.account.common.events.AccountClosedEvent;
import com.techbank.account.common.events.AccountOpenedEvent;
import com.techbank.account.common.events.FundsDepositedEvent;
import com.techbank.account.common.events.FundsWithdrawnEvent;
import com.techbank.account.query.infrastructure.handlers.EventHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.support.Acknowledgment;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountEventConsumerTest {

    @Mock
    private EventHandler eventHandler;
    @Mock
    private Acknowledgment ack;
    @Mock
    private AccountOpenedEvent accountOpenedEvent;
    @Mock
    private FundsDepositedEvent fundsDepositedEvent;
    @Mock
    private FundsWithdrawnEvent fundsWithdrawnEvent;
    @Mock
    private AccountClosedEvent accountClosedEvent;

    @Test
    void listenToAccountOpenedEventAndSendAck() {
        new AccountEventConsumer(eventHandler).consume(accountOpenedEvent, ack);
        verify(eventHandler).on(accountOpenedEvent);
        verify(ack).acknowledge();
    }

    @Test
    void listenToFundsDepositedEventAndSendAck() {
        new AccountEventConsumer(eventHandler).consume(fundsDepositedEvent, ack);
        verify(eventHandler).on(fundsDepositedEvent);
        verify(ack).acknowledge();
    }

    @Test
    void listenToFundsWithdrawnEventAndSendAck() {
        new AccountEventConsumer(eventHandler).consume(fundsWithdrawnEvent, ack);
        verify(eventHandler).on(fundsWithdrawnEvent);
        verify(ack).acknowledge();
    }

    @Test
    void listenToAccountClosedEventAndSendAck() {
        new AccountEventConsumer(eventHandler).consume(accountClosedEvent, ack);
        verify(eventHandler).on(accountClosedEvent);
        verify(ack).acknowledge();
    }
}
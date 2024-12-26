package com.techbank.account.query.infrastructure.consumers;

import com.techbank.account.query.domain.events.AccountClosedEvent;
import com.techbank.account.query.domain.events.AccountOpenedEvent;
import com.techbank.account.query.domain.events.FundsDepositedEvent;
import com.techbank.account.query.domain.events.FundsWithdrawnEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {
    void consumeAccountOpenedEvent(@Payload String event, Acknowledgment ack);

    void consumeFundsDepositedEvent(@Payload String event, Acknowledgment ack);

    void consumeFundsWithdrawnEvent(@Payload String event, Acknowledgment ack);

    void consumeAccountClosedEvent(@Payload String event, Acknowledgment ack);
}

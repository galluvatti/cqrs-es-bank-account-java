package com.techbank.account.query.infrastructure.consumers;

import com.google.gson.Gson;
import com.techbank.account.query.domain.events.AccountClosedEvent;
import com.techbank.account.query.domain.events.AccountOpenedEvent;
import com.techbank.account.query.domain.events.FundsDepositedEvent;
import com.techbank.account.query.domain.events.FundsWithdrawnEvent;
import com.techbank.account.query.infrastructure.handlers.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AccountEventConsumer implements EventConsumer {

    private static final Logger logger = Logger.getLogger(AccountEventConsumer.class.getName());

    private final EventHandler eventHandler;

    @Autowired
    public AccountEventConsumer(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    @KafkaListener(topics = "AccountOpenedEvent",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    @Override
    public void consumeAccountOpenedEvent(String event, Acknowledgment ack) {
        logger.log(Level.INFO, "New event received " + event);
        eventHandler.on(new Gson().fromJson(event, AccountOpenedEvent.class));
        ack.acknowledge();
    }

    @KafkaListener(topics = "FundsDepositedEvent",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    @Override
    public void consumeFundsDepositedEvent(String event, Acknowledgment ack) {
        logger.log(Level.INFO, "New event received " + event);
        eventHandler.on(new Gson().fromJson(event, FundsDepositedEvent.class));
        ack.acknowledge();
    }

    @KafkaListener(topics = "FundsWithdrawnEvent",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    @Override
    public void consumeFundsWithdrawnEvent(String event, Acknowledgment ack) {
        logger.log(Level.INFO, "New event received " + event);
        eventHandler.on(new Gson().fromJson(event, FundsWithdrawnEvent.class));
        ack.acknowledge();
    }

    @KafkaListener(topics = "AccountClosedEvent",
            groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    @Override
    public void consumeAccountClosedEvent(String event, Acknowledgment ack) {
        logger.log(Level.INFO, "New event received " + event);
        eventHandler.on(new Gson().fromJson(event, AccountClosedEvent.class));
        ack.acknowledge();
    }
}

package com.techbank.account.command.infrastructure;

import com.google.gson.Gson;
import com.techbank.cqrs.core.events.BaseEvent;
import com.techbank.cqrs.core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AccountEventProducer implements EventProducer {
    private static final Logger logger = Logger.getLogger(AccountEventProducer.class.getName());


    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public AccountEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void produce(String topic, BaseEvent event) {
        logger.log(Level.INFO, MessageFormat.format("Sending event to topic {0}", topic));
        String json = new Gson().toJson(event);
        logger.log(Level.INFO, "Json to be sent: %s".formatted(json));
        kafkaTemplate.send(topic, json);
    }
}

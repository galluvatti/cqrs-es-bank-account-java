package com.techbank.cqrs.core.events;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "eventStore")
public class EventModel {

    @Id
    private String id;
    private Date timestamp;
    private String aggregateId;
    private String aggregateType;
    private int version;
    private String eventType;
    private BaseEvent eventData;

    public EventModel(Date timestamp, String aggregateId, String aggregateType, int version, String eventType, BaseEvent eventData) {
        this.timestamp = timestamp;
        this.aggregateId = aggregateId;
        this.aggregateType = aggregateType;
        this.version = version;
        this.eventType = eventType;
        this.eventData = eventData;
    }

    public String getId() {
        return id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getAggregateId() {
        return aggregateId;
    }

    public String getAggregateType() {
        return aggregateType;
    }

    public int getVersion() {
        return version;
    }

    public String getEventType() {
        return eventType;
    }

    public BaseEvent getEventData() {
        return eventData;
    }
}

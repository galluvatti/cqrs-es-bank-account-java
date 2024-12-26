package com.techbank.cqrs.core.events;

import com.techbank.cqrs.core.messages.Message;

import java.util.Objects;

public abstract class BaseEvent extends Message {
    private int version;

    public BaseEvent(String id, int version) {
        super(id);
        this.version = version;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        BaseEvent baseEvent = (BaseEvent) o;
        return version == baseEvent.version;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), version);
    }
}

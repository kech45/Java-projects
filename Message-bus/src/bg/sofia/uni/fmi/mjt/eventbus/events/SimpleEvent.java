package bg.sofia.uni.fmi.mjt.eventbus.events;

import java.time.Instant;

public class SimpleEvent<T extends Payload<?>> implements Event<T> {
    private final T payload;
    private final Instant created;
    private final int priority;
    private final String source;

    public SimpleEvent(T payload, int priority, String source) {
        if(payload == null || source.isEmpty()) {
            throw new IllegalArgumentException("Null parameters not allowed!");
        }

        if(source.isBlank()) {
            throw new IllegalArgumentException("Source string cannot be blank");
        }

        this.payload = payload;
        this.created = Instant.now();
        this.priority = priority;
        this.source = source;
    }

    @Override
    public Instant getTimeStamp() {
        return this.created;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }

    @Override
    public String getSource() {
        return this.source;
    }

    @Override
    public T getPayload() {
        return this.payload;
    }
}

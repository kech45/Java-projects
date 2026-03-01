package bg.sofia.uni.fmi.mjt.eventbus.events;

import java.time.Instant;

public interface Event<T extends Payload<?>> {
    Instant getTimeStamp();

    int getPriority();

    String getSource();

    T getPayload();
}

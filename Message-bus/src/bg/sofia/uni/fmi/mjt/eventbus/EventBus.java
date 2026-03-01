package bg.sofia.uni.fmi.mjt.eventbus;

import bg.sofia.uni.fmi.mjt.eventbus.events.Event;
import bg.sofia.uni.fmi.mjt.eventbus.exception.MissingSubscriptionException;
import bg.sofia.uni.fmi.mjt.eventbus.subscribers.Subscriber;

import java.time.Instant;
import java.util.Collection;

public interface EventBus {
    <T extends Event<?>> void subscribe(Class<T> eventType, Subscriber<? super T> subscriber);

    <T extends Event<?>> void unsubscribe(Class<T> eventType, Subscriber<? super T> subscriber)
        throws MissingSubscriptionException;

    <T extends Event<?>> void publish(T event);

    void clear();

    <T extends Event<?>> Collection<T> getEventLogs(Class<T> eventType, Instant from, Instant to);

    <T extends Event<?>> Collection<Subscriber<?>> getSubscribersForEvent(Class<T> eventType);
}

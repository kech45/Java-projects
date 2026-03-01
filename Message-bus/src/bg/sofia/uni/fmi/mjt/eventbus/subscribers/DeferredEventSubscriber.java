package bg.sofia.uni.fmi.mjt.eventbus.subscribers;

import bg.sofia.uni.fmi.mjt.eventbus.events.Event;
import bg.sofia.uni.fmi.mjt.eventbus.events.EventComparator;

import java.util.*;


public class DeferredEventSubscriber<T extends Event<?>> implements Subscriber<T>, Iterable<T> {
    private final List<T> unprocessedEvents;

    public DeferredEventSubscriber() {
        this.unprocessedEvents = new ArrayList<>();
    }

    @Override
    public void onEvent(T event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null!");
        }
        unprocessedEvents.add(event);
    }

    @Override
    public Iterator<T> iterator() {
        List<T> copy = new ArrayList<>(unprocessedEvents);
        copy.sort(new EventComparator<>());
        return copy.iterator();
    }

    public boolean isEmpty() {
        return this.unprocessedEvents.isEmpty();
    }

    public List<T> getUnprocessedEvents() {
        return Collections.unmodifiableList(this.unprocessedEvents);
    }
}

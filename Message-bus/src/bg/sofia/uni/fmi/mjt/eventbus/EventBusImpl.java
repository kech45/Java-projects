package bg.sofia.uni.fmi.mjt.eventbus;
import bg.sofia.uni.fmi.mjt.eventbus.events.Event;
import bg.sofia.uni.fmi.mjt.eventbus.exception.MissingSubscriptionException;
import bg.sofia.uni.fmi.mjt.eventbus.subscribers.Subscriber;


import java.time.Instant;
import java.util.*;


public class EventBusImpl implements EventBus {

    private final Map<Class<? extends Event<?>>, Set<Subscriber<?>>> subs;
    private final Map<Class<? extends Event<?>>, List<Event<?>>> events;

    public EventBusImpl() {
        this.subs = new HashMap<>();
        this.events = new HashMap<>();
    }

    @Override
    public <T extends Event<?>> void subscribe(Class<T> eventType, Subscriber<? super T> subscriber) {
        if(eventType == null) {
            throw new IllegalArgumentException("Event type cannot be null!");
        }

        if(subscriber == null) {
            throw new IllegalArgumentException("Subscriber cannot be null!");
        }

        subs.putIfAbsent(eventType, new HashSet<>());
        subs.get(eventType).add(subscriber);
    }

    @Override
    public <T extends Event<?>> void unsubscribe(Class<T> eventType, Subscriber<? super T> subscriber)
        throws MissingSubscriptionException {
        if(eventType == null) {
            throw new IllegalArgumentException("Event type cannot be null!");
        }

        if(subscriber == null) {
            throw new IllegalArgumentException("Subscriber cannot be null!");
        }

        Set<Subscriber<?>> subscribers = subs.get(eventType);

        if(subscribers == null || !subscribers.contains(subscriber)) {
            throw new MissingSubscriptionException("Subscriber is not subscribed to given event type!");
        }

        subs.get(eventType).remove(subscriber);
    }

    @Override
    public <T extends Event<?>> void publish(T event) {
        if(event == null) {
            throw new IllegalArgumentException("Event to be published cannot be null!");
        }

        @SuppressWarnings("unchecked")
        Class<? extends Event<?>> eventClass = (Class<? extends Event<?>>) event.getClass();

        events.putIfAbsent(eventClass, new ArrayList<>());
        events.get(eventClass).add(event);

        Set<Subscriber<?>> subscribers = subs.get(eventClass);

        if(subscribers == null) {
            return;
        }

        @SuppressWarnings("unchecked")
        Set<Subscriber<? super T>> castSubscribers = (Set<Subscriber<? super T>>) (Set<?>) subscribers;
        //we only add subscribers in the bus through subscribe and since that has all the correct
        //checks, subscribe guarantees right pairing

        for(Subscriber<? super T> subscriber : castSubscribers) {
            subscriber.onEvent(event);
        }
    }

    @Override
    public void clear() {
        subs.clear();
        events.clear();
    }

    @Override
    public <T extends Event<?>> Collection<T> getEventLogs(Class<T> eventType, Instant from, Instant to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("From or To instants cannot be null!");
        }

        if (eventType == null) {
            throw new IllegalArgumentException("Given event type cannot be null!");
        }

        List<T> result = new ArrayList<>();
        if (events.containsKey(eventType)) {
            for (Event<?> event : events.get(eventType)) {
                if(event.getTimeStamp().compareTo(from) >= 0 && event.getTimeStamp().compareTo(to) < 0){
                    @SuppressWarnings("unchecked")
                    T castEvent = (T) event;
                    result.add(castEvent);
                }
            }
        }
        return Collections.unmodifiableList(result);
    }

    @Override
    public <T extends Event<?>> Collection<Subscriber<?>> getSubscribersForEvent(Class<T> eventType) {
        if(eventType == null) {
            throw new IllegalArgumentException("Event type cannot be null!");
        }

        Set<Subscriber<?>>subsForType = subs.get(eventType);

        if(subsForType == null) {
            return Collections.emptySet();
        }

        return Collections.unmodifiableSet(subsForType);
    }

}

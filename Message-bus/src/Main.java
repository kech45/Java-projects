import bg.sofia.uni.fmi.mjt.eventbus.EventBus;
import bg.sofia.uni.fmi.mjt.eventbus.EventBusImpl;
import bg.sofia.uni.fmi.mjt.eventbus.events.Payload;
import bg.sofia.uni.fmi.mjt.eventbus.events.SimpleEvent;
import bg.sofia.uni.fmi.mjt.eventbus.events.StringPayload;
import bg.sofia.uni.fmi.mjt.eventbus.events.Event;
import bg.sofia.uni.fmi.mjt.eventbus.subscribers.DeferredEventSubscriber;
import bg.sofia.uni.fmi.mjt.eventbus.subscribers.Subscriber;
import bg.sofia.uni.fmi.mjt.eventbus.exception.MissingSubscriptionException;

import java.time.Instant;
import java.util.Collection;

public class Main {
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static <T extends Event<?>> Class<T> classOf(Class rawClass) {
        return (Class<T>) rawClass;
    }

    public static void main(String[] args) throws MissingSubscriptionException {

        EventBus bus = new EventBusImpl();

        SimpleEvent<StringPayload> event1 = new SimpleEvent<>(new StringPayload("Really"), 1, "HR");
        SimpleEvent<StringPayload> event2 = new SimpleEvent<>(new StringPayload("NotReally"), 2, "UI");
        SimpleEvent<StringPayload> event3 = new SimpleEvent<>(new StringPayload("ForallyReally"), 3, "JOHN");

        Subscriber<SimpleEvent<StringPayload>> sub1 = new DeferredEventSubscriber<>();
        Subscriber<SimpleEvent<StringPayload>> sub2 = new DeferredEventSubscriber<>();
        Subscriber<SimpleEvent<StringPayload>> sub3 = new DeferredEventSubscriber<>();

        Class<SimpleEvent<StringPayload>> eventClass = classOf(SimpleEvent.class);

        bus.subscribe(eventClass, sub1);
        bus.subscribe(eventClass, sub2);
        bus.subscribe(eventClass, sub3);

        bus.publish(event1);
        bus.publish(event2);
        bus.publish(event3);


        Collection<SimpleEvent<StringPayload>> logs = bus.getEventLogs(eventClass,
                Instant.now().minusSeconds(50000),
                Instant.now().plusSeconds(50000));

        System.out.println("Logs:" + logs.size());
    }
}
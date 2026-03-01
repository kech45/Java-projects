package bg.sofia.uni.fmi.mjt.eventbus.events;

import java.util.Comparator;

public class EventComparator<T extends Event<?>> implements Comparator<T> {

    @Override
    public int compare(T event1, T event2) {
        if (event1.getPriority() > event2.getPriority()) {
            return -1;
        }
        if (event1.getPriority() < event2.getPriority()) {
            return 1;
        }
        return event1.getTimeStamp().compareTo(event2.getTimeStamp());
    }
}

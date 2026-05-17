package service.event;

import java.util.ArrayList;
import java.util.List;

public class EventPublisher {
    private final List<EventListener> listeners = new ArrayList<>();

    public void register(EventListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
    }

    public void publish(String event) {
        listeners.forEach(listener -> listener.onEvent(event));
    }
}

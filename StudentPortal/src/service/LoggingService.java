package service;

import repository.Database;
import service.event.EventListener;
import service.event.EventPublisher;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LoggingService implements EventListener {
    private static LoggingService instance;
    private final List<String> logs = new ArrayList<>();
    private final EventPublisher publisher = new EventPublisher();

    private LoggingService() {
        publisher.register(this);
    }

    public static synchronized LoggingService getInstance() {
        if (instance == null) {
            instance = new LoggingService();
        }
        return instance;
    }

    public void registerListener(EventListener listener) {
        publisher.register(listener);
    }

    public void log(String message) {
        String entry = message == null ? "null" : message;
        logs.add(entry);
        publisher.publish(entry);
    }

    public List<String> getLogs() {
        return Collections.unmodifiableList(logs);
    }

    @Override
    public void onEvent(String event) {
        // Default listener stores events for history
    }
}

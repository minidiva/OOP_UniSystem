package domain.course;

import java.io.Serializable;
import java.util.Objects;

public class Lesson implements Serializable {
    private final int id;
    private final LessonType type;
    private final String topic;
    private final int durationMinutes;
    private final String description;

    public Lesson(int id, LessonType type, String topic, int durationMinutes, String description) {
        this.id = id;
        this.type = Objects.requireNonNull(type);
        this.topic = Objects.requireNonNull(topic);
        this.durationMinutes = Math.max(0, durationMinutes);
        this.description = description == null ? "" : description;
    }

    public int getId() { return id; }
    public LessonType getType() { return type; }
    public String getTopic() { return topic; }
    public int getDurationMinutes() { return durationMinutes; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return String.format("Lesson %d [%s]: %s (%d min) - %s", id, type, topic, durationMinutes, description);
    }
}

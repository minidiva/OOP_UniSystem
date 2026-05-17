package domain.course;

import domain.user.Student;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Course implements Serializable {
    private int id;
    private String title;
    private String description;
    private int credits;
    private boolean active = true;
    private final List<Lesson> lessons = new ArrayList<>();

    public Course(int id, String title, String description, int credits) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.credits = Math.max(0, credits);
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getCredits() { return credits; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public List<Lesson> getLessons() { return Collections.unmodifiableList(lessons); }

    public void addLesson(Lesson lesson) {
        if (lesson != null) lessons.add(lesson);
    }

    public boolean isAvailableFor(Student student) {
        if (student == null || !active) return false;
        return student.getCredits() + credits <= 21 && student.getFailCount() < 3;
    }

    // Сравнение по id — нужно чтобы contains() работал корректно
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        return id == ((Course) o).id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id + ": " + title + " (" + credits + " credits) — " + description;
    }
}
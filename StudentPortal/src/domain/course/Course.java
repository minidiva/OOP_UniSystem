package domain.course;

import domain.user.Student;
import java.io.Serializable;

public class Course implements Serializable {
    private int id;
    private String title;
    private String description;
    private int credits;
    
    public Course(int id, String title, String description, int credits) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.credits = credits;
    }
    
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getCredits() { return credits; }
    
    public boolean isAvailableFor(Student student) {
        return true;
    }
    
    @Override
    public String toString() {
        return id + ": " + title + " (" + credits + " credits) — " + description;
    }
}

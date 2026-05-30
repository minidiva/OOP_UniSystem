package domain.course;

import domain.user.Student;
import domain.user.Teacher;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable {
    private int id;
    private String title;
    private String description;
    private int credits;
    private List<Teacher> instructors;
    
    public Course(int id, String title, String description, int credits) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.credits = credits;
        this.instructors = new ArrayList<>();
    }
    
    // Геттеры
    public int getId() {
        return id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public int getCredits() {
        return credits;
    }
    
    public List<Teacher> getInstructors() {
        return new ArrayList<>(instructors);
    }
    
    // Сеттеры
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public void setCredits(int credits) {
        this.credits = credits;
    }
    
    public void addInstructor(Teacher teacher) {
        if (teacher != null && !instructors.contains(teacher)) {
            instructors.add(teacher);
            System.out.println(" Instructor " + teacher.getFullName() + " added to course: " + this.title);
        }
    }
    
    public void removeInstructor(Teacher teacher) {
        instructors.remove(teacher);
    }
    
    public boolean isAvailableFor(Student student) {
        return true;
    }
    
    @Override
    public String toString() {
        return id + ": " + title + " (" + credits + " credits) — " + description;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course course = (Course) obj;
        return id == course.id;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
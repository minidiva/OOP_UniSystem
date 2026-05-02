package domain.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import domain.course.Course;

public class Teacher extends Employee {
    private String title;  // PROFESSOR, SENIOR_LECTURER, LECTURER, TUTOR
    private List<Course> teachingCourses;
    
    public Teacher() {
        this.role = Role.TEACHER;
        this.teachingCourses = new ArrayList<>();
    }
    
    public Teacher(int id, String firstName, String lastName, String email, String password, 
                   String title, double salary) {
        super(id, firstName, lastName, email, password, Role.TEACHER, salary, LocalDate.now());
        this.title = title;
        this.teachingCourses = new ArrayList<>();
    }
    
    // геттеры и сеттеры...
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public List<Course> getTeachingCourses() { return teachingCourses; }
    public void addCourse(Course course) { teachingCourses.add(course); }
}
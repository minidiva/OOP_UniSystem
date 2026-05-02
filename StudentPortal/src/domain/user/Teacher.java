package domain.user;

import java.util.ArrayList;
import java.util.List;
import domain.course.Course;

public class Teacher extends User {
    private String title;  // tutor, lecturer, senior_lecturer, professor
    private double salary;
    private List<Course> teachingCourses;
    
    public Teacher() {
        this.role = Role.TEACHER;
        this.teachingCourses = new ArrayList<>();
    }
    
    public Teacher(int id, String firstName, String lastName, String email, String password, String title, double salary) {
        super(id, firstName, lastName, email, password, Role.TEACHER);
        this.title = title;
        this.salary = salary;
        this.teachingCourses = new ArrayList<>();
    }
    
    public void putMark(Student student, Course course, double firstMark, double secondMark, double finalMark) {
        System.out.println("Mark put for " + student.getFullName() + " in " + course.getTitle());
    }
    
    // Геттеры/сеттеры
    public String getTitle() { return title; }
    public double getSalary() { return salary; }
    public List<Course> getTeachingCourses() { return teachingCourses; }
    public void setTitle(String title) { this.title = title; }
    public void addCourse(Course course) { teachingCourses.add(course); }
}
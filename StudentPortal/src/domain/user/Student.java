package domain.user;

import java.util.ArrayList;
import java.util.List;
import domain.course.Course;

public class Student extends User {
    private int year;
    private String major;
    private int credits;
    private int failCount;
    private double gpa;
    private List<Course> enrolledCourses;
    
    public Student() {
        this.role = Role.STUDENT;
        this.enrolledCourses = new ArrayList<>();
        this.credits = 0;
        this.failCount = 0;
    }
    
    public Student(int id, String firstName, String lastName, String email, String password, int year, String major) {
        super(id, firstName, lastName, email, password, Role.STUDENT);
        this.year = year;
        this.major = major;
        this.credits = 0;
        this.failCount = 0;
        this.gpa = 0.0;
        this.enrolledCourses = new ArrayList<>();
    }
    
    // Проверка для регистрации
    public boolean canRegisterForCourse(Course course) {
        if (credits + course.getCredits() > 21) {
            System.out.println("Cannot register: Credit limit would exceed 21 (current: " + credits + ")");
            return false;
        }
        if (failCount >= 3) {
            System.out.println("Cannot register: You have 3 or more fails");
            return false;
        }
        if (enrolledCourses.contains(course)) {
            System.out.println("Already registered for this course");
            return false;
        }
        return true;
    }
    
    public void registerCourse(Course course) {
        if (canRegisterForCourse(course)) {
            enrolledCourses.add(course);
            credits += course.getCredits();
            System.out.println("Registered for: " + course.getTitle());
        }
    }
    
    // Геттеры/сеттеры
    public int getYear() { return year; }
    public String getMajor() { return major; }
    public int getCredits() { return credits; }
    public int getFailCount() { return failCount; }
    public double getGpa() { return gpa; }
    public List<Course> getEnrolledCourses() { return enrolledCourses; }
    
    public void setYear(int year) { this.year = year; }
    public void setMajor(String major) { this.major = major; }
    public void addFail() { failCount++; }
    public void setGpa(double gpa) { this.gpa = gpa; }
}
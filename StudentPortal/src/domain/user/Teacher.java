package domain.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import domain.course.Course;

public class Teacher extends User {
    private String title;  // PROFESSOR, SENIOR_LECTURER, LECTURER, TUTOR
    private double salary;
    private LocalDate hireDate;
    private List<Course> teachingCourses;
    
    public Teacher() {
        this.role = Role.TEACHER;
        this.teachingCourses = new ArrayList<>();
    }
    
    public Teacher(int id, String firstName, String lastName, String email, String password, 
                   String title, double salary) {
        super(id, firstName, lastName, email, password, Role.TEACHER);
        this.title = title;
        this.salary = salary;
        this.hireDate = LocalDate.now();
        this.teachingCourses = new ArrayList<>();
    }
    
    // НОВЫЙ МЕТОД — выставление оценки
    public void putMark(Student student, Course course, double firstAttestation, 
                        double secondAttestation, double finalExam) {
        // Проверка границ
        if (firstAttestation < 0 || firstAttestation > 30) {
            System.out.println(" First attestation must be between 0 and 30");
            return;
        }
        if (secondAttestation < 0 || secondAttestation > 30) {
            System.out.println(" Second attestation must be between 0 and 30");
            return;
        }
        if (finalExam < 0 || finalExam > 40) {
            System.out.println(" Final exam must be between 0 and 40");
            return;
        }
        
        Mark mark = new Mark(firstAttestation, secondAttestation, finalExam);
        student.addMark(course, mark);
        System.out.println(" Mark put for " + student.getFullName() + " in " + course.getTitle());
        System.out.println("   Total: " + mark.calculateTotal() + " (" + mark.getLetterGrade() + ")");
    }
    
    // Геттеры и сеттеры
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
    
    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }
    
    public List<Course> getTeachingCourses() { return teachingCourses; }
    public void setTeachingCourses(List<Course> teachingCourses) { this.teachingCourses = teachingCourses; }
    public void addCourse(Course course) { teachingCourses.add(course); }
    
    @Override
    public String toString() {
        return super.toString() + ", Title: " + title + ", Salary: " + salary;
    }
}
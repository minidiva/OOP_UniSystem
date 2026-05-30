package command.manager;

import java.util.Scanner;
import command.core.Command;
import domain.course.Course;
import domain.user.Manager;
import domain.user.Teacher;
import service.CourseService;
import repository.Database;
import util.Printer;

public class AssignTeacherCommand implements Command {
    private final Manager manager;
    private final CourseService courseService;
    private final Database db;
    private final Printer printer;
    
    public AssignTeacherCommand(Manager manager, CourseService courseService, Database db, Printer printer) {
        this.manager = manager;
        this.courseService = courseService;
        this.db = db;
        this.printer = printer;
    }
    
    @Override
    public String name() {
        return "assignteacher";
    }
    
    @Override
    public String description() {
        return "Assign teacher to a course (Manager only)";
    }
    
    @Override
    public void execute(Scanner scanner) {
        printer.println("\n=== ASSIGN TEACHER TO COURSE ===");
        
        printer.println("\n📚 Available courses:");
        courseService.getAllCourses().forEach(printer::println);
        
        printer.println("Enter course ID: ");
        String courseId = scanner.nextLine();
        Course course = courseService.getById(courseId).orElse(null);
        
        if (course == null) {
            printer.println(" Course not found!");
            return;
        }
        
        printer.println("\n‍ Available teachers:");
        db.getUsers().values().stream()
            .filter(u -> u instanceof Teacher)
            .forEach(printer::println);
        
        printer.println("Enter teacher email: ");
        String teacherEmail = scanner.nextLine();
        
        Teacher teacher = (Teacher) db.getUsers().values().stream()
            .filter(u -> u instanceof Teacher && u.getEmail().equals(teacherEmail))
            .findFirst()
            .orElse(null);
        
        if (teacher == null) {
            printer.println(" Teacher not found!");
            return;
        }
        
        course.addInstructor(teacher);
        teacher.addCourse(course);
        printer.println(" Teacher " + teacher.getFullName() + " assigned to course: " + course.getTitle());
    }
}
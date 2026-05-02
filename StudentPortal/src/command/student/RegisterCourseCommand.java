package command.student;

import java.util.Scanner;
import command.core.Command;
import domain.user.Student;
import service.CourseService;
import util.Printer;

public class RegisterCourseCommand implements Command {
    private final Student student;
    private final CourseService courseService;
    private final Printer printer;
    
    public RegisterCourseCommand(Student student, CourseService courseService, Printer printer) {
        this.student = student;
        this.courseService = courseService;
        this.printer = printer;
    }
    
    public String name() { return "register"; }
    public String description() { return "Записаться на курс"; }
    
    public void execute(Scanner scanner) {
        printer.println("\n=== REGISTER FOR COURSE ===");
        printer.println("Available courses:");
        courseService.getAvailableFor(student).forEach(printer::println);
        
        printer.println("Enter course ID: ");
        String courseId = scanner.nextLine();
        
        var courseOpt = courseService.getById(courseId);
        if (courseOpt.isPresent()) {
            student.registerCourse(courseOpt.get());
        } else {
            printer.println(" Course not found");
        }
    }
}

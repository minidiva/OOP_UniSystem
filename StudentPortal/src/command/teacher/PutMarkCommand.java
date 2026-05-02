package command.teacher;

import java.util.Scanner;
import command.core.Command;
import domain.user.Teacher;
import domain.user.Student;
import service.CourseService;
import util.Printer;
import repository.Database;

public class PutMarkCommand implements Command {
    private final Teacher teacher;
    private final CourseService courseService;
    private final Database db;
    private final Printer printer;
    
    public PutMarkCommand(Teacher teacher, CourseService courseService, Database db, Printer printer) {
        this.teacher = teacher;
        this.courseService = courseService;
        this.db = db;
        this.printer = printer;
    }
    
    public String name() { return "mark"; }
    public String description() { return "Поставить оценку студенту"; }
    
    public void execute(Scanner scanner) {
        printer.println("\n=== PUT MARK ===");
        printer.println("Student email: ");
        String studentEmail = scanner.nextLine();
        
        Student student = (Student) db.getUsers().values().stream()
            .filter(u -> u.getEmail().equals(studentEmail) && u.getRole().isStudent())
            .findFirst()
            .orElse(null);
        
        if (student == null) {
            printer.println(" Student not found");
            return;
        }
        
        printer.println("Course ID: ");
        String courseId = scanner.nextLine();
        var courseOpt = courseService.getById(courseId);
        
        if (courseOpt.isEmpty()) {
            printer.println(" Course not found");
            return;
        }
        
        printer.println("First attestation (0-30): ");
        double first = Double.parseDouble(scanner.nextLine());
        printer.println("Second attestation (0-30): ");
        double second = Double.parseDouble(scanner.nextLine());
        printer.println("Final exam (0-40): ");
        double finalExam = Double.parseDouble(scanner.nextLine());
        
        teacher.putMark(student, courseOpt.get(), first, second, finalExam);
    }
}
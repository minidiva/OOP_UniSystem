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
        if (!scanner.hasNextLine()) {
            printer.println("Input closed.");
            return;
        }
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
        if (!scanner.hasNextLine()) {
            printer.println("Input closed.");
            return;
        }
        String courseId = scanner.nextLine();
        var courseOpt = courseService.getById(courseId);
        
        if (courseOpt.isEmpty()) {
            printer.println(" Course not found");
            return;
        }
        
        Double first = readDouble(scanner, "First attestation (0-30): ");
        if (first == null) return;
        Double second = readDouble(scanner, "Second attestation (0-30): ");
        if (second == null) return;
        Double finalExam = readDouble(scanner, "Final exam (0-40): ");
        if (finalExam == null) return;
        
        // teacher.putMark(student, courseOpt.get(), first, second, finalExam);
    }

    private Double readDouble(Scanner scanner, String prompt) {
        printer.println(prompt);
        if (!scanner.hasNextLine()) {
            printer.println("Input closed.");
            return null;
        }

        String raw = scanner.nextLine().trim();
        try {
            return Double.parseDouble(raw);
        } catch (NumberFormatException e) {
            printer.println(" Invalid number format: " + raw);
            return null;
        }
    }
}
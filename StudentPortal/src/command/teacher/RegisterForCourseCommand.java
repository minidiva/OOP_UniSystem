package command.teacher;

import java.util.Scanner;
import command.core.Command;
import domain.user.Teacher;
import service.CourseService;
import service.RegistrationService;
import util.Printer;

public class RegisterForCourseCommand implements Command {
    private final Teacher teacher;
    private final CourseService courseService;
    private final RegistrationService registrationService;
    private final Printer printer;

    public RegisterForCourseCommand(Teacher teacher, CourseService courseService, RegistrationService registrationService, Printer printer) {
        this.teacher = teacher;
        this.courseService = courseService;
        this.registrationService = registrationService;
        this.printer = printer;
    }

    @Override
    public String name() {
        return "register";
    }

    @Override
    public String description() {
        return "Send request for instruction specific course";
    }

    @Override
    public void execute(Scanner scanner) {
        printer.println("\n=== REGISTER FOR COURSE ===");
        printer.println("Available courses:");
        courseService.getAllCourses().forEach(printer::println);

        printer.println("Enter course ID: ");
        if (!scanner.hasNextLine()) {
            printer.println("Input closed.");
            return;
        }
        String courseId = scanner.nextLine().trim();

        var courseOpt = courseService.getById(courseId);
        if (courseOpt.isPresent()) {
            try {
                var request = registrationService.requestApproval(teacher, courseOpt.get());
                printer.println("Registration request created: " + request.getId());
            } catch (IllegalStateException e) {
                printer.println("Cannot request registration: " + e.getMessage());
            }
        } else {
            printer.println("Course not found.");
        }
    }
}
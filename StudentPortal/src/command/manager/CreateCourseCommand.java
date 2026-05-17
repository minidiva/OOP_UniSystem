package command.manager;

import java.util.Scanner;
import service.CourseService;
import domain.course.Course;
import command.core.Command;
import util.Printer;

public class CreateCourseCommand implements Command {

    private final CourseService courseService;
    private final Printer printer;

    public CreateCourseCommand(CourseService courseService, Printer printer) {
        this.courseService = courseService;
        this.printer = printer;
    }

    @Override
    public String name() {
        return "course_create";
    }

    @Override
    public String description() {
        return "Create a new course";
    }

    @Override
    public void execute(Scanner scanner) {
        printer.println("\n=== CREATE COURSE ===");

        printer.println("Title: ");
        if (!scanner.hasNextLine()) {
            printer.println("Input closed.");
            return;
        }
        String title = scanner.nextLine().trim();
        if (title.isEmpty()) {
            printer.println("Title cannot be empty.");
            return;
        }

        printer.println("Description: ");
        if (!scanner.hasNextLine()) {
            printer.println("Input closed.");
            return;
        }
        String description = scanner.nextLine().trim();

        printer.println("Credits: ");
        if (!scanner.hasNextLine()) {
            printer.println("Input closed.");
            return;
        }
        String creditsText = scanner.nextLine().trim();
        int credits;
        try {
            credits = Integer.parseInt(creditsText);
        } catch (NumberFormatException e) {
            printer.println("Invalid credits. Please enter a whole number.");
            return;
        }

        Course course = courseService.createCourse(title, description, credits);
        printer.println("Course created: " + course);
    }
}

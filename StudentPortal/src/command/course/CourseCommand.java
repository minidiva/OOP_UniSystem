package command.course;

import java.util.Scanner;
import command.core.Command;
import command.core.CommandWithArgs;
import domain.course.Course;
import domain.user.Manager;
import domain.user.Session;
import service.CourseService;
import util.Printer;

public class CourseCommand implements Command, CommandWithArgs {

    private final Session session;
    private final CourseService courseService;
    private final Printer printer;
    private String action;

    public CourseCommand(Session session, CourseService courseService, Printer printer) {
        this.session = session;
        this.courseService = courseService;
        this.printer = printer;
    }

    @Override
    public String name() {
        return "course";
    }

    @Override
    public String description() {
        return "Course operations: create, update, delete, view";
    }

    @Override
    public void setArguments(String args) {
        this.action = args == null ? "" : args.trim().toLowerCase();
    }

    @Override
    public void execute(Scanner scanner) {
        if (action == null || action.isBlank()) {
            printer.println("Usage: course [create|view|update|delete]");
            return;
        }

        String verb = action.split("\\s+")[0];
        switch (verb) {
            case "create" -> createCourse(scanner);
            case "view" -> viewCourses();
            case "update" -> updateCourse(scanner);
            case "delete" -> deleteCourse(scanner);
            default -> printer.println("Unknown course action: " + verb + ". Use create, view, update or delete.");
        }
    }

    private void viewCourses() {
        printer.println("\n=== COURSES ===");
        printer.printList(courseService.getAllCourses());
    }

    private void createCourse(Scanner scanner) {
        if (!isManager()) {
            printer.println("Permission denied. Only managers can create courses.");
            return;
        }

        printer.println("\n=== CREATE COURSE ===");
        String title = readLine(scanner, "Title: ");
        if (title == null || title.isBlank()) return;

        String description = readLine(scanner, "Description: ");
        if (description == null) return;

        Integer credits = readInt(scanner, "Credits: ");
        if (credits == null) return;

        Course course = courseService.createCourse(title, description, credits);
        printer.println("Course created: " + course);
    }

    private void updateCourse(Scanner scanner) {
        if (!isManager()) {
            printer.println("Permission denied. Only managers can update courses.");
            return;
        }

        printer.println("\n=== UPDATE COURSE ===");
        Integer courseId = readInt(scanner, "Course ID: ");
        if (courseId == null) return;

        var courseOpt = courseService.getById(String.valueOf(courseId));
        if (courseOpt.isEmpty()) {
            printer.println(" Course not found");
            return;
        }

        String title = readLine(scanner, "New title: ");
        if (title == null) return;

        String description = readLine(scanner, "New description: ");
        if (description == null) return;

        Integer credits = readInt(scanner, "New credits: ");
        if (credits == null) return;

        var updated = courseService.updateCourse(courseId, title, description, credits);
        updated.ifPresentOrElse(
            c -> printer.println("Course updated: " + c),
            () -> printer.println("Unable to update course."
        ));
    }

    private void deleteCourse(Scanner scanner) {
        if (!isManager()) {
            printer.println("Permission denied. Only managers can delete courses.");
            return;
        }

        printer.println("\n=== DELETE COURSE ===");
        Integer courseId = readInt(scanner, "Course ID: ");
        if (courseId == null) return;

        boolean removed = courseService.deleteCourse(courseId);
        if (removed) {
            printer.println("Course deleted: " + courseId);
        } else {
            printer.println(" Course not found");
        }
    }

    private String readLine(Scanner scanner, String prompt) {
        printer.println(prompt);
        if (!scanner.hasNextLine()) {
            printer.println("Input closed.");
            return null;
        }
        return scanner.nextLine().trim();
    }

    private Integer readInt(Scanner scanner, String prompt) {
        String raw = readLine(scanner, prompt);
        if (raw == null) return null;
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            printer.println("Invalid number: " + raw);
            return null;
        }
    }

    private boolean isManager() {
        return session.isAuthenticated() && session.getCurrentUser() instanceof Manager;
    }
}

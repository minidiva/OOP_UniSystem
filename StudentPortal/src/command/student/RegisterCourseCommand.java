package command.student;

import java.util.List;
import java.util.Scanner;
import command.core.Command;
import domain.course.Course;
import domain.user.Student;
import domain.user.Teacher;
import service.CourseService;
import service.RegistrationService;
import service.UserService;
import util.Printer;

public class RegisterCourseCommand implements Command {
    private final Student student;
    private final CourseService courseService;
    private final RegistrationService registrationService;
    private final UserService userService;
    private final Printer printer;

    public RegisterCourseCommand(Student student, CourseService courseService,
                                  RegistrationService registrationService,
                                  UserService userService, Printer printer) {
        this.student = student;
        this.courseService = courseService;
        this.registrationService = registrationService;
        this.userService = userService;
        this.printer = printer;
    }

    @Override
    public String name() { return "register"; }

    @Override
    public String description() { return "Записаться на курс"; }

    @Override
    public void execute(Scanner scanner) {
        printer.println("\n=== REGISTER FOR COURSE ===");

        // 1. Показываем доступные курсы
        List<Course> available = courseService.getAvailableFor(student);
        if (available.isEmpty()) {
            printer.println("No courses available for registration.");
            return;
        }
        printer.println("Available courses:");
        for (int i = 0; i < available.size(); i++) {
            Course c = available.get(i);
            printer.println("  " + (i + 1) + ". " + c);
        }
        printer.println("Enter course number: ");
        Course selectedCourse = pickFromList(scanner, available);
        if (selectedCourse == null) return;

        // 2. Показываем учителей которые ведут этот курс
        List<Teacher> teachers = userService.getTeachersForCourse(selectedCourse);
        Teacher selectedTeacher = null;
        if (teachers.isEmpty()) {
            printer.println("No teachers assigned to this course yet.");
        } else {
            printer.println("\nTeachers for " + selectedCourse.getTitle() + ":");
            for (int i = 0; i < teachers.size(); i++) {
                Teacher t = teachers.get(i);
                printer.println("  " + (i + 1) + ". " + t.getFullName()
                        + " (" + t.getTitle() + ")"
                        + (t.getAverageRating() > 0
                            ? " — rating: " + String.format("%.1f", t.getAverageRating())
                            : ""));
            }
            printer.println("Enter teacher number (or 0 to skip): ");
            if (!scanner.hasNextLine()) { printer.println("Input closed."); return; }
            String raw = scanner.nextLine().trim();
            try {
                int idx = Integer.parseInt(raw);
                if (idx > 0 && idx <= teachers.size()) {
                    selectedTeacher = teachers.get(idx - 1);
                }
            } catch (NumberFormatException e) {
                printer.println("Invalid input, skipping teacher selection.");
            }
        }

        // 3. Отправляем заявку
        try {
            var request = registrationService.requestApproval(student, selectedCourse);
            printer.println("Registration request created: " + request.getId());
            if (selectedTeacher != null) {
                printer.println("Preferred teacher: " + selectedTeacher.getFullName());
            }
        } catch (IllegalStateException e) {
            printer.println("Cannot request registration: " + e.getMessage());
        }
    }

    private <T> T pickFromList(Scanner scanner, List<T> list) {
        if (!scanner.hasNextLine()) { printer.println("Input closed."); return null; }
        String raw = scanner.nextLine().trim();
        try {
            int index = Integer.parseInt(raw) - 1;
            if (index < 0 || index >= list.size()) {
                printer.println("Invalid number.");
                return null;
            }
            return list.get(index);
        } catch (NumberFormatException e) {
            printer.println("Invalid input.");
            return null;
        }
    }
}
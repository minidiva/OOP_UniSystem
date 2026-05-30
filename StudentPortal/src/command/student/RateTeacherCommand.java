package command.student;

import java.util.Scanner;
import command.core.Command;
import domain.user.Student;
import domain.user.Teacher;
import service.UserService;
import util.Printer;

public class RateTeacherCommand implements Command {
    private final UserService userService;
    private final Printer printer;

    public RateTeacherCommand(Student student, UserService userService, Printer printer) {
        this.userService = userService;
        this.printer = printer;
    }

    @Override
    public String name() {
        return "rate";
    }

    @Override
    public String description() {
        return "Rate a teacher";
    }

    @Override
    public void execute(Scanner scanner) {
        printer.println("\n=== RATE TEACHER ===");
        printer.println("Teacher email: ");
        if (!scanner.hasNextLine()) {
            printer.println("Input closed.");
            return;
        }
        String email = scanner.nextLine().trim();
        var optional = userService.findByEmail(email);
        if (optional.isEmpty() || !(optional.get() instanceof Teacher)) {
            printer.println("Teacher not found.");
            return;
        }
        Teacher teacher = (Teacher) optional.get();
        printer.println("Rating (1-5): ");
        if (!scanner.hasNextLine()) {
            printer.println("Input closed.");
            return;
        }
        String raw = scanner.nextLine().trim();
        try {
            int rating = Integer.parseInt(raw);
            if (rating < 1 || rating > 5) {
                printer.println("Rating must be 1-5.");
                return;
            }
            teacher.addRating(rating);
            printer.println("Thank you! " + teacher.getFullName() + " now has average rating " + String.format("%.2f", teacher.getAverageRating()));
        } catch (NumberFormatException e) {
            printer.println("Invalid number.");
        }
    }
}

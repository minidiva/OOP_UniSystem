package command.teacher;

import java.util.List;
import java.util.Scanner;
import command.core.Command;
import domain.course.Course;
import domain.user.Teacher;
import domain.user.Student;
import service.CourseService;
import service.UserService;
import util.Printer;

public class PutMarkCommand implements Command {
    private final Teacher teacher;
    private final CourseService courseService;
    private final UserService userService;
    private final Printer printer;

    public PutMarkCommand(Teacher teacher, CourseService courseService, UserService userService, Printer printer) {
        this.teacher = teacher;
        this.courseService = courseService;
        this.userService = userService;
        this.printer = printer;
    }

    @Override
    public String name() { return "mark"; }

    @Override
    public String description() { return "Поставить оценку студенту"; }

    @Override
    public void execute(Scanner scanner) {
        printer.println("\n=== PUT MARK ===");

        // 1. Показываем только курсы которые ведёт этот учитель
        List<Course> myCourses = teacher.getTeachingCourses();
        if (myCourses.isEmpty()) {
            printer.println("You are not assigned to any courses.");
            return;
        }
        printer.println("Your courses:");
        for (int i = 0; i < myCourses.size(); i++) {
            Course c = myCourses.get(i);
            printer.println("  " + (i + 1) + ". [" + c.getId() + "] " + c.getTitle());
        }
        printer.println("Enter course number: ");
        Course selectedCourse = pickFromList(scanner, myCourses);
        if (selectedCourse == null) return;

        // 2. Показываем студентов записанных на этот курс
        List<Student> enrolled = userService.getStudentsEnrolledIn(selectedCourse);
        if (enrolled.isEmpty()) {
            printer.println("No students enrolled in " + selectedCourse.getTitle() + ".");
            return;
        }
        printer.println("\nStudents enrolled in " + selectedCourse.getTitle() + ":");
        for (int i = 0; i < enrolled.size(); i++) {
            Student s = enrolled.get(i);
            printer.println("  " + (i + 1) + ". " + s.getFullName() + " (" + s.getEmail() + ")");
        }
        printer.println("Enter student number: ");
        Student selectedStudent = pickFromList(scanner, enrolled);
        if (selectedStudent == null) return;

        // 3. Вводим оценки
        Double first = readDouble(scanner, "First attestation (0-30): ");
        if (first == null) return;
        Double second = readDouble(scanner, "Second attestation (0-30): ");
        if (second == null) return;
        Double finalExam = readDouble(scanner, "Final exam (0-40): ");
        if (finalExam == null) return;

        teacher.putMark(selectedStudent, selectedCourse, first, second, finalExam);
        printer.println("Mark saved for " + selectedStudent.getFullName() + ".");
    }

    // Выбор элемента из списка по номеру
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

    private Double readDouble(Scanner scanner, String prompt) {
        printer.println(prompt);
        if (!scanner.hasNextLine()) { printer.println("Input closed."); return null; }
        String raw = scanner.nextLine().trim();
        try {
            return Double.parseDouble(raw);
        } catch (NumberFormatException e) {
            printer.println("Invalid number: " + raw);
            return null;
        }
    }
}
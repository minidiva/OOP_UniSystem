package command.student;

import util.Printer;
import java.util.*;
import service.CourseService;
import domain.user.Student;
import command.core.Command;

public class ViewCoursesCommand implements Command {

    private final CourseService courseService;
    private final Student student;
    private final Printer printer;

    public ViewCoursesCommand(CourseService cs, Student student, Printer printer) {
        this.courseService = cs;
        this.student = student;
        this.printer = printer;
    }

    @Override
    public String name() { return "courses"; }
    
    @Override
    public String description() { 
        return student != null ? "Просмотреть доступные курсы" : "Просмотреть все курсы"; 
    }

    @Override
    public void execute(Scanner scanner) {
        printer.println("\n=== COURSES ===");
        if (student != null) {
            printer.printList(courseService.getAvailableFor(student));
        } else {
            printer.printList(courseService.getAllCourses());
        }
    }
}
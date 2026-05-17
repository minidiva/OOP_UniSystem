package command.student;
import util.Printer;

import java.util.*;
import service.CourseService;
import domain.user.*;
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

    public String name() { return "courses"; }
    public String description() { return "Просмотреть курсы"; }

    public void execute(Scanner scanner) {
        printer.printList(courseService.getAvailableFor(student));
    }
}
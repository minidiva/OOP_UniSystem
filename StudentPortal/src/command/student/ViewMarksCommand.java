package command.student;

import java.util.Scanner;
import command.core.Command;
import domain.user.Student;
import util.Printer;

public class ViewMarksCommand implements Command {
    private final Student student;
    private final Printer printer;
    
    public ViewMarksCommand(Student student, Printer printer) {
        this.student = student;
        this.printer = printer;
    }
    
    @Override
    public String name() { 
        return "marks"; 
    }
    
    @Override
    public String description() { 
        return "Посмотреть свои оценки"; 
    }
    
    @Override
    public void execute(Scanner scanner) {
        printer.println("\n=== MY MARKS ===");
        if (student.getMarks().isEmpty()) {
            printer.println("No marks yet.");
        } else {
            student.getMarks().forEach((course, mark) -> 
                printer.println(course.getTitle() + ": " + mark)
            );
        }
    }
}
package command.student;

import java.util.Scanner;
import command.core.Command;
import domain.user.Student;
import util.Printer;

public class ViewTranscriptCommand implements Command {
    private final Student student;
    private final Printer printer;

    public ViewTranscriptCommand(Student student, Printer printer) {
        this.student = student;
        this.printer = printer;
    }

    @Override
    public String name() {
        return "transcript";
    }

    @Override
    public String description() {
        return "View academic transcript and GPA";
    }

    @Override
    public void execute(Scanner scanner) {
        printer.println("\n=== TRANSCRIPT ===");
        printer.println(student.getTranscript());
    }
}

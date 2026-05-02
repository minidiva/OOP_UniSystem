package app;
import java.util.*;
import util.Printer;
import command.core.*;
import domain.course.Course;
import domain.user.Student;
import domain.user.User;
import repository.CourseRepository;
import config.CommandConfigurator;
import service.CourseService;
import ui.Menu;


public class App {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Printer printer = new Printer();

        CommandRegistry registry = new CommandRegistry();
        CommandManager manager = new CommandManager();
        CourseRepository courseRepository = new CourseRepository();

        courseRepository.save(new Course(1, "Introduction to Networks", "Introductory course for freshmen", 6));

        User user = new Student();
        Menu menu = new Menu(registry, printer);

        CommandConfigurator.configure(
            user, registry, manager,
            menu,
            new CourseService(courseRepository),
            printer
        );


        menu.show();
        while (true) {

            String input = scanner.nextLine();

            Command cmd = registry.get(input);

            if (cmd == null) {
                printer.println("");
                continue;
            }

            manager.execute(cmd, scanner);
        }
    }
}
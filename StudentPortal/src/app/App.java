package app;

import java.util.*;
import util.Printer;
import command.core.*;
import command.system.LoginCommand;
import domain.course.Course;
import domain.user.Session;
import repository.CourseRepository;
import config.CommandConfigurator;
import service.CourseService;
import ui.Menu;
import repository.Database;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Printer printer = new Printer();

        CommandRegistry registry = new CommandRegistry();
        CommandManager manager = new CommandManager();
        CourseRepository courseRepository = new CourseRepository();


        // Инициализация базы данных
        Database db = Database.getInstance();
        db.load();              
        db.initTestData();      
        
        printer.println("\n Database has " + db.getUsers().size() + " users:");
        db.getUsers().values().forEach(u -> 
            printer.println("   - " + u.getEmail() + " (password: " + u.getPassword() + ")")
        );
        printer.println(" Courses: " + db.getCourses().size() + "\n");

        courseRepository.save(new Course(1, "Introduction to Networks", "Introductory course for freshmen", 6));

        Session session = new Session();
        Menu menu = new Menu(registry, printer);
        CourseService courseService = new CourseService(courseRepository);
        LoginCommand loginCommand = new LoginCommand(db, printer, session);

        CommandConfigurator.configure(
            session, registry, manager,
            menu,
            courseService,
            printer,
            loginCommand
        );

        menu.show();
        while (true) {
            System.out.print("\n> ");
            String input = scanner.nextLine().trim().toLowerCase();

            Command cmd = registry.get(input);

            if (cmd == null) {
                printer.println(" Unknown command. Type 'help' for available commands.");
                continue;
            }

            manager.execute(cmd, scanner);

            if (cmd == loginCommand && session.isAuthenticated()) {
                registry.clear();
                CommandConfigurator.configure(
                    session, registry, manager,
                    menu,
                    courseService,
                    printer,
                    loginCommand
                );
                menu.show();
            }
        }
    }
}
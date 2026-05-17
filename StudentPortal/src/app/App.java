package app;

import java.util.*;
import util.Printer;
import command.core.*;
import command.system.LoginCommand;
import domain.user.Session;
import repository.CourseRepository;
import repository.UserRepository;
import repository.Database;
import config.CommandConfigurator;
import service.CourseService;
import service.UserService;
import service.RegistrationService;
import service.ResearchService;
import ui.Menu;
import bootstrap.DataSeeder;
import storage.DatabaseStorage;

public class App {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Printer printer = new Printer();

        CommandRegistry registry = new CommandRegistry();
        CommandManager commandManager = new CommandManager();
        CourseRepository courseRepository = new CourseRepository();
        UserRepository userRepository = new UserRepository();

        Database db = Database.getInstance();
        DatabaseStorage.load().ifPresent(db::replaceWith);
        if (db.isEmpty()) {
            DataSeeder.bootstrap(db);
        }

        printer.println("\n Database has " + db.getUsers().size() + " users:");
        db.getUsers().values().forEach(u ->
            printer.println("   - " + u.getEmail() + " (password: " + u.getPassword() + ")")
        );
        printer.println(" Courses: " + db.getCourses().size() + "\n");

        Session session = new Session();
        Menu menu = new Menu(registry, printer, session);
        CourseService courseService = new CourseService(courseRepository);
        UserService userService = new UserService(userRepository);
        RegistrationService registrationService = new RegistrationService();
        ResearchService researchService = new ResearchService();
        LoginCommand loginCommand = new LoginCommand(userService, printer, session);

        CommandConfigurator.configure(
            session,
            registry,
            commandManager,
            menu,
            courseService,
            userService,
            registrationService,
            researchService,
            printer,
            loginCommand
        );

        menu.show();
        while (scanner.hasNextLine()) {
            System.out.print("\n> ");
            String rawInput = scanner.nextLine().trim().toLowerCase();
            String commandKey = rawInput;
            String commandArgs = "";

            Command cmd = registry.get(commandKey);
            if (cmd == null && rawInput.contains(" ")) {
                int firstSpace = rawInput.indexOf(' ');
                commandKey = rawInput.substring(0, firstSpace);
                commandArgs = rawInput.substring(firstSpace + 1).trim();
                cmd = registry.get(commandKey);
            }

            if (cmd == null) {
                printer.println(session.getCurrentLanguage().get("message.unknown_command"));
                continue;
            }

            if (cmd instanceof CommandWithArgs) {
                CommandWithArgs argCmd = (CommandWithArgs) cmd;
                argCmd.setArguments(commandArgs);
            }

            commandManager.execute(cmd, scanner);

            if (cmd == loginCommand && session.isAuthenticated()) {
                registry.clear();
                CommandConfigurator.configure(
                    session,
                    registry,
                    commandManager,
                    menu,
                    courseService,
                    userService,
                    registrationService,
                    researchService,
                    printer,
                    loginCommand
                );
                menu.show();
            }
        }

        DatabaseStorage.save(db);
        printer.println("Input closed. Exiting.");
        scanner.close();
    }
}
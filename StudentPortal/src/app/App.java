package app;

import java.util.*;
import util.Printer;
import command.core.*;
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
        
        // Инициализация базы данных
        Database db = Database.getInstance();   
        db.load();
        db.initTestData();
        
        CourseRepository courseRepository = new CourseRepository();
        CourseService courseService = new CourseService(courseRepository);
        
        Menu menu = new Menu(registry, printer);
        
        CommandConfigurator.configure(null, registry, manager, menu, courseService, printer);
        
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
        }
    }
}
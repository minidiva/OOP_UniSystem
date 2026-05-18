package command.system;

import java.util.Scanner;
import command.core.Command;
import command.core.CommandRegistry;
import command.core.CommandManager;
import service.CourseService;
import ui.Menu;
import util.Printer;
import domain.user.Session;
import repository.Database;

public class LogoutCommand implements Command {
    private final CommandRegistry registry;
    private final CommandManager manager;
    private final Menu menu;
    private final CourseService courseService;
    private final Printer printer;
    private final Session session;
    
    public LogoutCommand(CommandRegistry registry, CommandManager manager, 
                         Menu menu, CourseService courseService, 
                         Printer printer, Session session) {
        this.registry = registry;
        this.manager = manager;
        this.menu = menu;
        this.courseService = courseService;
        this.printer = printer;
        this.session = session;
    }
    
    @Override
    public String name() {
        return "logout";
    }
    
    @Override
    public String description() {
        return "Выйти из аккаунта";
    }
    
    @Override
    public void execute(Scanner scanner) {
        printer.println("\n Logging out...");
        
  
        session.logout();
        
        registry.clear();
        
        Database db = Database.getInstance();
        registry.register(new ShowMenuCommand(menu, session));
        registry.register(new UndoCommand(manager, printer));
        registry.register(new RedoCommand(manager, printer));
        registry.register(new LoginCommand(db, printer, registry, manager, menu, courseService));
        
        menu.show();
        printer.println(" You have been logged out. Type 'login' to sign in again.");
    }
}
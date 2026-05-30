package command.system;

import java.util.Scanner;
import command.core.Command;
import command.core.CommandManager;
import command.core.CommandRegistry;
import repository.Database;
import domain.user.User;
import domain.user.Session;
import service.CourseService;
import service.UserService;
import service.RegistrationService;
import service.ResearchService;
import ui.Menu;
import util.Printer;
import config.CommandConfigurator;

public class LoginCommand implements Command {
    private final Database db;
    private final Printer printer;
    private final CommandRegistry registry;
    private final CommandManager manager;
    private final Menu menu;
    private final CourseService courseService;
    private final Session session;
    private final UserService userService;
    private final RegistrationService registrationService;
    private final ResearchService researchService;
    private User currentUser;
    
    public LoginCommand(UserService userService, Printer printer, Session session) {
        this.db = Database.getInstance();
        this.printer = printer;
        this.session = session;
        this.userService = userService;
        this.registry = null;
        this.manager = null;
        this.menu = null;
        this.courseService = null;
        this.registrationService = null;
        this.researchService = null;
    }
    
    public LoginCommand(Database db, Printer printer, CommandRegistry registry, 
                        CommandManager manager, Menu menu, CourseService courseService,
                        Session session, UserService userService, 
                        RegistrationService registrationService, ResearchService researchService) {
        this.db = db;
        this.printer = printer;
        this.registry = registry;
        this.manager = manager;
        this.menu = menu;
        this.courseService = courseService;
        this.session = session;
        this.userService = userService;
        this.registrationService = registrationService;
        this.researchService = researchService;
    }
    
    @Override
    public String name() { 
        return "login"; 
    }
    
    @Override
    public String description() { 
        return "Вход в систему"; 
    }
    
    @Override
    public void execute(Scanner scanner) {
        printer.println("\n=== LOGIN ===");
        
        printer.println("Email: ");
        String email = scanner.nextLine();
        
        printer.println("Password: ");
        String password = scanner.nextLine();
        
        currentUser = findUserByEmailAndPassword(email, password);
        
        if (currentUser != null) {
            printer.println(" Welcome, " + currentUser.getFullName() + "!");
            printer.println("   Role: " + currentUser.getRole());
            
            if (session != null) {
                session.setCurrentUser(currentUser);
            }
            
            if (registry != null && manager != null && menu != null && courseService != null && session != null) {
                registry.clear();
                CommandConfigurator.configure(session, registry, manager, menu, courseService, 
                                             userService, registrationService, researchService, printer, this);
                menu.show();
            } else {
               
                printer.println("\n=== Available commands ===");
                printer.println("help - Show available commands");
                printer.println("logout - Logout");
                printer.println("users - Manage users");
                printer.println("logs - View logs");
            }
        } else {
            printer.println(" Invalid email or password");
        }
    }
    
    private User findUserByEmailAndPassword(String email, String password) {
        return db.getUsers().values().stream()
            .filter(user -> user.getEmail().equals(email))
            .filter(user -> user.getPassword().equals(password))
            .findFirst()
            .orElse(null);
    }
    
    public User getCurrentUser() { 
        return currentUser; 
    }
}
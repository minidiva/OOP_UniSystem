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
    private User currentUser;
    
    public LoginCommand(Database db, Printer printer, CommandRegistry registry, 
                        CommandManager manager, Menu menu, CourseService courseService) {
        this.db = db;
        this.printer = printer;
        this.registry = registry;
        this.manager = manager;
        this.menu = menu;
        this.courseService = courseService;
        this.session = null;
        this.userService = null;
    }
    
    public LoginCommand(Database db, Printer printer, Session session) {
        this.db = db;
        this.printer = printer;
        this.session = session;
        this.registry = null;
        this.manager = null;
        this.menu = null;
        this.courseService = null;
        this.userService = null;
    }
    
    // Конструктор 3: для использования в App (с UserService)
    public LoginCommand(UserService userService, Printer printer, Session session) {
        this.db = Database.getInstance();
        this.printer = printer;
        this.session = session;
        this.userService = userService;
        this.registry = null;
        this.manager = null;
        this.menu = null;
        this.courseService = null;
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
        
        // Поиск пользователя в базе
        currentUser = findUserByEmailAndPassword(email, password);
        
        if (currentUser != null) {
            printer.println(" Welcome, " + currentUser.getFullName() + "!");
            printer.println("   Role: " + currentUser.getRole());
            
            if (session != null) {
                session.setCurrentUser(currentUser);
            }
            
            // Обновляем команды после логина
            if (registry != null && manager != null && menu != null && courseService != null) {
                registry.clear();
                CommandConfigurator.configure(session, registry, manager, menu, courseService, 
                                             null, null, null, printer, this);
                menu.show();
            }
        } else {
            printer.println(" Invalid email or password");
            printer.println("   Hint: Use test@email.com / 1234567");
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
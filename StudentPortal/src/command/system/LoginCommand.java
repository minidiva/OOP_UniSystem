package command.system;

import java.util.Scanner;
import command.core.Command;
import repository.Database;
import domain.user.Session;
import domain.user.User;
import util.Printer;

public class LoginCommand implements Command {
    private final Database db;
    private final Printer printer;
    private final Session session;
    private User currentUser;
    
    public LoginCommand(Database db, Printer printer, Session session) {
        this.db = db;
        this.printer = printer;
        this.session = session;
    }
    
    @Override
    public String name() {
        return "login";
    }
    
    @Override
    public String description() {
        return session.getCurrentLanguage().get("command.login.description");
    }
    
    @Override
    public void execute(Scanner scanner) {
        printer.println("\n=== LOGIN ===");
        
        printer.println("Email: ");
        if (!scanner.hasNextLine()) {
            printer.println("Input closed.");
            return;
        }
        String email = scanner.nextLine();
        
        printer.println("Password: ");
        if (!scanner.hasNextLine()) {
            printer.println("Input closed.");
            return;
        }
        String password = scanner.nextLine();
        
        currentUser = findUserByEmailAndPassword(email, password);
        
        if (currentUser != null) {
            session.setCurrentUser(currentUser);
            printer.println("Welcome, " + currentUser.getFullName() + "! Role: " + currentUser.getRole());
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
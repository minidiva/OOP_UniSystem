package command.system;

import java.util.Scanner;
import command.core.Command;
import repository.Database;
import domain.user.User;
import util.Printer;

public class LoginCommand implements Command {
    private final Database db;
    private final Printer printer;
    private User currentUser;
    
    public LoginCommand(Database db, Printer printer) {
        this.db = db;
        this.printer = printer;
    }
    
    public String name() { return "login"; }
    public String description() { return "Вход в систему"; }
    
    public void execute(Scanner scanner) {
        printer.println("\n=== LOGIN ===");
        printer.println("Email: ");
        String email = scanner.nextLine();
        printer.println("Password: ");
        String password = scanner.nextLine();
        
        currentUser = db.getUsers().values().stream()
            .filter(u -> u.login(email, password))
            .findFirst()
            .orElse(null);
        
        if (currentUser != null) {
            printer.println("Welcome, " + currentUser.getFullName() + "! Role: " + currentUser.getRole());
        } else {
            printer.println("Invalid email or password");
        }
    }
    
    public User getCurrentUser() { return currentUser; }
}

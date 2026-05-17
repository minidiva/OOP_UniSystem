package command.admin;

import java.util.Scanner;
import command.core.Command;
import command.core.CommandWithArgs;
import domain.user.Admin;
import domain.user.Role;
import domain.user.Student;
import domain.user.Teacher;
import domain.user.Manager;
import domain.user.Title;
import service.UserService;
import util.Printer;

public class UserCommand implements Command, CommandWithArgs {
    private final Admin admin;
    private final UserService userService;
    private final Printer printer;
    private String action;

    public UserCommand(Admin admin, UserService userService, Printer printer) {
        this.admin = admin;
        this.userService = userService;
        this.printer = printer;
    }

    @Override
    public String name() {
        return "user";
    }

    @Override
    public String description() {
        return "Manage users: create, list, delete";
    }

    @Override
    public void setArguments(String args) {
        this.action = args == null ? "" : args.trim().toLowerCase();
    }

    @Override
    public void execute(Scanner scanner) {
        if (!admin.getRole().isAdmin()) {
            printer.println("Permission denied.");
            return;
        }
        if (action.startsWith("create")) {
            createUser(scanner);
        } else if (action.startsWith("delete")) {
            deleteUser(scanner);
        } else if (action.startsWith("list")) {
            listUsers();
        } else {
            printer.println("Usage: user [create|list|delete]");
        }
    }

    private void createUser(Scanner scanner) {
        printer.println("Enter role [student, teacher, manager, admin]: ");
        String role = scanner.hasNextLine() ? scanner.nextLine().trim().toLowerCase() : null;
        if (role == null) return;
        printer.println("ID: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        printer.println("First name: ");
        String first = scanner.nextLine().trim();
        printer.println("Last name: ");
        String last = scanner.nextLine().trim();
        printer.println("Email: ");
        String email = scanner.nextLine().trim();
        printer.println("Password: ");
        String password = scanner.nextLine().trim();

        switch (role) {
            case "student" -> {
                printer.println("Year: ");
                int year = Integer.parseInt(scanner.nextLine().trim());
                printer.println("Major: ");
                String major = scanner.nextLine().trim();
                userService.save(new Student(id, first, last, email, password, year, major));
            }
            case "teacher" -> {
                printer.println("Title [PROFESSOR,SENIOR_LECTURER,LECTURER,TUTOR]: ");
                Title title = Title.valueOf(scanner.nextLine().trim().toUpperCase());
                printer.println("Salary: ");
                double salary = Double.parseDouble(scanner.nextLine().trim());
                userService.save(new Teacher(id, first, last, email, password, title, salary));
            }
            case "manager" -> {
                printer.println("Manager type: ");
                String type = scanner.nextLine().trim();
                printer.println("Salary: ");
                double salary = Double.parseDouble(scanner.nextLine().trim());
                userService.save(new Manager(id, first, last, email, password, type, salary));
            }
            case "admin" -> {
                printer.println("Access level: ");
                String access = scanner.nextLine().trim();
                userService.save(new Admin(id, first, last, email, password, access));
            }
            default -> printer.println("Unknown role.");
        }
    }

    private void deleteUser(Scanner scanner) {
        printer.println("Email to delete: ");
        if (!scanner.hasNextLine()) {
            printer.println("Input closed.");
            return;
        }
        String email = scanner.nextLine().trim();
        if (userService.deleteByEmail(email)) {
            printer.println("Deleted " + email);
        } else {
            printer.println("User not found.");
        }
    }

    private void listUsers() {
        userService.getAllUsers().forEach(printer::println);
    }
}

package config;

import util.Printer;
import command.core.CommandRegistry;
import command.student.ViewCoursesCommand;
import command.student.RegisterCourseCommand;
import command.student.ViewMarksCommand;
import command.teacher.PutMarkCommand;
import command.core.CommandManager;
import domain.user.User;
import domain.user.Student;
import domain.user.Teacher;
import domain.user.Manager;
import domain.user.Admin;
import service.CourseService;
import command.system.*;
import ui.Menu;
import repository.Database;


public class CommandConfigurator {

    public static void configure(
            User user,
            CommandRegistry registry,
            CommandManager cmdManager,     // ← переименовано, чтобы не путать
            Menu menu,
            CourseService cs,
            Printer printer
    ) {
        Database db = Database.getInstance();
        
        // ===== ОБЩИЕ КОМАНДЫ ДЛЯ ВСЕХ =====
        registry.register(new UndoCommand(cmdManager, printer));
        registry.register(new RedoCommand(cmdManager, printer));
        registry.register(new ShowMenuCommand(menu));
        registry.register(new LoginCommand(db, printer, registry, cmdManager, menu, cs));
        
        if (user == null) {
            return;
        }
        
        // ===== КОМАНДЫ ДЛЯ СТУДЕНТА =====
        if (user instanceof Student student) {
            registry.register(new ViewCoursesCommand(cs, student, printer));
            registry.register(new RegisterCourseCommand(student, cs, printer));
            registry.register(new ViewMarksCommand(student, printer));
        }
        
        // ===== КОМАНДЫ ДЛЯ ПРЕПОДАВАТЕЛЯ =====
        else if (user instanceof Teacher teacher) {
            registry.register(new ViewCoursesCommand(cs, null, printer));
            registry.register(new PutMarkCommand(teacher, cs, db, printer));
        }
        
        // ===== КОМАНДЫ ДЛЯ МЕНЕДЖЕРА =====
        else if (user instanceof Manager manager) {
            registry.register(new ViewCoursesCommand(cs, null, printer));
            printer.println("ℹ️ Manager commands coming soon: addcourse, assign, approve");
        }
        
        // ===== КОМАНДЫ ДЛЯ АДМИНИСТРАТОРА =====
        else if (user instanceof Admin admin) {
            printer.println("ℹ️ Admin commands coming soon: users, logs");
        }
    }
}
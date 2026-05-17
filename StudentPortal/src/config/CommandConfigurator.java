package config;

import util.Printer;
import command.core.CommandRegistry;
import command.student.ViewCoursesCommand;
import command.student.RegisterCourseCommand;
import command.student.ViewMarksCommand;
import command.teacher.PutMarkCommand;
import command.core.CommandManager;
import domain.user.Session;
import domain.user.User;
import domain.user.Student;
import domain.user.Teacher;
import service.CourseService;
import command.system.*;
import ui.Menu;
import repository.Database;


public class CommandConfigurator {

    public static void configure(
            Session session,
            CommandRegistry registry,
            CommandManager cmdManager,     // ← переименовано, чтобы не путать
            Menu menu,
            CourseService cs,
            Printer printer,
            LoginCommand loginCommand
    ) {
        Database db = Database.getInstance();
        
        registry.register(new UndoCommand(cmdManager, printer));
        registry.register(new RedoCommand(cmdManager, printer));
        registry.register(new ShowMenuCommand(menu, session));
        registry.register(new ChangeLanguageCommand(session, printer));
        registry.register(loginCommand);

        if (session.isAuthenticated()) {
            User user = session.getCurrentUser();

            if (user instanceof Student) {
                Student student = (Student) user;
                registry.register(new ViewCoursesCommand(cs, student, printer));
                registry.register(new RegisterCourseCommand(student, cs, printer));
                registry.register(new ViewMarksCommand(student, printer));
            }
            
            if (user instanceof Teacher) {
                Teacher teacher = (Teacher) user;
                registry.register(new PutMarkCommand(teacher, cs, db, printer));
            }
        }
    }
}
package config;

import util.Printer;
import command.core.CommandRegistry;
import command.student.ViewCoursesCommand;
import command.student.RegisterCourseCommand;
import command.core.CommandManager;
import domain.user.Session;
import service.CourseService;
import command.system.*;
import domain.user.*;
import ui.Menu;
import repository.Database;
import command.teacher.PutMarkCommand;

public class CommandConfigurator {

    public static void configure(
            Session session,
            CommandRegistry registry,
            CommandManager manager,
            Menu menu,
            CourseService cs,
            Printer printer,
            LoginCommand loginCommand
    ) {
        Database db = Database.getInstance();
        
        registry.register(new UndoCommand(manager, printer));
        registry.register(new RedoCommand(manager, printer));
        registry.register(new ShowMenuCommand(menu));
        registry.register(loginCommand);

        if (session.isAuthenticated()) {
            User user = session.getCurrentUser();

            if (user instanceof Student student) {
                registry.register(new ViewCoursesCommand(cs, student, printer));
                registry.register(new RegisterCourseCommand(student, cs, printer));
            }
            
            if (user instanceof Teacher teacher) {
                registry.register(new PutMarkCommand(teacher, cs, db, printer));
            }
        }
    }
}
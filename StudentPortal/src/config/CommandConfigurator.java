package config;
import util.Printer;
import command.core.CommandRegistry;
import command.student.ViewCoursesCommand;
import command.core.CommandManager;
import domain.user.User;
import config.CommandConfigurator;
import service.CourseService;
import command.system.*;
import domain.user.*;
import ui.Menu;


public class CommandConfigurator {

    public static void configure(
            User user,
            CommandRegistry registry,
            CommandManager manager,
            Menu menu,
            CourseService cs,
            Printer printer
    ) {

        // общие команды
        registry.register(new UndoCommand(manager, printer));
        registry.register(new RedoCommand(manager, printer));
        registry.register(new ShowMenuCommand(menu));

        if (user instanceof Student student) {
            registry.register(new ViewCoursesCommand(cs, student, printer));
        }
    }
}

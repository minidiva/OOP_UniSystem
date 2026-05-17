package config;

import util.Printer;
import command.core.CommandRegistry;
import command.core.CommandManager;
import command.admin.UserCommand;
import command.course.CourseCommand;
import command.manager.ApproveRegistrationCommand;
import command.research.ResearchCommand;
import command.student.RateTeacherCommand;
import command.student.ViewCoursesCommand;
import command.student.ViewMarksCommand;
import command.student.ViewTranscriptCommand;
import command.student.RegisterCourseCommand;
import command.teacher.PutMarkCommand;
import command.teacher.RegisterForCourseCommand;
import command.system.ChangeLanguageCommand;
import command.system.LoginCommand;
import command.system.RedoCommand;
import command.system.ShowMenuCommand;
import command.system.UndoCommand;
import command.system.ViewLogsCommand;
import domain.user.Session;
import domain.user.User;
import domain.user.Student;
import domain.user.Teacher;
import domain.user.Manager;
import domain.user.Admin;
import service.CourseService;
import service.RegistrationService;
import service.ResearchService;
import service.UserService;
import service.LoggingService;
import ui.Menu;

public class CommandConfigurator {

    public static void configure(
            Session session,
            CommandRegistry registry,
            CommandManager cmdManager,
            Menu menu,
            CourseService cs,
            UserService userService,
            RegistrationService registrationService,
            ResearchService researchService,
            Printer printer,
            LoginCommand loginCommand
    ) {
        registry.register(new ShowMenuCommand(menu, session));
        registry.register(new ChangeLanguageCommand(session, printer));
        registry.register(loginCommand);

        if (session.isAuthenticated()) {
            User user = session.getCurrentUser();

            registry.register(new ResearchCommand(researchService, printer));

            if (user instanceof Student) {
                Student student = (Student) user;
                registry.register(new ViewCoursesCommand(cs, student, printer));
                registry.register(new RegisterCourseCommand(student, cs, registrationService, userService, printer));
                registry.register(new ViewMarksCommand(student, printer));
                registry.register(new ViewTranscriptCommand(student, printer));
                registry.register(new RateTeacherCommand(student, userService, printer));
            }

            if (user instanceof Teacher) {
                Teacher teacher = (Teacher) user;
                registry.register(new PutMarkCommand(teacher, cs, userService, printer));
                registry.register(new RegisterForCourseCommand(teacher, cs, registrationService, printer));
            }

            if (user instanceof Manager) {
                registry.register(new CourseCommand(session, cs, printer));
                Manager manager = (Manager) user;
                registry.register(new ApproveRegistrationCommand(manager, registrationService, printer));
            }

            if (user instanceof Admin) {
                Admin admin = (Admin) user;
                registry.register(new UserCommand(admin, userService, printer));
                registry.register(new ViewLogsCommand(LoggingService.getInstance(), printer));
            }
        }
    }
}
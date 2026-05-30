package command.manager;

import java.util.Scanner;
import command.core.Command;
import domain.course.Course;
import service.CourseService;
import util.Printer;

public class CreateCourseCommand implements Command {
    private final CourseService courseService;
    private final Printer printer;
    
    public CreateCourseCommand(CourseService courseService, Printer printer) {
        this.courseService = courseService;
        this.printer = printer;
    }
    
    @Override
    public String name() {
        return "createcourse";
    }
    
    @Override
    public String description() {
        return "Create a new course";
    }
    
    @Override
    public void execute(Scanner scanner) {
        printer.println("\n=== CREATE NEW COURSE ===");
        
        printer.println("Course ID: ");
        int id = Integer.parseInt(scanner.nextLine());
        
        printer.println("Title: ");
        String title = scanner.nextLine();
        
        printer.println("Description: ");
        String description = scanner.nextLine();
        
        printer.println("Credits: ");
        int credits = Integer.parseInt(scanner.nextLine());
        
        Course course = new Course(id, title, description, credits);
        courseService.addCourse(course);
        
        printer.println("✅ Course created: " + title);
    }
}

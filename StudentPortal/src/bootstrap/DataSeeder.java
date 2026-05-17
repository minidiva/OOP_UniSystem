package bootstrap;

import repository.Database;
import domain.research.Researcher;
import domain.research.ResearcherFactory;
import domain.user.Admin;
import domain.user.Manager;
import domain.user.Student;
import domain.user.Teacher;
import domain.user.Title;
import domain.course.Course;

public class DataSeeder {

    public static void bootstrap(Database db) {
        db.clear();

        Student student1 = new Student(1, "Inzhu", "Aitakhyn", "i_aitakhyn@kbtu.kz", "1234567", 3, "Information Systems");
        student1.setCredits(15);
        student1.setGpa(3.8);
        db.saveUser(student1);

        Student student2 = new Student(2, "Alan", "Tokin", "a_tokin@kbtu.kz", "1234567", 4, "Information Systems");
        student2.setCredits(12);
        student2.setGpa(3.5);
        db.saveUser(student2);

        Student student3 = new Student(3, "Alua", "Ussenbayeva", "a_ussenbayeva@kbtu.kz", "1234567", 2, "Information Systems");
        student3.setCredits(10);
        student3.setGpa(3.2);
        db.saveUser(student3);

        Student student4 = new Student(4, "Gulnazym", "Ualikhan", "g_ualikhan@kbtu.kz", "1234567", 1, "Information Systems");
        student4.setCredits(8);
        student4.setGpa(3.9);
        db.saveUser(student4);

        Teacher teacher = new Teacher(5, "Miras", "Assubay", "m_assubay@kbtu.kz", "1234567", Title.PROFESSOR, 500000);
        db.saveUser(teacher);
        Researcher professor = ResearcherFactory.wrap(teacher);

        try {
            student2.assignSupervisor(professor);
        } catch (Exception e) {
            // safe fallback for sample data
        }

        Manager manager = new Manager(6, "Dana", "Serikova", "manager@kbtu.kz", "1234567", "OR_MANAGER", 400000);
        db.saveUser(manager);

        Admin admin = new Admin(7, "Admin", "Adminovich", "admin@kbtu.kz", "1234567", "full");
        db.saveUser(admin);

        db.saveCourse(new Course(1, "Java Programming", "OOP and Java basics", 5));
        db.saveCourse(new Course(2, "Database Systems", "SQL and JDBC", 5));
        db.saveCourse(new Course(3, "Web Development", "Spring Boot and React", 4));

        System.out.println(" Test data initialized!");
        System.out.println("   Students: 4");
        System.out.println("   Teachers: 1");
        System.out.println("   Managers: 1");
        System.out.println("   Admin: 1");
        System.out.println("   Courses: 3");
    }
}
package repository;

import java.io.*;
import java.util.*;
import domain.course.Course;
import domain.user.*;

public class Database implements Serializable {
    
    private static Database instance;
    private static final String FILE_PATH = "database.ser";
    
    private Map<String, User> users = new HashMap<>();
    private Map<String, Course> courses = new HashMap<>();
    
    private Database() {}
    
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }
    
    public void initTestData() {
        users.clear();
        courses.clear();
        
        // СТУДЕНТЫ 
        
        // Студент 1 - 3 курс
        Student student1 = new Student(1, "Inzhu", "Aitakhyn", "i_aitakhyn@kbtu.kz", "1234567", 3, "Information Systems");
        student1.setCredits(15);
        student1.setGpa(3.8);
        users.put(student1.getEmail(), student1);
        
        // Студент 2 - 4 курс
        Student student2 = new Student(2, "Alan", "Tokin", "a_tokin@kbtu.kz", "1234567", 4, "Information Systems");
        student2.setCredits(12);
        student2.setGpa(3.5);
        users.put(student2.getEmail(), student2);
        
        // Студент 3 - 2 курс
        Student student3 = new Student(3, "Alua", "Ussenbayeva", "a_ussenbayeva@kbtu.kz", "1234567", 2, "Information Systems");
        student3.setCredits(10);
        student3.setGpa(3.2);
        users.put(student3.getEmail(), student3);
        
        // Студент 4 - 1 курс
        Student student4 = new Student(4, "Gulnazym", "Ualikhan", "g_ualikhan@kbtu.kz", "1234567", 1, "Information Systems");
        student4.setCredits(8);
        student4.setGpa(3.9);
        users.put(student4.getEmail(), student4);
        
        // ПРЕПОДАВАТЕЛИ 
        
        Teacher teacher = new Teacher(5, "Miras", "Assubay", "m_assubay@kbtu.kz", "1234567", "PROFESSOR", 500000);
        users.put(teacher.getEmail(), teacher);
        
        // МЕНЕДЖЕРЫ 
        
        Manager manager = new Manager(6, "Dana", "Serikova", "manager@kbtu.kz", "1234567", "OR_MANAGER", 400000);
        users.put(manager.getEmail(), manager);
        
        // АДМИН 
        
        Admin admin = new Admin(7, "Admin", "Adminovich", "admin@kbtu.kz", "1234567", "full");
        users.put(admin.getEmail(), admin);
        
        // КУРСЫ 
        
        courses.put("1", new Course(1, "Java Programming", "OOP and Java basics", 5));
        courses.put("2", new Course(2, "Database Systems", "SQL and JDBC", 5));
        courses.put("3", new Course(3, "Web Development", "Spring Boot and React", 4));
        
        System.out.println(" Test data initialized!");
        System.out.println("   Students: 4");
        System.out.println("   Teachers: 1");
        System.out.println("   Managers: 1");
        System.out.println("   Admin: 1");
        System.out.println("   Courses: 3");
    }
    
    public Map<String, User> getUsers() { return users; }
    public Map<String, Course> getCourses() { return courses; }
    
    public void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(this);
            System.out.println("Data saved successfully!");
        } catch (IOException e) {
            System.out.println(" Error saving data: " + e.getMessage());
        }
    }
    
    public void load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            Database loaded = (Database) ois.readObject();
            this.users = loaded.users;
            this.courses = loaded.courses;
            System.out.println(" Data loaded successfully!");
        } catch (IOException e) {
            System.out.println("️ No save file found. Starting with empty database.");
        } catch (ClassNotFoundException e) {
            System.out.println(" Error loading data: " + e.getMessage());
        }
    }
}

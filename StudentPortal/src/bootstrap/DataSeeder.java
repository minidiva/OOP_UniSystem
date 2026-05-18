package bootstrap;

import repository.Database;
import domain.research.Researcher;
import domain.research.ResearcherFactory;
import domain.research.ResearchEmployee;
import domain.research.ResearchPaper;
import domain.research.ResearchPaperFactory;
import domain.research.ResearchProject;
import domain.user.Admin;
import domain.user.Manager;
import domain.user.Student;
import domain.user.Teacher;
import domain.user.Title;
import domain.course.Course;
import java.time.LocalDate;
import java.util.Arrays;

public class DataSeeder {

    public static void bootstrap(Database db) {
        db.clear();

        // ========== СТУДЕНТЫ ==========
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

        // ПРЕПОДАВАТЕЛИ
        // автоматически Researcher
        Teacher teacher1 = new Teacher(5, "Dmitrii", "Tuchashvili", "dmitrii@kbtu.kz", "1234567", Title.PROFESSOR, 500000);
        db.saveUser(teacher1);
        Researcher professor = ResearcherFactory.wrap(teacher1);
        
        // Научные статьи профессора
        ResearchPaper paper1 = ResearchPaperFactory.create(
            "PAPER-001",
            "Machine Learning in Education",
            Arrays.asList("D. Tuchashvili", "I. Aitakhyn"),
            15,
            "KBTU Journal",
            8,
            LocalDate.of(2024, 5, 15),
            "10.1234/kbtu.2024.001",
            Arrays.asList("AI", "Education", "ML"),
            "This paper explores machine learning applications in higher education, focusing on student performance prediction."
        );
        professor.getProfile().addPaper(paper1);
        
        ResearchPaper paper2 = ResearchPaperFactory.create(
            "PAPER-002",
            "Blockchain for University Systems",
            Arrays.asList("D. Tuchashvili", "A. Tokin"),
            8,
            "International Conference on Education Technology",
            12,
            LocalDate.of(2025, 1, 10),
            "10.5678/ictedu.2025.002",
            Arrays.asList("Blockchain", "Education", "Security"),
            "This paper proposes a blockchain-based system for secure student records and diploma verification."
        );
        professor.getProfile().addPaper(paper2);
        
        ResearchPaper paper3 = ResearchPaperFactory.create(
            "PAPER-003",
            "Natural Language Processing for Kazakh Language",
            Arrays.asList("D. Tuchashvili", "A. Ussenbayeva"),
            22,
            "Central Asian Journal of AI",
            15,
            LocalDate.of(2025, 3, 20),
            "10.9012/cajai.2025.003",
            Arrays.asList("NLP", "Kazakh", "AI"),
            "This paper presents a neural network approach for Kazakh language text classification."
        );
        professor.getProfile().addPaper(paper3);
        
        // ResearchEmployee
        ResearchEmployee researcher1 = new ResearchEmployee(9, "Marat", "Zhakypov", 
            "marat@research.kz", "1234567", 450000, "Artificial Intelligence");
        db.saveUser(researcher1);
        
        // Статьи исследователя
        ResearchPaper paper4 = ResearchPaperFactory.create(
            "PAPER-004",
            "Deep Learning for NLP",
            Arrays.asList("M. Zhakypov"),
            12,
            "AI Journal",
            10,
            LocalDate.of(2025, 2, 14),
            "10.3456/aij.2025.004",
            Arrays.asList("Deep Learning", "NLP", "Transformers"),
            "This paper reviews state-of-the-art deep learning methods for natural language processing."
        );
        researcher1.getProfile().addPaper(paper4);
        
        ResearchPaper paper5 = ResearchPaperFactory.create(
            "PAPER-005",
            "Computer Vision in Healthcare",
            Arrays.asList("M. Zhakypov", "I. Aitakhyn"),
            5,
            "Medical AI Conference",
            7,
            LocalDate.of(2025, 4, 1),
            "10.7890/medai.2025.005",
            Arrays.asList("Computer Vision", "Healthcare", "AI"),
            "This paper explores computer vision applications for medical image analysis."
        );
        researcher1.getProfile().addPaper(paper5);

        // ИССЛЕДОВАТЕЛЬСКИЕ ПРОЕКТЫ
 
        try {
            ResearchProject project1 = new ResearchProject("PROJ-001", "AI in Education");
            project1.addParticipant(professor);
            project1.addParticipant(researcher1);
            project1.publishPaper(paper1);
            project1.publishPaper(paper3);
            db.saveResearchProject(project1);
            
            ResearchProject project2 = new ResearchProject("PROJ-002", "Blockchain for University");
            project2.addParticipant(professor);
            project2.publishPaper(paper2);
            db.saveResearchProject(project2);
        } catch (domain.research.exceptions.NotAResearcherException e) {
            System.out.println(" Could not add participant to project: " + e.getMessage());
        }

        // МЕНЕДЖЕРЫ
        Manager manager = new Manager(6, "Dana", "Serikova", "manager@kbtu.kz", "1234567", "OR_MANAGER", 400000);
        db.saveUser(manager);

        // АДМИНИСТРАТОР 
        Admin admin = new Admin(7, "Admin", "Adminovich", "admin@kbtu.kz", "1234567", "full");
        db.saveUser(admin);

        //  КУРСЫ
        db.saveCourse(new Course(1, "Java Programming", "OOP and Java basics, Spring Boot", 5));
        db.saveCourse(new Course(2, "Database Systems", "SQL, JDBC, Hibernate", 5));
        db.saveCourse(new Course(3, "Web Development", "HTML, CSS, JavaScript, React", 4));
        db.saveCourse(new Course(4, "Machine Learning", "Python, NumPy, Scikit-learn", 5));
        db.saveCourse(new Course(5, "Cybersecurity", "Network security, Cryptography", 4));
        db.saveCourse(new Course(6, "Cloud Computing", "AWS, Azure, GCP", 4));
        db.saveCourse(new Course(7, "Artificial Intelligence", "Neural Networks, Deep Learning", 5));
        db.saveCourse(new Course(8, "Data Science", "Pandas, Matplotlib, Statistics", 5));

        // ВЫВОД ИНФОРМАЦИИ 
        System.out.println("\n Test data initialized!");
        System.out.println("=================================");
        System.out.println("‍ Students: 4");
        System.out.println(" Teacher: 1 (PROFESSOR: Dmitrii Tuchashvili)");
        System.out.println(" ResearchEmployee: 1 (Marat Zhakypov)");
        System.out.println(" Managers: 1");
        System.out.println(" Admin: 1");
        System.out.println(" Courses: 8");
        System.out.println(" Research Papers: 5");
        System.out.println(" Research Projects: 2");
        System.out.println("=================================");
        System.out.println("\n TEST CREDENTIALS (password: 1234567):");
        System.out.println("   Student (3rd year):    i_aitakhyn@kbtu.kz");
        System.out.println("   Student (4th year):    a_tokin@kbtu.kz");
        System.out.println("   Student (2nd year):    a_ussenbayeva@kbtu.kz");
        System.out.println("   Student (1st year):    g_ualikhan@kbtu.kz");
        System.out.println("   Teacher (Professor):   dmitrii@kbtu.kz");
        System.out.println("   Researcher:            marat@research.kz");
        System.out.println("   Manager:               manager@kbtu.kz");
        System.out.println("   Admin:                 admin@kbtu.kz");
        System.out.println("=================================");
        System.out.println("\n RESEARCH PAPERS SORTING DEMO:");
        System.out.println("   Use command: research papers date");
        System.out.println("   Use command: research papers citations");
        System.out.println("   Use command: research papers pages");
    }
}

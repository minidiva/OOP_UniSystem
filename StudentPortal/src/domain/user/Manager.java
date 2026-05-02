package domain.user;

public class Manager extends User {
    private String managerType; // OR_MANAGER, DEPARTMENT_MANAGER, DEAN
    private double salary;
    
    public Manager() {
        this.role = Role.MANAGER;
    }
    
    public Manager(int id, String firstName, String lastName, String email, String password, String managerType, double salary) {
        super(id, firstName, lastName, email, password, Role.MANAGER);
        this.managerType = managerType;
        this.salary = salary;
    }
    
    public void approveRegistration(Student student, String courseName) {
        System.out.println(" Manager approved registration for " + student.getFullName() + " to " + courseName);
    }
    
    // Геттеры/сеттеры
    public String getManagerType() { return managerType; }
    public double getSalary() { return salary; }
}
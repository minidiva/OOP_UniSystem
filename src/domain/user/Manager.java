package domain.user;

import java.time.LocalDate;

public class Manager extends Employee {
    private String managerType;  // OR_MANAGER, DEPARTMENT_MANAGER, DEAN
    
    public Manager() {
        this.role = Role.MANAGER;
    }
    
    public Manager(int id, String firstName, String lastName, String email, String password, 
                   String managerType, double salary) {
        super(id, firstName, lastName, email, password, Role.MANAGER, salary, LocalDate.now());
        this.managerType = managerType;
    }
    
    public String getManagerType() { return managerType; }
    public void setManagerType(String managerType) { this.managerType = managerType; }
}
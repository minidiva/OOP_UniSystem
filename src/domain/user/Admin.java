package domain.user;

import java.time.LocalDate;

public class Admin extends Employee {
    private String accessLevel;
    
    public Admin() {
        this.role = Role.ADMIN;
    }
    
    public Admin(int id, String firstName, String lastName, String email, String password, 
                 String accessLevel) {
        super(id, firstName, lastName, email, password, Role.ADMIN, 0, LocalDate.now());
        this.accessLevel = accessLevel;
    }
    
    public String getAccessLevel() { return accessLevel; }
    public void setAccessLevel(String accessLevel) { this.accessLevel = accessLevel; }
}
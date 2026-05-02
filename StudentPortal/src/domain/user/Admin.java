package domain.user;

public class Admin extends User {
    private String accessLevel;
    
    public Admin() {
        this.role = Role.ADMIN;
    }
    
    public Admin(int id, String firstName, String lastName, String email, String password, String accessLevel) {
        super(id, firstName, lastName, email, password, Role.ADMIN);
        this.accessLevel = accessLevel;
    }
    
    public void manageUsers() {
        System.out.println("Admin managing users...");
    }
    
    public String getAccessLevel() { return accessLevel; }
}
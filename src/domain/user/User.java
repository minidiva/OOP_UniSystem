package domain.user;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    protected int id;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String password;
    protected Role role;
    
    public User() {}
    
    public User(int id, String firstName, String lastName, String email, String password, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    
    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }
    
    // Геттеры
    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getFullName() { return firstName + " " + lastName; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public Role getRole() { return role; }
    
    // Сеттеры
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    
    @Override
    public String toString() {
        return String.format("%d: %s %s (%s) - %s", id, firstName, lastName, email, role);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return id == user.id;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
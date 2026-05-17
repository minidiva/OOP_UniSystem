package domain.user;

import java.time.LocalDate;

public abstract class Employee extends User {
    protected double salary;
    protected LocalDate hireDate;
    
    public Employee() {
        super();
    }
    
    public Employee(int id, String firstName, String lastName, String email, String password, 
                    Role role, double salary, LocalDate hireDate) {
        super(id, firstName, lastName, email, password, role);
        this.salary = salary;
        this.hireDate = hireDate;
    }
    
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
    
    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }
    
    @Override
    public String toString() {
        return super.toString() + ", Salary: " + salary + ", Hire Date: " + hireDate;
    }
}
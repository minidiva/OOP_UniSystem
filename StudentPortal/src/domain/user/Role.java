package domain.user;

public enum Role {
    STUDENT, TEACHER, MANAGER, ADMIN;
    
    public boolean isStudent() { return this == STUDENT; }
    public boolean isTeacher() { return this == TEACHER; }
    public boolean isManager() { return this == MANAGER; }
    public boolean isAdmin() { return this == ADMIN; }
}
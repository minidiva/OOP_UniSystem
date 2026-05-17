package repository;

import java.util.*;
import domain.user.*;

public class UserRepository {

    public void save(User user) {
        Database.getInstance().saveUser(user);
    }

    public Optional<User> findByEmail(String email) {
        return Database.getInstance().getUsers().values().stream()
            .filter(user -> user.getEmail().equals(email))
            .findFirst();
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        return Database.getInstance().getUsers().values().stream()
            .filter(user -> user.getEmail().equals(email))
            .filter(user -> user.getPassword().equals(password))
            .findFirst();
    }

    public Optional<Student> findStudentByEmail(String email) {
        return Database.getInstance().getUsers().values().stream()
            .filter(user -> user instanceof Student)
            .map(user -> (Student) user)
            .filter(student -> student.getEmail().equals(email))
            .findFirst();
    }

    public List<User> findAll() {
        return new ArrayList<>(Database.getInstance().getUsers().values());
    }

    public boolean deleteByEmail(String email) {
        return Database.getInstance().deleteUser(email);
    }
}

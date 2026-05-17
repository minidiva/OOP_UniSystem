package service;

import java.util.*;
import java.util.stream.Collectors;
import domain.course.Course;
import domain.user.*;
import repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> authenticate(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<Student> findStudentByEmail(String email) {
        return userRepository.findStudentByEmail(email);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public boolean deleteByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(u -> userRepository.deleteByEmail(email))
                .orElse(false);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersSortedByGpa() {
        return userRepository.findAll().stream()
                .sorted(Comparator.comparingDouble(u -> u instanceof Student ? ((Student) u).getGpa() : 0.0))
                .collect(Collectors.toList());
    }

    public List<User> getUsersSortedAlphabetically() {
        return userRepository.findAll().stream()
                .sorted(Comparator.comparing(User::getLastName).thenComparing(User::getFirstName))
                .collect(Collectors.toList());
    }

    // Возвращает студентов записанных на указанный курс
    public List<Student> getStudentsEnrolledIn(Course course) {
        return userRepository.findAll().stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .filter(s -> s.getEnrolledCourses().contains(course))
                .collect(Collectors.toList());
    }

    // Возвращает учителей которые ведут указанный курс
    public List<Teacher> getTeachersForCourse(Course course) {
        return userRepository.findAll().stream()
                .filter(u -> u instanceof Teacher)
                .map(u -> (Teacher) u)
                .filter(t -> t.getTeachingCourses().contains(course))
                .collect(Collectors.toList());
    }
}
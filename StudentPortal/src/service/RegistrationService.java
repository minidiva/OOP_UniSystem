package service;

import domain.course.Course;
import domain.registration.RegistrationRequest;
import domain.user.Manager;
import domain.user.Student;
import domain.user.Teacher;
import domain.user.User;
import repository.Database;
import java.util.*;
import java.util.stream.Collectors;

public class RegistrationService {
    private final Database database = Database.getInstance();
    private final LoggingService loggingService = LoggingService.getInstance();

    public RegistrationRequest requestApproval(User user, Course course) {
        if (user instanceof Student student) {
            if (!student.canRegisterForCourse(course)) {
                throw new IllegalStateException("Student cannot request registration for this course.");
            }
        }
        String requestId = String.format("R-%d-%d-%d", user.getId(), course.getId(), System.currentTimeMillis());
        RegistrationRequest request = new RegistrationRequest(requestId, user.getId(), course.getId());
        database.saveRegistrationRequest(request);
        loggingService.log("Registration requested: " + user.getFullName() + " -> " + course.getTitle());
        return request;
    }

    public boolean approveRequest(String requestId, Manager manager) {
        Optional<RegistrationRequest> request = database.getRegistrationRequests().stream()
            .filter(r -> r.getId().equals(requestId))
            .findFirst();

        if (request.isEmpty()) return false;

        RegistrationRequest target = request.get();
        if (!target.isPending()) return false;

        target.approve(manager.getFullName());
        database.saveRegistrationRequest(target);

        // Находим пользователя по id (Database хранит по email, ищем через stream)
        User user = database.getUsers().values().stream()
            .filter(u -> u.getId() == target.getUserId())
            .findFirst()
            .orElse(null);
        Course course = database.getCourses().get(String.valueOf(target.getCourseId()));

        if (user != null && course != null) {
            if (user instanceof Student student) {
                student.registerCourse(course);
            } else if (user instanceof Teacher teacher) {
                teacher.addCourse(course);
            }
        }

        loggingService.log("Registration approved by " + manager.getFullName() + ": " + target.getId());
        return true;
    }

    public List<RegistrationRequest> getPendingRequests() {
        return database.getRegistrationRequests().stream()
            .filter(RegistrationRequest::isPending)
            .collect(Collectors.toList());
    }

    public List<RegistrationRequest> getAllRequests() {
        return new ArrayList<>(database.getRegistrationRequests());
    }
}
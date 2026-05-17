package service;

import java.util.*;
import domain.course.*;
import domain.user.*;
import repository.CourseRepository;
import java.util.stream.Collectors;

public class CourseService {

    private final CourseRepository courseRepository;
    
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    
    public void createCourse(Course course) {
        courseRepository.save(course);
    }
    
    public Course createCourse(int id, String title, String description, int credits) {
        Course course = new Course(id, title, description, credits);
        createCourse(course);
        return course;
    }

    public int getNextCourseId() {
        return courseRepository.findAll().stream()
            .mapToInt(Course::getId)
            .filter(i -> i > 0)
            .sorted()
            .reduce(1, (nextId, existingId) -> nextId == existingId ? nextId + 1 : nextId);
    }

    public Course createCourse(String title, String description, int credits) {
        int id = getNextCourseId();
        return createCourse(id, title, description, credits);
    }

    public Optional<Course> updateCourse(int id, String title, String description, int credits) {
        var existing = getById(String.valueOf(id));
        if (existing.isEmpty()) {
            return Optional.empty();
        }
        Course course = new Course(id, title, description, credits);
        createCourse(course);
        return Optional.of(course);
    }

    public boolean deleteCourse(int id) {
        var existing = getById(String.valueOf(id));
        if (existing.isEmpty()) {
            return false;
        }
        courseRepository.delete(String.valueOf(id));
        return true;
    }
    
    public List<Course> getAvailableFor(Student student) {
        return courseRepository.findAll().stream()
             .filter(c -> c.isAvailableFor(student))
             .filter(c -> !student.getEnrolledCourses().contains(c))
             .collect(Collectors.toList());
    }
    
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    
    public Optional<Course> getById(String id) {
        return courseRepository.findById(id);
    }
}
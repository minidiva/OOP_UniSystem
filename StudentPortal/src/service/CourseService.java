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
    
    public List<Course> getAvailableFor(Student student) {
        return courseRepository.findAll().stream()
             .filter(c -> c.isAvailableFor(student))
             .collect(Collectors.toList());
    }
    
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
    
    public Optional<Course> getById(String id) {
        return courseRepository.findById(id);
    }
    
    public void addCourse(Course course) {
        if (course != null) {
            courseRepository.save(course);
            System.out.println(" Course added: " + course.getTitle());
        }
    }
    

    
    public Course createCourse(String title, String description, int credits) {
        int newId = generateNewId();
        Course course = new Course(newId, title, description, credits);
        courseRepository.save(course);
        System.out.println("Course created: " + title);
        return course;
    }
    
    public Optional<Course> updateCourse(int id, String title, String description, int credits) {
        Optional<Course> existing = courseRepository.findById(String.valueOf(id));
        if (existing.isPresent()) {
            Course course = existing.get();
            course.setTitle(title);
            course.setDescription(description);
            course.setCredits(credits);
            courseRepository.save(course);
            return Optional.of(course);
        }
        return Optional.empty();
    }
    
    public boolean deleteCourse(int id) {
        Optional<Course> existing = courseRepository.findById(String.valueOf(id));
        if (existing.isPresent()) {
            courseRepository.delete(String.valueOf(id));
            System.out.println(" Course deleted: " + existing.get().getTitle());
            return true;
        }
        System.out.println(" Course not found with ID: " + id);
        return false;
    }
    
    private int generateNewId() {
        return courseRepository.findAll().stream()
            .mapToInt(Course::getId)
            .max()
            .orElse(0) + 1;
    }
}
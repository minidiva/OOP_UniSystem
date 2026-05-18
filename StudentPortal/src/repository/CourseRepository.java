package repository;

import java.util.*;
import domain.course.Course;

public class CourseRepository {

    public void save(Course course) {
        Database.getInstance().saveCourse(course);
    }

    public Optional<Course> findById(String id) {
        return Optional.ofNullable(Database.getInstance().getCourses().get(id));
    }

    public List<Course> findAll() {
        return new ArrayList<>(Database.getInstance().getCourses().values());
    }

    public void delete(String id) {
        Database.getInstance().deleteCourse(id);
    }
    
}

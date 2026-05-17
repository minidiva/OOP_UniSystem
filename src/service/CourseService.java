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
	
	public Optional<Course> getById(String id) {
	    return courseRepository.findById(id);
	}
}
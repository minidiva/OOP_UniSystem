package domain.course;

import domain.user.Student;

public class Course {

	private final int id;
	private final String title;
	private final String description;

	public Course(int id, String title, String description) {
		this.id = id;
		this.title = title;
		this.description = description;
	}

	public int getId() {
		return this.id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}
	
	public boolean isAvailableFor(Student student) {
	    return true;
	}

	@Override
	public String toString() {
		return id + ": " + title + " — " + description;
	}

}

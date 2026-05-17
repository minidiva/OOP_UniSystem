package repository;

import java.io.Serializable;
import java.util.*;
import domain.course.Course;
import domain.registration.RegistrationRequest;
import domain.user.User;
import domain.research.ResearchProfile;
import domain.research.ResearchProject;

public class Database implements Serializable {

    private static Database instance;

    private Map<String, User> users = new HashMap<>();
    private Map<String, Course> courses = new HashMap<>();
    private Map<String, ResearchProfile> researchProfiles = new HashMap<>();
    private Map<String, ResearchProject> researchProjects = new HashMap<>();
    private Map<String, domain.registration.RegistrationRequest> registrationRequests = new LinkedHashMap<>();

    private Database() {}

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Map<String, User> getUsers() {
        return Collections.unmodifiableMap(users);
    }

    public Map<String, Course> getCourses() {
        return Collections.unmodifiableMap(courses);
    }

    public void saveUser(User user) {
        users.put(user.getEmail(), user);
    }

    public void saveCourse(Course course) {
        courses.put(String.valueOf(course.getId()), course);
    }

    public void saveResearchProfile(ResearchProfile profile) {
        researchProfiles.put(String.valueOf(profile.getOwnerId()), profile);
    }

    public Optional<ResearchProfile> getResearchProfile(String ownerId) {
        return Optional.ofNullable(researchProfiles.get(ownerId));
    }

    public Collection<ResearchProfile> getResearchProfiles() {
        return Collections.unmodifiableCollection(researchProfiles.values());
    }

    public void saveResearchProject(ResearchProject project) {
        researchProjects.put(project.getId(), project);
    }

    public Collection<ResearchProject> getResearchProjects() {
        return Collections.unmodifiableCollection(researchProjects.values());
    }

    public void saveRegistrationRequest(RegistrationRequest request) {
        registrationRequests.put(request.getId(), request);
    }

    public Collection<RegistrationRequest> getRegistrationRequests() {
        return Collections.unmodifiableCollection(registrationRequests.values());
    }

    public void deleteCourse(String id) {
        courses.remove(id);
    }

    public boolean deleteUser(String email) {
        return users.remove(email) != null;
    }

    public void replaceWith(Database other) {
        users = new HashMap<>(other.users);
        courses = new HashMap<>(other.courses);
        researchProfiles = new HashMap<>(other.researchProfiles);
        researchProjects = new HashMap<>(other.researchProjects);
        registrationRequests = new LinkedHashMap<>(other.registrationRequests);
    }

    public void clear() {
        users.clear();
        courses.clear();
        researchProfiles.clear();
        researchProjects.clear();
        registrationRequests.clear();
    }

    public boolean isEmpty() {
        return users.isEmpty() && courses.isEmpty();
    }
}

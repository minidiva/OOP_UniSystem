package domain.research;

import domain.user.Employee;
import java.time.LocalDate;
import java.util.*;

public class ResearchEmployee extends Employee implements Researcher {
    private ResearchProfile profile;
    private String mainResearchField;
    
    public ResearchEmployee(int id, String firstName, String lastName, String email, 
                           String password, double salary, String mainResearchField) {
        super(id, firstName, lastName, email, password, null, salary, LocalDate.now());
        this.profile = new ResearchProfile(id, firstName + " " + lastName);
        this.mainResearchField = mainResearchField;
    }
    
    @Override
    public int getOwnerId() {
        return getId();
    }
    
    @Override
    public String getDisplayName() {
        return getFullName();
    }
    
    @Override
    public double getHIndex() {
        return profile.getHIndex();
    }
    
    @Override
    public List<ResearchPaper> getPapers() {
        return profile.getPapers();
    }
    
    @Override
    public void printPapers(Comparator<ResearchPaper> comparator) {
        profile.printPapers(comparator);
    }
    
    @Override
    public ResearchProfile getProfile() {
        return profile;
    }
    
    public String getMainResearchField() {
        return mainResearchField;
    }
    
    public void publishPaper(ResearchPaper paper) {
        profile.addPaper(paper);
    }
    
    @Override
    public String toString() {
        return super.toString() + ", Research Field: " + mainResearchField + ", H-Index: " + getHIndex();
    }
}
package domain.research;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ResearchProfile attaches research data to a user (by id).
 */
public class ResearchProfile implements Researcher, Serializable {
    private final int ownerId;
    private final String ownerName;
    private final List<ResearchPaper> papers = new ArrayList<>();
    private final List<String> projectIds = new ArrayList<>();

    public ResearchProfile(int ownerId, String ownerName) {
        this.ownerId = ownerId;
        this.ownerName = ownerName == null ? "" : ownerName;
    }

    public int getOwnerId() { return ownerId; }

    @Override
    public String getDisplayName() { return ownerName; }

    public void addPaper(ResearchPaper paper) {
        if (paper != null) papers.add(paper);
    }

    public void addProject(String projectId) { projectIds.add(projectId); }

    @Override
    public List<ResearchPaper> getPapers() { return Collections.unmodifiableList(papers); }

    @Override
    public void printPapers(Comparator<ResearchPaper> comparator) {
        List<ResearchPaper> sorted = papers.stream().sorted(comparator).collect(Collectors.toList());
        sorted.forEach(p -> System.out.println(p));
    }

    @Override
    public double getHIndex() {
        List<Integer> citations = papers.stream().map(ResearchPaper::getCitations).sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        int h = 0;
        for (int i = 0; i < citations.size(); i++) {
            if (citations.get(i) >= i + 1) h = i + 1;
            else break;
        }
        return h;
    }
    @Override
    public ResearchProfile getProfile() {
        return this;
    }
}

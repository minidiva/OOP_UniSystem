package domain.research;

import java.io.Serializable;
import java.util.*;
import domain.research.exceptions.NotAResearcherException;

public class ResearchProject implements Serializable {
    private final String id;
    private final String topic;
    private final Set<Integer> participantIds = new LinkedHashSet<>();
    private final List<ResearchPaper> publishedPapers = new ArrayList<>();

    public ResearchProject(String id, String topic) {
        this.id = Objects.requireNonNull(id);
        this.topic = topic == null ? "" : topic;
    }

    public String getId() { return id; }
    public String getTopic() { return topic; }

    public void addParticipant(Researcher researcher) throws NotAResearcherException {
        if (researcher == null) throw new NotAResearcherException("Null cannot join project");
        participantIds.add(researcher.getOwnerId());
    }

    public Set<Integer> getParticipantIds() { return Collections.unmodifiableSet(participantIds); }

    public void publishPaper(ResearchPaper paper) {
        if (paper != null) publishedPapers.add(paper);
    }

    public List<ResearchPaper> getPublishedPapers() { return Collections.unmodifiableList(publishedPapers); }
}

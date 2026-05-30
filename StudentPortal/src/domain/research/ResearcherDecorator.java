package domain.research;

import domain.user.User;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ResearcherDecorator implements Researcher {
    private final User user;
    private final ResearchProfile profile;

    public ResearcherDecorator(User user, ResearchProfile profile) {
        this.user = Objects.requireNonNull(user);
        this.profile = Objects.requireNonNull(profile);
    }

    @Override
    public int getOwnerId() {
        return user.getId();
    }

    @Override
    public String getDisplayName() {
        return user.getFullName();
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

    public ResearchProfile getProfile() {
        return profile;
    }

    @Override
    public String toString() {
        return String.format("%s (Researcher) — H-index %.1f", getDisplayName(), getHIndex());
    }
    
}

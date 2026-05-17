package domain.research;

import java.util.Comparator;
import java.util.List;

/**
 * Researcher role abstraction. Implementations wrap a user or profile.
 */
public interface Researcher {
    int getOwnerId();
    String getDisplayName();
    double getHIndex();
    List<ResearchPaper> getPapers();
    void printPapers(Comparator<ResearchPaper> comparator);
}

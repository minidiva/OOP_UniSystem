package domain.research;

import java.util.Comparator;

/**
 * Strategy interface to provide a Comparator for ResearchPaper sorting.
 */
public interface PaperSortingStrategy {
    Comparator<ResearchPaper> comparator();
}

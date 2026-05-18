package domain.research;

import java.util.Comparator;

public class ByCitationsStrategy implements PaperSortingStrategy {
    @Override
    public Comparator<ResearchPaper> comparator() {
        return ResearchPaper.BY_CITATIONS_DESC;
    }
}

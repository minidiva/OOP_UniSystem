package domain.research;

import java.util.Comparator;

public class ByPagesStrategy implements PaperSortingStrategy {
    @Override
    public Comparator<ResearchPaper> comparator() {
        return ResearchPaper.BY_PAGES_DESC;
    }
}

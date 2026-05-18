package domain.research;

import java.util.Comparator;

public class ByDateStrategy implements PaperSortingStrategy {
    @Override
    public Comparator<ResearchPaper> comparator() {
        return ResearchPaper.BY_DATE_DESC;
    }
}

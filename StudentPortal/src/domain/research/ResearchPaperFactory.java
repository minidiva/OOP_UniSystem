package domain.research;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public final class ResearchPaperFactory {
    private ResearchPaperFactory() {}

    public static ResearchPaper create(String id, String title, List<String> authors, int citations,
                                       String venue, int pages, LocalDate publicationDate,
                                       String doi, List<String> keywords, String abstractText) {
        Objects.requireNonNull(id, "Paper id is required");
        Objects.requireNonNull(title, "Paper title is required");
        return new ResearchPaper(id, title, authors, citations, venue, pages, publicationDate, doi, keywords, abstractText);
    }
}

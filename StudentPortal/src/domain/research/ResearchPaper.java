package domain.research;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

/**
 * IEEE-style Research Paper metadata
 */
public class ResearchPaper implements Serializable {
    private final String id;
    private final String title;
    private final List<String> authors;
    private final int citations;
    private final String venue;
    private final int pages;
    private final LocalDate publicationDate;
    private final String doi;
    private final List<String> keywords;
    private final String abstractText;

    public ResearchPaper(String id, String title, List<String> authors, int citations,
                         String venue, int pages, LocalDate publicationDate,
                         String doi, List<String> keywords, String abstractText) {
        this.id = Objects.requireNonNull(id);
        this.title = Objects.requireNonNull(title);
        this.authors = authors == null ? List.of() : List.copyOf(authors);
        this.citations = Math.max(0, citations);
        this.venue = venue == null ? "" : venue;
        this.pages = Math.max(0, pages);
        this.publicationDate = publicationDate;
        this.doi = doi == null ? "" : doi;
        this.keywords = keywords == null ? List.of() : List.copyOf(keywords);
        this.abstractText = abstractText == null ? "" : abstractText;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public List<String> getAuthors() { return authors; }
    public int getCitations() { return citations; }
    public String getVenue() { return venue; }
    public int getPages() { return pages; }
    public LocalDate getPublicationDate() { return publicationDate; }
    public String getDoi() { return doi; }
    public List<String> getKeywords() { return keywords; }
    public String getAbstractText() { return abstractText; }

    @Override
    public String toString() {
        return String.format("%s — %s (%d citations) [%s]", title, venue, citations, publicationDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResearchPaper)) return false;
        ResearchPaper that = (ResearchPaper) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    public static final Comparator<ResearchPaper> BY_DATE_DESC = Comparator.nullsLast(
        Comparator.comparing(ResearchPaper::getPublicationDate, Comparator.nullsLast(Comparator.naturalOrder())).reversed()
    );

    public static final Comparator<ResearchPaper> BY_CITATIONS_DESC = Comparator.comparingInt(ResearchPaper::getCitations).reversed();

    public static final Comparator<ResearchPaper> BY_PAGES_DESC = Comparator.comparingInt(ResearchPaper::getPages).reversed();
}

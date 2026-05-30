package service;

import domain.research.PaperSortingStrategy;
import domain.research.ResearchPaper;
import domain.research.Researcher;
import repository.Database;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ResearchAnalytics provides analytics over research data using the Strategy pattern
 * for flexible sorting/ranking.
 */
public class ResearchAnalytics {
    private final Database db = Database.getInstance();

    public List<ResearchPaper> allPapersSorted(PaperSortingStrategy strategy) {
        Comparator<ResearchPaper> comp = strategy == null ? ResearchPaper.BY_DATE_DESC : strategy.comparator();
        return db.getResearchProfiles().stream()
            .flatMap(p -> p.getPapers().stream())
            .sorted(comp)
            .collect(Collectors.toList());
    }

    public Optional<Researcher> topCitedResearcher(Optional<Integer> year, List<Researcher> researchers) {
        if (researchers == null) researchers = Collections.emptyList();
        return researchers.stream()
            .max(Comparator.comparingInt(r -> totalCitations(r, year)));
    }

    public int totalCitations(Researcher researcher, Optional<Integer> year) {
        return researcher.getPapers().stream()
            .filter(p -> year.map(y -> p.getPublicationDate() != null && p.getPublicationDate().getYear() == y).orElse(true))
            .mapToInt(ResearchPaper::getCitations)
            .sum();
    }
}

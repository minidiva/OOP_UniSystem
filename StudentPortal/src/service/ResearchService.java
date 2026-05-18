package service;

import domain.research.*;
import domain.user.User;
import repository.Database;
import java.util.*;
import java.util.stream.Collectors;
import domain.research.exceptions.NotAResearcherException;

public class ResearchService {
    private final Database database = Database.getInstance();
    private final LoggingService loggingService = LoggingService.getInstance();

    public Researcher asResearcher(User user) {
        Researcher researcher = ResearcherFactory.wrap(user);
        loggingService.log("Researcher profile accessed for " + user.getFullName());
        return researcher;
    }

    public ResearchProject createProject(String id, String topic, List<Researcher> participants) throws NotAResearcherException {
        ResearchProject project = new ResearchProject(id, topic);
        for (Researcher participant : participants) {
            project.addParticipant(participant);
        }
        database.saveResearchProject(project);
        loggingService.log("Research project created: " + topic + " (" + id + ")");
        return project;
    }

    public void publishPaper(User author, ResearchPaper paper) {
        Researcher researcher = asResearcher(author);
        if (researcher instanceof ResearcherDecorator) {
            ResearcherDecorator decorator = (ResearcherDecorator) researcher;
            decorator.getProfile().addPaper(paper);
            database.saveResearchProfile(decorator.getProfile());
            loggingService.log("Paper published by " + researcher.getDisplayName() + ": " + paper.getTitle());
            ResearchRegistry.getInstance().notifyPaperPublished(decorator.getProfile(), paper);
        }
    }

    public List<Researcher> getAllResearchers() {
        return database.getResearchProfiles().stream()
            .map(profile -> database.getUsers().values().stream()
                .filter(user -> user.getId() == profile.getOwnerId())
                .findFirst()
                .map(user -> new ResearcherDecorator(user, profile)))
            .flatMap(Optional::stream)
            .collect(Collectors.toList());
    }

    public void printAllPapersSorted(Comparator<ResearchPaper> comparator) {
        getAllResearchers().forEach(researcher -> {
            loggingService.log("Research papers for " + researcher.getDisplayName() + " sorted");
            researcher.printPapers(comparator);
        });
    }

    public Optional<Researcher> findTopCitedResearcher(Optional<Integer> year) {
        return getAllResearchers().stream()
            .max(Comparator.comparingDouble(r -> totalCitations(r, year)));
    }

    public int totalCitations(Researcher researcher, Optional<Integer> year) {
        return researcher.getPapers().stream()
            .filter(paper -> year.map(y -> paper.getPublicationDate() != null && paper.getPublicationDate().getYear() == y).orElse(true))
            .mapToInt(ResearchPaper::getCitations)
            .sum();
    }
}
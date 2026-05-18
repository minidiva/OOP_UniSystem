package service;

import domain.research.ResearchPaper;
import domain.research.ResearchProfile;
import repository.Database;
import service.event.EventPublisher;
import service.event.EventListener;

import java.util.*;

/**
 * Singleton registry / facade for research-related events and queries.
 * Acts as a centralized point (Singleton) and Observer publisher for research updates.
 */
public class ResearchRegistry {
    private static ResearchRegistry instance;
    private final Database database = Database.getInstance();
    private final EventPublisher publisher = new EventPublisher();

    private ResearchRegistry() {}

    public static synchronized ResearchRegistry getInstance() {
        if (instance == null) instance = new ResearchRegistry();
        return instance;
    }

    public void registerListener(EventListener listener) {
        publisher.register(listener);
    }

    public void notifyPaperPublished(ResearchProfile profile, ResearchPaper paper) {
        if (profile == null || paper == null) return;
        database.saveResearchProfile(profile);
        publisher.publish("paper.published:" + profile.getOwnerId() + ":" + paper.getId());
    }

    public Collection<ResearchProfile> getAllProfiles() {
        return database.getResearchProfiles();
    }
}

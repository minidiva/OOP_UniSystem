package domain.research;

import domain.user.User;
import repository.Database;
import java.util.Optional;

public final class ResearcherFactory {

    private ResearcherFactory() {}

    public static Researcher wrap(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        ResearchProfile profile = getOrCreateProfile(user);
        return new ResearcherDecorator(user, profile);
    }

    public static ResearchProfile getOrCreateProfile(User user) {
        String key = String.valueOf(user.getId());
        Optional<ResearchProfile> existing = Database.getInstance().getResearchProfile(key);
        if (existing.isPresent()) {
            return existing.get();
        }
        ResearchProfile profile = new ResearchProfile(user.getId(), user.getFullName());
        Database.getInstance().saveResearchProfile(profile);
        return profile;
    }
}

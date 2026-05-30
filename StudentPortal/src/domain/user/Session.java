package domain.user;

import domain.common.EnglishLanguage;
import domain.common.Language;

public class Session {
    private User currentUser;
    private Language currentLanguage = new EnglishLanguage();

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public Language getCurrentLanguage() {
        return currentLanguage;
    }

    public void setCurrentLanguage(Language currentLanguage) {
        this.currentLanguage = currentLanguage;
    }

    public boolean isAuthenticated() {
        return currentUser != null;
    }
    
    // ДОБАВЬ ЭТОТ МЕТОД
    public void logout() {
        this.currentUser = null;
    }
}

package domain.common;

public class LanguageFactory {
    public static Language fromCode(String code) {
        if (code == null) {
            return null;
        }

        String normalized = code.trim().toLowerCase();
        if ("en".equals(normalized)) {
            return new EnglishLanguage();
        }
        if ("ru".equals(normalized)) {
            return new RussianLanguage();
        }
        if ("kz".equals(normalized)) {
            return new KazakhLanguage();
        }
        return null;
    }
}

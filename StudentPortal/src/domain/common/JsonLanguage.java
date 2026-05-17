package domain.common;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

public abstract class JsonLanguage implements Language {
    private final Map<String, String> dictionary;

    public JsonLanguage(String fileName) {
        Map<String, String> loadedDictionary = Collections.emptyMap();

        InputStream stream = getClass().getResourceAsStream("/lang/" + fileName);
        if (stream == null) {
            System.err.println("Critical: Language resource not found: " + fileName);
        } else {
            try (Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
                loadedDictionary = new Gson().fromJson(reader,
                        new TypeToken<Map<String, String>>() {}.getType());
                if (loadedDictionary == null) {
                    loadedDictionary = Collections.emptyMap();
                }
            } catch (Exception e) {
                System.err.println("Critical: Could not load language file " + fileName + " (" + e.getMessage() + ")");
                loadedDictionary = Collections.emptyMap();
            }
        }

        this.dictionary = loadedDictionary;
    }

    @Override
    public String get(String key) {
        return dictionary.getOrDefault(key, "MISSING_KEY: " + key);
    }
}
package domain.common;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Lightweight JSON loader (no external dependencies).
 * Supports flat JSON objects with string key-value pairs.
 */
public abstract class JsonLanguage implements Language {
    private final Map<String, String> dictionary;

    public JsonLanguage(String fileName) {
        Map<String, String> loadedDictionary = new HashMap<>();
        InputStream stream = getClass().getResourceAsStream("/lang/" + fileName);
        if (stream == null) {
            System.err.println("Language resource not found: " + fileName);
            this.dictionary = Collections.emptyMap();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("\"") && line.contains(":")) {
                    int colon = line.indexOf(":");
                    String keyPart = line.substring(0, colon).trim();
                    String valPart = line.substring(colon + 1).trim();
                    String key = stripQuotes(keyPart);
                    String val = stripQuotes(valPart).replaceAll("\\\\n", "\\n");
                    if (!key.isEmpty()) loadedDictionary.put(key, val);
                }
            }
        } catch (Exception e) {
            System.err.println("Could not load language file " + fileName + ": " + e.getMessage());
        }
        this.dictionary = Collections.unmodifiableMap(loadedDictionary);
    }

    private String stripQuotes(String s) {
        s = s.trim();
        if (s.endsWith(",")) s = s.substring(0, s.length() - 1).trim();
        if (s.startsWith("\"") && s.endsWith("\"")) return s.substring(1, s.length() - 1);
        return s;
    }

    @Override
    public String get(String key) {
        return dictionary.getOrDefault(key, "MISSING_KEY: " + key);
    }
}
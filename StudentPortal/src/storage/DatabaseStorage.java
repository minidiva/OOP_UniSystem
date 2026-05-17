package storage;

import java.io.*;
import java.util.Optional;
import repository.Database;

public class DatabaseStorage {

    private static final String FILE_PATH = "database.ser";

    public static Optional<Database> load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            Database loaded = (Database) ois.readObject();
            System.out.println(" Data loaded successfully!");
            return Optional.of(loaded);
        } catch (FileNotFoundException e) {
            System.out.println("️ No save file found. Starting with empty database.");
            return Optional.empty();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(" Error loading data: " + e.getMessage());
            return Optional.empty();
        }
    }

    public static void save(Database db) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(db);
            System.out.println("Data saved successfully!");
        } catch (IOException e) {
            System.out.println(" Error saving data: " + e.getMessage());
        }
    }
}

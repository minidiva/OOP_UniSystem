package command.core;
import java.util.*;

public interface Command {
    String name();
    String description();
    void execute(Scanner scanner);
    default void undo() {}
}
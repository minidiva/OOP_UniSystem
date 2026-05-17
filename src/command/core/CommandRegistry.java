package command.core;
import java.util.*;

public class CommandRegistry {

    private final Map<String, Command> commands = new LinkedHashMap<>();

    public void register(Command command) {
        commands.put(command.name(), command);
    }

    public Command get(String name) {
        return commands.get(name);
    }

    public Collection<Command> all() {
        return commands.values();
    }

    public void clear() {
        commands.clear();
    }
}

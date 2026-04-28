package command.core;
import java.util.*;
import command.system.*;

public class CommandManager {

    private final Deque<Command> history = new ArrayDeque<>();
    private final Deque<Command> redoStack = new ArrayDeque<>();

    public void execute(Command cmd, Scanner scanner) {
        cmd.execute(scanner);

        if (!(cmd instanceof UndoCommand) && !(cmd instanceof RedoCommand)) {
            history.push(cmd);
            redoStack.clear();
        }
    }

    public void undo() {
        if (history.isEmpty()) return;

        Command cmd = history.pop();
        cmd.undo();
        redoStack.push(cmd);
    }

    public void redo() {
        if (redoStack.isEmpty()) return;

        Command cmd = redoStack.pop();
        cmd.execute(null);
        history.push(cmd);
    }

    public Deque<Command> history() {
        return history;
    }
}
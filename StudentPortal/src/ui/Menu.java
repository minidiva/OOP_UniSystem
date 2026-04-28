package ui;
import util.Printer;
import command.core.*;

public class Menu {

    private final CommandRegistry registry;
    private final Printer printer;

    public Menu(CommandRegistry registry, Printer printer) {
        this.registry = registry;
        this.printer = printer;
    }

    public void show() {
        printer.println("\n=== Доступные команды ===");
        registry.all().forEach(cmd ->
            printer.println(cmd.name() + " - " + cmd.description())
        );
    }
}
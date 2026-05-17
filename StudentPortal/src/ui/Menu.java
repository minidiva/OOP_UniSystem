package ui;
import util.Printer;
import command.core.*;
import domain.user.Session;

public class Menu {

    private final CommandRegistry registry;
    private final Printer printer;
    private final Session session;

    public Menu(CommandRegistry registry, Printer printer, Session session) {
        this.registry = registry;
        this.printer = printer;
        this.session = session;
    }

    public void show() {
        String header = session.getCurrentLanguage().get("menu.header");
        printer.println("\n" + header);
        registry.all().forEach(cmd ->
            printer.println(cmd.name() + " - " + cmd.description())
        );
    }
}
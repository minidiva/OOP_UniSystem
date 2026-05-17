package command.system;
import java.util.Scanner;
import command.core.*;
import domain.user.Session;
import ui.Menu;

public class ShowMenuCommand implements Command {

    private final Menu menu;
    private final Session session;

    public ShowMenuCommand(Menu menu, Session session) {
        this.menu = menu;
        this.session = session;
    }

    public String name() { return "help"; }
    public String description() {
        return session.getCurrentLanguage().get("command.menu.show");

    }

    @Override
    public void execute(Scanner scanner) {
        menu.show();
    }

}
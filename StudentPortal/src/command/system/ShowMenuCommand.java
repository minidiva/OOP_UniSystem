package command.system;
import java.util.Scanner;
import command.core.*;
import ui.Menu;

public class ShowMenuCommand implements Command {

    private final Menu menu;

    public ShowMenuCommand(Menu menu) {
        this.menu = menu;
    }

    public String name() { return "help"; }
    public String description() { return "Просмотреть доступные команды"; }

    @Override
    public void execute(Scanner scanner) {
        menu.show();
    }

}
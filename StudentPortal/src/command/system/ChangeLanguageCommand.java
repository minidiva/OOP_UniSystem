package command.system;

import java.util.Scanner;
import command.core.Command;
import util.Printer;
import domain.user.Session;
import domain.common.Language;
import domain.common.LanguageFactory;

public class ChangeLanguageCommand implements Command {

    private final Session session;
    private final Printer printer;

    public ChangeLanguageCommand(Session session, Printer printer) {
        this.session = session;
        this.printer = printer;
    }

    @Override
    public String name() {
        return "language";
    }

    @Override
    public String description() {
        return session.getCurrentLanguage().get("command.language.description");
    }

    @Override
    public void execute(Scanner scanner) {
        printer.println(session.getCurrentLanguage().get("language.prompt"));
        printer.println("en - English");
        printer.println("ru - Русский");
        printer.println("kz - Казакша");

        printer.println(session.getCurrentLanguage().get("language.choose"));

        if (!scanner.hasNextLine()) {
            printer.println("Input closed.");
            return;
        }
        String languageCode = scanner.nextLine().trim().toLowerCase();
        Language selected = LanguageFactory.fromCode(languageCode);

        if (selected == null) {
            printer.println(session.getCurrentLanguage().get("language.invalid"));
            return;
        }

        session.setCurrentLanguage(selected);
        printer.println(session.getCurrentLanguage().get("language.changed"));
    }
}

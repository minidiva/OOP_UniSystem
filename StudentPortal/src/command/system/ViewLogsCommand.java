package command.system;

import java.util.Scanner;
import command.core.Command;
import service.LoggingService;
import util.Printer;

public class ViewLogsCommand implements Command {
    private final LoggingService loggingService;
    private final Printer printer;

    public ViewLogsCommand(LoggingService loggingService, Printer printer) {
        this.loggingService = loggingService;
        this.printer = printer;
    }

    @Override
    public String name() {
        return "logs";
    }

    @Override
    public String description() {
        return "View system logs";
    }

    @Override
    public void execute(Scanner scanner) {
        printer.println("\n=== SYSTEM LOGS ===");
        loggingService.getLogs().forEach(printer::println);
    }
}

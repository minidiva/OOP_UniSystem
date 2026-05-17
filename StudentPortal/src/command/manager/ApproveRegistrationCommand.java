package command.manager;

import java.util.Scanner;
import command.core.Command;
import domain.user.Manager;
import service.RegistrationService;
import util.Printer;

public class ApproveRegistrationCommand implements Command {
    private final Manager manager;
    private final RegistrationService registrationService;
    private final Printer printer;

    public ApproveRegistrationCommand(Manager manager, RegistrationService registrationService, Printer printer) {
        this.manager = manager;
        this.registrationService = registrationService;
        this.printer = printer;
    }

    @Override
    public String name() {
        return "approve";
    }

    @Override
    public String description() {
        return "Approve pending course registrations";
    }

    @Override
    public void execute(Scanner scanner) {
        printer.println("\n=== APPROVE REGISTRATIONS ===");
        registrationService.getPendingRequests().forEach(printer::println);
        printer.println("Enter request ID: ");
        if (!scanner.hasNextLine()) {
            printer.println("Input closed.");
            return;
        }
        String requestId = scanner.nextLine().trim();
        if (registrationService.approveRequest(requestId, manager)) {
            printer.println("Request approved.");
        } else {
            printer.println("Unable to approve request.");
        }
    }
}

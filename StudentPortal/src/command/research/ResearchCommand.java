package command.research;

import java.util.Scanner;
import java.util.Optional;
import command.core.Command;
import domain.research.ResearchPaper;
import service.ResearchService;
import util.Printer;

public class ResearchCommand implements Command {
    private final ResearchService researchService;
    private final Printer printer;

    public ResearchCommand(ResearchService researchService, Printer printer) {
        this.researchService = researchService;
        this.printer = printer;
    }

    @Override
    public String name() {
        return "research";
    }

    @Override
    public String description() {
        return "Explore research papers and top cited researchers";
    }

    @Override
    public void execute(Scanner scanner) {
        printer.println("\n=== RESEARCH ===");
        printer.println("Commands: papers [date|citations|pages] | top [year]");
        printer.println("Enter command: ");
        if (!scanner.hasNextLine()) {
            printer.println("Input closed.");
            return;
        }
        String line = scanner.nextLine().trim().toLowerCase();
        if (line.startsWith("papers")) {
            if (line.contains("date")) {
                researchService.printAllPapersSorted(ResearchPaper.BY_DATE_DESC);
            } else if (line.contains("citations")) {
                researchService.printAllPapersSorted(ResearchPaper.BY_CITATIONS_DESC);
            } else if (line.contains("pages")) {
                researchService.printAllPapersSorted(ResearchPaper.BY_PAGES_DESC);
            } else {
                printer.println("Available sorts: date, citations, pages");
            }
        } else if (line.startsWith("top")) {
            Optional<Integer> year = Optional.empty();
            String[] parts = line.split("\\s+");
            if (parts.length > 1) {
                try {
                    year = Optional.of(Integer.parseInt(parts[1]));
                } catch (NumberFormatException ignored) {
                    printer.println("Invalid year.");
                    return;
                }
            }
            Optional.ofNullable(researchService.findTopCitedResearcher(year).orElse(null))
                .ifPresentOrElse(
                    researcher -> printer.println("Top cited researcher: " + researcher.getDisplayName()),
                    () -> printer.println("No researchers found")
                );
        } else {
            printer.println("Unknown research command.");
        }
    }
}

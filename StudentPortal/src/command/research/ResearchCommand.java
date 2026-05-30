package command.research;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

import command.core.Command;
import domain.research.ResearchPaper;
import domain.research.ResearchPaperFactory;
import domain.research.ResearchProfile;
import domain.research.Researcher;
import domain.research.ResearcherFactory;
import domain.user.Student;
import domain.user.Teacher;
import domain.user.User;
import repository.Database;
import service.ResearchService;
import util.Printer;

public class ResearchCommand implements Command {
    private final ResearchService researchService;
    private final Printer printer;
    private final Database db;
    private final User currentUser;

    public ResearchCommand(ResearchService researchService, Printer printer, Database db, User currentUser) {
        this.researchService = Objects.requireNonNull(researchService, "researchService must not be null");
        this.printer = Objects.requireNonNull(printer, "printer must not be null");
        this.db = Objects.requireNonNull(db, "db must not be null");
        this.currentUser = Objects.requireNonNull(currentUser, "currentUser must not be null");
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
        printer.println("Query commands: papers [date|citations|pages] | top [year]");
        if (isAuthorized()) {
            printer.println("Create commands: create_researcher | create_paper | assign_supervisor | list_profiles | show_profile <email>");
        }
        printer.println("Enter command: ");
        if (!scanner.hasNextLine()) {
            printer.println("Input closed.");
            return;
        }
        String line = scanner.nextLine().trim().toLowerCase();

        if (line.startsWith("papers")) {
            handlePapers(line);
        } else if (line.startsWith("top")) {
            handleTop(line);
        } else if (line.equals("create_researcher")) {
            handleAuthorized(() -> createResearcher(scanner));
        } else if (line.equals("create_paper")) {
            handleAuthorized(() -> createPaper(scanner));
        } else if (line.equals("assign_supervisor")) {
            handleAuthorized(() -> assignSupervisor(scanner));
        } else if (line.equals("list_profiles")) {
            handleAuthorized(this::listProfiles);
        } else if (line.startsWith("show_profile")) {
            handleAuthorized(() -> {
                String[] parts = line.split("\\s+");
                if (parts.length < 2) {
                    printer.println("Usage: show_profile <email>");
                } else {
                    showProfile(parts[1]);
                }
            });
        } else {
            printer.println("Unknown research command.");
        }
    }

    private void handlePapers(String line) {
        if (line.contains("date")) {
            researchService.printAllPapersSorted(ResearchPaper.BY_DATE_DESC);
        } else if (line.contains("citations")) {
            researchService.printAllPapersSorted(ResearchPaper.BY_CITATIONS_DESC);
        } else if (line.contains("pages")) {
            researchService.printAllPapersSorted(ResearchPaper.BY_PAGES_DESC);
        } else {
            printer.println("Available sorts: date, citations, pages");
        }
    }

    private void handleTop(String line) {
        Integer year = null;
        String[] parts = line.split("\\s+");
        if (parts.length > 1) {
            try {
                year = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                printer.println("Invalid year.");
                return;
            }
        }
        final Integer finalYear = year;
        researchService.findTopCitedResearcher(Optional.ofNullable(finalYear)).ifPresentOrElse(
            r -> printer.println("Top cited researcher: " + r.getDisplayName()),
            () -> printer.println("No researchers found")
        );
    }

    /**
     * Runs the given action if the current user is authorized.
     * Prints an unknown command message otherwise (hides restricted commands).
     */
    private void handleAuthorized(Runnable action) {
        if (isAuthorized()) {
            action.run();
        } else {
            printer.println("Unknown research command.");
        }
    }

    /**
     * Authorized: 4th year students, professors, or anyone with a research profile.
     */
    private boolean isAuthorized() {
        if (currentUser instanceof Student s) {
            if (s.getYear() == 4) return true;
        }
        if (currentUser instanceof Teacher t) {
            if (t.getTitle() != null && t.getTitle().isProfessor()) return true;
        }
        return db.getResearchProfile(String.valueOf(currentUser.getId())).isPresent();
    }

    private Optional<User> findUserByEmail(String email) {
        return db.getUsers().values().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst();
    }

    private void createResearcher(Scanner scanner) {
        printer.println("Enter user email to wrap as Researcher: ");
        String email = scanner.nextLine().trim();
        findUserByEmail(email).ifPresentOrElse(
            u -> {
                Researcher r = ResearcherFactory.wrap(u);
                printer.println("Researcher created: " + r.getDisplayName() + " (h-index=" + r.getHIndex() + ")");
            },
            () -> printer.println("User not found: " + email)
        );
    }

    private void createPaper(Scanner scanner) {
        try {
            printer.println("Author email: ");
            String email = scanner.nextLine().trim();
            Optional<User> ou = findUserByEmail(email);
            if (ou.isEmpty()) {
                printer.println("Author not found");
                return;
            }
            User author = ou.get();

            printer.println("Paper ID: ");
            String id = scanner.nextLine().trim();
            printer.println("Title: ");
            String title = scanner.nextLine().trim();
            printer.println("Authors (comma separated): ");
            List<String> authors = Arrays.asList(scanner.nextLine().split("\\s*,\\s*"));
            printer.println("Citations (int): ");
            int citations = Integer.parseInt(scanner.nextLine().trim());
            printer.println("Venue: ");
            String venue = scanner.nextLine().trim();
            printer.println("Pages (int): ");
            int pages = Integer.parseInt(scanner.nextLine().trim());
            printer.println("Publication date (yyyy-mm-dd) or blank: ");
            String dateStr = scanner.nextLine().trim();
            LocalDate pubDate = dateStr.isEmpty() ? null : LocalDate.parse(dateStr);
            printer.println("DOI: ");
            String doi = scanner.nextLine().trim();
            printer.println("Keywords (comma separated) or blank: ");
            String kline = scanner.nextLine().trim();
            List<String> keywords = kline.isEmpty() ? List.of() : Arrays.asList(kline.split("\\s*,\\s*"));
            printer.println("Abstract (optional): ");
            String abstractText = scanner.nextLine().trim();

            ResearchPaper paper = ResearchPaperFactory.create(id, title, authors, citations, venue, pages, pubDate, doi, keywords, abstractText);
            researchService.publishPaper(author, paper);
            printer.println("Paper published by " + author.getFullName() + ": " + paper.getTitle());
        } catch (NumberFormatException e) {
            printer.println("Invalid number: " + e.getMessage());
        } catch (DateTimeParseException e) {
            printer.println("Invalid date format. Use yyyy-mm-dd.");
        }
    }

    private void assignSupervisor(Scanner scanner) {
        printer.println("Student email: ");
        String sEmail = scanner.nextLine().trim();
        printer.println("Supervisor email: ");
        String supEmail = scanner.nextLine().trim();

        Optional<User> su = findUserByEmail(sEmail);
        Optional<User> spu = findUserByEmail(supEmail);

        if (su.isEmpty() || spu.isEmpty()) {
            printer.println("Student or supervisor not found");
            return;
        }

        User studentUser = su.get();
        if (!(studentUser instanceof Student student)) {
            printer.println("Target is not a student");
            return;
        }

        Researcher supervisor = ResearcherFactory.wrap(spu.get());
        try {
            student.assignSupervisor(supervisor);
            printer.println("Supervisor " + supervisor.getDisplayName() + " (h-index=" + supervisor.getHIndex() + ") assigned to " + student.getFullName());
        } catch (Exception e) {
            printer.println("Failed to assign supervisor: " + e.getMessage());
        }
    }

    private void listProfiles() {
        List<Researcher> researchers = researchService.getAllResearchers();
        if (researchers.isEmpty()) {
            printer.println("No researchers found");
            return;
        }
        printer.println("=== Research Profiles ===");
        researchers.forEach(r -> printer.println(
            "  - ID " + r.getOwnerId() + ": " + r.getDisplayName() +
            " (h-index=" + r.getHIndex() + ", papers=" + r.getPapers().size() + ")"
        ));
    }

    private void showProfile(String email) {
        Optional<User> ou = findUserByEmail(email);
        if (ou.isEmpty()) {
            printer.println("User not found");
            return;
        }
        User u = ou.get();
        Optional<ResearchProfile> rp = db.getResearchProfile(String.valueOf(u.getId()));
        if (rp.isEmpty()) {
            printer.println("No research profile for user " + u.getFullName());
            return;
        }
        ResearchProfile profile = rp.get();
        printer.println("=== Profile for " + profile.getDisplayName() + " ===");
        printer.println("  H-index: " + profile.getHIndex());
        printer.println("  Papers: " + profile.getPapers().size());
        if (!profile.getPapers().isEmpty()) {
            printer.println("  Publications:");
            profile.getPapers().forEach(p -> printer.println("    - " + p));
        }
    }
}
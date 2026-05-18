package service;

import java.util.Collections;
import java.util.List;

public class RoleAssignmentResult {
    public enum Decision { APPROVED, REJECTED }

    private final Decision decision;
    private final String reason;
    private final List<String> actions;

    public RoleAssignmentResult(Decision decision, String reason, List<String> actions) {
        this.decision = decision;
        this.reason = reason == null ? "" : reason;
        this.actions = actions == null ? Collections.emptyList() : List.copyOf(actions);
    }

    public static RoleAssignmentResult approved(List<String> actions) {
        return new RoleAssignmentResult(Decision.APPROVED, "", actions == null ? Collections.emptyList() : actions);
    }

    public static RoleAssignmentResult rejected(String reason) {
        List<String> empty = Collections.emptyList();
        return new RoleAssignmentResult(Decision.REJECTED, reason, empty);
    }

    public Decision getDecision() { return decision; }
    public String getReason() { return reason; }
    public List<String> getActions() { return actions; }

    @Override
    public String toString() {
        return String.format("Decision: %s, Reason: %s, Actions: %s", decision, reason, actions);
    }
}

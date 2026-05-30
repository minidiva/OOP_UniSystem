package service;

import domain.research.Researcher;
import domain.user.Admin;
import domain.user.Employee;
import domain.user.Manager;
import domain.user.Student;
import domain.user.Teacher;
import domain.user.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Deterministic business rule engine for assigning Researcher role.
 * Only handles validation and profile creation; does not change unrelated systems.
 */
public class ResearchRoleAssigner {

    public static final class ManualOptions {
        public boolean justificationProvided;
        public boolean explicitEmployeeApproval;
        public Researcher supervisor; // optional
    }

    public RoleAssignmentResult automaticAssignment(User user) {
        Objects.requireNonNull(user);
        List<String> actions = new ArrayList<>();

        if (user instanceof Teacher) {
            Teacher t = (Teacher) user;
            if (t.getTitle() != null && t.getTitle().isProfessor()) {
                actions.add("profile created");
                actions.add("role assigned");
                return RoleAssignmentResult.approved(actions);
            } else {
                return RoleAssignmentResult.rejected("Teacher is not a professor; automatic assignment requires PROFESSOR title");
            }
        }

        if (user instanceof Student) {
            Student s = (Student) user;
            if (s.getYear() == 4) {
                Researcher sup = s.getSupervisor();
                if (sup == null) return RoleAssignmentResult.rejected("4th year student must have a supervisor for automatic assignment");
                if (sup.getHIndex() < 3) {
                    return RoleAssignmentResult.rejected("Supervisor h-index < 3: supervisor must be a researcher with h-index >= 3");
                }
                actions.add("profile created");
                actions.add("role assigned");
                actions.add("supervisor linked: " + sup.getDisplayName());
                return RoleAssignmentResult.approved(actions);
            } else {
                return RoleAssignmentResult.rejected("Only 4th year students are eligible for automatic researcher assignment");
            }
        }

        return RoleAssignmentResult.rejected("Automatic assignment only supports Teacher or Student types");
    }

    public RoleAssignmentResult manualAssignment(User requester, User target, ManualOptions options) {
        Objects.requireNonNull(requester);
        Objects.requireNonNull(target);
        if (!(requester instanceof Admin || requester instanceof Manager)) {
            return RoleAssignmentResult.rejected("Only Admin or Manager may perform manual assignments");
        }
        options = options == null ? new ManualOptions() : options;
        List<String> actions = new ArrayList<>();

        if (target instanceof Student) {
            Student s = (Student) target;
            if (s.getYear() != 4) return RoleAssignmentResult.rejected("Student must be 4th year for assignment");
            Researcher sup = options.supervisor != null ? options.supervisor : s.getSupervisor();
            if (sup == null) return RoleAssignmentResult.rejected("4th year student must have a valid supervisor for assignment");
            if (sup.getHIndex() < 3) return RoleAssignmentResult.rejected("Supervisor h-index < 3: assignment rejected");
            actions.add("profile created");
            actions.add("role assigned");
            actions.add("supervisor linked: " + sup.getDisplayName());
            return RoleAssignmentResult.approved(actions);
        }

        if (target instanceof Teacher) {
            Teacher t = (Teacher) target;
            if (t.getTitle() != null && t.getTitle().isProfessor()) {
                actions.add("profile created");
                actions.add("role assigned");
                return RoleAssignmentResult.approved(actions);
            } else {
                if (!options.justificationProvided) {
                    return RoleAssignmentResult.rejected("Non-professor teachers require justification for manual assignment");
                }
                actions.add("profile created (justified)");
                actions.add("role assigned");
                return RoleAssignmentResult.approved(actions);
            }
        }

        if (target instanceof Employee) {
            if (!options.explicitEmployeeApproval) return RoleAssignmentResult.rejected("Employee assignment requires explicit approval");
            actions.add("profile created");
            actions.add("role assigned");
            return RoleAssignmentResult.approved(actions);
        }

        return RoleAssignmentResult.rejected("Manual assignment supports Student, Teacher, or Employee only");
    }

    public RoleAssignmentResult applyForResearcher(User applicant, boolean approved, Researcher supervisorIfAny) {
        Objects.requireNonNull(applicant);
        List<String> actions = new ArrayList<>();

        if (applicant instanceof Student) {
            Student s = (Student) applicant;
            if (s.getYear() != 4) return RoleAssignmentResult.rejected("Student applicants must be 4th year");
            if (!approved) return RoleAssignmentResult.rejected("Application requires approval by admin/manager");
            Researcher sup = supervisorIfAny != null ? supervisorIfAny : s.getSupervisor();
            if (sup == null) return RoleAssignmentResult.rejected("Student applicant must have a supervisor upon approval");
            if (sup.getHIndex() < 3) return RoleAssignmentResult.rejected("Supervisor h-index < 3: application rejected");
            actions.add("profile created");
            actions.add("role assigned");
            actions.add("supervisor linked: " + sup.getDisplayName());
            return RoleAssignmentResult.approved(actions);
        }

        if (applicant instanceof Teacher) {
            Teacher t = (Teacher) applicant;
            // teachers considered; approval required if not professor
            if (t.getTitle() != null && t.getTitle().isProfessor()) {
                actions.add("profile created");
                actions.add("role assigned");
                return RoleAssignmentResult.approved(actions);
            }
            if (!approved) return RoleAssignmentResult.rejected("Teacher applicants who are not professors require approval");
            actions.add("profile created (approved)");
            actions.add("role assigned");
            return RoleAssignmentResult.approved(actions);
        }

        // For other users, reject application flow
        return RoleAssignmentResult.rejected("Application process supports Student and Teacher applicants only");
    }
}

package domain.user;

public enum Title {
    PROFESSOR,
    SENIOR_LECTURER,
    LECTURER,
    TUTOR;

    public boolean isProfessor() {
        return this == PROFESSOR;
    }
}

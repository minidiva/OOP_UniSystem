package domain.registration;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class RegistrationRequest implements Serializable {
    private final String id;
    private final int userId;      // было: studentId
    private final int courseId;
    private final LocalDateTime requestedAt;
    private RegistrationStatus status;
    private String reviewedBy;

    public RegistrationRequest(String id, int userId, int courseId) {
        this.id = Objects.requireNonNull(id);
        this.userId = userId;
        this.courseId = courseId;
        this.requestedAt = LocalDateTime.now();
        this.status = RegistrationStatus.PENDING;
    }

    public String getId() { return id; }
    public int getUserId() { return userId; }       // было: getStudentId()
    public int getCourseId() { return courseId; }
    public LocalDateTime getRequestedAt() { return requestedAt; }
    public RegistrationStatus getStatus() { return status; }
    public String getReviewedBy() { return reviewedBy; }

    public void approve(String reviewer) {
        this.status = RegistrationStatus.APPROVED;
        this.reviewedBy = reviewer;
    }

    public void reject(String reviewer) {
        this.status = RegistrationStatus.REJECTED;
        this.reviewedBy = reviewer;
    }

    public boolean isPending() {
        return status == RegistrationStatus.PENDING;
    }

    @Override
    public String toString() {
        return String.format("Request %s: user=%d course=%d status=%s requested=%s reviewer=%s",
                id, userId, courseId, status, requestedAt, reviewedBy == null ? "n/a" : reviewedBy);
    }
}

package service;

import domain.user.Mark;
import java.util.Collection;

public class StandardGpaStrategy implements GpaStrategy {
    @Override
    public double calculateGpa(Collection<Mark> marks) {
        if (marks == null || marks.isEmpty()) {
            return 0.0;
        }
        return marks.stream()
            .mapToDouble(Mark::getGpaValue)
            .average()
            .orElse(0.0);
    }
}

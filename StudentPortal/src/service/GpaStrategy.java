package service;

import domain.user.Mark;
import java.util.Collection;

public interface GpaStrategy {
    double calculateGpa(Collection<Mark> marks);
}

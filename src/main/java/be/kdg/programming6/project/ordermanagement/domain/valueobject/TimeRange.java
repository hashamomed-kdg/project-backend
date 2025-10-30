package be.kdg.programming6.project.ordermanagement.domain.valueobject;

import java.time.LocalTime;

public record TimeRange(LocalTime open, LocalTime close) {
    public boolean includes(LocalTime time) {
        return time.isAfter(open) && time.isBefore(close);
    }
}

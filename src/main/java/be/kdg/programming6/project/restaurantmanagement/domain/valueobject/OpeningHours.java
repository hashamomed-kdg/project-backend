package be.kdg.programming6.project.restaurantmanagement.domain.valueobject;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public record OpeningHours(Map<DayOfWeek, List<TimeRange>> openingHours) {
    public List<TimeRange> getOpeningTime(DayOfWeek day) {
        return openingHours.get(day);
    }
    public boolean isOpenAt(LocalDateTime dateTime){
        List<TimeRange> timeRanges = openingHours.get(dateTime.getDayOfWeek());
        if (timeRanges == null) {
            return false;
        }
        LocalTime time = dateTime.toLocalTime();
        return timeRanges.stream().anyMatch(tr -> tr.includes(time));
    }
}

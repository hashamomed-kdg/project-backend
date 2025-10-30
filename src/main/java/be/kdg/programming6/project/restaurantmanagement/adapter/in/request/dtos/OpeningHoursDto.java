package be.kdg.programming6.project.restaurantmanagement.adapter.in.request.dtos;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;
import java.util.Map;

public record OpeningHoursDto(
        @JsonValue
        Map<String, List<TimeRangeDto>> openingHours
) {
}

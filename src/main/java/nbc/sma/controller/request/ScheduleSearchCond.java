package nbc.sma.controller.request;

import java.time.LocalDate;

public record ScheduleSearchCond(
        LocalDate updatedAt,
        String username
) {
}

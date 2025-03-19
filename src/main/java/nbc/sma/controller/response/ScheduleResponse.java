package nbc.sma.controller.response;

import java.time.LocalDateTime;

public record ScheduleResponse(
        Long scheduleId,
        String username,
        String task,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

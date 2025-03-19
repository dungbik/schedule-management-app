package nbc.sma.controller.response;

import java.time.LocalDateTime;

public record CreateScheduleResponse(
        Long scheduleId,
        String username,
        String task,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}

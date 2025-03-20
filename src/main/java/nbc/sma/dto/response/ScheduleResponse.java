package nbc.sma.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponse {
    private Long scheduleId;
    private UserResponse user;
    private String task;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

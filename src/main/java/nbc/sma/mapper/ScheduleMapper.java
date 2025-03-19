package nbc.sma.mapper;

import nbc.sma.controller.request.CreateScheduleRequest;
import nbc.sma.controller.response.CreateScheduleResponse;
import nbc.sma.entity.Schedule;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ScheduleMapper {

    public Schedule toEntity(CreateScheduleRequest req) {
        return Schedule.builder()
                .username(req.username())
                .password(req.password())
                .task(req.task())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public CreateScheduleResponse toResponse(Long scheduleId, Schedule schedule) {
        return new CreateScheduleResponse(scheduleId, schedule.getUsername(), schedule.getTask(), schedule.getCreatedAt(), schedule.getUpdatedAt());
    }
}

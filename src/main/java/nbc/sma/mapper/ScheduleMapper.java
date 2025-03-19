package nbc.sma.mapper;

import nbc.sma.controller.request.CreateScheduleRequest;
import nbc.sma.controller.response.FindSchedulesResponse;
import nbc.sma.controller.response.ScheduleResponse;
import nbc.sma.entity.Schedule;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

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

    public ScheduleResponse toResponse(Schedule schedule) {
        return new ScheduleResponse(schedule.getId(), schedule.getUsername(), schedule.getTask(), schedule.getCreatedAt(), schedule.getUpdatedAt());
    }

    public FindSchedulesResponse toResponse(List<Schedule> results) {
        return new FindSchedulesResponse(results.stream().map(this::toResponse).toList(), results.size());
    }
}

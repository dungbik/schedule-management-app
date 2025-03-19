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
                .userId(req.userId())
                .password(req.password())
                .task(req.task())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public FindSchedulesResponse toResponse(List<ScheduleResponse> results) {
        return new FindSchedulesResponse(results, results.size());
    }
}

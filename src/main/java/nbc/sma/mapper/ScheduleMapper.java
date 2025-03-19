package nbc.sma.mapper;

import nbc.sma.controller.request.CreateScheduleRequest;
import nbc.sma.entity.Schedule;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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
}

package nbc.sma.service;

import lombok.RequiredArgsConstructor;
import nbc.sma.controller.request.CreateScheduleRequest;
import nbc.sma.controller.response.CreateScheduleResponse;
import nbc.sma.entity.Schedule;
import nbc.sma.mapper.ScheduleMapper;
import nbc.sma.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;

    public CreateScheduleResponse createSchedule(CreateScheduleRequest req) {
        Schedule schedule = scheduleMapper.toEntity(req);
        Long scheduleId = scheduleRepository.save(schedule);

        return scheduleMapper.toResponse(scheduleId, schedule);
    }
}

package nbc.sma.service;

import lombok.RequiredArgsConstructor;
import nbc.sma.controller.request.CreateScheduleRequest;
import nbc.sma.controller.request.ScheduleSearchCond;
import nbc.sma.controller.response.ScheduleResponse;
import nbc.sma.controller.response.FindSchedulesResponse;
import nbc.sma.entity.Schedule;
import nbc.sma.mapper.ScheduleMapper;
import nbc.sma.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;

    public ScheduleResponse createSchedule(CreateScheduleRequest req) {
        Schedule schedule = scheduleRepository.save(scheduleMapper.toEntity(req));

        return scheduleMapper.toResponse(schedule);
    }

    public FindSchedulesResponse findSchedules(ScheduleSearchCond cond) {
        List<Schedule> results = scheduleRepository.findAll(cond);

        return scheduleMapper.toResponse(results);
    }

    public ScheduleResponse findSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.find(scheduleId);

        return scheduleMapper.toResponse(schedule);
    }
}

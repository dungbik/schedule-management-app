package nbc.sma.service;

import lombok.RequiredArgsConstructor;
import nbc.sma.controller.request.CreateScheduleRequest;
import nbc.sma.controller.request.EditScheduleRequest;
import nbc.sma.controller.request.ScheduleSearchCond;
import nbc.sma.controller.response.ScheduleResponse;
import nbc.sma.controller.response.FindSchedulesResponse;
import nbc.sma.entity.Schedule;
import nbc.sma.entity.User;
import nbc.sma.mapper.ScheduleMapper;
import nbc.sma.mapper.UserMapper;
import nbc.sma.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final UserService userService;
    private final UserMapper userMapper;

    public ScheduleResponse createSchedule(CreateScheduleRequest req) {
        User user = userService.find(req.userId());
        if (user == null) {
            throw new RuntimeException("존재하지 않는 사용자입니다.");
        }

        Schedule schedule = scheduleMapper.toEntity(req);
        scheduleRepository.save(schedule);

        return new ScheduleResponse(schedule.getId(), userMapper.toResponse(user), schedule.getTask(), schedule.getCreatedAt(), schedule.getUpdatedAt());
    }

    public FindSchedulesResponse findSchedules(ScheduleSearchCond cond) {
        List<ScheduleResponse> results = scheduleRepository.findAllResponse(cond);

        return new FindSchedulesResponse(results, results.size(), cond.getPage());
    }

    public ScheduleResponse findSchedule(Long scheduleId) {
        return scheduleRepository.findResponse(scheduleId);
    }

    public void editSchedule(Long scheduleId, EditScheduleRequest req) {

        Schedule schedule = scheduleRepository.find(scheduleId);
        if (schedule == null) {
            throw new RuntimeException("존재하지 않는 일정입니다.");
        }

        if (!Objects.equals(schedule.getPassword(), req.password())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        userService.editName(schedule.getUserId(), req.username());
        scheduleRepository.update(scheduleId, req.task());
    }

    public void deleteSchedule(Long scheduleId) {
        scheduleRepository.delete(scheduleId);
    }
}

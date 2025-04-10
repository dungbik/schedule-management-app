package nbc.sma.service;

import lombok.RequiredArgsConstructor;
import nbc.sma.dto.request.CreateScheduleRequest;
import nbc.sma.dto.request.EditScheduleRequest;
import nbc.sma.dto.request.ScheduleSearchRequest;
import nbc.sma.dto.response.ScheduleResponse;
import nbc.sma.dto.response.FindSchedulesResponse;
import nbc.sma.entity.Schedule;
import nbc.sma.entity.User;
import nbc.sma.exception.InvalidPasswordException;
import nbc.sma.exception.NotFoundException;
import nbc.sma.dto.mapper.ScheduleMapper;
import nbc.sma.dto.mapper.UserMapper;
import nbc.sma.repository.ScheduleRepository;
import org.springframework.dao.DataAccessException;
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
            throw new NotFoundException("존재하지 않는 사용자입니다.");
        }

        Schedule schedule = scheduleMapper.toEntity(req);
        scheduleRepository.save(schedule);

        return new ScheduleResponse(schedule.getId(), userMapper.toResponse(user), schedule.getTask(), schedule.getCreatedAt(), schedule.getUpdatedAt());
    }

    public FindSchedulesResponse findSchedules(ScheduleSearchRequest req) {
        List<ScheduleResponse> results = scheduleRepository.findAllResponse(req.getUpdatedAt(), req.getUsername(), req.getUserId(), req.getPage(), req.getSize());

        return new FindSchedulesResponse(results, results.size(), req.getPage());
    }

    public ScheduleResponse findSchedule(Long scheduleId) {
        try {
            return scheduleRepository.findResponse(scheduleId);
        } catch (DataAccessException dae) {
            throw new NotFoundException("존재하지 않는 일정입니다.");
        }
    }

    public void editSchedule(Long scheduleId, EditScheduleRequest req) {

        Schedule schedule = scheduleRepository.findById(scheduleId);
        if (schedule == null) {
            throw new NotFoundException("존재하지 않는 일정입니다.");
        }

        if (!Objects.equals(schedule.getPassword(), req.password())) {
            throw new InvalidPasswordException();
        }

        userService.editName(schedule.getUserId(), req.username());
        scheduleRepository.update(scheduleId, req.task());
    }

    public void deleteSchedule(Long scheduleId) {
        scheduleRepository.deleteById(scheduleId);
    }
}

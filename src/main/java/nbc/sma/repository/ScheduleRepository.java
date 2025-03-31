package nbc.sma.repository;

import nbc.sma.dto.response.ScheduleResponse;
import nbc.sma.entity.Schedule;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleRepository {

    void save(Schedule schedule);

    List<ScheduleResponse> findAllResponse(LocalDate updatedAt, String username, Long userId, Integer page, Integer size);

    Schedule findById(Long scheduleId);

    ScheduleResponse findResponse(Long scheduleId);

    void update(Long scheduleId, String task);

    void deleteById(Long scheduleId);
}

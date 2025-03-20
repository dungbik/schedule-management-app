package nbc.sma.repository;

import nbc.sma.dto.request.ScheduleSearchCond;
import nbc.sma.dto.response.ScheduleResponse;
import nbc.sma.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {

    void save(Schedule schedule);

    List<ScheduleResponse> findAllResponse(ScheduleSearchCond cond);

    Schedule findById(Long scheduleId);

    ScheduleResponse findResponse(Long scheduleId);

    void update(Long scheduleId, String task);

    void deleteById(Long scheduleId);
}

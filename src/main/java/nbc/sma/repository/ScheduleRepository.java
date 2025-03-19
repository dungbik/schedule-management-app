package nbc.sma.repository;

import nbc.sma.controller.request.ScheduleSearchCond;
import nbc.sma.controller.response.ScheduleResponse;
import nbc.sma.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {

    void save(Schedule schedule);

    List<ScheduleResponse> findAllResponse(ScheduleSearchCond cond);

    Schedule find(Long scheduleId);

    ScheduleResponse findResponse(Long scheduleId);

    void update(Long scheduleId, String task);

    void delete(Long scheduleId);
}

package nbc.sma.repository;

import nbc.sma.controller.request.EditScheduleRequest;
import nbc.sma.controller.request.ScheduleSearchCond;
import nbc.sma.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {

    Schedule save(Schedule schedule);

    List<Schedule> findAll(ScheduleSearchCond cond);

    Schedule find(Long scheduleId);

    void update(Long scheduleId, EditScheduleRequest req);
}

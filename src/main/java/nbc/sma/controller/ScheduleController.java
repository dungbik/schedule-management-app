package nbc.sma.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nbc.sma.controller.request.CreateScheduleRequest;
import nbc.sma.controller.request.EditScheduleRequest;
import nbc.sma.controller.request.ScheduleSearchCond;
import nbc.sma.controller.response.ScheduleResponse;
import nbc.sma.controller.response.FindSchedulesResponse;
import nbc.sma.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<ScheduleResponse> createSchedule(
            @Valid @RequestBody CreateScheduleRequest req
    ) {
        ScheduleResponse res = scheduleService.createSchedule(req);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<FindSchedulesResponse> findSchedules(
            @ModelAttribute ScheduleSearchCond cond
    ) {
        FindSchedulesResponse res = scheduleService.findSchedules(cond);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<ScheduleResponse> findSchedule(
            @PathVariable Long scheduleId
    ) {
        ScheduleResponse res = scheduleService.findSchedule(scheduleId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/{scheduleId}/edit")
    public ResponseEntity<Void> editSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody EditScheduleRequest req
    ) {
        scheduleService.editSchedule(scheduleId, req);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

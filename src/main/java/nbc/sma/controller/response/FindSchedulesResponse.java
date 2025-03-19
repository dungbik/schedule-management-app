package nbc.sma.controller.response;

import java.util.List;

public record FindSchedulesResponse(
        List<ScheduleResponse> results,
        int size,
        int curPage
) {
}

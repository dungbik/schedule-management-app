package nbc.sma.controller.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleSearchCond {
    private LocalDate updatedAt;
    private String username;
    private Long userId;

    @Size(min = 1)
    private Integer page = 1;

    @Size(min = 1, max = 30)
    private Integer size = 20;
}

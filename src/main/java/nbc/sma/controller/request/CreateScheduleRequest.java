package nbc.sma.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateScheduleRequest(
        @NotBlank @Size(max = 50) String username,
        @NotBlank @Size(max = 50) String password,
        @NotBlank @Size(max = 200) String task
) {

}

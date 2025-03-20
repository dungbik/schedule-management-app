package nbc.sma.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateScheduleRequest(
        @NotNull                            Long userId,
        @NotNull @NotBlank @Size(max = 50)  String password,
        @NotNull @NotBlank @Size(max = 200) String task
) {

}

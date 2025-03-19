package nbc.sma.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EditScheduleRequest(
        @NotNull @NotBlank @Size(max = 50) String username,
        @NotNull @NotBlank @Size(max = 50) String task,
        @NotNull @NotBlank @Size(max = 50) String password
) {
}

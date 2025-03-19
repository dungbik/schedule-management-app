package nbc.sma.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotNull @NotBlank @Size(max = 50) @Email String email,
        @NotNull @NotBlank @Size(max = 50)        String name
) {
}

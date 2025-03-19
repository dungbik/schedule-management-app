package nbc.sma.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object detail;

    public static ErrorResponse of(int status, String message) {
        return new ErrorResponse(status, message, null);
    }

    public static ErrorResponse of(int status, String message, Object detail) {
        return new ErrorResponse(status, message, detail);
    }
}

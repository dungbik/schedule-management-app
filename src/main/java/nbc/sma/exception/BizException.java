package nbc.sma.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class BizException extends RuntimeException {
    private final HttpStatus status;
    private final String message;
}

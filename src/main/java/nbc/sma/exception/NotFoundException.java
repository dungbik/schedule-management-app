package nbc.sma.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BizException {

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}

package nbc.sma.exception;

import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends BizException {

    public InvalidPasswordException() {
        super(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
    }
}

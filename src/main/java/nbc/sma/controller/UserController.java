package nbc.sma.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nbc.sma.dto.request.RegisterRequest;
import nbc.sma.dto.response.UserResponse;
import nbc.sma.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody RegisterRequest req
    ) {
        UserResponse res = userService.register(req);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}

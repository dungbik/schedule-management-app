package nbc.sma.dto.mapper;

import nbc.sma.dto.request.RegisterRequest;
import nbc.sma.dto.response.UserResponse;
import nbc.sma.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper {

    public User toEntity(RegisterRequest req) {
        return User.builder()
                .email(req.email())
                .name(req.name())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(user.getId(), user.getEmail(), user.getName());
    }
}

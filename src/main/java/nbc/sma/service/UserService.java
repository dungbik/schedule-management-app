package nbc.sma.service;

import lombok.RequiredArgsConstructor;
import nbc.sma.dto.request.RegisterRequest;
import nbc.sma.dto.response.UserResponse;
import nbc.sma.entity.User;
import nbc.sma.dto.mapper.UserMapper;
import nbc.sma.repository.UserRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponse register(RegisterRequest req) {
        User user = userMapper.toEntity(req);
        userRepository.save(user);

        return userMapper.toResponse(user);
    }

    public void editName(Long userId, String name) {
        userRepository.update(userId, name);
    }

    public User find(Long userId) {
        return userRepository.findById(userId);
    }
}

package nbc.sma.repository;

import nbc.sma.entity.User;

public interface UserRepository {
    void save(User user);

    void update(Long userId, String name);

    User findById(Long userId);
}

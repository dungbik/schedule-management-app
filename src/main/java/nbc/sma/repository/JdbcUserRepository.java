package nbc.sma.repository;

import lombok.RequiredArgsConstructor;
import nbc.sma.entity.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class JdbcUserRepository implements UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void save(User user) {
        String sql = "INSERT INTO user (email, name, created_at, updated_at) VALUES (:email, :name, :createdAt, :updatedAt)";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(user);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder);
        user.setId(keyHolder.getKey().longValue());

    }

    @Override
    public void update(Long userId, String name) {
        String sql = "UPDATE user " +
                "SET name=:name, updated_at=:updatedAt " +
                "WHERE id=:id";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("name", name)
                .addValue("updatedAt", LocalDateTime.now())
                .addValue("id", userId);

        jdbcTemplate.update(sql, param);
    }

    @Override
    public User find(Long userId) {
        String sql = "SELECT id, email, name, created_at, updated_at FROM user WHERE id = :userId";
        Map<String, Object> param = Map.of("userId", userId);
        return jdbcTemplate.queryForObject(sql, param, userRowMapper());
    }

    private RowMapper<User> userRowMapper() {
        return BeanPropertyRowMapper.newInstance(User.class);
    }

}

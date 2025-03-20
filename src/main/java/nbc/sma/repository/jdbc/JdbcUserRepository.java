package nbc.sma.repository.jdbc;

import lombok.RequiredArgsConstructor;
import nbc.sma.entity.User;
import nbc.sma.repository.UserRepository;
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

    /**
     * 사용자 엔티티를 저장하고 저장된 ID를 넣어준다.
     * @param user 사용자 엔티티
     */
    @Override
    public void save(User user) {
        String sql = "INSERT INTO user (email, name, created_at, updated_at) VALUES (:email, :name, :createdAt, :updatedAt)";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(user);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder);
        user.setId(keyHolder.getKey().longValue());

    }

    /**
     * userId 에 해당하는 사용자의 이름을 변경한다.
     * @param userId 유저 id
     * @param name 사용자 이름
     */
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

    /**
     * userId 에 해당하는 사용자 정보를 반환한다.
     * @param userId 사용자 id
     * @return userId 에 해당하는 사용자 정보
     */
    @Override
    public User findById(Long userId) {
        String sql = "SELECT id, email, name, created_at, updated_at FROM user WHERE id = :userId";
        Map<String, Object> param = Map.of("userId", userId);
        return jdbcTemplate.queryForObject(sql, param, userRowMapper());
    }

    /**
     * 유저 정보를 만드는 Mapper
     * @return 유저 정보를 만드는 Mapper
     */
    private RowMapper<User> userRowMapper() {
        return BeanPropertyRowMapper.newInstance(User.class);
    }

}

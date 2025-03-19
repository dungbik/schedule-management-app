package nbc.sma.repository;

import lombok.RequiredArgsConstructor;
import nbc.sma.controller.request.EditScheduleRequest;
import nbc.sma.controller.request.ScheduleSearchCond;
import nbc.sma.entity.Schedule;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class JdbcScheduleRepository implements ScheduleRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Schedule save(Schedule schedule) {
        String sql = "INSERT INTO schedule (username, password, task, created_at, updated_at) VALUES (:username, :password, :task, :createdAt, :updatedAt)";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(schedule);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder);

        schedule.setId(keyHolder.getKey().longValue());

        return schedule;
    }

    @Override
    public List<Schedule> findAll(ScheduleSearchCond cond) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(cond);
        String sql = createSearchQuery(cond);

        return jdbcTemplate.query(sql, param, scheduleRowMapper());
    }

    @Override
    public Schedule find(Long scheduleId) {
        String sql = "SELECT id, username, task, created_at, updated_at FROM schedule where id = :id";
        Map<String, Object> param = Map.of("id", scheduleId);
        return jdbcTemplate.queryForObject(sql, param, scheduleRowMapper());
    }

    @Override
    public void update(Long scheduleId, EditScheduleRequest req) {
        String sql = "UPDATE schedule " +
                "SET username=:username, task=:task, updated_at=:updatedAt " +
                "WHERE id=:id";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("username", req.username())
                .addValue("task", req.task())
                .addValue("updatedAt", LocalDateTime.now())
                .addValue("id", scheduleId);

        jdbcTemplate.update(sql, param);
    }

    @Override
    public void delete(Long scheduleId) {
        String sql = "DELETE FROM schedule WHERE id = :id";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", scheduleId);

        jdbcTemplate.update(sql, param);
    }

    private String createSearchQuery(ScheduleSearchCond cond) {
        String sql = "SELECT id, username, task, created_at, updated_at FROM schedule";
        if (cond.updatedAt() != null || StringUtils.hasText(cond.username())) {
            sql += " WHERE";
        }

        if (cond.updatedAt() != null) {
            sql += " DATE(updated_at) = :updatedAt";
        }

        if (StringUtils.hasText(cond.username())) {
            if (cond.updatedAt() != null) {
                sql += " AND";
            }
            sql += " username like concat('%', :username, '%')";
        }

        sql += " ORDER BY updated_at DESC";
        return sql;
    }

    private RowMapper<Schedule> scheduleRowMapper() {
        return BeanPropertyRowMapper.newInstance(Schedule.class);
    }
}

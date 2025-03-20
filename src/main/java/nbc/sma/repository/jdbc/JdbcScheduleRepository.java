package nbc.sma.repository.jdbc;

import lombok.RequiredArgsConstructor;
import nbc.sma.dto.request.ScheduleSearchCond;
import nbc.sma.dto.response.ScheduleResponse;
import nbc.sma.dto.response.UserResponse;
import nbc.sma.entity.Schedule;
import nbc.sma.exception.NotFoundException;
import nbc.sma.repository.ScheduleRepository;
import org.springframework.dao.EmptyResultDataAccessException;
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

    /**
     * 일정 엔티티를 저장하고 저장된 id를 넣어준다.
     * @param schedule 일정 엔티티
     */
    @Override
    public void save(Schedule schedule) {
        String sql = "INSERT INTO schedule (user_id, password, task, created_at, updated_at) VALUES (:userId, :password, :task, :createdAt, :updatedAt)";
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(schedule);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder);

        schedule.setId(keyHolder.getKey().longValue());
    }

    /**
     * 검색을 수행하여 해당하는 일정 정보 목록을 반환한다.
     * @param cond 검색 조건
     * @return 검색 조건에 해당하는 일정 정보 목록
     */
    @Override
    public List<ScheduleResponse> findAllResponse(ScheduleSearchCond cond) {
        String sql = createSearchQuery(cond);
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("updatedAt", cond.getUpdatedAt())
                .addValue("username", cond.getUsername())
                .addValue("userId", cond.getUserId())
                .addValue("limit", cond.getSize())
                .addValue("offset", (cond.getPage() - 1) * cond.getSize());

        return jdbcTemplate.query(sql, param, scheduleResponseRowMapper());
    }

    /**
     * scheduleId 에 해당하는 일정 정보를 반환한다.
     * @param scheduleId 일정 id
     * @return id가 scheduleId에 해당하는 일정 정보
     */
    @Override
    public Schedule find(Long scheduleId) {
        String sql = "SELECT id, user_id, password, task, created_at, updated_at FROM schedule WHERE id = :scheduleId";
        Map<String, Object> param = Map.of("scheduleId", scheduleId);
        return jdbcTemplate.queryForObject(sql, param, scheduleRowMapper());
    }

    /**
     * scheduleId 에 해당하며 작성자 정보가 포함된 일정 정보를 반환한다.
     * @param scheduleId 일정 id
     * @return id가 scheduleId에 해당하는 작성자 정보가 포한된 일정 정보
     */
    @Override
    public ScheduleResponse findResponse(Long scheduleId) {
        String sql = "SELECT s.id, u.id, u.email, u.name, task, s.created_at, s.updated_at FROM schedule s JOIN user u ON u.id = s.user_id WHERE s.id = :id";
        Map<String, Object> param = Map.of("id", scheduleId);
        try {
            return jdbcTemplate.queryForObject(sql, param, scheduleResponseRowMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException("존재하지 않는 일정입니다.");
        }
    }

    /**
     * scheduleId 에 해당하는 일정의 할 일 내용을 수정한다.
     * @param scheduleId 일정 id
     * @param task 할 일
     */
    @Override
    public void update(Long scheduleId, String task) {
        String sql = "UPDATE schedule " +
                "SET task=:task, updated_at=:updatedAt " +
                "WHERE id=:id";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("task", task)
                .addValue("updatedAt", LocalDateTime.now())
                .addValue("id", scheduleId);

        jdbcTemplate.update(sql, param);
    }

    /**
     * scheduleId 에 해당하는 일정을 삭제한다.
     * @param scheduleId 일정 id
     */
    @Override
    public void delete(Long scheduleId) {
        String sql = "DELETE FROM schedule WHERE id = :id";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("id", scheduleId);

        jdbcTemplate.update(sql, param);
    }

    /**
     * 검색 쿼리를 동적으로 생성
     * @param cond 검색 조건
     * @return 검색을 수행하는 쿼리문
     */
    private String createSearchQuery(ScheduleSearchCond cond) {
        String sql = "SELECT DISTINCT s.id, u.email, u.name, s.task, s.created_at, s.updated_at FROM schedule s JOIN user u ON u.id = s.user_id";

        if (StringUtils.hasText(cond.getUsername())) {
            sql += " AND u.name like concat('%', :username, '%')";
        }

        if (cond.getUpdatedAt() != null) {
            sql += " WHERE DATE(s.updated_at) = :updatedAt";
        }

        if (cond.getUserId() != null) {
            sql += " AND s.user_id = :userId";
        }

        sql += " ORDER BY s.updated_at DESC LIMIT :limit OFFSET :offset";
        return sql;
    }

    /**
     * 일정 정보를 만드는 Mapper
     * @return 일정 정보를 만드는 Mapper
     */
    private RowMapper<Schedule> scheduleRowMapper() {
        return BeanPropertyRowMapper.newInstance(Schedule.class);
    }

    /**
     * 작성자 정보를 포함한 일정 정보를 만드는 Mapper
     * @return 작성자 정보를 포함한 일정 정보를 만드는 Mapper
     */
    private RowMapper<ScheduleResponse> scheduleResponseRowMapper() {
        return ((rs, rowNum) -> {
            ScheduleResponse response = new ScheduleResponse();
            response.setScheduleId(rs.getLong("s.id"));
            response.setUser(new UserResponse(rs.getLong("u.id"), rs.getString("u.email"), rs.getString("u.name")));
            response.setTask(rs.getString("s.task"));
            response.setCreatedAt(rs.getTimestamp("s.created_at").toLocalDateTime());
            response.setUpdatedAt(rs.getTimestamp("s.updated_at").toLocalDateTime());
            return response;
        });
    }
}

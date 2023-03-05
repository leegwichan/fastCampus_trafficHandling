package com.example.fastcampusmysql.domain.follow.repository;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FollowRepository {

    private static final String TABLE_NAME = "FOLLOW";
    private static final String FIND_BY_FROM_MEMBER_ID_SQL =
            String.format("SELECT * FROM %s WHERE fromMemberId = :fromMemberId", TABLE_NAME);
    private static final RowMapper<Follow> ROW_MAPPER = (ResultSet resultSet, int rowNum) ->
            Follow.builder()
                    .id(resultSet.getLong("id"))
                    .fromMemberId(resultSet.getLong("fromMemberId"))
                    .toMemberId(resultSet.getLong("toMemberId"))
                    .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
                    .build();

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Follow> findAllByFromMemberId(Long fromMemberId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("fromMemberId", fromMemberId);
        return namedParameterJdbcTemplate.query(FIND_BY_FROM_MEMBER_ID_SQL, params, ROW_MAPPER);
    }

    public Follow save(Follow follow) {
        if (follow.getId() == null) {
            return insert(follow);
        }
        throw new UnsupportedOperationException("Follow 는 갱신을 지원하지 않습니다.");
    }

    private Follow insert(Follow follow) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource params = new BeanPropertySqlParameterSource(follow);
        long id = jdbcInsert.executeAndReturnKey(params).longValue();

        return Follow.builder()
                .id(id)
                .toMemberId(follow.getToMemberId())
                .fromMemberId(follow.getFromMemberId())
                .createdAt(follow.getCreatedAt())
                .build();
    }
}

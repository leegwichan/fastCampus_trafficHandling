package com.example.fastcampusmysql.domain.post.repository;

import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.entity.Timeline;
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
public class TimelineRepository {

    private static final String TABLE_NAME = "Timeline";
    private static final RowMapper<Timeline> MAPPER = (ResultSet resultset, int rowNum) -> Timeline.builder()
            .id(resultset.getLong("id"))
            .postId(resultset.getLong("postId"))
            .memberId(resultset.getLong("memberId"))
            .createdAt(resultset.getObject("createdAt", LocalDateTime.class)).build();

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<Timeline> findAllByMemberIdOrderByIdDesc(Long memberId, int size) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("size", size);

        String sql = String.format("SELECT * FROM %s\n" +
                "where memberId = :memberId\n" +
                "ORDER BY id desc LIMIT : size", TABLE_NAME);

        return namedParameterJdbcTemplate.query(sql, params, MAPPER);
    }

    public List<Timeline> findAllByLessThanIdAndMemberIdAndOrderByIdDesc(Long id, Long memberId, Integer size) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("id", id)
                .addValue("size", size);

        String sql = String.format("SELECT * FROM %s\n" +
                "where memberId = :memberId AND id < :id\n" +
                "ORDER BY id desc LIMIT :size", TABLE_NAME);

        return namedParameterJdbcTemplate.query(sql, params, MAPPER);
    }

    public Timeline save(Timeline timeline) {
        if (timeline.getId() == null) {
            return insert(timeline);
        }
        throw new UnsupportedOperationException("Post 는 갱신을 지원하지 않습니다.");
    }

    public void insert(List<Timeline> timelines) {
        String sql = String.format("INSERT INTO `%s` (postId, memberId, createdAt) " +
                "VALUES (:postId, :memberId, :createdAt)", TABLE_NAME);

        SqlParameterSource[] parms = timelines.stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);
        namedParameterJdbcTemplate.batchUpdate(sql, parms);
    }

    private Timeline insert(Timeline timeline) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource params = new BeanPropertySqlParameterSource(timeline);
        long id = jdbcInsert.executeAndReturnKey(params).longValue();

        return Timeline.builder()
                .id(id)
                .memberId(timeline.getMemberId())
                .postId(timeline.getPostId())
                .createdAt(timeline.getCreatedAt()).build();
    }
}

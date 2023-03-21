package com.example.fastcampusmysql.domain.post.repository;

import com.example.fastcampusmysql.util.repository.PageHelper;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class PostRepository {

    private static final String TABLE_NAME = "POST";
    private static final RowMapper<Post> POST_MAPPER = (ResultSet resultset, int rowNum) -> Post.builder()
                    .id(resultset.getLong("id"))
                    .memberId(resultset.getLong("memberId"))
                    .contents(resultset.getString("contents"))
                    .createdDate(resultset.getObject("createdDate", LocalDate.class))
                    .createdAt(resultset.getObject("createdAt", LocalDateTime.class)).build();
    private static final RowMapper<DailyPostCount> DAILY_POST_COUNT_MAPPER = (ResultSet resultSet, int rowNum) ->
            new DailyPostCount(
                    resultSet.getLong("memberId"),
                    resultSet.getObject("createdDate", LocalDate.class),
                    resultSet.getLong("count")
            );

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<DailyPostCount> groupByCreateDate(DailyPostCountRequest request) {
        String sql = String.format("SELECT createdDate, memberId, count(id) AS count FROM %s " +
                "WHERE memberId = :memberId and createdDate between :firstDate and :lastDate " +
                "GROUP BY createdDate, memberId", TABLE_NAME);

        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(request);
        return namedParameterJdbcTemplate.query(sql, params, DAILY_POST_COUNT_MAPPER);
    }

    public Page<Post> findAllByMemberId(Long memberId, Pageable pageable) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("size", pageable.getPageSize())
                .addValue("offset", pageable.getOffset());

        String sql = String.format("SELECT * FROM %s WHERE memberId = :memberId\n" +
                "ORDER BY %s\n" +
                "LIMIT :size OFFSET :offset", TABLE_NAME, PageHelper.orderBy(pageable.getSort()));

        List<Post> posts = namedParameterJdbcTemplate.query(sql, params, POST_MAPPER);
        return new PageImpl(posts, pageable, getCount(memberId));
    }

    private Long getCount(Long memberId) {
        String sql = String.format("SELECT count(id) FROM %s WHERE memberId = :memberId", TABLE_NAME);

        MapSqlParameterSource params = new MapSqlParameterSource().addValue("memberId", memberId);
        return namedParameterJdbcTemplate.queryForObject(sql, params, Long.class);
    }

    public List<Post> findAllByMemberIdAndOrderByIdDesc(Long memberId, Integer size) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("size", size);

        String sql = String.format("SELECT * FROM %s\n" +
                "where memberId = :memberId\n" +
                "ORDER BY id desc LIMIT :size", TABLE_NAME);

        return namedParameterJdbcTemplate.query(sql, params, POST_MAPPER);
    }

    public List<Post> findAllByLessThanIdAndMemberIdAndOrderByIdDesc(Long id, Long memberId, Integer size) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("id", id)
                .addValue("size", size);

        String sql = String.format("SELECT * FROM %s\n" +
                "where memberId = :memberId AND id < :id\n" +
                "ORDER BY id desc LIMIT :size", TABLE_NAME);

        return namedParameterJdbcTemplate.query(sql, params, POST_MAPPER);
    }

    public List<Post> findAllByInMemberIdAndOrderByIdDesc(List<Long> memberIds, Integer size) {
        if (memberIds.isEmpty()) {
            return List.of();
        }

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("memberIds", memberIds)
                .addValue("size", size);

        String sql = String.format("SELECT * FROM %s\n" +
                "where memberId in (:memberIds)\n" +
                "ORDER BY id desc LIMIT :size", TABLE_NAME);

        return namedParameterJdbcTemplate.query(sql, params, POST_MAPPER);
    }

    public List<Post> findAllByLessThanIdAndInMemberIdAndOrderByIdDesc(Long id, List<Long> memberIds, Integer size) {
        if (memberIds.isEmpty()) {
            return List.of();
        }

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("memberIds", memberIds)
                .addValue("id", id)
                .addValue("size", size);

        String sql = String.format("SELECT * FROM %s\n" +
                "where memberId in (:memberIds) AND id < :id\n" +
                "ORDER BY id desc LIMIT :size", TABLE_NAME);

        return namedParameterJdbcTemplate.query(sql, params, POST_MAPPER);
    }

    public Post save(Post post) {
        if (post.getId() == null) {
            return insert(post);
        }
        throw new UnsupportedOperationException("Post 는 갱신을 지원하지 않습니다.");
    }

    private Post insert(Post post) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource params = new BeanPropertySqlParameterSource(post);
        long id = jdbcInsert.executeAndReturnKey(params).longValue();

        return Post.builder()
                .id(id)
                .memberId(post.getMemberId())
                .contents(post.getContents())
                .createdDate(post.getCreatedDate())
                .createdAt(post.getCreatedAt())
                .build();
    }

    public void bulkInsert(List<Post> posts) {
        var sql = String.format(" INSERT INTO `%s` (memberId, contents, createdDate, createdAt) " +
                "VALUES (:memberId, :contents, :createdDate, :createdAt)", TABLE_NAME);

        SqlParameterSource[] params = posts
                .stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);
        namedParameterJdbcTemplate.batchUpdate(sql, params);
    }
}

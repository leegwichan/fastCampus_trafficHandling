package com.example.fastcampusmysql.domain.membernamehistory.repository;

import com.example.fastcampusmysql.domain.membernamehistory.entity.MemberNicknameHistory;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class MemberNameHistoryRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String TABLE_NAME = "MEMBER_NICKNAME_HISTORY";
    private static final String FIND_SQL_BY_MEMBER_ID =
            String.format("SELECT * FROM %s WHERE memberId = :memberId", TABLE_NAME);
    private static final RowMapper<MemberNicknameHistory> ROW_MAPPER = (ResultSet resultSet, int rowNum) ->
            MemberNicknameHistory.builder()
                    .id(resultSet.getLong("id"))
                    .memberId(resultSet.getLong("memberId"))
                    .nickname(resultSet.getString("nickname"))
                    .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
                    .build();

    public List<MemberNicknameHistory> findAllByMemberId(Long memberId) {
        var params = new MapSqlParameterSource()
                .addValue("memberId", memberId);
        return namedParameterJdbcTemplate.query(FIND_SQL_BY_MEMBER_ID, params, ROW_MAPPER);
    }

    public MemberNicknameHistory save(MemberNicknameHistory history) {
        if (history.getId() == 0L) {
            return insert(history);
        }
        throw new UnsupportedOperationException("MemberNameHistoryRepository 는 갱신을 지원하지 않습니다.");
    }

    private MemberNicknameHistory insert(MemberNicknameHistory history) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource params = new BeanPropertySqlParameterSource(history);
        long id = jdbcInsert.executeAndReturnKey(params).longValue();

        return MemberNicknameHistory.builder()
                .id(id)
                .memberId(history.getMemberId())
                .nickname(history.getNickname())
                .createdAt(history.getCreatedAt())
                .build();
    }
}

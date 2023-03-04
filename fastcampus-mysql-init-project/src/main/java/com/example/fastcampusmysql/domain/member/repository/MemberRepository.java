package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entity.Member;
import lombok.AllArgsConstructor;
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
import java.util.Optional;

@Repository
@AllArgsConstructor
public class MemberRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final String TABLE_NAME = "MEMBER";
    private static final String FIND_SQL = String.format("SELECT * FROM %s WHERE id = :id", TABLE_NAME);
    private static final String UPDATE_SQL = String.format(
            "UPDATE %s SET email = :email, nickname = :nickname, birthday = :birthday WHERE id = :id", TABLE_NAME);

    public Optional<Member> findById(Long id) {
        var param = new MapSqlParameterSource()
                .addValue("id", id);
        RowMapper<Member> rowMapper = (ResultSet resultSet, int rowNum) -> Member.builder()
                .id(resultSet.getLong("id"))
                .email(resultSet.getString("email"))
                .nickname(resultSet.getString("nickname"))
                .birthday(resultSet.getObject("birthday", LocalDate.class))
                .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
                .build();

        var member = namedParameterJdbcTemplate.queryForObject(FIND_SQL, param, rowMapper);
        return Optional.ofNullable(member);
    }

    public Member save(Member member) {
        if (member.getId() == 0L) {
            return insert(member);
        }
        return update(member);
    }

    private Member insert(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        long id = jdbcInsert.executeAndReturnKey(params).longValue();

        return Member.builder()
                .id(id)
                .nickname(member.getNickname())
                .email(member.getEmail())
                .birthday(member.getBirthday())
                .build();
    }

    private Member update(Member member) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        namedParameterJdbcTemplate.update(UPDATE_SQL, params);
        return member;
    }
}

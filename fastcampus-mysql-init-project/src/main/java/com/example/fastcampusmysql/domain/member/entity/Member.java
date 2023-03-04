package com.example.fastcampusmysql.domain.member.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Member {

    private final long id;
    private String nickname;
    private final String email;
    private LocalDate birthday;
    private final LocalDateTime createdAt;

    private final static int NICKNAME_MAX_LENGTH = 10;

    @Builder
    public Member(long id, String nickname, String email, LocalDate birthday, LocalDateTime createdAt) {
        validateNickname(nickname);

        this.id = id;
        this.nickname = Objects.requireNonNull(nickname);
        this.email = Objects.requireNonNull(email);
        this.birthday = Objects.requireNonNull(birthday);
        this.createdAt = Objects.requireNonNullElse(createdAt, LocalDateTime.now());
    }

    public void changeNickname(String to) {
        Objects.requireNonNull(to);
        validateNickname(to);
        this.nickname = to;
    }

    private void validateNickname(String nickname) {
        Assert.isTrue(nickname.length() <= NICKNAME_MAX_LENGTH, "최대 길이를 초과했습니다.");
    }
}

package com.example.fastcampusmysql.domain.post.entity;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Post {

    private final Long id;
    private final Long memberId;
    private String contents;
    private Long likeCount;
    private final Long version;
    private final LocalDate createdDate;
    private final LocalDateTime createdAt;

    @Builder
    public Post(Long id, Long memberId, String contents, Long likeCount, Long version,
                LocalDate createdDate, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.contents = Objects.requireNonNull(contents);
        this.likeCount = Objects.requireNonNullElse(likeCount, 0L);
        this.version = Objects.requireNonNullElse(version, 1L);
        this.createdDate = Objects.requireNonNullElse(createdDate, LocalDate.now());
        this.createdAt = Objects.requireNonNullElse(createdAt, LocalDateTime.now());
    }

    public void like() {
        likeCount++;
    }

    public void updateContent(String contents) {
        this.contents = Objects.requireNonNull(contents);
    }
}

package com.example.fastcampusmysql.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
public class PostDto {
    private Long id;
    private Long memberId;
    private String contents;
    private LocalDate createdDate;
    private Long likeCount;
}

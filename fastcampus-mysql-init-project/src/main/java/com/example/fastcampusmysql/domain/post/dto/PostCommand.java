package com.example.fastcampusmysql.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class PostCommand {
    private final Long memberId;
    private final String contents;
}

package com.example.fastcampusmysql.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
public class DailyPostCount {

    private Long memberId;
    private LocalDate date;
    private Long postCount;
}

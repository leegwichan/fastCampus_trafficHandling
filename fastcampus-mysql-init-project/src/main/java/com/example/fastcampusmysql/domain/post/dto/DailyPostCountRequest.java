package com.example.fastcampusmysql.domain.post.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
public class DailyPostCountRequest {
    private Long memberId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate firstDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate lastDate;
}

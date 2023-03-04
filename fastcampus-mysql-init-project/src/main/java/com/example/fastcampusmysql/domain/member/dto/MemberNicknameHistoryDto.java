package com.example.fastcampusmysql.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class MemberNicknameHistoryDto {
    private Long id;
    private Long memberId;
    private String nickname;
    private LocalDateTime createdAt;
}

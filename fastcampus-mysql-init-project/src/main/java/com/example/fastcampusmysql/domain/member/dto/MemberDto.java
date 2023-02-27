package com.example.fastcampusmysql.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Getter
public class MemberDto {
    private Long id;
    private String email;
    private String nickname;
    private LocalDate birthday;
}

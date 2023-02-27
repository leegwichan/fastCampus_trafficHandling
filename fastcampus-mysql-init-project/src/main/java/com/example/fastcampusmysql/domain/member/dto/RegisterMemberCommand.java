package com.example.fastcampusmysql.domain.member.dto;

import lombok.Getter;
import java.time.LocalDate;

@Getter
public class RegisterMemberCommand {
    private String nickname;
    private String email;
    private LocalDate birthday;
}

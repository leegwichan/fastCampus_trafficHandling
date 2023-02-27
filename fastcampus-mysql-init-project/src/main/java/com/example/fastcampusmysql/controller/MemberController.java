package com.example.fastcampusmysql.controller;

import com.example.fastcampusmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.domain.member.service.MemberWriteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class MemberController {

    private final MemberWriteService writeService;

    @PostMapping("/members")
    public void register(@RequestBody RegisterMemberCommand command) {
        writeService.create(command);
    }
}

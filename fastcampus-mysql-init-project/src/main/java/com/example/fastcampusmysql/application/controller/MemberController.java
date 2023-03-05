package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.dto.MemberNicknameHistoryDto;
import com.example.fastcampusmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.domain.member.dto.UpdateNicknameMemberCommand;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.mapper.MemberMapper;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import com.example.fastcampusmysql.domain.member.service.MemberWriteService;
import com.example.fastcampusmysql.domain.membernamehistory.entity.MemberNicknameHistory;
import com.example.fastcampusmysql.domain.membernamehistory.mapper.MemberNicknameHistoryMapper;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@AllArgsConstructor
@RestController
public class MemberController {

    private final MemberWriteService writeService;
    private final MemberReadService readService;
    private final MemberMapper memberMapper;
    private final MemberNicknameHistoryMapper memberNicknameHistoryMapper;

    @GetMapping("/members/{id}")
    public MemberDto getMember(@PathVariable("id") long memberId) {
        Member member = readService.getMember(memberId);
        return memberMapper.toDto(member);
    }

    @GetMapping("/members/{id}/nickname-histories")
    public List<MemberNicknameHistoryDto> getMemberNicknameHistories(@PathVariable("id") long memberId) {
        List<MemberNicknameHistory> histories = readService.getMemberNameHistories(memberId);
        return memberNicknameHistoryMapper.toDto(histories);
    }

    @PostMapping("/members")
    public MemberDto register(@RequestBody RegisterMemberCommand command) {
        Member member = writeService.create(command);
        return memberMapper.toDto(member);
    }

    @PatchMapping("/members/{id}/nickname")
    public MemberDto changeNickname(@PathVariable("id") long memberId,
                                    @RequestBody UpdateNicknameMemberCommand command) {
        Member member = writeService.changeNickname(memberId, command.getNickname());
        return memberMapper.toDto(member);
    }
}

package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.application.usacase.CreateFollowMemberUsacase;
import com.example.fastcampusmysql.application.usacase.GetFollowMemberUsacase;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {

    private final CreateFollowMemberUsacase createFollowMemberUsacase;
    private final GetFollowMemberUsacase getFollowMemberUsacase;
    private final MemberMapper memberMapper;

    @GetMapping("{fromId}")
    public List<MemberDto> getByFromId(@PathVariable("fromId") Long fromMemberId) {
        List<Member> members = getFollowMemberUsacase.execute(fromMemberId);
        return memberMapper.toDto(members);
    }

    @PostMapping("/{fromId}/{toId}")
    public void create(@PathVariable("fromId") Long fromMemberId,
                       @PathVariable("toId") Long toMemberId) {
        createFollowMemberUsacase.execute(fromMemberId, toMemberId);
    }

}

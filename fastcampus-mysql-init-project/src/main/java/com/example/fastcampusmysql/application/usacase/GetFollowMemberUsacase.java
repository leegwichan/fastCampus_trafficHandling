package com.example.fastcampusmysql.application.usacase;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetFollowMemberUsacase {

    private final MemberReadService memberReadService;
    private final FollowReadService followReadService;

    public List<Member> execute(Long fromMemberId) {
        Member from = memberReadService.getMember(fromMemberId);
        List<Long> ids = followReadService.getFollowByFromIds(fromMemberId).stream()
                .map(Follow::getToMemberId)
                .collect(Collectors.toList());
        return memberReadService.getMembers(ids);
    }
}

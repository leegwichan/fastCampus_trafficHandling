package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberReadService {

    private final MemberRepository memberRepository;

    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow();
    }
}

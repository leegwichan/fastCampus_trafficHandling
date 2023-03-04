package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import com.example.fastcampusmysql.domain.membernamehistory.entity.MemberNicknameHistory;
import com.example.fastcampusmysql.domain.membernamehistory.repository.MemberNameHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberReadService {

    private final MemberRepository memberRepository;
    private final MemberNameHistoryRepository memberNameHistoryRepository;

    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow();
    }

    public List<MemberNicknameHistory> getMemberNameHistories(Long memberId) {
        return memberNameHistoryRepository.findAllByMemberId(memberId);
    }
}

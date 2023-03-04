package com.example.fastcampusmysql.domain.member.mapper;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.dto.MemberNicknameHistoryDto;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.membernamehistory.entity.MemberNicknameHistory;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MemberMapper {
    public MemberDto toDto(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .birthday(member.getBirthday())
                .build();
    }

    public List<MemberNicknameHistoryDto> toDto(List<MemberNicknameHistory> histories) {
        return histories.stream()
                .map(history -> toDto(history))
                .collect(Collectors.toList());
    }

    private MemberNicknameHistoryDto toDto(MemberNicknameHistory history) {
        return MemberNicknameHistoryDto.builder()
                .id(history.getId())
                .memberId(history.getMemberId())
                .nickname(history.getNickname())
                .createdAt(history.getCreatedAt())
                .build();
    }
}

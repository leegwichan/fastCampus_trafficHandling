package com.example.fastcampusmysql.domain.follow.service;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.repository.FollowRepository;
import com.example.fastcampusmysql.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FollowWriteService {

    private final FollowRepository followRepository;

    public Follow create(Member fromMember, Member toMember) {
        if (Objects.equals(fromMember.getId(), toMember.getId())) {
            throw new IllegalArgumentException("From, to 회원이 동일합니다.");
        }

        Follow follow = Follow.builder()
                .fromMemberId(fromMember.getId())
                .toMemberId(toMember.getId())
                .build();
        return followRepository.save(follow);
    }
}

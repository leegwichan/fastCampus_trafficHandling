package com.example.fastcampusmysql.domain.post.service;

import com.example.fastcampusmysql.domain.post.entity.Timeline;
import com.example.fastcampusmysql.domain.post.repository.TimelineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TimelineWriteService {

    private final TimelineRepository timelineRepository;

    public void deliveryToTimeline(long postId, List<Long> toMemberIds) {
        List<Timeline> timelines = toMemberIds.stream()
                .map(toMemberId -> toTimeline(toMemberId, postId))
                .collect(Collectors.toList());

        timelineRepository.insert(timelines);
    }

    private Timeline toTimeline(Long memberId, Long postId) {
        return Timeline.builder()
                .memberId(memberId)
                .postId(postId).build();
    }
}

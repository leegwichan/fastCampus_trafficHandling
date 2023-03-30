package com.example.fastcampusmysql.domain.post.service;

import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.entity.Timeline;
import com.example.fastcampusmysql.domain.post.repository.TimelineRepository;
import com.example.fastcampusmysql.util.repository.CursorRequest;
import com.example.fastcampusmysql.util.repository.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimelineReadService {

    private final TimelineRepository timelineRepository;

    public List<Timeline> getTimelines(Long memberId, CursorRequest cursorRequest) {
        return findAllBy(memberId, cursorRequest);
    }

    private List<Timeline> findAllBy(Long memberId, CursorRequest request) {
        if (request.isHasKey()) {
            return timelineRepository.findAllByLessThanIdAndMemberIdAndOrderByIdDesc(request.getKey(), memberId, request.getSize());
        }
        return timelineRepository.findAllByMemberIdOrderByIdDesc(memberId, request.getSize());
    }

    private Long getNextKey(List<Timeline> timelines) {
        return timelines.stream()
                .mapToLong(Timeline::getId)
                .min().orElse(CursorRequest.NONE_KEY);
    }
}

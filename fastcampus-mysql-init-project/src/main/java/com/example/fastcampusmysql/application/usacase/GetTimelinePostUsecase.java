package com.example.fastcampusmysql.application.usacase;

import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.service.PostReadService;
import com.example.fastcampusmysql.domain.post.service.TimelineReadService;
import com.example.fastcampusmysql.util.repository.CursorRequest;
import com.example.fastcampusmysql.util.repository.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class GetTimelinePostUsecase {

    private final FollowReadService followReadService;
    private final PostReadService postReadService;
    private final TimelineReadService timelineReadService;

    public PageCursor<Post> execute(Long memberId, CursorRequest cursorRequest) {
        List<Long> followerIds = followReadService.getFollowByFromIds(memberId).stream()
                .map(follow -> follow.getToMemberId())
                .collect(Collectors.toList());

        return postReadService.getPosts(followerIds, cursorRequest);
    }

    public PageCursor<Post> executeTimeline(Long memberId, CursorRequest cursorRequest) {
        // 정상적으로 구현되지 않았음
        List<Long> followerIds = followReadService.getFollowByFromIds(memberId).stream()
                .map(follow -> follow.getToMemberId())
                .collect(Collectors.toList());

        return postReadService.getPosts(followerIds, cursorRequest);
    }
}

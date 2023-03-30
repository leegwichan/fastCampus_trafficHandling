package com.example.fastcampusmysql.application.usacase;

import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.service.PostWriteService;
import com.example.fastcampusmysql.domain.post.service.TimelineWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CreatePostUsecase {

    private final PostWriteService postWriteService;
    private final FollowReadService followReadService;
    private final TimelineWriteService timelineWriteService;

    public Post execute(Post post) {
        Post saved = postWriteService.save(post);

        List<Long> toMemberIds = followReadService.getFollowByFromIds(post.getMemberId()).stream()
                .map(follow -> follow.getToMemberId())
                .collect(Collectors.toList());
        timelineWriteService.deliveryToTimeline(saved.getId(), toMemberIds);

        return saved;
    }

}

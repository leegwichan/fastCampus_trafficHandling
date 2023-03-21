package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.application.usacase.GetTimelinePostUsecase;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.dto.PostCommand;
import com.example.fastcampusmysql.domain.post.dto.PostDto;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.mapper.PostMapper;
import com.example.fastcampusmysql.domain.post.service.PostReadService;
import com.example.fastcampusmysql.domain.post.service.PostWriteService;
import com.example.fastcampusmysql.util.repository.CursorRequest;
import com.example.fastcampusmysql.util.repository.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostMapper mapper;
    private final PostWriteService postWriteService;
    private final PostReadService postReadService;
    private final GetTimelinePostUsecase getTimelinePostUsecase;

    @GetMapping("/members/{memberId}")
    public Page<Post> getPosts(@PathVariable Long memberId,
                               @RequestParam Integer page,
                               @RequestParam Integer size) {
        return postReadService.getPosts(memberId, PageRequest.of(page, size));
    }

    @GetMapping("/members/{memberId}/by-cursor")
    public PageCursor<Post> getPostsByCursor(@PathVariable Long memberId,
                                             @RequestParam(required = false) Long key,
                                             @RequestParam Integer size) {
        return postReadService.getPosts(memberId, CursorRequest.of(key, size));
    }

    @GetMapping("/daily-post-counts")
    public List<DailyPostCount> getDailyPostCount(DailyPostCountRequest request) {
        return postReadService.getDailyPostCount(request);
    }

    @GetMapping("/member/{memberId}/timeline")
    public PageCursor<Post> getTimeLine(@PathVariable Long memberId,
                                        @RequestParam(required = false) Long key,
                                        @RequestParam Integer size) {
        return getTimelinePostUsecase.execute(memberId, CursorRequest.of(key, size));
    }

    @PostMapping
    public PostDto post(@RequestBody PostCommand command) {
        Post post = mapper.toEntity(command);
        Post savePost = postWriteService.save(post);
        return mapper.toDto(savePost);
    }
}

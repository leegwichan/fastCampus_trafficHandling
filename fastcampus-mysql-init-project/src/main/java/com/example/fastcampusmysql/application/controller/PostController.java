package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.dto.PostCommand;
import com.example.fastcampusmysql.domain.post.dto.PostDto;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.mapper.PostMapper;
import com.example.fastcampusmysql.domain.post.service.PostReadService;
import com.example.fastcampusmysql.domain.post.service.PostWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostMapper mapper;
    private final PostWriteService postWriteService;
    private final PostReadService postReadService;

    @GetMapping("/daily-post-counts")
    public List<DailyPostCount> getDailyPostCount(DailyPostCountRequest request) {
        return postReadService.getDailyPostCount(request);
    }

    @PostMapping
    public PostDto post(@RequestBody PostCommand command) {
        Post post = mapper.toEntity(command);
        Post savePost = postWriteService.save(post);
        return mapper.toDto(savePost);
    }
}

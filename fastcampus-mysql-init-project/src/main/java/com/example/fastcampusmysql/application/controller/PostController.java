package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.domain.post.dto.PostCommand;
import com.example.fastcampusmysql.domain.post.dto.PostDto;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.mapper.PostMapper;
import com.example.fastcampusmysql.domain.post.service.PostWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostMapper mapper;
    private final PostWriteService postWriteService;

    @PostMapping
    public PostDto post(@RequestBody PostCommand command) {
        Post post = mapper.toEntity(command);
        Post savePost = postWriteService.save(post);
        return mapper.toDto(savePost);
    }
}

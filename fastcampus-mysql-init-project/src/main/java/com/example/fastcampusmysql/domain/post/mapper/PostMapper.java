package com.example.fastcampusmysql.domain.post.mapper;

import com.example.fastcampusmysql.domain.post.dto.PostCommand;
import com.example.fastcampusmysql.domain.post.dto.PostDto;
import com.example.fastcampusmysql.domain.post.entity.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {

    public Post toEntity(PostCommand command) {
        return Post.builder()
                .memberId(command.getMemberId())
                .contents(command.getContents()).build();
    }

    public PostDto toDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .memberId(post.getMemberId())
                .contents(post.getContents())
                .createdDate(post.getCreatedDate())
                .build();
    }
}

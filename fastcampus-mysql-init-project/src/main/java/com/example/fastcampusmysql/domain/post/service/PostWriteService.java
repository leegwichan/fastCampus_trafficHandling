package com.example.fastcampusmysql.domain.post.service;

import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostWriteService {

    private final PostRepository postRepository;

    public Post save(Post post) {
        return postRepository.save(post);
    }

//    // 비관적 Lock 구현
//    @Transactional
//    public Post like(Long postId) {
//        Post post = postRepository.findById(postId, true)
//                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));
//
//        post.like();
//        return postRepository.save(post);
//    }

    // Optimistic Lock 방법 구현 (repository 에서 version 구현)
    @Transactional
    public Post like(Long postId) {
        Post post = postRepository.findById(postId, false)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다."));

        post.like();
        return postRepository.save(post);
    }

    public Post updatePost(Long postId) {
        return null;
    }
}

package com.example.fastcampusmysql.domain.post.service;

import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import com.example.fastcampusmysql.util.repository.CursorRequest;
import com.example.fastcampusmysql.util.repository.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostReadService {

    private final PostRepository postRepository;

    public List<DailyPostCount> getDailyPostCount(DailyPostCountRequest request) {
        return postRepository.groupByCreateDate(request);
    }

    public Page<Post> getPosts(Long memberId, PageRequest pageRequest) {
        return postRepository.findAllByMemberId(memberId, pageRequest);
    }

    public PageCursor<Post> getPosts(Long memberId, CursorRequest request) {
        List<Post> posts = findAllBy(memberId, request);
        Long nextKey = getNextKey(posts);

        return new PageCursor<>(request.next(nextKey), posts);
    }

    private List<Post> findAllBy(Long memberId, CursorRequest request) {
        if (request.isHasKey()) {
            return postRepository.findAllByLessThanIdAndMemberIdAndOrderByIdDesc(request.getKey(), memberId, request.getSize());
        }
        return postRepository.findAllByMemberIdAndOrderByIdDesc(memberId, request.getSize());
    }

    public PageCursor<Post> getPosts(List<Long> memberIds, CursorRequest request) {
        List<Post> posts = findAllBy(memberIds, request);
        Long nextKey = getNextKey(posts);

        return new PageCursor<>(request.next(nextKey), posts);
    }

    private List<Post> findAllBy(List<Long> memberIds, CursorRequest request) {
        if (request.isHasKey()) {
            return postRepository.findAllByLessThanIdAndInMemberIdAndOrderByIdDesc(request.getKey(), memberIds, request.getSize());
        }
        return postRepository.findAllByInMemberIdAndOrderByIdDesc(memberIds, request.getSize());
    }

    private Long getNextKey(List<Post> posts) {
        return posts.stream()
                .mapToLong(Post::getId)
                .min().orElse(CursorRequest.NONE_KEY);
    }

}

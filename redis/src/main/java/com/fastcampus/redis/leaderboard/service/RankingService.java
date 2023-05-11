package com.fastcampus.redis.leaderboard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RankingService {

    private static final String LEADER_BOARD_KEY = "leaderBoard";

    private final StringRedisTemplate redisTemplate;

    public boolean setUserScore(String userId, Long score) {
        ZSetOperations zSetOps = redisTemplate.opsForZSet();
        return zSetOps.add(LEADER_BOARD_KEY, userId, score);
    }

    public Long getUserRank(String userId) {
        ZSetOperations zSetOps = redisTemplate.opsForZSet();
        return zSetOps.reverseRank(LEADER_BOARD_KEY, userId) +1;
    }

    public List<String> getTopRanks(int limit) {
        ZSetOperations zSetOps = redisTemplate.opsForZSet();
        Set<String> rangeSet = zSetOps.reverseRange(LEADER_BOARD_KEY, 0, limit-1);

        return rangeSet.stream().collect(Collectors.toList());
    }
}

package com.fastcampus.redis.leaderboard.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class RankingServiceTest {

    private static final int ELEMENT_COUNT = 30_000;
    private static final int MAX_VALUE = 1_000_000;
    private static final int TOP_LIMIT = 10;

    @Autowired RankingService rankingService;
    @Autowired StringRedisTemplate redisTemplate;

    @Test
    void compareSoringTest() {
        Duration javaSort = javaSortPerformance();
        Duration redisSort = inMemorySortPerformance();

        System.out.printf("JAVA SORT : %d ms\n", javaSort.getNano() / 1_000_000);
        System.out.printf("REDIS SORT : %d ms\n", redisSort.getNano() / 1_000_000);

        assertThat(javaSort.getNano()).isGreaterThan(redisSort.getNano());
    }

    Duration javaSortPerformance() {
        List<Integer> list = new ArrayList<>();
        for (int count = 0; count < ELEMENT_COUNT; count++) {
            int score = (int)(Math.random() * MAX_VALUE);
            list.add(score);
        }

        Instant before = Instant.now();
        list.stream().sorted(Comparator.reverseOrder()).limit(TOP_LIMIT).collect(Collectors.toList());
        return Duration.between(before, Instant.now());
    }

    Duration inMemorySortPerformance() {
        redisTemplate.opsForZSet().removeRange("leaderBoard", 0, MAX_VALUE);

        for (int count = 0; count < ELEMENT_COUNT; count++) {
            long score = (int)(Math.random() * MAX_VALUE);
            rankingService.setUserScore("userId_" + count, score);
        }

        Instant before = Instant.now();
        rankingService.getTopRanks(TOP_LIMIT);
        return Duration.between(before, Instant.now());
    }
}

package com.fastcampus.redis.test;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BasicRedisController {

    private static final String REDIS_KEY = "fruit";
    private final StringRedisTemplate redisTemplate;

    @PostMapping("/fruit")
    public String setFruit(@RequestParam String name) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(REDIS_KEY, name);

        return name;
    }

    @GetMapping("/fruit")
    public String getFruit() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String fruitName = ops.get(REDIS_KEY);

        return fruitName;
    }
}

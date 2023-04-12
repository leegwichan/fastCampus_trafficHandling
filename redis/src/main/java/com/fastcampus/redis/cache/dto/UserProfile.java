package com.fastcampus.redis.cache.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserProfile {
    private long id;
    private String name;
    private int age;
}

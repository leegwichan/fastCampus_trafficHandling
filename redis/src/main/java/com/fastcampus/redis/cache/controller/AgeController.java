package com.fastcampus.redis.cache.controller;

import com.fastcampus.redis.cache.dto.UserProfile;
import com.fastcampus.redis.cache.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AgeController {

    private final UserService userService;

    @GetMapping("/users/{userId}/profile/redis")
    public UserProfile getUserProfile(@PathVariable Long userId) {
        return userService.getUserProfile(userId);
    }

    @GetMapping("users/{userId}/profile/annotation")
    public UserProfile getUserProfileByAnnotation(@PathVariable Long userId) {
        String name = userService.getUserName(userId);
        int age = userService.getUserAge(userId);

        return new UserProfile(userId, name, age);
    }
}

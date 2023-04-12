package com.fastcampus.redis.cache.service;

import com.fastcampus.redis.cache.dto.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ExternalUserService externalUserService;

    public UserProfile getUserProfile(long id) {
        return externalUserService.getUserInfo(id);
    }
}

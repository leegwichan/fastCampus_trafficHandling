package com.fastcampus.redis.cache.service;

import com.fastcampus.redis.cache.repository.UserCacheRepository;
import com.fastcampus.redis.cache.dto.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserCacheRepository userCacheRepository;
    private final ExternalUserService externalUserService;

    public UserProfile getUserProfile(long id) {
        Optional<UserProfile> profile = userCacheRepository.findById(id);

        if (profile.isPresent()) {
            return profile.get();
        }

        UserProfile externalProfile = externalUserService.getUserInfo(id);
        userCacheRepository.save(externalProfile);
        return externalProfile;
    }
}

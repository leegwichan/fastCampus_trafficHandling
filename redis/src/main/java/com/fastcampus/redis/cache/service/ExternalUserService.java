package com.fastcampus.redis.cache.service;

import com.fastcampus.redis.cache.dto.UserProfile;
import org.springframework.stereotype.Service;

@Service
public class ExternalUserService {

    private final String MOCK_USER_NAME = "STEVE";
    private final int MOCK_USER_AGE = 18;

    private final long SECOND = 1000;

    // 외부 서비스나 DB 호출
    public UserProfile getUserInfo(long id) {
        sleep(1L);
        return new UserProfile(id, MOCK_USER_NAME, MOCK_USER_AGE);
    }

    private void sleep(long second) {
        try {
            Thread.sleep(second * SECOND);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

package com.fastcampus.redis.cache.repository;

import com.fastcampus.redis.cache.dto.UserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Repository
@RequiredArgsConstructor
public class UserCacheRepository {

    private static final String NAME_KEY_PREFIX = "name_";
    private static final String AGE_KEY_PREFIX = "age_";
    private static final int TIMEOUT = 5;

    private final RedisTemplate<String, String> redisTemplate;
    private ValueOperations<String, String> ops = redisTemplate.opsForValue();

    public Optional<UserProfile> findById(long id) {
        String name = ops.get(NAME_KEY_PREFIX + id);
        if (name == null) {
            return Optional.empty();
        }

        int age = toInt(ops.get(AGE_KEY_PREFIX + id));
        return Optional.of(new UserProfile(id, name, age));
    }

    public UserProfile save(UserProfile userProfile) {
        saveName(userProfile.getId(), userProfile.getName());
        saveAge(userProfile.getId(), userProfile.getAge());
        return userProfile;
    }

    private void saveName(long id, String name) {
        String nameId = NAME_KEY_PREFIX + id;
        save(nameId, name);
    }

    private void saveAge(long id, int age) {
        String ageId = AGE_KEY_PREFIX + id;
        save(ageId, age);
    }

    private void save(String key, String value) {
        ops.set(key, value, TIMEOUT, TimeUnit.SECONDS);
    }

    private void save(String key, int value) {
        ops.set(key, Integer.toString(value), TIMEOUT, TimeUnit.SECONDS);
    }

    private int toInt(String string) {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Cache value error!");
        }
    }
}

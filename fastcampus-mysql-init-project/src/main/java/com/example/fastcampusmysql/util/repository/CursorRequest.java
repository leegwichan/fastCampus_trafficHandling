package com.example.fastcampusmysql.util.repository;

import lombok.Getter;
import java.util.Objects;

@Getter
public class CursorRequest {

    public static final Long NONE_KEY = -1L;

    private Long key;
    private Integer size;

    private CursorRequest(Long key, Integer size) {
        this.key = key == null || key.longValue() <= 0 ? NONE_KEY : key;
        this.size = Objects.requireNonNull(size);
    }

    public static CursorRequest of(Long key, Integer size) {
        return new CursorRequest(key, size);
    }

    public boolean isHasKey() {
        return key != NONE_KEY;
    }

    public CursorRequest next(Long key) {
        return new CursorRequest(key, this.size);
    }
}

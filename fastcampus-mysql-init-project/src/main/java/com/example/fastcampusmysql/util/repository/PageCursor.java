package com.example.fastcampusmysql.util.repository;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@AllArgsConstructor
@Getter
public class PageCursor<T> {
    CursorRequest nextCursorRequest;
    List<T> contents;
}

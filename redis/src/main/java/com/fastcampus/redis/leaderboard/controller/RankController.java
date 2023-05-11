package com.fastcampus.redis.leaderboard.controller;

import com.fastcampus.redis.leaderboard.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/ranks")
@RequiredArgsConstructor
public class RankController {

    private final RankingService rankingService;

    @GetMapping
    public ResponseEntity getUserRank(@RequestParam String userId) {
        Long response = rankingService.getUserRank(userId);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @GetMapping("/top")
    public ResponseEntity getTopRanks(@RequestParam int limit) {
        List<String> response = rankingService.getTopRanks(limit);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity setUserRank(@RequestParam String userId,
                                      @RequestParam Long score) {
        Boolean response = rankingService.setUserScore(userId, score);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }
}

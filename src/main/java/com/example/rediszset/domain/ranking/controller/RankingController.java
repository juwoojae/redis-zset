package com.example.rediszset.domain.ranking.controller;

import com.example.rediszset.domain.ranking.model.RankingDto;
import com.example.rediszset.domain.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/ranking")
@RequiredArgsConstructor
public class RankingController {

    private final RankingService rankingService;
    /**
     * Redis 안에 들어있는 category 의 상위 몇개 인기순위 몇개를 조회 하는 작업을 수행한다
     * n을 조회할수 있다.
     * 역대 누적 순위
     */
    @GetMapping("/category")
    public ResponseEntity<List<RankingDto>> findCategoryTopN(
            @RequestParam(defaultValue = "3") int limit
    ) {
        return ResponseEntity.ok(rankingService.findCategoryTopN(limit));
    }
    /**
     * redis에 날짜별로 구분된 category 값들을 조회
     * 최근 3일을 기준으로 조회
     */
    @GetMapping("/category/last-3-days")
    public ResponseEntity<List<RankingDto>> findCategoryLast3Days(@RequestParam LocalDate standardDate) {

        return ResponseEntity.ok(rankingService.findTop3CategoryInLast3Days(standardDate));
    }
}

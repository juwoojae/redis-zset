package com.example.rediszset.domain.category.controller;

import com.example.rediszset.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    /**
     * 카테고리 정보를 조회하면 {카테고리} 의 뷰 카운트를 올려준다.
     * redis 에 저장한다
     * redis 의 TV 라는 zset 의 score 를 1 올려준다.
     * @param category
     * @return
     */
//    @GetMapping("/{category}")
//    public ResponseEntity<String> findCategoryInfo(@PathVariable String category) {
//        return ResponseEntity.ok(categoryService.findCategoryInfo(category));
//    }
    /**
     * Time window 개념을 도입해서 시간별로, 날짜 별로 구분해서 값을 넣어줄 예정이다.
     * 2025-11-26
     * 2025-11-27
     * 2025-11-28 -> 오늘
     */
    @GetMapping("/{category}")
    public ResponseEntity<String> findCategoryInfo(
            @PathVariable String category,
            @RequestParam LocalDate currentDate
    ) {
        return ResponseEntity.ok(categoryService.findCategoryInfo(category, currentDate));
    }
}

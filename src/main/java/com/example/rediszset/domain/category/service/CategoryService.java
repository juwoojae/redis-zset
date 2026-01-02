package com.example.rediszset.domain.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CategoryService {

    public static final String CATEGORY_KEY = "category";
    public static final String CATEGORY_DAILY_KEY = "category";

    private final StringRedisTemplate stringRedisTemplate;
    /**
     * 외부 엔드 포인트를 통해서 받아온 category를 redis 에 score 1 을 올려줄 것이다.
     * redis 에 category 라는 키 안에 외부에서 받아온 category value 에 1점을 올려 줄 것이다.
     * @param category
     * @return
     */
    public String findCategoryInfo(String category) {

        stringRedisTemplate.opsForZSet().incrementScore(CATEGORY_KEY, category, 1);
        return category + "을/를 조회 했습니다";
    }
    /**
     * Time window 개념을 도입하여 날짜별로 구분해서 값을 넣어줄 예정이다
     */
    public String findCategoryInfo(String category, LocalDate currentDate) {

        String key = CATEGORY_DAILY_KEY + currentDate;
        stringRedisTemplate.opsForZSet().incrementScore(key, category, 1);
        return category + "을/를 조회 하였습니다.";
    }
}

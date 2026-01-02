package com.example.rediszset.domain.ranking.service;

import com.example.rediszset.domain.ranking.model.RankingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.example.rediszset.domain.category.service.CategoryService.CATEGORY_DAILY_KEY;
import static com.example.rediszset.domain.category.service.CategoryService.CATEGORY_KEY;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final StringRedisTemplate stringRedisTemplate;

    /**
     *  redis 에서 직접 값을 가져오는 행위를 해야한다.
     */
    public List<RankingDto> findCategoryTopN(int limit) {

        // 1 단계 : redis 에서 category 안에 있는 값을 가져올것 (상위 n 개)
        Set<TypedTuple<String>> result = stringRedisTemplate.opsForZSet().
                reverseRangeWithScores(CATEGORY_KEY, 0, limit - 1);

        if(result.isEmpty()){
            return Collections.emptyList();
        }

        return result.stream()
                .map(tuple-> new RankingDto(tuple.getValue(), tuple.getScore()))
                .toList();
    }

    /**
     * standardDate 를 기준으로 3일치 zset 을 가져오기
     * 선별된 zset 을 하나의 zset 으로 묶어주기
     * 이렇게 묶인 zset 을 기준으로 상위 몇개 하위 몇개 원하는 조건대로 조회
     */
    public List<RankingDto> findTop3CategoryInLast3Days(LocalDate standardDate) {

        //1. standardDate 를 기준으로 3일치 zset 을 가져오기
        List<String> keys = List.of(
                CATEGORY_DAILY_KEY + standardDate.toString(),
                CATEGORY_DAILY_KEY + standardDate.minusDays(1).toString(),
                CATEGORY_DAILY_KEY + standardDate.minusDays(2).toString()
        );

        //2. 선별된 zset 을 하나의 zset 으로 묶어주기
        String destKey = "category_rank:last3days";

        stringRedisTemplate.opsForZSet().unionAndStore(
                keys.get(0), // 중심이 되는 ZSET 이름
                keys.subList(1 ,keys.size()), // 중심이 되는 zset 을 제외한 나머지 것들
                destKey // 결국에 저장하는 key
        );
        //3. 이렇게 묶인 zset 을 기준으로 상위 몇개 하위 몇개 원하는 조건대로 조회

        Set<TypedTuple<String>> result = stringRedisTemplate.opsForZSet().
                reverseRangeWithScores(destKey, 0,  2);  //Top 3 이므로

        if(result.isEmpty()){
            return Collections.emptyList();
        }

        return result.stream()
                .map(tuple-> new RankingDto(tuple.getValue(), tuple.getScore()))
                .toList();
    }
}

# redis-zset

Redis Sorted Set(ZSet)을 활용한 간단한 랭킹/카테고리 예제 프로젝트입니다. Spring Boot와 Spring Data Redis를 사용합니다.

## 요구사항
- Java 17
- Redis (기본: localhost:6379)

## 실행
```bash
./gradlew bootRun
```

## 설정
`src/main/resources/application.yml`에서 Redis 접속 정보를 설정합니다.

## 프로젝트 구조
```
src/
  main/
    java/com/example/rediszset/
      RedisZsetApplication.java
      common/config/
        OpenApiConfig.java
        RedisConfig.java
      domain/
        category/
          controller/CategoryController.java
          service/CategoryService.java
        ranking/
          controller/RankingController.java
          model/RankingDto.java
          service/RankingService.java
    resources/
      application.yml
  test/
    java/com/example/rediszset/
      RedisZsetApplicationTests.java
```

## API 문서
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`

## 테스트
```bash
./gradlew test
```

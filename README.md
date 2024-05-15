# 과일가게

[자바와 스프링 부트로 생애 최초 서버 만들기, 누구나 쉽게 개발부터 배포까지! [서버 개발 올인원 패키지]](https://inf.run/Hywa) 마지막 과제 구현

## 요구사항

- `POST /api/v1/fruit` - 과일가게에 입고된 과일정보를 저장한다

### 요청예시

```json
{
  "name": "사과",
  "warehousingDate": "2024-02-01",
  "price": 5000
}
```

- `PUT /api/v1/fruit` - 팔린 과일정보를 저장한다

### 요청예시

```json
{
  "id": 3
}
```

- `GET /api/v1/fruit/stat/:name` - 과일이름을 기준으로 팔린 금액, 팔리지 않은 금액을 조회한다

### 요청예시

```json
{
  "salesAmount": 6000,
  "notSalesAmount": 4000
}
```

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

## 개발

### 개발순서

- 서비스, 리포지토리 -> 테스트 검증 -> 웹 계층

### @Builder를 왜 사용하나요?

생성자로 객체를 생성해도 되는데 왜 `@Builder`를 사용했는지에 대해

- 가독성을 높이고 사람에 의한 실수를 줄여줌: 생성자 파라미터가 많아지면 가독성이 떨어짐
- 필요한 데이터만 설정할 수 있고 코드가 줄어듦(값 생성에 유연함)
- **객체의 불변성**

요약: 유지보수에 도움 됨

### RestTemplate VS MockMvc

- 어떤 관점에서 테스트 하냐에 따라 다르다

### 예외 테스트

- [AssertJ Exception Assertions by Baeldung](https://www.baeldung.com/assertj-exception-assertion)

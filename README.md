# wanted-pre-onboarding-backend

## 프로젝트 소개

기업 채용을 위한 웹 서비스입니다.

회사는 채용공고를 생성하고, 사용자는 지원합니다.

## 기술 스택

### Language

![Java](https://img.shields.io/badge/java-007396?style=for-the-badge&logoColor=white)

### Framework

![Spring](https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![SpringBoot](https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)

### Database

![SpringDataJPA](https://img.shields.io/badge/springdatajpa-6DB33F?style=for-the-badge&logoColor=white)
![QueryDSL](https://img.shields.io/badge/querydsl-0085CA?style=for-the-badge&logoColor=white)
![H2](https://img.shields.io/badge/h2-003DFF?style=for-the-badge&logoColor=white)

### Test

![JUnit5](https://img.shields.io/badge/junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![mockito](https://img.shields.io/badge/mockito-578B34?style=for-the-badge&logoColor=white)

## 주요 기능

1. 회사 채용공고 등록
    - POST `/api/notices/{companyId}`
2. 회사 채용공고 수정
    - PUT `/api/notices/{noticeId}`
3. 회사 채용공고 삭제
    - DELETE `/api/notices/{noticeId}`
4. 사용자 채용공고 목록 조회
    - GET `/api/users/notices`
5. 사용자 채용공고 검색
    - GET `/api/users/notices/search?searchCondition=검색조건`
6. 사용자 채용공고 상세페이지 조회
    - GET `/api/users/notices/{noticeId}`
7. 사용자 채용공고 지원
    - POST `/api/users/{userId}/notices/{noticeId}`

## 디렉토리 구조

```
wanted_pre_onboarding_backend
 ┣ api ---------------------------------------- api 컨트롤러
 ┃ ┣ company
 ┃ ┃ ┣ request -------------------------------- 요청
 ┃ ┃ ┃ ┣ CreateNoticeRequest.java
 ┃ ┃ ┃ ┗ UpdateNoticeRequest.java
 ┃ ┃ ┣ response ------------------------------- 응답
 ┃ ┃ ┃ ┣ CreateNoticeResponse.java
 ┃ ┃ ┃ ┗ UpdateNoticeResponse.java
 ┃ ┃ ┗ CompanyApiController.java
 ┃ ┗ user
 ┃ ┃ ┣ response
 ┃ ┃ ┃ ┣ ApplyNoticeResponse.java
 ┃ ┃ ┃ ┣ FindNoticeDetailResponse.java
 ┃ ┃ ┃ ┗ FindNoticeResponse.java
 ┃ ┃ ┗ UserApiController.java
 ┣ common ------------------------------------- 공통(에러 코드, 예외처리)
 ┃ ┣ constant
 ┃ ┃ ┗ ErrorCode.java
 ┃ ┣ exception
 ┃ ┃ ┣ CustomException.java
 ┃ ┃ ┗ CustomExceptionHandler.java
 ┃ ┗ response
 ┃ ┃ ┗ ErrorResponse.java
 ┣ config ------------------------------------- 설정 정보
 ┃ ┗ JpaConfig.java
 ┣ domain ------------------------------------- 도메인
 ┃ ┣ ApplyHistory.java
 ┃ ┣ Company.java
 ┃ ┣ Notice.java
 ┃ ┗ User.java
 ┣ repository --------------------------------- 레포지토리
 ┃ ┣ custom
 ┃ ┃ ┣ NoticeCustomRepository.java
 ┃ ┃ ┗ NoticeCustomRepositoryImpl.java
 ┃ ┣ ApplyHistoryRepository.java
 ┃ ┣ CompanyRepository.java
 ┃ ┣ NoticeRepository.java
 ┃ ┗ UserRepository.java
 ┣ service ------------------------------------ 서비스
 ┃ ┣ company
 ┃ ┃ ┣ dto ------------------------------------ dto
 ┃ ┃ ┃ ┣ CreateNoticeDto.java
 ┃ ┃ ┃ ┗ UpdateNoticeDto.java
 ┃ ┃ ┗ CompanyService.java
 ┃ ┗ user
 ┃ ┃ ┗ UserService.java
 ┣ InitDb.java -------------------------------- 초기 db 설정
 ┗ WantedPreOnboardingBackendApplication.java
```
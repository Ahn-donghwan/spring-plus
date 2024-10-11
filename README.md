# SPRING - PLUS

## 🚫 레거시 코드 리팩토링 플러스 🚫

--------------------------------------------------------------

### Level 1: Spring & JPA
@Transactional 에러 해결
이슈: Connection is read-only 에러 발생,
해결 방법: @Transactional 애너테이션으로 쓰기 권한 부여하여 문제 해결

## 2. JWT 기반 닉네임 구현
설명: User 엔티티에 nickname 필드 추가하고 JWT에서 닉네임을 추출할 수 있도록 설정함

## 3. AOP 동작 문제 해결
설명: AdminAccessLoggingAspect의 AOP 설정 수정하여 changeUserRole() 메소드 호출 전 로그 기록하도록 설정함

## 4. 컨트롤러 테스트 코드
설명: todo 단건 조회 시 todo 가 존재하지 않아 예외가 발생함, 테스트 코드 수정하여 테스트 통과하도록 만듦

## 5. JPQL을 사용한 동적 검색
설명: weather와 수정일 조건을 동적으로 추가할 수 있게 JPQL 쿼리 구성함

### Level 2: 고급 JPA & N+1 해결

## 6. Cascade를 활용한 관계 설정
설명: 할 일 저장 시 User가 자동으로 담당자로 등록되도록 Cascade 설정함

## 7. N+1 문제 해결
설명: getComments() 메소드에서 N+1 문제 방지를 위해 @EntityGraph 사용함

## 8. QueryDSL 적용
설명: findByIdWithUser 메소드를 QueryDSL로 리팩토링하고 N+1 문제 방지함

## 9. Spring Security로 접근 권한 관리
설명: 기존 Filter와 Argument Resolver의 접근 권한 관리 코드를 Spring Security로 전환함

### Level 3: 고급 기능 및 최적화

## 10. QueryDSL을 사용한 검색 기능
설명: 일정 검색 API를 추가하여 제목, 닉네임, 생성일로 검색 가능하도록 설정하고 필요한 정보만 반환하도록 최적화함

## 11. @Transactional 옵션으로 독립적 트랜잭션 처리
설명: 매니저 등록 시 로그 테이블에 요청 기록이 남도록 @Transactional 옵션 설정함

## 12. AWS 설정 및 배포
설명: EC2, RDS, S3 연동하여 어플리케이션 배포하고, S3에 프로필 이미지 업로드 API 구현함
추가사항: 보안 그룹 설정 및 설정 화면 캡처 포함함

## 13. 대용량 데이터 처리 및 최적화
설명: 테스트 코드로 100만 개 유저 데이터 생성, 닉네임 검색 API 최적화하여 속도 개선함,
실행 결과: 최초 속도와 최적화 후 속도를 비교하여 표에 정리함
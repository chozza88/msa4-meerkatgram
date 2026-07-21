# Meerkatgram Agent 가이드 & 문서 링크 (Documentation Directory)

Meerkatgram 프로젝트의 설계, API 명세, 구현 가이드 등의 문서 위치를 정리한 인덱스 파일입니다. 각 문서는 상대 경로 링크로 연결되어 있습니다.

---

## 📂 문서 인덱스 (Index)

### 1. 프로젝트 기본 개요 및 가이드 (General Docs)
*   **[README.md](meerkatgram-doc/README.md)**: 프로젝트 소개, 화면 디자인, ERD 이미지, 기술 스택 및 주요 기능 요약
*   **[CLAUDE.md](meerkatgram-doc/CLAUDE.md)**: LLM 개발 가이드 및 스타일/코딩 가이드라인
*   **[checklist-working.md](meerkatgram-doc/meerkatgram-doc/checklist-working.md)**: 문서 작성 점검용 체크리스트

### 2. 설계 및 구현 가이드 상세 (Detailed 1st-doc)
*   **[01-project-overview.md (프로젝트 개요)](meerkatgram-doc/meerkatgram-doc/1st-doc/01-project-overview.md)**: 요구사항 정의, 주요 기능 리스트, 요청 처리 흐름도
*   **[02-erd-and-database.md (ERD 및 DB 설계)](meerkatgram-doc/meerkatgram-doc/1st-doc/02-erd-and-database.md)**: 테이블 상세 정의, 관계 설정 및 MyBatis 연결 구조
*   **[03-backend-architecture.md (백엔드 아키텍처)](meerkatgram-doc/meerkatgram-doc/1st-doc/03-backend-architecture.md)**: Layered Architecture 구조, 패키지 설계 의도 설명
*   **[04-api-specification.md (API 명세서)](meerkatgram-doc/meerkatgram-doc/1st-doc/04-api-specification.md)**: Auth, User, Post, File API의 상세 엔드포인트 명세 및 요청/응답 예제
*   **[05-auth-jwt-guide.md (JWT 인증 구현 가이드)](meerkatgram-doc/meerkatgram-doc/1st-doc/05-auth-jwt-guide.md)**: Spring Security 필터 체인 연동, Access & Refresh Token 갱신 흐름 및 구현 코드 설명
*   **[06-key-features-guide.md (핵심 기능 구현 가이드)](meerkatgram-doc/meerkatgram-doc/1st-doc/06-key-features-guide.md)**: 글로벌 예외 처리, 파일 업로드/정적 리소스 매핑 및 게시글 CRUD 상세
*   **[07-setup-guide.md (환경 설정 및 실행 가이드)](meerkatgram-doc/meerkatgram-doc/1st-doc/07-setup-guide.md)**: DB 스키마 SQL, 빌드 방법, cURL 기반 API 테스트 및 트러블슈팅
*   **[08-api-response-summary.md (API 응답 및 에러 코드 분석 정리)](meerkatgram-doc/meerkatgram-doc/1st-doc/08-api-response-summary.md)**: 실코드를 분석한 각 API별 응답 성공/실패 케이스 정리 및 명세서와의 차이점

### 3. 프로젝트 분석 보고서 (Project Analysis)
*   **[README.md (분석 인덱스)](meerkatgram-doc/analysis/README.md)**: 새로 작성된 분석 문서들의 허브 및 파일 생성 경로 정의
*   **[architecture-and-layers.md (아키텍처 및 레이어 분석)](meerkatgram-doc/analysis/architecture-and-layers.md)**: 실제 적용된 Spring Data JPA 및 QueryDSL 실태, 레이어 구조 분석
*   **[code-convention.md (코드 컨벤션 분석)](meerkatgram-doc/analysis/code-convention.md)**: record 기반 DTO, Entity 격리, GlobalExceptionHandler 설정 분석
*   **[business-logic-and-flow.md (비전공자용 비즈니스 분석)](meerkatgram-doc/analysis/business-logic-and-flow.md)**: 미어캣 마을 비유를 통한 비즈니스 시나리오 및 시퀀스 다이어그램 가이드

---

## 🏗️ 프로젝트 구조 및 레이어 역할 (Project Structure & Layers)

이 프로젝트는 **레이어드 아키텍처(Layered Architecture)**를 적용하여 설계되었으며, `domain`과 `global` 패키지로 크게 역할을 분리하여 관리합니다.

### 1. 디렉토리 및 패키지 구조

```text
src/main/java/com/msa4meerkatgram/
│
├── Msa4MeerkatgramApplication.java      ← 애플리케이션 시작점 (@SpringBootApplication)
│
├── domain/                              ← 비즈니스 도메인별 코드
│   ├── auth/                            ← 인증 관련 (로그인, 로그아웃, 토큰 재발급 등)
│   ├── file/                            ← 파일 업로드 관련 (프로필/게시글 이미지 업로드 등)
│   ├── post/                            ← 게시글 CRUD 및 페이징 처리 관련
│   └── user/                            ← 회원가입, 유저 조회 관련
│
└── global/                              ← 전 도메인 공통 기술 설정 및 유틸리티
    ├── config/                          ← CORS, 리소스 매핑 설정 등
    ├── errors/                          ← 예외 처리기 (@RestControllerAdvice) 및 커스텀 예외 정의
    ├── responses/                       ← 공통 응답 DTO (GlobalRes)
    ├── security/                        ← Spring Security 설정, JWT 필터 및 토큰 관리
    └── util/                            ← 로컬 파일 저장 유틸리티 등

src/main/resources/
└── application.yaml                     ← 전체 애플리케이션 설정 파일 (※ 기존 설계에 있던 mapper XML 폴더는 JPA 및 QueryDSL 도입으로 제거됨)
```

### 2. 레이어별 역할 및 책임 (Layer Roles & Responsibilities)

프로젝트 내의 레이어는 다음과 같이 책임을 격리하여 상호 통신합니다.

| 레이어 (Layer) | 역할 및 설명 (Role & Responsibility) | 비고 및 주의 사항 |
| :--- | :--- | :--- |
| **Filter Layer** | Security 필터 체인을 통해 들어오는 HTTP 요청을 가로채고 **JWT 토큰 검증, 인증 및 인가**를 수행합니다. | 비즈니스 로직을 처리하지 않습니다. |
| **Controller Layer** | 클라이언트로부터 HTTP 요청을 수신하여 입력값을 검증(`@Valid`)하고, 적절한 Service를 호출한 뒤 결과를 **공통 응답 형태(`GlobalRes`)**로 포장하여 반환합니다. | 비즈니스 로직이나 데이터베이스 직접 조회를 배제합니다. |
| **Service Layer** | 실제 애플리케이션의 **비즈니스 로직**을 실행하며, 트랜잭션(`@Transactional`)의 경계를 정의합니다. 필요 시 다수의 Repository를 호출합니다. | Controller의 HTTP 세션이나 HttpServletRequest 등 웹 기술 관련 코드에 의존하지 않습니다. |
| **Repository Layer (JPA / QueryDSL)** | 데이터베이스 액세스 레이어로, **기본 CRUD(JPA)** 및 **복잡한 검색/조인(QueryDSL)**을 전담하며 데이터를 Java 객체로 매핑합니다. | 데이터베이스 쿼리 실행 및 매핑에만 충실하고 비즈니스 로직을 다루지 않습니다. (※ 설계 가이드의 MyBatis Mapper 역할이 JPA로 마이그레이션됨) |

### 3. `domain` vs `global` 패키지 분리 가이드

*   **`domain/`**: 도메인(업무 기능) 단위로 묶인 패키지입니다. (예: `post`, `user` 등). 변경의 주된 원인이 비즈니스 요구사항에 있습니다.
*   **`global/`**: 전 도메인에 공통으로 필요한 횡단 관심사(Cross-cutting Concerns) 코드입니다. (예: Security, JWT, Error handling, Utility). 변경의 주된 원인이 시스템적/기술적 변화에 있습니다.
*   **의존 방향 규칙**: `domain` $\rightarrow$ `global` 방향의 의존만 허용하며, `global`은 `domain` 코드를 알거나 참조해서는 안 됩니다 (단방향 의존성 유지).


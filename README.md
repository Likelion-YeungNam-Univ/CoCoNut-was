# 𖣑 소상공인과 지역주민을 이어주는 외주 플랫폼 Bridgee
<img width="1159" height="650" alt="Image" src="https://github.com/user-attachments/assets/7d7480fc-7e53-402a-a8f7-300d92286f07" />

# 📌 프로젝트 소개
Bridgee는 경산 지역 소상공인과 학생들을 연결하여, 상생하는 외주 공모전 플랫폼입니다. 소상공인은 AI의 도움을 받아 손쉽게 프로젝트를 의뢰하고, 학생들은 자신의 재능을 발휘하여 작품을 제출하고 보상을 받을 수 있습니다.

## 🔍 주요기능
### 맞춤형 공모전 의뢰
<img width="1161" height="652" alt="Image" src="https://github.com/user-attachments/assets/98d881ba-5886-4835-ac9d-430bf565d348" />

### 투표를 통한 평가
<img width="1104" height="619" alt="Image" src="https://github.com/user-attachments/assets/df8c9525-8240-44a7-b3ad-d910ce6c3294" />

### 지역 기반 연결
<img width="1106" height="619" alt="Image" src="https://github.com/user-attachments/assets/a657063f-20f3-4396-b5b4-5fb1a4afcdf0" />

<br>

# 🧑‍💻 팀원 소개
| 최정 | 김동민 | 임태현 | 조민서 | 신혜진 | 이수민 |
|:------:|:------:|:------:|:------:|:------:|:------:|
| <img src="https://avatars.githubusercontent.com/u/160298290?v=4" alt="최정" width="150"> | <img src="https://avatars.githubusercontent.com/u/80417179?v=4" alt="김동민" width="150"> | <img src="https://avatars.githubusercontent.com/u/165642906?v=4" alt="임태현" width="150"> | <img src="https://avatars.githubusercontent.com/u/165632548?v=4" alt="조민서" width="150"> |<img src="https://avatars.githubusercontent.com/u/139312570?v=4" alt="신혜진" width="150"> |<img src="https://avatars.githubusercontent.com/u/160111840?v=4" alt="이수민" width="150"> |
| BE | BE | FE | FE | FE | P&D |
| [GitHub](https://github.com/chlwjd0803) | [GitHub](https://github.com/eastminnn) | [GitHub](https://github.com/Limtaehyeon) | [GitHub](https://github.com/chominseo0723) |[GitHub](https://github.com/hyeji-neee) |[GitHub](https://github.com/SSXXMM22) |

<br>

# 📝 역할분담
### 최정
- 유저(User): 회원가입, 로그인, 로그아웃, 회원탈퇴, 개인정보 조회 등 유저 계정 관련 핵심 기능을 개발했습니다. 특히 JWT를 활용한 토큰 기반 인증 및 인가 시스템을 구현하여 보안을 강화했습니다.

- 작품(Submission): 공모전에 대한 작품 관련 기능을 개발하였습니다. AI 지원 기능을 활용해 작품 설명을 전문적으로 작성하고, 마감일과 중복 제출 여부를 철저히 검증하여 참가자들이 원활하게 작품을 제출하고 관리할 수 있도록 돕는 기능을 구현하였습니다다.

- 투표(Vote): 공모전 작품에 대한 투표 시스템을 구축했습니다. 사용자가 투표할 수 있는 자격(중복 투표 방지, 공모전 주최자 및 작품 제출자 제외)을 검증하고, 투표 결과를 집계하는 기능을 구현했습니다.

- GCS(Google Cloud Storage): 이미지 업로드 및 관리를 위해 Google Cloud Storage와의 연동을 담당했습니다. MultipartFile을 받아 GCS에 이미지를 업로드하고, 저장된 이미지의 URL을 반환하는 기능을 구현했습니다.

- AI : OpenAI API를 활용하여 공모전 기획과 작품 설명을 지원하는 AI 기능을 개발했습니다. 사용자의 프롬프트를 바탕으로 상세 설명, 상금, 기간 등을 추천해주는 로직을 구현했습니다.

### 김동민
- 공모전(Project): 공모전의 생성, 조회(목록/상세), 수정, 삭제 기능을 구현했습니다. 공모전의 상태(진행 중, 투표 중, 종료) 변경 로직과 마감일 이후 자동으로 상태가 바뀌는 스케줄러를 구현했습니다.

- 보상(Reward): 소상공인이 우승자를 선정하고 보상을 지급하는 기능을 개발했습니다. 우승자 선정 시 중복 수상이 발생하지 않도록 로직을 추가하고, 우승자에게 보상 내역을 제공하는 기능을 구현했습니다.

- AWS EC2: 서비스의 안정적인 운영을 위한 서버 배포를 담당했습니다.

- DB: AWS EC2 환경 내에서 MySQL 데이터베이스를 관리했습니다. ERD 설계 및 JPA를 활용한 엔티티(Entity) 및 레포지토리(Repository)를 구현하여 데이터의 저장, 조회, 수정, 삭제를 관리했습니다.

- Git init(리포지토리와 개발 환경 초기 세팅): 팀원들이 신속하게 개발을 시작하도록 Git 커밋 및 브랜치 컨벤션을 포함한 리포지토리 컨벤션을 구축했습니다. Gradle 기반의 개발 환경을 세팅하고, CI/CD 파이프라인을 GitHub Actions를 활용하여 자동화했습니다

<br>

# 📌 프로젝트 아키텍처
<img width="946" height="708" alt="Image" src="https://github.com/user-attachments/assets/b5757057-57bd-4335-88a5-86755b1425b0" />

<br>

# 📌 프로젝트 구조
```
CoCoNut-was
├── build.gradle
├── gradlew
├── gradlew.bat
├── settings.gradle
└── src
└── main
├── java
│   └── CoCoNut_was
│       ├── domains
│       │   ├── project
│       │   │   ├── controller
│       │   │   │   ├── EnumController.java
│       │   │   │   ├── ProjectAiAssistanceController.java
│       │   │   │   └── ProjectController.java
│       │   │   ├── dto
│       │   │   │   ├── EnumResponseDto.java
│       │   │   │   ├── ProjectRequestDto.java
│       │   │   │   ├── ProjectDetailResponseDto.java
│       │   │   │   └── ProjectListResponseDto.java
│       │   │   ├── entity
│       │   │   │   ├── Project.java
│       │   │   │   ├── Category.java
│       │   │   │   └── Status.java
│       │   │   ├── repository
│       │   │   │   ├── ProjectRepository.java
│       │   │   │   └── ProjectColorRepository.java
│       │   │   └── service
│       │   │       ├── ProjectService.java
│       │   │       └── ProjectScheduler.java
│       │   ├── reward
│       │   │   ├── controller
│       │   │   │   └── RewardController.java
│       │   │   └── ...
│       │   ├── submission
│       │   │   ├── controller
│       │   │   │   ├── SubmissionController.java
│       │   │   │   └── SubmissionAiAssistanceController.java
│       │   │   └── ...
│       │   ├── user
│       │   │   ├── controller
│       │   │   │   └── UserController.java
│       │   │   └── ...
│       │   └── vote
│       │       ├── controller
│       │       │   └── VoteController.java
│       │       └── ...
│       ├── exception
│       │   ├── ErrorCode.java
│       │   └── GlobalExceptionHandler.java
│       ├── gcs
│       │   └── ImageUploadService.java
│       └── ...
└── resources
└── application.yml
```

# 📌 컨벤션
### 📚 커밋 메시지

| message | description                                           |
| ------- | ----------------------------------------------------- |
| feat    | 🥥 새로운 기능 추가, 기존 기능을 요구 사항에 맞추어 수정 |
| fix     | 🐛 기능에 대한 버그 수정                                 |
| docs    | 📝 문서(주석) 수정                                       |
| refactor| ✏️ 기능 변화가 아닌 코드 리팩터링                        |
| test    | ⚙️ 테스트 코드 추가/수정                                 |
| chore   | 🛠️ 패키지 매니저 수정, 그 외 기타 수정 ex) .gitignore    |

💡 **커밋 메시지 예시**  
- feat : 로그인 폼 완성  
- fix : 회원가입 에러 해결
- docs : 회원가입 로직 주석 수정  
---

### 🌳 Branch Naming 규칙

우리 팀은 기능 개발 중심으로 브랜치 네이밍을 다음과 같이 사용합니다:

- **feature/**: 새로운 기능 개발용 브랜치  
  예) `feature/login-page`, `feature/user-profile`

- **fix/**: 버그 수정용 브랜치  
  예) `fix/login-error`, `fix/signup-validation`

- **hotfix/**: 긴급 수정용 브랜치 (주로 main에서 바로 생성)  
  예) `hotfix/security-patch`

- **release/**: 배포 준비용 브랜치  
  예) `release/v1.0.0`

- **develop**: 다음 릴리스용 개발 기본 브랜치  
  - 모든 feature, fix 브랜치는 develop으로 병합

- **main**: 항상 배포 가능한 안정된 코드 유지 브랜치

---
# 🗄️ ERD
<img width="1494" height="923" alt="Image" src="https://github.com/user-attachments/assets/67baa306-7415-4ecd-a545-29bf8f55eb68" />
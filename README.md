# 🥥 CoCoNut-web  
> CoCoNut 서비스의 백엔드 저장소입니다.

---

## 📚 커밋 메시지 규칙

| **메시지 타입** | **설명** |
| --- | --- |
| **feat** | ✨ 새로운 기능 추가 및 기존 기능 수정 |
| **fix** | 🐛 버그 수정 |
| **docs** | 📚 문서 및 주석 수정 |
| **style** | 🎨 코드 스타일 및 포맷팅 수정 |
| **refact** | ♻️ 기능 변화 없는 코드 리팩터링 |
| **test** | ✅ 테스트 코드 추가/수정 |
| **chore** | 🔧 패키지 매니저 수정 및 기타 잡다한 변경(ex: `.gitignore`) |
| **merge** | 🔀 브랜치 병합 |

💡 **커밋 메시지 예시**  
- feat : 로그인 폼 완성  
- fix : 회원가입 에러 해결
- docs : 회원가입 로직 주석 수정  


## 🌿 Branch Naming 규칙

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
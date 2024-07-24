# 개발 프로세스

GitHub Project를 기반으로 프로젝트를 진행한다.

1. **To Do 작성 및 작업 할당**:
    - 새로운 작업 항목을 To Do 리스트에 추가하고 담당자를 지정한다.
2. **작업 진행 (In Progress)**:
    - 작업을 시작하면 In Progress로 이동하여 진행 상황을 업데이트한다.
3. **작업 완료 후 리뷰 (Review In Progress)**:
    - 작업을 완료하면 Review In Progress로 이동하여 코드 리뷰를 요청한다.
4. **리뷰 승인 후 완료 (Done)**:
    - 리뷰어가 작업을 승인하면 Reviewer Approved 상태로 변경하고 Done 리스트로 이동한다.

## 워크플로우

1. **GitHub 이슈 기반 브랜치 생성**:
    - 브랜치 이름 형식: `feature/이슈번호`
    - 예시: `feature/YB-2`

2. **개발 완료 시 PR 생성**:
    - PR 제목 형식: `[접두사/이슈번호]: 내용`
    - 예시: `[Feature/1]: 소셜 로그인 기능 구현`

3. **Develop 브랜치 병합**:
    - 코드 리뷰 및 1명 이상의 승인(approve) 후 병합
    - 병합 방법: merge commit

## 버전 관리 규칙

### Commit/PR 접두사 규칙

- `feat/[Feature]`: 새로운 기능 추가
- `fix/[Fix]`: 버그 수정
- `hotfix/[HotFix]`: 긴급 수정
- `docs/[Docs]`: 문서 수정
- `style/[Style]`: 코드 포맷팅 등 코드 변경이 없는 경우
- `refactor/[Refactor]`: 코드 리팩토링
- `test/[Test]`: 테스트 코드, 리팩토링 테스트 코드 추가
- `chore/[Chore]`: 빌드 업무 수정, 패키지 매니저 수정

### Commit 규칙

- 커밋 형식: `접두사(이슈번호): 내용`
- 예시: `feat(YB-2): accessToken 발급 로직 구현`

### PR 규칙

- PR 제목 형식: `[접두사/이슈번호]: 내용`
- 예시: `[Feature/1]: 소셜 로그인 기능 구현`

### Merge 규칙

- 병합 방법: merge commit
- 병합 커밋 메시지: PR 제목과 동일하게 작성하며, PR 번호를 추가
- 리뷰 완료 및 승인 후 PR을 생성한 사람이 Merge 수행
- Merge Conflict 발생 시, PR을 생성한 사람이 충돌 해결
- 공동 작업자가 있는 경우 병합 커밋에 공동 작업자 추가

## 예시

### GitHub 이슈 및 브랜치 생성

1. GitHub 이슈 생성: `이슈 번호: YB-2`, `내용: accessToken 발급 로직 구현`
2. 브랜치 생성:
    ```bash
    git checkout -b feature/YB-2
    ```

### Commit 메시지 예시

```bash
git commit -m "feat(YB-2): accessToken 발급 로직 구현"

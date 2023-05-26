# 수강신청 미션

---

## Step3 - 수강신청(DB 적용)

### 1차 피드백 수정
- [X] 유효성검증 메서드는 validate-로 분리

### 프로그래밍 요구사항
* 앞 단계에서 구현한 도메인 모델을 DB 테이블과 매핑하고, 데이터를 저장한다.

### 구현 목록
- [X] 테이블 생성
- [ ] 수강 신청 테이블 생성 쿼리
- [ ] 강사의 강의 개설 기능 매핑
- [ ] 이미지 커버 수정 기능 매핑
- [ ] 강의 상태 변경 기능 매핑
- [ ] 학생 강의 수강 신청 기능 매핑
- [ ] 학생 강의 수강 취소 기능 매핑
- [ ] 강의 유료 무료 타입 수정 기능

---

## Step2 - 수강신청(도메인 모델)

### 수강 신청 기능 요구사항
* 과정(Course)은 기수 단위로 여러 개의 강의(Session)를 가질 수 있다.
* 강의는 시작일과 종료일을 가진다.
* 강의는 강의 커버 이미지 정보를 가진다.
* 강의는 무료 강의와 유료 강의로 나뉜다.
* 강의 상태는 준비중, 모집중, 종료 3가지 상태를 가진다.
* 강의 수강신청은 강의 상태가 모집중일 때만 가능하다.
* 강의는 강의 최대 수강 인원을 초과할 수 없다.

### 구현 목록
* [X] 강의 객체 구현
  * [X] 강사 강의 개설 기능
  * [X] 강의 시작일과 종료일 유효성 체크 기능
    * 강의 종료일은 강의 시작일보다 앞일 수 없음
  * [X] 강의 이미지 커버 수정 기능
  * [X] 강의 상태 변경 기능
    * 강의 개설 시에는 default로 준비중 상태
    * 원할 때에 강의 상태를 모집중으로 변경 가능
    * 강의 종료 상태는 별도로 바꾸지 않고 강의 종료 날짜가 지나면 바뀌도록 구현
  * [X] 학생 강의 수강 신청 기능
  * [X] 강의 최대 수강 인원 유효성 체크 기능
    * 강의 등록인원은 최대 수강 인원을 초과할 수 없음
  * [X] 학생 강의 수강 취소 기능
  * [X] 강의 유료 무료 타입 수정 기능
    * 강의 상태가 준비중일 때만 수정 가능

* [X] 과정 객체 구현

---

## Step1 - 레거시 코드 리팩터링
### 요구 사항
* 질문 삭제하기 요구사항
    * 질문 데이터를 완전히 삭제하는 것이 아니라 데이터의 상태를 삭제 상태(deleted - boolean type)로 변경한다.
    * 로그인 사용자와 질문한 사람이 같은 경우 삭제 가능하다.
    * 답변이 없는 경우 삭제가 가능하다.
    * 질문자와 답변글의 모든 답변자가 같은 경우 삭제가 가능하다.
    * 질문을 삭제할 때 답변 또한 삭제해야 하며, 답변의 삭제 또한 삭제 상태(deleted)를 변경한다.
    * 질문자와 답변자가 다른 경우 답변을 삭제할 수 없다.
    * 질문과 답변 삭제 이력에 대한 정보를 DeleteHistory를 활용해 남긴다.

* 리팩터링 요구사항
    * nextstep.qna.service.QnaService의 deleteQuestion()는 앞의 질문 삭제 기능을 구현한 코드이다. 이 메소드는 단위 테스트하기 어려운 코드와 단위 테스트 가능한 코드가 섞여 있다.
    * QnaService의 deleteQuestion() 메서드에 단위 테스트 가능한 코드(핵심 비지니스 로직)를 도메인 모델 객체에 구현한다.
    * QnaService의 비지니스 로직을 도메인 모델로 이동하는 리팩터링을 진행할 때 TDD로 구현한다.
    * QnaService의 deleteQuestion() 메서드에 대한 단위 테스트는 src/test/java 폴더 nextstep.qna.service.QnaServiceTest이다. 도메인 모델로 로직을 이동한 후에도 QnaServiceTest의 모든 테스트는 통과해야 한다.

* 구현 내용
- [X] Question 클래스에 delete 메서드를 추가
    - [X] 질문 삭제 권한 확인 기능
    - [X] 다른 사람이 쓴 답변 확인 기능
    - [X] DeleteHistory 생성 후 반환 기능
- [X] QnAService 클래스 내 deleteQuestion 메서드 비즈니스 로직 코드 삭제

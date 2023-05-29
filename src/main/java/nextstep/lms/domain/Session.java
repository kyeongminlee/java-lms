package nextstep.lms.domain;

import nextstep.users.domain.NsUser;

import java.time.LocalDate;

public class Session {

    private Long id;
    private SessionDate sessionDate;
    private Long imageId;
    private SessionState sessionState;
    private SessionType sessionType;
    private StudentCapacity studentCapacity;

    public Session(LocalDate startDate, LocalDate endDate) {
        this(null, startDate, endDate,
                SessionState.READY, SessionType.FREE, 0,
                1, null);
    }

    public Session(LocalDate startDate, LocalDate endDate, Long imageId,
                   SessionType sessionType, int studentCapacity) {
        this(null, startDate, endDate,
                SessionState.READY, sessionType, 0,
                studentCapacity, imageId);
    }

    public Session(Long id, LocalDate startDate, LocalDate endDate,
                   SessionState sessionState, SessionType sessionType, int registeredStudent,
                   int studentCapacity, Long imageId) {
        this.id = id;
        this.sessionDate = new SessionDate(startDate, endDate);
        this.sessionState = sessionState;
        this.sessionType = sessionType;
        this.studentCapacity = new StudentCapacity(studentCapacity, registeredStudent);
        this.imageId = imageId;
    }

    public static Session createSession(LocalDate startDate, LocalDate endDate, Long longCover,
                                        SessionType sessionType, int studentCapacity) {
        return new Session(startDate, endDate, longCover, sessionType, studentCapacity);
    }

    public Long changeImageCover(Long changeCover) {
        this.imageId = changeCover;
        return imageId;
    }

    public SessionState recruitStudents() {
        sessionState = SessionState.RECRUITING;
        return sessionState;
    }

    public SessionState changeFinishSessionState(LocalDate date) {
        if (sessionDate.isAfterEndDate(date)) {
            sessionState = SessionState.FINISH;
        }
        return sessionState;
    }

    public Student enroll(NsUser nsUser) {
        validateSessionState();
        studentCapacity.enroll();

        return Student.init(nsUser, this);
    }

    private void validateSessionState() {
        if (!sessionState.equals(SessionState.RECRUITING)) {
            throw new IllegalArgumentException("모집 중이 아닙니다.");
        }
    }

    public Student cancel(Student student) {
        student.sessionCancel();
        studentCapacity.cancel();

        return student;
    }

    public SessionType changeSessionType(SessionType sessionType) {
        validateReadyState();
        this.sessionType = sessionType;
        return sessionType;
    }

    private void validateReadyState() {
        if (!sessionState.equals(SessionState.READY)) {
            throw new IllegalArgumentException("수정 기간이 지났습니다.");
        }
    }

    public boolean isFree() {
        return sessionType.equals(SessionType.FREE);
    }

    public Long getId() {
        return id;
    }

    public SessionDate getSessionDate() {
        return sessionDate;
    }

    public Long getImageId() {
        return imageId;
    }

    public String getSessionState() {
        return sessionState.toString();
    }

    public String getSessionType() {
        return sessionType.toString();
    }

    public StudentCapacity getStudentCapacity() {
        return studentCapacity;
    }
}

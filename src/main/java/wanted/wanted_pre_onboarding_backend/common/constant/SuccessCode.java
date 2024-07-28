package wanted.wanted_pre_onboarding_backend.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {

    NOTICE_CREATED(CREATED, "채용공고가 등록되었습니다."),
    NOTICE_UPDATED(OK, "채용공고가 수정되었습니다."),
    NOTICE_DELETED(OK, "채용공고가 삭제되었습니다."),
    NOTICES_FOUND(OK, "채용공고 목록 조회가 완료되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}

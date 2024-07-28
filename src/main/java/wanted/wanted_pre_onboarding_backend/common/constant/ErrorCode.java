package wanted.wanted_pre_onboarding_backend.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    COMPANY_NOT_FOUND(NOT_FOUND, "해당 id를 가진 회사가 없습니다."),
    NOTICE_NOT_FOUND(NOT_FOUND, "해당 id를 가진 채용공고가 없습니다."),
    USER_NOT_FOUND(NOT_FOUND, "해당 id를 가진 사용자가 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}

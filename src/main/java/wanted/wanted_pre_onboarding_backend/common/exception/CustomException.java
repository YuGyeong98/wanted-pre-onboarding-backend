package wanted.wanted_pre_onboarding_backend.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import wanted.wanted_pre_onboarding_backend.common.constant.ErrorCode;

@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;
}

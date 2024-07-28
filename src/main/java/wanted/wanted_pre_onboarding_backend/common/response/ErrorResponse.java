package wanted.wanted_pre_onboarding_backend.common.response;

import org.springframework.http.ResponseEntity;
import wanted.wanted_pre_onboarding_backend.common.constant.ErrorCode;

public record ErrorResponse(String name, String message) {

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        ErrorResponse response = new ErrorResponse(errorCode.name(), errorCode.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(response);
    }
}

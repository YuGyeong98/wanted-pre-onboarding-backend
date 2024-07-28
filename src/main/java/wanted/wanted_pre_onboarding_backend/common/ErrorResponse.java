package wanted.wanted_pre_onboarding_backend.common;

import org.springframework.http.ResponseEntity;

public record ErrorResponse(String name, String message) {

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        ErrorResponse response = new ErrorResponse(errorCode.name(), errorCode.getMessage());
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(response);
    }
}

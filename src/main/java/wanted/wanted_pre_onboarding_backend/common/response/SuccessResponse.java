package wanted.wanted_pre_onboarding_backend.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import wanted.wanted_pre_onboarding_backend.common.constant.SuccessCode;

@Data
@RequiredArgsConstructor
public class SuccessResponse<T> {

    private final String name;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    private SuccessResponse(String name, String message, T data) {
        this.name = name;
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseEntity<SuccessResponse<T>> toResponseEntity(SuccessCode successCode, T data) {
        SuccessResponse<T> response = new SuccessResponse<>(successCode.name(), successCode.getMessage(), data);
        return ResponseEntity.status(successCode.getHttpStatus())
                .body(response);
    }

    public static <T> ResponseEntity<SuccessResponse<T>> toResponseEntity(SuccessCode successCode) {
        SuccessResponse<T> response = new SuccessResponse<>(successCode.name(), successCode.getMessage());
        return ResponseEntity.status(successCode.getHttpStatus())
                .body(response);
    }
}

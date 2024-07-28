package wanted.wanted_pre_onboarding_backend.common.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import wanted.wanted_pre_onboarding_backend.common.response.ErrorResponse;

import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static wanted.wanted_pre_onboarding_backend.common.constant.ErrorCode.USER_APPLY_ONCE;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException customException) {
        return ErrorResponse.toResponseEntity(customException.getErrorCode());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return ErrorResponse.toResponseEntity(USER_APPLY_ONCE);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String name = Objects.requireNonNull(ex.getFieldError()).getField().toUpperCase();
        String message = ex.getFieldError().getDefaultMessage();
        return ResponseEntity.status(BAD_REQUEST)
                .body(new ErrorResponse(name, message));
    }
}

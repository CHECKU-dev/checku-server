package dev.checku.checkuserver.global.error;

import dev.checku.checkuserver.domain.log.application.ErrorLogService;
import dev.checku.checkuserver.domain.log.dto.ErrorLogDto;
import dev.checku.checkuserver.domain.portal.application.PortalSessionService;
import dev.checku.checkuserver.global.error.exception.BusinessException;
import dev.checku.checkuserver.global.error.exception.FeignClientException;
import dev.checku.checkuserver.domain.portal.application.PortalLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.List;

import static dev.checku.checkuserver.global.error.exception.ErrorCode.NETWORK_ERROR;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ErrorLogService errorLogService;
    private final PortalLoginService portalLoginService;
    private final PortalSessionService portalSessionService;

    /**
     * javax.validation.Valid 또는 @Validated binding error가 발생할 경우
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
        log.error("handleBindException", e);

        List<String> errorMessages = new ArrayList<>();

        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                errorMessages.add(fieldError.getDefaultMessage());
                saveErrorLog(HttpStatus.BAD_REQUEST.value(), fieldError.getDefaultMessage());
            }
        }

        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, errorMessages);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * 주로 @RequestParam enum으로 binding 못했을 경우 발생
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("handleMethodArgumentTypeMismatchException", e);
        
        saveErrorLog(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        List<String> errorMessages = List.of(e.getName());
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, errorMessages);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException", e);
        saveErrorLog(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage());
        List<String> errorMessages = List.of(e.getMessage());
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.METHOD_NOT_ALLOWED, errorMessages);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorResponse);
    }

    /**
     * 비즈니스 로직 실행 중 오류 발생
     */
    @ExceptionHandler(value = {BusinessException.class})
    protected ResponseEntity<ErrorResponse> handleConflict(BusinessException e) {
        log.error("BusinessException", e);
        saveErrorLog(e.getStatus(), e.getMessage());
        List<String> errorMessages = List.of(e.getMessage());
        HttpStatus httpStatus = HttpStatus.valueOf(e.getStatus());
        ErrorResponse errorResponse = ErrorResponse.of(httpStatus, errorMessages);
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    /**
     * FeignClient 예외 발생
     */
    @ExceptionHandler(FeignClientException.class)
    protected ResponseEntity<ErrorResponse> handleFeignClientException(FeignClientException e) {
        log.error("FeignClientException", e);
        saveErrorLog(e.getStatus(), e.getMessage());
        List<String> errorMessages = List.of(e.getMessage());
        HttpStatus httpStatus = HttpStatus.valueOf(e.getStatus());
        ErrorResponse errorResponse = ErrorResponse.of(httpStatus, errorMessages);

        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    /**
     * 나머지 예외 발생
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("Exception", e);
        saveErrorLog(HttpStatus.BAD_REQUEST.value(), NETWORK_ERROR.getMessage());
        List<String> errorMessages = List.of(NETWORK_ERROR.getMessage());
        portalSessionService.updatePortalSession(portalLoginService.login());
//        PortalUtils.updateJsessionid(loginService.login());
        ErrorResponse errorResponse = ErrorResponse.of(HttpStatus.BAD_REQUEST, errorMessages);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    private void saveErrorLog(int statusCode, String errorMessage) {
        errorLogService.saveErrorLog(ErrorLogDto.of(statusCode, errorMessage));
    }

}
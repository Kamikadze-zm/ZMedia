package ru.kamikadze_zm.zmedia.controller;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.kamikadze_zm.zmedia.exception.InvalidVerificationCodeException;
import ru.kamikadze_zm.zmedia.exception.ValidationFieldsException;
import ru.kamikadze_zm.zmedia.jwtauth.InvalidTokenException;
import ru.kamikadze_zm.zmedia.model.ApiError;
import ru.kamikadze_zm.zmedia.model.ApiError.ErrorCode;
import ru.kamikadze_zm.zmedia.model.ValidationError;
import ru.kamikadze_zm.zmedia.model.ValidationFieldError;
import ru.kamikadze_zm.zmedia.model.ValidationObjectError;
import ru.kamikadze_zm.zmedia.util.AuthenticationUtil;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    
    private static final Logger LOG = LogManager.getLogger(RestExceptionHandler.class);
    
    private static final String VALIDATION_EXCEPTION_MESSAGE = "Validation exception";
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        BindingResult br = ex.getBindingResult();
        return handleValidationError(ex, br.getGlobalErrors(), br.getFieldErrors());
    }
    
    @ExceptionHandler(ValidationFieldsException.class)
    protected ResponseEntity<Object> handleValidationFieldsException(ValidationFieldsException ex) {
        return handleValidationError(ex, null, ex.getFieldsErrors());
    }
    
    @ExceptionHandler({AuthenticationException.class, InvalidTokenException.class})
    protected ResponseEntity<Object> handleAuthenticationException(Exception e) {
        return unauthorizedResponse(e);
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e) {
        if (!AuthenticationUtil.isAuthenticated() || AuthenticationUtil.isAnonymous()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        ApiError ae = new ApiError(ErrorCode.OTHER, e);
        return new ResponseEntity<>(ae, HttpStatus.FORBIDDEN);
    }
    
    @ExceptionHandler(InvalidVerificationCodeException.class)
    protected ResponseEntity<Object> handleInvalidVerificationCodeException(InvalidVerificationCodeException e) {
        ApiError ae = new ApiError(ErrorCode.INVALID_VERIFICATION_CODE, e);
        return new ResponseEntity<>(ae, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleUnhandledExceptions(Exception e) {
        if (LOG.isInfoEnabled()) {
            LOG.info("Unhandled exception: ", e);
        }
        ApiError ae = new ApiError(ErrorCode.OTHER, e);
        return new ResponseEntity<>(ae, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    private ResponseEntity<Object> handleValidationError(Exception ex, List<ObjectError> objectErrors, List<FieldError> fieldErrors) {
        List<ValidationObjectError> voes = null;
        if (objectErrors != null && !objectErrors.isEmpty()) {
            voes = new ArrayList<>();
            for (FieldError e : fieldErrors) {
                voes.add(new ValidationObjectError(e));
            }
        }
        List<ValidationFieldError> vfes = null;
        if (fieldErrors != null && !fieldErrors.isEmpty()) {
            vfes = new ArrayList<>();
            for (FieldError e : fieldErrors) {
                vfes.add(new ValidationFieldError(e));
            }
        }
        ValidationError ve = new ValidationError(VALIDATION_EXCEPTION_MESSAGE, VALIDATION_EXCEPTION_MESSAGE, voes, vfes);
        return new ResponseEntity<>(ve, HttpStatus.BAD_REQUEST);
    }
    
    private ResponseEntity<Object> unauthorizedResponse(Exception e) {
        ApiError ae = new ApiError(ErrorCode.OTHER, e);
        return new ResponseEntity<>(ae, HttpStatus.UNAUTHORIZED);
    }
}

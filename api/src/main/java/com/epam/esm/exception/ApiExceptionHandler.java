package com.epam.esm.exception;

import com.epam.esm.controller.constants.ExceptionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Controller for handling exceptions
 */
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    private final MessageSource messageSource;

    @Autowired
    public ApiExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Handle situation when field value filled with incorrect type
     *
     * @param exception the HttpMessageNotReadableException exception
     * @param headers   the HttpHeaders headers
     * @param status    the HttpStatus status
     * @param request   the WebRequest request
     * @return custom exception info and http status
     */
    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ResponseException(messageSource.getMessage("not.readable.message",
                null, LocaleContextHolder.getLocale()), 40000),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle situation when field value filled with invalid data
     *
     * @param exception the MethodArgumentNotValidException exception
     * @param headers   the HttpHeaders headers
     * @param status    the HttpStatus status
     * @param request   the WebRequest request
     * @return custom exception info and http status
     */
    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        List<ResponseException> responseExceptions = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(objectError ->
                responseExceptions.add(new ResponseException(messageSource
                        .getMessage(Objects.requireNonNull(objectError.getDefaultMessage()),
                                new Object[]{objectError.getField(), objectError.getRejectedValue()}, LocaleContextHolder.getLocale()),
                        ExceptionInfo.getExceptionCodeByMessage(objectError.getDefaultMessage()))));
        return new ResponseEntity<>(responseExceptions, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle situation when entity could not be found
     *
     * @param exception thrown exception
     * @return custom exception info and http status
     */
    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ResponseException> handleNotFoundException(NotFoundException exception) {
        return new ResponseEntity<>(new ResponseException(messageSource.getMessage("not.found.message",
                new Object[]{exception.getId()}, LocaleContextHolder.getLocale()),
                exception.getExceptionCode()), HttpStatus.NOT_FOUND);
    }

    /**
     * Handle situation when entity could not be added. It's already exist
     *
     * @param exception thrown exception
     * @return custom exception info and http status
     */
    @ExceptionHandler(value = EntityExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ResponseException> handleEntityExistException(EntityExistException exception) {
        return new ResponseEntity<>(new ResponseException(messageSource.getMessage("entity.exist.message",
                null, LocaleContextHolder.getLocale()), exception.getExceptionCode()),
                HttpStatus.CONFLICT);
    }

    /**
     * Handle situation when parameters in URL are incorrect
     *
     * @param exception thrown exception
     * @return custom exception info and http status
     */
    @ExceptionHandler(value = ExpectationFailedException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<ResponseException> handleExpectationFailedException(ExpectationFailedException exception) {
        return new ResponseEntity<>(new ResponseException(messageSource.getMessage("expectation.failed.message",
                null, LocaleContextHolder.getLocale()), exception.getExceptionCode()),
                HttpStatus.EXPECTATION_FAILED);
    }

    /**
     * Handle situation when amount of elements per page in URL
     * are incorrect (less then 1 or bigger then 100)
     *
     * @param exception thrown exception
     * @return custom exception info and http status
     */
    @ExceptionHandler(value = PageElementAmountException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<ResponseException> handlePageElementAmountException(PageElementAmountException exception) {
        return new ResponseEntity<>(new ResponseException(messageSource.getMessage("page.element.amount.message",
                null, LocaleContextHolder.getLocale()), exception.getExceptionCode()),
                HttpStatus.EXPECTATION_FAILED);
    }

    /**
     * Handle situation when number of page in URL bigger then amount of pages
     *
     * @param exception thrown exception
     * @return custom exception info and http status
     */
    @ExceptionHandler(value = PageNumberException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ResponseEntity<ResponseException> handlePageNumberException(PageNumberException exception) {
        return new ResponseEntity<>(new ResponseException(messageSource.getMessage("bid.page.number.message",
                new Object[]{exception.getPagesAmount()}, LocaleContextHolder.getLocale()), exception.getExceptionCode()),
                HttpStatus.EXPECTATION_FAILED);
    }

    /**
     * Handle @PathVariable incorrect param
     *
     * @return custom exception info and http status
     */
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseException> handleMethodArgumentTypeMismatch() {
        return new ResponseEntity<>(new ResponseException(messageSource.getMessage("invalid.argument.message",
                null, LocaleContextHolder.getLocale()), 40015),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NotFoundLoginException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseException> handleNotFoundLoginException(NotFoundLoginException exception) {
        return new ResponseEntity<>(new ResponseException(messageSource.getMessage("not.found.login.message",
                new Object[]{exception.getLogin()}, LocaleContextHolder.getLocale()),
                exception.getExceptionCode()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = JwtAuthenticationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseException> handleJwtAuthenticationException(JwtAuthenticationException exception) {
        return new ResponseEntity<>(new ResponseException(messageSource.getMessage(exception.getMessage(),
                null, LocaleContextHolder.getLocale()),
                exception.getExceptionCode()), HttpStatus.BAD_REQUEST);
    }
}
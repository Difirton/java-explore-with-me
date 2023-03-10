package ru.practicum.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.category.error.CategoryNotFoundException;
import ru.practicum.compilation.error.CompilationNotFoundException;
import ru.practicum.event.error.EventNotFoundException;
import ru.practicum.request.error.RequestNotFoundException;
import ru.practicum.user.error.UserNotFoundException;
import ru.practicum.web.dto.error.ErrorDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class MainGlobalExceptionHandler {
    public static final String ERROR_LOG = "Error {}: {}";

    @ExceptionHandler({UserNotFoundException.class, RequestNotFoundException.class, EventNotFoundException.class,
    CompilationNotFoundException.class, CategoryNotFoundException.class})
    public ResponseEntity<ErrorDto> handleNotFound(RuntimeException e) {
        log.error(ERROR_LOG, e.getClass().getSimpleName(), e.getMessage());
        ErrorDto errorDto = ErrorDto.builder()
                .reason("The required object was not found.")
                .message(e.getMessage())
                .status(HttpStatus.NOT_FOUND.getReasonPhrase())
                .timestamp(LocalDateTime.now())
                .errors(Arrays.stream(e.getStackTrace())
                        .map(StackTraceElement::getClassName)
                        .collect(Collectors.toList()))
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({IllegalStateException.class, IllegalArgumentException.class,
            InvalidDataAccessApiUsageException.class, javax.validation.ConstraintViolationException.class,
            HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorDto> handleBadRequest(RuntimeException e) {
        log.info(ERROR_LOG, e.getClass().getSimpleName(), e.getMessage());
        ErrorDto errorDto = ErrorDto.builder()
                .reason("For the requested operation the conditions are not met.")
                .message(e.getMessage())
                .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .timestamp(LocalDateTime.now())
                .errors(Arrays.stream(e.getStackTrace())
                        .map(StackTraceElement::getClassName)
                        .collect(Collectors.toList()))
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, MissingServletRequestParameterException.class})
    public ResponseEntity<List<ErrorDto>> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        log.error(ERROR_LOG, e.getClass().getSimpleName(), e.getMessage());
        List<ErrorDto> errorDtoList = new ArrayList<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            ErrorDto errorDto = ErrorDto.builder()
                    .reason("For the requested operation the conditions are not met")
                    .message(error.getDefaultMessage())
                    .status(HttpStatus.BAD_REQUEST.getReasonPhrase())
                    .timestamp(LocalDateTime.now())
                    .errors(Arrays.stream(e.getStackTrace())
                            .map(StackTraceElement::getClassName)
                            .collect(Collectors.toList()))
                    .build();
            errorDtoList.add(errorDto);
        });
        return new ResponseEntity<>(errorDtoList, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
    public ResponseEntity<ErrorDto> handleConflict(RuntimeException e) {
        log.error(ERROR_LOG, e.getClass().getSimpleName(), e.getMessage());
        ErrorDto errorDto = ErrorDto.builder()
                .reason("Error occurred")
                .message(e.getMessage())
                .status(HttpStatus.CONFLICT.getReasonPhrase())
                .timestamp(LocalDateTime.now())
                .errors(Arrays.stream(e.getStackTrace())
                        .map(StackTraceElement::getClassName)
                        .collect(Collectors.toList()))
                .build();
        return new ResponseEntity<>(errorDto, HttpStatus.CONFLICT);
    }
}
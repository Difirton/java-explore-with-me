package ru.practicum.request.error;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestNotFoundException extends RuntimeException {
    public RequestNotFoundException(Long id) {
        super(String.format("Request with id = %s, not found", id));
        log.error("Request with id = {}, not found", id);
    }

    public RequestNotFoundException(Long id, Long userId) {
        super(String.format("Request with id = %s of user with id = %s, not found", id, userId));
        log.error("Request with id = {} of user with id = {}, not found", id, userId);
    }
}

package ru.practicum.request.error;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestsLimitException extends RuntimeException {

    public RequestsLimitException(Long id) {
        super(String.format("The limit of applications has been reached, application with id has been rejected = %s",
                id));
        log.error("The limit of applications has been reached, application with id has been rejected = {}", id);
    }
}

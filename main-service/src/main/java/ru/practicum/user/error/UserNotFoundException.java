package ru.practicum.user.error;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super(String.format("User request with id = %s, not found", id));
        log.error("User request with id = {}, not found", id);
    }
}

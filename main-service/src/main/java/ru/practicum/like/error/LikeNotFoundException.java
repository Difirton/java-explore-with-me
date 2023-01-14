package ru.practicum.like.error;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LikeNotFoundException extends RuntimeException {

    public LikeNotFoundException(Long id) {
        super(String.format("Like with id = %s, not found", id));
        log.error("Like with id = {}, not found", id);
    }

    public LikeNotFoundException(Long userId, Long eventId) {
        super(String.format("Like for user with id = %s of event with id = %s, not found", userId, eventId));
        log.error("Like for user with id = {} of event with id = {} not found", userId, eventId);
    }
}

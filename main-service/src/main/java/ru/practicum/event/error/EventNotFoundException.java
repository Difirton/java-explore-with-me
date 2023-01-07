package ru.practicum.event.error;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventNotFoundException extends RuntimeException {

    public EventNotFoundException(Long id) {
        super(String.format("Event with id = %s, not found", id));
        log.error("Event with id = {}, not found", id);
    }

    public EventNotFoundException(Long id, Long userId) {
        super(String.format("Event with id = %s, not found for user with id = %s", id, userId));
        log.error("Event with id = {}, not found for user with id = {}", id, userId);
    }
}

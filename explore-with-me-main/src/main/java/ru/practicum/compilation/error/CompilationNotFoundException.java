package ru.practicum.compilation.error;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompilationNotFoundException extends RuntimeException {

    public CompilationNotFoundException(Long id) {
        super(String.format("Compilation with id = %s, not found", id));
        log.error("Compilation with id = {}, not found", id);
    }
}

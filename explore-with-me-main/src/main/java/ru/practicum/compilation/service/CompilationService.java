package ru.practicum.compilation.service;

import ru.practicum.compilation.repository.Compilation;

public interface CompilationService {
    Compilation createCompilation(Compilation compilation);

    void deleteCompilation(Long compId);

    void deleteEventFromCompilation(Long compId, Long eventId);

    void addEventToCompilation(Long compId, Long eventId);

    void unpinCompilation(Long compId);

    void pinCompilation(Long compId);
}

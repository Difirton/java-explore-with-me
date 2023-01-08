package ru.practicum.compilation.service;

import ru.practicum.compilation.repository.Compilation;

import java.util.List;
import java.util.Optional;

public interface CompilationService {
    Compilation createCompilation(Compilation compilation);

    void deleteCompilation(Long compId);

    void deleteEventFromCompilation(Long compId, Long eventId);

    void addEventToCompilation(Long compId, Long eventId);

    void unpinCompilation(Long compId);

    void pinCompilation(Long compId);

    List<Compilation> findCompilations(Optional<Boolean> pinned, Integer from, Integer size);

    Compilation findCompilationById(Long compilationId);
}

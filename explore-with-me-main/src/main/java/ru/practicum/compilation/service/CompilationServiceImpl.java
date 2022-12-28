package ru.practicum.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.compilation.error.CompilationNotFoundException;
import ru.practicum.compilation.repository.Compilation;
import ru.practicum.compilation.repository.CompilationRepository;
import ru.practicum.event.error.EventNotFoundException;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.event.repository.entity.Event;

@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    @Autowired
    private final CompilationRepository compilationRepository;
    @Autowired
    private final EventRepository eventRepository;

    @Override
    public Compilation createCompilation(Compilation compilation) {
        return compilationRepository.save(compilation);
    }

    @Override
    public void deleteCompilation(Long compId) {
        compilationRepository.deleteById(compId);
    }

    @Transactional
    @Override
    public void deleteEventFromCompilation(Long compId, Long eventId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new CompilationNotFoundException(compId));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        event.getCompilations().remove(compilation);
        compilation.getEvents().remove(event);
        compilationRepository.save(compilation);
        eventRepository.save(event);
    }

    @Transactional
    @Override
    public void addEventToCompilation(Long compId, Long eventId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new CompilationNotFoundException(compId));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        event.getCompilations().add(compilation);
        compilation.getEvents().add(event);
        compilationRepository.save(compilation);
        eventRepository.save(event);
    }

    @Override
    public void unpinCompilation(Long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new CompilationNotFoundException(compId));
        compilation.setPinned(false);
        compilationRepository.save(compilation);
    }

    @Override
    public void pinCompilation(Long compId) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new CompilationNotFoundException(compId));
        compilation.setPinned(true);
        compilationRepository.save(compilation);
    }
}

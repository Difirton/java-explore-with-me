package ru.practicum.event.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.event.repository.entity.Event;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long>, CustomEventRepository {

    Optional<Event> findByIdAndInitiatorId(Long id, Long initiatorId);

    List<Event> findEventsByInitiatorId(Long userId, PageRequest pageRequest);
}